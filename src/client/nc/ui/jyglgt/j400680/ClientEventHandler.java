package nc.ui.jyglgt.j400680;

import java.util.ArrayList;
import java.util.Vector;
import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.jyglgt.billmanage.AbstractEventHandler;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fa.pub.bill.FAPfUtil;
import nc.vo.jyglgt.j400680.CreditVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;

/**
* �����տ�֪ͨ��
 */

public class ClientEventHandler extends AbstractEventHandler {
	
	ClientEnvironment ce =ClientEnvironment.getInstance();
	
	

	public ClientEventHandler(BillManageUI arg0, IControllerBase arg1) {
		super(arg0, arg1);
	}

	@Override
	protected void onBoLineAdd() throws Exception {
		String receivetype=getHeadValue("receivetype")==null?"":getHeadValue("receivetype").toString();
		String pk_receiveway=getHeadValue("pk_receiveway")==null?"":getHeadValue("pk_receiveway").toString();
//		if(receivetype.equals(IJyglgtConst.RECEIVETYPE_TK)){
//        	showErrorMessage("�տ�����Ϊ�˿����������壡");
//        	return;
//		}
		if(!Toolkits.isEmpty(pk_receiveway)){
			String str = getBalanceName(pk_receiveway);
			if(str.indexOf("�ж�")== -1){
				showErrorMessage("�տʽ�������жң�����������壡");
				return;
			}
		}else{
			showErrorMessage("���������տʽ��");
			return;
		}
		super.onBoLineAdd();
	}
	private String getBalanceName(String pk_receiveway) throws BusinessException{
		String sql = "select balancename from gt_balanceway where pk_balanceway = '"+pk_receiveway+"'";
		String str = Proxy.getItf().queryStrBySql(sql);
		return str;
	}
	@Override
	protected void onBoSave() throws Exception {		
		UFDouble skmny = getBillCardPanelWrapper().getBillCardPanel().getHeadItem("bcskmny").getValueObject()==null?new UFDouble(0):new UFDouble(getBillCardPanelWrapper().getBillCardPanel().getHeadItem("bcskmny").getValueObject().toString());
		Vector total = getBillCardPanelWrapper().getBillCardPanel().getBillData().getBillModel().getTotalTableModel().getDataVector();
		int rows=getRowCount();
		if(rows>0&&!Toolkits.isEmpty(total)){
			int colum = getBillCardPanelWrapper().getBillCardPanel().getBillData().getBillModel().getBodyColByKey("moneyy");
			Vector obj = (Vector) total.get(0);
			UFDouble value = obj.get(colum)==null?new UFDouble(0):new UFDouble(obj.get(colum).toString());
			if(skmny.doubleValue()!=value.doubleValue()){
				showErrorMessage("��Ʊ���ϼ����ͷ��һ��");
				return;
			}
		}
		if(skmny.doubleValue()==0){
			showErrorMessage("����Ϊ�㣡");
			return;
		}
		//�Ӽ۷�ʽΪ�̶����ۣ��жҼӼ۲���Ϊ��
		String priceway=getHeadValue("priceway")==null?"":getHeadValue("priceway").toString();
		Object cdprice=getHeadValue("cdprice");
		if("�̶�����".equals(priceway)&&Toolkits.isEmpty(cdprice)){
			showErrorMessage("�Ӽ۷�ʽΪ�̶����ۣ��жҼӼ۲���Ϊ��");
			return;		
		}
		String pk_receiveway=getHeadValue("pk_receiveway")==null?"":getHeadValue("pk_receiveway").toString();
		if("�ж�".equals(pk_receiveway)&&Toolkits.isEmpty(priceway)){
			showErrorMessage("�տʽΪ�ж�ʱ���Ӽ۷�ʽ����");
			return;		
		}
		//		String str = getBalanceName(pk_receiveway);
//		if(str!=null){
//			if(str.indexOf("�ж�")== -1&&rows>0 ){
//				showErrorMessage("�տʽ�������жң����岻�������ݣ�");
//				return;		
//			}
//			if(str.indexOf("�ж�")==2){
//				//2012-10-24  ���Ƴж�Ҫ��
//				String priceway=getHeadValue("priceway")==null?"":getHeadValue("priceway").toString();
//				if(priceway.equals("")||priceway.length()<=0){
//					showErrorMessage("�տʽΪ�ж�ʱ����������Ӽ۷�ʽ������");
//					return;
//				}
//				//2012-10-24  ���Ƴж�Ҫ��
//			 }
//			
//		}
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("cdprice").setEdit(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("montax").setEdit(true);

		String receivetype = getHeadValue("receivetype")==null?"":getHeadValue("receivetype").toString();
//		if(receivetype.equals(IJyglgtConst.RECEIVETYPE_TK)){
//			Object pk_vbillcode_tk= getHeadValue("pk_vbillcode_tk");
//			if(Toolkits.isEmpty(pk_vbillcode_tk)){
//				showErrorMessage("��ѡ�˿�ţ�");
//				return;		
//			}
//		}
//		setfalseedit(true);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("priceway").setEdit(false);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("cdprice").setEdit(false);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("montax").setEdit(false);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("hddays").setEdit(false);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voicesdate").setEdit(false);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voiceedate").setEdit(false);
		super.onBoSave();
	}
	@Override
	protected void onBoCancel() throws Exception {
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("cdprice").setEdit(true);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("montax").setEdit(true);
//		setfalseedit(true);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("priceway").setEdit(false);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("cdprice").setEdit(false);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("montax").setEdit(false);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("hddays").setEdit(false);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voicesdate").setEdit(false);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voiceedate").setEdit(false);
		super.onBoCancel();
	}
	@Override
	protected void onBoEdit() throws Exception {
		Object vdef15 = getHeadValue("vdef15");
		Object vdef13 = getHeadValue("vdef13");
		if(!Toolkits.isEmpty(vdef15)&&vdef15.toString().equals("Y")){
			showErrorMessage("���տ�ɼ��л���Ҳ�б�֤���Ʊ�����ɲ����޸�!");
			return;
		}
		super.onBoEdit();
		if(Toolkits.getString(vdef13).equals("Y")){	//add by cm 2012-02-11
			onBindingOrders();
			//ADD BY SK START 2012-3-28
			String receivetype = getHeadValue("receivetype")==null?"":getHeadValue("receivetype").toString();
//			if(receivetype.equals(IJyglgtConst.RECEIVETYPE_TK)){
//				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_vbillcode_tk").setEnabled(true);
//				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("priceway").setEnabled(false);
//				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_balatype").setEnabled(false);
//				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("csaleid").setEnabled(false);
//				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("txdays").setEnabled(false);
//			}
			//ADD BY SK END
        }else{
		int receivetype = (Integer) getBillCardPanelWrapper().getBillCardPanel().getHeadItem("receivetype").getValueObject();
		if(receivetype==2){
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_vbillcode_tk").setEnabled(true);//.setEdit(true);
		}else{
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_vbillcode_tk").setEnabled(false);//.setEdit(false);
		}
			String wayname=Toolkits.getString(getBillCardPanelWrapper().getBillCardPanel().getHeadItem("priceway").getValueObject());
			if(wayname==null||wayname.length()==0){
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("cdprice").setEnabled(false);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("montax").setEnabled(false);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("priceway").setEnabled(false);
//				getBillCardPanel().getHeadItem("pk_productline").setEnabled(false);
//				getBillCardPanel().getHeadItem("pk_custproduct").setEnabled(false);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("hddays").setEnabled(false);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voicesdate").setEnabled(false);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voiceedate").setEnabled(false);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("txdays").setEnabled(false);
				getBillCardPanelWrapper().getBillCardPanel().setHeadItem("cdprice", null);
				getBillCardPanelWrapper().getBillCardPanel().setHeadItem("montax", null);
				getBillCardPanelWrapper().getBillCardPanel().setHeadItem("priceway", null);
//				getBillCardPanel().setHeadItem("pk_productline", null);
//				getBillCardPanel().setHeadItem("pk_custproduct", null);
				getBillCardPanelWrapper().getBillCardPanel().setHeadItem("hddays", null);
				getBillCardPanelWrapper().getBillCardPanel().setHeadItem("voicesdate", null);
				getBillCardPanelWrapper().getBillCardPanel().setHeadItem("voiceedate", null);
				getBillCardPanelWrapper().getBillCardPanel().setHeadItem("txdays", null);
			}else
				if(wayname.equals("�̶��Ӽ�")){
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("priceway").setEdit(true);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("cdprice").setEdit(true);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("montax").setEdit(false);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("hddays").setEdit(false);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voicesdate").setEdit(false);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voiceedate").setEdit(false);
				getBillCardPanelWrapper().getBillCardPanel().setHeadItem("montax", null);
				getBillCardPanelWrapper().getBillCardPanel().setHeadItem("hddays", null);
				getBillCardPanelWrapper().getBillCardPanel().setHeadItem("voicesdate", null);
				getBillCardPanelWrapper().getBillCardPanel().setHeadItem("voiceedate", null);
				//add by cm start
				getBillCardPanelWrapper().getBillCardPanel().setHeadItem("txdays", null);
				//add by cm end
			}else if(wayname.equals("���ּӼ�")){
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("priceway").setEdit(true);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("cdprice").setEdit(false);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("montax").setEdit(true);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("hddays").setEdit(true);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voicesdate").setEdit(true);
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voiceedate").setEdit(true);
				getBillCardPanelWrapper().getBillCardPanel().setHeadItem("cdprice", null);
			}
        }
	}


