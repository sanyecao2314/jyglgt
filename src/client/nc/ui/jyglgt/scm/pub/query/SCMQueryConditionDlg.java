package nc.ui.jyglgt.scm.pub.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

//import nc.itf.uap.rbac.PowerClientService;
//import nc.ui.bd.datapower.DataPowerServ;

import nc.ui.bd.manage.RefCellRender;
import nc.ui.bd.manage.UIRefCellEditor;
import nc.ui.jyglgt.scm.ic.freeitem.FreeItemRefPane;
import nc.ui.jyglgt.scm.ic.measurerate.InvMeasRate;
import nc.ui.jyglgt.scm.pub.sourceref.BillRefModeSelPanel;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UIScrollPane;
import nc.ui.pub.query.EditComponentFacotry;
import nc.ui.querytemplate.filtereditor.INameGenerator;
import nc.ui.querytemplate.operator.IOperatorConstants;
import nc.ui.querytemplate.querytype.IQueryTypeEditor;
import nc.ui.querytemplate.querytype.QueryTypeEditorFactory;
import nc.ui.scm.inter.ic.ILocatorRefPane;
import nc.ui.scm.inter.ic.ILotQueryRef;
import nc.ui.scm.inter.ic.IStatbRefPane;
import nc.ui.scm.pub.query.DataObject;
import nc.ui.scm.service.LocalCallService;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.query.ConditionVO;
import nc.vo.pub.query.QueryConditionVO;
import nc.vo.pub.query.RefResultVO;
import nc.vo.querytemplate.querytype.IQueryType;
import nc.vo.querytemplate.querytype.ISubject;
import nc.vo.querytemplate.querytype.QueryTypeInfo;
import nc.vo.scm.ic.bill.FreeVO;
import nc.vo.scm.ic.bill.InvVO;
import nc.vo.scm.ic.bill.WhVO;
import nc.vo.scm.print.PrintConst;
import nc.vo.scm.pu.VariableConst;
import nc.vo.scm.pub.SCMEnv;
import nc.vo.scm.pub.query.DataPowerHelp;
import nc.vo.scm.service.ServcallVO;

/**
 * 此处插入类型说明。 继承的查询条件对话框，加入了置自由项参照，置下拉框，自动清零,初始化系统参照功能
 * 
 * 支持权限， 打印次数PANEL，
 * 
 * 创建日期：(2001-7-25 10:25:10)
 * 
 * @author：仲瑞庆
 */

public class SCMQueryConditionDlg extends nc.ui.pub.query.QueryConditionClient {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UIPanel ivjUIPanel = null;

	private javax.swing.JTextField ivjtfUnitCode = null;

	ClientEnvironment env = null;

	// 自由项值的表，key为自由项的字段，值为对应的自由项VO
	// private Hashtable m_htFreeVO= new Hashtable();
	// 自由项与存货的对照表，key为自由项字段，值为存货字段
	private Hashtable<String, String> m_htFreeItemInv = new Hashtable<String, String>();

	// 存货的表，key为存货字段，值为包含该存货的空的自由项VO的存货VO
	private Hashtable<String, InvVO> m_htInvVO = new Hashtable<String, InvVO>();

	// 自动清值的表，key为关键字段，值为对应该字段的其他字段的一个数组
	private Hashtable<String, String[]> m_htAutoClear = new Hashtable<String, String[]>();

	// 仓库的表，key为仓库字段，值为仓库的对应的仓库VO
	private Hashtable<String, WhVO> m_htWhVO = new Hashtable<String, WhVO>();

	// 对辅计量的考虑而添加
	private InvMeasRate m_voInvMeas = new nc.ui.jyglgt.scm.ic.measurerate.InvMeasRate();

	// 辅计量对应表
	private Hashtable<String, String[]> m_htAstCorpInv = new Hashtable<String, String[]>();

	// 参照与公司的关系
	// kp.pk_corp,{pk_calbody,cwarehouseid}
	private HashMap<String, String[]> m_htCorpRef = new HashMap<String, String[]>();

	// pk_calbody,cwarehouseid
	private ArrayList<String> m_alpower = new ArrayList<String>();

	// pk_calbody,kp.pk_corp
	// cwarehouseid,pk_corp
	private HashMap<String, String> m_hmRefCorp = new HashMap<String, String>();

	// 可以追加到查询VO[]后
	private ConditionVO[] m_dataPowerScmVOs = null;

	private HashMap<String, String> m_htRefField = null;

	// 系统参照初始化where子句
	private ArrayList<ArrayList<String>> m_alRefInitWhereClause = new ArrayList<ArrayList<String>>();

	// m_alRefInitWhereClause ELEMENT DEFINITION
	private final int RI_FIELD_CODE = 0;

	private final int RI_REF_NAME = 1;

	private final int RI_WHERE_CLAUSE = 2;

	private final int RI_CHECK_FIELD_CODE = 3;

	// 对统计结构的考虑而添加
	private Hashtable<String, String> m_htStatBtoH = new Hashtable<String, String>();

	private Hashtable<String, String> m_htStatHtoCorp = new Hashtable<String, String>();

	// 对批次号的考虑而添加
	private Hashtable<String, String> m_htLotInv = new Hashtable<String, String>();

	// 当下拉框修改，更改某一字段的参照
	private ArrayList<ArrayList<Object>> m_alComboxToRef = new ArrayList<ArrayList<Object>>();

	// 有关公司参照的初始化
	private ArrayList<ArrayList<Serializable>> m_alCorpRef = new ArrayList<ArrayList<Serializable>>();

	// 有权限的公司值
	// kp.pk_corp,new ArrayList{1001,1002,1003}
	private HashMap<String, ArrayList> m_hmCorpData = new HashMap<String, ArrayList>();

	private String[] m_sdefaultcorps = null;

	// 有关货位参照的指定记录
	private ArrayList<ArrayList<String>> m_alLocatorRef = new ArrayList<ArrayList<String>>();

	// private String m_sCorpID= null; //公司ID
	// private String m_sUserID= null; //当前使用者ID
	// private String m_sLogDate= null; //当前登录日期
	// private String m_sUserName= null; //当前使用者名称

	// 默认的公司
	private String m_sDefaultCorpID = "";

	private String m_sCorpFieldCode = "";

	// 初始化多限制参照用
	private Hashtable<String, ArrayList<Object>> m_htInitMultiRef = new Hashtable<String, ArrayList<Object>>();

	// 打印状态PANEL
	private QryPrintStatusPanel m_QryPrintStatusPanel;

	private BillRefModeSelPanel m_billRefModeSelPanel;

	private boolean m_isShowBillRefModeSelPanel = false;

	// 是否显示打印状态PANEL from 3.1
	private boolean m_isShowPrintStatusPanel = false;

	// 是否返回 数据权限 SQL。存货核算产品中，部分节点不支持权限。default is supported
	private boolean m_isDataPowerSqlReturned = true;

	/**
	 * @param dataPowerSqlReturned
	 *            the m_isDataPowerSqlReturned to set
	 */
	public void setM_isDataPowerSqlReturned(boolean dataPowerSqlReturned) {
		m_isDataPowerSqlReturned = dataPowerSqlReturned;
	}

	// 与模板返回值无关数据权限条件vo
	private ConditionVO[] m_cCtrTmpDataPowerVOs = null;

	// czp , 2006-10-30, 资源是否启用缓存，如果权限设置有调整，要求用户退出节点重新进入
	// 结构：{key=表名+资源名称+pk_corp, value = 权限是否启用}
	// private static HashMap<String, UFBoolean> m_mapPowerEnableCache = new
	// HashMap<String, UFBoolean>();

	// czp , 2006-10-30, 缓存权限条件子查询，如果权限设置有调整，要求用户退出节点重新进入
	// 结构：{key=表名+资源名称+操作员ID+pk_corp0+pk_corp1+..., value = 权限子串}
	private static HashMap<String, String> m_mapPowerSubSqlCache = new HashMap<String, String>();

	// zc 启用权限管理后保存查询条件出现了多余行
	private boolean saveHistory = false;

	// tianft 2009/09/30 查询模板处理类
	private SCMQueryConditionHandler queryCondHandler = null;

	/**
	 * QueryConditionDlg 构造子注解。
	 */
	public SCMQueryConditionDlg() {
		super();
		setIsWarningWithNoInput(true);
		setSealedDataShow(true);
	}

	/**
	 * QueryConditionDlg 构造子：增加设置权限构造子
	 * 
	 * @param isFixedSet
	 */
	public SCMQueryConditionDlg(boolean isFixedSet) {
		super(isFixedSet);
		setIsWarningWithNoInput(true);
		setSealedDataShow(true);
	}

	/**
	 * QueryConditionDlg 构造子注解。
	 * 
	 * @param parent
	 *            java.awt.Container
	 */
	public SCMQueryConditionDlg(java.awt.Container parent) {
		super(parent);
		setIsWarningWithNoInput(true);
		setSealedDataShow(true);
		this.hideNormal();
	}

	/**
	 * QueryConditionDlg 构造子注解。
	 * 
	 * @param parent
	 *            java.awt.Container
	 * @param title
	 *            java.lang.String
	 */
	public SCMQueryConditionDlg(java.awt.Container parent, String title) {
		super(parent, title);
		setIsWarningWithNoInput(true);
		setSealedDataShow(true);
	}

	/**
	 * QueryConditionDlg 构造子注解。
	 * 
	 * @param parent
	 *            java.awt.Frame
	 */
	public SCMQueryConditionDlg(java.awt.Frame parent) {
		super(parent);
		setIsWarningWithNoInput(true);
		setSealedDataShow(true);
	}

	/**
	 * QueryConditionDlg 构造子注解。
	 * 
	 * @param parent
	 *            java.awt.Frame
	 * @param title
	 *            java.lang.String
	 */
	public SCMQueryConditionDlg(java.awt.Frame parent, String title) {
		super(parent, title);
		setIsWarningWithNoInput(true);
		setSealedDataShow(true);
	}

	/**
	 * 创建者：仲瑞庆 功能：加入当自由项修改时，当存货修改时，检查自动清零 参数： 返回： 例外： 日期：(2001-8-12 18:02:51)
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @param editor
	 *            javax.swing.table.TableCellEditor
	 * @param row
	 *            int
	 * @param col
	 *            int 修改：余大英 将rrvo[row]替换为rrvo[index]
	 */
	public void afterEdit(TableCellEditor editor, int row, int col) {
		// 临时加入
		// if (true) {
		// super.afterEdit(editor, row, col);
		// }
		// 以后删除
		// m_immobilityRows

		int index1 = getIndexes(row - getImmobilityRows());
		int index = row - getImmobilityRows();
		// row=row-getImmobilityRows();
		// 2002-10-27.01 wnj add below check;
		if (index1 < 0 || index1 >= getConditionDatas().length) {
			nc.vo.scm.pub.SCMEnv.out("qry cond err.");
			return;
		}

		QueryConditionVO qcvo = getConditionDatas()[index1];
		if ((col == COLVALUE) && (editor instanceof UIFreeItemCellEditor)) {
			// 写入自由项VO的值
			Object temp = ((UIFreeItemCellEditor) editor).getComponent();
			if (temp instanceof FreeItemRefPane) {
				FreeItemRefPane pane = (FreeItemRefPane) temp;
				FreeVO fvo = pane.getFreeVO();
				// String sFieldCode=
				// getConditionDatas()[index].getFieldCode();
				// m_htFreeVO.put(sFieldCode, fvo);
				// 于结果VO中写入PK,Code,Name均为vfree0
				RefResultVO[] rrvo = getValueRefResults();
				if (rrvo[index] == null)
					rrvo[index] = new RefResultVO();
				rrvo[index].setRefCode(fvo.getVfree0().trim());
				rrvo[index].setRefName(fvo.getVfree0().trim());
				rrvo[index].setRefPK(fvo.getVfree0().trim());
				rrvo[index].setRefObj(fvo);
				setValueRefResults(rrvo);
				// 于条件VO中写入对应项的值
				// QueryConditionVO[] qcvo= getConditionDatas();
				// qcvo[index].setValue(fvo.getVfree0().trim());
				// setConditionDatas(qcvo);
				// 置入界面
				getUITabInput().setValueAt(fvo.getVfree0().trim(), row, col);
				// 置入保存的InvVO
				String sFreeItemCode = qcvo.getFieldCode().trim();
				if (m_htFreeItemInv.containsKey(sFreeItemCode)) {
					String sInvIDCode = m_htFreeItemInv.get(sFreeItemCode)
							.toString().trim();
					String sInvID = "";
					sInvID = getPKbyFieldCode(sInvIDCode);
					if (m_htInvVO.containsKey(sInvID)) {
						InvVO ivo = m_htInvVO.get(sInvID);
						ivo.setFreeItemVO(fvo);
						m_htInvVO.put(sInvID, ivo);
					}
				}
			}

		} else if ((col == COLVALUE)
				&& (editor instanceof UIComboBoxCellEditor)) {
			// 对下拉框

			Object temp = ((UIComboBoxCellEditor) editor).getComponent();
			if (temp instanceof UIComboBox) {
				UIComboBox pane = (UIComboBox) temp;
				DataObject doValue = (DataObject) pane.getItemAt(pane
						.getSelectedIndex());
				// 于结果VO中写入PK,Code,Name均为vfree0
				RefResultVO[] rrvo = getValueRefResults();
				if (rrvo[index] == null)
					rrvo[index] = new RefResultVO();
				rrvo[index].setRefCode(doValue.getID().toString().trim());
				rrvo[index].setRefName(doValue.getName().toString().trim());
				rrvo[index].setRefPK(doValue.getID().toString().trim());
				// rrvo[index].setRefObj(doValue);
				setValueRefResults(rrvo);
				// 置入界面
				getUITabInput().setValueAt(doValue.getName().toString().trim(),
						row, col);
			}

		} else if ((col == COLVALUE) && (editor instanceof UIRefCellEditor)) {
			super.afterEdit(editor, row, col);
			Object temp = ((UIRefCellEditor) editor).getComponent();
			if (temp instanceof UIRefPane) {
				UIRefPane pane = (UIRefPane) temp;
				String sRefNodeName = pane.getRefNodeName().trim();

				// if(m_htCorpRef.containsKey(qcvo.getFieldCode())){
				// String scorp=(String)m_htCorpRef.get(qcvo.getFieldCode());
				// pane.setPk_corp((String)m_htCorpValue.get(scorp));

				// }

				if (sRefNodeName.equals("公司目录")) {
					if (qcvo.getFieldCode() != null) {
						if (m_htCorpRef.containsKey(qcvo.getFieldCode())) {
							String[] sRefs = m_htCorpRef.get(qcvo
									.getFieldCode());
							for (int kk = 0; kk < sRefs.length; kk++) {
								Object oRef = getValueRefObjectByFieldCode(sRefs[kk]);
								if (oRef != null && oRef instanceof UIRefPane) {
									// nc.vo.scm.pub.SCMEnv.out("afterEdit"+qcvo.getFieldCode()+"-"+sRefs[kk]+":"+((UIRefPane)oRef).getRefModel()+((UIRefPane)oRef).getRefModel().getPk_corp());

									((UIRefPane) oRef).getRefModel()
											.setPk_corp(pane.getRefPK());

									// nc.vo.scm.pub.SCMEnv.out("afterEdit"+qcvo.getFieldCode()+":"+pane.getRefPK());

									// nc.vo.scm.pub.SCMEnv.out("afterEdit"+qcvo.getFieldCode()+"-"+sRefs[kk]+":"+((UIRefPane)oRef).getRefModel()+((UIRefPane)oRef).getRefModel().getPk_corp());

								}
							}

						}
						// m_htCorpValue.put(qcvo.getFieldCode(),pane.getRefPK().trim());

					}
				} else if (sRefNodeName.equals("存货档案")
						|| sRefNodeName.equals("成套件")) {
					if (pane.getRefPK() != null) {
						String sTempID1 = pane.getRefPK().trim();
						String sTempID2 = null;
						ArrayList<String> alIDs = new ArrayList<String>();
						alIDs.add(sTempID2);
						alIDs.add(sTempID1);
						alIDs.add(getCurUserID());
						alIDs.add(null);
						try {
							// 当为存货修改时，生成InvVO,以备传给自由项参照
							// 当为存货修改时，生成InvVO,以备传给自由项参照
							ServcallVO scd = new ServcallVO();
							scd
									.setBeanName("nc.bs.ic.pub.bill.GeneralBillImpl");
							scd.setMethodName("queryInfo");
							scd.setParameter(new Object[] { new Integer(0),
									alIDs });
							scd.setParameterTypes(new Class[] { Integer.class,
									ArrayList.class });
							nc.vo.scm.ic.bill.InvVO voInv = (InvVO) (LocalCallService
									.callService("ic", new ServcallVO[] { scd })[0]);
							// nc.vo.ic.pub.bill.InvVO voInv =
							// (InvVO)
							// nc.ui.ic.ic221.SpecialHBO_Client.queryInfo(new
							// Integer(0), alIDs);
							m_htInvVO.put(sTempID1, voInv);
						} catch (Exception e) {
						}
					}
				} else if (sRefNodeName.equals("会计期间")) {
					if (pane.getRefPK() != null) {
						// 于结果VO中写入PK,Code,Name
						RefResultVO[] rrvo = getValueRefResults();
						if (rrvo[index] == null)
							rrvo[index] = new RefResultVO();
						rrvo[index].setRefCode(pane.getRefCode().trim());
						rrvo[index].setRefName(pane.getRefName().trim());
						// pane.getRefName().trim() + "年-" +
						// pane.getRefCode().trim() + "月");
						rrvo[index].setRefPK(pane.getRefPK().trim());
						rrvo[index].setRefObj(pane
								.getRefValue("bd_accperiodmonth.begindate"));
						setValueRefResults(rrvo);
						// 置入界面
						getUITabInput().setValueAt(pane.getRefName().trim(),
						// pane.getRefName().trim() + "年-" +
								// pane.getRefCode().trim() + "月",
								row, col);
					}
				} else if (sRefNodeName.equals("仓库档案")) {
					if (pane.getRefPK() != null) {
						String sTempID1 = pane.getRefPK().trim();
						String sTempID2 = null;
						ArrayList<String> alIDs = new ArrayList<String>();
						alIDs.add(sTempID2);
						alIDs.add(sTempID1);
						alIDs.add(getCurUserID());
						alIDs.add(null);
						try {
							// 当为仓库修改时，生成WhVO,以备传给货位参照
							ServcallVO scd = new ServcallVO();
							scd.setBeanName("nc.bs.ic.ic221.SpecialHBO");
							scd.setMethodName("queryInfo");
							scd.setParameter(new Object[] { new Integer(1/** QryInfoConst.WH */
							), sTempID1 });
							scd.setParameterTypes(new Class[] { Integer.class,
									String.class });
							WhVO voWh = (WhVO) (LocalCallService.callService(
									"ic", new ServcallVO[] { scd })[0]);

							// WhVO voWh =
							// (WhVO)
							// nc.ui.ic.ic221.SpecialHBO_Client.queryInfo(
							// new Integer(QryInfoConst.WH),
							// sTempID1);
							m_htWhVO.put(sTempID1, voWh);
						} catch (Exception e) {
						}
					}
				}
			}
			if (qcvo.getDataType().intValue() == 300) {
				// 对统计结构
			} else if (qcvo.getDataType().intValue() == 350) {
				// 对统计范围

				IStatbRefPane pane = (IStatbRefPane) temp;

				// 于结果VO中写入PK,Code,Name均为vfree0
				RefResultVO[] rrvo = getValueRefResults();
				if (rrvo[index] == null)
					rrvo[index] = new RefResultVO();
				rrvo[index].setRefCode(pane.getStatcode().trim());
				rrvo[index].setRefName(pane.getStatcode().trim());
				rrvo[index].setRefPK(pane.getStatbid().trim());
				// rrvo[index].setRefObj(null);
				setValueRefResults(rrvo);
				// 置入界面
				getUITabInput().setValueAt(
						pane.getStatcode().toString().trim(), row, col);
			} else if (qcvo.getDataType().intValue() == 400) {
				// 对批次号
				UIRefPane pane = (UIRefPane) temp;
				// LotQueryRef pane = (LotQueryRef) temp;
				// 于结果VO中写入PK,Code,Name均为vfree0
				RefResultVO[] rrvo = getValueRefResults();
				if (rrvo[index] == null)
					rrvo[index] = new RefResultVO();
				rrvo[index].setRefCode(pane.getText().trim());
				rrvo[index].setRefName(pane.getText().trim());
				rrvo[index].setRefPK(pane.getText().trim());
				// rrvo[index].setRefObj(null);
				setValueRefResults(rrvo);
				// 置入界面
				getUITabInput().setValueAt(pane.getText().trim(), row, col);
			}
		} else {
			super.afterEdit(editor, row, col);
		}
		// 清对应值
		if (col == COLVALUE) {
			String sFieldCode = qcvo.getFieldCode().trim();
			autoClear(sFieldCode, row, col);
		}
	}

