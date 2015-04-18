package nc.ui.trade.bill;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import nc.bs.logging.Logger;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.pub.workflownote.FlowStateDlg;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bsdelegate.BusinessDelegator;
import nc.ui.trade.bsdelegate.DefaultBusinessSplit;
import nc.ui.trade.bsdelegate.IBusinessSplit;
import nc.ui.trade.buffer.RecordNotFoundExcetption;
import nc.ui.trade.businessaction.BdBusinessAction;
import nc.ui.trade.businessaction.BusinessAction;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.businessaction.IBusinessController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.handler.EventHandler;
import nc.ui.trade.pub.BillDirectPrint;
import nc.ui.trade.pub.CardPanelPRTS;
import nc.ui.trade.pub.ReportTreeTableModelAdapter;
import nc.ui.trade.query.INormalQuery;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.pf.pub.BusitypeVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.pf.IPFConfigInfo;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.trade.pub.IExAggVO;
import nc.vo.trade.summarize.Hashlize;
import nc.vo.trade.summarize.VOHashPrimaryKeyAdapter;

/**
 * 平台单据的按钮控制委托器。 创建日期：(2004-1-9 10:33:09)
 * 
 * @author：樊冠军
 */
public abstract class BillEventHandler extends EventHandler {

	private static final String staticACTION = "BOACTION";

	private static final String staticASS = "BOASS";

