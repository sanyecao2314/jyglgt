package nc.ui.jyglgt.j400675; 
 
import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.pub.beans.UIDialog;
import nc.ui.trade.controller.IControllerBase; 
import nc.ui.trade.manage.BillManageUI; 
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;

 
/** 

 * ����: �����۸�����EH�� 
*/ 
public class ClientEventHandler extends nc.ui.jyglgt.j400670.ClientEventHandler{ 
	 
	public ClientEventHandler(BillManageUI billUI, IControllerBase control) { 
		super(billUI, control); 
	} 

	   
	@Override
	protected StringBuffer getWhere() {
		StringBuffer sb = new StringBuffer();
		sb.append(" billtype='"+getUIController().getBillType()+"' and ");
		return sb;
	}
	
	 /**
     * ����: �Զ�����˰�ť��������
     * @return 
     * @author ����������
     * 2011-6-3 
     */
	public void onBoCheckPass() throws Exception {
    	AggregatedValueObject avo=getBufferData().getCurrentVO();
    	if(avo==null)return ;
        int result=getBillUI().showYesNoMessage("ȷ���˲�����?");
        if(result==UIDialog.ID_YES){
	        SuperVO vo=(SuperVO) avo.getParentVO();
			// �����
	        vo.setAttributeValue("vapproveid" , _getOperator());
			// �������
			vo.setAttributeValue("dapprovedate", _getDate());
        	// ������
 	        vo.setAttributeValue("vunapproveid" , null);
 			// ��������
 			vo.setAttributeValue("vunapprovedate", null);
			// ����״̬
			vo.setAttributeValue("vbillstatus", IJyglgtBillStatus.CHECKPASS);
			Proxy.getItf().updateVO(vo);
	        getBufferData().updateView(); 
	        super.onBoRefresh();
        }
	}
	

	 /**
     * ����: �Զ�������ť��������
     * @return 
     * @author ����������
     * 2011-6-3 
     */
	public void onBoCheckNoPass() throws Exception {
    	AggregatedValueObject avo=getBufferData().getCurrentVO();
    	if(avo==null)return ;
        int result=getBillUI().showYesNoMessage("ȷ���˲�����?");
        if(result==UIDialog.ID_YES){
	        SuperVO vo=(SuperVO) avo.getParentVO();
	         String  vpid= (String) vo.getAttributeValue("vapproveid");
//	         if(_getOperator().equals(vpid.trim())){//update by cm 2014-5-16  
	        	String errmsg = beforeCheckNoPass(avo);
	        	if(!Toolkits.isEmpty(errmsg)){
	        		throw new BusinessException(errmsg);
	        	}
	        	// ������
	 	        vo.setAttributeValue("vunapproveid" , _getOperator());
	 			// ��������
	 			vo.setAttributeValue("vunapprovedate", _getDate());
	 			// ����״̬
	 			vo.setAttributeValue("vbillstatus", IJyglgtBillStatus.FREE);
				// �����
		        vo.setAttributeValue("vapproveid" , "");
				// �������
				vo.setAttributeValue("vapprovedate", null);
				vo.setAttributeValue("dapprovedate", null);
				Proxy.getItf().updateVO(vo);
	 	        getBufferData().updateView(); 
	 	        super.onBoRefresh();
	 	        //update by cm 2014-5-16  
//	         }else {
//	        	 showErrorMessage("������������˱�����ͬһ���ˣ�");
//	         }
			
        }
	}
} 
