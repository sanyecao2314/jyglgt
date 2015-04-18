package nc.ui.jyglgt.z100101; 
  
import nc.ui.jyglgt.billmanage.AbstractClientUI; 
import nc.ui.trade.bill.AbstractManageController; 
import nc.ui.trade.manage.ManageEventHandler; 
 
/** 
 * @author 自动生成 
 * 名称: 增值税UI类 
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
