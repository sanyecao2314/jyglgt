package nc.ui.po.oper;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import nc.bs.framework.common.NCLocator;
import nc.itf.arap.pub.IArapBillMapQureyPublic;
import nc.itf.po.IOrder;
import nc.itf.uap.rbac.IUserManageQuery;
import nc.ui.bd.b21.CurrParamQuery;
import nc.ui.ic.pub.RefMsg;
import nc.ui.ml.NCLangRes;
import nc.ui.po.OrderHelper;
import nc.ui.po.assistquery.PoAssistOperHelper;
import nc.ui.po.pub.ContractClassParse;
import nc.ui.po.pub.GrossProfitUI;
import nc.ui.po.pub.IPOVOChangeListener;
import nc.ui.po.pub.IPoCardPanel;
import nc.ui.po.pub.IPoListPanel;
import nc.ui.po.pub.InvAttrCellRenderer;
import nc.ui.po.pub.OrderPrintData;
import nc.ui.po.pub.PoCardPanel;
import nc.ui.po.pub.PoChangeUI;
import nc.ui.po.pub.PoListPanel;
import nc.ui.po.pub.PoLoadDataTool;
import nc.ui.po.pub.PoPublicUIClass;
import nc.ui.po.pub.PoQueryCondition;
import nc.ui.po.pub.PoVOBufferManager;
import nc.ui.po.pub.TableRowCellRender;
import nc.ui.po.rp.PoReceivePlanUI;
import nc.ui.pu.plugin.PUPluginUI;
import nc.ui.pu.pub.ATPForOneInvMulCorpUI;
import nc.ui.pu.pub.POPubSetUI2;
import nc.ui.pu.pub.PuTool;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.FramePanel;
import nc.ui.pub.ToftPanel;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIMenuItem;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.BillTableMouseListener;
import nc.ui.pub.bill.BillUIUtil;
import nc.ui.pub.change.PfChangeBO_Client;
import nc.ui.pub.msg.PfLinkData;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.scm.exp.ExcpTool;
import nc.ui.scm.ic.setpart.SetPartDlg;
import nc.ui.scm.plugin.InvokeEventProxy;
import nc.ui.scm.pub.BusiBillManageTool;
import nc.ui.scm.pub.CollectSettingDlg;
import nc.ui.scm.pub.bill.IBillExtendFun;
import nc.ui.scm.pub.billutil.BillCardPanelHelper;
import nc.ui.scm.pub.print.ISetBillVO;
import nc.ui.scm.pub.print.ScmPrintTool;
import nc.ui.scm.pub.report.AvgSaleQueryUI;
import nc.ui.scm.pub.report.BillRowNo;
import nc.ui.scm.pub.sourceref.SourceRefDlg;
import nc.ui.scm.sourcebill.SourceBillFlowDlg;
import nc.ui.sm.createcorp.CreateCorpQuery_Client;
import nc.ui.uap.sf.SFClientUtil;
import nc.vo.arap.billtypemap.BillTypeMapVO;
import nc.vo.ep.dj.DJZBItemVO;
import nc.vo.ep.dj.DJZBVO;
import nc.vo.po.OrderHeaderVO;
import nc.vo.po.OrderItemVO;
import nc.vo.po.OrderVO;
import nc.vo.po.pub.Operlog;
import nc.vo.po.pub.OrderPubVO;
import nc.vo.po.pub.OrderVORefreshTool;
import nc.vo.pr.pray.PraybillItemVO;
import nc.vo.pr.pray.PraybillVO;
import nc.vo.pu.exception.MaxPriceException;
import nc.vo.pu.exception.MaxStockException;
import nc.vo.pu.exception.PoReviseException;
import nc.vo.pu.exception.RwtPoToPrException;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.pub.ValidationException;
import nc.vo.pub.bill.BillTempletVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.constant.ScmConst;
import nc.vo.scm.datapower.BtnPowerVO;
import nc.vo.scm.ic.exp.ATPNotEnoughException;
import nc.vo.scm.pu.BillTypeConst;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pu.Timer;
import nc.vo.scm.pub.SCMEnv;
import nc.vo.scm.pub.report.AvgSaleQueryVO;
import nc.vo.scm.pub.session.ClientLink;
import nc.vo.scm.pub.smart.ObjectUtils;

public abstract class PoToftPanel extends nc.ui.pub.ToftPanel implements
		BillTableMouseListener, IPoCardPanel, IPoListPanel,
		// 列表打印
		ISetBillVO ,IPOVOChangeListener{
	
	// 单据Panel
	private PoCardPanel m_cpBill = null;

	// 发运产品是否启用
	private UFBoolean m_ufbIsDmEnabled = null;

	// 单据卡片列表共用的实例
	private POPubSetUI2 m_objSetUI2 = null;

	// 定单表体币种id
	private String m_Ccurencyid = null;

  //since v55
	protected TableRowCellRender m_renderYellowAlarmLine = new TableRowCellRender();

	
	public HashMap<String, PraybillItemVO> hPraybillVO = new HashMap<String, PraybillItemVO>();

	
	public boolean bAddNew = true;

	public boolean bRevise = false;
	
	boolean bBillList = false;
	
	HashMap hMaxPrice = new HashMap();
	
	private BillTempletVO m_billTempletVo = null;
	/**
	 * 作者：王印芬 功能：默认构造子 参数：无 返回：无 例外：无 日期：(2001-5-22 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public PoToftPanel() {
		super();
		init();
	}

	/**
	 * 设置单据标题
	 * 
	 * @param
	 * @return String
	 * @exception
	 * @see
	 * @since 2001-04-26
	 */
	abstract public String getTitle();

	/**
	 * 作者：李亮 功能：卡片初始化 参数：无 返回：无 例外：无 日期：(2001-04-21 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志： 2002-07-25 wyf 注释掉参照公司ID的设置
	 */
	private void init() {

		m_objSetUI2 = new POPubSetUI2();

		setCurPk_corp(PoPublicUIClass.getLoginPk_corp());
		// 加载卡片
		setLayout(new java.awt.BorderLayout());
		if (isInitStateBill()) {
			add(getPoCardPanel(), java.awt.BorderLayout.CENTER);
			setButtons(getBtnManager().getBtnaBill(this));
			// 初始化按钮
			setCurOperState(STATE_BILL_BROWSE);
			setButtonsStateInit();
		} else {
			add(getPoListPanel(), java.awt.BorderLayout.CENTER);
			setButtons(getBtnManager().getBtnaList(this));
			// 初始化按钮
			setCurOperState(STATE_LIST_BROWSE);
			setButtonsStateList();
		}
	}

	/**
	 * 作者：李亮 功能：配合按钮动作 参数：nc.ui.pub.ButtonObject bo 按钮 返回：无 例外：无 日期：(2002-3-13
	 * 11:39:21) 修改日期，修改人，修改原因，注释标志： 2002-04-22 wyf 分开单据及列表的按钮 wyf
	 * add/modify/delete 2002-03-21 begin/end
	 */
	public void onButtonClicked(nc.ui.pub.ButtonObject bo) {
		try{
			getInvokeEventProxy().beforeButtonClicked(bo);
		}catch(Exception e){
			showErrorMessage(e.getMessage());
			return;
		}
		if (getCurOperState() == STATE_LIST_BROWSE) {
			onButtonClickedList(bo);
		} else {
			onButtonClickedBill(bo);
		}try{
			getInvokeEventProxy().afterButtonClicked(bo);
		}catch(Exception e){
			showErrorMessage(e.getMessage());
			return;
		}
	}

	// 存量查询
	private ATPForOneInvMulCorpUI m_atpDlg = null;

	// 是否曾经进行过查询,以确定刷新按钮是否可用
	private boolean m_bEverQueryed = false;

	// VO转换标志 true VO是转入的 false 普通VO
	private boolean m_bFromOtherBill = false;

	// 存放当前缓存内的订单VO数组 [i] OrderVO
	private PoVOBufferManager m_bufPoVO = null;

	// 在单据转入时，存放缓存中已有的单据
	private PoVOBufferManager m_bufPoVOFrmBill = null;

	// 业务类型
	private String m_cbiztype = null;

	// VO转换的源单据类型
	private String m_cupsourcebilltype = null;

	// 供应商信息
	private nc.ui.bd.b09.CustInfoUI m_dlgCustInfo = null;

	// 成套件
	private SetPartDlg m_dlgInvSetQuery = null;

	// 定位框
	private PoLocateDlg m_dlgLocate = null;

	// 销量查询
	private AvgSaleQueryUI m_dlgSaleNum = null;

	// wyf 2002-12-27 add begin
	// 为避免来自请购单时，经过配额分配后，两张订单会来自同一个请购单行，
	// 导致一张保存后另一张因并发无法保存的问题。
	// 该变量在一张请购单保存后，更新已保存的请购单头体TS，在其他张订单保存前更新订单的来源头及体TS，避免并发
	// KEY: 订单头ID+订单体ID VALUE：String[2] [0]头TS [1]体TS
	private HashMap m_hmapUpSourcePrayTs = null;

	// int 转入单据之前的缓存中的单据数
	private int m_iOrgBufferLen = 0;

	// 列表Panel
	private PoListPanel m_listBill = null;

	// 当前操作状态
	// 单据操作类型 0原始 1增加 2修改 3作废 4列表 5毛利预估
	private int m_nCurOperState = 0;

	// 编辑时进行毛利预估、可用量查询时保存操作类型
	private int m_nflagSave = 0;

	// 单位编码
	private String m_pk_corp = null;

	// 毛利预估Panel
	private GrossProfitUI m_pnlGross = null;

	// wyf 2002-12-27 add end

	// 列表打印
	private ScmPrintTool m_printList = null;

	/**
	 * 作者：王印芬 功能：二次开发的项目使用，为类ContractClassParse提供 参数：FramePanel panel 返回：无 例外：无
	 * 日期：(2003-5-22 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	public PoToftPanel(FramePanel panel) {
		super();
		setFrame(panel);
		init();
	}

	/**
	 * 作者：WYF 功能：显示订单，并根据是否完全重新显示采取不同的显示策略 参数：boolean bEntirelyReset
	 * 完全重新显示，还是替换部分内容显示 替换内容可参见方法PoCardPanel.displayCurVOAfterJustSave(OrderVO)
	 * 返回：无 例外：无 日期：(2004-06-07 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	protected void displayCurVOEntirelyReset(boolean bEntirelyReset) {

		if (getBufferLength() == 0) {
			// 为空时
			getPoCardPanel().getBillModel().clearBodyData();
			// 增加表头
			getPoCardPanel().addNew();
			setButtonsStateBrowse();
			return;
		}

		OrderVO voCur = getOrderDataVOAt(getBufferVOManager().getVOPos());
		if (voCur.getBodyVO() == null) {
			// 回滚
			getBufferVOManager().setVOPos(
					getBufferVOManager().getPreviousVOPos());
			return;
		}

		// 显示VO
		if (bEntirelyReset) {
			getPoCardPanel().displayCurVO(voCur, isFromOtherBill());
		} else {
			getPoCardPanel().displayCurVOAfterJustSave(voCur);
		}

		// 操作后，刷新界面图标
		// V5 Del: setImageAfterOpr(stat);
	}

	/**
	 * 作者：王印芬 功能：返回当前缓存中VO的长度 参数：无 返回：无 例外：无 日期：(2003-4-01 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	protected int getBufferLength() {
		return getBufferVOManager().getLength();
	}

	/**
	 * 作者：王印芬 功能：字段m_vecOrder的GET方法 参数：无 返回：无 例外：无 日期：(2003-4-01 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */

	private PoVOBufferManager getBufferVOFrmBill() {
		if (m_bufPoVOFrmBill == null) {
			m_bufPoVOFrmBill = new PoVOBufferManager();
		}

		return m_bufPoVOFrmBill;
	}

	/**
	 * 获取界面按钮组
	 * 
	 * @param
	 * @return ButtonObject[]
	 * @exception
	 * @see
	 * @since 2001-05-26
	 */
	private ButtonObject[] getButtonsGrossProfit() {
		return getGrossProfitPanel().getButtonObjectArray();
	}

	/**
	 * 作者：王印芬 功能：返回来源单据类型 参数：无 返回：java.lang.String 例外：无 日期：(2004-4-01 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private java.lang.String getCupsourcebilltype() {
		return m_cupsourcebilltype;
	}

	/**
	 * 函数的功能、用途、对属性的更改，以及函数执行前后对象的状态。
	 * 
	 * @param 参数说明
	 * @return 返回值
	 * @exception 异常描述
	 * @see 需要参见的其它内容
	 * @since 从类的那一个版本，此方法被添加进来
	 * @return java.lang.String
	 */
	public java.lang.String getCurBizeType() {
		return m_cbiztype;
	}

	/**
	 * 得到当前界面的操作状态
	 * 
	 * @param 参数说明
	 * @return 返回值
	 * @exception 异常描述
	 * @see 需要参见的其它内容
	 * @since 从类的那一个版本，此方法被添加进来
	 * @return java.lang.String
	 */
	public int getCurOperState() {
		return m_nCurOperState;
	}

	/**
	 * @功能：获取用户ID
	 */
	public String getUserID() {
		String userId = nc.ui.pub.ClientEnvironment.getInstance().getUser()
				.getPrimaryKey();
		return userId;
	}

	/**
	 * @功能：获取用户ID
	 */
	public String getCorp() {
		String userId = nc.ui.pub.ClientEnvironment.getInstance()
				.getCorporation().getPk_corp();
		return userId;
	}

	/**
	 * 作者：WYF 功能：获取当前公司 参数：无 返回：无 例外：无 日期：(2004-06-14 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	protected java.lang.String getCurPk_corp() {
		return m_pk_corp;
	}

	/**
	 * 作者：王印芬 功能：定位对话框 参数：无 返回：DLG 例外：无 日期：(2001-10-20 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private PoLocateDlg getDlgLocate() {
		if (m_dlgLocate == null) {
			m_dlgLocate = new PoLocateDlg(this);
		}
		return m_dlgLocate;
	}

	/**
	 * 功能：获取销量查询对话框
	 */
	private AvgSaleQueryUI getDlgSaleNum() {
		if (null == m_dlgSaleNum) {
			m_dlgSaleNum = new AvgSaleQueryUI(this);
			m_dlgSaleNum.setTxtDayText(new Integer(7));
		}
		return m_dlgSaleNum;
	}

	/**
	 * 获取采购订单毛利预估界面Panel
	 * 
	 * @param
	 * @return BasisLendUI
	 * @exception
	 * @see
	 * @since 2001-04-28
	 */

	private GrossProfitUI getGrossProfitPanel() {
		if (m_pnlGross == null) {
			m_pnlGross = new GrossProfitUI(getCurPk_corp(), getPoCardPanel()
					.getPoPubSetUi2());
		}
		return m_pnlGross;
	}

	/**
	 * 作者：王印芬 功能：m_hmapUpSourcePrayTs的GET方法 参数：无 返回：Hashtable 例外：无
	 * 日期：(2001-10-20 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	private HashMap getHtUpSourcePrayTs() {
		if (m_hmapUpSourcePrayTs == null) {
			m_hmapUpSourcePrayTs = new HashMap();
		}
		return m_hmapUpSourcePrayTs;
	}

	/**
	 * 作者：WYF 功能：获取单据Panel。 参数：无 返回：无 例外：无 日期：(2001-10-20 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	protected abstract PoCardPanel getInitPoCardPanel(POPubSetUI2 setUi);

	/**
	 * 作者：WYF 功能：获取列表Panel。 参数：无 返回：无 例外：无 日期：(2001-10-20 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	protected abstract PoListPanel getInitPoListPanel(POPubSetUI2 setUi);

	/**
	 * 作者：王印芬 功能：IPoCardPanel的接口方法 参数：无 返回：无 例外：无 日期：(2003-10-09 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public int getOperState() {
		if (getCurOperState() == STATE_BILL_EDIT) {
			return IPoCardPanel.OPER_STATE_EDIT;
		} else {
			return IPoCardPanel.OPER_STATE_BROWSE;
		}
	}

	/**
	 * 作者：王印芬 功能：得到第i个订单VO 参数：无 返回：无 例外：无 日期：(2003-4-01 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public OrderVO getOrderDataVOAt(int iPos) {
		if (getBufferLength() == 0) {
			return null;
		}
		OrderVO voCur = (OrderVO) getBufferVOManager().getVOAt_LoadItemYes(
				this, iPos);
		// 来自其他单据时，切换单据加载表体
		if (isFromOtherBill()&&voCur!=null) {
			PoChangeUI.loadVOBodyWhenConvert(this, voCur,
					getCupsourcebilltype());
		}

		return voCur;
	}

	/**
	 * 作者：王印芬 功能：PoListPanelInterface的接口方法 参数：无 返回：无 例外：无 日期：(2003-10-09
	 * 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	public OrderHeaderVO[] getOrderViewHVOs() {
		if (getBufferLength() == 0) {
			return null;
		}

		OrderHeaderVO[] hvo = new OrderHeaderVO[getBufferLength()];
		for (int i = 0; i < getBufferLength(); i++) {
			hvo[i] = getBufferVOManager().getHeadVOAt(i);
		}
		return hvo;
	}

	/**
	 * 作者：王印芬 功能：该方法为接口PoListPanelInterface的方法 返回某下标的订单VO 参数：无 返回：OrderVO 例外：无
	 * 日期：(2003-05-09 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	public OrderVO getOrderViewVOAt(int iPos) {
		return getOrderDataVOAt(iPos);
	}

	/**
	 * 作者：王印芬 功能：字段m_iOrgBufferLen的GET方法 参数：无 返回：无 例外：无 日期：(2003-4-01 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */

	private int getOrgBufferLen() {
		return m_iOrgBufferLen;
	}

	/**
	 * 作者：李亮 功能：获取单据Panel。 参数：无 返回：无 例外：无 日期：(2001-10-20 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public PoCardPanel getPoCardPanel() {

		if (m_cpBill == null) {
			// 加载模板
			m_cpBill = getInitPoCardPanel(m_objSetUI2);
		}
		return m_cpBill;
	}

	/**
	 * 作者：王印芬 功能：获取列表 参数：无 返回：无 例外：无 日期：(2003-05-13 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public PoListPanel getPoListPanel() {

		if (m_listBill == null) {
			// 加载模板
			m_listBill = getInitPoListPanel(m_objSetUI2);
			// 加入监听
			m_listBill.addMouseListener(this);
			// 加入监听，实现VO缓存与表模型一同排序
			m_listBill.getHeadBillModel().addSortRelaObjectListener(
					getBufferVOManager());
		}
		return m_listBill;
	}

	/**
	 * 作者：WYF 功能：获取查询条件设置实例 参数：无 返回：无 例外：无 日期：(2001-10-20 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志： 2002-06-21 wyf 修改查询模板与业务类型相关的情况
	 */
	protected abstract PoQueryCondition getPoQueryCondition();

	/**
	 * 作者：WYF 功能：返回需打印或预览的数据 参数：无 返回：无 例外：无 日期：(2003-11-07 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private ArrayList getPrintAryList() {

		OrderVO[] voaSelected = getSelectedVOs();
		if (voaSelected == null) {
			return null;
		}

		int iLen = voaSelected.length;
		ArrayList arylistPrint = new ArrayList();
		for (int i = 0; i < iLen; i++) {
			arylistPrint.add(voaSelected[i]);
		}

		return arylistPrint;
	}

	/**
	 * 作者：王印芬 功能：得到批打印工具 参数：无 返回：无 例外：无 日期：(2003-11-04 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private ScmPrintTool getPrintTool() {
		if (m_printList == null) {
			m_printList = new ScmPrintTool(this, getPoCardPanel(),
					getModuleCode());
		}

		return m_printList;
	}

	/**
	 * 作者：李金巧 功能：获取需要保存的单据VO。 参数：无 返回：需保存的单据VO 例外： 日期：(2002-4-16 11:37:13)
	 * 修改日期，修改人，修改原因，注释标志： 2002-05-14 wyf 因后台加锁需全部VO，因此前端加入未改变的表体VO 2002-07-15
	 * wyf 增加对自定义项的处理 2002-07-16 wyf 加入对主辅币核算出现问题的处理 2002-12-27 wyf
	 * 加入来自请单的单据的来源TS进行处理的代码
	 */
	private OrderVO getSaveVO() {

		// 增加对校验公式的支持,错误显示由UAP处理 since v501
		if (!getPoCardPanel().getBillData().execValidateFormulas()) {
			return null;
		}

		OrderVO voSaved = getPoCardPanel().getSaveVO(
				getOrderDataVOAt(getBufferVOManager().getVOPos()));

		if (voSaved == null) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020601", "UPP4004020601-000073")/*
															 * @res
															 * ""未修改任何項目，保存操作無效""
															 */);
			return null;
		}
		// 改成后台组件处理
		// ICentralPurRule purRule =
		// (ICentralPurRule)nc.bs.framework.common.NCLocator.getInstance().lookup(ICentralPurRule.class.getName());
		OrderItemVO[] voSavedItem = voSaved.getBodyVO();
		int iBodyLen = voSavedItem.length;
		for(int i = 0 ; i < iBodyLen ; i ++){
			if(PuPubVO.getUFDouble_NullAsZero(voSavedItem[i].getNexchangeotobrate()).compareTo(new UFDouble(0))==0){
				MessageDialog.showHintDlg(this,
						NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000270")/* @res "提示" */,
				NCLangRes.getInstance().getStrByID("4004020201",
						"UPP4004020201-V56006")/* @res "折本汇率不能为0" */);
				return null;
			}
		}
		// 处理来自请购单的并发
		if (isFromOtherBill()
				&& getCupsourcebilltype().equals(BillTypeConst.PO_PRAY)) {
			voSavedItem = voSaved.getBodyVO();
			iBodyLen = voSavedItem.length;
			for (int i = 0; i < iBodyLen; i++) {
				String sType = voSavedItem[i].getCupsourcebilltype();
				if (sType != null) {
					String sHId = voSavedItem[i].getCupsourcebillid();
					if (sHId != null && sHId.trim().length() > 0
							&& getHtUpSourcePrayTs().containsKey(sHId)) {
						voSavedItem[i]
								.setCupsourcehts((String) getHtUpSourcePrayTs()
										.get(sHId));
					}
					String sBId = voSavedItem[i].getCupsourcebillrowid();
					if (sBId != null && sBId.trim().length() > 0
							&& getHtUpSourcePrayTs().containsKey(sBId)) {
						voSavedItem[i]
								.setCupsourcebts((String) getHtUpSourcePrayTs()
										.get(sBId));
					}
				}
			}
		}

		return voSaved;
	}

	/**
	 * 作者：WYF 功能：得到选中的订单数组，并根据参数决定是否查询无表体的订单 参数：boolean bQuery 是否查询 返回：无 例外：无
	 * 日期：(2003-11-05 13:24:16) 修改日期，修改人，修改原因，注释标志：
	 */
	public OrderVO[] getSelectedVOs() {

		// 需作废的订单
		Vector vecSelectedVO = new Vector();

		int[] iaSelectedRowCount = getPoListPanel().getHeadSelectedRows();
		int iLen = iaSelectedRowCount.length;
		int iVOPos = 0;
		for (int i = 0; i < iLen; i++) {
			iVOPos = getPoListPanel().getVOPos(iaSelectedRowCount[i]);
			vecSelectedVO.add(getBufferVOManager().getVOAt_LoadItemNo(iVOPos));
		}

		// 是否并发
		OrderVO[] voaRet = new OrderVO[iLen];
		vecSelectedVO.toArray(voaRet);
		boolean bSuccess = PoLoadDataTool.loadItemsForOrderVOs(this, voaRet);
		if (!bSuccess) {
			return null;
		}

		return voaRet;
	}

	/**
	 * @param 参数说明
	 * @return 返回值
	 * @exception 异常描述
	 * @see 需要参见的其它内容
	 * @since 从类的那一个版本，此方法被添加进来。（可选）
	 * 
	 */
	private boolean isEverQueryed() {

		return m_bEverQueryed;
	}

	/**
	 * @param 参数说明
	 * @return 返回值
	 * @exception 异常描述
	 * @see 需要参见的其它内容
	 * @since 从类的那一个版本，此方法被添加进来。（可选）
	 * 
	 */
	public boolean isFromOtherBill() {

		return m_bFromOtherBill;
	}

	/**
	 * 作者：李亮 功能：响应鼠标双击 参数： 返回： 例外： 日期：(2002-4-4 13:24:16) 修改日期，修改人，修改原因，注释标志：
	 * 2002-06-05 王印芬 修改函数onDoubleClick()
	 */
	public void mouse_doubleclick(nc.ui.pub.bill.BillMouseEnent e) {
		if (e.getPos() == BillItem.HEAD) {
			if(!onDoubleClick(e.getRow())) return;
			if (isFromOtherBill()) {
				onBillModify();
				showHintMessage(NCLangRes.getInstance().getStrByID(
						"4004020201", "UPP4004020201-000041")/* @res "编辑订单" */);
			} else {
				setButtonsStateBrowse();
				showHintMessage(NCLangRes.getInstance().getStrByID(
						"4004020201", "UPP4004020201-000042")/* @res "浏览订单" */);
			}
		}
	}

	/**
	 * 作者：李亮 功能：增加一行单据体 参数：无 返回：无 例外：无 日期：(2001-4-8 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public void onBillAppendLine() {
		m_renderYellowAlarmLine.setRight(false);
		showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000043")/* @res "增加订单行" */);
		getPoCardPanel().onActionAppendLine();
		setButtonsStateEdit();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH036")/* @res "增行成功" */);
	}

	/**
	 * 作者：李亮 功能：应付帐款查询 参数：无 返回：无 例外：无 日期：(2001-4-8 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillApQuery() {

		String sVendMangId = getPoCardPanel().getHeadItem("cvendormangid")
				.getValue();
		String sVendbaseId = getPoCardPanel().getHeadItem("cvendorbaseid")
				.getValue();
		if (sVendMangId == null || sVendMangId.trim().length() == 0) {
			return;
		}

		ApQueryDlg apDlg = new ApQueryDlg(this, getCurPk_corp(), sVendMangId,sVendbaseId,
				getPoCardPanel().getPoPubSetUi2());
		// apDlg.setLocation(
		// this.getX() + (this.getWidth()-apDlg.getWidth()) / 2,
		// this.getY() + this.getHeight()/ 2);
		// apDlg.setVisible(true);
		apDlg.showModal();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000038")/* @res "应付帐款查询" */);
	}

	/**
	 * 作者：李亮 功能：取消已进行的操作，不保存 参数：无 返回：无 例外：无 日期：(2001-04-28 10:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillCancel() {
    getPoCardPanel().stopEditing();
		m_renderYellowAlarmLine.setRight(false);
		hMaxPrice.clear();
		// 复置单据操作状态标志
		setCurOperState(STATE_BILL_BROWSE);

		if (isFromOtherBill()) {
			getBufferVOManager().removeAt(getBufferVOManager().getVOPos());

			if (getBufferLength() > 0) {
				onBillList();
			} else {
				// 临时缓存移到正式缓存
				getBufferVOManager().resetVOs(getBufferVOFrmBill());
				getBufferVOManager().setVOPos(
						getBufferVOManager().getLength() - 1);

				// 设置单据页面不可编辑
				getPoCardPanel().setEnabled(false);
				setIsFromOtherBill(false);
				// 显示当前订单
				displayCurVOEntirelyReset(true);

				setButtonsStateBrowse();
			}
		} else {
			// 设置单据页面不可编辑
			getPoCardPanel().setEnabled(false);
			// 显示当前订单
			displayCurVOEntirelyReset(true);

			setButtonsStateBrowse();
		}
		// 晁志平 增加重庆力帆自由项输入提示功能(订单维护、补货)
		if (!(this instanceof RevisionUI)) {
			InvAttrCellRenderer ficr = new InvAttrCellRenderer();
			ficr.setFreeItemRenderer(
					(nc.ui.pub.bill.BillCardPanel) getPoCardPanel(), null);
		}
		// 清除追加数据缓存
		hPraybillVO.clear();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH008")/* @res "取消成功" */);
	}

	/**
	 * 对当前单据进行合并显示，并可打印 功能： 参数： 返回： 例外： 日期：(2002-9-25 13:51:28)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillCombin() {
		CollectSettingDlg dlg = new CollectSettingDlg(this,
				NCLangRes.getInstance().getStrByID("common",
						"4004COMMON000000089")/* @res "合并显示" */,
				ScmConst.PO_Order, "4004020201", getCorpPrimaryKey(),
				ClientEnvironment.getInstance().getUser().getPrimaryKey(),
				OrderVO.class.getName(), OrderHeaderVO.class.getName(),
				OrderItemVO.class.getName());
		//
		dlg
				.initData(getPoCardPanel(), new String[] { "cinventorycode",
						"cinventoryname", "cspecification", "ctype",
						"prodarea", "ccurrencytype" }, // 固定分组列
						null, // new String[]{"dplanarrvdate"},缺省分组列
						new String[] { "noriginalcurmny", "noriginaltaxmny",
								"noriginaltaxpricemny", "nordernum" }, // 求和列
						null, new String[] { "noriginalcurprice",
								"noriginalnetprice" }, "nordernum"
				// delete by fangy 2002-10-29
				// "nc.vo.po.OrderItemVO"
				);
		dlg.showModal();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000039")/* @res "合并显示完成" */);
	}

	/**
	 * 此处插入方法说明。 功能：显示合同信息（甘肃电讯） 参数： 返回： 例外： 日期：2003/05/17 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillContractClass() {
		ContractClassParse.showDlg(this, getParameter(ContractClassParse
				.getParaName()), getOrderDataVOAt(getBufferVOManager()
				.getVOPos()));
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000040")/* @res "显示合同信息完成" */);
	}

	/**
	 * 作者：李金巧 功能：完成单据复制 参数： 返回： 例外： 日期：(2002-4-4 13:24:16) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 20050330 Czp V31增加逻辑：采购订单复制，当订单上游单据关闭时，订单应控制不可复制
	 */
	private void onBillCopyBill() {

		// //来源单据是否关闭
		// if(isExistClosedUpSrcBill( getOrderDataVOAt(
		// getBufferVOManager().getVOPos() ) )){
		// MessageDialog.showHintDlg(this,NCLangRes.getInstance().getStrByID("4004020201","UPPSCMCommon-000270")/*@res
		// "提示"*/,NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-000254")/*@res
		// "采购订单部分上游请购单已经关闭，不能复制"*/);
		// return;
		// }
		// 设置操作状态变量
		setCurOperState(STATE_BILL_EDIT);
		// 清空当前界面单据状态标志
		// V5 Del : setImageType(IMAGE_NULL);
		// 复制
		getPoCardPanel().onActionCopyBill(
				getOrderDataVOAt(getBufferVOManager().getVOPos()));
		// 检查是否有封存的存货
		BillCardPanelHelper.clearSealedInventories(getPoCardPanel(), "cmangid",
				new String[] { "cmangid", "cbaseid", "cinventorycode",
						"cinventoryname", "cspecification", "ctype" });
		// 设置按钮
		setButtonsStateEdit();
		// 全键盘
		getPoCardPanel().transferFocusTo(BillCardPanel.HEAD);

		// 晁志平 增加重庆力帆自由项输入提示功能(订单维护、补货)
		if (!(this instanceof RevisionUI)) {
			InvAttrCellRenderer ficr = new InvAttrCellRenderer();
			ficr.setFreeItemRenderer(
					(nc.ui.pub.bill.BillCardPanel) getPoCardPanel(),
					getOrderDataVOAt(getBufferVOManager().getVOPos()));
		}
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH029")/* @res "已复制" */);

	}

	/**
	 * 作者：李金巧 功能：完成单据行复制 参数： 返回： 例外： 日期：(2002-4-4 13:24:16) 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillCopyLine() {

		showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000044")/* @res "复制订单行" */);
		if (getPoCardPanel().getRowCount() > 0) {
			if (getPoCardPanel().getBillTable().getSelectedRowCount() <= 0) {
				showWarningMessage(NCLangRes.getInstance().getStrByID(
						"4004020201", "UPP4004020201-000045")/*
																 * @res
																 * "复制行前请先选择表体行"
																 */);
				return;
			}

			getPoCardPanel().copyLine();

		}
		String bRight = getPoCardPanel().getHeadItem("bsocooptome").getValue();
		if(bRight!=null&&bRight.equals("true")){
			for(int i = 0 ; i < getPoCardPanel().getBillModel().getRowCount() ; i ++){
				getPoCardPanel().getBillModel().setValueAt(null, i, "csourcebillid");
				getPoCardPanel().getBillModel().setValueAt(null, i, "csourcebilltype");
				getPoCardPanel().getBillModel().setValueAt(null, i, "csourcerowid");
				getPoCardPanel().getBillModel().setValueAt(null, i, "cupsourcebillid");
				getPoCardPanel().getBillModel().setValueAt(null, i, "cupsourcebillrowid");
				getPoCardPanel().getBillModel().setValueAt(null, i, "cupsourcebilltype");
			}
		}
		setButtonsStateEdit();

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH039")/* @res "复制行成功" */);
	}

	/**
	 * 作者：李亮 功能：删除一行单据体 参数： 返回： 例外： 日期：(2002-1-4 13:24:16) 修改日期，修改人，修改原因，注释标志：
	 * 2002-07-08 wyf 删除一行后不自动增行，去掉判断，因为不存在行时，删行不可用
	 */
	private void onBillDeleteLine() {

		showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000046")/* @res "删除订单行" */);

		getPoCardPanel().onActionDeleteLine();
    
    /*del , since 2008-10-24, 删除，行数为零也不必重新设置表头业务类型参照。
		if (getPoCardPanel().getRowCount() <= 0) {
			UIRefPane refpane = (UIRefPane) getPoCardPanel().getHeadItem(
					"cbiztype").getComponent();
			refpane.setRefModel(new PuBizTypeRefModel(PoPublicUIClass
					.getLoginPk_corp(), BillTypeConst.PO_ORDER, false));
		}
    */
    
		setButtonsStateEdit();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH037")/* @res "删行成功" */);
	}

	/**
	 * 作者：李亮 功能：作废当前订单 参数：无 返回：无 例外：无 日期：(2002-4-4 13:24:16) 修改日期，修改人，修改原因，注释标志：
	 * 2002-05-22 王印芬 数据库中去掉提示，代码中加入
	 */
	private void onBillDiscard() {

		OrderVO voCanceled = getOrderDataVOAt(getBufferVOManager().getVOPos());
		try{
			getInvokeEventProxy().beforeAction(nc.vo.scm.plugin.Action.DELETE,new OrderVO[]{ voCanceled});
		}catch(Exception e){
			showErrorMessage(e.getMessage());
			return;
		}
		// 作废
		boolean bSuccessed = onDiscard(this, new OrderVO[] { voCanceled });
		if (!bSuccessed) {
			return;
		}
		// 业务日志
		Operlog operlog = new Operlog();
		voCanceled.getOperatelogVO().setOpratorname(
				nc.ui.pub.ClientEnvironment.getInstance().getUser()
						.getUserName());
		voCanceled.getOperatelogVO().setCompanyname(
				nc.ui.pub.ClientEnvironment.getInstance().getCorporation()
						.getUnitname());
		voCanceled.getOperatelogVO().setOperatorId(
				nc.ui.pub.ClientEnvironment.getInstance().getUser()
						.getPrimaryKey());
		operlog.insertBusinessExceptionlog(voCanceled, "删除", "删除",
				nc.vo.scm.funcs.Businesslog.MSGMESSAGE,
				nc.vo.scm.pu.BillTypeConst.PO_ORDER, nc.ui.sm.cmenu.Desktop
						.getApplet().getParameter("USER_IP"));
		getBufferVOManager().removeAt(getBufferVOManager().getVOPos());
		displayCurVOEntirelyReset(true);
		// 设置按钮
		setButtonsStateBrowse();

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH006")/* @res "删除成功" */);
	}

	/**
	 * 作者：王印芬 功能：卡片的文档管理 参数：无 返回：无 例外：无 日期：(2003-04-11 10:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillDocManage() {

		OrderVO voCur = getOrderDataVOAt(getBufferVOManager().getVOPos());

		// 处理文档管理框删除按钮是否可用
		HashMap mapBtnPowerVo = new HashMap();
		Integer iBillStatus = null;
		BtnPowerVO pVo = new BtnPowerVO(voCur.getHeadVO().getVordercode());
		iBillStatus = PuPubVO.getInteger_NullAs(voCur.getHeadVO()
				.getForderstatus(), new Integer(0));
		if (iBillStatus.intValue() == 2 || iBillStatus.intValue() == 3
				|| iBillStatus.intValue() == 5) {
			pVo.setFileDelEnable("false");
		}
		mapBtnPowerVo.put(voCur.getHeadVO().getVordercode(), pVo);
		nc.ui.scm.file.DocumentManager.showDM(this,ScmConst.PO_Order ,new String[] { voCur
				.getHeadVO().getCorderid() }, mapBtnPowerVo);
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000025")/* @res "文档管理成功" */);
	}


	/**
	 * 作者：王印芬 功能：关闭当前订单，对应按钮"关闭"的操作 参数：无 返回：无 例外：无 日期：(2003-03-31 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillExecClose() {
	    String action = "CLOSE";
		// 复制当前单据
		OrderVO voOrderClone = (OrderVO) (getOrderDataVOAt(getBufferVOManager()
				.getVOPos()).clone());
		OrderItemVO[] voaItem = voOrderClone.getBodyVO();
		int iBodyLen = voaItem.length;
		for (int i = 0; i < iBodyLen; i++) {
			// 关闭激活订单行
			if (voaItem[i].getIisactive().compareTo(
					OrderItemVO.IISACTIVE_ACTIVE) == 0) {
				// //状态字为正常关闭：到BO中设定
				// voaItem[i].setIisactive(OrderItemVO.IISACTIVE_CLOSE_NORMAL);
				// 记录关闭时间到修正日期，以备评估时查询
				voaItem[i].setDcorrectdate(getClientEnvironment().getDate());

				// V501,支持记录关闭时间
				voaItem[i].setDCloseDate(PoPublicUIClass.getLoginDate());
				// V501,支持记录关闭人
				voaItem[i].setCCloseUserId(PoPublicUIClass.getLoginUser());
			}
		}
		PoPublicUIClass.setCuserId(new OrderVO[] { voOrderClone });

		// 关闭
		OrderVO voRet = null;
		try {
			voRet = (OrderVO) PfUtilClient.processActionNoSendMessage(this,
			        action, BillTypeConst.PO_ORDER, getClientEnvironment()
							.getDate().toString().trim(), voOrderClone, null,
					null, null);
			if (!PfUtilClient.isSuccess()) {
				return;
			}
		} catch (Exception e) {
			PuTool.outException(this, e);
			return;
		}
		OrderVO[] voLightRefreshed = null;
        try {
            /*
             * czp V31 流量优化，减少下行传输数据量 voSaved =
             * OrderBO_Client.queryOrderVOByKey(voSaved.getHeadVO().getCorderid()) ;
             */
            voLightRefreshed = OrderHelper.queryOrderVOsLight(
                    new String[] { voRet.getHeadVO().getCorderid() }, action);
        } catch (Exception e) {
            PuTool.printException(e);
            voRet = null;
        }
        if(voLightRefreshed != null && voLightRefreshed.length > 0
                && voRet != null){
            OrderVORefreshTool.refreshVO(voRet, voLightRefreshed[0], action);
        }
