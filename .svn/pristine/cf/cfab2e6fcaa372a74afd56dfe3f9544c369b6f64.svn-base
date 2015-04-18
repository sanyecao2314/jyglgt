package nc.ui.jyglgt.scm.pub.query;

import static nc.vo.jcom.lang.StringUtil.isEmpty;

import javax.swing.JPanel;

import nc.vo.pub.query.ConditionVO;
import nc.vo.scm.print.PrintConst;
import nc.vo.scm.pub.SCMEnv;


/**
 * 创建人：王乃军

 * 功能：打印状态对话框。

 * 日期：(2004-11-15 14:06:17)
 */
public class QryPrintStatusPanel extends nc.ui.pub.beans.UIPanel implements IQueryTabPanel{
	private static final long serialVersionUID = 690536709108147998L;

	private javax.swing.JRadioButton ivjJRadioButtonAll = null;
	private javax.swing.JRadioButton ivjJRadioButtonNotPrinted = null;
	private javax.swing.JRadioButton ivjJRadioButtonPrinted = null;

 	public final static int SEL_ALL=-1;//全部
 	public final static int SEL_NOT_PRINTED= 0;//没打过的
 	public final static int SEL_PRINTED= 1;//打过的
 	
 	private String tableCode = null;

	private String code = PrintConst.IPrintCount;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

/**
 * QryPrintStatusPanel 构造子注解。
 */
public QryPrintStatusPanel() {
	super();
	initialize();
}
/**
 * QryPrintStatusPanel 构造子注解。
 * @param p0 java.awt.LayoutManager
 */
public QryPrintStatusPanel(java.awt.LayoutManager p0) {
	super(p0);
}
/**
 * QryPrintStatusPanel 构造子注解。
 * @param p0 java.awt.LayoutManager
 * @param p1 boolean
 */
public QryPrintStatusPanel(java.awt.LayoutManager p0, boolean p1) {
	super(p0, p1);
}
/**
 * QryPrintStatusPanel 构造子注解。
 * @param p0 boolean
 */
public QryPrintStatusPanel(boolean p0) {
	super(p0);
}
/**
 * 返回 JRadioButtonAll 特性值。
 * @return javax.swing.JRadioButton
 */
/* 警告：此方法将重新生成。 */
private javax.swing.JRadioButton getJRadioButtonAll() {
	if (ivjJRadioButtonAll == null) {
		try {
			ivjJRadioButtonAll = new javax.swing.JRadioButton();
			ivjJRadioButtonAll.setName("JRadioButtonAll");
			ivjJRadioButtonAll.setSelected(true);
			ivjJRadioButtonAll.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("scmcommon","UPPSCMCommon-000217")/*@res "全部"*/);
			ivjJRadioButtonAll.setBounds(55, 13, 151, 42);
			// user code begin {1}
			ivjJRadioButtonAll.setSelected(true);//default!
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			SCMEnv.error(ivjExc);
		}
	}
	return ivjJRadioButtonAll;
}
/**
 * 返回 JRadioButtonNotPrinted 特性值。
 * @return javax.swing.JRadioButton
 */
/* 警告：此方法将重新生成。 */
private javax.swing.JRadioButton getJRadioButtonNotPrinted() {
	if (ivjJRadioButtonNotPrinted == null) {
		try {
			ivjJRadioButtonNotPrinted = new javax.swing.JRadioButton();
			ivjJRadioButtonNotPrinted.setName("JRadioButtonNotPrinted");
			ivjJRadioButtonNotPrinted.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("scmpub","UPPscmpub-000561")/*@res "未打印过的"*/);
			ivjJRadioButtonNotPrinted.setBounds(55, 123, 151, 42);
			// user code begin {1}
			ivjJRadioButtonNotPrinted.setSelected(false);
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			SCMEnv.error(ivjExc);
		}
	}
	return ivjJRadioButtonNotPrinted;
}
/**
 * 返回 JRadioButtonPrinted 特性值。
 * @return javax.swing.JRadioButton
 */
/* 警告：此方法将重新生成。 */
private javax.swing.JRadioButton getJRadioButtonPrinted() {
	if (ivjJRadioButtonPrinted == null) {
		try {
			ivjJRadioButtonPrinted = new javax.swing.JRadioButton();
			ivjJRadioButtonPrinted.setName("JRadioButtonPrinted");
			ivjJRadioButtonPrinted.setSelected(false);
			ivjJRadioButtonPrinted.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("scmpub","UPPscmpub-000562")/*@res "打印过的"*/);
			ivjJRadioButtonPrinted.setBounds(55, 68, 151, 42);
			// user code begin {1}
			ivjJRadioButtonPrinted.setSelected(false);
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			SCMEnv.error(ivjExc);
		}
	}
	return ivjJRadioButtonPrinted;
}
/**
 * 创建人：王乃军
 *
 * 功能：得到选择的结果。sql
 *
 * 参数：表别名。会加在返回SQL的字段前面。可为空。
 *
 * 返回：
 *
 * 异常：
 *
 * 日期：(2004-11-15 14:16:10)
 * 修改日期，修改人，修改原因，注释标志：
 */