	@Override
	protected void onBoDelete() throws Exception {

		AggregatedValueObject avo = getBufferData().getCurrentVO();
		CreditVO vo = (CreditVO) avo.getParentVO();
	     if(Math.abs(getUFDouble(vo.getTkmny()).doubleValue())>0){
	    		showErrorMessage("��ʹ�����˻�������ɾ����");
	        	return ;
	     }
		else if(Math.abs(getUFDouble(vo.getFhmny()).doubleValue())>0){
        	showErrorMessage("��ʹ���˷���������ɾ����");
        	return ;
        }else if(Math.abs(getUFDouble(vo.getJsmny()).doubleValue())>0){
        	showErrorMessage("��ʹ���˽��������ɾ����");
        	return ;
        }else if(vo.getVdef15()==("Y")){
        	showErrorMessage("�ɼ��л���Ҳ�б�֤���Ʊ�����ɵı�֤�𣬲���ɾ��!");
        	return;
        }else if(Toolkits.getString(vo.getVdef13()).equals("Y")){	//add by cm 2012-02-11
        	showErrorMessage("�Ƿ����ģ�����ɾ��!");
        	return;
        }
		super.onBoDelete();
	}
	
	@Override
	public void onBoAdd(ButtonObject bo) throws Exception {
		// TODO Auto-generated method stub
		super.onBoAdd(bo);
		
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("skmny").setEdit(true);
	}
	private int falg = 0;
	@Override
	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn);
		switch (intBtn) {
//		case IGTButton.BTN_COLLECTION:
//			onBoCollection();
//			return;
//		case IGTButton.BTN_MARGINCOLLECTION:
//			onBoMarginCollection();
//			return;
//		case IGTButton.BTN_REGISTRATION:
//			onBoRegistration(); 
//			return;
//		case IGTButton.BTN_BINDINGORDERS://add by sk
//			/**
//			 * add by cm start 2012-2-10
//			 * ��Ŀ����	fhmny
//			 *///��Ŀ����	fhmny
////			String vdef13 = (String) getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vdef13").getValueObject()==null?new String("N"):
////				(String) getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vdef13").getValueObject();
//			Object fhmny =   getBillCardPanelWrapper().getBillCardPanel().getHeadItem("fhmny").getValueObject()==null?new UFDouble(0):
//				 getBillCardPanelWrapper().getBillCardPanel().getHeadItem("fhmny").getValueObject();
////			int billstatus = (Integer) getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vbillstatus").getValueObject();
//			Integer receivetype = (Integer) getBillCardPanelWrapper().getBillCardPanel().getHeadItem("receivetype").getValueObject()==null?0:
//				(Integer) getBillCardPanelWrapper().getBillCardPanel().getHeadItem("receivetype").getValueObject();
//			if(receivetype==0){
//				if(Toolkits.getUFDouble(fhmny).doubleValue()>0){
//					return;
//				}else{
//					falg=1;
//					onBindingOrders();
//				}
//			}else return;
		}
	}
	
	private void setfalseedit1(boolean flag,boolean tflag) {
		String wayname=Toolkits.getString(getBillCardPanelWrapper().getBillCardPanel().getHeadItem("priceway").getValueObject());
		if(wayname==null||wayname.length()==0){
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("cdprice").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("montax").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("hddays").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voicesdate").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voiceedate").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_productline").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_custproduct").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_receiveway").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_balatype").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("skmny").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("bcskmny").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("isreceipt").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("receivemnydate").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_deptdoc").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_psndoc").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_cumandoc").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vbillcode").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_vbillcode_tk").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("receivetype").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("deposit").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("priceway").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("txdays").setEnabled(flag);
		}else if(wayname.equals("�̶��Ӽ�")){
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("priceway").setEdit(tflag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("cdprice").setEdit(tflag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("montax").setEdit(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("hddays").setEdit(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voicesdate").setEdit(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voiceedate").setEdit(flag);
			//
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_productline").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_custproduct").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_receiveway").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_balatype").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("skmny").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("bcskmny").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("isreceipt").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("receivemnydate").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_deptdoc").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_psndoc").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_cumandoc").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vbillcode").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_vbillcode_tk").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("receivetype").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("deposit").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("txdays").setEnabled(flag);

		}else if(wayname.equals("���ּӼ�")){
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("priceway").setEdit(tflag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("cdprice").setEdit(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("montax").setEdit(tflag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("hddays").setEdit(tflag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voicesdate").setEdit(tflag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voiceedate").setEdit(tflag);
			//
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_productline").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_custproduct").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_receiveway").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_balatype").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("skmny").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("bcskmny").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("isreceipt").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("receivemnydate").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_deptdoc").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_psndoc").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_cumandoc").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vbillcode").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_vbillcode_tk").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("receivetype").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("deposit").setEnabled(flag);
			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("txdays").setEnabled(flag);
		}

	}
	
	
	private void onBindingOrders() throws Exception  {
		// TODO Auto-generated method stub
		super.onBoRefresh();
    	super.onBoEdit();
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("csaleid").setEnabled(true);
//		if(falg==1){
//			
//			setfalseedit1(false,true);
//			//add by sk 2012-4-6 �޶���������
//			falg=0;
//		}else{
//			setfalseedit(false);
//		}
		String pk_cumandoc =Toolkits.getString(getHeadValue("pk_cumandoc"));
		UIRefPane pane = (UIRefPane)getBillCardPanelWrapper().getBillCardPanel().getHeadItem("csaleid").getComponent();
	    if((pane!=null)&&(!pk_cumandoc.equals(""))){
	    	StringBuffer sql=new StringBuffer("");
	    	sql.append(" and so_sale.ccustomerid='"+pk_cumandoc+"' ");
	    	pane.getRef().getRefModel().addWherePart(sql.toString(),true);
	    }
	    pane.updateUI();
	}

