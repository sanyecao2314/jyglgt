package nc.work.gdt;

import nc.ui.jyglgt.billcard.AbstractClientUI;
import nc.ui.jyglgt.billcard.AbstractEventHandler;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.ICardController;
import nc.vo.jyglgt.button.ButtonFactory;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * 功能 作者：公共开发者 日期：2012-08-03
 */

public class ClientUI extends AbstractClientUI {
	
	public int maxbtableindex = 0;
	
	public ClientUI() {
	}

	@Override
	protected ICardController createController() {
		return new ClientCtrl();
	}

	// 界面调用时启用
	public void setDefaultData() throws Exception {

		// 调用父类自动赋值制单人，登陆公司
		super.setDefaultData();
		// 设定单据状态
		this.getBillCardPanel().setHeadItem("vbillstatus", IBillStatus.FREE);
		this.getBillCardPanel().setHeadItem("modelcode", "jyglgt");
		maxbtableindex = MyUtil.getMaxIndex(getBillCardPanel().getHeadItems());
	}
	
	@Override
	protected void initSelfData() {
		super.initSelfData();
	}
	
	@Override
	public void initPrivateButton() {
		super.initPrivateButton();
		ButtonVO funbtn = ButtonFactory.createButtonVO(800, "生成单据", "生成单据");
		funbtn.setOperateStatus(new int[]{IBillOperate.OP_ADD});
		addPrivateButton(funbtn);
		
		ButtonVO test = ButtonFactory.createButtonVO(801, "测试", "测试");
		test.setOperateStatus(new int[]{IBillOperate.OP_ADD});
		addPrivateButton(test);
	}
	
	@Override
	protected AbstractEventHandler createEventHandler() {
		return new ClientEventHandler(this,getUIControl());
	}
	
	public void afterEdit(BillEditEvent e) {
		super.afterEdit(e);
		Object nodecode = getHeadValueObject("nodecode");
		if(e.getKey().equals("vclass")){
			
		}else if(e.getKey().equals("htablename")){
			Object htname = getHeadValueObject("htablename");
			if(!Toolkits.isEmpty(htname)){
				String pka = htname.toString().substring(htname.toString().lastIndexOf("_")+1);
				setHeadValue("hpk", "pk_"+pka);
				setHeadValue("cancatfiel", "pk_"+pka);
				String firstpre = pka.substring(0, 1);
				setHeadValue("hvoname", "nc.vo.jyglgt."+nodecode.toString().toLowerCase()+"."+firstpre.toUpperCase()+pka.substring(1)+"VO");
			}
		}else if(e.getKey().equals("btablename")){
			Object btname = getHeadValueObject("btablename");
			if(!Toolkits.isEmpty(btname)){
				String pkb = btname.toString().substring(btname.toString().indexOf("_")+1);
				setHeadValue("bpk", "pk_"+pkb);
				String firstpre = pkb.substring(0, 1);
				setHeadValue("bvoname", "nc.vo.jyglgt."+nodecode.toString().toLowerCase()+"."+firstpre.toUpperCase()+pkb.substring(1).replace("_", "")+"VO");
			}
		}else if(e.getKey().startsWith("btablename")||e.getKey().equals("nodecode")){
			
			if(e.getKey().equals("nodecode")){//结点号变更
				setHeadValue("uiclass", "nc.ui.jyglgt."+nodecode.toString().toLowerCase()+".ClientUI");
				Object htname = getHeadValueObject("htablename");
				if(!Toolkits.isEmpty(htname)){
					String pka = htname.toString().substring(htname.toString().lastIndexOf("_")+1);
					setHeadValue("hpk", "pk_"+pka);
					setHeadValue("cancatfiel", "pk_"+pka);
					String firstpre = pka.substring(0, 1);
					setHeadValue("hvoname", "nc.vo.jyglgt."+nodecode.toString().toLowerCase()+"."+firstpre.toUpperCase()+pka.substring(1)+"VO");
				}
			}
			for (int i = 1; i <= maxbtableindex; i++) {
				String k = "";
				if(i != 1){
					k = i + "";
				}
				Object btname = getHeadValueObject("btablename"+k);
				if(!Toolkits.isEmpty(btname)){
					String pkb = btname.toString().substring(btname.toString().indexOf("_")+1);
					setHeadValue("bpk"+k, "pk_"+pkb);
					String firstpre = pkb.substring(0, 1);
					setHeadValue("bvoname"+k, "nc.vo.jyglgt."+nodecode.toString().toLowerCase()+"."+firstpre.toUpperCase()+pkb.substring(1).replace("_", "")+"VO");
				}
			}
			
		}
	}
	
	private void setHeadValue(String key, Object value) {
		getBillCardPanel().getHeadItem(key).setValue(value);
	}

	private Object getHeadValueObject(String str) {
		return getBillCardPanel().getHeadItem(str).getValueObject();
	}
	
}