	/**
	 * 创建者：仲瑞庆 功能：依关键字段请其他字段的值 参数： 返回： 例外： 日期：(2001-8-13 14:29:41)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	protected void autoClear(String sKey, int row, int col) {
		if (m_htAutoClear.containsKey(sKey)) {
			String[] sOtherFieldCodes = m_htAutoClear.get(sKey);
			RefResultVO[] rrvo = getValueRefResults();
			QueryConditionVO[] qcvos = getConditionDatas();
			// 清值
			for (int i = 0; i < sOtherFieldCodes.length; i++) {
				String sFieldCodeClear = sOtherFieldCodes[i].trim();
				for (int j = 0; j < getUITabInput().getRowCount(); j++) {
					int index = getIndexes(j - getImmobilityRows());
					// 2002-10-27.01 wnj add below check.
					if (index < 0 || index >= qcvos.length) {
						nc.vo.scm.pub.SCMEnv.out("qry cond err.");
						continue;
					}
					if (qcvos[index].getFieldCode().trim().equals(
							sFieldCodeClear)) {
						// 查到对应的结果VO，清掉PK，Code, Name
						if (rrvo[j] != null) {
							rrvo[j].setRefCode("");
							rrvo[j].setRefName("");
							rrvo[j].setRefPK("");
						}
						// 清界面显示值
						getUITabInput().setValueAt(null, j, col);
						break;
					}
				}
			}
		}
	}

	/**
	 * 为权限参照加上 is Null条件
	 * 
	 * @param tableName
	 * @param tabelShowName
	 * @param pk_Corp
	 */
	protected void addNullContionDataPower(String refTableName,
			String sFieldCode, String pk_Corp) {
	}

	/**
	 * 取得自动增加的基础数据权限条件VO数组 创建日期:(2001-4-25 16:50:17)
	 * 
	 * @return nc.vo.pub.query.ConditionVO[]
	 */
	// public nc.vo.pub.query.ConditionVO[] getDataPowerVOs() {
	// /*
	// if(m_dataPowerVOs_this != null){
	// return m_dataPowerVOs_this;
	// }
	// */
	// return super.getDataPowerVOs();
	// }
	/**
	 * 返回用户未设条件的参照项目所对应的基础数据权限条件VO数组。 调用此方法时 创建日期：(2003-05-29 9:55:15) 57
	 * 去掉此方法用uap的方法替代
	 * 
	 * @return nc.vo.pub.query.ConditionVO[]
	 */
	@SuppressWarnings("unused")
	public void calculateDataPowerVOs() {
		if (m_htCorpRef.size() == 0
				&& (m_cCtrTmpDataPowerVOs == null || m_cCtrTmpDataPowerVOs.length == 0)) {

			/*
			 * by czp, 2006-11-04，原因：
			 * 
			 * 调用super.calculateDataPowerVOs()权限处理,再在super.getConditionVO()[this.getConditionVO()调用]返回错误的数据权限查询VO
			 * 
			 * super.calculateDataPowerVOs()错误查询条件VO描述：子串值只有“id in 权限串”，未拼接id is
			 * null,不符合供应链业务要求
			 *  // 修改父类没有计算IS NULL的情况，否则这种条件就查询不出来(德美)
			 * super.calculateDataPowerVOs();
			 */
			return;
		}

		HashMap<String, String[]> hmRefCorp = getRefCorp();

		HashMap<String, QueryConditionVO> hmPoweredConditionVO = new HashMap<String, QueryConditionVO>();// 设置了权限的条件VO：WNJ2005-04-27

		ArrayList<String> alNullValueCondVO = new ArrayList<String>();// 保存没有录入值的条件VO：WNJ2005-04-27

		m_dataPowerScmVOs = null;

		String strDef = null; // 模版中的项目标识（字段 or（表+字段））

		// 得到所有模版中基础数据参照类型并使用数据权限的项目
		for (int i = 0; i < getConditionDatas().length; i++) {
			QueryConditionVO vo = getConditionDatas()[i];
			if (isDataPwr(vo)) {

				// 穿透式查询
				strDef = getFldCodeByPower(vo);
				// 为了知道权限参照的返回类型：PK,code,name
				hmPoweredConditionVO.put(strDef, vo);
				hmPoweredConditionVO.put(getFldCodeByPower(vo), vo);

			}
		}

		// 从中过滤掉用户界面设置了条件的项目
		ConditionVO[] vos = getConditionVO();
		if (vos != null) {
			String strUsed = null;
			HashMap<String, ConditionVO> htUsed = new HashMap<String, ConditionVO>();
			ConditionVO usedVO = null;
			int iLen = vos.length;
			for (int i = 0; i < iLen; i++) {
				usedVO = vos[i];
				strUsed = usedVO.getFieldCode();
				QueryConditionVO voQuery = hmPoweredConditionVO.get(strUsed);
				// 防止用户记住了存货编码等，绕过权限控制

				if (voQuery != null
						&& voQuery.getIfAutoCheck() != null
						&& voQuery.getIfAutoCheck().booleanValue()
						&& (IOperatorConstants.EQ.equalsIgnoreCase(usedVO
								.getOperaCode())
								|| IOperatorConstants.EQIC
										.equalsIgnoreCase(usedVO.getOperaCode())
								|| IOperatorConstants.IN
										.equalsIgnoreCase(usedVO.getOperaCode()) || IOperatorConstants.INIC
								.equalsIgnoreCase(usedVO.getOperaCode()))) {
					htUsed.put(strUsed, usedVO);
				}

				// 重新置公司的值
				if (m_htCorpRef.containsKey(strUsed)) {
					String[] sps = m_htCorpRef.get(strUsed);
					for (int j = 0; j < sps.length; j++) {
						hmRefCorp.put(sps[j], new String[] { usedVO
								.getRefResult().getRefPK() });
					}
				}

			}
			for (int i = 0; i < m_alpower.size(); i++) {
				strUsed = m_alpower.get(i);

				// 权限参照没有设置条件
				if (!htUsed.containsKey(strUsed))
					alNullValueCondVO.add(strUsed);

			}
		}

		// 为剩余项目查询基础数据权限
		if (alNullValueCondVO.size() > 0) {

			Vector<ConditionVO> vecVO = new Vector<ConditionVO>();
			String[] fieldcodes = alNullValueCondVO.toArray(new String[0]);
			Map<String, String> mapSQL =
          getSubSqlForMutiCorpCtrl(hmRefCorp, hmPoweredConditionVO,
              fieldcodes);
			// 首先初始化启用权限信息 ,可能会初始化一些多余的数据
			// for (String sCorp : vecCorps) {
			// initUsedDataPowerInfo(vecTableNames, vecRefNames, sCorp);
			// }

			for (int i = 0; i < alNullValueCondVO.size(); i++) {
				String fieldcode = alNullValueCondVO.get(i);

				QueryConditionVO voQuery = hmPoweredConditionVO.get(fieldcode);
				if (voQuery == null) {
					nc.vo.scm.pub.SCMEnv.out("@@@@没有QueryConditionVO::"
							+ fieldcode);
					continue;
				}

				String sRefNodeName = voQuery.getConsultCode();
				String insql = mapSQL.get(sRefNodeName);

				appendPowerCons(vecVO, voQuery, insql);
			}
			// 组织带权限的查询条件VO并返回
			if (vecVO.size() > 0) {
				m_dataPowerScmVOs = new ConditionVO[vecVO.size()];
				vecVO.copyInto(m_dataPowerScmVOs);
			} 
		}

	}

  private HashMap<String, String[]> getRefCorp() {
    HashMap<String, String[]> hmRefCorp = new HashMap<String, String[]>();

		// 缺省参照的公司是当前公司
		//
		for (int i = 0; i < m_alpower.size(); i++) {
			// 找到参照的公司域
			String corpfieldcode = m_hmRefCorp.get(m_alpower.get(i));
			if (corpfieldcode != null) {
				if (m_hmCorpData.get(corpfieldcode) != null) {
					ArrayList alcorpvalue = m_hmCorpData.get(corpfieldcode);
					String[] scorpvalue = new String[alcorpvalue.size()];
					alcorpvalue.toArray(scorpvalue);

					hmRefCorp.put(m_alpower.get(i), scorpvalue);

				} else {
					hmRefCorp.put(m_alpower.get(i), m_sdefaultcorps);
				}

			}
		}
    return hmRefCorp;
  }
//	
//	 /**
//     * 取得自动增加的基础数据权限条件VO数组 创建日期:(2001-4-25 16:50:17)
//     * 
//     * @return nc.vo.pub.query.ConditionVO[]
//     */
//    public nc.vo.pub.query.ConditionVO[] getDataPowerVOs() {
//        return m_dataPowerScmVOs;
//    }

  private Map<String, String> getSubSqlForMutiCorpCtrl(
      HashMap<String, String[]> hmRefCorp,
      HashMap<String, QueryConditionVO> hmPoweredConditionVO,
      String[] fieldcodes) {
    Vector<String> vecTableNames = new Vector<String>();
    Vector<String> vecRefNames = new Vector<String>();
    Vector<String> vecCorps = new Vector<String>();
    for (int i = 0; i < fieldcodes.length; i++) {
    	String fieldcode = fieldcodes[i];
    	QueryConditionVO voQuery = hmPoweredConditionVO.get(fieldcode);
    	if (voQuery == null) {
    		continue;
    	}
    	String sRefNodeName = voQuery.getConsultCode();
    	String dpTableName = null;
    	try {
    		dpTableName = nc.ui.bd.datapower.DataPowerServ
    				.getRefTableName(sRefNodeName); // 根据参照名称获得基础数据表名
    	} catch (Exception e) {
    		SCMEnv.out(e);
    	}
    	if (dpTableName != null) {
    		vecTableNames.add(dpTableName);
    		vecRefNames.add(sRefNodeName);
    	}
    	String[] corpValues = hmRefCorp.get(getFldCodeByPower(voQuery));
    	if (corpValues != null) {
    		for (String corp : corpValues) {
    			if (!vecCorps.contains(corp)) {
    				vecCorps.add(corp);
    			}
    		}
    	}
    }
    
    String[] tableNames = vecTableNames.toArray(new String[0]);
    String[] refNames = vecRefNames.toArray(new String[0]);
    String[] pk_corps = vecCorps.toArray(new String[0]);
    String userID = getClientEnvironment().getUser().getPrimaryKey();
    Map<String, String> mapSQL = DataPowerHelp.getSubSqlForMutilCorpCtrl(tableNames, refNames, userID, pk_corps);
    return mapSQL;
  }

	/**
	 * 作者：余大英 创建日期：(2001-8-29 19:22:04)
	 * 
	 * 
	 * 
	 * @param voCons
	 *            nc.vo.pub.query.ConditionVO[]
	 * @param strKeys
	 *            java.lang.String[]
	 * @param bmust
	 *            boolean
	 * @exception nc.vo.pub.BusinessException
	 *                异常说明。
	 */
	public void checkAllHaveOrNot(ConditionVO[] voCons, String[] strKeys)
			throws nc.vo.pub.BusinessException {

		if (strKeys == null)
			return;

		if (voCons == null || voCons.length < 1)
			throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
					.getInstance().getStrByID("scmpub", "UPPscmpub-000162")/*
																			 * @res
																			 * "没有查询条件！"
																			 */);

		// java.util.ArrayList alCon = getConVOGroup(voConditions);

		// for (int j = 0; j < alCon.size(); j++) {
		// ConditionVO[] voCons = (ConditionVO[]) alCon.get(j);

		java.util.Hashtable<String, String> htKey = new java.util.Hashtable<String, String>();
		StringBuffer sMsg = new StringBuffer();
		for (int i = 0; i < strKeys.length; i++) {
			if (!htKey.containsKey(strKeys[i])) {
				htKey.put(strKeys[i], strKeys[i]);
				sMsg.append(getNamebyFieldCode(strKeys[i]) + ",");
			}

		}

		for (int i = 0; i < voCons.length; i++) {
			String sFieldCode = voCons[i].getFieldCode();
			if (htKey.containsKey(sFieldCode))
				htKey.remove(sFieldCode);

		}

		// if (bmust) {
		if (htKey.size() != 0 && htKey.size() != strKeys.length)
			throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
					.getInstance().getStrByID("scmpub", "UPPscmpub-000163")/*
																			 * @res
																			 * "下列项或全为空或全不为空："
																			 */