//		try {
//			voRet = OrderHelper.queryOrderVOByKey(voRet.getHeadVO().getCorderid());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		// 重新设置当前订单VO
		getBufferVOManager().setVOAt(getBufferVOManager().getVOPos(), voRet);

		// 处理显示
		for (int i = 0; i < iBodyLen; i++) {
			getPoCardPanel().setBodyValueAt(
					OrderItemVO.IISACTIVE_CLOSE_ABNORMAL, i, "iisactive");
			getPoCardPanel().setBodyValueAt(getClientEnvironment().getDate(),
					i, "dcorrectdate");
			getPoCardPanel().setBodyValueAt(PoPublicUIClass.getLoginDate(), i,
					"dclosedate");
			getPoCardPanel().setBodyValueAt(PoPublicUIClass.getLoginUser(), i,
					"ccloseuserid");
			getPoCardPanel().execBodyFormulas(
					i,
					getPoCardPanel().getBodyItem("ccloseusername")
							.getLoadFormula());
			getPoCardPanel().setBodyValueAt(true, i, "bcloserow");
		}
		setButtonsStateBrowse();

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH013")/* @res "已关闭" */);
		// 操作后，刷新界面图标
		// V5 Del: setImageAfterOpr(getOrderDataVOAt(
		// getBufferVOManager().getVOPos() ).getHeadVO().getForderstatus());
	}

	/**
	 * 解冻当前订单
	 * 
	 * @param
	 * @return
	 * @exception
	 * @see
	 * @since 2001-04-28
	 */
	private void onBillExecUnFreeze() {

		// setStateFlag(STATE_UNFREEZE);

		// 获取要保存的VO
		// 复制当前单据
		OrderVO voOrderClone = (OrderVO) (getOrderDataVOAt(getBufferVOManager()
				.getVOPos()).clone());
		if (voOrderClone == null) {
			return;
		}
		// OrderItemVO[] voaItem = voOrderClone.getBodyVO() ;
		// int iBodyLen = voaItem.length ;
		// 解冻
		Object voRet = null;
		try {
			// 解冻
			// PfUtilClient.processBatch(
			// "UNFREEZE",
			// BillTypeConst.PO_ORDER,
			// PoPublicUIClass.getLoginDate().toString(),
			// new OrderVO[]{voOrderClone});
			voRet = PfUtilClient.processActionNoSendMessage(this,
					"UNFREEZE", BillTypeConst.PO_ORDER, getClientEnvironment()
							.getDate().toString().trim(), voOrderClone, null,
					null, null);
			if (!PfUtilClient.isSuccess()) {
				return;
			}

		} catch (Exception e) {
			PuTool.outException(this, e);
			return;
		}
		//解决并发问题，by zhaoyha at 2009.12.5 BUG NCdp201099268
    if(null!=voRet && voRet instanceof OrderVO[] && 0<((OrderVO[])voRet).length)
     voOrderClone.getHeadVO().setTs(((OrderVO[])voRet)[0].getHeadVO().getTs());
      
		voOrderClone.getHeadVO().setForderstatus(0);
		// 重新设置当前订单VO
		getBufferVOManager().setVOAt(getBufferVOManager().getVOPos(),
				voOrderClone);
		// 处理显示
		// for (int i = 0; i < iBodyLen; i++){
		// getPoCardPanel().setBodyValueAt(OrderItemVO.IISACTIVE_CLOSE_ABNORMAL,i,"iisactive")
		// ;
		// getPoCardPanel().setBodyValueAt(getClientEnvironment().getDate(),i,"dcorrectdate")
		// ;
		// getPoCardPanel().setBodyValueAt(PoPublicUIClass.getLoginDate()
		// ,i,"dclosedate") ;
		// getPoCardPanel().setBodyValueAt(PoPublicUIClass.getLoginUser()
		// ,i,"ccloseuserid") ;
		// getPoCardPanel().execBodyFormulas(i,getPoCardPanel().getBodyItem("ccloseusername").getLoadFormula());
		// }
		getPoCardPanel().setHeadItem("forderstatus",
				voOrderClone.getHeadVO().getForderstatus());
    getPoCardPanel().setHeadItem("ts",
        voOrderClone.getHeadVO().getTs());
		setButtonsStateBrowse();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH019")/* @res "已解冻" */);
		// if (PfUtilClient.isSuccess()) {
		// showHintMessage(NCLangRes.getInstance().getStrByID("common","UCH019")/*@res
		// "已解冻"*/);
		//
		// //保存成功后重置数据
		// resetData(voaSelected);
		//
		// getPoListPanel().displayCurVO(0,true);
		// } else
		// showHintMessage(NCLangRes.getInstance().getStrByID("40040203","UPP40040203-000015")/*@res
		// "订单解冻失败"*/);
		//
		// setStateFlag(STATE_INIT);
	}

	/**
	 * 作者：王印芬 功能：打开当前订单，对应按钮"打开"的操作 参数：无 返回：无 例外：无 日期：(2003-03-31 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillExecOpen() {

		// 复制当前单据
		OrderVO voOrderClone = (OrderVO) (getOrderDataVOAt(getBufferVOManager()
				.getVOPos()).clone());
		OrderItemVO[] voaItem = voOrderClone.getBodyVO();
		int iBodyLen = voaItem.length;
		for (int i = 0; i < iBodyLen; i++) {
			// 关闭激活订单行
			if (voaItem[i].getIisactive().compareTo(
					OrderItemVO.IISACTIVE_ACTIVE) != 0) {
				// //状态字为激活：到BO中设定
				// voaItem[i].setIisactive(OrderItemVO.IISACTIVE_ACTIVE);
				// 清空修正日期
				voaItem[i].setDcorrectdate(null);

				// V501,支持记录关闭时间
				voaItem[i].setDCloseDate(null);
				// V501,支持记录关闭人
				voaItem[i].setCCloseUserId(null);
			}
		}
		PoPublicUIClass.setCuserId(new OrderVO[] { voOrderClone });

		// 打开
		OrderVO voRet = null;
		try {
			voRet = (OrderVO) PfUtilClient.processActionNoSendMessage(this,
					"OPEN", BillTypeConst.PO_ORDER, getClientEnvironment()
							.getDate().toString().trim(), voOrderClone, null,
					null, null);
			if (!PfUtilClient.isSuccess()) {
				return;
			}
		} catch (Exception e) {
			PuTool.outException(this, e);
			return;
		}
		
		try {
			voRet = OrderHelper.queryOrderVOByKey(voRet.getHeadVO().getCorderid());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 重新设置当前订单VO
		getBufferVOManager().setVOAt(getBufferVOManager().getVOPos(), voRet);

		// 处理显示
		for (int i = 0; i < iBodyLen; i++) {
			getPoCardPanel().setBodyValueAt(OrderItemVO.IISACTIVE_ACTIVE, i,
					"iisactive");
			getPoCardPanel().setBodyValueAt(null, i, "dcorrectdate");
			getPoCardPanel().setBodyValueAt(null, i, "dclosedate");
			getPoCardPanel().setBodyValueAt(null, i, "ccloseuserid");
			getPoCardPanel().setBodyValueAt(null, i, "ccloseusername");
			getPoCardPanel().setBodyValueAt(null, i, "bcloserow");
		}
		setButtonsStateBrowse();

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH012")/* @res "已打开" */);
		// 操作后，刷新界面图标
		// V5 Del: setImageAfterOpr(getOrderDataVOAt(
		// getBufferVOManager().getVOPos() ).getHeadVO().getForderstatus());
	}

	/**
	 * 作者：李亮 功能：显示第一张采购订单 参数：无 返回：无 例外：无 日期：(2001-5-13 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志： 2002-06-25 wyf
	 * 精简代码，相同代码称到onButtonClicked中及setButtonState中 2003-02-21 wyf 加入记录前一VO位置的代码
	 */
	private void onBillFirst() {

		getBufferVOManager().setPreviousVOPos(getBufferVOManager().getVOPos());
		getBufferVOManager().setVOPos(0);

		displayCurVOEntirelyReset(true);
		setButtonsStateBrowse();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000026")/* @res "成功显示首页" */);

	}

	/**
	 * 作者：李亮 功能：订单毛利预估 参数：无 返回：无 例外：无 日期：(2001-5-13 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillGrossProfit() {
		if (getPoCardPanel().getRowCount() == 0
				|| getPoCardPanel().getBillTable().getSelectedRow() == -1) {
			showWarningMessage(NCLangRes.getInstance().getStrByID(
					"common", "UCH004"));
			return;
		}
		OrderVO voOrder = (OrderVO) getPoCardPanel().getBillValueVO(
				OrderVO.class.getName(), OrderHeaderVO.class.getName(),
				OrderItemVO.class.getName());

		if (voOrder == null || voOrder.getBodyVO().length == 0) {
			return;
		}

		ArrayList listGrossItem = new ArrayList();
		ArrayList listHintRow = new ArrayList();
		int iLen = voOrder.getBodyVO().length;
		for (int i = 0; i < iLen; i++) {
			if (voOrder.getBodyVO()[i].getCmangid() == null) {
				continue;
			}
			String sBaseId = (String) getPoCardPanel().getBodyValueAt(i,
					"cbaseid");
			if (sBaseId == null) {
				continue;
			}
			// =============费用行、折扣行、无币种行
			if (PuTool.isLabor(sBaseId)
					|| PuTool.isDiscount(sBaseId)
					|| PuPubVO
							.getString_TrimZeroLenAsNull(voOrder.getBodyVO()[i]
									.getCcurrencytypeid()) == null) {
				listHintRow.add("" + new Integer(i + 1));
				continue;
			}

			// 加入毛利预估行:赠品不计毛利
			if (voOrder.getBodyVO()[i] != null
					&& !voOrder.getBodyVO()[i].isLargess()) {
				listGrossItem.add(voOrder.getBodyVO()[i]);
			}
		}

		iLen = listHintRow.size();
		if (iLen > 0) {
			String strErrMsg = PuPubVO
					.getSelectStringByFields((String[]) listHintRow
							.toArray(new String[listHintRow.size()]));
			showWarningMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000047", null,
					new String[] { strErrMsg })/*
												 * @res
												 * "{0}行不能进行毛利预估；原因是该行存货可能为费用、折扣属性或该行无币种"
												 */);
		}
		iLen = listGrossItem.size();
		if (iLen == 0) {
			return;
		}
		int iSelected = getPoCardPanel().getBillTable().getSelectedRows()[0];
		m_Ccurencyid = getPoCardPanel().getBodyValueAt(iSelected,
				"ccurrencytypeid").toString();
		try {
			m_nflagSave = getCurOperState();
			setCurOperState(STATE_BILL_GROSS_EVALUATE);

			remove(getPoCardPanel());
			add(getGrossProfitPanel(), java.awt.BorderLayout.CENTER);

			// 设置报表Panel按钮
			setButtons(getButtonsGrossProfit());

			getButtonsGrossProfit()[0].setVisible(true);

			// 显示结果
			voOrder.setChildrenVO((OrderItemVO[]) listGrossItem
					.toArray(new OrderItemVO[iLen]));
			getGrossProfitPanel().display(voOrder);
			getGrossProfitPanel().displayData(m_Ccurencyid);
			repaint();

			showHintMessage(NCLangRes.getInstance().getStrByID(
					"common", "4004COMMON000000041")/* @res "订单毛利预估完成" */);
		} catch (Exception e) {
			PuTool.outException(this, e);
		}
	}

	/**
	 * 插入一行单据体
	 * 
	 * @param
	 * @return
	 * @exception
	 * @see
	 * @since 2001-04-28
	 */
	private void onBillInsertLine() {

		showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000048")/* @res "插入订单行" */);
		// 插行
		getPoCardPanel().onActionInsertLine();
		// 按钮
		setButtonsStateEdit();

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH038")/* @res "插入行成功" */);
	}

	/**
	 * 作者：WYF 功能：替换件 参数：无 返回：无 例外：无 日期：(2003-11-28 13:24:16) 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillInvReplace() {

		if (getPoCardPanel().getRowCount() == 0
				|| getPoCardPanel().getBillTable().getSelectedRow() == -1) {
			showWarningMessage(NCLangRes.getInstance().getStrByID(
					"common", "UCH004"));
			return;
		}
		int[] iRows = getPoCardPanel().getBillTable().getSelectedRows();
		int iRow = iRows[0];
		if (iRow == -1) {
			MessageDialog.showHintDlg(this,
					NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000270")/* @res "提示" */,
					NCLangRes.getInstance().getStrByID("4004020201",
							"UPP4004020201-000049")/* @res "请选中有存货的行" */);
			return;
		}

		String sMangId = (String) getPoCardPanel().getBodyValueAt(iRow,
				"cmangid");
		if (PuPubVO.getString_TrimZeroLenAsNull(sMangId) == null) {
			MessageDialog.showHintDlg(this,
					NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000270")/* @res "提示" */,
					NCLangRes.getInstance().getStrByID("4004020201",
							"UPP4004020201-000049")/* @res "请选中有存货的行" */);
			return;
		}
		// 由于UAP控件不支持刷新传入的管理档案PK，故每次新NEW控件（局部变量）
		// 存货信息
		nc.ui.bd.b16.InvManCardPanel m_pnlInvInfo = new nc.ui.bd.b16.InvManCardPanel();

		m_pnlInvInfo.setInvManPk(sMangId);

		UIDialog dlg = new UIDialog(this);
		dlg.setTitle(NCLangRes.getInstance().getStrByID("4004020201",
				"UPT4004020201-000071")/* @res "存货信息" */);
		dlg.setSize(700, 500);
		dlg.setContentPane(m_pnlInvInfo);
		dlg.showModal();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000042")/* @res "替换件显示完成" */);
	}

	/**
	 * 作者：王印芬 功能：成套件查询 参数：无 返回：无 例外：无 日期：(2003-03-31 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillInvSetQuery() {

		if (getPoCardPanel().getRowCount() == 0) {
			return;
		}

		int iRow = getPoCardPanel().getBillTable().getSelectedRow();
		if (iRow == -1) {
			MessageDialog.showHintDlg(this,
					NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000270")/* @res "提示" */,
					NCLangRes.getInstance().getStrByID("4004020201",
							"UPP4004020201-000049")/* @res "请选中有存货的行" */);
			return;
		}

		String sMangId = (String) getPoCardPanel().getBodyValueAt(iRow,
				"cmangid");
		if (PuPubVO.getString_TrimZeroLenAsNull(sMangId) == null) {
			MessageDialog.showHintDlg(this,
					NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000270")/* @res "提示" */,
					NCLangRes.getInstance().getStrByID("4004020201",
							"UPP4004020201-000049")/* @res "请选中有存货的行" */);
			return;
		}

		if (m_dlgInvSetQuery == null) {
			m_dlgInvSetQuery = new SetPartDlg(this, NCLangRes
					.getInstance().getStrByID("4004020201",
							"UPP4004020201-000109")/* @res "成套件" */);
		}
		m_dlgInvSetQuery.setParam(getCurPk_corp(), sMangId);
		m_dlgInvSetQuery.showSetpartDlg();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000043")/* @res "成套件查询完成" */);
	}

	/**
	 * 作者：李亮 功能：显示最后一张采购订单 参数：无 返回：无 例外：无 日期：(2001-5-13 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志： 2002-06-25 wyf
	 * 精简代码，相同代码称到onButtonClicked中及setButtonState中 2003-02-21 wyf 加入记录前一VO位置的代码
	 */
	private void onBillLast() {

		getBufferVOManager().setPreviousVOPos(getBufferVOManager().getVOPos());
		getBufferVOManager().setVOPos(getBufferLength() - 1);

		displayCurVOEntirelyReset(true);
		setButtonsStateBrowse();

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000029")/* @res "成功显示末页" */);
	}

	/**
	 * 作者：李亮 功能：显示订单列表界面 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
	 * 日期：(2001-4-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillList() {
		Timer time = new Timer();
		time.start();
		Timer time1 = new Timer();
		time1.start();
		// 设置标志
		setCurOperState(STATE_LIST_BROWSE);

		// 显示列表界面
		int iSortCol = getPoCardPanel().getBillModel().getSortColumn();
		boolean bSortAsc = getPoCardPanel().getBillModel().isSortAscending();
		remove(getPoCardPanel());
		add(getPoListPanel(),java.awt.BorderLayout.CENTER);

		// 清空当前界面单据状态标志
		// V5 Del : setImageType(IMAGE_NULL);
		
		// 显示VO
		if(!bBillList){
		getPoListPanel().displayCurVO(getBufferVOManager().getVOPos(), true,
				isFromOtherBill());
		int bodyLength=ArrayUtils.isEmpty(getBufferVOManager().getVOs())?0:
		  getBufferVOManager().getVOs()[getBufferVOManager().getVOPos()].getBodyLen();
		for (int i = 0; i < bodyLength; i++) {
			Object btransclosed = getPoCardPanel().getBillModel().getValueAt(i, "btransclosed");
			if(btransclosed != null){
				getPoListPanel().getBodyBillModel().setValueAt(new UFBoolean(String.valueOf(btransclosed)), i, "btransclosed");
			}
		}
		}else{
			if (getPoListPanel().getHeadTable().getRowCount()!=0) {
				getPoListPanel().getHeadBillModel().setRowState(getBufferVOManager().getVOPos(), BillModel.SELECTED);
				getPoListPanel().getHeadTable().setRowSelectionInterval(getBufferVOManager().getVOPos(),getBufferVOManager().getVOPos()) ;
			}
		}
		time1.showExecuteTime("卡片切换到列表加载表头占用时间");
		// 手工排序列表表体(同步卡片表体排序结果)
		if (iSortCol >= 0) {
			getPoListPanel().getBodyBillModel()
					.sortByColumn(iSortCol, bSortAsc);
		}
		// 设置按钮
		setButtons(getBtnManager().getBtnaList(this));

		// 手动触发按钮逻辑
		if (isFromOtherBill()) {
			setButtonsStateListFromBills();
		} else {
			setButtonsStateListNormal();
		}

		updateUI();

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH022")/* @res "列表显示" */);
		time.showExecuteTime("卡片切换到列表占用时间");
	}

	/**
	 * 联查
	 * 
	 * @param 参数说明
	 * @return 返回值
	 * @exception 异常描述
	 * @see 需要参见的其它内容
	 * @since 从类的那一个版本，此方法被添加进来。（可选）
	 * @deprecated 该方法从类的那一个版本后，已经被其它方法替换。（可选）
	 * 
	 */
	private void onBillLnkQuery() {

		OrderVO voOrder = getOrderDataVOAt(getBufferVOManager().getVOPos());

		SourceBillFlowDlg soureDlg = new nc.ui.scm.sourcebill.SourceBillFlowDlg(
				this, BillTypeConst.PO_ORDER,
				voOrder.getHeadVO().getCorderid(), null, PoPublicUIClass
						.getLoginUser(), voOrder.getHeadVO().getVordercode());
		soureDlg.showModal();

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000019")/* @res "联查成功" */);
	}

	/**
	 * 订单列表界面中定位
	 * 
	 * @param
	 * @return
	 * @exception
	 * @see
	 * @since 2001-04-28 2003-02-21 wyf 加入记录前一VO位置的代码
	 */
	private void onBillLocate() {

		int iRet = UIDialog.ID_CANCEL;

		if (getBufferLength() > 0) {

			iRet = getDlgLocate().showModal();

			if (getDlgLocate().isCloseOk()) {

				getBufferVOManager().setPreviousVOPos(
						getBufferVOManager().getVOPos());
				getBufferVOManager().setVOPos(
						getDlgLocate().getBillLocation() - 1);

				displayCurVOEntirelyReset(true);
			}
		}

		// 设置按钮
		setButtonsStateBrowse();
		if (iRet == UIDialog.ID_OK) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"common", "4004COMMON000000035")/* @res "定位成功" */);
		} else {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"common", "4004COMMON000000076")/* @res "浏览单据" */);
		}
	}

	/**
	 * 作者：李亮 功能：修改当前订单 参数：无 返回：无 例外：无 日期：(2002-3-13 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志： 2002-05-09 wyf 加入设置合同号及存货的可编辑性 2002-05-27 wyf
	 * 加入对合同相关信息的可编辑性的控制 2002-06-14 wyf 修改对按钮的控制 2002-06-25 wyf 加入批次号的可编辑性控制
	 * 2002-06-26 wyf 加入汇率的可编辑性控制 2002-07-01 wyf 加入换算率的可编辑性控制
	 */
	protected void onBillModify() {
		bAddNew = true;
		bRevise = false;
		m_renderYellowAlarmLine.setRight(false);
		// if(getPoCardPanel().getTailItem("cauditpsn").getValue() == null){
		//			
		// }else{
		// this.showErrorMessage("已经有审批人，不允许修改");
		// return;
		// }
		// 为追加增行增加子按钮
		String strBizType = getPoCardPanel().getHeadItem("cbiztype").getValue();
		boolean bright = false;
		String cupsourcetype = (String)getPoCardPanel().getBillModel().getValueAt(0, "cupsourcebilltype");
		if(cupsourcetype!=null&&cupsourcetype.equals("20")){
			bright = true;
		}
		if (strBizType != null) {
			ButtonObject bo = new ButtonObject("11");
			bo.setTag(strBizType);
			PfUtilClient.retAddBtn(getBtnManager().btnBillAddContinue,
					getCurPk_corp(), nc.vo.scm.pu.BillTypeConst.PO_ORDER, bo);
			Vector vecBtn = getBtnManager().btnBillAddContinue.getChildren();
			if (vecBtn.size() > 0) {
				for (int i = 0; i < vecBtn.size(); ) {
					ButtonObject btn = (ButtonObject) vecBtn.get(i);
					if (!btn.getTag().startsWith("20")||!bright) {
						getBtnManager().btnBillAddContinue
								.removeChildButton(btn);
					}else{
						i++;
					}

				}
			}
		}
		setButtons(getBtnManager().getBtnaBill(this));
		
		showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000041")/* @res "编辑订单" */);

		// 设置操作状态变量
		setCurOperState(STATE_BILL_EDIT);
		// 设置按钮可用性
		setButtonsStateEdit();
		if(getBtnManager().btnBillAddContinue.getChildCount()<=0){
			getBtnManager().btnBillAddContinue.setEnabled(false);
		}else{
			getBtnManager().btnBillAddContinue.setEnabled(true);
		}
		updateButton(getBtnManager().btnBillAddContinue);
		updateButtonsAll();
		//
		getPoCardPanel().onActionModify();

		// 全键盘
		getPoCardPanel().transferFocusTo(BillCardPanel.HEAD);
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000030")/* @res "正在修改" */);
		OrderVO voCur = getOrderDataVOAt(getBufferVOManager().getVOPos());
		
		//modify by hanbin 2010-12-25 原因：(NCdp202872199)电子商务分标分量的时候，弹出采购订单界面的时候，从列表切换到卡片的时候，订单从新询价了（不应该询价）
		//电子商务中标结果推采购订单时，不需要重新询价、关联合同等
		if(isFromOtherBill() && !isFromECBill() && !(cupsourcetype != null && (cupsourcetype.equals(BillTypeConst.CT_ORDINARY) || cupsourcetype.equals(BillTypeConst.CT_BEFOREDATE)))
		        && !(cupsourcetype != null && cupsourcetype.equals(BillTypeConst.PO_ARRIVE))
		        && !(cupsourcetype != null && cupsourcetype.equals(BillTypeConst.SALE_ORDER) && voCur.getHeadVO().getBsocooptome() != null
		            && voCur.getHeadVO().getBsocooptome().booleanValue())){
		    //外系统过来时，需要关联合同，其他修改时不需要关联合同。(从合同过来、到货单(补货参照退货单))时也不用关联（已经存在关联关系）
		  getPoCardPanel().setRelated_Cnt(0, getPoCardPanel().getRowCount()-1, false, false);
		}

	}

	private boolean isFromECBill() {
		//模板为空、表体没有数据，返回false
		if (getPoCardPanel() == null || getPoCardPanel().getBillModel() == null)
			return false;
		if (getPoCardPanel().getBillModel().getRowCount() < 0)
			return false;
		
		//根据表体中数据第一行的单据类型：电子商务单据类型
		String vecbilltype = (String) getPoCardPanel().getBillModel().getValueAt(0, "vecbilltype");
		if (StringUtils.isEmpty(vecbilltype))
			return false;
		if (BillTypeConst.EC_PUSHORDERBILL.equalsIgnoreCase(vecbilltype.trim()))
			return true;
		return false;
	}
	
	/**
	 * 作者：晁志平 功能：设置单据卡片右键菜单行操作与按钮组“行操作”权限相同 参数：无 返回：无 例外：无 日期：(2004-11-26
	 * 10:06:21) 修改日期，修改人，修改原因，注释标志：
	 */

	private void setPopMenuBtnsEnable() {
		// 没有分配行操作权限
		UIMenuItem[] items = getPoCardPanel().getMiReSortRowNos();
		UIMenuItem[] itemsCardEdit = getPoCardPanel().getCardEditMenu();
		if (getBtnManager().btnBillRowOperation == null
				|| getBtnManager().btnBillRowOperation.getChildCount() == 0) {
			getPoCardPanel().getBodyPanel().getMiAddLine().setEnabled(false);
			getPoCardPanel().getBodyPanel().getMiCopyLine().setEnabled(false);
			getPoCardPanel().getBodyPanel().getMiDelLine().setEnabled(false);
			getPoCardPanel().getBodyPanel().getMiInsertLine().setEnabled(false);
			getPoCardPanel().getBodyPanel().getMiPasteLine().setEnabled(false);
			getPoCardPanel().getBodyPanel().getMiPasteLineToTail().setEnabled(
					false);
			if (items != null && items.length > 0) {
				for (int i = 0; i < items.length; i++) {
					if (items[i] == null) {
						continue;
					}
					items[i].setEnabled(false);
				}
			}
			if (itemsCardEdit != null && itemsCardEdit.length > 0) {
				for (int i = 0; i < itemsCardEdit.length; i++) {
					if (itemsCardEdit[i] == null) {
						continue;
					}
					itemsCardEdit[i].setEnabled(false);
				}
			}
		}
		// 分配行操作权限
		else {
			getPoCardPanel().getBodyPanel().getMiAddLine().setEnabled(
					isPowerBtn(getBtnManager().btnBillAddRow.getCode()));
			getPoCardPanel().getBodyPanel().getMiCopyLine().setEnabled(
					isPowerBtn(getBtnManager().btnBillCopyRow.getCode()));
			getPoCardPanel().getBodyPanel().getMiDelLine().setEnabled(
					isPowerBtn(getBtnManager().btnBillDelRow.getCode()));
			getPoCardPanel().getBodyPanel().getMiInsertLine().setEnabled(
					isPowerBtn(getBtnManager().btnBillInsertRow.getCode()));
			getPoCardPanel().getBodyPanel().getMiPasteLine().setEnabled(
					isPowerBtn(getBtnManager().btnBillPasteRow.getCode()));
			// 粘贴到行尾与粘贴可用性逻辑相同
			getPoCardPanel().getBodyPanel().getMiPasteLineToTail().setEnabled(
					isPowerBtn(getBtnManager().btnBillPasteRowTail.getCode()));
			// since v55, 与行操作的重排行号权限相同
			if (items != null && items.length > 0) {
				for (int i = 0; i < items.length; i++) {
					if (items[i] == null) {
						continue;
					}
					items[i]
							.setEnabled(isPowerBtn(getBtnManager().btnBillReSortRowNo
									.getCode()));
				}
			}
			if (itemsCardEdit != null && itemsCardEdit.length > 0) {
				for (int i = 0; i < itemsCardEdit.length; i++) {
					if (itemsCardEdit[i] == null) {
						continue;
					}
					itemsCardEdit[i]
							.setEnabled(isPowerBtn(getBtnManager().btnCardEdit
									.getCode()));
				}
			}
		}
	}

	/**
	 * 作者：李亮 功能：增加一张采购订单 参数：无 返回：无 例外：无 日期：(2002-3-13 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志： 2002-05-27 王印芬 先清除界面数据，再设置按钮状态，否则会导致按钮状态不正确
	 */
	private void onBillNew() {
		m_renderYellowAlarmLine.setRight(false);
		bAddNew = false;
		// 设置操作状态变量
		setCurOperState(STATE_BILL_EDIT);
		// 设置界面数据
		getPoCardPanel().onActionNew(getCurBizeType());
		getPoCardPanel().setSoZ(false);
		// 清空当前界面单据状态标志
		// V5 Del : setImageType(IMAGE_NULL);
		// 设置按钮
		setButtonsStateEdit();
		// 全键盘
		getPoCardPanel().transferFocusTo(BillCardPanel.HEAD);
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000030")/* @res "正在修改" */);

	}

	/**
	 * 作者：李亮 功能：显示下一张订单 参数：无 返回：无 例外：无 日期：(2001-5-13 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志： 2002-06-25 wyf
	 * 精简代码，相同代码称到onButtonClicked中及setButtonState中 2003-02-21 wyf 加入记录前一VO位置的代码
	 */
	private void onBillNext() {
		getBufferVOManager().setPreviousVOPos(getBufferVOManager().getVOPos());
		getBufferVOManager().setVOPos(getBufferVOManager().getVOPos() + 1);

		displayCurVOEntirelyReset(true);
		setButtonsStateBrowse();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000028")/* @res "成功显示下一页" */);
	}

	/*
	 * 重排行号
	 */
	private void onBillReSortRowNo() {
		PuTool.resortRowNo(getPoCardPanel(), ScmConst.PO_Order, "crowno");
	}

	/**
	 * 作者：李亮 功能：行粘贴 参数：无 返回：无 例外：无 日期：(2001-5-13 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 * 2002-09-20 wyf 恢复对（上层）来源ID及来源行ID的复制 2002-11-25 wyf 对TS赋空 2003-02-27 wyf
	 * 修改为：对所有复制的行进行项目清空及编辑性控制
	 */
	private void onBillPasteLine() {

		showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000050")/* @res "粘贴订单行" */);
		// 粘贴行
		getPoCardPanel().onActionPasteLine();
		// 按钮
		setButtonsStateEdit();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH040")/* @res "粘贴行成功" */);
	}

	/*
	 * since v55, 增加按钮的粘贴行到表尾功能
	 */
	private void onBillPasteLineTail() {

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"SCMCOMMON000000266")/* @res "粘贴行到表尾" */);
		// 粘贴行
		getPoCardPanel().onActionPasteLineToTail();
		// 按钮
		setButtonsStateEdit();
		//
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH040")/* @res "粘贴行成功" */);
	}

	/**
	 * 作者：李亮 功能：应付帐款查询 参数：无 返回：无 例外：无 日期：(2001-4-8 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillPayExecStat() {

		// ================显示相应明细表
		PayExecStatDlg dlgPay = new PayExecStatDlg(this, getOrderViewVOAt(
				getBufferVOManager().getVOPos()).getHeadVO().getPk_corp(),
				getOrderViewVOAt(getBufferVOManager().getVOPos()).getHeadVO()
						.getCorderid());
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000044")/* @res "应付帐款查询完成" */);
	}

	/*
   * 预付款功能 
	 */
	private void onBillPrePay() {
		HashMap hBillbid = new HashMap();
		OrderVO voOrder = getOrderDataVOAt(getBufferVOManager().getVOPos());
		
    if(voOrder == null){
      return;
    }
    
		OrderVO voOrderCloned = null;
		try{
			voOrderCloned= (OrderVO)ObjectUtils.serializableClone(voOrder);
		}catch(Exception e){
			 SCMEnv.out(e);
		}
		IOrder iorder = (IOrder)nc.bs.framework.common.NCLocator.getInstance().lookup(IOrder.class.getName());
		try{
			voOrderCloned = iorder.queryOrderVOByKey(voOrderCloned.getHeadVO().getCorderid());
		}catch(Exception e){
			showErrorMessage(e.getMessage());
		}
		if (voOrder.getHeadVO().getForderstatus() != 3) {
			MessageDialog.showHintDlg(this,
					NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000270")/* @res "提示" */,
					NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-V56007")/* @res "只有审批的订单才能付款" */);
			return;
		}
		Vector vecItems = new Vector();
		for (int i = 0; i < voOrderCloned.getBodyLen(); i++) {
			if (voOrderCloned.getBodyVO()[i].getIisactive() != 0) {
				hBillbid.put(voOrderCloned.getBodyVO()[i].getCorder_bid(), "");
				showHintMessage(NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-V56008",null,new String[]{String.valueOf(i + 1)})/* @res "订单预付款为整单付款,订单表体行{0}已经关闭" */);
				return;
			}else{
				vecItems.add(voOrderCloned.getBodyVO()[i]);
			}
		}
		if(vecItems.size()>0){
			OrderItemVO[] vos = new OrderItemVO[vecItems.size()];
			vecItems.copyInto(vos);
			voOrderCloned.setChildrenVO(vos);
		}else{
			MessageDialog.showHintDlg(this,
					NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000270")/* @res "提示" */,
					NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-V56009")/* @res "订单不能预付" */);
			return;
		}
		// 根据单据界面的时间戳去库里匹配是否并发

		DJZBVO[] voD3s = null;
		try {
			voD3s = PoAssistOperHelper.querybyOutId(voOrderCloned.getHeadVO().getPrimaryKey());
		} catch (Exception e) {
			PuTool.outException(this, e);
			return;
		}
		// 订单整单金额
		UFDouble dTotalMny = new UFDouble(0);
    // 订单限制金额
		UFDouble dPermit = new UFDouble(0);
		UFDouble dTempTotal = new UFDouble(0);
		for (int i = 0; i < voOrderCloned.getBodyVO().length; i++) {
			if(voOrderCloned.getBodyVO()[i].getBlargess()==null||!voOrderCloned.getBodyVO()[i].getBlargess().booleanValue()){
			dTotalMny = dTotalMny.add(voOrderCloned.getBodyVO()[i]
					.getNoriginaltaxpricemny());
			}
		}
		if(dTotalMny.compareTo(new UFDouble(0)) == 0) {
			MessageDialog.showHintDlg(this,
					NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000270")/* @res "提示" */,
					NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-V56009")/* @res "订单不能预付" */);
			return;
		}
		dTempTotal = dTotalMny;
		if (voD3s == null || voD3s.length == 0) {
			dPermit = dTotalMny;
		} else {
			for (int i = 0; i < voD3s.length; i++) {
				for (int j = 0; j < voD3s[i].getChildrenVO().length; j++) {
					if(!hBillbid.containsKey(((DJZBItemVO) voD3s[i].getChildrenVO()[j]).getDdhh()))
					// 订单限制金额
					dTotalMny = dTotalMny.sub(((DJZBItemVO) voD3s[i].getChildrenVO()[j]).getJfybje());
				}
			}
			dPermit = dTotalMny;
			dPermit = dPermit.setScale(PuTool.findArapCurrtypeVOByPK(voOrderCloned.getBodyVO()[0].getCcurrencytypeid()), UFDouble.ROUND_HALF_UP);
		}
		if (dPermit.compareTo(new UFDouble(0)) <= 0) {
			MessageDialog.showHintDlg(this,
					NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000270")/* @res "提示" */,
					NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-V56010")/* @res "该订单已经全额预付款" */);
			return;
		}
		int iFinaDigit = 2;
		for (int i = 0; i < voOrderCloned.getBodyVO().length; i++) {
			int iDigit = getPoCardPanel().getPoPubSetUi2().getSCurrDecimal(voOrderCloned.getBodyVO()[i].getCcurrencytypeid());
			if (iDigit > iFinaDigit) {
				iFinaDigit = iDigit;
			}
		}
    // 弹出付款金额和比率确认对话框
		PrePayDlg prePayDlg = new PrePayDlg(this, NCLangRes.getInstance()
        .getStrByID("4004020201","UPP4004020201-001158")/*
         * @res
         *""付款金额和比率"*/, dTempTotal,dPermit, iFinaDigit);
		prePayDlg.showModal();
		if (UIDialog.ID_OK != prePayDlg.getResult()) {
		  return;
    }
    // 用户输入的比率
    String strRate = prePayDlg.field1.getText();
    // 用户输入的金额
    String strMny = prePayDlg.field2.getText();
    UFDouble dRate = null;
    UFDouble dMny = new UFDouble(strMny);
    UFDouble dRowTatol = new UFDouble(0);
    if (strRate == null || strRate.equals("")) {
      dRate = new UFDouble(strMny).div(dTotalMny);
    } else {
      dRate = new UFDouble(strRate).div(new UFDouble(100));
    }
    Vector vecItem = new Vector();
    // 组织订单传给预付款单的数据
    if (voOrderCloned.getBodyVO().length == 1) {
    	int iDigit = getPoCardPanel()
        .getPoPubSetUi2()
        .getSCurrDecimal(
            voOrderCloned.getBodyVO()[0].getCcurrencytypeid());
      // 订单表体只有一条数据，将用户输入的整单金额置入即可
      voOrderCloned.getBodyVO()[0].setNoriginaltaxpricemny(dMny);
      //voOrderCloned.getBodyVO()[0].setNtaxpricemny(dMny);
      voOrderCloned.getBodyVO()[0].setNoriginaltaxmny(voOrderCloned.getBodyVO()[0].getNoriginaltaxmny().multiply(dRate).setScale(iDigit,UFDouble.ROUND_HALF_UP));
      //voOrderCloned.getBodyVO()[0].setNtaxmny(voOrderCloned.getBodyVO()[0].getNoriginaltaxmny());
      voOrderCloned.getBodyVO()[0].setNoriginalcurmny(
          voOrderCloned.getBodyVO()[0].getNoriginaltaxpricemny().sub(voOrderCloned.getBodyVO()[0].getNoriginaltaxmny()).setScale(iDigit,UFDouble.ROUND_HALF_UP));
      //voOrderCloned.getBodyVO()[0].setNmoney(voOrderCloned.getBodyVO()[0].getNoriginalcurmny());
      voOrderCloned.getBodyVO()[0].setNordernum(null);
      voOrderCloned.getBodyVO()[0].setNorgnettaxprice(null);
      voOrderCloned.getBodyVO()[0].setNorgtaxprice(null);
      voOrderCloned.getBodyVO()[0].setNoriginalcurprice(null);
      voOrderCloned.getBodyVO()[0].setNoriginalnetprice(null);
      vecItem.add(voOrderCloned.getBodyVO()[0]);
    } else {
      // 订单表体有多行数据，需要根据比率和整单金额作尾差处理.处理算法如下
      for (int i = 0; i < voOrderCloned.getBodyVO().length; i++) {
        int iDigit = getPoCardPanel()
            .getPoPubSetUi2()
            .getSCurrDecimal(
                voOrderCloned.getBodyVO()[i].getCcurrencytypeid());
        if(voOrderCloned.getBodyVO()[i].getBlargess()==null||!voOrderCloned.getBodyVO()[i].getBlargess().booleanValue()){
        if (i != voOrderCloned.getBodyVO().length - 1) {
          dRowTatol = dRowTatol.add(voOrderCloned.getBodyVO()[i].getNoriginaltaxpricemny().multiply(dRate).setScale(iDigit,UFDouble.ROUND_HALF_UP));
          // 原币价税合计
          voOrderCloned.getBodyVO()[i].setNoriginaltaxpricemny(
              voOrderCloned.getBodyVO()[i].getNoriginaltaxpricemny().multiply(dRate).setScale(iDigit,UFDouble.ROUND_HALF_UP));
          // 本币价税合计
          //voOrderCloned.getBodyVO()[i].setNtaxpricemny(voOrderCloned.getBodyVO()[i].getNoriginaltaxpricemny().setScale(iDigit, UFDouble.ROUND_HALF_UP));
          // 原币税额
          voOrderCloned.getBodyVO()[i].setNoriginaltaxmny(
              voOrderCloned.getBodyVO()[i].getNoriginaltaxmny().multiply(dRate).setScale(iDigit,UFDouble.ROUND_HALF_UP));
          // 本币税额
          //voOrderCloned.getBodyVO()[i].setNtaxmny(voOrderCloned.getBodyVO()[i].getNtaxmny().setScale(iDigit,UFDouble.ROUND_HALF_UP));
          // 原币金额
          voOrderCloned.getBodyVO()[i].setNoriginalcurmny(
              voOrderCloned.getBodyVO()[i].getNoriginaltaxpricemny().sub(
                  voOrderCloned.getBodyVO()[i].getNoriginaltaxmny()).setScale(iDigit, UFDouble.ROUND_HALF_UP));
          // 本币金额
          //voOrderCloned.getBodyVO()[i].setNmoney(voOrderCloned.getBodyVO()[i].getNmoney().setScale(iDigit,UFDouble.ROUND_HALF_UP));
          //
          voOrderCloned.getBodyVO()[i].setNordernum(null);
          voOrderCloned.getBodyVO()[i].setNorgnettaxprice(null);
          voOrderCloned.getBodyVO()[i].setNorgtaxprice(null);
          voOrderCloned.getBodyVO()[i].setNoriginalcurprice(null);
          voOrderCloned.getBodyVO()[i].setNoriginalnetprice(null);
        } else {
          // 原币价税合计
          voOrderCloned.getBodyVO()[i].setNoriginaltaxpricemny(dMny.sub(dRowTatol).setScale(iDigit,UFDouble.ROUND_HALF_UP));
          // 本币价税合计
          //voOrderCloned.getBodyVO()[i].setNtaxpricemny(voOrderCloned.getBodyVO()[i].getNoriginaltaxpricemny().setScale(iDigit, UFDouble.ROUND_HALF_UP));
          // 原币税额
          voOrderCloned.getBodyVO()[i].setNoriginaltaxmny(voOrderCloned.getBodyVO()[i].getNoriginaltaxmny().multiply(dRate).setScale(iDigit,UFDouble.ROUND_HALF_UP));
          // 本币税额
          //voOrderCloned.getBodyVO()[i].setNtaxmny(voOrderCloned.getBodyVO()[i].getNoriginaltaxmny().setScale(iDigit,UFDouble.ROUND_HALF_UP));
          // 原币金额
          voOrderCloned.getBodyVO()[i].setNoriginalcurmny(voOrderCloned.getBodyVO()[i].getNoriginaltaxpricemny().sub(
                  voOrderCloned.getBodyVO()[i].getNoriginaltaxmny()).setScale(iDigit, UFDouble.ROUND_HALF_UP));
          // 本币金额
          //voOrderCloned.getBodyVO()[i].setNmoney(voOrderCloned.getBodyVO()[i].getNoriginalcurmny().setScale(iDigit,UFDouble.ROUND_HALF_UP));
          //
          voOrderCloned.getBodyVO()[i].setNordernum(null);
          voOrderCloned.getBodyVO()[i].setNorgnettaxprice(null);
          voOrderCloned.getBodyVO()[i].setNorgtaxprice(null);
          voOrderCloned.getBodyVO()[i].setNoriginalcurprice(null);
          voOrderCloned.getBodyVO()[i].setNoriginalnetprice(null);
        }
        vecItem.add(voOrderCloned.getBodyVO()[i]);
        }
      }
    }
    if(voOrderCloned.getBodyLen()!=vecItem.size()){
    	OrderItemVO[] items = new OrderItemVO[vecItem.size()];
    	vecItem.copyInto(items);
    	voOrderCloned.setChildrenVO(items);
    }
    
    //arap提供接口，寻找生成的arap单据类型
    String sTargetType = "D3";
    IArapBillMapQureyPublic bo = (IArapBillMapQureyPublic) NCLocator.getInstance().lookup(IArapBillMapQureyPublic.class.getName());
    
    /* 此处选择硬代码修改订单的业务流程来实现向收付传递目的业务流程
     * 
     * 不支持通过流程平台定义21->D3目的业务流程，因为这样做会有两个问题：
     *  1)、与收付自己的定义重复
     *  2)、如果收付调整了单据类型，不是D3了，流程平台还是要重新定义一个新的目的业务流程
     *  
     * 也就是说虽然流程平台可以定义21->D3的目的流程，但是DESTBUSITYPE!=null的分支也是走不到的(因为此处的硬代码已经调整了业务流程)，
     * 即"H_xslxbm->iif(DESTBUSITYPE==null,H_cbiztype,DESTBUSITYPE)实际上等同于 H_xslxbm->H_cbiztype
     */
    String sTargetBusiType = null;
    try {
      BillTypeMapVO list = bo.queryBillMap(BillTypeConst.PO_ORDER, "fk", getCorpPrimaryKey());
      if (list != null){
    	  if( list.getTargetbilltype() != null) {
    		  sTargetType = list.getTargetbilltype();
      	  } 
    	  if( list.getBusitype() != null) {
    		  sTargetBusiType = list.getBusitype();
      	  }
      }
      if(sTargetBusiType != null){
    	  voOrderCloned.getHeadVO().setCbiztype(sTargetBusiType);
      }
    } catch (Exception e) {
      PuTool.outException(this, e);
    }
    
    //计算本币信息{本币金额、本币税额、本币价税合计}
    PoChangeUI.setVONativeAndAssistCurrValue(voOrderCloned);
    //
    DJZBVO vos = null;
    try {
      vos = (DJZBVO) PfChangeBO_Client.pfChangeBillToBill(voOrderCloned, BillTypeConst.PO_ORDER, sTargetType);
    } catch (Exception e) {
      ExcpTool.reportException(this, e);
      return;
    }
    PfLinkData linkData = new PfLinkData();
    linkData.setUserObject(new Object[] { new DJZBVO[] { vos } });
    SFClientUtil.openLinkedADDDialog(nc.ui.pf.pub.PfUIDataCache.getBillType(sTargetType).getNodecode(), this, linkData);
    //
		showHintMessage(NCLangRes.getInstance().getStrByID("common","4004COMMON000000045")/* @res "预付款处理完成" */);
	}

	/**
	 * 作者：李亮 功能：显示上一张采购订单 参数：无 返回：无 例外：无 日期：(2001-5-13 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志： 2002-06-25 wyf
	 * 精简代码，相同代码称到onButtonClicked中及setButtonState中 2003-02-21 wyf 加入记录前一VO位置的代码
	 */
	private void onBillPrevious() {

		getBufferVOManager().setPreviousVOPos(getBufferVOManager().getVOPos());
		getBufferVOManager().setVOPos(getBufferVOManager().getVOPos() - 1);

		displayCurVOEntirelyReset(true);
		setButtonsStateBrowse();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000027")/* @res "成功显示上一页" */);
	}

	/**
	 * 作者：李亮 功能：模板打印 参数：无 返回：无 例外：无 日期：(2001-5-13 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillPrint() {
		ArrayList listVo = new ArrayList();
		listVo.add(getOrderDataVOAt(getBufferVOManager().getVOPos()));

		try {
			// 目前还有深圳天音保留到货计划合并打印功能、无锡大湖、德美化工不想补空行
			if (nc.vo.scm.pub.CustomerConfigVO.getCustomerName()
					.equalsIgnoreCase(
							nc.vo.scm.pub.CustomerConfigVO.NAME_TIANYIN)
					|| nc.vo.scm.pub.CustomerConfigVO
							.getCustomerName()
							.equalsIgnoreCase(
									nc.vo.scm.pub.CustomerConfigVO.NAME_WUXITAIHU)
					|| nc.vo.scm.pub.CustomerConfigVO
							.getCustomerName()
							.equalsIgnoreCase(
									nc.vo.scm.pub.CustomerConfigVO.NAME_NANJINGPUZHEN)
					|| nc.vo.scm.pub.CustomerConfigVO
							.getCustomerName()
							.equalsIgnoreCase(
									nc.vo.scm.pub.CustomerConfigVO.NAME_DEMEIHUAGONG)
					|| nc.vo.scm.pub.CustomerConfigVO
							.getCustomerName()
							.equalsIgnoreCase(
									nc.vo.scm.pub.CustomerConfigVO.NAME_JINLIUFU)
									|| nc.vo.scm.pub.CustomerConfigVO
									.getCustomerName()
									.equalsIgnoreCase(
											nc.vo.scm.pub.CustomerConfigVO.NAME_FSBIGUIYUAN)) {
				OrderPrintData printData = new OrderPrintData();
				printData.setBillCardPanel(getPoCardPanel());
				m_printList = new ScmPrintTool(getPoCardPanel(), printData,
						listVo, getModuleCode());
			} else {
				m_printList = new ScmPrintTool(this, getPoCardPanel(), listVo,
						getModuleCode());
			}
		} catch (Exception e) {
			SCMEnv.out(e);
		}
		try {
			if (listVo.size() > 1)
				m_printList.onBatchPrint(getPoListPanel(), getPoCardPanel(),
						BillTypeConst.PO_ORDER);
			else
				m_printList.onCardPrint(getPoCardPanel(), getPoListPanel(),
						BillTypeConst.PO_ORDER);
//			if (PuPubVO.getString_TrimZeroLenAsNull(m_printList
//					.getPrintMessage()) != null) {
//				MessageDialog.showHintDlg(this, NCLangRes
//						.getInstance().getStrByID("SCMCOMMON",
//								"UPPSCMCommon-000270")/* @res "提示" */,
//						m_printList.getPrintMessage());
//			}
		} catch (Exception e) {
			MessageDialog.showErrorDlg(this,
					NCLangRes.getInstance().getStrByID("4004020201",
							"UPP4004020201-000051")/* @res "报错" */, e
							.getMessage());
		}
	}

	/**
	 * 作者：李亮 功能：模板打印 参数：无 返回：无 例外：无 日期：(2001-5-13 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillPrintPreview() {
		ArrayList listVo = new ArrayList();
		listVo.add(getOrderDataVOAt(getBufferVOManager().getVOPos()));

		try {
			// 目前还有深圳天音保留到货计划合并打印功能、无锡大湖、南京蒲镇、北京金六福不想补空行
			/*if (nc.vo.scm.pub.CustomerConfigVO.getCustomerName()
					.equalsIgnoreCase(
							nc.vo.scm.pub.CustomerConfigVO.NAME_TIANYIN)
					|| nc.vo.scm.pub.CustomerConfigVO
							.getCustomerName()
							.equalsIgnoreCase(
									nc.vo.scm.pub.CustomerConfigVO.NAME_WUXITAIHU)
					|| nc.vo.scm.pub.CustomerConfigVO
							.getCustomerName()
							.equalsIgnoreCase(
									nc.vo.scm.pub.CustomerConfigVO.NAME_NANJINGPUZHEN)
					|| nc.vo.scm.pub.CustomerConfigVO
							.getCustomerName()
							.equalsIgnoreCase(
									nc.vo.scm.pub.CustomerConfigVO.NAME_DEMEIHUAGONG)
					|| nc.vo.scm.pub.CustomerConfigVO
							.getCustomerName()
							.equalsIgnoreCase(
									nc.vo.scm.pub.CustomerConfigVO.NAME_JINLIUFU)
									|| nc.vo.scm.pub.CustomerConfigVO
									.getCustomerName()
									.equalsIgnoreCase(
											nc.vo.scm.pub.CustomerConfigVO.NAME_FSBIGUIYUAN)) {*/
				OrderPrintData printData = new OrderPrintData();
				printData.setBillCardPanel(getPoCardPanel());
				m_printList = new ScmPrintTool(getPoCardPanel(), printData,
						listVo, getModuleCode());
			/*} else {
				m_printList = new ScmPrintTool(this, getPoCardPanel(), listVo,
						getModuleCode());
			}*/
		} catch (Exception e) {
			SCMEnv.out(e);
		}
		try {
			m_printList.onCardPrintPreview(getPoCardPanel(), getPoListPanel(),
					BillTypeConst.PO_ORDER);
//			if (PuPubVO.getString_TrimZeroLenAsNull(m_printList
//					.getPrintMessage()) != null) {
//				MessageDialog.showHintDlg(this, NCLangRes
//						.getInstance().getStrByID("SCMCOMMON",
//								"UPPSCMCommon-000270")/* @res "提示" */,
//						m_printList.getPrintMessage());
//			}
		} catch (Exception e) {
			MessageDialog.showErrorDlg(this,
					NCLangRes.getInstance().getStrByID("4004020201",
							"UPP4004020201-000051")/* @res "报错" */, e
							.getMessage());
		}
//		getPoCardPanel().displayCurVO(((OrderVO)listVo.get(0)),true);
	}

	/**
	 * 作者：李亮 功能：订单列表界面中查询 参数：无 返回：无 例外：无 日期：(2001-4-18 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志： 2002-05-22 王印芬 加入是否曾查询过的标志，以确定刷新按钮是否可用
	 */
	private void onBillQuery() {
		m_renderYellowAlarmLine.setRight(false);
		
		getPoQueryCondition().showModal();

		if (getPoQueryCondition().isCloseOK()) {

			onBillRefresh();
		}

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH009")/* @res "查询完成" */);
	}

	/**
	 * 作者：李亮 功能：订单界面中刷新 参数：无 返回：无 例外：无 日期：(2001-4-18 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志： 2003-03-04 wyf 对自由等状态一并写入到m_strWhere中
	 */
	private void onBillRefresh() {
		setBillList(false);
		// 获取数据
		queryOrderVOsFromDB();
		setIsFromOtherBill(false);
//		if(getBufferLength()==1||getBufferLength()==0){
			getBufferVOManager().setVOPos(0);
			displayCurVOEntirelyReset(true);
			setButtonsStateBrowse();
//		}else{
//			onBillList();
//		}

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH007")/* @res "刷新成功" */);
	}

	/**
	 * 作者：WYF 功能：显示到货计划界面 参数：无 返回：无 例外：无 日期：(2004-02-23 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 2004-11-01，晁志平，V31新增需示：业务类型未安排到货计划的订单的〖到货计划〗按钮不可使用
	 */
	private void onBillReveivePlan() {

		OrderVO vo = getOrderDataVOAt(getBufferVOManager().getVOPos());
		try {
			UFBoolean ufbBusiRp = OrderHelper.isBusiRp(vo.getHeadVO()
					.getCbiztype(), vo.getHeadVO().getPk_corp());
			if (!ufbBusiRp.booleanValue()) {
				MessageDialog.showErrorDlg(this, NCLangRes
						.getInstance().getStrByID("4004020201",
								"UPP4004020201-000052")/*
														 * @res "是否进行到货计划安排"
														 */, NCLangRes.getInstance().getStrByID("4004020201",
						"UPP4004020201-000053")/*
												 * @res "当前业务类型“不进行到货计划安排”"
												 */);
				return;
			}
		} catch (Exception e) {
			SCMEnv.out("判断当前业务类型“是否进行到货计划安排”时出错!直接返回");/*-=notranslate=-*/
			return;
		}
		if (m_ufbIsDmEnabled == null) {
			try {
				boolean isEnabled = CreateCorpQuery_Client.isEnabled(getCorpPrimaryKey(),
						ScmConst.m_sModuleCodeDM);
				m_ufbIsDmEnabled = new UFBoolean(isEnabled);
			} catch (Exception e) {
				SCMEnv.out("判断供应链发运产品是否启用时出现异常，生成发运日计划功能不能使用");/*-=notranslate=-*/
			}
		}
		PoReceivePlanUI pnlRP = new PoReceivePlanUI(vo);
		pnlRP.display(this, m_ufbIsDmEnabled);

		// 重新对订单VO取值
		if (pnlRP.isOrderVOChanged()) {
			if (pnlRP.getOrderVO() == null) {
				getBufferVOManager().removeAt(getBufferVOManager().getVOPos());
			} else {
				getBufferVOManager().setVOAt(getBufferVOManager().getVOPos(),
						pnlRP.getOrderVO());
			}
			displayCurVOEntirelyReset(true);
			setButtonsStateBrowse();
		}
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000046")/* @res "到货计划处理完成" */);
	}

	/**
	 * 作者：周晓 功能：供应商条码导入 参数：无 返回：无 例外：无 日期：(2004-02-23 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 */
	private void onBillProviderBarcodeQuery() {

		OrderVO vo = getOrderDataVOAt(getBufferVOManager().getVOPos());
		try {
			RefMsg msg = new RefMsg(this);
			msg.setIBillOperate(2);
			msg.setBillVos(new AggregatedValueObject[] { vo });
			nc.ui.ic.ic009.ClientUI.openNodeAsDlg(this, msg);

		} catch (Exception e) {
			SCMEnv.out("判断当前业务类型“是否进行到货计划安排”时出错!直接返回");/*-=notranslate=-*/
			return;
		}
	}

	/**
	 * 作者：WYF 功能：销量查询 参数：无 返回：无 例外：无 日期：(2004-04-14 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillSaleNum() {

		if (getPoCardPanel().getRowCount() == 0) {
			return;
		}

		int iRow = getPoCardPanel().getBillTable().getSelectedRow();
		if (iRow == -1) {
			MessageDialog.showHintDlg(this,
					NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000270")/* @res "提示" */,
					NCLangRes.getInstance().getStrByID("4004020201",
							"UPP4004020201-000049")/* @res "请选中有存货的行" */);
			return;
		}
		iRow = PuTool.getIndexBeforeSort(getPoCardPanel(), iRow);

		String sMangId = (String) getPoCardPanel().getBodyValueAt(iRow,
				"cmangid");
		if (PuPubVO.getString_TrimZeroLenAsNull(sMangId) == null) {
			MessageDialog.showHintDlg(this,
					NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000270")/* @res "提示" */,
					NCLangRes.getInstance().getStrByID("4004020201",
							"UPP4004020201-000049")/* @res "请选中有存货的行" */);
			return;
		}

		AvgSaleQueryVO voSaleQuery = new AvgSaleQueryVO();
		voSaleQuery.setDqueryDate(PoPublicUIClass.getLoginDate());
		voSaleQuery.setCinvmandocid(sMangId);
		voSaleQuery.setCinvbasdocid((String) getPoCardPanel().getBodyValueAt(
				iRow, "cbaseid"));
		voSaleQuery.setCunitid((String) getPoCardPanel().getBodyValueAt(iRow,
				"cmessureunit"));
		voSaleQuery.setCunitname((String) getPoCardPanel().getBodyValueAt(iRow,
				"cmessureunitname"));
		voSaleQuery.setCinvcode((String) getPoCardPanel().getBodyValueAt(iRow,
				"cinventorycode"));
		voSaleQuery.setCinvname((String) getPoCardPanel().getBodyValueAt(iRow,
				"cinventoryname"));
		voSaleQuery.setCinvspec((String) getPoCardPanel().getBodyValueAt(iRow,
				"cspecification"));
		voSaleQuery.setCinvtype((String) getPoCardPanel().getBodyValueAt(iRow,
				"ctype"));

		getDlgSaleNum().initData(new AvgSaleQueryVO[] { voSaleQuery });
		getDlgSaleNum().showModal();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000047")/* @res "销量查询完成" */);
	}

	/**
	 * 审批中修订保存
	 */
	private boolean onBillReviseSave() {
		// 得到保存的VO：只对显示的VO进行保存
		OrderVO voSaved = getPoCardPanel().getSaveVO(
				getOrderDataVOAt(getBufferVOManager().getVOPos()));
		if (voSaved == null) {
			// 未被修改
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020601", "UPP4004020601-000073")/*
															 * @res
															 * ""未修改任何項目，保存操作無效""
															 */);
			return false;
		}

		// 新增的历史VO
		OrderVO voHistory = getOrderDataVOAt(getBufferVOManager().getVOPos())
				.getHistoryVO(PoPublicUIClass.getLoginUser());
		// 设置修订人和修订时间
		voHistory.getHeadVO().setCrevisepsn(
				nc.ui.pub.ClientEnvironment.getInstance().getUser()
						.getPrimaryKey());
		voHistory.getHeadVO().setTrevisiontime(
				new UFDateTime(System.currentTimeMillis()));
		// -------保存
		boolean bContinue = true;
		while (bContinue) {
			try {
				PfUtilClient.processActionNoSendMessage(this, "REVISE",
						nc.vo.scm.pu.BillTypeConst.PO_ORDER,
						getClientEnvironment().getDate().toString(), voSaved,
						voHistory, null, null);
				bContinue = false;
			} catch (Exception e) {
				setCurOperState(STATE_BILL_EDIT);
				if (e instanceof RwtPoToPrException) {
					// 请购提示
					int iRet = showYesNoMessage(e.getMessage());
					if (iRet == MessageDialog.ID_YES) {
						// 继续循环
						bContinue = true;
						// 是否第一次
						voSaved.setFirstTime(false);
					} else {
						showHintMessage(NCLangRes.getInstance()
								.getStrByID("4004020201",
										"UPP4004020201-000041")/* @res "编辑订单" */);
						return false;
					}
				} else if (e instanceof nc.vo.scm.pub.SaveHintException) {
					// 合同提示
					int iRet = showYesNoMessage(e.getMessage());
					if (iRet == MessageDialog.ID_YES) {
						// 继续循环
						bContinue = true;
						// 是否第一次
						voSaved.setFirstTime(false);
					} else {
						showHintMessage(NCLangRes.getInstance()
								.getStrByID("4004020201",
										"UPP4004020201-000041")/* @res "编辑订单" */);
						return false;
					}
				} else if (e instanceof nc.vo.scm.pub.StockPresentException) {
					// 超现存量提示
					int iRet = showYesNoMessage(e.getMessage());
					if (iRet == MessageDialog.ID_YES) {
						// 继续循环
						bContinue = true;
						// 是否第一次
						voSaved.setFirstTimeSP(false);
					} else {
						showHintMessage(NCLangRes.getInstance()
								.getStrByID("4004020201",
										"UPP4004020201-000041")/* @res "编辑订单" */);
						return false;
					}
				} else if (e instanceof PoReviseException) {
					// 有后续单据的提示
					int iRet = showYesNoMessage(e.getMessage());
					if (iRet == MessageDialog.ID_YES) {
						// 继续循环
						bContinue = true;
						// 是否第一次
						voSaved.setFirstTimeSP(false);
					} else {
						showHintMessage(NCLangRes.getInstance()
								.getStrByID("4004020201",
										"UPP4004020201-000041")/* @res "编辑订单" */);
						return false;
					}
				} else if (e instanceof BusinessException
						|| e instanceof ValidationException) {
					showHintMessage(NCLangRes.getInstance()
							.getStrByID("4004020201", "UPP4004020201-000041")/*
																				 * @res
																				 * "编辑订单"
																				 */);
					PuTool.outException(this, e);
					return false;
				} else {
					PuTool.outException(this, e);
					showHintMessage(NCLangRes.getInstance()
							.getStrByID("4004020201", "UPP4004020201-000041")/*
																				 * @res
																				 * "编辑订单"
																				 */);
					return false;
				}
			}
		}

		showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000094")/* @res "订单修订完成" */);

		String sId = getOrderDataVOAt(getBufferVOManager().getVOPos())
				.getHeadVO().getCorderid();

		// 重新查询，替换当前VO
		try {
			// ==================保存后处理 重新查询
			voSaved = OrderHelper.queryOrderVOByKey(sId);
		} catch (Exception e) {
			PuTool.outException(this, e);
			showWarningMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000095")/*
															 * @res
															 * "订单保存成功，但出现并发操作，可重新刷新查看该单据"
															 */);
		}
		getBufferVOManager().setVOAt(getBufferVOManager().getVOPos(), voSaved);

		// 显示当前VO
		displayCurVOEntirelyReset(false);

		// 设置界面与按钮状态
		getPoCardPanel().setEnabled(false);
		setCurOperState(STATE_BILL_BROWSE);
		setButtonsStateBrowse();

		updateUI();

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH005")/* @res "保存成功" */);
		/** 发消息给制单人和前面审批过的审批人 */
		String makeMan = voSaved.getHeadVO().getCoperator();
		String auditMan = voSaved.getHeadVO().getCauditpsn();
		String billid = voSaved.getHeadVO().getCorderid();
		String biztype = voSaved.getHeadVO().getCbiztype();
		String billcode = voSaved.getHeadVO().getVordercode();
		PuTool.sendMessageToMen(makeMan, ClientEnvironment.getInstance()
				.getUser().getPrimaryKey(), billid, billcode, "21");
		return true;
	}

	/**
	 * 作者：李亮 功能：订单列表界面中查询 参数：无 返回：无 例外：无 日期：(2001-4-18 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 20050328 Czp 增加返回值，用于编辑状态下送审判断
	 */
	protected boolean onBillSave() {
		// 审批中修订保存
		if (bRevise) {
			return onBillReviseSave();
			// 普通修改保存
		} else {
			nc.vo.scm.pu.Timer timeDebug = new nc.vo.scm.pu.Timer();
			timeDebug.start();
			
			// 获取VO后进行保存，并得到保存后表头、新增表体行的主键
			// 数组key第一个元素为表头主键，随后元素为表体主键
			OrderVO voSavedOrder = getSaveVO();
			if (voSavedOrder == null) {
				return false;
			}
//			//存货和资产仓关系检查
//			checkStorAndInvIsCapital(voSavedOrder);
			
			timeDebug.addExecutePhase("得到待保存VO");/*-=notranslate=-*/
			// 保存
			voSavedOrder = onSave(this, getPoCardPanel(), voSavedOrder);
			if (voSavedOrder == null) {
				return false;
			}
			timeDebug.addExecutePhase("保存");/*-=notranslate=-*/

			String sOrderId = voSavedOrder.getHeadVO().getCorderid();

			if (isFromOtherBill()&&isHaveSourceTS(getBufferVOManager().getVOAt_LoadItemNo(getBufferVOManager().getVOPos()))) {
				// 处理从请购单转入的单据可能存在的并发问题：更新TS
				if (sOrderId != null
						&& getCupsourcebilltype().equals(BillTypeConst.PO_PRAY)) {
					ArrayList listPrTs = null;
					try {
						listPrTs = OrderHelper.queryPrayHBTSInAOrder(sOrderId);
					} catch (Exception e) {
						PuTool.outException(this, e);
					}
					if (listPrTs != null) {
						String[][] saHTs = (String[][]) listPrTs.get(0);
						String[][] saBTs = (String[][]) listPrTs.get(1);
						int iHLen = saHTs.length;
						for (int i = 0; i < iHLen; i++) {
							getHtUpSourcePrayTs().put(saHTs[i][0], saHTs[i][1]);
						}
						int iBLen = saBTs.length;
						for (int i = 0; i < iBLen; i++) {
							getHtUpSourcePrayTs().put(saBTs[i][0], saBTs[i][1]);
						}
					}
				}
				timeDebug.addExecutePhase("请购单并发查询");/*-=notranslate=-*/

				// =============其他单据转入时
				// 加入到临时缓存中，从正式缓存中移走
				getBufferVOManager().removeAt(getBufferVOManager().getVOPos());
				for(int i = 0 ; i < getBufferVOManager().getVOs().length ; i ++){
					for(int j = 0 ; j < getBufferVOManager().getVOs()[i].getBodyVO().length ; j ++){
						if(getHtUpSourcePrayTs().containsKey(getBufferVOManager().getVOs()[i].getBodyVO()[j].getCupsourcebillrowid())){
							getBufferVOManager().getVOs()[i].getBodyVO()[j].setCupsourcebts((String)getHtUpSourcePrayTs().get(getBufferVOManager().getVOs()[i].getBodyVO()[j].getCupsourcebillrowid()));
							getBufferVOManager().getVOs()[i].getBodyVO()[j].setCupsourcehts((String)getHtUpSourcePrayTs().get(getBufferVOManager().getVOs()[i].getBodyVO()[j].getCupsourcebillid()));
						}
					}
				}
				if (voSavedOrder != null) {
					for(int j = 0 ; j < voSavedOrder.getBodyVO().length ; j ++){
						if(getHtUpSourcePrayTs().containsKey(voSavedOrder.getBodyVO()[j].getCupsourcebillrowid())){
							voSavedOrder.getBodyVO()[j].setCupsourcebts((String)getHtUpSourcePrayTs().get(voSavedOrder.getBodyVO()[j].getCupsourcebillrowid()));
							voSavedOrder.getBodyVO()[j].setCupsourcehts((String)getHtUpSourcePrayTs().get(voSavedOrder.getBodyVO()[j].getCupsourcebillid()));
						}
					}
					getBufferVOFrmBill().addVONoListener(voSavedOrder);
				}
				timeDebug.showAllExecutePhase("转单");/*-=notranslate=-*/

				// 缓存中还有未转完的单据
				if (getBufferLength() > 0) {
					getBufferVOManager().setVOPos(0);
					onBillList();
					setButtonsStateList();
					return true;
				} else {
					setIsFromOtherBill(false);
					// 临时缓存到正式缓存
					getBufferVOManager().resetVOs(getBufferVOFrmBill());
					// 设置位置
					getBufferVOManager().setVOPos(getBufferLength() - 1);
					// 清空所有上层的请购单来源
					setHmapUpSourcePrayTs(null);

					// 清临时缓存
					getBufferVOFrmBill().clear();
				}
			} else {
				String sOrgId = null;
				if (getBufferLength() != 0) {
					sOrgId = getOrderDataVOAt(getBufferVOManager().getVOPos())
							.getHeadVO().getCorderid();
				}
				if (voSavedOrder == null && sOrderId.equals(sOrgId)) {
					// 已存在的单据，刷新不成功，从缓存中去除
					getBufferVOManager().removeAt(
							getBufferVOManager().getVOPos());
					showHintMessage(NCLangRes.getInstance()
							.getStrByID("4004020201", "UPP4004020201-000054")/*
																				 * @res
																				 * "存在并发操作，请刷新"
																				 */);
				} else {
					if (sOrderId.equals(sOrgId)) {
						// 修改：重置
						getBufferVOManager().setVOAt(
								getBufferVOManager().getVOPos(), voSavedOrder);
					} else {
						// 增加：追加
						getBufferVOManager().addVO(voSavedOrder);
						getBufferVOManager().setVOPos(getBufferLength() - 1);
					}
				}
				timeDebug.addExecutePhase("重新设置VO");/*-=notranslate=-*/

			}

			// 显示当前订单
			displayCurVOEntirelyReset(false);
			timeDebug.addExecutePhase("重新显示");/*-=notranslate=-*/

			// 设置单据页面不可编辑
			getPoCardPanel().setEnabled(false);
			// 复置单据状态标志
			setCurOperState(STATE_BILL_BROWSE);

			// 按钮状态
			setButtonsStateBrowse();

			this.repaint();

			// 晁志平 增加重庆力帆自由项输入提示功能(订单维护、补货)
			if (!(this instanceof RevisionUI)) {
				InvAttrCellRenderer ficr = new InvAttrCellRenderer();
				ficr.setFreeItemRenderer(
						(nc.ui.pub.bill.BillCardPanel) getPoCardPanel(), null);
			}

			timeDebug.showAllExecutePhase("订单UI保存");/*-=notranslate=-*/
			// 清除追加数据缓存
			hPraybillVO.clear();
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"common", "UCH005")/* @res "保存成功" */);
			return true;
		}
	}
	private boolean isHaveSourceTS(OrderVO voaOrder){
		if(voaOrder==null){
			return false;
		}
		OrderItemVO[] vos = voaOrder.getBodyVO();
		for(OrderItemVO vo:vos){
		  // wuxla 电子商务新增：电子商务推单时，分标分量没有ts，所以是在订单保存后电子商务才会去保存分标分量，所以VO对照没有对照
			if(vo.getCupsourcebts()!=null||vo.getCecbill_bid()!=null)
				return true;
		}
		return false;
	}
	/**
	 * 作者：WYF 功能：送审当前订单 参数：无 返回：无 例外：无 日期：(2003-10-28 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 20050328 Czp V31 调整送审处理
	 */
	private void onBillSendToAudit() {

		showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000055")/* @res "送审当前订单" */);

		try {
		  //卡片显示
		  if(!(getCurOperState() == STATE_LIST_BROWSE)){
  			// 编辑状态送审＝“保存”＋“送审”
  			if (getCurOperState() == STATE_BILL_EDIT) {
  				boolean bContinue = onBillSave();
  				if (!bContinue) {
  					return;
  				}
  			}
  			OrderVO voOrder = getOrderDataVOAt(getBufferVOManager().getVOPos());
  			// 支持天音送审后置为正在审批状态需求2005-01-29
  			ArrayList userObj = new ArrayList();
  			userObj.add(PoPublicUIClass.getLoginUser());
  			userObj.add(PoPublicUIClass.getLoginDate());
  			try {
  				PfUtilClient.processAction("SAVE", BillTypeConst.PO_ORDER,
  						ClientEnvironment.getInstance().getDate().toString(),
  						voOrder, userObj);
  			} catch (Exception ex) {
  				PuTool.outException(this, ex);
  			}
  			// 天音要求
  			if (nc.vo.scm.pub.CustomerConfigVO.getCustomerName()
  					.equalsIgnoreCase(
  							nc.vo.scm.pub.CustomerConfigVO.NAME_TIANYIN)) {
  				// 重新查询单据
  				voOrder = OrderHelper.queryOrderVOByKey(voOrder.getHeadVO()
  						.getCorderid());
  				// 修改：重置
  				getBufferVOManager().setVOAt(getBufferVOManager().getVOPos(),
  						voOrder);
  				// 显示当前订单
  				displayCurVOEntirelyReset(false);
  				this.repaint();
  			} else {
  				// since v50 , 支持送审即审批情况
  
  				OrderVO[] voNewOrder = OrderHelper.queryOrderVOsLight(
  						new String[] { getOrderDataVOAt(
  								getBufferVOManager().getVOPos()).getHeadVO()
  								.getCorderid() }, "AUDIT");
  
  				// 刷新表头VO
  				getBufferVOManager().getVOAt_LoadItemNo(
  						getBufferVOManager().getVOPos()).getHeadVO()
  						.refreshByHeaderVo(voNewOrder[0].getHeadVO());
  
  				// 重新设置
  				getPoCardPanel().reloadBillCardAfterAudit(
  						voNewOrder[0],
  						getBufferVOManager().getVOAt_LoadItemNo(
  								getBufferVOManager().getVOPos()));
  			}
		  }else{
		    OrderVO[] voaSelected = getSelectedVOs();
		    if (voaSelected == null) {
		      return;
		    }
		    //列表显示 批送审
		    ArrayList userObj = new ArrayList();
        userObj.add(PoPublicUIClass.getLoginUser());
        userObj.add(PoPublicUIClass.getLoginDate());
		    PfUtilClient.runBatch(this, "SAVE", BillTypeConst.PO_ORDER, 
		        ClientEnvironment.getInstance().getDate().toString(),
		        voaSelected, null, null, null);
        OrderVO[] voNewOrder = null;
        Vector<String> vStrings = new Vector<String>();
        for (int i = 0; i < voaSelected.length; i++) {
          vStrings.add(voaSelected[i].getHeadVO().getCorderid());
        }
        String[] strOrderId = new String[vStrings.size()];
        vStrings.copyInto(strOrderId);
        try {
          voNewOrder = OrderHelper.queryOrderVOsLight(strOrderId,
              "SAVE");
        } catch (Exception e) {
          // TODO 自动生成 catch 块
          PuTool.outException(this, e);
          return;
        }
        if (voNewOrder == null) {
          showHintMessage(PuPubVO.MESSAGE_CONCURRENT);
          return;
        }
        refreshByHeaderVos(voNewOrder);
        // 刷新表头VO
        getBufferVOManager().getVOAt_LoadItemNo(
            getBufferVOManager().getVOPos());
        int[] pos = getPoListPanel().getHeadTable().getSelectedRows();
        getPoListPanel().displayCurVO(pos[0], true, isFromOtherBill());
        // 按钮状态
        setButtonsStateListNormal();
        updateUI();
		  }
		} catch (Exception e) {
			PuTool.outException(this, e);
		}

		setButtonsStateBrowse();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH023")/* @res "已送审" */);
	}

	/**
	 * 作者：李亮 功能：可用量查询 参数：无 返回：无 例外：无 日期：(2001-05-22 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志： 2003-02-21 王印芬
	 * 修改为：如果到货或入库数量绝对值＞订单数量，仍进行查询，并不是直接提示用户不可进行查询
	 */
	private void onBillUsableNum() {

		if (getPoCardPanel().getRowCount() == 0) {
			return;
		}

		int iRow = getPoCardPanel().getBillTable().getSelectedRow();
		if (iRow == -1) {
			iRow = 0;
		}

		OrderItemVO voItem = (OrderItemVO) getPoCardPanel().getBillModel()
				.getBodyValueRowVO(iRow, OrderItemVO.class.getName());
		// 信息完整性检查
		if (PuPubVO.getString_TrimZeroLenAsNull(voItem.getCmangid()) == null
				|| PuPubVO.getString_TrimZeroLenAsNull(voItem
						.getAttributeValue("dconsigndate")) == null) {
			MessageDialog.showHintDlg(this, NCLangRes.getInstance()
					.getStrByID("SCMCOMMON", "UPPSCMCommon-000270")/*
																	 * @res "提示"
																	 */, NCLangRes.getInstance().getStrByID("4004020201",
					"UPP4004020201-000056")/*
											 * @res "存货、计划到货日期信息不完整,不能查询存量"
											 */);
			return;
		}
		OrderHeaderVO voHead = (OrderHeaderVO) getPoCardPanel().getBillData()
				.getHeaderValueVO(OrderHeaderVO.class.getName());
		// 本SET语句是为修正合并显示后公司不能取到加入
		// voHead.setPk_corp(getOrderDataVOAt(getVOPos()).getHeadVO().getPk_corp())
		// ;
		OrderVO voPara = new OrderVO(1);
		voPara.setParentVO(voHead);
		voPara.setChildrenVO(new OrderItemVO[] { voItem });

		if (saPkCorp == null) {
			try {
				IUserManageQuery myService = (IUserManageQuery) nc.bs.framework.common.NCLocator
						.getInstance().lookup(IUserManageQuery.class.getName());
				nc.vo.bd.CorpVO[] vos = myService
						.queryAllCorpsByUserPK(getClientEnvironment().getUser()
								.getPrimaryKey());
				if (vos == null || vos.length == 0) {
					SCMEnv.out("未查询到有权限公司，直接返回!");/*-=notranslate=-*/
					return;
				}
				int iLen = vos.length;
				saPkCorp = new String[iLen];
				for (int i = 0; i < iLen; i++) {
					saPkCorp[i] = vos[i].getPrimaryKey();
				}
			} catch (Exception e) {
				MessageDialog.showErrorDlg(this, NCLangRes
						.getInstance().getStrByID("SCMCOMMON",
								"UPPSCMCommon-000270")/*
														 * @res "提示"
														 */, NCLangRes.getInstance().getStrByID("4004020201",
						"UPP4004020201-000057")/*
												 * @res
												 * "获取有权限公司时出现异常(详细信息参见控制台日志)"
												 */);
				SCMEnv.out(e);
				return;
			}
		}
		getDlgAtp().setPkCorps(saPkCorp);
		getDlgAtp().initData(voPara);
		getDlgAtp().showModal();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000032")/* @res "存量查询完成" */);

	}

	/**
	 * 此处插入方法说明。 功能： 参数： 返回： 例外： 日期：(2002-10-23 13:49:34) 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillVendorInfo() {

		String sVendMangId = getPoCardPanel().getHeadItem("cvendormangid")
				.getValue();
		if (PuPubVO.getString_TrimZeroLenAsNull(sVendMangId) == null) {
			return;
		}

		if (m_dlgCustInfo == null) {
			m_dlgCustInfo = new nc.ui.bd.b09.CustInfoUI(this,
					NCLangRes.getInstance().getStrByID("4004020201",
							"UPT4004020201-000085")/* @res "供应商信息" */);
		}

		m_dlgCustInfo.initData(sVendMangId); // 传入供应商管理ID
		m_dlgCustInfo.showModal();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000048")/* @res "供应商查询完成" */);

	}

	/**
	 * 作者：李亮 功能：配合按钮动作 参数：nc.ui.pub.ButtonObject bo 按钮 返回：无 例外：无 日期：(2002-3-13
	 * 11:39:21) 修改日期，修改人，修改原因，注释标志： 2002-04-22 wyf 分开单据及列表的按钮 wyf
	 * add/modify/delete 2002-03-21 begin/end 2002-07-25 wyf 使用业务类型及增加前，重置公司 wyf
	 * add/modify/delete 2002-03-21 begin/end 2002-10-31 wyf 加入合同附件函数
	 */
	protected void onButtonClickedBill(nc.ui.pub.ButtonObject bo) {

		// 从毛利预估界面返回
		if (getCurOperState() == STATE_BILL_GROSS_EVALUATE) {
			shiftGrossProfitToPoCard();
		}
		if (getBtnManager().btnCardEdit != null
				&& bo == getBtnManager().btnCardEdit) {
			getPoCardPanel().startRowCardEdit();
		} else if (bo == getBtnManager().btnBillReSortRowNo) {
			onBillReSortRowNo();
		} else if (bo.getParent() == getBtnManager().btnBillPasteRowTail) {
			onBillPasteLine();
		} else if (bo.getParent() == getBtnManager().btnBillBusitype) {
			// 业务类型
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000293")/* @res "选择业务类型" */);
			m_cbiztype = bo.getTag();

			setCurPk_corp(PoPublicUIClass.getLoginPk_corp());
			bo.setSelected(true);
			PfUtilClient.retAddBtn(getBtnManager().btnBillAdd, getCurPk_corp(),
					nc.vo.scm.pu.BillTypeConst.PO_ORDER, bo);
			setButtons(getBtnManager().getBtnaBill(this));
			getBtnManager().btnBillAdd.setEnabled(true);
			updateButton(getBtnManager().btnBillAdd);
			updateButtonsAll();

			// 设置是否可编辑
			setButtonsStateBrowse();
		} else if (bo.getParent() == getBtnManager().btnBillAdd) {
			// 增加
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000058")/* @res "增加订单" */);

			setCurPk_corp(PoPublicUIClass.getLoginPk_corp());
			setCurOperState(STATE_BILL_EDIT);
			// 为单据转入使用，保留上一次的位置
			getBufferVOManager().setPreviousVOPos(
					getBufferVOManager().getVOPos());
			if (bo.getTag().startsWith("makeflag")) {
				onBillNew();
			} else {
				// 来源单据类型 业务类型
				String tag = bo.getTag();
				int index = tag.indexOf(":");
				setCupsourcebilltype(tag.substring(0, index));
				setCurBizeType(tag.substring(index + 1, tag.length()));

				// 设置环境变量业务类型，UI端的VO交换可用用到
				ClientEnvironment.getInstance().putValue(
						OrderPubVO.ENV_BIZTYPEID,
						tag.substring(index + 1, tag.length()));
				if (m_cupsourcebilltype.equals("20")) {
					// 调用公共接口
					SourceRefDlg.getCacheToMapAdapter().clear();
					SourceRefDlg.childButtonClicked(bo,
							getCurPk_corp(),
							getModuleCode(), // "4004020201"
							getClientEnvironment().getUser().getPrimaryKey(),
							nc.vo.scm.pu.BillTypeConst.PO_ORDER, this);
				} else {
				  SourceRefDlg.setBIsRetVOsFromThis(null);
					// 调用公共接口
					PfUtilClient.childButtonClicked(bo,
							getCurPk_corp(),
							getModuleCode(), // "4004020201"
							getClientEnvironment().getUser().getPrimaryKey(),
							nc.vo.scm.pu.BillTypeConst.PO_ORDER, this);
				}
				if (SourceRefDlg.isCloseOK()) {
					// 初始化缓存原有的长度
					setOrgBufferLen(getBufferLength());
					AggregatedValueObject[] arySourceVOs = null;
					if (m_cupsourcebilltype.equals("20")) {
						arySourceVOs = SourceRefDlg.getRetsVos();
					} else {

						arySourceVOs = PfUtilClient.getRetVos();
					}
					processAfterChange(getCupsourcebilltype(), arySourceVOs);
					SCMEnv.out("out");

				} else {
					setCurOperState(STATE_BILL_BROWSE);
				}
			}
		} else if (bo == getBtnManager().btnBillByZYSOOrder) {
			if(isSoEnable()){
			// 增加
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000058")/* @res "增加订单" */);

			setCurPk_corp(PoPublicUIClass.getLoginPk_corp());
			setCurOperState(STATE_BILL_EDIT);
			// 为单据转入使用，保留上一次的位置
			getBufferVOManager().setPreviousVOPos(
					getBufferVOManager().getVOPos());
			if (bo.getName().equals(
					NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000341")/* @res "自制单据" */)) {
				onBillNew();
			} else {
				bo.setTag("30:直运");
				// 调用公共接口
				SourceRefDlg.childButtonClicked(bo,
						getCurPk_corp(),
						getModuleCode(), // "4004020201"
						getClientEnvironment().getUser().getPrimaryKey(),
						nc.vo.scm.pu.BillTypeConst.PO_ORDER, this);
				if (SourceRefDlg.isCloseOK()) {
					// 初始化缓存原有的长度
					setOrgBufferLen(getBufferLength());
					AggregatedValueObject[] arySourceVOs = SourceRefDlg
							.getRetsVos();
					processAfterChange("30", arySourceVOs);
					SCMEnv.out("out");
				} else {
					setCurOperState(STATE_BILL_BROWSE);
				}
			}
			}
		} else if (bo.getParent() == getBtnManager().btnBillAddContinue) {
			// 来源单据类型 业务类型
			String tag = bo.getTag();
			int index = tag.indexOf(":");
			setCupsourcebilltype(tag.substring(0, index));
			setCurBizeType(tag.substring(index + 1, tag.length()));

			// 设置环境变量业务类型，UI端的VO交换可用用到
			ClientEnvironment.getInstance().putValue(OrderPubVO.ENV_BIZTYPEID,
					tag.substring(index + 1, tag.length()));

			// 调用公共接口
			PfUtilClient.childButtonClicked(bo,
					getCurPk_corp(),
					getModuleCode(), // "4004020201"
					getClientEnvironment().getUser().getPrimaryKey(),
					nc.vo.scm.pu.BillTypeConst.PO_ORDER, this);
			if (PfUtilClient.isCloseOK()) {
				// 初始化缓存原有的长度
				setOrgBufferLen(getBufferLength());
				AggregatedValueObject[] arySourceVOs = PfUtilClient.getRetVos();
				processAfterChangeAdd(getCupsourcebilltype(), arySourceVOs);
				m_renderYellowAlarmLine.setRight(false);
				getPoCardPanel().getBillTable().repaint();
				SCMEnv.out("out");
			} else {
				setCurOperState(STATE_BILL_EDIT);
			}
		} else if (bo == getBtnManager().btnBillSave
				|| bo.getParent() == getBtnManager().btnBillMaintain) {
			// 维护组
			onButtonClickedBillMaintain(bo);
		} else if (bo == getBtnManager().btnBillQuery
				|| bo.getParent() == getBtnManager().btnBillBrowse) {
			// 浏览组
			onButtonClickedBillBrowse(bo);
		} else if (bo.getParent() == getBtnManager().btnBillRowOperation) {
			// 行操作组
			onButtonClickedRowOperation(bo);
		} else if (bo == getBtnManager().btnBillExecute
				|| bo.getParent() == getBtnManager().btnBillExecute) {
			// 执行组
			onButtonClickedBillExec(bo);
		} else if (bo == getBtnManager().btnBillShift) {
			// 切换
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000059")/* @res "转至列表" */);
			onBillList();
			setBillList(true);
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"common", "UCH022")/* @res "列表显示" */);
		}
		// 到货计划
		else if (bo == getBtnManager().btnBillReceivePlan) {
			onBillReveivePlan();
		}
		// 预付款
		else if (bo == getBtnManager().btnBillPrePay) {
			onBillPrePay();
		}
		// 打印
		else if (bo == getBtnManager().btnBillPrint) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000060")/* @res "打印订单" */);
			onBillPrint();
		}
		// 预览
		else if (bo == getBtnManager().btnBillPrintPreview) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPT4004020201-000083")/* @res "打印预览" */);
			onBillPrintPreview();
		}
		// 毛利预估
		else if (bo == getBtnManager().btnBillGrossProfit) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPT4004020201-000080")/* @res "毛利预估" */);
			onBillGrossProfit();
		}
		// 可用量查询
		else if (bo == getBtnManager().btnBillUsenum) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000061")/* @res "可用量查询" */);
			onBillUsableNum();
		}
		// 销量查询
		else if (bo == getBtnManager().btnBillSaleNum) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPT4004020201-000076")/* @res "销量查询" */);
			onBillSaleNum();
		}
		// 应付款查询
		else if (bo == getBtnManager().btnBillAp) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000062")/* @res "应付款查询" */);
			onBillApQuery();
		}
		// 付款执行情况查询
		else if (bo == getBtnManager().btnBillPayExecStat) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000063")/* @res "付款执行情况查询" */);
			onBillPayExecStat();
		}
		// 合并显示
		else if (bo == getBtnManager().btnBillCombin) {
			onBillCombin();
		}
		// 逐级联查
		else if (bo == getBtnManager().btnBillLnkQry) {
			onBillLnkQuery();
		}
		// 二次开发的合同信息
		else if (bo == getBtnManager().btnBillContractClass) {
			onBillContractClass();
		}
		// 状态查询
		else if (bo == getBtnManager().btnBillStatusQry) {
			onBillAuditStatusQuery();
		}
		// 供应商信息
		else if (bo == getBtnManager().btnBillVendor) {
			onBillVendorInfo();
		}
		// 替换件
		else if (bo == getBtnManager().btnBillInvReplace) {
			onBillInvReplace();
		}
		// 成套件
		else if (bo == getBtnManager().btnBillInvSetQuery) {
			onBillInvSetQuery();
		}
		// 文档管理
		else if (bo == getBtnManager().btnBillDocManage) {
			onBillDocManage();
		}
		// 导入供应商条码文件
		else if (bo == getBtnManager().btnBillImportVendorCode) {
			onBillProviderBarcodeQuery();
		}
		// 询销售价格
		else if (bo == getBtnManager().btnBillSOPrice) {
			onBillLookSoPrice();
		}
		// 参照协同销售订单
		else if (bo == getBtnManager().btnBillCoopWithSo) {
			// 增加
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000058")/* @res "增加订单" */);
			if(isSoEnable()){
			setCurPk_corp(PoPublicUIClass.getLoginPk_corp());
			setCurOperState(STATE_BILL_EDIT);
			// 为单据转入使用，保留上一次的位置
			getBufferVOManager().setPreviousVOPos(
					getBufferVOManager().getVOPos());
			if (bo.getName().equals(
					NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000341")/* @res "自制单据" */)) {
				onBillNew();
			} else {
				bo.setTag("30:协同");
				// 调用公共接口
				SourceRefDlg.childButtonClicked(bo,
						getCurPk_corp(),
						getModuleCode(), // "4004020201"
						getClientEnvironment().getUser().getPrimaryKey(),
						nc.vo.scm.pu.BillTypeConst.PO_ORDER, this);
				if (SourceRefDlg.isCloseOK()) {
					// 初始化缓存原有的长度
					setOrgBufferLen(getBufferLength());
					AggregatedValueObject[] arySourceVOs = SourceRefDlg
							.getRetsVos();
					processAfterChange("30", arySourceVOs);
					SCMEnv.out("out");
				} else {
					setCurOperState(STATE_BILL_BROWSE);
				}
			}
			}
		} else if (bo == getBtnManager().btnBillPushCoopToSo) {
			onBillPush();
		} else if (bo == getBtnManager().btnBillTransportStatus) {
			showDeliveryStatusUI();
		}
	}
	/**
	 * 运输状态
	 */
	private void showDeliveryStatusUI(){
		OrderVO vo = getOrderDataVOAt(getBufferVOManager().getVOPos());
		getPoCardPanel().showDeliveryStatusUI(vo);
		setButtonsStateBrowse();
	}
	/**
	 * 订单编辑状态询协同公司价格 入口参数：订单供应商对应协同公司客户，存货，主单位，收货地区，数量，币种
	 */
	public void onBillLookSoPrice() {
		getPoCardPanel().LookSoPrice();
	}

	/**
	 * 协同推式生成销售订单
	 * 
	 */
	private void onBillPush() {
		OrderVO voSavedOrder = getOrderDataVOAt(getBufferVOManager().getVOPos());
		if (voSavedOrder == null) {
			return;
		}
		if (voSavedOrder.getHeadVO().getForderstatus() != 3) {
			MessageDialog.showHintDlg(this,
					NCLangRes.getInstance().getStrByID("SCMCOMMON",
					"UPPSCMCommon-000270")/* @res "提示" */,
			NCLangRes.getInstance().getStrByID("4004020201",
					"UPP4004020201-V56002")/* @res "订单必须为审核状态" */);
			return;
		}
		if (voSavedOrder.getHeadVO().getBcooptoso() != null
				&& voSavedOrder.getHeadVO().getBcooptoso().booleanValue()) {
			MessageDialog.showHintDlg(this,
					NCLangRes.getInstance().getStrByID("SCMCOMMON",
					"UPPSCMCommon-000270")/* @res "提示" */,
			NCLangRes.getInstance().getStrByID("4004020201",
					"UPP4004020201-V56003")/* @res "订单已经协同生成销售订单，不能再生成销售订单" */);
			return;
		}
		if (voSavedOrder.getHeadVO().getBsocooptome() != null
				&& voSavedOrder.getHeadVO().getBsocooptome().booleanValue()) {
			MessageDialog.showHintDlg(this,
					NCLangRes.getInstance().getStrByID("SCMCOMMON",
					"UPPSCMCommon-000270")/* @res "提示" */,
			NCLangRes.getInstance().getStrByID("4004020201",
					"UPP4004020201-V56004")/* @res "订单由销售订单协同生成，不能再生成销售订单" */);
			return;
		}
		
		boolean bContinue = true;
		voSavedOrder.getHeadVO().setAttributeValue("bcheckatp", UFBoolean.TRUE);
		while (bContinue) {
			try {
				nc.ui.pub.pf.PfUtilClient.processBatchFlow(this, "PurchaseOrder",
						BillTypeConst.SALE_ORDER,
						PoPublicUIClass.getLoginDate().toString(), new OrderVO[] {
							voSavedOrder
						}, null);
				// 刷新标志
				OrderVO[] voLightRefreshed = OrderHelper.queryOrderVOsLight(
						new String[] {
							voSavedOrder.getHeadVO().getCorderid()
						}, "AUDIT");
				voSavedOrder.getHeadVO().refreshByHeaderVo(
						voLightRefreshed[0].getHeadVO());

				getPoCardPanel().displayCurVO(voSavedOrder, true);
				showHintMessage(NCLangRes.getInstance().getStrByID("4004020201",
						"UPP4004020201-V56005")/* @res "协同生成销售订单成功" */);
				break;
			}
			catch (Exception e) {
				Exception exp = nc.vo.so.pub.ExceptionUtils.marshException(e);
				if ((exp instanceof ATPNotEnoughException && ((ATPNotEnoughException) exp)
						.getHint() == null)
						|| !(exp instanceof ATPNotEnoughException)) {
					showErrorMessage(exp.getMessage());
					break;
				}
				String sMsg = exp.getMessage()
						+ "\n"
						+ NCLangRes.getInstance().getStrByID("4004020201",
								"UPP4004020201-000277")/* @res "是否继续？" */;
				if (showYesNoMessage(sMsg) != MessageDialog.ID_YES)
					break;
				else
					voSavedOrder.getHeadVO().setAttributeValue("bcheckatp",
							UFBoolean.FALSE);
			}
		}
	}

	/**
   * 作者：WYF 功能：配合按钮动作 辅助 参数：nc.ui.pub.ButtonObject bo 按钮 返回：无 例外：无
   * 日期：(2003-11-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	private void onButtonClickedBillAssistFun(nc.ui.pub.ButtonObject bo) {

		// 到货计划
		if (bo == getBtnManager().btnBillReceivePlan) {
			onBillReveivePlan();
		}
		// 预付款
		else if (bo == getBtnManager().btnBillPrePay) {
			onBillPrePay();
		}
		// 打印
		else if (bo == getBtnManager().btnBillPrint) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000060")/* @res "打印订单" */);
			onBillPrint();
		}
		// 预览
		else if (bo == getBtnManager().btnBillPrintPreview) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPT4004020201-000083")/* @res "打印预览" */);
			onBillPrintPreview();
		}
		// 毛利预估
		else if (bo == getBtnManager().btnBillGrossProfit) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPT4004020201-000080")/* @res "毛利预估" */);
			onBillGrossProfit();
		}
		// 可用量查询
		else if (bo == getBtnManager().btnBillUsenum) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000061")/* @res "可用量查询" */);
			onBillUsableNum();
		}
		// 销量查询
		else if (bo == getBtnManager().btnBillSaleNum) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPT4004020201-000076")/* @res "销量查询" */);
			onBillSaleNum();
		}
		// 应付款查询
		else if (bo == getBtnManager().btnBillAp) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000062")/* @res "应付款查询" */);
			onBillApQuery();
		}
		// 付款执行情况查询
		else if (bo == getBtnManager().btnBillPayExecStat) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000063")/* @res "付款执行情况查询" */);
			onBillPayExecStat();
		}
		// 合并显示
		else if (bo == getBtnManager().btnBillCombin) {
			onBillCombin();
		}
		// 逐级联查
		else if (bo == getBtnManager().btnBillLnkQry) {
			onBillLnkQuery();
		}
		// 二次开发的合同信息
		else if (bo == getBtnManager().btnBillContractClass) {
			onBillContractClass();
		}
		// 状态查询
		else if (bo == getBtnManager().btnBillStatusQry) {
			onBillAuditStatusQuery();
		}
		// 供应商信息
		else if (bo == getBtnManager().btnBillVendor) {
			onBillVendorInfo();
		}
		// 替换件
		else if (bo == getBtnManager().btnBillInvReplace) {
			onBillInvReplace();
		}
		// 成套件
		else if (bo == getBtnManager().btnBillInvSetQuery) {
			onBillInvSetQuery();
		}
		// 文档管理
		else if (bo == getBtnManager().btnBillDocManage) {
			onBillDocManage();
		}
		// 导入供应商条码文件
		else if (bo == getBtnManager().btnBillImportVendorCode) {
			onBillProviderBarcodeQuery();
		}
		else if (bo == getBtnManager().btnBillTransportStatus) {
			showDeliveryStatusUI();
		}
	}

	/**
	 * 作者：李亮 功能：配合按钮动作 参数：nc.ui.pub.ButtonObject bo 按钮 返回：无 例外：无 日期：(2002-3-13
	 * 11:39:21) 修改日期，修改人，修改原因，注释标志： 2002-04-22 wyf 分开单据及列表的按钮 wyf
	 * add/modify/delete 2002-03-21 begin/end 2002-07-25 wyf 使用业务类型及增加前，重置公司 wyf
	 * add/modify/delete 2002-03-21 begin/end 2002-10-31 wyf 加入合同附件函数
	 */
	private void onButtonClickedBillBrowse(nc.ui.pub.ButtonObject bo) {

		if (bo == getBtnManager().btnBillQuery) {
			showProgressBar(true);
			// 查询
			setCurPk_corp(PoPublicUIClass.getLoginPk_corp());
			onBillQuery();
			showProgressBar(false);
		} else if (bo == getBtnManager().btnBillRefresh) {
			// 刷新
			setCurPk_corp(PoPublicUIClass.getLoginPk_corp());
			onBillRefresh();
		} else if (bo == getBtnManager().btnBillFirst) {
			// 首张
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000064")/* @res "浏览首张订单" */);
			onBillFirst();
		} else if (bo == getBtnManager().btnBillPrevious) {
			// 上张
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000065")/* @res "浏览上张订单" */);
			onBillPrevious();
		} else if (bo == getBtnManager().btnBillNext) {
			// 下张
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000066")/* @res "浏览下张订单" */);
			onBillNext();
		} else if (bo == getBtnManager().btnBillLast) {
			// 末张
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000067")/* @res "浏览末张订单" */);
			onBillLast();
		} else if (bo == getBtnManager().btnBillLocate) {
			// 定位
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000068")/*
															 * @res
															 * "输入订单位置，浏览相应订单"
															 */);
			onBillLocate();
		}

	}

	/**
	 * 作者：WYF 功能：配合按钮动作 动作组执行 参数：nc.ui.pub.ButtonObject bo 按钮 返回：无 例外：无
	 * 日期：(2003-11-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	private boolean onButtonClickedBillExec(nc.ui.pub.ButtonObject bo) {

		if (getBtnManager().btnBillExecute != bo.getParent()) {
			return false;
		}

		// ==================执行
		// 卡片界面
//		if (bo == getBtnManager().btnBillExecAudit) {
//			showHintMessage(NCLangRes.getInstance().getStrByID(
//					"4004020201", "UPP4004020201-000069")/* @res "正在审批..." */);
//			onBillExecAudit();
//		} else if (bo == getBtnManager().btnBillExecUnAudit) {
//			showHintMessage(NCLangRes.getInstance().getStrByID(
//					"4004020201", "UPP4004020201-000070")/* @res "正在弃审..." */);
//			onBillExecUnAudit();
//		} else 
		if (bo == getBtnManager().btnBillExecClose) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000071")/* @res "正在关闭..." */);
			onBillExecClose();
		} else if (bo == getBtnManager().btnBillExecOpen) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000367")/* @res "正在打开..." */);
			onBillExecOpen();
		} else if (bo == getBtnManager().btnBillExecUnFreeze) {
			onBillExecUnFreeze();
		} else if (bo == getBtnManager().btnBillSendToAudit) {
			onBillSendToAudit();
		} else {
			try {
				PfUtilClient.processActionNoSendMessage(this, bo.getTag(),
						BillTypeConst.PO_ORDER, getClientEnvironment()
								.getDate().toString(),
						getOrderDataVOAt(getBufferVOManager().getVOPos()),
						null, null, null);
			} catch (Exception e) {
				PuTool.outException(this, e);
			}
		}

		return true;
	}

	/**
	 * 作者：WYF 功能：维护组下的按钮操作 参数：nc.ui.pub.ButtonObject bo 按钮 返回：无 例外：无
	 * 日期：(2004-03-16 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	private void onButtonClickedBillMaintain(nc.ui.pub.ButtonObject bo) {

		if (bo == getBtnManager().btnBillModify) {
			// 修改
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000041")/* @res "编辑订单" */);
			onBillModify();
		} else if (bo == getBtnManager().btnBillSave) {
			// 保存
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000072")/* @res "保存订单" */);
			onBillSave();
		} else if (bo == getBtnManager().btnBillCancel) {
			// 取消
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000349")/* @res "取消保存" */);
			onBillCancel();
		} else if (bo == getBtnManager().btnBillAnnul) {
			// 作废
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000073")/* @res "作废订单" */);
			onBillDiscard();
		} else if (bo == getBtnManager().btnBillCopyBill) {
			// 复制
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000041")/* @res "编辑订单" */);
			setCurPk_corp(PoPublicUIClass.getLoginPk_corp());
			onBillCopyBill();
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"common", "UCH029")/* @res "已复制" */);
		}
		// 送审
		else if (bo == getBtnManager().btnBillSendToAudit) {
			onBillSendToAudit();
		}

	}

	/**
	 * 作者：李亮 功能：配合按钮动作 参数：nc.ui.pub.ButtonObject bo 按钮 返回：无 例外：无 日期：(2002-3-13
	 * 11:39:21) 修改日期，修改人，修改原因，注释标志： 2002-04-22 wyf 分开单据及列表的按钮 wyf
	 * add/modify/delete 2002-03-21 begin/end
	 */
	protected void onButtonClickedList(nc.ui.pub.ButtonObject bo) {

		// 列表界面
		if (bo == getBtnManager().btnListSelectAll) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004070101", "UPT4004070101-000025")/*
															 * @res "全部选择"
															 */);
			onListSelectAll();
		} else if (bo == getBtnManager().btnListDeselectAll) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004070101", "UPT4004070101-000026")/*
															 * @res "全部取消"
															 */);
			onListDeselectAll();
		} else if (bo == getBtnManager().btnListQuery) {
			// wyf 2002-07-25 add begin
			setCurPk_corp(PoPublicUIClass.getLoginPk_corp());
			onListQuery();
			// wyf 2002-07-25 add end
			showProgressBar(false);
		} else if (bo == getBtnManager().btnListModify) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000041")/*
															 * @res "编辑订单"
															 */);
			onListModify();
		} else if (bo == getBtnManager().btnListAnnul) {
			onListDiscard();
		} else if (bo == getBtnManager().btnListRefresh) {
			// wyf 2002-07-25 add begin
			setCurPk_corp(PoPublicUIClass.getLoginPk_corp());
			// wyf 2002-07-25 add end
			onListRefresh();
		} else if (bo == getBtnManager().btnListShift) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000074")/*
															 * @res "转至卡片"
															 */);
			onListCard();
		} else if (bo == getBtnManager().btnListCancelTransform) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000382")/*
														 * @res "放弃未完成的单据"
														 */);
			onListCancelTransform();
		} else if (bo == getBtnManager().btnListDocManage) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPT4004020201-000097")/*
															 * @res "文档管理"
															 */);
			onListDocManage();
		} else if (bo == getBtnManager().btnListUsenum) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPT4004020201-000081")/*
															 * @res "存量查询"
															 */);
			onListUsableNum();
		} else if (bo == getBtnManager().btnListPrint) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000368")/*
														 * @res "批打印"
														 */);
			onListPrint();
		} else if (bo == getBtnManager().btnListPreview) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000369")/*
														 * @res "批预览"
														 */);
			onListPreview();
		} else if (bo == getBtnManager().btnBillExecAudit) {
			onListExecAudit();
		} else if (bo == getBtnManager().btnBillExecUnAudit) {
			onListExecUnAudit();
		} else if (bo == getBtnManager().btnBillCopyBill) {
			onListCard();
			onBillCopyBill();
		} else if (bo == getBtnManager().btnBillExecUnFreeze) {
			onListExecUnFreeze();
		} else if (bo == getBtnManager().btnBillLnkQry) {
			onListLnkQry();
		}// 送审
    else if (bo == getBtnManager().btnBillSendToAudit) {
      onBillSendToAudit();
    }
    else {
    	onButtonClickedBillAssistFun(bo);
    }
	}

	/**
	 * 列表联查
	 */
	private void onListLnkQry() {
		int[] iSelected = getPoListPanel().getHeadSelectedRows();
		OrderVO oraVO = getOrderDataVOAt(iSelected[0]);
		SourceBillFlowDlg soureDlg = new nc.ui.scm.sourcebill.SourceBillFlowDlg(
				this, BillTypeConst.PO_ORDER, oraVO.getHeadVO().getCorderid(),
				null, PoPublicUIClass.getLoginUser(), oraVO.getHeadVO().getVordercode());
		soureDlg.showModal();

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000019")/* @res "联查成功" */);
	}

	/**
	 * 列表审批。
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * <p>
	 * 
	 * @author czp
	 * @time 2007-3-15 下午02:18:51
	 */
	private void onListExecUnAudit() {

		nc.vo.scm.pu.Timer timeDebug = new nc.vo.scm.pu.Timer();
		timeDebug.start();

		// 获取要保存的VO
		OrderVO[] voaSelected = getSelectedVOs();
		if (voaSelected == null || voaSelected.length == 0) {
			return;
		}
		timeDebug.addExecutePhase("获得VO数组");/*-=notranslate=-*/

		// 回退审批人及审批日期哈希表，操作失败时用到
		HashMap<String, ArrayList<Object>> mapAuditInfo = new HashMap<String, ArrayList<Object>>();
		ArrayList<Object> listAuditInfo = null;
		OrderHeaderVO headVO = null;
		for (int i = 0; i < voaSelected.length; i++) {
			headVO = voaSelected[i].getHeadVO();
			//有优化到循环外边的余地，一次性提示出错误的订单号
			if (headVO.getBcooptoso() != null
					&& headVO.getBcooptoso().booleanValue()) {
				MessageDialog.showHintDlg(this,
						NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000270")/* @res "提示" */,
				NCLangRes.getInstance().getStrByID("4004020201",
						"UPP4004020201-V56016", null, new String[]{headVO.getVordercode()})/* @res "采购订单{0}已经协同生成销售订单,不能弃审" */);
				return;
			}
			//
			headVO.setAttributeValue("cauditpsnold", headVO.getCauditpsn());// 为判断是否允许弃审他人的单据
			// 赋操作员：为判断是否允许弃审他人的单据
			headVO.setAttributeValue("coperatoridnow", getClientEnvironment()
					.getUser().getPrimaryKey());

			// 操作失败时用到，回退审批人及审批日期哈希表
			if (PuPubVO.getString_TrimZeroLenAsNull(headVO.getCauditpsn()) != null) {
				listAuditInfo = new ArrayList<Object>();
				listAuditInfo.add(headVO.getCauditpsn());
				listAuditInfo.add(headVO.getDauditdate());
				mapAuditInfo.put(headVO.getPrimaryKey(), listAuditInfo);
			}
		}
		try {
			// 保存
			Object[] objs = new Object[voaSelected.length];
			ClientLink cl = new ClientLink(getClientEnvironment());
			int iLen = objs.length;
			for (int i = 0; i < iLen; i++) {
				objs[i] = cl;
			}
			PfUtilClient.processBatch("UNAPPROVE", BillTypeConst.PO_ORDER,
					PoPublicUIClass.getLoginDate().toString(), voaSelected,
					objs);

		} catch (Exception e) {
			// 回退审批人及审批日期
			if (mapAuditInfo.size() > 0) {
				for (int i = 0; i < voaSelected.length; i++) {
					headVO = voaSelected[i].getHeadVO();
					if (!mapAuditInfo.containsKey(headVO.getPrimaryKey())) {
						headVO.setCauditpsn(null);
						headVO.setDauditdate(null);
						continue;
					}
					listAuditInfo = (ArrayList<Object>) mapAuditInfo.get(headVO
							.getPrimaryKey());
					headVO.setCauditpsn((String) listAuditInfo.get(0));
					headVO.setDauditdate((UFDate) listAuditInfo.get(1));
				}
			}
			PuTool.outException(this, e);
			return;
		}
		timeDebug.addExecutePhase("弃审");/*-=notranslate=-*/

		if (PfUtilClient.isSuccess()) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"40040203", "UPP40040203-000012")/* @res "订单弃审成功" */);
			/** **********记录业务日志************ */
			Operlog operlog = new Operlog();
			for (OrderVO order : voaSelected) {
				order.getOperatelogVO().setOpratorname(
						nc.ui.pub.ClientEnvironment.getInstance().getUser()
								.getUserName());
				order.getOperatelogVO().setCompanyname(
						nc.ui.pub.ClientEnvironment.getInstance()
								.getCorporation().getUnitname());
				order.getOperatelogVO().setOperatorId(
						nc.ui.pub.ClientEnvironment.getInstance().getUser()
								.getPrimaryKey());
				operlog.insertBusinessExceptionlog(order, "弃审", "弃审",
						nc.vo.scm.funcs.Businesslog.MSGMESSAGE,
						nc.vo.scm.pu.BillTypeConst.PO_ORDER,
						nc.ui.sm.cmenu.Desktop.getApplet().getParameter(
								"USER_IP"));
			}
			/** **********记录业务日志* end *********** */
			// 保存成功后重置数据
