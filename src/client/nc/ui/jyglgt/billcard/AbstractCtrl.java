
package nc.ui.jyglgt.billcard;

import nc.ui.trade.bill.ICardController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.jyglgt.button.ButtonTool;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst;

/**
 * 说明:基础控制类,卡片型界面应继承此类
 * @author 公共开发者 2010-8-30 下午01:57:47
 */
public class AbstractCtrl implements ICardController, ISingleController {

	public AbstractCtrl() {
		super();
	}
	public String[] getCardBodyHideCol() {
		return null;
	}
	public int[] getCardButtonAry() {
		return ButtonTool.deleteButton(IBillButton.Query, IJyglgtConst.LIST_BUTTONS);
	}
	public boolean isShowCardRowNo() {
		return true;
	}
	public boolean isShowCardTotal() {
		return false;
	}
	public String getBillType() {
		return null;
	}
	public String[] getBillVoName() {
		return new String[]{};
	}
	public String getBodyCondition() {
		return null;
	}
	public String getBodyZYXKey() {
		return null;
	}
	public int getBusinessActionType() {
		return IBusinessActionType.BD;
	}
	public String getChildPkField() {
		return null;
	}
	public String getHeadZYXKey() {
		return null;
	}
	public String getPkField() {
		return null;
	}
	public Boolean isEditInGoing() throws Exception {
		return null;
	}
	public boolean isExistBillStatus() {
		return false;
	}
	public boolean isLoadCardFormula() {
		return false;
	}
	public boolean isSingleDetail() {
		return true;
	}

}
