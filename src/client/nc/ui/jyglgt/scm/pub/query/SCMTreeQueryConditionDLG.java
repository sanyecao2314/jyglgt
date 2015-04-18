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

	/** 保存所有的查询模板VO处理器 */
	private Set<IQueryTemplateTotalVOProcessor> totalVOProcessorList = new HashSet<IQueryTemplateTotalVOProcessor>();

	/** 默认值的处理器 */
	private DefaultValueProcessor defaultValueProcessor;

	/** 参照字段编辑器工厂 */
	private RefEditorFactory refEditorFactory;

	/** 自定义字段sql创建工厂 */
	private CustomFieldSqlFactory customFieldSqlFactory;

	/** 所有加入的自定义查询面板 */
	private List<IQueryTabPanel> CustomQueryPanels = new ArrayList<IQueryTabPanel>();

	private TableLinkedFilterEdit tableLinkedFilterEdit;
	   
    private  DataPowerCtl powerCtl;

	protected void onBtnOK() {
		// 进行数据检查
		String msg = checkCondition();
		if (getQryCondEditor().getNormalPanel() != null
				&& getQryCondEditor().getNormalPanel() instanceof INormalPanelCheck) {
			try {
				if(!((INormalPanelCheck) getQryCondEditor().getNormalPanel()).normalPanelCheck()){
					
					MessageDialog.showErrorDlg(this, NCLangRes.getInstance().getStrByID("scmpub", "scmpub-000029")/*输入条件错误*/, NCLangRes.getInstance().getStrByID("scmpub", "scmpub-000030")/*输入条件错误,请检查。*/);
					return;
					
				}
				
			} catch (Exception e) {
				msg=e.getMessage();
				
			}

		}

		if (msg != null) {
			int res = MessageDialog.showWarningDlg(this, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("_Template", "UPP_Template-000037")/* 警告 */, msg);
			if (res == MessageDialog.ID_CANCEL)// 强行关闭警告提示框,认为取消查询
				// 如果单击确定,则聚焦未完成必输项
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
	 * 获取实例
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
	 * 获取实例
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
	 * 获取实例
	 * 
	 * @param parent
	 * @param normalPnl
	 *            常用条件查询面板
	 * @param sNodeCode
	 *            节点编码
	 * @return
	 * @since 2008-11-6
	 */
	public static SCMTreeQueryConditionDLG getInstance(Container parent,
			INormalQueryPanel normalPnl, String sNodeCode) {
		return getInstance(parent, normalPnl, sNodeCode, null, true, null);
	}

	/**
	 * 获取实例
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
	 * 获取实例
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
	 * 获取实例
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
		// 注册组合模式的处理器
		super.registerQueryTemplateTotalVOProceeor(new IQueryTemplateTotalVOProcessor() {
			public void processQueryTempletTotalVO(QueryTempletTotalVO totalVO) {
				for (IQueryTemplateTotalVOProcessor processor : totalVOProcessorList) {
					processor.processQueryTempletTotalVO(totalVO);
				}
			}
		});
	}

	/**
	 * @return 默认值处理器
	 */
	private DefaultValueProcessor getDefaultValueProcessor() {
		if (null == defaultValueProcessor) {
			defaultValueProcessor = new DefaultValueProcessor();
			// 注册默认值处理器
			registerQueryTemplateTotalVOProceeor(defaultValueProcessor);
		}
		return defaultValueProcessor;
	}

	/**
	 * @return 参照面板编辑器设置工厂
	 */
	private RefEditorFactory getRefEditorFactory() {
		if (null == refEditorFactory) {
			refEditorFactory = new RefEditorFactory();
			// 注册参照编辑器工厂
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
	 * @return 自定义sql工厂
	 * @since 2008-11-5
	 */
	private CustomFieldSqlFactory getCustomFieldSqlFactory() {
		if (null == customFieldSqlFactory) {
			customFieldSqlFactory = new CustomFieldSqlFactory(getQueryContext());
			// 注册自定义字段sql工厂
			registerFilterEditorFactory(customFieldSqlFactory);
		}
		return customFieldSqlFactory;
	}

	/**
	 * 注册查询模板VO的处理器，重写父类的方法，使得可以支持多个处理器， 在处理时循环调用各个处理器
	 * 
	 * @param totalVOProcessor
	 *            模板VO处理器
	 * @see nc.ui.querytemplate.QueryConditionDLG#registerQueryTemplateTotalVOProceeor(nc.ui.querytemplate.IQueryTemplateTotalVOProcessor)
	 */
	public void registerQueryTemplateTotalVOProceeor(IQueryTemplateTotalVOProcessor totalVOProcessor) {
		totalVOProcessorList.add(totalVOProcessor);
	}

	/**
	 * 注册自定义字段sql的创建器
	 * 
	 * @param fieldCode
	 *            要返回自定义sql的字段编码
	 * @param sqlCreator
	 *            sql创建器
	 * @since 2008-11-5
	 */
	public void regsterCustomSqlCreator(String fieldCode, IFilterSqlCreator sqlCreator) {
		getCustomFieldSqlFactory().registCreator(fieldCode, sqlCreator);
	}

	public void addTableLinkedMeta(String queryName, SingleTableLinkedMeta meta) {
		getTableLinkedFilterEdit().addTableLinkedMeta(queryName, meta);
	}

	/**
	 * 加入自定义查询面板
	 * 
	 * @param panel
	 *            要加入的查询面板
	 */
	public void addCustomQueryPanel(IQueryTabPanel panel) {
		getQryCondEditor().getEditorTabbedPane().addTab(panel.getTitle(), panel.getPanel());
		CustomQueryPanels.add(panel);
	}

	/**
	 * 在 index 位置插入自定义查询面板
	 * 
	 * @param panel
	 *            要加入的查询面板
	 * @param index
	 *            要加入此查询面板的位置
	 */
	public void addCustomQueryPanel(IQueryTabPanel panel, int index) {
		getQryCondEditor().getEditorTabbedPane().insertTab(panel.getTitle(), null,
				panel.getPanel(), null, index);
		CustomQueryPanels.add(panel);
	}

	/**
	 * 兼容原来设置默认值的方法
	 * 
	 * @param fieldCode
	 * @param pk
	 * @param text
	 */
	public void setDefaultValue(String fieldCode, String pk, String text) {
		setDefaultValue(null, fieldCode, pk, text);
	}

	/**
	 * 兼容原来设置默认值的方法
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
	 * 设置“取值”处的参照
	 * 
	 * @param fieldCode
	 *            字段编码
	 * @param refPanelCreator
	 *            参照面板创建器
	 */
	public void setValueRef(String fieldCode, IRefPanelCreator<? extends JComponent> refPanelCreator) {
		setValueRef(null, fieldCode, refPanelCreator);
	}

	/**
	 * 设置“取值”处的参照
	 * 
	 * @param tableCode
	 *            表编码
	 * @param fieldCode
	 *            字段编码
	 * @param refPanelCreator
	 *            参照面板创建器
	 */
	public void setValueRef(String tableCode, String fieldCode,
			IRefPanelCreator<? extends JComponent> refPanelCreator) {
		getRefEditorFactory().setValueRef(tableCode, fieldCode, refPanelCreator);
	}

	/**
	 * 设置“取值”处的参照
	 * 
	 * @param fieldCode
	 *            字段编码
	 * @param refNodeName
	 *            参照节点名称，如"人员档案"、"日历"等
	 */
	public void setValueRef(String fieldCode, String refNodeName) {
		setValueRef(null, fieldCode, refNodeName);
	}

	/**
	 * 设置“取值”处的参照
	 * 
	 * @param tableCode
	 *            表编码
	 * @param fieldCode
	 *            字段编码
	 * @param refNodeName
	 *            参照节点名称，如"人员档案"、"日历"等
	 */
	public void setValueRef(String tableCode, String fieldCode, String refNodeName) {
		getRefEditorFactory().setValueRef(tableCode, fieldCode, refNodeName);
	}

	/**
	 * 设置“取值”处的参照
	 * 
	 * @param fieldCode
	 *            字段编码
	 * @param refModel
	 *            参照模型
	 */
	public void setValueRef(String fieldCode, AbstractRefModel refModel) {
		setValueRef(null, fieldCode, refModel);
	}

	/**
	 * 设置“取值”处的参照
	 * 
	 * @param tableCode
	 *            表编码
	 * @param fieldCode
	 *            字段编码
	 * @param refModel
	 *            参照模型
	 */
	public void setValueRef(String tableCode, String fieldCode, AbstractRefModel refModel) {
		getRefEditorFactory().setValueRef(tableCode, fieldCode, refModel);
	}

	/**
	 * 隐藏“常用条件”页签
	 * 
	 * @see nc.ui.querytemplate.QueryConditionDLG#setVisibleNormalPanel(boolean)
	 */
	public void hideNormal() {
		setVisibleNormalPanel(false);
	}

	/**
	 * 更新自定义项
	 * 
	 * @param pk_corp
	 *            公司主键
	 * @param cbilltypecode
	 *            单据类型
	 * @param hPrefix
	 *            单据模板中单据头的自定义项前缀
	 * @param bPrefix
	 *            单据模板中单据体的自定义项前缀
	 */
	public void updateQueryConditionClientUserDef(String pk_corp, String cbilltypecode,
			String hPrefix, String bPrefix) {
		// 自定义项处理类
		UserDefConditionProcessor udcp = new UserDefConditionProcessor(pk_corp, cbilltypecode,
				hPrefix, bPrefix);
		// 注册模板VO处理，用于显示之前的自定义项处理
		registerQueryTemplateTotalVOProceeor(udcp);
		// 注册自定义项的编辑器工厂，用于显示输入自定义项
		registerFieldValueEelementEditorFactory(udcp);
	}

	/**
	 * 获得查询条件VO数组，<b>不包含逻辑条件(isCondition 属性为 false 的条件)</b>
	 * 
	 * @return 获得查询条件VO数组
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
	 * 如有自定义查询页签，加入相关sql
	 * 
	 * @return 完整 sql 查询条件语句
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