//			onListRefresh();
			OrderVO[] voNewOrder = null;
			Vector<String> vStrings = new Vector<String>();
			for (int i = 0; i < voaSelected.length; i++) {
				vStrings.add(voaSelected[i].getHeadVO().getCorderid());
			}
			String[] strOrderId = new String[vStrings.size()];
			vStrings.copyInto(strOrderId);
			try {
				voNewOrder = OrderHelper.queryOrderVOsLight(strOrderId,
						"UNAUDIT");
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				PuTool.outException(this, e);
				return;
			}
			timeDebug.addExecutePhase("重新查询(轻量VO)");/*-=notranslate=-*/
			if (voNewOrder == null) {
				showHintMessage(PuPubVO.MESSAGE_CONCURRENT);
				return;
			}
			refreshByHeaderVos(voNewOrder);
			getBufferVOManager().getVOAt_LoadItemNo(
					getBufferVOManager().getVOPos());
			int[] pos = getPoListPanel().getHeadTable().getSelectedRows();
			getPoListPanel().displayCurVO(pos[0], true, isFromOtherBill());
			timeDebug.addExecutePhase("重新显示");/*-=notranslate=-*/

		} else {
			// 回退审批人及审批日期
			if (mapAuditInfo.size() > 0) {
				for (int i = 0; i < voaSelected.length; i++) {
					headVO = voaSelected[i].getHeadVO();
					if (!mapAuditInfo.containsKey(headVO.getPrimaryKey())) {
						headVO.setCauditpsn(null);
						headVO.setDauditdate(null);
						continue;
					}
					listAuditInfo = (ArrayList<Object>) mapAuditInfo.get(headVO
							.getPrimaryKey());
					headVO.setCauditpsn((String) listAuditInfo.get(0));
					headVO.setDauditdate((UFDate) listAuditInfo.get(1));
				}
			}
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"40040203", "UPP40040203-000013")/* @res "订单弃审失败" */);
		}
		timeDebug.showAllExecutePhase("订单UI弃审");/*-=notranslate=-*/

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH011")/* @res "弃审成功" */);
	}

	/**
	 * 列表审批功能。
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * <p>
	 * 
	 * @author czp
	 * @time 2007-3-15 下午02:13:56
	 */
	public void onListExecAudit() {

		nc.vo.scm.pu.Timer timeDebug = new nc.vo.scm.pu.Timer();
		timeDebug.start();

		// 获取要保存的VO
		OrderVO[] voaSelected = getSelectedVOs();
		if (voaSelected == null) {
			return;
		}
		timeDebug.addExecutePhase("获得VO数组");/*-=notranslate=-*/

		// 回退审批人及审批日期哈希表，操作失败时用到
		HashMap<String, ArrayList<Object>> mapAuditInfo = new HashMap<String, ArrayList<Object>>();
		ArrayList<Object> listAuditInfo = null;
		OrderHeaderVO headVO = null;
		for (int i = 0; i < voaSelected.length; i++) {
			headVO = voaSelected[i].getHeadVO();
			// 操作失败时用到，回退审批人及审批日期哈希表
			if (PuPubVO.getString_TrimZeroLenAsNull(headVO.getCauditpsn()) != null) {
				listAuditInfo = new ArrayList<Object>();
				listAuditInfo.add(headVO.getCauditpsn());
				listAuditInfo.add(headVO.getDauditdate());
				mapAuditInfo.put(headVO.getPrimaryKey(), listAuditInfo);
			}
			// 设置审批人为当前登录操作员
			headVO.setCauditpsn(PoPublicUIClass.getLoginUser());
		}

		try {
			// 保存
			Object[] objs = new Object[voaSelected.length];
			ClientLink cl = new ClientLink(getClientEnvironment());
			String strErr = PuTool.getAuditLessThanMakeMsg(voaSelected,
					"dorderdate", "vordercode", cl.getLogonDate(),
					ScmConst.PO_Order);
			if (strErr != null) {
				throw new BusinessException(strErr);
			}
			int iLen = objs.length;
			for (int i = 0; i < iLen; i++) {
				objs[i] = cl;
			}
			PfUtilClient.processBatchFlow(this, "APPROVE",
					BillTypeConst.PO_ORDER, PoPublicUIClass.getLoginDate()
							.toString(), voaSelected, objs);
		} catch (Exception e) {
			// 回退审批人及审批日期
			if (mapAuditInfo.size() > 0) {
				for (int i = 0; i < voaSelected.length; i++) {
					headVO = voaSelected[i].getHeadVO();
					if (!mapAuditInfo.containsKey(headVO.getPrimaryKey())) {
						headVO.setCauditpsn(null);
						headVO.setDauditdate(null);
						continue;
					}
					listAuditInfo = (ArrayList<Object>) mapAuditInfo.get(headVO
							.getPrimaryKey());
					headVO.setCauditpsn((String) listAuditInfo.get(0));
					headVO.setDauditdate((UFDate) listAuditInfo.get(1));
				}
			}
			MessageDialog.showErrorDlg(this,NCLangRes.getInstance().getStrByID("40040401", "UPPSCMCommon-000270")/*@res"提示"*/, e.getMessage());
			return;
		}
		timeDebug.addExecutePhase("审批");/*-=notranslate=-*/

		if (PfUtilClient.isSuccess()) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"40040203", "UPP40040203-000006")/* @res "订单审批成功" */);
			/** **********记录业务日志************ */
			Operlog operlog = new Operlog();
			for (OrderVO order : voaSelected) {
				order.getOperatelogVO().setOpratorname(
						nc.ui.pub.ClientEnvironment.getInstance().getUser()
								.getUserName());
				order.getOperatelogVO().setCompanyname(
						nc.ui.pub.ClientEnvironment.getInstance()
								.getCorporation().getUnitname());
				order.getOperatelogVO().setOperatorId(
						nc.ui.pub.ClientEnvironment.getInstance().getUser()
								.getPrimaryKey());
				operlog.insertBusinessExceptionlog(order, "审批", "审批",
						nc.vo.scm.funcs.Businesslog.MSGMESSAGE,
						nc.vo.scm.pu.BillTypeConst.PO_ORDER,
						nc.ui.sm.cmenu.Desktop.getApplet().getParameter(
								"USER_IP"));
			}
			/** **********记录业务日志* end *********** */
