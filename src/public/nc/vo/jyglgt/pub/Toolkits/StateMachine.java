package nc.vo.jyglgt.pub.Toolkits;


import nc.ui.trade.button.IBillButton;
import nc.vo.jyglgt.button.IJyglgtButton;
import nc.vo.jyglgt.pub.StateVO;


/**
 * ˵��: ״̬��,�жϵ���״̬,�Զ����½��水ť״̬
 * 
 * @author ���������� 2012-1-5
 */
public class StateMachine {
	public StateMachine() {
		super();
	}

    /**
     * �����ṩ�Ķ����͵��ݵ�״̬���ж϶����Ƿ����ִ��
     * @param voCurState BgtStateVO
     * @param iRoleType int
     * @param btn String
     * @return boolean
     */
    public boolean isVaidAction(StateVO voState, int btn) {
        // ����ͨ����ť���ύ̬������̬ʱ�ɱ༭
        // �޸ģ�����̬ʱ�ɱ༭
        if(btn==IBillButton.Edit&&(voState.getBillStatus() == IJyglgtBillStatus.FREE || voState.getBillStatus() == IJyglgtBillStatus.NOPASS)){
        	return true;}
        // ��ӡ������̬��δ��ˡ����̬ʱ�ɱ༭
        if(btn==IBillButton.Print&&(voState.getBillStatus() == IJyglgtBillStatus.FREE ||voState.getBillStatus() == IJyglgtBillStatus.NOPASS||voState.getBillStatus() == IJyglgtBillStatus.CHECKPASS)){
        	return true;
        }// ɾ��������̬ʱ�ɱ༭
        else if(btn==IBillButton.Delete&&(voState.getBillStatus() == IJyglgtBillStatus.FREE || voState.getBillStatus() == IJyglgtBillStatus.NOPASS)){
        	return true;
        }else if(btn == IBillButton.Copy&& (voState.getBillStatus() == IJyglgtBillStatus.FREE ||voState.getBillStatus() == IJyglgtBillStatus.NOPASS||voState.getBillStatus() == IJyglgtBillStatus.CHECKPASS)){
			return true;
		}else if(btn==999&&(voState.getBillStatus() == IJyglgtBillStatus.CHECKPASS)){
        	return true;
        }else if(btn==IJyglgtButton.BTN_IMPORT&&(voState.getBillStatus() == IJyglgtBillStatus.FREE ||voState.getBillStatus() == IJyglgtBillStatus.NOPASS||voState.getBillStatus() == IJyglgtBillStatus.CHECKPASS)){
        	return true;
        }else if(btn==IJyglgtButton.BTN_EXPORT&&(voState.getBillStatus() == IJyglgtBillStatus.FREE ||voState.getBillStatus() == IJyglgtBillStatus.NOPASS||voState.getBillStatus() == IJyglgtBillStatus.CHECKPASS)){
        	return true;
        }else if(btn==IJyglgtButton.BTN_PASS&&(voState.getBillStatus() == IJyglgtBillStatus.FREE ||voState.getBillStatus() == IJyglgtBillStatus.COMMIT||voState.getBillStatus() == IJyglgtBillStatus.CHECKGOING)){
        	return true;
        }else if(btn==IJyglgtButton.BTN_UNPASS&&((voState.getBillStatus() == IJyglgtBillStatus.CHECKPASS )||(voState.getBillStatus() == IJyglgtBillStatus.CHECKGOING ) || (voState.getBillStatus() == IJyglgtBillStatus.NOPASS ))){
        	return true;
        }else if(btn==IJyglgtButton.BTN_COMMIT&&(voState.getBillStatus() == IJyglgtBillStatus.FREE ||(voState.getBillStatus() == IJyglgtBillStatus.NOPASS ))){
        	return true;
        }else if(btn==IJyglgtButton.BTN_DOMAIL&&(voState.getBillStatus() == IJyglgtBillStatus.FREE )){
        	return true;  
        }// "�ر�" ����ͨ��ʱ�ɱ༭ 
		else if(btn == IJyglgtButton.BTN_CLOSE&& (voState.getBillStatus() == IJyglgtBillStatus.CHECKPASS  )) {
			return true;
		}
		return false;
	}

}