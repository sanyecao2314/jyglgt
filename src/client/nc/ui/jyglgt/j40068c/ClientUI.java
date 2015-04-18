package nc.ui.jyglgt.j40068c;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import nc.ui.jyglgt.billmanage.AbstractCtrl;
import nc.ui.jyglgt.billmanage.AbstractMultiChildClientUI;
import nc.ui.jyglgt.billmanage.CommonBusinessDelegator;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.gt.gs.gs11.BalanceInfoBVO;
import nc.vo.gt.gs.gs11.BalanceListBVO;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;




/**
 * @功能：销售结算
 */
public class ClientUI extends AbstractMultiChildClientUI {
	/**
	 * ID初始化
	 */
	private static final long serialVersionUID = 1L;
	protected String Model1 = "gt_balanceinfo_b";
	protected String Model2 = "gt_balancelist_b";
	
	public ClientUI() {
		super();
		getBillCardPanel().execHeadTailEditFormulas();
	}
	
	// 得到界面控制类
	protected AbstractCtrl createController() {
		return new ClientCtrl();
	}
	// 得到事件处理器类
	public ManageEventHandler createEventHandler() {
		return new ClientEventHandler(this, this.getUIControl());
	}
	@Override
	public void setDefaultData() throws Exception {
		super.setDefaultData();
		BillItem oper = getBillCardPanel().getTailItem("lastoperator");
	    if (oper != null){
	         oper.setValue(_getOperator());
	    }
	    BillItem date = getBillCardPanel().getTailItem("lastdate");
	    if (date != null){
	         date.setValue(_getDate());
	    }
		getBillCardPanel().setHeadItem("balancedate",_getDate());
		getBillCardPanel().setHeadItem("gwsdate",_getDate());
		getBillCardPanel().setHeadItem("gwedate",_getDate());
		//销售类型默认SO06,部门默认销售处 
//		String sql="select pk_busitype from bd_busitype where busiprop in(1,2) and nvl(dr,0)=0";
//		String sql_dep="select pk_deptdoc from bd_deptdoc where  nvl(dr,0)=0 and pk_corp='"+_getCorp().getPk_corp()+"'";
//		Object pk_busitype=Proxy.getIGTItf().queryStrBySql(sql);
//		Object pk_deptdoc=Proxy.getIGTItf().queryStrBySql(sql_dep);
        //setHeadValue("pk_busitype", pk_busitype);
        //setHeadValue("pk_deptdoc", pk_deptdoc);
		//end by shipeng 
	}

	@Override
	protected void initSelfData() {
		super.initSelfData();
		getBillCardPanel().getBillTable(Model1).addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
            	//第一页签选中行,匹配第二所有的明细行
                super.mousePressed(e);
                try{
                    int[] rows=getBillCardPanel().getBillTable(Model1).getSelectedRows();
                    for(int i=0;i<rows.length;i++){
                    	//得到选中行的行标
                    	Object a= getbodyValueByModel(Model1,rows[i], BalanceInfoBVO.PK_INVMANDOC);
                    	if(a==null||a.equals("")){
                    		continue;
                    	}else{
                    		//遍历第二个页签,改变对应行的颜色
                    		int rowcount2 = getModelByName(Model2).getRowCount();
            				for(int j=0;j<rowcount2;j++){
            					String b = getbodyValueByModel(Model2,j, BalanceListBVO.PK_INVMANDOC)==null?"":getbodyValueByModel(Model2,j,  BalanceListBVO.PK_INVMANDOC).toString();
            					getModelByName(Model2).getRowAttribute(j).clearCellBackColor();
            					if(a.equals(b)){
            						BillItem[] all = getModelByName(Model2).getBodyItems();
            						for(int k=0;k<all.length;k++)
            							getModelByName(Model2).setBackground(Color.PINK, j, k);
            					}
            				}
                    	}
                    }
                 }catch(Exception e1){}
                }
            }
         );
		//行操作只允许“删行”,其余全屏蔽 
//		ButtonObject[] btns = getButtonManager().getButton(IBillButton.Line).getChildButtonGroup();
//		for(int i=0;i<btns.length;i++){
//			if(!"删行".equals(btns[i].getName())){
//				getButtonManager().getButton(IBillButton.Line).removeChildButton(btns[i]);
//			}
//		}
	}

	
 	protected nc.ui.trade.bsdelegate.BusinessDelegator createBusinessDelegator() { 
 		return new CommonBusinessDelegator(this); 
 	} 

	@Override
	public void initButton(int id, String code, String name, int[] newStatus,
			int[] childButton) {
		super.initButton(id, code, name, newStatus, childButton);
	}
	
	public boolean beforeEdit(BillEditEvent e) {
		return super.beforeEdit(e);
	}
	
	//过滤产品用户
	public  void filterProductRef(BillEditEvent e,String key){
  		StringBuffer sb=new StringBuffer();
    	UIRefPane pane = (UIRefPane)getBillCardPanel().getHeadItem(key).getComponent();
    	if(e.getKey().equals("pk_cumandoc")&&pane!=null){
    		Object pk_cumandoc=getHeadValueObject("pk_cumandoc");
    		if(!Toolkits.isEmpty(pk_cumandoc)){
    			sb.append( " and so_sale.ccustomerid = '"+pk_cumandoc+"' "  );   		
    		}
    		pane.getRef().getRefModel().addWherePart(sb.toString(),true);
    	    pane.updateUI();
    	}
	}

	@Override
	public boolean beforeEdit(BillItemEvent e) {
		return super.beforeEdit(e);
	}

	@Override
	public void afterEdit(BillEditEvent e) {
		//过滤产品用户
		filterProductRef(e,"vdef9");
		filterProductRef(e,"preferentialtype");
		super.afterEdit(e);
	}
	
	public void afterEdit_yh(BillEditEvent e) {
		super.afterEdit(e);
	}
	
}
