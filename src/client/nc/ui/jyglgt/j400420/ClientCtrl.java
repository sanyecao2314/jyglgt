package nc.ui.jyglgt.j400420; 
  
import nc.ui.jyglgt.billmanage.AbstractCtrl; 
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType; 
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst; 
import nc.vo.trade.pub.HYBillVO; 
import nc.vo.jyglgt.button.IJyglgtButton; 
import nc.vo.jyglgt.button.ButtonTool; 
import nc.ui.trade.bill.ISingleController;   
  
/** 
 * @author ʩ��
 * ����: �ɹ�������CTRL�� 
*/ 
public class ClientCtrl extends AbstractCtrl implements ISingleController { 
  
 	public String getBillType() { 
 		return IJyglgtBillType.JY01; 
 	} 
  
 	public String[] getBillVoName() { 
 		return new String[] {  
 				HYBillVO.class.getName(), 
 				nc.vo.jyglgt.j400420.PosanctionVO.class.getName(), 
 				nc.vo.jyglgt.j400420.PosanctionVO.class.getName()}; 
 	} 
  
 	public int[] getCardButtonAry() { 
 		return ButtonTool.insertButtons(new int[]{IJyglgtButton.BTN_COMMIT,IJyglgtButton.BTN_PASS,IJyglgtButton.BTN_UNPASS}, IJyglgtConst.CARD_BUTTONS_M, 7); 
 	} 
  
 	public int[] getListButtonAry() { 
 		return IJyglgtConst.LIST_BUTTONS_M; 
 	} 
  
 	public String getPkField() { 
 		return "pk_posanction"; 
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