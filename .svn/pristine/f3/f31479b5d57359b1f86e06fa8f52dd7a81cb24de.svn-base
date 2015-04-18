package nc.ui.po.oper;

/**
 * 采购订单修订界面
 * 作者：WYF
 * @version		2004/06/14
 * 
 * Modify : V5,czp 
 */
import java.util.HashMap;

import javax.swing.ListSelectionModel;

import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.bd.datapower.ClientEnv;
import nc.ui.ml.NCLangRes;
import nc.ui.po.OrderHelper;
import nc.ui.po.pub.PoCardPanel;
import nc.ui.po.pub.PoListPanel;
import nc.ui.po.pub.PoPublicUIClass;
import nc.ui.pu.pub.POPubSetUI2;
import nc.ui.pu.pub.PuTool;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillMouseEnent;
import nc.ui.pub.pf.PfUtilClient;
import nc.vo.jyglgt.pub.Toolkits.IDatasourceConst;
import nc.vo.po.OrderHeaderVO;
import nc.vo.po.OrderItemVO;
import nc.vo.po.OrderVO;
import nc.vo.pu.exception.MaxPriceException;
import nc.vo.pu.exception.PoReviseException;
import nc.vo.pu.exception.RwtPoToPrException;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ValidationException;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.SCMEnv;

