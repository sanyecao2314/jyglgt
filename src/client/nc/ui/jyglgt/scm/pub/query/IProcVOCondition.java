package nc.ui.jyglgt.scm.pub.query;

import nc.ui.querytemplate.filter.DefaultFilter;
import nc.vo.pub.BusinessException;
import nc.vo.pub.query.QueryConditionVO;

public interface IProcVOCondition {
	
	
	/**
	 * ����fileter�������sql,���ڲ�ѯǰ���������sql����
	 * @param filter
	 * @return
	 */
	public String getSqlString(DefaultFilter filter);
}
