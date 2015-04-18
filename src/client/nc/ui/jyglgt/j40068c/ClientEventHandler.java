package nc.ui.jyglgt.j40068c;

import java.util.ArrayList;
import java.util.HashMap;
import com.sun.jna.Native;
import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.jyglgt.billmanage.AbstractEventHandler;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.billcodemanage.BillcodeRuleBO_Client;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.pub.tools.BannerDialog;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.gt.gs.gs11.BalanceInfoBVO;
import nc.vo.gt.gs.gs11.ExAggSellPaymentJSVO;
import nc.vo.gt.gs.gs11.SellPaymentJSVO;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.jyglgt.button.IJyglgtButton;
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
 * @功能：销售结算
 */
public class ClientEventHandler extends AbstractEventHandler {


	protected String Model1 = "gt_balanceinfo_b";
	protected String Model2 = "gt_balancelist_b";

	public ClientEventHandler(BillManageUI arg0, IControllerBase arg1) {
		super(arg0, arg1);
	}
	
	protected void onBoSave() throws Exception {
		int row1 = getModelByName(Model1).getRowCount();
		if(row1<=0){
			showErrorMessage("结算信息为空,不能保存!");
			return;
		}
        getBillCardPanelWrapper().getBillCardPanel().getBillData().dataNotNullValidate();
		BillItem billcode=getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vbillcode");
		//保存时生成单据号
		if(needBillNo()){
	        String billNo = BillcodeRuleBO_Client.getBillCode(getUIController().getBillType(), getBillUI()._getCorp().getPk_corp(),
			        null, null);
            if(billcode!=null){
				getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vbillcode").setValue(billNo);
			}
		}
		AggregatedValueObject avo=getBillCardPanelWrapper().getBillVOFromUI();
		String  pk_corp= getBillUI()._getCorp().getPk_corp();
        ExAggSellPaymentJSVO iexaggvo = (ExAggSellPaymentJSVO)avo;
        Proxy.getItf().writeBackToCreditAndBalanceDetail(iexaggvo,pk_corp);
		String vbillcode =getHeadValue("vbillcode")==null?"":getHeadValue("vbillcode").toString();
    	StringBuffer strWhere = getWhere();
    	if(!vbillcode.equals("")){
    		strWhere.append(" vbillcode ='" + vbillcode + "' ");
    		strWhere.append(" and pk_corp='"+_getCorp().getPk_corp()+"' ");
    	}else{
    		strWhere.append(" pk_corp='"+_getCorp().getPk_corp()+"' ");
    	}
		SuperVO[] queryVos = queryHeadVOs(strWhere.toString());
		getBufferData().clear();
		// 增加数据到Buffer
		addDataToBuffer(queryVos);
		updateBuffer();
	}
	
