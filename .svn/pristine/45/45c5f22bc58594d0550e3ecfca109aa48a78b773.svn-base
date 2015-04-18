package nc.ui.jyglgt.j40068d;

import java.util.ArrayList;
import java.util.HashMap;
import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.jyglgt.billmanage.AbstractCtrl;
import nc.ui.jyglgt.billmanage.AbstractEventHandler;
import nc.ui.pub.bill.BillEditEvent;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;


/**
 * @功能：优惠结算
 */
public class ClientUI extends nc.ui.jyglgt.j40068c.ClientUI {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientUI() {
		super();
	}
	
	// 得到界面控制类
	protected AbstractCtrl createController() {
		return new ClientCtrl();
	}
	// 得到事件处理器类
	public AbstractEventHandler createEventHandler() {
		return new ClientEventHandler(this, this.getUIControl());
	}
	
	@Override
	public boolean beforeEdit(BillEditEvent e) {
		if(e.getTableCode().equals(Model2)&&e.getKey().equals("preferential")){
			Object isdeal = getBillCardPanel().getHeadItem("accounty").getValueObject();
			if(isdeal!=null){
				boolean flag= Boolean.valueOf(isdeal.toString());
				if(flag){
					showErrorMessage("已计算过的明细不允许再次修改优惠单价！");
					return false;
				}
			}
			
		}
		return true;
	}
	
	@Override
	public void afterEdit(BillEditEvent e) {
		super.afterEdit_yh(e);
		if(e.getKey().equals("specialyhjg")){
			int rowcount = getModelByName(Model2).getRowCount();
			if(rowcount<=0){getBillCardPanel().getHeadItem("specialyhjg").setValue(null);return;};
			Object value = getBillCardPanel().getHeadItem("specialyhjg").getValueObject();
			UFDouble specialyhjg = value==null?new UFDouble(0.00):new UFDouble(value.toString());
			String sql_pk_upper_b=" select max(pk_upper_b) pk_upper_b from jyglgt_balancelist_b where  vdef19='"+_getOperator()+"' group by poundcode,pk_measure_b";
			ArrayList<HashMap<String,String>> list_pk_upper_b;
			try {
				list_pk_upper_b = Proxy.getItf().queryArrayBySql(sql_pk_upper_b);
				A :for(int i=0;i<rowcount;i++){
					String pk_upper_b=getBillCardPanel().getBillModel(Model2).getValueAt(i, "pk_upper_b")==null?"":getBillCardPanel().getBillModel(Model2).getValueAt(i, "pk_upper_b").toString();
					if(list_pk_upper_b!=null&&list_pk_upper_b.size()>0){
						for(HashMap<String,String> map_pk_upper_b:list_pk_upper_b){
							if(map_pk_upper_b.get("pk_upper_b")!=null&&map_pk_upper_b.get("pk_upper_b").equals(pk_upper_b)){
								setbodyValueByModel(Model2, specialyhjg, i, "preferential");
								String[] formulas = new String[]{"preferentialmny->tonumber(preferential)*tonumber(weight)"};
								getBillCardPanel().getBillModel(Model2).execFormula(i,formulas);
								continue A;
							}
						}
					}
					setbodyValueByModel(Model2, new UFDouble(0), i, "preferential");
					String[] formulas = new String[]{"preferentialmny->tonumber(preferential)*tonumber(weight)"};
					getBillCardPanel().getBillModel(Model2).execFormula(i,formulas);
				}
			} catch (BusinessException e2) {
				e2.printStackTrace();
			}
		}else if(e.getKey().equals("vdef1")){
			int rowcount = getModelByName(Model1).getRowCount();
			if(rowcount<=0){getBillCardPanel().getHeadItem("vdef1").setValue(null);return;};
			Object value = getBillCardPanel().getHeadItem("vdef1").getValueObject();
			UFDouble specialyhjg = value==null?new UFDouble(0.00):new UFDouble(value.toString());
			UFDouble totalyhmny=value==null?new UFDouble(0.00):new UFDouble(value.toString());
			UFDouble tempmny=new UFDouble(0);
			UFDouble totalweight=new UFDouble(0);
			for(int i=0;i<rowcount;i++){
				UFDouble weight=getBillCardPanel().getBillModel(Model1).getValueAt(i, "suttle")==null?new UFDouble(0):new UFDouble(getBillCardPanel().getBillModel(Model1).getValueAt(i, "suttle").toString()); 
				totalweight = totalweight.add(weight);
			}
			for(int i=0;i<rowcount;i++){
				if(i==rowcount-1){
					specialyhjg=totalyhmny.sub(tempmny);
					setbodyValueByModel(Model1, specialyhjg, i, "preferentialmny");
					break;
				}
				UFDouble weight=getBillCardPanel().getBillModel(Model1).getValueAt(i, "suttle")==null?new UFDouble(0):new UFDouble(getBillCardPanel().getBillModel(Model1).getValueAt(i, "suttle").toString()); 
				UFDouble yhmny=new UFDouble(specialyhjg.multiply(weight.div(totalweight)).doubleValue(),2);
				setbodyValueByModel(Model1, yhmny, i, "preferentialmny");
				tempmny=tempmny.add(yhmny);
			}
		}
	}
	
	
}
