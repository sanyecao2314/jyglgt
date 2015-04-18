package nc.bs.jyglgt.bfgldao;

import nc.bs.jyglgt.dao.ServerDAO;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.pub.BusinessException;


/**
 * @author 施鹏
 * @功能： NC采购订单与系统自动化接口后台处理类
 * 
 */
public class BfglToPoDAO extends ServerDAO {
	public BfglToPoDAO() {
		super();
	}

	PersistenceManager sManager = null;



	
	/**
	 * 判断系统自动化派车单是否存在
	 * @throws BusinessException 
	 * datasource		大宗数据源
	 * vbillcode		订单编号
	 * bpk				订单子表id，目前传入为空
	 * */
	public boolean isExitByBfJLArriveNote(String datasource,String vbillcode,String bpk) throws BusinessException{		
		try {
			JdbcSession session = getsManager(datasource).getJdbcSession();
			// 查询语句
			StringBuffer sql=new StringBuffer();
			sql.append(" select count(bfpcd_pczh) count ")
				.append(" from BFPCD ")
				.append(" where BFPCD_SFBZ='0' and BFPCD_HTBH='"+vbillcode+"' "); 
			if(bpk!=null){
				sql.append(" and BFPCD_C7='"+bpk+"'");
			}
			
			// 数据库访问操作
			Object al = (Object) session.executeQuery(sql.toString(),
					new ColumnProcessor());
			int count = al == null ? 0 : Integer.parseInt(al.toString());
        	if(count>0){
        		return true;	
        	}else{
        		return false;
        	}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		} finally {
			if (sManager != null)
				sManager.release();
		}
	}
	


    

    private PersistenceManager getsManager(String datasource) throws DbException {
		if (sManager == null)
			sManager = PersistenceManager.getInstance(datasource);
		return sManager;
	}


}
