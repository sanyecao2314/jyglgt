package nc.ui.jyglgt.z100102; 
  
import nc.ui.jyglgt.billmanage.AbstractClientUI; 
import nc.ui.trade.bill.AbstractManageController; 
import nc.ui.trade.manage.ManageEventHandler; 
 
/** 
 * @author �Զ����� 
 * ����: Ӫҵ˰UI�� 
*/ 
public class ClientUI extends AbstractClientUI { 
  
	public ClientUI() { 
	} 
  
 	public ManageEventHandler createEventHandler() { 
 		return new ClientEventHandler(this, this.createController()); 
 	} 
  
 	protected AbstractManageController createController() { 
 		return new ClientCtrl(); 
 	} 
  
} 
