package nc.ui.jyglgt.j400670;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.jyglgt.billmanage.AbstractClientUI;
import nc.ui.jyglgt.billmanage.AbstractCtrl;
import nc.ui.jyglgt.billmanage.AbstractEventHandler;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;

/**
 * 名称: 销售价格政策UI类 
 */
public class ClientUI extends AbstractClientUI {
	/**
	 * 序列版本号
	 */
	private static final long serialVersionUID = 1L;

	public ClientUI() {
		super();
	}
	// 自定义按钮注册
	public void initPrivateButton() {
		super.initPrivateButton();
//		initButton(IGTButton.BTN_UPDATEBNT, "修改按钮组", "修改按钮组",
//				new int[] { nc.ui.trade.base.IBillOperate.OP_NOTEDIT }, 
//				new int[] {IGTButton.BTN_ZJCH, IGTButton.BTN_XGJZDATE });
//		initButton(IGTButton.BTN_ASSIST_QUERY_ONHAND,"现存量查询","现存量查询",
//        		new int[] { nc.ui.trade.base.IBillOperate.OP_NO_ADDANDEDIT},null);
	}
	// 得到界面控制类
	protected AbstractCtrl createController() {
		return new ClientCtrl();
	}
	// 得到事件处理器类
	public AbstractEventHandler createEventHandler() {
		return new ClientEventHandler(this, getUIControl());
	}
	//新增
	public void setDefaultData() throws Exception {
		super.setDefaultData();
		getBillCardPanel().getHeadItem("pk_corp").setValue(_getCorp().getPk_corp());
//		//获取价格标准
//		String sql="select docname from bd_defdoc where pk_defdoc in(select def1 from bd_corp where pk_corp='"+_getCorp().getPk_corp()+"')"; 
//        Proxy.getInstance();
//		Object pricebz=((IGTITF)Proxy.getIGTItf()).queryStrBySql(sql);
//        if(!Toolkits.isEmpty(pricebz)){
//        	getBillCardPanel().getHeadItem("pricebz").setValue(pricebz);
//        }
        BillItem oper = getBillCardPanel().getTailItem("lastoperator");
        if (oper != null){
            oper.setValue(_getOperator());
        }
        BillItem date = getBillCardPanel().getTailItem("lastdate");
        if (date != null){
            date.setValue(_getDate());
        }
		getBillCardPanel().getHeadItem("sdate").setValue(_getDate());
		getBillCardPanel().getHeadItem("stime").setValue(_gettime());
		getBillCardPanel().getHeadItem("edate").setValue(_getDate());
		getBillCardPanel().setHeadItem("etime", "23:59:59");
		getBillCardPanel().execHeadLoadFormulas();
	}
	//获得当前时间
	private String _gettime() {
		String str = new SimpleDateFormat("HH:mm:ss").format(new Date());
		return str;
	}
	@Override
	public void afterEdit(BillEditEvent e) {
		super.afterEdit(e);
		String[] free0={"free1_0","free2_0","free3_0","free4_0"};
		String[] pkfree0={"pk_free1_0","pk_free2_0","pk_free3_0","pk_free4_0"};
		String[] free1={"free1_1","free2_1","free3_1","free4_1"};
		String[] pkfree1={"pk_free1_1","pk_free2_1","pk_free3_1","pk_free4_1"};
		for(int i=0;i<free0.length;i++){
		    free0TOFree1(e,free0[i],pkfree0[i],pkfree1[i],free1[i]);//由自定义下限带出自定义上限
		}
		if(e.getKey().equals("product")){//根据产品线来设置表体字段的显示
			delBodyValues();//清空表体
			Object obj=getHeadValueObject("product");
			if(!Toolkits.isEmpty(obj)){
				columnshow(e,obj.toString());
			}
		}
		//含税调整单价
		else if(e.getKey().equals("vdef10")){
			UFDouble vdef10=getHeadValueObject("vdef10")==null?new UFDouble(0):new UFDouble(getHeadValueObject("vdef10").toString());
			if(vdef10.doubleValue()!=0){
				TZhsprice(vdef10);
			}
		}
		//含税调整单价
		else if(e.getKey().equals("vdef1")){
			UFDouble vdef1=getHeadValueObject("vdef1")==null?new UFDouble(0):new UFDouble(getHeadValueObject("vdef1").toString());
			if(vdef1.doubleValue()!=0){
				TZwsprice(vdef1);
			}
		}
		//时间控制
//		ctrTime(e);
	}
	/**
	 * 调整单价
	 * */
	private void TZhsprice(UFDouble vdef10) {
		for(int i=0;i<getBillCardPanel().getRowCount();i++){
			UFDouble hsprice=getBodyValue("hsprice", i)==null?new UFDouble(0):new UFDouble(getBodyValue("hsprice", i).toString());
			UFDouble tzhsprice=hsprice.add(vdef10);
			if(tzhsprice.doubleValue()>0){
				getBillCardPanel().getBillModel("gt_pricepolicy_b").setRowState(i, BillModel.MODIFICATION);
				setBodyValue(tzhsprice, i, "hsprice");
			}
		}
		String memo=getHeadValueObject("memo")==null?"":getHeadValueObject("memo").toString();
		if(memo.equals("")){
			setHeadValue("memo", ""+_getServerTime()+"调整单价'"+Toolkits.cutZero(Toolkits.getString(vdef10))+"'成功！");
		}else{
		    setHeadValue("memo", ""+memo+"\n"+_getServerTime()+"调整单价'"+Toolkits.cutZero(Toolkits.getString(vdef10))+"'成功！");
		}
		setHeadValue("vdef10", null);
		showHintMessage("调整单价'"+Toolkits.cutZero(Toolkits.getString(vdef10))+"'成功！");
	}
	
