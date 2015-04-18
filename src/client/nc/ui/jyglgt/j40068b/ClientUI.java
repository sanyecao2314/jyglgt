package nc.ui.jyglgt.j40068b; 
  
import java.util.ArrayList;
import java.util.HashMap;

import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.bd.ref.busi.PsndocDefaulRefModel;
import nc.ui.jyglgt.billmanage.AbstractClientUI; 
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.trade.bill.AbstractManageController; 
import nc.ui.trade.manage.ManageEventHandler; 
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;
 
/** 
 * @author 自动生成 
 * 名称: 价格优惠单UI类 
*/ 
public class ClientUI extends AbstractClientUI { 
	private String oldclasswherepart = null;
	public ClientUI() { 
	} 
  
	@Override
	protected void postInit() {
		super.postInit();
		UIRefPane panel = (UIRefPane) getBillCardPanel().getHeadItem("commitmanid").getComponent();
		nc.ui.bd.ref.busi.PsndocDefaulRefModel refm = (PsndocDefaulRefModel) panel.getRefModel();
		oldclasswherepart = refm.getClassWherePart();
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
 		if(e.getKey().equals("pk_dept")){
 			UIRefPane panel = (UIRefPane) getBillCardPanel().getHeadItem("commitmanid").getComponent();
 			nc.ui.bd.ref.busi.PsndocDefaulRefModel refm = (PsndocDefaulRefModel) panel.getRefModel();
 			String pk_dept = getHeadValueObject("pk_dept")==null?null:getHeadValueObject("pk_dept").toString();
 			if(pk_dept != null)
 				refm.setClassWherePart(refm.getClassWherePart()+" and pk_deptdoc='"+pk_dept+"' ");
 			else
 				refm.setClassWherePart(oldclasswherepart);
 		} else	if(e.getKey().equals("pk_customer")){
             setHeadValue("pk_saleorder", null);
 		}else if(e.getKey().equals("pk_saleorder")){
 			String pk_saleorder = getHeadValueObject("pk_saleorder")==null?"":getHeadValueObject("pk_saleorder").toString();
            String sql="select * from so_saleorder_b a left join so_saleexecute  b on a.corder_bid=b.csale_bid where a.csaleid='"+pk_saleorder+"'";
            try {
				ArrayList list =Proxy.getItf().queryArrayBySql(sql);
				for(int i=0;i<getBillCardPanel().getRowCount();i++){
					getBillCardPanel().delLine();
				}
				if(list!=null&&list.size()>0){
					for(int i=0;i<list.size();i++){
						HashMap map=(HashMap)list.get(i);	
						getBillCardPanel().addLine();
						setBodyValue(Toolkits.getString(map.get("cinvbasdocid")), i, "pk_invbasdoc");
						setBodyValue(Toolkits.getString(map.get("cinventoryid")), i, "pk_invmandoc");
						setBodyValue(Toolkits.getString(map.get("corder_bid")), i, "vdef6");
						setBodyValue(Toolkits.getString(map.get("vfree1")), i, "vfree1");
						setBodyValue(Toolkits.getString(map.get("vfree2")), i, "vfree2");
						setBodyValue(Toolkits.getString(map.get("vfree3")), i, "vfree3");
						setBodyValue(Toolkits.getString(map.get("vdef18")), i, "vdef7");
						setBodyValue(Toolkits.getString(map.get("pk_defdoc18")), i, "pk_quality");
						setBodyValue(Toolkits.getUFDouble(map.get("noriginalcurtaxprice")), i, "ddprice");
						getBillCardPanel().execBodyFormula(i, "pk_invbasdoc");
						getBillCardPanel().execBodyFormula(i, "pk_quality");
					}
				}
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 		}else if(e.getPos()==HEAD&&e.getKey().equals("disprice")){
 			UFDouble disprice = getHeadValueObject("disprice")==null?new UFDouble(0):new UFDouble(getHeadValueObject("disprice").toString());
			for(int i=0;i<getBillCardPanel().getRowCount();i++){
				setBodyValue(disprice, i, "disprice");
			}
 		}else if(e.getPos()==BODY&&e.getKey().equals("disprice")){
 			setHeadValue("disprice", null);
 		}
 	}
 	
    public boolean beforeEdit(BillItemEvent e) {
    	if(e.getItem().getKey().equals("pk_saleorder")){
    		StringBuffer sqlsale = new StringBuffer("");
			Object invmandocid = getHeadValueObject("pk_customer");
			Object sdate = getHeadValueObject("vdef6");
			Object edate = getHeadValueObject("vdef7");
			UIRefPane panesale = (UIRefPane)getBillCardPanel().getHeadItem("pk_saleorder").getComponent();
			if(!Toolkits.isEmpty(invmandocid)){
				sqlsale.append(" and so.ccustomerid = '"+invmandocid.toString()+"' ");
			}
			if(!Toolkits.isEmpty(sdate)&&!Toolkits.isEmpty(sdate)){
				sqlsale.append(" and so.dbilldate >= '"+sdate.toString()+"' ");
				sqlsale.append(" and so.dbilldate <= '"+edate.toString()+"' ");
			}
			panesale.getRefModel().addWherePart(sqlsale.toString());
    	}
    	return super.beforeEdit(e);
    }
} 