	/**
	 * BillButtonController 构造子注解。
	 * 
	 * @param billUI
	 *            nc.ui.trade.pub.AbstractBillUI
	 * @param control
	 *            nc.ui.trade.controller.IControllerBase
	 */
	public BillEventHandler(AbstractBillUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	/**
	 * 业务的执行动作后。 创建日期：(2004-1-8 12:55:35)
	 * 
	 * nc.ui.pub.ButtonObject
	 * 
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	protected void afterOnBoAction(int intBtn, AggregatedValueObject billVo)
			throws java.lang.Exception {
	}

	/**
	 * 此处插入方法说明。 创建日期：(2004-1-8 12:55:35)
	 * 
	 * @param bo
	 *            nc.ui.pub.ButtonObject
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	protected void afterOnBoAss(ButtonObject bo) throws java.lang.Exception {
	}

	/**
	 * 业务的执行动作前。 创建日期：(2004-1-8 12:55:35)
	 * 
	 * nc.ui.pub.ButtonObject
	 * 
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	protected void beforeOnBoAction(int intBtn, AggregatedValueObject billVo)
			throws java.lang.Exception {
		if (billVo instanceof HYBillVO)
			((HYBillVO) billVo).setM_billField(getBillField());
	}

	/**
	 * 此处插入方法说明。 创建日期：(2004-1-8 12:55:35)
	 * 
	 * @param bo
	 *            nc.ui.pub.ButtonObject
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	protected void beforeOnBoAss(ButtonObject bo) throws java.lang.Exception {
	}

	/**
	 * 业务类型动作执行前的事件处理。 如果必须对该动作执行前进行控制，则必须重载该方法 创建日期：(2004-1-6 21:01:32)
	 * 
	 * @param billUI
	 *            nc.ui.trade.pub.AbstractBillUI int
	 */
	protected void busiTypeBefore(AbstractBillUI billUI, ButtonObject bo)
			throws Exception {
	}

	/**
	 * 清空子表主键。 创建日期：(2004-2-25 19:59:34)
	 * 
	 * @param vos
	 *            nc.vo.pub.CircularlyAccessibleValueObject[]
	 */
	private void clearChildPk(CircularlyAccessibleValueObject[] vos)
			throws Exception {
		if (vos == null || vos.length == 0)
			return;
		for (int i = 0; i < vos.length; i++) {
			vos[i].setPrimaryKey(null);
		}
	}

	/**
	 * 父类按钮的事件处理。 创建日期：(2004-2-25 21:04:27)
	 * 
	 * @param intBtn
	 *            int
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	private void complexOnButton(int intBtn, ButtonObject bo)
			throws java.lang.Exception {
		switch (intBtn) {
		case IBillButton.Busitype: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000060")/*
													 * @res "开始选择业务类型，请等待......"
													 */);
			onBoBusiType(bo);
			break;
		}
		case IBillButton.Add: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000061")/*
													 * @res "开始进行增加单据，请等待......"
													 */);
			onBoBusiTypeAdd(bo, null);
			break;
		}
		case IBillButton.Action: {
			onBoAction(bo);
			break;
		}
		case IBillButton.Ass: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000062")/*
													 * @res
													 * "开始对单据的辅助进行操作，请等待......"
													 */);
			onBoAss(bo);
			break;
		}
		case IBillButton.Line: {
			onBoLine(bo);
			break;
		}
		case IBillButton.File: {
			onBoFile(bo);
		}
		case IBillButton.Brow: {
			onBoBrow(bo);
			break;
		}
		case IBillButton.NodeKey: {
			onBoNodekey(bo);
			break;
		}
		case IBillButton.Refbill: {
			ButtonVO btnVo = (ButtonVO) bo.getData();
			onBoElse(btnVo.getBtnNo());
			break;
		}
		default: {
			if(bo.getData() != null && bo.getData() instanceof ButtonVO) {
				ButtonVO btnVo = (ButtonVO) bo.getData();
				onBoElse(btnVo.getBtnNo());
			}
			break;
		}
		}
	}

	/**
	 * 进行单据动作的处理, 如果进行单据动作处理需要重载该方法 创建日期：(2004-1-3 18:13:36)
	 */
	protected IBusinessController createBusinessAction() {
		switch (getUIController().getBusinessActionType()) {
		case IBusinessActionType.PLATFORM:
			return new BusinessAction(getBillUI());
		case IBusinessActionType.BD:
			return new BdBusinessAction(getBillUI());
		default:
			return new BusinessAction(getBillUI());
		}

	}

	/**
	 * 获得卡片模版的包装类。 创建日期：(2004-1-6 22:29:36)
	 * 
	 * @return nc.ui.pub.bill.BillCardPanel
	 */
	protected abstract BillCardPanelWrapper getBillCardPanelWrapper();

	/**
	 * 获得常量字段。 创建日期：(2004-1-7 8:44:06)
	 * 
	 * @return nc.ui.trade.buffer.BillUIBuffer
	 */
	protected final BusinessDelegator getBusiDelegator() {
		return getBillUI().getBusiDelegator();
	}

	/**
	 * 初始化在流程平台注册的执行动作按钮 创建日期：(2002-12-23 10:11:27)
	 */
	public final void initActionButton() {
		try {
			ButtonObject boAction = getButtonManager().getButton(
					IBillButton.Action);
			// 业务类型
			if (getBillUI().isBusinessType().booleanValue()) {
				ButtonObject boBusitype = getButtonManager().getButton(
						IBillButton.Busitype);
				ButtonObject boAdd = getButtonManager().getButton(
						IBillButton.Add);
				
				ButtonObject boSelectBusitype = null;
				
				if(boBusitype != null) {
					getBusiDelegator().retBusinessBtn(boBusitype,
							_getCorp().getPrimaryKey(),
							getUIController().getBillType());
					if (boBusitype.getChildButtonGroup() != null
							&& boBusitype.getChildButtonGroup().length > 0) {

						boBusitype.getChildButtonGroup()[0].setSelected(true);
						boBusitype.setCheckboxGroup(true);

						ButtonObject bo = boBusitype.getChildButtonGroup()[0];
						// 设置业务类型
						BusitypeVO vo = (BusitypeVO) bo.getData();
						// 业务类型主键
						getBillUI().setBusinessType(vo.getPrimaryKey());
						// 业务类型代码
						getBillUI().setBusicode(vo.getBusicode());

						boSelectBusitype = boBusitype.getChildButtonGroup()[0];
						
						// 处理执行按钮(与单据状态无关）
						if (boAction != null)
							getBusiDelegator().retElseBtn(boAction,
									getUIController().getBillType(), staticACTION);
						initAssButton(boSelectBusitype);
					}
				}
				// 新增
				if (boAdd != null)
					getBusiDelegator().retAddBtn(boAdd,
							_getCorp().getPrimaryKey(),
							getUIController().getBillType(),
							boSelectBusitype);

//				if (boBusitype != null && boBusitype.getChildButtonGroup() != null
//						&& boBusitype.getChildButtonGroup().length > 0) {
//
//					boBusitype.getChildButtonGroup()[0].setSelected(true);
//					boBusitype.setCheckboxGroup(true);
//
//					// 新增
//					if (boAdd != null)
//						getBusiDelegator().retAddBtn(boAdd,
//								_getCorp().getPrimaryKey(),
//								getUIController().getBillType(),
//								boBusitype.getChildButtonGroup()[0]);
//
//					ButtonObject bo = boBusitype.getChildButtonGroup()[0];
//					// 设置业务类型
//					BusitypeVO vo = (BusitypeVO) bo.getData();
//					// 业务类型主键
//					getBillUI().setBusinessType(vo.getPrimaryKey());
//					// 业务类型代码
//					getBillUI().setBusicode(vo.getBusicode());
//
//					// 处理执行按钮(与单据状态无关）
//					if (boAction != null)
//						getBusiDelegator().retElseBtn(boAction,
//								getUIController().getBillType(), staticACTION);
//					initAssButton(boBusitype.getChildButtonGroup()[0]);
//				} else
//					System.out.println("没有初始化业务类型!");
			} else {
				// 处理执行按钮(与单据状态无关）
				if (boAction != null)
					getBusiDelegator().retElseBtn(boAction,
							getUIController().getBillType(), staticACTION);
			}
			// 处理执行按钮
			if (boAction != null) {
				getButtonManager().setActionButtonVO(
						getBillUI().isSaveAndCommitTogether());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000063")/* @res "初始化业务执行按钮有误" */);
		}
	}

	/**
	 * 初始化在流程平台中注册的单据动作
	 */
	private void initAssButton(ButtonObject bo) throws Exception {
		ButtonObject boAss = getButtonManager().getButton(IBillButton.Ass);
		if (boAss == null)
			return;
		getBusiDelegator().retElseBtn(boAss, getUIController().getBillType(),
				staticASS);
	}

	/**
	 * 此处插入方法说明。 创建日期：(2004-1-7 10:53:11)
	 * 
	 * @return boolean
	 */
	public boolean isAdding() {
		return getBillUI().getBillOperate() == IBillOperate.OP_ADD
				|| getBillUI().getBillOperate() == IBillOperate.OP_REFADD;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2004-1-7 10:53:11)
	 * 
	 * @return boolean
	 */
	public boolean isEditing() {
		return getBillUI().getBillOperate() == IBillOperate.OP_EDIT;
	}

	/**
	 * 参照单据进行制单。 创建日期：(2003-6-12 10:01:28)
	 */
	public void onBillRef() throws Exception {
		ButtonObject btn = getButtonManager().getButton(IBillButton.Refbill);
		btn.setTag(getBillUI().getRefBillType() + ":");
		onBoBusiTypeAdd(btn, null);
	}

	/**
	 * 按钮m_boAction点击时执行的动作,如有必要，请覆盖. 单据执行动作处理
	 */
	private final void onBoAction(ButtonObject bo) throws Exception {
		// getBillUI().showHintMessage(
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
		// "UPPuifactory-000179")/*
		// * @res "开始执行操作,请等待..."
		// */);
		ButtonVO btnVo = (ButtonVO) bo.getData();
		if (btnVo == null)
			return;
		switch (btnVo.getBtnNo()) {
		case IBillButton.Audit: {
			onBoAudit();
			break;
		}
		case IBillButton.CancelAudit: {
			onBoCancelAudit();
			break;
		}
		case IBillButton.Commit: {
			onBoCommit();
			break;
		}
		case IBillButton.Del: {
			onBoDel();
			break;
		}
		default: {
			onBoActionElse(bo);
			break;
		}
		}

		// getBillUI().showHintMessage(
		// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
		// "UPPuifactory-000180")/*
		// * @res "执行完毕."
		// */
		// );
	}

	/**
	 * Action按钮点击时执行的动作,如有必要，请覆盖.
	 */
	public void onBoActionElse(ButtonObject bo) throws Exception {
		// 获得数据
		AggregatedValueObject modelVo = getBufferData().getCurrentVOClone();
		int intBtn = 0;
		if (bo.getData() != null)
			intBtn = ((ButtonVO) bo.getData()).getBtnNo();
		beforeOnBoAction(intBtn, modelVo);
		// *******************
		Object retObj = getBusinessAction().processAction(bo.getTag(), modelVo,
				getUIController().getBillType(),
				getBillUI()._getDate().toString(), getBillUI().getUserObject());

		if (PfUtilClient.isSuccess()) {
			if (retObj instanceof AggregatedValueObject) {
				AggregatedValueObject retVo = (AggregatedValueObject) retObj;
				afterOnBoAction(intBtn, retVo);
				CircularlyAccessibleValueObject[] childVos = getChildVO(retVo);
				if (childVos == null)
					modelVo.setParentVO(retVo.getParentVO());
				else
					modelVo = retVo;
			}
			// 更新列表
			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
		}
	}

	/**
	 * 单据增加的处理 创建日期：(2002-12-23 12:43:15)
	 */
	public void onBoAdd(ButtonObject bo) throws Exception {
		// 设置为新增处理
		getBillUI().setBillOperate(IBillOperate.OP_ADD);
	}

	/**
	 * 按钮m_boAction点击时执行的动作,如有必要，请覆盖. 单据执行动作处理
	 */
	private final void onBoAss(ButtonObject bo) throws Exception {
		beforeOnBoAss(bo);
		AggregatedValueObject modelVo = getBufferData().getCurrentVO();
		Object ret = getBusinessAction().processAction(bo.getTag(), modelVo,
				getUIController().getBillType(),
				getBillUI()._getDate().toString(), getBillUI().getUserObject());
		if (ret != null && ret instanceof AggregatedValueObject) {
			AggregatedValueObject vo = (AggregatedValueObject) ret;
			// 更新状态
			modelVo.getParentVO().setAttributeValue(
					getBillField().getField_BillStatus(),
					vo.getParentVO().getAttributeValue(
							getBillField().getField_BillStatus()));
			// 更新时间戳
			modelVo.getParentVO().setAttributeValue("ts",
					vo.getParentVO().getAttributeValue("ts"));

			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
		}
		afterOnBoAss(bo);
	}

	/**
	 * 按钮m_boAssign点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoAssign() throws Exception {
	}

	/**
	 * 按钮m_boAudit点击时执行的动作,如有必要，请覆盖.
	 */
	public void onBoAudit() throws Exception {
		// 获得数据
		AggregatedValueObject modelVo = getBufferData().getCurrentVOClone();
		setCheckManAndDate(modelVo);
		// 如果状态一致则退出
		if (checkVOStatus(modelVo, new int[] { IBillStatus.CHECKPASS })) {
			System.out.println("无效的鼠标处理机制");
			return;
		}
		beforeOnBoAction(IBillButton.Audit, modelVo);
		// *******************
		AggregatedValueObject retVo = (AggregatedValueObject) getBusinessAction()
				.approve(modelVo, getUIController().getBillType(),
						getBillUI()._getDate().toString(),
						getBillUI().getUserObject());

		if (PfUtilClient.isSuccess()) {

			afterOnBoAction(IBillButton.Audit, retVo);
			CircularlyAccessibleValueObject[] childVos = getChildVO(retVo);
			if (childVos == null)
				modelVo.setParentVO(retVo.getParentVO());
			else
				modelVo = retVo;
			// 更新列表
			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
		}

	}

	/**
	 * 对于单子表的界面处理，必须重载此方法获得数据，同时进行缓存数据 加载 getBufferData().clear();
	 * getBufferData().adddVOToBuffer(aVo); //如果列表setListHeadData(queryVos);
	 * //设置单据操作状态 getBillUI().setBillOperate(IBillOperate.OP_NOTEDIT);
	 */
	protected void onBoBodyQuery() throws Exception {
		StringBuffer strWhere = new StringBuffer();

		if (askForBodyQueryCondition(strWhere) == false)
			return;// 用户放弃了查询
		doBodyQuery(strWhere.toString());

	}

	/**
	 * @param strWhere
	 * @throws Exception
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected void doBodyQuery(String strWhere) throws Exception,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		SuperVO[] queryVos = getBusiDelegator().queryByCondition(
				Class.forName(getUIController().getBillVoName()[2]),
				strWhere == null ? "" : strWhere);

		getBufferData().clear();

		AggregatedValueObject vo = (AggregatedValueObject) Class.forName(
				getUIController().getBillVoName()[0]).newInstance();
		vo.setChildrenVO(queryVos);
		getBufferData().addVOToBuffer(vo);

		updateBuffer();
	}

	/**
	 * @param strWhere
	 * @param b
	 * @return
	 */
	protected boolean askForBodyQueryCondition(StringBuffer sqlWhereBuf)
			throws Exception {
		if (sqlWhereBuf == null)
			throw new IllegalArgumentException(
					"askForQueryCondition().sqlWhereBuf cann't be null");
		UIDialog querydialog = getQueryUI();

		if (querydialog.showModal() != UIDialog.ID_OK)
			return false;
		INormalQuery query = (INormalQuery) querydialog;

		String strWhere = query.getWhereSql();
		if (strWhere == null)
			strWhere = "1=1";

		strWhere = "(" + strWhere + ") and (isnull(dr,0)=0)";

		if (getUIController().getBodyCondition() != null)
			strWhere = strWhere + " and "
					+ getUIController().getBodyCondition();
		// 现在我先直接把这个拼好的串放到StringBuffer中而不去优化拼串的过程
		sqlWhereBuf.append(strWhere);
		return true;
	}

	/**
	 * 浏览操作处理。 创建日期：(2004-1-7 8:57:02)
	 * 
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	private void onBoBrow(ButtonObject bo) throws java.lang.Exception {
		int intBtn = Integer.parseInt(bo.getTag());
		// 动作执行前处理
		buttonActionBefore(getBillUI(), intBtn);
		switch (intBtn) {
		case IBillButton.First: {
			getBufferData().first();
			break;
		}
		case IBillButton.Prev: {
			getBufferData().prev();
			break;
		}
		case IBillButton.Next: {
			getBufferData().next();
			break;
		}
		case IBillButton.Last: {
			getBufferData().last();
			break;
		}
		}
		// 动作执行后处理
		buttonActionAfter(getBillUI(), intBtn);
		getBillUI().showHintMessage(
				nc.ui.ml.NCLangRes.getInstance()
						.getStrByID(
								"uifactory",
								"UPPuifactory-000503",
								null,
								new String[] { nc.vo.format.Format
										.indexFormat(getBufferData()
												.getCurrentRow() + 1) })/*
																		 * @res
																		 * "转换第:" +
																		 * getBufferData().getCurrentRow() +
																		 * "页完成)"
																		 */
		);

	}

	/**
	 * 按钮m_boBusiType点击时执行的动作,如有必要，请覆盖.
	 */
	private final void onBoBusiType(ButtonObject bo) throws Exception {
		// 执行前处理
		busiTypeBefore(getBillUI(), bo);
		bo.setSelected(true);
		// 设置业务类型
		BusitypeVO vo = (BusitypeVO) bo.getData();
		// 处理增加按钮
		getBusiDelegator()
				.retAddBtn(getButtonManager().getButton(IBillButton.Add),
						_getCorp().getPrimaryKey(),
						getUIController().getBillType(), bo);
		// //处理执行按钮(与单据状态无关）
		getBusiDelegator().retElseBtn(
				getButtonManager().getButton(IBillButton.Action),
				getUIController().getBillType(), staticACTION);

		getButtonManager().setActionButtonVO(
				getBillUI().isSaveAndCommitTogether());

		String oldtype = getBillUI().getBusinessType();
		String newtype = vo.getPrimaryKey();
		String oldcode = getBillUI().getBusicode();
		String newcode = vo.getBusicode();

		// 业务类型主键
		getBillUI().setBusinessType(newtype);
		// 业务类型代码
		getBillUI().setBusicode(newcode);

		// 重新刷新UI
		getBillUI().initUI();
		// 清空UI缓存
		getBillUI().getBufferData().clear();
		getBillUI().getBufferData().setCurrentRow(-1);

		getBillUI().updateButtonUI();

		if (m_bbl != null) {
			BusiTypeChangeEvent e = new BusiTypeChangeEvent(this, oldtype,
					newtype, oldcode, newcode);
			m_bbl.busiTypeChange(e);
		}
	}

	/**
	 * 按钮m_boAdd点击时执行的动作,如有必要，请覆盖.
	 * 
	 * @param bo
	 *            来源单据的平台生成按钮
	 * @param sourceBillId
	 *            参考来源单据Id
	 */
	public final void onBoRefAdd(String strRefBillType, String sourceBillId)
			throws Exception {

		onBoBusiTypeAdd(getBusiDelegator().getRefButton(
				getButtonManager().getButton(IBillButton.Add), strRefBillType),
				sourceBillId);
	}

	/**
	 * 按钮m_boAdd点击时执行的动作,如有必要，请覆盖.
	 * 
	 * @param bo
	 *            来源单据的平台生成按钮
	 * @param sourceBillId
	 *            参考来源单据Id
	 */
	private final void onBoBusiTypeAdd(ButtonObject bo, String sourceBillId)
			throws Exception {
		getBusiDelegator().childButtonClicked(bo, _getCorp().getPrimaryKey(),
				getBillUI()._getModuleCode(), _getOperator(),
				getUIController().getBillType(), getBillUI(),
				getBillUI().getUserObject(), sourceBillId);
		if (nc.ui.pub.pf.PfUtilClient.makeFlag) {
			// 设置单据状态
			getBillUI().setCardUIState();
			// 新增
			getBillUI().setBillOperate(IBillOperate.OP_ADD);
		} else {
			if (PfUtilClient.isCloseOK()) {
				if (m_bbl != null) {
					String tmpString = bo.getTag();
					int findIndex = tmpString.indexOf(":");
					String newtype = tmpString.substring(0, findIndex);
					RefBillTypeChangeEvent e = new RefBillTypeChangeEvent(this,
							null, newtype);
					m_bbl.refBillTypeChange(e);
				}
				if (isDataChange())
					setRefData(PfUtilClient.getRetVos());
				else
					setRefData(PfUtilClient.getRetOldVos());
			}
		}
	}

	/**
	 * 按钮m_boCancel点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoCancel() throws Exception {

		if (getBufferData().isVOBufferEmpty()) {
			getBillUI().setBillOperate(IBillOperate.OP_INIT);
		} else {
			getBillUI().setBillOperate(IBillOperate.OP_NOTEDIT);
			
            if(getBufferData().getCurrentRow()==-1){
               getBufferData().setCurrentRow(0);
            }else{
               getBufferData().setCurrentRow(getBufferData().getCurrentRow());
            }			
			
		}

	}

	/**
	 * 按钮m_boCancelAudit点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoCancelAudit() throws Exception {
		// 获得数据
		AggregatedValueObject modelVo = getBufferData().getCurrentVOClone();
		// 放入反审批日期、审批人
		setCheckManAndDate(modelVo);
		// 如果状态一致则退出
		if (checkVOStatus(modelVo, new int[] { IBillStatus.FREE })) {
			System.out.println("无效的鼠标处理机制");
			return;
		}
		beforeOnBoAction(IBillButton.CancelAudit, modelVo);
		// *******************
		AggregatedValueObject retVo = (AggregatedValueObject) getBusinessAction()
				.unapprove(modelVo, getUIController().getBillType(),
						getBillUI()._getDate().toString(),
						getBillUI().getUserObject());

		if (PfUtilClient.isSuccess()) {
			afterOnBoAction(IBillButton.CancelAudit, retVo);
			CircularlyAccessibleValueObject[] childVos = getChildVO(retVo);
			if (childVos == null)
				modelVo.setParentVO(retVo.getParentVO());
			else
				modelVo = retVo;

			Integer intState = (Integer) modelVo.getParentVO()
					.getAttributeValue(getBillField().getField_BillStatus());
			if (intState.intValue() == IBillStatus.FREE) {
				modelVo.getParentVO().setAttributeValue(
						getBillField().getField_CheckMan(), null);
				modelVo.getParentVO().setAttributeValue(
						getBillField().getField_CheckDate(), null);
			}
			// 更新列表数据
			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
		}
	}

	/**
	 * 按钮m_boCard点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoCard() throws Exception {

		// setPageButtonState();
	}

	/**
	 * 按钮m_boCommit点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoCommit() throws Exception {
		// 获得数据
		AggregatedValueObject modelVo = getBufferData().getCurrentVOClone();
		// 临时性设置制单人为当前操作人，
		modelVo.getParentVO().setAttributeValue(
				getBillField().getField_Operator(), getBillUI()._getOperator());
		beforeOnBoAction(IBillButton.Commit, modelVo);
		String strTime = ClientEnvironment.getServerTime()
				.toString();
		ArrayList retList = getBusinessAction().commit(modelVo,
				getUIController().getBillType(),
				getBillUI()._getDate().toString() + strTime.substring(10),
				getBillUI().getUserObject());

		if (PfUtilClient.isSuccess()) {
			Object o = retList.get(1);
			if (o instanceof AggregatedValueObject) {
				AggregatedValueObject retVo = (AggregatedValueObject) o;
				afterOnBoAction(IBillButton.Commit, retVo);
				CircularlyAccessibleValueObject[] childVos = getChildVO(retVo);
				if (childVos == null)
					modelVo.setParentVO(retVo.getParentVO());
				else
					modelVo = retVo;
			}
			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
		}
	}

	/**
	 * 按钮m_boCopy点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoCopy() throws Exception {
		// 获得数据
		AggregatedValueObject copyVo = getBufferData().getCurrentVOClone();
		// 进行主键清空处理
		copyVo.getParentVO().setPrimaryKey(null);
		if (copyVo instanceof IExAggVO) {
			clearChildPk(((IExAggVO) copyVo).getAllChildrenVO());
		} else {
			clearChildPk(copyVo.getChildrenVO());
		}
		// 设置为新增处理
		getBillUI().setBillOperate(IBillOperate.OP_ADD);
		// 设置单据号
		String noField = getBillUI().getBillField().getField_BillNo();
		BillItem noitem = getBillCardPanelWrapper().getBillCardPanel()
				.getHeadItem(noField);
		if (noitem != null)
			copyVo.getParentVO().setAttributeValue(noField,
					noitem.getValueObject());
		// 设置界面数据
		getBillUI().setCardUIData(copyVo);
	}

	/**
	 * 按钮m_boDel点击时执行的动作,如有必要，请覆盖. 单据的作废处理
	 */
	protected void onBoDel() throws Exception {
		AggregatedValueObject modelVo = getBufferData().getCurrentVO();
		// 设置字段常量
		if (modelVo instanceof HYBillVO)
			((HYBillVO) modelVo).setM_billField(getBillField());

		AggregatedValueObject delVo = (AggregatedValueObject) getBusinessAction()
				.delete(modelVo, getUIController().getBillType(),
						getBillUI()._getDate().toString(),
						getBillUI().getUserObject());
		if (PfUtilClient.isSuccess()) {
			getBillUI().setBillOperate(IBillOperate.OP_NOTEDIT);
			// 更新状态
			modelVo.getParentVO().setAttributeValue(
					getBillField().getField_BillStatus(),
					new Integer(IBillStatus.DELETE));
			modelVo.getParentVO().setAttributeValue("ts",
					delVo.getParentVO().getAttributeValue("ts"));
			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
		}

	}

	/**
	 * 按钮m_boDel点击时执行的动作,如有必要，请覆盖. 档案的删除处理
	 */
	protected void onBoDelete() throws Exception {
		// 界面没有数据或者有数据但是没有选中任何行
		if (getBufferData().getCurrentVO() == null)
			return;

		if (MessageDialog.showOkCancelDlg(getBillUI(),
				nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
						"UPPuifactory-000064")/* @res "档案删除" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
						"UPPuifactory-000065")/* @res "是否确认删除该基本档案?" */
				, MessageDialog.ID_CANCEL) != UIDialog.ID_OK)
			return;

		AggregatedValueObject modelVo = getBufferData().getCurrentVO();
		getBusinessAction().delete(modelVo, getUIController().getBillType(),
				getBillUI()._getDate().toString(), getBillUI().getUserObject());
		if (PfUtilClient.isSuccess()) {
			// 清除界面数据
			getBillUI().removeListHeadData(getBufferData().getCurrentRow());
			if (getUIController() instanceof ISingleController) {
				ISingleController sctl = (ISingleController) getUIController();
				if (!sctl.isSingleDetail())
					getBufferData().removeCurrentRow();
			} else {
				getBufferData().removeCurrentRow();
			}

		}
		if (getBufferData().getVOBufferSize() == 0)
			getBillUI().setBillOperate(IBillOperate.OP_INIT);
		else
			getBillUI().setBillOperate(IBillOperate.OP_NOTEDIT);
		getBufferData().setCurrentRow(getBufferData().getCurrentRow());
	}

	/**
	 * 按钮m_boEdit点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoEdit() throws Exception {
		// 界面没有数据或者有数据但是没有选中任何行
		if (getBufferData().getCurrentVO() == null)
			return;
		String strTime = getBillUI()._getServerTime().toString();
		AggregatedValueObject modelVo = getBufferData().getCurrentVO();
		Object o = getBusinessAction()
				.edit(
						modelVo,
						getUIController().getBillType(),
						getBillUI()._getDate().toString()
								+ strTime.substring(10), null);

		if (o instanceof AggregatedValueObject) {
			AggregatedValueObject retVo = (AggregatedValueObject) o;
			if (retVo.getChildrenVO() == null)
				modelVo.setParentVO(retVo.getParentVO());
			else
				modelVo = retVo;
			getBufferData().setVOAt(getBufferData().getCurrentRow(), modelVo);
			getBufferData().setCurrentRow(getBufferData().getCurrentRow());
		}

		getBillUI().setBillOperate(IBillOperate.OP_EDIT);
	}

	/**
	 * 其它按钮动作的事件处理，比如(导入等)
	 */
	protected void onBoElse(int intBtn) throws Exception {
	}

	/**
	 * 行操作处理。 创建日期：(2004-1-7 8:57:02)
	 * 
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	private void onBoLine(ButtonObject bo) throws java.lang.Exception {
		int intBtn = -1 ;//Integer.parseInt(bo.getTag());

		if(bo.getData() != null && bo.getData() instanceof ButtonVO) {
			ButtonVO btnVo = (ButtonVO) bo.getData();
			intBtn = btnVo.getBtnNo();
		}else {
			intBtn = Integer.parseInt(bo.getTag());
		}
		
		// 动作执行前处理
		buttonActionBefore(getBillUI(), intBtn);

		getBillUI().showHintMessage(bo.getName());
		
		switch (intBtn) {
		case IBillButton.AddLine: {
			onBoLineAdd();
			break;
		}
		case IBillButton.DelLine: {
			onBoLineDel();
			break;
		}
		case IBillButton.CopyLine: {
			onBoLineCopy();
			break;
		}
		case IBillButton.InsLine: {
			onBoLineIns();
			break;
		}
		case IBillButton.PasteLine: {
			onBoLinePaste();
			break;
		}
		case IBillButton.PasteLinetoTail: {
			onBoLinePasteToTail();
			break;
		}
		default:
			onBoElse(intBtn);
			break;
		}

		// 动作执行后处理
		buttonActionAfter(getBillUI(), intBtn);
	}

	/**
	 * 按钮m_boLineAdd点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoLineAdd() throws Exception {
		// 增加行之前调用的抽象方法
		getBillCardPanelWrapper().addLine();
		// int rows =
		// getBillCardPanelWrapper().getBillCardPanel().getBillModel().getRowCount();
		// getBillCardPanelWrapper().getBillCardPanel().getBillTable().getSelectionModel().setSelectionInterval(rows-1,rows-1);
		postProcessOfAddNewLine();
	}

	/**
	 * 
	 */
	protected void postProcessOfAddNewLine() {
		try {
			// CircularlyAccessibleValueObject addedVO = null;
			// try
			// {
			// addedVO = (CircularlyAccessibleValueObject)
			// Class.forName(getUIController().getBillVoName()[2]).newInstance();
			// }
			// catch (Exception e)
			// {
			// e.printStackTrace();
			// throw new RuntimeException("creating bodyvo instance fails");
			//
			// }
			CircularlyAccessibleValueObject vo = processNewBodyVO(getBillCardPanelWrapper()
					.getSelectedBodyVOs()[0]);
			// CircularlyAccessibleValueObject vo = processNewBodyVO(addedVO);
			int row = getBillCardPanelWrapper().getBillCardPanel()
					.getBillTable().getSelectedRow();
			if (row == -1)//
				row = getBillCardPanelWrapper().getBillCardPanel()
						.getBillModel().getRowCount() - 1;
			if (row < 0)
				throw new RuntimeException("cann't get selected row");
			if (vo != null)
				getBillCardPanelWrapper().getBillCardPanel().getBillModel()
						.setBodyRowVO(vo, row);
		} catch (NullPointerException e) {
			System.out.println("错误：增行或删行后没有获取到被选择的VO");
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("错误：增行或删行后没有获取到被选择的VO");
			e.printStackTrace();
		}
	}

	/**
	 * 按钮m_boLineCopy点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoLineCopy() throws Exception {
		getBillCardPanelWrapper().copySelectedLines();
	}

	/**
	 * 按钮m_boLineDel点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoLineDel() throws Exception {
		getBillCardPanelWrapper().deleteSelectedLines();

	}

	/**
	 * 按钮m_boLineIns点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoLineIns() throws Exception {
		getBillCardPanelWrapper().insertLine();
		if (getBillCardPanelWrapper().getBillCardPanel().getRowCount() > 0)
			postProcessOfAddNewLine();

	}

	/**
	 * 改方法在增行和插行后被调用，可以在该方法中为新增的行设置一些默认值
	 * 
	 * @param newBodyVO
	 * @return TODO
	 */
	protected CircularlyAccessibleValueObject processNewBodyVO(
			CircularlyAccessibleValueObject newBodyVO) {
		return newBodyVO;

	}

	/**
	 * 按钮m_boLinePaste点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoLinePaste() throws Exception {
		// 粘贴前对复制的VO做些业务相关的处理
		processCopyedBodyVOsBeforePaste(getBillCardPanelWrapper()
				.getCopyedBodyVOs());
		getBillCardPanelWrapper().pasteLines();

	}

	/**
	 * 粘贴拷贝行到表尾
	 */
	protected void onBoLinePasteToTail() throws Exception {
		// 粘贴前对复制的VO做些业务相关的处理
		processCopyedBodyVOsBeforePaste(getBillCardPanelWrapper()
				.getCopyedBodyVOs());
		getBillCardPanelWrapper().pasteLinesToTail();

	}

	/**
	 * 按钮m_boPrint点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoPrint() throws Exception {

		nc.ui.pub.print.IDataSource dataSource = new CardPanelPRTS(getBillUI()
				._getModuleCode(), getBillCardPanelWrapper().getBillCardPanel());
		nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(null,
				dataSource);
		print.setTemplateID(getBillUI()._getCorp().getPrimaryKey(), getBillUI()
				._getModuleCode(), getBillUI()._getOperator(), getBillUI()
				.getBusinessType(), getBillUI().getNodeKey());
		if (print.selectTemplate() == 1)
			print.preview();

	}

	/**
	 * 按钮m_boDirectPrint点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoDirectPrint() throws Exception {

		BillModel billmodel = getBillCardPanelWrapper().getBillCardPanel()
				.getBillModel();
		if (billmodel == null)
			return;
		if (billmodel instanceof ReportTreeTableModelAdapter) {
			((ReportTreeTableModelAdapter) billmodel).setPrinting(true);
		}

		BillDirectPrint print = new BillDirectPrint(getBillCardPanelWrapper()
				.getBillCardPanel(), getBillCardPanelWrapper()
				.getBillCardPanel().getTitle());
		print.onPrint();

		if (billmodel instanceof ReportTreeTableModelAdapter) {
			((ReportTreeTableModelAdapter) billmodel).setPrinting(false);
		}
	}

	/**
	 * 对于单子表的界面处理，必须重载此方法获得数据，同时进行缓存数据 加载 getBufferData().clear();
	 * getBufferData().adddVOToBuffer(aVo); //如果列表setListHeadData(queryVos);
	 * //设置单据操作状态 getBillUI().setBillOperate(IBillOperate.OP_NOTEDIT);
	 */
	protected void onBoQuery() throws Exception {

		StringBuffer strWhere = new StringBuffer();

		if (askForQueryCondition(strWhere) == false)
			return;// 用户放弃了查询

		SuperVO[] queryVos = queryHeadVOs(strWhere.toString());

		getBufferData().clear();
		// 增加数据到Buffer
		addDataToBuffer(queryVos);

		updateBuffer();

	}

	/**
	 * 根据输入的条件strWhere查询主表的数据
	 * 
	 * @param strWhere
	 * @return 查到的主表数据
	 * @throws Exception
	 * @throws ClassNotFoundException
	 */
	protected SuperVO[] queryHeadVOs(String strWhere) throws Exception,
			ClassNotFoundException {
		SuperVO[] queryVos = getBusiDelegator().queryHeadAllData(
				Class.forName(getUIController().getBillVoName()[1]),
				getUIController().getBillType(), strWhere.toString());
		return queryVos;
	}

	/**
	 * 弹出查询对话框向用户询问查询条件。 如果用户在对话框点击了“确定”，那么返回true,否则返回false
	 * 查询条件通过传入的StringBuffer返回给调用者
	 * 
	 * @param sqlWhereBuf
	 *            保存查询条件的StringBuffer
	 * @return 用户选确定返回true否则返回false
	 */
	protected boolean askForQueryCondition(StringBuffer sqlWhereBuf)
			throws Exception {
		if (sqlWhereBuf == null)
			throw new IllegalArgumentException(
					"askForQueryCondition().sqlWhereBuf cann't be null");
		UIDialog querydialog = getQueryUI();

		if (querydialog.showModal() != UIDialog.ID_OK)
			return false;
		INormalQuery query = (INormalQuery) querydialog;

		String strWhere = query.getWhereSql();
		if (strWhere == null || strWhere.trim().length()==0)
			strWhere = "1=1";

		if (getButtonManager().getButton(IBillButton.Busitype) != null) {
			if (getBillIsUseBusiCode().booleanValue())
				// 业务类型编码
				strWhere = "(" + strWhere + ") and "
						+ getBillField().getField_BusiCode() + "='"
						+ getBillUI().getBusicode() + "'";

			else
				// 业务类型
				strWhere = "(" + strWhere + ") and "
						+ getBillField().getField_Busitype() + "='"
						+ getBillUI().getBusinessType() + "'";

		}

		strWhere = "(" + strWhere + ") and (isnull(dr,0)=0)";

		if (getHeadCondition() != null)
			strWhere = strWhere + " and " + getHeadCondition();
		// 现在我先直接把这个拼好的串放到StringBuffer中而不去优化拼串的过程
		sqlWhereBuf.append(strWhere);
		return true;
	}

	/**
	 * 用指定的VO数组 <I>resultVOs </I>去更新BillUIBuffer.这个操作会先把Buffer中原有的数据清空。
	 * 如果指定resultVOs为空Buffer将被情况，且CurrentRow被设置为-1 否则CurrentRow设置为第0行
	 * 
	 * @throws Exception
	 */
	final protected void updateBuffer() throws Exception // 暂时改成final,确保目前还没有人继承过它
	{

		if (getBufferData().getVOBufferSize() != 0) {

			getBillUI().setListHeadData(
					getBufferData().getAllHeadVOsFromBuffer());
			getBillUI().setBillOperate(IBillOperate.OP_NOTEDIT);
			getBufferData().setCurrentRow(0);
		} else {
			getBillUI().setListHeadData(null);
			getBillUI().setBillOperate(IBillOperate.OP_INIT);
			getBufferData().setCurrentRow(-1);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000066")/* @res "没有查到任何满足条件的数据!" */);
		}
	}

	/*
	 * 增加数据到BillUIBuffer 如果增加到缓存的数据需要处理，则覆盖该方法
	 */
	protected void addDataToBuffer(SuperVO[] queryVos) throws Exception {
		if (queryVos == null) {
			getBufferData().clear();
			return;
		}
		for (int i = 0; i < queryVos.length; i++) {
			AggregatedValueObject aVo = (AggregatedValueObject) Class.forName(
					getUIController().getBillVoName()[0]).newInstance();
			aVo.setParentVO(queryVos[i]);
			getBufferData().addVOToBuffer(aVo);
		}
	}

	/**
	 * 按钮m_boRefresh点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoRefresh() throws Exception {
		// 加载数据
		try {
			getBufferData().refresh();
		} catch (RecordNotFoundExcetption e)// 如果当前的记录被删除了将抛出这个异常
		{
			if (getBillUI().showYesNoMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000185")/*
													 * @res
													 * "当前的单据已经被删除了，是否需要在界面上保留这份数据?"
													 */) != MessageDialog.ID_YES) {
				getBufferData().removeCurrentRow();

			}

		}

	}

	/**
	 * 按钮m_boReturn点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoReturn() throws Exception {
	}

	/**
	 * 按钮m_boSave点击时执行的动作,如有必要，请覆盖.
	 */
	protected void onBoSave() throws Exception {
		AggregatedValueObject billVO = getBillUI().getChangedVOFromUI();
		setTSFormBufferToVO(billVO);
		AggregatedValueObject checkVO = getBillUI().getVOFromUI();
		setTSFormBufferToVO(checkVO);
		// 进行数据晴空
		Object o = null;
		ISingleController sCtrl = null;
		if (getUIController() instanceof ISingleController) {
			sCtrl = (ISingleController) getUIController();
			if (sCtrl.isSingleDetail()) {
				o = billVO.getParentVO();
				billVO.setParentVO(null);
			} else {
				o = billVO.getChildrenVO();
				billVO.setChildrenVO(null);
			}
		}

		boolean isSave = true;

		// 判断是否有存盘数据
		if (billVO.getParentVO() == null
				&& (billVO.getChildrenVO() == null || billVO.getChildrenVO().length == 0)) {
			isSave = false;
		} else {
			if (getBillUI().isSaveAndCommitTogether())
				billVO = getBusinessAction().saveAndCommit(billVO,
						getUIController().getBillType(), _getDate().toString(),
						getBillUI().getUserObject(), checkVO);
			else

				// write to database
				billVO = getBusinessAction().save(billVO,
						getUIController().getBillType(), _getDate().toString(),
						getBillUI().getUserObject(), checkVO);
		}

		// 进行数据恢复处理
		if (sCtrl != null) {
			if (sCtrl.isSingleDetail())
				billVO.setParentVO((CircularlyAccessibleValueObject) o);
		}
		int nCurrentRow = -1;
		if (isSave) {
			if (isEditing()) {
				if (getBufferData().isVOBufferEmpty()) {
					getBufferData().addVOToBuffer(billVO);
					nCurrentRow = 0;

				} else {
					getBufferData().setCurrentVO(billVO);
					nCurrentRow = getBufferData().getCurrentRow();
				}
			} else {
				getBufferData().addVOsToBuffer(
						new AggregatedValueObject[] { billVO });
				nCurrentRow = getBufferData().getVOBufferSize() - 1;
			}
		}

		if (nCurrentRow >= 0) {
			getBufferData().setCurrentRowWithOutTriggerEvent(nCurrentRow);
		}
		
		setAddNewOperate(isAdding(), billVO);

		// 设置保存后状态
		setSaveOperateState();
		
		if (nCurrentRow >= 0) {
			getBufferData().setCurrentRow(nCurrentRow);
		}
	}

	/**
	 * 业务错误处理
	 */
	protected void onBusinessException(BusinessException e) {
		MessageDialog.showErrorDlg(getBillUI(), null, e.getMessage());
		Logger.error(e.getMessage(), e);

	}

	/**
	 * Button的事件响应处理。 创建日期：(2004-1-6 17:20:57)
	 * 
	 * @param bo
	 *            nc.ui.pub.ButtonObject 异常说明。
	 */
	public void onButton(ButtonObject bo) {
		if (getBillUI().getBillOperate() == IBillOperate.OP_ADD
				|| getBillUI().getBillOperate() == IBillOperate.OP_EDIT) {
			if (getBillCardPanelWrapper() != null)
				getBillCardPanelWrapper().getBillCardPanel().stopEditing();

		}
		try {
			ButtonObject parentBtn = bo.getParent();

			if (parentBtn != null && Integer.parseInt(parentBtn.getTag()) < 100) {
				int intParentBtn = Integer.parseInt(parentBtn.getTag());
				complexOnButton(intParentBtn, bo);
			} else {
				if (bo.getTag() == null)
					System.out.println("新增按钮必须设置TAG,TAG>100的整数.....");
				int intBtn = Integer.parseInt(bo.getTag());
				if (intBtn > 100)// 编号大于100认为是自定义按钮
					onBoElse(intBtn);
				else
					// 否则认为是预置按钮
					simpleOnButton(intBtn, bo);
			}
		} catch (BusinessException ex) {
			onBusinessException(ex);
		} catch (SQLException ex) {
			getBillUI().showErrorMessage(ex.getMessage());
		} catch (Exception e) {
			getBillUI().showErrorMessage(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * 参照上游单据进行拆单的处理。 创建日期：(2003-6-14 14:45:54)
	 * 
	 * @return nc.vo.pub.AggregatedValueObject[]
	 * @param refVos
	 *            nc.vo.pub.AggregatedValueObject[]
	 */
	protected AggregatedValueObject[] onSplitBillVos(
			AggregatedValueObject[] refVos) throws Exception {
		return null;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2004-1-8 16:33:05)
	 * 
	 * @param vos
	 *            nc.vo.pub.CircularlyAccessibleValueObject[]
	 */
	protected void processCopyedBodyVOsBeforePaste(
			CircularlyAccessibleValueObject[] vos) {
		if (vos == null)
			return;

		for (int i = 0; i < vos.length; i++) {
			vos[i].setAttributeValue(getUIController().getPkField(), null);
			vos[i].setAttributeValue(getUIController().getChildPkField(), null);
		}
	}

	/**
	 * 增加单据的处理，如果vos==null,则为新增处理 else为参照处理返回的所选择单据VO数组 创建日期：(2003-1-6 10:31:53)
	 * 
	 * nc.vo.pub.AggregatedValueObject
	 */
	protected AggregatedValueObject refVOChange(AggregatedValueObject[] vos)
			throws Exception {
		return vos[0];
	}

	private void setCheckManAndDate(AggregatedValueObject vo) throws Exception {
		// 放入审批日期、审批人
		vo.getParentVO().setAttributeValue(getBillField().getField_CheckDate(),
				getBillUI()._getDate());
		vo.getParentVO().setAttributeValue(getBillField().getField_CheckMan(),
				getBillUI()._getOperator());
	}

	/**
	 * 父类按钮的事件处理。 创建日期：(2004-2-25 21:04:27)
	 * 
	 * @param intBtn
	 *            int
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	private void simpleOnButton(int intBtn, ButtonObject bo)
			throws java.lang.Exception {
		buttonActionBefore(getBillUI(), intBtn);
		switch (intBtn) {
		case IBillButton.Add: {
			if (!getBillUI().isBusinessType().booleanValue()) {
				getBillUI().showHintMessage(
						nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"uifactory", "UPPuifactory-000061")/*
																	 * @res
																	 * "开始进行增加单据，请等待......"
																	 */);
				onBoAdd(bo);
				// 动作执行后处理
				buttonActionAfter(getBillUI(), intBtn);
			}
			break;
		}
		case IBillButton.Edit: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000067")/*
													 * @res "开始进行编辑单据，请等待......"
													 */);
			onBoEdit();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Del: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000068")/*
													 * @res "开始进行作废单据，请等待......"
													 */);
			onBoDel();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000069")/* @res "单据作废操作结束" */
			);
			break;
		}
		case IBillButton.Delete: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000070")/*
													 * @res "开始进行档案删除，请等待......"
													 */);
			onBoDelete();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000071")/* @res "档案删除完成" */
			);
			break;
		}
		case IBillButton.Query: {
			getBillUI().showHintMessage(bo.getName());

			if (super.getUIController() instanceof ISingleController) {
				ISingleController strl = (ISingleController) super
						.getUIController();
				if (strl.isSingleDetail())
					onBoBodyQuery();
				else
					onBoQuery();
			} else
				onBoQuery();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);

			break;
		}
		case IBillButton.Save: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000072")/*
													 * @res "开始进行单据保存，请等待......"
													 */);
			onBoSave();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000073")/* @res "单据保存操作结束" */
			);
			break;
		}
		case IBillButton.Cancel: {
			onBoCancel();
			// 清除提示状态栏提示信息
			getBillUI().showHintMessage("");
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Print: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000074")/*
													 * @res "开始进行打印单据，请等待......"
													 */);
			onBoPrint();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000075")/* @res "单据打印操作结束" */
			);
			break;
		}
		case IBillButton.DirectPrint: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000074")/*
													 * @res "开始进行打印单据，请等待......"
													 */);
			onBoDirectPrint();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000075")/* @res "单据打印操作结束" */
			);
			break;
		}

		case IBillButton.Return: {
			onBoReturn();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Card: {
			onBoCard();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			break;
		}
		case IBillButton.Refresh: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000076")/*
													 * @res "开始进行刷新单据，请等待......"
													 */);
			onBoRefresh();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000077")/* @res "单据刷新操作结束" */
			);

			break;
		}
		case IBillButton.Refbill: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000078")/*
													 * @res "开始进行参照单据，请等待......"
													 */);
			onBillRef();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000079")/* @res "单据参照操作结束" */
			);
			break;
		}
		case IBillButton.Copy: {
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000080")/*
													 * @res "开始进行数据复制，请等待......"
													 */);
			onBoCopy();
			// 动作执行后处理
			buttonActionAfter(getBillUI(), intBtn);
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000081")/* @res "数据复制操作结束" */
			);
			break;
		}
		case IBillButton.Audit: {
			// 因为下面的提示不符合易用性规范，现在删掉它们
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res "开始执行操作,请等待..."
			// */);
			onBoAudit();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res "执行完毕. "
			// */
			// );
			break;
		}
		case IBillButton.CancelAudit: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res 开始执行操作, 请等待...
			// */
			// );
			onBoCancelAudit();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res 执行完毕.
			// */
			// );
			break;
		}
		case IBillButton.Commit: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res 开始执行操作, 请等待...
			// */
			// );
			onBoCommit();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res 执行完毕.
			// */
			// );
			break;
		}
		case IBillButton.SelAll: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res 开始执行操作, 请等待...
			// */
			// );

			onBoSelAll();

			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res 执行完毕.
			// */
			// );
			break;
		}
		case IBillButton.SelNone: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res 开始执行操作, 请等待...
			// */
			// );
			onBoSelNone();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res 执行完毕.
			// */
			// );
			break;
		}
		case IBillButton.ImportBill: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res 开始执行操作, 请等待...
			// */
			// );
			onBoImport();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res 执行完毕.
			// */
			// );
			break;
		}
		case IBillButton.ExportBill: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res 开始执行操作, 请等待...
			// */
			// );
			onBoExport();
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res 执行完毕.
			// */
			// );
			break;
		}
		case IBillButton.ApproveInfo: {
			onBoApproveInfo();
			break;
		}
		default: {
			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000179")/*
			// * @res 开始执行操作, 请等待...
			// */
			// );

			onBoActionElse(bo);

			// getBillUI().showHintMessage(
			// nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
			// "UPPuifactory-000180")/*
			// * @res 执行完毕.
			// */
			// );
			break;
		}
		}

	}

	public void onBoApproveInfo() throws Exception {
		String strBilltype = getBillField().getField_Billtype();
		String billType = (String) getBufferData().getCurrentVO().getParentVO()
				.getAttributeValue(strBilltype);
		String billId = (String) getBufferData().getCurrentVO().getParentVO()
				.getPrimaryKey();

		// XXX:leijun+如果单据实体中没有单据类型属性，则取UI控制器中的单据类型
		if (StringUtil.isEmptyWithTrim(billType))
			billType = getUIController().getBillType();

		FlowStateDlg dlg = new FlowStateDlg(getBillUI().getParent(), billType,
				IPFConfigInfo.STATEBUSINESSTYPE, billId);
		dlg.setVisible(true);

	}

	/**
	 * 单据导出
	 * 
	 * @throws Exception
	 */
	protected void onBoExport() throws Exception {
		// int ncount = getBillUI().getBufferData().getVOBufferSize();
		// AggregatedValueObject[] billVOs = new HYBillVO[ncount];
		// for (int i = 0; i < ncount; i++)
		// {
		// billVOs[i] = getBillUI().getBufferData().getVOByRowNo(i);
		// }
		// IBillExport billExport = getBillExportTool();
		// SwapInfoVO swapinfo = getSwapInfo();
		// JFileChooser chooser = new JFileChooser();
		// BillDataFileFilter filter = new BillDataFileFilter();
		// chooser.setFileFilter(filter);
		// int returnVal = chooser.showSaveDialog(getBillUI());
		// if (returnVal == JFileChooser.APPROVE_OPTION)
		// {
		// billExport.exportBill(billVOs, swapinfo, chooser.getSelectedFile()
		// .getPath());
		// }
	}

	/**
	 * 取得交换信息设置
	 * 
	 * @return
	 */
	// protected SwapInfoVO getSwapInfo()
	// {
	// SwapInfoVO swapinfo = new SwapInfoVO();
	// swapinfo.setAccount(ClientEnvironment.getInstance().getAccount()
	// .getAccountCode());
	// swapinfo.setBilltype(getUIController().getBillType());
	// swapinfo.setFilename("");
	// swapinfo.setPk_corp(ClientEnvironment.getInstance().getCorporation().getPk_corp());
	// swapinfo.setReceiver("");
	// swapinfo.setSender("NC");
	// swapinfo.setIsExchange("Y");
	// swapinfo.setReplace("Y");
	// return swapinfo;
	// }
	/**
	 * 取得单据导出工具,默认为VOToXML
	 * 
	 * @return
	 */
	// protected IBillExport getBillExportTool()
	// {
	// return new VOToXML();
	// }
	/**
	 * 单据导入
	 * 
	 * @throws Exception
	 */
	protected void onBoImport() throws Exception {
		// JFileChooser chooser = new JFileChooser();
		// BillDataFileFilter filter = new BillDataFileFilter();
		// chooser.setFileFilter(filter);
		// int returnVal = chooser.showOpenDialog(getBillUI());
		// if (returnVal == JFileChooser.APPROVE_OPTION)
		// {
		// //导入单据
		// String filename = chooser.getSelectedFile().getPath();
		// IBillImport importBill = new XMLToVO();
		// Object[] result = importBill.importBill(filename, ClientEnvironment
		// .getInstance().getAccount().getAccountCode(),
		// getUIController().getBillType());
		// //放到单据上
		// if (result == null || result.length == 0)
		// {
		// throw new BusinessException("没有得到任何导入的数据！");
		// }
		// //如果是聚合VO
		// if (result[0] instanceof AggregatedValueObject)
		// {
		// AggregatedValueObject[] billvos = new
		// AggregatedValueObject[result.length];
		// for (int i = 0; i < result.length; i++)
		// {
		// billvos[i] = (AggregatedValueObject) result[i];
		// }
		// addAggDataToUI(billvos);
		// }
		// //如果是单表类型
		// else if (result[0] instanceof SuperVO)
		// {
		// SuperVO[] billvos = new SuperVO[result.length];
		// for (int i = 0; i < result.length; i++)
		// {
		// billvos[i] = (SuperVO) result[i];
		// }
		// updateBuffer();
		// }
		// else if (result[0] instanceof ValueObject)
		// {
		// throw new BusinessException("暂时不支持ValueObject数据导入！");
		// }
		// }
	}

	private IBillBusiListener m_bbl = null;

	/**
	 * 此处插入方法说明。 创建日期：(2004-1-8 12:55:35)
	 * 
	 * nc.ui.pub.ButtonObject 异常说明。
	 */
	protected void addBillBusiListener(IBillBusiListener bbl) {
		m_bbl = bbl;
	}

	/**
	 * 进行检查单据状态是否一致,如果状态等于当前指定状态，则该return true动作取消。 创建日期：(2004-6-11 17:33:18)
	 * 
	 * @param vo
	 *            nc.vo.pub.AggregatedValueObject
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	protected boolean checkVOStatus(AggregatedValueObject vo, int[] intStatus)
			throws java.lang.Exception {
		if (vo == null || vo.getParentVO() == null)
			return true;
		Integer intState = (Integer) vo.getParentVO().getAttributeValue(
				getBillField().getField_BillStatus());
		if (intState == null)
			throw new BusinessException(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("uifactory", "UPPuifactory-000082")/*
																	 * @res
																	 * "单据模版:单据状态vbillstatus的处理错误!"
																	 */);
		int intCurrentState = intState.intValue();
		for (int i = 0; i < intStatus.length; i++) {
			if (intStatus[i] == intCurrentState)
				return true;
		}
		return false;
	}

	/**
	 * 实例化界面进行拆分单据VO的处理, 如果进行拆分单据处理需要重载该方法 创建日期：(2004-1-3 18:13:36)
	 */
	protected IBusinessSplit createBusinessSplit() {
		return new DefaultBusinessSplit();
	}

	/**
	 * 在查询数据时使用，是否是否业务类型编码替代业务类型pk 默认为false 表示使用业务类型pk。 可以在衍生类中重写
	 * 创建日期：(2004-5-26 14:08:42)
	 * 
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public UFBoolean getBillIsUseBusiCode() {
		return new UFBoolean(false);
	}

	/**
	 * 获得子表数据。 创建日期：(2004-3-11 17:44:14)
	 * 
	 * @return nc.vo.pub.CircularlyAccessibleValueObject[]
	 */
	private CircularlyAccessibleValueObject[] getChildVO(
			AggregatedValueObject retVo) {
		CircularlyAccessibleValueObject[] childVos = null;
		if (retVo instanceof IExAggVO)
			childVos = ((IExAggVO) retVo).getAllChildrenVO();
		else
			childVos = retVo.getChildrenVO();
		return childVos;
	}

	/**
	 * 主表对应的固定查询条件。 创建日期：(2002-12-27 16:58:18)
	 * 
	 * @return java.lang.String
	 */
	protected String getHeadCondition() {
		// 公司
		if (getBillCardPanelWrapper() != null)
			if (getBillCardPanelWrapper().getBillCardPanel().getHeadTailItem(
					getBillField().getField_Corp()) != null)
				return getBillField().getField_Corp() + "='"
						+ getBillUI()._getCorp().getPrimaryKey() + "'";
		return null;
	}

	/**
	 * 初始化在NodeKey按钮 创建日期：(2002-12-23 10:11:27)
	 */
	public final void initNodeKeyButton() {
		try {
			ButtonObject boNodeKey = getButtonManager().getButton(
					IBillButton.NodeKey);
			// NodeKey设置模版
			if (boNodeKey != null) {
				if (boNodeKey.getChildButtonGroup() != null
						&& boNodeKey.getChildButtonGroup().length > 0) {
					ButtonObject bo = boNodeKey.getChildButtonGroup()[0];
					bo.setSelected(true);

					// NodeKey
					getBillUI().setNodeKey(bo.getTag());
				} else
					System.out.println("没有初始化NodeKey类型!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			getBillUI().showHintMessage(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
							"UPPuifactory-000083")/*
													 * @res "初始化业务NodeKey按钮有误"
													 */);
		}
	}

	/**
	 * 进行参照单据时是否需要数据交换。 创建日期：(2004-4-5 19:12:29)
	 * 
	 * @return boolean
	 */
	protected boolean isDataChange() {
		return true;
	}

	/**
	 * 参照单据进行制单。 创建日期：(2003-6-12 10:01:28)
	 */
	protected void onBillRef(int intBtn, String refBilltype) throws Exception {
		ButtonObject btn = getButtonManager().getButton(intBtn);
		btn.setTag(refBilltype + ":");
		onBoBusiTypeAdd(btn, null);
	}

	/**
	 * 按钮m_boNodekey点击时执行的动作,如有必要，请覆盖.
	 */
	private final void onBoNodekey(ButtonObject bo) throws Exception {
		bo.setSelected(true);
		// 设置NodeKey
		getBillUI().setNodeKey(bo.getTag());

		// 重新刷新UI
		getBillUI().initUI();
		// 清空查询模版
		setQueryUI(null);
		// 清空UI缓存
		getBillUI().getBufferData().clear();
		getBillUI().getBufferData().setCurrentRow(-1);

		getBillUI().updateButtonUI();
	}

	/**
	 * 按钮m_boSelAll点击时执行的动作, 如有必要，请覆盖.
	 */
	protected void onBoSelAll() throws Exception {
		return;
	}

	/**
	 * 按钮m_boSelNone点击时执行的动作, 如有必要，请覆盖.
	 */
	protected void onBoSelNone() throws Exception {
		return;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2004-1-8 12:55:35)
	 * 
	 * nc.ui.pub.ButtonObject 异常说明。
	 */
	protected void removeBillBusiListener() {
		m_bbl = null;
	}

	/**
	 * 设置新增的操作处理。 创建日期：(2004-4-1 10:11:58)
	 */
	protected void setAddNewOperate(boolean isAdding,
			AggregatedValueObject billVO) throws Exception {
		
	}

	/**
	 * 设置界面数据，拆分处理器重载该方法。 创建日期：(2004-4-1 8:20:25)
	 * 
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	protected void setRefData(AggregatedValueObject[] vos)
			throws java.lang.Exception {
		// 设置单据状态
		getBillUI().setCardUIState();

		AggregatedValueObject vo = refVOChange(vos);
		if (vo == null)
			throw new BusinessException(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("uifactory", "UPPuifactory-000084")/*
																	 * @res
																	 * "未选择参照单据"
																	 */);
		// 设置为新增处理
		getBillUI().setBillOperate(IBillOperate.OP_REFADD);
		// 填充界面
		getBillCardPanelWrapper().setCardData(vo);
	}

	/**
	 * 设置保存后操作状态。 创建日期：(2004-4-1 10:11:58)
	 */
	protected void setSaveOperateState() throws Exception {
		getBillUI().setBillOperate(IBillOperate.OP_NOTEDIT);
	}

	/**
	 * 设置Buffer中的TS到当前设置VO。 创建日期：(2004-5-14 18:04:59)
	 * 
	 * @param setVo
	 *            nc.vo.pub.AggregatedValueObject
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	protected void setTSFormBufferToVO(AggregatedValueObject setVo)
			throws java.lang.Exception {
		if (setVo == null)
			return;
		AggregatedValueObject vo = getBufferData().getCurrentVO();
		if (vo == null)
			return;
		if (getBillUI().getBillOperate() == IBillOperate.OP_EDIT) {

			if (vo.getParentVO() != null && setVo.getParentVO() != null)
				setVo.getParentVO().setAttributeValue("ts",
						vo.getParentVO().getAttributeValue("ts"));
			// SuperVO[] changedvos = (SuperVO[]) setVo.getChildrenVO();
			SuperVO[] changedvos = (SuperVO[]) getChildVO(setVo);

			if (changedvos != null && changedvos.length != 0) {
				// 哈西化缓存中的字表数据
				HashMap bufferedVOMap = null;
				// SuperVO[] bufferedVOs = (SuperVO[]) vo.getChildrenVO();
				SuperVO[] bufferedVOs = (SuperVO[]) getChildVO(vo);
				if (bufferedVOs != null && bufferedVOs.length != 0) {
					bufferedVOMap = Hashlize.hashlizeObjects(bufferedVOs,
							new VOHashPrimaryKeyAdapter());
					for (int i = 0; i < changedvos.length; i++) {
						if (changedvos[i].getPrimaryKey() != null) {
							ArrayList bufferedAl = (ArrayList) bufferedVOMap
									.get(changedvos[i].getPrimaryKey());

							if (bufferedAl != null) {
								SuperVO bufferedVO = (SuperVO) bufferedAl
										.get(0);
								changedvos[i].setAttributeValue("ts",
										bufferedVO.getAttributeValue("ts"));
							}
						}
					}
				}
			}
		}
	}

	public IBillBusiListener getBillBusiListener() {
		return m_bbl;
	}
}