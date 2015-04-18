/**
 * 
 */
package nc.bs.jyglgt.dao;

import java.sql.Connection;
import nc.bs.dao.BaseDAO;
import nc.bs.logging.Logger;
import nc.bs.pub.pf.PfUtilBO;
import nc.bs.pub.pf.PfUtilTools;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.generator.SequenceGenerator;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;


/**
 * @author  公共开发者 2012-1-5  13:48:36
 * @类型名  : AbstractDAO
 * @功能 : 后台数据库操作的基类
 * @版本 : v1.0
 * @修改记录 : 
 */
public  class AbstractDAO {
	private SequenceGenerator sg = null;	//OID对象
	private BaseDAO baseDAO = null;	//数据库操作基类
	private PfUtilBO pfUtilBO = null;	//流程平台后台端普通类
	
	//以上是为了执行存储过程
	private PersistenceManager sessionManager = null;
	private Connection conn = null;//
	private JdbcSession session=null;
	
	/**
	 * 取得session的值
	 * @return session - JdbcSession
	 * @throws DbException 
	 */
	protected JdbcSession getSession() throws DbException {
		if (session==null){
			if (sessionManager==null)
				getSessionManager();//如无连接，则采用默认数据源进行连接
			
			session = sessionManager.getJdbcSession();
		}
		return session;
	}

	/**
	 * 取得conn的值
	 * @return conn - Connection
	 * @throws DbException 
	 */
	protected Connection getConn() throws DbException {
		if (conn==null){
			conn=getSession().getConnection();
		}
		
		return conn;
	}

	/**
	 * 取得sessionManager的值
	 * @return sessionManager - PersistenceManager
	 * @throws DbException 
	 */
	protected PersistenceManager getSessionManager() throws DbException {
		if (sessionManager==null)
			sessionManager = PersistenceManager.getInstance();
		return sessionManager;
	}
	/**
	 * 取得sessionManager的值
	 * 
	 * @param datasource
	 * @return
	 * @throws DbException 
	 */
	protected PersistenceManager getSessionManager(String datasource) throws DbException {
		if (sessionManager==null)
			sessionManager = PersistenceManager.getInstance(datasource);
		return sessionManager;
	}
	/**
	 * 功能：关闭会话
	 *  
	 */
	protected void releaseSessionManager(){
		if (sessionManager!=null)
			sessionManager.release();
	}

	/**
	 * 功能：OID对象
	 * 
	 * @return 
	 */
	protected SequenceGenerator getSequenceGenerator(){
		if (sg==null)
			sg=new SequenceGenerator();
		return sg;
	}
	
	/**
	 * 功能：获取PK
	 * 
	 * @param pk_corp
	 * @return 
	 */
	public String getOID(String pk_corp){
		return getSequenceGenerator().generate(pk_corp.trim());
	}
	
	/**
	 * 功能：获取PK
	 * 
	 * @param pk_corp
	 * @return 
	 */
	public String[] getMoreOID(String pk_corp,int pkcount){
		return getSequenceGenerator().generate(pk_corp.trim(),pkcount);
	}
	
	/**
	 * 功能：数据库操作对象
	 * 
	 * @return 
	 */
	protected BaseDAO getBaseDAO() {
		if(baseDAO==null)
			baseDAO = new BaseDAO();
		return baseDAO;
	}
	
	/**
	 * 功能：平台操作对象
	 * 
	 * @return 
	 */
	protected PfUtilBO getPfUtilBO(){
		if (pfUtilBO==null)
			pfUtilBO=new PfUtilBO();
		return pfUtilBO;
	}

	/**
	 * 功能：执行单据动作脚本
	 * 
	 * @param actionName 动作名称
	 * @param billType	单据类型
	 * @param sDate		日期
	 * @param billVO	单据VO
	 * @throws BusinessException 
	 */
	protected void processBillVO(String actionName,String billType,String sDate,AggregatedValueObject billVO)throws BusinessException{
		try {
			getPfUtilBO().processAction(actionName,billType,sDate,null,billVO,null);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			Logger.error("执行动作脚本失败：", e);
			throw new BusinessException(e);
		}
	}
	
	/**
	 * 功能：运行数据交换类
	 * 
	 * @param sourceBillType 源单据类型PK
	 * @param destBillType 目的单据类型PK
	 * @param sourceBillVO 源单据聚合VO
	 * @return 目的单据聚合VO
	 * @throws BusinessException 
	 */
	protected AggregatedValueObject runChangeData(String sourceBillType, String destBillType,AggregatedValueObject sourceBillVO) throws BusinessException{
		return PfUtilTools.runChangeData(sourceBillType, destBillType, sourceBillVO);
	}
	
	/**
	 * 功能：运行数据交换类(VO数组)
	 * 
	 * @param sourceBillType 源单据类型PK
	 * @param destBillType 目的单据类型PK
	 * @param sourceBillVO 源单据聚合VO数组
	 * @return 源单据聚合VO数组
	 * @throws BusinessException 
	 */
	protected AggregatedValueObject[] runChangeDataAry(String sourceBillType, String destBillType,AggregatedValueObject[] sourceBillVO) throws BusinessException{
		return PfUtilTools.runChangeDataAry(sourceBillType, destBillType, sourceBillVO);
	}
	
}
