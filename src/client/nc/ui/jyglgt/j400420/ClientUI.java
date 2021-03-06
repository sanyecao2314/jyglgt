package nc.ui.jyglgt.j400420; 
  
import nc.ui.jyglgt.billmanage.AbstractClientUI; 
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.trade.bill.AbstractManageController; 
import nc.ui.trade.manage.ManageEventHandler; 
 
/** 
 * @author 施鹏 
 * 名称: 采购奖罚单UI类 
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

	@Override
	public void afterEdit(BillEditEvent e) {
		super.afterEdit(e);
		if(e.getKey().equals("pk_order")){
			Object pk_order=getHeadValueObject("pk_order");
			UIRefPane pane = (UIRefPane)getBillCardPanel().getHeadItem("pk_invbasdoc").getComponent();
			String sql = " and pk_invbasdoc in(select cbaseid from po_order_b where nvl(dr,0)=0 and corderid='"+pk_order+"')";
			pane.getRef().getRefModel().addWherePart(sql.toString(),true);
			pane.updateUI();
		}else if(e.getKey().equals("sanctiontype")){
			String sanctiontype=getHeadValueObject("sanctiontype")==null?"":getHeadValueObject("sanctiontype").toString();
		    if(sanctiontype.equals("单价")){
		    	getBillCardPanel().getHeadItem("price").setEnabled(true);
		    	getBillCardPanel().getHeadItem("mny").setEnabled(false);
		    	getBillCardPanel().getHeadItem("bucklenum").setEnabled(false);
		    }else  if(sanctiontype.equals("金额")){
		    	getBillCardPanel().getHeadItem("price").setEnabled(false);
		    	getBillCardPanel().getHeadItem("mny").setEnabled(true);
		    	getBillCardPanel().getHeadItem("bucklenum").setEnabled(false);
		    }else   if(sanctiontype.equals("扣重")){
		    	getBillCardPanel().getHeadItem("price").setEnabled(false);
		    	getBillCardPanel().getHeadItem("mny").setEnabled(false);
		    	getBillCardPanel().getHeadItem("bucklenum").setEnabled(true);
		    }
		}
	} 
  
} 
