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

 * 名称: 调拨价格政策EH类 
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
     * 功能: 自定义审核按钮触发方法
     * @return 
     * @author 公共开发者
     * 2011-6-3 
     */
	public void onBoCheckPass() throws Exception {
    	AggregatedValueObject avo=getBufferData().getCurrentVO();
    	if(avo==null)return ;
        int result=getBillUI().showYesNoMessage("确定此操作吗?");
        if(result==UIDialog.ID_YES){
	        SuperVO vo=(SuperVO) avo.getParentVO();
			// 审核人
	        vo.setAttributeValue("vapproveid" , _getOperator());
			// 审核日期
			vo.setAttributeValue("dapprovedate", _getDate());
        	// 弃审人
 	        vo.setAttributeValue("vunapproveid" , null);
 			// 弃审日期
 			vo.setAttributeValue("vunapprovedate", null);
			// 单据状态
			vo.setAttributeValue("vbillstatus", IJyglgtBillStatus.CHECKPASS);
			Proxy.getItf().updateVO(vo);
	        getBufferData().updateView(); 
	        super.onBoRefresh();
        }
	}
	

	 /**
     * 功能: 自定义弃审按钮触发方法
     * @return 
     * @author 公共开发者
     * 2011-6-3 
     */
	public void onBoCheckNoPass() throws Exception {
    	AggregatedValueObject avo=getBufferData().getCurrentVO();
    	if(avo==null)return ;
        int result=getBillUI().showYesNoMessage("确定此操作吗?");
        if(result==UIDialog.ID_YES){
	        SuperVO vo=(SuperVO) avo.getParentVO();
	         String  vpid= (String) vo.getAttributeValue("vapproveid");
//	         if(_getOperator().equals(vpid.trim())){//update by cm 2014-5-16  
	        	String errmsg = beforeCheckNoPass(avo);
	        	if(!Toolkits.isEmpty(errmsg)){
	        		throw new BusinessException(errmsg);
	        	}
	        	// 弃审人
	 	        vo.setAttributeValue("vunapproveid" , _getOperator());
	 			// 弃审日期
	 			vo.setAttributeValue("vunapprovedate", _getDate());
	 			// 单据状态
	 			vo.setAttributeValue("vbillstatus", IJyglgtBillStatus.FREE);
				// 审核人
		        vo.setAttributeValue("vapproveid" , "");
				// 审核日期
				vo.setAttributeValue("vapprovedate", null);
				vo.setAttributeValue("dapprovedate", null);
				Proxy.getItf().updateVO(vo);
	 	        getBufferData().updateView(); 
	 	        super.onBoRefresh();
	 	        //update by cm 2014-5-16  
//	         }else {
//	        	 showErrorMessage("审核人与弃审人必须是同一个人！");
//	         }
			
        }
	}
} 
