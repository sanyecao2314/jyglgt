package nc.bs.jyglgt.saleoutdao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nc.bs.jyglgt.dao.ServerDAO;
import nc.bs.logging.Logger;
import nc.bs.pub.pf.PfUtilBO;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.jdbc.framework.processor.MapProcessor;
import nc.vo.ic.pub.bill.GeneralBillHeaderVO;
import nc.vo.ic.pub.bill.GeneralBillItemVO;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.ic.pub.smallbill.SMGeneralBillVO;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.scm.ic.bill.FreeVO;
import nc.vo.so.so001.SaleorderBVO;


/**
 * @author 施鹏
 * @功能： 皮带秤计量(销售出库)后台处理类
 * 
 */
public class SaleoutDAO extends ServerDAO {
	public SaleoutDAO() {
		super();
	}

	PersistenceManager sManager = null;

	/**
	 * 更新皮带秤计量表数据
	 * */
	public void updateBFPDC(String key,int flag,String datasource) throws BusinessException {
		try {
			JdbcSession session = getsManager(datasource).getJdbcSession();
			String sql = " update BFFHCL set BFFHCL_CKBZ='"+flag+"' where BFFHCL_CKBZ='0' and BFFHCL_DJBH='"+key+"'";
			session.executeUpdate(sql);
		} catch (DbException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

	}
	
	/**
	 * 向中间表插入数据
	 * */
	public void insertInteSaleoutByBfgl(String datasource) throws BusinessException {
		insertInteSaleout(datasource);
	}

	/**
	 * 插入中间表数据
	 * @throws BusinessException 
	 * **/
    public void insertInteSaleout(String datasource) throws BusinessException{
		try {
			JdbcSession session = getsManager(datasource).getJdbcSession();
			// 查询语句
			String sql = "select  * from BFFHCL  where bffhcl_djlx='1' and bffhcl_ckbz !='1' ";
			// 数据库访问操作
			ArrayList al = (ArrayList) session.executeQuery(sql,new MapListProcessor());
			// 遍历结果集
			if (al!=null&&al.size() > 0) {
				for (int i = 0; i < al.size(); i++) {
					HashMap hm = (HashMap) al.get(i);
					String cgeneralhid = null;
					try{
						//判断传输值是否已经存在，如果有则跳过
						String intehsql = "select cgeneralhid from ic_general_h where cbilltypecode ='4C' and vuserdef20 ='"+Toolkits.getUFDouble(hm.get("bffhcl_djbh"))+"' and nvl(dr,0)=0 ";
						String pk_gen=queryStrBySql(intehsql);
						if(pk_gen!=null&&!pk_gen.equals("")){
							continue;
						}
						//传输数据到库存销售出库单
    					cgeneralhid=insertGen(hm,session); 
    					//更新自动化系统标记
    					updateBFPDC(Toolkits.getString(hm.get("bffhcl_djbh")),1,datasource);
					}catch(Exception e){
						e.printStackTrace();
						//保存传输过程出现的错误历史记录
						insertExLog(e,Toolkits.getString(hm.get("bffhcl_djbh")),Toolkits.getString(hm.get("bffhcl_c6")));					
						continue;
					}
					//将已经传输成功的在错误历史记录还有的数据清除掉
					delExLog(Toolkits.getString(hm.get("bffhcl_djbh")));
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
    	nc.vo.jyglgt.bfsfcllog.BfsfclLogVO logvo=new nc.vo.jyglgt.bfsfcllog.BfsfclLogVO();
		logvo.setDr(new Integer(0));
		logvo.setPk_corp(pk_corp);
		logvo.setBfsfcl_djbh(bfsfcl_djbh);
		logvo.setExlog(e.getMessage());
		logvo.setBilltype("4C");
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

	
	private UFDate getNCDateFormat(Object object) {
		if(object == null || object.toString().length() != 8){
			return null;
		}
		String oldstr = object.toString().substring(0, 4)+"-"+object.toString().substring(4, 6)+"-"+object.toString().substring(6, 8);
		return new UFDate(oldstr);
	}
	
	/**
	 * 生成库存销售出库单
	 * @param session 
	 * @throws BusinessException 
	 * @throws DbException 
	 * **/
    public String insertGen(HashMap hm, JdbcSession session) throws BusinessException, DbException{
    	String djbh = Toolkits.getString(hm.get("bffhcl_djbh"));
    	String pk_corp=Toolkits.getString(hm.get("bffhcl_c6"));/*移出部门*/
		GeneralBillHeaderVO genHVO = new GeneralBillHeaderVO();
		String cuserid = getCuserid("0000");//默认值
		if("".equals(cuserid)){
			throw new BusinessException("根据司磅员传入的值，没有在用户档案中查询到用户！"); 
		}
		genHVO.setAttributeValue("coperatorid", cuserid);//制单人
		genHVO.setAttributeValue("coperatoridnow", cuserid);//制单人
		UFDate resdate = getNCDateFormat(hm.get("bffhcl_rq"));//转成NC的格式 8位外系统'20140122' --->'2014-01-22'
		genHVO.setAttributeValue("dbilldate", resdate);//单据日期
		
//		String cuserid = getCuserid(Toolkits.getString(hm.get("bffhcl_shy")));	//发货员
//		if("".equals(cuserid)){
//			throw new BusinessException("根据发货员传入的值，没有在用户档案中查询到用户！"); 
//		}
//		genHVO.setAttributeValue("coperatorid", cuserid);//制单人
		String cwarehouseid = getCwarehouseid(Toolkits.getString(hm.get("bffhcl_ckbh")),pk_corp);
		if("".equals(cwarehouseid)){
			throw new BusinessException("根据传输的仓库编码没有找到仓库！");
		}
		genHVO.setAttributeValue("cwarehouseid", cwarehouseid);//仓库	
		
		//销售出库,找对应的客商,但中间表没有客商信息
		String custpk = getDocPkByCode(pk_corp,Toolkits.getString(hm.get("bffhcl_shbh")),2); /*客商编号*/
		if("".equals(custpk)){
			throw new BusinessException("根据客商编码传入的值，没有在客商档案中找到！");
		}
		genHVO.setAttributeValue("ccustomerid",custpk);////客户ID
		
		
		if("".equals(getPk_calbody(cwarehouseid))){
			throw new BusinessException("库存组织为空！");
		}
		genHVO.setAttributeValue("pk_calbody",getPk_calbody(cwarehouseid));//库存组织PK
		if ("".equals(getPk_rdcl("销售出库"))) {
			throw new BusinessException("没有找到销售出库对应的收发类别！");
		}
		genHVO.setAttributeValue("cdispatcherid",getPk_rdcl("销售出库"));//收发类别PK
//		String pk_cubasdoc=getCustcode(Toolkits.getString(hm.get("bfsfcl_shbh"))); /*供应商编号*/
//		genHVO.setAttributeValue("pk_cubasdoc",pk_cubasdoc);//供应商基本档案ID
		genHVO.setAttributeValue("vuserdef19",Toolkits.getString(hm.get("bffhcl_clbh")));//车号/*车辆编号*/
		genHVO.setAttributeValue("vuserdef20",Toolkits.getString(hm.get("bffhcl_djbh")));//出库磅单号
		genHVO.setAttributeValue("vuserdef1",Toolkits.getString(hm.get("bffhcl_pczh")));//派车单号 add by gdt
		String ywlb = Toolkits.getString(hm.get("bffhcl_ywlb"));//自动化的业务类别 add by gdt 单品外销、单品内销、多品单户外销、多品多户外销
		
//		if (StringUtils.isNotEmpty(String.valueOf(hm.get("bffhcl_fhbh")))) {
//			getPk_trancust(hm.get("bffhcl_fhbh"));
//			genHVO.setAttributeValue("ctrancustid",Toolkits.getString(hm.get("bffhcl_clbh")));//承运商
//		}
		genHVO.setAttributeValue("vuserdef19",Toolkits.getString(hm.get("bffhcl_clbh")));//车号/*车辆编号*/
		 
//		try {
//			//生成单据编号
//			String billNo = BillcodeRuleBO_Client.getBillCode("4C", pk_corp,null, null);
//			if(billNo!=null)
//				genHVO.setVbillcode(billNo);
//			else
//				genHVO.setVbillcode("4C000001");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		genHVO.setAttributeValue("fbillflag", nc.vo.pub.VOStatus.NEW);//Toolkits.getInteger(saleHVO.getIverifystate()).toString());//单据状态
		genHVO.setAttributeValue("cbilltypecode", "4C");//
		genHVO.setAttributeValue("pk_corp",pk_corp);//pk_corp
		genHVO.setAttributeValue("dr", 0);
		
		//子表
		GeneralBillItemVO[] genBVO = null;
		if("单品外销".equals(ywlb)||"单品内销".equals(ywlb)){//单品外销、单品内销（没有辅数量）
			genBVO = new GeneralBillItemVO[1];
			for (int i = 0; i < genBVO.length; i++) {
	    		genBVO[i]  = new GeneralBillItemVO();
	    		genBVO[i].setAttributeValue("dr", 0);
	    		genBVO[i].setAttributeValue("pk_corp", pk_corp);
	    		genBVO[i].setNegallowed(UFBoolean.TRUE);//允许负结存
	    		genBVO[i].setDbizdate( genHVO.getDbilldate());   		
	    		ArrayList<HashMap<String, String>> listpkm = getPk_invmb(Toolkits.getString(hm.get("bffhcl_wlbh")), pk_corp);
	    		if(listpkm.size()==0){
	    			throw new BusinessException("存货编码"+Toolkits.getString(hm.get("bffhcl_wlbh"))+"在NC中没有维护！");
	    		}
	    		genBVO[i].setAttributeValue("crowno", 10*(i+1));//行号
	    		genBVO[i].setAttributeValue("cinventoryid", listpkm.get(0).get("pk_invmandoc"));//存货管理档案
	    		genBVO[i].setAttributeValue("cinvbasid", listpkm.get(0).get("pk_invbasdoc"));//存货基本档案
//	    		genBVO[i].setAttributeValue("dvalidate", Toolkits.getUFDate(salebvo[i].getDvdate()));//失效日期
//	    		genBVO[i].setAttributeValue("scrq", Toolkits.getUFDate(saleHVO.getDveridate().substring(0, 10)));//生产日期
	    		
	    		if("单品外销".equals(ywlb)||"单品内销".equals(ywlb)){//单品外销、单品内销（没有辅数量）
	    			genBVO[i].setAttributeValue("noutnum", Toolkits.getUFDouble(hm.get("bffhcl_jz")));//实出数量
	    			genBVO[i].setAttributeValue("nshouldoutnum", Toolkits.getUFDouble(hm.get("bfpcdmx_sfsl")));//应出数量
	    		}

//	    		String bfpcd_tdls = Toolkits.getString(hm.get("bfpcd_tdls"));//销售订单主表id
				String bfpcd_tdfl = Toolkits.getString(hm.get("bfpcd_tdfl"));//销售订单子表id
				String saleordersql = " select b.* from so_sale h inner join so_saleorder_b b on h.csaleid=b.csaleid where nvl(h.dr,0)=0 and nvl(b.dr,0)=0 and b.corder_bid='"+bfpcd_tdfl+"'  ";
				SaleorderBVO salebvo = (SaleorderBVO) queryBySql(saleordersql, null, new BeanProcessor(SaleorderBVO.class));
				if(salebvo == null){
					throw new BusinessException("查找不到对应的销售订单表体行");
				}
				genBVO[i].setCsourcebillhid(salebvo.getCsaleid());//来源单据表头序列号
				genBVO[i].setAttributeValue("csourcebillbid", bfpcd_tdfl); //来源单据表体序列号
				genBVO[i].setAttributeValue("csourcetype", "30"); //来源单据类型
				genBVO[i].setCfirstbillbid(bfpcd_tdfl);
				genBVO[i].setCfirstbillhid(salebvo.getCsaleid());
				genBVO[i].setCfirsttype("30");
	    		
				//设置自由项
				String freesql = " select vfree1,vfree2,vfree3,vfree4,vfree5 from so_saleexecute where csale_bid='"+bfpcd_tdfl+"' and nvl(dr,0)=0 ";
				Map freemap =  (Map) queryBySql(freesql, null, new MapProcessor());
				if(freemap != null && freemap.size() != 0){
					FreeVO freevo = new FreeVO();
					freevo.setVfree1((String) freemap.get("vfree1"));
					freevo.setVfreeid1(listpkm.get(0).get("free1"));
					
					freevo.setVfree2((String) freemap.get("vfree2"));
					freevo.setVfreeid2(listpkm.get(0).get("free2"));
					
					freevo.setVfree3((String) freemap.get("vfree3"));
					freevo.setVfreeid3(listpkm.get(0).get("free3"));
					
					freevo.setVfree4((String) freemap.get("vfree4"));
					freevo.setVfreeid4(listpkm.get(0).get("free4"));
					
					freevo.setVfree5((String) freemap.get("vfree5"));
					freevo.setVfreeid5(listpkm.get(0).get("free5"));
					genBVO[i].setFreeItemVO(freevo);
					genBVO[i].setIsFreeItemMgt(1);//启用自由项管理
					genBVO[i].setIsAstUOMmgt(1);//启用主辅计量管理
				}
				String wholemanaflag = listpkm.get(0).get("wholemanaflag");
				if("Y".equals(wholemanaflag)){//批次号
					genBVO[i].setVbatchcode(salebvo.getCbatchid());
					genBVO[i].setIsLotMgt(1);
				}
	    		//出库没有单价和金额
	    		genBVO[i].setAttributeValue("nprice", Toolkits.getUFDouble(hm.get("bffhcl_dj")));//单价
	    		genBVO[i].setAttributeValue("nmny", Toolkits.getUFDouble(hm.get("bffhcl_je")));//金额
	    		genBVO[i].setAttributeValue("nplannedprice", Toolkits.getUFDouble(hm.get("bffhcl_dj")));//单价
	    		genBVO[i].setAttributeValue("nplannedmny", Toolkits.getUFDouble(hm.get("bffhcl_je")));//金额
	    		
	    		ArrayList<HashMap<String, String>> listhsljldw = new ArrayList<HashMap<String,String>>();
	    		listhsljldw = getHslJldw(listpkm.get(0).get("pk_invbasdoc"));
	    		if(listhsljldw.size()>0){
	    			genBVO[i].setAttributeValue("castunitid", listhsljldw.get(0).get("pk_measdoc1"));//辅计量单位
	    			genBVO[i].setAttributeValue("hsl", listhsljldw.get(0).get("mainmeasrate"));//换算率
	    		}    		
			}
		}else if("多品单户外销".equals(ywlb)||"多品多户外销".equals(ywlb)){//多品单户外销、多品多户外销（有辅数量）
			String bffhcl_djbh = Toolkits.getString(hm.get("bffhcl_djbh"));//发货登记表体 关联字段1
			String bffhcl_c6 = Toolkits.getString(hm.get("bffhcl_c6"));//发货登记表体 关联字段2
			String sql = " select * from BFPCDMX where BFPCDMX_C3='"+bffhcl_djbh+"' and BFPCDMX_C4='"+bffhcl_c6+"'";//
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
	    		genBVO[i].setNegallowed(UFBoolean.TRUE);//允许负结存
	    		genBVO[i].setDbizdate( genHVO.getDbilldate());   		
	    		ArrayList<HashMap<String, String>> listpkm = getPk_invmb(invcode, pk_corp);
	    		if(listpkm.size()==0){
	    			throw new BusinessException("存货编码"+invcode+"在NC中没有维护！");
	    		}
	    		genBVO[i].setAttributeValue("crowno", 10*(i+1));//行号
	    		genBVO[i].setAttributeValue("cinventoryid", listpkm.get(0).get("pk_invmandoc"));//存货管理档案
	    		genBVO[i].setAttributeValue("cinvbasid", listpkm.get(0).get("pk_invbasdoc"));//存货基本档案
//	    		genBVO[i].setAttributeValue("dvalidate", Toolkits.getUFDate(salebvo[i].getDvdate()));//失效日期
//	    		genBVO[i].setAttributeValue("scrq", Toolkits.getUFDate(saleHVO.getDveridate().substring(0, 10)));//生产日期
	    		
	    		if("多品单户外销".equals(ywlb)||"多品多户外销".equals(ywlb)){//多品单户外销、多品多户外销（有辅数量）
	    			genBVO[i].setAttributeValue("noutnum", Toolkits.getUFDouble(smp.get("bfpcdmx_jz")));//实出数量
	    			genBVO[i].setNoutassistnum(Toolkits.getUFDouble(smp.get("bfpcdmx_sfjs")));//实出辅数量
	    			genBVO[i].setAttributeValue("nshouldoutnum", Toolkits.getUFDouble(smp.get("bfpcdmx_sfsl")));//应出数量
	    			genBVO[i].setNshouldoutassistnum(Toolkits.getUFDouble(smp.get("bfpcdmx_sfjs")));//应出辅数量
	    		}else{
	    			throw new BusinessException("只有业务类别为单品外销、单品内销、多品单户外销,多品多户外销才可生成销售出库单");
	    		}
	    		
	    		String bfpcdmx_tdfl = Toolkits.getString(smp.get("bfpcdmx_tdfl"));//销售订单子表id
				String saleordersql = " select b.* from so_sale h inner join so_saleorder_b b on h.csaleid=b.csaleid where nvl(h.dr,0)=0 and nvl(b.dr,0)=0 and b.corder_bid='"+bfpcdmx_tdfl+"'  ";
				SaleorderBVO salebvo = (SaleorderBVO) queryBySql(saleordersql, null, new BeanProcessor(SaleorderBVO.class));
				if(salebvo == null){
					throw new BusinessException("查找不到对应的销售订单表体行");
				}
				genBVO[i].setCsourcebillhid(salebvo.getCsaleid());//来源单据表头序列号
				genBVO[i].setAttributeValue("csourcebillbid", bfpcdmx_tdfl); //来源单据表体序列号
				genBVO[i].setAttributeValue("csourcetype", "30"); //来源单据类型
				genBVO[i].setCfirstbillbid(bfpcdmx_tdfl);
				genBVO[i].setCfirstbillhid(salebvo.getCsaleid());
				genBVO[i].setCfirsttype("30");
	    		
				//设置自由项
				String freesql = " select vfree1,vfree2,vfree3,vfree4,vfree5 from so_saleexecute where csale_bid='"+bfpcdmx_tdfl+"' and nvl(dr,0)=0 ";
				Map freemap =  (Map) queryBySql(freesql, null, new MapProcessor());
				if(freemap != null && freemap.size() != 0){
					FreeVO freevo = new FreeVO();
					freevo.setVfree1((String) freemap.get("vfree1"));
					freevo.setVfreeid1(listpkm.get(0).get("free1"));
					
					freevo.setVfree2((String) freemap.get("vfree2"));
					freevo.setVfreeid2(listpkm.get(0).get("free2"));
					
					freevo.setVfree3((String) freemap.get("vfree3"));
					freevo.setVfreeid3(listpkm.get(0).get("free3"));
					
					freevo.setVfree4((String) freemap.get("vfree4"));
					freevo.setVfreeid4(listpkm.get(0).get("free4"));
					
					freevo.setVfree5((String) freemap.get("vfree5"));
					freevo.setVfreeid5(listpkm.get(0).get("free5"));
					genBVO[i].setFreeItemVO(freevo);
					genBVO[i].setIsFreeItemMgt(1);//启用自由项管理
					genBVO[i].setIsAstUOMmgt(1);//启用主辅计量管理
				}
				
				String wholemanaflag = listpkm.get(0).get("wholemanaflag");
				if("Y".equals(wholemanaflag)){//批次号
					genBVO[i].setVbatchcode(salebvo.getCbatchid());
					genBVO[i].setIsLotMgt(1);
				}
	    		//出库没有单价和金额
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
			throw new BusinessException("只有业务类别为单品外销、单品内销、多品单户外销,多品多户外销才可生成销售出库单");
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
			ArrayList al = (ArrayList) new PfUtilBO().processAction("WRITE", "4C", genHVO.getDbilldate().toString(), null, billvo, null);
			billvoreturn = (SMGeneralBillVO) al.get(2);
		} catch (Exception e) {
//			showErrorMessage(""+e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		return billvoreturn.getPrimaryKey();
    }

}
