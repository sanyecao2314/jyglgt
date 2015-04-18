package nc.bs.jyglgt.povoicedao;

import java.util.ArrayList;
import java.util.HashMap;
import nc.bs.jyglgt.dao.ServerDAO;
import nc.bs.logging.Logger;
import nc.bs.pub.pf.PfUtilBO;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.ui.pub.billcodemanage.BillcodeRuleBO_Client;
import nc.vo.jyglgt.bfsfcllog.BfsfclLogVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pi.InvoiceHeaderVO;
import nc.vo.pi.InvoiceItemVO;
import nc.vo.pi.InvoiceVO;
import nc.vo.pub.BusinessException;


/**
 * @author 施鹏
 * @功能： 物流自动化系统的采购结算单后台处理类
 * 
 */
public class PoVoiceDAO extends ServerDAO {
	public PoVoiceDAO() {
		super();
	}

	PersistenceManager sManager = null;

	/**
	 * 更新采购结算单表数据
	 * */
	public void updateBFSFCL(String key,String datasource) throws BusinessException {
		try {
			JdbcSession session = getsManager(datasource).getJdbcSession();
			String sql = " update BFCLJZD set BFCLJZD_C15='1' where BFCLJZD_JSBH='"+key+"'";
			session.executeUpdate(sql);
		} catch (DbException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

	}

	

	
	/**
	 * 向中间表插入数据
	 * */
	public void insertIntePoVoiceByBfgl(String datasource) throws BusinessException {
		insertIntePoStore(datasource);
	}

	/**
	 * 插入中间表数据
	 * @throws BusinessException 
	 * **/
    public void insertIntePoStore(String datasource) throws BusinessException{
		try {
			JdbcSession session = getsManager(datasource).getJdbcSession();
			// 查询语句
			String sql = "select  * from BFCLJZD  where BFCLJZD_C15<>'1' and BFCLJZD_SHBZ='0'";
			// 数据库访问操作
			ArrayList al = (ArrayList) session.executeQuery(sql,new MapListProcessor());
			// 遍历结果集
			if (al!=null&&al.size() > 0) {
				for (int i = 0; i < al.size(); i++) {
					HashMap hm = (HashMap) al.get(i);
					String cgeneralhid = null;
					try{
						//判断传输值是否已经存在，如果有则跳过
						String intehsql = "select pk_invoice from po_invoice where cbilltypecode like '25%' and vuserdef20 ='"+Toolkits.getUFDouble(hm.get("bfcljzd_jsbh"))+"' ";
						String pk_gen=queryStrBySql(intehsql);
						if(pk_gen!=null&&!pk_gen.equals("")){
							continue;
						}
						//传输数据到采购发票
    					insertInvoice(hm); 
    					//更新自动化系统标记
    					updateBFSFCL(Toolkits.getString(hm.get("bfcljzd_jsbh")),datasource);
					}catch(Exception e){
						e.printStackTrace();
						//保存传输过程出现的错误历史记录
						insertExLog(e,Toolkits.getString(hm.get("bfcljzd_jsbh")),Toolkits.getString(hm.get("bfcljzd_c8")));					
						continue;
					}
					//将已经传输成功的在错误历史记录还有的数据清除掉
					delExLog(Toolkits.getString(hm.get("bfcljzd_jsbh")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		} 
		finally {
			if (sManager != null)
				sManager.release();// 需要关闭会话
		}
    }
    

    
    /**
     * 保存传输过程出现的错误历史记录
     * */
    private void insertExLog(Exception e,String bfsfcl_djbh,String pk_corp){
		BfsfclLogVO logvo=new BfsfclLogVO();
		logvo.setDr(new Integer(0));
		logvo.setBilltype("25");
		logvo.setPk_corp(pk_corp);
		logvo.setBfsfcl_djbh(bfsfcl_djbh);
		logvo.setExlog(e.getMessage());
		try {
			insertVObackPK(logvo);
		} catch (BusinessException e1) {
			e1.printStackTrace();
			Logger.debug("保存传输过程出现的错误历史记录出现异常:insertExLog,可能是网络异常");
		}
    }
    
    /**
     * 将已经传输成功的在错误历史记录还有的数据清除掉
     * */
    private void delExLog(String bfsfcl_djbh){
    	String sql=" select pk_bfsfcllog from jyglgt_bfsfcllog  where bfsfcl_djbh='"+bfsfcl_djbh+"' ";
    	ArrayList<HashMap<String,String>> list= null;
		try {
			list = queryArrayBySql(sql);
	    	if(list!=null&&list.size()>0){
	    		for(HashMap<String,String> map:list){
		    		String delsql=" delete from  jyglgt_bfsfcllog where pk_bfsfcllog='"+map.get("pk_bfsfcllog")+"'";
		    		updateBYsql(delsql);
	    		}
	    	}
		} catch (BusinessException e) {
			e.printStackTrace();
			Logger.debug("将已经传输成功的在错误历史记录还有的数据清除掉出现异常:delExLog,可能是网络异常");
		}

    }

	private PersistenceManager getsManager(String datasource) throws DbException {
		if (sManager == null)
			sManager = PersistenceManager.getInstance(datasource);
		return sManager;
	}
	
	private String u8TsToNcTs(String u8Ts){
		String ncTs="";
		if(u8Ts==null||u8Ts.equalsIgnoreCase("")||u8Ts.length()<=19){
			ncTs=u8Ts;
		}else{
			ncTs=u8Ts.substring(0,19);
		}
		return ncTs;
	}
	
	/**
	 * 生成采购发票
	 * @throws BusinessException 
	 * **/
    public void insertInvoice(HashMap hm) throws BusinessException{
    	String pk_corp=Toolkits.getString(hm.get("bfcljzd_c8"));
    	InvoiceHeaderVO hvo=new InvoiceHeaderVO();
		String cuserid = getCuserid(Toolkits.getString(hm.get("bfcljzd_zdxm")));
		if("".equals(cuserid)){
			throw new BusinessException("根据制单人传入的值，没有在用户档案中查询到用户！"); 
		}
		hvo.setCoperator(cuserid);//制单人
		hvo.setTmaketime(_getTime());
		hvo.setDinvoicedate(_getDate());
		String cwarehouseid = getCwarehouseid(Toolkits.getString(hm.get("bfcljzd_c7")),pk_corp);
		if("".equals(cwarehouseid)){
			throw new BusinessException("根据传输的仓库编码没有找到仓库！");
		}	
		String custpk = getDocPkByCode(pk_corp,Toolkits.getString(hm.get("bfcljzd_shbh")),2);
		if("".equals(custpk)){
			throw new BusinessException("根据供应商编码传入的值，没有在客商档案中找到！");
		}
		hvo.setCvendormangid(custpk);////供应商ID
		hvo.setDarrivedate(Toolkits.getUFDate(hm.get("bfcljzd_zzrq")));//票到日期
		hvo.setIinvoicetype(new Integer(0));//发票类型
		String pk_calbody=getPk_calbody(cwarehouseid);
		if("".equals(pk_calbody)){
			throw new BusinessException("库存组织为空！");
		}
		hvo.setCstoreorganization(pk_calbody);//库存组织PK
		String pk_cubasdoc=getCustcode(Toolkits.getString(hm.get("bfcljzd_shbh")));
		hvo.setCvendorbaseid(pk_cubasdoc);//供应商基本档案ID
		try {
			//生成单据编号
			String billNo = BillcodeRuleBO_Client.getBillCode("25", pk_corp,null, null);
			if(billNo!=null)
			    hvo.setVinvoicecode(billNo);
			else
				hvo.setVinvoicecode("25000001");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hvo.setCbilltype("25");
		hvo.setPk_corp(pk_corp);
		hvo.setDr(new Integer(0));
		hvo.setIbillstatus(IJyglgtBillStatus.FREE);	
		//子表
		InvoiceItemVO[] itemvo = new InvoiceItemVO[1];
    	for (int i = 0; i < itemvo.length; i++) {
    		itemvo[i]  = new InvoiceItemVO();
    		itemvo[i].setDr(new Integer(0));
    		itemvo[i].setPk_corp(pk_corp);		
    		ArrayList<HashMap<String, String>> listpkm = getPk_invmb(Toolkits.getString(hm.get("bfcljzd_wlbh")), pk_corp);
    		if(listpkm.size()==0){
    			throw new BusinessException("存货编码"+Toolkits.getString(hm.get("bfcljzd_wlbh"))+"在NC中没有维护！");
    		}
    		itemvo[i].setCrowno( String.valueOf(10*(i+1)));//行号
            itemvo[i].setCmangid(listpkm.get(0).get("pk_invmandoc"));//存货管理档案
            itemvo[i].setCbaseid(listpkm.get(0).get("pk_invbasdoc"));//存货基本档案
            itemvo[i].setNinvoicenum(Toolkits.getUFDouble(hm.get("bfcljzd_yssl")));//发票数量
            itemvo[i].setNoriginalcurprice(Toolkits.getUFDouble(hm.get("bfcljzd_hsdj")));//单价
            itemvo[i].setNmoney(Toolkits.getUFDouble(hm.get("bfcljzd_jsje")));//本币金额
            itemvo[i].setNoriginalcurmny(Toolkits.getUFDouble(hm.get("bfcljzd_jsje")));//金额
            itemvo[i].setNoriginalsummny(Toolkits.getUFDouble(hm.get("bfcljzd_jsje")));//价税合计
            itemvo[i].setNtaxrate(Toolkits.getUFDouble(hm.get("bfcljzd_jsje")));//税率
    		ArrayList<HashMap<String, String>> listhsljldw = new ArrayList<HashMap<String,String>>();
    		listhsljldw = getHslJldw(listpkm.get(0).get("pk_invbasdoc"));
    		if(listhsljldw.size()>0){
    			itemvo[i].setCbaseid(listhsljldw.get(0).get("pk_measdoc"));//单位
    		}    		
		}
    	InvoiceVO billvo = new InvoiceVO();
		billvo.setParentVO(hvo);
		billvo.setChildrenVO(itemvo);
		try {
			//获取返回值
			ArrayList al = (ArrayList) new PfUtilBO().processAction("WRITE", "25", _getDate().toString(), null, billvo, null);
		} catch (Exception e) {
//			showErrorMessage(""+e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
    }
 
}
