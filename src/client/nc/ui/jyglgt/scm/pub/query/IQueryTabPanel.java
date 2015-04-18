package nc.ui.jyglgt.scm.pub.query;

import javax.swing.JPanel;

import nc.vo.pub.query.ConditionVO;

/**
 * 新版查询模板的查询面板接口，可以在查询对话框中加入自己的查询面版
 * 
 * @see nc.ui.jyglgt.scm.pub.query.SCMTreeQueryConditionDLG#addCustomQueryPanel(IQueryTabPanel panel)
 * @since 2008-11-6
 */
public interface IQueryTabPanel {
	/**
	 * @return 面板标题
	 */
	String getTitle();

	String getWhereSql();

	ConditionVO[] getConditionVOs();

	/**
	 * @return 获得面板
	 */
	JPanel getPanel();
}
