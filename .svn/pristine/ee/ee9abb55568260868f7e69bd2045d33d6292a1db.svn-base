package nc.ui.jyglgt.j40068b; 
  
import nc.ui.jyglgt.billmanage.AbstractCtrl; 
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType; 
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst; 
import nc.vo.trade.pub.HYBillVO; 
import nc.vo.jyglgt.button.IJyglgtButton; 
import nc.vo.jyglgt.button.ButtonTool; 
import nc.ui.trade.bill.ISingleController;   
  
/** 
 * @author 自动生成 
 * 名称: 价格优惠单CTRL类 
*/ 
public class ClientCtrl extends AbstractCtrl { 
  
 	public String getBillType() { 
 		return IJyglgtBillType.JY05; 
 	} 
  
 	public String[] getBillVoName() { 
 		return new String[] {  
 				HYBillVO.class.getName(), 
 				nc.vo.jyglgt.j40068b.PricediscountVO.class.getName(), 
 				nc.vo.jyglgt.j40068b.PricediscountbVO.class.getName()}; 
 	} 
  
 	public int[] getCardButtonAry() { 
 		int[] newbtns = ButtonTool.insertButtons(new int[]{IJyglgtButton.BTN_COMMIT,IJyglgtButton.BTN_PASS,IJyglgtButton.BTN_UNPASS}, IJyglgtConst.CARD_BUTTONS_M, IJyglgtConst.CARD_BUTTONS_M.length-1);
 		return newbtns;
 	} 
  
 	public int[] getListButtonAry() { 
 		return IJyglgtConst.LIST_BUTTONS_M; 
 	} 
  
 	public String getPkField() { 
 		return "pk_pricediscount"; 
 	} 
  
 	@Override 
 	public String getChildPkField() { 
 		return "pk_pricediscount_b"; 
 	} 
  
} 
