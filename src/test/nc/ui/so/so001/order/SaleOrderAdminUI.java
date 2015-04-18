package nc.ui.so.so001.order;

import java.util.Vector;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.scm.extend.IFuncExtend;
import nc.ui.scm.so.SaleBillType;
import nc.ui.so.so001.panel.SaleBillUI;
import nc.vo.so.so001.SaleOrderVO;

/**
 * 销售订单管理 创建日期：(2001-4-13 15:26:05)
 * 
 * @author 宋杰
 * 
 * @rebuild V5.1 销售订单维护 zhongwei
 */
public class SaleOrderAdminUI extends SaleBillUI {

	// 按钮初始化标记
	private boolean b_init;

	public SaleOrderAdminUI() {
		super();
	}

	protected void initCustomerUI() {
		// new OpenSaleOrderForCRM().openNode(this);//test
	}

	public SaleOrderAdminUI(String pk_corp, String billtype, String busitype, String operator, String id) {
		super(pk_corp, billtype, busitype, operator, id);
	}

	@Override
	public String getBillButtonAction(nc.ui.pub.ButtonObject bo) {
		return null;
	}

	@Override
	public String getBillButtonState() {
		return null;
	}

	/**
	 * 获得按钮数组。
	 * 
	 * 支持插件菜单,location规则如下：0 卡片列表;1 退货;2 BOM; 3 核销
	 * 
	 * 创建日期：(2001-11-15 8:58:51)
	 * 
	 * @return nc.ui.pub.ButtonObject[]
	 */
	public nc.ui.pub.ButtonObject[] getBillButtons() {
		if (!b_init) {
			initBtnGrp();
			b_init = true;
		}

		if (strShowState.equals("列表")) {  /*-=notranslate=-*/
			return aryListButtonGroup;
		} else if (strShowState.equals("卡片")) {  /*-=notranslate=-*/
			initLineButton();
			return aryButtonGroup;
		} else {
			return aryBatchButtonGroup;
		}
	}

	/**
	 * V51要求卡片和列表的按钮一致
	 * 
	 */
	private void initBtnGrp() {

		// 列表按钮
		ButtonObject[] aryListButtonGroup = { boBusiType, boAdd, boSave, boMaintain, boLine, boAudit, boAction,
				boQuery, boBrowse, boCard, boPrntMgr, boAssistant, boAsstntQry };

		// 卡片按钮
		ButtonObject[] aryButtonGroup = { boBusiType, boAdd, boSave, boMaintain, boLine, boAudit, boAction, boQuery,
				boBrowse, boReturn, boPrntMgr, boAssistant, boAsstntQry,boCalculate,boReadCard };

		// 退货卡片按钮
		ButtonObject[] aryBatchButtonGroup = { boBatch, boLine, boRefundmentDocument, boBack };

		// 配置销售按钮组
		ButtonObject[] bomButtonGroup = { boBomSave, boBomEdit, boBomCancel, boBomReturn, boOrderQuery,
		// boBomPrint
		};

		/*IFuncExtend funcExtend = getFuncExtend();
		if (funcExtend != null) {
			ButtonObject[] boExtend = m_funcExtend.getExtendButton();
			if (boExtend != null && boExtend.length > 0) {

				// 卡片按钮
				ButtonObject[] botempcard = new ButtonObject[aryButtonGroup.length + boExtend.length];

				System.arraycopy(aryButtonGroup, 0, botempcard, 0, aryButtonGroup.length);

				System.arraycopy(boExtend, 0, botempcard, aryButtonGroup.length, boExtend.length);

				aryButtonGroup = botempcard;

				// 列表按钮
				ButtonObject[] botemplist = new ButtonObject[aryListButtonGroup.length + boExtend.length];

				System.arraycopy(aryListButtonGroup, 0, botemplist, 0, aryListButtonGroup.length);

				System.arraycopy(boExtend, 0, botemplist, aryListButtonGroup.length, boExtend.length);

				aryListButtonGroup = botemplist;
			}

		}
		ButtonObject[] boExtend = getExtendBtns();
		if (boExtend != null && boExtend.length > 0) {

			// 卡片按钮
			ButtonObject[] botempcard = new ButtonObject[aryButtonGroup.length + boExtend.length];

			System.arraycopy(aryButtonGroup, 0, botempcard, 0, aryButtonGroup.length);

			System.arraycopy(boExtend, 0, botempcard, aryButtonGroup.length, boExtend.length);

			aryButtonGroup = botempcard;

			// 列表按钮
			ButtonObject[] botemplist = new ButtonObject[aryListButtonGroup.length + boExtend.length];

			System.arraycopy(aryListButtonGroup, 0, botemplist, 0, aryListButtonGroup.length);

			System.arraycopy(boExtend, 0, botemplist, aryListButtonGroup.length, boExtend.length);

			aryListButtonGroup = botemplist;
		}*/

		// 导入插件菜单
		ButtonObject[][] ret_butns = new SaleOrderPluginMenuUtil().addMenu(aryListButtonGroup, aryButtonGroup,
				aryBatchButtonGroup, bomButtonGroup, getModuleCode());

		this.aryListButtonGroup = ret_butns[0];
		this.aryButtonGroup = ret_butns[1];
		this.aryBatchButtonGroup = ret_butns[2];
		this.bomButtonGroup = ret_butns[3];

	}

