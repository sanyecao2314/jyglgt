package nc.ui.jyglgt.z100101; 
  
import nc.ui.jyglgt.billmanage.AbstractCtrl; 
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType; 
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst; 
import nc.vo.trade.pub.HYBillVO; 
import nc.vo.jyglgt.button.IJyglgtButton; 
import nc.vo.jyglgt.button.ButtonTool; 
import nc.ui.trade.bill.ISingleController;   
  
/** 
 * @author �Զ����� 
 * ����: ��ֵ˰CTRL�� 
*/ 
public class ClientCtrl extends AbstractCtrl implements ISingleController { 
  
 	public String getBillType() { 
 		return IJyglgtBillType.zzs; 
 	} 
  
 	public String[] getBillVoName() { 
 		return new String[] {  
 				HYBillVO.class.getName(), 
 				nc.vo.jyglgt.z100101.ZzsVO.class.getName(), 
 				nc.vo.jyglgt.z100101.ZzsVO.class.getName()}; 
 	} 
  
 	public int[] getCardButtonAry() { 
 		return ButtonTool.deleteButton(IJyglgtButton.Line, IJyglgtConst.CARD_BUTTONS_M); 
 	} 
  
 	public int[] getListButtonAry() { 
 		return IJyglgtConst.LIST_BUTTONS_M; 
 	} 
  
 	public String getPkField() { 
 		return "pk_zzs"; 
 	} 
  
 	@Override 
 	public String getChildPkField() { 
 		return null; 
 	} 
 	 /** 
 	 * �Ƿ񵥱���,=true�����壬=false����ͷ��
  	 * �������ڣ�(2004-2-5 13:43:00) 
 	 * @return boolean 
 	 */ 
 	public boolean isSingleDetail() { 
 		return false; 
 	} 
  
} 