public class RevisionUI extends PoToftPanel {
	//查询条件设置模板
	private OrderUIQueDlg m_condition = null;

/**
 * RevisionUI_Test 构造子注解。
 * @param panel nc.ui.pub.FramePanel
 */
public RevisionUI(nc.ui.pub.FramePanel panel) {
	super(panel);
}
/**
 * 作者：王印芬
 * 功能： 得到按钮管理器
 * 参数：无
 * 返回：PoToftPanelButton
 * 例外：无
 * 日期：(2004-4-01 11:39:21)
 * 修改日期，修改人，修改原因，注释标志：
 */
protected PoToftPanelButton getBtnManager() {
	if (m_btnManager==null) {
		m_btnManager = new	RevisionUIButton() ;
	}
	return	 m_btnManager;
}
/**
 * 作者：WYF
 * 功能：获取单据Panel。
 * 参数：无
 * 返回：无
 * 例外：无
 * 日期：(2001-10-20 11:39:21)
 * 修改日期，修改人，修改原因，注释标志：
 */
protected nc.ui.po.pub.PoCardPanel getInitPoCardPanel(POPubSetUI2 setUi) {
	return new PoCardPanel(
		this,
		PoPublicUIClass.getLoginPk_corp(),
		nc.vo.scm.pu.BillTypeConst.PO_REVISE,
		setUi);

}
/**
 * 作者：WYF
 * 功能：获取列表Panel。
 * 参数：无
 * 返回：无
 * 例外：无
 * 日期：(2001-10-20 11:39:21)
 * 修改日期，修改人，修改原因，注释标志：
 */
protected nc.ui.po.pub.PoListPanel getInitPoListPanel(POPubSetUI2 setUi) {
	// 加载模板
	PoListPanel	listBill =
		new PoListPanel(this, PoPublicUIClass.getLoginPk_corp(), nc.vo.scm.pu.BillTypeConst.PO_REVISE,setUi);
	//只允许单选监听
	listBill.getHeadTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	//listBill.addMouseListener(this);

	return	listBill ;
}
/**
 * 作者：WYF
 * 功能：获取查询条件设置实例
 * 参数：无
 * 返回：无
 * 例外：无
 * 日期：(2001-10-20 11:39:21)
 * 修改日期，修改人，修改原因，注释标志：
 * 2002-06-21	wyf		修改查询模板与业务类型相关的情况
 */
protected nc.ui.po.pub.PoQueryCondition getPoQueryCondition() {
	/*V5支持集中采购调整*/
//	if (m_condition==null) {
//		m_condition = new PoQueryCondition(
//		    this,
//		    nc.ui.ml.NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-000093")/*@res "采购订单修订查询"*/,
//		    "4004020202",
//		    PoPublicUIClass.getLoginPk_corp(),
//			PoPublicUIClass.getLoginUser());
//		m_condition.hideNormal();
//		m_condition.hideCorp();
//	}
	/*V5支持集中采购调整*/
	if (m_condition==null) {
		m_condition = new OrderUIQueDlg(
			this,
			"4004020202",
			PoPublicUIClass.getLoginPk_corp(),
			PoPublicUIClass.getLoginUser()) ;		
		m_condition.hideNormal();
		m_condition.setShowPrintStatusPanel(false);
		m_condition.setTitle(nc.ui.ml.NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-000093")/*@res "采购订单修订查询"*/);
	}
	return	m_condition ;
}
/**
 * 作者：WYF
 * 功能：得到查询的VO
 * 参数：无
 * 返回：OrderVO[]		查询到的VO数组，其中只有第一张含体，其他只有头
 * 例外：无
 * 日期：(2003-11-05 13:24:16)
 * 修改日期，修改人，修改原因，注释标志：
 */
protected nc.vo.po.OrderVO[] getQueriedVOHead() throws	Exception{
	//通过Client取得数据，单位需重新设置
	return OrderHelper.queryForRevision(
		((OrderUIQueDlg)getPoQueryCondition()).getNormalVOs(true),
		getPoQueryCondition().getConditionVO());
}
/**
 * 作者：WYF
 * 功能：获取按钮管理器
 * 参数：无
 * 返回：无
 * 例外：无
 * 日期：(2004-10-20 11:39:21)
 * 修改日期，修改人，修改原因，注释标志：
 */
private RevisionUIButton	getThisBtnManager(){

	return	((RevisionUIButton)getBtnManager()) ;
}
/**
 * 子类实现该方法，返回业务界面的标题。
 * @version (00-6-6 13:33:25)
 *
 * @return java.lang.String
 */
public String getTitle() {
	return getPoListPanel().getBillListData().getTitle() ;
}
/**
 * 作者：WYF
 * 功能：初始化是卡片还是列表
 * 参数：无
 * 返回：boolean		卡片返回true；列表返回false
 *		此方法默认返回true，子类若初始化为列表可重写该方法
 * 例外：无
 * 日期：(2004-06-23 13:24:16)
 * 修改日期，修改人，修改原因，注释标志：
 */
protected	boolean	isInitStateBill(){
	return	false ;
}
/**
 * 响应鼠标双击
 * @param
 * @return
 * @exception   Exception
 * @see
 * @since		2001-04-21
*/
public void mouse_doubleclick(nc.ui.pub.bill.BillMouseEnent e) {
	if (e.getPos() == BillItem.HEAD) {

		showHintMessage(  nc.ui.ml.NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-000074")/*@res "转至卡片"*/  ) ;
		//add by shipeng at 2014-09-16
		OrderVO voCur = getOrderDataVOAt(getBufferVOManager().getVOPos());
		OrderHeaderVO headVO = voCur.getHeadVO();
		try {
			if(Proxy.getItf().isExitByBfJLArriveNote(IDatasourceConst.BFJL, headVO.getVordercode(), null)){
				MessageDialog.showHintDlg(this,
						NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000270")/* @res "提示" */,
						"NC采购订单已生成物流自动化的收货通知单，不允许弃审");
				return ;
			}
		} catch (BusinessException e1) {
			e1.printStackTrace();
		}
		
		//end by shipeng 
		
		
		//
		getBufferVOManager().setVOPos(getPoListPanel().getVOPos(e.getRow()));
		//

		//显示卡片
		remove(getPoListPanel());
		add(getPoCardPanel(),java.awt.BorderLayout.CENTER);
		//按钮
		setCurOperState(STATE_BILL_EDIT) ;
		setButtons(getThisBtnManager().getBtnaBill(this));

		//显示VO
		displayCurVOEntirelyReset(true) ;

		//修订
		onBillRevise() ;
	}
}
/**
 * 进入列表界面
 * @param
 * @return
 * @exception   Exception
 * @see
 * @since		2001-04-21
*/
private void onBillRevise() {
	showHintMessage(  nc.ui.ml.NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-000041")/*@res "编辑订单"*/  ) ;

	OrderVO		voOrder = getOrderViewVOAt(getBufferVOManager().getVOPos()) ;
	if(voOrder.getHeadVO().getPk_corp().equals(ClientEnvironment.getInstance().getCorporation().getPk_corp())){
	String  ccurtypeid = ((OrderHeaderVO)voOrder.getParentVO()).getCcurrencytypeid();
	if(ccurtypeid == null || (ccurtypeid != null && ccurtypeid.trim().length() == 0)){
		((OrderHeaderVO)voOrder.getParentVO()).setCcurrencytypeid(((OrderItemVO[])voOrder.getChildrenVO())[0].getCcurrencytypeid());
	}
	//修改原有后续信息
	getPoCardPanel().clearAfterBillInfo(voOrder.getHeadVO().getCorderid()) ;
	if ( !getPoCardPanel().loadAfterBillInfo(voOrder) ) {
		//设置按钮
		setButtonsStateBrowse() ;
		updateUI();
		return	;
	}

	//设置状态
	setCurOperState(STATE_BILL_EDIT) ;
	//设置按钮
	setButtonsStateEdit() ;
	//设置编辑性
	getPoCardPanel().onActionRevise(voOrder) ;
  //设置封存时表头字段也能显示出来
	//龙旗控股有限公司项目问题v502 modify 2007 11 06
  try{//临时处理,支持开户银行调整后可以去掉
    setHeadRefPK(voOrder);
  }catch(Exception e){
    SCMEnv.out(e);
  }
  //修订人
  getPoCardPanel().setTailItem("crevisepsn", PoPublicUIClass.getLoginUser());
	//刷新
	updateUI();
	//全键盘
	getPoCardPanel().transferFocusTo(BillCardPanel.HEAD) ;

	showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","4004COMMON000000030")/*@res "正在修改"*/);
	}
}
/**
 * @param voOrder
 */
private void setHeadRefPK(OrderVO voOrder) {
	//采购员
	UIRefPane refpnl = (UIRefPane) getPoCardPanel().getHeadItem("cemployeeid").getComponent();
	refpnl.getRefModel().setSealedDataShow(true);
	String strVarValue = getPoCardPanel().getHeadItem("cemployeeid").getValue();
	if(strVarValue == null ||( strVarValue != null && strVarValue.trim().length() == 0)){
		refpnl.setPK(voOrder.getHeadVO().getCemployeeid());
		String strFormula = "cemployeename->getColValue(bd_psndoc,psnname,pk_psndoc,cemployeeid)";
		Object ob = getPoCardPanel().execHeadFormula(strFormula);
		refpnl.getUITextField().setText((String) ob);
	}
	//采购部门
	refpnl = (UIRefPane) getPoCardPanel().getHeadItem("cdeptid").getComponent();
	refpnl.getRefModel().setSealedDataShow(true);
	strVarValue = getPoCardPanel().getHeadItem("cdeptid").getValue();
	if(strVarValue == null ||( strVarValue != null && strVarValue.trim().length() == 0)){
		refpnl.setPK(voOrder.getHeadVO().getCdeptid());
		String strFormula = "cdeptname->getColValue(bd_deptdoc,deptname,pk_deptdoc,cdeptid)";
		Object ob = getPoCardPanel().execHeadFormula(strFormula);
		refpnl.getUITextField().setText((String) ob);
	}
    //业务类型
	refpnl = (UIRefPane) getPoCardPanel().getHeadItem("cbiztype").getComponent();
	refpnl.getRefModel().setSealedDataShow(true);
	strVarValue = getPoCardPanel().getHeadItem("cbiztype").getValue();
	if(strVarValue == null ||( strVarValue != null && strVarValue.trim().length() == 0)){
		refpnl.setPK(voOrder.getHeadVO().getCbiztype());
		String strFormula = "cbiztype->getColValue(bd_busitype,businame,pk_busitype,cbiztype)";
		Object ob = getPoCardPanel().execHeadFormula(strFormula);
		refpnl.getUITextField().setText((String) ob);
	}
    //采购组织
	refpnl = (UIRefPane) getPoCardPanel().getHeadItem("cpurorganization").getComponent();
	refpnl.getRefModel().setSealedDataShow(true);
	strVarValue = getPoCardPanel().getHeadItem("cpurorganization").getValue();
	if(strVarValue == null ||( strVarValue != null && strVarValue.trim().length() == 0)){
		refpnl.setPK(voOrder.getHeadVO().getCpurorganization());
		String strFormula = "cpurorganizationname->getColValue(bd_purorg,name,pk_purorg,cpurorganization)";
		Object ob = getPoCardPanel().execHeadFormula(strFormula);
		refpnl.getUITextField().setText((String) ob);
	}
    //供应商
	refpnl = (UIRefPane) getPoCardPanel().getHeadItem("cvendormangid").getComponent();
	refpnl.getRefModel().setSealedDataShow(true);
	strVarValue = getPoCardPanel().getHeadItem("cvendorbaseid").getValue();
	if(strVarValue == null ||( strVarValue != null && strVarValue.trim().length() == 0)){
		refpnl.setPK(voOrder.getHeadVO().getCvendorbaseid());
		String strFormula = "cvendormangname->getColValue(bd_cubasdoc,custshortname,pk_cubasdoc,cvendorbaseid)";
		Object ob = getPoCardPanel().execHeadFormula(strFormula);
		refpnl.getUITextField().setText((String) ob);
	}
    //开户银行
	refpnl = (UIRefPane) getPoCardPanel().getHeadItem("cfreecustid").getComponent();
	refpnl.getRefModel().setSealedDataShow(true);
	strVarValue = getPoCardPanel().getHeadItem("cfreecustid").getValue();
	if(strVarValue == null ||( strVarValue != null && strVarValue.trim().length() == 0)){
		refpnl.setPK(voOrder.getHeadVO().getCfreecustid());
		//String strFormula = "caccountbankname->iif(cfreecustid=null||cfreecustid="+""+",getColValue(bd_custbank,accname,pk_custbank,caccountbankid),getColValue(so_freecust,vaccname,cfreecustid,cfreecustid))";
    //since v55
    String strFormula = "caccountbankname->getColValue(bd_bankaccbas,accountname,pk_bankaccbas,caccountbankid)";
		Object ob = getPoCardPanel().execHeadFormula(strFormula);
		refpnl.getUITextField().setText((String) ob);
	}
    //收货方
	refpnl = (UIRefPane) getPoCardPanel().getHeadItem("creciever").getComponent();
	refpnl.getRefModel().setSealedDataShow(true);
	strVarValue = getPoCardPanel().getHeadItem("creciever").getValue();
	if(strVarValue == null ||( strVarValue != null && strVarValue.trim().length() == 0)){
		refpnl.setPK(voOrder.getHeadVO().getCreciever());
		String strFormula = "crecievername->getColValue(bd_cumandoc,pk_cubasdoc,pk_cumandoc,creciever);crecievername->getColValue(bd_cubasdoc,custshortname,pk_cubasdoc,crecievername)";
		Object ob = getPoCardPanel().execHeadFormula(strFormula);
		refpnl.getUITextField().setText((String) ob);
	}
    //发票方
	refpnl = (UIRefPane) getPoCardPanel().getHeadItem("cgiveinvoicevendor").getComponent();
	refpnl.getRefModel().setSealedDataShow(true);
	strVarValue = getPoCardPanel().getHeadItem("cgiveinvoicevendor").getValue();
	if(strVarValue == null ||( strVarValue != null && strVarValue.trim().length() == 0)){
		refpnl.setPK(voOrder.getHeadVO().getCgiveinvoicevendor());
		String strFormula = "cgiveinvoicevendorname->getColValue(bd_cumandoc,pk_cubasdoc,pk_cumandoc,cgiveinvoicevendor);cgiveinvoicevendorname->getColValue(bd_cubasdoc,custshortname,pk_cubasdoc,cgiveinvoicevendorname)";
		Object ob = getPoCardPanel().execHeadFormula(strFormula);
		refpnl.getUITextField().setText((String) ob);
	}
    //发运方式
	refpnl = (UIRefPane) getPoCardPanel().getHeadItem("ctransmodeid").getComponent();
	refpnl.getRefModel().setSealedDataShow(true);
	strVarValue = getPoCardPanel().getHeadItem("ctransmodeid").getValue();
	if(strVarValue == null ||( strVarValue != null && strVarValue.trim().length() == 0)){
		refpnl.setPK(voOrder.getHeadVO().getCtransmodeid());
		String strFormula = "ctransmodename->getColValue(bd_sendtype,sendname,pk_sendtype,ctransmodeid)";
		Object ob = getPoCardPanel().execHeadFormula(strFormula);
		refpnl.getUITextField().setText((String) ob);
	}
    //散户
	refpnl = (UIRefPane) getPoCardPanel().getHeadItem("cfreecustid").getComponent();
	refpnl.getRefModel().setSealedDataShow(true);
	strVarValue = getPoCardPanel().getHeadItem("cfreecustid").getValue();
	if(strVarValue == null ||( strVarValue != null && strVarValue.trim().length() == 0)){
		refpnl.setPK(voOrder.getHeadVO().getCfreecustid());
		String strFormula = "cfreecustname->getColValue(so_freecust,vcustname,cfreecustid,cfreecustid)";
		Object ob = getPoCardPanel().execHeadFormula(strFormula);
		refpnl.getUITextField().setText((String) ob);
	}
    //付款协议
	refpnl = (UIRefPane) getPoCardPanel().getHeadItem("ctermprotocolid").getComponent();
	refpnl.getRefModel().setSealedDataShow(true);
	strVarValue = getPoCardPanel().getHeadItem("ctermprotocolid").getValue();
	if(strVarValue == null ||( strVarValue != null && strVarValue.trim().length() == 0)){
		refpnl.setPK(voOrder.getHeadVO().getCtermprotocolid());
		String strFormula = "ctermprotocolname->getColValue(bd_payterm,termname,pk_payterm,ctermprotocolid)";
		Object ob = getPoCardPanel().execHeadFormula(strFormula);
		refpnl.getUITextField().setText((String) ob);
	}
    //币种
	refpnl = (UIRefPane) getPoCardPanel().getHeadItem("ccurrencytypeid").getComponent();
	refpnl.getRefModel().setSealedDataShow(true);
	strVarValue = getPoCardPanel().getHeadItem("ccurrencytypeid").getValue();
	if(strVarValue == null ||( strVarValue != null && strVarValue.trim().length() == 0)){
		refpnl.setPK(voOrder.getHeadVO().getCcurrencytypeid());
		String strFormula = "ccurrencytypeid->getColValue(bd_currtype,currtypename,pk_currtype,ccurrencytypeid)";
		Object ob = getPoCardPanel().execHeadFormula(strFormula);
		refpnl.getUITextField().setText((String) ob);
	}
    //解冻人
	refpnl = (UIRefPane) getPoCardPanel().getHeadItem("cunfreeze").getComponent();
	refpnl.getRefModel().setSealedDataShow(true);
	strVarValue = getPoCardPanel().getHeadItem("cunfreeze").getValue();
	if(strVarValue == null ||( strVarValue != null && strVarValue.trim().length() == 0)){
		refpnl.setPK(voOrder.getHeadVO().getCunfreeze());
		String strFormula = "cunfreeze->getColValue(sm_user,user_name,cUserId,cunfreeze)";
		Object ob = getPoCardPanel().execHeadFormula(strFormula);
		refpnl.getUITextField().setText((String) ob);
	}
    //银行帐号
//	refpnl = (UIRefPane) getPoCardPanel().getHeadItem("cfreecustid").getComponent();
//	refpnl.getRefModel().setSealedDataShow(true);
//	strVarValue = getPoCardPanel().getHeadItem("cfreecustid").getValue();
//	if(strVarValue == null ||( strVarValue != null && strVarValue.trim().length() == 0)){
//		refpnl.setPK(voOrder.getHeadVO().getCfreecustid());
//		String strFormula = "account->iif(cfreecustid=null||cfreecustid=+"+""+",getColValue(bd_custbank,account,pk_custbank,caccountbankid),getColValue(so_freecust,vaccount,cfreecustid,cfreecustid))";
//		Object ob = getPoCardPanel().execHeadFormula(strFormula);
//		refpnl.getUITextField().setText((String) ob);
//	}
}

/**
 * 作者：WYF
 * 功能：保存
 * 参数：无
 * 返回：无
 * 例外：无
 * 日期：(2001-04-21  11:39:21)
 * 修改日期，修改人，修改原因，注释标志：
 */
protected boolean onBillSave() {

	//得到保存的VO：只对显示的VO进行保存
	OrderVO voSaved = getPoCardPanel().getSaveVO(getOrderDataVOAt(getBufferVOManager().getVOPos()));
	if (voSaved == null) {
		//未被修改
	    showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("4004020601","UPP4004020601-000073")/*@res ""未修改任何項目，保存操作無效""*/);
		return false;
	}
	if(voSaved.getHeadVO().getNprepaymny()!=null&&PuPubVO.getUFDouble_NullAsZero(voSaved.getHeadVO().getNprepaymny()).compareTo(new UFDouble(0))>0){
		UFDouble dPrePayMNY = UFDouble.ZERO_DBL;
		for(int i = 0 ; i < voSaved.getBodyLen() ; i ++){
			dPrePayMNY = dPrePayMNY.add(PuPubVO.getUFDouble_NullAsZero(voSaved.getBodyVO()[i].getNtaxpricemny()));
		}
		if(PuPubVO.getUFDouble_NullAsZero(voSaved.getHeadVO().getNprepaymny()).compareTo(dPrePayMNY)>0){
			showHintMessage(NCLangRes.getInstance().getStrByID("4004020601", "RevisionUI-000000")/*订单表体价税合计之合已经小于订单预付款金额！*/);
			return false;
		}
	}

	//新增的历史VO
	OrderVO voHistory = getOrderDataVOAt(getBufferVOManager().getVOPos()).getHistoryVO(PoPublicUIClass.getLoginUser());

	//-------保存
	boolean bContinue = true;
	while (bContinue) {
		try {
			PfUtilClient.processActionNoSendMessage(
				this,
				"REVISE",
				nc.vo.scm.pu.BillTypeConst.PO_ORDER,
				getClientEnvironment().getDate().toString(),
				voSaved,
				voHistory,
				null,
				null);
			bContinue = false;
		} catch (Exception e) {
			setCurOperState(STATE_BILL_EDIT);
			if (e instanceof RwtPoToPrException) {
				//请购提示
				int iRet = showYesNoMessage(e.getMessage());
				if (iRet == MessageDialog.ID_YES) {
					//继续循环
					bContinue = true;
					//是否第一次
					voSaved.setFirstTime(false);
				} else {
					showHintMessage( nc.ui.ml.NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-000041")/*@res "编辑订单"*/ );
					return false;
				}
			} else
			if (e instanceof nc.vo.scm.pub.SaveHintException) {
				//合同提示
				int iRet = showYesNoMessage(e.getMessage());
				if (iRet == MessageDialog.ID_YES) {
					//继续循环
					bContinue = true;
					//是否第一次
					voSaved.setFirstTime(false);
				} else {
					showHintMessage( nc.ui.ml.NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-000041")/*@res "编辑订单"*/ );
					return false;
				}
			} else if (e instanceof nc.vo.scm.pub.StockPresentException) {
				//超现存量提示
				int iRet = showYesNoMessage(e.getMessage());
				if (iRet == MessageDialog.ID_YES) {
					//继续循环
					bContinue = true;
					//是否第一次
					voSaved.setFirstTimeSP(false);
				} else {
					showHintMessage( nc.ui.ml.NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-000041")/*@res "编辑订单"*/ );
					return false;
				}
			}  else if (e instanceof PoReviseException) {
				//有后续单据的提示
				int iRet = showYesNoMessage(e.getMessage());
				if (iRet == MessageDialog.ID_YES) {
					//继续循环
					bContinue = true;
					//是否第一次
					voSaved.setFirstTimeSP(false);
				} else {
					showHintMessage( nc.ui.ml.NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-000041")/*@res "编辑订单"*/ );
					return false;
				}
			} else if (e instanceof MaxPriceException) {
        HashMap hData = ((MaxPriceException) e).getIRows();
        m_renderYellowAlarmLine.setRight(true);
        m_renderYellowAlarmLine.setRowRender(getPoCardPanel().getBillTable(), hData);
        // pnlBill.getBillTable().gett
        // 最高限价超出提示
        getPoCardPanel().getBillTable().repaint();
        String[] sWarn = (String[]) hData.values().toArray(new String[hData.size()]);
        String sShow = new String();
        for (int i = 0; i < sWarn.length; i++) {
          sShow += sWarn[i] + ",";
        }
        int iRet = showYesNoMessage(NCLangRes.getInstance().getStrByID("4004020601", "RevisionUI-000001", null, new String[]{sShow})/*序号：{0}存在超出最高限价的行，是否继续？*/);

        if (iRet == MessageDialog.ID_YES) {
          // 继续循环
          bContinue = true;
          // 是否第一次
          voSaved.setFirstTimePrice(false);
          m_renderYellowAlarmLine.setRight(false);
          getPoCardPanel().getBillTable().repaint();
        } else {
          showHintMessage(nc.ui.ml.NCLangRes
              .getInstance().getStrByID("SCMCOMMON",
                  "UPPSCMCommon-000010")/*
                               * @res "保存失败"
                               */);
          return false;
        }
      } else if (e instanceof BusinessException || e instanceof ValidationException) {
				showHintMessage( nc.ui.ml.NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-000041")/*@res "编辑订单"*/ );
				PuTool.outException(this, e);
				return false;
			} else {
				PuTool.outException(this, e);
				showHintMessage( nc.ui.ml.NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-000041")/*@res "编辑订单"*/ );
				return false;
			}
		}
	}

	showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-000094")/*@res "订单修订完成"*/);

//	String sId = getOrderDataVOAt(getBufferVOManager().getVOPos()).getHeadVO().getCorderid();
	//区分是新增、修改{0,新增；1，修改}
  int iCurOperType = PuPubVO.getString_TrimZeroLenAsNull(voSaved
      .getHeadVO().getPrimaryKey()) == null ? 0 : 1;
	OrderVO[] voLightRefreshed = null;
	//重新查询，替换当前VO
	try {
		//==================保存后处理	重新查询
	  voLightRefreshed = OrderHelper.queryOrderVOsLight(
        new String[] { voSaved.getHeadVO().getCorderid() }, "SAVE");
	} catch (Exception e) {
		PuTool.outException(this, e);
		showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("4004020201","UPP4004020201-000095")/*@res "订单保存成功，但出现并发操作，可重新刷新查看该单据"*/);
	}
	
	voSaved.setLastestVo(voLightRefreshed[0],iCurOperType);
	getBufferVOManager().setVOAt(getBufferVOManager().getVOPos(), voSaved);

	//显示当前VO
	displayCurVOEntirelyReset(false);

	//设置界面与按钮状态
	getPoCardPanel().setEnabled(false);
	setCurOperState(STATE_BILL_BROWSE);
	setButtonsStateBrowse();

	updateUI();

	showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UCH005")/*@res "保存成功"*/);
	return true;

}
/**
 * 子类实现该方法，响应按钮事件。
 * @version (00-6-1 10:32:59)
 *
 * @param bo ButtonObject
 */
protected void onButtonClickedBill(nc.ui.pub.ButtonObject bo) {
	if (bo == getThisBtnManager().btnBillRevise) {
		onBillRevise();
	} else {
		super.onButtonClickedBill(bo);
	}
}
/**
 * 子类实现该方法，响应按钮事件。
 * @version (00-6-1 10:32:59)
 *
 * @param bo ButtonObject
 */
protected void onButtonClickedList(nc.ui.pub.ButtonObject bo) {
	if (bo == getThisBtnManager().btnListRevise) {
		onListRevise();
	}  else {
		super.onButtonClickedList(bo);
	}
}
/**
 * 显示请购单列表界面
 */
private void onListRevise() {

	//onDoubleClick(getPoListPanel().getHeadSelectedRow());

	BillMouseEnent	mEvent = new	BillMouseEnent(
		getPoListPanel(),
		getPoListPanel().getHeadSelectedRow(),
		BillItem.HEAD
		 ) ;

	mouse_doubleclick(mEvent) ;
	
	showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","4004COMMON000000030")/*@res "正在修改"*/);
}
}