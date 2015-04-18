package nc.ui.jyglgt.j400680;

import nc.ui.jyglgt.billmanage.AbstractCtrl;
import nc.ui.trade.bill.ISingleController;
import nc.vo.jyglgt.button.ButtonTool;
import nc.vo.jyglgt.button.IJyglgtButton;
import nc.vo.jyglgt.j400680.AggCreditVO;
import nc.vo.jyglgt.j400680.CreditBVO;
import nc.vo.jyglgt.j400680.CreditVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst;

/** 
 * 名称: 销售收款通知单CTRL类 
*/ 
public class ClientCtrl extends AbstractCtrl implements ISingleController{
	
	public int[] getCardButtonAry() {
 		return ButtonTool.deleteButton(IJyglgtButton.Line, ButtonTool.insertButtons(new int[]{IJyglgtButton.BTN_COMMIT,IJyglgtButton.BTN_PASS,IJyglgtButton.BTN_UNPASS}, IJyglgtConst.CARD_BUTTONS_M, 7));
	}

	    public int[] getListButtonAry() {
	 		return IJyglgtConst.LIST_BUTTONS_M; 
	    }

	@Override
	public String getBillType() {
 		return IJyglgtBillType.JY04; 
	}

	@Override
	public String[] getBillVoName() {
		return  new String []{
				AggCreditVO.class.getName(),
				CreditVO.class.getName(),
				CreditVO.class.getName(),
			};
	}

	@Override
	public String getChildPkField() {
		return "pk_credit_b";
	}

	@Override
	public String getPkField() {
		return null;
	}
	 /** 
 	 * 是否单表体,=true单表体，=false单表头。
  	 * 创建日期：(2004-2-5 13:43:00) 
 	 * @return boolean 
 	 */ 
 	public boolean isSingleDetail() { 
 		return false; 
 	} 
}
