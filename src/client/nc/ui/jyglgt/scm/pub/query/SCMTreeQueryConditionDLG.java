package nc.ui.jyglgt.scm.pub.query;

import static nc.vo.jcom.lang.StringUtil.isEmpty;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;

import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.querytemplate.IQueryTemplateTotalVOProcessor;
import nc.ui.querytemplate.QueryConditionDLG;
import nc.ui.querytemplate.normalpanel.INormalQueryPanel;
import nc.vo.pub.query.ConditionVO;
import nc.vo.pub.query.QueryTempletTotalVO;
import nc.vo.querytemplate.TemplateInfo;
import nc.vo.scm.pub.query.DataPowerCtl;

public class SCMTreeQueryConditionDLG extends QueryConditionDLG {
	private static final long serialVersionUID = 8780166357664587473L;

	/** �������еĲ�ѯģ��VO������ */
	private Set<IQueryTemplateTotalVOProcessor> totalVOProcessorList = new HashSet<IQueryTemplateTotalVOProcessor>();

	/** Ĭ��ֵ�Ĵ����� */
	private DefaultValueProcessor defaultValueProcessor;

	/** �����ֶα༭������ */
	private RefEditorFactory refEditorFactory;

	/** �Զ����ֶ�sql�������� */
	private CustomFieldSqlFactory customFieldSqlFactory;

	/** ���м�����Զ����ѯ��� */
	private List<IQueryTabPanel> CustomQueryPanels = new ArrayList<IQueryTabPanel>();

	private TableLinkedFilterEdit tableLinkedFilterEdit;
	   
    private  DataPowerCtl powerCtl;

	protected void onBtnOK() {
		// �������ݼ��
		String msg = checkCondition();
		if (getQryCondEditor().getNormalPanel() != null
				&& getQryCondEditor().getNormalPanel() instanceof INormalPanelCheck) {
			try {
				if(!((INormalPanelCheck) getQryCondEditor().getNormalPanel()).normalPanelCheck()){
					
					MessageDialog.showErrorDlg(this, NCLangRes.getInstance().getStrByID("scmpub", "scmpub-000029")/*������������*/, NCLangRes.getInstance().getStrByID("scmpub", "scmpub-000030")/*������������,���顣*/);
					return;
					
				}
				
			} catch (Exception e) {
				msg=e.getMessage();
				
			}

		}

		if (msg != null) {
			int res = MessageDialog.showWarningDlg(this, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("_Template", "UPP_Template-000037")/* ���� */, msg);
			if (res == MessageDialog.ID_CANCEL)// ǿ�йرվ�����ʾ��,��Ϊȡ����ѯ
				// �������ȷ��,��۽�δ��ɱ�����
				this.closeCancel();
		} else {
			beforeCloseOK();
			this.closeOK();
		}
	}

	public SCMTreeQueryConditionDLG(Container parent, INormalQueryPanel normalPnl, TemplateInfo ti) {
		super(parent, normalPnl, ti);
		init();
	}

