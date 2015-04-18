package nc.work.gdt;

import nc.ui.jyglgt.billcard.AbstractCtrl;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.vo.jyglgt.button.ButtonTool;
import nc.vo.jyglgt.button.IJyglgtButton;
import nc.vo.jyglgt.pub.BillMakeVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst;
import nc.vo.trade.pub.HYBillVO;

/**
 * 功能 作者：王良超 日期：2010-08-03
 */
public class ClientCtrl extends AbstractCtrl {

	// 走审批流要返回PLATFROM类型
	public int getBusinessActionType() {
		return IBusinessActionType.BD;
	}
	
	//修改1
	public String getBillType() {
		return "GDT";
	}

	public String[] getBillVoName() {
		return new String[] { 
				HYBillVO.class.getName(),
				BillMakeVO.class.getName(),
				BillMakeVO.class.getName()};
	}

	//修改2
	public int[] getCardButtonAry() {
		//删除掉"行操作"的 标准管理型单据卡片按钮
		return ButtonTool.insertButtons(new int[]{800}, new int[]{IJyglgtButton.Add,IJyglgtButton.Cancel}, 0);
	}

	// 修改3
	public int[] getListButtonAry() {
		return IJyglgtConst.LIST_BUTTONS_M;
	}

	public String getPkField() {
		return "pk_resource";
	}

	@Override
	public String getChildPkField() {
		// TODO Auto-generated method stub
		return null;
	}

}