//			onListRefresh();
//			ConditionVO[] voaUserDefined = getPoQueryCondition()
//					.getConditionVO();
//			if (voaUserDefined == null
//					|| (voaUserDefined != null && voaUserDefined.length == 0)) {
				OrderVO[] voNewOrder = null;
				Vector<String> vStrings = new Vector<String>();
				for (int i = 0; i < voaSelected.length; i++) {
					vStrings.add(voaSelected[i].getHeadVO().getCorderid());
				}
				String[] strOrderId = new String[vStrings.size()];
				vStrings.copyInto(strOrderId);
				try {
					voNewOrder = OrderHelper.queryOrderVOsLight(strOrderId,
							"AUDIT");
				} catch (Exception e) {
					// TODO 自动生成 catch 块
					PuTool.outException(this, e);
					return;
				}
				timeDebug.addExecutePhase("重新查询(轻量VO)");/*-=notranslate=-*/
				if (voNewOrder == null) {
					showHintMessage(PuPubVO.MESSAGE_CONCURRENT);
					return;
				}
				refreshByHeaderVos(voNewOrder);
				// 刷新表头VO
				getBufferVOManager().getVOAt_LoadItemNo(
						getBufferVOManager().getVOPos());
				int[] pos = getPoListPanel().getHeadTable().getSelectedRows();
				getPoListPanel().displayCurVO(pos[0], true, isFromOtherBill());
				// 按钮状态
				setButtonsStateListNormal();
				updateUI();
				showHintMessage(NCLangRes.getInstance().getStrByID(
						"common", "UCH007")/* @res "刷新成功" */);
