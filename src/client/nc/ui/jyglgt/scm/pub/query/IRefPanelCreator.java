package nc.ui.jyglgt.scm.pub.query;

import javax.swing.JComponent;


public interface IRefPanelCreator<T extends JComponent> {
	/**
	 * 创建一个参照面板供新版查询模板使用，由于在新版查询模板中，
	 * 一个字段可能有多个编辑器需要显示，如“介于”操作符等，
	 * 所以此方法每次返回的一定要新创建的对象，不能是指向同一个对象的引用
	 * 
	 * @return 参照面板
	 */
	T create();
}
