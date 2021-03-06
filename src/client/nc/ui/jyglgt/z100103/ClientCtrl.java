package nc.ui.jyglgt.z100103; 
  
import nc.ui.jyglgt.billmanage.AbstractCtrl; 
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType; 
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst; 
import nc.vo.trade.pub.HYBillVO; 
import nc.vo.jyglgt.button.IJyglgtButton; 
import nc.vo.jyglgt.button.ButtonTool; 
import nc.ui.trade.bill.ISingleController;   
  
/** 
 * @author 自动生成 
 * 名称: 所得税CTRL类 
*/ 
public class ClientCtrl extends AbstractCtrl implements ISingleController { 
  
 	public String getBillType() { 
 		return IJyglgtBillType.sds; 
 	} 
  
 	public String[] getBillVoName() { 
 		return new String[] {  
 				HYBillVO.class.getName(), 
 				nc.vo.jyglgt.z100103.SdsVO.class.getName(), 
 				nc.vo.jyglgt.z100103.SdsVO.class.getName()}; 
 	} 
  
 	public int[] getCardButtonAry() { 
 		return ButtonTool.deleteButton(IJyglgtButton.Line, IJyglgtConst.CARD_BUTTONS_M); 
 	} 
  
 	public int[] getListButtonAry() { 
 		return IJyglgtConst.LIST_BUTTONS_M; 
 	} 
  
 	public String getPkField() { 
 		return "pk_sds"; 
 	} 
  
 	@Override 
 	public String getChildPkField() { 
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
