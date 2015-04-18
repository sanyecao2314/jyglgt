package nc.bs.jyglgt.freightdao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.naming.NamingException;

import nc.bs.jyglgt.dao.ServerDAO;
import nc.bs.pub.SystemException;
import nc.bs.pub.billcodemanage.BillcodeGenerater;
import nc.bs.scm.pub.smart.SmartDMO;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.vo.ia.bill.BillHeaderVO;
import nc.vo.ia.bill.BillItemVO;
import nc.vo.ia.bill.BillVO;
import nc.vo.jyglgt.arap.DJZBItemVO;
import nc.vo.jyglgt.arap.DJZBVO;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;


/**
 * @author guoyt
 * @功能： 运费结算后台处理类
 * 
 */
public class FreightDAO extends ServerDAO {
	public FreightDAO() {
		super();
	}

	PersistenceManager sManager = null;

	/**
	 * 更新皮带秤计量表数据
	 * */
	public void updateBFYFJS(ArrayList<String> jsbhList,int flag,String datasource) throws BusinessException {
		try {
			JdbcSession session = getsManager(datasource).getJdbcSession();
			String keys = getStrIN(jsbhList);
			String sql = " update bfyfjs set bfyfjs_c10='"+flag+"' where bfyfjs_jsbh in "+keys;
			session.executeUpdate(sql);
		} catch (DbException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

	}
	
	/**
	 * 向中间表插入数据
	 * */
	public void insertInteFreightByBfgl(String datasource) throws BusinessException {
		insertInteFreight(datasource);
	}

	/**
	 * 插入中间表数据
	 * @throws BusinessException 
	 * **/
    public void insertInteFreight(String datasource) throws BusinessException{
		try {
			JdbcSession session = getsManager(datasource).getJdbcSession();
			// 查询语句
			String sql = "select * from BFYFJS where BFYFJS_SHBZ=1 and isnull(BFYFJS_C10,0)=0";
			// 数据库访问操作
			ArrayList<HashMap<String, Object>> al = (ArrayList<HashMap<String, Object>>) session.executeQuery(sql,new MapListProcessor());
			// 遍历结果集
			if (al!=null&&al.size() > 0) {
				boolean success = true;
				ArrayList<String> jsbhList = new ArrayList<String>();
				for (int i = 0; i < al.size(); i++) {
					HashMap<String, Object> hm = (HashMap<String, Object>) al.get(i);
					String jsbh = Toolkits.getString(hm.get("bfyfjs_jsbh"));
					try{
						String bsql = " select * from bfyfjsmx inner join bfsfcl on bfyfjsmx_c6 = bfsfcl_djbh where bfyfjsmx_jsbh='" +jsbh+ "'";
						ArrayList<HashMap<String, Object>> bdList = (ArrayList<HashMap<String, Object>>) session.executeQuery(bsql,new MapListProcessor());
						if(bdList == null || bdList.size() == 0){
							throw new BusinessException("没有找到，没有在用户档案中查询到用户！"); 
						}
						
						insertIa(hm, bdList);

						invoiceCTYfd(hm, bdList);
						//传输数据到库存销售出库单
    					
    					//更新自动化系统标记
//    					updateBFPDC(Toolkits.getString(hm.get("bfyfjs_jsbh")),1,datasource);
						jsbhList.add(jsbh);
					}catch(Exception e){
						success = false;
						e.printStackTrace();
						//保存传输过程出现的错误历史记录
						insertExLog(e,Toolkits.getString(hm.get("bfyfjs_jsbh")),Toolkits.getString(hm.get("bfsfcl_c12")),datasource);
						continue;
					}

				}
				if(success){
					//将已经传输成功的在错误历史记录还有的数据清除掉
					delExLog(jsbhList, datasource);
					//一起回写大宗结算单，避免出现部分同步问题
					updateBFYFJS(jsbhList,1,datasource);
				}else{
					throw new BusinessException("运费结算同步异常，请查看日志！");
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
     * 保存传输过程出现的错误历史记录，保存到大宗数据库中
     * @throws BusinessException 
     * */
    private void insertExLog(Exception buse,String bfyfjs_jsbh,String pk_corp,String datasource) throws BusinessException{
    	
    	try {
			JdbcSession session = getsManager(datasource).getJdbcSession();
			String sql = "insert into freightlog (freightlog_djbh,freightlog_dwbm,freightlog_extlog) "
	    		+ " values('" +bfyfjs_jsbh+ "','" +pk_corp+ "','" +buse.getMessage()+ "')";
			session.executeUpdate(sql);
		} catch (DbException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
    }
    
    /**
     * 将已经传输成功的在错误历史记录还有的数据清除掉
     * @throws BusinessException 
     * */
    private void delExLog(ArrayList<String> jsbhList,String datasource) throws BusinessException{
    	String keys = getStrIN(jsbhList);
		try {
			JdbcSession session = getsManager(datasource).getJdbcSession();
    		String delsql=" delete from freightlog where freightlog_djbh in "+keys;
    		session.executeUpdate(delsql);
		} catch (DbException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

    }

	private PersistenceManager getsManager(String datasource) throws DbException {
		if (sManager == null)
			sManager = PersistenceManager.getInstance(datasource);
		return sManager;
	}

	/**
	 * 生成库存销售出库单
	 * @throws BusinessException 
	 * **/
    public String insertIa(HashMap<String, Object> hm, ArrayList<HashMap<String, Object>> bdList) throws BusinessException{
    	String pk_corp=Toolkits.getString(bdList.get(0).get("bfsfcl_c12"));/*公司*/
    	
    	String cuserid = getCuserid("0000");//默认值
		if("".equals(cuserid)){
			throw new BusinessException("根据司磅员传入的值，没有在用户档案中查询到用户！"); 
		}
		
		for(HashMap<String, Object> m : bdList){
			UFDate resdate = getNCDateFormat(m.get("bfsfcl_rq"));		//日期
			String djbh = Toolkits.getString(m.get("bfsfcl_djbh"));		//磅单编号
			BillVO billvo = new BillVO();
			BillHeaderVO billHvo = new BillHeaderVO();
			BillItemVO[] billBvos = new BillItemVO[1];

			/**
			 * 转换入库调整单表头信息
			 */
			//设置默认值
			billHvo.setBauditedflag(UFBoolean.FALSE);			//是否审核
			billHvo.setBdisableflag(UFBoolean.FALSE);			//是否作废
			//发票取N，结算单取Y
			billHvo.setBestimateflag(UFBoolean.TRUE);			//是否暂估
			billHvo.setBoutestimate(UFBoolean.FALSE);			//是否发出商品标识
			billHvo.setBwithdrawalflag(UFBoolean.FALSE);		//是否假退料
			billHvo.setCbilltypecode("I9");
			billHvo.setClastoperatorid(cuserid);					//最后修改人
			billHvo.setCoperatorid(cuserid);						//制单人
			billHvo.setDbilldate(resdate);							//单据日期
			billHvo.setFdispatchflag(0);							//收发标志
			billHvo.setIdeptattr(-1);
			billHvo.setPk_corp(pk_corp);							//公司主键
			billHvo.setTlastmaketime(new UFDateTime(System.currentTimeMillis()).toString());	//最后修改时间
			billHvo.setTmaketime(new UFDateTime(System.currentTimeMillis()).toString());		//制单时间
			billHvo.setVbillcode(getBillCode("I9",pk_corp));		//单据编号
//			billHvo.setCsourcemodulename("PO");
			billHvo.setCsourcemodulename(null);						//来源模块
			billHvo.setVnote("大宗运费结算生成");					//备注
			
			//设置暂估单转换值
			String pk_cubasdoc=getCustcode(Toolkits.getString(m.get("bfsfcl_shbh")));
			String custpk = getDocPkByCode(pk_corp,Toolkits.getString(m.get("bfsfcl_shbh")),2);
			if("".equals(custpk)){
				throw new BusinessException("根据磅单号【" +djbh+ "】的供应商编码传入的值，没有在客商档案中找到！");
			}
			billHvo.setCcustomvendorbasid(pk_cubasdoc);				//客商基本档案
			billHvo.setCcustomvendorid(custpk);						//供应商管理档案
			billHvo.setCdeptid(null);								//部门
			billHvo.setCemployeeid(null);							//人员标示
			String ckbh = Toolkits.getString(m.get("bfsfcl_ckbh"));
			if(ckbh.equals("")){
				throw new BusinessException("磅单号【" +djbh+ "】传输的仓库编码为空无法生成NC入库单！");
			}
			String cwarehouseid = getCwarehouseid(ckbh,pk_corp);
			if("".equals(cwarehouseid)){
				throw new BusinessException("根据磅单号【" +djbh+ "】传输的仓库编码没有找到仓库！");
			}
			billHvo.setCwarehouseid(cwarehouseid);					//仓库
			String pk_calbody = getPk_calbody(cwarehouseid);
			billHvo.setCstockrdcenterid(pk_calbody);				//库存组织（仓储）
			billHvo.setCrdcenterid(getCostCalBody(pk_calbody));		//库存组织(成本）
			billHvo.setCdispatchid("0001A7100000000007Y7");			//收发类别
			billHvo.setDr(0);
			billHvo.setStatus(2);
			
			billvo.setParentVO(billHvo);
			
			BillItemVO billBvo = new BillItemVO();
			//设置表体默认值
			billBvo.setBadjustedItemflag(UFBoolean.FALSE);		//是否调整了分录标志
			billBvo.setBauditbatchflag(UFBoolean.FALSE);		//是否批次核算		
			billBvo.setBlargessflag(UFBoolean.FALSE);			//是否赠品
			billBvo.setBretractflag(UFBoolean.FALSE);			//是否采购费用结算
			billBvo.setBrtvouchflag(UFBoolean.FALSE);			//是否已生成实时凭证
			billBvo.setBtransferincometax(UFBoolean.FALSE);		//是否进项税转出
			billBvo.setCbilltypecode("I9");						//单据类型
//			billBvo.setCfirstbilltypecode("GP01");				//源头单据类型
//			billBvo.setDbizdate(new UFDate(inFo[2]));
			billBvo.setFcalcbizflag(new Integer(0));			//计算关系的业务分类
			billBvo.setFdatagetmodelflag(new Integer(1));		//数据获得方式
			billBvo.setFolddatagetmodelflag(new Integer(1));	//成本计算前数据获得方式
			billBvo.setFoutadjustableflag(UFBoolean.FALSE);		//出库是否可调整标志
			
			billBvo.setIauditsequence(new Integer(-1));			//审核序号
			billBvo.setPk_corp(pk_corp);
			billBvo.setVbillcode(billHvo.getVbillcode());
			
			//设置结算单转换值
			billBvo.setVdef10(djbh);							//磅单号
			billBvo.setVdef11(Toolkits.getString(hm.get("bfyfjs_jsbh")));//运费结算单号
//			billBvo.setCfirstbillid(fHvo.getCorderid());
			ArrayList<HashMap<String, String>> listpkm = getPk_invmb(Toolkits.getString(m.get("bfsfcl_wlbh")), pk_corp);
			if(listpkm.size()==0){
				throw new BusinessException("存货编码"+Toolkits.getString(hm.get("bfsfcl_wlbh"))+"在NC中没有维护！");
			}
			billBvo.setCinvbasid(listpkm.get(0).get("pk_invbasdoc"));		//存货基本档案id
			billBvo.setCinventoryid(listpkm.get(0).get("pk_invmandoc"));	//存货管理档案id
			billBvo.setCrdcenterid(pk_calbody);								//库存组织标识
			billBvo.setFpricemodeflag(new Integer(getPriceFlag(listpkm.get(0).get("pk_invmandoc"),pk_calbody,pk_corp)));//计价方式  根据存货及仓储库存组织得到
			billBvo.setCvendorbasid(pk_cubasdoc);							//供应商基本档案id
			billBvo.setCvendorid(custpk);									//供应商管理档案id
//				billBvo.setNadjustnum(fBvos[i]);
			UFDouble wsjsmny = Toolkits.getUFDouble(m.get("bfyfjsmx_u3"));

			billBvo.setNmoney(wsjsmny);										//金额
			billBvo.setNsimulatemny(wsjsmny);								//模拟成本
//			billBvo.setNprice(fBvos[i].getJsmny().mod(bvo[i].getYfnum()));
//			billBvo.setCsourcebillid(fBvos[0].getFreighthid());
//			billBvo.setCsourcebillitemid(fBvos[0].getFreightbid());
//			billBvo.setCsourcebilltypecode(fHvo.getBilltype());
			
			//增加
			billBvo.setNplanedprice(billBvo.getNprice());
			billBvo.setNplanedmny(billBvo.getNmoney());
			billBvo.setIrownumber(new Integer(10));							//行号
			billBvo.setDr(0);
			billBvo.setStatus(2);
			billBvos[0] = billBvo;
			billvo.setChildrenVO(billBvos);
			//生成入库调整单
			try {
				insertBill(billvo);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BusinessException("磅单号【" +djbh+ "】生成入库调整单异常 " + e.getMessage());
			}
		}
		return pk_corp;
    }

	public void  invoiceCTYfd(HashMap<String, Object> hm, ArrayList<HashMap<String, Object>> bdList)throws BusinessException{
		String pk_corp=Toolkits.getString(bdList.get(0).get("bfsfcl_c12"));/*公司*/
    	
    	String cuserid = getCuserid("0000");//默认值
		if("".equals(cuserid)){
			throw new BusinessException("根据司磅员传入的值，没有在用户档案中查询到用户！"); 
		}
		
		for(HashMap<String, Object> m : bdList){
			UFDate resdate = getNCDateFormat(m.get("bfsfcl_rq"));		//日期
			String djbh = Toolkits.getString(m.get("bfsfcl_djbh"));		//磅单编号
			UFDouble mny = Toolkits.getUFDouble(m.get("bfyfjsmx_je"));
			//设置暂估单转换值
			String pk_cubasdoc=getCustcode(Toolkits.getString(m.get("bfsfcl_shbh")));
			String custpk = getDocPkByCode(pk_corp,Toolkits.getString(m.get("bfsfcl_shbh")),2);
			if("".equals(custpk)){
				throw new BusinessException("根据磅单号【" +djbh+ "】的供应商编码传入的值，没有在客商档案中找到！");
			}
			if(mny.doubleValue() <=0 ){
				throw new BusinessException("磅单号【" +djbh+ "】的运费金额不能为空！");
			}
			//单据主表。
		    DJZBVO  djzbVO=new DJZBVO();
		    djzbVO.setDwbm(pk_corp); //单位编号
	        djzbVO.setDjbh(getBillCode("D1",pk_corp)); //单据编号        
	        djzbVO.setDjrq(resdate);//单据日期
	        
	        djzbVO.setBbje(mny);//本币金额
	    	djzbVO.setDjlxbm("D1");
	    	djzbVO.setYwbm(getDjlx(pk_corp,"D1")); //单据类型    
	        djzbVO.setDjdl("yf");//单据大类
	        Calendar cal = Calendar.getInstance();
	        String year =String.valueOf(cal.get(Calendar.YEAR));
	        String month = String.valueOf(cal.get(Calendar.MONTH )+1);
	        if(month.length()==1){month="0"+month;}
	        djzbVO.setDjkjnd(year); //单据会计年度 
	        djzbVO.setDjkjqj(month); // 单据会计期间
	        djzbVO.setQcbz(new UFBoolean(false)); //期初标志  
	        djzbVO.setDjzt(new Integer(1));
	        djzbVO.setFbje(new UFDouble(0));//辅币金额
	        djzbVO.setScomment("大宗运费结算生成"); //备注 
	        djzbVO.setZzzt(new Integer(0));
	        djzbVO.setYbje(mny);//原币金额
	        djzbVO.setSxbz(new Integer(0));//生效标记
	        djzbVO.setLybz(new Integer(0));//来源标记（本系统）
	        djzbVO.setEffectdate(resdate);//起效日期
	        djzbVO.setHzbz("-1");//坏账标记
	        djzbVO.setIsjszxzf(new UFBoolean(false));//是否结算中心支付
	        djzbVO.setIsnetready(new UFBoolean(false));//是否已经录入
	        djzbVO.setIsreded(new UFBoolean(false));//是否红冲
	        djzbVO.setLrr(cuserid);
	        djzbVO.setPrepay(new UFBoolean(false));///预收付款标志   
	        djzbVO.setSxbz(new Integer(0));//生效标记
	        djzbVO.setZgyf(new Integer(0));//暂估应付标志
	        djzbVO.setZdr(cuserid);// 制单人 
	        djzbVO.setZdrq(resdate);//制单日期  
//	        djzbVO.setPj_jsfs(hvo.getPk_balatype()); //结算方式
	        djzbVO.setPzglh(new Integer(1));//系统标记 1、应付
	        djzbVO.setDr(new Integer(0));// 删除标记
	        String  pk_jxz =insertVObackPK(djzbVO);
	         if(Toolkits.isEmpty(pk_jxz)||pk_jxz==null){
	        	 throw new BusinessException("转入应付单失败！");
	         }
	         //单据子表。
	         DJZBItemVO item=new DJZBItemVO(); 
	    	 item=new DJZBItemVO();
	         item.setVouchid(pk_jxz);    //主表id   
		     item.setDeptid(null);// 部门编码
		     item.setYwybm(null) ;//业务员编码  
		     item.setBilldate(resdate);
		     item.setDjbh(djzbVO.getDjbh());//单据编号
		     item.setBzbm("00010000000000000001");//币种编码--默认人民币
		     item.setDjdl("yf");	   
		     item.setDjxtflag(new Integer(0));//单据协同标志
		     item.setDwbm(pk_corp);
		     item.setFbhl(new UFDouble(0));//辅币汇率
		     item.setFbye(new UFDouble(0));//辅币余额
		     item.setFlbh(new Integer(0));//单据分录编号
		     item.setFx(new Integer(-1));//方向
		     item.setHbbm(pk_cubasdoc);//伙伴编码
		     item.setIssfkxychanged(new UFBoolean(false));//收款协议是否发生变化
		     item.setIsverifyfinished(new UFBoolean(false));//是否核销完成
		     item.setJfbbsj(new UFDouble(0));//借方本币税金
		     item.setJffbsj(new UFDouble(0));
		     item.setJfybsj(new UFDouble(0));
		     item.setJfbbje(new UFDouble(0));
		     item.setJfybje(new UFDouble(0));
		     item.setJfybwsje(new UFDouble(0));
		     item.setJffbje(new UFDouble(0));
		     item.setJfjs(new UFDouble(0));
		     item.setJfzkfbje(new UFDouble(0));
		     item.setJfzkybje(new UFDouble(0));
		     item.setKslb(1);//扣税类别，应税外加
		     item.setOld_sys_flag(new UFBoolean(false));//无用1
		     item.setPausetransact(new UFBoolean(false));//挂起标记
		     item.setPjdirection("none");//票据方向
		     item.setQxrq(resdate);
		     item.setSfbz("3"); ///收付标志   
		     item.setShlye(new UFDouble(0));//数量余额
		     item.setWbfbbje(new UFDouble(0));//借方本币无税金额
		     item.setWbffbje(new UFDouble(0));
		     item.setWbfbbje(new UFDouble(0));
		     item.setWbfybje(new UFDouble(0));
		     item.setWldx(new Integer(1));//往来对象标记
		     item.setXgbh(new Integer(-1));//转移标记
		     item.setYsbbye(new UFDouble(0));//无用7
		     item.setYsfbye(new UFDouble(0));//无用6
		     item.setYsybye(new UFDouble(0));//无用5
		     item.setYwybm(null);//业务员
		     //转换金额及单价
		     UFDouble sl = Toolkits.getUFDouble(m.get("bfyfjsmx_sl"));
		     UFDouble ntaxmny = mny.mod(sl).div(100);//税金
		     UFDouble nsummny = mny;//本币价税合计
		     UFDouble nmoney = Toolkits.getUFDouble(m.get("bfyfjsmx_u3"));//本币无税金额
		     UFDouble noriginaltaxmny = ntaxmny;//原币税额
		     UFDouble noriginalsummny = mny;//原币价税合计
		     UFDouble noriginalcurmny = nmoney;//原币无税金额
		     UFDouble nassisttaxmny = new UFDouble(0);//辅币税金
		     UFDouble nassistsummny = new UFDouble(0);//辅币价税合计
		     UFDouble ninvoicenum = Toolkits.getUFDouble(m.get("bfyfjsmx_jz"));//数量
		     UFDouble noriginalcurprice = new UFDouble(0);//无税单价
		     UFDouble noriginalprice = new UFDouble(0);//含税单价

	         noriginalcurprice = noriginalcurmny.div(ninvoicenum);
	         noriginalprice = noriginalsummny.div(ninvoicenum);
	         
		     item.setDfbbsj(ntaxmny);//贷方本币税金
		     item.setDfbbje(nsummny);//贷方本币金额
	         item.setDfbbwsje(nmoney);//贷方本币无税金额
		     item.setDfybsj(noriginaltaxmny);//贷方原币税金
	         item.setDfybje(noriginalsummny);//贷方原币金额
		     item.setYbye(noriginalsummny);//原币余额
		     item.setBbye(noriginalsummny);//本次收款金额==原币金额
	         item.setDfybwsje(noriginalcurmny);//贷方原币无税金额
		     item.setDffbsj(nassisttaxmny);//贷方辅币税金
		     item.setDffbje(nassistsummny);//贷方辅币金额
	         item.setDfshl(ninvoicenum);//贷方数量  
		     item.setDfjs(new UFDouble(0));//贷方件数
	         item.setBjdwhsdj(new UFDouble(0));//报价单含税单价
	         item.setBjdwsl(new UFDouble(0));//报价单数量
	         item.setBjdwwsdj(new UFDouble(0));//报价单位无税单价
	         item.setDj(noriginalcurprice);//原币无税单价
		     item.setHsdj(noriginalprice);//报价单含税单价
	         item.setBbhl(new UFDouble(1));//本币汇率
		     item.setSl(sl);//税率
	         item.setDr(new Integer(0));
	         ArrayList<HashMap<String, String>> listpkm = getPk_invmb(Toolkits.getString(m.get("bfsfcl_wlbh")), pk_corp);
			 if(listpkm.size()==0){
				throw new BusinessException("存货编码"+Toolkits.getString(hm.get("bfsfcl_wlbh"))+"在NC中没有维护！");
			 }
	         item.setChbm_cl(listpkm.get(0).get("pk_invmandoc"));//存货管理档案主键
	         item.setCinventoryid(listpkm.get(0).get("pk_invbasdoc"));//存货基本档案主键
	         item.setFph(null);//发票号
	         item.setChmc(listpkm.get(0).get("invname"));//存货名称
//	             item.setPj_jsfs(bvo[i].getPk_balatype());//结算方式     
//		     item.setPh(bvos[0].getCsourcebilltype());//源头单据类型
//		     item.setXyzh(bvos[0].getCsourcebillid());//源头单据id
//		     item.setCksqsh(bvos[0].getCsourcebillrowid());//源头单据行id
//		     item.setJsfsbm(hvo.getBilltype());//来源单据类型
//		     item.setDdlx(bvos[0].getCinvoiceid());//来源单据id
//		     item.setDdhh(bvos[0].getCinvoice_bid());//来源单据行id
		     item.setDjlxbm("D1");
		     item.setZyx4("0001A7100000000007Y7");//收发类别
		     insertVObackPK(item);
		}
	    
	}
	
	private void insertBill(BillVO billvo) throws BusinessException{
		//入库调整单生成
		SmartDMO sdmo = null;
		try {
			sdmo = new SmartDMO();
			BillHeaderVO hvo = (BillHeaderVO)billvo.getParentVO();
			BillItemVO[] bvos = (BillItemVO[])billvo.getChildrenVO();
			String[] keys=sdmo.getOIDs(hvo.getPk_corp(),1);
			hvo.setPrimaryKey(keys[0]);
			sdmo.maintain(hvo);
			for(int i=0; i<bvos.length; i++){
				String[] bks = sdmo.getOIDs(bvos[i].getPk_corp(),1);
				bvos[i].setCbill_bid(bks[0]);
				bvos[i].setCbillid(keys[0]);
			}
			sdmo.maintain(bvos);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(" 生成入库调整单异常 " + e.getMessage());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(" 生成入库调整单异常 " + e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException(" 生成入库调整单异常 " + e.getMessage());
		}
	}

	public static String getBillCode(String billtypecode, String pk_corp){
		String customBillCode=null;
		String billTypecode=billtypecode;
		//生成一个BillCodeObjValueVO
		nc.vo.pub.billcodemanage.BillCodeObjValueVO 
		vo=new nc.vo.pub.billcodemanage.BillCodeObjValueVO();
		String billcode=null;
		try {
			billcode=(new BillcodeGenerater()).getBillCode(billTypecode,pk_corp,customBillCode,vo);
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return billcode;
	}
	  
    private UFDate getNCDateFormat(Object object) {
		if(object == null || object.toString().length() != 8){
			return null;
		}
		String oldstr = object.toString().substring(0, 4)+"-"+object.toString().substring(4, 6)+"-"+object.toString().substring(6, 8);
		return new UFDate(oldstr);
	}
	
	private String getCostCalBody(String pk_storcalbody) throws BusinessException{
		if(pk_storcalbody==null || pk_storcalbody.length()==0)
			throw new BusinessException(" 生成入库调整单异常: 库存组织不能为空！");
		String sql = "select pk_costcalbody from bd_storvscost where dr=0 and pk_storcalbody='" +pk_storcalbody+ "'";
		String costCalBody = queryStrBySql(sql);
		if(costCalBody==null || costCalBody.length()==0)
			return pk_storcalbody;
		return costCalBody;
	}

	public static String getStrIN(ArrayList<String> idlist) {
		if (idlist != null && idlist.size() > 0 && idlist.get(0) != null) {
			StringBuilder s = new StringBuilder("(");
			for (int i = 0; i < idlist.size(); i++) {
				s.append("'");
				s.append(idlist.get(i));
				s.append("'");
				if (i != idlist.size() - 1) {
					s.append(",");
				} else {
					s.append(")");
				}
			}
			return s.toString();
		}
		return null;
	}
	
   /**
    * 获取单据类型主键
    * @param  String pk_corp
    * @param  String djlxbm
    * */
	private String getDjlx(String pk_corp,String djlxbm)throws BusinessException{		  
		 String sql=" select djlxoid from arap_djlx where djdl='yf' and djlxbm='"+djlxbm+"' and dwbm='"+pk_corp+"'";
		 return queryStrBySql(sql);
	}
	
	
	//计价方式取得
	private String getPriceFlag(String pk_invmandoc, String pk_calbody, String pk_corp) throws BusinessException{
		if(pk_calbody==null || pk_calbody.length()==0)
			throw new BusinessException(" 生成入库调整单异常: 库存组织不能为空！");
		String sql = "select pricemethod from bd_produce where pk_invmandoc='" +pk_invmandoc+ "' and pk_corp='" +pk_corp+ "' and pk_calbody='" +pk_calbody+ "' and dr=0";
		String pricemethod = queryStrBySql(sql);
		if(pricemethod==null || pricemethod.length()==0)
			throw new BusinessException(" 生成入库调整单异常: 没有对应的计价方式！");
		return pricemethod;
	}
		
}