public String getResult(String sTableAlias) {
	String sResSql = ""; //default is ALL.
	
	if (sTableAlias == null)
		sTableAlias = "";
	else
		sTableAlias = sTableAlias.trim()+".";

	if (getJRadioButtonPrinted().isSelected())
		sResSql = sResSql + sTableAlias + nc.vo.scm.print.PrintConst.IPrintCount + ">0 ";
	else if (getJRadioButtonNotPrinted().isSelected())
		sResSql =sResSql + " ("+ sTableAlias  + nc.vo.scm.print.PrintConst.IPrintCount + "<=0 OR "
			+ sTableAlias  + nc.vo.scm.print.PrintConst.IPrintCount + " IS NULL ) ";
	//else // means ALL   sResSql = ""

	return sResSql;
}
/**
 * 创建人：王乃军
 *
 * 功能：得到选择的结果。

 	设计文档中未考虑到。
 *
 * 参数：
 *
 * 返回： SEL_XXX constants
 *
 * 异常：
 *
 * 日期：(2004-11-15 14:16:10)
 * 修改日期，修改人，修改原因，注释标志：
 */
public int getStatus() {
	int iSelect = SEL_ALL;

	if (getJRadioButtonPrinted().isSelected())
		iSelect = SEL_PRINTED;
	else if (getJRadioButtonNotPrinted().isSelected())
		iSelect = SEL_NOT_PRINTED;
	return iSelect;
}

/**
 * 初始化类。
 */
/* 警告：此方法将重新生成。 */
private void initialize() {
	try {
		// user code begin {1}
		// user code end
		setName("QryPrintStatusPanel");
		setLayout(null);
		setSize(269, 178);
		add(getJRadioButtonAll(), getJRadioButtonAll().getName());
		add(getJRadioButtonPrinted(), getJRadioButtonPrinted().getName());
		add(getJRadioButtonNotPrinted(), getJRadioButtonNotPrinted().getName());
	} catch (java.lang.Throwable ivjExc) {
		SCMEnv.error(ivjExc);
	}
	// user code begin {2}
	javax.swing.ButtonGroup bg=new javax.swing.ButtonGroup();
	bg.add(getJRadioButtonAll());
	bg.add(getJRadioButtonNotPrinted());
	bg.add(getJRadioButtonPrinted());
	// user code end
}
/**
 * 主入口点 - 当部件作为应用程序运行时，启动这个部件。
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		javax.swing.JFrame frame = new javax.swing.JFrame();
		QryPrintStatusPanel aQryPrintStatusPanel;
		aQryPrintStatusPanel = new QryPrintStatusPanel();
		frame.setContentPane(aQryPrintStatusPanel);
		frame.setSize(aQryPrintStatusPanel.getSize());
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});
		frame.setVisible(true);
		java.awt.Insets insets = frame.getInsets();
		frame.setSize(frame.getWidth() + insets.left + insets.right, frame.getHeight() + insets.top + insets.bottom);
		frame.setVisible(true);
	} catch (Throwable exception) {
		nc.vo.scm.pub.SCMEnv.out("nc.ui.pub.beans.UIPanel 的 main() 中发生异常");/*-=notranslate=-*/
		nc.vo.scm.pub.SCMEnv.out(exception);
	}
}

	public ConditionVO[] getConditionVOs() {
		int iStatus = getStatus();
		if (SEL_ALL == iStatus) {
			return new ConditionVO[0];
		}
		ConditionVO printConditionVO = new ConditionVO();
		printConditionVO.setFieldCode(getCode());
		printConditionVO.setTableCode(getTableCode());
		printConditionVO.setDataType(ConditionVO.INTEGER);
		printConditionVO.setValue("0");
		if (iStatus == QryPrintStatusPanel.SEL_PRINTED) {
			printConditionVO.setOperaCode(">");
		} else {
			printConditionVO.setOperaCode("=");
		}
		return new ConditionVO[] { printConditionVO };
	}

	public String getWhereSql() {
		String colum = null;
		if (!isEmpty(getTableCode())) {
			colum = getCode();
		} else {
			colum = getTableCode() + "." + getCode();
		}
		switch (getStatus()) {
			case SEL_PRINTED:
				return colum + " > 0";
			case SEL_NOT_PRINTED:
				return colum + " = 0";
			case SEL_ALL:
			default:
				return null;
		}
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public String getTitle() {
		return nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UC000-0001993")/* @res "打印状态" */;
	}
	public JPanel getPanel() {
		return this;
	}

}