	protected void onBoSelfSave() throws Exception {
		super.onBoSave();
	}
	
	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn);
		switch (intBtn) {
		case IJyglgtButton.BTN_CALCULATE:
			onBoCount();
			return;
		case IJyglgtButton.BTN_READCARD:
			onBoReadCard();
			return;	
		case IJyglgtButton.BTN_VADD:
			onBoVAdd(intBtn);
			return;	
		}
	}
	
	public void onBoVAdd(int intBtn) throws Exception{
		nc.ui.pub.ButtonObject bo = getButtonManager().getButton(intBtn);
		super.onBoAdd( bo);
	}
	/**
	 * 功能：读卡
	 * @author shipeng
	 * @param onBoReadCard
	 * @time 2014-10-26
	 * */
	private void onBoReadCard() {
		Object pk_cumandoc = getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_cumandoc").getValueObject();
		if(Toolkits.isEmpty(pk_cumandoc)){
			getBillUI().showWarningMessage("请先选择客户后再读卡！");
			return;
		}	
		Runnable checkrun = new Runnable(){
	        public void run(){
                //线程对话框，系统运行提示框
                BannerDialog dialog = new BannerDialog(getBillUI());
                dialog.start();            
                ICCard iccard = null;
                Declare.mwrf epen = null;
                try{
                    //将需要做处理的方法写在这里
                	epen = (Declare.mwrf) Native.loadLibrary("mwhrf_bj", Declare.mwrf.class);   
                	if (epen != null){
                		System.out.println("DLL加载成功！");                 		
                		iccard = ICCard.getInstance();
                		iccard.DevConnect(epen);
                		int time = 1;
                		String res = null;
                		while(time > 0){
                			res = iccard.read(epen);
                			if (res != null) break;
                			Thread.sleep(300l);
                			time--;
                		}
                		if (res != null ) {
							String[] bills = res.split(",");
							//派车单号
							getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vdef6").setValue(bills[1]);
							Object pk_cumandoc = getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_cumandoc").getValueObject();
							ExAggSellPaymentJSVO hybvo_down=new ExAggSellPaymentJSVO();
							BalanceInfoBVO[] bvos = IssueToBalanceTool.getBodyVOBySql(Toolkits.getString(pk_cumandoc), bills[1].trim());
							if(bvos!=null&&bvos.length>0){
								hybvo_down.setTableVO("gt_balanceinfo_b",bvos);	
								getBillCardPanelWrapper().getBillCardPanel().getBillModel("gt_balanceinfo_b").setBodyDataVO(bvos);
								getBillCardPanelWrapper().getBillCardPanel().getBillModel("gt_balanceinfo_b").execLoadFormula();	
							}
						}
                	} else {
                		System.out.println("DLL加载失败！");	
                	}
                }catch(Exception e){
                        e.printStackTrace();
                }finally{
                	iccard.disconnectDev(epen);
                    dialog.end();
                }
	        }
	};
	//启用线程
	Thread readcard = new Thread(checkrun);
	readcard.setDaemon(true);
	readcard.start();
	
	}
	
	
	@SuppressWarnings("unchecked")
	protected void onBoCount() throws BusinessException {
		Object isdeal = getBillCardPanelWrapper().getBillCardPanel().getHeadItem("accounty").getValueObject();
		if(isdeal!=null){
			boolean flag= Boolean.valueOf(isdeal.toString());
			if(flag){
				showErrorMessage("不可重复计算！");
				return;
			}
		}
		
		int rowcount = getModelByName(Model2).getRowCount();
		if(rowcount<=0){
			showErrorMessage("销售明细无数据,无法计算,请先检索明细!");
			return;
		}
		
		//从临时表里面查询出汇总数据
		StringBuffer sb = new StringBuffer();
		sb.append(" select pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vdef11 dj,")
		 .append("  sum(nvl(twonum,0)) num")
		 .append(" from gt_balancelist_b ")
		 .append("  where nvl(dr,0)=0 and vdef20='"+_getOperator()+"' ")
		 .append(" group by pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vdef11");
		ArrayList<HashMap<String,String>> al = (ArrayList<HashMap<String,String>>)Proxy.getItf().queryArrayBySql(sb.toString());
		HashMap<String,UFDouble>hm_jl2=new HashMap<String,UFDouble>();
		if(al!=null&&al.size()>0){
			for(int i=0;i<al.size();i++){
				HashMap<String,String> hm = (HashMap<String,String>) al.get(i);
				String pk_invmandoc=hm.get("pk_invmandoc")==null?"":hm.get("pk_invmandoc").trim();
				String pk_invbasdoc=hm.get("pk_invbasdoc")==null?"":hm.get("pk_invbasdoc").trim();
				String vfree1=hm.get("vfree1")==null?"":hm.get("vfree1").trim();
				String vfree2=hm.get("vfree2")==null?"":hm.get("vfree2").trim();
				String vfree3=hm.get("vfree3")==null?"":hm.get("vfree3").trim();
				String vfree4=hm.get("vfree4")==null?"":hm.get("vfree4").trim();
				String dj=hm.get("dj")==null?"":hm.get("dj").trim();
				String key = pk_invmandoc+pk_invbasdoc+vfree1+vfree2+vfree3+vfree4+dj;
				UFDouble num=hm.get("num")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("num")));
				if(!hm_jl2.containsKey(key)){
					hm_jl2.put(key, num);
				}
			}
		}
		
		StringBuffer sb_fnum = new StringBuffer();
		sb_fnum.append(" select sum(fnum)fnum,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,dj")
		.append(" from (select distinct pk_measure_b,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vdef11 dj,")
		 .append("  nvl(num,0)fnum ")
		 .append(" from gt_balancelist_b ")
		 .append("  where nvl(dr,0)=0 and vdef20='"+_getOperator()+"') ")
		 .append(" group by pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,dj");
		ArrayList<HashMap<String,String>> al2 = (ArrayList<HashMap<String,String>>)Proxy.getItf().queryArrayBySql(sb_fnum.toString());
		HashMap<String,UFDouble>hm_jl=new HashMap<String,UFDouble>();
		if(al2!=null&&al2.size()>0){
			for(int i=0;i<al2.size();i++){
				HashMap<String,String> hm = (HashMap<String,String>) al2.get(i);
				String pk_invmandoc=hm.get("pk_invmandoc")==null?"":hm.get("pk_invmandoc").trim();
				String pk_invbasdoc=hm.get("pk_invbasdoc")==null?"":hm.get("pk_invbasdoc").trim();
				String vfree1=hm.get("vfree1")==null?"":hm.get("vfree1").trim();
				String vfree2=hm.get("vfree2")==null?"":hm.get("vfree2").trim();
				String vfree3=hm.get("vfree3")==null?"":hm.get("vfree3").trim();
				String vfree4=hm.get("vfree4")==null?"":hm.get("vfree4").trim();
				String dj=hm.get("dj")==null?"":hm.get("dj").trim();
				String key = pk_invmandoc+pk_invbasdoc+vfree1+vfree2+vfree3+vfree4+dj;
				UFDouble fnum=hm.get("fnum")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("fnum")));
				if(!hm_jl.containsKey(key)){
					hm_jl.put(key, fnum);
				}
			}
		}
		
		//从临时表里面查询出汇总数据
		sb = new StringBuffer();
		sb.append("select pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vfree5,dj,pk_receiveway,")
		 .append(" sum(num)num, sum(ynum) ynum,sum(fnum)fnum,sum(ddmny) ddmny,sum(jsmny) jsmny,sum(yhmny) yhmny,sum(yjsmny)yjsmny  ")
		 .append(" from ")
		 .append(" (select pk_measure_b,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,null vfree5,vdef11 dj,pk_receiveway,")
		 .append(" nvl(twonum,0) num,nvl(weight,0) ynum, nvl(num,0)fnum,nvl(vdef9,0) ddmny,sum(nvl(vdef3,0))-sum(nvl(preferentialmny,0)) jsmny,sum(nvl(preferentialmny,0)) yhmny,sum(nvl(vdef4,0))yjsmny")
		 .append(" from gt_balancelist_b ")
		 .append("  where nvl(dr,0)=0 and vdef20='"+_getOperator()+"'")
		 .append("  group by pk_measure_b,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vdef11,pk_receiveway,vdef9,twonum,weight,num")
		 .append(" )a") 
		 .append("  group by  pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vfree5, dj,pk_receiveway");
		al = (ArrayList<HashMap<String,String>>)Proxy.getItf().queryArrayBySql(sb.toString());
		StringBuffer sb2 = new StringBuffer();//查询总二次计重
		sb2.append("select sum(num) totalnum ")
		 .append(" from ")
		 .append(" (select pk_measure_b,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,null vfree5,vdef11 dj,pk_receiveway,")
		 .append(" nvl(twonum,0) num,nvl(weight,0) ynum, nvl(num,0)fnum,nvl(vdef9,0) ddmny,sum(nvl(vdef3,0))-sum(nvl(preferentialmny,0)) jsmny,sum(nvl(preferentialmny,0)) yhmny,sum(nvl(vdef4,0))yjsmny")
		 .append(" from gt_balancelist_b ")
		 .append("  where nvl(dr,0)=0 and vdef20='"+_getOperator()+"'") 
		 .append("  group by pk_measure_b,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vdef11,pk_receiveway,vdef9,twonum,weight,num")
		 .append(" )a");
		String strtotalnum=Proxy.getItf().queryStrBySql(sb2.toString());
		UFDouble totalnum_sql=strtotalnum==null?new UFDouble(0):new UFDouble(strtotalnum);
		HashMap<String,String>hm_jr=new HashMap<String,String>();
		HashMap<String,UFDouble>hm_ufd=new HashMap<String,UFDouble>();//保留精度三位计重
		HashMap<String,UFDouble>hm_ufd2=new HashMap<String,UFDouble>();//保留原精度计重
		UFDouble totalnum_insert=new UFDouble(0);
		if(al!=null&&al.size()>0){
			for(int i=0;i<al.size();i++){
				HashMap<String,String> hm = (HashMap<String,String>) al.get(i);
				String pk_invmandoc=hm.get("pk_invmandoc")==null?"":hm.get("pk_invmandoc").trim();
				String pk_invbasdoc=hm.get("pk_invbasdoc")==null?"":hm.get("pk_invbasdoc").trim();
				String vfree1=hm.get("vfree1")==null?"":hm.get("vfree1").trim();
				String vfree2=hm.get("vfree2")==null?"":hm.get("vfree2").trim();
				String vfree3=hm.get("vfree3")==null?"":hm.get("vfree3").trim();
				String vfree4=hm.get("vfree4")==null?"":hm.get("vfree4").trim();
				String vfree5=hm.get("vfree5")==null?"":hm.get("vfree5").trim();
				String dj=hm.get("dj")==null?"":hm.get("dj").trim();
				String pk_receiveway=hm.get("pk_receiveway")==null?"":hm.get("pk_receiveway").trim();
				UFDouble num=hm.get("num")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("num")));
				UFDouble ynum=hm.get("ynum")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("ynum")));
				UFDouble num_3=new UFDouble(num.doubleValue(),3);//三位精度计重
				UFDouble fnum=new UFDouble(0);
				String key = pk_invmandoc+pk_invbasdoc+vfree1+vfree2+vfree3+vfree4+dj;					
				UFDouble num_insert=new UFDouble(0);
				if(!hm_jr.containsKey(key)){
					hm_ufd.put(key, num_3);
					hm_ufd2.put(key, num);
					num_insert=num_3;
					hm_jr.put(key, key);
					fnum=hm_jl.get(key);
				}else{
					UFDouble totalnum = hm_ufd.get(key);
					UFDouble totalnum2 = hm_ufd2.get(key);
					totalnum2=totalnum2.add(num);
					UFDouble totaltwonum=hm_jl2.get(key);
					if(totalnum2.sub(totaltwonum).doubleValue()==0){
						num_insert=totaltwonum.sub(totalnum);
					}else{
						totalnum=totalnum.add(num_3);
						num_insert=num_3;
					}
				}	
				if(i==al.size()-1){
					num_insert=totalnum_sql.sub(totalnum_insert);
				}else{
					totalnum_insert=totalnum_insert.add(num_insert);
				}				
				UFDouble ddmny=hm.get("ddmny")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("ddmny")),2);
				UFDouble jsmny=hm.get("jsmny")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("jsmny")),2);
				UFDouble yhmny=hm.get("yhmny")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("yhmny")),2);
				UFDouble yjsmny=hm.get("yjsmny")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("yjsmny")),2);
				UFDouble ddprice=num.doubleValue()==0?new UFDouble(0):ddmny.div(ynum);
				UFDouble jsprice=num.doubleValue()==0?new UFDouble(0):jsmny.div(num_insert);
				UFDouble yhprice=num.doubleValue()==0?new UFDouble(0):yhmny.div(num_insert);
				getModelByName(Model1).addLine();
				setbodyValueByModel(Model1, pk_invmandoc, i, "pk_invmandoc");
				setbodyValueByModel(Model1, pk_invbasdoc, i, "pk_invbasdoc");
				setbodyValueByModel(Model1, vfree1, i, "vfree1");
				setbodyValueByModel(Model1, vfree2, i, "vfree2");
				setbodyValueByModel(Model1, vfree3, i, "vfree3");
				setbodyValueByModel(Model1, vfree4, i, "vfree4");
				setbodyValueByModel(Model1, vfree5, i, "vfree5");
				setbodyValueByModel(Model1, dj, i, "vdef11");			
				setbodyValueByModel(Model1, pk_receiveway, i, "vdef13");	//收款方式
				setbodyValueByModel(Model1, num_insert, i, "suttle");
				setbodyValueByModel(Model1, fnum, i, "num");
				setbodyValueByModel(Model1, ddprice, i, "price");
				setbodyValueByModel(Model1, jsprice, i, "vdef1");
				setbodyValueByModel(Model1, yhprice, i, "preferentialprice");
				setbodyValueByModel(Model1, ddmny, i, "moneys"); 
				setbodyValueByModel(Model1, jsmny, i, "vdef2"); 
				setbodyValueByModel(Model1, yhmny, i, "preferentialmny");
				setbodyValueByModel(Model1, yjsmny, i, "vdef4");
				setbodyValueByModel(Model1, _getCorp().getPk_corp(), i, "pk_corp");
				setbodyValueByModel(Model1, new Integer(0), i, "dr");
				execAllBodyEditFormulas(Model1,i);
			}
		}
		
		//设为已计算
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("accounty").setValue(new UFBoolean(true));
		//更新临时插入标记
		int row_model2=getModelByName(Model2).getRowCount();
		for(int i=0; i<row_model2;i++){
			setbodyValueByModel(Model2,null, i, "vdef20");
		}
		getBillUI().showHintMessage("计算完成");
	}
	
	
	protected void onBoDelete() throws Exception {

    	// 界面没有数据或者有数据但是没有选中任何行
		if (getBufferData().getCurrentVO() == null)
			return;

		if (MessageDialog.showOkCancelDlg(getBillUI(),
				nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
						"UPPuifactory-000064")/* @res "档案删除" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
						"UPPuifactory-000065")/* @res "是否确认删除该基本档案?" */
				, MessageDialog.ID_CANCEL) != UIDialog.ID_OK)
			return;
	
		AggregatedValueObject modelVo = getBufferData().getCurrentVO();
		getBusinessAction().delete(modelVo, getUIController().getBillType(),
				getBillUI()._getDate().toString(), getBillUI().getUserObject());
		if (PfUtilClient.isSuccess()) {
		
			AggregatedValueObject avo=getBufferData().getCurrentVO();
			String  pk_corp= getBillUI()._getCorp().getPk_corp();
	        ExAggSellPaymentJSVO iexaggvo = (ExAggSellPaymentJSVO)avo;
	        Proxy.getItf().delBackToCreditAndBalanceDetail(iexaggvo,pk_corp);			
			// 清除界面数据
			getBillUI().removeListHeadData(getBufferData().getCurrentRow());
			if (getUIController() instanceof ISingleController) {
				ISingleController sctl = (ISingleController) getUIController();
				if (!sctl.isSingleDetail())
					getBufferData().removeCurrentRow();
			} else {
				getBufferData().removeCurrentRow();
			}

		}
		if (getBufferData().getVOBufferSize() == 0)
			getBillUI().setBillOperate(IBillOperate.OP_INIT);
		else
			getBillUI().setBillOperate(IBillOperate.OP_NOTEDIT);
		getBufferData().setCurrentRow(getBufferData().getCurrentRow());
    
		
	}
	
	protected void onBoDelete_yh() throws Exception {
    	super.onBoDelete(); 
	}
		
	
	/**
	 * 取表体数据
	 * @param modelName
	 * @param row
	 * @param column
	 * @return
	 */
	public Object getbodyValueByModel(String modelName, int row, String column){
		return getModelByName(modelName).getValueAt(row, column);
	}
	
	/**
	 * 向表体设置数据
	 * @param modelName
	 * @param row
	 * @param key
	 * @return
	 */
	public void setbodyValueByModel(String modelName,Object value,int row, String key){
		 getModelByName(modelName).setValueAt(value ,row, key);
	}
	
	/**
	 * @param modelName
	 * @return BillModel
	 */
	public BillModel getModelByName(String modelName){
		return getBillCardPanelWrapper().getBillCardPanel().getBillModel(modelName);
	}
	
	/**
	 * 执行表本单行所有项 的公式
	 * @param String modelName
	 * @param int row
	 * @author sp
	 */ 
	public void execAllBodyEditFormulas(String modelName,int row) {
		BillItem[] allbodyitems = getModelByName(modelName).getBodyItems();
		for(int j=0;j<allbodyitems.length;j++){
			String[] formulas = allbodyitems[j].getEditFormulas();
			if(formulas == null){
				continue;
			}
			getModelByName(modelName).execFormula(row,formulas);
		}
	}
	
	 /**
     * 功能: 对同一列前台重复行的校验
     * @param column 字段
     * @param rows  行数
     * @return boolean
     * @author 施鹏
     * 2011-6-3 
     */
    protected boolean isPreRepeat(String column,int rows,String model){
        for(int i=0;i<rows-1;i++){
            String preValue=getStr(i,column);
            for(int j=i+1;j<rows;j++){
                String curValue=getStr(j,column,model);                  
                if(preValue.compareTo(curValue)==0){
                	String key=getBillCardPanelWrapper().getBillCardPanel().getBillModel(model).getItemByKey(column).getName();
                    showErrorMessage("第"+(j+1)+"行："+key+"："+curValue+" 重复！");
                    return true;
                }
            }
        }
        return false;
    }
    
	public void onBoAdd(ButtonObject bo) throws Exception {
		IssuleToBalanceDialog dialog = new IssuleToBalanceDialog();
		dialog.getUpData();
		if(dialog.showModal()!= UIDialog.ID_OK)
			return;
		GeneralBillVO[] hybvo_up=dialog.getHybvos_cur();
		ExAggSellPaymentJSVO hybvo_down=new ExAggSellPaymentJSVO();
		HYBillVO hybvo = IssueToBalanceTool.upToDownHybillVO(hybvo_up[0]);
		SellPaymentJSVO hvo = (SellPaymentJSVO) hybvo.getParentVO();
		hybvo_down.setParentVO(hvo);
		BalanceInfoBVO[] bvos = (BalanceInfoBVO[])hybvo.getChildrenVO();
		hybvo_down.setTableVO("gt_balanceinfo_b", hybvo.getChildrenVO());
		((SellPaymentJSVO)hybvo_down.getParentVO()).setPk_operator(_getOperator());
		((SellPaymentJSVO)hybvo_down.getParentVO()).setDmakedate(_getDate());
		((SellPaymentJSVO)hybvo_down.getParentVO()).setVbillstatus(IJyglgtBillStatus.FREE);
		((SellPaymentJSVO)hybvo_down.getParentVO()).setBalancedate(_getDate());
		super.onBoAdd(bo);		
		getBillCardPanelWrapper().getBillCardPanel().setBillValueVO(hybvo_down);
		getBillCardPanelWrapper().getBillCardPanel().getBillModel("gt_balanceinfo_b").setBodyDataVO(bvos);
		getBillUI().setDefaultData();
		getBillCardPanelWrapper().getBillCardPanel().getBillModel("gt_balanceinfo_b").execLoadFormula();
	}
	
	public void onBoAddPrv(ButtonObject bo) throws Exception{
		super.onBoAdd(bo);
	}
    
    /**
     * 功能: 返回字符串
     * @param row 行号
     * @param str 列字符
     * @author 施鹏
     * 2011-6-3 
     */
    protected String getStr(int row,String str,String model){
       return getBillCardPanelWrapper().getBillCardPanel().getBillModel(model).getValueAt(row, str)==null?"":getBillCardPanelWrapper().getBillCardPanel().getBillModel(model).getValueAt(row, str).toString();
    }
   
	public void onBoCheckPass() throws Exception {
		AggregatedValueObject avo=getBufferData().getCurrentVO();
		String  pk_corp= getBillUI()._getCorp().getPk_corp();
    	if(avo==null)return ;
        int result=getBillUI().showYesNoMessage("确定此操作吗?");
        if(result==UIDialog.ID_YES){
           //回写收款余额表
           ExAggSellPaymentJSVO iexaggvo = (ExAggSellPaymentJSVO)avo;
           Proxy.getItf().writeBackToBalance(iexaggvo,pk_corp,_getOperator());
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
	        	 BalanceInfoBVO[] infobvos = (BalanceInfoBVO[]) iexaggvo.getTableVO(Model1);
	        	 for(int i=0;i<infobvos.length;i++){
	        		 String pk_balanceinfo_b = infobvos[i].getPk_balanceinfo_b();
	        		 StringBuffer sb = new StringBuffer();
	        		 sb.append(" select count(*) from gt_sellpaymentjs a inner join gt_balanceinfo_b b on a.pk_sellpaymentjs=b.pk_sellpaymentjs ") 
	        		 .append(" where nvl(a.dr,0)=0 and nvl(b.dr,0)=0 and a.billtype='"+IJyglgtBillType.JY07+"' and b.vdef15='"+pk_balanceinfo_b+"' and a.pk_corp='"+_getCorp().getPk_corp()+"' ");
	        		 String flag=Proxy.getItf().queryStrBySql(sb.toString());
	        		 if(getUFDouble(flag).doubleValue()>0){
		        		 showErrorMessage("该单据已做过优惠不可弃审");
		        		 return;
		        	 }
	        	 }
	        	 //弃审反操作
	        	 Proxy.getItf().deleteBackToBalance(iexaggvo,pk_corp,_getOperator());
	 	         getBufferData().updateView(); 
		         super.onBoRefresh();
	         }else {
	        	 showErrorMessage("审核人与弃审人必须是同一个人！");
	         }
        }
	}
	
	/**
	 * 返回查询条件
	 * @author 施鹏
	 * @serialData 2011-7-4
	 * */
	protected StringBuffer getWhere(){
		StringBuffer sb=new StringBuffer();
		sb.append(" ( billtype='"+IJyglgtBillType.JY06+"') and");
		return sb;
	}

	
}
