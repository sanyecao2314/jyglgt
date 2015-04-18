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
 * �˴���������˵���� �̳еĲ�ѯ�����Ի��򣬼���������������գ����������Զ�����,��ʼ��ϵͳ���չ���
 * 
 * ֧��Ȩ�ޣ� ��ӡ����PANEL��
 * 
 * �������ڣ�(2001-7-25 10:25:10)
 * 
 * @author��������
 */

public class SCMQueryConditionDlg extends nc.ui.pub.query.QueryConditionClient {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UIPanel ivjUIPanel = null;

	private javax.swing.JTextField ivjtfUnitCode = null;

	ClientEnvironment env = null;

	// ������ֵ�ı�keyΪ��������ֶΣ�ֵΪ��Ӧ��������VO
	// private Hashtable m_htFreeVO= new Hashtable();
	// �����������Ķ��ձ�keyΪ�������ֶΣ�ֵΪ����ֶ�
	private Hashtable<String, String> m_htFreeItemInv = new Hashtable<String, String>();

	// ����ı�keyΪ����ֶΣ�ֵΪ�����ô���Ŀյ�������VO�Ĵ��VO
	private Hashtable<String, InvVO> m_htInvVO = new Hashtable<String, InvVO>();

	// �Զ���ֵ�ı�keyΪ�ؼ��ֶΣ�ֵΪ��Ӧ���ֶε������ֶε�һ������
	private Hashtable<String, String[]> m_htAutoClear = new Hashtable<String, String[]>();

	// �ֿ�ı�keyΪ�ֿ��ֶΣ�ֵΪ�ֿ�Ķ�Ӧ�Ĳֿ�VO
	private Hashtable<String, WhVO> m_htWhVO = new Hashtable<String, WhVO>();

	// �Ը������Ŀ��Ƕ����
	private InvMeasRate m_voInvMeas = new nc.ui.jyglgt.scm.ic.measurerate.InvMeasRate();

	// ��������Ӧ��
	private Hashtable<String, String[]> m_htAstCorpInv = new Hashtable<String, String[]>();

	// �����빫˾�Ĺ�ϵ
	// kp.pk_corp,{pk_calbody,cwarehouseid}
	private HashMap<String, String[]> m_htCorpRef = new HashMap<String, String[]>();

	// pk_calbody,cwarehouseid
	private ArrayList<String> m_alpower = new ArrayList<String>();

	// pk_calbody,kp.pk_corp
	// cwarehouseid,pk_corp
	private HashMap<String, String> m_hmRefCorp = new HashMap<String, String>();

	// ����׷�ӵ���ѯVO[]��
	private ConditionVO[] m_dataPowerScmVOs = null;

	private HashMap<String, String> m_htRefField = null;

	// ϵͳ���ճ�ʼ��where�Ӿ�
	private ArrayList<ArrayList<String>> m_alRefInitWhereClause = new ArrayList<ArrayList<String>>();

	// m_alRefInitWhereClause ELEMENT DEFINITION
	private final int RI_FIELD_CODE = 0;

	private final int RI_REF_NAME = 1;

	private final int RI_WHERE_CLAUSE = 2;

	private final int RI_CHECK_FIELD_CODE = 3;

	// ��ͳ�ƽṹ�Ŀ��Ƕ����
	private Hashtable<String, String> m_htStatBtoH = new Hashtable<String, String>();

	private Hashtable<String, String> m_htStatHtoCorp = new Hashtable<String, String>();

	// �����κŵĿ��Ƕ����
	private Hashtable<String, String> m_htLotInv = new Hashtable<String, String>();

	// ���������޸ģ�����ĳһ�ֶεĲ���
	private ArrayList<ArrayList<Object>> m_alComboxToRef = new ArrayList<ArrayList<Object>>();

	// �йع�˾���յĳ�ʼ��
	private ArrayList<ArrayList<Serializable>> m_alCorpRef = new ArrayList<ArrayList<Serializable>>();

	// ��Ȩ�޵Ĺ�˾ֵ
	// kp.pk_corp,new ArrayList{1001,1002,1003}
	private HashMap<String, ArrayList> m_hmCorpData = new HashMap<String, ArrayList>();

	private String[] m_sdefaultcorps = null;

	// �йػ�λ���յ�ָ����¼
	private ArrayList<ArrayList<String>> m_alLocatorRef = new ArrayList<ArrayList<String>>();

	// private String m_sCorpID= null; //��˾ID
	// private String m_sUserID= null; //��ǰʹ����ID
	// private String m_sLogDate= null; //��ǰ��¼����
	// private String m_sUserName= null; //��ǰʹ��������

	// Ĭ�ϵĹ�˾
	private String m_sDefaultCorpID = "";

	private String m_sCorpFieldCode = "";

	// ��ʼ�������Ʋ�����
	private Hashtable<String, ArrayList<Object>> m_htInitMultiRef = new Hashtable<String, ArrayList<Object>>();

	// ��ӡ״̬PANEL
	private QryPrintStatusPanel m_QryPrintStatusPanel;

	private BillRefModeSelPanel m_billRefModeSelPanel;

	private boolean m_isShowBillRefModeSelPanel = false;

	// �Ƿ���ʾ��ӡ״̬PANEL from 3.1
	private boolean m_isShowPrintStatusPanel = false;

	// �Ƿ񷵻� ����Ȩ�� SQL����������Ʒ�У����ֽڵ㲻֧��Ȩ�ޡ�default is supported
	private boolean m_isDataPowerSqlReturned = true;

	/**
	 * @param dataPowerSqlReturned
	 *            the m_isDataPowerSqlReturned to set
	 */
	public void setM_isDataPowerSqlReturned(boolean dataPowerSqlReturned) {
		m_isDataPowerSqlReturned = dataPowerSqlReturned;
	}

	// ��ģ�巵��ֵ�޹�����Ȩ������vo
	private ConditionVO[] m_cCtrTmpDataPowerVOs = null;

	// czp , 2006-10-30, ��Դ�Ƿ����û��棬���Ȩ�������е�����Ҫ���û��˳��ڵ����½���
	// �ṹ��{key=����+��Դ����+pk_corp, value = Ȩ���Ƿ�����}
	// private static HashMap<String, UFBoolean> m_mapPowerEnableCache = new
	// HashMap<String, UFBoolean>();

	// czp , 2006-10-30, ����Ȩ�������Ӳ�ѯ�����Ȩ�������е�����Ҫ���û��˳��ڵ����½���
	// �ṹ��{key=����+��Դ����+����ԱID+pk_corp0+pk_corp1+..., value = Ȩ���Ӵ�}
	private static HashMap<String, String> m_mapPowerSubSqlCache = new HashMap<String, String>();

	// zc ����Ȩ�޹���󱣴��ѯ���������˶�����
	private boolean saveHistory = false;

	// tianft 2009/09/30 ��ѯģ�崦����
	private SCMQueryConditionHandler queryCondHandler = null;

	/**
	 * QueryConditionDlg ������ע�⡣
	 */
	public SCMQueryConditionDlg() {
		super();
		setIsWarningWithNoInput(true);
		setSealedDataShow(true);
	}

	/**
	 * QueryConditionDlg �����ӣ���������Ȩ�޹�����
	 * 
	 * @param isFixedSet
	 */
	public SCMQueryConditionDlg(boolean isFixedSet) {
		super(isFixedSet);
		setIsWarningWithNoInput(true);
		setSealedDataShow(true);
	}

