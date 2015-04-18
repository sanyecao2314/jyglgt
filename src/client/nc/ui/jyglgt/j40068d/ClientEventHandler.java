package nc.ui.jyglgt.j40068d;

import java.util.ArrayList;
import java.util.HashMap;
import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIDialog;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.gt.gs.gs11.BalanceInfoBVO;
import nc.vo.gt.gs.gs11.ExAggSellPaymentJSVO;
import nc.vo.gt.gs.gs11.SellPaymentJSVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;



/**
 * @功能：优惠结算
 */
public class ClientEventHandler extends nc.ui.jyglgt.j40068c.ClientEventHandler {


	public ClientEventHandler(BillManageUI arg0, IControllerBase arg1) {
		super(arg0, arg1);

	}
	
	
	/**
	 * 功能说明：计算按钮
	 * @author 施鹏
	 * @throws BusinessException 
	 * @time 2011-7-6
	 * */
	@SuppressWarnings("unchecked")
	protected void onBoCount() throws BusinessException {
		int rowcount = getModelByName(Model2).getRowCount();
		Object isdeal = getBillCardPanelWrapper().getBillCardPanel().getHeadItem("accounty").getValueObject();
		if(isdeal!=null){
			boolean flag= Boolean.valueOf(isdeal.toString());
			if(flag){
				showErrorMessage("不可重复计算！");
				return;
			}
		}
		if(rowcount<=0){
			showErrorMessage("销售明细无数据,无法计算,请先检索明细!");
			return;
		}
		for(int i=0;i<rowcount;i++){
			String preferential = getString(getbodyValueByModel(Model2, i, "preferential"));
			if(Toolkits.isEmpty(preferential)){
				showErrorMessage("第"+(i+1)+"行,优惠单价为空,请输入后,再计算!");
				return;
			}
		}		
//		//从第二个页签汇总数据
//		secondToFirst(rowcount);
		//从临时表里面查询出汇总数据
		StringBuffer sb = new StringBuffer();
		sb.append("select pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vfree5,dj,pk_receiveway,")
		 .append(" sum(num)num,sum(fnum)fnum,sum(jsmny) jsmny,sum(yhmny)yhmny  ")
		 .append(" from ")
		 .append(" (select pk_measure_b,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,null vfree5,vdef11 dj,null pk_receiveway,")
		 .append(" nvl(twonum,0) num, nvl(num,0)fnum,sum(nvl(balancemny,0))-nvl(vdef1,0) jsmny,sum(nvl(preferentialmny,0)) yhmny")  
		 .append(" from jyglgt_balancelist_b ")
		 .append("  where nvl(dr,0)=0 and vdef19='"+_getOperator()+"'")// in("+pk_balanceListTemps+")") 
		 .append("  group by pk_measure_b,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vdef1,vdef11 ,twonum,num")
		 .append(" )a") 
		 .append("  group by  pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vfree5, dj,pk_receiveway");
		ArrayList<HashMap<String,String>> al = (ArrayList<HashMap<String,String>>)Proxy.getItf().queryArrayBySql(sb.toString());
		if(al!=null&&al.size()>0){
			for(int i=0;i<al.size();i++){
				HashMap<String,String> hm = (HashMap<String,String>) al.get(i);
				HashMap<String,UFDouble>hm_ufd=new HashMap<String,UFDouble>();//保留精度三位计重
				HashMap<String,UFDouble>hm_ufd2=new HashMap<String,UFDouble>();//保留原精度计重
				String pk_measure_b=hm.get("pk_measure_b")==null?"":hm.get("pk_measure_b");
				String pk_receiveway=hm.get("pk_receiveway")==null?"":hm.get("pk_receiveway");
				String key = pk_measure_b+pk_receiveway;
				UFDouble num=hm.get("num")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("num")));
				UFDouble ynum=hm.get("ynum")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("ynum")));
				UFDouble num_3=new UFDouble(num.doubleValue(),3);//三位精度计重
				if(!hm.containsKey(key)){
					hm_ufd.put(key, num_3);
					hm_ufd2.put(key, num);
					num=num_3;
				}else{
					UFDouble totalnum = hm_ufd.get(key);
					UFDouble totalnum2 = hm_ufd.get(key);
					totalnum2=totalnum2.add(num);
					if(new UFDouble(totalnum2.doubleValue(),3).sub(new UFDouble(ynum.doubleValue(),3)).doubleValue()==0){
						num=ynum.sub(totalnum);
					}else{
						totalnum=totalnum.add(num_3);
						num=num_3;
					}
				}
				UFDouble fnum=hm.get("fnum")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("fnum")));
				UFDouble jsmny=hm.get("jsmny")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("jsmny")),2);
				UFDouble yhmny=hm.get("yhmny")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("yhmny")),2);
				UFDouble yhprice=new UFDouble(yhmny.div(num).doubleValue(),4);
				UFDouble jsprice=num.doubleValue()==0?new UFDouble(0):jsmny.div(num);
				getModelByName(Model1).addLine();
				setbodyValueByModel(Model1, pk_measure_b, i, "vdef12");
				setbodyValueByModel(Model1, hm.get("pk_invmandoc"), i, "pk_invmandoc");
				setbodyValueByModel(Model1, hm.get("pk_invbasdoc"), i, "pk_invbasdoc");
				setbodyValueByModel(Model1, hm.get("vfree1"), i, "vfree1");
				setbodyValueByModel(Model1, hm.get("vfree2"), i, "vfree2");
				setbodyValueByModel(Model1, hm.get("vfree3"), i, "vfree3");
				setbodyValueByModel(Model1, hm.get("vfree4"), i, "vfree4");
				setbodyValueByModel(Model1, hm.get("vfree5"), i, "vfree5");
				setbodyValueByModel(Model1, hm.get("dj"), i, "vdef11");		
				setbodyValueByModel(Model1, pk_receiveway, i, "vdef13");	//收款方式
				setbodyValueByModel(Model1, num, i, "suttle");
				setbodyValueByModel(Model1, fnum, i, "num");
				setbodyValueByModel(Model1, jsprice, i, "price");
				setbodyValueByModel(Model1, yhprice, i, "preferentialprice");
				setbodyValueByModel(Model1, jsmny, i, "moneys"); 
				setbodyValueByModel(Model1, yhmny, i, "preferentialmny");
				setbodyValueByModel(Model1, _getCorp().getPk_corp(), i, "pk_corp");
				setbodyValueByModel(Model1, new Integer(0), i, "dr");
				execAllBodyEditFormulas(Model1,i);
			}
		}
		//设为已计算
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("accounty").setValue(new UFBoolean(true));
		//表头特殊优惠
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("specialyhjg").setEnabled(false);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vdef1").setEnabled(false);
		getBillUI().showHintMessage("计算完成");
	}

	public void onBoAdd(ButtonObject bo) throws Exception {
		JY06TOJY07Dialog dialog = new JY06TOJY07Dialog();
		if (dialog.getQueryConditionClient().showModal() == UIDialog.ID_CANCEL) {
			super.onBoCancel();
			return;
		}
		dialog.getUpData();
		if(dialog.showModal()!= UIDialog.ID_OK)
			return;
		HYBillVO[] hybvo_up=dialog.getHybvos();
		ExAggSellPaymentJSVO hybvo_down=new ExAggSellPaymentJSVO();	
		HYBillVO hybvo = JY06TOJY07Tool.upToDownHybillVO(hybvo_up[0]);
		SellPaymentJSVO hvo = (SellPaymentJSVO) hybvo.getParentVO();
		hybvo_down.setParentVO(hvo);
		BalanceInfoBVO[] bvos = (BalanceInfoBVO[])hybvo.getChildrenVO();
		hybvo_down.setTableVO("gt_balanceinfo_b", hybvo.getChildrenVO());
		((SellPaymentJSVO)hybvo_down.getParentVO()).setPk_operator(_getOperator());
		((SellPaymentJSVO)hybvo_down.getParentVO()).setDmakedate(_getDate());
		((SellPaymentJSVO)hybvo_down.getParentVO()).setVbillstatus(IJyglgtBillStatus.FREE);
		((SellPaymentJSVO)hybvo_down.getParentVO()).setBalancedate(_getDate());
		super.onBoAddPrv(bo);		
		getBillCardPanelWrapper().getBillCardPanel().setBillValueVO(hybvo_down);
		getBillCardPanelWrapper().getBillCardPanel().getBillModel("gt_balanceinfo_b").setBodyDataVO(bvos);
		getBillUI().setDefaultData();
		getBillCardPanelWrapper().getBillCardPanel().getBillModel("gt_balanceinfo_b").execLoadFormula();
	}

	public AggregatedValueObject getAVO(){
		AggregatedValueObject avo=null;
		try {
			avo= getBillCardPanelWrapper().getBillVOFromUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return avo;
	}
	@Override
	protected void onBoSave() throws Exception {
		int row1 = getModelByName(Model1).getRowCount();
		if(row1<=0){
			showErrorMessage("结算信息为空,不能保存!请计算!");
			return;
		}
        super.onBoSelfSave();
	}


	/**
	 * 返回查询条件
	 * @author 施鹏
	 * */
	protected StringBuffer getWhere(){
		StringBuffer sb=new StringBuffer();
		sb.append(" ( billtype='"+IJyglgtBillType.JY07+"') and");
		return sb;
	}
	

	protected void onBoDelete() throws Exception {    
		super.onBoDelete_yh();
	}
	
	
	public void onBoCheckPass() throws Exception {
		AggregatedValueObject avo=getBufferData().getCurrentVO();
		String  pk_corp= getBillUI()._getCorp().getPk_corp();
    	if(avo==null)return ;
        int result=getBillUI().showYesNoMessage("确定此操作吗?");
        if(result==UIDialog.ID_YES){
           //回写收款余额表
           ExAggSellPaymentJSVO iexaggvo = (ExAggSellPaymentJSVO)avo;
           Proxy.getItf().writeBackToBalanceYh(iexaggvo,pk_corp,_getOperator());
	        getBufferData().updateView(); 
	        super.onBoRefresh();
        }
	}
	
	public void onBoCheckNoPass() throws Exception {
	 	super.onBoRefresh();
		AggregatedValueObject avo=getBufferData().getCurrentVO();
    	if(avo==null)return ;
        int result=getBillUI().showYesNoMessage("确定此操作吗?");
        if(result==UIDialog.ID_YES){
	        SuperVO vo=(SuperVO) avo.getParentVO();
	         String  vpid= (String)vo.getAttributeValue("vapproveid");
	         String pk_corp=(String)vo.getAttributeValue("pk_corp");
	         if(_getOperator().equals(vpid.trim())){
	        	int rowcount = getModelByName(Model1).getRowCount();
	        	for(int j=0;j<rowcount;j++){
	        		String vdef12 =getbodyValueByModel(Model1,j, "vdef12")==null?"":(String) getbodyValueByModel(Model1,j, "vdef12").toString();
	        		if(!Toolkits.isEmpty(vdef12)){
	        			String sql="select cupsourcebillbodyid from so_saleinvoice_b where cupsourcebillbodyid='"+vdef12+"' and nvl(dr,0)=0";
	        			String cupsourcebillbodyid=Proxy.getItf().queryStrBySql(sql);
	        			if(!Toolkits.isEmpty(cupsourcebillbodyid)){
		        			showErrorMessage("该单据已生成销售发票，不可弃审");
			        		return;	
	        			}
	        		}
	        	 }
	        	 ExAggSellPaymentJSVO iexaggvo = (ExAggSellPaymentJSVO)avo;
	        	 //弃审反操作
	        	 Proxy.getItf().deleteBackToBalanceYh(iexaggvo,pk_corp,_getOperator());
	 	         getBufferData().updateView(); 
		         super.onBoRefresh();
	         }else {
	        	 showErrorMessage("审核人与弃审人必须是同一个人！");
	         }
        }
	}
}
