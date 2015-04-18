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
 * @author ���������� 2014-9-14
 */
public class IJyglgtImpl implements IJyglgtItf {

	// ����VO�����PK
	public void insertVOsWithPK(Object[] objs) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			dao.insertVOsWithPK(objs);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// ����VO���鲻��PK
	public void insertVOsArr(Object[] objs) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			dao.insertVOsArr(objs);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// ɾ��VO����
	public void deleteVOsArr(Object[] objs) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			dao.deleteVOsArr(objs);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// ɾ��VO
	public void deleteVO(Object obj) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			dao.deleteVO(obj);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// ����VO����
	public void updateVOsArr(Object[] objs) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			dao.updateVOsArr(objs);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// ����VO
	public void updateVO(Object obj) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			dao.updateVO(obj);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}

	}

	// ����VO����PK
	public String insertVObackPK(Object obj) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			return dao.insertVObackPK(obj);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// ִ��update���
	public void updateBYsql(String sql) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			dao.updateBYsql(sql);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// ִ��query���ݲ�ͬ��������ز�ͬ�Ķ���
	public Object queryBySql(String sql, SQLParameter parm,
			ResultSetProcessor rsp) throws BusinessException {
		try {
			nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
			return dao.queryBySql(sql, parm, rsp);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	// ���ܣ�ִ��SQL����һ��ֵ
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
	 * ���ܣ���SQL��VO����ѯ����¼
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @param voname
	 *            VO����
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
	 * ����:����sqlִ�з���ArrayList,ArrayList��ΪHashMap
	 * 
	 * @param sql
	 *            query���
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
	 * ����ۺ�Vo
	 * */
	public void updateAggregatedValueVO(AggregatedValueObject avo)
			throws BusinessException {
		nc.bs.jyglgt.dao.ServerDAO dao = new nc.bs.jyglgt.dao.ServerDAO();
		dao.updateAggregatedValueVO(avo);
	}

	/**
	 * ����ۺ�Vo
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
	 * @���� ������ӱ�ۺ�Vo(���ӱ�IEXAggvo��Ҫ��дgetAllChildrenVO()����)
	 *     (���ӱ�IEXAggvo��Ҫ��дgetAllChildrenVO()����)
	 */
	public void insertIEXAggregatedValueVO(IExAggVO obj, String[] tablecodes)
			throws BusinessException {
		new ServerDAO().insertIEXAggregatedValueVO(obj, tablecodes);
	}

	/**
	 * ����б�����ɾ���޸�ʱ��������ݣ������������ݲ��룬ɾ�����ݸ���ɾ�����
	 * */
	public void updateAggVOToEdit(AggregatedValueObject obj, String hid,
			String bid, String btable) throws BusinessException {
		ServerDAO dao = new ServerDAO();
		dao.updateAggVOToEdit(obj, hid, bid, btable);
	}

	/**
	 * ���ݴ����������������ȡ������λ����
	 * 
	 * @return String pk_invbasdoc ���������������
	 * @serialData 2011-7-20
	 * @author sp
	 */
	public String getPk_measdocByPk(String pk_invbasdoc)
			throws BusinessException {
		return new ServerDAO().getPk_measdocByPk(pk_invbasdoc);
	}

	/**
	 * ��ѯvalue�Ƿ������tabName��field��,Ҫ�󱻲�ѯ�ı��б�����dr�ֶ�
	 * 
	 * @param pk_plan
	 * @param field
	 *            �����ֶ�
	 * @param tabName
	 * @return true������,falseδ����
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
	 * ����: ��ѯvalue[]�Ƿ������tabName��field[]��,Ҫ�󱻲�ѯ�ı��б�����dr�ֶ� value�����˳�������field��Ӧ
	 * 
	 * @param tabName
	 *            ����
	 * @param field
	 *            �ֶ�������
	 * @param value
	 *            ֵ����
	 * @param condition
	 *            ����:and/or
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
	 * ���ݹ�˾�����õ���������
	 * @param pk_corp ��˾����
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
	 * �൥�ݶ��ӱ����ݲ���
	 * @author ʩ��
	 * @time 2014-09-15
	 * */
	public AggregatedValueObject[] insertIEXAggVO(AggregatedValueObject[] avos)throws BusinessException{
		return new nc.bs.jyglgt.dao.ServerDAO().insertIEXAggVO(avos);
	}
	/**
	 * �������ƻ�ȡ��������
	 * @param pk_corp
	 * @param name
	 * @param flag 0:�ͻ�������1�����ŵ���
	 * @author ʩ��
	 * @throws BusinessException 
	 * @time 2014-09-15
	 * */
	public  String getDocPkByName(String pk_corp ,String name,int flag) throws BusinessException{

		return new nc.bs.jyglgt.dao.ServerDAO().getDocPkByName(pk_corp, name,flag);
	}
	
	/**
	 * ���ݿ��̹�����ת��
	 * @throws BusinessException 
	 * @time 2014-09-15
	 * */
	public  String getCusPkByCusPk(String pk_corp ,String inpk_cumandoc ) throws BusinessException{
		return new nc.bs.jyglgt.dao.ServerDAO().getCusPkByCusPk(pk_corp ,inpk_cumandoc);
	}
	
	/**
	 * ���ݴ��������ת��
	 * @throws BusinessException 
	 * @time 2014-09-15
	 * */
	public String getInvPkByInvPk(String pk_corp ,String inpk_invmandoc )throws BusinessException{
		return new nc.bs.jyglgt.dao.ServerDAO().getInvPkByInvPk(pk_corp ,inpk_invmandoc);
	}
	
	/**
	 * ���ݱ����ȡ��������
	 * @param pk_corp
	 * @param name
	 * @param flag 0:��װ��𵵰�
	 * @author ʩ��
	 * @throws BusinessException 
	 * @time 2014-09-15
	 * */
	public  String getDocPkByCode(String pk_corp ,String code,int flag) throws BusinessException{
		return new nc.bs.jyglgt.dao.ServerDAO().getDocPkByCode(pk_corp, code,flag);
	}


	/**
	 * ���ӱ����ݱ���
	 * @author ʩ��
	 * @time 2014-09-15
	 * */
	public AggregatedValueObject saveHYBillVO(AggregatedValueObject avo,AggregatedValueObject checkvo)throws BusinessException{
		return new nc.bs.jyglgt.dao.ServerDAO().saveHYBillVO(avo, checkvo);
	}


	/**
	 * �ж�ϵͳ�Զ����ջ�֪ͨ�Ƿ����
	 * @throws BusinessException 
	 * */
	public boolean isExitByBfJLArriveNote(String datasource,String vbillcode,String bpk) throws BusinessException{
		return new nc.bs.jyglgt.bfgldao.BfglToPoDAO().isExitByBfJLArriveNote(datasource, vbillcode, bpk);
	}
	
	/**
	 * ��ȡ�Զ����ջ������
	 * @author ʩ��
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
	 * ��ȡ�Զ����ɹ����㵥����
	 * @author ʩ��
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
			// ��ѯ���
			String sql = "select  * from dual";
			// ���ݿ���ʲ���
			session.executeQuery(sql,new MapListProcessor());
		} catch (Exception e) {
			return false;
		} finally{
			if (sManager != null)
				sManager.release();// ��Ҫ�رջỰ
		}
		return true;
	}
	
	public void updateMiddleDBByBFJL(String sql)throws BusinessException{
		PersistenceManager sManager = null;
		int flag = 0 ;
		try {
			sManager = PersistenceManager.getInstance(IDatasourceConst.BFJL);
			JdbcSession session = sManager.getJdbcSession();
			// ���ݿ���ʲ���
			flag = session.executeUpdate(sql);
		} catch (Exception e) {
			throw new BusinessException("�����Զ���ϵͳʧ��,��ȷ���Ƿ����ӳɹ�.");
		} finally{
			if (sManager != null)
				sManager.release();// ��Ҫ�رջỰ
		}
	}
	
	public void updateMiddleDB(GeneralBillVO vo) throws BusinessException{
		String bfsfcl_djbh = String.valueOf(vo.getHeaderVO ().getAttributeValue("vuserdef20"));
  		String	sql= " update BFSFCL set BFSFCL_RKBZ='0',BFSFCL_RKDH='' where BFSFCL_DJLX='1' and BFSFCL_DJBH='" + bfsfcl_djbh + "';";
		 updateMiddleDBByBFJL(sql);
	}
	
	/**
	 * @���� ���ۻ������ ����ʱ��д ������ʱ��,��д�տ���������
	 * @param ExAggSellPaymentJSVO
	 *            iexaggvo
	 * @param String
	 *            pk_corp
	 * @throws BusinessException
	 * @author ʩ��
	 * @serialData 2011-7-4
	 */
	public void writeBackToBalanceTemp(ExAggSellPaymentJSVO iexaggvo,
			String pk_corp,String pk_operator) throws BusinessException {
		new nc.bs.gt.gs.balance.BalanceBO().writeBackToBalanceTemp(iexaggvo,
				pk_corp,pk_operator);
	}

	/**
	 * @���� ���ۻ�����㷴���� ����ʱ��д ������ʱ��,��д�տ���������
	 * @param ExAggSellPaymentJSVO
	 *            iexaggvo
	 * @param String
	 *            pk_corp
	 * @throws BusinessException
	 * @author ʩ��
	 * @serialData 2011-7-4
	 */
	public void deleteBackToBalanceTemp(ExAggSellPaymentJSVO iexaggvo,
			String pk_corp) throws BusinessException {
		new nc.bs.gt.gs.balance.BalanceBO().deleteBackToBalanceTemp(iexaggvo,
				pk_corp);
	}

	/**
	 * @���� ���ۻ������ ����ʱ��д ������ʱ��
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author ʩ��
	 * @serialData 2011-9-20
	 */
	public boolean writeBackToBalanceTemp_save(ExAggSellPaymentJSVO iexaggvo,String pk_corp)throws BusinessException{
		nc.bs.gt.gs.balance.BalanceBO bo=new nc.bs.gt.gs.balance.BalanceBO();
		return bo.writeBackToBalanceTemp_save(iexaggvo, pk_corp);
	}
	/**
	 * @���� ���ۻ�����㷴���� ɾ��ʱ��д ������ʱ��
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author ʩ��
	 * @serialData 2011-9-20
	 */
	public boolean deleteBackToBalanceTemp_del(ExAggSellPaymentJSVO iexaggvo,String pk_corp) throws BusinessException{
		nc.bs.gt.gs.balance.BalanceBO bo=new nc.bs.gt.gs.balance.BalanceBO();
		return bo.deleteBackToBalanceTemp_del(iexaggvo, pk_corp);
	}
	
	/**
	 * @���� ���۽��� ����ʱ��д�տ�֪ͨ�����ɽ�����ϸ
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author ʩ��
	 * @serialData 2014-10-26
	 */
	public boolean writeBackToCreditAndBalanceDetail(ExAggSellPaymentJSVO iexaggvo,String pk_corp)throws BusinessException{
		nc.bs.gt.gs.balance.BalanceBO bo=new nc.bs.gt.gs.balance.BalanceBO();
		return bo.writeBackToCreditAndBalanceDetail(iexaggvo, pk_corp);
	}
	
	/**
	 * @���� ���۽��� ɾ��ʱʱ��д�տ�֪ͨ��ɾ��������ϸ
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author ʩ��
	 * @serialData 2014-10-26
	 */
	public boolean delBackToCreditAndBalanceDetail(ExAggSellPaymentJSVO iexaggvo,String pk_corp)throws BusinessException{
		nc.bs.gt.gs.balance.BalanceBO bo=new nc.bs.gt.gs.balance.BalanceBO();
		return bo.delBackToCreditAndBalanceDetail(iexaggvo, pk_corp);
	}
	
	/**
	 * @���� ���ۻ������ ����ʱ��д�տ���������
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author ʩ��
	 * @serialData 2014-10-26
	 */
	public void writeBackToBalance(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator)throws BusinessException{
		new nc.bs.gt.gs.balance.BalanceBO().writeBackToBalance(iexaggvo,
				pk_corp,pk_operator);
	}
	
	/**
	 * @���� ���ۻ�����㷴���� ����ʱ��д �տ���������
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author ʩ��
	 * @serialData 2014-10-26
	 */
	public void deleteBackToBalance(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator) throws BusinessException{
		new nc.bs.gt.gs.balance.BalanceBO().deleteBackToBalance(iexaggvo,
				pk_corp,pk_operator);
	}
	
	/**
	 * @���� �����Żݽ��� ����ʱ��д�տ���������
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author ʩ��
	 * @serialData 2014-10-26
	 */
	public void writeBackToBalanceYh(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator)throws BusinessException{
		new nc.bs.gt.gs.balance.BalanceBO().writeBackToBalanceYh(iexaggvo,
				pk_corp,pk_operator);
	}
	
	/**
	 * @���� �����Żݽ��㷴���� ����ʱ��д �տ���������
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author ʩ��
	 * @serialData 2014-10-26
	 */
	public void deleteBackToBalanceYh(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator) throws BusinessException{
		new nc.bs.gt.gs.balance.BalanceBO().deleteBackToBalanceYh(iexaggvo,
				pk_corp,pk_operator);
	}
	
}