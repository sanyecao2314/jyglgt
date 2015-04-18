package nc.ui.jyglgt.j40068c;

import nc.ui.jyglgt.billmanage.AbstractCtrl;
import nc.vo.gt.gs.gs11.BalanceInfoBVO;
import nc.vo.gt.gs.gs11.BalanceListBVO;
import nc.vo.gt.gs.gs11.ExAggSellPaymentJSVO;
import nc.vo.gt.gs.gs11.SellPaymentJSVO;
import nc.vo.jyglgt.button.ButtonTool;
import nc.vo.jyglgt.button.IJyglgtButton;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst;



/**
 * @功能：销售结算
 */
public class ClientCtrl extends AbstractCtrl {
	
	public int[] getCardButtonAry(){
		return ButtonTool.insertButtons(new int[]{IJyglgtButton.BTN_PASS,IJyglgtButton.BTN_UNPASS,IJyglgtButton.BTN_VADD,IJyglgtButton.BTN_READCARD},
				 ButtonTool.deleteButtons(new int[]{IJyglgtButton.Edit,IJyglgtButton.Line}, IJyglgtConst.CARD_BUTTONS_M), 3);
	}
	@Override
	public int[] getListButtonAry() {
		return ButtonTool.insertButtons(new int[]{IJyglgtButton.BTN_VADD},
				 ButtonTool.deleteButtons(new int[]{IJyglgtButton.Edit}, IJyglgtConst.LIST_BUTTONS_M), 3);
	}
	

	/**
	 * 返回单据类型
	 */
	public String getBillType() {
		return IJyglgtBillType.JY06; 
	}
	/**
	 * 返回VO信息
	 */
	public String[] getBillVoName() {
		return new String[]{
				ExAggSellPaymentJSVO.class.getName(),
				SellPaymentJSVO.class.getName(),
				BalanceInfoBVO.class.getName(),
				BalanceListBVO.class.getName(),
		};
	}
	/**
	 * 返回主表主键
	 */
	public String getPkField() {
		return SellPaymentJSVO.PK_SELLPAYMENTJS;
	}
	@Override
	public String getChildPkField() {
		return BalanceInfoBVO.PK_BALANCEINFO_B;
	}
	
}