//			}
			timeDebug.addExecutePhase("重新显示");/*-=notranslate=-*/

		} else {
			// 回退审批人及审批日期
			if (mapAuditInfo.size() > 0) {
				for (int i = 0; i < voaSelected.length; i++) {
					headVO = voaSelected[i].getHeadVO();
					if (!mapAuditInfo.containsKey(headVO.getPrimaryKey())) {
						headVO.setCauditpsn(null);
						headVO.setDauditdate(null);
						continue;
					}
					listAuditInfo = (ArrayList<Object>) mapAuditInfo.get(headVO
							.getPrimaryKey());
					headVO.setCauditpsn((String) listAuditInfo.get(0));
					headVO.setDauditdate((UFDate) listAuditInfo.get(1));
				}
			}
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"40040203", "UPP40040203-000007")/* @res "订单审批失败" */);
		}
		timeDebug.addExecutePhase("订单UI审批");/*-=notranslate=-*/

		showHintMessage(NCLangRes.getInstance().getStrByID(
				"SCMCOMMON", "UPPSCMCommon-000236")/* @res "审核成功" */);
	}

	/**
	 * 刷新表头
	 */
	private void refreshByHeaderVos(OrderVO[] voNewOrder) {
		OrderVO[] oldOrderVOs = getBufferVOManager().getVOs();
		for (int i = 0; i < voNewOrder.length; i++) {
			for (int j = 0; j < oldOrderVOs.length; j++) {
				if (oldOrderVOs[j].getHeadVO().getCorderid().equals(
						voNewOrder[i].getHeadVO().getCorderid())) {
					oldOrderVOs[j].getHeadVO().setTs(
							voNewOrder[i].getHeadVO().getTs());
					if (PuPubVO.getString_TrimZeroLenAsNull(voNewOrder[i]
							.getHeadVO().getCorderid()) != null) {
						oldOrderVOs[j].getHeadVO().setCorderid(
								voNewOrder[i].getHeadVO().getCorderid());
					}
					if (PuPubVO.getString_TrimZeroLenAsNull(voNewOrder[i]
							.getHeadVO().getVordercode()) != null) {
						oldOrderVOs[j].getHeadVO().setVordercode(
								voNewOrder[i].getHeadVO().getVordercode());
					}
					oldOrderVOs[j].getHeadVO().setForderstatus(
							voNewOrder[i].getHeadVO().getForderstatus());
					oldOrderVOs[j].getHeadVO().setCauditpsn(
							voNewOrder[i].getHeadVO().getCauditpsn());
					oldOrderVOs[j].getHeadVO().setDauditdate(
							voNewOrder[i].getHeadVO().getDauditdate());
					oldOrderVOs[j].getHeadVO().setTaudittime(
							voNewOrder[i].getHeadVO().getTaudittime());
					oldOrderVOs[j].getHeadVO().setTmaketime(
							voNewOrder[i].getHeadVO().getTmaketime());
					oldOrderVOs[j].getHeadVO().setTlastmaketime(
							voNewOrder[i].getHeadVO().getTlastmaketime());
					break;
				}
			}

		}
	}

	/**
	 * 解冻当前订单
	 * 
	 * @param
	 * @return
	 * @exception
	 * @see
	 * @since 2001-04-28
	 */
	private void onListExecUnFreeze() {

		// 获取要保存的VO
		OrderVO[] voaSelected = getSelectedVOs();
		if (voaSelected == null) {
			return;
		}

		try {
			// 解冻
			PfUtilClient.processBatch("UNFREEZE", BillTypeConst.PO_ORDER,
					PoPublicUIClass.getLoginDate().toString(), voaSelected);

		} catch (Exception e) {
			PuTool.outException(this, e);
			return;
		}

		if (PfUtilClient.isSuccess()) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"common", "UCH019")/* @res "已解冻" */);

			onListRefresh();
		} else {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"40040203", "UPP40040203-000015")/* @res "订单解冻失败" */);
		}
	}

	/**
	 * 作者：WYF 功能：编辑的按钮处理 参数：nc.ui.pub.ButtonObject bo 按钮 返回：booleal 例外：无
	 * 日期：(2003-11-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	private void onButtonClickedRowOperation(nc.ui.pub.ButtonObject bo) {

		// 行操作
		if (bo == getBtnManager().btnBillAddRow) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000043")/* @res "增加订单行" */);
			onBillAppendLine();
		} else if (bo == getBtnManager().btnBillDelRow) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000046")/* @res "删除订单行" */);
			onBillDeleteLine();
		} else if (bo == getBtnManager().btnBillInsertRow) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000048")/* @res "插入订单行" */);
			onBillInsertLine();
		} else if (bo == getBtnManager().btnBillCopyRow) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000044")/* @res "复制订单行" */);
			onBillCopyLine();
		} else if (bo == getBtnManager().btnBillPasteRow) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000050")/* @res "粘贴订单行" */);
			onBillPasteLine();
		} else if (bo == getBtnManager().btnBillPasteRowTail) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"common", "SCMCOMMON000000266")/* @res "粘贴行到表尾" */);
			onBillPasteLineTail();
		}

	}

	/**
	 * 作者：李金巧 功能：处理鼠标双击，显示列表界面表头选中行对应卡片 该函数在双击及列表修改按钮下调用
	 * 在此函数中处理了列表排序，请不要再在列表修改按钮下再次处理 参数：int iRow 界面行 返回： 例外： 日期：(2002-4-4
	 * 13:24:16) 修改日期，修改人，修改原因，注释标志： 2002-05-28 王印芬 加入对列表排序的处理 2002-06-05 王印芬
	 * 修改双击转过去不是双击这张的问题 2003-02-24 王印芬 修改为：并发的单据不予转为卡片
	 */
	public boolean onDoubleClick(int iRow) {
		// 计算真正位置
		getBufferVOManager().setVOPos(getPoListPanel().getVOPos(iRow));

		OrderVO voCur = getOrderDataVOAt(getBufferVOManager().getVOPos());
		if (voCur.getBodyVO() == null || voCur.getBodyVO().length == 0) {
			return false;
		}

		showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000074")/* @res "转至卡片" */);

		// 设置状态
		setCurOperState(STATE_BILL_BROWSE);

		// 显示卡片
    remove(getPoListPanel());
    add(getPoCardPanel(),BorderLayout.CENTER);

		// 设置按钮
		setButtons(getBtnManager().getBtnaBill(this));

		// 显示
		displayCurVOEntirelyReset(true);
		updateUI();
		return true;
	}

	/**
	 * 作者：王印芬 功能：放弃转入未完成的单据 参数：无 返回：无 例外：无 日期：(2003-03-31 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void onListCancelTransform() {
		getPoListPanel().getHeadBillModel().clearBodyData();
		getPoListPanel().getBodyBillModel().clearBodyData();
		// 临时缓存到正式缓存
		getBufferVOManager().resetVOs(getBufferVOFrmBill());
		// 清临时缓存
		getBufferVOFrmBill().clear();

		// 现在的缓存长度
		int iCurBufferLen = getBufferLength();
		// 两长度一致，表明一张都未转入
		if (getOrgBufferLen() == iCurBufferLen) {
			getBufferVOManager().setVOPos(
					getBufferVOManager().getPreviousVOPos());
		} else {
			getBufferVOManager().setVOPos(getBufferLength() - 1);
		}
		// 显示原有或最后一张单据
		getBufferVOManager().setPreviousVOPos(getBufferVOManager().getVOPos());

		setIsFromOtherBill(false);
		setCurOperState(STATE_BILL_BROWSE);

		// ======界面准备
		// 卡片准备
		remove(getPoListPanel());
		add(getPoCardPanel(), BorderLayout.CENTER);
		getPoCardPanel().setEnabled(false);

		// 按钮
		setButtons(getBtnManager().getBtnaBill(this));

		// 显示
		displayCurVOEntirelyReset(true);
		setButtonsStateBrowse();
		// 清除追加数据缓存
		hPraybillVO.clear();
		// 清空所有上层的请购单来源
		setHmapUpSourcePrayTs(null);
		updateUI();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH008")/* @res "取消成功" */);
	}

	/**
	 * 作者：李亮 功能：列表界面的切换按钮触发后的处理 参数：无 返回：无 例外：无 日期：(2001-10-20 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志： 2002-05-23 wyf 加入不存在订单时的处理 2002-05-28 wyf 加入列表排序的处理
	 * 2002-06-24 wyf 加入对不存在订单时的处理
	 */
	private void onListCard() {
		bAddNew = true;
		// 转单后切换按钮可用
		if (getPoListPanel().getHeadSelectedRow() >= 0
				&& getPoListPanel().getHeadBillModel().getValueAt(
						getPoListPanel().getHeadSelectedRow(), "corderid") == null) {
			onListModify();
			return;
		}
		// 当前操作状态
		setCurOperState(STATE_BILL_BROWSE);

		// ======界面准备
		int iSortCol = getPoListPanel().getBodyBillModel().getSortColumn();
		boolean bSortAsc = getPoListPanel().getBodyBillModel()
				.isSortAscending();
		// boolean bSortAsc = getPoListPanel().getBodyBillModel().getSortAsc();
		remove(getPoListPanel());
		add(getPoCardPanel(), BorderLayout.CENTER);
		// 按钮
		setButtons(getBtnManager().getBtnaBill(this));

		if (getBufferLength() > 0) {
			// 切换到界面后显示哪张
			// m_nPos = 0;
			getBufferVOManager().setVOPos(0);
			for (int i = 0; i < getPoListPanel().getHeadRowCount(); i++) {
				if (getPoListPanel().getHeadRowState(i) == BillModel.SELECTED) {
					getBufferVOManager().setVOPos(getPoListPanel().getVOPos(i));
					break;
				}
			}

			// 显示
			displayCurVOEntirelyReset(true);

			if (getBufferLength() > 0) {
				if (isFromOtherBill()) {
					onBillModify();
				}
			}
		} else {
			getPoCardPanel().addNew();
		}
		// 手工排序卡片表体(同步列表表体排序结果)
		if (iSortCol >= 0) {
			getPoCardPanel().getBillModel().sortByColumn(iSortCol, bSortAsc);
		}
		setButtonsStateBrowse();
		updateUI();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH021")/* @res "卡片显示" */);
	}

	/**
	 * 订单列表界面中全部取消
	 * 
	 * @param
	 * @return
	 * @exception
	 * @see
	 * @since 2001-04-28
	 */
	private void onListDeselectAll() {

		// 设为全部取消选择
		getPoListPanel().onActionDeselectAll();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000034")/* @res "全部取消成功" */);
	}

	/**
	 * 作者：李亮 功能：订单列表界面中作废 参数：无 返回：无 例外：无 日期：(2002-4-4 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志： 2002-05-22 王印芬 数据库中去掉提示，代码中加入 2002-05-28 王印芬
	 * 加入对列表排序的处理 2002-06-27 王印芬 修正作废错误订单的问题 2003-03-05 王印芬 优化代码
	 */
	private void onListDiscard() {

		// 需作废的订单
		int[] iaSelectedRowCount = getPoListPanel().getHeadSelectedRows();
		int iDeletedLen = iaSelectedRowCount.length;
		for (int i = 0; i < iDeletedLen; i++) {
			int iVOPos = getPoListPanel().getVOPos(iaSelectedRowCount[i]);
			OrderVO voCur = getBufferVOManager().getVOAt_LoadItemNo(iVOPos);

			Integer iStatus = voCur.getHeadVO().getForderstatus();
			if (iStatus.compareTo(nc.vo.scm.pu.BillStatus.FREE) != 0
					&& iStatus.compareTo(nc.vo.scm.pu.BillStatus.AUDITFAIL) != 0) {
				// 以下情况不可作废
				showWarningMessage(NCLangRes.getInstance().getStrByID(
						"4004020201", "UPP4004020201-000075")/*
																 * @res
																 * "作废失败：存在非自由状态的订单"
																 */);
				return;
			}
		}

		// 是否并发
		OrderVO[] voaCanceled = getSelectedVOs();
		if (voaCanceled == null) {
			return;
		}
		try{
			getInvokeEventProxy().beforeAction(nc.vo.scm.plugin.Action.DELETE,voaCanceled);
		}catch(Exception e){
			showErrorMessage(e.getMessage());
			return;
		}
		// 作废
		boolean bSuccessed = onDiscard(this, voaCanceled);
		if (!bSuccessed) {
			return;
		}
		Operlog operlog = new Operlog();
		for (OrderVO orderVO : voaCanceled) {
			// 业务日志
			orderVO.getOperatelogVO().setOpratorname(
					nc.ui.pub.ClientEnvironment.getInstance().getUser()
							.getUserName());
			orderVO.getOperatelogVO().setCompanyname(
					nc.ui.pub.ClientEnvironment.getInstance().getCorporation()
							.getUnitname());
			orderVO.getOperatelogVO().setOperatorId(
					nc.ui.pub.ClientEnvironment.getInstance().getUser()
							.getPrimaryKey());
			operlog.insertBusinessExceptionlog(orderVO, "删除", "删除",
					nc.vo.scm.funcs.Businesslog.MSGMESSAGE,
					nc.vo.scm.pu.BillTypeConst.PO_ORDER, nc.ui.sm.cmenu.Desktop
							.getApplet().getParameter("USER_IP"));
		}
		// 保存后重置数据
		if (getBufferLength() == iDeletedLen) {
			getBufferVOManager().clear();
		} else {
			// 从后向前REMOVE
			// 最大的排在最后
			Integer[] iaIndex = getPoListPanel().getHeadSelectedVOPoses();
			for (int i = iDeletedLen - 1; i >= 0; i--) {
				getBufferVOManager().removeAt(iaIndex[i].intValue());
			}
		}

		getPoListPanel().displayCurVO(0, true, isFromOtherBill());
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH006")/* @res "删除成功" */);

	}

	/**
	 * 作者：王印芬 功能：列表文档管理 参数：无 返回：无 例外：无 日期：(2003-04-11 10:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void onListDocManage() {
		// 对选中的行进行文档管理
		int[] iaSelectedRow = getPoListPanel().getHeadSelectedRows();
		int iSelectedLen = getPoListPanel().getHeadSelectedCount();
		String[] saPk_corp = new String[iSelectedLen];
		String[] saCode = new String[iSelectedLen];
		// 处理文档管理框删除按钮是否可用
		HashMap mapBtnPowerVo = new HashMap();
		Integer iBillStatus = null;
		BtnPowerVO pVo = null;
		for (int i = 0; i < iSelectedLen; i++) {
			OrderVO voCur = getOrderDataVOAt(getPoListPanel().getVOPos(
					iaSelectedRow[i]));
			saPk_corp[i] = voCur.getHeadVO().getCorderid();
			saCode[i] = voCur.getHeadVO().getVordercode();
			pVo = new BtnPowerVO(saCode[i]);
			iBillStatus = PuPubVO.getInteger_NullAs(voCur.getHeadVO()
					.getForderstatus(), new Integer(0));
			if (iBillStatus.intValue() == 2 || iBillStatus.intValue() == 3
					|| iBillStatus.intValue() == 5) {
				pVo.setFileDelEnable("false");
			}
			mapBtnPowerVo.put(saCode[i], pVo);
		}

		// 调用文档管理对话框
		nc.ui.scm.file.DocumentManager.showDM(this,ScmConst.PO_Order, saPk_corp,
				mapBtnPowerVo);

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000025")/* @res "文档管理成功" */);
	}

	/**
	 * 作者：李亮 功能：订单列表界面中修改，返回卡片界面 参数： 返回： 例外： 日期：(2002-4-4 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志： 2002-06-05 王印芬 修改与onDoubleClick()相关的部分
	 */
	private void onListModify() {

		// 界面的位置
		onDoubleClick(getPoListPanel().getHeadSelectedRow());

		// 是否需要加载对应存货编码及名称
		onBillModify();

		//  
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000030")/* @res "正在修改" */);
	}

	/**
	 * 作者：WYF 功能：批量预览 参数：无 返回：无 例外：无 日期：(2003-11-04 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void onListPreview() {

		ArrayList arylistPrint = getPrintAryList();
		if (arylistPrint == null) {
			return;
		}
		try {
			getPrintTool().setData(arylistPrint);
			getPrintTool().onBatchPrintPreview(getPoListPanel(),
					getPoCardPanel(), BillTypeConst.PO_ORDER);
			if (PuPubVO.getString_TrimZeroLenAsNull(getPrintTool()
					.getPrintMessage()) != null
					&& getPrintTool().getPrintMessage().trim().length() > 0) {
				showHintMessage(getPrintTool().getPrintMessage());
			}
		} catch (Exception e) {
			MessageDialog.showErrorDlg(this,
					NCLangRes.getInstance().getStrByID("4004020201",
							"UPP4004020201-000051")/* @res "报错" */, e
							.getMessage());
		}

	}

	/**
	 * 作者：WYF 功能：批量打印 参数：无 返回：无 例外：无 日期：(2003-11-04 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void onListPrint() {

		ArrayList arylistPrint = getPrintAryList();
		if (arylistPrint == null) {
			return;
		}
		try {
			getPrintTool().setData(arylistPrint);
			getPrintTool().onBatchPrint(getPoListPanel(), getPoCardPanel(),
					BillTypeConst.PO_ORDER);
			if (PuPubVO.getString_TrimZeroLenAsNull(getPrintTool()
					.getPrintMessage()) != null) {
				this.showHintMessage(getPrintTool().getPrintMessage());
				/*MessageDialog.showHintDlg(this, );*/
			}
		} catch (Exception e) {
			MessageDialog.showErrorDlg(this,
					NCLangRes.getInstance().getStrByID("4004020201",
							"UPP4004020201-000051")/* @res "报错" */, e
							.getMessage());
		}
	}

	/**
	 * 作者：李亮 功能：订单列表界面中查询 参数：无 返回：无 例外：无 日期：(2001-4-18 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void onListQuery() {

		getPoQueryCondition().showModal();

		if (getPoQueryCondition().isCloseOK()) {
			onListRefresh();
		}

		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH009")/* @res "查询完成" */);
	}

	/**
	 * 订单列表界面中刷新
	 * 
	 * @param
	 * @return
	 * @exception
	 * @see
	 * @since 2001-04-28 2003-02-24 wyf 修改对显示表体的调用为优化后的函数 2003-03-04 wyf
	 *        修改对查询的调用
	 */
	private void onListRefresh() {

		queryOrderVOsFromDB();

		getPoListPanel().displayCurVO(0, true, isFromOtherBill());

		// //按钮状态
		setButtonsStateListNormal();
		updateUI();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH007")/* @res "刷新成功" */);
	}

	/**
	 * 作者：李亮 功能：订单列表界面中全部选择 参数：无 返回：无 例外：无 日期：(2001-4-18 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志： 2002-06-05 王印芬 加入对全选、全消按钮的控制
	 */
	private void onListSelectAll() {

		// 设为全部选中
		getPoListPanel().onActionSelectAll();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000033")/* @res "全部选中成功" */);
	}

	/**
	 * 作者：王印芬 功能：列表可用量查询 参数：无 返回：无 例外：无 日期：(2001-05-22 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void onListUsableNum() {

		int iRow = getPoListPanel().getHeadSelectedRow();
		OrderVO voBuf = getOrderDataVOAt(getPoListPanel().getVOPos(iRow));
		OrderItemVO bodyVO[] = voBuf.getBodyVO();
		if (bodyVO == null || bodyVO.length == 0) {
			return;
		}

		int iSelectBodyRow = getPoListPanel().getBodyTable().getSelectedRow();
		if (iSelectBodyRow < 0 || iSelectBodyRow > bodyVO.length - 1) {
			iSelectBodyRow = 0;
		}
		if (PuPubVO
				.getString_TrimZeroLenAsNull(voBuf.getBodyVO()[iSelectBodyRow]
						.getCmangid()) == null
				|| PuPubVO
						.getString_TrimZeroLenAsNull(voBuf.getBodyVO()[iSelectBodyRow]
								.getAttributeValue("dconsigndate")) == null) {
			MessageDialog.showHintDlg(this, NCLangRes.getInstance()
					.getStrByID("SCMCOMMON", "UPPSCMCommon-000270")/*
																	 * @res "提示"
																	 */, NCLangRes.getInstance().getStrByID("4004020201",
					"UPP4004020201-000056")/*
											 * @res "存货、计划到货日期信息不完整,不能查询存量"
											 */);
			return;
		}

		// 组合新VO初始化并调用存量查询对话框
		OrderVO voPara = new OrderVO(1);
		voPara.setParentVO(voBuf.getHeadVO());
		voPara
				.setChildrenVO(new OrderItemVO[] { voBuf.getBodyVO()[iSelectBodyRow] });

		if (saPkCorp == null) {
			try {
				IUserManageQuery myService = (IUserManageQuery) nc.bs.framework.common.NCLocator
						.getInstance().lookup(IUserManageQuery.class.getName());
				nc.vo.bd.CorpVO[] vos = myService
						.queryAllCorpsByUserPK(getClientEnvironment().getUser()
								.getPrimaryKey());
				if (vos == null || vos.length == 0) {
					SCMEnv.out("未查询到有权限公司，直接返回!");/*-=notranslate=-*/
					return;
				}
				int iLen = vos.length;
				saPkCorp = new String[iLen];
				for (int i = 0; i < iLen; i++) {
					saPkCorp[i] = vos[i].getPrimaryKey();
				}
			} catch (Exception e) {
				MessageDialog.showErrorDlg(this, NCLangRes
						.getInstance().getStrByID("SCMCOMMON",
								"UPPSCMCommon-000270")/*
														 * @res "提示"
														 */, NCLangRes.getInstance().getStrByID("4004020201",
						"UPP4004020201-000057")/*
												 * @res
												 * "获取有权限公司时出现异常(详细信息参见控制台日志)"
												 */);
				SCMEnv.out(e);
				return;
			}
		}
		getDlgAtp().setPkCorps(saPkCorp);
		getDlgAtp().initData(voPara);
		getDlgAtp().showModal();
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000032")/* @res "存量查询完成" */);
	}

	/**
	 * 作者：李亮 功能：取符合查询条件的VO 参数：无 返回：无 例外：无 日期：(2002-4-22 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志： 2002-06-21 wyf 修改查询为不按业务类型查询 2002-07-25 wyf
	 * 加入设置当前公司为登录公司ID的代码 2002-12-05 wyf 修改当前查询的公司，按查询框中的公司查询 2003-02-21 wyf
	 * 修改为查询时只查表头，提高效率 2003-03-04 wyf 进一步优化效率，修改原先查询表头的方法
	 */
	private void queryOrderVOsFromDB() {
		showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000076")/* @res "查询订单......" */);

		try {
			getPoListPanel().getHeadBillModel().clearBodyData();
			getPoListPanel().getBodyBillModel().clearBodyData();
			// 通过Client取得数据，单位需重新设置
			getBufferVOManager().resetVOsNoListenner(getQueriedVOHead());

			setEverQueryed(true);
		} catch (Exception e) {
			PuTool.outException(this, e);
			return;
		}

		if (getBufferLength() == 0) {
			showWarningMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000077")/*
															 * @res
															 * "没有符合条件的采购订单"
															 */);
			// 清空当前界面单据状态标志
			// V5 Del : setImageType(IMAGE_NULL);
		}

		showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000042")/* @res "浏览订单" */);
	}

	/**
	 * 作者：王印芬 功能：列表批打时需在卡片上显示当前VO，使用此接口方法 为接口ISetBillVO的接口方法 参数：无 返回：无 例外：无
	 * 日期：(2003-11-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	public void setBillVO(AggregatedValueObject vo) {

		getPoCardPanel().displayCurVO((OrderVO) vo, isFromOtherBill());
	}

	/**
	 * 作者：王印芬 功能：设置浏览状态的按钮可用性 参数：无 返回：无 例外：无 日期：(2002-5-15 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志： 2002-10-31 wyf 加入对文件操作按钮的控制 2002-11-01 wyf
	 * 修改当前无单据时的空指针错误 2007-04-26 czp 修改按钮逻辑错误
	 */
	protected void setButtonsStateBrowse() {
		// 修订
 		if (getBtnManager().btnBillRevise != null) {
			getBtnManager().btnBillRevise.setEnabled(getBufferVOManager().getVOAt_LoadItemNo(getBufferVOManager().getVOPos())==null?false:getBufferVOManager().getVOAt_LoadItemNo(getBufferVOManager().getVOPos()).isSameCorp());
		}
		// 放弃转单
		if (getBtnManager().btnListCancelTransform != null) {
			getBtnManager().btnListCancelTransform.setVisible(false);
		}
		// 卡片显示/列表显示
		getBtnManager().btnBillShift.setName(NCLangRes.getInstance()
				.getStrByID("SCMCOMMON", "UPPSCMCommon-000464")
		/* @res "列表显示" */);
		//
		getBtnManager().btnBillReplenish.setEnabled(true);
		// 全选
		getBtnManager().btnListSelectAll.setEnabled(false);
		// 全消
		getBtnManager().btnListDeselectAll.setEnabled(false);
		// 业务类型
		getBtnManager().btnBillBusitype.setEnabled(true);
		// 增加
		if (getBtnManager().btnBillBusitype.isVisible() == false
				|| getCurBizeType() == null) {
			// 没有业务类型则增加按钮可用
			getBtnManager().btnBillAdd.setEnabled(false);
		} else {
			getBtnManager().btnBillAdd.setEnabled(true);
		}
		// 保存
		getBtnManager().btnBillSave.setEnabled(false);
		// 放弃
		getBtnManager().btnBillCancel.setEnabled(false);
		// 行操作
		getBtnManager().btnBillRowOperation.setEnabled(false);
		// 查询
		getBtnManager().btnBillQuery.setEnabled(true);
		// 列表
		getBtnManager().btnBillShift.setEnabled(true);

		// ----------浏览
		getBtnManager().btnBillBrowse.setEnabled(true);
		// 首上下末张
		OrderVO voCur = null;
		if (getBufferLength() == 0) {
			// 作废
			getBtnManager().btnBillAnnul.setEnabled(false);
			// 修改
			getBtnManager().btnBillModify.setEnabled(false);
			// 翻页
			getBtnManager().btnBillFirst.setEnabled(false);
			getBtnManager().btnBillPrevious.setEnabled(false);
			getBtnManager().btnBillNext.setEnabled(false);
			getBtnManager().btnBillLast.setEnabled(false);
			// 审批
			getBtnManager().btnBillExecAudit.setEnabled(false);
			// 解冻
			getBtnManager().btnBillExecUnFreeze.setEnabled(false);
			// 执行
			getBtnManager().btnBillExecute.setEnabled(false);
			// 文档管理
			getBtnManager().btnBillDocManage.setEnabled(false);
			// 合同查询
			getBtnManager().btnBillContractClass.setEnabled(false);
			getBtnManager().btnBillImportVendorCode.setEnabled(false);
		} else {
			voCur = getOrderDataVOAt(getBufferVOManager().getVOPos());
			// 文档管理
			getBtnManager().btnBillDocManage.setEnabled(true);
			// 合同查询
			getBtnManager().btnBillContractClass.setEnabled(true);
			// 修改、作废
			getBtnManager().btnBillModify.setEnabled(voCur.isCanBeModified());
			getBtnManager().btnBillAnnul.setEnabled(voCur.isCanBeAnnuled());
			// 审批、弃审、关闭、打开
			if (getBtnManager().btnBillExecute.getChildButtonGroup() == null
					|| getBtnManager().btnBillExecute.getChildButtonGroup().length == 0) {
				getBtnManager().btnBillExecute.setEnabled(false);
			} else {
				getBtnManager().btnBillExecute.setEnabled(true);
				getBtnManager().btnBillExecAudit.setEnabled(voCur
						.isCanBeAudited());
				getBtnManager().btnBillExecUnAudit.setEnabled(voCur
						.isCanBeUnAudited());
				getBtnManager().btnBillExecClose.setEnabled(voCur
						.isCanBeClosed());
				getBtnManager().btnBillExecOpen.setEnabled(voCur
						.isCanBeOpened());
				getBtnManager().btnBillExecUnFreeze.setEnabled(voCur
						.isCanBeUnFreezed());
			}
			getBtnManager().btnBillImportVendorCode.setEnabled(true);
			// 只有一张订单
			if (getBufferLength() == 1) {
				// 翻页
				// getBtnManager().btnBillPageOperation.setEnabled(false);
				getBtnManager().btnBillFirst.setEnabled(false); // 首张
				getBtnManager().btnBillPrevious.setEnabled(false); // 上张
				getBtnManager().btnBillNext.setEnabled(false); // 下张
				getBtnManager().btnBillLast.setEnabled(false); // 末张
			} else {
				// 已是第一张订单
				if (getBufferVOManager().getVOPos() == 0) {
					getBtnManager().btnBillFirst.setEnabled(false); // 首张
					getBtnManager().btnBillPrevious.setEnabled(false); // 上张
					getBtnManager().btnBillNext.setEnabled(true); // 下张
					getBtnManager().btnBillLast.setEnabled(true); // 末张
				}
				// 已是最后一张订单
				else if (getBufferVOManager().getVOPos() == getBufferLength() - 1) {
					getBtnManager().btnBillFirst.setEnabled(true); // 首张
					getBtnManager().btnBillPrevious.setEnabled(true); // 上张
					getBtnManager().btnBillNext.setEnabled(false); // 下张
					getBtnManager().btnBillLast.setEnabled(false); // 末张
				}
				// 中间任何一张
				else {
					getBtnManager().btnBillFirst.setEnabled(true); // 首张
					getBtnManager().btnBillPrevious.setEnabled(true); // 上张
					getBtnManager().btnBillNext.setEnabled(true); // 下张
					getBtnManager().btnBillLast.setEnabled(true); // 末张
				}
			}
		}

		// 辅助
		getBtnManager().btnBillAssist.setEnabled(true);
		{
			// 刷新
			if (isEverQueryed()) {
				getBtnManager().btnBillRefresh.setEnabled(true);
			} else {
				getBtnManager().btnBillRefresh.setEnabled(false);
			}
			boolean bEnabled = true;
			if (getBufferLength() == 0) {
				bEnabled = false;
			}
			getBtnManager().btnBillCopyBill.setEnabled(bEnabled);
			// 退货订单不可维护到货计划、预付款
			boolean bReturn = false;
			if (voCur != null && voCur.getHeadVO() != null
					&& voCur.getHeadVO().getBreturn() != null) {
				bReturn = voCur.getHeadVO().getBreturn().booleanValue();
			}
			getBtnManager().btnBillReceivePlan.setEnabled(bEnabled & !bReturn);
			getBtnManager().btnBillPrePay.setEnabled(bEnabled & !bReturn);
			//
			getBtnManager().btnBillLocate.setEnabled(bEnabled);
			getBtnManager().btnBillGrossProfit.setEnabled(bEnabled);
			getBtnManager().btnBillUsenum.setEnabled(bEnabled);
			getBtnManager().btnBillSaleNum.setEnabled(bEnabled);
			getBtnManager().btnBillByZYSOOrder.setEnabled(true);
			
			getBtnManager().btnBillCoopWithSo.setEnabled(true);
			getBtnManager().btnBillPushCoopToSo.setEnabled(true);
			getBtnManager().btnBillInvSetQuery.setEnabled(bEnabled);
			getBtnManager().btnBillAp.setEnabled(bEnabled);
			getBtnManager().btnBillVendor.setEnabled(bEnabled);
			getBtnManager().btnBillInvReplace.setEnabled(bEnabled);
			getBtnManager().btnBillStatusQry.setEnabled(bEnabled);
			getBtnManager().btnBillLnkQry.setEnabled(bEnabled);
			getBtnManager().btnBillCombin.setEnabled(bEnabled);

			// 自由状态不予查询
			if (getBufferLength() == 0) {
				getBtnManager().btnBillSOPrice.setEnabled(false);
				getBtnManager().btnBillPushCoopToSo.setEnabled(false);
				getBtnManager().btnBillPayExecStat.setEnabled(false);
			} else {
				getBtnManager().btnBillSOPrice.setEnabled(true);
				Integer iStatus = getOrderViewVOAt(
						getBufferVOManager().getVOPos()).getHeadVO()
						.getForderstatus();
				if (iStatus.compareTo(nc.vo.scm.pu.BillStatus.AUDITED) == 0
						|| iStatus.compareTo(nc.vo.scm.pu.BillStatus.OUTPUT) == 0) {
					getBtnManager().btnBillPayExecStat.setEnabled(true);
					getBtnManager().btnBillCoopWithSo.setEnabled(true);
				} else {
					getBtnManager().btnBillPayExecStat.setEnabled(false);
					getBtnManager().btnBillCoopWithSo.setEnabled(true);
				}
				
			}

			if (getBufferLength() == 0) {
				getBtnManager().btnBillPrint.setEnabled(false);
				getBtnManager().btnBillPrintPreview.setEnabled(false);
				getBtnManager().btnBillTransportStatus.setEnabled(false);
			} else {
				getBtnManager().btnBillPrint.setEnabled(true);
				getBtnManager().btnBillPrintPreview.setEnabled(true);
				getBtnManager().btnBillTransportStatus.setEnabled(true);
			}
			if(voCur == null || voCur.getHeadVO().isReturn() 
			    || !nc.vo.scm.pu.BillStatus.AUDITED.equals(voCur.getHeadVO().getForderstatus())){
				getBtnManager().btnBillTransportStatus.setEnabled(false);
			}
			// 送审
			setButtonsState_SendToAudit();
		}
		getBtnManager().btnBillAddContinue.setEnabled(false);
		// 弹出式菜单
		getPoCardPanel().setBodyMenuShow(true);

		
		// 支持产业链功能扩展
		if (this instanceof IBillExtendFun) {
			((IBillExtendFun) this).setExtendBtnsStat(1);

		}
		
		// wuxla 57 二次开发支持
		getInvokeEventProxy().setButtonStatus();
		updateButtonsAll();
	}

	/**
	 * 作者：王印芬 功能：设置送审按钮的可用性 参数：无 返回：无 例外：无 日期：(2003-10-27 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void setButtonsState_SendToAudit() {

		OrderVO voCur = getOrderDataVOAt(getBufferVOManager().getVOPos());
		// 新增单据
		if ((voCur == null
				|| getPoCardPanel().getHeadItem("corderid").getValue() == null || getPoCardPanel()
				.getHeadItem("corderid").getValue().trim().equals(""))
				&& getCurOperState() == STATE_BILL_EDIT) {
			String sBillType = BillTypeConst.PO_ORDER;
			String sPk_corp = PoPublicUIClass.getLoginPk_corp();
			String sBizType = getPoCardPanel().getHeadItem("cbiztype")
					.getValue();
			String sOper = PoPublicUIClass.getLoginUser();
			boolean bEnabled = BusiBillManageTool.isNeedSendToAudit(sBillType,
					sPk_corp, sBizType, null, sOper);
			getBtnManager().btnBillSendToAudit.setEnabled(bEnabled);
			return;
		}
		// 浏览、修改单据
		if (getBufferLength() == 0) {
			getBtnManager().btnBillSendToAudit.setEnabled(false);
		} else {
			// 订单维护、补货处理
			if (getCurOperState() == STATE_BILL_EDIT
					&& voCur.getHeadVO() != null
					&& nc.vo.scm.pu.BillStatus.AUDITFAIL.equals(voCur
							.getHeadVO().getForderstatus())) {
				getBtnManager().btnBillSendToAudit.setEnabled(true);
			} else {
				getBtnManager().btnBillSendToAudit.setEnabled(PuTool
						.isNeedSendToAudit(BillTypeConst.PO_ORDER, voCur));
			}
		}
		
		

	}

	/**
	 * 作者：王印芬 功能：设置编辑时的按钮状态 参数：无 返回：无 例外：无 日期：(2002-6-5 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志： 2002-10-31 wyf 加入对文件操作按钮的控制
	 */
	protected void setButtonsStateEdit() {

		// 审核
		if (getBtnManager().btnBillExecAudit != null) {
			getBtnManager().btnBillExecAudit.setEnabled(false);
		}
		// 解冻
		if (getBtnManager().btnBillExecUnFreeze != null) {
			getBtnManager().btnBillExecUnFreeze.setEnabled(false);
		}
		// 修订
		if (getBtnManager().btnBillRevise != null) {
			getBtnManager().btnBillRevise.setEnabled(false);
		}
		// 放弃转单
		if (getBtnManager().btnListCancelTransform != null) {
			getBtnManager().btnListCancelTransform.setVisible(false);
		}
		// 补货
		getBtnManager().btnBillReplenish.setEnabled(false);
		// 业务类型
		getBtnManager().btnBillBusitype.setEnabled(false);
		// 增加
		getBtnManager().btnBillAdd.setEnabled(false);
		// 修改
		getBtnManager().btnBillModify.setEnabled(false);
		// 执行
		getBtnManager().btnBillExecute.setEnabled(true);
		getBtnManager().btnBillExecUnAudit.setEnabled(false);
		getBtnManager().btnBillExecClose.setEnabled(false);
		getBtnManager().btnBillExecOpen.setEnabled(false);
		getBtnManager().btnBillExecUnFreeze.setEnabled(false);
		// 保存
		getBtnManager().btnBillSave.setEnabled(true);
		// 送审
		// getBtnManager().btnBillSendToAudit.setEnabled(PuTool.isNeedSendToAudit(getPoCardPanel().getBillType(),getSaveVO()));
		getBtnManager().btnBillSendToAudit.setEnabled(true);

		// 行操作
		getBtnManager().btnBillRowOperation.setEnabled(true);
		{
			// ======行操作
			int iRow = getPoCardPanel().getRowCount();
			if (iRow <= 0) {
				getBtnManager().btnBillDelRow.setEnabled(false);
			} else {
				getBtnManager().btnBillDelRow.setEnabled(true);
			}

			// boolean bCouldBeMade = getPoCardPanel().isCouldBeMade() ;
			getBtnManager().btnBillAddRow.setEnabled(true);
			getBtnManager().btnBillCopyRow.setEnabled(true);
			getBtnManager().btnBillInsertRow.setEnabled(true);
			getBtnManager().btnBillPasteRow.setEnabled(true);

			// 对弹出式菜单
			getPoCardPanel().setBodyMenuShow(true);
			getPoCardPanel().getAddLineMenuItem().setEnabled(true);
			getPoCardPanel().getInsertLineMenuItem().setEnabled(true);
			getPoCardPanel().getCopyLineMenuItem().setEnabled(true);
			getPoCardPanel().getPasteLineMenuItem().setEnabled(true);
			getPoCardPanel().getPasteLineToTailMenuItem().setEnabled(true);
			getPoCardPanel().getDelLineMenuItem().setEnabled(true);
		}
		// 放弃
		getBtnManager().btnBillCancel.setEnabled(true);
		// 作废
		getBtnManager().btnBillAnnul.setEnabled(false);
		// -----------浏览
		getBtnManager().btnBillBrowse.setEnabled(false);
		// 查询
		getBtnManager().btnBillQuery.setEnabled(false);
		// 切换
		getBtnManager().btnBillShift.setEnabled(false);
		// 翻页
		getBtnManager().btnBillFirst.setEnabled(false);
		getBtnManager().btnBillPrevious.setEnabled(false);
		getBtnManager().btnBillNext.setEnabled(false);
		getBtnManager().btnBillLast.setEnabled(false);

		// 辅助
		getBtnManager().btnBillAssist.setEnabled(true);
		{
			getBtnManager().btnBillCopyBill.setEnabled(false);
			getBtnManager().btnBillReceivePlan.setEnabled(false);
			getBtnManager().btnBillPrePay.setEnabled(false);
			getBtnManager().btnBillRefresh.setEnabled(false);
			getBtnManager().btnBillLocate.setEnabled(false);
			getBtnManager().btnBillGrossProfit.setEnabled(true);
			getBtnManager().btnBillUsenum.setEnabled(true);
			getBtnManager().btnBillSaleNum.setEnabled(true);
			getBtnManager().btnBillByZYSOOrder.setEnabled(false);
			getBtnManager().btnBillSOPrice.setEnabled(true);
			getBtnManager().btnBillCoopWithSo.setEnabled(false);
			getBtnManager().btnBillPushCoopToSo.setEnabled(false);
			getBtnManager().btnBillInvSetQuery.setEnabled(true);
			getBtnManager().btnBillAp.setEnabled(true);
			getBtnManager().btnBillPayExecStat.setEnabled(false);
			getBtnManager().btnBillPrint.setEnabled(false);
			getBtnManager().btnBillPrintPreview.setEnabled(false);
			getBtnManager().btnBillCombin.setEnabled(false);
			getBtnManager().btnBillVendor.setEnabled(true);
			getBtnManager().btnBillInvReplace.setEnabled(true);
			getBtnManager().btnBillStatusQry.setEnabled(false);
			getBtnManager().btnBillImportVendorCode.setEnabled(true);
			// 合同查询
			getBtnManager().btnBillContractClass.setEnabled(false);
			// 文档管理
			if (getPoCardPanel().getHeadItem("corderid").getValue() == null
					|| getPoCardPanel().getHeadItem("corderid").getValue()
							.toString().equals("")) {
				getBtnManager().btnBillDocManage.setEnabled(false);
			} else {
				getBtnManager().btnBillDocManage.setEnabled(true);
			}
		}
		if (bAddNew) {
			getBtnManager().btnBillAddContinue.setEnabled(true);
		} else {
			getBtnManager().btnBillAddContinue.setEnabled(false);
		}
		getBtnManager().btnBillTransportStatus.setEnabled(false);
		getBtnManager().btnBillLnkQry.setEnabled(false);
		/** 设置右键菜单与按钮组“行操作”权限相同 */
		setPopMenuBtnsEnable();

		/** 设置送审 */
		setButtonsState_SendToAudit();

		// 支持产业链功能扩展
		if (this instanceof IBillExtendFun) {
			((IBillExtendFun) this).setExtendBtnsStat(2);
		}
		
		// wuxla 57 二次开发支持
		getInvokeEventProxy().setButtonStatus();
		updateButtonsAll();

	}

	/**
	 * 作者：王印芬 功能：设置初始化界面时的按钮状态 参数：无 返回：无 例外：无 日期：(2002-6-5 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志： 2002-10-31 wyf 加入对文件操作按钮的控制
	 */
	private void setButtonsStateInit() {
		// 毛利预估之返回,此按钮只起权限作用，真正起作用的按钮在GrossProfitUI
		getBtnManager().btnbillReturn.setVisible(false);
		getBtnManager().btnBillMaintainRevise.setVisible(false);
		// 卡片显示/列表显示
		getBtnManager().btnBillShift.setName(NCLangRes.getInstance()
				.getStrByID("SCMCOMMON", "UPPSCMCommon-000464")
		/* @res "列表显示" */);
		getBtnManager().btnBillReplenish.setEnabled(true);
		getBtnManager().btnBillRc.setEnabled(true);
		getBtnManager().btnBillIc.setEnabled(true);
		// 全选
		getBtnManager().btnListSelectAll.setEnabled(false);
		// 全消
		getBtnManager().btnListDeselectAll.setEnabled(false);
		// 放弃转单
		getBtnManager().btnListCancelTransform.setVisible(false);
		// 业务类型
		getBtnManager().btnBillBusitype.setEnabled(true);
		// 增加
		getBtnManager().btnBillAdd.setEnabled(true);
		// 修改
		getBtnManager().btnBillModify.setEnabled(false);
		// 保存
		getBtnManager().btnBillSave.setEnabled(false);
		// 行操作
		getBtnManager().btnBillRowOperation.setEnabled(false);
		// 放弃
		getBtnManager().btnBillCancel.setEnabled(false);
		// 审批
		getBtnManager().btnBillExecAudit.setEnabled(false);
		// 解冻
		getBtnManager().btnBillExecUnFreeze.setEnabled(false);
		// 执行
		getBtnManager().btnBillExecute.setEnabled(false);
		// 作废
		getBtnManager().btnBillAnnul.setEnabled(false);
		// ----------浏览
		getBtnManager().btnBillBrowse.setEnabled(true);
		// 查询
		getBtnManager().btnBillQuery.setEnabled(true);
		// 翻页
		getBtnManager().btnBillFirst.setEnabled(false);
		getBtnManager().btnBillPrevious.setEnabled(false);
		getBtnManager().btnBillNext.setEnabled(false);
		getBtnManager().btnBillLast.setEnabled(false);
		// 切换
		getBtnManager().btnBillShift.setEnabled(true);
		// 文档管理
		getBtnManager().btnBillDocManage.setEnabled(false);
		// 辅助
		getBtnManager().btnBillAssist.setEnabled(true);
		{
			// 只有打印可用
			getBtnManager().btnBillCopyBill.setEnabled(false);
			getBtnManager().btnBillReceivePlan.setEnabled(false);
			getBtnManager().btnBillPrePay.setEnabled(false);
			getBtnManager().btnBillRefresh.setEnabled(false);
			getBtnManager().btnBillSendToAudit.setEnabled(false);
			getBtnManager().btnBillLocate.setEnabled(false);
			getBtnManager().btnBillGrossProfit.setEnabled(false);
			getBtnManager().btnBillUsenum.setEnabled(false);
			getBtnManager().btnBillSaleNum.setEnabled(false);
			getBtnManager().btnBillByZYSOOrder.setEnabled(true);
			getBtnManager().btnBillSOPrice.setEnabled(false);
			getBtnManager().btnBillCoopWithSo.setEnabled(true);
			getBtnManager().btnBillPushCoopToSo.setEnabled(false);
			getBtnManager().btnBillInvSetQuery.setEnabled(false);
			getBtnManager().btnBillAp.setEnabled(false);
			getBtnManager().btnBillPrint.setEnabled(false);
			getBtnManager().btnBillPrintPreview.setEnabled(false);
			getBtnManager().btnBillCombin.setEnabled(false);
			getBtnManager().btnBillVendor.setEnabled(false);
			getBtnManager().btnBillInvReplace.setEnabled(false);
			getBtnManager().btnBillStatusQry.setEnabled(false);
			getBtnManager().btnBillLnkQry.setEnabled(false);
			getBtnManager().btnBillPayExecStat.setEnabled(false);
			// 合同查询
			getBtnManager().btnBillContractClass.setEnabled(false);
			getBtnManager().btnBillImportVendorCode.setEnabled(false);
		}
		getBtnManager().btnBillAddContinue.setEnabled(false);
		
		// 初始化业务类型
    PfUtilClient.retBusiAddBtn(
    m_btnManager.btnBillBusitype,getBtnManager().btnBillAdd,
    getCurPk_corp(),
    BillTypeConst.PO_ORDER );
    
//		PuTool.initBusiAddBtns(getBtnManager().btnBillBusitype,
//				getBtnManager().btnBillAdd, getPoCardPanel().getBillType(),
//				getCorpPrimaryKey());
		if (getBtnManager().btnBillBusitype != null
				&& getBtnManager().btnBillBusitype.getChildCount() > 0) {
		  getBtnManager().btnBillBusitype.getChildButtonGroup()[0].setSelected(true);
		  getBtnManager().btnBillBusitype.setCheckboxGroup(true);
			setCurBizeType(getBtnManager().btnBillBusitype
					.getChildButtonGroup()[0].getTag());
		}
		getBtnManager().btnBillTransportStatus.setEnabled(false);
		// 支持产业链功能扩展
		if (this instanceof IBillExtendFun) {
			((IBillExtendFun) this).setExtendBtnsStat(0);
		}
		
		//wuxla 57 二次开发支持
		getInvokeEventProxy().setButtonStatus();
		updateButtonsAll();
	}

	/**
	 * 作者：王印芬 功能：设置订单列表的按钮状态 参数：无 返回：无 例外：无 日期：(2002-6-5 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public void setButtonsStateList() {
		//
		if (isFromOtherBill()) {
			setButtonsStateListFromBills();
		} else {
			setButtonsStateListNormal();
		}

		updateButtonsAll();
	}

	/**
	 * 作者：王印芬 功能：设置其他单据转入时的列表按钮状态 参数：无 返回：无 例外：无 日期：(2002-6-5 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private void setButtonsStateListFromBills() {
		// 卡片显示/列表显示
		getBtnManager().btnListShift.setName(NCLangRes.getInstance()
				.getStrByID("SCMCOMMON", "UPPSCMCommon-000463")
		/* @res "卡片显示" */);
		// 列表无效
		getBtnManager().btnBillBusitype.setEnabled(false);
		getBtnManager().btnBillAdd.setEnabled(false);
		getBtnManager().btnBillSave.setEnabled(false);
		getBtnManager().btnBillSendToAudit.setEnabled(false);
		getBtnManager().btnBillCancel.setEnabled(false);
		getBtnManager().btnBillRowOperation.setEnabled(false);
		getBtnManager().btnBillLocate.setEnabled(false);
		getBtnManager().btnBillFirst.setEnabled(false);
		getBtnManager().btnBillPrevious.setEnabled(false);
		getBtnManager().btnBillNext.setEnabled(false);
		getBtnManager().btnBillLast.setEnabled(false);

		getBtnManager().btnBillCombin.setEnabled(false);

		getBtnManager().btnBillAp.setEnabled(false);
		getBtnManager().btnBillGrossProfit.setEnabled(false);
		getBtnManager().btnBillInvReplace.setEnabled(false);
		getBtnManager().btnBillInvSetQuery.setEnabled(false);
		getBtnManager().btnBillLnkQry.setEnabled(false);
		getBtnManager().btnBillPayExecStat.setEnabled(false);
		getBtnManager().btnBillPrePay.setEnabled(false);
		getBtnManager().btnBillReceivePlan.setEnabled(false);
		getBtnManager().btnBillSaleNum.setEnabled(false);
		getBtnManager().btnBillByZYSOOrder.setEnabled(false);
		getBtnManager().btnBillSOPrice.setEnabled(true);
		getBtnManager().btnBillCoopWithSo.setEnabled(false);
		getBtnManager().btnBillPushCoopToSo.setEnabled(false);
		getBtnManager().btnBillStatusQry.setEnabled(false);
		getBtnManager().btnBillVendor.setEnabled(false);
		getBtnManager().btnBillImportVendorCode.setEnabled(false);

		// 其它无效
		getBtnManager().btnBillCopyBill.setEnabled(false);
		getBtnManager().btnBillExecAudit.setEnabled(false);
		getBtnManager().btnBillExecUnAudit.setEnabled(false);
		getBtnManager().btnBillExecUnFreeze.setEnabled(false);
		getBtnManager().btnBillPrint.setEnabled(false);
		getBtnManager().btnBillPrintPreview.setEnabled(false);

		int iSelectedCount = getPoListPanel().getHeadSelectedCount();

		if (iSelectedCount == 0 || getBufferLength() < 1) {
			getBtnManager().btnListModify.setEnabled(false);
			getBtnManager().btnListDeselectAll.setEnabled(false);

			// 只有一张单据时，全选可用
			if (getBufferLength() == 1) {
				getBtnManager().btnListSelectAll.setEnabled(true);
			} else {
				getBtnManager().btnListSelectAll.setEnabled(false);
			}
		} else {
			if (iSelectedCount == 1) {
				getBtnManager().btnListModify.setEnabled(true);
				getBtnManager().btnListUsenum.setEnabled(true);
			} else {
				getBtnManager().btnListModify.setEnabled(false);
				getBtnManager().btnListUsenum.setEnabled(false);
			}

			getBtnManager().btnListDeselectAll.setEnabled(false);
			getBtnManager().btnListSelectAll.setEnabled(false);
		}
		// 查询
		getBtnManager().btnListQuery.setEnabled(false);
		// 作废
		getBtnManager().btnListAnnul.setEnabled(false);
		// 刷新
		getBtnManager().btnListRefresh.setEnabled(false);
		// 切换
		getBtnManager().btnListShift.setEnabled(true);
		// 文档管理
		getBtnManager().btnListDocManage.setEnabled(false);
		// 放弃转单
		getBtnManager().btnListCancelTransform.setVisible(true);
		getBtnManager().btnListCancelTransform.setEnabled(true);
		// 打印
		getBtnManager().btnListAssist.setEnabled(false);
		
		getBtnManager().btnBillTransportStatus.setEnabled(false);
		getBtnManager().btnBillAddContinue.setEnabled(false);
		// 支持产业链功能扩展
		if (this instanceof IBillExtendFun) {
			((IBillExtendFun) this).setExtendBtnsStat(4);
		}
		
		//wuxla 57 二次开发支持
		getInvokeEventProxy().setButtonStatus();

		updateButtonsAll();

	}

	/**
	 * 作者：王印芬 功能：设置订单列表的按钮状态 参数：无 返回：无 例外：无 日期：(2002-6-5 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志： 2002-10-17 wyf 修改冻结的订单可以修改的问题 2002-02-24 wyf
	 * 加入对存在并发的单据的按钮处理
	 */
	private void setButtonsStateListNormal() {

		// 卡片显示/列表显示
		getBtnManager().btnListShift.setName(NCLangRes.getInstance()
				.getStrByID("SCMCOMMON", "UPPSCMCommon-000463")
		/* @res "卡片显示" */);

		// 列表无效
		getBtnManager().btnBillReplenish.setEnabled(false);
		//
		getBtnManager().btnBillBusitype.setEnabled(false);
		getBtnManager().btnBillAdd.setEnabled(false);
		getBtnManager().btnBillSave.setEnabled(false);
		getBtnManager().btnBillCancel.setEnabled(false);
		getBtnManager().btnBillRowOperation.setEnabled(false);
		getBtnManager().btnBillLocate.setEnabled(false);
		getBtnManager().btnBillFirst.setEnabled(false);
		getBtnManager().btnBillPrevious.setEnabled(false);
		getBtnManager().btnBillNext.setEnabled(false);
		getBtnManager().btnBillLast.setEnabled(false);

		getBtnManager().btnBillCombin.setEnabled(false);

		getBtnManager().btnBillAp.setEnabled(false);
		getBtnManager().btnBillGrossProfit.setEnabled(false);
		getBtnManager().btnBillInvReplace.setEnabled(false);
		getBtnManager().btnBillInvSetQuery.setEnabled(false);
		getBtnManager().btnBillLnkQry.setEnabled(true);
		getBtnManager().btnBillPayExecStat.setEnabled(false);
		getBtnManager().btnBillPrePay.setEnabled(false);
		getBtnManager().btnBillReceivePlan.setEnabled(false);
		getBtnManager().btnBillSaleNum.setEnabled(false);
		getBtnManager().btnBillByZYSOOrder.setEnabled(false);
		getBtnManager().btnBillSOPrice.setEnabled(false);
		getBtnManager().btnBillCoopWithSo.setEnabled(false);
		getBtnManager().btnBillPushCoopToSo.setEnabled(false);
		getBtnManager().btnBillVendor.setEnabled(false);
		getBtnManager().btnBillImportVendorCode.setEnabled(false);

		// 打开、关闭
		getBtnManager().btnBillExecClose.setEnabled(false);
		getBtnManager().btnBillExecOpen.setEnabled(false);

		// 选中的行
		int iSelectedCount = getPoListPanel().getHeadSelectedCount();

		if (getBufferLength() == 0) {
			// 没有订单
			getBtnManager().btnBillRevise.setEnabled(false);
			getBtnManager().btnListSelectAll.setEnabled(false);
			getBtnManager().btnListDeselectAll.setEnabled(false);
			getBtnManager().btnListAnnul.setEnabled(false);
			getBtnManager().btnListModify.setEnabled(false);
			getBtnManager().btnListShift.setEnabled(true);
			getBtnManager().btnListDocManage.setEnabled(false);
			getBtnManager().btnListUsenum.setEnabled(false);
			// 审批
			getBtnManager().btnBillExecAudit.setEnabled(false);
			getBtnManager().btnBillExecUnFreeze.setEnabled(false);
			getBtnManager().btnBillExecute.setEnabled(false);
			getBtnManager().btnBillCopyBill.setEnabled(false);
		} else {
			// 有订单
			if (iSelectedCount == 0) {
				// 未选中订单
				getBtnManager().btnBillRevise.setEnabled(false);
				boolean bSelected = true;
				int[] iSel = getPoListPanel().getHeadTable().getSelectedRows();
				for (int i = 0; i < iSel.length; i++) {
					bSelected = getBufferVOManager().getVOs()[i]
							.isCanBeAudited();
					if (!bSelected)
						break;
				}
				getBtnManager().btnBillExecAudit.setEnabled(bSelected);
				boolean bUnSelected = true;
				for (int i = 0; i < iSel.length; i++) {
					bUnSelected = getBufferVOManager().getVOs()[i]
							.isCanBeUnAudited();
					if (!bUnSelected)
						break;
				}
				getBtnManager().btnBillExecUnAudit.setEnabled(bUnSelected);
				getBtnManager().btnListAnnul.setEnabled(false);
				getBtnManager().btnListModify.setEnabled(false);
				getBtnManager().btnListShift.setEnabled(false);
				getBtnManager().btnListUsenum.setEnabled(false);
				getBtnManager().btnBillExecAudit.setEnabled(false);
				getBtnManager().btnBillExecUnFreeze.setEnabled(false);
				getBtnManager().btnBillExecute.setEnabled(false);
				getBtnManager().btnBillCopyBill.setEnabled(false);
				// 打印
				getBtnManager().btnListPrint.setEnabled(false);
				getBtnManager().btnListPreview.setEnabled(false);
				
				getBtnManager().btnListSelectAll.setEnabled(true);
				getBtnManager().btnListDeselectAll.setEnabled(false);

				// 文档管理
				getBtnManager().btnListDocManage.setEnabled(false);
			} else {
				// 全消可用
				getBtnManager().btnListDeselectAll.setEnabled(true);
				// 全选
				if (iSelectedCount == getBufferLength()) {
					getBtnManager().btnListSelectAll.setEnabled(false);
				} else {
					getBtnManager().btnListSelectAll.setEnabled(true);
				}
				// 执行可用
				getBtnManager().btnBillExecute.setEnabled(true);

				// 选中一张订单
				if (iSelectedCount == 1) {
					// 修订
					getBtnManager().btnBillRevise.setEnabled(getBufferVOManager().getVOAt_LoadItemNo(getBufferVOManager().getVOPos())==null?false:getBufferVOManager().getVOAt_LoadItemNo(getBufferVOManager().getVOPos()).isSameCorp());
					// 拷贝
					getBtnManager().btnBillCopyBill.setEnabled(true);
					// 因此方法必在加载表体后进行，所以已知是否存在并发，只需直接调用VO，不需再加载
					// 是否可以切换
					OrderVO voCur = getBufferVOManager().getVOAt_LoadItemNo(
							getPoListPanel().getHeadSelectedVOPoses()[0]
									.intValue());
					if (voCur.getBodyVO() == null
							|| voCur.getBodyVO().length == 0) {
						getBtnManager().btnListShift.setEnabled(false);
					} else {
						getBtnManager().btnListShift.setEnabled(true);
					}
					// 是否可以修改、作废
          getBtnManager().btnListModify.setEnabled(voCur.isCanBeModified());
          getBtnManager().btnListAnnul.setEnabled(voCur.isCanBeAnnuled());
          //
					getBtnManager().btnListUsenum.setEnabled(true);
					// 审批、弃审、关闭、打开
					getBtnManager().btnBillExecAudit.setEnabled(voCur.isCanBeAudited());
					getBtnManager().btnBillExecUnAudit.setEnabled(voCur.isCanBeUnAudited());
					getBtnManager().btnBillExecUnFreeze.setEnabled(voCur.isCanBeUnFreezed());
					
					// 到货计划
					//57 wuxla 到货计划，预付款不可用，否则取到的订单数据是错误的
					getBtnManager().btnBillReceivePlan.setEnabled(false);
					// 预付款
					getBtnManager().btnBillPrePay.setEnabled(false);
					// 打印
					getBtnManager().btnBillPrint.setEnabled(true);
					// 预览
					getBtnManager().btnBillPrintPreview.setEnabled(true);
					// 可用量查询
					getBtnManager().btnBillUsenum.setEnabled(true);
					// 销量查询
					getBtnManager().btnBillSaleNum.setEnabled(true);
					// 应付款查询
					getBtnManager().btnBillAp.setEnabled(true);
					// 付款执行情况查询
					getBtnManager().btnBillPayExecStat.setEnabled(true);
					// 合并显示
					getBtnManager().btnBillCombin.setEnabled(true);
					// 逐级联查
					getBtnManager().btnBillLnkQry.setEnabled(true);
					// 二次开发的合同信息
					getBtnManager().btnBillContractClass.setEnabled(true);
					// 状态查询
					getBtnManager().btnBillStatusQry.setEnabled(true);
					// 供应商信息
					getBtnManager().btnBillVendor.setEnabled(true);
					// 替换件
					getBtnManager().btnBillInvReplace.setEnabled(true);
					// 成套件
					getBtnManager().btnBillInvSetQuery.setEnabled(true);
					// 文档管理
					getBtnManager().btnBillDocManage.setEnabled(true);
					// 导入供应商条码文件
					getBtnManager().btnBillImportVendorCode.setEnabled(true);
					// 运输状态
					getBtnManager().btnBillTransportStatus.setEnabled(true);
				}
				else {
					getBtnManager().btnBillCopyBill.setEnabled(false);
					// 有一个不可审批的单据，那么审批按钮不可用
					boolean bSelected = true;
					//有一个可以解冻的单据，那么解冻按钮可用
					boolean bUnFreeze = false;
					int[] iSel = getPoListPanel().getHeadTable()
							.getSelectedRows();
					for (int i = 0; i < iSel.length; i++) {
					  if (!bSelected && bUnFreeze)
					    break;
						bSelected = bSelected ? getBufferVOManager().getVOs()[iSel[i]].isCanBeAudited() : false ;
						bUnFreeze = !bUnFreeze ? getBufferVOManager().getVOs()[iSel[i]].isCanBeUnFreezed() : true;
					}
					getBtnManager().btnBillExecAudit.setEnabled(bSelected);
					boolean bUnSelected = true;
					for (int i = 0; i < iSel.length; i++) {
						bUnSelected = getBufferVOManager().getVOs()[iSel[i]]
								.isCanBeUnAudited();
						if (!bUnSelected)
							break;
					}
					getBtnManager().btnBillExecUnAudit.setEnabled(bUnSelected);
					getBtnManager().btnBillExecUnFreeze.setEnabled(bUnFreeze);
					getBtnManager().btnListShift.setEnabled(false);
					getBtnManager().btnListModify.setEnabled(false);
					getBtnManager().btnListAnnul.setEnabled(true);

					getBtnManager().btnListUsenum.setEnabled(false);
					
					// 到货计划
					getBtnManager().btnBillReceivePlan.setEnabled(false);
					// 预付款
					getBtnManager().btnBillPrePay.setEnabled(false);
					// 打印
					getBtnManager().btnBillPrint.setEnabled(false);
					// 预览
					getBtnManager().btnBillPrintPreview.setEnabled(false);
					// 可用量查询
					getBtnManager().btnBillUsenum.setEnabled(false);
					// 销量查询
					getBtnManager().btnBillSaleNum.setEnabled(false);
					// 应付款查询
					getBtnManager().btnBillAp.setEnabled(false);
					// 付款执行情况查询
					getBtnManager().btnBillPayExecStat.setEnabled(false);
					// 合并显示
					getBtnManager().btnBillCombin.setEnabled(false);
					// 逐级联查
					getBtnManager().btnBillLnkQry.setEnabled(false);
					// 二次开发的合同信息
					getBtnManager().btnBillContractClass.setEnabled(false);
					// 状态查询
					getBtnManager().btnBillStatusQry.setEnabled(false);
					// 供应商信息
					getBtnManager().btnBillVendor.setEnabled(false);
					// 替换件
					getBtnManager().btnBillInvReplace.setEnabled(false);
					// 成套件
					getBtnManager().btnBillInvSetQuery.setEnabled(false);
					// 文档管理
					getBtnManager().btnBillDocManage.setEnabled(false);
					// 导入供应商条码文件
					getBtnManager().btnBillImportVendorCode.setEnabled(false);
					// 运输状态
					getBtnManager().btnBillTransportStatus.setEnabled(false);
				}
				// 打印
				getBtnManager().btnListAssist.setEnabled(true);
				getBtnManager().btnListPrint.setEnabled(true);
				getBtnManager().btnListPreview.setEnabled(true);
			}
		}
		// 查询
		getBtnManager().btnListQuery.setEnabled(true);
		// 刷新
		if (isEverQueryed()) {
			getBtnManager().btnListRefresh.setEnabled(true);
		} else {
			getBtnManager().btnListRefresh.setEnabled(false);
		}

		// 放弃转单
		getBtnManager().btnListCancelTransform.setVisible(false);
		getBtnManager().btnBillAddContinue.setEnabled(false);
		// 支持产业链功能扩展
		if (this instanceof IBillExtendFun) {
			((IBillExtendFun) this).setExtendBtnsStat(3);
		}
		// 5.6 支持列表批送审
		int[] iSel = getPoListPanel().getHeadTable().getSelectedRows();
		if(iSel != null && iSel.length > 0 && iSel[0] != -1){
      OrderVO voFirst = getBufferVOManager().getVOs()[iSel[0]];
  		getBtnManager().btnBillSendToAudit.setEnabled(PuTool
          .isNeedSendToAudit(BillTypeConst.PO_ORDER, voFirst));
		}else{
		  getBtnManager().btnBillSendToAudit.setEnabled(false);
		}
		
		//wuxla 57 二次开发支持
		getInvokeEventProxy().setButtonStatus();
		updateButtonsAll();

	}

	/**
	 * 改变界面按钮状态
	 */
	protected void updateButtonsAll() {
		int iLen = getBtnManager().getBtnTree(this).getButtonArray().length;
		for (int i = 0; i < iLen; i++) {
			// 递归调用
			update(getBtnManager().getBtnTree(this).getButtonArray()[i]);
		}
		super.updateButtons();
	}

	/**
	 * 更新按钮状态（递归方式）
	 */
	private void update(ButtonObject bo) {
		updateButton(bo);
		if (bo.getChildCount() > 0) {
			for (int i = 0, len = bo.getChildCount(); i < len; i++)
				update(bo.getChildButtonGroup()[i]);
		}
	}

	/**
	 * 函数的功能、用途、对属性的更改，以及函数执行前后对象的状态。
	 * 
	 * @param 参数说明
	 * @return 返回值
	 * @exception 异常描述
	 * @see 需要参见的其它内容
	 * @since 从类的那一个版本，此方法被添加进来
	 * @return java.lang.String
	 */
	private void setCupsourcebilltype(String sNewBillType) {
		m_cupsourcebilltype = sNewBillType;
	}

	/**
	 * 函数的功能、用途、对属性的更改，以及函数执行前后对象的状态。
	 * 
	 * @param 参数说明
	 * @return 返回值
	 * @exception 异常描述
	 * @see 需要参见的其它内容
	 * @since 从类的那一个版本，此方法被添加进来
	 * @param newCurBizeType
	 *            java.lang.String
	 */
	private void setCurBizeType(java.lang.String newCurBizeType) {
		m_cbiztype = newCurBizeType;
	}

	/**
	 * 设置当前操作状态(如"增加","查询"等)
	 * 
	 * @param 参数说明
	 * @return 返回值
	 * @exception 异常描述
	 * @see 需要参见的其它内容
	 * @since 从类的那一个版本，此方法被添加进来
	 * @param newCurOperState
	 *            java.lang.String
	 */
	protected void setCurOperState(int newCurOperState) {
		m_nCurOperState = newCurOperState;
	}

	/**
	 * 函数的功能、用途、对属性的更改，以及函数执行前后对象的状态。
	 * 
	 * @param 参数说明
	 * @return 返回值
	 * @exception 异常描述
	 * @see 需要参见的其它内容
	 * @since 从类的那一个版本，此方法被添加进来
	 * @return java.lang.String
	 */
	protected void setCurPk_corp(String sPk_corp) {
		m_pk_corp = sPk_corp;
	}

	/**
	 * @param 参数说明
	 * @return 返回值
	 * @exception 异常描述
	 * @see 需要参见的其它内容
	 * @since 从类的那一个版本，此方法被添加进来。（可选）
	 * 
	 */
	private void setEverQueryed(boolean newQueryed) {

		m_bEverQueryed = newQueryed;
	}

	/**
	 * 作者：王印芬 功能：m_hmapUpSourcePrayTs的SET方法 参数：Hashtable htNew 新的哈希表 返回：无 例外：无
	 * 日期：(2001-10-20 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	private void setHmapUpSourcePrayTs(HashMap hmapNew) {
		m_hmapUpSourcePrayTs = hmapNew;
	}

	/**
	 * @param 参数说明
	 * @return 返回值
	 * @exception 异常描述
	 * @see 需要参见的其它内容
	 * @since 从类的那一个版本，此方法被添加进来。（可选）
	 * 
	 */
	private void setIsFromOtherBill(boolean bValue) {

		m_bFromOtherBill = bValue;
	}

	/**
	 * 作者：王印芬 功能：字段m_iOrgBufferLen的SET方法 参数：无 返回：无 例外：无 日期：(2003-4-01 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */

	private void setOrgBufferLen(int iNewLen) {
		m_iOrgBufferLen = iNewLen;
	}

	/**
	 * 从借入单生成订单或请购单生成订单界面返回订单维护卡片界面
	 * 
	 * @param
	 * @return
	 * @exception
	 * @see
	 * @since 2001-04-28
	 */
	private void shiftGrossProfitToPoCard() {

		remove(getGrossProfitPanel());
		add(getPoCardPanel(), java.awt.BorderLayout.CENTER);

		setCurOperState(m_nflagSave);
		setButtons(getBtnManager().getBtnaBill(this));

		if (getCurOperState() == STATE_BILL_EDIT) {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000041")/* @res "编辑订单" */);
		} else {
			showHintMessage(NCLangRes.getInstance().getStrByID(
					"4004020201", "UPP4004020201-000042")/* @res "浏览订单" */);
		}
		updateUI();
	}

	// 按钮组
	protected PoToftPanelButton m_btnManager = null;

	// 公司ID
	private String[] saPkCorp = null;

	// 用户的当前及操作状态:卡片浏览,编辑,普通列表、毛利预估
	protected static final int STATE_BILL_BROWSE = 0;

	protected static final int STATE_BILL_EDIT = 1;

	protected static final int STATE_BILL_GROSS_EVALUATE = 3;

	protected static final int STATE_LIST_BROWSE = 2;

	/**
	 * 作者：王印芬 功能： 得到按钮管理器 参数：无 返回：PoToftPanelButton 例外：无 日期：(2004-4-01 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	protected abstract PoToftPanelButton getBtnManager();

	/**
	 * 作者：王印芬 功能：VO管理器的GET方法 参数：无 返回：无 例外：无 日期：(2003-4-01 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public PoVOBufferManager getBufferVOManager() {
		if (m_bufPoVO == null) {
			m_bufPoVO = new PoVOBufferManager();
			m_bufPoVO.addRegister(this);
		}

		return m_bufPoVO;
	}

	/**
	 * 功能：获取存量查询对话框
	 */
	private ATPForOneInvMulCorpUI getDlgAtp() {
		if (null == m_atpDlg) {
			m_atpDlg = new ATPForOneInvMulCorpUI(this);
		}
		return m_atpDlg;
	}

	/**
	 * 作者：WYF 功能：得到查询的VO 参数：无 返回：OrderVO[] 查询到的VO数组，其中只有第一张含体，其他只有头 例外：无
	 * 日期：(2003-11-05 13:24:16) 修改日期，修改人，修改原因，注释标志：
	 */
	protected abstract OrderVO[] getQueriedVOHead() throws Exception;

	/**
	 * 作者：WYF 功能：初始化是卡片还是列表 参数：无 返回：boolean 卡片返回true；列表返回false
	 * 此方法默认返回true，子类若初始化为列表可重写该方法 例外：无 日期：(2004-06-23 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	protected boolean isInitStateBill() {
		return true;
	}

	/**
	 * 查询当前单据审批状态
	 */

	protected void onBillAuditStatusQuery() {
		// 当前界面存在单据
		if (getBufferLength() > 0) {
			// 如果该单据处于正在审批状态，执行下列语句：
			// 表头币种处理
			if (getOrderDataVOAt(getBufferVOManager().getVOPos()) != null
					&& getOrderDataVOAt(getBufferVOManager().getVOPos())
							.getHeadVO().getCcurrencytypeid() == null) {
				getOrderDataVOAt(getBufferVOManager().getVOPos()).getHeadVO()
						.setCcurrencytypeid(
								getOrderDataVOAt(
										getBufferVOManager().getVOPos())
										.getBodyVO()[0].getCcurrencytypeid());

			}
			nc.ui.pub.workflownote.FlowStateDlg approvestatedlg = new nc.ui.pub.workflownote.FlowStateDlg(
					this, BillTypeConst.PO_ORDER, getOrderDataVOAt(
							getBufferVOManager().getVOPos()).getHeadVO()
							.getCbiztype(), getOrderDataVOAt(
							getBufferVOManager().getVOPos()).getHeadVO()
							.getPrimaryKey());
			approvestatedlg.showModal();
		}
		showHintMessage(NCLangRes.getInstance().getStrByID("common",
				"UCH035")/* @res "审批状态查询成功" */);
	}

	/**
	 * 作者：WYF 功能：作废订单VO数组，并于其中检查是否可作废 参数：ToftPanel pnlToft TOFTPANEL OrderVO[]
	 * voaDelete 订单VO数组 返回：boolean 成功为true；放弃或失败为false 例外：无 日期：(2004-6-3
	 * 13:24:16) 修改日期，修改人，修改原因，注释标志：
	 */
	public static boolean onDiscard(ToftPanel pnlToft, OrderVO[] voaDelete) {

		if (voaDelete == null) {
			return false;
		}

		int iDeletedLen = voaDelete.length;
		// 需作废的订单
		for (int i = 0; i < iDeletedLen; i++) {
			Integer iStatus = voaDelete[i].getHeadVO().getForderstatus();
			if (iStatus.compareTo(nc.vo.scm.pu.BillStatus.FREE) != 0
					&& iStatus.compareTo(nc.vo.scm.pu.BillStatus.AUDITFAIL) != 0) {
				// 以下情况不可作废
				if (iDeletedLen == 1) {
					MessageDialog.showHintDlg(pnlToft, NCLangRes
							.getInstance().getStrByID("SCMCOMMON",
									"UPPSCMCommon-000270")/*
															 * @res "提示"
															 */, NCLangRes.getInstance().getStrByID(
							"4004020201", "UPP4004020201-000078")/*
																	 * @res
																	 * "订单非自由状态，请检查"
																	 */);
				} else {
					MessageDialog.showHintDlg(pnlToft, NCLangRes
							.getInstance().getStrByID("SCMCOMMON",
									"UPPSCMCommon-000270")/*
															 * @res "提示"
															 */, NCLangRes.getInstance().getStrByID(
							"4004020201", "UPP4004020201-000079")/*
																	 * @res
																	 * "存在非自由状态的订单，请检查"
																	 */);
				}
				return false;
			}

			// 作废前处理
			voaDelete[i].setStatus(VOStatus.DELETED);

			// 进行到货计划检查
			try {
				voaDelete[i].validateDiscard();
			} catch (ValidationException e) {
				PuTool.outException(pnlToft, e);
				return false;
			}

		}
		for (int i = 0; i < voaDelete.length; i++) {
			if (voaDelete[i].getHeadVO().getBcooptoso() != null
					&& voaDelete[i].getHeadVO().getBcooptoso().booleanValue()) {
				MessageDialog.showHintDlg(pnlToft,
						NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000270")/* @res "提示" */,
						NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-V56013")/* @res "订单以协同生成销售订单,不能作废" */);
				return false;
			}
		}
		// 设置cuserid
		PoPublicUIClass.setCuserId(voaDelete);

		// 询问
		int iRet = MessageDialog.showYesNoDlg(pnlToft,
				NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000219")/* @res "确定" */,
				NCLangRes.getInstance().getStrByID("common",
						"4004COMMON000000069")/* @res "是否确认作废？" */,
				UIDialog.ID_NO);
		if (iRet != MessageDialog.ID_YES) {
			return false;
		}

		pnlToft.showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000081")/* @res "作废订单..." */);

		// 第一次保存
		boolean bContinue = true;
		boolean bException = false;
		// ==================保存
		while (bContinue) {
			pnlToft.showHintMessage(NCLangRes.getInstance()
					.getStrByID("4004020201", "UPP4004020201-000082")/*
																		 * @res
																		 * "保存订单..."
																		 */);
			try {
				PfUtilClient.processBatch("DISCARD", BillTypeConst.PO_ORDER,
						PoPublicUIClass.getLoginDate().toString(), voaDelete);
				bContinue = false;
			} catch (Exception e) {
				if (e instanceof nc.vo.scm.pub.SaveHintException) {
					// 最高限价、最高库存、合同提示
					int iMesRet = pnlToft.showYesNoMessage(e.getMessage());
					if (iMesRet == MessageDialog.ID_YES) {
						// 继续循环
						bContinue = true;
						// 是否第一次
						for (int i = 0; i < iDeletedLen; i++) {
							voaDelete[i].setFirstTime(false);
						}
					} else {
						bException = true;
						break;
					}
				} else if (e instanceof BusinessException
						|| e instanceof ValidationException) {
					pnlToft.showWarningMessage(e.getMessage());
					bException = true;
					break;
				} else {
					pnlToft.showErrorMessage(OrderPubVO.returnHintMsgWhenNull(e
							.getMessage()));
					bException = true;
					break;
				}
			}
		}

		// 作废未成功时重置数据
		if (bException) {
			for (int i = 0; i < iDeletedLen; i++) {
				voaDelete[i].setFirstTime(true);
				voaDelete[i].setStatus(VOStatus.UNCHANGED);
			}
			return false;
		}
		pnlToft.showHintMessage(NCLangRes.getInstance().getStrByID(
				"common", "4004COMMON000000068")/* @res "作废成功" */);
		return true;

	}

	/**
	 * 作者：晁志平 功能：保存订单方法，该方法实应为本类的一个私有方法，但限于未合理构建，导致不伦不类。 有时间应调整 参数：无 返回：登录的公司ID
	 * 例外：无 日期：(2005-02-04 10:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	public OrderVO onSave(ToftPanel pnlToft, PoCardPanel pnlBill,
			OrderVO voSaved) {

		nc.vo.scm.pu.Timer timeDebug = new nc.vo.scm.pu.Timer();
		timeDebug.start();

		// 获取VO后进行保存，并得到保存后表头、新增表体行的主键
		// 数组key第一个元素为表头主键，随后元素为表体主键
		if (pnlToft == null || pnlBill == null || voSaved == null) {
			return null;
		}
		boolean firstRow = false;
		String sBaseid = (String) pnlBill.getBillModel().getValueAt(0,
				"cbaseid");
		if (sBaseid == null) {
			firstRow = true;
		}
		// 公司
		// boolean bAutoSendToAudit =
		// PuTool.isAutoSendToAudit(voSaved.getHeadVO().getPk_corp()) ;
		for (int i = 0; i < voSaved.getBodyVO().length; i++) {
			if (firstRow) {
				voSaved.getBodyVO()[i].setRowNo(i + 2);
			} else {
				voSaved.getBodyVO()[i].setRowNo(i + 1);
			}
		}
		// 第一次保存
		boolean bContinue = true;
		// 区分是新增、修改{0,新增；1，修改}
		final int iCurOperType = PuPubVO.getString_TrimZeroLenAsNull(voSaved
				.getHeadVO().getPrimaryKey()) == null ? 0 : 1;
		// ==================保存
		// 支持天音送审后置为正在审批状态需求2005-01-29
		ArrayList userObj = new ArrayList();
		userObj.add(PoPublicUIClass.getLoginUser());
		userObj.add(PoPublicUIClass.getLoginDate());
		while (bContinue) {
			pnlToft.showHintMessage(NCLangRes.getInstance()
					.getStrByID("4004020201", "UPP4004020201-000082")/*
																		 * @res
																		 * "保存订单..."
																		 */);
			try {
				Object oRet = nc.ui.pub.pf.PfUtilClient
						.processActionNoSendMessage(pnlToft, "SAVEBASE",
								BillTypeConst.PO_ORDER, PoPublicUIClass
										.getLoginDate().toString(), voSaved,
								userObj, null, null);
				// 与平台：以免出指派框时流程无法进行
				if (oRet == null) {
					return null;
				}
				// 得到主键
				ArrayList arraylist = (ArrayList) oRet;
				// sOrderId = (String) arraylist.get(0);
				voSaved = (OrderVO) arraylist.get(1);

				bContinue = false;
			} catch (Exception e) {
				PuTool.printException(e);

				if (e instanceof nc.vo.scm.pub.SaveHintException) {
					// 合同提示
					int iRet = pnlToft.showYesNoMessage(e.getMessage());
					if (iRet == MessageDialog.ID_YES) {
						// 继续循环
						bContinue = true;
						// 是否第一次
						voSaved.setFirstTime(false);
					} else {
						pnlToft.showHintMessage(NCLangRes
								.getInstance().getStrByID("SCMCOMMON",
										"UPPSCMCommon-000010")/*
																 * @res "保存失败"
																 */);
						return null;
					}
				} else if (e instanceof nc.vo.scm.pub.StockPresentException) {
					// 超现存量提示
					int iRet = pnlToft.showYesNoMessage(e.getMessage());
					if (iRet == MessageDialog.ID_YES) {
						// 继续循环
						bContinue = true;
						// 是否第一次
						voSaved.setFirstTimeSP(false);
					} else {
						pnlToft.showHintMessage(NCLangRes
								.getInstance().getStrByID("SCMCOMMON",
										"UPPSCMCommon-000010")/*
																 * @res "保存失败"
																 */);
						return null;
					}
				} else if (e instanceof RwtPoToPrException) {
					// 请购单累计数量超出提示
					int iRet = pnlToft.showYesNoMessage(e.getMessage());
					if (iRet == MessageDialog.ID_YES) {
						// 继续循环
						bContinue = true;
						// 是否第一次
						voSaved.setFirstTime(false);
					} else {
						pnlToft.showHintMessage(NCLangRes
								.getInstance().getStrByID("SCMCOMMON",
										"UPPSCMCommon-000010")/*
																 * @res "保存失败"
																 */);
						return null;
					}
				} else if (e instanceof MaxPriceException) {
					hMaxPrice = ((MaxPriceException) e).getIRows();
					m_renderYellowAlarmLine.setRight(true);
					m_renderYellowAlarmLine.setRowRender(pnlBill.getBillTable(), hMaxPrice);
					// pnlBill.getBillTable().gett
					// 最高限价超出提示
					pnlBill.getBillTable().repaint();
					String[] sWarn = (String[]) hMaxPrice.values().toArray(
							new String[hMaxPrice.size()]);
					String sShow = new String();
					for (int i = 0; i < sWarn.length; i++) {
						sShow += sWarn[i] + ",";
					}
					int iRet = pnlToft.showYesNoMessage(
							NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-V56014",null,new String[]{sShow})
							/* @res "序号{0}存在超出最高限价的行,是否继续?" */);

					if (iRet == MessageDialog.ID_YES) {
						// 继续循环
						bContinue = true;
						// 是否第一次
						voSaved.setFirstTimePrice(false);
						m_renderYellowAlarmLine.setRight(false);
						pnlBill.getBillTable().repaint();
					} else {
						pnlToft.showHintMessage(NCLangRes
								.getInstance().getStrByID("SCMCOMMON",
										"UPPSCMCommon-000010")/*
																 * @res "保存失败"
																 */);
						return null;
					}
				} else if (e instanceof MaxStockException) {
					// 最高库存超出提示
					int iRet = pnlToft.showYesNoMessage(e.getMessage());
					if (iRet == MessageDialog.ID_YES) {
						// 继续循环
						bContinue = true;
						// 是否第一次
						voSaved.setFirstTimeStock(false);
					} else {
						pnlToft.showHintMessage(NCLangRes
								.getInstance().getStrByID("SCMCOMMON",
										"UPPSCMCommon-000010")/*
																 * @res "保存失败"
																 */);
						return null;
					}
				} else {
					// 异常报告
					if (e instanceof BusinessException
							|| e instanceof ValidationException
							|| (e.getMessage() != null && (e.getMessage()
									.indexOf(
											NCLangRes.getInstance()
													.getStrByID("common",
															"UC000-0000275")/*
																			 * @res
																			 * "供应商"
																			 */) >= 0
									|| e
											.getMessage()
											.indexOf(
													NCLangRes
															.getInstance()
															.getStrByID(
																	"4004020201",
																	"UPP4004020201-000083")/*
																							 * @res
																							 * "采购"
																							 */) >= 0
									|| e
											.getMessage()
											.indexOf(
													NCLangRes
															.getInstance()
															.getStrByID(
																	"4004020201",
																	"UPP4004020201-000084")/*
																							 * @res
																							 * "订单"
																							 */) >= 0 || e.getMessage().indexOf(
									NCLangRes.getInstance()
											.getStrByID("4004020201",
													"UPP4004020201-000085")/*
																			 * @res
																			 * "核准"
																			 */) >= 0))) {
						pnlToft.showHintMessage(e.getMessage());
						pnlToft.showErrorMessage(e.getMessage());
					} else {
						pnlToft.showErrorMessage(nc.vo.po.pub.OrderPubVO
								.returnHintMsgWhenNull(e.getMessage()));
					}

					pnlToft.showHintMessage(NCLangRes.getInstance()
							.getStrByID("SCMCOMMON", "UPPSCMCommon-000010")/*
																			 * @res
																			 * "保存失败"
																			 */);
					return null;
				}
			}
		}
		m_renderYellowAlarmLine.setRight(false);
		hMaxPrice.clear();
		pnlToft.showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000086")/* @res "订单保存成功" */);
		timeDebug.addExecutePhase("订单保存");/*-=notranslate=-*/

		/*
		 * 重新设置单据状态
		 */
		voSaved.setStatus(VOStatus.UNCHANGED);
		/*
		 * 重新查询单据: 1、解决保存动作后仍有动作配置的刷新问题，因为在这种情况下保存脚本返回的VO相对此时刻的数据库来说仍是不一致的中间状态VO
		 * 2、解决用户自定义模板时定义了本币金额、辅币金额六字段，但我们目前的编辑订单换算逻辑里无这六个字段的实时换算，而是在保存前统一处理的问题
		 */
		OrderVO[] voLightRefreshed = null;
		try {
			/*
			 * czp V31 流量优化，减少下行传输数据量 voSaved =
			 * OrderBO_Client.queryOrderVOByKey(voSaved.getHeadVO().getCorderid()) ;
			 */
			voLightRefreshed = OrderHelper.queryOrderVOsLight(
					new String[] { voSaved.getHeadVO().getCorderid() }, "SAVE");
			timeDebug.addExecutePhase("重新查询订单");/*-=notranslate=-*/
		} catch (Exception e) {
			PuTool.printException(e);
			pnlToft.showWarningMessage(NCLangRes.getInstance()
					.getStrByID("4004020201", "UPP4004020201-000087")/*
																		 * @res
																		 * "订单保存成功，但出现并发操作，请刷新后查看该单据"
																		 */);
			voSaved = null;
		}
		timeDebug.showAllExecutePhase("订单单纯UI保存");/*-=notranslate=-*/
		// 刷新界面显示VO
		boolean bRefreshSucc = voSaved.setLastestVo(voLightRefreshed[0],
				iCurOperType);
		// 刷新失败处理{一般不会发生此种情况}
		if (!bRefreshSucc) {
			try {
				voSaved = OrderHelper.queryOrderVOByKey(voSaved.getHeadVO()
						.getCorderid());
				timeDebug.addExecutePhase("重新查询订单");/*-=notranslate=-*/
			} catch (Exception e) {
				PuTool.printException(e);
				pnlToft.showWarningMessage(NCLangRes.getInstance()
						.getStrByID("4004020201", "UPP4004020201-000087")/*
																			 * @res
																			 * "订单保存成功，但出现并发操作，请刷新后查看该单据"
																			 */);
				voSaved = null;
			}
		}
		// 业务日志 
		// V56 改为后台处理