	@Override
	public String getBillID() {

		if ("退货".equals(strState)) { /*-=notranslate=-*/
			return id;
		}

		if (strShowState == "列表") {  /*-=notranslate=-*/
			return (String) getBillListPanel().getHeadBillModel().getValueAt(num, "csaleid");
		} else if (strShowState == "卡片") {  /*-=notranslate=-*/
			return (String) getBillCardPanel().getHeadItem("csaleid").getValueObject();
		} else
			return null;
	}

	@Override
	public String getNodeCode() {
		return getModuleCode();
	}

	@Override
	public String getTitle() {
		return getBillCardPanel().getBillData().getTitle();
		//return getBillListPanel().getBillListData().getTitle();
	}

	protected void initButtons() {
		
		PfUtilClient.retBusiAddBtn(boBusiType, boAdd, getCorpPrimaryKey(), SaleBillType.SaleOrder);

		// 业务类型
		//PfUtilClient.retBusinessBtn(boBusiType, getCorpPrimaryKey(), SaleBillType.SaleOrder);
		if (boBusiType.getChildButtonGroup() != null && boBusiType.getChildButtonGroup().length > 0) {
			boBusiType.setTag(boBusiType.getChildButtonGroup()[0].getTag());
			boBusiType.getChildButtonGroup()[0].setSelected(true);
			boBusiType.setCheckboxGroup(true);
			// 新增
			//PfUtilClient.retAddBtn(boAdd, getCorpPrimaryKey(), getBillType(), boBusiType.getChildButtonGroup()[0]);
		}

		// 维护
		boMaintain.removeAllChildren();
		boMaintain.addChildButton(boEdit);
		boMaintain.addChildButton(boCancel);
		boMaintain.addChildButton(boBlankOut);
		boMaintain.addChildButton(boCopyBill);

		// 行操作
		boLine.removeAllChildren();
		boLine.addChildButton(boAddLine);
		boLine.addChildButton(boDelLine);
		boLine.addChildButton(boInsertLine);
		boLine.addChildButton(boCopyLine);
		boLine.addChildButton(boPasteLine);
		boLine.addChildButton(boPasteLineToTail);
		boLine.addChildButton(boFindPrice);
		boLine.addChildButton(boCardEdit);
		boLine.addChildButton(boResortRowNo);

		// 浏览
		boBrowse.removeAllChildren();
		boBrowse.addChildButton(boRefresh);
		boBrowse.addChildButton(boFind);
		boBrowse.addChildButton(boFirst);
		boBrowse.addChildButton(boPre);
		boBrowse.addChildButton(boNext);
		boBrowse.addChildButton(boLast);
		boBrowse.addChildButton(boListSelectAll);
		boBrowse.addChildButton(boListDeselectAll);

		// 执行
		retElseBtn("Order002", "Order001");
		retElseBtn("Order002", "Order003");

		// 辅助查询
		boAsstntQry.removeAllChildren();
		boAsstntQry.addChildButton(boOrderQuery);
		boAsstntQry.addChildButton(boOnHandShowHidden);
		boAsstntQry.addChildButton(boAuditFlowStatus);
		boAsstntQry.addChildButton(boCustCredit);
		boAsstntQry.addChildButton(boOrderExecRpt);
		boAsstntQry.addChildButton(boCustInfo);
		boAsstntQry.addChildButton(boPrifit);

		// 打印
		boPrntMgr.removeAllChildren();
		boPrntMgr.addChildButton(boPreview);
		boPrntMgr.addChildButton(boPrint);
		boPrntMgr.addChildButton(boSplitPrint);

		// 协同
		boAssistant.addChildButton(boCoPushPo);
		boAssistant.addChildButton(boCoRefPo);

		if (strShowState.equals("列表")) { /*-=notranslate=-*/
			setButtons(getBillButtons());
		}// end list
		else if (strShowState.equals("卡片")) { /*-=notranslate=-*/

			if (strState.equals("BOM")) {
				setButtons(bomButtonGroup);
			} else {
				setButtons(getBillButtons());
			}

		}// end card
	}