	/**
	 * QueryConditionDlg ������ע�⡣
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
	 * QueryConditionDlg ������ע�⡣
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
	 * QueryConditionDlg ������ע�⡣
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
	 * QueryConditionDlg ������ע�⡣
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
	 * �����ߣ������� ���ܣ����뵱�������޸�ʱ��������޸�ʱ������Զ����� ������ ���أ� ���⣺ ���ڣ�(2001-8-12 18:02:51)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param editor
	 *            javax.swing.table.TableCellEditor
	 * @param row
	 *            int
	 * @param col
	 *            int �޸ģ����Ӣ ��rrvo[row]�滻Ϊrrvo[index]
	 */
	public void afterEdit(TableCellEditor editor, int row, int col) {
		// ��ʱ����
		// if (true) {
		// super.afterEdit(editor, row, col);
		// }
		// �Ժ�ɾ��
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
			// д��������VO��ֵ
			Object temp = ((UIFreeItemCellEditor) editor).getComponent();
			if (temp instanceof FreeItemRefPane) {
				FreeItemRefPane pane = (FreeItemRefPane) temp;
				FreeVO fvo = pane.getFreeVO();
				// String sFieldCode=
				// getConditionDatas()[index].getFieldCode();
				// m_htFreeVO.put(sFieldCode, fvo);
				// �ڽ��VO��д��PK,Code,Name��Ϊvfree0
				RefResultVO[] rrvo = getValueRefResults();
				if (rrvo[index] == null)
					rrvo[index] = new RefResultVO();
				rrvo[index].setRefCode(fvo.getVfree0().trim());
				rrvo[index].setRefName(fvo.getVfree0().trim());
				rrvo[index].setRefPK(fvo.getVfree0().trim());
				rrvo[index].setRefObj(fvo);
				setValueRefResults(rrvo);
				// ������VO��д���Ӧ���ֵ
				// QueryConditionVO[] qcvo= getConditionDatas();
				// qcvo[index].setValue(fvo.getVfree0().trim());
				// setConditionDatas(qcvo);
				// �������
				getUITabInput().setValueAt(fvo.getVfree0().trim(), row, col);
				// ���뱣���InvVO
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
			// ��������

			Object temp = ((UIComboBoxCellEditor) editor).getComponent();
			if (temp instanceof UIComboBox) {
				UIComboBox pane = (UIComboBox) temp;
				DataObject doValue = (DataObject) pane.getItemAt(pane
						.getSelectedIndex());
				// �ڽ��VO��д��PK,Code,Name��Ϊvfree0
				RefResultVO[] rrvo = getValueRefResults();
				if (rrvo[index] == null)
					rrvo[index] = new RefResultVO();
				rrvo[index].setRefCode(doValue.getID().toString().trim());
				rrvo[index].setRefName(doValue.getName().toString().trim());
				rrvo[index].setRefPK(doValue.getID().toString().trim());
				// rrvo[index].setRefObj(doValue);
				setValueRefResults(rrvo);
				// �������
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

				if (sRefNodeName.equals("��˾Ŀ¼")) {
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
				} else if (sRefNodeName.equals("�������")
						|| sRefNodeName.equals("���׼�")) {
					if (pane.getRefPK() != null) {
						String sTempID1 = pane.getRefPK().trim();
						String sTempID2 = null;
						ArrayList<String> alIDs = new ArrayList<String>();
						alIDs.add(sTempID2);
						alIDs.add(sTempID1);
						alIDs.add(getCurUserID());
						alIDs.add(null);
						try {
							// ��Ϊ����޸�ʱ������InvVO,�Ա��������������
							// ��Ϊ����޸�ʱ������InvVO,�Ա��������������
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
				} else if (sRefNodeName.equals("����ڼ�")) {
					if (pane.getRefPK() != null) {
						// �ڽ��VO��д��PK,Code,Name
						RefResultVO[] rrvo = getValueRefResults();
						if (rrvo[index] == null)
							rrvo[index] = new RefResultVO();
						rrvo[index].setRefCode(pane.getRefCode().trim());
						rrvo[index].setRefName(pane.getRefName().trim());
						// pane.getRefName().trim() + "��-" +
						// pane.getRefCode().trim() + "��");
						rrvo[index].setRefPK(pane.getRefPK().trim());
						rrvo[index].setRefObj(pane
								.getRefValue("bd_accperiodmonth.begindate"));
						setValueRefResults(rrvo);
						// �������
						getUITabInput().setValueAt(pane.getRefName().trim(),
						// pane.getRefName().trim() + "��-" +
								// pane.getRefCode().trim() + "��",
								row, col);
					}
				} else if (sRefNodeName.equals("�ֿ⵵��")) {
					if (pane.getRefPK() != null) {
						String sTempID1 = pane.getRefPK().trim();
						String sTempID2 = null;
						ArrayList<String> alIDs = new ArrayList<String>();
						alIDs.add(sTempID2);
						alIDs.add(sTempID1);
						alIDs.add(getCurUserID());
						alIDs.add(null);
						try {
							// ��Ϊ�ֿ��޸�ʱ������WhVO,�Ա�������λ����
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
				// ��ͳ�ƽṹ
			} else if (qcvo.getDataType().intValue() == 350) {
				// ��ͳ�Ʒ�Χ

				IStatbRefPane pane = (IStatbRefPane) temp;

				// �ڽ��VO��д��PK,Code,Name��Ϊvfree0
				RefResultVO[] rrvo = getValueRefResults();
				if (rrvo[index] == null)
					rrvo[index] = new RefResultVO();
				rrvo[index].setRefCode(pane.getStatcode().trim());
				rrvo[index].setRefName(pane.getStatcode().trim());
				rrvo[index].setRefPK(pane.getStatbid().trim());
				// rrvo[index].setRefObj(null);
				setValueRefResults(rrvo);
				// �������
				getUITabInput().setValueAt(
						pane.getStatcode().toString().trim(), row, col);
			} else if (qcvo.getDataType().intValue() == 400) {
				// �����κ�
				UIRefPane pane = (UIRefPane) temp;
				// LotQueryRef pane = (LotQueryRef) temp;
				// �ڽ��VO��д��PK,Code,Name��Ϊvfree0
				RefResultVO[] rrvo = getValueRefResults();
				if (rrvo[index] == null)
					rrvo[index] = new RefResultVO();
				rrvo[index].setRefCode(pane.getText().trim());
				rrvo[index].setRefName(pane.getText().trim());
				rrvo[index].setRefPK(pane.getText().trim());
				// rrvo[index].setRefObj(null);
				setValueRefResults(rrvo);
				// �������
				getUITabInput().setValueAt(pane.getText().trim(), row, col);
			}
		} else {
			super.afterEdit(editor, row, col);
		}
		// ���Ӧֵ
		if (col == COLVALUE) {
			String sFieldCode = qcvo.getFieldCode().trim();
			autoClear(sFieldCode, row, col);
		}
	}

	/**
	 * �����ߣ������� ���ܣ����ؼ��ֶ��������ֶε�ֵ ������ ���أ� ���⣺ ���ڣ�(2001-8-13 14:29:41)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	protected void autoClear(String sKey, int row, int col) {
		if (m_htAutoClear.containsKey(sKey)) {
			String[] sOtherFieldCodes = m_htAutoClear.get(sKey);
			RefResultVO[] rrvo = getValueRefResults();
			QueryConditionVO[] qcvos = getConditionDatas();
			// ��ֵ
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
						// �鵽��Ӧ�Ľ��VO�����PK��Code, Name
						if (rrvo[j] != null) {
							rrvo[j].setRefCode("");
							rrvo[j].setRefName("");
							rrvo[j].setRefPK("");
						}
						// �������ʾֵ
						getUITabInput().setValueAt(null, j, col);
						break;
					}
				}
			}
		}
	}

	/**
	 * ΪȨ�޲��ռ��� is Null����
	 * 
	 * @param tableName
	 * @param tabelShowName
	 * @param pk_Corp
	 */
	protected void addNullContionDataPower(String refTableName,
			String sFieldCode, String pk_Corp) {
	}

	/**
	 * ȡ���Զ����ӵĻ�������Ȩ������VO���� ��������:(2001-4-25 16:50:17)
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
	 * �����û�δ�������Ĳ�����Ŀ����Ӧ�Ļ�������Ȩ������VO���顣 ���ô˷���ʱ �������ڣ�(2003-05-29 9:55:15) 57
	 * ȥ���˷�����uap�ķ������
	 * 
	 * @return nc.vo.pub.query.ConditionVO[]
	 */
	@SuppressWarnings("unused")
	public void calculateDataPowerVOs() {
		if (m_htCorpRef.size() == 0
				&& (m_cCtrTmpDataPowerVOs == null || m_cCtrTmpDataPowerVOs.length == 0)) {

			/*
			 * by czp, 2006-11-04��ԭ��
			 * 
			 * ����super.calculateDataPowerVOs()Ȩ�޴���,����super.getConditionVO()[this.getConditionVO()����]���ش��������Ȩ�޲�ѯVO
			 * 
			 * super.calculateDataPowerVOs()�����ѯ����VO�������Ӵ�ֵֻ�С�id in Ȩ�޴�����δƴ��id is
			 * null,�����Ϲ�Ӧ��ҵ��Ҫ��
			 *  // �޸ĸ���û�м���IS NULL��������������������Ͳ�ѯ������(����)
			 * super.calculateDataPowerVOs();
			 */
			return;
		}

		HashMap<String, String[]> hmRefCorp = getRefCorp();

		HashMap<String, QueryConditionVO> hmPoweredConditionVO = new HashMap<String, QueryConditionVO>();// ������Ȩ�޵�����VO��WNJ2005-04-27

		ArrayList<String> alNullValueCondVO = new ArrayList<String>();// ����û��¼��ֵ������VO��WNJ2005-04-27

		m_dataPowerScmVOs = null;

		String strDef = null; // ģ���е���Ŀ��ʶ���ֶ� or����+�ֶΣ���

		// �õ�����ģ���л������ݲ������Ͳ�ʹ������Ȩ�޵���Ŀ
		for (int i = 0; i < getConditionDatas().length; i++) {
			QueryConditionVO vo = getConditionDatas()[i];
			if (isDataPwr(vo)) {

				// ��͸ʽ��ѯ
				strDef = getFldCodeByPower(vo);
				// Ϊ��֪��Ȩ�޲��յķ������ͣ�PK,code,name
				hmPoweredConditionVO.put(strDef, vo);
				hmPoweredConditionVO.put(getFldCodeByPower(vo), vo);

			}
		}

		// ���й��˵��û�������������������Ŀ
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
				// ��ֹ�û���ס�˴������ȣ��ƹ�Ȩ�޿���

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

				// �����ù�˾��ֵ
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

				// Ȩ�޲���û����������
				if (!htUsed.containsKey(strUsed))
					alNullValueCondVO.add(strUsed);

			}
		}

		// Ϊʣ����Ŀ��ѯ��������Ȩ��
		if (alNullValueCondVO.size() > 0) {

			Vector<ConditionVO> vecVO = new Vector<ConditionVO>();
			String[] fieldcodes = alNullValueCondVO.toArray(new String[0]);
			Map<String, String> mapSQL =
          getSubSqlForMutiCorpCtrl(hmRefCorp, hmPoweredConditionVO,
              fieldcodes);
			// ���ȳ�ʼ������Ȩ����Ϣ ,���ܻ��ʼ��һЩ���������
			// for (String sCorp : vecCorps) {
			// initUsedDataPowerInfo(vecTableNames, vecRefNames, sCorp);
			// }

			for (int i = 0; i < alNullValueCondVO.size(); i++) {
				String fieldcode = alNullValueCondVO.get(i);

				QueryConditionVO voQuery = hmPoweredConditionVO.get(fieldcode);
				if (voQuery == null) {
					nc.vo.scm.pub.SCMEnv.out("@@@@û��QueryConditionVO::"
							+ fieldcode);
					continue;
				}

				String sRefNodeName = voQuery.getConsultCode();
				String insql = mapSQL.get(sRefNodeName);

				appendPowerCons(vecVO, voQuery, insql);
			}
			// ��֯��Ȩ�޵Ĳ�ѯ����VO������
			if (vecVO.size() > 0) {
				m_dataPowerScmVOs = new ConditionVO[vecVO.size()];
				vecVO.copyInto(m_dataPowerScmVOs);
			} 
		}

	}

  private HashMap<String, String[]> getRefCorp() {
    HashMap<String, String[]> hmRefCorp = new HashMap<String, String[]>();

		// ȱʡ���յĹ�˾�ǵ�ǰ��˾
		//
		for (int i = 0; i < m_alpower.size(); i++) {
			// �ҵ����յĹ�˾��
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
//     * ȡ���Զ����ӵĻ�������Ȩ������VO���� ��������:(2001-4-25 16:50:17)
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
    				.getRefTableName(sRefNodeName); // ���ݲ������ƻ�û������ݱ���
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
	 * ���ߣ����Ӣ �������ڣ�(2001-8-29 19:22:04)
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
	 *                �쳣˵����
	 */
	public void checkAllHaveOrNot(ConditionVO[] voCons, String[] strKeys)
			throws nc.vo.pub.BusinessException {

		if (strKeys == null)
			return;

		if (voCons == null || voCons.length < 1)
			throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
					.getInstance().getStrByID("scmpub", "UPPscmpub-000162")/*
																			 * @res
																			 * "û�в�ѯ������"
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
																			 * "�������ȫΪ�ջ�ȫ��Ϊ�գ�"
																			 */

					+ sMsg);

		// } else {
		// if (htKey.size() != strKeys.length)
		// throw new nc.vo.pub.BusinessException("��������" + sMsg);
		// }

		// }
		return;

	}

	/**
	 * ���ߣ����Ӣ �������ڣ�(2001-7-12 12:58:52) ����������
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
					"UPPscmpub-000164")/* @res "���Ų�ƥ�䣡" */;
			throw new nc.vo.pub.BusinessException(sMsg);
		}

		return;
	}

	/**
	 * �������ڣ�(2003-11-20 11:55:13) ���ߣ������� ������ ���أ� ˵����
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

				// ����
				if (!conditions[i].getLogic()) {

					if (!hm.containsKey(conditions[i].getFieldCode())) {
						voPre = conditions[i - 1];
						hm.put(voPre.getFieldCode(), voPre);
					} else
						voPre = hm.get(conditions[i].getFieldCode());

					if (!conditions[i - 1].getFieldCode().equals(
							conditions[i].getFieldCode())) {
						sReturnError = "ֻ����ͬ�Ĳ�ѯ��Ŀ����ʹ��'����'!";
						break;
					}

					if (voPre.getNoLeft() || !voPre.getNoRight()) {
						sReturnError = "���ڵ�һ��'" + voPre.getFieldName()
								+ "'����������������,����ȥ��������!";
						break;
					}

				}
			}
		}

		return sReturnError;

	}

	/**
	 * ���ߣ����Ӣ �������ڣ�(2001-8-29 19:22:04)
	 * 
	 * strKeys�е���������ڲ�ѯ���ֶΣ�������������
	 * 
	 * @param voCons
	 *            nc.vo.pub.query.ConditionVO[]
	 * @param strKeys
	 *            java.lang.String[]
	 * @param bmust
	 *            boolean
	 * @exception nc.vo.pub.BusinessException
	 *                �쳣˵����
	 */
	public void checkNotField(ConditionVO[] voCons, String[] strKeys)
			throws nc.vo.pub.BusinessException {

		if (strKeys == null)
			return;

		if (voCons == null || voCons.length < 1)
			throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
					.getInstance().getStrByID("scmpub", "UPPscmpub-000162")/*
																			 * @res
																			 * "û�в�ѯ������"
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
																	 * "Ӧû�����ţ�"
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
																		 * "Ӧ�����������"
																		 */);
				}

			}

		}

		if (count != -1 && i != count + 1)
			throw new nc.vo.pub.BusinessException(sMsg
					+ nc.ui.ml.NCLangRes.getInstance().getStrByID("scmpub",
							"UPPscmpub-000166")/* @res "Ӧ�����������" */);

		return;

	}

	/**
	 * ���ߣ����Ӣ �������ڣ�(2001-8-29 19:22:04)
	 * 
	 * 
	 * strKeys�е������ֻ�ܳ���һ��
	 * 
	 * @param voCons
	 *            nc.vo.pub.query.ConditionVO[]
	 * @param strKeys
	 *            java.lang.String[]
	 * @param bmust
	 *            boolean
	 * @exception nc.vo.pub.BusinessException
	 *                �쳣˵����
	 */
	public void checkOncetime(ConditionVO[] voCons, String[] strKeys)
			throws nc.vo.pub.BusinessException {

		if (strKeys == null)
			return;

		if (voCons == null || voCons.length < 1)
			throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
					.getInstance().getStrByID("scmpub", "UPPscmpub-000162")/*
																			 * @res
																			 * "û�в�ѯ������"
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
														 * "�������������������ֻ�ܳ���һ�Σ�"
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
	 * ���ߣ����Ӣ �������ڣ�(2001-8-29 19:22:04)
	 * 
	 * if(bmust) strKeys�е�fieldcode�������һ�� else strKeys�е�fieldcode���ֻ�ܳ���һ��
	 * 
	 * @param voCons
	 *            nc.vo.pub.query.ConditionVO[]
	 * @param strKeys
	 *            java.lang.String[]
	 * @param bmust
	 *            boolean
	 * @exception nc.vo.pub.BusinessException
	 *                �쳣˵����
	 */
	public void checkOneNotOther(ConditionVO[] voCons, String[] strKeys,
			boolean bmust) throws nc.vo.pub.BusinessException {

		if (strKeys == null)
			return;

		if (voCons == null || voCons.length < 1)
			throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
					.getInstance().getStrByID("scmpub", "UPPscmpub-000162")/*
																			 * @res
																			 * "û�в�ѯ������"
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
														 * @res "������Ӧ������ֻ����һ�"
														 */
						+ sMsg);
		} else {
			if (count > 1)
				throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
						.getInstance().getStrByID("scmpub", "UPPscmpub-000169")/*
																				 * @res
																				 * "���������ֻ����һ�"
																				 */
						+ sMsg);
		}

		// }

		return;

	}

	/**
	 * ���ߣ����Ӣ �������ڣ�(2001-8-29 19:22:04)
	 * 
	 * if(bmust) strKeys�е�fieldcode�������һ�� else strKeys�е�fieldcode���ֻ�ܳ���һ��
	 * 
	 * @param voCons
	 *            nc.vo.pub.query.ConditionVO[]
	 * @param strKeys
	 *            java.lang.String[]
	 * @param bmust
	 *            boolean
	 * @exception nc.vo.pub.BusinessException
	 *                �쳣˵����
	 */
	public void checkOneNotOther(ConditionVO[] voCons, ArrayList alKeys,
			boolean bmust) throws nc.vo.pub.BusinessException {

		if (alKeys == null)
			return;

		if (voCons == null || voCons.length < 1)
			throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
					.getInstance().getStrByID("scmpub", "UPPscmpub-000162")/*
																			 * @res
																			 * "û�в�ѯ������"
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
																				 * "������Ӧ������ֻ����һ�飺"
																				 */
						+ sMsg);
		} else {
			if (htField.size() > 1)
				throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
						.getInstance().getStrByID("scmpub", "UPPscmpub-000171")/*
																				 * @res
																				 * "���������ֻ����һ�飺"
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
	 * ���ߣ����� �������ڣ�(2003-6-10 18:18:09) ������A��ѯ�����ֶ�=true ʱ��B��ѯ�ֶα�����д
	 * 
	 * sFlagField:�߼���ѯ�����ֶ� sFieldMustFillIn�� �߼��ֶ�=true ʱ������д���ֶ�
	 * sFieldMustFillInName���ֶ�����
	 * 
	 */
	public void checkOneTrueAnotherMustFillin(ConditionVO[] voCons,
			String sFlagField, String sFieldMustFillIn,
			String sFieldMustFillInName) throws nc.vo.pub.BusinessException {

		if (sFlagField == null || sFieldMustFillIn == null
				|| sFieldMustFillInName == null || voCons == null
				|| voCons.length < 1) {
			nc.vo.scm.pub.SCMEnv.out("�������ѯ��������");
			return;
			// throw new nc.vo.pub.BusinessException("û�в�ѯ������");
		}

		String sFieldCode = null;
		String sFieldValue = null;
		String sFlagFieldValue = null; // �߼��ֶ�ֵ
		String sFlagFieldName = null; // �߼��ֶ�����
		String sFieldMustFillInValue = null; // ��У���ֶ�
		for (int i = 0; i < voCons.length; i++)
			if (voCons[i] != null) {
				sFieldCode = voCons[i].getFieldCode();
				sFieldValue = voCons[i].getValue();
				// ���߼���ѯ�����ֶ�sFlagField.��
				if (sFlagField.equalsIgnoreCase(sFieldCode)) {
					sFlagFieldValue = sFieldValue;
					sFlagFieldName = voCons[i].getFieldName();
				} // �Ǳ�����д���ֶΣ�
				else if (sFieldMustFillIn.equalsIgnoreCase(sFieldCode)) {
					// ����ֵ��׼���ж�
					sFieldMustFillInValue = sFieldValue;
				}
			}
		// ����Ϊtrue�����ұ���ֵδ��״�
		UFBoolean flag = new UFBoolean(sFlagFieldValue);
		if (flag.booleanValue() // "true".equalsIgnoreCase(sFlagFieldValue)
				&& (sFieldMustFillInValue == null || sFieldMustFillInValue
						.trim().length() == 0))
			throw new nc.vo.pub.BusinessException("��" + sFlagFieldName
					+ "��ֵΪtrueʱ,\n��" + sFieldMustFillInName + "������Ϊ�գ�");

		return;

	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-8-23 20:49:16) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * ���ԭʼ�Ĳ�ѯVO[] 2007-09-05 xhq
	 */
	public nc.vo.pub.query.ConditionVO[] getSuperConditionVO() {
		return super.getConditionVO();
	}

	/**
	 * ȡ���û��趨�Ĳ�ѯ����VO���� �������ڣ�(2001-4-25 16:50:17)
	 * 
	 * @return nc.vo.pub.query.ConditionVO[]
	 */
	public nc.vo.pub.query.ConditionVO[] getConditionVO() {
		nc.vo.pub.query.ConditionVO[] voaCond = super.getConditionVO();

		// zc_����Ȩ�޹���󱣴��ѯ���������˶�����
		if (isSaveHistory())
			return voaCond;

		// ������ж�������Ŀ���ǣ�����Ҫ�๫˾�����¿���Ȩ��ʱ��ʹ�õ�Ȩ��vo��m_cCtrTmpDataPowerVOs
		// ��super.getConditionVO�еõ���vo����Ȩ���Ӳ�ѯvo�����ǲ���Ϊ�˶๫˾�ģ�����˵�Բ�ѯ����ڶ๫˾��˵�Ǵ����
		// ����Ҫ���Ĺ���ʱҪ���˵�����ص�vo��Ȼ��ƴ���϶๫˾��m_cCtrTmpDataPowerVOs
		if (m_isDataPowerSqlReturned)
			voaCond = setCVO4MulCoPower(voaCond);

		nc.vo.pub.query.ConditionVO[] voaCondpower = null;
		// 2005-05-13 wnj ���Բ�����Ȩ������,for IA,����
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

		int iEleCount = 0;// ����������VO����,����û����������NULL��

		if (voaCond != null)
			iEleCount = voaCond.length;
		// 2004-11-29 :add print status query condition
		ConditionVO voPrintStatus = getPrintStatusCondVO();
		nc.vo.scm.pub.SCMEnv.out("try PrintCount.");
		if (voPrintStatus != null) {
			nc.vo.scm.pub.SCMEnv.out("try PrintCount."
					+ voPrintStatus.getFieldCode() + ","
					+ voPrintStatus.getValue());
			// �����ϴ�ӡ������
			ConditionVO[] voaNewCond = new ConditionVO[iEleCount + 1];
			for (int i = 0; i < iEleCount; i++)
				voaNewCond[i] = voaCond[i];
			// ���һ���Ǵ�ӡ״̬
			voaNewCond[iEleCount] = voPrintStatus;
			return voaNewCond;
		}

		return voaCond;
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-11-22 16:35:52) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String
	 */
	public String getCorpFieldCode() {
		return m_sCorpFieldCode;
	}

	/**
	 * ?user> ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2004-9-22 16:26:28) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return nc.vo.pub.query.ConditionVO[]
	 */
	public ConditionVO[] getDataPowerScmVOs() {
		return m_dataPowerScmVOs;
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-11-22 16:34:24) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * ���ݴ����code��name����һ���Զ����ѯ���� �������ڣ�(2003-9-24 15:34:20)
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
		voQuery.setOperaName("����@");

		voQuery.setDataType(intZero);
		voQuery.setNewMaxLength(new Integer(20));
		voQuery.setReturnType(intOne);// ֻ��������
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
	 * �õ���չ�Ĳ�ѯ������
	 * 
	 * �������ڣ�(2001-8-14 9:52:51)
	 * 
	 * @return nc.vo.pub.query.ConditionVO[]
	 * @param voCons
	 *            nc.vo.pub.query.ConditionVO[]
	 */
	public ConditionVO[] getExpandVOs(ConditionVO[] cvo) {
		ConditionVO[] cvoNew = null;
		Vector<ConditionVO> vCvoNew = new Vector<ConditionVO>(1, 1);
		// �˵�vfree0���������,ͬʱȡ����Ӧ��������FreeVO
		boolean bHasPrintCount = false;// �Ƿ����˴�ӡ������������ֹ�ظ����롣

		for (int i = 0; i < cvo.length; i++) {
			if (!bHasPrintCount
					&& cvo[i].getFieldCode().indexOf(
							nc.vo.scm.print.PrintConst.IPrintCount) > 0)
				bHasPrintCount = true;
			// ��������
			if (cvo[i].getDataType() == 100) {
				String sFieldCode = cvo[i].getFieldCode().trim();
				String sAfterVfree0 = sFieldCode.substring(sFieldCode
						.indexOf("vfree0") + 6);
				// ������ֵ�ı��к��и���
				// if (m_htFreeVO.containsKey(sFieldCode)) {
				// FreeVO fvo= (FreeVO) m_htFreeVO.get(sFieldCode);
				FreeVO fvo = (FreeVO) cvo[i].getRefResult().getRefObj();
				// ���ʮ������VO�������µĽ����
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
				// ��������
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
			// �����ϴ�ӡ������
			voPrintStatus = getPrintStatusCondVO();
			nc.vo.scm.pub.SCMEnv.out("try PrintCount. ex.");
			if (voPrintStatus != null)// �д�������
				iEleCount++;
		}
		cvoNew = new ConditionVO[iEleCount];
		vCvoNew.copyInto(cvoNew);

		if (!bHasPrintCount && voPrintStatus != null) {// �д�������
			nc.vo.scm.pub.SCMEnv.out("try PrintCount.ex."
					+ voPrintStatus.getFieldCode() + ","
					+ voPrintStatus.getValue());
			// ���һ���Ǵ�ӡ״̬
			cvoNew[iEleCount - 1] = voPrintStatus;
		}

		return cvoNew;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-8-14 9:52:51)
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
		// �˵�vfree0���������,ͬʱȡ����Ӧ��������FreeVO
		boolean bHasPrintCount = false;// �Ƿ����˴�ӡ������������ֹ�ظ����롣

		for (int i = 0; i < cvo.length; i++) {
			if (!bHasPrintCount
					&& cvo[i].getFieldCode().indexOf(
							nc.vo.scm.print.PrintConst.IPrintCount) > 0)
				bHasPrintCount = true;
			// ��������
			if (cvo[i].getDataType() == 100) {
				String sFieldCode = cvo[i].getFieldCode().trim();
				String sAfterVfree0 = sFieldCode.substring(sFieldCode
						.indexOf("vfree0") + 6);
				// ������ֵ�ı��к��и���
				// if (m_htFreeVO.containsKey(sFieldCode)) {
				// FreeVO fvo= (FreeVO) m_htFreeVO.get(sFieldCode);
				FreeVO fvo = (FreeVO) cvo[i].getRefResult().getRefObj();
				// ���ʮ������VO�������µĽ����
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
				// ��������
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
			// �����ϴ�ӡ������
			voPrintStatus = getPrintStatusCondVO();
			nc.vo.scm.pub.SCMEnv.out("try PrintCount. ex2.");
			if (voPrintStatus != null)// �д�������
				iEleCount++;
		}
		cvoNew = new ConditionVO[iEleCount];
		vCvoNew.copyInto(cvoNew);

		if (!bHasPrintCount && voPrintStatus != null) {// �д�������
			nc.vo.scm.pub.SCMEnv.out("try PrintCount.ex2."
					+ voPrintStatus.getFieldCode() + ","
					+ voPrintStatus.getValue());
			// ���һ���Ǵ�ӡ״̬
			cvoNew[iEleCount - 1] = voPrintStatus;
		}

		return cvoNew;
	}

	/**
	 * �����ߣ������� ���ܣ������������Ų����ǰ����ʾ������ ������ ���أ� ���⣺ ���ڣ�(2001-8-23 19:03:26)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-8-23 20:49:16) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-8-23 20:49:16) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * ?user> ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2004-9-22 16:14:36) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
			m_htRefField.put("�����֯" + String.valueOf(RETURNCODE), "bodycode");
			m_htRefField.put("�����֯" + String.valueOf(RETURNNAME), "bodyname");
			m_htRefField.put("�����֯" + String.valueOf(RETURNPK), "pk_calbody");
			m_htRefField.put("�����֯" + String.valueOf(-1),
					" from bd_calbody where 0=0 ");
			m_htRefField.put("�ֿ⵵��" + String.valueOf(RETURNCODE), "storcode");
			m_htRefField.put("�ֿ⵵��" + String.valueOf(RETURNNAME), "storname");
			m_htRefField.put("�ֿ⵵��" + String.valueOf(RETURNPK), "pk_stordoc");
			m_htRefField.put("�ֿ⵵��" + String.valueOf(-1),
					" from bd_stordoc where 0=0 ");
			m_htRefField.put("���̵���" + String.valueOf(RETURNCODE), "custcode");
			m_htRefField.put("���̵���" + String.valueOf(RETURNNAME), "custname");
			m_htRefField.put("���̵���" + String.valueOf(RETURNPK), "pk_cumandoc");
			m_htRefField
					.put("���̵���" + String.valueOf(-1),
							" from bd_cubasdoc b ,bd_cumandoc m where b.pk_cubasdoc=m.pk_cubasdoc ");
			m_htRefField.put("�ͻ�����" + String.valueOf(RETURNCODE), "custcode");
			m_htRefField.put("�ͻ�����" + String.valueOf(RETURNNAME), "custname");
			m_htRefField.put("�ͻ�����" + String.valueOf(RETURNPK), "pk_cumandoc");
			m_htRefField
					.put("�ͻ�����" + String.valueOf(-1),
							" from bd_cubasdoc b ,bd_cumandoc m where b.pk_cubasdoc=m.pk_cubasdoc ");
			m_htRefField.put("��Ӧ�̵���" + String.valueOf(RETURNCODE), "custcode");
			m_htRefField.put("��Ӧ�̵���" + String.valueOf(RETURNNAME), "custname");
			m_htRefField.put("��Ӧ�̵���" + String.valueOf(RETURNPK), "pk_cumandoc");
			m_htRefField
					.put("��Ӧ�̵���" + String.valueOf(-1),
							" from bd_cubasdoc b ,bd_cumandoc m where b.pk_cubasdoc=m.pk_cubasdoc ");
			m_htRefField.put("���ŵ���" + String.valueOf(RETURNCODE), "deptcode");
			m_htRefField.put("���ŵ���" + String.valueOf(RETURNNAME), "deptname");
			m_htRefField.put("���ŵ���" + String.valueOf(RETURNPK), "pk_deptdoc");
			m_htRefField.put("���ŵ���" + String.valueOf(-1),
					" from bd_deptdoc where 0=0 ");
			m_htRefField.put("��Ա����" + String.valueOf(RETURNCODE), "psncode");
			m_htRefField.put("��Ա����" + String.valueOf(RETURNNAME), "psnname");
			m_htRefField.put("��Ա����" + String.valueOf(RETURNPK), "pk_psndoc");
			m_htRefField.put("��Ա����" + String.valueOf(-1),
					" from bd_psndoc where 0=0 ");
			// added by czp , 2006-09-21
			m_htRefField.put("�ɹ���֯" + String.valueOf(RETURNCODE), "code");
			m_htRefField.put("�ɹ���֯" + String.valueOf(RETURNNAME), "name");
			m_htRefField.put("�ɹ���֯" + String.valueOf(RETURNPK), "pk_purorg");
			m_htRefField.put("�ɹ���֯" + String.valueOf(-1),
					" from bd_purorg where 0=0 ");

			// Added by Shaw on Sep 27, 2006
			m_htRefField.put("�������" + String.valueOf(RETURNCODE), "invcode");
			m_htRefField.put("�������" + String.valueOf(RETURNNAME), "invname");
			m_htRefField.put("�������" + String.valueOf(RETURNPK), "pk_invmandoc");
			m_htRefField
					.put("�������" + String.valueOf(-1),
							" from bd_invbasdoc b, bd_invmandoc m where b.pk_invbasdoc = m.pk_invbasdoc ");
			m_htRefField.put("�������" + String.valueOf(RETURNCODE),
					"invclasscode");
			m_htRefField.put("�������" + String.valueOf(RETURNNAME),
					"invclassname");
			m_htRefField.put("�������" + String.valueOf(RETURNPK), "pk_invcl");
			m_htRefField.put("�������" + String.valueOf(-1),
					" from bd_invcl where 0=0 ");

			m_htRefField.put("��Ŀ������" + String.valueOf(RETURNCODE), "jobcode");
			m_htRefField.put("��Ŀ������" + String.valueOf(RETURNNAME), "jobname");
			m_htRefField.put("��Ŀ������" + String.valueOf(RETURNPK),
					"pk_jobmngfil");
			m_htRefField
					.put("��Ŀ������" + String.valueOf(-1),
							" from bd_jobbasfil b, bd_jobmngfil m where b.pk_jobbasfil = m.pk_jobbasfil ");
			//
			m_htRefField.put("��������" + String.valueOf(RETURNCODE), "areaclcode");
			m_htRefField.put("��������" + String.valueOf(RETURNNAME), "areaclname");
			m_htRefField.put("��������" + String.valueOf(RETURNPK), "pk_areacl");
			m_htRefField.put("��������" + String.valueOf(-1),
					" from bd_areacl where 0=0 ");

			// salecorp added 20070430 zhongwei
			m_htRefField.put("������֯" + String.valueOf(RETURNCODE),
					"vsalestrucode");
			m_htRefField.put("������֯" + String.valueOf(RETURNNAME),
					"vsalestruname");
			m_htRefField.put("������֯" + String.valueOf(RETURNPK), "csalestruid");
			m_htRefField.put("������֯" + String.valueOf(-1),
					" from bd_salestru where 0=0 ");

			// ������Ŀ֧������Ȩ�� ������
			m_htRefField.put("������Ŀ" + String.valueOf(RETURNCODE),
					"ccheckitemcode");
			m_htRefField.put("������Ŀ" + String.valueOf(RETURNNAME),
					"ccheckitemname");
			m_htRefField.put("������Ŀ" + String.valueOf(RETURNPK), "ccheckitemid");/*-=notranslate=-*/
			m_htRefField.put("������Ŀ" + String.valueOf(-1),
					" from qc_checkitem where 0=0 ");
		}

		return m_htRefField.get(sRefNodeName + String.valueOf(flag));
	}

	/**
	 * ?user> ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2005-1-7 11:56:19) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return nc.ui.pub.beans.UIRefPane
	 * @param sFieldCode
	 *            java.lang.String
	 */
	private UIRefPane getRefPaneByCode(String sFieldCode) {

		nc.vo.pub.query.QueryConditionVO[] voaConData = getConditionDatas();
		if (voaConData == null || voaConData.length == 0)
			return null;
		// ����Ϊ�Բ��յĳ�ʼ��

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
		// ����Ϊ�Բ��յĳ�ʼ��

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
	 * ���� tfUnitCode ����ֵ��
	 * 
	 * @return javax.swing.JTextField
	 */
	/* ���棺�˷������������ɡ� */
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
	 * �˴����뷽��˵���� �������ڣ�(2001-7-25 13:50:05)
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
	 * �˴����뷽��˵���� �������ڣ�(2001-7-25 14:48:18)
	 */
	public String getUnitCode() {
		return gettfUnitCode().getText();

	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-8-23 20:49:16) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-8-23 20:49:16) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
				if (getConditionVO()[i].getDataType() == 5)// ����һ�����Լ����Ĳ���
					al
							.add(getConditionVO()[i].getRefResult().getRefPK()
									.trim());
				else
					// ���ò��գ���ҪΪ���ڲ��գ�Լ����Ĳ��յ����
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
	 * �����ߣ������� ���ܣ���Where�Ӿ��н��������������Vfree0ֵ�� ��Combobox���ͽ�ȡIDֵ(����) ������ ���أ� ���⣺
	 * ���ڣ�(2001-8-12 18:00:24) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String
	 */
	public String getWhereSQL(ConditionVO[] cvo) {

		return super.getWhereSQL(getExpandVOs(cvo));
	}

	/**
	 * �����ߣ������� ���ܣ���Where�Ӿ��н��������������Vfree0ֵ�� ��Combobox���ͽ�ȡIDֵ(����) ������ ���أ� ���⣺
	 * ���ڣ�(2001-8-12 18:00:24) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String
	 */
	public String getWhereSQL(ConditionVO[] cvo, String sFreeAlianame) {

		return super.getWhereSQL(getExpandVOs(cvo, sFreeAlianame));
	}

	/**
	 * �����ߣ������� ���ܣ���ʼ������ ������ ���أ� ���⣺ ���ڣ�(2001-8-27 15:15:52) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public void initCorpRef(String sCorpFieldCode, String sShowCorpID,
			ArrayList alAllCorpIDs) { 
		// ����Ϊ�Թ�˾���յĳ�ʼ��
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
	 * �����ߣ������� ���ܣ���ʼ������ ������ ���أ� ���⣺ ���ڣ�(2001-8-27 15:15:52) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public void initLocatorRef(String sLocatorFieldCode, String sInvFieldCode,
			String sWhFieldCode) {
		// ����Ϊ�Ի�λ���յļ�¼
		ArrayList<String> altemp = new ArrayList<String>();
		altemp.add(sLocatorFieldCode);
		altemp.add(sInvFieldCode);
		altemp.add(sWhFieldCode);
		m_alLocatorRef.add(altemp);
	}

	/**
	 * �����ߣ������� ���ܣ���ʼ������ ������ ���أ� ���⣺ ���ڣ�(2001-8-27 15:15:52) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public void initQueryDlgRef() {
		// ����Ϊ�Բ��յĳ�ʼ��
		// һ��ֿ�
		setRefInitWhereClause("head.cwarehouseid", "�ֿ⵵��",
				"gubflag='N'  and pk_corp=", "pk_corp");
		// '" + m_sCorpID + "'");
		// ���һ��ֿ�
		setRefInitWhereClause("cinwarehouseid", "�ֿ⵵��",
				"gubflag='N'  and pk_corp=", "pk_corp");
		// ����һ��ֿ�
		setRefInitWhereClause("coutwarehouseid", "�ֿ⵵��",
				"gubflag='N'  and pk_corp=", "pk_corp");
		// ��Ʒ�ֿ�
		setRefInitWhereClause("cwastewarehouseid", "�ֿ⵵��",
				"gubflag='Y'  and pk_corp=", "pk_corp");
		// �ͻ�
		setRefInitWhereClause("head.ccustomerid", "�ͻ�����",
				"(custflag ='0' or custflag ='2') and  bd_cumandoc.pk_corp=",
				"pk_corp");
		// '" + m_sCorpID + "'");
		// ��Ӧ��
		setRefInitWhereClause("head.cproviderid", "��Ӧ�̵���",
				"(custflag ='1' or custflag ='3') and  bd_cumandoc.pk_corp=",
				"pk_corp");
		// ��Ӧ��
		setRefInitWhereClause("cvendorid", "��Ӧ�̵���",
				"(custflag ='1' or custflag ='3')  and bd_cumandoc.pk_corp=",
				"pk_corp");
		// '" + m_sCorpID + "'");
		// ���,��λ������˴����Ϊ�Ƿ����,�����񣬷��ۿ۴��
		setRefInitWhereClause("cinventorycode", "�������",
				" bd_invbasdoc.discountflag='N' and bd_invbasdoc.laborflag='N' "
						+ "  and bd_invmandoc.pk_corp=", "pk_corp");
		// '" + m_sCorpID + "'");
		// ���,��λ������˴����Ϊ�Ƿ����,�����񣬷��ۿ۴��
		setRefInitWhereClause("body.cinventoryid", "�������",
				" bd_invbasdoc.discountflag='N' and bd_invbasdoc.laborflag='N' "
						+ "  and bd_invmandoc.pk_corp=", "pk_corp");

		// �ɱ����� add by hanwei 2003-11-06
		setRefInitWhereClause("ccostobject", "���ϵ���",
				"sfcbdx='Y' and bd_produce.pk_corp=", "pk_corp");

		// ----------- �շ���� cdispatcherid --------------
		setRefInitWhereClause("cdispatcherid", "�շ����",
				" pk_corp='0001' or pk_corp=", "pk_corp");
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-12-27 18:53:45) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return boolean
	 */
	public boolean isCloseOK() {
		return getResult() == ID_OK;
	}

	/**
	 * �����ߣ������� ���ܣ����ø�������Ӧ�Ĺ�˾�ʹ�����ڵ��ֶε�ӳ��� ��������һ��Ϊ���������ֶΣ� �ڶ���Ϊһ�����飬�ں�����Ԫ�أ�
	 * ��һ��Ϊ��˾�ֶΣ��ڶ���Ϊ����ֶ� ���أ� ���⣺ ���ڣ�(2001-8-23 18:56:19) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * �����ߣ������� ���ܣ��趨��Щ�ֶν����ؼ��ֶ��޸Ķ��Զ���� ������ ���أ� ���⣺ ���ڣ�(2001-8-13 13:10:28)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * �����ߣ������� ���ܣ���Ի����������Ӧ�ֶε������� ��Ӧ�����ģ�����Ӧ����Ӧ�ֶε�����Ϊ200 ������ȡֵ����values,
	 * values��Ӧ����һ���մ�--""��ʹѡ���п�ѡ�� values�е�һ��ΪID���ڶ���Ϊname����ʾ�ã� ���أ� ���⣺
	 * ���ڣ�(2001-8-12 11:19:14) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * �����ߣ������� ���ܣ���ʼ��ϵͳ���� ������ ʾ����
	 * 
	 * ���أ� ���⣺ ���ڣ�(2001-8-27 11:16:57) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * ?user> ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2004-9-24 9:24:10) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 */
	public void setDefaultCorps(String[] sNewCorps) {
		m_sdefaultcorps = sNewCorps;
	}

	/**
	 * �����ߣ������� ���ܣ�����Դ�ֶε���ʾֵ����ֵѡ��ı�ʱ������Ӧ��Ĳ��ո��ģ� ��û�ж�Ӧ��ֵ�Ĳ��գ���Ӧ���ֹ���롣 ������
	 * Object[][]�ṹ��Object[i][0] Ϊ��Դ�ֶεĿ���ֵ��Object[i][1]Ϊ��Ӧ�ֶε�Ref���� ���أ� ���⣺
	 * ���ڣ�(2001-9-3 11:42:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * �����ߣ������� ���ܣ���Ի����������Ӧ�ֶε���������� ��Ӧ�����ģ�����Ӧ����Ӧ�ֶε�����Ϊ100 ������ ���أ� ���⣺
	 * ���ڣ�(2001-8-12 10:56:11) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return boolean
	 * @param sFieldCode
	 *            java.lang.String
	 */
	public boolean setFreeItem(String sFieldCode, String sInvFieldCode) {
		try {
			FreeItemRefPane firpFreeItemRefPane = new FreeItemRefPane();

			// �ñ༭��
			UIFreeItemCellEditor editor = new UIFreeItemCellEditor(
					firpFreeItemRefPane);
			setValueRef(sFieldCode, editor);

			// ��¼�����������Ķ��ձ�
			m_htFreeItemInv.put(sFieldCode.trim(), sInvFieldCode.trim());

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * �����ߣ������� ���ܣ���ָ���ֶ��趨��ʼ���ڣ� ��booleanֵ�������³�������ĩ�� �·����ɵ�¼���ھ��� ������ ���أ� ���⣺
	 * ���ڣ�(2001-12-7 14:57:50) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * �����ߣ������� ���ܣ���ָ���ֶ��趨��ʼ���ڣ� ��booleanֵ�������³�������ĩ�� �·����ɵ�¼���ھ��� ������ ���أ� ���⣺
	 * ���ڣ�(2001-12-7 14:57:50) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * �����ߣ������� ���ܣ���Ի����������Ӧ�ֶε����κŲ��� ��Ӧ�����ģ�����Ӧ����Ӧ�ֶε�����Ϊ400 ������ ���أ� ���⣺
	 * ���ڣ�(2001-8-29 13:40:07) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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

			// �ñ༭��
			UIRefCellEditor editor = new UIRefCellEditor(lqrLotQueryRef);
			setValueRef(sFieldLotCode, editor);

			// ��¼��������κŵĶ��ձ�
			m_htLotInv.put(sFieldLotCode.trim(), sFieldInvCode.trim());

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * �����ߣ������� ���ܣ���ʼ��ϵͳ���� ���������������ֶΣ��������ƣ����յ�where�Ӿ䣬���յ�where�Ӿ�������ֶ�ȡֵ��Դ ʾ����
	 * //����Ϊ�Բ��յĳ�ʼ�� //һ��ֿ� ivjQueryConditionDlg.setRefInitWhereClause(
	 * "cwarehouseid", "�ֿ⵵��", "gubflag='N' and sealflag='N' and pk_corp=",
	 * "pk_corp"); //'" + m_sCorpID + "'"); //���һ��ֿ�
	 * ivjQueryConditionDlg.setRefInitWhereClause( "cinwarehouseid", "�ֿ⵵��",
	 * "gubflag='N' and sealflag='N' and pk_corp=", "pk_corp"); //����һ��ֿ�
	 * ivjQueryConditionDlg.setRefInitWhereClause( "coutwarehouseid", "�ֿ⵵��",
	 * "gubflag='N' and sealflag='N' and pk_corp=", "pk_corp"); //��Ʒ�ֿ�
	 * ivjQueryConditionDlg.setRefInitWhereClause( "cwarehouseid", "�ֿ⵵��",
	 * "gubflag='Y' and sealflag='N' and pk_corp=", "pk_corp"); //�ͻ�
	 * ivjQueryConditionDlg.setRefInitWhereClause( "ccustomerid", "���̵���",
	 * "custflag ='Y' and bd_cumandoc.pk_corp=", "pk_corp"); //'" + m_sCorpID +
	 * "'"); //��Ӧ�� ivjQueryConditionDlg.setRefInitWhereClause( "cproviderid",
	 * "��Ӧ�̵���", "custflag ='N' and bd_cumandoc.pk_corp=", "pk_corp"); //'" +
	 * m_sCorpID + "'"); //���,��λ������˴����Ϊ�Ƿ����
	 * ivjQueryConditionDlg.setRefInitWhereClause( "cinventorycode", "�������",
	 * "bd_invmandoc.sealflag ='N' and bd_invmandoc.pk_corp=", "pk_corp"); //'" +
	 * m_sCorpID + "'"); //���,��λ������˴����Ϊ�Ƿ����
	 * ivjQueryConditionDlg.setRefInitWhereClause( "cinventoryid", "�������",
	 * "bd_invmandoc.sealflag ='N' and bd_invmandoc.pk_corp=", "pk_corp");
	 * 
	 * ���أ� ���⣺ ���ڣ�(2001-8-27 11:16:57) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
			// ����Ӧ�ò��ü��null
			for (int i = m_alRefInitWhereClause.size() - 1; i >= 0; i--) {
				sMyFieldCode = m_alRefInitWhereClause.get(i).get(0).toString();
				// �������ȥ���еļ�¼�������µġ�
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
	 * �����ߣ������� ���ܣ���ʼ������,ע����Դ����ֻ��Ϊ�ֶδ���ʽ���� ���������������ֶΣ����յ�ǰwhere�Ӿ䣬���յĺ�where�Ӿ䣬
	 * ���յ�where�Ӿ�������ֶ�ȡֵ��Դ�Ͷ�Ӧ���������е�key, �Ƿ�ʹ��null�Ϳմ���־ ʾ���� ���أ� ���⣺ ���ڣ�(2001-8-27
	 * 11:16:57) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * �����ߣ������� ���ܣ���Ի����������Ӧ�ֶε�ͳ�Ʒ�Χ���� ��Ӧ�����ģ�����Ӧ����Ӧ�ֶε�����Ϊ350
	 * 
	 * ���Ӧ�ֶ�Ϊͳ�ƽṹ���� ��Ӧ�����ģ�����Ӧ����Ӧ�ֶε�����Ϊ300 ������ ���أ� ���⣺ ���ڣ�(2001-8-29 12:50:51)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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

			// �ñ༭��
			UIRefCellEditor editorh = new UIRefCellEditor(
					(UIRefPane) srpStathRefPane1);
			setValueRef(sFieldStateHCode, editorh);

			UIRefCellEditor editorb = new UIRefCellEditor(
					(UIRefPane) srpStatbRefPane2);
			setValueRef(sFieldStateBCode, editorb);

			// ��¼ͳ�ƽṹ��ͳ�Ʒ�Χ�Ķ��ձ�
			m_htStatBtoH.put(sFieldStateBCode.trim(), sFieldStateHCode.trim());
			// ��¼ͳ�ƽṹ�빫˾�Ķ��ձ�
			m_htStatHtoCorp.put(sFieldStateHCode.trim(), sFieldCorpcode.trim());

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * �����ߣ������� ���ܣ���ʼ������ ������ ���أ� ���⣺ ���ڣ�(2001-8-13 15:45:09) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * �����ʼ�����������,��ʼ������������
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
		// �漰��ϵͳ���յĳ�ʼ��
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
				if ("�ֿ⵵��".equals(refname))
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
						// 2005-05-27:���˾�����ѧ�����ֿ⡢�����֯����Ȩ�޿������⣨ֻ�����˿����֯��Ȩ�ޣ���
						// ��Ϊ�ֿ�����п����֯����Ȩ���Ƿ�װ��setWhereString�����еģ��������������֮��ʹ����Ȩ��ʧЧ��
						// ��Ϊʹ��addWherePart( ).
						// ����ҵ��ģ��ֻʹ��addWherePart�� ����������Ҫ���׸��ǲ��յ�ȱʡ���ơ�
						nc.vo.scm.pub.SCMEnv.out("wh sql="
								+ alRefRow.get(RI_WHERE_CLAUSE).toString()
										.trim() + "'" + sPK + "'");
						swhere = new StringBuffer();
						if (refwhere != null)
							swhere.append(refwhere);
						if (refcheck != null)
							swhere.append("'" + sPK + "'");

						if ("�ֿ⵵��".equals(refname))
							uirp.getRefModel().addWherePart(
									" and " + swhere.toString());
						else
							uirp.setWhereString(swhere.toString());
						changeValueRef(refname, uirp);
						break;
					}
				} // 2002-10-25 WNJ ADD BELOW "DISTINCT" STATEMENT
				if ("���̵���".equals(refname))
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

					if ("�ֿ⵵��".equals(refname))
						uirp.getRefModel().addWherePart(
								" and " + swhere.toString());
					else
						uirp.setWhereString(swhere.toString());
					changeValueRef(refname, uirp);

				}
				break;
			}
		} // �Թ�˾���յĳ�ʼ��
		for (int i = 0; i < m_alCorpRef.size(); i++) {
			alRefRow = m_alCorpRef.get(i);
			if (alRefRow.get(0).toString().trim().equals(
					qcvo.getFieldCode().trim())) {
				UIRefPane uirp = new UIRefPane();
				uirp.setRefNodeName("��˾Ŀ¼");
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
		// �Ի�λ���յĳ�ʼ��
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
		// �Զ����Ʋ��յĳ�ʼ��
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

		super.setValueRefEditor(iRow, iCol); // ��������
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
			} // �ñ༭��
			UIFreeItemCellEditor editor = new UIFreeItemCellEditor(
					firpFreeItemRefPane);
			getUITabInput().getColumnModel().getColumn(iCol).setCellEditor(
					editor);
		} else if (qcvo.getDataType().intValue() == 200) { // ��������
			// do nothing

		} else if (qcvo.getDataType().intValue() == 300) { // ��ͳ�ƽṹ
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
				// �ñ༭��
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
		} else if (qcvo.getDataType().intValue() == 350) { // ��ͳ�Ʒ�Χ
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

				// �ñ༭��
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
		} else if (qcvo.getDataType().intValue() == 400) { // �����κ�
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
			} // �ñ༭��
			UIRefCellEditor editor = new UIRefCellEditor(lqrLotQueryRef);
			getUITabInput().getColumnModel().getColumn(iCol).setCellEditor(
					editor);
			getUITabInput().setValueAt(sLotValue, iRow, iCol);
		} else if (qcvo.getDataType().intValue() == 5) { // ���Ǵ�������
			if (m_htAstCorpInv.containsKey(sFieldCode)) {
				// �Ǹ���������
				String[] sCorpInv = m_htAstCorpInv.get(sFieldCode);
				String sCorp = sCorpInv[0];
				String sCorpID = "";
				String sInv = sCorpInv[1];
				String sInvID = "";
				sCorpID = getPKbyFieldCode(sCorp);
				sInvID = getPKbyFieldCode(sInv);
				UIRefPane refCast = new UIRefPane();
				refCast.setRefNodeName("��������");
				refCast.getRefModel().setSealedDataShow(true);
				m_voInvMeas.filterMeas(sCorpID, sInvID, refCast);
				UIRefCellEditor editor = new UIRefCellEditor(refCast);
				getUITabInput().getColumnModel().getColumn(iCol).setCellEditor(
						editor);
			}
		} // �ɱ䶯�������ñ����ò���
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
	 * �˴����뷽��˵��:�����Զ�����Ŀ sDefInvBasAliasName���������������ѯ����� sDefInvManAliasName��
	 * �����������ѯ�����
	 * 
	 * ��ѯitem�е�itemkey=��ѯ����������ǰ׺+itemkey
	 * 
	 * �������ڣ�(2003-9-27 11:10:48)
	 */
	public void showBillDefCondition(String sHeadDefAliasName,
			String sBodyDefAliasName) {

		Hashtable<String, String> htbInvDef = new Hashtable<String, String>();
		htbInvDef.put("��Ӧ��/ARAP����ͷ", sHeadDefAliasName);
		htbInvDef.put("��Ӧ��/ARAP������", sBodyDefAliasName);
		showDefCondition(htbInvDef);
		return;
	}

	/**
	 * �˴����뷽��˵��:�����Զ�����Ŀ sDefInvBasAliasName���������������ѯ����� sDefInvManAliasName��
	 * �����������ѯ�����
	 * 
	 * ��ѯitem�е�itemkey=��ѯ����������ǰ׺+itemkey
	 * 
	 * �������ڣ�(2003-9-27 11:10:48)
	 */
	public void showDefCondition(String sDefInvBasAliasName,
			String sDefInvManAliasName) {

		Hashtable<String, String> htbInvDef = new Hashtable<String, String>();
		htbInvDef.put("�������", sDefInvBasAliasName);
		htbInvDef.put("���������", sDefInvManAliasName);
		showDefCondition(htbInvDef);
		return;
	}

	/**
	 * �˴����뷽��˵��:�����Զ�����Ŀ--���ݵı�ͷ�ͱ���
	 * 
	 * ��ѯitem�е�itemkey=��ѯ����������ǰ׺+itemkey AppendStyle:�ֶα������ɷ�� 1: ��ԭ���ֶ������ӱ���ǰ׺
	 * ###.def 2: ��ԭ���ֶ������Ӻ�׺��def+"####" �磺vuserdef5h��vuserdef5��Ϊԭʼ�ֶ����ƣ� 3:
	 * ��ԭ���ֶ�������ǰ׺��"####"+def �磺hvuserdef5
	 * 
	 * �������ڣ�(2003-9-27 11:10:48)
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
			Hashtable<String, UIRefPane> htDef = new Hashtable<String, UIRefPane>(); // key��FieldCode��Value��UIRefPane
			Vector<QueryConditionVO> vAddDef = new Vector<QueryConditionVO>(); // ������ģ���ﶨ�������
			if (sResult != null) {
				paneDefs = new nc.ui.pub.beans.UIRefPane[sResult.length];
				for (int i = 0; i < sResult.length; i++) {
					String[] sTemp = sResult[i];
					if (sTemp.length > 1) {
						// ������
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
								if (sObjName.equalsIgnoreCase("�������")
										|| sObjName.equalsIgnoreCase("���������"))
									sFieldCode = htbDocumentRef.get(sObjName)
											+ "." + sFieldName;
								else if (sObjName
										.equalsIgnoreCase("��Ӧ��/ARAP����ͷ")) {
									sFieldCode = "vuser" + sFieldName
											+ htbDocumentRef.get(sObjName);
								} else if (sObjName
										.equalsIgnoreCase("��Ӧ��/ARAP������")) {
									sFieldCode = "vuser" + sFieldName;
								} else
									sFieldCode = sFieldName;
							}

							vAddDef.addElement(getDefaultQueryCndVO(sFieldCode,
									sDefName));
							// �����0��ʼ���Զ�������1��ʼ
							if (sDefType.equals("ͳ��")) {
								// ���ò���
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
								paneDefs[i].setRefNodeName("�Զ������");
								paneDefs[i].getRef().getRefModel()
										.addWherePart(sWhereString);
							} else if (sDefType.equals("����")) {
								paneDefs[i] = new UIRefPane();
								paneDefs[i].setRefNodeName("����");
							} else if (sDefType.equals("��ע")) {
								paneDefs[i] = new UIRefPane();
								paneDefs[i].setMaxLength(iLengthNum);
								paneDefs[i].setButtonVisible(false);
							} else if (sDefType.equals("����")) {
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
			} // ����û�����õ��Զ������ʾ
			QueryConditionVO[] vos = getConditionDatas();
			Vector<QueryConditionVO> vTemp = new Vector<QueryConditionVO>();
			for (int i = 0; i < vos.length; i++) {
				String sCode = vos[i].getFieldCode();
				if (sCode.indexOf("def") < 0) {
					vTemp.addElement(vos[i]);
					continue;
				} // ���� by hanwei ������ϴμ�����Զ���������2003-10-22
				// if (!htDef.containsKey(sCode)) {
				// //vos[i].setIfDefault(new UFBoolean(false));
				// continue;
				// }
				vTemp.addElement(vos[i]);
			} // ����ģ����û�е��Զ����ѯ����
			for (int j = 0; j < vAddDef.size(); j++) {
				vTemp.addElement(vAddDef.elementAt(j));
			}
			QueryConditionVO[] vosResult = new QueryConditionVO[vTemp.size()];
			vTemp.copyInto(vosResult);
			// ��������ģ������
			setConditionDatas(vosResult);
			// ���ò���
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
	 * �˴����뷽��˵��:�����Զ�����Ŀ sDefInvBasAliasName���������������ѯ����� sDefInvManAliasName��
	 * �����������ѯ�����
	 * 
	 * ��ѯitem�е�itemkey=��ѯ����������ǰ׺+itemkey
	 * 
	 * �������ڣ�(2003-9-27 11:10:48)
	 */
	public void showInvDefCondition(String sDefInvBasAliasName,
			String sDefInvManAliasName) {

		Hashtable<String, String> htbInvDef = new Hashtable<String, String>();
		htbInvDef.put("�������", sDefInvBasAliasName);
		htbInvDef.put("���������", sDefInvManAliasName);
		showDefCondition(htbInvDef);
		return;
	}

	/**
	 * �����ˣ����˾�
	 * 
	 * ���ܣ��Ƿ���ʾ��ӡ״̬PANEL
	 * 
	 * ������
	 * 
	 * ���أ�
	 * 
	 * �쳣��
	 * 
	 * ���ڣ�(2004-11-25 16:27:37) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param isShowPrintStatusPanel
	 *            boolean
	 */
	public void setShowPrintStatusPanel(boolean isShowPrintStatusPanel) {
		if (isShowPrintStatusPanel)
			this.getUITabbedPane().insertTab(getUIPanelPrintStatus().getName(),
					null, getUIPanelPrintStatus(), null,
					getUITabbedPane().getTabCount());
		else if (m_isShowPrintStatusPanel)// �Ѿ���ʾ���ˣ�����Ҫȥ����ʾ
			this.getUITabbedPane().removeTabAt(
					getUITabbedPane().getTabCount() - 1);
		// else do nothing.
		m_isShowPrintStatusPanel = isShowPrintStatusPanel;
		// getUIPanelPrintStatus().setVisible(isShowPrintStatusPanel);
	}

	/**
	 * �����ˣ����˾�
	 * 
	 * ���ܣ��Ƿ���ʾ��ӡ״̬PANEL
	 * 
	 * ������
	 * 
	 * ���أ�
	 * 
	 * �쳣��
	 * 
	 * ���ڣ�(2004-11-25 16:27:37) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return boolean
	 */
	private boolean isShowPrintStatusPanel() {
		return m_isShowPrintStatusPanel;
	}

	/**
	 * ���� ��ӡ״̬PANE��
	 * 
	 * @return nc.ui.pub.beans.UIPanel
	 */
	/* ���棺�˷������������ɡ� */
	private QryPrintStatusPanel getUIPanelPrintStatus() {
		if (m_QryPrintStatusPanel == null) {
			try {
				m_QryPrintStatusPanel = new QryPrintStatusPanel();
				m_QryPrintStatusPanel
						.setName(nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"common", "UC000-0001993")/* @res "��ӡ״̬" */);
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
	 * �õ���ӡ�������������û�ѡ��Ľ����
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ʹ��ʾ���� QryPrintStatusPanel.SEL_ALL=-1;//ȫ��
	 * QryPrintStatusPanel.SEL_NOT_PRINTED= 0;//û�����
	 * QryPrintStatusPanel.SEL_PRINTED= 1;//�����
	 * <p>
	 * <b>����˵��</b>
	 * 
	 * @return
	 * <p>
	 * @author czp
	 * @time 2007-3-5 ����02:53:33
	 */
	public int getPrintStatus() {
		return getUIPanelPrintStatus().getStatus();
	}

	/**
	 * ���ݴ�ӡ�������������û�ѡ��Ľ��������������ز�ѯ��������
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ʹ��ʾ��
	 * <p>
	 * <b>����˵��</b>
	 * 
	 * @param sTableAlias
	 * @return
	 *            <p>
	 * @author czp
	 * @time 2007-3-5 ����02:56:46
	 */
	public String getPrintResult(String sTableAlias) {

		int iSelRslt = getPrintStatus();

		// ��ȫ���������Ч�ڲ�����
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
		// ����ӡ����
		if (iSelRslt == QryPrintStatusPanel.SEL_PRINTED) {
			sResSql = sResSql + sTableAlias + PrintConst.IPrintCount + ">0 ";
		}
		// ��δ��ӡ����
		else if (iSelRslt == QryPrintStatusPanel.SEL_NOT_PRINTED) {
			sResSql = sResSql + " (" + sTableAlias + PrintConst.IPrintCount
					+ "<=0 OR " + sTableAlias + PrintConst.IPrintCount
					+ " IS NULL ) ";
		}

		return sResSql;
	}

	/**
	 * �����ˣ����˾�
	 * 
	 * ���ܣ� �õ���ӡ����VO
	 * 
	 * ������
	 * 
	 * ���أ�
	 * 
	 * �쳣��
	 * 
	 * ���ڣ�(2004-11-25 16:33:33) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return nc.vo.pub.query.ConditionVO
	 */
	private ConditionVO getPrintStatusCondVO() {
		if (!isShowPrintStatusPanel())
			return null;
		else {
			int iStatus = getUIPanelPrintStatus().getStatus();
			// ѡ��ȫ���Ļ��������������
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
	 * @return ���� m_isDataPowerSqlReturned��
	 */
	public boolean isDataPowerSqlReturned() {
		return m_isDataPowerSqlReturned;
	}

	/**
	 * ����ģ��field_codeֵ���ж��Ƿ�����������ģ����(������Ϊ֧�ִ��벻���ڵ�ģ��field_code���)
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
	 * ��ʼ���Ƿ���������Ȩ�޵�����
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ʹ��ʾ��
	 * <p>
	 * <b>����˵��</b>
	 * 
	 * @param vecTableNames
	 * @param vecTableShowNames
	 * @param pk_corp
	 *            <p>
	 * @author donggq
	 * @time 2009-8-7 ����02:02:47
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
	 * �ӻ����л�ȡ��Դ�Ƿ�������Ȩ�ޣ����������û�����ѯ������֮
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
	 * �ӻ����л�ȡȨ�޲�ѯ�����������������û�����ѯ������֮
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

		// if ( pk_corps.length == 1 ) { // ����˾Ȩ�޵���ǰ̨���棬������������songhy��start
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
		// else { // �๫˾Ȩ�޵��ú�̨��ѯ�����������ߣ�songhy��end
		// strPowerSubSql = DPServ.getSubSqlForMutilCorp(dpTableName, sRefName,
		// sCurUser,pk_corps);
		// }

		m_mapPowerSubSqlCache.put(strKey, strPowerSubSql == null ? ""
				: strPowerSubSql);

		return strPowerSubSql;
	}

	/**
	 * ȡ��ģ����������Ҫ��������Ȩ�޵Ĳ��յ�Ȩ�޿���sql
	 * 
	 * @author guyan
	 * @Data 2006-03-25
	 * @param sCurUser
	 *            ��ǰ����Աid
	 * @param pk_corps
	 *            ��Ҫȡ��Ȩ�޵Ĺ�˾pk pk_corps[0]����Ϊ��ǰ��˾pkcorp
	 * @param sRefNames
	 *            ��Ҫȡ��Ȩ�޵Ĳ��ձ��루���ձ���Ӧ����uap����ĺ������ƣ����磺�����֯��
	 *            Ŀǰ֧�ֵĲ����У������֯���ֿ⵵�������̵����� �ͻ���������Ӧ�̵��������ŵ�������Ա����
	 * @param sRefSqlFieldCodes
	 *            �ڲ�ѯ����иò��ն�Ӧ���ֶ����� ���磺tableA.invcode
	 * @return iReturntypes ��Ӧ���շ���ֵ�Ǳ��룬���ƻ���pk
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
				// �ж���Щ������ģ����������Ȩ��
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
					 * by czp, 2006-09-22,�����µ��� 1)���Ե����ߴ��ݲ���Ϊ׼�� ����Ҫ�� : ���ݿ��б���
					 * data_type = 5�� 2)��ԭ���� consultcode ƥ�䣬����Ϊ��field_codeƥ�� ��
					 * ����Ҫ�� �����ݿ��� consult = "������������"
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
				// ����Ҫ����Ȩ�޿��ƵĲ����ṩ�Ӳ�ѯ
				if (htCtrRefDataPower.size() > 0) {
					// ��֯��Ҫ��ѯ�Ƿ���������Ȩ�޵�����
					Vector<String> vecTableNames = new Vector<String>();
					Vector<String> vecRefNames = new Vector<String>();
					for (int i = 0; i < sRefNames.length; i++) {
						// �����filed_code����ģ���У��򲻴���
						if (!isExistFieldCode(sRefSqlFieldCodes[i])) {
							continue;
						}
						if (htCtrRefDataPower.get(sRefSqlFieldCodes[i]) != null) {
							String dpTableName = nc.ui.bd.datapower.DataPowerServ
									.getRefTableName(sRefNames[i]); // ���ݲ������ƻ�û������ݱ���
							vecTableNames.add(dpTableName);
							vecRefNames.add(sRefNames[i]);
						}
					}
					// ��ʼ���Ƿ���������Ȩ�޵�����
					// initUsedDataPowerInfo(vecTableNames,vecRefNames,pk_corps[0]);

					Vector<ConditionVO> vecVO = new Vector<ConditionVO>();
					for (int i = 0; i < sRefNames.length; i++) {
						// �����filed_code����ģ���У��򲻴���
						if (!isExistFieldCode(sRefSqlFieldCodes[i])) {
							continue;
						}
						if (htCtrRefDataPower.get(sRefSqlFieldCodes[i]) != null) {
							String dpTableName = nc.ui.bd.datapower.DataPowerServ
									.getRefTableName(sRefNames[i]); // ���ݲ������ƻ�û������ݱ���
							// ����ò���û������Ȩ�ޣ�������
							// if (!isUsedDataPower(dpTableName, sRefNames[i],
							// pk_corps[0])) {
							// continue;
							// }
							String strPowerSubSql = getPowerSubSql(dpTableName,
									sRefNames[i], sCurUser, pk_corps);
							if (StringUtil.isEmptyWithTrim(strPowerSubSql))
								continue;

							// Ŀǰ֧�ֲ��ֻ�������Ȩ��
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
	 * ������ж�������Ŀ���ǣ�����Ҫ�๫˾�����¿���Ȩ��ʱ��ʹ�õ�Ȩ��vo��m_cCtrTmpDataPowerVOs
	 * ��super.getConditionVO�еõ���vo����Ȩ���Ӳ�ѯvo�����ǲ���Ϊ�˶๫˾�ģ�����˵�Բ�ѯ����ڶ๫˾��˵�Ǵ����
	 * ����Ҫ���Ĺ���ʱҪ���˵�����ص�vo��Ȼ��ƴ���϶๫˾��m_cCtrTmpDataPowerVOs
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
																				// �ദ�õ�������Ϊ����
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

		HashMap<String, QueryConditionVO> hmPoweredConditionVO = new HashMap<String, QueryConditionVO>();// ������Ȩ�޵�����VO��WNJ2005-04-27

		String strDef = null; // ģ���е���Ŀ��ʶ���ֶ� or����+�ֶΣ���

		// �õ�����ģ���л������ݲ������Ͳ�ʹ������Ȩ�޵���Ŀ
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
						.out("@@@@û��QueryConditionVO::" + fieldcode);
				continue;
			}
			String sRefNodeName = voQuery.getConsultCode();
      String insql = mapSQL.get(sRefNodeName);
			appendPowerCons(vecVO, voQuery, insql);
		}
		// ��֯��Ȩ�޵Ĳ�ѯ����VO������
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
      // �޸��ˣ������� �޸�ʱ�䣺2009-10-21 ����03:46:58 �޸�ԭ�򣺶���δ֪SQL��in�Ӿ䣬һ���ü���()
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

		// ������ǡ������ġ�����Ȩ�޴�������Դ�������By Ydy��Czp��2007-04-24
		if (insql.indexOf(VariableConst.PREFIX_OF_DATAPOWER_SQL) < 0) {
			return;
		}

		String fieldcode = voQuery.getFieldCode();

		// ydy 2005-06-30 ���� (** is null or ** in ())
		// if(!(sRefNodeName.startsWith("���"))){
		// ͨ�����������Ƿ�Ҫ��is null����������
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
			// �޸��ˣ������� �޸�ʱ�䣺2009-10-21 ����03:46:58 �޸�ԭ�򣺶���δ֪SQL��in�Ӿ䣬һ���ü���()
					.append(" in (").append(insql).append(")) ");

			vo.setValue(subSql.toString());
		}

		if (voQuery.getAddIsNull4Power().booleanValue()) {
			vo.setNoRight(false);
			vo.setLogic(false);
		}

		vecVO.addElement(vo);
		// } catch (Exception e) {
		// // nc.vo.scm.pub.SCMEnv.out("��ȡ" + info[0] + "��Ӧ��" + info[1] +
		// // "������Ȩ�޳������ܲ������⡣");
		// nc.vo.scm.pub.SCMEnv.out(e);
		// }

	}

	private String getFldCodeByPower(QueryConditionVO vo) {
		String strDef = vo.getFieldCode();
		// Ϊ��֪��Ȩ�޲��յķ������ͣ�PK,code,name
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
					.getRefTableName(sRefNodeName); // ���ݲ������ƻ�û������ݱ���
		} catch (Exception e) {

		}

		if (dpTableName == null)
			return null;

		// ArrayList<String> alnewCorp=new ArrayList<String>();
		// if (corps != null ) {
		// // ����ò���û������Ȩ�ޣ�������
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
				&& (isDataPower() || vo.getIfDataPower().booleanValue()) // ������ȫ����������Ȩ�޻��ߴ�������
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
	/* ���棺�˷������������ɡ� */
	private BillRefModeSelPanel getBillRefModeSelPanel() {
		if (m_billRefModeSelPanel == null) {
			try {
				m_billRefModeSelPanel = new BillRefModeSelPanel();
				m_billRefModeSelPanel
						.setName(nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"SCMCOMMON", "UPPSCMCommon-000501")/* @res"���ݲ�����ʾ��ʽ" */);
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
	 * �����ˣ� *
	 * 
	 * ���ܣ���ʾ����ѡ�����
	 * 
	 * ������
	 * 
	 * ���أ�
	 * 
	 * �쳣��
	 * 
	 * ���ڣ�(2004-11-25 16:27:37) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
		else if (m_isShowPrintStatusPanel)// �Ѿ���ʾ���ˣ�����Ҫȥ����ʾ
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
	 * zc_����Ȩ�޹���󱣴��ѯ���������˶�����
	 */
	public void actionPerformed(java.awt.event.ActionEvent e) {
		if (((UIButton) e.getSource()).getName().equals("UIButtonSaveHistory"))
			setSaveHistory(true);
		super.actionPerformed(e);
		if (((UIButton) e.getSource()).getName().equals("UIButtonSaveHistory"))
			setSaveHistory(false);

	}

	/**
	 * �����ˣ�
	 * 
	 * ���ܣ�
	 * 
	 * ������
	 * 
	 * ���أ�
	 * 
	 * �쳣��
	 * 
	 * ���ڣ�(2004-11-25 16:27:37) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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

	/** ���Ӳ�ѯ��� */
	private IQueryTypeEditor queryTypeEditor = null;

	private IQueryTypeEditor getQueryTypeEditor() {
		if (!isQueryTypeEditorUsed()) {
			throw new UnsupportedOperationException(
					"ֻ�����ò�ѯ��ʽ����IQueryType.NEITHERʱ���ܵ��ô˷���");
		}
		if (queryTypeEditor == null) {
			createQueryTypeEditor();
		}
		return queryTypeEditor;
	}

	/**
	 * �������Ӳ�ѯ���
	 * 
	 * @author ��ǿ��
	 * @since 2009-8-28
	 */
	private void createQueryTypeEditor() {
		final String title = nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"scmpub", "UPPscmpub-001207");// "��ѯ��ʽ"
		if (null != queryTypeEditor) {
			getUITabbedPane().remove(getUITabbedPane().indexOfTab(title));
		}
		queryTypeEditor = QueryTypeEditorFactory.create(getQueryType());
		getUITabbedPane().insertTab(title, null,
				new UIScrollPane(queryTypeEditor.getComponent()), title,
				getUITabbedPane().getTabCount());
	}

	/** ���Ӳ�ѯ���� */
	private int queryType = IQueryType.NEITHER;

	/**
	 * ���ò�ѯ��ʽ���������������ֵ <blockquote><table>
	 * <tr>
	 * <td>IQueryType.PAGED_ONLY</td>
	 * <td>ֻ��ʾ��ҳ����</td>
	 * </tr>
	 * <tr>
	 * <td>IQueryType.ASYN_ONLY</td>
	 * <td>ֻ��ʾ�첽��ѯ����</td>
	 * </tr>
	 * <tr>
	 * <td>IQueryType.BOTH</td>
	 * <td>ͬʱ��ʾ��ҳ���ú��첽��ѯ����</td>
	 * </tr>
	 * <tr>
	 * <td>IQueryType.NEITHER</td>
	 * <td>���Ǹ��Ӳ�ѯ</td>
	 * </tr>
	 * </table></blockquote>
	 * 
	 * @param queryType
	 *            ��ѯ��ʽ
	 * @author ��ǿ��
	 * @since 2009-8-24
	 */
	public void setQueryType(int queryType) {
		this.queryType = queryType;
		if (isQueryTypeEditorUsed()) {
			getQueryTypeEditor();
		}
	}

	/**
	 * ��ȡ��ѯ��ʽ������ֵ���������� <blockquote><table>
	 * <tr>
	 * <td>IQueryType.PAGED_ONLY</td>
	 * <td>ֻ��ʾ��ҳ����</td>
	 * </tr>
	 * <tr>
	 * <td>IQueryType.ASYN_ONLY</td>
	 * <td>ֻ��ʾ�첽��ѯ����</td>
	 * </tr>
	 * <tr>
	 * <td>IQueryType.BOTH</td>
	 * <td>ͬʱ��ʾ��ҳ���ú��첽��ѯ����</td>
	 * </tr>
	 * <tr>
	 * <td>IQueryType.NEITHER</td>
	 * <td>���Ǹ��Ӳ�ѯ</td>
	 * </tr>
	 * </table></blockquote>
	 * 
	 * @return ���ز�ѯ��ʽ
	 * @author ��ǿ��
	 * @since 2009-8-24
	 */
	public int getQueryType() {
		return queryType;
	}

	/**
	 * �Ƿ�ʹ�� ��ѯ��ʽ ҳǩ
	 */
	private boolean isQueryTypeEditorUsed() {
		return getQueryType() != IQueryType.NEITHER;
	}

	/**
	 * ���ø��Ӳ�ѯ�пɹ���ҳʹ�õ���Ŀ
	 * <p>
	 * <strong> ����ͬһ����ѯ�Ի��򣬸÷�����
	 * <p>
	 * <code>setFixedPagedSubjects(ISubject[])<code><p>����ͬʱֻ������ֻ��һ��������
	 * </strong>
	 * @param subjects
	 *            ��Ŀ����
	 */
	public void setPagedSubjects(ISubject[] subjects) {
		createQueryTypeEditor();
		getQueryTypeEditor().setPagedSubjects(subjects);
	}

	/**
	 * ���ø��Ӳ�ѯ�й̶��ķ�ҳ��Ŀ���飬ʹ�ô˷������õķ�ҳ��Ŀ�������û��༭
	 * <p>
	 * <strong> ����ͬһ����ѯ�Ի��򣬸÷�����
	 * <p>
	 * <code>setPagedSubjects(ISubject[])<code><p>����ͬʱֻ������ֻ��һ��������
	 * </strong>
	 * 
	 * @param subjects
	 *            ��Ŀ����
	 */
	public void setFixedPagedSubjects(ISubject[] subjects) {
		getQueryTypeEditor().setFixedPagedSubjects(subjects);
	}

	/**
	 * ע�Ḵ�Ӳ�ѯ�в�ѯ���Ĭ������
	 * 
	 * @param generator
	 *            ����������
	 */
	public void registerQueryResultDefaultName(INameGenerator generator) {
		getQueryTypeEditor().registerQueryResultDefaultName(generator);
	}

	/**
	 * ���ظ��Ӳ�ѯ�Ĳ�ѯ��ʽ��Ϣ
	 * <p>
	 * <strong> ���ص���Ϣ��һ�����������ͻ�����Ҫ�����Լ��Ĳ�ѯ��ʽȥ���ʶ�Ӧ����Ϣ����
	 */
	public QueryTypeInfo getQueryTypeInfo() {
		return getQueryTypeEditor().getQueryTypeInfo();
	}

	/**
	 * 
	 * ���ߣ������ ���ܣ���ȡ��ѯģ�崦���� ������ ���أ� ���⣺ ���ڣ�2009-9-30 �޸����ڣ� �޸��ˣ��޸�ԭ��ע�ͱ�־
	 */
	protected SCMQueryConditionHandler getQueryCondHandler() {
		if (queryCondHandler == null)
			queryCondHandler = new SCMQueryConditionHandler(this);
		return queryCondHandler;
	}

	/**
	 * 
	 * ���ߣ������ ���ܣ���Ӳ�ѯ�����ֶ� ������dateFieldCodes - Ҫ��ӵ������ֶ����� fromDates - from date
	 * ���� toDates - to date ���� ���أ� ���⣺ ���ڣ�2009-9-22 �޸����ڣ� �޸��ˣ��޸�ԭ��ע�ͱ�־
	 */
	public void addExtraDates(String[] dateFieldCodes, UFDate[] fromDates,
			UFDate[] toDates) {
		getQueryCondHandler().addExtraDates(dateFieldCodes, fromDates, toDates);
	}

	/**
	 * 
	 * ���ߣ������ ���ܣ���Ӳ�ѯ�����ֶ� ������dateFieldCode - Ҫ��ӵ������ֶ� fromDate - from date
	 * toDate - to date ���أ� ���⣺ ���ڣ�2009-9-22 �޸����ڣ� �޸��ˣ��޸�ԭ��ע�ͱ�־
	 */
	public void addExtraDate(String dateFieldCode, UFDate fromDate,
			UFDate toDate) {
		getQueryCondHandler().addExtraDate(dateFieldCode, fromDate, toDate);
	}

	/**
	 * 
	 * ���ߣ������ ���ܣ� ������dateFieldCode ���أ� ���⣺ ���ڣ�2009-9-23 �޸����ڣ� �޸��ˣ��޸�ԭ��ע�ͱ�־
	 */
	public void addCurToCurDate(String dateFieldCode) {
		getQueryCondHandler().addCurToCurDate(dateFieldCode);
	}

	/**
	 * 
	 * ���ߣ������ ���ܣ� ������dateFieldCode ���أ� ���⣺ ���ڣ�2009-9-23 �޸����ڣ� �޸��ˣ��޸�ԭ��ע�ͱ�־
	 */
	public void addCurToCurDates(String[] dateFieldCode) {
		getQueryCondHandler().addCurToCurDates(dateFieldCode);
	}

	/**
	 * 
	 * ���ߣ������ ���ܣ� ������dateFieldCode ���أ� ���⣺ ���ڣ�2009-9-23 �޸����ڣ� �޸��ˣ��޸�ԭ��ע�ͱ�־
	 */
	public void addCurMthFirstToCurDate(String dateFieldCode) {
		getQueryCondHandler().addCurMthFirstToCurDate(dateFieldCode);
	}

	/**
	 * 
	 * ���ߣ������ ���ܣ� ������dateFieldCode ���أ� ���⣺ ���ڣ�2009-9-23 �޸����ڣ� �޸��ˣ��޸�ԭ��ע�ͱ�־
	 */
	public void addCurMthFirstToCurDates(String[] dateFieldCode) {
		getQueryCondHandler().addCurMthFirstToCurDates(dateFieldCode);
	}

	/**
	 * ���෽����д
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