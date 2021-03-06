package nc.ui.jyglgt.j400670;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.jyglgt.billmanage.AbstractEventHandler;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.pub.tools.BannerDialog;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fa.pub.bill.FAPfUtil;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.lang.UFTime;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * 名称: 销售价格政策EH类 
 */
public class ClientEventHandler extends AbstractEventHandler{

	String[] headKey=new String[]{"product","sdate","stime","edate","etime","memo"};
	String[] bodyKey=new String[]{"vinvcode","vlevel","free1_0","free1_1","free1_2",
			  "free2_0","free2_1","free2_2","free3_0","free3_1","free3_2","free4_0",
			  "free4_1","free4_2","free5","free6","free7","free8","free9","free10","vzkway","hsprice","memo"};
	public ClientEventHandler(BillManageUI arg0, IControllerBase arg1) {
		super(arg0, arg1);
	}
    //判断哪个按钮被触发
	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn); 
//		switch (intBtn) {
//		case IGTButton.BTN_ZJCH:
//			onBoStoreAdd(); 
//			return;
//		case IGTButton.BTN_XGJZDATE:
//			onBoUpdateDate(); 
//			return;
//		case IGTButton.BTN_AUTOOPEN:
////			onBoAutoOpen();
//			return;
//		}
	}
//	@Override
//	protected void onBoReturn() throws Exception {
//		super.onBoReturn();
//		super.onBoQuery();
//	}
	@Override
	protected void onBoCard() throws Exception {
		super.onBoCard();
		super.onBoRefresh();
		String product=Toolkits.getString(getHeadValue("product"));
		columnshow(product);
	}
	//复制按钮处理
	protected void onBoCopy() throws Exception {
		super.onBoCopy();
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("sdate").setValue(_getDate());
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("stime").setValue(getBillUI()._getServerTime().getTime());
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("edate").setValue(_getDate());
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("etime").setValue("23:59:59");
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("memo").setValue(null);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vbillcode").setValue(null);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vbillstatus").setValue(IJyglgtBillStatus.FREE);
		getBillCardPanelWrapper().getBillCardPanel().getTailItem("pk_operator").setValue(_getOperator());
		getBillCardPanelWrapper().getBillCardPanel().getTailItem("dmakedate").setValue(_getDate());
		getBillCardPanelWrapper().getBillCardPanel().getTailItem("vapproveid").setValue(null);
		getBillCardPanelWrapper().getBillCardPanel().getTailItem("vapprovedate").setValue(null);
		getBillCardPanelWrapper().getBillCardPanel().getTailItem("vunapproveid").setValue(null);
		getBillCardPanelWrapper().getBillCardPanel().getTailItem("vunapprovedate").setValue(null);
		for(int i=0;i<getRowCount();i++){
			setBodyValue(null, i, "memo");
			setBodyValue(null, i, "pk_deliveryplan_b");
		}
		String product=Toolkits.getString(getHeadValue("product"));
		columnshow(product);
		