//	private void setfalseedit(boolean flag) {
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("cdprice").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("montax").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("hddays").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voicesdate").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("voiceedate").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_productline").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_custproduct").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_receiveway").setEnabled(flag);
////		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_balatype").setEnabled(flag);//update by cm 2012-2-22 Ҫ����㷽ʽ�����޸�
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("skmny").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("bcskmny").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("isreceipt").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("receivemnydate").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_deptdoc").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_psndoc").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_cumandoc").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vbillcode").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_vbillcode_tk").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("receivetype").setEnabled(flag);
//		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("deposit").setEnabled(flag);//add by cm 2012-2-10
//		String pk_receiveway  = getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_receiveway").getValueObject()==null?"":
//			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_receiveway").getValueObject().toString();
////		String pk_receiveway = (String)getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_receiveway").getValueObject();
//		BalanceWayVO vo = null;
//		try {
//			//��������������
//			vo = (BalanceWayVO) HYPubBO_Client.queryByPrimaryKey(BalanceWayVO.class, pk_receiveway);
//		} catch (UifException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		String balancename = "";
//		if(vo!=null){
//			balancename = vo.getBalancename();
//		}
//		if(balancename.indexOf("�ж�")==-1){
//			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("priceway").setEnabled(flag);
//			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("txdays").setEnabled(flag);
//		}else{
//			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("priceway").setEnabled(true);
//			getBillCardPanelWrapper().getBillCardPanel().getHeadItem("txdays").setEnabled(true);
//		}
//	}

	/**
	 * @throws exc 
	* @����: �տ�
	* @���� ������  
	* @����ʱ�� 2011-6-28 ����05:10:29
	* @throws
	 */
