package nc.ui.jyglgt.scm.pub.query;

import javax.swing.JPanel;

import nc.vo.pub.query.ConditionVO;

/**
 * �°��ѯģ��Ĳ�ѯ���ӿڣ������ڲ�ѯ�Ի����м����Լ��Ĳ�ѯ���
 * 
 * @see nc.ui.jyglgt.scm.pub.query.SCMTreeQueryConditionDLG#addCustomQueryPanel(IQueryTabPanel panel)
 * @since 2008-11-6
 */
public interface IQueryTabPanel {
	/**
	 * @return ������
	 */
	String getTitle();

	String getWhereSql();

	ConditionVO[] getConditionVOs();

	/**
	 * @return ������
	 */
	JPanel getPanel();
}
