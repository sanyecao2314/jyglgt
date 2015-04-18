package nc.bs.jyglgt.bfgldao;

import nc.bs.jyglgt.dao.ServerDAO;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.pub.BusinessException;


/**
 * @author ʩ��
 * @���ܣ� NC�ɹ�������ϵͳ�Զ����ӿں�̨������
 * 
 */
public class BfglToPoDAO extends ServerDAO {
	public BfglToPoDAO() {
		super();
	}

	PersistenceManager sManager = null;



	
	/**
	 * �ж�ϵͳ�Զ����ɳ����Ƿ����
	 * @throws BusinessException 
	 * datasource		��������Դ
	 * vbillcode		�������
	 * bpk				�����ӱ�id��Ŀǰ����Ϊ��
	 * */
	public boolean isExitByBfJLArriveNote(String datasource,String vbillcode,String bpk) throws BusinessException{		
		try {
			JdbcSession session = getsManager(datasource).getJdbcSession();
			// ��ѯ���
			StringBuffer sql=new StringBuffer();
			sql.append(" select count(bfpcd_pczh) count ")
				.append(" from BFPCD ")
				.append(" where BFPCD_SFBZ='0' and BFPCD_HTBH='"+vbillcode+"' "); 
			if(bpk!=null){
				sql.append(" and BFPCD_C7='"+bpk+"'");
			}
			
			// ���ݿ���ʲ���
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