//	protected void onBoCollection() throws Exception{
//		AggregatedValueObject avo = getBufferData().getCurrentVO();
//		Object isreceipt = getBillCardPanelWrapper().getBillCardPanel().getHeadItem("isreceipt").getValueObject();
//		if (avo == null){return;}
//		if (isreceipt.equals("false")){
//			showErrorMessage("�õ��ݲ����տ�");
//			return;
//		}
//		int result = getBillUI().showYesNoMessage("ȷ���˲�����?");
//		if (result == UIDialog.ID_YES) {
//			CreditVO hvo=(CreditVO)avo.getParentVO();
//			Integer  typevalue=hvo.getReceivetype();
//			UFDouble deposit=getUFDouble(hvo.getDeposit());
//			UFDouble skmny= getUFDouble(hvo.getSkmny());
//			String vdef15 = Toolkits.getString(getHeadValue("vdef15"));
//			if(vdef15.equals("Y")){
//				showErrorMessage("�ɻ���ת��ı�֤����Ҫ�տ��ת�տ�!");
//				return;
//			}
//			
//			if(typevalue==Integer.parseInt(IJyglgtConst.RECEIVETYPE_SK) && skmny.doubleValue()==0){
//				this.getBillUI().showErrorMessage("�տ�֪ͨ���տ����Ϊ�㣡");
//				return;
//			 }else if(typevalue==Integer.parseInt(IJyglgtConst.RECEIVETYPE_DJ)&&deposit.doubleValue()==0 ){
//				this.getBillUI().showErrorMessage("��֤�𣬱�֤�����Ϊ�㣡");
//				return;
//			}else{
//				//add by cm start 
//				String pk_credit = hvo.getPk_credit();
//				   String str = "select count(*) from gt_credit_hb_b where vdef11='"+pk_credit+"' and nvl(dr,0)=0 and pk_corp='"+hvo.getPk_corp()+"'";		            	  
//				   String Count= Proxy.getItf().queryStrBySql(str.toString());		  	            
//	 	            if(getUFDouble(Count).doubleValue()>=1){
//	 	            	 showErrorMessage("�õ������кϲ��տ�֪ͨ���������տ");
//	 		        	 return;
//	 	            }
//	 	            //add by cm end
//				StringBuffer sbf=new StringBuffer();
//			    sbf.append("select count(*) from arap_djzb where zyx1 in ('"+hvo.getVbillcode()+"')and dwbm='"+hvo.getPk_corp()+"' and nvl(dr,0)=0 ");
//			 	   String count=Proxy.getItf().queryStrBySql(sbf.toString());
//			   if(Toolkits.getInteger(count).intValue()>=1||(hvo.getVdef15()!=null&&hvo.getVdef15().equals("Y"))){
//				  this.getBillUI().showErrorMessage("NC�տ���տ�����ظ��տ");
//					return;
//			    }else{
//			    	avo.getParentVO().setAttributeValue("vapproveid", getBillUI()._getOperator());
//			    	if (typevalue==Integer.parseInt(IJyglgtConst.RECEIVETYPE_DJ) ||typevalue==Integer.parseInt(IJyglgtConst.RECEIVETYPE_SK)||typevalue==Integer.parseInt(IJyglgtConst.RECEIVETYPE_TK)){
//			    	        if(typevalue==Integer.parseInt(IJyglgtConst.RECEIVETYPE_DJ)){
//									String vbillcode = BillcodeRuleBO_Client.getBillCode("F2-1",getBillUI()._getCorp().getPk_corp(),null,null);
//									Proxy.getItf().ncGatherOrder(avo,vbillcode,_getDate()); 
//							 	getBillUI().showHintMessage("�տ�ɹ���");
//							}
//			    	        if(typevalue==Integer.parseInt(IJyglgtConst.RECEIVETYPE_SK)){
//									String vbillcode = BillcodeRuleBO_Client.getBillCode("D2",getBillUI()._getCorp().getPk_corp(),null,null);
//									Proxy.getItf().ncGatherOrder(avo,vbillcode,_getDate());
//							 	getBillUI().showHintMessage("�տ�ɹ���");	
//						     }
//			    	        if(typevalue==Integer.parseInt(IJyglgtConst.RECEIVETYPE_TK)){
//								String vbillcode = BillcodeRuleBO_Client.getBillCode("D2",getBillUI()._getCorp().getPk_corp(),null,null);
//								Proxy.getItf().ncGatherOrder(avo,vbillcode,_getDate());
//						 	getBillUI().showHintMessage("�˿�ɹ���");	
//					     }
//					   } else {this.getBillUI().showErrorMessage("����״̬Ϊ������տ�����Ϊ�տ�֪ͨ,��֤����˿���ܴ˲�����");
//						  return;}	
//				   }
//			   
// 	            
//			    }
//
//		}
//	}
	/**
	 * ��֤��ת�տ�
	 */
