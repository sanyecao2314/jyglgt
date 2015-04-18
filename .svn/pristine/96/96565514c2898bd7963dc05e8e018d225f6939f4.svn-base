package nc.ui.jyglgt.scm.pub.report;

import java.util.Hashtable;
import java.util.Vector;

import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.pub.bill.BillItem;
import nc.vo.scm.pub.report.GroupVO;

public class GroupPanel extends UIPanel implements BillEditListener2,
		BillEditListener {
	private BillCardPanel groupPanel = null;
	private UIPanel normalPanel = null;
	private BillItem[] biGroups = null;
	private String[] groupNames = {
			nc.ui.ml.NCLangRes.getInstance().getStrByID("scmpub",
					"UPPscmpub-000158")/* @res "分组可选条件" */,
			nc.ui.ml.NCLangRes.getInstance().getStrByID("scmpub",
					"UPPscmpub-000159")/* @res "分组顺序" */,
			nc.ui.ml.NCLangRes.getInstance().getStrByID("scmpub",
					"UPPscmpub-000160")/* @res "分组选择" */,
			nc.ui.ml.NCLangRes.getInstance().getStrByID("scmcommon",
					"UPPSCMCommon-000285")/* @res "小计" */, "key" };
	private String[] groupKeys = { "condition", "order", "select", "subtotal",
			"key" };
	private boolean[] groupShow = { true, true, true, true, false };
	private int[] groupDataType = { 0, 0, 4, 4, 0 };
	private String[][] groupLoad = { null, null, null, null, null };
	private String[] conditionName = null;
	private String[] conditionKey = null;
	private Hashtable ht = new Hashtable();

	/**
	 * GroupPanel 构造子注解。
	 */
	public GroupPanel(String[] key, String[] name) {
		super();
		setCondition(key, name);
		initHash();
		initialize();
	}

	/**
	 * GroupPanel 构造子注解。
	 * 
	 * @param p0
	 *            java.awt.LayoutManager
	 */
	public GroupPanel(java.awt.LayoutManager p0, String[] key, String[] name) {
		super(p0);
		setCondition(key, name);
		initialize();
	}

	/**
	 * GroupPanel 构造子注解。
	 * 
	 * @param p0
	 *            java.awt.LayoutManager
	 * @param p1
	 *            boolean
	 */
	public GroupPanel(java.awt.LayoutManager p0, boolean p1, String[] key,
			String[] name) {
		super(p0, p1);
		setCondition(key, name);
		initialize();
	}

	/**
	 * GroupPanel 构造子注解。
	 * 
	 * @param p0
	 *            boolean
	 */
	public GroupPanel(boolean p0, String[] key, String[] name) {
		super(p0);
		setCondition(key, name);
		initialize();
	}

	public void afterEdit(nc.ui.pub.bill.BillEditEvent e) {
		int rowNum = e.getRow();
		if (e.getKey().equals("order")) {
			getGroupPanel().setBodyValueAt(new nc.vo.pub.lang.UFBoolean(true),
					rowNum, "select");
			GroupVO[] vos = (GroupVO[]) getGroupPanel().getBillModel()
					.getBodyValueVOs(GroupVO.class.getName());
			String order = getGroupPanel().getBodyValueAt(rowNum, e.getKey())
					.toString();
			for (int i = 0; i < vos.length; i++) {
				if (i != rowNum
						&& vos[i].getAttributeValue(e.getKey()) != null
						&& vos[i].getAttributeValue(e.getKey()).toString()
								.equals(order)) {
					getGroupPanel().setBodyValueAt(null, rowNum, e.getKey());
					getGroupPanel().setBodyValueAt(null, rowNum, "select");
				}
			}

			int freeRow = getRowIndexOfAttr(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC000-0003327")/* @res "自由项" */);

			if (order == null || order.toString().length() == 0) {
				getGroupPanel().setBodyValueAt(null, rowNum, "select");
				getGroupPanel().setBodyValueAt(null, rowNum, "subtotal");
				if (getGroupPanel().getBodyValueAt(rowNum, "condition")
						.toString().equals("存货")) {

					if (freeRow != -1) {
						getGroupPanel().setBodyValueAt(null, freeRow,
								e.getKey());
						getGroupPanel().setBodyValueAt(null, freeRow,
								"subtotal");
						getGroupPanel().setBodyValueAt(null, freeRow, "select");
						getGroupPanel().getBodyItem("order").setEnabled(false);
					}
				}
			} else {
				getGroupPanel().getBodyItem("order").setEnabled(true);
			}

		}
		//
		if (e.getKey().equals("select")) {
			getGroupPanel().setBodyValueAt(null, rowNum, e.getKey());
			getGroupPanel().setBodyValueAt(null, rowNum, "order");
			getGroupPanel().setBodyValueAt(null, rowNum, "subtotal");
			int freeRow = getRowIndexOfAttr(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC000-0003327")/* @res "自由项" */);
			if (freeRow != -1) {
				getGroupPanel().setBodyValueAt(null, freeRow, e.getKey());
				getGroupPanel().setBodyValueAt(null, freeRow, "order");
				getGroupPanel().setBodyValueAt(null, freeRow, "subtotal");
			}
		}
		//
		if (e.getKey().equals("subtotal")) {
			if (getGroupPanel().getBodyValueAt(rowNum, "order") == null
					|| (getGroupPanel().getBodyValueAt(rowNum, "order") != null && getGroupPanel()
							.getBodyValueAt(rowNum, "order").toString()
							.length() == 0)) {
				getGroupPanel().setBodyValueAt(null, rowNum, e.getKey());
			}
		}

		getGroupPanel().updateValue();
		getGroupPanel().updateUI();
	}

	public boolean beforeEdit(BillEditEvent e) {

		int rowNum = e.getRow();
		// 监听取值内容
		if (e.getKey().equals("select")) {
			if (getGroupPanel().getBodyValueAt(rowNum, e.getKey()) != null) {
				if (getGroupPanel().getBodyValueAt(rowNum, e.getKey())
						.toString().equals("true")) {
					getGroupPanel().setBodyValueAt(null, rowNum, "order");
				}
			}
			getGroupPanel().setBodyValueAt(null, rowNum, e.getKey());
		}

		if (e.getKey().equals("order")) {
			if (getGroupPanel().getBodyValueAt(rowNum, "condition").equals(
					"自由项")) {
				int invRow = getRowIndexOfAttr(nc.ui.ml.NCLangRes.getInstance()
						.getStrByID("common", "UC000-0001439")/* @res "存货" */);
				if (invRow != -1
						&& (getGroupPanel().getBodyValueAt(invRow, "order") == null || getGroupPanel()
								.getBodyValueAt(invRow, "order").toString()
								.length() == 0)) {
					getGroupPanel().getBodyItem("order").setEnabled(false);
				}
			} else {
				getGroupPanel().getBodyItem("order").setEnabled(true);
			}
		}

		getGroupPanel().updateValue();
		getGroupPanel().updateUI();

		return true;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-10-08 2:02:27)
	 * 
	 * @param e
	 *            ufbill.BillEditEvent
	 */
	public void bodyRowChange(nc.ui.pub.bill.BillEditEvent e) {
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-9-12 16:52:03)
	 */
	public String[] getConditionKeys() {
		return conditionKey;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-9-12 16:52:03)
	 */
	public String[] getConditionNames() {
		return conditionName;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-9-13 16:28:26)
	 */
	public String getFirstSubtotalCol() {
		String col = null;
		String[] cols = getSubtotalCols();
		if (cols != null && cols.length > 0) {
			col = cols[0];
		}
		return col;
	}

	/**
	 * 查询条件显示Panel。 创建日期：(2001-08-09 10:49:20)
	 */
	public BillCardPanel getGroupPanel() {
		if (groupPanel == null) {
			groupPanel = new BillCardPanel();
			groupPanel.setPreferredSize(new java.awt.Dimension(485, 259));
			groupPanel.setName(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"scmpub", "UPPscmpub-000161")/* @res "分组依据设置" */);

			initPanel();

			BillData billdata = new BillData();
			billdata.setBodyItems(biGroups);
			groupPanel.setBillData(billdata);
			groupPanel.getBodyPanel().setRowNOShow(false);
			groupPanel.getBodyItem("condition").setEdit(false);
			groupPanel.addEditListener(this);
			groupPanel.addBodyEditListener2(this);

			loadData();

		}
		return groupPanel;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-9-12 17:09:04)
	 */
	public String[] getNames() {
		return getValuesByCol("condition");
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-9-12 17:09:04)
	 */
	public String[] getResultKeys() {
		String[] names = getResultNames();
		if (names == null || names.length == 0) {
			return null;
		}
		String[] keys = new String[names.length];
		for (int i = 0; i < names.length; i++) {
			keys[i] = ht.get(names[i]).toString();
		}
		return keys;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-9-12 17:09:04)
	 */
	public String[] getResultNames() {
		String[] cols = getNames();
		Vector v = new Vector();
		if (cols == null || cols.length == 0) {
			return null;
		}
		for (int i = 0; i < cols.length; i++) {
			if (!cols[i].equals("自由项")) {
				v.addElement(cols[i]);
			}
		}
		String[] names = new String[v.size()];
		v.copyInto(names);
		return names;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-9-24 17:27:37)
	 */
	public int getRowIndexOfAttr(String attr) {
		int row = -1;
		String[] names = getConditionNames();
		for (int i = 0; i < names.length; i++) {
			if (names[i].equals(attr)) {
				row = i;
				break;
			}
		}
		return row;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-9-13 16:28:26)
	 */
	public String[] getSubtotalCols() {
		String[] cols = null;
		Vector v = new Vector();
		GroupVO[] groupVO = getVos();
		if (groupVO != null) {
			for (int i = 0; i < groupVO.length; i++) {
				if (groupVO[i].getAttributeValue("subtotal") != null
						&& groupVO[i].getAttributeValue("subtotal").toString()
								.equals("Y")) {
					v
							.addElement(groupVO[i].getAttributeValue("key")
									.toString());
				}
			}
		}

		if (v.size() > 0) {
			cols = new String[v.size()];
			v.copyInto(cols);
		}
		return cols;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-9-12 17:09:04)
	 */
	public String[] getValuesByCol(String col) {
		GroupVO[] vos = getVos();
		if (vos == null)
			return null;
		String[] keys = new String[vos.length];
		for (int i = 0; i < vos.length; i++) {
			keys[i] = vos[i].getAttributeValue(col).toString();
		}
		return keys;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-9-12 17:09:04)
	 */
	public GroupVO[] getVos() {

		getGroupPanel().updateValue();

		GroupVO[] vos = (GroupVO[]) getGroupPanel().getBillModel()
				.getBodyValueVOs(GroupVO.class.getName());
		GroupVO[] results = null;
		Vector v = new Vector();
		if (vos == null || (vos != null && vos.length == 0)) {
			return results;
		}

		for (int i = 0; i < vos.length; i++) {
			if (vos[i].getAttributeValue("order") != null
					&& vos[i].getAttributeValue("order").toString().length() > 0) {
				v.addElement(vos[i]);
			}
		}

		if (v.size() > 0) {
			results = new GroupVO[v.size()];
			v.copyInto(results);
		}

		sort(results, "order");

		return results;
	}

	/**
	 * 每当部件抛出异常时被调用
	 * 
	 * @param exception
	 *            java.lang.Throwable
	 */
	public void handleException(java.lang.Throwable exception) {

		/* 除去下列各行的注释，以将未捕捉到的异常打印至 stdout。 */
		// nc.vo.scm.pub.SCMEnv.out("--------- 未捕捉到的异常 ---------");
		nc.vo.scm.pub.SCMEnv.out(exception);
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-9-24 21:07:27)
	 */
	public void initHash() {
		for (int i = 0; i < getConditionNames().length; i++) {
			ht.put(getConditionNames()[i], getConditionKeys()[i]);
		}
	}

	/**
	 * 初始化类。
	 */
	/* 警告：此方法将重新生成。 */
	private void initialize() {
		try {
			setName("GroupPanel");
			setPreferredSize(new java.awt.Dimension(536, 377));
			add(getGroupPanel(), "Center");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-11-23 9:54:32)
	 */
	public void initPanel() {
		try {
			biGroups = new BillItem[groupNames.length];
			for (int i = 0; i < groupNames.length; i++) {
				biGroups[i] = new BillItem();
				biGroups[i].setName(groupNames[i]);
				biGroups[i].setKey(groupKeys[i]);
				biGroups[i].setPos(BillItem.BODY);
				biGroups[i].setShow(groupShow[i]);
				biGroups[i].setWidth(115);
				// biGroups[i].setWidth(153);
				biGroups[i].setDataType(groupDataType[i]);
				biGroups[i].setRefType("");
				biGroups[i].setEdit(true);
				biGroups[i].setLoadFormula(groupLoad[i]);
			}
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out(e);
		}
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-9-24 17:27:37)
	 */
	public boolean isGroupByFreeItem() {
		boolean flag = false;
		String[] names = getNames();
		if (names == null && names.length == 0) {
			return false;
		}
		for (int i = 0; i < names.length; i++) {
			if (names[i].equals("自由项")) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-9-12 16:52:03)
	 */
	public void loadData() {
		for (int i = 0; i < getConditionKeys().length; i++) {
			getGroupPanel().addLine();
			getGroupPanel().setBodyValueAt(getConditionNames()[i], i,
					"condition");
			getGroupPanel().setBodyValueAt(getConditionKeys()[i], i, "key");
		}
		// 设置默认值
		for (int i = 0; i < getGroupPanel().getRowCount(); i++) {
			getGroupPanel().setBodyValueAt(new Integer(i + 1).toString(), i,
					"order");
			getGroupPanel().setBodyValueAt("Y", i, "select");
		}

		getGroupPanel().updateValue();
		getGroupPanel().updateUI();
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-9-12 16:39:30)
	 */
	public void setCondition(String[] conditionKey, String[] conditionName) {
		this.conditionKey = conditionKey;
		this.conditionName = conditionName;
	}

	/**
	 * 此处插入方法说明。
	 * 创建日期：(2002-9-13 14:02:32)
	 */
	public void setSubtotalShow(boolean flag) {
		if (flag) {
			getGroupPanel().showBodyTableCol("subtotal");
		} else {
			getGroupPanel().hideBodyTableCol("subtotal");
		}
	}

	/**
	 * 冒泡排序。
	 * 创建日期：(2002-4-30 14:43:58)
	 */
	public void sort(GroupVO[] vos, String col) {
		if (vos != null && vos.length > 0) {
			for (int i = 0; i < vos.length; i++) {
				for (int j = i + 1; j < vos.length; j++) {
					GroupVO vo1 = (GroupVO) vos[i].clone();
					GroupVO vo2 = (GroupVO) vos[j].clone();
					String s1 = vos[i].getAttributeValue(col).toString();
					String s2 = vos[j].getAttributeValue(col).toString();
					if (s1.compareTo(s2) > 0) {
						//交换位置
						vos[i] = vo2;
						vos[j] = vo1;
					}
				}
			}
		}
	}
}