	public SCMTreeQueryConditionDLG(Container parent, TemplateInfo ti) {
		this(parent, null, ti);
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @param parent
	 * @param sNodeCode
	 * @param isDataPower
	 * @param procVoc
	 * @return
	 */
	public static SCMTreeQueryConditionDLG getInstance(Container parent, String tempId) {
		return getInstance(parent, null, tempId, true);
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @param parent
	 * @param sNodeCode
	 * @param isDataPower
	 * @param procVoc
	 * @return
	 */
	public static SCMTreeQueryConditionDLG getInstance(Container parent, String sNodeCode,
			boolean isDataPower) {
		return getInstance(parent, null, sNodeCode, null, isDataPower, null);
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @param parent
	 * @param normalPnl
	 *            ����������ѯ���
	 * @param sNodeCode
	 *            �ڵ����
	 * @return
	 * @since 2008-11-6
	 */
	public static SCMTreeQueryConditionDLG getInstance(Container parent,
			INormalQueryPanel normalPnl, String sNodeCode) {
		return getInstance(parent, normalPnl, sNodeCode, null, true, null);
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @param parent
	 * @param sNodeCode
	 * @param tempid
	 * @param isDataPower
	 * @param procVoc
	 * @return
	 */
	public static SCMTreeQueryConditionDLG getInstance(Container parent, String sNodeCode,
			String tempid, boolean isDataPower) {
		return getInstance(parent, null, sNodeCode, tempid, isDataPower, null);
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @param parent
	 * @param sNodeCode
	 * @param tempid
	 * @param businessType
	 * @return
	 */
	public static SCMTreeQueryConditionDLG getInstance(Container parent, String sNodeCode,
			String businessType) {
		return getInstance(parent, null, sNodeCode, null, false, businessType);
	}

	/**
	 * ��ȡʵ��
	 * 
	 * @param parent
	 * @param sNodeCode
	 * @param tempid
	 * @param isDataPower
	 * @param procVoc
	 * @return
	 */
	private static SCMTreeQueryConditionDLG getInstance(Container parent,
			INormalQueryPanel normalPnl, String sNodeCode, String tempid, boolean isDataPower,
			String businessType) {
		TemplateInfo tempinfo = new TemplateInfo();
		tempinfo.setPk_Org(ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
		tempinfo.setCurrentCorpPk(ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
		tempinfo.setFunNode(sNodeCode);
		tempinfo.setTemplateId(tempid);
		tempinfo.setUserid(ClientEnvironment.getInstance().getUser().getPrimaryKey());
		tempinfo.setDataPowerCtrl(isDataPower);
		tempinfo.setBusiType(businessType);
		return new SCMTreeQueryConditionDLG(parent, normalPnl, tempinfo);
	}

	private void init() {
		// ע�����ģʽ�Ĵ�����
		super.registerQueryTemplateTotalVOProceeor(new IQueryTemplateTotalVOProcessor() {
			public void processQueryTempletTotalVO(QueryTempletTotalVO totalVO) {
				for (IQueryTemplateTotalVOProcessor processor : totalVOProcessorList) {
					processor.processQueryTempletTotalVO(totalVO);
				}
			}
		});
	}

	/**
	 * @return Ĭ��ֵ������
	 */
	private DefaultValueProcessor getDefaultValueProcessor() {
		if (null == defaultValueProcessor) {
			defaultValueProcessor = new DefaultValueProcessor();
			// ע��Ĭ��ֵ������
			registerQueryTemplateTotalVOProceeor(defaultValueProcessor);
		}
		return defaultValueProcessor;
	}

	/**
	 * @return �������༭�����ù���
	 */
	private RefEditorFactory getRefEditorFactory() {
		if (null == refEditorFactory) {
			refEditorFactory = new RefEditorFactory();
			// ע����ձ༭������
			registerFieldValueEelementEditorFactory(refEditorFactory);
		}
		return refEditorFactory;
	}

	private TableLinkedFilterEdit getTableLinkedFilterEdit() {
		if (null == tableLinkedFilterEdit) {
			tableLinkedFilterEdit = new TableLinkedFilterEdit(getQueryContext());
			getQueryContext().getFilterEditorManager().registerFilterEditorFactory(
					tableLinkedFilterEdit);
		}
		return tableLinkedFilterEdit;
	}

	/**
	 * @return �Զ���sql����
	 * @since 2008-11-5
	 */
	private CustomFieldSqlFactory getCustomFieldSqlFactory() {
		if (null == customFieldSqlFactory) {
			customFieldSqlFactory = new CustomFieldSqlFactory(getQueryContext());
			// ע���Զ����ֶ�sql����
			registerFilterEditorFactory(customFieldSqlFactory);
		}
		return customFieldSqlFactory;
	}

	/**
	 * ע���ѯģ��VO�Ĵ���������д����ķ�����ʹ�ÿ���֧�ֶ���������� �ڴ���ʱѭ�����ø���������
	 * 
	 * @param totalVOProcessor
	 *            ģ��VO������
	 * @see nc.ui.querytemplate.QueryConditionDLG#registerQueryTemplateTotalVOProceeor(nc.ui.querytemplate.IQueryTemplateTotalVOProcessor)
	 */
	public void registerQueryTemplateTotalVOProceeor(IQueryTemplateTotalVOProcessor totalVOProcessor) {
		totalVOProcessorList.add(totalVOProcessor);
	}

	/**
	 * ע���Զ����ֶ�sql�Ĵ�����
	 * 
	 * @param fieldCode
	 *            Ҫ�����Զ���sql���ֶα���
	 * @param sqlCreator
	 *            sql������
	 * @since 2008-11-5
	 */
	public void regsterCustomSqlCreator(String fieldCode, IFilterSqlCreator sqlCreator) {
		getCustomFieldSqlFactory().registCreator(fieldCode, sqlCreator);
	}

	public void addTableLinkedMeta(String queryName, SingleTableLinkedMeta meta) {
		getTableLinkedFilterEdit().addTableLinkedMeta(queryName, meta);
	}

	/**
	 * �����Զ����ѯ���
	 * 
	 * @param panel
	 *            Ҫ����Ĳ�ѯ���
	 */
	public void addCustomQueryPanel(IQueryTabPanel panel) {
		getQryCondEditor().getEditorTabbedPane().addTab(panel.getTitle(), panel.getPanel());
		CustomQueryPanels.add(panel);
	}

	/**
	 * �� index λ�ò����Զ����ѯ���
	 * 
	 * @param panel
	 *            Ҫ����Ĳ�ѯ���
	 * @param index
	 *            Ҫ����˲�ѯ����λ��
	 */
	public void addCustomQueryPanel(IQueryTabPanel panel, int index) {
		getQryCondEditor().getEditorTabbedPane().insertTab(panel.getTitle(), null,
				panel.getPanel(), null, index);
		CustomQueryPanels.add(panel);
	}

	/**
	 * ����ԭ������Ĭ��ֵ�ķ���
	 * 
	 * @param fieldCode
	 * @param pk
	 * @param text
	 */
	public void setDefaultValue(String fieldCode, String pk, String text) {
		setDefaultValue(null, fieldCode, pk, text);
	}

	/**
	 * ����ԭ������Ĭ��ֵ�ķ���
	 * 
	 * @param tableCode
	 * @param fieldCode
	 * @param pk
	 * @param text
	 */
	public void setDefaultValue(String tableCode, String fieldCode, String pk, String text) {
		getDefaultValueProcessor().setDefaultValue(tableCode, fieldCode, pk, text);
	}

	/**
	 * ���á�ȡֵ�����Ĳ���
	 * 
	 * @param fieldCode
	 *            �ֶα���
	 * @param refPanelCreator
	 *            ������崴����
	 */
	public void setValueRef(String fieldCode, IRefPanelCreator<? extends JComponent> refPanelCreator) {
		setValueRef(null, fieldCode, refPanelCreator);
	}

	/**
	 * ���á�ȡֵ�����Ĳ���
	 * 
	 * @param tableCode
	 *            �����
	 * @param fieldCode
	 *            �ֶα���
	 * @param refPanelCreator
	 *            ������崴����
	 */
	public void setValueRef(String tableCode, String fieldCode,
			IRefPanelCreator<? extends JComponent> refPanelCreator) {
		getRefEditorFactory().setValueRef(tableCode, fieldCode, refPanelCreator);
	}

	/**
	 * ���á�ȡֵ�����Ĳ���
	 * 
	 * @param fieldCode
	 *            �ֶα���
	 * @param refNodeName
	 *            ���սڵ����ƣ���"��Ա����"��"����"��
	 */
	public void setValueRef(String fieldCode, String refNodeName) {
		setValueRef(null, fieldCode, refNodeName);
	}

	/**
	 * ���á�ȡֵ�����Ĳ���
	 * 
	 * @param tableCode
	 *            �����
	 * @param fieldCode
	 *            �ֶα���
	 * @param refNodeName
	 *            ���սڵ����ƣ���"��Ա����"��"����"��
	 */
	public void setValueRef(String tableCode, String fieldCode, String refNodeName) {
		getRefEditorFactory().setValueRef(tableCode, fieldCode, refNodeName);
	}

	/**
	 * ���á�ȡֵ�����Ĳ���
	 * 
	 * @param fieldCode
	 *            �ֶα���
	 * @param refModel
	 *            ����ģ��
	 */
	public void setValueRef(String fieldCode, AbstractRefModel refModel) {
		setValueRef(null, fieldCode, refModel);
	}

	/**
	 * ���á�ȡֵ�����Ĳ���
	 * 
	 * @param tableCode
	 *            �����
	 * @param fieldCode
	 *            �ֶα���
	 * @param refModel
	 *            ����ģ��
	 */
	public void setValueRef(String tableCode, String fieldCode, AbstractRefModel refModel) {
		getRefEditorFactory().setValueRef(tableCode, fieldCode, refModel);
	}

	/**
	 * ���ء�����������ҳǩ
	 * 
	 * @see nc.ui.querytemplate.QueryConditionDLG#setVisibleNormalPanel(boolean)
	 */
	public void hideNormal() {
		setVisibleNormalPanel(false);
	}

	/**
	 * �����Զ�����
	 * 
	 * @param pk_corp
	 *            ��˾����
	 * @param cbilltypecode
	 *            ��������
	 * @param hPrefix
	 *            ����ģ���е���ͷ���Զ�����ǰ׺
	 * @param bPrefix
	 *            ����ģ���е�������Զ�����ǰ׺
	 */
	public void updateQueryConditionClientUserDef(String pk_corp, String cbilltypecode,
			String hPrefix, String bPrefix) {
		// �Զ��������
		UserDefConditionProcessor udcp = new UserDefConditionProcessor(pk_corp, cbilltypecode,
				hPrefix, bPrefix);
		// ע��ģ��VO����������ʾ֮ǰ���Զ������
		registerQueryTemplateTotalVOProceeor(udcp);
		// ע���Զ�����ı༭��������������ʾ�����Զ�����
		registerFieldValueEelementEditorFactory(udcp);
	}

	/**
	 * ��ò�ѯ����VO���飬<b>�������߼�����(isCondition ����Ϊ false ������)</b>
	 * 
	 * @return ��ò�ѯ����VO����
	 * @see nc.ui.querytemplate.QueryConditionDLG#getLogicalConditionVOs()
	 */
	public ConditionVO[] getGeneralCondtionVOs() {
		List<ConditionVO> allConds = new ArrayList<ConditionVO>();
		allConds.addAll(Arrays.asList(getQryCondEditor().getGeneralCondtionVOs()));
		for (IQueryTabPanel panel : CustomQueryPanels) {
			allConds.addAll(Arrays.asList(panel.getConditionVOs()));
		}
		return allConds.toArray(new ConditionVO[allConds.size()]);
	}

	/**
	 * �����Զ����ѯҳǩ���������sql
	 * 
	 * @return ���� sql ��ѯ�������
	 */
	public String getWhereSQL() {
		String sql = super.getWhereSQL();
		for (IQueryTabPanel panel : CustomQueryPanels) {
			String pnlSql = panel.getWhereSql();
			if (!isEmpty(pnlSql)) {
				if (!isEmpty(sql)) {
					sql += " and ";
				}
				sql += pnlSql;
			}
		}
		return sql;
	}

	public DataPowerCtl getPowerCtl() {
		return powerCtl;
	}

	public void setPowerCtl(DataPowerCtl powerCtl) {
		this.powerCtl = powerCtl;
	}

}