//		PriceChangeDlg dlg=new PriceChangeDlg();
//		dlg.show();
//		UFDouble value=new UFDouble(dlg.getReturnValue());
//		if(dlg.isOk){
//			for(int i=0;i<getRowCount();i++){
//				UFDouble hsprice=getBillCardPanelWrapper().getBillCardPanel().getBodyValueAt(i, "hsprice")==null?new UFDouble(0):new UFDouble(String.valueOf(getBillCardPanelWrapper().getBillCardPanel().getBodyValueAt(i, "hsprice")));
//				setBodyValue(hsprice.add(value), i, "hsprice");
//			}
//	    }
	}

	//取消时对表头和表体数据的编辑设置
	protected void onBoCancel() throws Exception {
	    setAllItemEditTrue(headKey,bodyKey);
		super.onBoCancel();
	    showLine();
	}
	/**
	 * 增加存货按钮
	 * @throws Exception 
     * @author 施坤
     * 2011-11-12 
     */
	protected void onBoStoreAdd() throws Exception{
		int row = getRowCount();
		BillItem[] headitsb = getBillCardPanelWrapper().getBillCardPanel().getBodyItems();
		int colnum=getBillCardPanelWrapper().getBillCardPanel().getBodyItems().length;
		for(int i=0;i<row;i++){
			for(int j=0;j<colnum;j++){
				getBillCardPanelWrapper().getBillCardPanel().getBillModel().setBackground(Color.LIGHT_GRAY, i, j);
				getBillCardPanelWrapper().getBillCardPanel().getBillModel().setCellEditable(i, headitsb[j].getKey(), false);
			}
		}
		onBoEdit();
		super.onBoLineAdd();
		int rownum = getRowCount();
		Object obj=getHeadValue("product");
		if(!Toolkits.isEmpty(obj)){
			columnshow(obj.toString(),rownum-1);
		}
		setBodyValue(getOperName()+"于"+getBillUI()._getServerTime()+" 增加存货", rownum-1, "memo");
		BillItem[] headits = getBillCardPanelWrapper().getBillCardPanel().getHeadItems();
		for(int i=0;i<headits.length;i++){
			headits[i].setEdit(false);
		}
	}
	/** 修改截止日期时间按钮 */
	private void onBoUpdateDate() throws Exception{
		hidLine();
		onBoEdit();
		setAllItemEditFalse();
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("edate").setEdit(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("edate").getComponent().setBackground(Color.PINK);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("etime").setEdit(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("etime").getComponent().setBackground(Color.PINK);
	}
	/**
	 * 功能说明：根据开始日期和开始时间来自动审核当前单据，并且所有审核过的单据将被关闭，当前自由态单据状态变为审核态. 
	 * 当前单据已审核时，点击按钮提示，该单据已审核，操作无效。
	 * @author 朱天宝
	 * @throws Exception 
	 * @time 2010-11-8
	 * */
	private void onBoAutoOpen() throws Exception {
		AggregatedValueObject avo = getBufferData().getCurrentVO();
		if (avo == null)
			return;
		int result = getBillUI().showYesNoMessage("自动开启吗?");
		if (result == UIDialog.ID_YES) {
			getBillUI().showWarningMessage("系统已开启，不可以关闭当前界面，否则开启无效！");
			
			Runnable run = new Runnable(){
				public void run(){
	 				BannerDialog banner = new BannerDialog(getParent());			
					getParent().setCursor(new Cursor(Cursor.WAIT_CURSOR));
					banner.start();
					try {
						if(autoCheck()){
			    	        getBufferData().updateView();
		    	        	getBillUI().showOkCancelMessage("自动审核完成！");
						};
					} catch (Exception e) {
						e.printStackTrace();
						banner.end();
						getParent().setCursor(Cursor.getDefaultCursor());
						getBillUI().showErrorMessage("后台处理业务异常:"+e.getMessage());
					}finally{
						banner.end();
						getParent().setCursor(Cursor.getDefaultCursor());
					}	
					}
				};
				new Thread(run).start();
        }
			
	}
	/**
	 * 功能说明：审核按钮控制：所有审核过的单据将被关闭，当前自由态单据状态变为审核态. 
	 * 当前单据已审核时，点击按钮提示，该单据已审核，操作无效。
	 * @author 朱天宝
	 * @throws Exception 
	 * @time 2010-11-8
	 * */
	@SuppressWarnings("static-access")
//	public void onBoCheckPass() throws Exception {
//    	AggregatedValueObject avo=getBufferData().getCurrentVO();
//    	if(avo==null)return ;
//        int result=getBillUI().showYesNoMessage("确定此操作吗?");
//        if(result==UIDialog.ID_YES){
//	     // 当前自由态单据状态变为审核态。
//			if (getInteger(this.getBillCardPanelWrapper().getBillCardPanel()
//					.getHeadItem("vbillstatus").getValueObject()) == (IBillStatus.FREE)) {
//				UFDate date=_getDate();
//				UFTime time=new UFTime(ClientEnvironment.getInstance().getServerTime().getTime());
//				// 当前调用审核通过方法
//				onSelfBoCheckPass((SuperVO)avo.getParentVO(),date,time);
//				// 关闭所有审核过的单据
//				closeAllCust();
//				//更新上一张单据的结束日期等于当前单据的开始日期
//				updateLastDate((SuperVO)avo.getParentVO(),date,time);				
//			}
//			// 当前单据已审核时，点击按钮提示，该单据已审核，操作无效
//			else if (getInteger(this.getBillCardPanelWrapper()
//					.getBillCardPanel().getHeadItem("vbillstatus")
//					.getValueObject()) == (IBillStatus.CHECKPASS)) {
//				this.getBillUI().showErrorMessage("该单据已审核，操作无效！");
//				return;
//			}
//			getBufferData().updateView(); 
//			onBoRefresh();
//        }
//	}
	// 当前调用审核通过方法
	public void onSelfBoCheckPass(SuperVO vo, UFDate date, UFTime time) throws Exception {
		// 审核人
        vo.setAttributeValue("vapproveid" , _getOperator());
		// 审核日期
		vo.setAttributeValue("vapprovedate", _getDate());
		// 开始日期
		vo.setAttributeValue("sdate", date);
		// 开始时间
		vo.setAttributeValue("stime",time);
		// 单据状态
		vo.setAttributeValue("vbillstatus", IJyglgtBillStatus.CHECKPASS);
		Proxy.getInstance();
		Proxy.getItf().updateVO(vo);
        getBufferData().updateView(); 
	}
	//更新上一张单据的结束日期等于当前单据的开始日期
	@SuppressWarnings({ "unchecked"})
	private void updateLastDate(SuperVO vo, UFDate date, UFTime time) throws BusinessException{
       	String vbillcode =getHeadValue("vbillcode")==null?"":getHeadValue("vbillcode").toString();
       	String product =getHeadValue("product")==null?"":getHeadValue("product").toString();
    	String sql="select edate,etime,pk_pricepolicy from gt_pricepolicy where nvl(dr,0)=0 and product='"+product+"'  and pk_corp='"+_getCorp().getPk_corp()+"' ";
    	//如果有主键说明 当前是“修改”操作(修改时,此条判断要排除自身这条记录)
    	if(!vbillcode.equals("")){
    		sql += " and vbillcode <'" + vbillcode + "' ";
    	}
    	//加上order by
    	sql += " order by vbillcode desc ";
		Proxy.getInstance();
		ArrayList list =Proxy.getItf().queryArrayBySql(sql);
		String lastPk="";
		if(list==null||list.size()==0){return;}else{
			HashMap<String, String> map=(HashMap<String,String>)list.get(0);
			lastPk=map.get("pk_pricepolicy");
			if(!Toolkits.isEmpty(lastPk)){
				StringBuffer sql2=new StringBuffer();
				sql2.append(" update gt_pricepolicy")
				.append(" set edate='" +date+"',")
				.append(" etime='" + time+"'")
				.append(" where pk_pricepolicy = '"+lastPk+"'" );
				try {
					Proxy.getInstance();
					Proxy.getItf().updateBYsql(sql2.toString());
			        getBufferData().updateView(); 
				} catch (BusinessException e) {
					e.printStackTrace();
					showErrorMessage("审核异常:" + e.getMessage());
				}
			}
		}
	}	
	/**
	 * 功能说明：关闭所有审核过的单据
	 * @author 施坤
	 * @time 2011-11-13
	 * */
	public void closeAllCust() {
		Object  pkPrimay = getHeadValue("pk_pricepolicy");
       	String product =getHeadValue("product")==null?"":getHeadValue("product").toString();
		if(!Toolkits.isEmpty(pkPrimay)){
			StringBuffer sql=new StringBuffer();
			sql.append(" update gt_pricepolicy")
			.append(" set vbillstatus='" + IJyglgtBillStatus.CLOSE+"'")
			.append(" where vbillstatus = '" + IBillStatus.CHECKPASS+ "'" )
			.append(" and pk_pricepolicy != '" + pkPrimay + "' and product='"+product+"' and nvl(dr,0)=0 and pk_corp='"+_getCorp().getPk_corp()+"'");
			try {
				Proxy.getInstance();
				Proxy.getItf().updateBYsql(sql.toString());
		        getBufferData().updateView(); 
			} catch (BusinessException e) {
				e.printStackTrace();
				showErrorMessage("审核异常:" + e.getMessage());
			}
		}
	}
	//保存按钮的控制
	protected void onBoSave() throws Exception {
		int rows = getBillCardPanelWrapper().getBillCardPanel().getRowCount();
		if(upPKdown(rows)){return;}//下限和上限的比较
		if(isallPreRepeat(rows)){return;}//一个产品下的表体信息不可重复
		if(beginOverEnetime()){
        	showErrorMessage("开始时间不能高于截止时间！");
        	return;
		}else if(!beginOverLastEnetime()){
        	showErrorMessage("开始时间低于上一张单据开始时间！");
        	return;
		}
	    setAllItemEditTrue(headKey,bodyKey);
	    for(int i=0;i<getRowCount();i++){
	    	String pk_invmandoc=getBodyValue(i, "pk_invmandoc")==null?"":getBodyValue(i, "pk_invmandoc").toString();
	    	String pk_level=getBodyValue(i, "pk_level")==null?"":getBodyValue(i, "pk_level").toString();
	    	String sql=" select pk_invmandoc from bd_invmandoc where pk_invmandoc='"+pk_invmandoc+"' " +
	    			"    and wholemanaflag='Y' and nvl(dr,0)=0 and pk_corp='"+_getCorp().getPk_corp()+"' ";
	    	ArrayList list =Proxy.getItf().queryArrayBySql(sql);
	    	if(list!=null&&list.size()>0){
	    		if(pk_level.equals("")){
	    			showErrorMessage("第"+(i+1)+"行：质量等级为必输项！");
	    			return;
	    		}
	    	}
	    }
			super.onBoSave();
	}

	String[] vbodyKey=new String[]{"vareal","vinvcode","vlevel"};
	String[] vbodyKey0=new String[]{"free1_0","free2_0","free3_0","free4_0"};
	String[] vbodyKey1=new String[]{"free1_1","free2_1","free3_1","free4_1"};
	String[] vbodyKey2=new String[]{"free1_2","free2_2","free3_2","free4_2"};
	/**
	 * 下限和上限的比较
	 * */
	private boolean upPKdown(int rows) {
		for(int i=0;i<rows;i++){
			for(int j=0;j<4;j++){
				UFDouble fp0=Toolkits.getUFDouble(getBodyValue(i, vbodyKey0[j]));
				UFDouble fp1=Toolkits.getUFDouble(getBodyValue(i, vbodyKey1[j]));
				String fp2=Toolkits.getString(getBodyValue(i,vbodyKey2[j]) );
				if(fp0.doubleValue()!=0||fp1.doubleValue()!=0){
					
					if(fp0.doubleValue()>fp1.doubleValue()){
						showErrorMessage("第"+(i+1)+"行：下限值不可大于上限值！");
						return true;
					}
					
					if(fp2.equals("左包含")){
						if(fp0.doubleValue()==fp1.doubleValue()){
							showErrorMessage("第"+(i+1)+"行：左包含时下限值不可等于上限值！");
							return true;
						}
					}else if(fp2.equals("右包含")){
						if(fp0.doubleValue()==fp1.doubleValue()){
							showErrorMessage("第"+(i+1)+"行：右包含时下限值不可等于上限值！");
							return true;
						}
					}else if(fp2.equals("全不包含")){
						if(fp0.doubleValue()==fp1.doubleValue()){
							showErrorMessage("第"+(i+1)+"行：全不包含时下限值不可等于上限值！");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	/**
	 * 一个产品下的表体信息不可重复
	 * */
	private boolean isallPreRepeat(int rows) {
		Boolean flag=true;
		StringBuffer sb=new StringBuffer("");
		for(int i=0;i<rows-1;i++){
			for(int j=i+1;j<rows;j++){
				StringBuffer sql=new StringBuffer("");
				int xt=0;
				for(int lie=0;lie<vbodyKey.length;lie++){
					String ibody=Toolkits.getString(getBodyValue(i, vbodyKey[lie]));
					String jbody=Toolkits.getString(getBodyValue(j, vbodyKey[lie]));
					if(ibody.equals(jbody)){
						xt++;
					}
				}
				if(xt==vbodyKey.length){
					for(int d=0;d<vbodyKey1.length;d++){
						if(!duibifree(i,j,vbodyKey0[d],vbodyKey1[d],vbodyKey2[d])){
							if(sql.toString().equals("")){
							    sql.append("第"+(i+1)+"行与第"+(j+1)+"行的存货信息不符合要求;\n");
							}
						}else{
							sql=new StringBuffer("");
							break;
						}
					}
					sb.append(sql.toString());
				}
			}
		}
		if(!sb.toString().trim().equals("")){
			showErrorMessage(sb.toString()+" 请核实!");
		}else{
			flag=false;
		}
		return flag;
	}
	private boolean duibifree(int i,int j,String free0, String free1, String free2) {
		UFDouble fp0=Toolkits.getUFDouble(getBodyValue(i, free0));
		UFDouble fp1=Toolkits.getUFDouble(getBodyValue(i, free1));
		String fp2=Toolkits.getString(getBodyValue(i, free2));
		UFDouble fd0=Toolkits.getUFDouble(getBodyValue(j, free0));
		UFDouble fd1=Toolkits.getUFDouble(getBodyValue(j, free1));
		String fd2=Toolkits.getString(getBodyValue(j, free2));
		if((fp0.doubleValue()!=0&&fp1.doubleValue()!=0)&&(fd0.doubleValue()!=0&&fd1.doubleValue()!=0)){
			if(fp2.equals("全包含")&&fd2.equals("全包含")){//皆为全包含
				if(fp0.doubleValue()>fd1.doubleValue()||fd0.doubleValue()>fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("全包含")&&fd2.equals("左包含")){//上位全包含，下位左包含
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("全包含")&&fd2.equals("右包含")){//上位全包含，下位右包含
				if(fp0.doubleValue()>fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("全包含")&&fd2.equals("全不包含")){//上位全包含，下位全不包含
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("左包含")&&fd2.equals("全包含")){//上位左包含，下位全包含
				if(fp0.doubleValue()>fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("左包含")&&fd2.equals("右包含")){//上位左包含，下位右包含
				if(fp0.doubleValue()>fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("左包含")&&fd2.equals("左包含")){//上位左包含，下位左包含
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("左包含")&&fd2.equals("全不包含")){//上位左包含，下位全不包含
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("右包含")&&fd2.equals("全包含")){//上位右包含，下位全包含
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("右包含")&&fd2.equals("左包含")){//上位右包含，下位左包含
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("右包含")&&fd2.equals("右包含")){//上位右包含，下位右包含
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("右包含")&&fd2.equals("全不包含")){//上位右包含，下位全不包含
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("全不包含")&&fd2.equals("全包含")){//上位全不包含，下位全包含
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("全不包含")&&fd2.equals("左包含")){//上位全不包含，下位全包含
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("全不包含")&&fd2.equals("右包含")){//上位全不包含，下位全包含
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("全不包含")&&fd2.equals("全不包含")){//上位全不包含，下位全包含
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}
	//开始时间不能高于结束时间
	private boolean beginOverEnetime() throws ParseException{
		boolean isOver=false;			
		// 开始日期
		Object sdate = getHeadValue("sdate");
		// 开始时间
		Object stime = getHeadValue("stime");
		// 结束日期
		Object edate = getHeadValue("edate");
		// 结束时间
		Object etime = getHeadValue("etime");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 
		String str1 = sdate.toString() + " "+ stime.toString(); 
		String str2 = edate.toString() + " "+ etime.toString(); 
		Date dt1 = df.parse(str1); 
        Date dt2 = df.parse(str2); 
        if (dt1.getTime() > dt2.getTime()) { 
        	isOver=true;
        }
		return isOver;
	}
	//开始日期不能低于上一张单据开始日期
	@SuppressWarnings("unchecked")
	private boolean beginOverLastEnetime() throws BusinessException, ParseException{
		boolean isOver=true;		
		// 开始日期
		Object sdate = getHeadValue("sdate");
		if(!Toolkits.isEmpty(sdate)){
	       	String vbillcode =getHeadValue("vbillcode")==null?"":getHeadValue("vbillcode").toString();
	    	String sql="select sdate,stime,pk_pricepolicy from gt_pricepolicy where nvl(dr,0)=0  and pk_corp='"+_getCorp().getPk_corp()+"' ";
	    	//如果有主键说明 当前是“修改”操作(修改时,此条判断要排除自身这条记录)
	    	if(!vbillcode.equals("")){
	    		sql += " and vbillcode<'" + vbillcode + "' ";
	    	}
	    	//加上order by
	    	sql += " order by vbillcode desc ";
			Proxy.getInstance();
			ArrayList list =Proxy.getItf().queryArrayBySql(sql);
			if(list==null||list.size()==0){isOver=true;}else{
				HashMap<String, String> map=(HashMap<String,String>)list.get(0);
				String str = map.get("sdate");
		        if (new UFDate(sdate.toString()).before(new UFDate(str))) { 
		        	isOver=false;
		        }
			}
		}
		return isOver;
	}
	String sys_sdate=null;
	String sys_stime=null;
	AggregatedValueObject avo=null;
	/**
	 * 审核条件为获取当前界面的开始日期和开始时间在后台查询匹配的一条,于服务器时间相等时执行
	 * */
	private boolean  autoCheck() {
		avo=getBufferData().getCurrentVO();
		sys_sdate=getBillUI()._getDate().toString();
		sys_stime=getBillUI()._getServerTime().getTime().toString();  
		if(avo==null)return false;
		SuperVO vo=(SuperVO)avo.getParentVO();
		String sdate=vo.getAttributeValue("sdate")==null?"":vo.getAttributeValue("sdate").toString().trim();
		String stime=vo.getAttributeValue("stime")==null?"":vo.getAttributeValue("stime").toString().trim();
	    while(!sys_sdate.equalsIgnoreCase(sdate)||!sys_stime.equalsIgnoreCase(stime)){
			sys_sdate=getBillUI()._getDate().toString();
			sys_stime=getBillUI()._getServerTime().getTime().toString();  
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
	    }
		String pk_pricepolicy=vo.getAttributeValue("pk_pricepolicy")==null?"":vo.getAttributeValue("pk_pricepolicy").toString().trim();
	    if(sys_sdate.equalsIgnoreCase(sdate)&&sys_stime.equalsIgnoreCase(stime)&&!Toolkits.isEmpty(pk_pricepolicy)){
	    	onAudoBoCheckPass(pk_pricepolicy,vo);
	    	return true;
	    }else{
	    	return false;
	    }
	}
	/**
	 * 功能说明：自动审核当期，关闭之前的单据
	 * @param  String pk_price_zc
	 * @param  SuperVO avo
	 * @author 施鹏
	 * @throws Exception 
	 * @time 2010-11-9
	 * */
	@SuppressWarnings("unchecked")
	private void onAudoBoCheckPass(String pk_pricepolicy,SuperVO vo){
		try {
	        vo.setAttributeValue("vapproveid" , _getOperator());
			vo.setAttributeValue("vapprovedate", _getDate());
			vo.setAttributeValue("vbillstatus", IJyglgtBillStatus.CHECKPASS);
			Proxy.getInstance();
			Proxy.getItf().updateVO(vo);
			// 关闭所有审核过的单据
			StringBuffer sql_update=new StringBuffer();
			sql_update.append(" update gt_pricepolicy")
			.append(" set vbillstatus='" + IJyglgtBillStatus.CLOSE+"'")
			.append(" where vbillstatus = '" + IBillStatus.CHECKPASS+ "'" )
			.append(" and pk_pricepolicy != '" + pk_pricepolicy + "' and nvl(dr,0)=0 and pk_corp='"+_getCorp().getPk_corp()+"'");	
			Proxy.getInstance();
			Proxy.getItf().updateBYsql(sql_update.toString());
			//更新上一张单据的结束日期等于当前单据的开始日期
	     	String vbillcode =vo.getAttributeValue("vbillcode")==null?"":vo.getAttributeValue("vbillcode").toString();
	    	String sql="select edate,etime,pk_pricepolicy from gt_pricepolicy where nvl(dr,0)=0  and pk_corp='"+_getCorp().getPk_corp()+"' ";
	    	//如果有主键说明 当前是“修改”操作(修改时,此条判断要排除自身这条记录
	    	sql += " and vbillcode <'" + vbillcode + "' ";
	    	sql += " order by vbillcode desc ";
			Proxy.getInstance();
			ArrayList list =Proxy.getItf().queryArrayBySql(sql);
			String lastPk="";
			if(list==null||list.size()==0){return;}else{
				HashMap<String, String> map=(HashMap<String,String>)list.get(0);
				lastPk=map.get("pk_pricepolicy");
				if(!Toolkits.isEmpty(lastPk)){
					StringBuffer sql2=new StringBuffer();
					sql2.append(" update gt_pricepolicy")
					.append(" set edate='" + vo.getAttributeValue("sdate")+"'")
					.append(" where pk_pricepolicy = '" + lastPk + "'" );
					Proxy.getInstance();
					Proxy.getItf().updateBYsql(sql2.toString());
				}
			}
		}catch (BusinessException e) {
				e.printStackTrace();
		}
			
	}
    Container parent;
    public Container getParent() {
    	if(parent==null){
    		parent=new Container();
    	}
        return parent;
    }
    @Override
    protected void onBoLineAdd() throws Exception {
    	super.onBoLineAdd();
    	Integer vbillstatus=Toolkits.getInteger(getHeadValue("vbillstatus"));
    	if(vbillstatus==1){
    		setBodyValue(getOperName()+"于"+getBillUI()._getServerTime()+" 增加存货", getRowCount()-1, "memo");
        }
		Object obj=getHeadValue("product");
		if(!Toolkits.isEmpty(obj)){
			int row=getRowCount()-1;
			columnshow(obj.toString(),row);
		}
    }
    /**
     * 获取价格政策自由项对应的自由项列表编码；
     * */
	@SuppressWarnings("unchecked")
	private void columnshow(String product, int row) {
		String sql=" select b.free,b.listcode from gt_columnshow_b b " +
		"    left join gt_columnshow a on a.pk_columnshow=b.pk_columnshow" +
		"    where b.showflag='Y' and a.pk_productline='"+product+"' and nvl(a.dr,0)=0 and nvl(b.dr,0)=0 ";
		ArrayList al;
		try {
			al = Proxy.getItf().queryArrayBySql(sql.toString());
			if(al!=null&&al.size()>0){
				for(int i=0;i<al.size();i++){
					HashMap<String, String> hmp =(HashMap<String, String>)al.get(i);
					String vfree = Toolkits.getString(hmp.get("free"));
					String listcode = Toolkits.getString(hmp.get("listcode"));
					setlist(listcode,vfree,row);//向价格政策插入自由项列表编码
				}
			}
		}catch (BusinessException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 向价格政策插入自由项列表编码
	 * */
	private void setlist(String listcode, String vfree, int row) {
		if(vfree.equals("free1_0")||vfree.equals("free1_1")){
			setBodyValue(listcode, row, "list1_0");
			setBodyValue(listcode, row, "list1_1");
		}else if(vfree.equals("free2_0")||vfree.equals("free2_1")){
			setBodyValue(listcode, row, "list2_0");
			setBodyValue(listcode, row, "list2_1");
		}else if(vfree.equals("free3_0")||vfree.equals("free3_1")){
			setBodyValue(listcode, row, "list3_0");
			setBodyValue(listcode, row, "list3_1");
	    }else if(vfree.equals("free4_0")||vfree.equals("free4_1")){
			setBodyValue(listcode, row, "list4_0");
			setBodyValue(listcode, row, "list4_1");
	    }
	}
	@Override
	protected void onBoEdit() throws Exception {
		super.onBoEdit();
		String product=Toolkits.getString(getHeadValue("product"));
		columnshow(product);
	}
	/**
	 * 功能：根据产品线来设置表体字段的显示
	 * */
	@SuppressWarnings("unchecked")
	private void columnshow(String product) {
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
					getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().showTableCol(vfree);
					BillData bd = getBillCardPanelWrapper().getBillCardPanel().getBillData();
					BillItem item = bd.getBodyItem("gt_pricepolicy_b",vfree);
					item.setName(name);
					getBillCardPanelWrapper().getBillCardPanel().setBillData(bd);
				}
				for(int j=0;j<frees.length;j++){
					Boolean vflag=false;
					for(int x=0;x<vfrees.length;x++){
						if(frees[j].equals(vfrees[x])){
							vflag=true;
						}
					}
					if(!vflag){
						getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().hideTableCol(frees[j]);
					}
				}
			}else{
				for(int j=0;j<frees.length;j++){
					getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().hideTableCol(frees[j]);
				}
			}
		} catch (BusinessException e1) {
			e1.printStackTrace();
		}
	}
	
	public void onBoCheckPass() throws Exception {
		if (this.getBillUI().getBufferData() == null)
			return;

		AggregatedValueObject billvo = (HYBillVO) this.getBillUI().getBufferData().getCurrentVO();
		if (Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.COMMIT
				|| Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.CHECKGOING) {
			this.getBillCardPanelWrapper().getBillCardPanel()
					.dataNotNullValidate();
			billvo.getParentVO().setAttributeValue("vapproveid" , _getOperator());
			PfUtilClient.processActionFlow(this.getBillUI(),FAPfUtil.AUDIT, IJyglgtBillType.JY02, this._getDate().toString(), billvo,null);
			this.getBillUI().showHintMessage("审批完成...");

			super.onBoRefresh();
		} else if(Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.FREE){
			this.getBillUI().showWarningMessage("没有提交，不可以重复审核！");
			return;
		}else {
			this.getBillUI().showWarningMessage("不能重复审核！");
			return;
		}
	}


	protected void onBoCommit() throws Exception {
		if (this.getBillUI().getBufferData() == null)
			return;
		AggregatedValueObject billvo = (HYBillVO) this.getBillUI().getBufferData()
				.getCurrentVO();
		if(!Toolkits.getString(billvo.getParentVO().getAttributeValue("pk_operator")).equals(_getOperator())){
			this.getBillUI().showErrorMessage("提交失败，请确定是否有权限提交");
			return;
		}
		if (Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() != IJyglgtBillStatus.FREE
				&& Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() != IJyglgtBillStatus.NOPASS) {
			this.getBillUI().showWarningMessage("不能重复提交！");
			return;
		}
		billvo.getParentVO().setAttributeValue("vbillstatus", IJyglgtBillStatus.COMMIT);
			PfUtilClient.processAction(FAPfUtil.SAVE, IJyglgtBillType.JY02, this._getDate()
					.toString(), billvo, FAPfUtil.UPDATE_SAVE);

		this.getBillUI().showHintMessage("提交完成.....");
		super.onBoRefresh();
	}
	
	public void onBoCheckNoPass() throws Exception {
		for(int row=0;row<getRowCount();row++){
			String pk_deliveryplan_b=getBodyValue(row, "pk_deliveryplan_b")==null?"":getBodyValue(row, "pk_deliveryplan_b").toString();
	    	//判断下游单据是否使用
	    	if(!pk_deliveryplan_b.equals("")){
	    		showErrorMessage("已被下游单据使用，不可以弃审！");
	    		return ;
	    	}
		}
		if (this.getBillUI().getBufferData() == null)
			return;

		AggregatedValueObject billvo = (HYBillVO) this.getBillUI().getBufferData().getCurrentVO();
		if (Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.CHECKPASS
				|| Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.CHECKGOING
				|| Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.NOPASS) {
//			if(!Toolkits.getString(billvo.getParentVO().getAttributeValue("vapproveid")).equals(_getOperator())){
//			this.getBillUI().showErrorMessage("非审核人本人，不可以弃审");
//			return;
//		}
		billvo.getParentVO().setAttributeValue("vapproveid" , _getOperator());
		billvo.getParentVO().setAttributeValue("vunapproveid" , _getOperator());
		billvo.getParentVO().setAttributeValue("vunapprovedate" , _getDate());
//		billvo.getParentVO().setAttributeValue("vbillstatus" , IJyglgtBillStatus.FREE);
		billvo.getParentVO().setAttributeValue("dapprovedate" , null);

			PfUtilClient.processActionFlow(this.getBillUI(),FAPfUtil.UNAUDIT, IJyglgtBillType.JY02, this._getDate().toString(), billvo,null);
			this.getBillUI().showHintMessage("弃审完成...");

			super.onBoRefresh();
		} else if(Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.FREE){
			this.getBillUI().showWarningMessage("制单人状态，不需要弃审！");
			return;
		}else {
			this.getBillUI().showWarningMessage("不能重复审核！");
			return;
		}
      }
	
	@Override
	protected StringBuffer getWhere() {
		StringBuffer sb = new StringBuffer();
		sb.append(" billtype='"+getUIController().getBillType()+"' and ");
		return sb;
	}
}