//	protected void onBoMarginCollection() throws Exception{
//		int checkpass = IBillStatus.CHECKPASS;
//		Object vbillstatic = getHeadValue("vbillstatus");
//		Object receivetype = getHeadValue("receivetype");
//		
//		if (getInteger(checkpass) == getInteger(vbillstatic.toString().trim())
//				&& receivetype.toString().trim().equals(IJyglgtConst.RECEIVETYPE_DJ)) {
//			AggregatedValueObject avo = getBufferData().getCurrentVO();
//			if (avo == null)
//				return;
//			CreditVO vo = (CreditVO) avo.getParentVO();
//			UFDouble djmny = getUFDouble(vo.getDeposit());
//			//edit by zyb vdef15ΪY,�����ǻ���ת�ı�֤��
//			String vdef15 = Toolkits.getString(getHeadValue("vdef15"));
//			String billNO= vo.getVbillcode();
//			StringBuffer sbf=new StringBuffer();
//			 sbf.append("select count(*) from arap_djzb where zyx1='"+billNO+"'and nvl(dr,0)=0 and dwbm='"+vo.getPk_corp()+"' ");
//			 String count= Proxy.getItf().queryStrBySql(sbf.toString());	
//			if(djmny.doubleValue()==0){
//				this.getBillUI().showErrorMessage("��֤��Ϊ�㣬����ת�տ");return;}
//			else if(getUFDouble(count).doubleValue()==0&&!vdef15.equals("Y")){
//				this.getBillUI().showErrorMessage("��δ�տ�����տ");
//				return;
//			}
//			Proxy.getItf().marginCollection(avo, getBillUI()._getDate());
//			getBillUI().showHintMessage("��֤��ת�տ�ɹ���");
//			getBufferData().updateView();
//			super.onBoRefresh();
//		} else {
//			this.getBillUI().showErrorMessage("����״̬Ϊ������տ�����Ϊ��֤����ܴ˲�����");
//		}
//	}
	/**
	* @����: ��Ʊ�Ǽ�
	* @���� �ĺ�  
	 */
//	protected void onBoRegistration() throws BusinessException{
//		AggregatedValueObject avo=getBufferData().getCurrentVO();
//		Object isreceipt = getBillCardPanelWrapper().getBillCardPanel().getHeadItem("isreceipt").getValueObject();
//		if (isreceipt.equals("false")){
//			showErrorMessage("�õ��ݲ�����Ʊ�Ǽ�");
//			return;
//		}
//		int result = getBillUI().showYesNoMessage("ȷ����Ʊ�Ǽǲ�����?");
//		if (result == UIDialog.ID_YES) {
//		if(avo==null)return ;
//		CreditVO cvo = (CreditVO)avo.getParentVO();
//		cvo.setAttributeValue("vdef20", _getCorp().getUnitname());
//		CreditBVO[] cbvo = (CreditBVO[])avo.getChildrenVO();
//		if(cbvo.length==0){
//			getBillUI().showErrorMessage("������Ʊ����Ϣ�����ܽ�����Ʊ�Ǽǣ�");return;
//		}
//		else{
//		for(CreditBVO bbvo : cbvo){
//			String pk_basinfo = bbvo.getPk_basinfo();
//		StringBuffer sbf=new StringBuffer();
//		sbf.append(" select fbmbillno from fbm_baseinfo where fbmbillno='"+pk_basinfo+"'and nvl(dr,0)=0 ");
//		String str = Proxy.getItf().queryStrBySql(sbf.toString());
//		if(str==null){
//		Proxy.getItf().fbmGatherOrder(avo,cvo);
//		getBillUI().showHintMessage("��Ʊ�Ǽǳɹ���");
//		return;
//		}
//		else{
//			this.getBillUI().showErrorMessage("��Ʊ�ѵǼǣ�");return;
//		}
//		}
//		}
//		}else{return;}
//	}
	
	/**
	 * ����˵������˰�ť�������ݣ����ö����������ݣ���ͨ����ͬ���տ������жϣ����ɻ����޸Ŀɷ�������
	 * 
	 * @return
	 * @author л����
	 * @throws Exception
	 * @time 2010-10-20
	 */
