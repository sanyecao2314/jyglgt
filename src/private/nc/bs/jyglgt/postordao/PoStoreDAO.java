package nc.bs.jyglgt.postordao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nc.bs.jyglgt.dao.ServerDAO;
import nc.bs.logging.Logger;
import nc.bs.pub.pf.PfUtilBO;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.vo.ic.pub.bill.GeneralBillHeaderVO;
import nc.vo.ic.pub.bill.GeneralBillItemVO;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.ic.pub.smallbill.SMGeneralBillVO;
import nc.vo.jyglgt.bfsfcllog.BfsfclLogVO;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;


/**
 * @author 施鹏
 * @功能： 物流自动化系统的收货过磅单后台处理类
 * 
 */
public class PoStoreDAO extends ServerDAO {
	public PoStoreDAO() {
		super();
	}

	PersistenceManager sManager = null;
	
	/**
	 * 
	 */
	String vbillcode = null;

	/**
	 * 更新收货榜单表数据
	 * */
	public void updateBFSFCL(String key,int flag,String datasource) throws BusinessException {
		try {
			JdbcSession session = getsManager(datasource).getJdbcSession();
			String sql = " update BFSFCL set BFSFCL_RKBZ='"+flag+"',BFSFCL_RKDH='" + vbillcode + "' where BFSFCL_DJLX='1' and BFSFCL_DJBH='"+key+"'";
			session.executeUpdate(sql);
		} catch (DbException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

	}

	

	
	/**
	 * 向中间表插入数据
	 * */
	public void insertIntePoStorByBfgl(String datasource) throws BusinessException {
		insertIntePoStore(datasource);
	}

	/**
	 * 插入中间表数据
	 * @throws BusinessException 
	 * **/
    public void insertIntePoStore(String datasource) throws BusinessException{
		try {
			JdbcSession session = getsManager(datasource).getJdbcSession();
			// 查询语句    BFSFCL_RKBZ:入库标志 0:未入库；1：采购
			String sql = "select  * from BFSFCL  where BFSFCL_RKBZ='0' and BFSFCL_C13='1'";
			// 数据库访问操作
			ArrayList al = (ArrayList) session.executeQuery(sql,new MapListProcessor());
			// 遍历结果集
			if (al!=null&&al.size() > 0) {
				for (int i = 0; i < al.size(); i++) {
					HashMap hm = (HashMap) al.get(i);
					String cgeneralhid = null;
					try{
						//判断传输值是否已经存在，如果有则跳过
						String intehsql = "select cgeneralhid from ic_general_h where cbilltypecode ='45' and vuserdef20 ='"+Toolkits.getUFDouble(hm.get("bfsfcl_djbh"))+"' and nvl(dr,0)=0 ";
						String pk_gen=queryStrBySql(intehsql);
						if(pk_gen!=null&&!pk_gen.equals("")){
							continue;
						}
						//传输数据到库存采购入库单
    					cgeneralhid=insertGen(hm,session); 
    					//更新自动化系统标记
    					updateBFSFCL(Toolkits.getString(hm.get("bfsfcl_djbh")),1,datasource);
					}catch(Exception e){
						e.printStackTrace();
						//保存传输过程出现的错误历史记录
						insertExLog(e,Toolkits.getString(hm.get("bfsfcl_djbh")),Toolkits.getString(hm.get("bfsfcl_c12")));					
						continue;
					}
					//将已经传输成功的在错误历史记录还有的数据清除掉
					delExLog(Toolkits.getString(hm.get("bfsfcl_djbh")));
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
		logvo.setBilltype("45");
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
	 * 生成库存采购入库单
	 * @param session 
	 * @throws BusinessException 
	 * @throws DbException 
	 * **/
    public String insertGen(HashMap hm, JdbcSession session) throws BusinessException, DbException{
    	String pk_corp=Toolkits.getString(hm.get("bfsfcl_c12"));
		GeneralBillHeaderVO genHVO = new GeneralBillHeaderVO();
//		String cuserid = getCuserid(Toolkits.getString(hm.get("bfsfcl_sby")));
		genHVO.setAttributeValue("cdispatcherid", "0001A7100000000007Y7");//默认值采购入库
		String cuserid = getCuserid("0000");//默认值
		if("".equals(cuserid)){
			throw new BusinessException("根据司磅员传入的值，没有在用户档案中查询到用户！"); 
		}
		genHVO.setAttributeValue("coperatorid", cuserid);//制单人
		
		UFDate resdate = getNCDateFormat(hm.get("bfsfcl_rq"));//转成NC的格式 8位外系统'20140122' --->'2014-01-22'
		genHVO.setAttributeValue("dbilldate", resdate);//单据日期
		String ckbh = Toolkits.getString(hm.get("bfsfcl_ckbh"));
		if(ckbh.equals("")){
			throw new BusinessException("传输的仓库编码为空无法生成NC入库单！");
		}
		String cwarehouseid = getCwarehouseid(ckbh,pk_corp);
		if("".equals(cwarehouseid)){
			throw new BusinessException("根据传输的仓库编码没有找到仓库！");
		}
		genHVO.setAttributeValue("cwarehouseid", cwarehouseid);//仓库	
		String custpk = getDocPkByCode(pk_corp,Toolkits.getString(hm.get("bfsfcl_shbh")),2);
		if("".equals(custpk)){
			throw new BusinessException("根据供应商编码传入的值，没有在客商档案中找到！");
		}
		genHVO.setAttributeValue("cproviderid",custpk);////供应商ID
		if("".equals(getPk_calbody(cwarehouseid))){
			throw new BusinessException("库存组织为空！");
		}
		genHVO.setAttributeValue("pk_calbody",getPk_calbody(cwarehouseid));//库存组织PK
		String pk_cubasdoc=getCustcode(Toolkits.getString(hm.get("bfsfcl_shbh")));
		genHVO.setAttributeValue("pk_cubasdoc",pk_cubasdoc);//供应商基本档案ID
		genHVO.setAttributeValue("vuserdef19",Toolkits.getString(hm.get("bfsfcl_clbh")));//车号
		genHVO.setAttributeValue("vuserdef20",Toolkits.getString(hm.get("bfsfcl_djbh")));//单据编号
		genHVO.setAttributeValue("vuserdef1",Toolkits.getString(hm.get("bfsfcl_pczh")));//派车单号 add by gdt
		String ywlb = Toolkits.getString(hm.get("bfsfcl_ywlb"));//自动化的业务类别 add by gdt 单品外购、单品内购（没有辅数量） 多品外购（有辅数量）
//		try {
//			//生成单据编号
//			String billNo = BillcodeRuleBO_Client.getBillCode("45", pk_corp,null, null);
//			if(billNo!=null)
//				genHVO.setVbillcode(billNo);
//			else
//				genHVO.setVbillcode("45000001");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		vbillcode = genHVO.getVbillcode();
		//BFSFCL_KZZD换成bfsfcl_htbh
		String orderbid = Toolkits.getString(hm.get("bfsfcl_c6"));
		if(orderbid == null || orderbid.trim().equals("")){
			throw new BusinessException("传入的采购订单子表主键为空,BFSFCL_C6为空");
		}
		String pk_order = getPo_order(orderbid);
		if("".equals(pk_order)){
			throw new BusinessException("查找不到对应的采购订单");
		}
		//获取订单信息
		String sql_order="select * from po_order where corderid='"+pk_order+"'";
		ArrayList list_order=queryArrayBySql(sql_order);
		if(list_order == null || list_order.size() == 0){
			throw new BusinessException("查找不到对应的采购订单");
		}
		
		HashMap map =(HashMap)list_order.get(0);
		genHVO.setAttributeValue("cbiztype",Toolkits.getString(map.get("cbiztype")) );//业务类型
		genHVO.setAttributeValue("vuserdef17",Toolkits.getString(map.get("vdef17")) );//业务类型
		String lkfs = Toolkits.getString(map.get("vdef11"));//入库方式 add by gdt
		
		genHVO.setAttributeValue("vuserdef12", lkfs);
		
			
		// 设置加锁用字段
		genHVO.setCoperatoridnow(cuserid);
		genHVO.setVnote("自动化系统生成");
		genHVO.setAttributeValue("fbillflag", nc.vo.pub.VOStatus.NEW);//Toolkits.getInteger(saleHVO.getIverifystate()).toString());//单据状态
		genHVO.setAttributeValue("cbilltypecode", "45");//
		genHVO.setAttributeValue("pk_corp",pk_corp);//pk_corp
		genHVO.setAttributeValue("pk_purcorp",pk_corp);//pk_corp
		genHVO.setAttributeValue("dr", 0);
		//子表
		GeneralBillItemVO[] genBVO = null;
		if("单品外购".equals(ywlb)||"单品内购".equals(ywlb)){//单品只有一个子表
			genBVO = new GeneralBillItemVO[1];
			for (int i = 0; i < genBVO.length; i++) {
				genBVO[i]  = new GeneralBillItemVO();
				genBVO[i].setAttributeValue("dr", 0);
				genBVO[i].setAttributeValue("pk_corp", pk_corp);
				genBVO[i].setAttributeValue("pk_invoicecorp", pk_corp);//开票公司 add by gdt
				genBVO[i].setDbizdate( genHVO.getDbilldate());   		
				ArrayList<HashMap<String, String>> listpkm = getPk_invmb(Toolkits.getString(hm.get("bfsfcl_wlbh")), pk_corp);
				if(listpkm.size()==0){
					throw new BusinessException("存货编码"+Toolkits.getString(hm.get("bfsfcl_wlbh"))+"在NC中没有维护！");
				}
				genBVO[i].setAttributeValue("crowno", 10*(i+1));//行号
				genBVO[i].setAttributeValue("cinventoryid", listpkm.get(0).get("pk_invmandoc"));//存货管理档案
				genBVO[i].setAttributeValue("cinvbasid", listpkm.get(0).get("pk_invbasdoc"));//存货基本档案
				
				
				String pk_order_b = getPo_order_b(listpkm.get(0).get("pk_invbasdoc"), pk_order);
				if("".equals(pk_order_b)){
					throw new BusinessException("查找不到对应的采购订单表体行");
				}
				genBVO[i].setCsourcebillhid(pk_order);//来源单据表头序列号
				genBVO[i].setAttributeValue("csourcebillbid", pk_order_b); //来源单据表体序列号
				genBVO[i].setAttributeValue("csourcetype", "21"); //来源单据类型
				genBVO[i].setCfirstbillbid(pk_order_b);
				genBVO[i].setCfirstbillhid( pk_order);
				genBVO[i].setCfirsttype("21");
//    		genBVO[i].setAttributeValue("dvalidate", Toolkits.getUFDate(salebvo[i].getDvdate()));//失效日期
//    		genBVO[i].setAttributeValue("scrq", Toolkits.getUFDate(saleHVO.getDveridate().substring(0, 10)));//生产日期
				//分情况设置 应收发 实收发 辅数 add by gdt
				if("单品外购".equals(ywlb)||"单品内购".equals(ywlb)){//单品外购、单品内购（没有辅数量）
					if("我方".equals(lkfs)){//bfsfcl_jz写入NC采购入库单的实收数量；如果是对方，将BFSFCL_YSSL写入NC采购入库单的实收数量。表体将原发数量(BFSFCL_YSSL)-自定项12-数量；实收数量（bfsfcl_jz）写入应收数量
						genBVO[i].setAttributeValue("ninnum", Toolkits.getUFDouble(hm.get("bfsfcl_jz")));//实收数量
						genBVO[i].setAttributeValue("nshouldinnum", Toolkits.getUFDouble(hm.get("bfsfcl_jz")));//应收数量
					}else if("对方".equals(lkfs)){
						genBVO[i].setAttributeValue("ninnum", Toolkits.getUFDouble(hm.get("bfsfcl_yssl")));//实收数量
						genBVO[i].setAttributeValue("nshouldinnum", Toolkits.getUFDouble(hm.get("bfsfcl_jz")));//应收数量
					}else{
						throw new BusinessException("所对应订单的入库方式，既非我方，也非对方，请检查");
					}
					genBVO[i].setAttributeValue("vuserdef12", Toolkits.getUFDouble(hm.get("bfsfcl_yssl")));//原发数量
				}
				
				String dbid = Toolkits.getString(hm.get("bfsfcl_c6"));
				String sql = "select noriginalcurprice, noriginalcurmny from po_order_b where corder_bid ='" +dbid+ "'";
				ArrayList<HashMap<String, String>> borderList =queryArrayBySql(sql);
				if(borderList == null || borderList.size() == 0){
					throw new BusinessException("查找不到对应的采购订单体");
				}else{
					genBVO[i].setAttributeValue("nprice", borderList.get(0).get("noriginalcurprice"));//单价
					genBVO[i].setAttributeValue("nmny", borderList.get(0).get("noriginalcurmny"));//金额
					genBVO[i].setAttributeValue("nplannedprice", borderList.get(0).get("noriginalcurprice"));//单价
					genBVO[i].setAttributeValue("nplannedmny", borderList.get(0).get("noriginalcurmny"));//金额
				}
				
				ArrayList<HashMap<String, String>> listhsljldw = new ArrayList<HashMap<String,String>>();
				listhsljldw = getHslJldw(listpkm.get(0).get("pk_invbasdoc"));
				if(listhsljldw.size()>0){
					genBVO[i].setAttributeValue("castunitid", listhsljldw.get(0).get("pk_measdoc1"));//辅计量单位
					genBVO[i].setAttributeValue("hsl", listhsljldw.get(0).get("mainmeasrate"));//换算率
				}    		
			}
		}else if("多品外购".equals(ywlb)){//多品从 发货登记表体 里拿数据封装成 入库单的子表
			String bfsfcl_djbh = Toolkits.getString(hm.get("bfsfcl_djbh"));//发货登记表体 关联字段1
			String bfsfcl_c12 = Toolkits.getString(hm.get("bfsfcl_c12"));//发货登记表体 关联字段2
			String sql = " select * from BFPCDMX where BFPCDMX_C3='"+bfsfcl_djbh+"' and BFPCDMX_C4='"+bfsfcl_c12+"'";//
			ArrayList al = (ArrayList) session.executeQuery(sql,new MapListProcessor());
			if(al == null || al.size() == 0){
				throw new BusinessException("多品外购类型,但在自动化'发货登记表体'里没找到子表记录");
			}
			
			genBVO = new GeneralBillItemVO[al.size()];
			for (int i = 0; i < al.size(); i++) {
				Map smp = (Map) al.get(i);
				String invcode = Toolkits.getString(smp.get("bfpcdmx_wlbh"));
				genBVO[i]  = new GeneralBillItemVO();
				genBVO[i].setAttributeValue("dr", 0);
				genBVO[i].setAttributeValue("pk_corp", pk_corp);
				genBVO[i].setAttributeValue("pk_invoicecorp", pk_corp);//开票公司 add by gdt
				genBVO[i].setDbizdate( genHVO.getDbilldate());   		
				ArrayList<HashMap<String, String>> listpkm = getPk_invmb(invcode, pk_corp);
				if(listpkm.size()==0){
					throw new BusinessException("存货编码"+invcode+"在NC中没有维护！");
				}
				genBVO[i].setAttributeValue("crowno", 10*(i+1));//行号
				genBVO[i].setAttributeValue("cinventoryid", listpkm.get(0).get("pk_invmandoc"));//存货管理档案
				genBVO[i].setAttributeValue("cinvbasid", listpkm.get(0).get("pk_invbasdoc"));//存货基本档案
				
				
				String pk_order_b = getPo_order_b(listpkm.get(0).get("pk_invbasdoc"), pk_order);
				if("".equals(pk_order_b)){
					throw new BusinessException("查找不到对应的采购订单表体行");
				}
				genBVO[i].setCsourcebillhid(pk_order);//来源单据表头序列号
				genBVO[i].setAttributeValue("csourcebillbid", pk_order_b); //来源单据表体序列号
				genBVO[i].setAttributeValue("csourcetype", "21"); //来源单据类型
				genBVO[i].setCfirstbillbid(pk_order_b);
				genBVO[i].setCfirstbillhid( pk_order);
				genBVO[i].setCfirsttype("21");
//    		genBVO[i].setAttributeValue("dvalidate", Toolkits.getUFDate(salebvo[i].getDvdate()));//失效日期
//    		genBVO[i].setAttributeValue("scrq", Toolkits.getUFDate(saleHVO.getDveridate().substring(0, 10)));//生产日期
				//分情况设置 应收发 实收发 辅数 add by gdt
				if("多品外购".equals(ywlb)){//多品外购（有辅数量）
					if("我方".equals(lkfs)){
						genBVO[i].setAttributeValue("ninnum", Toolkits.getUFDouble(smp.get("bfpcdmx_jz")));//实收数量
						genBVO[i].setAttributeValue("ninassistnum", Toolkits.getUFDouble(smp.get("bfpcdmx_sfjs")));//实收辅数量
						genBVO[i].setAttributeValue("nshouldinnum", Toolkits.getUFDouble(smp.get("bfpcdmx_jz")));//应收数量
						genBVO[i].setNneedinassistnum(Toolkits.getUFDouble(smp.get("bfpcdmx_sfjs")));//应收辅数量
					}else if("对方".equals(lkfs)){
						genBVO[i].setAttributeValue("ninnum", Toolkits.getUFDouble(smp.get("bfpcdmx_sl")));//实收数量
						genBVO[i].setAttributeValue("ninassistnum", Toolkits.getUFDouble(smp.get("bfpcdmx_js")));//实收辅数量
						genBVO[i].setAttributeValue("nshouldinnum", Toolkits.getUFDouble(smp.get("bfpcdmx_jz")));//应收数量
						genBVO[i].setNneedinassistnum(Toolkits.getUFDouble(smp.get("bfpcdmx_sfjs")));//应收辅数量
					}else{
						throw new BusinessException("所对应订单的入库方式，既非我方，也非对方，请检查");
					}
					genBVO[i].setAttributeValue("vuserdef12", Toolkits.getUFDouble(smp.get("bfpcdmx_sl")));//原发数量
					genBVO[i].setAttributeValue("vuserdef11", Toolkits.getUFDouble(smp.get("bfpcdmx_js")));//原发件数
				}

				String bid = Toolkits.getString(smp.get("bfpcdmx_tdfl"));
				String bsql = "select noriginalcurprice, noriginalcurmny from po_order_b where corder_bid ='" +bid+ "'";
				ArrayList<HashMap<String, String>> borderList =queryArrayBySql(bsql);
				if(borderList == null || borderList.size() == 0){
					throw new BusinessException("查找不到对应的采购订单体");
				}else{
					genBVO[i].setAttributeValue("nprice", borderList.get(0).get("noriginalcurprice"));//单价
					genBVO[i].setAttributeValue("nmny", borderList.get(0).get("noriginalcurmny"));//金额
					genBVO[i].setAttributeValue("nplannedprice", borderList.get(0).get("noriginalcurprice"));//单价
					genBVO[i].setAttributeValue("nplannedmny", borderList.get(0).get("noriginalcurmny"));//金额
				}
				
				genBVO[i].setAttributeValue("nprice", Toolkits.getUFDouble(smp.get("bfpcdmx_dj")));//单价
				genBVO[i].setAttributeValue("nmny", Toolkits.getUFDouble(smp.get("bfpcdmx_je")));//金额
				genBVO[i].setAttributeValue("nplannedprice", Toolkits.getUFDouble(smp.get("bfpcdmx_dj")));//单价
				genBVO[i].setAttributeValue("nplannedmny", Toolkits.getUFDouble(smp.get("bfpcdmx_je")));//金额
				ArrayList<HashMap<String, String>> listhsljldw = new ArrayList<HashMap<String,String>>();
				listhsljldw = getHslJldw(listpkm.get(0).get("pk_invbasdoc"));
				if(listhsljldw.size()>0){
					genBVO[i].setAttributeValue("castunitid", listhsljldw.get(0).get("pk_measdoc1"));//辅计量单位
					genBVO[i].setAttributeValue("hsl", listhsljldw.get(0).get("mainmeasrate"));//换算率
				}    		
			}
		}else{
			throw new BusinessException("只有业务类别为单品外购、单品内购、多品外购，才可生成采购入库单");
		}
		GeneralBillVO billvo = new GeneralBillVO();
		SMGeneralBillVO billvoreturn = new SMGeneralBillVO();
		billvo.setParentVO(genHVO);
		billvo.setChildrenVO(genBVO);
		billvo.setIsCheckCredit(true);
		billvo.setIsCheckPeriod(true);
		billvo.setIsCheckAtp(true);
		billvo.setGetPlanPriceAtBs(false);
		billvo.setIsRwtPuUserConfirmFlag(false);
		billvo.setStatus(nc.vo.pub.VOStatus.NEW);
		billvo.m_sActionCode = "WRITE";//新增状态
		try {
			//获取返回值
			ArrayList al = (ArrayList) new PfUtilBO().processAction("WRITE", "45", genHVO.getDbilldate().toString(), null, billvo, null);
			billvoreturn = (SMGeneralBillVO) al.get(2);
		} catch (Exception e) {
//			showErrorMessage(""+e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		return billvoreturn.getPrimaryKey();
    }
    
    private UFDate getNCDateFormat(Object object) {
		if(object == null || object.toString().length() != 8){
			return null;
		}
		String oldstr = object.toString().substring(0, 4)+"-"+object.toString().substring(4, 6)+"-"+object.toString().substring(6, 8);
		return new UFDate(oldstr);
	}
 
}
