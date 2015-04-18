package nc.bs.jyglgt.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nc.bs.jyglgt.dao.CreateBillDAO;
import nc.bs.jyglgt.dao.ServerDAO;
import nc.bs.jyglgt.freightdao.FreightDAO;
import nc.bs.jyglgt.saleoutdao.SaleoutDAO;
import nc.bs.jyglgt.transoutdao.TransoutDAO;
import nc.itf.jyglgt.pub.IJyglgtItf;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.vo.gt.gs.gs11.ExAggSellPaymentJSVO;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.jyglgt.pub.BillMakeVO;
import nc.vo.jyglgt.pub.GDTBillcodeRuleVO;
import nc.vo.jyglgt.pub.MyBillTempBVO;
import nc.vo.jyglgt.pub.MyBillTempTVO;
import nc.vo.jyglgt.pub.MyBillTempVO;
import nc.vo.jyglgt.pub.Toolkits.IDatasourceConst;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.billtype.BilltypeVO;
import nc.vo.sm.funcreg.FuncRegisterVO;
import nc.vo.trade.pub.IExAggVO;

/**
 * @author 公共开发者 2014-9-14
 */
public class IJyglgtImpl implements IJyglgtItf {

	// 保存VO数组带PK
	public void insertVOsWithPK(Object[] objs) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			dao.insertVOsWithPK(objs);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// 保存VO数组不带PK
	public void insertVOsArr(Object[] objs) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			dao.insertVOsArr(objs);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// 删除VO数组
	public void deleteVOsArr(Object[] objs) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			dao.deleteVOsArr(objs);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// 删除VO
	public void deleteVO(Object obj) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			dao.deleteVO(obj);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// 更新VO数组
	public void updateVOsArr(Object[] objs) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			dao.updateVOsArr(objs);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// 更新VO
	public void updateVO(Object obj) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			dao.updateVO(obj);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

	}

	// 保存VO返回PK
	public String insertVObackPK(Object obj) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			return dao.insertVObackPK(obj);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// 执行update语句
	public void updateBYsql(String sql) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			dao.updateBYsql(sql);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// 执行query根据不同结果集返回不同的对象
	public Object queryBySql(String sql, SQLParameter parm,
			ResultSetProcessor rsp) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			return dao.queryBySql(sql, parm, rsp);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// 功能：执行SQL返回一个值
	public String queryStrBySql(String sql) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			return dao.queryStrBySql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());

		}
	}

	/**
	 * 功能：由SQL和VO，查询出记录
	 * 
	 * @param sql
	 *            查询语句
	 * @param voname
	 *            VO名称
	 * @return ArrayList
	 * @throws BusinessException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList getVOsfromSql(String sql, String voname)
			throws BusinessException, ClassNotFoundException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			return dao.getVOsfromSql(sql, voname);
		} catch (Exception e) {

			throw new BusinessException(e.getMessage());
		}
	}

	/**
	 * 功能:根据sql执行返回ArrayList,ArrayList里为HashMap
	 * 
	 * @param sql
	 *            query语句
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList queryArrayBySql(String sql) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			return dao.queryArrayBySql(sql);
		} catch (BusinessException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

	public String[] getPKFromDB(String pk_corp, int pkNum) {
		nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
		return dao.getPKFromDB(pk_corp, pkNum);
	}

	/**
	 * 保存聚合Vo
	 * */
	public void updateAggregatedValueVO(AggregatedValueObject avo)
			throws BusinessException {
		nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
		dao.updateAggregatedValueVO(avo);
	}

	/**
	 * 保存聚合Vo
	 * 
	 * @param AggregatedValueObject
	 *            obj
	 * @author shipeng
	 * @throws BusinessException
	 * */
	public String insertAggregatedValueVO(AggregatedValueObject obj)
			throws BusinessException {
		nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
		return dao.insertAggregatedValueVO(obj);
	}


	/**
	 * @param obj
	 * @throws BusinessException
	 * @功能 保存多子表聚合Vo(多子表IEXAggvo里要重写getAllChildrenVO()方法)
	 *     (多子表IEXAggvo里要重写getAllChildrenVO()方法)
	 */
	public void insertIEXAggregatedValueVO(IExAggVO obj, String[] tablecodes)
			throws BusinessException {
		new ServerDAO().insertIEXAggregatedValueVO(obj, tablecodes);
	}

	/**
	 * 针对有表体增删行修改时保存的数据，表体增行数据插入，删行数据更新删除标记
	 * */
	public void updateAggVOToEdit(AggregatedValueObject obj, String hid,
			String bid, String btable) throws BusinessException {
		ServerDAO dao = new ServerDAO();
		dao.updateAggVOToEdit(obj, hid, bid, btable);
	}

	/**
	 * 根据存货基本档案主键获取计量单位主键
	 * 
	 * @return String pk_invbasdoc 存货基本档案主键
	 * @serialData 2011-7-20
	 * @author sp
	 */
	public String getPk_measdocByPk(String pk_invbasdoc)
			throws BusinessException {
		return new ServerDAO().getPk_measdocByPk(pk_invbasdoc);
	}

	/**
	 * 查询value是否存在于tabName中field中,要求被查询的表中必须有dr字段
	 * 
	 * @param pk_plan
	 * @param field
	 *            表中字段
	 * @param tabName
	 * @return true被引用,false未引用
	 * @throws Exception
	 * @author shipeng 2011-6-3 14:09:43
	 */
	public boolean existValue(String tabName, String field, String value)
			throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			return dao.existValue(tabName, field, value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

	/**
	 * 功能: 查询value[]是否存在于tabName中field[]中,要求被查询的表中必须有dr字段 value数组的顺序必须与field对应
	 * 
	 * @param tabName
	 *            表名
	 * @param field
	 *            字段名数组
	 * @param value
	 *            值数组
	 * @param condition
	 *            条件:and/or
	 * @return boolean
	 * @throws Exception
	 * @return:boolean
	 * @author shipeng 2011-6-3 14:09:43
	 */
	public boolean existValue(String tabName, String[] field, String[] value)
			throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			return dao.existValue(tabName, field, value, "AND");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

	public boolean existValue(String tabName, String field, String value,
			String condition) throws BusinessException {
		nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
		return dao.existValue(tabName, field, value, condition);
	}

	public boolean existValues(String tabName, String[] field, String[] value,
			String condition) throws BusinessException {
		nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
		return dao.existValues(tabName, field, value, condition);
	}
	
	/**
	 * 根据公司主键得到单据主键
	 * @param pk_corp 公司主键
	 * @serialData 2011-8-23
	 * @author guoyt
	 */
	public String getOID(String pk_corp) throws BusinessException {
		return new nc.bs.jyglgt.dao.AbstractDAO().getOID(pk_corp);
	}


	public String[] getMoreOID(String pk_corp, int pkcount)
			throws BusinessException {
		return new nc.bs.jyglgt.dao.AbstractDAO().getMoreOID(pk_corp, pkcount);
	}


	public HashMap<String, String> getDeptAndUser(String cuserid) throws BusinessException{
		return new nc.bs.jyglgt.dao.ServerDAO().getDeptAndUser(cuserid);
	}

	/**
	 * 多单据多子表数据插入
	 * @author 施鹏
	 * @time 2014-09-15
	 * */
	public AggregatedValueObject[] insertIEXAggVO(AggregatedValueObject[] avos)throws BusinessException{
		return new nc.bs.jyglgt.dao.ServerDAO().insertIEXAggVO(avos);
	}
	/**
	 * 根据名称获取档案主键
	 * @param pk_corp
	 * @param name
	 * @param flag 0:客户档案，1：部门档案
	 * @author 施鹏
	 * @throws BusinessException 
	 * @time 2014-09-15
	 * */
	public  String getDocPkByName(String pk_corp ,String name,int flag) throws BusinessException{

		return new nc.bs.jyglgt.dao.ServerDAO().getDocPkByName(pk_corp, name,flag);
	}
	
	/**
	 * 根据客商管理档案转换
	 * @throws BusinessException 
	 * @time 2014-09-15
	 * */
	public  String getCusPkByCusPk(String pk_corp ,String inpk_cumandoc ) throws BusinessException{
		return new nc.bs.jyglgt.dao.ServerDAO().getCusPkByCusPk(pk_corp ,inpk_cumandoc);
	}
	
	/**
	 * 根据存货管理档案转换
	 * @throws BusinessException 
	 * @time 2014-09-15
	 * */
	public String getInvPkByInvPk(String pk_corp ,String inpk_invmandoc )throws BusinessException{
		return new nc.bs.jyglgt.dao.ServerDAO().getInvPkByInvPk(pk_corp ,inpk_invmandoc);
	}
	
	/**
	 * 根据编码获取档案主键
	 * @param pk_corp
	 * @param name
	 * @param flag 0:包装类别档案
	 * @author 施鹏
	 * @throws BusinessException 
	 * @time 2014-09-15
	 * */
	public  String getDocPkByCode(String pk_corp ,String code,int flag) throws BusinessException{
		return new nc.bs.jyglgt.dao.ServerDAO().getDocPkByCode(pk_corp, code,flag);
	}


	/**
	 * 主子表数据保存
	 * @author 施鹏
	 * @time 2014-09-15
	 * */
	public AggregatedValueObject saveHYBillVO(AggregatedValueObject avo,AggregatedValueObject checkvo)throws BusinessException{
		return new nc.bs.jyglgt.dao.ServerDAO().saveHYBillVO(avo, checkvo);
	}


	/**
	 * 判断系统自动化收货通知是否存在
	 * @throws BusinessException 
	 * */
	public boolean isExitByBfJLArriveNote(String datasource,String vbillcode,String bpk) throws BusinessException{
		return new nc.bs.jyglgt.bfgldao.BfglToPoDAO().isExitByBfJLArriveNote(datasource, vbillcode, bpk);
	}
	
	/**
	 * 获取自动化收获榜单数据
	 * @author 施鹏
	 * @time 2014-09-18
	 * */
	public void insertIntePoStorByBfgl(String datasource)throws BusinessException{
		new nc.bs.jyglgt.postordao.PoStoreDAO().insertIntePoStorByBfgl(datasource);
	}
	
	public void insertInteSaleoutByBfgl(String datasource)throws BusinessException{
		new SaleoutDAO().insertInteSaleoutByBfgl(datasource);
	}
	public void insertInteTransoutByBfgl(String datasource)throws BusinessException{
		new TransoutDAO().insertInteTransoutByBfgl(datasource);
	}
	/**
	 * 获取自动化采购结算单数据
	 * @author 施鹏
	 * @time 2014-09-24
	 * */
	public void insertIntePoVoiceByBfgl(String datasource)throws BusinessException{
		new nc.bs.jyglgt.povoicedao.PoVoiceDAO().insertIntePoVoiceByBfgl(datasource);
	}
	
	public void insertInteFreightByBfgl(String datasource)throws BusinessException{
		new FreightDAO().insertInteFreightByBfgl(datasource);
	}
	public void autoCreateBill(BilltypeVO btvo, GDTBillcodeRuleVO trvo, MyBillTempVO thvo,List<MyBillTempBVO> bvolist, List<MyBillTempTVO> tvolist, FuncRegisterVO fvo, BillMakeVO hvo) throws BusinessException {
		new CreateBillDAO().autoCreateBill(btvo,trvo,thvo,bvolist,tvolist,fvo,hvo);
	}
	
	public boolean testConnMiddleDB(){
		PersistenceManager sManager = null;
		try {
			sManager = PersistenceManager.getInstance(IDatasourceConst.BFJL);
			JdbcSession session = sManager.getJdbcSession();
			// 查询语句
			String sql = "select  * from dual";
			// 数据库访问操作
			session.executeQuery(sql,new MapListProcessor());
		} catch (Exception e) {
			return false;
		} finally{
			if (sManager != null)
				sManager.release();// 需要关闭会话
		}
		return true;
	}
	
	public void updateMiddleDBByBFJL(String sql)throws BusinessException{
		PersistenceManager sManager = null;
		int flag = 0 ;
		try {
			sManager = PersistenceManager.getInstance(IDatasourceConst.BFJL);
			JdbcSession session = sManager.getJdbcSession();
			// 数据库访问操作
			flag = session.executeUpdate(sql);
		} catch (Exception e) {
			throw new BusinessException("更新自动化系统失败,请确定是否连接成功.");
		} finally{
			if (sManager != null)
				sManager.release();// 需要关闭会话
		}
	}
	
	public void updateMiddleDB(GeneralBillVO vo) throws BusinessException{
		String bfsfcl_djbh = String.valueOf(vo.getHeaderVO ().getAttributeValue("vuserdef20"));
  		String	sql= " update BFSFCL set BFSFCL_RKBZ='0',BFSFCL_RKDH='' where BFSFCL_DJLX='1' and BFSFCL_DJBH='" + bfsfcl_djbh + "';";
		 updateMiddleDBByBFJL(sql);
	}
	
	/**
	 * @功能 销售货款结算 保存时回写 结算临时表,回写收款余额表结算额
	 * @param ExAggSellPaymentJSVO
	 *            iexaggvo
	 * @param String
	 *            pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-7-4
	 */
	public void writeBackToBalanceTemp(ExAggSellPaymentJSVO iexaggvo,
			String pk_corp,String pk_operator) throws BusinessException {
		new nc.bs.gt.gs.balance.BalanceBO().writeBackToBalanceTemp(iexaggvo,
				pk_corp,pk_operator);
	}

	/**
	 * @功能 销售货款结算反操作 保存时回写 结算临时表,回写收款余额表结算额
	 * @param ExAggSellPaymentJSVO
	 *            iexaggvo
	 * @param String
	 *            pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-7-4
	 */
	public void deleteBackToBalanceTemp(ExAggSellPaymentJSVO iexaggvo,
			String pk_corp) throws BusinessException {
		new nc.bs.gt.gs.balance.BalanceBO().deleteBackToBalanceTemp(iexaggvo,
				pk_corp);
	}

	/**
	 * @功能 销售货款结算 保存时回写 结算临时表
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-9-20
	 */
	public boolean writeBackToBalanceTemp_save(ExAggSellPaymentJSVO iexaggvo,String pk_corp)throws BusinessException{
		nc.bs.gt.gs.balance.BalanceBO bo=new nc.bs.gt.gs.balance.BalanceBO();
		return bo.writeBackToBalanceTemp_save(iexaggvo, pk_corp);
	}
	/**
	 * @功能 销售货款结算反操作 删除时回写 结算临时表
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-9-20
	 */
	public boolean deleteBackToBalanceTemp_del(ExAggSellPaymentJSVO iexaggvo,String pk_corp) throws BusinessException{
		nc.bs.gt.gs.balance.BalanceBO bo=new nc.bs.gt.gs.balance.BalanceBO();
		return bo.deleteBackToBalanceTemp_del(iexaggvo, pk_corp);
	}
	
	/**
	 * @功能 销售结算 保存时回写收款通知并生成结算明细
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public boolean writeBackToCreditAndBalanceDetail(ExAggSellPaymentJSVO iexaggvo,String pk_corp)throws BusinessException{
		nc.bs.gt.gs.balance.BalanceBO bo=new nc.bs.gt.gs.balance.BalanceBO();
		return bo.writeBackToCreditAndBalanceDetail(iexaggvo, pk_corp);
	}
	
	/**
	 * @功能 销售结算 删除时时回写收款通知并删除结算明细
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public boolean delBackToCreditAndBalanceDetail(ExAggSellPaymentJSVO iexaggvo,String pk_corp)throws BusinessException{
		nc.bs.gt.gs.balance.BalanceBO bo=new nc.bs.gt.gs.balance.BalanceBO();
		return bo.delBackToCreditAndBalanceDetail(iexaggvo, pk_corp);
	}
	
	/**
	 * @功能 销售货款结算 保存时回写收款余额表结算额
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public void writeBackToBalance(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator)throws BusinessException{
		new nc.bs.gt.gs.balance.BalanceBO().writeBackToBalance(iexaggvo,
				pk_corp,pk_operator);
	}
	
	/**
	 * @功能 销售货款结算反操作 弃审时回写 收款余额表结算额
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public void deleteBackToBalance(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator) throws BusinessException{
		new nc.bs.gt.gs.balance.BalanceBO().deleteBackToBalance(iexaggvo,
				pk_corp,pk_operator);
	}
	
	/**
	 * @功能 销售优惠结算 保存时回写收款余额表结算额
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public void writeBackToBalanceYh(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator)throws BusinessException{
		new nc.bs.gt.gs.balance.BalanceBO().writeBackToBalanceYh(iexaggvo,
				pk_corp,pk_operator);
	}
	
	/**
	 * @功能 销售优惠结算反操作 弃审时回写 收款余额表结算额
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public void deleteBackToBalanceYh(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator) throws BusinessException{
		new nc.bs.gt.gs.balance.BalanceBO().deleteBackToBalanceYh(iexaggvo,
				pk_corp,pk_operator);
	}
	
}