//		Operlog operlog = new Operlog();
//		voSaved.getOperatelogVO().setOpratorname(
//				nc.ui.pub.ClientEnvironment.getInstance().getUser()
//						.getUserName());
//		voSaved.getOperatelogVO().setCompanyname(
//				nc.ui.pub.ClientEnvironment.getInstance().getCorporation()
//						.getUnitname());
//		voSaved.getOperatelogVO().setOperatorId(
//				nc.ui.pub.ClientEnvironment.getInstance().getUser()
//						.getPrimaryKey());
//		operlog.insertBusinessExceptionlog(voSaved, "保存", "保存",
//				nc.vo.scm.funcs.Businesslog.MSGMESSAGE,
//				nc.vo.scm.pu.BillTypeConst.PO_ORDER, nc.ui.sm.cmenu.Desktop
//						.getApplet().getParameter("USER_IP"));
		return voSaved;

	}

	/**
	 * 作者：WYF 功能：送审当前订单 参数：无 返回：boolean 成功为true；否则为false; 例外：无 日期：(2003-10-28
	 * 13:24:16) 修改日期，修改人，修改原因，注释标志：
	 */
	public static boolean onSendToAudit(ToftPanel pnlToft, OrderVO voOrder) {

		pnlToft.showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000055")/* @res "送审当前订单" */);
		nc.vo.scm.pu.Timer timeDebug = new nc.vo.scm.pu.Timer();
		timeDebug.start();

		try {
		} catch (Exception e) {
			PuTool.outException(pnlToft, e);
			return false;
		}
		timeDebug.addExecutePhase("送审");/*-=notranslate=-*/
		timeDebug.addExecutePhase("采购订单送审");/*-=notranslate=-*/

		pnlToft.showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000042")/* @res "浏览订单" */);
		return true;

	}

	/**
	 * 作者：WYF 功能：进行VO转换的后续处理
	 * 调用此方法前请先充实方法nc.ui.po.pub.PoChangeUI.getChangedVOs(String,
	 * AggregatedValueObject [], Container, String) 参数：voaSourceVO voaSourceVO
	 * 转换前的VO数组 返回：无 例外：无 日期：(2002-3-13 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	protected void processAfterChange(String sUpBillType,
			AggregatedValueObject[] voaSourceVO) {
		if(voaSourceVO==null){
			return;
		}
		Timer time = new Timer();
		time.start();
		time.addExecutePhase("转单加载");
		// 根据来源单据类型决定采用哪种处理
		OrderVO[] voaRet = null;
		// 缓存从请购单来的数据，过滤追加数据
		if (voaSourceVO[0] instanceof PraybillVO && sUpBillType.equals("20")) {
			for (int i = 0; i < voaSourceVO.length; i++) {
				for (int j = 0; j < voaSourceVO[i].getChildrenVO().length; j++) {
					hPraybillVO.put(((PraybillItemVO) voaSourceVO[i].getChildrenVO()[j]).getCpraybill_bid(),
							(PraybillItemVO) voaSourceVO[i].getChildrenVO()[j]);
				}
			}
		}

		voaRet = PoChangeUI.getChangedVOs(sUpBillType, voaSourceVO, this, getCurBizeType());

		if (voaRet == null) {
			setCurPk_corp(PoPublicUIClass.getLoginPk_corp());
			return;
		}
		for(OrderVO vo : voaRet){
			if(vo.getHeadVO().getCvendormangid()!=null){
				continue;
			}else{
				for(OrderItemVO voBody : vo.getBodyVO()){
					voBody.setCcurrencytypeid(null);
				}
			}
		}
		if("30".equals(sUpBillType)&&!((OrderVO[])voaSourceVO)[0].getIsCoop()){
			getPoCardPanel().setSoZ(true);
		}
		String ccurrencytypeidHead = null;
		String strCoperator = null;
		for (int i = 0; i < voaRet.length; i++) {
			ccurrencytypeidHead = voaRet[i].getHeadVO().getCcurrencytypeid();
			if (ccurrencytypeidHead == null
					|| (ccurrencytypeidHead != null && ccurrencytypeidHead.trim().length() == 0)) {
				voaRet[i].getHeadVO().setCcurrencytypeid(voaRet[i].getBodyVO()[0].getCcurrencytypeid());
			}

			voaRet[i].getHeadVO().setCoperator(getUserID());
			//wuxla 电子商务推订单时，公司取电子商务表头的“采购公司”，不是当前公司
			if(BillTypeConst.EC_PUSHORDERBILL.equals(sUpBillType)){
				continue;
			}
			voaRet[i].getHeadVO().setPk_corp(getCorp());
		}

		// 进行缓存转换、显示等工作
		setBillList(false);
		setIsFromOtherBill(true);
		setCupsourcebilltype(sUpBillType);

		// 设置当前公司
		setCurPk_corp(voaRet[0].getHeadVO().getPk_corp());

		// 转移原来缓存中的单据到QueryTemp中
		getBufferVOFrmBill().resetVOsNoListener(getBufferVOManager());
		// 设置VO到界面
		getBufferVOManager().resetVOsNoListenner(voaRet);
		getBufferVOManager().setVOPos(0);
		setCurOperState(STATE_BILL_EDIT);
		getInvokeEventProxy().beforeSetBillVOsToListHead(getBufferVOManager().getHeadVOs());
		onBillList();

		// 设置按钮状态
		setButtonsStateList();

		showHintMessage(NCLangRes.getInstance().getStrByID(
				"4004020201", "UPP4004020201-000042")/* @res "浏览订单" */);
		time.showAllExecutePhase("转单加载完毕");
	}

	/**
	 * 作者：WYF 功能：进行VO转换的后续处理
	 * 调用此方法前请先充实方法nc.ui.po.pub.PoChangeUI.getChangedVOs(String,
	 * AggregatedValueObject [], Container, String) 参数：voaSourceVO voaSourceVO
	 * 转换前的VO数组 返回：无 例外：无 日期：(2002-3-13 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	protected void processAfterChangeAdd(String sUpBillType,
			AggregatedValueObject[] voaSourceVO) {

		// 根据来源单据类型决定采用哪种处理
		OrderVO[] voaRet = null;
		// 缓存从请购单来的数据，过滤追加数据
		if (voaSourceVO[0] instanceof PraybillVO && sUpBillType.equals("20")) {
			for (int i = 0; i < voaSourceVO.length; i++) {
				for (int j = 0; j < voaSourceVO[i].getChildrenVO().length; j++) {
					hPraybillVO.put(((PraybillItemVO) voaSourceVO[i]
							.getChildrenVO()[j]).getCpraybill_bid(),
							(PraybillItemVO) voaSourceVO[i].getChildrenVO()[j]);
				}
			}
		}
		setIsFromOtherBill(true);
		// 重新追加的数据
		voaRet = PoChangeUI.getChangedVOs(sUpBillType, voaSourceVO, this,
				getCurBizeType());
		// 当前卡片上的数据
		OrderVO voCur =(OrderVO) getPoCardPanel().getBillValueVO(OrderVO.class.getName(),OrderHeaderVO.class.getName(),OrderItemVO.class.getName());
		OrderVO newvo = null;
		try {
			newvo = (OrderVO) ObjectUtils.serializableClone(voCur);
		} catch (Exception e) {
			SCMEnv.out(e);
		}
		// 取得必输项目的itemkey
		BillItem[] headItems = getPoCardPanel().getHeadShowItems();

		// 获得非空单据头Item的Key
		String[] headNotNullKeys = new String[] { "cbiztype", "cvendorbaseid",
				"cpurorganization" };

		for (int i = 0; i < voaRet.length; i++) {
			boolean bRight = true;
			for (int j = 0; j < headNotNullKeys.length; j++) {
				if (voaRet[i].getHeadVO().getAttributeValue(headNotNullKeys[j]) != null
						&& !voaRet[i].getHeadVO().getAttributeValue(
								headNotNullKeys[j]).equals(
								newvo.getHeadVO().getAttributeValue(
										headNotNullKeys[j]))) {
					bRight = false;
					for (int m = 0; m < voaRet[i].getBodyVO().length; m++) {
						hPraybillVO.remove(voaRet[i].getBodyVO()[m]
								.getCupsourcebillrowid());
					}
					break;
				}
			}
			if (bRight) {
				Vector vecData = new Vector();
				Vector vecNewData = new Vector();
				for (int n = 0; n < newvo.getBodyVO().length; n++) {
					vecData.add(newvo.getBodyVO()[n]);
				}
				for (int m = 0; m < voaRet[i].getBodyVO().length; m++) {
					vecNewData.add(voaRet[i].getBodyVO()[m]);
				}
				OrderItemVO[] newitemvos = new OrderItemVO[vecNewData.size()];
				vecNewData.copyInto(newitemvos);
				newvo.setChildrenVO(newitemvos);
				PoChangeUI.loadVOBodyWhenConvert(this, newvo,
						sUpBillType);
				for(int k = 0 ; k < newitemvos.length ; k ++){
					vecData.add(newitemvos[k]);
				}
				OrderItemVO[] itemvos = new OrderItemVO[vecData.size()];
				vecData.copyInto(itemvos);
				newvo.setChildrenVO(itemvos);
			} else {
				showHintMessage(NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-V56015")
						/* @res "追加信息表头字段业务类型，供应商，采购组织和当前订单表头不相同的值，不能追加到当前表体" */);
			}
		}
		// 重新设置行号
		for(int i = 0 ; i < newvo.getBodyLen() ; i++){
			newvo.getBodyVO()[i].setCrowno(null);
		}
		BillRowNo.setVORowNoByRule(newvo, nc.vo.scm.pu.BillTypeConst.PO_ORDER,
				"crowno");
		String sLocalCurr = null;
		try {
			// 本币
			sLocalCurr = CurrParamQuery.getInstance().getLocalCurrPK(getCorp());
			// POPubSetUI.getCurrArith_Busi(voOrder.getHeadVO().getPk_corp()).getLocalCurrPK();
		} catch (Exception e) {
			PuTool.outException(e);
			return;
		}
		for (int i = 0; i < newvo.getBodyVO().length; i++) {
			if(newvo.getBodyVO()[i].getCcurrencytypeid()==null){
				newvo.getBodyVO()[i].setCcurrencytypeid(sLocalCurr);
				newvo.getBodyVO()[i].setNexchangeotobrate(new UFDouble(1));
			}
		}
		newvo.setBodyChanged(false);
		
