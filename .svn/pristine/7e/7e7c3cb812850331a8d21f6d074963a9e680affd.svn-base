package nc.ui.gt.gs.gs24;

import nc.ui.jyglgt.billmanage.AbstractCtrl;
import nc.vo.gt.gs.gs24.AggColumnShowVO;
import nc.vo.gt.gs.gs24.ColumnShowVO;
import nc.vo.gt.gs.gs24.ColumnShowbVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst;

/**
 * @功能：模板表体字段显示设置控制类
 */
public class ClientCtrl extends AbstractCtrl {

    public int[] getCardButtonAry() {
	   return IJyglgtConst.CARD_BUTTONS_M;
    }

    public int[] getListButtonAry() {
	   return IJyglgtConst.LIST_BUTTONS_M;
    }

    public String getBillType() {
 		return IJyglgtBillType.GS24; 
    }

    public String[] getBillVoName() {
		return  new String []{
			AggColumnShowVO.class.getName(),
			ColumnShowVO.class.getName(),
			ColumnShowbVO.class.getName(),
		};
    }

    public String getChildPkField() {
	   return ColumnShowbVO.PK_COLUMNSHOW_B;
    }

    public String getPkField() {
	    return ColumnShowVO.PK_COLUMNSHOW;
    }

}