	/**
	 * 无税调整单价
	 * */
	private void TZwsprice(UFDouble vdef1) {
		for(int i=0;i<getBillCardPanel().getRowCount();i++){
			UFDouble hsprice=getBodyValue("vdef1", i)==null?new UFDouble(0):new UFDouble(getBodyValue("vdef1", i).toString());
			UFDouble tzhsprice=hsprice.add(vdef1);
			if(tzhsprice.doubleValue()>0){
				getBillCardPanel().getBillModel("gt_pricepolicy_b").setRowState(i, BillModel.MODIFICATION);
				setBodyValue(tzhsprice, i, "vdef1");
			}
		}
		String memo=getHeadValueObject("memo")==null?"":getHeadValueObject("memo").toString();
		if(memo.equals("")){
			setHeadValue("memo", ""+_getServerTime()+"调整无税单价'"+Toolkits.cutZero(Toolkits.getString(vdef1))+"'成功！");
		}else{
		    setHeadValue("memo", ""+memo+"\n"+_getServerTime()+"调整无税单价'"+Toolkits.cutZero(Toolkits.getString(vdef1))+"'成功！");
		}
		setHeadValue("vdef1", null);
		showHintMessage("调整无税单价'"+Toolkits.cutZero(Toolkits.getString(vdef1))+"'成功！");
	}
	/**
	 * 由自定义下限带出自定义上限
	 * */
	private void free0TOFree1(BillEditEvent e, String free0, String pkfree0,
			String pkfree1, String free1) {
		if(e.getKey().equals(free0)){
			Object pk=getBodyValue(pkfree0, e.getRow());
			if(!Toolkits.isEmpty(pk)){
				setBodyValue(pk.toString(), e.getRow(), pkfree1);
				getBillCardPanel().execBodyFormula(e.getRow(), free1);
			}
		}
	}
	/**
	 * 功能：根据产品线来设置表体字段的显示
	 * */
	@SuppressWarnings("unchecked")
	private void columnshow(BillEditEvent e, String product) {
		String sql=" select b.free,b.name from gt_columnshow_b b " +
				"    left join gt_columnshow a on a.pk_columnshow=b.pk_columnshow" +
				"    where b.showflag='Y' and a.pk_productline='"+product+"' and nvl(a.dr,0)=0 and nvl(b.dr,0)=0 ";
		ArrayList al;
		try {
			String[] frees={"free1_0","free1_1","free1_2","free2_0","free2_1","free2_2","free3_0","free3_1","free3_2","free4_0","free4_1","free4_2","free5","free6","free7","free8","free9","free10"};
			String[] vfrees=new String[18];
			al = Proxy.getItf().queryArrayBySql(sql.toString());
			if(al!=null&&al.size()>0){
				for(int i=0;i<al.size();i++){
					HashMap<String, String> hmp =(HashMap<String, String>)al.get(i);
					String vfree = Toolkits.getString(hmp.get("free"));
					vfrees[i]=vfree;
					String name = Toolkits.getString(hmp.get("name"));
					getBillCardPanel().getBodyPanel().showTableCol(vfree);
					BillData bd = getBillCardPanel().getBillData();
					BillItem item = bd.getBodyItem("gt_pricepolicy_b",vfree);
					item.setName(name);
					getBillCardPanel().setBillData(bd);
				}
				for(int j=0;j<frees.length;j++){
					Boolean vflag=false;
					for(int x=0;x<vfrees.length;x++){
						if(frees[j].equals(vfrees[x])){
							vflag=true;
						}
					}
					if(!vflag){
						getBillCardPanel().getBodyPanel().hideTableCol(frees[j]);
					}
				}
			}else{
//				showHintMessage("请确定该产品线没有要显示的字段！");
				for(int j=0;j<frees.length;j++){
					getBillCardPanel().getBodyPanel().hideTableCol(frees[j]);
				}
			}
		} catch (BusinessException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * 功能：时间控制
	 * */
	private void ctrTime(BillEditEvent e){
		if(e.getKey().equals("stime")){
			Object stime=getHeadValueObject("stime");
			if(!Toolkits.isEmpty(stime)){setHeadValue("stime", getTime(stime));
			}
		}
		if(e.getKey().equals("etime")){
			Object etime=getHeadValueObject("etime");
			if(!Toolkits.isEmpty(etime)){setHeadValue("etime", getTime(etime));}
		}
	}
	
	private String getTime(Object time){	
		String str="";
		Pattern p =Pattern.compile("([0-1]?[0-9]|2[0-3])([0-5][0-9]){2}");
		Pattern p1 =Pattern.compile("([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])");
		if(time.toString().length()<6){
			showWarningMessage("请输入6位符合时间的数字");
			return str;
		}
		 Matcher m = p.matcher(time.toString().substring(0, 6));
		 Matcher m1 = p1.matcher(time.toString());
		 if(m.matches()){
			 str=(time.toString().substring(0, 2)+":"+time.toString().substring(2, 4)+":"+time.toString().substring(4, 6));		 
		 }else if(m1.matches()){
			 str=time.toString();
		 }else{
			 showWarningMessage("请输入6位符合时间的数字");
			 return str;
		 }
		 return str;
	}
	
	public boolean beforeEdit(BillEditEvent e) {
		beforevfree(e);//自由项编辑前的参照控制
		beforevinvcode(e);//存货编辑前参照控制
		return super.beforeEdit(e);
	}
	/**
	 * 功能：存货编辑前参照控制
	 * */
	private void beforevinvcode(BillEditEvent e) {
		if(e.getKey().equals("vinvcode")){
			StringBuffer sb=new StringBuffer();
	        UIRefPane pane = (UIRefPane)getBillCardPanel().getBodyItem("vinvcode").getComponent();
	        if(pane!=null){
	        	String productline=Toolkits.getString(getHeadValueObject("product"));
	        	sb.append(" and bd_invbasdoc.pk_prodline='"+productline+"' ");
	    		pane.getRef().getRefModel().addWherePart(sb.toString(),true);
	    	}
	    	pane.updateUI();
			
		}
	}
	/**
	 * 功能：自由项编辑前的参照控制
	 * */
    private void beforevfree(BillEditEvent e) {
		Object obj=getHeadValueObject("product");
		if(!Toolkits.isEmpty(obj)){
			String[] vfree={"free1_0","free1_1","free2_0","free2_1","free3_0","free3_1","free4_0","free4_1"};
			String[] listcode={"list1_0","list1_1","list2_0","list2_1","list3_0","list3_1","list4_0","list4_1"};
			for(int i=0;i<vfree.length;i++){
				String licode=Toolkits.getString(getBodyValue(listcode[i], e.getRow()));
				freeRef(e,vfree[i],licode);
			}
		}else{
			showErrorMessage("请先输入产品线！");
			return;
		}
	}
	private void freeRef(BillEditEvent e, String vfree, String licode) {
		if(e.getKey().equals(vfree)){
				StringBuffer sb=new StringBuffer();
		        UIRefPane pane = (UIRefPane)getBillCardPanel().getBodyItem(vfree).getComponent();
		        if(pane!=null){
		        	sb.append(" and b.doclistcode='"+licode+"' ");
		    		pane.getRef().getRefModel().addWherePart(sb.toString(),true);
		    	}
		    	pane.updateUI();
		}
	}
}
