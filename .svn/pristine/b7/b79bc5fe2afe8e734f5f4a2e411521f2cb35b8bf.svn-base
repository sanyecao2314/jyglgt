package nc.ui.so.so001.panel.list;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JComponent;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.pub.beans.ValueChangedListener;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillListData;
import nc.ui.pub.query.EditComponentFacotry;
import nc.ui.scm.pub.bill.IBillExtendFun;
import nc.ui.scm.pub.def.DefSetTool;
import nc.ui.scm.so.SaleBillType;
import nc.ui.so.so001.panel.card.SOBillCardTools;
import nc.ui.so.so001.panel.card.SOBillQueryDlg;
import nc.ui.so.so001.panel.card.SaleBillCardUI;
import nc.vo.scm.pub.SCMEnv;
import nc.vo.so.so001.SaleOrderVO;
import nc.vo.so.so001.SaleorderBVO;
import nc.vo.so.so001.SaleorderHVO;

/**
 * 销售订单UI端抽象基类(主要处理列表和缓存操作)
 * 
 * @author zhongwei
 * 
 */
@SuppressWarnings({ "restriction", "serial" })
public abstract class SaleBillListUI extends SaleBillCardUI implements
		IBillExtendFun, ValueChangedListener {
	
	// 库存组织属性
    public final static int ICalbodyType_All = 0; // 0：二者皆是
    public final static int ICalbodyType_STOR = 1;// 1：仓储
    public final static int ICalbodyType_COST = 2;// 2：成本

	private SOBillListPanel ivjSaleOrderListPane;

	// 退货卡片按钮
	protected ButtonObject[] aryBatchButtonGroup;

	// 卡片按钮组
	protected ButtonObject[] aryButtonGroup;

	// 列表按钮组
	protected ButtonObject[] aryListButtonGroup;

	protected ButtonObject boAdd;

	protected ButtonObject boFirst;

	protected ButtonObject boPre;

	protected ButtonObject boNext;

	protected ButtonObject boLast;

	protected ButtonObject boEdit;

	protected ButtonObject boSave;

	protected ButtonObject boCancel;

	protected ButtonObject boSendAudit;
	//add by sk 2011-10-14   导入
	protected ButtonObject boImport;
	
	protected ButtonObject boQTImport;
	//end by sk

	//add by shipeng 2014-10-11   取价
	protected ButtonObject boCalculate;
	protected ButtonObject boReadCard;
	
	//end by shipeng
	
	// 暂未使用
	protected ButtonObject boOrdPay;

	protected ButtonObject boCachPay;

	public ButtonObject boAction;

	protected ButtonObject boLine;

	protected ButtonObject boPreview;

	protected ButtonObject boPrint;
	
	protected ButtonObject boSplitPrint;

	public ButtonObject boAssistant;

	protected ButtonObject boDocument;

	protected ButtonObject boRefundmentDocument;

	protected ButtonObject boReturn;

	protected ButtonObject boAuditFlowStatus;

	protected ButtonObject boOrderQuery;

	protected ButtonObject boBatch;

	protected ButtonObject boBrowse;

	protected ButtonObject boListSelectAll;

	protected ButtonObject boListDeselectAll;

	protected ButtonObject boCard;

	protected ButtonObject boRefundment;

	protected ButtonObject boFind;

	protected ButtonObject boQuery;

	protected ButtonObject boMaintain;

	protected ButtonObject boBlankOut;

	protected ButtonObject boCopyBill;

	protected ButtonObject boAudit;

	protected ButtonObject boCancelAudit;

	protected ButtonObject boModification;

	protected ButtonObject boCancelFreeze;

	protected ButtonObject boFreeze;

	protected ButtonObject boFinish;

	protected ButtonObject boRefresh;

	protected ButtonObject boOrdBalance;

	protected ButtonObject boAsstntQry;

	protected ButtonObject boOnHandShowHidden;

	protected ButtonObject boCustCredit;

	protected ButtonObject boOrderExecRpt;

	protected ButtonObject boCustInfo;

	protected ButtonObject boPrifit;

	protected ButtonObject boPrntMgr;

	protected ButtonObject boBack;
	
	//参照协同采购订单
	protected ButtonObject boCoRefPo;
	//生成协同采购订单
	protected ButtonObject boCoPushPo;

	private SOBillQueryDlg dlgQuery;

	protected int selectRow = -1; // 选中行
	
	protected String csaleid = null; // 当前选中单据ID

	protected int num = -1; // 当前ID

	@SuppressWarnings("unchecked")
	protected Vector vIDs; // ID

	protected JComponent curShowPanel = null;

	//protected ArrayList alInvs = getBillCardPanel().alInvs;

	public SaleBillListUI() {
		super();
	}

	public SaleBillListUI(String pk_corp, String billtype, String busitype,
			String operator, String id) {
		super(pk_corp, billtype, busitype, operator, id);
	}

	/**
	 * 初始化类。
	 */
	protected void initialize() {
		long st = System.currentTimeMillis();

		strShowState = "列表"; /*-=notranslate=-*/
		
		// 单据显示状态
		selectRow = -1; // 选中行
		num = -1; // 当前ID

		loadAllBtns();
		
		super.initialize();

		loadListTemplet();
		initIDs();
		switchInterface();

		getBillListPanel().addBodyTotalLstn();
		getBillListPanel().addHeadSortRelaLstn();
		getBillListPanel().addEditListener(ivjSaleOrderListPane);

		setTitleText(getTitle());
		
		nc.vo.scm.pub.SCMEnv.out(" admin initialize cost time "
				+ (System.currentTimeMillis() - st));
	}

	/**
	 * 初始化所有按钮对象，没有业务特性
	 * 	
	 * 在初始化界面之前调用，子类可覆写
	 * 
	 */
	protected void loadAllBtns() {
		getBoAction();
		getBoAdd();
		getBoAddLine();
		getBoAssistant();
		getBoAsstntQry();
		getBoAudit();
		getBoAuditFlowStatus();
		getBoBack();
		getBoBatch();
		getBoBlankOut();
		getBoBrowse();
		getBoCancel();
		getBoCancelAudit();
		getBoCancelFreeze();
		getBoCard();
		getBoCopyBill();
		getBoCopyLine();
		getBoFindPrice();
		getBoCardEdit();
		getBoResortRowNo();
		getBoCustCredit();
		getBoCustInfo();
		getBoDelLine();
		getBoDocument();
		getBoRefundmentDocument();
		getBoEdit();
		getBoFind();
		getBoFinish();
		getBoFirst();
		getBoFreeze();
		getBoInsertLine();
		getBoLast();
		getBoLine();
		getBoListDeselectAll();
		getBoListSelectAll();
		getBoMaintain();
		getBoModification();
		getBoNext();
		getBoOnHandShowHidden();
		getBoOrdBalance();
		getBoOrderExecRpt();
		getBoOrderQuery();
		getBoCachPay();
		getBoPasteLine();
		getBoPasteLineToTail();
		getBoPre();
		getBoPreview();
		getBoPrifit();
		getBoPrint();
		getBoSplitPrint();
		getBoPrintMgr();
		getBoQuery();
		getBoRefresh();
		getBoRefundment();
		getBoReturn();
		getBoSave();
		getBoSendAudit();
		getBoStockLock();
		getBoSendInv();
		getBoSupplyInv();
		getBoDirectInv();
		getBoCoRefPo();
		getBoCoPushPo();
		//导入
		getBoImport();
		getBoQTImport();
		//取价 add  by shipeng at 2014-10-11
		getBoCalculate();
		getBoReadCard();//读卡
		//end by shipeng at 2014-10-11
	}
	/**
	 * 功能：导入excel其他
	 * @author sk
	 * @param boQTImport
	 * @time 2011-10-17
	 * */
	protected ButtonObject getBoQTImport() {
		if (boQTImport == null) {
			boQTImport = new ButtonObject("其它导入",
					"其它导入", 988, "其它导入"); 
		}
		return boQTImport;
	}

	
	/**
	 * 功能：导入excel中板
	 * @author sk
	 * @param boImport
	 * @time 2011-10-17
	 * */
	protected ButtonObject getBoImport() {
		if (boImport == null) {
			boImport = new ButtonObject("中板导入",
					"中板导入", 987, "中板导入"); 
		}
		return boImport;
	}
	
	/**
	 * 功能：取价
	 * @author shipeng
	 * @param boCalculate
	 * @time 2014-10-11
	 * */
	protected ButtonObject getBoCalculate() {
		if (boCalculate == null) {
			boCalculate = new ButtonObject("取价",
					"取价", 989, "取价"); 
		}
		return boCalculate;
	}
	
	/**
	 * 功能：读卡
	 * @author shipeng
	 * @param boCalculate
	 * @time 2014-10-11
	 * */
	protected ButtonObject getBoReadCard() {
		if (boReadCard == null) {
			boReadCard = new ButtonObject("读卡",
					"读卡", 990, "读卡"); 
		}
		return boReadCard;
	}
	
	private ButtonObject getBoCoRefPo(){
		if(boCoRefPo==null){
			boCoRefPo = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("sopub", "UPPsopub-000325")/*
			 * @res "参照协同采购订单"
			 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("sopub", "UPPsopub-000325")/*
			 * @res
			 * "参照协同采购订单"
			 */, 0, "参照协同采购订单"); /*-=notranslate=-*/
			boCoRefPo.setTag("21:协同"); /*-=notranslate=-*/
			
		}
		return boCoRefPo;
		
	}
	private ButtonObject getBoCoPushPo(){
		if(boCoPushPo==null){
			boCoPushPo = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("sopub", "UPPsopub-000324")/*
			 * @res "生成协同采购订单"
			 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("sopub", "UPPsopub-000324")/*
			 * @res
			 * "生成协同采购订单"
			 */, 0, "生成协同采购订单"); /*-=notranslate=-*/
			
		}
		return boCoPushPo;
		
	}

	/**
	 * 加载列表模板。
	 * 
	 * 创建日期：(2001-11-15 9:03:35)
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void loadListTemplet() {
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"SCMCOMMON", "UPPSCMCommon-000135")/* @res "开始加载列表模板...." */);
		
		BillListData bd = new BillListData(billtempletVO);
		billtempletVO = null;

		BillItem bm = bd.getHeadItem("naccountperiod");
		if (bm != null) {
			bm.setDecimalDigits(0);
		}

		SOBillCardTools.processCTBillItem(null, bd);
		
		// 设置界面，置入数据源
		getBillListPanel().setListData(bd);		
		
		// 改变界面
		setListPanelByPara(bd);
		
		setButtonsState();
		
		// 设置下拉框
		initBodyComboBox();

		BillItem bmbiztype = bd.getHeadItem("cbiztype");
		if (bmbiztype != null) {
			bmbiztype.setShow(false);
			bmbiztype.setEdit(false);
		}

		BillItem cbiztypename = bd.getHeadItem("cbiztypename");
		if (cbiztypename != null) {
			cbiztypename.setEdit(false);
		}

		getBillListPanel().getChildListPanel().setTatolRowShow(true);
		
		getBillListPanel().getHeadTable().setSelectionMode(
				javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
	    // 表体加背景色
		String[] tableCodes = getBillListPanel().getBillListData().getBodyTableCodes();
		for (String tableCode : tableCodes) {
			getBillListPanel().getBodyTable(tableCode).setRowSelectionAllowed(true);
			getBillListPanel().getBodyTable(tableCode).setColumnSelectionAllowed(false);
		}

		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"SCMCOMMON", "UPPSCMCommon-000147")/* @res "列表模板加载成功！" */);
	}

	/**
	 * 根据参数改变列表界面（主要控制字段精度）。
	 * 
	 * 创建日期：(2001-9-27 16:13:57)
	 * 
	 */
	private void setListPanelByPara(BillListData bdData) {

		try {
            //隐藏捆绑、预订单是否关闭
			getBillListPanel().hideBodyTableCol("bbindflag");
			getBillListPanel().hideBodyTableCol("bpreorderclose");
			getBillListPanel().hideHeadTableCol("nexchangeotobrate");
			getBillListPanel().hideHeadTableCol("ccurrencytypeid");

			// 建议发货库存组织(V5.3去掉此参数控制)
			/*if (SA_16 != null && SA_16.booleanValue()) {
				if (bdData.getBodyItem("sharecode2", "cadvisecalbody") != null)
					bdData.getBodyItem("sharecode2", "cadvisecalbody").setShow(
							true);
				else
					bdData.getBodyItem("cadvisecalbody").setShow(true);
			} else {
				if (bdData.getBodyItem("sharecode2", "cadvisecalbody") != null)
					bdData.getBodyItem("sharecode2", "cadvisecalbody").setShow(
							false);
				else
					bdData.getBodyItem("cadvisecalbody").setShow(false);
			}*/


            //根据币种设置折本汇率
			//getBillListPanel().setCurrTypeDigit();
			
			// 设置小数位数
			bdData.getBodyItem("nexchangeotobrate").setDecimalDigits(4);
			
			// 设置折扣小数位数
			if (bdData.getBodyItem("nitemdiscountrate") != null)
				bdData.getBodyItem("nitemdiscountrate").setDecimalDigits(6);
			if (bdData.getBodyItem("ndiscountrate") != null)
				bdData.getBodyItem("ndiscountrate").setDecimalDigits(6);
            if (bdData.getHeadItem("ndiscountrate")!= null)
            	bdData.getHeadItem("ndiscountrate").setDecimalDigits(6);            
            
			// 数量
			String[] aryNum = SOBillCardTools.getSaleItems_Num();

			if (BD501 != null) {
				for (int i = 0; i < aryNum.length; i++) {
					bdData.getBodyItem(aryNum[i]).setDecimalDigits(
							BD501.intValue());
				}
			}
			if (BD502 != null) {
				bdData.getBodyItem("npacknumber").setDecimalDigits(
						BD502.intValue());
				// bdData.getBodyItem("nquoteunitnum").setDecimalDigits(BD502.intValue());
			}
			// 单价
			if (BD505 != null) {
				bdData.getBodyItem("noriginalcurprice").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("noriginalcurtaxprice").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("noriginalcurnetprice").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("noriginalcurtaxnetprice").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("norgqttaxprc").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("norgqtprc").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("norgqttaxnetprc").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("norgqtnetprc").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("nprice").setDecimalDigits(BD505.intValue());
				bdData.getBodyItem("ntaxprice").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("nnetprice").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("ntaxnetprice").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("nqttaxnetprc").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("nqtnetprc").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("nqttaxprc").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("nqtprc").setDecimalDigits(BD505.intValue());
				bdData.getBodyItem("nprice").setDecimalDigits(BD505.intValue());
				bdData.getBodyItem("ntaxprice").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("nnetprice").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("ntaxnetprice").setDecimalDigits(
						BD505.intValue());

				bdData.getBodyItem("norgviaprice").setDecimalDigits(
						BD505.intValue());
				bdData.getBodyItem("norgviapricetax").setDecimalDigits(
						BD505.intValue());
			}
			// 换算率
			if (BD503 != null) {
				bdData.getBodyItem("scalefactor").setDecimalDigits(
						BD503.intValue());
				bdData.getBodyItem("nqtscalefactor").setDecimalDigits(
						BD503.intValue());
				bdData.getHeadItem("npreceiverate").setDecimalDigits(
						BD503.intValue());
			}

		} catch (Exception e) {
			SCMEnv.out(e);
		}
		try {
			// 自定义项
			//nc.vo.bd.def.DefVO[] defs = null;

			// 表头
			// 查得对应于公司的该单据的自定义项设置
			//defs = head_defs;

			/*if (defs != null)
				bdData.updateItemByDef(defs, "vdef", true);

			// 表体
			// 查得对应于公司的该单据的自定义项设置
			defs = body_defs;

			if (defs != null)
				bdData.updateItemByDef(defs, "vdef", false);*/
			//列表
			DefSetTool.updateBillListPanelUserDef(getBillListPanel(),
					getClientEnvironment().getCorporation().getPk_corp(),
					SaleBillType.SaleOrder, "vdef", "vdef");
			
			getBillListPanel().setListData(bdData);
			
		} catch (Throwable ex) {
			//ex.printStackTrace();
		}

	}

	/**
	 * 初始化。
	 * 
	 * 创建日期：(2001-5-12 12:04:03)
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void initIDs() {
		vIDs = new Vector();
		for (int i = 0; i < getBillListPanel().getHeadBillModel().getRowCount(); i++) {
			vIDs.add(getBillListPanel().getHeadBillModel().getValueAt(i,
					"csaleid"));
		}
	}

	/**
	 * 切换界面。
	 * 
	 * 创建日期：(2001-10-25 12:34:10)
	 * 
	 */
	protected void switchInterface() {
		if (strShowState.equals("列表")) { /*-=notranslate=-*/
			remove(getSplitPanelBc());
			add(getBillListPanel(), "Center");
			setCurShowPanel(getBillListPanel());
			//隐藏捆绑
			getBillListPanel().hideBodyTableCol("bbindflag");
		} else {
			remove(getBillListPanel());
			add(getSplitPanelBc(), "Center");
			setCurShowPanel(getSplitPanelBc());
			//无论模板和参数如何设置，本币和辅币均不可编辑
			getBillCardTools().setBodyItemEnable(
					SOBillCardTools.getSaleOrderItems_Price_Mny_NoOriginal(),false);
			//隐藏捆绑
			getBillCardPanel().hideBodyTableCol("bbindflag");
		}
		
		updateUI();
	}

	/**
	 * 此处插入方法说明。 创建日期：(2004-4-7 22:31:33)
	 * 
	 * @param newCurShowPanel
	 *            javax.swing.JPanel
	 */
	public void setCurShowPanel(JComponent newCurShowPanel) {
		curShowPanel = newCurShowPanel;
	}

	/**
	 * 返回列表。
	 * 
	 * @return nc.ui.pub.bill.BillCardPanel
	 */
	public SOBillListPanel getBillListPanel() {
		if (ivjSaleOrderListPane == null) {
			try {
				ivjSaleOrderListPane = new SOBillListPanel(this,
						getCorpPrimaryKey(), vocache);
				//ivjSaleOrderListPane.hideBodyTableCol("bbindflag");
			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return ivjSaleOrderListPane;
	}

	protected abstract void onCard();

	protected SOBillQueryDlg getQueryDlg() {
		return getQueryDlg(getCorpPrimaryKey());
	}

	/**
	 * 跨公司专用
	 * 
	 * @param pk_corp
	 * @return
	 */
//	protected SOBillQueryDlg getQueryDlg(String pk_corp) {
//		if (dlgQuery == null) {
//			dlgQuery = new SOBillQueryDlg(this, pk_corp);
//			dlgQuery.addCurToCurDate("so_sale.dbilldate");
//		}
//
//		return dlgQuery;
//	}
	
	/**
	 * 跨公司专用
	 * 
	 * @param pk_corp
	 * @return
	 */
	protected SOBillQueryDlg getQueryDlg(String pk_corp) {
		if (dlgQuery == null) {
			dlgQuery = new SOBillQueryDlg(this, pk_corp);
			dlgQuery.addCurToCurDate("so_sale.dbilldate");
//			ncm miaohl 增加监听
			nc.vo.pub.query.QueryConditionVO[] voaConData = dlgQuery.getConditionDatas();
			String sFieldCode = "";
			nc.ui.pub.beans.UIRefPane ref = null;
			for (int i = 0; i < voaConData.length; i++)
			{
				if (voaConData[i].getDataType().intValue() == nc.vo.pub.query.IQueryConstants.UFREF) {
					sFieldCode = voaConData[i].getFieldCode();
					EditComponentFacotry factry = new EditComponentFacotry(
							voaConData[i]);
					ref = (nc.ui.pub.beans.UIRefPane) factry
							.getEditComponent(null);
					if (sFieldCode.equals("so_saleorder_b.cconsigncorpid")) {
						ref.setName("QueryInCorp");
						ref.addValueChangedListener(this);// 调入公司
						ref.getRefModel().setUseDataPower(false);
						dlgQuery.setValueRef(sFieldCode, ref);
						continue;
					}
				}
			}
		}	
		return dlgQuery;
	}

	/**
	 * 获取扩展按钮数组
	 */
	public ButtonObject[] getExtendBtns() {
		return null;
	}

	/**
	 * 控制扩展按钮的事件
	 */
	public void onExtendBtnsClick(ButtonObject bo) {

	}

	/**
	 * 控制扩展按钮状态
	 */
	public void setExtendBtnsStat(int iState) {

	}

	/**
	 * 表体发货公司改变时，控制界面的变化。 创建日期：(2004-2-9 15:10:14)
	 */
	protected void ctlUIOnCconsignCorpChg(int row) {
		String pk_corp = getBillCardTools().getHeadStringValue("pk_corp");
		if (pk_corp == null)
			return;
		String cconsigncorp = getBillCardTools().getBodyStringValue(row,
				"cconsigncorpid");

		// 如果发货公司与销售公司(即登陆公司)相同，则为单公司销售，
		// 发货收货库存组织，收货仓库，是否直运不可编辑
		if (cconsigncorp == null || pk_corp.equals(cconsigncorp)) {

			getBillCardTools().setBodyCellsEdit(
					new String[] { "creccalbody", "crecwarehouse",
							"bdericttrans" }, row, false);
			//
			getBillCardTools().setBodyCellsEdit(
					new String[] { "boosflag", "bsupplyflag" }, row, true);

		} else {
			getBillCardTools().setBodyCellsEdit(
					new String[] { "boosflag", "bsupplyflag" }, row, false);
			getBillCardTools().setBodyCellsEdit(
					new String[] { "creccalbody", "crecwarehouse",
							"bdericttrans" }, row, true);

		}
	}

	/**
	 * 处理在Card面板上更新单据后，保持列表面板上的vo同时更新，yb。
	 * 
	 * 创建日期：(2003-10-20 8:59:36)
	 * 
	 */
	protected void updateCacheVO() {
		if (strShowState.equals("列表")) { /*-=notranslate=-*/
			int row = getBillListPanel().getHeadTable().getSelectedRow();
			if (row < 0)
				return;

			getBillListPanel().fillCacheName();
			getBillListPanel().fillBodyCacheName();

			String sSaleid = (String) getBillListPanel().getHeadBillModel()
					.getValueAt(row, "csaleid");
			if (sSaleid == null)
				return;
			SaleOrderVO saleorder = vocache.getSaleOrderVO(sSaleid);

			/*getBillListPanel().getHeadBillModel().setBodyRowVO(
					saleorder.getParentVO(), num);*/
			getBillListPanel().getHeadBillModel().setBodyRowVO(
					saleorder.getParentVO(), row);

			// 设置表头金额精度
			try {
				/*nc.ui.so.so001.panel.bom.BillTools.setMnyToModelByCurrency(
						getBillListPanel().getHeadBillModel(), saleorder
								.getParentVO(), num, getCorpPrimaryKey(),
						saleorder.getBodyVOs()[0].getCcurrencytypeid(),
						new String[] { "npreceivemny", "nreceiptcathmny" });*/
				nc.ui.so.so001.panel.bom.BillTools.setMnyToModelByCurrency(
						getBillListPanel().getHeadBillModel(), saleorder
								.getParentVO(), row, getCorpPrimaryKey(),
						saleorder.getBodyVOs()[0].getCcurrencytypeid(),
						new String[] { "npreceivemny", "nreceiptcathmny" });
			} catch (Exception e) {
				handleException(e);
			}

		} else {
			SaleOrderVO saleorder = (SaleOrderVO) getBillCardPanel()
					.getBillValueVO(SaleOrderVO.class.getName(),
							SaleorderHVO.class.getName(),
							SaleorderBVO.class.getName());

			SaleOrderVO oldvo = null;
			if (saleorder.getHeadVO().getCsaleid() != null)
				oldvo = vocache.getSaleOrderVO(saleorder.getHeadVO()
						.getCsaleid());

			SaleorderHVO hvo = (SaleorderHVO) saleorder.getParentVO();
			UIRefPane ufref = (UIRefPane) getBillCardPanel().getHeadItem(
					"ccalbodyid").getComponent();
			hvo.setAttributeValue("ccalbodyname", ufref.getUITextField()
					.getText());

			// if (boBusiType.getSelectedChildButton() != null
			// && boBusiType.getSelectedChildButton().length > 0
			// && boBusiType.getSelectedChildButton()[0] != null
			// && boBusiType.getSelectedChildButton()[0].getCode() != null) {
			// hvo.setAttributeValue("cbiztypename", boBusiType
			// .getSelectedChildButton()[0].getCode());
			//
			// } else
			if (oldvo != null) {
				hvo.setAttributeValue("cbiztypename", oldvo.getHeadVO()
						.getAttributeValue("cbiztypename"));
			}

			ufref = (UIRefPane) getBillCardPanel().getHeadItem("ccustomerid")
					.getComponent();
			hvo.setAttributeValue("ccustomername", ufref.getUITextField()
					.getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem("cdeptid")
					.getComponent();
			hvo
					.setAttributeValue("cdeptname", ufref.getUITextField()
							.getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem("cemployeeid")
					.getComponent();
			hvo.setAttributeValue("cemployeename", ufref.getUITextField()
					.getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem("cfreecustid")
					.getComponent();
			hvo.setAttributeValue("cfreecustname", ufref.getUITextField()
					.getText());

			ufref = (UIRefPane) getBillCardPanel()
					.getHeadItem("creceiptcorpid").getComponent();
			hvo.setAttributeValue("creceiptcorpname", ufref.getUITextField()
					.getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem(
					"creceiptcustomerid").getComponent();
			hvo.setAttributeValue("creceiptcustomername", ufref
					.getUITextField().getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem("csalecorpid")
					.getComponent();
			hvo.setAttributeValue("csalecorpname", ufref.getUITextField()
					.getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem(
					"ctermprotocolid").getComponent();
			hvo.setAttributeValue("ctermprotocolname", ufref.getUITextField()
					.getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem("ctransmodeid")
					.getComponent();
			hvo.setAttributeValue("ctransmodename", ufref.getUITextField()
					.getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem("cwarehouseid")
					.getComponent();
			hvo.setAttributeValue("cwarehousename", ufref.getUITextField()
					.getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem("vreceiveaddress")
					.getComponent();
			hvo.setAttributeValue("vreceiveaddress", ufref.getUITextField()
					.getText());
	
			ufref = (UIRefPane) getBillCardPanel().getTailItem("capproveid")
					.getComponent();
			hvo.setAttributeValue("capprovename", ufref.getUITextField()
					.getText());

			ufref = (UIRefPane) getBillCardPanel().getTailItem("coperatorid")
					.getComponent();
			hvo.setAttributeValue("coperatorname", ufref.getUITextField()
					.getText());
	
			vocache.updateSaleOrderVO(hvo.getCsaleid(), saleorder,
					getMustUpdateHeadNames(), getMustUpdateBodyNames());

			if ((num > -1)
					&& (num < getBillListPanel().getHeadBillModel()
							.getRowCount())) {
				hvo = vocache.getSaleOrderVO(hvo.getCsaleid()).getHeadVO();
				getBillListPanel().getHeadBillModel().setBodyRowVO(hvo, num);
			} //联查->双击销售单进入为卡片模板(并非有列表->卡片)，所以num为初始值-1，没有被赋值0  liuzwc 2009-06-09
			else if(getBillListPanel().getHeadBillModel()
							.getRowCount() == 1) {
				hvo = vocache.getSaleOrderVO(hvo.getCsaleid()).getHeadVO();
				getBillListPanel().getHeadBillModel().setBodyRowVO(hvo, 0);
			}
		}

	}

	/**
	 * 处理在Card面板上更新单据后，保持列表面板上的vo同时更新，yb。
	 * 
	 * 创建日期：(2003-10-20 8:59:36)
	 * 
	 */
	protected void addCacheVO() {
		if (strShowState.equals("列表")) { /*-=notranslate=-*/

		} else {
			SaleOrderVO saleorder = (SaleOrderVO) getBillCardPanel()
					.getBillValueVO(SaleOrderVO.class.getName(),
							SaleorderHVO.class.getName(),
							SaleorderBVO.class.getName());

			UIRefPane ufref = (UIRefPane) getBillCardPanel().getHeadItem(
					"ccalbodyid").getComponent();
			saleorder.getHeadVO().setAttributeValue("ccalbodyname",
					ufref.getUITextField().getText());

//			if (boBusiType.getSelectedChildButton() != null
//					&& boBusiType.getSelectedChildButton().length > 0
//					&& boBusiType.getSelectedChildButton()[0] != null
//					&& boBusiType.getSelectedChildButton()[0].getCode() != null) {
//				saleorder.getHeadVO().setAttributeValue("cbiztypename",
//						boBusiType.getSelectedChildButton()[0].getCode());
//
//			} else {
				ufref = (UIRefPane) getBillCardPanel().getHeadItem("cbiztype")
						.getComponent();
				saleorder.getHeadVO().setAttributeValue("cbiztypename",
						ufref.getUITextField().getText());
//			}

			ufref = (UIRefPane) getBillCardPanel().getHeadItem("ccustomerid")
					.getComponent();
			saleorder.getHeadVO().setAttributeValue("ccustomername",
					ufref.getUITextField().getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem("cdeptid")
					.getComponent();
			saleorder.getHeadVO().setAttributeValue("cdeptname",
					ufref.getUITextField().getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem("cemployeeid")
					.getComponent();
			saleorder.getHeadVO().setAttributeValue("cemployeename",
					ufref.getUITextField().getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem("cfreecustid")
					.getComponent();
			saleorder.getHeadVO().setAttributeValue("cfreecustname",
					ufref.getUITextField().getText());

			ufref = (UIRefPane) getBillCardPanel()
					.getHeadItem("creceiptcorpid").getComponent();
			saleorder.getHeadVO().setAttributeValue("creceiptcorpname",
					ufref.getUITextField().getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem(
					"creceiptcustomerid").getComponent();
			saleorder.getHeadVO().setAttributeValue("creceiptcustomername",
					ufref.getUITextField().getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem("csalecorpid")
					.getComponent();
			saleorder.getHeadVO().setAttributeValue("csalecorpname",
					ufref.getUITextField().getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem(
					"ctermprotocolid").getComponent();
			saleorder.getHeadVO().setAttributeValue("ctermprotocolname",
					ufref.getUITextField().getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem("ctransmodeid")
					.getComponent();
			saleorder.getHeadVO().setAttributeValue("ctransmodename",
					ufref.getUITextField().getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem("cwarehouseid")
					.getComponent();
			saleorder.getHeadVO().setAttributeValue("cwarehousename",
					ufref.getUITextField().getText());

			ufref = (UIRefPane) getBillCardPanel().getHeadItem(
					"vreceiveaddress").getComponent();
			saleorder.getHeadVO().setAttributeValue("vreceiveaddress",
					ufref.getUITextField().getText());

			ufref = (UIRefPane) getBillCardPanel().getTailItem("capproveid")
					.getComponent();
			saleorder.getHeadVO().setAttributeValue("capprovename",
					ufref.getUITextField().getText());

			ufref = (UIRefPane) getBillCardPanel().getTailItem("coperatorid")
					.getComponent();
			saleorder.getHeadVO().setAttributeValue("coperatorname",
					ufref.getUITextField().getText());

			vocache.addSaleOrderVO(saleorder);

			//同步列表
			getBillListPanel().getHeadBillModel().addLine();
			getBillListPanel().getHeadBillModel().setBodyRowVO(saleorder.getParentVO(),
					getBillListPanel().getHeadBillModel().getRowCount() - 1);
			
			// 设置表头金额精度
			int iselrow = getBillListPanel().getHeadBillModel().getRowCount() - 1;
			try {
				nc.ui.so.so001.panel.bom.BillTools.setMnyToModelByCurrency(
						getBillListPanel().getHeadBillModel(), saleorder
								.getParentVO(), iselrow, getCorpPrimaryKey(),
						saleorder.getBodyVOs()[0].getCcurrencytypeid(),
						new String[] { "npreceivemny", "nreceiptcathmny" });
			} catch (Exception e) {
				handleException(e);
			}
			
			/*getBillListPanel().getHeadTable().getSelectionModel()
					.setSelectionInterval(iselrow, iselrow);*/
		}
	}

	@SuppressWarnings("unchecked")
	private ArrayList getMustUpdateHeadNames() {
		String[] heads = new String[] { "fstatus", "breceiptendflag",
				"boutendflag", "binvoicendflag", "ibalanceflag", "bpayendflag",
				"btransendflag", "ts", "capproveid", "dapprovedate","capprovename",
				"daudittime"
		};
		ArrayList al = new ArrayList();

		for (int i = 0; i < heads.length; i++)
			al.add(heads[i]);
		return al;
	}

	@SuppressWarnings("unchecked")
	private ArrayList getMustUpdateBodyNames() {

		String[] heads = new String[] { "csaleid", "corder_bid", "frowstatus",
				"ts", "bifinvoicefinish", "bifpaybalance", "bifpayfinish",
				"bifreceivefinish", "bifinventoryfinish", "bifpaysign",
				"biftransfinish", "dlastconsigdate", "dlasttransdate",
				"dlastoutdate", "dlastinvoicedt", "dlastpaydate",
				"ntotalreturnnumber", "ntotalcarrynumber", "ntaltransnum",
				"ntranslossnum",
				"ntotalreceivenumber", "ntotalinvoicenumber",
				"ntotalbalancenumber", "ntotalpaymny", "ntotalinventorynumber",
				"ntotalsignnumber", "ntotalcostmny", "narrangescornum",
				"narrangepoapplynum", "narrangetoornum", "norrangetoapplynum",
				"ntotlbalcostnum", "barrangedflag", "carrangepersonid",
				"narrangemonum", "ts"

		};

		ArrayList al = new ArrayList();

		for (int i = 0; i < heads.length; i++)
			al.add(heads[i]);
		return al;
	}
	
	protected ButtonObject getBoRefundmentDocument() {
		if (boRefundmentDocument == null) {
			boRefundmentDocument = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000026")/* @res "文档管理" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000026")/* @res "文档管理" */, "文档管理"); /*-=notranslate=-*/
			boRefundmentDocument.setTag("document");
		}
		return boRefundmentDocument;
	}

	protected ButtonObject getBoDocument() {
		if (boDocument == null) {
			boDocument = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000026")/* @res "文档管理" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000026")/* @res "文档管理" */, "文档管理"); /*-=notranslate=-*/
			boDocument.setTag("document");
		}
		return boDocument;
	}

	protected ButtonObject getBoRefundment() {
		if (boRefundment == null) {
			boRefundment = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000151")/* @res "退货" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000151")/* @res "退货" */, "退货"); /*-=notranslate=-*/
		}
		return boRefundment;
	}

	protected ButtonObject getBoCard() {
		if (boCard == null) {
			boCard = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UCH021")/*
			 * @res "卡片"
			 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH021")/*
			 * @res
			 * "切换到单据卡片"
			 */, 2, "卡片显示"); /*-=notranslate=-*/
		}
		return boCard;
	}

	protected ButtonObject getBoBatch() {
		if (boBatch == null) {
			boBatch = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000151")/* @res "退货" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000151")/* @res "退货" */, "退货"); /*-=notranslate=-*/
		}
		return boBatch;
	}

	protected ButtonObject getBoPreview() {
		if (boPreview == null) {
			boPreview = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000032")/* @res "预览" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000306")/* @res "预览单据" */, 0, "预览"); /*-=notranslate=-*/
		}
		return boPreview;
	}

	protected ButtonObject getBoPrint() {
		if (boPrint == null) {
			boPrint = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000007")/* @res "打印" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000169")/* @res "打印单据" */, 0, "打印"); /*-=notranslate=-*/
		}
		return boPrint;
	}

	protected ButtonObject getBoSplitPrint() {
		if (boSplitPrint == null) {
			boSplitPrint = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("40060301","UPT40060301-000595")/* @res "分单打印" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000595")/* @res "分单打印" */, 0, "分单打印"); /*-=notranslate=-*/
		}
		return boSplitPrint;
	}
	
	protected ButtonObject getBoEdit() {
		if (boEdit == null) {
			boEdit = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000045")/* @res "修改" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000291")/* @res "修改单据" */, 2, "修改"); /*-=notranslate=-*/
		}
		return boEdit;
	}

	protected ButtonObject getBoOrderQuery() {
		if (boOrderQuery == null) {
			boOrderQuery = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000033")/* @res "联查" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000033")/* @res "联查" */, 0, "联查"); /*-=notranslate=-*/
		}
		return boOrderQuery;
	}

	protected ButtonObject getBoSendAudit() {
		if (boSendAudit == null) {
			boSendAudit = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000034")/* @res "送审" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000034")/* @res "送审" */, 0, "送审"); /*-=notranslate=-*/
		}
		return boSendAudit;
	}

	protected ButtonObject getBoAuditFlowStatus() {
		if (boAuditFlowStatus == null) {
			boAuditFlowStatus = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000025")/* @res "审批流状态" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000025")/* @res "审批流状态" */, 0, "审批流状态"); /*-=notranslate=-*/
			boAuditFlowStatus.setTag("auditflowstatus");
		}
		return boAuditFlowStatus;
	}

	protected ButtonObject getBoCachPay() {
		if (boCachPay == null) {
			boCachPay = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000028")/* @res "订单收款" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000028")/* @res "订单收款" */, "订单收款"); /*-=notranslate=-*/
		}
		return boCachPay;
	}

	protected ButtonObject getBoListDeselectAll() {
		if (boListDeselectAll == null) {
			boListDeselectAll = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000042")/* @res "全消" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000042")/* @res "全部取消" */, 2, "全消"); /*-=notranslate=-*/
		}
		return boListDeselectAll;
	}

	protected ButtonObject getBoListSelectAll() {
		if (boListSelectAll == null) {
			boListSelectAll = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000041")/* @res "全选" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000041")/* @res "全部选择" */, 2, "全选"); /*-=notranslate=-*/
		}
		return boListSelectAll;
	}

	protected ButtonObject getBoBrowse() {
		if (boBrowse == null) {
			boBrowse = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000021")/* @res "浏览" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000148")/*
					 * @res "浏览单据"
					 */, 1, "浏览"); /*-=notranslate=-*/
		}
		return boBrowse;
	}

	protected ButtonObject getBoFind() {
		if (boFind == null) {
			boFind = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000089")/* @res "定位" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000168")/* @res "定位单据" */, 0, "定位"); /*-=notranslate=-*/
		}
		return boFind;
	}

	protected ButtonObject getBoQuery() {
		if (boQuery == null) {
			boQuery = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000006")/* @res "查询" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000034")/*
					 * @res "查询单据"
					 */, 0, "查询"); /*-=notranslate=-*/
		}
		return boQuery;
	}

	protected ButtonObject getBoMaintain() {
		if (boMaintain == null) {
			boMaintain = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000538")/* @res "维护" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000538")/* @res "维护" */, "维护"); /*-=notranslate=-*/
		}
		return boMaintain;
	}

	protected ButtonObject getBoCancel() {
		if (boCancel == null) {
			boCancel = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000008")/* @res "取消" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000008")/* @res "取消" */, 0, "取消"); /*-=notranslate=-*/
		}
		return boCancel;
	}

	protected ButtonObject getBoLine() {
		if (boLine == null) {
			boLine = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000011")/* @res "行操作" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000011")/* @res "行操作" */, 0, "行操作"); /*-=notranslate=-*/
		}
		return boLine;
	}

	protected ButtonObject getBoAddLine() {
		if (boAddLine == null) {
			boAddLine = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000012")/* @res "增行" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000012")/* @res "增行" */, 0, "增行"); /*-=notranslate=-*/
		}
		return boAddLine;
	}

	protected ButtonObject getBoDelLine() {
		if (boDelLine == null) {
			boDelLine = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000013")/* @res "删行" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000013")/* @res "删行" */, 0, "删行"); /*-=notranslate=-*/
		}
		return boDelLine;
	}

	protected ButtonObject getBoInsertLine() {
		if (boInsertLine == null) {
			boInsertLine = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000016")/* @res "插入行" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000016")/* @res "插入行" */, "插入行"); /*-=notranslate=-*/
		}
		return boInsertLine;
	}

	protected ButtonObject getBoCopyLine() {
		if (boCopyLine == null) {
			boCopyLine = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000014")/* @res "复制行" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000014")/* @res "复制行" */, 0, "复制行"); /*-=notranslate=-*/
		}
		return boCopyLine;
	}
	
	protected ButtonObject getBoPasteLine() {
		if (boPasteLine == null) {
			boPasteLine = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000015")/* @res "粘贴行" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000015")/* @res "粘贴行" */, 0, "粘贴行"); /*-=notranslate=-*/
		}
		return boPasteLine;
	}
	
	protected ButtonObject getBoPasteLineToTail() {
		if (boPasteLineToTail == null) {
			boPasteLineToTail = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "SCMCOMMON000000266")/* @res "粘贴行到表尾" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"SCMCOMMON000000266")/* @res "粘贴行到表尾" */, 0, "粘贴行到表尾"); /*-=notranslate=-*/
		}
		return boPasteLineToTail;
	}
	
	protected ButtonObject getBoFindPrice() {
		if (boFindPrice == null) {
			boFindPrice = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060207",
							"UPT40060207-000020")/* @res "询价" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060207",
							"UPT40060207-000020")/* @res "询价" */, 0, "询价"); /*-=notranslate=-*/
		}
		return boFindPrice;
	}
	
	protected ButtonObject getBoCardEdit() {
		if (boCardEdit == null) {
			boCardEdit = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"SCMCOMMON000000267")/* @res "卡片编辑" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"SCMCOMMON000000267")/* @res "卡片编辑" */, 0, "卡片编辑"); /*-=notranslate=-*/
		}
		return boCardEdit;
	}
	
	protected ButtonObject getBoResortRowNo() {
		if (boResortRowNo == null) {
			boResortRowNo = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"SCMCOMMON000000283")/* @res "重排行号" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"SCMCOMMON000000283")/* @res "重排行号" */, 0, "重排行号"); /*-=notranslate=-*/
		}
		return boResortRowNo;
	}

	protected ButtonObject getBoBlankOut() {
		if (boBlankOut == null) {
			boBlankOut = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000039")/* @res "删除" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000039")/* @res "删除" */, 3, "删除"); /*-=notranslate=-*/
			boBlankOut.setTag("SoBlankout");
		}
		return boBlankOut;
	}

	protected ButtonObject getBoCopyBill() {
		if (boCopyBill == null) {
			boCopyBill = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000043")/* @res "复制" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000043")/* @res "复制" */, 0, "复制"); /*-=notranslate=-*/
		}
		return boCopyBill;
	}

	protected ButtonObject getBoAction() {
		if (boAction == null) {
			boAction = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000026")/* @res "执行" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000026")/* @res "执行" */, 0, "执行"); /*-=notranslate=-*/
		}
		return boAction;
	}

	protected ButtonObject getBoAudit() {
		if (boAudit == null) {
			boAudit = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000027")/* @res "审批" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000027")/* @res "审批" */, 0, "审批"); /*-=notranslate=-*/
			boAudit.setTag("APPROVE");
		}
		return boAudit;
	}

	protected ButtonObject getBoCancelAudit() {
		if (boCancelAudit == null) {
			boCancelAudit = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000028")/* @res "弃审" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000214")/* @res "取消审批单据" */, 0, "弃审"); /*-=notranslate=-*/
			boCancelAudit.setTag("SoUnApprove");
		}
		return boCancelAudit;
	}

	protected ButtonObject getBoModification() {
		if (boModification == null) {
			boModification = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000290")/* @res "修订" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000159")/* @res "修订单据" */, 3, "修订"); /*-=notranslate=-*/
		}
		return boModification;
	}

	protected ButtonObject getBoCancelFreeze() {
		if (boCancelFreeze == null) {
			boCancelFreeze = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000031")/* @res "解冻" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000156")/* @res "解冻单据" */, 0, "解冻"); /*-=notranslate=-*/
			boCancelFreeze.setTag("OrderUnFreeze");
		}
		return boCancelFreeze;
	}

	protected ButtonObject getBoFreeze() {
		if (boFreeze == null) {
			boFreeze = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000030")/* @res "冻结" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000155")/* @res "冻结单据" */, 0, "冻结"); /*-=notranslate=-*/
			boFreeze.setTag("OrderFreeze");
		}
		return boFreeze;
	}

	protected ButtonObject getBoFinish() {
		if (boFinish == null) {
			boFinish = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000119")/* @res "关闭" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000119")/* @res "关闭" */, 0, "关闭"); /*-=notranslate=-*/
			boFinish.setTag("OrderFinish");
		}
		return boFinish;
	}

	protected ButtonObject getBoRefresh() {
		if (boRefresh == null) {
			boRefresh = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000009")/* @res "刷新" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000009")/* @res "刷新" */, "刷新"); /*-=notranslate=-*/
		}
		return boRefresh;
	}

	protected ButtonObject getBoFirst() {
		if (boFirst == null) {
			boFirst = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UCH031")/* @res "首页" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000088")/* @res "第一张单据" */, 0, "首页"); /*-=notranslate=-*/
		}
		return boFirst;
	}

	protected ButtonObject getBoPre() {
		if (boPre == null) {
			boPre = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"SCMCOMMON000000163")/* @res "上页" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000166")/* @res "上一张单据" */, 0, "上页"); /*-=notranslate=-*/
		}
		return boPre;
	}

	protected ButtonObject getBoNext() {
		if (boNext == null) {
			boNext = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000023")/* @res "下页" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000167")/* @res "下一张单据" */, 0, "下页"); /*-=notranslate=-*/
		}
		return boNext;
	}

	protected ButtonObject getBoLast() {
		if (boLast == null) {
			boLast = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000062")/* @res "末页" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000342")/* @res "最后一张单据" */, 0, "末页"); /*-=notranslate=-*/
		}
		return boLast;
	}

	protected ButtonObject getBoAssistant() {
		if (boAssistant == null) {
			boAssistant = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000160")/* @res "辅助功能" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000160")/* @res "辅助功能" */, "辅助功能"); /*-=notranslate=-*/
		}
		return boAssistant;
	}

	protected ButtonObject getBoOrdBalance() {
		if (boOrdBalance == null) {
			boOrdBalance = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000030")/* @res "收款核销" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000030")/* @res "收款核销" */, "收款核销"); /*-=notranslate=-*/
		}
		return boOrdBalance;
	}

	protected ButtonObject getBoStockLock() {
		if (boStockLock == null) {
			boStockLock = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC000-0001824")/* @res "库存硬锁定" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC000-0001824")/* @res "库存硬锁定" */, "库存硬锁定"); /*-=notranslate=-*/
			boStockLock.setTag("StockLock");
		}
		return boStockLock;
	}
	
	protected ButtonObject getBoSendInv() {
		if (boSendInv == null) {
			boSendInv = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("40060301", "UPT40060301-000571")/* @res "发货安排" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000571")/* @res "发货安排" */, "发货安排"); /*-=notranslate=-*/
			boSendInv.setTag(SENDINV);
		}
		return boSendInv;
	}
	
	protected ButtonObject getBoSupplyInv() {
		if (boSupplyInv == null) {
			boSupplyInv = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("40060301", "UPT40060301-000572")/* @res "补货安排" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000572")/* @res "补货安排" */, "补货安排"); /*-=notranslate=-*/
			boSupplyInv.setTag(SUPPLYINV);
		}
		return boSupplyInv;
	}
	
	protected ButtonObject getBoDirectInv() {
		if (boDirectInv == null) {
			boDirectInv = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("40060301", "UPT40060301-000573")/* @res "直运安排" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000573")/* @res "直运安排" */, "直运安排"); /*-=notranslate=-*/
			boDirectInv.setTag(DIRECTINV);
		}
		return boDirectInv;
	}

	protected ButtonObject getBoAsstntQry() {
		if (boAsstntQry == null) {
			boAsstntQry = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000056")/* @res "辅助查询" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000056")/* @res "辅助查询" */, "辅助查询"); /*-=notranslate=-*/
		}
		return boAsstntQry;
	}

	protected ButtonObject getBoOnHandShowHidden() {
		if (boOnHandShowHidden == null) {
			boOnHandShowHidden = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000509")/* @res "切换" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPP40060301-000509")/* @res "现存量显示/隐藏" */, 2,
					"存量显示/隐藏");/*-=notranslate=-*/
		}
		return boOnHandShowHidden;
	}

	protected ButtonObject getBoCustCredit() {
		if (boCustCredit == null) {
			boCustCredit = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000035")/* @res "客户信用" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060301",
							"UPT40060301-000035")/* @res "客户信用" */, 0, "客户信用"); /*-=notranslate=-*/
			boCustCredit.setTag("CustCredited");
		}
		return boCustCredit;
	}

	protected ButtonObject getBoOrderExecRpt() {
		if (boOrderExecRpt == null) {
			boOrderExecRpt = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060302",
							"UPT40060302-000040")/* @res "订单执行情况" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060302",
							"UPT40060302-000040")/* @res "订单执行情况" */, 0,
					"订单执行情况"); /*-=notranslate=-*/
			boOrderExecRpt.setTag("OrderExecRpt");
		}
		return boOrderExecRpt;
	}

	protected ButtonObject getBoCustInfo() {
		if (boCustInfo == null) {
			boCustInfo = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060302",
							"UPT40060302-000041")/* @res "客户情况" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060302",
							"UPT40060302-000041")/* @res "客户情况" */, 0, "客户信息"); /*-=notranslate=-*/
			boCustInfo.setTag("CustInfo");
		}
		return boCustInfo;
	}

	protected ButtonObject getBoPrifit() {
		if (boPrifit == null) {
			boPrifit = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060302",
							"UPT40060302-000042")/* @res "毛利预估" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("40060302",
							"UPT40060302-000042")/* @res "毛利预估" */, 0, "毛利预估"); /*-=notranslate=-*/
			boPrifit.setTag("Prifit");
		}
		return boPrifit;
	}

	protected ButtonObject getBoPrintMgr() {
		if (boPrntMgr == null) {
			boPrntMgr = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"SCMCOMMON000000225")/* @res "打印管理" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"SCMCOMMON000000225")/* @res "打印管理" */, "打印管理"); /*-=notranslate=-*/
		}
		return boPrntMgr;
	}

	protected ButtonObject getBoAdd() {
		if (boAdd == null) {
			boAdd = new ButtonObject(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000287")/* @res "新增" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000288")/* @res "新增单据" */, 1, "新增"); /*-=notranslate=-*/
		}
		return boAdd;
	}

	protected ButtonObject getBoSave() {
		if (boSave == null) {
			boSave = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UC001-0000001")/* @res "保存" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000008")/* @res "保存单据" */, 3, "保存"); /*-=notranslate=-*/
		}
		return boSave;
	}

	protected ButtonObject getBoReturn() {
		if (boReturn == null) {
			boReturn = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("common", "UCH022")/*
			 * @res "返回"
			 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH022")/*
			 * @res
			 * "返回单据列表"
			 */, 0, "列表显示"); /*-=notranslate=-*/
		}
		return boReturn;
	}

	protected ButtonObject getBoBack() {
		if (boBack == null) {
			boBack = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("40060301", "UPT40060301-000597")/*@res "返回"*/, 
				nc.ui.ml.NCLangRes.getInstance()
						.getStrByID("40060301", "UPT40060301-000597")/*
						 * @res "返回"
						 */, "返回");  /*-=notranslate=-*/
		}
		return boBack;
	}

	/**
	 * @author miaohl
	 * 查询时候发货库存组织仓库关联公司
	 */
	public void valueChanged(ValueChangedEvent event) {
		// TODO 自动生成方法存根
		Object obj = event.getSource();
		@SuppressWarnings("unused")
		String sName = ((nc.ui.pub.beans.UIRefPane) obj).getName();
		UIRefPane ref = (nc.ui.pub.beans.UIRefPane)getQueryDlg().getValueRefObjectByFieldCode("so_saleorder_b.cadvisecalbodyid");
		if(ref != null )
		{
			ref.setWhereString(" pk_corp='"
					+ ((nc.ui.pub.beans.UIRefPane) obj).getRefPK()
					+ "' and (sealflag is null or sealflag = 'N') and property <> "
					+ String.valueOf(ICalbodyType_COST));
			ref.setPK(null);
			
		}
		nc.ui.pub.beans.UIRefPane ref1 = ((nc.ui.pub.beans.UIRefPane) getQueryDlg()
				.getValueRefObjectByFieldCode("so_saleorder_b.cbodywarehouseid"));
		if(ref1 != null)
		{
			ref1
					.setWhereString(" pk_corp='"
							+ ((nc.ui.pub.beans.UIRefPane) obj).getRefPK()
							+ "' and pk_calbody in (select pk_calbody from bd_calbody where sealflag is null or sealflag='N')");
			// + String.valueOf(ConstVO.ICalbodyType_COST));
			ref1.setPK(null);
		}
	}
}
