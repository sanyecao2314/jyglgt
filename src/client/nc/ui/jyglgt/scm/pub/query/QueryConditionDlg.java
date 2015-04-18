package nc.ui.jyglgt.scm.pub.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

import nc.ui.bd.manage.RefCellRender;
import nc.ui.bd.manage.UIRefCellEditor;
import nc.ui.jyglgt.scm.ic.freeitem.FreeItemRefPane;
import nc.ui.jyglgt.scm.ic.measurerate.InvMeasRate;
import nc.ui.jyglgt.scm.pub.report.GroupPanel;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.scm.inter.ic.ILocatorRefPane;
import nc.ui.scm.inter.ic.ILotQueryRef;
import nc.ui.scm.inter.ic.IStatbRefPane;
import nc.ui.scm.pub.query.DataObject;
import nc.ui.scm.service.LocalCallService;
import nc.vo.pub.CommonConstant;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.query.ConditionVO;
import nc.vo.pub.query.QueryConditionVO;
import nc.vo.pub.query.RefResultVO;
import nc.vo.scm.ic.bill.FreeVO;
import nc.vo.scm.ic.bill.InvVO;
import nc.vo.scm.ic.bill.WhVO;
import nc.vo.scm.service.ServcallVO;

public class QueryConditionDlg extends nc.ui.jyglgt.scm.pub.query.SCMQueryConditionDlg {
	private UIPanel ivjUIPanel = null;

	private javax.swing.JTextField ivjtfUnitCode = null;

	//������ֵ�ı�keyΪ��������ֶΣ�ֵΪ��Ӧ��������VO
	//private Hashtable m_htFreeVO= new Hashtable();
	//�����������Ķ��ձ�keyΪ�������ֶΣ�ֵΪ����ֶ�
	private Hashtable m_htFreeItemInv = new Hashtable();

	//����ı�keyΪ����ֶΣ�ֵΪ�����ô���Ŀյ�������VO�Ĵ��VO
	private Hashtable m_htInvVO = new Hashtable();

	//�Զ���ֵ�ı�keyΪ�ؼ��ֶΣ�ֵΪ��Ӧ���ֶε������ֶε�һ������
	private Hashtable m_htAutoClear = new Hashtable();

	//�ֿ�ı�keyΪ�ֿ��ֶΣ�ֵΪ�ֿ�Ķ�Ӧ�Ĳֿ�VO
	private Hashtable m_htWhVO = new Hashtable();

	//��ѡ���г�ʼ��������
	private int m_iFirstSelectRow = -1;

	private int m_iFirstSelectCol = -1;

	//�Ը������Ŀ��Ƕ����
	private InvMeasRate m_voInvMeas = new nc.ui.jyglgt.scm.ic.measurerate.InvMeasRate();

	//��������Ӧ��
	private Hashtable m_htAstCorpInv = new Hashtable();

	//ϵͳ���ճ�ʼ��where�Ӿ�
	private ArrayList m_alRefInitWhereClause = new ArrayList();

	//��ͳ�ƽṹ�Ŀ��Ƕ����
	private Hashtable m_htStatBtoH = new Hashtable();

	private Hashtable m_htStatHtoCorp = new Hashtable();

	//�����κŵĿ��Ƕ����
	private Hashtable m_htLotInv = new Hashtable();

	//���������޸ģ�����ĳһ�ֶεĲ���
	private ArrayList m_alComboxToRef = new ArrayList();

	//�йع�˾���յĳ�ʼ��
	private ArrayList m_alCorpRef = new ArrayList();

	//�йػ�λ���յ�ָ����¼
	private ArrayList m_alLocatorRef = new ArrayList();

	//private String m_sCorpID= null; //��˾ID
	//private String m_sUserID= null; //��ǰʹ����ID
	//private String m_sLogDate= null; //��ǰ��¼����
	//private String m_sUserName= null; //��ǰʹ��������

	//Ĭ�ϵĹ�˾
	private String m_sDefaultCorpID = "";

	private String m_sCorpFieldCode = "";

	//�����빫˾�Ĺ�ϵ
	private HashMap m_htCorpRef = new HashMap();

	//��ʼ�������Ʋ�����
	private Hashtable m_htInitMultiRef = new Hashtable();

	/**
	 * QueryConditionDlg ������ע�⡣
	 */
	public QueryConditionDlg() {
		super();
		init();
	}

	/**
	 * QueryConditionDlg ������ע�⡣
	 * 
	 * @param parent
	 *            java.awt.Container
	 */
	public QueryConditionDlg(java.awt.Container parent) {
		super(parent);
		init();
	}

	/**
	 * QueryConditionDlg ������ע�⡣
	 * 
	 * @param parent
	 *            java.awt.Container
	 * @param title
	 *            java.lang.String
	 */
	public QueryConditionDlg(java.awt.Container parent, String title) {
		super(parent, title);
		init();
	}

	/**
	 * QueryConditionDlg ������ע�⡣
	 * 
	 * @param parent
	 *            java.awt.Frame
	 */
	public QueryConditionDlg(java.awt.Frame parent) {
		super(parent);
		init();
	}