	public void setButtonsState() {
		super.setButtonsState();

		getPluginProxy().setButtonStatus();
	}

	@Override
	protected String getClientUI() {
		return SaleOrderAdminUI.class.getName();
	}

	/**
	 * 仿照onNew实现,相当于转单新增
	 * 
	 * @param vo
	 */
	public void addCRMInfo(SaleOrderVO vo) {
		try {
			// 切换到卡片
			strShowState = "卡片";  /*-=notranslate=-*/
			strState = "自由";  /*-=notranslate=-*/
			switchInterface();
			setButtons(getBillButtons());
			updateUI();

			// 销售不走询价：SA15--N,恢复模板的默认值
			if (!SA_15.booleanValue())
				getBillCardTools().resumeBillBodyItemsEdit(getBillCardTools().getSaleOrderItems_Price_Original());

			// PfUtilClient.childButtonClicked(boAdd, getCorpPrimaryKey(),
			// getNodeCode(), getClientEnvironment().getUser()
			// .getPrimaryKey(), getBillType(), this);
			vRowATPStatus = new Vector();

			boolean bisCalculate = getBillCardPanel().getBillModel().isNeedCalculate();
			getBillCardPanel().getBillModel().setNeedCalculate(false);

			if (vo == null) {
				onNewBySelf();
			} else {
				// //指定一个业务类型
				getBillCardPanel().setBusiType(getSelectedBusyType());
				onNewByOther(new SaleOrderVO[] { vo });
			}

			getBillCardPanel().getBillModel().setNeedCalculate(bisCalculate);
			if (bisCalculate)
				getBillCardPanel().getBillModel().reCalcurateAll();
			binitOnNewByOther = false;

			// 设置表体菜单
			setBodyMenuItem(false);
			/**CRM销售机会传递过来的销售订单打开时编辑性设置*/
			   //放开业务类型编辑性
		    if(null != getBillCardPanel().getHeadItem("cbiztype")){
		    getBillCardPanel().getHeadItem("cbiztype").setEnabled(true);
		    getBillCardPanel().getHeadItem("cbiztype").setEdit(true);
		    }
		    
		    if(null != getBillCardPanel().getHeadItem("cbiztypename")){
		    getBillCardPanel().getHeadItem("cbiztypename").setEnabled(true);
		    getBillCardPanel().getHeadItem("cbiztypename").setEdit(true);
		    }
		    //封死备注编辑性
		    getBillCardPanel().getHeadItem("vnote").setEnabled(false);
		    getBillCardPanel().getHeadItem("vnote").setEdit(false);
		    /**CRM销售机会传递过来的销售订单打开时编辑性设置*/
			// 清除缓存数据
			getBillCardTools().clearCatheData();

			setButtonsState();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