					+ sMsg);

		// } else {
		// if (htKey.size() != strKeys.length)
		// throw new nc.vo.pub.BusinessException("下列项不能填：" + sMsg);
		// }

		// }
		return;

	}

	/**
	 * 作者：余大英 创建日期：(2001-7-12 12:58:52) 检查括号配对
	 * 
	 * @param table
	 *            nc.ui.pub.beans.UITable
	 */
	public void checkBracket(ConditionVO[] conditions)
			throws nc.vo.pub.BusinessException {
		int left = 0;
		int right = 0;
		String sMsg = "";

		for (int i = 0; i < conditions.length; i++)
			if ((conditions[i].getValue() != null)
					&& (conditions[i].getValue().toString().trim().length() > 0)) {

				if (!conditions[i].getNoLeft())
					left++;
				if (!conditions[i].getNoRight())
					right++;

			}
		if (left != right) {
			sMsg = nc.ui.ml.NCLangRes.getInstance().getStrByID("scmpub",
					"UPPscmpub-000164")/* @res "括号不匹配！" */;
			throw new nc.vo.pub.BusinessException(sMsg);
		}

		return;
	}

	/**
	 * 创建日期：(2003-11-20 11:55:13) 作者：仲瑞庆 参数： 返回： 说明：
	 * 
	 * @return java.lang.String
	 * @param conditions
	 *            nc.vo.pub.query.ConditionVO[]
	 */
	public String checkCondition(ConditionVO[] conditions) {
		String sReturnError = super.checkCondition(conditions);
		if ((sReturnError != null) && (sReturnError.trim().length() != 0)) {
			return sReturnError;
		}

		if (conditions != null && conditions.length > 1) {
			HashMap<String, ConditionVO> hm = new HashMap<String, ConditionVO>();// field_code
			ConditionVO voPre = null;
			for (int i = 1; i < conditions.length; i++) {

				// 或者
				if (!conditions[i].getLogic()) {

					if (!hm.containsKey(conditions[i].getFieldCode())) {
						voPre = conditions[i - 1];
						hm.put(voPre.getFieldCode(), voPre);
					} else
						voPre = hm.get(conditions[i].getFieldCode());

					if (!conditions[i - 1].getFieldCode().equals(
							conditions[i].getFieldCode())) {
						sReturnError = "只有相同的查询项目可以使用'或者'!";
						break;
					}

					if (voPre.getNoLeft() || !voPre.getNoRight()) {
						sReturnError = "请在第一个'" + voPre.getFieldName()
								+ "'的条件外层加左括号,并且去掉右括号!";
						break;
					}

				}
			}
		}

		return sReturnError;

	}

	/**
	 * 作者：余大英 创建日期：(2001-8-29 19:22:04)
	 * 
	 * strKeys中的项都不是用于查询的字段，放在数组的最后
	 * 
	 * @param voCons
	 *            nc.vo.pub.query.ConditionVO[]
	 * @param strKeys
	 *            java.lang.String[]
	 * @param bmust
	 *            boolean
	 * @exception nc.vo.pub.BusinessException
	 *                异常说明。
	 */
	public void checkNotField(ConditionVO[] voCons, String[] strKeys)
			throws nc.vo.pub.BusinessException {

		if (strKeys == null)
			return;

		if (voCons == null || voCons.length < 1)
			throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
					.getInstance().getStrByID("scmpub", "UPPscmpub-000162")/*
																			 * @res
																			 * "没有查询条件！"
																			 */);

		java.util.Hashtable<String, String> htKey = new java.util.Hashtable<String, String>();
		StringBuffer sMsg = new StringBuffer();

		for (int i = 0; i < strKeys.length; i++) {
			if (!htKey.containsKey(strKeys[i])) {
				htKey.put(strKeys[i], strKeys[i]);
				sMsg.append("[" + getNamebyFieldCode(strKeys[i]) + "]");
			}

		}

		int count = -1;
		int i = 0;
		for (i = 0; i < voCons.length; i++) {

			String sFieldCode = voCons[i].getFieldCode();
			if (htKey.containsKey(sFieldCode)) {
				// sMsg.append("<"+voCons[i].getFieldName()+">");
				if (!voCons[i].getNoLeft() || !voCons[i].getNoRight())
					throw new nc.vo.pub.BusinessException(sMsg
							+ nc.ui.ml.NCLangRes.getInstance().getStrByID(
									"scmpub", "UPPscmpub-000165")/*
																	 * @res
																	 * "应没有括号！"
																	 */);

				if (count == -1)
					count = i;
				else {
					if (i == count + 1)
						count = i;
					else
						throw new nc.vo.pub.BusinessException(sMsg
								+ nc.ui.ml.NCLangRes.getInstance().getStrByID(
										"scmpub", "UPPscmpub-000166")/*
																		 * @res
																		 * "应连续放在最后！"
																		 */);
				}

			}

		}

		if (count != -1 && i != count + 1)
			throw new nc.vo.pub.BusinessException(sMsg
					+ nc.ui.ml.NCLangRes.getInstance().getStrByID("scmpub",
							"UPPscmpub-000166")/* @res "应连续放在最后！" */);

		return;

	}

	/**
	 * 作者：余大英 创建日期：(2001-8-29 19:22:04)
	 * 
	 * 
	 * strKeys中的项最多只能出现一次
	 * 
	 * @param voCons
	 *            nc.vo.pub.query.ConditionVO[]
	 * @param strKeys
	 *            java.lang.String[]
	 * @param bmust
	 *            boolean
	 * @exception nc.vo.pub.BusinessException
	 *                异常说明。
	 */
	public void checkOncetime(ConditionVO[] voCons, String[] strKeys)
			throws nc.vo.pub.BusinessException {

		if (strKeys == null)
			return;

		if (voCons == null || voCons.length < 1)
			throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
					.getInstance().getStrByID("scmpub", "UPPscmpub-000162")/*
																			 * @res
																			 * "没有查询条件！"
																			 */);

		// java.util.ArrayList alCon = getConVOGroup(voConditions);

		// for (int j = 0; j < alCon.size(); j++) {

		java.util.Hashtable<String, String> htKey = new java.util.Hashtable<String, String>();
		java.util.Hashtable<String, String> htField = new java.util.Hashtable<String, String>();

		// ConditionVO[] voCons=(ConditionVO[])alCon.get(j);

		StringBuffer sMsg = new StringBuffer();

		for (int i = 0; i < strKeys.length; i++) {
			if (!htKey.containsKey(strKeys[i])) {
				htKey.put(strKeys[i], strKeys[i]);
				sMsg.append("[" + getNamebyFieldCode(strKeys[i]) + "]");
			}

		}

		for (int i = 0; i < voCons.length; i++) {
			String sFieldCode = voCons[i].getFieldCode();
			if (htKey.containsKey(sFieldCode)) {

				if (htField.containsKey(sFieldCode))
					throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
							.getInstance().getStrByID("scmpub",
									"UPPscmpub-000167")/*
														 * @res
														 * "在组条件中下列项最多只能出现一次："
														 */
							+ sMsg);
				else
					htField.put(sFieldCode, sFieldCode);

			}

		}

		// }

		return;

	}

	/**
	 * 作者：余大英 创建日期：(2001-8-29 19:22:04)
	 * 
	 * if(bmust) strKeys中的fieldcode必须出现一项 else strKeys中的fieldcode最多只能出现一项
	 * 
	 * @param voCons
	 *            nc.vo.pub.query.ConditionVO[]
	 * @param strKeys
	 *            java.lang.String[]
	 * @param bmust
	 *            boolean
	 * @exception nc.vo.pub.BusinessException
	 *                异常说明。
	 */
	public void checkOneNotOther(ConditionVO[] voCons, String[] strKeys,
			boolean bmust) throws nc.vo.pub.BusinessException {

		if (strKeys == null)
			return;

		if (voCons == null || voCons.length < 1)
			throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
					.getInstance().getStrByID("scmpub", "UPPscmpub-000162")/*
																			 * @res
																			 * "没有查询条件！"
																			 */);
		// checkOncetime(voCons, strKeys);

		int count = 0;
		java.util.Hashtable<String, String> htKey = new java.util.Hashtable<String, String>();
		java.util.Hashtable<String, String> htField = new java.util.Hashtable<String, String>();
		StringBuffer sMsg = new StringBuffer();

		for (int i = 0; i < strKeys.length; i++) {
			if (!htKey.containsKey(strKeys[i])) {
				htKey.put(strKeys[i], strKeys[i]);
				sMsg.append("[" + getNamebyFieldCode(strKeys[i]) + "]");

			}

		}

		for (int i = 0; i < voCons.length; i++) {
			String sFieldCode = voCons[i].getFieldCode();
			// if (htKey.containsKey(sFieldCode))
			// sMsg.append("<" + voCons[i].getFieldName() + ">");
			if (htKey.containsKey(sFieldCode)
					&& !htField.containsKey(sFieldCode)) {

				count++;
			}
			if (!htField.containsKey(sFieldCode))
				htField.put(sFieldCode, sFieldCode);

		}

		if (bmust) {
			if (count != 1)
				throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
						.getInstance().getStrByID("40083620",
								"UPT40083620-000042")/*
														 * @res "下列项应必填且只能填一项："
														 */
						+ sMsg);
		} else {
			if (count > 1)
				throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
						.getInstance().getStrByID("scmpub", "UPPscmpub-000169")/*
																				 * @res
																				 * "下列项最多只能填一项："
																				 */
						+ sMsg);
		}

		// }

		return;

	}

	/**
	 * 作者：余大英 创建日期：(2001-8-29 19:22:04)
	 * 
	 * if(bmust) strKeys中的fieldcode必须出现一项 else strKeys中的fieldcode最多只能出现一项
	 * 
	 * @param voCons
	 *            nc.vo.pub.query.ConditionVO[]
	 * @param strKeys
	 *            java.lang.String[]
	 * @param bmust
	 *            boolean
	 * @exception nc.vo.pub.BusinessException
	 *                异常说明。
	 */
	public void checkOneNotOther(ConditionVO[] voCons, ArrayList alKeys,
			boolean bmust) throws nc.vo.pub.BusinessException {

		if (alKeys == null)
			return;

		if (voCons == null || voCons.length < 1)
			throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
					.getInstance().getStrByID("scmpub", "UPPscmpub-000162")/*
																			 * @res
																			 * "没有查询条件！"
																			 */);

		int count = 0;
		java.util.Hashtable<String, Integer> htKey = new java.util.Hashtable<String, Integer>();
		java.util.Hashtable<Integer, Integer> htField = new java.util.Hashtable<Integer, Integer>();

		StringBuffer sMsg = new StringBuffer();

		for (int j = 0; j < alKeys.size(); j++) {
			String[] strKeys = (String[]) alKeys.get(j);
			if (strKeys == null)
				continue;
			sMsg.append("[");
			for (int i = 0; i < strKeys.length; i++) {
				if (!htKey.containsKey(strKeys[i])) {
					htKey.put(strKeys[i], new Integer(j));
					sMsg.append(getNamebyFieldCode(strKeys[i]) + ",");
				}

			}
			sMsg.append("]");
		}

		for (int i = 0; i < voCons.length; i++) {
			String sFieldCode = voCons[i].getFieldCode();

			if (htKey.containsKey(sFieldCode)) {
				Integer iGroup = htKey.get(sFieldCode);
				if (!htField.containsKey(iGroup)) {
					String[] strKeys = (String[]) alKeys.get(iGroup.intValue());
					// if (strKeys != null) {
					// sMsg.append("[");
					// for (int j = 0; j < strKeys.length; j++) {
					// sMsg.append(strKeys[j] + ",");

					// }
					// sMsg.append("]");
					// }

					htField.put(iGroup, iGroup);
				}

			}

		}

		if (bmust) {
			if (htField.size() != 1)
				throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
						.getInstance().getStrByID("scmpub", "UPPscmpub-000170")/*
																				 * @res
																				 * "下列组应必填且只能填一组："
																				 */
						+ sMsg);
		} else {
			if (htField.size() > 1)
				throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
						.getInstance().getStrByID("scmpub", "UPPscmpub-000171")/*
																				 * @res
																				 * "下列组最多只能填一组："
																				 */
						+ sMsg);
		}

		// }

		return;

	}

	/**
	 * 
	 * @return
	 */
	protected ClientEnvironment getClientEnvironment() {
		if (env == null) {
			env = ClientEnvironment.getInstance();
		}
		return env;
	}

	/**
	 * 作者：韩卫 创建日期：(2003-6-10 18:18:09) 检查如果A查询条件字段=true 时，B查询字段必须填写
	 * 
	 * sFlagField:逻辑查询条件字段 sFieldMustFillIn： 逻辑字段=true 时必须填写的字段
	 * sFieldMustFillInName：字段名称
	 * 
	 */
	public void checkOneTrueAnotherMustFillin(ConditionVO[] voCons,
			String sFlagField, String sFieldMustFillIn,
			String sFieldMustFillInName) throws nc.vo.pub.BusinessException {

		if (sFlagField == null || sFieldMustFillIn == null
				|| sFieldMustFillInName == null || voCons == null
				|| voCons.length < 1) {
			nc.vo.scm.pub.SCMEnv.out("检查必填查询条件错误！");
			return;
			// throw new nc.vo.pub.BusinessException("没有查询条件！");
		}

		String sFieldCode = null;
		String sFieldValue = null;
		String sFlagFieldValue = null; // 逻辑字段值
		String sFlagFieldName = null; // 逻辑字段名称
		String sFieldMustFillInValue = null; // 被校验字段
		for (int i = 0; i < voCons.length; i++)
			if (voCons[i] != null) {
				sFieldCode = voCons[i].getFieldCode();
				sFieldValue = voCons[i].getValue();
				// 是逻辑查询条件字段sFlagField.？
				if (sFlagField.equalsIgnoreCase(sFieldCode)) {
					sFlagFieldValue = sFieldValue;
					sFlagFieldName = voCons[i].getFieldName();
				} // 是必须填写的字段？
				else if (sFieldMustFillIn.equalsIgnoreCase(sFieldCode)) {
					// 记下值，准备判断
					sFieldMustFillInValue = sFieldValue;
				}
			}
		// 设置为true，并且必须值未填，抛错。
		UFBoolean flag = new UFBoolean(sFlagFieldValue);
		if (flag.booleanValue() // "true".equalsIgnoreCase(sFlagFieldValue)
				&& (sFieldMustFillInValue == null || sFieldMustFillInValue
						.trim().length() == 0))
			throw new nc.vo.pub.BusinessException("‘" + sFlagFieldName
					+ "’值为true时,\n‘" + sFieldMustFillInName + "’不能为空！");

		return;

	}

	/**
	 * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2001-8-23 20:49:16) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return java.lang.String
	 * @param sFieldCode
	 *            java.lang.String
	 */
	protected String[] getConditionbyFieldCode(String sFieldCode) {
		String[] sReturnString = null;
		ConditionVO[] cvos = getConditionVOsByFieldCode(sFieldCode);
		if (null != cvos && cvos.length > 0) {
			sReturnString = new String[cvos.length];
			for (int i = 0; i < sReturnString.length; i++) {
				sReturnString[i] = cvos[i].getOperaCode();
			}
		} else {
			sReturnString = new String[0];
		}
		return sReturnString;
	}

	/*
	 * 获得原始的查询VO[] 2007-09-05 xhq
	 */
	public nc.vo.pub.query.ConditionVO[] getSuperConditionVO() {
		return super.getConditionVO();
	}

	/**
	 * 取得用户设定的查询条件VO数组 创建日期：(2001-4-25 16:50:17)
	 * 
	 * @return nc.vo.pub.query.ConditionVO[]
	 */
	public nc.vo.pub.query.ConditionVO[] getConditionVO() {
		nc.vo.pub.query.ConditionVO[] voaCond = super.getConditionVO();

		// zc_启用权限管理后保存查询条件出现了多余行
		if (isSaveHistory())
			return voaCond;

		// 这里加判断条件的目的是：当需要多公司条件下控制权限时，使用的权限vo是m_cCtrTmpDataPowerVOs
		// 从super.getConditionVO中得到的vo存在权限子查询vo，但是不是为了多公司的，所以说自查询相对于多公司来说是错误的
		// 这里要做的工作时要过滤掉此相关的vo，然后拼接上多公司的m_cCtrTmpDataPowerVOs
		if (m_isDataPowerSqlReturned)
			voaCond = setCVO4MulCoPower(voaCond);

		nc.vo.pub.query.ConditionVO[] voaCondpower = null;
		// 2005-05-13 wnj 可以不返回权限条件,for IA,钟鸣
		if (m_isDataPowerSqlReturned)
			voaCondpower = getDataPowerScmVOs();
		// else voaCondpower =null;

		if (voaCondpower != null && voaCondpower.length > 0) {
			nc.vo.pub.query.ConditionVO[] vocons = new nc.vo.pub.query.ConditionVO[voaCond.length
					+ voaCondpower.length];
			int count = 0;
			for (int i = 0; i < voaCond.length; i++) {
				vocons[i] = voaCond[i];
				count++;
			}
			for (int i = 0; i < voaCondpower.length; i++) {
				vocons[count] = voaCondpower[i];
				count++;
			}
			voaCond = vocons;

		}

		int iEleCount = 0;// 现有条件的VO数组,可能没有条件，是NULL。

		if (voaCond != null)
			iEleCount = voaCond.length;
		// 2004-11-29 :add print status query condition
		ConditionVO voPrintStatus = getPrintStatusCondVO();
		nc.vo.scm.pub.SCMEnv.out("try PrintCount.");
		if (voPrintStatus != null) {
			nc.vo.scm.pub.SCMEnv.out("try PrintCount."
					+ voPrintStatus.getFieldCode() + ","
					+ voPrintStatus.getValue());
			// 附加上打印条件。
			ConditionVO[] voaNewCond = new ConditionVO[iEleCount + 1];
			for (int i = 0; i < iEleCount; i++)
				voaNewCond[i] = voaCond[i];
			// 最后一个是打印状态
			voaNewCond[iEleCount] = voPrintStatus;
			return voaNewCond;
		}

		return voaCond;
	}

	/**
	 * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2001-11-22 16:35:52) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return java.lang.String
	 */
	public String getCorpFieldCode() {
		return m_sCorpFieldCode;
	}

	/**
	 * ?user> 功能： 参数： 返回： 例外： 日期：(2004-9-22 16:26:28) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return nc.vo.pub.query.ConditionVO[]
	 */
	public ConditionVO[] getDataPowerScmVOs() {
		return m_dataPowerScmVOs;
	}

	/**
	 * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2001-11-22 16:34:24) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return java.lang.String
	 */
	public String getDefaultCorpID() {
		if (m_sDefaultCorpID == null || m_sDefaultCorpID.equals("")) {
			m_sDefaultCorpID = getClientEnvironment().getCorporation()
					.getPk_corp();
		}
		return m_sDefaultCorpID;
	}

	/**
	 * 根据传入的code和name构建一个自定义查询条件 创建日期：(2003-9-24 15:34:20)
	 * 
	 * @return nc.vo.pub.query.QueryConditionVO
	 * @param sFieldCode
	 *            java.lang.String
	 * @param sFieldName
	 *            java.lang.String
	 */
	public QueryConditionVO getDefaultQueryCndVO(String sFieldCode,
			String sFieldName) {
		if (sFieldCode == null || sFieldName == null) {
			return null;
		}
		UFBoolean bTrue = new UFBoolean(true);
		UFBoolean bFalse = new UFBoolean(false);
		Integer intZero = new Integer(0);
		Integer intOne = new Integer(1);
		QueryConditionVO voQuery = new QueryConditionVO();
		voQuery.setFieldCode(sFieldCode);
		voQuery.setFieldName(sFieldName);
		voQuery.setOperaCode("=@");
		voQuery.setOperaName("等于@");

		voQuery.setDataType(intZero);
		voQuery.setNewMaxLength(new Integer(20));
		voQuery.setReturnType(intOne);// 只返回名称
		voQuery.setDispType(intOne);
		voQuery.setPkTemplet("");
		voQuery.setIfAutoCheck(bTrue);
		voQuery.setIfMust(bFalse);
		voQuery.setIfDefault(bTrue);
		voQuery.setIfUsed(bTrue);
		voQuery.setIfOrder(bFalse);
		voQuery.setIfGroup(bFalse);
		voQuery.setIfSum(bFalse);

		return voQuery;
	}

	/**
	 * 得到扩展的查询条件。
	 * 
	 * 创建日期：(2001-8-14 9:52:51)
	 * 
	 * @return nc.vo.pub.query.ConditionVO[]
	 * @param voCons
	 *            nc.vo.pub.query.ConditionVO[]
	 */
	public ConditionVO[] getExpandVOs(ConditionVO[] cvo) {
		ConditionVO[] cvoNew = null;
		Vector<ConditionVO> vCvoNew = new Vector<ConditionVO>(1, 1);
		// 滤掉vfree0类的自由项,同时取出对应的自由项FreeVO
		boolean bHasPrintCount = false;// 是否有了打印次数条件，防止重复加入。

		for (int i = 0; i < cvo.length; i++) {
			if (!bHasPrintCount
					&& cvo[i].getFieldCode().indexOf(
							nc.vo.scm.print.PrintConst.IPrintCount) > 0)
				bHasPrintCount = true;
			// 对自由项
			if (cvo[i].getDataType() == 100) {
				String sFieldCode = cvo[i].getFieldCode().trim();
				String sAfterVfree0 = sFieldCode.substring(sFieldCode
						.indexOf("vfree0") + 6);
				// 自由项值的表中含有该项
				// if (m_htFreeVO.containsKey(sFieldCode)) {
				// FreeVO fvo= (FreeVO) m_htFreeVO.get(sFieldCode);
				FreeVO fvo = (FreeVO) cvo[i].getRefResult().getRefObj();
				// 拆出十个条件VO，加入新的结果集
				for (int j = 1; j <= 10; j++) {
					ConditionVO cvoAddVO = new ConditionVO();
					String sValueName = "vfree" + Integer.toString(j).trim();
					if (fvo.getAttributeValue(sValueName) == null) {
						cvoAddVO.setOperaCode("IS");
						cvoAddVO.setValue("NULL");
						cvoAddVO.setDataType(1);
					} else {
						cvoAddVO.setOperaCode("=");
						cvoAddVO.setValue(fvo.getAttributeValue(sValueName)
								.toString().trim());
						cvoAddVO.setDataType(0);
					}
					cvoAddVO.setFieldCode(sValueName + sAfterVfree0);
					cvoAddVO.setFieldName(sValueName + sAfterVfree0);
					cvoAddVO.setLogic(true);
					vCvoNew.addElement((ConditionVO) cvoAddVO.clone());
				}
				// }
				// 对下拉框
			} else if (cvo[i].getDataType() == 200) {
				ConditionVO cvoAddVO = cvo[i];
				Object oValue = null;
				if ((cvoAddVO.getRefResult() != null)
						&& (cvoAddVO.getRefResult().getRefObj() != null)) {
					oValue = ((DataObject) cvoAddVO.getRefResult().getRefObj())
							.getID();
					cvoAddVO.setValue(oValue.toString());
				} else {
					// RefResultVO rsvo=cvoAddVO.getRefResult();
					oValue = cvoAddVO.getValue();
					cvoAddVO.setValue(oValue.toString());
				}
				if (oValue instanceof Integer) {
					cvoAddVO.setDataType(1);
				} else if (oValue instanceof nc.vo.pub.lang.UFDouble) {
					cvoAddVO.setDataType(2);
				} else if (oValue instanceof String) {
					cvoAddVO.setDataType(0);
				}
				vCvoNew.addElement((ConditionVO) cvoAddVO.clone());
			} else {
				vCvoNew.addElement(cvo[i]);
			}
		}

		int iEleCount = vCvoNew.size();
		ConditionVO voPrintStatus = null;
		if (!bHasPrintCount) {
			// 2004-11-29 :add print status query condition
			// 附加上打印条件。
			voPrintStatus = getPrintStatusCondVO();
			nc.vo.scm.pub.SCMEnv.out("try PrintCount. ex.");
			if (voPrintStatus != null)// 有次数条件
				iEleCount++;
		}
		cvoNew = new ConditionVO[iEleCount];
		vCvoNew.copyInto(cvoNew);

		if (!bHasPrintCount && voPrintStatus != null) {// 有次数条件
			nc.vo.scm.pub.SCMEnv.out("try PrintCount.ex."
					+ voPrintStatus.getFieldCode() + ","
					+ voPrintStatus.getValue());
			// 最后一个是打印状态
			cvoNew[iEleCount - 1] = voPrintStatus;
		}

		return cvoNew;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-8-14 9:52:51)
	 * 
	 * @return nc.vo.pub.query.ConditionVO[]
	 * @param voCons
	 *            nc.vo.pub.query.ConditionVO[]
	 */
	public ConditionVO[] getExpandVOs(ConditionVO[] cvo, String sFreeAlianame) {
		if (sFreeAlianame == null) {
			sFreeAlianame = "";
		} else {
			sFreeAlianame = sFreeAlianame.trim() + '.';
		}
		ConditionVO[] cvoNew = null;
		Vector<ConditionVO> vCvoNew = new Vector<ConditionVO>(1, 1);
		// 滤掉vfree0类的自由项,同时取出对应的自由项FreeVO
		boolean bHasPrintCount = false;// 是否有了打印次数条件，防止重复加入。

		for (int i = 0; i < cvo.length; i++) {
			if (!bHasPrintCount
					&& cvo[i].getFieldCode().indexOf(
							nc.vo.scm.print.PrintConst.IPrintCount) > 0)
				bHasPrintCount = true;
			// 对自由项
			if (cvo[i].getDataType() == 100) {
				String sFieldCode = cvo[i].getFieldCode().trim();
				String sAfterVfree0 = sFieldCode.substring(sFieldCode
						.indexOf("vfree0") + 6);
				// 自由项值的表中含有该项
				// if (m_htFreeVO.containsKey(sFieldCode)) {
				// FreeVO fvo= (FreeVO) m_htFreeVO.get(sFieldCode);
				FreeVO fvo = (FreeVO) cvo[i].getRefResult().getRefObj();
				// 拆出十个条件VO，加入新的结果集
				for (int j = 1; j <= 10; j++) {
					ConditionVO cvoAddVO = new ConditionVO();
					String sValueName = "vfree" + Integer.toString(j).trim();
					if (fvo.getAttributeValue(sValueName) == null) {
						cvoAddVO.setOperaCode("IS");
						cvoAddVO.setValue("NULL");
						cvoAddVO.setDataType(1);
					} else {
						cvoAddVO.setOperaCode("=");
						cvoAddVO.setValue(fvo.getAttributeValue(sValueName)
								.toString().trim());
						cvoAddVO.setDataType(0);
					}
					cvoAddVO.setFieldCode(sFreeAlianame + sValueName
							+ sAfterVfree0);
					cvoAddVO.setFieldName(sFreeAlianame + sValueName
							+ sAfterVfree0);
					cvoAddVO.setLogic(true);
					vCvoNew.addElement((ConditionVO) cvoAddVO.clone());
				}
				// }
				// 对下拉框
			} else if (cvo[i].getDataType() == 200) {
				ConditionVO cvoAddVO = cvo[i];
				Object oValue = null;
				if ((cvoAddVO.getRefResult() != null)
						&& (cvoAddVO.getRefResult().getRefObj() != null)) {
					oValue = ((DataObject) cvoAddVO.getRefResult().getRefObj())
							.getID();
					cvoAddVO.setValue(oValue.toString());
				} else {
					// RefResultVO rsvo=cvoAddVO.getRefResult();
					oValue = cvoAddVO.getValue();
					cvoAddVO.setValue(oValue.toString());
				}
				if (oValue instanceof Integer) {
					cvoAddVO.setDataType(1);
				} else if (oValue instanceof nc.vo.pub.lang.UFDouble) {
					cvoAddVO.setDataType(2);
				} else if (oValue instanceof String) {
					cvoAddVO.setDataType(0);
				}
				vCvoNew.addElement((ConditionVO) cvoAddVO.clone());
			} else {
				vCvoNew.addElement(cvo[i]);
			}
		}

		int iEleCount = vCvoNew.size();
		ConditionVO voPrintStatus = null;
		if (!bHasPrintCount) {
			// 2004-11-29 :add print status query condition
			// 附加上打印条件。
			voPrintStatus = getPrintStatusCondVO();
			nc.vo.scm.pub.SCMEnv.out("try PrintCount. ex2.");
			if (voPrintStatus != null)// 有次数条件
				iEleCount++;
		}
		cvoNew = new ConditionVO[iEleCount];
		vCvoNew.copyInto(cvoNew);

		if (!bHasPrintCount && voPrintStatus != null) {// 有次数条件
			nc.vo.scm.pub.SCMEnv.out("try PrintCount.ex2."
					+ voPrintStatus.getFieldCode() + ","
					+ voPrintStatus.getValue());
			// 最后一个是打印状态
			cvoNew[iEleCount - 1] = voPrintStatus;
		}

		return cvoNew;
	}

	/**
	 * 创建者：仲瑞庆 功能：由条件索引号查得最前的显示索引号 参数： 返回： 例外： 日期：(2001-8-23 19:03:26)
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return int
	 * @param iIndexRow
	 *            int
	 */
	protected int getListRow(int iIndexRow) {
		for (int j = 0; j < getValueRefResults().length; j++) {
			if (getIndexes(j - getImmobilityRows()) == iIndexRow) {
				return j;
			}
		}
		return -1;
	}

	/**
	 * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2001-8-23 20:49:16) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return java.lang.String
	 * @param sFieldCode
	 *            java.lang.String
	 */
	protected String getNamebyFieldCode(String sFieldCode) {
		String sReturnString = "";
		for (int i = 0; i < getConditionDatas().length; i++) {
			if (getConditionDatas()[i].getFieldCode().trim().equals(sFieldCode)) {
				sReturnString = getConditionDatas()[i].getFieldName().trim();
				break;
			}
		}
		return sReturnString;
	}

	/**
	 * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2001-8-23 20:49:16) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return java.lang.String
	 * @param sFieldCode
	 *            java.lang.String
	 */
	protected String getPKbyFieldCode(String sFieldCode) {
		String sReturnString = "";
		for (int i = 0; i < getConditionDatas().length; i++) {
			if (getConditionDatas()[i].getFieldCode().trim().equals(sFieldCode)) {
				int iListRow = getListRow(i);
				if (iListRow != -1) {
					if (getValueRefResults()[iListRow] != null
							&& getValueRefResults()[iListRow].getRefPK() != null)
						sReturnString = getValueRefResults()[iListRow]
								.getRefPK().trim();
					else
						sReturnString = "";
				}
				break;
			}
		}
		return sReturnString;
	}

	/**
	 * ?user> 功能： 参数： 返回： 例外： 日期：(2004-9-22 16:14:36) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return java.lang.String
	 * @param sRefNodeName
	 *            java.lang.String
	 * @param flag
	 *            int
	 */
	protected String getRefField(String sRefNodeName, int flag) {
		if (m_htRefField == null) {
			m_htRefField = new HashMap<String, String>();
			m_htRefField.put("库存组织" + String.valueOf(RETURNCODE), "bodycode");
			m_htRefField.put("库存组织" + String.valueOf(RETURNNAME), "bodyname");
			m_htRefField.put("库存组织" + String.valueOf(RETURNPK), "pk_calbody");
			m_htRefField.put("库存组织" + String.valueOf(-1),
					" from bd_calbody where 0=0 ");
			m_htRefField.put("仓库档案" + String.valueOf(RETURNCODE), "storcode");
			m_htRefField.put("仓库档案" + String.valueOf(RETURNNAME), "storname");
			m_htRefField.put("仓库档案" + String.valueOf(RETURNPK), "pk_stordoc");
			m_htRefField.put("仓库档案" + String.valueOf(-1),
					" from bd_stordoc where 0=0 ");
			m_htRefField.put("客商档案" + String.valueOf(RETURNCODE), "custcode");
			m_htRefField.put("客商档案" + String.valueOf(RETURNNAME), "custname");
			m_htRefField.put("客商档案" + String.valueOf(RETURNPK), "pk_cumandoc");
			m_htRefField
					.put("客商档案" + String.valueOf(-1),
							" from bd_cubasdoc b ,bd_cumandoc m where b.pk_cubasdoc=m.pk_cubasdoc ");
			m_htRefField.put("客户档案" + String.valueOf(RETURNCODE), "custcode");
			m_htRefField.put("客户档案" + String.valueOf(RETURNNAME), "custname");
			m_htRefField.put("客户档案" + String.valueOf(RETURNPK), "pk_cumandoc");
			m_htRefField
					.put("客户档案" + String.valueOf(-1),
							" from bd_cubasdoc b ,bd_cumandoc m where b.pk_cubasdoc=m.pk_cubasdoc ");
			m_htRefField.put("供应商档案" + String.valueOf(RETURNCODE), "custcode");
			m_htRefField.put("供应商档案" + String.valueOf(RETURNNAME), "custname");
			m_htRefField.put("供应商档案" + String.valueOf(RETURNPK), "pk_cumandoc");
			m_htRefField
					.put("供应商档案" + String.valueOf(-1),
							" from bd_cubasdoc b ,bd_cumandoc m where b.pk_cubasdoc=m.pk_cubasdoc ");
			m_htRefField.put("部门档案" + String.valueOf(RETURNCODE), "deptcode");
			m_htRefField.put("部门档案" + String.valueOf(RETURNNAME), "deptname");
			m_htRefField.put("部门档案" + String.valueOf(RETURNPK), "pk_deptdoc");
			m_htRefField.put("部门档案" + String.valueOf(-1),
					" from bd_deptdoc where 0=0 ");
			m_htRefField.put("人员档案" + String.valueOf(RETURNCODE), "psncode");
			m_htRefField.put("人员档案" + String.valueOf(RETURNNAME), "psnname");
			m_htRefField.put("人员档案" + String.valueOf(RETURNPK), "pk_psndoc");
			m_htRefField.put("人员档案" + String.valueOf(-1),
					" from bd_psndoc where 0=0 ");
			// added by czp , 2006-09-21
			m_htRefField.put("采购组织" + String.valueOf(RETURNCODE), "code");
			m_htRefField.put("采购组织" + String.valueOf(RETURNNAME), "name");
			m_htRefField.put("采购组织" + String.valueOf(RETURNPK), "pk_purorg");
			m_htRefField.put("采购组织" + String.valueOf(-1),
					" from bd_purorg where 0=0 ");

			// Added by Shaw on Sep 27, 2006
			m_htRefField.put("存货档案" + String.valueOf(RETURNCODE), "invcode");
			m_htRefField.put("存货档案" + String.valueOf(RETURNNAME), "invname");
			m_htRefField.put("存货档案" + String.valueOf(RETURNPK), "pk_invmandoc");
			m_htRefField
					.put("存货档案" + String.valueOf(-1),
							" from bd_invbasdoc b, bd_invmandoc m where b.pk_invbasdoc = m.pk_invbasdoc ");
			m_htRefField.put("存货分类" + String.valueOf(RETURNCODE),
					"invclasscode");
			m_htRefField.put("存货分类" + String.valueOf(RETURNNAME),
					"invclassname");
			m_htRefField.put("存货分类" + String.valueOf(RETURNPK), "pk_invcl");
			m_htRefField.put("存货分类" + String.valueOf(-1),
					" from bd_invcl where 0=0 ");

			m_htRefField.put("项目管理档案" + String.valueOf(RETURNCODE), "jobcode");
			m_htRefField.put("项目管理档案" + String.valueOf(RETURNNAME), "jobname");
			m_htRefField.put("项目管理档案" + String.valueOf(RETURNPK),
					"pk_jobmngfil");
			m_htRefField
					.put("项目管理档案" + String.valueOf(-1),
							" from bd_jobbasfil b, bd_jobmngfil m where b.pk_jobbasfil = m.pk_jobbasfil ");
			//
			m_htRefField.put("地区分类" + String.valueOf(RETURNCODE), "areaclcode");
			m_htRefField.put("地区分类" + String.valueOf(RETURNNAME), "areaclname");
			m_htRefField.put("地区分类" + String.valueOf(RETURNPK), "pk_areacl");
			m_htRefField.put("地区分类" + String.valueOf(-1),
					" from bd_areacl where 0=0 ");

			// salecorp added 20070430 zhongwei
			m_htRefField.put("销售组织" + String.valueOf(RETURNCODE),
					"vsalestrucode");
			m_htRefField.put("销售组织" + String.valueOf(RETURNNAME),
					"vsalestruname");
			m_htRefField.put("销售组织" + String.valueOf(RETURNPK), "csalestruid");
			m_htRefField.put("销售组织" + String.valueOf(-1),
					" from bd_salestru where 0=0 ");

			// 检验项目支持数据权限 ｌｉｙｃ
			m_htRefField.put("检验项目" + String.valueOf(RETURNCODE),
					"ccheckitemcode");
			m_htRefField.put("检验项目" + String.valueOf(RETURNNAME),
					"ccheckitemname");
			m_htRefField.put("检验项目" + String.valueOf(RETURNPK), "ccheckitemid");/*-=notranslate=-*/
			m_htRefField.put("检验项目" + String.valueOf(-1),
					" from qc_checkitem where 0=0 ");
		}

		return m_htRefField.get(sRefNodeName + String.valueOf(flag));
	}

	/**
	 * ?user> 功能： 参数： 返回： 例外： 日期：(2005-1-7 11:56:19) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return nc.ui.pub.beans.UIRefPane
	 * @param sFieldCode
	 *            java.lang.String
	 */
	private UIRefPane getRefPaneByCode(String sFieldCode) {

		nc.vo.pub.query.QueryConditionVO[] voaConData = getConditionDatas();
		if (voaConData == null || voaConData.length == 0)
			return null;
		// 以下为对参照的初始化

		nc.ui.pub.beans.UIRefPane ref = null;
		for (int i = 0; i < voaConData.length; i++) {
			if (sFieldCode.equals(voaConData[i].getFieldCode())) {
				if (voaConData[i].getDataType().intValue() == nc.vo.pub.query.IQueryConstants.UFREF) {
					EditComponentFacotry factry = new EditComponentFacotry(
							voaConData[i]);
					ref = (nc.ui.pub.beans.UIRefPane) factry
							.getEditComponent(null);

				}

				break;
			}
		}
		return ref;
	}

	/**
	 * 
	 * @param sFieldCode
	 * @return
	 */
	public UIRefPane getRefPaneByNodeCode(String sFieldCode) {

		nc.vo.pub.query.QueryConditionVO[] voaConData = getConditionDatas();
		if (voaConData == null || voaConData.length == 0)
			return null;
		// 以下为对参照的初始化

		nc.ui.pub.beans.UIRefPane ref = null;
		for (int i = 0; i < voaConData.length; i++) {
			if (sFieldCode.equals(voaConData[i].getNodecode())) {
				if (voaConData[i].getDataType().intValue() == nc.vo.pub.query.IQueryConstants.UFREF) {
					EditComponentFacotry factry = new EditComponentFacotry(
							voaConData[i]);
					ref = (nc.ui.pub.beans.UIRefPane) factry
							.getEditComponent(null);

				}

				break;
			}
		}
		return ref;
	}

	/**
	 * 返回 tfUnitCode 特性值。
	 * 
	 * @return javax.swing.JTextField
	 */
	/* 警告：此方法将重新生成。 */
	private javax.swing.JTextField gettfUnitCode() {
		if (ivjtfUnitCode == null) {
			try {
				ivjtfUnitCode = new javax.swing.JTextField();
				ivjtfUnitCode.setName("tfUnitCode");
				ivjtfUnitCode.setBounds(104, 26, 77, 20);
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjtfUnitCode;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-7-25 13:50:05)
	 * 
	 * @return nc.ui.pub.beans.UIPanel
	 */
	public UIPanel getUIPanel() {

		if (ivjUIPanel == null) {
			try {
				// nc.ui.ic.ic601.NormalQuery ivjUIPanel1 = new
				// nc.ui.ic.ic601.NormalQuery();
				ivjUIPanel = new UIPanel();
				ivjUIPanel.setName("yudaying");
				ivjUIPanel.setLayout(null);
				// ivjtfUnitCode.setBounds(10, 10, 200,300);
				// user code begin {1}
				nc.vo.scm.pub.SCMEnv.out("call getUIPanel");
				ivjUIPanel.add(new nc.ui.pub.beans.UITextField("dddd"));
				ivjUIPanel.add(gettfUnitCode());
				// ivjUIPanel=(UIPanel)ivjUIPanel1;
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjUIPanel;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-7-25 14:48:18)
	 */
	public String getUnitCode() {
		return gettfUnitCode().getText();

	}

	/**
	 * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2001-8-23 20:49:16) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return java.lang.String
	 * @param sFieldCode
	 *            java.lang.String
	 */
	protected String getValuebyFieldCode(String sFieldCode) {
		String sReturnString = "";
		for (int i = 0; i < getConditionVO().length; i++) {
			if (getConditionVO()[i].getFieldCode().trim().equals(sFieldCode)) {
				sReturnString = getConditionVO()[i].getValue().trim();
				break;
			}
		}
		// for (int i = 0; i < getConditionDatas().length; i++) {
		// if (getConditionDatas()[i].getFieldCode().trim().equals(sFieldCode))
		// {
		// int iListRow = getListRow(i);
		// if (iListRow != -1) {
		// sReturnString =
		// getValueRefResults()[iListRow] == null ? "" :
		// getValueRefResults()[iListRow].getRefName().trim();
		// }
		// break;
		// }
		// }
		return sReturnString;
	}

	/**
	 * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2001-8-23 20:49:16) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return java.lang.String
	 * @param sFieldCode
	 *            java.lang.String
	 */
	protected String[] getValuesbyFieldCode(String sFieldCode) {
		String sReturnString[] = new String[0];
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 0; i < getConditionVO().length; i++) {
			if (getConditionVO()[i].getFieldCode().trim().equals(sFieldCode)) {
				if (getConditionVO()[i].getDataType() == 5)// 运用一般参照约束别的参照
					al
							.add(getConditionVO()[i].getRefResult().getRefPK()
									.trim());
				else
					// 运用参照（主要为日期参照）约束别的参照的情况
					al.add(getConditionVO()[i].getValue().trim());
			}
		}

		// for (int i = 0; i < getConditionDatas().length; i++) {
		// if (getConditionDatas()[i].getFieldCode().trim().equals(sFieldCode))
		// {
		// int iListRow = getListRow(i);
		// if (iListRow != -1) {
		// al.add(
		// getValueRefResults()[iListRow] == null ? "" :
		// getValueRefResults()[iListRow].getRefName().trim());
		// }
		// }
		// }
		sReturnString = al.toArray(sReturnString);
		return sReturnString;
	}

	/**
	 * 创建者：仲瑞庆 功能：在Where子句中将不含有自由项的Vfree0值， 对Combobox类型将取ID值(待定) 参数： 返回： 例外：
	 * 日期：(2001-8-12 18:00:24) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return java.lang.String
	 */
	public String getWhereSQL(ConditionVO[] cvo) {

		return super.getWhereSQL(getExpandVOs(cvo));
	}

	/**
	 * 创建者：仲瑞庆 功能：在Where子句中将不含有自由项的Vfree0值， 对Combobox类型将取ID值(待定) 参数： 返回： 例外：
	 * 日期：(2001-8-12 18:00:24) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return java.lang.String
	 */
	public String getWhereSQL(ConditionVO[] cvo, String sFreeAlianame) {

		return super.getWhereSQL(getExpandVOs(cvo, sFreeAlianame));
	}

	/**
	 * 创建者：仲瑞庆 功能：初始化参照 参数： 返回： 例外： 日期：(2001-8-27 15:15:52) 修改日期，修改人，修改原因，注释标志：
	 */
	public void initCorpRef(String sCorpFieldCode, String sShowCorpID,
			ArrayList alAllCorpIDs) { 
		// 以下为对公司参照的初始化
		ArrayList<Serializable> altemp = new ArrayList<Serializable>();
		altemp.add(sCorpFieldCode);
		altemp.add(sShowCorpID);
		m_sDefaultCorpID = sShowCorpID;
		m_sCorpFieldCode = sCorpFieldCode;
		if (null == alAllCorpIDs) {
			altemp.add(new ArrayList());
		} else {
			altemp.add(alAllCorpIDs);
		}
		m_alCorpRef.add(altemp);
		if (alAllCorpIDs != null) {
			if (m_sdefaultcorps == null) {
				m_sdefaultcorps = new String[alAllCorpIDs.size()];
				alAllCorpIDs.toArray(m_sdefaultcorps);
			}
			// if (!m_hmCorpData.containsKey(sCorpFieldCode)) {
			m_hmCorpData.put(sCorpFieldCode, alAllCorpIDs);

			// } else {
			// ArrayList alnew = (ArrayList) m_hmCorpData.get(sCorpFieldCode);
			// alnew.addAll(alAllCorpIDs);
			// m_hmCorpData.put(sCorpFieldCode, alnew);
			//
			// }
		}
		setDefaultValue(sCorpFieldCode.trim(), sShowCorpID, null);

	}

	/**
	 * 创建者：仲瑞庆 功能：初始化参照 参数： 返回： 例外： 日期：(2001-8-27 15:15:52) 修改日期，修改人，修改原因，注释标志：
	 */
	public void initLocatorRef(String sLocatorFieldCode, String sInvFieldCode,
			String sWhFieldCode) {
		// 以下为对货位参照的记录
		ArrayList<String> altemp = new ArrayList<String>();
		altemp.add(sLocatorFieldCode);
		altemp.add(sInvFieldCode);
		altemp.add(sWhFieldCode);
		m_alLocatorRef.add(altemp);
	}

	/**
	 * 创建者：仲瑞庆 功能：初始化参照 参数： 返回： 例外： 日期：(2001-8-27 15:15:52) 修改日期，修改人，修改原因，注释标志：
	 */
	public void initQueryDlgRef() {
		// 以下为对参照的初始化
		// 一般仓库
		setRefInitWhereClause("head.cwarehouseid", "仓库档案",
				"gubflag='N'  and pk_corp=", "pk_corp");
		// '" + m_sCorpID + "'");
		// 入库一般仓库
		setRefInitWhereClause("cinwarehouseid", "仓库档案",
				"gubflag='N'  and pk_corp=", "pk_corp");
		// 出库一般仓库
		setRefInitWhereClause("coutwarehouseid", "仓库档案",
				"gubflag='N'  and pk_corp=", "pk_corp");
		// 废品仓库
		setRefInitWhereClause("cwastewarehouseid", "仓库档案",
				"gubflag='Y'  and pk_corp=", "pk_corp");
		// 客户
		setRefInitWhereClause("head.ccustomerid", "客户档案",
				"(custflag ='0' or custflag ='2') and  bd_cumandoc.pk_corp=",
				"pk_corp");
		// '" + m_sCorpID + "'");
		// 供应商
		setRefInitWhereClause("head.cproviderid", "供应商档案",
				"(custflag ='1' or custflag ='3') and  bd_cumandoc.pk_corp=",
				"pk_corp");
		// 供应商
		setRefInitWhereClause("cvendorid", "供应商档案",
				"(custflag ='1' or custflag ='3')  and bd_cumandoc.pk_corp=",
				"pk_corp");
		// '" + m_sCorpID + "'");
		// 存货,单位编码过滤存货且为非封存存货,非劳务，非折扣存货
		setRefInitWhereClause("cinventorycode", "存货档案",
				" bd_invbasdoc.discountflag='N' and bd_invbasdoc.laborflag='N' "
						+ "  and bd_invmandoc.pk_corp=", "pk_corp");
		// '" + m_sCorpID + "'");
		// 存货,单位编码过滤存货且为非封存存货,非劳务，非折扣存货
		setRefInitWhereClause("body.cinventoryid", "存货档案",
				" bd_invbasdoc.discountflag='N' and bd_invbasdoc.laborflag='N' "
						+ "  and bd_invmandoc.pk_corp=", "pk_corp");

		// 成本对象 add by hanwei 2003-11-06
		setRefInitWhereClause("ccostobject", "物料档案",
				"sfcbdx='Y' and bd_produce.pk_corp=", "pk_corp");

		// ----------- 收发类别 cdispatcherid --------------
		setRefInitWhereClause("cdispatcherid", "收发类别",
				" pk_corp='0001' or pk_corp=", "pk_corp");
	}

	/**
	 * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2001-12-27 18:53:45) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return boolean
	 */
	public boolean isCloseOK() {
		return getResult() == ID_OK;
	}

	/**
	 * 创建者：仲瑞庆 功能：设置辅计量对应的公司和存货所在的字段的映射表 参数：第一个为辅计量的字段， 第二个为一个数组，内含两个元素，
	 * 第一个为公司字段，第二个为存货字段 返回： 例外： 日期：(2001-8-23 18:56:19) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return boolean
	 * @param sAstUnit
	 *            java.lang.String
	 * @param sInitParam
	 *            java.lang.String[]
	 */
	public boolean setAstUnit(String sAstUnit, String[] sInitParam) {
		if ((sAstUnit == null) || (sAstUnit.trim().length() == 0)
				|| (sInitParam == null) || (sInitParam.length != 2))
			return false;
		m_htAstCorpInv.put(sAstUnit.trim(), sInitParam);
		return true;
	}

	/**
	 * 创建者：仲瑞庆 功能：设定哪些字段将依关键字段修改而自动清空 参数： 返回： 例外： 日期：(2001-8-13 13:10:28)
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @param sWhenChanged
	 *            java.lang.String
	 * @param sThenClears
	 *            java.lang.String[]
	 */
	public void setAutoClear(String sWhenChanged, String[] sThenClears) {
		m_htAutoClear.put(sWhenChanged, sThenClears);
	}

	/**
	 * 创建者：仲瑞庆 功能：向对话框中置入对应字段的下拉框 对应该项的模板库中应置相应字段的类型为200 参数：取值来自values,
	 * values中应当有一个空串--""，使选择集中可选空 values中第一项为ID，第二项为name（显示用） 返回： 例外：
	 * 日期：(2001-8-12 11:19:14) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return boolean
	 * @param sFieldCode
	 *            java.lang.String
	 * @param Values
	 *            java.lang.Object[][]
	 */
	public boolean setCombox(String sFieldCode, Object[][] Values) {
		try {
			UIComboBox uicbUIComboBox = new UIComboBox();
			for (int i = 0; i < Values.length; i++) {
				DataObject doValue = new DataObject(Values[i][0], Values[i][1]);
				uicbUIComboBox.addItem(doValue);
			}

			UIComboBoxCellEditor editor = new UIComboBoxCellEditor(
					uicbUIComboBox);
			setValueRef(sFieldCode, editor);
			DataObject doValue = new DataObject(Values[0][0], Values[0][1]);
			setDefaultValue(sFieldCode, null, doValue.toString());

			RefResultVO rrvo = new RefResultVO();
			rrvo.setRefCode(doValue.getID().toString().trim());
			rrvo.setRefName(doValue.getName().toString().trim());
			rrvo.setRefPK(doValue.getID().toString().trim());

			setDefaultResultVO(sFieldCode, rrvo);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 创建者：仲瑞庆 功能：初始化系统参照 参数： 示例：
	 * 
	 * 返回： 例外： 日期：(2001-8-27 11:16:57) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return boolean
	 * @param sFieldCode
	 *            java.lang.String
	 * @param sWhereClause
	 *            java.lang.String
	 */
	public boolean setCorpRefs(String sCorpFieldCode, String[] sRefFieldCodes) {
		try {
			if (sCorpFieldCode != null && sRefFieldCodes != null
					&& sRefFieldCodes.length > 0) {
				m_htCorpRef.put(sCorpFieldCode, sRefFieldCodes);
				for (int i = 0; i < sRefFieldCodes.length; i++) {
					int index = m_alpower.indexOf(sRefFieldCodes[i]);
					if (index >= 0)
						continue;
					m_alpower.add(sRefFieldCodes[i]);
					m_hmRefCorp.put(sRefFieldCodes[i], sCorpFieldCode);
					UIRefPane oRef = getRefPaneByCode(sRefFieldCodes[i]);
					if (oRef != null) {
						oRef.getRefModel().setUseDataPower(true);
						setValueRef(sRefFieldCodes[i], oRef);
					}

				}
			}

			return true;
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out(e.getMessage());
			return false;
		}
	}

	/**
	 * ?user> 功能： 参数： 返回： 例外： 日期：(2004-9-24 9:24:10) 修改日期，修改人，修改原因，注释标志：
	 * 
	 */
	public void setDefaultCorps(String[] sNewCorps) {
		m_sdefaultcorps = sNewCorps;
	}

	/**
	 * 创建者：仲瑞庆 功能：依来源字段的显示值，当值选择改变时，将对应项的参照更改， 当没有对应该值的参照，对应项将禁止填入。 参数：
	 * Object[][]结构，Object[i][0] 为来源字段的可能值，Object[i][1]为对应字段的Ref对象 返回： 例外：
	 * 日期：(2001-9-3 11:42:32) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return boolean
	 * @param sSourceFieldCode
	 *            java.lang.String
	 * @param sDestinationFieldCode
	 *            java.lang.String
	 * @param oRefs
	 *            org.omg.CORBA.Object[][]
	 */
	public boolean setDifferenceRef(String sSourceFieldCode,
			String sDestinationFieldCode, Object[][] oRefs) {
		if ((null == sSourceFieldCode) || (null == sDestinationFieldCode)
				|| (null == oRefs))
			return false;

		sSourceFieldCode = sSourceFieldCode.trim();
		sDestinationFieldCode = sDestinationFieldCode.trim();

		boolean bFounded = false;
		for (int i = 0; i < m_alComboxToRef.size(); i++) {
			ArrayList<Object> alRow = (m_alComboxToRef.get(i));
			if ((alRow.get(0).toString().trim().equals(sSourceFieldCode))
					&& (alRow.get(1).toString().trim()
							.equals(sDestinationFieldCode))) {
				alRow.set(2, oRefs);
				m_alComboxToRef.set(i, alRow);
				bFounded = true;
				break;
			}
		}
		if (!bFounded) {
			ArrayList<Object> alRow = new ArrayList<Object>();
			alRow.add(sSourceFieldCode);
			alRow.add(sDestinationFieldCode);
			alRow.add(oRefs);
			m_alComboxToRef.add(alRow);
		}

		return true;
	}

	/**
	 * 创建者：仲瑞庆 功能：向对话框中置入对应字段的自由项参照 对应该项的模板库中应置相应字段的类型为100 参数： 返回： 例外：
	 * 日期：(2001-8-12 10:56:11) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return boolean
	 * @param sFieldCode
	 *            java.lang.String
	 */
	public boolean setFreeItem(String sFieldCode, String sInvFieldCode) {
		try {
			FreeItemRefPane firpFreeItemRefPane = new FreeItemRefPane();

			// 置编辑器
			UIFreeItemCellEditor editor = new UIFreeItemCellEditor(
					firpFreeItemRefPane);
			setValueRef(sFieldCode, editor);

			// 记录存货与自由项的对照表
			m_htFreeItemInv.put(sFieldCode.trim(), sInvFieldCode.trim());

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 创建者：仲瑞庆 功能：对指定字段设定初始日期， 依boolean值决定是月初还是月末， 月份则由登录日期决定 参数： 返回： 例外：
	 * 日期：(2001-12-7 14:57:50) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @param sFieldCode
	 *            java.lang.String
	 * @param ufdSourceDate
	 *            nc.vo.pub.lang.UFDate
	 * @param bIsFirstDateOrNot
	 *            boolean
	 */
	public void setInitDate(String sFieldCode, String ufdSourceDate) {
		setDefaultValue(sFieldCode, null, ufdSourceDate);
	}

	/**
	 * 创建者：仲瑞庆 功能：对指定字段设定初始日期， 依boolean值决定是月初还是月末， 月份则由登录日期决定 参数： 返回： 例外：
	 * 日期：(2001-12-7 14:57:50) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @param sFieldCode
	 *            java.lang.String
	 * @param ufdSourceDate
	 *            nc.vo.pub.lang.UFDate
	 * @param bIsFirstDateOrNot
	 *            boolean
	 */
	public void setInitDate(String sFieldCode, String ufdSourceDate,
			boolean bIsFirstDateOrNot) {
		UFDate ufdValueDate = new UFDate("2001-01-01");
		if (bIsFirstDateOrNot) {
			ufdValueDate = new UFDate(new String(ufdSourceDate.substring(0, 8)
					+ "01"));
		} else {
			ufdValueDate = new UFDate(new String(ufdSourceDate.substring(0, 8)
					+ new Integer((new UFDate(ufdSourceDate)).getDaysMonth())));
		}
		setDefaultValue(sFieldCode, null, ufdValueDate.toString());
	}

	/**
	 * 创建者：仲瑞庆 功能：向对话框中置入对应字段的批次号参照 对应该项的模板库中应置相应字段的类型为400 参数： 返回： 例外：
	 * 日期：(2001-8-29 13:40:07) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return boolean
	 * @param sFieldLotCode
	 *            java.lang.String
	 * @param sFieldInvCode
	 *            java.lang.String
	 */
	public boolean setLot(String sFieldLotCode, String sFieldInvCode) {
		try {
			UIRefPane lqrLotQueryRef = (UIRefPane) Class.forName(
					"nc.ui.ic.pub.lotquery.LotQueryRef").newInstance();

			lqrLotQueryRef.setWhereString(" and 1 = 1 ");

			// LotQueryRef lqrLotQueryRef= new LotQueryRef();

			// 置编辑器
			UIRefCellEditor editor = new UIRefCellEditor(lqrLotQueryRef);
			setValueRef(sFieldLotCode, editor);

			// 记录存货与批次号的对照表
			m_htLotInv.put(sFieldLotCode.trim(), sFieldInvCode.trim());

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 创建者：仲瑞庆 功能：初始化系统参照 参数：参照所在字段，参照名称，参照的where子句，参照的where子句中相关字段取值来源 示例：
	 * //以下为对参照的初始化 //一般仓库 ivjQueryConditionDlg.setRefInitWhereClause(
	 * "cwarehouseid", "仓库档案", "gubflag='N' and sealflag='N' and pk_corp=",
	 * "pk_corp"); //'" + m_sCorpID + "'"); //入库一般仓库
	 * ivjQueryConditionDlg.setRefInitWhereClause( "cinwarehouseid", "仓库档案",
	 * "gubflag='N' and sealflag='N' and pk_corp=", "pk_corp"); //出库一般仓库
	 * ivjQueryConditionDlg.setRefInitWhereClause( "coutwarehouseid", "仓库档案",
	 * "gubflag='N' and sealflag='N' and pk_corp=", "pk_corp"); //废品仓库
	 * ivjQueryConditionDlg.setRefInitWhereClause( "cwarehouseid", "仓库档案",
	 * "gubflag='Y' and sealflag='N' and pk_corp=", "pk_corp"); //客户
	 * ivjQueryConditionDlg.setRefInitWhereClause( "ccustomerid", "客商档案",
	 * "custflag ='Y' and bd_cumandoc.pk_corp=", "pk_corp"); //'" + m_sCorpID +
	 * "'"); //供应商 ivjQueryConditionDlg.setRefInitWhereClause( "cproviderid",
	 * "供应商档案", "custflag ='N' and bd_cumandoc.pk_corp=", "pk_corp"); //'" +
	 * m_sCorpID + "'"); //存货,单位编码过滤存货且为非封存存货
	 * ivjQueryConditionDlg.setRefInitWhereClause( "cinventorycode", "存货档案",
	 * "bd_invmandoc.sealflag ='N' and bd_invmandoc.pk_corp=", "pk_corp"); //'" +
	 * m_sCorpID + "'"); //存货,单位编码过滤存货且为非封存存货
	 * ivjQueryConditionDlg.setRefInitWhereClause( "cinventoryid", "存货档案",
	 * "bd_invmandoc.sealflag ='N' and bd_invmandoc.pk_corp=", "pk_corp");
	 * 
	 * 返回： 例外： 日期：(2001-8-27 11:16:57) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return boolean
	 * @param sFieldCode
	 *            java.lang.String
	 * @param sWhereClause
	 *            java.lang.String
	 */
	public boolean setRefInitWhereClause(String sFieldCode, String sRefName,
			String sWhereClause, String sCheckFieldCode) {
		try {
			// 2002-10-28.01 wnj add check unique,use last set value of list.
			//
			String sMyFieldCode = null;
			// 这里应该不用检查null
			for (int i = m_alRefInitWhereClause.size() - 1; i >= 0; i--) {
				sMyFieldCode = m_alRefInitWhereClause.get(i).get(0).toString();
				// 相等则移去已有的记录，用最新的。
				if (sMyFieldCode.trim().equals(sFieldCode.trim())) {
					// nc.vo.scm.pub.SCMEnv.out("reset cond "+sFieldCode);
					m_alRefInitWhereClause.remove(i);
				}
			}
			// private final int RI_FIELD_CODE=0;
			// private final int RI_REF_NAME=1;
			// private final int RI_WHERE_CLAUSE=2;
			// private final int RI_CHECK_FIELD_CODE=3;

			ArrayList<String> alrow = new ArrayList<String>();
			alrow.add(sFieldCode.trim());
			alrow.add(sRefName);
			alrow.add(sWhereClause.trim());
			alrow.add(sCheckFieldCode);
			m_alRefInitWhereClause.add(alrow);
			UIRefPane oRef = getRefPaneByCode(sFieldCode);
			if (oRef != null) {

				oRef.getRefModel().setPk_corp(getDefaultCorpID());
				setValueRef(sFieldCode, oRef);
			}

			return true;
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out(e.getMessage());
			return false;
		}
	}

	/**
	 * 创建者：仲瑞庆 功能：初始化参照,注意来源参照只能为字段串形式限制 参数：参照所在字段，参照的前where子句，参照的后where子句，
	 * 参照的where子句中相关字段取值来源和对应参照条件中的key, 是否使用null和空串标志 示例： 返回： 例外： 日期：(2001-8-27
	 * 11:16:57) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return boolean
	 * @param sFieldCode
	 *            java.lang.String
	 * @param sWhereClause
	 *            java.lang.String
	 */
	public boolean setRefMultiInit(String sFieldCode,
			String sBeforeWhereClause, String sAfterWhereClause,
			String[][] sCheckFieldCodes, boolean bIsNullUsed) {
		try {
			ArrayList<Object> alrow = new ArrayList<Object>();
			alrow.add(sBeforeWhereClause.trim());
			alrow.add(sAfterWhereClause.trim());
			alrow.add(sCheckFieldCodes);
			alrow.add(new UFBoolean(bIsNullUsed));

			m_htInitMultiRef.put(sFieldCode, alrow);
			return true;
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out(e.getMessage());
			return false;
		}
	}

	/**
	 * 创建者：仲瑞庆 功能：向对话框中置入对应字段的统计范围参照 对应该项的模板库中应置相应字段的类型为350
	 * 
	 * 其对应字段为统计结构参照 对应该项的模板库中应置相应字段的类型为300 参数： 返回： 例外： 日期：(2001-8-29 12:50:51)
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return boolean
	 * @param sFieldStateBCode
	 *            java.lang.String
	 * @param sFieldStateHCode
	 *            java.lang.String
	 */
	public boolean setStat(String sFieldStateBCode, String sFieldStateHCode,
			String sFieldCorpcode) {
		try {

			UIRefPane srpStathRefPane1 = (UIRefPane) Class.forName(
					"nc.ui.ic.pub.stat.StathRefPane").newInstance();
			// new StathRefPane("");
			// srpStathRefPane.setWhereString("bicusedflag='Y' and pk_corp=''");
			srpStathRefPane1.setWhereString("bicusedflag='Y'");
			UIRefPane srpStatbRefPane2 = (UIRefPane) Class.forName(
					"nc.ui.ic.pub.stat.StatbRefPane").newInstance();
			// new StatbRefPane();

			// 置编辑器
			UIRefCellEditor editorh = new UIRefCellEditor(
					(UIRefPane) srpStathRefPane1);
			setValueRef(sFieldStateHCode, editorh);

			UIRefCellEditor editorb = new UIRefCellEditor(
					(UIRefPane) srpStatbRefPane2);
			setValueRef(sFieldStateBCode, editorb);

			// 记录统计结构与统计范围的对照表
			m_htStatBtoH.put(sFieldStateBCode.trim(), sFieldStateHCode.trim());
			// 记录统计结构与公司的对照表
			m_htStatHtoCorp.put(sFieldStateHCode.trim(), sFieldCorpcode.trim());

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 创建者：仲瑞庆 功能：初始化参照 参数： 返回： 例外： 日期：(2001-8-13 15:45:09) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 加入初始化自由项参照,初始化辅计量参照
	 * 
	 * @param iRow
	 *            int
	 * @param iCol
	 *            int
	 */
	protected void setValueRefEditor(int iRow, int iCol) {

		int index = getIndexes(iRow - getImmobilityRows());
		// 2002-10-27.01 wnj add below check;
		if (index < 0 || index >= getConditionDatas().length) {
			nc.vo.scm.pub.SCMEnv.out("qry cond err.");
			return;
		}
		QueryConditionVO qcvo = getConditionDatas()[index];
		String sFieldCode = qcvo.getFieldCode().trim();
		ArrayList alRefRow = null;
		// 涉及对系统参照的初始化
		for (int i = 0; i < m_alRefInitWhereClause.size(); i++) {

			alRefRow = m_alRefInitWhereClause.get(i);
			String refname = alRefRow.get(RI_REF_NAME) == null ? null
					: alRefRow.get(RI_REF_NAME).toString().trim();
			String refcode = alRefRow.get(RI_FIELD_CODE).toString().trim();
			String refwhere = alRefRow.get(RI_WHERE_CLAUSE).toString().trim();
			Object refcheck = alRefRow.get(RI_CHECK_FIELD_CODE);
			if (refcode.equals(qcvo.getFieldCode().trim())) {
				UIRefPane uirp = (UIRefPane) getValueRefObjectByFieldCode(qcvo
						.getFieldCode());// new
				if (refname == null)
					refname = uirp.getRefNodeName();
				// UIRefPane();//getRefPaneByCode(qcvo.getFieldCode());/
				uirp.getRefModel().setSealedDataShow(true);
				String sPK = "";// why? clear data ?
				StringBuffer swhere = new StringBuffer();
				if (refwhere != null)
					swhere.append(refwhere);
				if (refcheck != null)
					swhere.append("''");
				if ("仓库档案".equals(refname))
					uirp.getRefModel()
							.addWherePart(" and " + swhere.toString());
				else
					uirp.setWhereString(swhere.toString());
				changeValueRef(refname, uirp);

				for (int j = 0; j < getUITabInput().getRowCount(); j++) {
					int indexNow = getIndexes(j - getImmobilityRows());
					// 2002-10-27.01 wnj add below check.
					if (indexNow < 0 || indexNow >= getConditionDatas().length) {
						// nc.vo.scm.pub.SCMEnv.out("qry cond err.");
						continue;
					}

					QueryConditionVO qcvoNow = getConditionDatas()[indexNow];
					if (qcvoNow.getFieldCode().trim().equals(refcheck)) {
						RefResultVO tempValue = getValueRefResults()[j
								- getImmobilityRows()];
						if (tempValue == null || tempValue.getRefPK() == null)
							break;
						else {
							sPK = getValueRefResults()[j - getImmobilityRows()]
									.getRefPK().trim();

						}
						// 2005-05-27:王乃军、宋学军：仓库、库存组织关联权限控制问题（只启用了库存组织的权限）。
						// 因为仓库参照中库存组织关联权限是封装在setWhereString（）中的，所以在这里调用之会使关联权限失效。
						// 改为使用addWherePart( ).
						// 建议业务模块只使用addWherePart（ ），除非真要彻底覆盖参照的缺省限制。
						nc.vo.scm.pub.SCMEnv.out("wh sql="
								+ alRefRow.get(RI_WHERE_CLAUSE).toString()
										.trim() + "'" + sPK + "'");
						swhere = new StringBuffer();
						if (refwhere != null)
							swhere.append(refwhere);
						if (refcheck != null)
							swhere.append("'" + sPK + "'");

						if ("仓库档案".equals(refname))
							uirp.getRefModel().addWherePart(
									" and " + swhere.toString());
						else
							uirp.setWhereString(swhere.toString());
						changeValueRef(refname, uirp);
						break;
					}
				} // 2002-10-25 WNJ ADD BELOW "DISTINCT" STATEMENT
				if ("客商档案".equals(refname))
					uirp.setStrPatch(" DISTINCT "); // 2002-10-25 WNJ ADD BELOW
				// "DISTINCT" STATEMENT
				if (refcode.indexOf("costobject") >= 0) {
					nc.ui.bd.ref.AbstractRefModel refmodel = (nc.ui.bd.ref.AbstractRefModel) uirp
							.getRef().getRefModel();
					String sTableName = refmodel.getTableName();
					if (sTableName.indexOf("bd_produce") < 0) {
						sTableName = sTableName + ",bd_produce";
						refmodel.setTableName(sTableName);
						uirp.setStrPatch(" DISTINCT ");

					}

				}
				if (sPK.equals("") && refcheck != null
						&& refcheck.toString().endsWith("pk_corp")) {
					swhere = new StringBuffer();
					swhere.append(refwhere);
					swhere.append("'" + m_sDefaultCorpID + "'");

					if ("仓库档案".equals(refname))
						uirp.getRefModel().addWherePart(
								" and " + swhere.toString());
					else
						uirp.setWhereString(swhere.toString());
					changeValueRef(refname, uirp);

				}
				break;
			}
		} // 对公司参照的初始化
		for (int i = 0; i < m_alCorpRef.size(); i++) {
			alRefRow = m_alCorpRef.get(i);
			if (alRefRow.get(0).toString().trim().equals(
					qcvo.getFieldCode().trim())) {
				UIRefPane uirp = new UIRefPane();
				uirp.setRefNodeName("公司目录");
				uirp.getRefModel().setSealedDataShow(true);
				String sPK = alRefRow.get(1).toString().trim();
				uirp.setPK(sPK);
				String sPKs = "'" + sPK + "' ";
				ArrayList alPKs = (ArrayList) alRefRow.get(2);
				for (int j = 0; j < alPKs.size(); j++) {
					sPKs = sPKs + ",'" + (String) alPKs.get(j) + "'";
				}
				uirp.getRefModel().addWherePart(
						" and pk_corp in (" + sPKs + ")");
				changeValueRef(alRefRow.get(0).toString().trim(), uirp);
				if (qcvo.getReturnType().intValue() == 1) {
					setDefaultValue(alRefRow.get(0).toString().trim(), sPK,
							uirp.getRefName());
				} else {
					setDefaultValue(alRefRow.get(0).toString().trim(), sPK,
							uirp.getRefCode());
				}
				break;
			}
		}
		// 对货位参照的初始化
		for (int i = 0; i < m_alLocatorRef.size(); i++) {
			alRefRow = m_alLocatorRef.get(i);
			if (alRefRow.get(0).toString().trim().equals(
					qcvo.getFieldCode().trim())) {
				String sNowFieldCode = alRefRow.get(0).toString().trim();
				String sInvFieldCode = alRefRow.get(1).toString().trim();
				String sWhFieldCode = alRefRow.get(2).toString().trim();
				String sInvID = getPKbyFieldCode(sInvFieldCode);
				InvVO invvo = null;
				if (m_htInvVO.containsKey(sInvID)) {
					invvo = m_htInvVO.get(sInvID);
				}
				String sWhID = getPKbyFieldCode(sWhFieldCode);
				WhVO voWh = null;
				if (m_htWhVO.containsKey(sWhID)) {
					voWh = m_htWhVO.get(sWhID);
				}
				String sName = getValuebyFieldCode(sNowFieldCode);
				String spk = getPKbyFieldCode(sNowFieldCode);

				ILocatorRefPane uirp = null;
				try {
					uirp = (ILocatorRefPane) Class.forName(
							"nc.bs.ic.pub.locatorref.LocatorRefPane")
							.newInstance();
				} catch (Exception e) {
					nc.vo.scm.pub.SCMEnv.out(e);
					changeValueRef(alRefRow.get(0).toString().trim(), null);
					break;
				}

				// LocatorRefPane uirp = new LocatorRefPane();
				uirp.setInOutFlag(nc.vo.scm.constant.ic.InOutFlag.OUT);
				uirp.setOldValue(sName, null, spk);
				uirp.setParam(voWh, invvo);
				changeValueRef(alRefRow.get(0).toString().trim(), uirp);

				break;
			}
		}
		// 对多限制参照的初始化
		if (m_htInitMultiRef.containsKey(sFieldCode)) {
			Object oRef = getValueRefObjectByFieldCode(sFieldCode);
			if (oRef instanceof UIRefCellEditor)
				oRef = ((UIRefCellEditor) oRef).getComponent();
			if (oRef != null && oRef instanceof UIRefPane) {
				UIRefPane uiRef = (UIRefPane) oRef;
				ArrayList alObj = m_htInitMultiRef.get(sFieldCode);
				String sBeforeWhereClause = (String) alObj.get(0);
				String sAfterWhereClause = (String) alObj.get(1);
				String[][] sKeys = (String[][]) alObj.get(2);
				boolean bIsNullUsed = ((UFBoolean) alObj.get(3)).booleanValue();
				StringBuffer sb = new StringBuffer();
				// sb.append(uiRef.getRefModel().getOriginWherePart());
				uiRef.getRefModel().setWherePart(
						uiRef.getRefModel().getOriginWherePart());
				if (sBeforeWhereClause != null
						&& sBeforeWhereClause.trim().length() > 0) {
					sb.append(sBeforeWhereClause);
				}
				boolean bFirstAddAnd = true;
				for (int i = 0; i < sKeys.length; i++) {
					String[] sValues = getValuesbyFieldCode(sKeys[i][0]);
					for (int j = 0; j < sValues.length; j++) {
						Object oValue = sValues[j];
						if (null != oValue
								&& oValue.toString().trim().length() != 0) {
							if (bFirstAddAnd) {
								sb.append(" 1=1 ");
								bFirstAddAnd = false;
							}
							sb.append(" and ").append(sKeys[i][1]).append(
									getConditionbyFieldCode(sKeys[i][0])[j]);
							sb.append("'").append(oValue).append("'");
						} else {
							if (bIsNullUsed) {
								if (bFirstAddAnd) {
									sb.append(" 1=1 ");
									bFirstAddAnd = false;
								}
								sb.append(" and (").append(sKeys[i][1]).append(
										"is null or len(ltrim(").append(
										sKeys[i][1]).append("))=0)");
								bIsNullUsed = false;
							}
						}
					}
				}
				if (sAfterWhereClause != null
						&& sAfterWhereClause.trim().length() > 0) {
					if (sb.toString().trim().length() == 0) {
						sb.append(" 1=1 " + sAfterWhereClause);
					} else {
						sb.append(sAfterWhereClause);
					}
				}

				if (null != sb && sb.toString().trim().length() > 0) {
					uiRef.getRefModel().addWherePart(" and " + sb.toString());
				}
				changeValueRef(sFieldCode, uiRef);
			}
		}

		super.setValueRefEditor(iRow, iCol); // 对自由项
		if (qcvo.getDataType().intValue() == 100) {
			FreeItemRefPane firpFreeItemRefPane = new FreeItemRefPane();
			String sFreeItemCode = qcvo.getFieldCode().trim();
			if (m_htFreeItemInv.containsKey(sFreeItemCode)) {
				String sInvIDCode = m_htFreeItemInv.get(sFreeItemCode)
						.toString().trim();
				String sInvID = "";
				sInvID = getPKbyFieldCode(sInvIDCode);
				if (m_htInvVO.containsKey(sInvID)) {
					InvVO ivo = m_htInvVO.get(sInvID);
					firpFreeItemRefPane.setFreeItemParam(ivo);
				}
			} // 置编辑器
			UIFreeItemCellEditor editor = new UIFreeItemCellEditor(
					firpFreeItemRefPane);
			getUITabInput().getColumnModel().getColumn(iCol).setCellEditor(
					editor);
		} else if (qcvo.getDataType().intValue() == 200) { // 对下拉框
			// do nothing

		} else if (qcvo.getDataType().intValue() == 300) { // 对统计结构
			UIRefPane srpStathRefPane = null;

			Object oRef = getValueRefObjectByFieldCode(sFieldCode);
			if (oRef instanceof UIRefCellEditor) {
				oRef = ((UIRefCellEditor) oRef).getComponent();
				if (oRef != null && oRef instanceof UIRefPane)
					srpStathRefPane = (UIRefPane) oRef;
			}

			if (srpStathRefPane == null) {
				try {
					srpStathRefPane = (UIRefPane) Class.forName(
							"nc.ui.ic.pub.stat.StathRefPane").newInstance();
				} catch (Exception e) {
					nc.vo.scm.pub.SCMEnv.out(e);
				}
				// 置编辑器
				UIRefCellEditor editorh = new UIRefCellEditor(
						(UIRefPane) srpStathRefPane);
				getUITabInput().getColumnModel().getColumn(iCol).setCellEditor(
						editorh);
			}

			if (m_htStatHtoCorp.containsKey(sFieldCode)) {
				String sCorpField = m_htStatHtoCorp.get(sFieldCode).toString()
						.trim();
				String sCorpValue = "";
				sCorpValue = getPKbyFieldCode(sCorpField);
				if (sCorpValue == null || sCorpValue.trim().length() == 0) {
					((UIRefPane) srpStathRefPane)
							.setWhereString("bicusedflag='Y'");
				} else {
					((UIRefPane) srpStathRefPane)
							.setWhereString("bicusedflag='Y' and pk_corp='"
									+ sCorpValue + "'");
				}
			}
		} else if (qcvo.getDataType().intValue() == 350) { // 对统计范围
			IStatbRefPane srpStatbRefPane = null;

			Object oRef = getValueRefObjectByFieldCode(sFieldCode);
			if (oRef instanceof UIRefCellEditor) {
				oRef = ((UIRefCellEditor) oRef).getComponent();
				if (oRef != null && oRef instanceof IStatbRefPane)
					srpStatbRefPane = (IStatbRefPane) oRef;
			}

			if (srpStatbRefPane == null) {
				try {

					srpStatbRefPane = (IStatbRefPane) Class.forName(
							"nc.ui.ic.pub.stat.StatbRefPane").newInstance();
				} catch (Exception e) {

					nc.vo.scm.pub.SCMEnv.out(e);
				}

				// 置编辑器
				UIRefCellEditor editorb = new UIRefCellEditor(
						(UIRefPane) srpStatbRefPane);
				getUITabInput().getColumnModel().getColumn(iCol).setCellEditor(
						editorb);
			}

			if (m_htStatBtoH.containsKey(sFieldCode)) {
				String sStatHCode = m_htStatBtoH.get(sFieldCode).toString()
						.trim();
				String sStatH = "";
				sStatH = getPKbyFieldCode(sStatHCode);
				srpStatbRefPane.setStathid(sStatH);
			}
		} else if (qcvo.getDataType().intValue() == 400) { // 对批次号
			UIRefPane lqrLotQueryRef = null;
			try {
				lqrLotQueryRef = (UIRefPane) Class.forName(
						"nc.ui.ic.pub.lotquery.LotQueryRef").newInstance();
			} catch (Exception e) {
				nc.vo.scm.pub.SCMEnv.out(e);
			}

			// LotQueryRef lqrLotQueryRef = new LotQueryRef();
			String sLot = qcvo.getFieldCode().trim();
			Object sLotValue = getUITabInput().getValueAt(iRow, iCol);
			if (m_htLotInv.containsKey(sLot)) {
				String sInvCode = m_htLotInv.get(sLot).toString().trim();
				String sInv = "";
				sInv = getPKbyFieldCode(sInvCode);
				String[] sInput = new String[] { sInv };
				((ILotQueryRef) lqrLotQueryRef).setParams(sInput);
				lqrLotQueryRef.setText((String) sLotValue);
				String sPK = "";
				for (int j = 0; j < getUITabInput().getRowCount(); j++) {
					int indexNow = getIndexes(j - getImmobilityRows());
					QueryConditionVO qcvoNow = getConditionDatas()[indexNow];
					if (qcvoNow.getFieldCode().trim().equals("pk_corp")) {
						sPK = getValueRefResults()[j] == null ? ""
								: getValueRefResults()[j].getRefPK().trim();
						lqrLotQueryRef
								.setWhereString(" bd_invbasdoc.discountflag='N' and bd_invbasdoc.laborflag='N' and bd_invmandoc.wholemanaflag='Y' "
										+ "and bd_invmandoc.sealflag ='N' and bd_invmandoc.pk_corp='"
										+ sPK + "'");
						break;
					}
				}
			} // 置编辑器
			UIRefCellEditor editor = new UIRefCellEditor(lqrLotQueryRef);
			getUITabInput().getColumnModel().getColumn(iCol).setCellEditor(
					editor);
			getUITabInput().setValueAt(sLotValue, iRow, iCol);
		} else if (qcvo.getDataType().intValue() == 5) { // 考虑处理辅计量
			if (m_htAstCorpInv.containsKey(sFieldCode)) {
				// 是辅计量参照
				String[] sCorpInv = m_htAstCorpInv.get(sFieldCode);
				String sCorp = sCorpInv[0];
				String sCorpID = "";
				String sInv = sCorpInv[1];
				String sInvID = "";
				sCorpID = getPKbyFieldCode(sCorp);
				sInvID = getPKbyFieldCode(sInv);
				UIRefPane refCast = new UIRefPane();
				refCast.setRefNodeName("计量档案");
				refCast.getRefModel().setSealedDataShow(true);
				m_voInvMeas.filterMeas(sCorpID, sInvID, refCast);
				UIRefCellEditor editor = new UIRefCellEditor(refCast);
				getUITabInput().getColumnModel().getColumn(iCol).setCellEditor(
						editor);
			}
		} // 由变动参照设置表重置参照
		boolean bFounded = false;
		boolean bActualize = false;
		Object Ref = null;
		for (int i = 0; i < m_alComboxToRef.size(); i++) {
			alRefRow = (m_alComboxToRef.get(i));
			if (alRefRow.get(1).toString().trim().equals(
					qcvo.getFieldCode().trim())) {
				String sSourceFieldCode = alRefRow.get(0).toString().trim();
				for (int j = 0; j < getConditionDatas().length; j++) {
					if (getConditionDatas()[j].getFieldCode().trim().equals(
							sSourceFieldCode)) {
						bFounded = true;
						Object oValue = getTabModelInput().getValueAt(
								getListRow(j), 4);
						Object[][] oRef = (Object[][]) alRefRow.get(2);
						for (int k = 0; k < oRef.length; k++) {
							if (oRef[k][0].equals(oValue)) {
								Ref = oRef[k][1];
								if (Ref instanceof UIRefCellEditor) {
									getUITabInput().getColumnModel().getColumn(
											iCol).setCellEditor(
											(UIRefCellEditor) Ref);
								} else if (Ref instanceof JCheckBox) {
									getUITabInput().getColumnModel().getColumn(
											iCol).setCellEditor(
											new DefaultCellEditor(
													(JCheckBox) Ref));
								} else if (Ref instanceof JComboBox) {
									getUITabInput().getColumnModel().getColumn(
											iCol).setCellEditor(
											new DefaultCellEditor(
													(JComboBox) Ref));
								} else if (Ref instanceof JTextField) {
									getUITabInput().getColumnModel().getColumn(
											iCol).setCellEditor(
											new DefaultCellEditor(
													(JTextField) Ref));
								} else if (Ref instanceof RefCellRender) {
									getUITabInput().getColumnModel().getColumn(
											iCol).setCellEditor(
											new UIRefCellEditor(
													(RefCellRender) Ref));
								} else if (Ref instanceof UIRefPane) {
									getUITabInput().getColumnModel().getColumn(
											iCol)
											.setCellEditor(
													new UIRefCellEditor(
															(UIRefPane) Ref));
								} else {
									getUITabInput().getColumnModel().getColumn(
											iCol).setCellEditor(null);
								}
								bActualize = true;
								break;
							}
						}
						break;
					}
				}
			}
		}
		if (bFounded && !bActualize) {
			getUITabInput().getColumnModel().getColumn(iCol)
					.setCellEditor(null);
		}

		return;
	}

	/**
	 * 此处插入方法说明:加载自定义项目 sDefInvBasAliasName：存货基本档案查询表别名 sDefInvManAliasName：
	 * 存货管理档案查询表别名
	 * 
	 * 查询item中的itemkey=查询条件表名的前缀+itemkey
	 * 
	 * 创建日期：(2003-9-27 11:10:48)
	 */
	public void showBillDefCondition(String sHeadDefAliasName,
			String sBodyDefAliasName) {

		Hashtable<String, String> htbInvDef = new Hashtable<String, String>();
		htbInvDef.put("供应链/ARAP单据头", sHeadDefAliasName);
		htbInvDef.put("供应链/ARAP单据体", sBodyDefAliasName);
		showDefCondition(htbInvDef);
		return;
	}

	/**
	 * 此处插入方法说明:加载自定义项目 sDefInvBasAliasName：存货基本档案查询表别名 sDefInvManAliasName：
	 * 存货管理档案查询表别名
	 * 
	 * 查询item中的itemkey=查询条件表名的前缀+itemkey
	 * 
	 * 创建日期：(2003-9-27 11:10:48)
	 */
	public void showDefCondition(String sDefInvBasAliasName,
			String sDefInvManAliasName) {

		Hashtable<String, String> htbInvDef = new Hashtable<String, String>();
		htbInvDef.put("存货档案", sDefInvBasAliasName);
		htbInvDef.put("存货管理档案", sDefInvManAliasName);
		showDefCondition(htbInvDef);
		return;
	}

	/**
	 * 此处插入方法说明:加载自定义项目--单据的表头和表体
	 * 
	 * 查询item中的itemkey=查询条件表名的前缀+itemkey AppendStyle:字段别名构成风格 1: 在原来字段名附加表名前缀
	 * ###.def 2: 在原来字段名附加后缀，def+"####" 如：vuserdef5h（vuserdef5：为原始字段名称） 3:
	 * 在原来字段名附加前缀，"####"+def 如：hvuserdef5
	 * 
	 * 创建日期：(2003-9-27 11:10:48)
	 */
	public void showDefCondition(Hashtable<String, String> htbDocumentRef) {

		UIRefPane[] paneDefs = null;
		try {
			StringBuffer sbSQL = new StringBuffer(" ");

			StringBuffer sbSQLDefault = new StringBuffer(" select ");
			sbSQLDefault
					.append(" a.fieldname,c.defname,b.objname,c.type,c.lengthnum,c.digitnum \n");
			sbSQLDefault
					.append(" from bd_defquote a,bd_defused b,bd_defdef c \n");
			sbSQLDefault.append(" where ");
			sbSQLDefault.append(" a.pk_defused = b.pk_defused \n");
			sbSQLDefault.append(" and ");
			sbSQLDefault.append(" c.pk_defdef = a.pk_defdef ");

			sbSQL.append(sbSQLDefault);
			sbSQL.append(" and (");
			java.util.Enumeration<String> en = htbDocumentRef.keys();
			int n = 0;
			while (en.hasMoreElements()) {
				if (n > 0)
					sbSQL.append(" or ");
				sbSQL.append(" b.objname = '" + en.nextElement().toString()
						+ "' \n");
				n++;
			}
			sbSQL.append(" ) ");
			sbSQL.append(" order by b.objname,a.fieldname ");

			String[][] sResult = nc.ui.scm.pub.CommonDataHelper.queryData(sbSQL
					.toString());
			Hashtable<String, UIRefPane> htDef = new Hashtable<String, UIRefPane>(); // key：FieldCode；Value：UIRefPane
			Vector<QueryConditionVO> vAddDef = new Vector<QueryConditionVO>(); // 不是在模板里定义的条件
			if (sResult != null) {
				paneDefs = new nc.ui.pub.beans.UIRefPane[sResult.length];
				for (int i = 0; i < sResult.length; i++) {
					String[] sTemp = sResult[i];
					if (sTemp.length > 1) {
						// 有数据
						String sFieldName = sTemp[0];
						String sDefName = sTemp[1];
						String sObjName = sTemp[2];
						String sDefType = sTemp[3];
						String sDigitNum = sTemp[4];
						int iLengthNum = 20;
						int iNum = 0;
						if (sTemp[4] != null && sTemp[4].length() != 0) {
							iLengthNum = new Integer(sDigitNum).intValue();
						}

						if (sTemp[5] != null && sTemp[5].length() != 0) {
							iNum = new Integer(sTemp[5]).intValue();
						}

						String sFieldCode = "";
						String sAlaisTable = null;

						if (sDefName != null && sDefName.length() > 0) {
							sAlaisTable = htbDocumentRef.get(sObjName);
							sDefName = sObjName + "-" + sDefName;
							if (sAlaisTable != null
									&& sAlaisTable.length() >= 0) {
								if (sObjName.equalsIgnoreCase("存货档案")
										|| sObjName.equalsIgnoreCase("存货管理档案"))
									sFieldCode = htbDocumentRef.get(sObjName)
											+ "." + sFieldName;
								else if (sObjName
										.equalsIgnoreCase("供应链/ARAP单据头")) {
									sFieldCode = "vuser" + sFieldName
											+ htbDocumentRef.get(sObjName);
								} else if (sObjName
										.equalsIgnoreCase("供应链/ARAP单据体")) {
									sFieldCode = "vuser" + sFieldName;
								} else
									sFieldCode = sFieldName;
							}

							vAddDef.addElement(getDefaultQueryCndVO(sFieldCode,
									sDefName));
							// 数组从0开始，自定义项由1开始
							if (sDefType.equals("统计")) {
								// 设置参照
								String sWhereString = " and ";
								sWhereString = sWhereString + " pk_defdef = ";
								sWhereString = sWhereString + "(";
								sWhereString = sWhereString + " select ";
								sWhereString = sWhereString + " a.pk_defdef ";
								sWhereString = sWhereString + " from ";
								sWhereString = sWhereString
										+ " bd_defquote a,bd_defused b ";
								sWhereString = sWhereString + " where ";
								sWhereString = sWhereString
										+ " a.pk_defused = b.pk_defused ";
								sWhereString = sWhereString + " and ";
								sWhereString = sWhereString + " b.objname = '"
										+ sObjName + "'";
								sWhereString = sWhereString + " and ";
								sWhereString = sWhereString
										+ " a.fieldname = '" + sFieldName + "'";
								sWhereString = sWhereString + " ) ";
								paneDefs[i] = new UIRefPane();
								paneDefs[i].setRefNodeName("自定义项档案");
								paneDefs[i].getRef().getRefModel()
										.addWherePart(sWhereString);
							} else if (sDefType.equals("日期")) {
								paneDefs[i] = new UIRefPane();
								paneDefs[i].setRefNodeName("日历");
							} else if (sDefType.equals("备注")) {
								paneDefs[i] = new UIRefPane();
								paneDefs[i].setMaxLength(iLengthNum);
								paneDefs[i].setButtonVisible(false);
							} else if (sDefType.equals("数字")) {
								paneDefs[i] = new UIRefPane();
								paneDefs[i].setTextType("TextDbl");
								paneDefs[i].setButtonVisible(false);
								paneDefs[i].setMaxLength(iLengthNum);
								paneDefs[i].setNumPoint(iNum);
							}
							htDef.put(sFieldCode, paneDefs[i]);
						}

					}
				}
			} // 设置没有引用的自定义项不显示
			QueryConditionVO[] vos = getConditionDatas();
			Vector<QueryConditionVO> vTemp = new Vector<QueryConditionVO>();
			for (int i = 0; i < vos.length; i++) {
				String sCode = vos[i].getFieldCode();
				if (sCode.indexOf("def") < 0) {
					vTemp.addElement(vos[i]);
					continue;
				} // 屏蔽 by hanwei 避免把上次加入的自定义项隐藏2003-10-22
				// if (!htDef.containsKey(sCode)) {
				// //vos[i].setIfDefault(new UFBoolean(false));
				// continue;
				// }
				vTemp.addElement(vos[i]);
			} // 加入模板中没有的自定义查询条件
			for (int j = 0; j < vAddDef.size(); j++) {
				vTemp.addElement(vAddDef.elementAt(j));
			}
			QueryConditionVO[] vosResult = new QueryConditionVO[vTemp.size()];
			vTemp.copyInto(vosResult);
			// 重新设置模板数据
			setConditionDatas(vosResult);
			// 设置参照
			Enumeration<String> e = htDef.keys();
			while (e.hasMoreElements()) {
				String sKey = e.nextElement();
				setValueRef(sKey, htDef.get(sKey));
			}

		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out(e);
		}

		return;
	}

	/**
	 * 此处插入方法说明:加载自定义项目 sDefInvBasAliasName：存货基本档案查询表别名 sDefInvManAliasName：
	 * 存货管理档案查询表别名
	 * 
	 * 查询item中的itemkey=查询条件表名的前缀+itemkey
	 * 
	 * 创建日期：(2003-9-27 11:10:48)
	 */
	public void showInvDefCondition(String sDefInvBasAliasName,
			String sDefInvManAliasName) {

		Hashtable<String, String> htbInvDef = new Hashtable<String, String>();
		htbInvDef.put("存货档案", sDefInvBasAliasName);
		htbInvDef.put("存货管理档案", sDefInvManAliasName);
		showDefCondition(htbInvDef);
		return;
	}

	/**
	 * 创建人：王乃军
	 * 
	 * 功能：是否显示打印状态PANEL
	 * 
	 * 参数：
	 * 
	 * 返回：
	 * 
	 * 异常：
	 * 
	 * 日期：(2004-11-25 16:27:37) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @param isShowPrintStatusPanel
	 *            boolean
	 */
	public void setShowPrintStatusPanel(boolean isShowPrintStatusPanel) {
		if (isShowPrintStatusPanel)
			this.getUITabbedPane().insertTab(getUIPanelPrintStatus().getName(),
					null, getUIPanelPrintStatus(), null,
					getUITabbedPane().getTabCount());
		else if (m_isShowPrintStatusPanel)// 已经显示过了，现在要去掉显示
			this.getUITabbedPane().removeTabAt(
					getUITabbedPane().getTabCount() - 1);
		// else do nothing.
		m_isShowPrintStatusPanel = isShowPrintStatusPanel;
		// getUIPanelPrintStatus().setVisible(isShowPrintStatusPanel);
	}

	/**
	 * 创建人：王乃军
	 * 
	 * 功能：是否显示打印状态PANEL
	 * 
	 * 参数：
	 * 
	 * 返回：
	 * 
	 * 异常：
	 * 
	 * 日期：(2004-11-25 16:27:37) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return boolean
	 */
	private boolean isShowPrintStatusPanel() {
		return m_isShowPrintStatusPanel;
	}

	/**
	 * 返回 打印状态PANE。
	 * 
	 * @return nc.ui.pub.beans.UIPanel
	 */
	/* 警告：此方法将重新生成。 */
	private QryPrintStatusPanel getUIPanelPrintStatus() {
		if (m_QryPrintStatusPanel == null) {
			try {
				m_QryPrintStatusPanel = new QryPrintStatusPanel();
				m_QryPrintStatusPanel
						.setName(nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"common", "UC000-0001993")/* @res "打印状态" */);
				m_QryPrintStatusPanel.setLayout(null);
				// user code begin {1}
				m_QryPrintStatusPanel.setVisible(false);// default is invisible.
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return m_QryPrintStatusPanel;
	}

	/**
	 * 得到打印条件输入面板的用户选择的结果。
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例： QryPrintStatusPanel.SEL_ALL=-1;//全部
	 * QryPrintStatusPanel.SEL_NOT_PRINTED= 0;//没打过的
	 * QryPrintStatusPanel.SEL_PRINTED= 1;//打过的
	 * <p>
	 * <b>参数说明</b>
	 * 
	 * @return
	 * <p>
	 * @author czp
	 * @time 2007-3-5 下午02:53:33
	 */
	public int getPrintStatus() {
		return getUIPanelPrintStatus().getStatus();
	}

	/**
	 * 根据打印条件输入面板的用户选择的结果及表别名，返回查询条件串。
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * 
	 * @param sTableAlias
	 * @return
	 *            <p>
	 * @author czp
	 * @time 2007-3-5 下午02:56:46
	 */
	public String getPrintResult(String sTableAlias) {

		int iSelRslt = getPrintStatus();

		// “全部”，则等效于不限制
		if (iSelRslt == QryPrintStatusPanel.SEL_ALL) {
			return null;
		}
		// default is ALL.
		String sResSql = "";

		if (sTableAlias == null) {
			sTableAlias = "";
		} else {
			sTableAlias = sTableAlias.trim() + ".";
		}
		// “打印过”
		if (iSelRslt == QryPrintStatusPanel.SEL_PRINTED) {
			sResSql = sResSql + sTableAlias + PrintConst.IPrintCount + ">0 ";
		}
		// “未打印过”
		else if (iSelRslt == QryPrintStatusPanel.SEL_NOT_PRINTED) {
			sResSql = sResSql + " (" + sTableAlias + PrintConst.IPrintCount
					+ "<=0 OR " + sTableAlias + PrintConst.IPrintCount
					+ " IS NULL ) ";
		}

		return sResSql;
	}

	/**
	 * 创建人：王乃军
	 * 
	 * 功能： 得到打印条件VO
	 * 
	 * 参数：
	 * 
	 * 返回：
	 * 
	 * 异常：
	 * 
	 * 日期：(2004-11-25 16:33:33) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return nc.vo.pub.query.ConditionVO
	 */
	private ConditionVO getPrintStatusCondVO() {
		if (!isShowPrintStatusPanel())
			return null;
		else {
			int iStatus = getUIPanelPrintStatus().getStatus();
			// 选择全部的话，忽略这个条件
			if (iStatus != QryPrintStatusPanel.SEL_PRINTED
					&& iStatus != QryPrintStatusPanel.SEL_NOT_PRINTED) {
				nc.vo.scm.pub.SCMEnv.out("PRINTCOUNT IGNORED");
				return null;
			}
			// else
			ConditionVO voCond = new ConditionVO();
			voCond.setFieldCode(nc.vo.scm.print.PrintConst.IPrintCount);
			voCond.setDataType(ConditionVO.INTEGER);
			if (iStatus == QryPrintStatusPanel.SEL_PRINTED) {
				voCond.setOperaCode(">");
				voCond.setValue("0");
				nc.vo.scm.pub.SCMEnv.out("PRINTCOUNT>0");
			} else { // NOT_PRINTED
				voCond.setFieldCode("ISNULL("
						+ nc.vo.scm.print.PrintConst.IPrintCount + ",0)");
				voCond.setOperaCode("=");
				voCond.setValue("0");
				nc.vo.scm.pub.SCMEnv.out("PRINTCOUNT=0");
			}
			return voCond;
		}

	}

	/**
	 * @return 返回 m_isDataPowerSqlReturned。
	 */
	public boolean isDataPowerSqlReturned() {
		return m_isDataPowerSqlReturned;
	}

	/**
	 * 给定模板field_code值，判断是否真正存在于模板中(本方法为支持传入不存在的模板field_code设计)
	 * 
	 * @since v50
	 * @author czp
	 * @date 2006-09-19
	 */
	public boolean isExistFieldCode(String strFieldCode) {
		if (strFieldCode == null || strFieldCode.trim().length() == 0) {
			return false;
		}
		QueryConditionVO[] voaAllTempateDatas = getAllTempletDatas();
		int iLen = voaAllTempateDatas.length;
		for (int i = 0; i < iLen; i++) {
			if (voaAllTempateDatas[i] == null
					|| voaAllTempateDatas[i].getFieldCode() == null) {
				continue;
			}
			if (strFieldCode.trim().equalsIgnoreCase(
					voaAllTempateDatas[i].getFieldCode().trim())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 初始化是否启用数据权限的数据
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * 
	 * @param vecTableNames
	 * @param vecTableShowNames
	 * @param pk_corp
	 *            <p>
	 * @author donggq
	 * @time 2009-8-7 下午02:02:47
	 */
	// private void initUsedDataPowerInfo(Vector<String>
	// vecTableNames,Vector<String> vecRefNames,String pk_corp){
	// if(vecTableNames == null
	// || vecRefNames == null
	// || pk_corp == null){
	// return;
	// }
	// String strKey ,sTableName, sRefName ;
	// Vector<String> vTableNames = new Vector<String>();
	// Vector<String> vRefNames = new Vector<String>();
	// for(int i = 0; i < vecTableNames.size(); i ++){
	// sTableName = vecTableNames.get(i);
	// sRefName = vecRefNames.get(i);
	// strKey = sTableName + sRefName + pk_corp;
	// if(!m_mapPowerEnableCache.containsKey(strKey)){
	// vTableNames.add(sTableName);
	// vRefNames.add(sRefName);
	// }
	// }
	// HashMap<String,UFBoolean> hmRet = new HashMap<String, UFBoolean>();
	// try{
	// if(vTableNames.size() > 0){
	// IScmPub iScmPubService = NCLocator.getInstance().lookup(IScmPub.class);
	// hmRet = iScmPubService.queryUsedDataPower(vTableNames, vRefNames,
	// pk_corp,ClientEnv.getInstance().getPk_user() );
	// }
	// }catch(BusinessException e){
	// SCMEnv.out(e);
	// }
	// m_mapPowerEnableCache.putAll(hmRet);
	// }
	/**
	 * 从缓存中获取资源是否启用了权限，如果缓存中没有则查询并缓存之
	 * 
	 * @param dpTableName
	 * @param sRefName
	 * @param sCurUser
	 * @param pk_corps
	 * @return
	 * @author czp
	 * @date 2006-10-30
	 */
	// public boolean isUsedDataPower(String dpTableName, String sRefName,
	// String pk_corp){
	// if(dpTableName == null
	// || sRefName == null
	// || pk_corp == null){
	// return false;
	// }
	// String strKey = dpTableName + sRefName + pk_corp;
	// if(m_mapPowerEnableCache.containsKey(strKey)){
	// return m_mapPowerEnableCache.get(strKey).booleanValue();
	// }
	// boolean bEnable = false;
	//		
	// try{
	// bEnable = DataPowerServ.isUsedDataPower(dpTableName, sRefName, pk_corp);
	// }catch(Exception e){
	// SCMEnv.out(e);
	// return false;
	// }
	//		
	// m_mapPowerEnableCache.put(strKey, new UFBoolean(bEnable));
	//		
	// return bEnable;
	// }
	/**
	 * 从缓存中获取权限查询条件串，如果缓存中没有则查询并缓存之
	 * 
	 * @param dpTableName
	 * @param sRefName
	 * @param sCurUser
	 * @param pk_corps
	 * @return
	 * @author czp
	 * @date 2006-10-30
	 */
	public String getPowerSubSql(String dpTableName, String sRefName,
			String sCurUser, String[] pk_corps) {
		if (dpTableName == null || sRefName == null || sCurUser == null
				|| pk_corps == null || pk_corps.length == 0) {
			return null;
		}
		String strCorps = pk_corps[0];
		for (int i = 1; i < pk_corps.length; i++) {
			strCorps += pk_corps[i];
		}
		String strKey = dpTableName + sRefName + sCurUser + strCorps;
		m_mapPowerSubSqlCache = new HashMap<String, String>();
		// if(m_mapPowerSubSqlCache.containsKey(strKey)){
		// return
		// PuPubVO.getString_TrimZeroLenAsNull(m_mapPowerSubSqlCache.get(strKey));
		// }

		// DataPowerServ DPServ = DataPowerServ.getInstance();
		String strPowerSubSql = DataPowerHelp.getSubSqlForMutilCorpFromUICache(
				dpTableName, sRefName, sCurUser, pk_corps);

		// if ( pk_corps.length == 1 ) { // 单公司权限调用前台缓存，降低连接数，songhy，start
		// try {
		// Object[][] retValues = CacheTool.getAnyValue2(dpTableName,
		// "resource_id", "resource_name='" + sRefName + "'");
		// if ( (retValues != null) && (retValues.length > 0)
		// && (retValues[0] != null) && (retValues[0].length > 0) ) {
		// UserPowerQueryVO queryVO = new UserPowerQueryVO();
		// queryVO.setResouceId( (Integer)retValues[0][0] );
		// queryVO.setUserPK( sCurUser );
		// queryVO.setCorpPK( pk_corps[0] );
		// queryVO.setOrgPK( pk_corps[0] );
		//					
		// strPowerSubSql =
		// PowerClientService.getInstance().getUserPowerSql(queryVO);
		// }
		// }
		// catch (Exception ex) {
		// nc.vo.scm.pub.SCMEnv.out(ex.getMessage());;
		// }
		// }
		// else { // 多公司权限调用后台查询，连接数过高，songhy，end
		// strPowerSubSql = DPServ.getSubSqlForMutilCorp(dpTableName, sRefName,
		// sCurUser,pk_corps);
		// }

		m_mapPowerSubSqlCache.put(strKey, strPowerSubSql == null ? ""
				: strPowerSubSql);

		return strPowerSubSql;
	}

	/**
	 * 取得模板中配置了要控制数据权限的参照的权限控制sql
	 * 
	 * @author guyan
	 * @Data 2006-03-25
	 * @param sCurUser
	 *            当前操作员id
	 * @param pk_corps
	 *            需要取得权限的公司pk pk_corps[0]必须为当前公司pkcorp
	 * @param sRefNames
	 *            需要取得权限的参照编码（参照编码应该是uap定义的汉字名称，例如：库存组织）
	 *            目前支持的参照有：库存组织，仓库档案，客商档案， 客户档案，供应商档案，部门档案，人员档案
	 * @param sRefSqlFieldCodes
	 *            在查询语句中该参照对应的字段名称 例如：tableA.invcode
	 * @return iReturntypes 对应参照返回值是编码，名称还是pk
	 */
	public void setRefsDataPowerConVOs(String sCurUser, String[] pk_corps,
			String[] sRefNames, String[] sRefSqlFieldCodes, int[] iReturntypes) {
		try {
			ConditionVO[] cVos = null;
			if (pk_corps != null && pk_corps.length > 0 && sRefNames != null
					&& sRefNames.length > 0 && sRefSqlFieldCodes != null
					&& sRefSqlFieldCodes.length > 0 && iReturntypes != null
					&& iReturntypes.length > 0
					&& sRefNames.length == sRefSqlFieldCodes.length
					&& sRefSqlFieldCodes.length == iReturntypes.length) {
				// 判断那些参照在模板中配置了权限
				Hashtable<String, QueryConditionVO> htCtrRefDataPower = new Hashtable<String, QueryConditionVO>();
				QueryConditionVO[] voaConData = getConditionDatas();
				QueryConditionVO[] qcVOs = getConditionDatas();
				Map<String, QueryConditionVO> queryCondMap = new HashMap<String, QueryConditionVO>();
				for (QueryConditionVO vo : qcVOs) {
					if(!vo.getAddIsNull4Power().booleanValue()){
					queryCondMap.put(vo.getFieldCode(), vo);
					}
				}
				for (int i = 0; i < voaConData.length; i++) {

					/*
					 * by czp, 2006-09-22,作以下调整 1)、以调用者传递参数为准， 不必要求 : 数据库中必须
					 * data_type = 5； 2)、原来按 consultcode 匹配，调整为按field_code匹配 ，
					 * 不必要求 ：数据库中 consult = "档案参照名称"
					 */
					/*
					 * if (voaConData[i].getDataType().intValue() ==
					 * nc.vo.pub.query.IQueryConstants.UFREF &&
					 */
					if (voaConData[i].getIfDataPower() != null
							&& voaConData[i].getIfDataPower().booleanValue()) {
						// htCtrRefDataPower.put(voaConData[i].getFieldCode(),"Y");
						// linwm
						htCtrRefDataPower.put(voaConData[i].getFieldCode(),
								voaConData[i]);
					}
				}
				// 对需要进行权限控制的参照提供子查询
				if (htCtrRefDataPower.size() > 0) {
					// 组织需要查询是否启用数据权限的数据
					Vector<String> vecTableNames = new Vector<String>();
					Vector<String> vecRefNames = new Vector<String>();
					for (int i = 0; i < sRefNames.length; i++) {
						// 传入的filed_code不在模板中，则不处理
						if (!isExistFieldCode(sRefSqlFieldCodes[i])) {
							continue;
						}
						if (htCtrRefDataPower.get(sRefSqlFieldCodes[i]) != null) {
							String dpTableName = nc.ui.bd.datapower.DataPowerServ
									.getRefTableName(sRefNames[i]); // 根据参照名称获得基础数据表名
							vecTableNames.add(dpTableName);
							vecRefNames.add(sRefNames[i]);
						}
					}
					// 初始化是否启用数据权限的数据
					// initUsedDataPowerInfo(vecTableNames,vecRefNames,pk_corps[0]);

					Vector<ConditionVO> vecVO = new Vector<ConditionVO>();
					for (int i = 0; i < sRefNames.length; i++) {
						// 传入的filed_code不在模板中，则不处理
						if (!isExistFieldCode(sRefSqlFieldCodes[i])) {
							continue;
						}
						if (htCtrRefDataPower.get(sRefSqlFieldCodes[i]) != null) {
							String dpTableName = nc.ui.bd.datapower.DataPowerServ
									.getRefTableName(sRefNames[i]); // 根据参照名称获得基础数据表名
							// 如果该参照没有启用权限，不处理
							// if (!isUsedDataPower(dpTableName, sRefNames[i],
							// pk_corps[0])) {
							// continue;
							// }
							String strPowerSubSql = getPowerSubSql(dpTableName,
									sRefNames[i], sCurUser, pk_corps);
							if (StringUtil.isEmptyWithTrim(strPowerSubSql))
								continue;

							// 目前支持部分基础数据权限
							String sField = getRefField(sRefNames[i],
									iReturntypes[i]);
							if (sField == null) {
								continue;
							}

							// linwm
							QueryConditionVO cvo = htCtrRefDataPower
									.get(sRefSqlFieldCodes[i]);
							RefResultVO ref = new RefResultVO();
							ref.setRefCode(sRefNames[i]);
							ref.setRefName(sRefNames[i]);
						
							
								String fieldCode =sRefSqlFieldCodes[i];
								if (!queryCondMap.containsKey(fieldCode)) {															
							ConditionVO vo1 = new ConditionVO();
							vo1.setFieldCode(sRefSqlFieldCodes[i]);
							vo1.setDataType(INTEGER);
							vo1.setOperaCode(" is ");
							vo1.setValue(" NULL ");
							vo1.setNoLeft(false);

							// linwm
							vo1.setTableCode(cvo.getTableCode());
							vo1.setTableName(cvo.getTableName());
							vo1.setRefResult(ref);
							vecVO.addElement(vo1);
								}

							ConditionVO vo = new ConditionVO();
							vo.setFieldCode(sRefSqlFieldCodes[i]);
							vo.setDataType(INTEGER);
							vo.setOperaCode("in");

							// linwm
							vo.setTableCode(cvo.getTableCode());
							vo.setTableName(cvo.getTableName());
							vo.setRefResult(ref);
							if (iReturntypes[i] == RETURNPK)
								vo.setValue(strPowerSubSql);
							else {
								StringBuffer subSql = new StringBuffer(
										" (select ");
								subSql.append(sField).append(
										getRefField(sRefNames[i], -1)).append(
										" and "
												+ getRefField(sRefNames[i],
														RETURNPK)).append(
										" in ").append(strPowerSubSql).append(
										")");
								vo.setValue(subSql.toString());
							}
							if (!queryCondMap.containsKey(fieldCode)){
							vo.setNoRight(false);
							vo.setLogic(false);
							}
						
							
							vecVO.addElement(vo);
						}
					}
					if (vecVO != null && vecVO.size() > 0) {
						cVos = new ConditionVO[vecVO.size()];
						vecVO.toArray(cVos);
					}
				}
			}

			m_cCtrTmpDataPowerVOs = cVos;
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out(e.getMessage());
			m_cCtrTmpDataPowerVOs = null;
		}
	}

	public void setCtrTmpDataPowerVOs(ConditionVO[] cCtrTmpDataPowerVOs) {
		m_cCtrTmpDataPowerVOs = cCtrTmpDataPowerVOs;
	}

	public ConditionVO[] getCtrTmpDataPowerVOs() {
		return m_cCtrTmpDataPowerVOs;
	}

	/*
	 * 这里加判断条件的目的是：当需要多公司条件下控制权限时，使用的权限vo是m_cCtrTmpDataPowerVOs
	 * 从super.getConditionVO中得到的vo存在权限子查询vo，但是不是为了多公司的，所以说自查询相对于多公司来说是错误的
	 * 这里要做的工作时要过滤掉此相关的vo，然后拼接上多公司的m_cCtrTmpDataPowerVOs
	 */
	private ConditionVO[] setCVO4MulCoPower(ConditionVO[] voaCond) {
		if (m_cCtrTmpDataPowerVOs != null && m_cCtrTmpDataPowerVOs.length > 0) {

			Vector<ConditionVO> v = new Vector<ConditionVO>();
			if (voaCond != null) {
				for (int i = 0; i < voaCond.length; i++) {
					ConditionVO newcon = voaCond[i];
					if (newcon != null) {
						String strtemp = newcon.getValue();
						if (strtemp == null
								|| strtemp.toLowerCase().startsWith(
										VariableConst.PREFIX_OF_DATAPOWER_SQL))// by
																				// chao,
																				// 多处用到，定义为常量
																				// ,
																				// 2006-09-21
							continue;
						else
							v.add(newcon);

					}
				}
			}
//			QueryConditionVO[] qcVOs = getConditionDatas();
//			Map<String, QueryConditionVO> queryCondMap = new HashMap<String, QueryConditionVO>();
//			for (QueryConditionVO vo : qcVOs) {
//				queryCondMap.put(vo.getFieldCode(), vo);
//			}
			for (int i = 0; i < m_cCtrTmpDataPowerVOs.length; i++) {
//				String fieldCode = m_cCtrTmpDataPowerVOs[i].getFieldCode();
//				if (m_cCtrTmpDataPowerVOs[i].getValue()!=null&&queryCondMap.containsKey(fieldCode)
//						&& !queryCondMap.get(fieldCode).getAddIsNull4Power()
//								.booleanValue()
//						&& "is".equalsIgnoreCase(m_cCtrTmpDataPowerVOs[i]
//								.getOperaCode().trim())
//						&& "NULL".equalsIgnoreCase(m_cCtrTmpDataPowerVOs[i]
//								.getValue().trim())) {
//					continue;
//
//				}
				v.add(m_cCtrTmpDataPowerVOs[i]);
			}
			ConditionVO[] newCondition = new ConditionVO[v.size()];
			v.copyInto(newCondition);
			return newCondition;
		} else
			return voaCond;
	}

	public ConditionVO[] getDataPowerConVOs(String corpValue, String[] refcodes) {

		if (corpValue == null)
			return null;

		if (refcodes == null) {
			if (m_alpower != null && m_alpower.size() > 0) {
				refcodes = new String[m_alpower.size()];
				m_alpower.toArray(refcodes);

			}

		}

		if (refcodes == null)
			return null;

		HashMap<String, String> hmRef = new HashMap<String, String>();

		for (int i = 0; i < refcodes.length; i++)
			hmRef.put(refcodes[i], refcodes[i]);

		HashMap<String, QueryConditionVO> hmPoweredConditionVO = new HashMap<String, QueryConditionVO>();// 设置了权限的条件VO：WNJ2005-04-27

		String strDef = null; // 模版中的项目标识（字段 or（表+字段））

		// 得到所有模版中基础数据参照类型并使用数据权限的项目
		for (int i = 0; i < getConditionDatas().length; i++) {
			QueryConditionVO vo = getConditionDatas()[i];
			if (!hmRef.containsKey(getFldCodeByPower(vo)))
				continue;
			if (isDataPwr(vo))
				hmPoweredConditionVO.put(getFldCodeByPower(vo), vo);
		}

		HashMap<String, String[]> hmRefCorp= getRefCorp();
		Vector<ConditionVO> vecVO = new Vector<ConditionVO>();
		Map<String, String> mapSQL =
      getSubSqlForMutiCorpCtrl(hmRefCorp, hmPoweredConditionVO,
          refcodes);
		for (int i = 0; i < refcodes.length; i++) {
			String fieldcode = refcodes[i];
			QueryConditionVO voQuery = hmPoweredConditionVO.get(fieldcode);
			if (voQuery == null) {
				nc.vo.scm.pub.SCMEnv
						.out("@@@@没有QueryConditionVO::" + fieldcode);
				continue;
			}
			String sRefNodeName = voQuery.getConsultCode();
      String insql = mapSQL.get(sRefNodeName);
			appendPowerCons(vecVO, voQuery, insql);
		}
		// 组织带权限的查询条件VO并返回
		if (vecVO.size() > 0) {
			ConditionVO[] voRets = new ConditionVO[vecVO.size()];
			vecVO.copyInto(voRets);
			return voRets;
		}

		return null;
	}

	private void appendPowerCons(Vector<ConditionVO> vecVO,
      QueryConditionVO voQuery, String insql) {

    if (insql == null || insql.trim().length() <= 0)
      return;

    String fieldcode = voQuery.getFieldCode();

    if (voQuery.getAddIsNull4Power().booleanValue()) {
      ConditionVO vo1 = new ConditionVO();
      vo1.setFieldCode(fieldcode);
      vo1.setDataType(INTEGER);
      vo1.setOperaCode(" is NULL ");
      vo1.setNoLeft(false);
      vecVO.addElement(vo1);
    }

    ConditionVO vo = new ConditionVO();
    vo.setTableName(voQuery.getTableName());
    vo.setTableCode(voQuery.getTableCode());
    vo.setFieldCode(fieldcode);
    vo.setDataType(INTEGER);
    vo.setOperaCode("in");
    if (voQuery.getReturnType().intValue() == RETURNPK)
      vo.setValue(insql);
    else {
      String sRefNodeName = voQuery.getConsultCode();
      StringBuffer subSql = new StringBuffer(" (select ");
      String sField = getRefField(sRefNodeName, voQuery.getReturnType()
          .intValue());
      subSql.append(sField).append(getRefField(sRefNodeName, -1)).append(
          " and " + getRefField(sRefNodeName, RETURNPK))
      // 修改人：刘家清 修改时间：2009-10-21 下午03:46:58 修改原因：对于未知SQL用in子句，一定得加上()
          .append(" in (").append(insql).append(")) ");

      vo.setValue(subSql.toString());
    }

    if (voQuery.getAddIsNull4Power().booleanValue()) {
      vo.setNoRight(false);
      vo.setLogic(false);
    }

    vecVO.addElement(vo);
  }
	
	private void appendPowerCons_old(Vector<ConditionVO> vecVO,
			QueryConditionVO voQuery, String[] corpValues) {

		// try {

		String insql = getMultiCorpsPowerSql(voQuery, corpValues);

		if (insql == null || insql.trim().length() <= 0)
			return;

		// 如果不是“真正的”数据权限串，则忽略此条件。By Ydy、Czp：2007-04-24
		if (insql.indexOf(VariableConst.PREFIX_OF_DATAPOWER_SQL) < 0) {
			return;
		}

		String fieldcode = voQuery.getFieldCode();

		// ydy 2005-06-30 加上 (** is null or ** in ())
		// if(!(sRefNodeName.startsWith("存货"))){
		// 通过参数控制是否要加is null（孔晓东）
		if (voQuery.getAddIsNull4Power().booleanValue()) {
			ConditionVO vo1 = new ConditionVO();
			vo1.setFieldCode(fieldcode);
			vo1.setDataType(INTEGER);
			vo1.setOperaCode(" is NULL ");
			// vo1.setValue(" NULL ");
			vo1.setNoLeft(false);

			vecVO.addElement(vo1);
		}
		// }

		ConditionVO vo = new ConditionVO();
		vo.setTableName(voQuery.getTableName());
		vo.setTableCode(voQuery.getTableCode());
		vo.setFieldCode(fieldcode);
		vo.setDataType(INTEGER);
		vo.setOperaCode("in");
		if (voQuery.getReturnType().intValue() == RETURNPK)
			vo.setValue(insql);
		else {
			String sRefNodeName = voQuery.getConsultCode();
			StringBuffer subSql = new StringBuffer(" (select ");
			String sField = getRefField(sRefNodeName, voQuery.getReturnType()
					.intValue());
			subSql.append(sField).append(getRefField(sRefNodeName, -1)).append(
					" and " + getRefField(sRefNodeName, RETURNPK))
			// 修改人：刘家清 修改时间：2009-10-21 下午03:46:58 修改原因：对于未知SQL用in子句，一定得加上()
					.append(" in (").append(insql).append(")) ");

			vo.setValue(subSql.toString());
		}

		if (voQuery.getAddIsNull4Power().booleanValue()) {
			vo.setNoRight(false);
			vo.setLogic(false);
		}

		vecVO.addElement(vo);
		// } catch (Exception e) {
		// // nc.vo.scm.pub.SCMEnv.out("获取" + info[0] + "对应的" + info[1] +
		// // "的数据权限出错，可能不是问题。");
		// nc.vo.scm.pub.SCMEnv.out(e);
		// }

	}

	private String getFldCodeByPower(QueryConditionVO vo) {
		String strDef = vo.getFieldCode();
		// 为了知道权限参照的返回类型：PK,code,name
		if (m_alpower.contains(strDef)) {
			return strDef;

		} else {
			strDef = vo.getTableName() + "." + vo.getTableCode();
			if (m_alpower.contains(strDef))
				return strDef;

		}

		return null;
	}

	private String getMultiCorpsPowerSql(QueryConditionVO voQuery,
			String[] corps) {

		nc.ui.bd.datapower.DataPowerServ DPServ = nc.ui.bd.datapower.DataPowerServ
				.getInstance();
		String sCurUser = getClientEnvironment().getUser().getPrimaryKey();
		String sRefNodeName = voQuery.getConsultCode();

		String dpTableName = null;
		try {
			dpTableName = nc.ui.bd.datapower.DataPowerServ
					.getRefTableName(sRefNodeName); // 根据参照名称获得基础数据表名
		} catch (Exception e) {

		}

		if (dpTableName == null)
			return null;

		// ArrayList<String> alnewCorp=new ArrayList<String>();
		// if (corps != null ) {
		// // 如果该参照没有启用权限，不处理
		// boolean ispower=false;
		// for(int m=0;m<corps.length;m++){
		// if (isUsedDataPower(dpTableName,sRefNodeName, corps[m])){
		// ispower=true;
		// alnewCorp.add(corps[m]);
		// //break;
		// }
		//				
		// }
		// if(!ispower)
		// return null;
		// }

		String insql = DataPowerHelp.getSubSqlForMutilCorpFromUICache(
				dpTableName, sRefNodeName, sCurUser, corps);
		// DPServ.getSubSqlForMutilCorp(dpTableName,
		// sRefNodeName, sCurUser, corps);

		return insql;
	}

	private boolean isDataPwr(QueryConditionVO vo) {

		if (vo.getIfUsed().booleanValue()
				&& (isDataPower() || vo.getIfDataPower().booleanValue()) // 设置了全局启用数据权限或者此项启用
				&& vo.getDataType().intValue() == UFREF
				&& (vo.getConsultCode() != null
						&& !vo.getConsultCode().trim().equals("-99")
						&& !vo.getConsultCode().startsWith("<") && !vo
						.getConsultCode().startsWith("{"))) {
			return true;

		}

		return false;
	}

	/**
	 * 
	 * 
	 * @return nc.ui.pub.beans.UIPanel
	 */
	/* 警告：此方法将重新生成。 */
	private BillRefModeSelPanel getBillRefModeSelPanel() {
		if (m_billRefModeSelPanel == null) {
			try {
				m_billRefModeSelPanel = new BillRefModeSelPanel();
				m_billRefModeSelPanel
						.setName(nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"SCMCOMMON", "UPPSCMCommon-000501")/* @res"单据参照显示形式" */);
				m_billRefModeSelPanel.setLayout(null);
				// user code begin {1}
				m_billRefModeSelPanel.setVisible(false);// default is invisible.
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
			}
		}
		return m_billRefModeSelPanel;
	}

	public boolean isShowDoubleTableRef() {
		if (m_isShowBillRefModeSelPanel)
			return getBillRefModeSelPanel().isShowDoubleTableRef();
		return true;
	}

	/**
	 * 创建人： *
	 * 
	 * 功能：显示参照选择面板
	 * 
	 * 参数：
	 * 
	 * 返回：
	 * 
	 * 异常：
	 * 
	 * 日期：(2004-11-25 16:27:37) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @param isShowPrintStatusPanel
	 *            boolean
	 */
	public void setBillRefModeSelPanel(boolean isShow) {

		if (isShow)
			this.getUITabbedPane().insertTab(
					getBillRefModeSelPanel().getName(), null,
					getBillRefModeSelPanel(), null,
					getUITabbedPane().getTabCount());
		else if (m_isShowPrintStatusPanel)// 已经显示过了，现在要去掉显示
			this.getUITabbedPane().removeTabAt(
					getUITabbedPane().getTabCount() - 1);
		// else do nothing.
		m_isShowBillRefModeSelPanel = isShow;

	}

	public boolean isSaveHistory() {
		return saveHistory;
	}

	public void setSaveHistory(boolean saveHistory) {
		this.saveHistory = saveHistory;
	}

	/**
	 * zc_启用权限管理后保存查询条件出现了多余行
	 */
	public void actionPerformed(java.awt.event.ActionEvent e) {
		if (((UIButton) e.getSource()).getName().equals("UIButtonSaveHistory"))
			setSaveHistory(true);
		super.actionPerformed(e);
		if (((UIButton) e.getSource()).getName().equals("UIButtonSaveHistory"))
			setSaveHistory(false);

	}

	/**
	 * 创建人：
	 * 
	 * 功能：
	 * 
	 * 参数：
	 * 
	 * 返回：
	 * 
	 * 异常：
	 * 
	 * 日期：(2004-11-25 16:27:37) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @param isShowPrintStatusPanel
	 *            boolean
	 */
	public void setBillRefShowMode(boolean isShowDoubleTableRef) {
		getBillRefModeSelPanel().setSelect(isShowDoubleTableRef);
	}

	public HashMap<String, ArrayList> getmCorpData() {
		return m_hmCorpData;
	}

	/** 复杂查询面板 */
	private IQueryTypeEditor queryTypeEditor = null;

	private IQueryTypeEditor getQueryTypeEditor() {
		if (!isQueryTypeEditorUsed()) {
			throw new UnsupportedOperationException(
					"只有设置查询方式不是IQueryType.NEITHER时才能调用此方法");
		}
		if (queryTypeEditor == null) {
			createQueryTypeEditor();
		}
		return queryTypeEditor;
	}

	/**
	 * 创建复杂查询面板
	 * 
	 * @author 蒲强华
	 * @since 2009-8-28
	 */
	private void createQueryTypeEditor() {
		final String title = nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"scmpub", "UPPscmpub-001207");// "查询方式"
		if (null != queryTypeEditor) {
			getUITabbedPane().remove(getUITabbedPane().indexOfTab(title));
		}
		queryTypeEditor = QueryTypeEditorFactory.create(getQueryType());
		getUITabbedPane().insertTab(title, null,
				new UIScrollPane(queryTypeEditor.getComponent()), title,
				getUITabbedPane().getTabCount());
	}

	/** 复杂查询类型 */
	private int queryType = IQueryType.NEITHER;

	/**
	 * 设置查询方式，可以设置下面的值 <blockquote><table>
	 * <tr>
	 * <td>IQueryType.PAGED_ONLY</td>
	 * <td>只显示分页设置</td>
	 * </tr>
	 * <tr>
	 * <td>IQueryType.ASYN_ONLY</td>
	 * <td>只显示异步查询配置</td>
	 * </tr>
	 * <tr>
	 * <td>IQueryType.BOTH</td>
	 * <td>同时显示分页设置和异步查询配置</td>
	 * </tr>
	 * <tr>
	 * <td>IQueryType.NEITHER</td>
	 * <td>不是复杂查询</td>
	 * </tr>
	 * </table></blockquote>
	 * 
	 * @param queryType
	 *            查询方式
	 * @author 蒲强华
	 * @since 2009-8-24
	 */
	public void setQueryType(int queryType) {
		this.queryType = queryType;
		if (isQueryTypeEditorUsed()) {
			getQueryTypeEditor();
		}
	}

	/**
	 * 获取查询方式，返回值的意义如下 <blockquote><table>
	 * <tr>
	 * <td>IQueryType.PAGED_ONLY</td>
	 * <td>只显示分页设置</td>
	 * </tr>
	 * <tr>
	 * <td>IQueryType.ASYN_ONLY</td>
	 * <td>只显示异步查询配置</td>
	 * </tr>
	 * <tr>
	 * <td>IQueryType.BOTH</td>
	 * <td>同时显示分页设置和异步查询配置</td>
	 * </tr>
	 * <tr>
	 * <td>IQueryType.NEITHER</td>
	 * <td>不是复杂查询</td>
	 * </tr>
	 * </table></blockquote>
	 * 
	 * @return 返回查询方式
	 * @author 蒲强华
	 * @since 2009-8-24
	 */
	public int getQueryType() {
		return queryType;
	}

	/**
	 * 是否使用 查询方式 页签
	 */
	private boolean isQueryTypeEditorUsed() {
		return getQueryType() != IQueryType.NEITHER;
	}

	/**
	 * 设置复杂查询中可供分页使用的项目
	 * <p>
	 * <strong> 对于同一个查询对话框，该方法和
	 * <p>
	 * <code>setFixedPagedSubjects(ISubject[])<code><p>方法同时只能有且只有一个被调用
	 * </strong>
	 * @param subjects
	 *            项目数组
	 */
	public void setPagedSubjects(ISubject[] subjects) {
		createQueryTypeEditor();
		getQueryTypeEditor().setPagedSubjects(subjects);
	}

	/**
	 * 设置复杂查询中固定的分页项目数组，使用此方法设置的分页项目不允许用户编辑
	 * <p>
	 * <strong> 对于同一个查询对话框，该方法和
	 * <p>
	 * <code>setPagedSubjects(ISubject[])<code><p>方法同时只能有且只有一个被调用
	 * </strong>
	 * 
	 * @param subjects
	 *            项目数组
	 */
	public void setFixedPagedSubjects(ISubject[] subjects) {
		getQueryTypeEditor().setFixedPagedSubjects(subjects);
	}

	/**
	 * 注册复杂查询中查询结果默认名称
	 * 
	 * @param generator
	 *            名称生成器
	 */
	public void registerQueryResultDefaultName(INameGenerator generator) {
		getQueryTypeEditor().registerQueryResultDefaultName(generator);
	}

	/**
	 * 返回复杂查询的查询方式信息
	 * <p>
	 * <strong> 返回的信息是一个完整集，客户端需要根据自己的查询方式去访问对应的信息即可
	 */
	public QueryTypeInfo getQueryTypeInfo() {
		return getQueryTypeEditor().getQueryTypeInfo();
	}

	/**
	 * 
	 * 作者：田锋涛 功能：获取查询模板处理类 参数： 返回： 例外： 日期：2009-9-30 修改日期， 修改人，修改原因，注释标志
	 */
	protected SCMQueryConditionHandler getQueryCondHandler() {
		if (queryCondHandler == null)
			queryCondHandler = new SCMQueryConditionHandler(this);
		return queryCondHandler;
	}

	/**
	 * 
	 * 作者：田锋涛 功能：添加查询日期字段 参数：dateFieldCodes - 要添加的日期字段数组 fromDates - from date
	 * 数组 toDates - to date 数组 返回： 例外： 日期：2009-9-22 修改日期， 修改人，修改原因，注释标志
	 */
	public void addExtraDates(String[] dateFieldCodes, UFDate[] fromDates,
			UFDate[] toDates) {
		getQueryCondHandler().addExtraDates(dateFieldCodes, fromDates, toDates);
	}

	/**
	 * 
	 * 作者：田锋涛 功能：添加查询日期字段 参数：dateFieldCode - 要添加的日期字段 fromDate - from date
	 * toDate - to date 返回： 例外： 日期：2009-9-22 修改日期， 修改人，修改原因，注释标志
	 */
	public void addExtraDate(String dateFieldCode, UFDate fromDate,
			UFDate toDate) {
		getQueryCondHandler().addExtraDate(dateFieldCode, fromDate, toDate);
	}

	/**
	 * 
	 * 作者：田锋涛 功能： 参数：dateFieldCode 返回： 例外： 日期：2009-9-23 修改日期， 修改人，修改原因，注释标志
	 */
	public void addCurToCurDate(String dateFieldCode) {
		getQueryCondHandler().addCurToCurDate(dateFieldCode);
	}

	/**
	 * 
	 * 作者：田锋涛 功能： 参数：dateFieldCode 返回： 例外： 日期：2009-9-23 修改日期， 修改人，修改原因，注释标志
	 */
	public void addCurToCurDates(String[] dateFieldCode) {
		getQueryCondHandler().addCurToCurDates(dateFieldCode);
	}

	/**
	 * 
	 * 作者：田锋涛 功能： 参数：dateFieldCode 返回： 例外： 日期：2009-9-23 修改日期， 修改人，修改原因，注释标志
	 */
	public void addCurMthFirstToCurDate(String dateFieldCode) {
		getQueryCondHandler().addCurMthFirstToCurDate(dateFieldCode);
	}

	/**
	 * 
	 * 作者：田锋涛 功能： 参数：dateFieldCode 返回： 例外： 日期：2009-9-23 修改日期， 修改人，修改原因，注释标志
	 */
	public void addCurMthFirstToCurDates(String[] dateFieldCode) {
		getQueryCondHandler().addCurMthFirstToCurDates(dateFieldCode);
	}

	/**
	 * 父类方法重写
	 * 
	 * @see nc.ui.pub.query.QueryConditionClient#doInitBeforShowModal()
	 */
	@Override
	protected int doInitBeforShowModal() {
		if (getQueryCondHandler().isAddExtraDt()) {
			initData();
			getQueryCondHandler().setAddExtraDt(false);
		}
		return super.doInitBeforShowModal();
	}

}