//	public void onBoCheckPass() throws Exception {
//		// �տ�����
//		String receivetype = getHeadValue("receivetype") == null ? ""
//				: getHeadValue("receivetype").toString().trim();
//		AggregatedValueObject avo = getBufferData().getCurrentVO();
//		String csaleid = (String) getBillCardPanelWrapper().getBillCardPanel().getHeadItem("csaleid").getValueObject();
////		int receivetype = (Integer) getBillCardPanelWrapper().getBillCardPanel().getHeadItem("receivetype").getValueObject();
//		int result ;
////		if(receivetype.equals(IJyglgtConst.RECEIVETYPE_DJ)&&csaleid!=null){
////		result = getBillUI().showYesNoMessage("������˺�ת�տ��Ҳ��ɱ༭����Ҫ�����۶�����ȷ���˲�����?");
////		}else{
//			result = getBillUI().showYesNoMessage("ȷ���˲�����?");
////		}
//		if (result == UIDialog.ID_YES) {
//			if (avo == null){return;}
//			// �տ�
//			if (receivetype.equals(IJyglgtConst.RECEIVETYPE_SK)) {				
//					try {
//						String vdef15  = avo.getParentVO().getAttributeValue("vdef15")==null?"":avo.getParentVO().getAttributeValue("vdef15").toString();
//						if(vdef15.equals("Y")){
//							showErrorMessage("�ɻ������ɵı�֤����Ҫ�տ�!");
//							return;
//						}
//						
//						AggregatedValueObject nvo = Proxy.getItf().gatherMny(avo,_getOperator(), _getDate());
//						 SuperVO vo=(SuperVO) nvo.getParentVO();
//						 Proxy.getItf().updateVO(vo);
//						 getBufferData().updateView(); 
//						 super.onBoRefresh();
//					} catch (BusinessException e) {
//						showErrorMessage(e.getMessage());}
//				// �˿�
//			} else if (receivetype.equals(IJyglgtConst.RECEIVETYPE_TK)) {
//				try {
//					AggregatedValueObject nvo =Proxy.getItf().returnMny(avo,_getOperator(), _getDate());
//					SuperVO vo=(SuperVO) nvo.getParentVO();
//					 Proxy.getItf().updateVO(vo);
//					 getBufferData().updateView(); 
//					 super.onBoRefresh();
//				} catch (BusinessException e) {
//						showErrorMessage(e.getMessage());}
//			}else {
////				String vbillcode=getHeadValue("vbillcode")==null?"":getHeadValue("vbillcode").toString();
////				String csaleid=getHeadValue("csaleid")==null?"":getHeadValue("csaleid").toString();
////	 	     	if(!Toolkits.isEmpty(csaleid)){
////					String updateSql="update so_sale"+
////		               " set vdef13='"+vbillcode+"' "+
////		               " where csaleid='"+csaleid+"'";
////		               Proxy.getIGTItf().updateBYsql(updateSql);
////	 	     	}  
//				  SuperVO vo=(SuperVO) avo.getParentVO();
//					// �����
//			        vo.setAttributeValue("vapproveid" , _getOperator());
//					// �������
//					vo.setAttributeValue("vapprovedate", _getDate());
//					// ����״̬
//					vo.setAttributeValue("vbillstatus", IGTBillStatus.CHECKPASS);
//		        	// ������
//		 	        vo.setAttributeValue("vunapproveid" ,"");
//		 			// ��������
//		 			vo.setAttributeValue("vunapprovedate",null);
//		 			Proxy.getItf().updateVO(vo);
//		 	        getBufferData().updateView(); 
////		 	         updateBuffer();
//		 	       super.onBoRefresh();
//			}
//		}else{return;}
//	 }
	
	 /**
	    * ����������NC�տɾ���ſ�������
	    * δɾ����ʾ��NC���տ��������
	    */
