package nc.ui.jyglgt.j400675; 

import nc.ui.jyglgt.billmanage.AbstractCtrl;
import nc.ui.jyglgt.billmanage.AbstractEventHandler;

  
 
/** 
 * ����: �����۸�����UI�� 
*/ 
public class ClientUI extends nc.ui.jyglgt.j400670.ClientUI { 
  
	public ClientUI() { 
	} 
	
	// �õ����������
	protected AbstractCtrl createController() {
		return new ClientCtrl();
	}
	// �õ��¼���������
	public AbstractEventHandler createEventHandler() {
		return new ClientEventHandler(this, this.getUIControl());
	}
} 