//		getBufferVOManager().removeAt(getBufferVOManager().getVOPos());
//		getBufferVOManager().addVONoListener( newvo);
		getPoCardPanel().displayCurVO(newvo, true);
		if(newvo.getHeadVO().getCorderid()!=null){
			setIsFromOtherBill(false);
		}
		// if (voaRet == null) {
		// setCurPk_corp(PoPublicUIClass.getLoginPk_corp());
		// return;
		// }
		//
		// String ccurrencytypeidHead = null;
		// String strCoperator = null;
		// for (int i = 0; i < voaRet.length; i++) {
		// ccurrencytypeidHead = voaRet[i].getHeadVO().getCcurrencytypeid();
		// if (ccurrencytypeidHead == null
		// || (ccurrencytypeidHead != null && ccurrencytypeidHead
		// .trim().length() == 0)) {
		// voaRet[i].getHeadVO().setCcurrencytypeid(
		// voaRet[i].getBodyVO()[0].getCcurrencytypeid());
		// }
		//			
		// voaRet[i].getHeadVO().setCoperator(getUserID());
		// voaRet[i].getHeadVO().setPk_corp(getCorp());
		// }
		//
		// // 进行缓存转换、显示等工作
		// setIsFromOtherBill(true);
		// setCupsourcebilltype(sUpBillType);
		//
		// // 设置当前公司
		// setCurPk_corp(voaRet[0].getHeadVO().getPk_corp());
		//
		// // 转移原来缓存中的单据到QueryTemp中
		// getBufferVOFrmBill().resetVOs(getBufferVOManager());
		// // 设置VO到界面
		// getBufferVOManager().resetVOs(voaRet);
		// getBufferVOManager().setVOPos(0);
		// setCurOperState(STATE_BILL_EDIT);
		// onBillList();
		//
		// // 设置按钮状态
		// setButtonsStateList();

		// showHintMessage(NCLangRes.getInstance().getStrByID(
		// "4004020201", "UPP4004020201-000042")/* @res "浏览订单" */);
	}

	/**
	 * 功能描述:退出系统
	 */
	public boolean onClosing() {
		// 正在编辑单据时退出提示
		if (getCurOperState() == STATE_BILL_EDIT) {
			int iRet = MessageDialog.showYesNoCancelDlg(this,
					NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000270")/* "提示" */,
					NCLangRes.getInstance().getStrByID("common",
							"UCH001")/* @res "是否保存已修改的数据？" */);
			// 保存成功后才退出
			if (iRet == MessageDialog.ID_YES) {
				return onBillSave();
			}
			// 退出
			else if (iRet == MessageDialog.ID_NO) {
				return true;
			}
			// 取消关闭
			else {
				return false;
			}
		}
		hPraybillVO.clear();
		setBillList(false);
//		SourceRefDlg.clearCacheByBillType(this);
		return true;
	}

	public HashMap<String, PraybillItemVO> gethPraybillVO() {
		return hPraybillVO;
	}
	public HashMap getMaxPrice(){
		return hMaxPrice;
	}
	public TableRowCellRender getRowRender(){
		return m_renderYellowAlarmLine;
	}
	private boolean isSoEnable(){
		boolean bSoEnable = false;
		try{
			if (CreateCorpQuery_Client.isEnabled(getCorp(),
					ScmConst.m_sModuleCodeSO)) {
				bSoEnable = true;
			}
		}catch(Exception e){
			showErrorMessage(e.getMessage());
			return false;
		}
		return bSoEnable;
	}
	public void updateListPanelData(String state){
		nc.vo.scm.pu.Timer timeDebug = new nc.vo.scm.pu.Timer();
		timeDebug.start();
		if(bBillList){
		OrderVO changedVO = getOrderDataVOAt(getBufferVOManager().getVOPos());
		if("修改".equals(state)){
			getPoListPanel().getHeadBillModel().setBodyRowVO(changedVO.getHeadVO(), getBufferVOManager().getVOPos());
			getPoListPanel().getHeadBillModel().execLoadFormulaByRow(getBufferVOManager().getVOPos());
			getPoListPanel().getBodyBillModel().setBodyDataVO(changedVO.getBodyVO());
			getPoListPanel().getBodyBillModel().execLoadFormula();
		}else if("增加".equals(state)){
			getPoListPanel().getHeadBillModel().addLine();
			changedVO = getOrderDataVOAt(getPoListPanel().getHeadBillModel().getRowCount()-1);
			getPoListPanel().getHeadBillModel().setBodyRowVO(changedVO.getHeadVO(), getPoListPanel().getHeadBillModel().getRowCount()-1);
			timeDebug.addExecutePhase("增加列表表头");/*-=notranslate=-*/
			getPoListPanel().getHeadBillModel().execLoadFormulaByRow(getPoListPanel().getHeadBillModel().getRowCount()-1);
			timeDebug.addExecutePhase("执行列表表头公式");/*-=notranslate=-*/
			getPoListPanel().getBodyBillModel().setBodyDataVO(changedVO.getBodyVO());
			timeDebug.addExecutePhase("增加列表表体");/*-=notranslate=-*/
			getPoListPanel().getBodyBillModel().execLoadFormula();
			timeDebug.addExecutePhase("执行列表表体公式");/*-=notranslate=-*/
			getBufferVOManager().setVOPos(getPoListPanel().getHeadBillModel().getRowCount()-1);
		}else if("删除".equals(state)){
			if(getBufferVOManager().getVOPos()>=0&&getBufferVOManager().getVOPos()<=getPoListPanel().getHeadBillModel().getRowCount())
			getPoListPanel().getHeadBillModel().delLine(new int[]{getBufferVOManager().getVOPos()});
		}
	}
		timeDebug.showAllExecutePhase("列表观察结束");/*-=notranslate=-*/
	}
	private void setBillList(boolean value){
		bBillList = value;
	}
	private PUPluginUI pluginui;
	private InvokeEventProxy invokeEventProxy;
	public PUPluginUI getPluginUI(){
		if(pluginui == null){
			pluginui = new PUPluginUI(this);
		}
		return pluginui;
	}
	public InvokeEventProxy getInvokeEventProxy() {
		if(invokeEventProxy == null){
			invokeEventProxy = new InvokeEventProxy("pu",
          "21", getPluginUI());
		}
		return invokeEventProxy;
	}
	public BillTempletVO getBillTempletVo() {
	  if(null == m_billTempletVo){
	    m_billTempletVo=BillUIUtil.getDefaultTempletStatic(getModuleCode(), 
	        null, PoPublicUIClass.getLoginUser(), getCorp(), null, null);
	  }
	  return m_billTempletVo;
	}
}