package nc.ui.jyglgt.j40068d;


import nc.vo.jyglgt.button.ButtonTool;
import nc.vo.jyglgt.button.IJyglgtButton;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst;



/**
 * @功能：优惠结算
 */
public class ClientCtrl extends nc.ui.jyglgt.j40068c.ClientCtrl {
	
	
	public String getBillType() {
		return IJyglgtBillType.JY07; 
	}
	
	// 卡片显示按钮
	public int[] getCardButtonAry() {
		return ButtonTool.insertButtons(new int[]{IJyglgtButton.BTN_PASS,IJyglgtButton.BTN_UNPASS},
				 ButtonTool.deleteButtons(new int[]{IJyglgtButton.Edit}, IJyglgtConst.CARD_BUTTONS_M), 3);
	}
	@Override
	public int[] getListButtonAry() {
		return ButtonTool.deleteButtons(new int[]{IJyglgtButton.Edit}, IJyglgtConst.LIST_BUTTONS_M);
	}
}