	/**
	 * QueryConditionDlg ������ע�⡣
	 * 
	 * @param parent
	 *            java.awt.Frame
	 * @param title
	 *            java.lang.String
	 */
	public QueryConditionDlg(java.awt.Frame parent, String title) {
		super(parent, title);
		init();
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
		//��ʱ����
		//if (true) {
		//super.afterEdit(editor, row, col);
		//}
		//�Ժ�ɾ��
		//m_immobilityRows

		int index1 = getIndexes(row - getImmobilityRows());
		int index = row - getImmobilityRows();
		//row=row-getImmobilityRows();
		//2002-10-27.01 wnj add below check;
		if (index1 < 0 || index1 >= getConditionDatas().length) {
			nc.vo.scm.pub.SCMEnv.out("qry cond err.");
			return;
		}

		QueryConditionVO qcvo = getConditionDatas()[index1];
		if ((col == COLVALUE) && (editor instanceof UIFreeItemCellEditor)) {
			//д��������VO��ֵ
			Object temp = ((UIFreeItemCellEditor) editor).getComponent();
			if (temp instanceof FreeItemRefPane) {
				FreeItemRefPane pane = (FreeItemRefPane) temp;
				FreeVO fvo = pane.getFreeVO();
				//String sFieldCode=
				//getConditionDatas()[index].getFieldCode();
				//m_htFreeVO.put(sFieldCode, fvo);
				//�ڽ��VO��д��PK,Code,Name��Ϊvfree0
				RefResultVO[] rrvo = getValueRefResults();
				if (rrvo[index] == null)
					rrvo[index] = new RefResultVO();
				rrvo[index].setRefCode(fvo.getVfree0().trim());
				rrvo[index].setRefName(fvo.getVfree0().trim());
				rrvo[index].setRefPK(fvo.getVfree0().trim());
				rrvo[index].setRefObj(fvo);
				setValueRefResults(rrvo);
				//������VO��д���Ӧ���ֵ
				//QueryConditionVO[] qcvo= getConditionDatas();
				//qcvo[index].setValue(fvo.getVfree0().trim());
				//setConditionDatas(qcvo);
				//�������
				getUITabInput().setValueAt(fvo.getVfree0().trim(), row, col);
				//���뱣���InvVO
				String sFreeItemCode = qcvo.getFieldCode().trim();
				if (m_htFreeItemInv.containsKey(sFreeItemCode)) {
					String sInvIDCode = m_htFreeItemInv.get(sFreeItemCode)
							.toString().trim();
					String sInvID = "";
					sInvID = getPKbyFieldCode(sInvIDCode);
					if (m_htInvVO.containsKey(sInvID)) {
						InvVO ivo = (InvVO) m_htInvVO.get(sInvID);
						ivo.setFreeItemVO(fvo);
						m_htInvVO.put(sInvID, ivo);
					}
				}
			}

		} else if ((col == COLVALUE)
				&& (editor instanceof UIComboBoxCellEditor)) {
			//��������

			Object temp = ((UIComboBoxCellEditor) editor).getComponent();
			if (temp instanceof UIComboBox) {
				UIComboBox pane = (UIComboBox) temp;
				DataObject doValue = (DataObject) pane.getItemAt(pane
						.getSelectedIndex());
				//�ڽ��VO��д��PK,Code,Name��Ϊvfree0
				RefResultVO[] rrvo = getValueRefResults();
				if (rrvo[index] == null)
					rrvo[index] = new RefResultVO();
				rrvo[index].setRefCode(doValue.getID().toString().trim());
				rrvo[index].setRefName(doValue.getName().toString().trim());
				rrvo[index].setRefPK(doValue.getID().toString().trim());
				//rrvo[index].setRefObj(doValue);
				setValueRefResults(rrvo);
				//�������
				getUITabInput().setValueAt(doValue.getName().toString().trim(),
						row, col);
			}

		} else if ((col == COLVALUE) && (editor instanceof UIRefCellEditor)) {
			super.afterEdit(editor, row, col);
			Object temp = ((UIRefCellEditor) editor).getComponent();
			if (temp instanceof UIRefPane) {
				UIRefPane pane = (UIRefPane) temp;
				String sRefNodeName = pane.getRefNodeName().trim();

				//if(m_htCorpRef.containsKey(qcvo.getFieldCode())){
				//String scorp=(String)m_htCorpRef.get(qcvo.getFieldCode());
				//pane.setPk_corp((String)m_htCorpValue.get(scorp));

				//}

				if (sRefNodeName.equals("��˾Ŀ¼")) {
					if (qcvo.getFieldCode() != null) {
						if (m_htCorpRef.containsKey(qcvo.getFieldCode())) {
							String[] sRefs = (String[]) m_htCorpRef.get(qcvo
									.getFieldCode());
							for (int kk = 0; kk < sRefs.length; kk++) {
								Object oRef = getValueRefObjectByFieldCode(sRefs[kk]);
								if (oRef != null && oRef instanceof UIRefPane) {
									//nc.vo.scm.pub.SCMEnv.out("afterEdit"+qcvo.getFieldCode()+"-"+sRefs[kk]+":"+((UIRefPane)oRef).getRefModel()+((UIRefPane)oRef).getRefModel().getPk_corp());
									((UIRefPane) oRef).getRefModel()
											.setPk_corp(pane.getRefPK());
									//nc.vo.scm.pub.SCMEnv.out("afterEdit"+qcvo.getFieldCode()+":"+pane.getRefPK());

									//nc.vo.scm.pub.SCMEnv.out("afterEdit"+qcvo.getFieldCode()+"-"+sRefs[kk]+":"+((UIRefPane)oRef).getRefModel()+((UIRefPane)oRef).getRefModel().getPk_corp());

								}
							}

						}
						//	m_htCorpValue.put(qcvo.getFieldCode(),pane.getRefPK().trim());

					}
				} else if (sRefNodeName.equals("�������")
						|| sRefNodeName.equals("���׼�")) {
					if (pane.getRefPK() != null) {
						String sTempID1 = pane.getRefPK().trim();
						String sTempID2 = null;
						ArrayList alIDs = new ArrayList();
						alIDs.add(sTempID2);
						alIDs.add(sTempID1);
						alIDs.add(getCurUserID());
						alIDs.add(null);
						try {
							//��Ϊ����޸�ʱ������InvVO,�Ա��������������
							ServcallVO scd = new ServcallVO();
							scd.setBeanName("nc.bs.ic.ic221.SpecialHImpl");
							scd.setMethodName("queryInfo");
							scd.setParameter(new Object[] { new Integer(0),
									alIDs });
							scd.setParameterTypes(new Class[] { Integer.class,
									ArrayList.class });
							nc.vo.scm.ic.bill.InvVO voInv = (InvVO) (LocalCallService
									.callService("ic",new ServcallVO[] { scd })[0]);

							m_htInvVO.put(sTempID1, voInv);
						} catch (Exception e) {
						}
					}
				} else if (sRefNodeName.equals("����ڼ�")) {
					if (pane.getRefPK() != null) {
						//�ڽ��VO��д��PK,Code,Name
						RefResultVO[] rrvo = getValueRefResults();
						if (rrvo[index] == null)
							rrvo[index] = new RefResultVO();
						rrvo[index].setRefCode(pane.getRefCode().trim());
						rrvo[index].setRefName(pane.getRefName().trim());
						//pane.getRefName().trim() + "��-" +
						// pane.getRefCode().trim() + "��");
						rrvo[index].setRefPK(pane.getRefPK().trim());
						rrvo[index].setRefObj(pane
								.getRefValue("bd_accperiodmonth.begindate"));
						setValueRefResults(rrvo);
						//�������
						getUITabInput().setValueAt(pane.getRefName().trim(),
						//pane.getRefName().trim() + "��-" +
								// pane.getRefCode().trim() + "��",
								row, col);
					}
				} else if (sRefNodeName.equals("�ֿ⵵��")) {
					if (pane.getRefPK() != null) {
						String sTempID1 = pane.getRefPK().trim();
						String sTempID2 = null;
						ArrayList alIDs = new ArrayList();
						alIDs.add(sTempID2);
						alIDs.add(sTempID1);
						alIDs.add(getCurUserID());
						alIDs.add(null);
						try {
							//��Ϊ�ֿ��޸�ʱ������WhVO,�Ա�������λ����
							ServcallVO scd = new ServcallVO();
							scd.setBeanName("nc.bs.ic.ic221.SpecialHImpl");
							scd.setMethodName("queryInfo");
							scd.setParameter(new Object[] { new Integer(1/** QryInfoConst.WH */
							), sTempID1 });
							scd.setParameterTypes(new Class[] { Integer.class,
									String.class });
							WhVO voWh = (WhVO) (LocalCallService
									.callService("ic",new ServcallVO[] { scd })[0]);

							/**
							 * WhVO voWh = (WhVO)
							 * nc.ui.ic.ic221.SpecialHBO_Client.queryInfo( new
							 * Integer(QryInfoConst.WH), sTempID1);
							 */
							m_htWhVO.put(sTempID1, voWh);
						} catch (Exception e) {
						}
					}
				}
			}
			if (qcvo.getDataType().intValue() == 300) {
				//��ͳ�ƽṹ
			} else if (qcvo.getDataType().intValue() == 350) {
				//��ͳ�Ʒ�Χ

				IStatbRefPane pane = (IStatbRefPane) temp;

				//�ڽ��VO��д��PK,Code,Name��Ϊvfree0
				RefResultVO[] rrvo = getValueRefResults();
				if (rrvo[index] == null)
					rrvo[index] = new RefResultVO();
				rrvo[index].setRefCode(pane.getStatcode().trim());
				rrvo[index].setRefName(pane.getStatcode().trim());
				rrvo[index].setRefPK(pane.getStatbid().trim());
				//rrvo[index].setRefObj(null);
				setValueRefResults(rrvo);
				//�������
				getUITabInput().setValueAt(
						pane.getStatcode().toString().trim(), row, col);
			} else if (qcvo.getDataType().intValue() == 400) {
				//�����κ�
				UIRefPane pane = (UIRefPane) temp;
				//LotQueryRef pane = (LotQueryRef) temp;
				//�ڽ��VO��д��PK,Code,Name��Ϊvfree0
				RefResultVO[] rrvo = getValueRefResults();
				if (rrvo[index] == null)
					rrvo[index] = new RefResultVO();
				rrvo[index].setRefCode(pane.getText().trim());
				rrvo[index].setRefName(pane.getText().trim());
				rrvo[index].setRefPK(pane.getText().trim());
				//rrvo[index].setRefObj(null);
				setValueRefResults(rrvo);
				//�������
				getUITabInput().setValueAt(pane.getText().trim(), row, col);
			}
		} else {
			super.afterEdit(editor, row, col);
		}
		//���Ӧֵ
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
			String[] sOtherFieldCodes = (String[]) m_htAutoClear.get(sKey);
			RefResultVO[] rrvo = getValueRefResults();
			QueryConditionVO[] qcvos = getConditionDatas();
			//��ֵ
			for (int i = 0; i < sOtherFieldCodes.length; i++) {
				String sFieldCodeClear = sOtherFieldCodes[i].trim();
				for (int j = 0; j < getUITabInput().getRowCount(); j++) {
					int index = getIndexes(j - getImmobilityRows());
					//2002-10-27.01 wnj add below check.
					if (index < 0 || index >= qcvos.length) {
						nc.vo.scm.pub.SCMEnv.out("qry cond err.");
						continue;
					}
					if (qcvos[index].getFieldCode().trim().equals(
							sFieldCodeClear)) {
						//�鵽��Ӧ�Ľ��VO�����PK��Code, Name
						if (rrvo[j] != null) {
							rrvo[j].setRefCode("");
							rrvo[j].setRefName("");
							rrvo[j].setRefPK("");
						}
						//�������ʾֵ
						getUITabInput().setValueAt(null, j, col);
						break;
					}
				}
			}
		}
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

