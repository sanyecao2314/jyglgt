package nc.ui.jyglgt.j400670;

import nc.ui.jyglgt.billmanage.AbstractCtrl;
import nc.vo.jyglgt.button.ButtonTool;
import nc.vo.jyglgt.button.IJyglgtButton;
import nc.vo.jyglgt.j400670.AggPricePolicyVO;
import nc.vo.jyglgt.j400670.PricePolicyVO;
import nc.vo.jyglgt.j400670.PricePolicybVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst;

/**
 * 名称: 销售价格政策CTRL类 
 */
public class ClientCtrl extends AbstractCtrl {
	public String getBillType() {
 		return IJyglgtBillType.JY02; 
	}
	// 卡片显示按钮
	public int[] getCardButtonAry() {
 		return ButtonTool.insertButtons(new int[]{IJyglgtButton.BTN_COMMIT,IJyglgtButton.BTN_PASS,IJyglgtButton.BTN_UNPASS, IJyglgtButton.BTN_CLOSE,IJyglgtButton.Copy}, IJyglgtConst.CARD_BUTTONS_M, 7);
	}
	// 列表显示按钮
	public int[] getListButtonAry() {
		return IJyglgtConst.LIST_BUTTONS_M;
	}
    // 返回单据的VO信息
	public String[] getBillVoName() {
		return new String[] { AggPricePolicyVO.class.getName(),
				PricePolicyVO.class.getName(), PricePolicybVO.class.getName() };
	}
	// 获得主表主键
	public String getChildPkField() {
		return PricePolicyVO.PK_PRICEPOLICY;
	}
	// 获得子表主键
	public String getPkField() {
		return PricePolicybVO.PK_PRICEPOLICY_B;
	}
}
