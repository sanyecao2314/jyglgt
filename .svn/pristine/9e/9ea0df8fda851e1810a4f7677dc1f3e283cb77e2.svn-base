package nc.ui.jyglgt.j400420; 
 
import nc.ui.jyglgt.billmanage.AbstractEventHandler;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fa.pub.bill.FAPfUtil;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.trade.pub.HYBillVO;

 
/** 
 * @author 施鹏 
 * 名称: 采购奖罚单EH类 
*/ 
public class ClientEventHandler extends AbstractEventHandler{ 
	 
	public ClientEventHandler(BillManageUI billUI, IControllerBase control) { 
		super(billUI, control); 
	} 
	 
	public void onBoCheckPass() throws Exception {
		if (this.getBillUI().getBufferData() == null)
			return;

		AggregatedValueObject billvo = (HYBillVO) this.getBillUI().getBufferData().getCurrentVO();
		if (Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.COMMIT
				|| Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.CHECKGOING) {
			billvo.getParentVO().setAttributeValue("vapproveid" , _getOperator());
			PfUtilClient.processActionFlow(this.getBillUI(),FAPfUtil.AUDIT, IJyglgtBillType.JY01, this._getDate().toString(), billvo,null);
			this.getBillUI().showHintMessage("审批完成...");

			super.onBoRefresh();
		} else if(Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.FREE){
			this.getBillUI().showWarningMessage("没有提交，不可以重复审核！");
			return;
		}else {
			this.getBillUI().showWarningMessage("不能重复审核！");
			return;
		}
	}


	protected void onBoCommit() throws Exception {
		if (this.getBillUI().getBufferData() == null)
			return;
		AggregatedValueObject billvo = (HYBillVO) this.getBillUI().getBufferData()
				.getCurrentVO();
		if(!Toolkits.getString(billvo.getParentVO().getAttributeValue("pk_operator")).equals(_getOperator())){
			this.getBillUI().showErrorMessage("提交失败，请确定是否有权限提交");
			return;
		}
		if (Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() != IJyglgtBillStatus.FREE
				&& Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() != IJyglgtBillStatus.NOPASS) {
			this.getBillUI().showWarningMessage("不能重复提交！");
			return;
		}
		billvo.getParentVO().setAttributeValue("vbillstatus", IJyglgtBillStatus.COMMIT);
			PfUtilClient.processAction(FAPfUtil.SAVE, IJyglgtBillType.JY01, this._getDate()
					.toString(), billvo, FAPfUtil.UPDATE_SAVE);

		this.getBillUI().showHintMessage("提交完成.....");
		super.onBoRefresh();
	}
	
	public void onBoCheckNoPass() throws Exception {
		if (this.getBillUI().getBufferData() == null)
			return;

		AggregatedValueObject billvo = (HYBillVO) this.getBillUI().getBufferData().getCurrentVO();
		if (Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.CHECKPASS
				|| Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.CHECKGOING
				|| Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.NOPASS) {
//			if(!Toolkits.getString(billvo.getParentVO().getAttributeValue("vapproveid")).equals(_getOperator())){
//				this.getBillUI().showErrorMessage("非审核人本人，不可以弃审");
//				return;
//			}
			billvo.getParentVO().setAttributeValue("vapproveid" , _getOperator());
			billvo.getParentVO().setAttributeValue("vunapproveid" , _getOperator());
			billvo.getParentVO().setAttributeValue("vunapprovedate" , _getDate());
//			billvo.getParentVO().setAttributeValue("vbillstatus" , IJyglgtBillStatus.FREE);
			billvo.getParentVO().setAttributeValue("dapprovedate" , null);

			Object obj=PfUtilClient.processActionFlow(this.getBillUI(),FAPfUtil.UNAUDIT, IJyglgtBillType.JY01, this._getDate().toString(), billvo,null);
			this.getBillUI().showHintMessage("弃审完成...");

			super.onBoRefresh();
		} else if(Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.FREE){
			this.getBillUI().showWarningMessage("制单人状态，不需要弃审！");
			return;
		}else {
			this.getBillUI().showWarningMessage("不能重复弃审！");
			return;
		}
      }
} 