//		public void onBoCheckNoPass() throws Exception {
//			super.onBoRefresh();
//	    	AggregatedValueObject avo=getBufferData().getCurrentVO();
//	    	if(avo==null)return ;
//	        int result=getBillUI().showYesNoMessage("ȷ��Ҫ���������?");
//	        if(result==UIDialog.ID_YES){
//	        	String receivetype=getHeadValue("receivetype")==null?"":getHeadValue("receivetype").toString();
//	        	CreditVO vo=(CreditVO) avo.getParentVO();
//		        String vpid= (String) vo.getAttributeValue("vapproveid");
//		        if(_getOperator().equals(vpid.trim())){
//		        	String vbillcode =vo.getVbillcode();
//		            String djvbillcode = vo.getDjvbillcode();
//		            StringBuffer str=new StringBuffer("");
//		            StringBuffer strb=new StringBuffer("");
//		            StringBuffer strc=new StringBuffer("");
//		            StringBuffer stra=new StringBuffer("");
//		            String pk_credit = vo.getPk_credit();
//		            if(!Toolkits.isEmpty(vbillcode)&& !Toolkits.isEmpty(djvbillcode)){
//		            	  str.append("select count(*)from arap_djzb where zyx1='"+djvbillcode+"'and dwbm='"+vo.getPk_corp()+"'and nvl(dr,0)=0 ");		            	  
//		  	              String Count= Proxy.getItf().queryStrBySql(str.toString());		  	            
//		 	              if(getUFDouble(Count).doubleValue()>0){
//		 	            	 showErrorMessage("�õ������տ��������");
//		 		        	 return;
//		 	              }
//		            }else if(Toolkits.isEmpty(vo.getDjvbillcode())&&!Toolkits.isEmpty(vbillcode)){
//		            	  str.append("select count(*)from arap_djzb where zyx1='"+vbillcode+"' and dwbm='"+vo.getPk_corp()+"'and nvl(dr,0)=0 ");
//		            	  stra.append("select count(*)from arap_djzb where zyx1='"+vo.getPk_credit()+"' and dwbm='"+vo.getPk_corp()+"'and nvl(dr,0)=0 ");
//			  	            String Count= Proxy.getItf().queryStrBySql(str.toString());
//			  	            String Counta= Proxy.getItf().queryStrBySql(stra.toString());
//			  	          if(getUFDouble(Count).doubleValue()>0||getUFDouble(Counta).doubleValue()>0){
//			 	            	 showErrorMessage("�õ������տ��������");
//			 		        	 return;
//		            }else if(getUFDouble(vo.getFhmny()).doubleValue()>0){
//		            	showErrorMessage("��ʹ���˷�������������");
//		            	return ;
//		            }else if(getUFDouble(vo.getJsmny()).doubleValue()>0){
//		            	showErrorMessage("��ʹ���˽������������");
//		            	return ;
//		            }else if(receivetype.equals(IJyglgtConst.RECEIVETYPE_SX)){
//		            	showErrorMessage("�տ�����Ϊ��������������");
//		            	return;
//		            }else if(receivetype.equals(IJyglgtConst.RECEIVETYPE_MB)){
//		            	showErrorMessage("�տ�����Ϊ�ֲ�����������");
//		            	return;
//		            }else if(receivetype.equals(IJyglgtConst.RECEIVETYPE_SK)&&getUFDouble(vo.getTkmny()).doubleValue()>0){
//		            	showErrorMessage("�տ���˿��������");
//		            	return;
//		            }
//		            }
//		             //add by cm start
//		             strb.append("select count(*) from gt_credit_hb_b where vdef11='"+pk_credit+"' and nvl(dr,0)=0 and pk_corp='"+vo.getPk_corp()+"'");		            	  
//		             String Count1= Proxy.getItf().queryStrBySql(strb.toString());		  	            
//		             if(getUFDouble(Count1).doubleValue()>=1){
//		            	 showErrorMessage("�õ������кϲ��տ�֪ͨ������������");
//		            	 return;
//		             }
//		             strc.append(" select count(*) from gt_credit where vdef12='"+pk_credit+"' and nvl(dr,0)=0 and pk_corp='1002'");
//		             String Count2= Proxy.getItf().queryStrBySql(strc.toString());	
//		             if(getUFDouble(Count2).doubleValue()>=1){
//		            	 showErrorMessage("�õ��������ɱ�֤������ɾ����֤��");
//		            	 return;
//		             }
//		             //add by cm end
//		            if(receivetype.equals(IJyglgtConst.RECEIVETYPE_TK)){
//		                AggregatedValueObject nvo = Proxy.getItf().returnMnyF(avo,_getOperator(), _getDate());
//		            }
//		            // ������
//		 	        vo.setAttributeValue("vunapproveid" , _getOperator());
//		 			// ��������
//		 			vo.setAttributeValue("vunapprovedate", _getDate());
//		 			// ����״̬
//		 			vo.setAttributeValue("vbillstatus", IJyglgtBillStatus.FREE);
//		 		    // �����
//			        vo.setAttributeValue("vapproveid" , "");
//					// �������
//					vo.setAttributeValue("vapprovedate", null);
//					// ����֤��SK���ݺ�
//					vo.setAttributeValue("vdef19", null);
//					// ����SK���ݺ�
//					vo.setAttributeValue("vdef20", null);
//					// �Ƿ���SK
//					vo.setAttributeValue("whethersk", null);
//		 			Proxy.getItf().updateVO(vo);
//		 	        getBufferData().updateView(); 
//		 	        super.onBoRefresh();
//	            }else {
//		        	 showErrorMessage("������������˱�����ͬһ���ˣ�");
//		        	 return;}
//	            }
//	        }
		
		/**
		 * add by ���� 2012-2-17
		 */
		@Override
		public void onBoQuery() throws Exception {
			// TODO Auto-generated method stub
			StringBuffer strWhere = getWhere();
	        if (askForQueryCondition(strWhere) == false)
	            return;// �û������˲�ѯ
			Object makedate = getBillCardPanelWrapper().getBillCardPanel().getTailItem("dmakedate");
	        Object vbillcode = getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vbillcode");
	        int i = strWhere.lastIndexOf(" gt_credit.vdef18 = 0");
	        int k = strWhere.lastIndexOf(" gt_credit.vdef18 = 1");
	        int s = strWhere.lastIndexOf(" gt_credit.vdef18 in (0,1)");
	        int falg = 0;
	        
	        //���vdef18��ֵ����ô�����
	        if(strWhere.indexOf("gt_credit.vdef18 = 0")!=-1){
	        	falg=1;
	        	strWhere.replace(i+1,i+21, "1=1");
	        }
	        else if(strWhere.indexOf("gt_credit.vdef18 = 1")!=-1){
	        	falg=2;
	        	strWhere.replace(k+1,k+21, "1=1");
	        }else if(strWhere.indexOf("gt_credit.vdef18 in (0,1)")!=-1){
	        	falg=3;
	        	strWhere.replace(s+1,s+26, "1=1");
	        }
	        strWhere.append(" and pk_corp='"+_getCorp().getPk_corp()+"' ");
	        if(makedate!=null&&vbillcode!=null)
	        	strWhere.append(" order by dmakedate desc,vbillcode desc");
	        else if(makedate!=null)
	        	strWhere.append(" order by dmakedate desc");
	        else if(vbillcode!=null)
	        	strWhere.append(" order by vbillcode desc");
	        SuperVO[] queryVos = queryHeadVOs(strWhere.toString());
	        getBufferData().clear();
	        //����������ݷŵ�CreditVO��
	        CreditVO[] credvo = (CreditVO[]) queryVos;
	        ArrayList<CreditVO> listvo = new ArrayList<CreditVO>();
	        //���vdef18��ֵ�ǡ��ǡ����ߡ����ǽ��룬��ɸѡ������
	        if(falg==1||falg==2){
	        	for (int j = 0; j < credvo.length; j++) {
	        		String sql = " select count(*) from arap_djzb where arap_djzb.zyx1='"+credvo[j].getVbillcode()+"'and arap_djzb.dwbm='"+_getCorp().getPk_corp()+"' and nvl(dr,0)=0 ";
	        		if(falg==1){
	        			String count = Proxy.getItf().queryStrBySql(sql);
	        			if (Toolkits.getInteger(count)>=1) {
	        				listvo.add(credvo[j]);
	        			}
	        		}else if(falg==2){
	        			String count = Proxy.getItf().queryStrBySql(sql);
	        			if (Toolkits.getInteger(count)==0) {
	        				listvo.add(credvo[j]);
	        			}
	        		}
	        	}
	        	CreditVO[] credvo1 = new CreditVO[listvo.size()];
	        	for (int j = 0; j < listvo.size(); j++) {
	        		credvo1[j] = listvo.get(j);
	        	}
	        	addDataToBuffer(credvo1);//��vo���ݷŵ�������
	        	updateBuffer();
	        }else{
	        	addDataToBuffer(credvo);
	        	updateBuffer();
	        }
		}
		
		public void onBoCheckPass() throws Exception {
			if (this.getBillUI().getBufferData() == null)
				return;

			AggregatedValueObject billvo = (HYBillVO) this.getBillUI().getBufferData().getCurrentVO();
			if (Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.COMMIT
					|| Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.CHECKGOING) {
				billvo.getParentVO().setAttributeValue("vapproveid" , _getOperator());
				PfUtilClient.processActionFlow(this.getBillUI(),FAPfUtil.AUDIT, IJyglgtBillType.JY04, this._getDate().toString(), billvo,null);
				this.getBillUI().showHintMessage("�������...");

				super.onBoRefresh();
			} else if(Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.FREE){
				this.getBillUI().showWarningMessage("û���ύ���������ظ���ˣ�");
				return;
			}else {
				this.getBillUI().showWarningMessage("�����ظ���ˣ�");
				return;
			}
		}


		protected void onBoCommit() throws Exception {
			if (this.getBillUI().getBufferData() == null)
				return;
			AggregatedValueObject billvo = (HYBillVO) this.getBillUI().getBufferData()
					.getCurrentVO();
			if(!Toolkits.getString(billvo.getParentVO().getAttributeValue("pk_operator")).equals(_getOperator())){
				this.getBillUI().showErrorMessage("�ύʧ�ܣ���ȷ���Ƿ���Ȩ���ύ");
				return;
			}
			if (Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() != IJyglgtBillStatus.FREE
					&& Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() != IJyglgtBillStatus.NOPASS) {
				this.getBillUI().showWarningMessage("�����ظ��ύ��");
				return;
			}
			billvo.getParentVO().setAttributeValue("vbillstatus", IJyglgtBillStatus.COMMIT);
				PfUtilClient.processAction(FAPfUtil.SAVE, IJyglgtBillType.JY04, this._getDate()
						.toString(), billvo, FAPfUtil.UPDATE_SAVE);

			this.getBillUI().showHintMessage("�ύ���.....");
			super.onBoRefresh();
		}
		
		public void onBoCheckNoPass() throws Exception {
			if (this.getBillUI().getBufferData() == null)
				return;

			AggregatedValueObject billvo = (HYBillVO) this.getBillUI().getBufferData().getCurrentVO();
//			if(Toolkits.getInteger(billvo.getParentVO().getAttributeValue("receivetype")).intValue()==Integer.parseInt(IJyglgtConst.RECEIVETYPE_YS)){
//				this.getBillUI().showWarningMessage("�տ�֪ͨΪ�Żݽ��������������");
//				return;
//			}
			if (Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.CHECKPASS
					|| Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.CHECKGOING
					|| Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.NOPASS) {
//				if(!Toolkits.getString(billvo.getParentVO().getAttributeValue("vapproveid")).equals(_getOperator())){
//					this.getBillUI().showErrorMessage("������˱��ˣ�����������");
//					return;
//				}
				billvo.getParentVO().setAttributeValue("vapproveid" , _getOperator());
				billvo.getParentVO().setAttributeValue("vunapproveid" , _getOperator());
				billvo.getParentVO().setAttributeValue("vunapprovedate" , _getDate());
//				billvo.getParentVO().setAttributeValue("vbillstatus" , IJyglgtBillStatus.FREE);
				billvo.getParentVO().setAttributeValue("dapprovedate" , null);

				Object obj=PfUtilClient.processActionFlow(this.getBillUI(),FAPfUtil.UNAUDIT, IJyglgtBillType.JY04, this._getDate().toString(), billvo,null);
				this.getBillUI().showHintMessage("�������...");

				super.onBoRefresh();
			} else if(Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.FREE){
				this.getBillUI().showWarningMessage("�Ƶ���״̬������Ҫ����");
				return;
			}else {
				this.getBillUI().showWarningMessage("�����ظ�����");
				return;
			}
	      }
}