		//java.util.ArrayList alCon = getConVOGroup(voConditions);

		//for (int j = 0; j < alCon.size(); j++) {
		//ConditionVO[] voCons = (ConditionVO[]) alCon.get(j);

		java.util.Hashtable htKey = new java.util.Hashtable();
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

		//		if (bmust) {
		if (htKey.size() != 0 && htKey.size() != strKeys.length)
			throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
					.getInstance().getStrByID("scmpub", "UPPscmpub-000163")/*
																		    * @res
																		    * "�������ȫΪ�ջ�ȫ��Ϊ�գ�"
																		    */
					+ CommonConstant.BEGIN_MARK
					+ sMsg
					+ CommonConstant.END_MARK);
		//		} else {
		//			if (htKey.size() != strKeys.length)
		//				throw new nc.vo.pub.BusinessException("��������" + sMsg);
		//		}

		//	}
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

		java.util.Hashtable htKey = new java.util.Hashtable();
		StringBuffer sMsg = new StringBuffer();
		sMsg.append(CommonConstant.BEGIN_MARK);
		for (int i = 0; i < strKeys.length; i++) {
			if (!htKey.containsKey(strKeys[i])) {
				htKey.put(strKeys[i], strKeys[i]);
				sMsg.append("<" + getNamebyFieldCode(strKeys[i]) + ">");
			}

		}
		sMsg.append(CommonConstant.END_MARK);
		int count = -1;
		int i = 0;
		for (i = 0; i < voCons.length; i++) {

			String sFieldCode = voCons[i].getFieldCode();
			if (htKey.containsKey(sFieldCode)) {
				//	sMsg.append("<"+voCons[i].getFieldName()+">");
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

		//java.util.ArrayList alCon = getConVOGroup(voConditions);

		//for (int j = 0; j < alCon.size(); j++) {

		java.util.Hashtable htKey = new java.util.Hashtable();
		java.util.Hashtable htField = new java.util.Hashtable();

		//		ConditionVO[] voCons=(ConditionVO[])alCon.get(j);

		StringBuffer sMsg = new StringBuffer();

		sMsg.append(CommonConstant.BEGIN_MARK);
		for (int i = 0; i < strKeys.length; i++) {
			if (!htKey.containsKey(strKeys[i])) {
				htKey.put(strKeys[i], strKeys[i]);
				sMsg.append("<" + getNamebyFieldCode(strKeys[i]) + ">");
			}

		}
		sMsg.append(CommonConstant.END_MARK);

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

		//	}

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
		//checkOncetime(voCons, strKeys);

		int count = 0;
		java.util.Hashtable htKey = new java.util.Hashtable();
		java.util.Hashtable htField = new java.util.Hashtable();
		StringBuffer sMsg = new StringBuffer();
		sMsg.append(CommonConstant.BEGIN_MARK);
		for (int i = 0; i < strKeys.length; i++) {
			if (!htKey.containsKey(strKeys[i])) {
				htKey.put(strKeys[i], strKeys[i]);
				sMsg.append("<" + getNamebyFieldCode(strKeys[i]) + ">");

			}

		}
		sMsg.append(CommonConstant.END_MARK);

		for (int i = 0; i < voCons.length; i++) {
			String sFieldCode = voCons[i].getFieldCode();
			//		if (htKey.containsKey(sFieldCode))
			//			sMsg.append("<" + voCons[i].getFieldName() + ">");
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
						.getInstance().getStrByID("scmpub", "UPPscmpub-000168")/*
																			    * @res
																			    * "������Ӧ������ֻ����һ�"
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

		//	}

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
		java.util.Hashtable htKey = new java.util.Hashtable();
		java.util.Hashtable htField = new java.util.Hashtable();

		StringBuffer sMsg = new StringBuffer();
		sMsg.append(CommonConstant.BEGIN_MARK);
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
		sMsg.append(CommonConstant.END_MARK);

		for (int i = 0; i < voCons.length; i++) {
			String sFieldCode = voCons[i].getFieldCode();

			if (htKey.containsKey(sFieldCode)) {
				Integer iGroup = (Integer) htKey.get(sFieldCode);
				if (!htField.containsKey(iGroup)) {
					String[] strKeys = (String[]) alKeys.get(iGroup.intValue());
					//if (strKeys != null) {
					//sMsg.append("[");
					//for (int j = 0; j < strKeys.length; j++) {
					//sMsg.append(strKeys[j] + ",");

					//}
					//sMsg.append("]");
					//}

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

		//	}

		return;

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
			//throw new nc.vo.pub.BusinessException("û�в�ѯ������");
		}

		String sFieldCode = null;
		String sFieldValue = null;
		String sFlagFieldValue = null; //�߼��ֶ�ֵ
		String sFlagFieldName = null; //�߼��ֶ�����
		String sFieldMustFillInValue = null; //��У���ֶ�
		for (int i = 0; i < voCons.length; i++)
			if (voCons[i] != null) {
				sFieldCode = voCons[i].getFieldCode();
				sFieldValue = voCons[i].getValue();
				//���߼���ѯ�����ֶ�sFlagField.��
				if (sFlagField.equalsIgnoreCase(sFieldCode)) {
					sFlagFieldValue = sFieldValue;
					sFlagFieldName = voCons[i].getFieldName();
				} //�Ǳ�����д���ֶΣ�
				else if (sFieldMustFillIn.equalsIgnoreCase(sFieldCode)) {
					//����ֵ��׼���ж�
					sFieldMustFillInValue = sFieldValue;
				}
			}
		//����Ϊtrue�����ұ���ֵδ��״�
		if ("true".equalsIgnoreCase(sFlagFieldValue)
				&& (sFieldMustFillInValue == null || sFieldMustFillInValue
						.trim().length() == 0))
			//		throw new nc.vo.pub.BusinessException(
			//			"��" + sFlagFieldName + "��ֵΪtrueʱ,\n��" + sFieldMustFillInName +
			// "������Ϊ�գ�");
			throw new nc.vo.pub.BusinessException(
					nc.ui.ml.NCLangRes.getInstance()
							.getStrByID(
									"scmpub",
									"UPPscmpub-001105",
									null,
									new String[] { sFlagFieldName,
											sFieldMustFillInName }));
		/*
		 * @res "����" + sFlagFieldName + "��ֵΪtrueʱ,\n��" + sFieldMustFillInName +
		 * "������Ϊ��
		 */

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

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-11-22 16:35:52) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String
	 */
	public String getCorpFieldCode() {
		return m_sCorpFieldCode;
	}

	/**
	 * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-11-22 16:34:24) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @return java.lang.String
	 */
	public String getDefaultCorpID() {
		return m_sDefaultCorpID;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-8-14 9:52:51)
	 * 
	 * @return nc.vo.pub.query.ConditionVO[]
	 * @param voCons
	 *            nc.vo.pub.query.ConditionVO[]
	 */
	public ConditionVO[] getExpandVOs(ConditionVO[] cvo) {
		//	ConditionVO[] cvo= getConditionVO();
		ConditionVO[] cvoNew = null;
		Vector vCvoNew = new Vector(1, 1);
		//�˵�vfree0���������,ͬʱȡ����Ӧ��������FreeVO
		for (int i = 0; i < cvo.length; i++) {
			//��������
			if (cvo[i].getDataType() == 100) {
				String sFieldCode = cvo[i].getFieldCode().trim();
				String sAfterVfree0 = sFieldCode.substring(sFieldCode
						.indexOf("vfree0") + 6);
				//������ֵ�ı��к��и���
				//if (m_htFreeVO.containsKey(sFieldCode)) {
				//FreeVO fvo= (FreeVO) m_htFreeVO.get(sFieldCode);
				FreeVO fvo = (FreeVO) cvo[i].getRefResult().getRefObj();
				//���ʮ������VO�������µĽ����
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
				//}
				//��������
			} else if (cvo[i].getDataType() == 200) {
				ConditionVO cvoAddVO = cvo[i];
				Object oValue = null;
				if ((cvoAddVO.getRefResult() != null)
						&& (cvoAddVO.getRefResult().getRefObj() != null)) {
					oValue = ((DataObject) cvoAddVO.getRefResult().getRefObj())
							.getID();
					cvoAddVO.setValue(oValue.toString());
				} else {
					//RefResultVO rsvo=cvoAddVO.getRefResult();
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

		cvoNew = new ConditionVO[vCvoNew.size()];
		vCvoNew.copyInto(cvoNew);

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
					sReturnString = getValueRefResults()[iListRow] == null ? ""
							: getValueRefResults()[iListRow].getRefPK().trim();
				}
				break;
			}
		}
		return sReturnString;
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
				//	nc.ui.ic.ic601.NormalQuery ivjUIPanel1 = new
				// nc.ui.ic.ic601.NormalQuery();
				ivjUIPanel = new UIPanel();
				ivjUIPanel.setName("yudaying");
				ivjUIPanel.setLayout(null);
				//	ivjtfUnitCode.setBounds(10, 10, 200,300);
				// user code begin {1}
				nc.vo.scm.pub.SCMEnv.out("call getUIPanel");
				ivjUIPanel.add(new nc.ui.pub.beans.UITextField("dddd"));
				ivjUIPanel.add(gettfUnitCode());
				//			ivjUIPanel=(UIPanel)ivjUIPanel1;
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
		//for (int i = 0; i < getConditionDatas().length; i++) {
		//if (getConditionDatas()[i].getFieldCode().trim().equals(sFieldCode))
		// {
		//int iListRow = getListRow(i);
		//if (iListRow != -1) {
		//sReturnString =
		//getValueRefResults()[iListRow] == null ? "" :
		// getValueRefResults()[iListRow].getRefName().trim();
		//}
		//break;
		//}
		//}
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
		ArrayList al = new ArrayList();
		for (int i = 0; i < getConditionVO().length; i++) {
			if (getConditionVO()[i].getFieldCode().trim().equals(sFieldCode)) {
				al.add(getConditionVO()[i].getValue().trim());
			}
		}

		//for (int i = 0; i < getConditionDatas().length; i++) {
		//if (getConditionDatas()[i].getFieldCode().trim().equals(sFieldCode))
		// {
		//int iListRow = getListRow(i);
		//if (iListRow != -1) {
		//al.add(
		//getValueRefResults()[iListRow] == null ? "" :
		// getValueRefResults()[iListRow].getRefName().trim());
		//}
		//}
		//}
		sReturnString = (String[]) al.toArray(sReturnString);
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
	 * �����ߣ������� ���ܣ���ʼ������ ������ ���أ� ���⣺ ���ڣ�(2001-8-27 15:15:52) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public void initCorpRef(String sCorpFieldCode, String sShowCorpID,
			ArrayList alAllCorpIDs) {
		//����Ϊ�Թ�˾���յĳ�ʼ��
		ArrayList altemp = new ArrayList();
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
		setDefaultValue(sCorpFieldCode.trim(), sShowCorpID, null);
	}

	/**
	 * �����ߣ������� ���ܣ���ʼ������ ������ ���أ� ���⣺ ���ڣ�(2001-8-27 15:15:52) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public void initLocatorRef(String sLocatorFieldCode, String sInvFieldCode,
			String sWhFieldCode) {
		//����Ϊ�Ի�λ���յļ�¼
		ArrayList altemp = new ArrayList();
		altemp.add(sLocatorFieldCode);
		altemp.add(sInvFieldCode);
		altemp.add(sWhFieldCode);
		m_alLocatorRef.add(altemp);
	}

	/**
	 * �����ߣ������� ���ܣ���ʼ������ ������ ���أ� ���⣺ ���ڣ�(2001-8-27 15:15:52) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public void initQueryDlgRef() {
		//����Ϊ�Բ��յĳ�ʼ��
		//һ��ֿ�
		setRefInitWhereClause("cwarehouseid", "�ֿ⵵��",
				"gubflag='N' and sealflag='N' and pk_corp=", "pk_corp");
		//'" + m_sCorpID + "'");
		//���һ��ֿ�
		setRefInitWhereClause("cinwarehouseid", "�ֿ⵵��",
				"gubflag='N' and sealflag='N' and pk_corp=", "pk_corp");
		//����һ��ֿ�
		setRefInitWhereClause("coutwarehouseid", "�ֿ⵵��",
				"gubflag='N' and sealflag='N' and pk_corp=", "pk_corp");
		//��Ʒ�ֿ�
		setRefInitWhereClause("cwastewarehouseid", "�ֿ⵵��",
				"gubflag='Y' and sealflag='N' and pk_corp=", "pk_corp");
		//�ͻ�
		setRefInitWhereClause(
				"ccustomerid",
				"���̵���",
				"(custflag ='0' or custflag ='2') and (bd_cumandoc.frozenflag='N' OR bd_cumandoc.frozenflag='n' OR bd_cumandoc.frozenflag IS NULL) and bd_cumandoc.pk_corp=",
				"pk_corp");
		//'" + m_sCorpID + "'");
		//��Ӧ��
		setRefInitWhereClause(
				"cproviderid",
				"���̵���",
				"(custflag ='1' or custflag ='3') and (bd_cumandoc.frozenflag='N' OR bd_cumandoc.frozenflag='n' OR bd_cumandoc.frozenflag IS NULL) and bd_cumandoc.pk_corp=",
				"pk_corp");
		//��Ӧ��
		setRefInitWhereClause(
				"cvendorid",
				"���̵���",
				"(custflag ='1' or custflag ='3') and (bd_cumandoc.frozenflag='N' OR bd_cumandoc.frozenflag='n'  OR bd_cumandoc.frozenflag IS NULL) and bd_cumandoc.pk_corp=",
				"pk_corp");
		//'" + m_sCorpID + "'");
		//���,��λ������˴����Ϊ�Ƿ����,�����񣬷��ۿ۴��
		setRefInitWhereClause(
				"cinventorycode",
				"�������",
				" bd_invbasdoc.discountflag='N' and bd_invbasdoc.laborflag='N' "
						+ " and bd_invmandoc.sealflag ='N' and bd_invmandoc.pk_corp=",
				"pk_corp");
		//'" + m_sCorpID + "'");
		//���,��λ������˴����Ϊ�Ƿ����,�����񣬷��ۿ۴��
		setRefInitWhereClause(
				"cinventoryid",
				"�������",
				" bd_invbasdoc.discountflag='N' and bd_invbasdoc.laborflag='N' "
						+ " and bd_invmandoc.sealflag ='N' and bd_invmandoc.pk_corp=",
				"pk_corp");
		//----------- �շ���� cdispatcherid --------------
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
			ArrayList alRow = (ArrayList) (m_alComboxToRef.get(i));
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
			ArrayList alRow = new ArrayList();
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

			//�ñ༭��
			UIFreeItemCellEditor editor = new UIFreeItemCellEditor(
					firpFreeItemRefPane);
			setValueRef(sFieldCode, editor);

			//��¼�����������Ķ��ձ�
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

			//LotQueryRef lqrLotQueryRef= new LotQueryRef();

			//�ñ༭��
			UIRefCellEditor editor = new UIRefCellEditor(lqrLotQueryRef);
			setValueRef(sFieldLotCode, editor);

			//��¼��������κŵĶ��ձ�
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
			//2002-10-28.01 wnj add check unique,use last set value of list.
			//
			String sMyFieldCode = null;
			//����Ӧ�ò��ü��null
			for (int i = m_alRefInitWhereClause.size() - 1; i >= 0; i--) {
				sMyFieldCode = ((ArrayList) m_alRefInitWhereClause.get(i)).get(
						0).toString();
				//�������ȥ���еļ�¼�������µġ�
				if (sMyFieldCode.trim().equals(sFieldCode.trim())) {
					//nc.vo.scm.pub.SCMEnv.out("reset cond "+sFieldCode);
					m_alRefInitWhereClause.remove(i);
				}
			}
			ArrayList alrow = new ArrayList();
			alrow.add(sFieldCode.trim());
			alrow.add(sRefName.trim());
			alrow.add(sWhereClause.trim());
			alrow.add(sCheckFieldCode.trim());
			m_alRefInitWhereClause.add(alrow);
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
			ArrayList alrow = new ArrayList();
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

			UIRefPane srpStathRefPane = (UIRefPane) Class.forName(
					"nc.ui.ic.pub.stat.StathRefPane").newInstance();
			//new StathRefPane("");
			//srpStathRefPane.setWhereString("bicusedflag='Y' and pk_corp=''");
			srpStathRefPane.setWhereString("bicusedflag='Y'");
			IStatbRefPane srpStatbRefPane = (IStatbRefPane) Class.forName(
					"nc.ui.ic.pub.stat.StathRefPane").newInstance();
			//new StatbRefPane();

			//�ñ༭��
			UIRefCellEditor editorh = new UIRefCellEditor(
					(UIRefPane) srpStathRefPane);
			setValueRef(sFieldStateHCode, editorh);

			UIRefCellEditor editorb = new UIRefCellEditor(
					(UIRefPane) srpStatbRefPane);
			setValueRef(sFieldStateBCode, editorb);

			//��¼ͳ�ƽṹ��ͳ�Ʒ�Χ�Ķ��ձ�
			m_htStatBtoH.put(sFieldStateBCode.trim(), sFieldStateHCode.trim());
			//��¼ͳ�ƽṹ�빫˾�Ķ��ձ�
			m_htStatHtoCorp.put(sFieldStateHCode.trim(), sFieldCorpcode.trim());

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * �����ߣ������� ���ܣ������ʼ�����������,��ʼ������������ ������ ���أ� ���⣺ ���ڣ�(2001-8-13 15:45:09)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param iRow
	 *            int
	 * @param iCol
	 *            int
	 */
	protected void setValueRefEditor(int iRow, int iCol) {

		int index = getIndexes(iRow - getImmobilityRows());
		//2002-10-27.01 wnj add below check;
		if (index < 0 || index >= getConditionDatas().length) {
			nc.vo.scm.pub.SCMEnv.out("qry cond err.");
			return;
		}
		QueryConditionVO qcvo = getConditionDatas()[index];
		String sFieldCode = qcvo.getFieldCode().trim();

		//�漰��ϵͳ���յĳ�ʼ��
		for (int i = 0; i < m_alRefInitWhereClause.size(); i++) {
			ArrayList alRow = (ArrayList) m_alRefInitWhereClause.get(i);
			if (alRow.get(0).toString().trim().equals(
					qcvo.getFieldCode().trim())) {
				UIRefPane uirp = (UIRefPane) getValueRefObjectByFieldCode(qcvo
						.getFieldCode());//new UIRefPane();
				//nc.vo.scm.pub.SCMEnv.out("setValueEditor
				// "+qcvo.getFieldCode()+":"+uirp.getRefModel()+uirp.getRefModel().getPk_corp());
				//uirp.setRefNodeName(alRow.get(1).toString().trim());
				uirp.getRefModel().setSealedDataShow(true);
				String sPK = "";
				for (int j = 0; j < getUITabInput().getRowCount(); j++) {
					int indexNow = getIndexes(j - getImmobilityRows());
					//2002-10-27.01 wnj add below check.
					if (indexNow < 0 || indexNow >= getConditionDatas().length) {
						//nc.vo.scm.pub.SCMEnv.out("qry cond err.");
						continue;
					}
					QueryConditionVO qcvoNow = getConditionDatas()[indexNow];
					if (qcvoNow.getFieldCode().trim().equals(
							alRow.get(3).toString().trim())) {
						RefResultVO tempValue = getValueRefResults()[j
								- getImmobilityRows()];
						if (tempValue == null || tempValue.getRefPK() == null)
							break;
						else {
							sPK = getValueRefResults()[j - getImmobilityRows()]
									.getRefPK().trim();

						}
						uirp.setWhereString(alRow.get(2).toString().trim()
								+ "'" + sPK + "'");
						//nc.vo.scm.pub.SCMEnv.out("setValueEditor
						// "+qcvo.getFieldCode()+":"+uirp.getRefModel()+uirp.getRefModel().getPk_corp());
						changeValueRef(alRow.get(0).toString().trim(), uirp);
						//nc.vo.scm.pub.SCMEnv.out("setValueEditor
						// "+qcvo.getFieldCode()+":"+uirp.getRefModel()+uirp.getRefModel().getPk_corp());
						break;
					}
				} //2002-10-25 WNJ ADD BELOW "DISTINCT" STATEMENT
				if ("���̵���".equals(alRow.get(1).toString()))
					uirp.setStrPatch(" DISTINCT "); //2002-10-25 WNJ ADD BELOW
				// "DISTINCT" STATEMENT
				if (alRow.get(0).toString().indexOf("costobject") >= 0) {
					nc.ui.bd.ref.AbstractRefModel refmodel = (nc.ui.bd.ref.AbstractRefModel) uirp
							.getRef().getRefModel();
					String sTableName = refmodel.getTableName();
					sTableName = sTableName + ",bd_produce";
					refmodel.setTableName(sTableName);
					uirp.setStrPatch(" DISTINCT ");
				}
				if (sPK.equals("")
						&& alRow.get(3).toString().trim().equals("pk_corp")) {
					uirp.setWhereString(alRow.get(2).toString().trim() + "'"
							+ m_sDefaultCorpID + "'");
					changeValueRef(alRow.get(0).toString().trim(), uirp);
				}
				break;
			}
		} //�Թ�˾���յĳ�ʼ��
		for (int i = 0; i < m_alCorpRef.size(); i++) {
			ArrayList alRow = (ArrayList) m_alCorpRef.get(i);
			if (alRow.get(0).toString().trim().equals(
					qcvo.getFieldCode().trim())) {
				UIRefPane uirp = new UIRefPane();
				uirp.setRefNodeName("��˾Ŀ¼");
				uirp.getRefModel().setSealedDataShow(true);
				String sPK = alRow.get(1).toString().trim();
				uirp.setPK(sPK);
				String sPKs = "'" + sPK + "' ";
				ArrayList alPKs = (ArrayList) alRow.get(2);
				for (int j = 0; j < alPKs.size(); j++) {
					sPKs = sPKs + ",'" + (String) alPKs.get(j) + "'";
				}
				uirp.getRefModel().addWherePart(
						" and pk_corp in (" + sPKs + ")");
				changeValueRef(alRow.get(0).toString().trim(), uirp);
				if (qcvo.getReturnType().intValue() == 1) {
					setDefaultValue(alRow.get(0).toString().trim(), sPK, uirp
							.getRefName());
				} else {
					setDefaultValue(alRow.get(0).toString().trim(), sPK, uirp
							.getRefCode());
				}
				break;
			}
		} //�Ի�λ���յĳ�ʼ��
		for (int i = 0; i < m_alLocatorRef.size(); i++) {
			ArrayList alRow = (ArrayList) m_alLocatorRef.get(i);
			if (alRow.get(0).toString().trim().equals(
					qcvo.getFieldCode().trim())) {
				String sNowFieldCode = alRow.get(0).toString().trim();
				String sInvFieldCode = alRow.get(1).toString().trim();
				String sWhFieldCode = alRow.get(2).toString().trim();
				String sInvID = getPKbyFieldCode(sInvFieldCode);
				InvVO invvo = null;
				if (m_htInvVO.containsKey(sInvID)) {
					invvo = (InvVO) m_htInvVO.get(sInvID);
				}
				String sWhID = getPKbyFieldCode(sWhFieldCode);
				WhVO voWh = null;
				if (m_htWhVO.containsKey(sWhID)) {
					voWh = (WhVO) m_htWhVO.get(sWhID);
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
					changeValueRef(alRow.get(0).toString().trim(), null);
					break;
				}

				//LocatorRefPane uirp = new LocatorRefPane();
				uirp.setInOutFlag(nc.vo.scm.constant.ic.InOutFlag.OUT);
				uirp.setOldValue(sName, null, spk);
				uirp.setParam(voWh, invvo);
				changeValueRef(alRow.get(0).toString().trim(), uirp);

				break;
			}
		} //�Զ����Ʋ��յĳ�ʼ��
		if (m_htInitMultiRef.containsKey(sFieldCode)) {
			Object oRef = getValueRefObjectByFieldCode(sFieldCode);
			if (oRef instanceof UIRefCellEditor)
				oRef = ((UIRefCellEditor) oRef).getComponent();
			if (oRef != null && oRef instanceof UIRefPane) {
				UIRefPane uiRef = (UIRefPane) oRef;
				ArrayList alObj = (ArrayList) m_htInitMultiRef.get(sFieldCode);
				String sBeforeWhereClause = (String) alObj.get(0);
				String sAfterWhereClause = (String) alObj.get(1);
				String[][] sKeys = (String[][]) alObj.get(2);
				boolean bIsNullUsed = ((UFBoolean) alObj.get(3)).booleanValue();
				StringBuffer sb = new StringBuffer();
				//sb.append(uiRef.getRefModel().getOriginWherePart());
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

		super.setValueRefEditor(iRow, iCol); //��������
		if (qcvo.getDataType().intValue() == 100) {
			FreeItemRefPane firpFreeItemRefPane = new FreeItemRefPane();
			String sFreeItemCode = qcvo.getFieldCode().trim();
			if (m_htFreeItemInv.containsKey(sFreeItemCode)) {
				String sInvIDCode = m_htFreeItemInv.get(sFreeItemCode)
						.toString().trim();
				String sInvID = "";
				sInvID = getPKbyFieldCode(sInvIDCode);
				if (m_htInvVO.containsKey(sInvID)) {
					InvVO ivo = (InvVO) m_htInvVO.get(sInvID);
					firpFreeItemRefPane.setFreeItemParam(ivo);
				}
			} //�ñ༭��
			UIFreeItemCellEditor editor = new UIFreeItemCellEditor(
					firpFreeItemRefPane);
			getUITabInput().getColumnModel().getColumn(iCol).setCellEditor(
					editor);
		} else if (qcvo.getDataType().intValue() == 200) { //��������
			//do nothing

		} else if (qcvo.getDataType().intValue() == 300) { //��ͳ�ƽṹ

			UIRefPane srpStathRefPane = null;
			try {
				srpStathRefPane = (UIRefPane) Class.forName(
						"nc.ui.ic.pub.stat.StathRefPane").newInstance();
			} catch (Exception e) {
				nc.vo.scm.pub.SCMEnv.out(e);
			}
			//new StathRefPane("");
			String sStatH = qcvo.getFieldCode().trim();
			if (m_htStatHtoCorp.containsKey(sStatH)) {
				String sCorpField = m_htStatHtoCorp.get(sStatH).toString()
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
			} //�ñ༭��
			UIRefCellEditor editorh = new UIRefCellEditor(
					(UIRefPane) srpStathRefPane);
			getUITabInput().getColumnModel().getColumn(iCol).setCellEditor(
					editorh);
		} else if (qcvo.getDataType().intValue() == 350) { //��ͳ�Ʒ�Χ

			IStatbRefPane srpStatbRefPane = null;
			try {

				srpStatbRefPane = (IStatbRefPane) Class.forName(
						"nc.ui.ic.pub.stat.StatbRefPane").newInstance();
			} catch (Exception e) {

				nc.vo.scm.pub.SCMEnv.out(e);
			}
			//new StatbRefPane();
			String sStatB = qcvo.getFieldCode().trim();
			if (m_htStatBtoH.containsKey(sStatB)) {
				String sStatHCode = m_htStatBtoH.get(sStatB).toString().trim();
				String sStatH = "";
				sStatH = getPKbyFieldCode(sStatHCode);
				srpStatbRefPane.setStathid(sStatH);
			} //�ñ༭��
			UIRefCellEditor editorb = new UIRefCellEditor(
					(UIRefPane) srpStatbRefPane);
			getUITabInput().getColumnModel().getColumn(iCol).setCellEditor(
					editorb);
		} else if (qcvo.getDataType().intValue() == 400) { //�����κ�

			UIRefPane lqrLotQueryRef = null;
			try {
				lqrLotQueryRef = (UIRefPane) Class.forName(
						"nc.ui.ic.pub.lotquery.LotQueryRef").newInstance();
			} catch (Exception e) {
				nc.vo.scm.pub.SCMEnv.out(e);
			}

			//LotQueryRef lqrLotQueryRef = new LotQueryRef();
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
			} //�ñ༭��
			UIRefCellEditor editor = new UIRefCellEditor(lqrLotQueryRef);
			getUITabInput().getColumnModel().getColumn(iCol).setCellEditor(
					editor);
			getUITabInput().setValueAt(sLotValue, iRow, iCol);
		} else if (qcvo.getDataType().intValue() == 5) { //���Ǵ�������
			if (m_htAstCorpInv.containsKey(sFieldCode)) {
				//�Ǹ���������
				String[] sCorpInv = (String[]) m_htAstCorpInv.get(sFieldCode);
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
		} //�ɱ䶯�������ñ����ò���
		boolean bFounded = false;
		boolean bActualize = false;
		Object Ref = null;
		for (int i = 0; i < m_alComboxToRef.size(); i++) {
			ArrayList alRow = (ArrayList) (m_alComboxToRef.get(i));
			if (alRow.get(1).toString().trim().equals(
					qcvo.getFieldCode().trim())) {
				String sSourceFieldCode = alRow.get(0).toString().trim();
				for (int j = 0; j < getConditionDatas().length; j++) {
					if (getConditionDatas()[j].getFieldCode().trim().equals(
							sSourceFieldCode)) {
						bFounded = true;
						Object oValue = getTabModelInput().getValueAt(
								getListRow(j), 4);
						Object[][] oRef = (Object[][]) alRow.get(2);
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

	//�������
	private GroupPanel m_groupPanel = null;

	/**
	 * @author ����� �������ڣ�(2003-8-6 11:41:56)
	 * @version ����޸�����
	 * @see ��Ҫ�μ���������
	 * @since �Ӳ�Ʒ����һ���汾�����౻��ӽ���������ѡ��
	 * 
	 * @return nc.ui.bizscm.query.GroupPanel
	 */
	public GroupPanel getGroupPanel() {
		//if (m_groupPanel != null) return m_groupPanel;

		//m_groupPanel = new GroupPanel(m_groupItemKey,m_groupItemName);
		//getUIPanelNormal().setLayout(new java.awt.BorderLayout());
		//getUIPanelNormal().add(m_groupPanel, "Center");
		return m_groupPanel;
	}

	public void hideGroupPanel() {
		getUIPanelNormal().remove(m_groupPanel);

	}

	/**
	 * @author ����� �������ڣ�(2003-8-14 11:32:01)
	 * @version ����޸�����
	 * @see ��Ҫ�μ���������
	 * @since �Ӳ�Ʒ����һ���汾�����౻��ӽ���������ѡ��
	 *  
	 */
	private void init() {

	}

	/**
	 * @author ����� �������ڣ�(2003-8-6 9:36:18)
	 * @version ����޸�����
	 * @see ��Ҫ�μ���������
	 * @since �Ӳ�Ʒ����һ���汾�����౻��ӽ���������ѡ��
	 * 
	 * @param key
	 *            java.lang.String[]
	 * @param name
	 *            java.lang.String[]
	 */
	public void showGroupPanel() {
		if (m_groupPanel == null) {
			ConditionVO[] cvo = this.getGroupVO();
			int len = cvo.length;
			//������ݿ����Ƿ��з�����Ϣ
			if (len == 0) {
				this.hideNormal();
				nc.vo.scm.pub.SCMEnv.out("���ݿ��ѯģ����û�з�����Ϣ");
				return;
			}
			String key[] = new String[len];
			String name[] = new String[len];
			for (int i = 0; i < len; i++) {
				key[i] = cvo[i].getFieldCode();
				name[i] = cvo[i].getFieldName();
			}
			m_groupPanel = new GroupPanel(key, name);
			getUIPanelNormal().setLayout(new java.awt.BorderLayout());
			getUIPanelNormal().add(m_groupPanel, "Center");
		}
	}

	/**
	 * @author ����� �������ڣ�(2003-8-6 9:36:18)
	 * @version ����޸�����
	 * @see ��Ҫ�μ���������
	 * @since �Ӳ�Ʒ����һ���汾�����౻��ӽ���������ѡ��
	 * 
	 * @param key
	 *            java.lang.String[]
	 * @param name
	 *            java.lang.String[]
	 */
	public void showGroupPanel(String[] key, String[] name) {
		if (m_groupPanel == null) {
			m_groupPanel = new GroupPanel(key, name);
			getUIPanelNormal().setLayout(new java.awt.BorderLayout());
			getUIPanelNormal().add(m_groupPanel, "Center");
		}
	}
}