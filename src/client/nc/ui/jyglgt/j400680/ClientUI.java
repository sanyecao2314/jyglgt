package nc.ui.jyglgt.j400680;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.jyglgt.billmanage.AbstractClientUI;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.business.HYPubBO_Client;
import nc.ui.trade.manage.ManageEventHandler;
import nc.uif.pub.exception.UifException;
import nc.vo.jyglgt.j400680.BalanceWayVO;
import nc.vo.jyglgt.j400680.CreditVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;

/**
 *销售收款通知单
 */

public class ClientUI extends AbstractClientUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] skkey = new String[]{"deposit","tkmny"};
	String[] tkkey = new String[]{"skmny","tkmny"};
	String[] bzjkey = new String[]{"skmny","tkmny"};
	
	@Override
	public void setDefaultData() throws Exception {
		super.setDefaultData();
		setHeadValue("receivemnydate", _getDate());
		getBillCardPanel().getHeadItem("deposit").setEdit(false);//update by cm 2012-2-26
		getBillCardPanel().setHeadItem("priceway", null);
	}
	
	@Override
	protected AbstractManageController createController() {
		return new ClientCtrl();
	}

	@Override
	public ManageEventHandler createEventHandler() {
		return new ClientEventHandler(this, this.getUIControl());
	}
	
	@Override
	public void afterEdit(BillEditEvent e) {
		super.afterEdit(e);
		if(e.getKey().equals("skmny")){
			UFDouble skmny = getHeadValueObject("skmny")==null?new UFDouble(0):new UFDouble(getHeadValueObject("skmny").toString());
			String receivetype = getHeadValueObject("receivetype")==null?"":getHeadValueObject("receivetype").toString();
//			if(receivetype.equals(IJyglgtConst.RECEIVETYPE_TK)&&skmny.doubleValue()>0){
//				   showErrorMessage("收款类型为退款时不容许录入正值");
//				   getBillCardPanel().setHeadItem("skmny", new UFDouble(0));
//				   return;
//		    }else if(receivetype.equals(IJyglgtConst.RECEIVETYPE_SK)&&skmny.doubleValue()<0){
//				   showErrorMessage("收款类型为收款通知时不容许录入负值");
//				   getBillCardPanel().setHeadItem("skmny", new UFDouble(0));
//				   return;
//		    }
			UFDouble deposit = getHeadValueObject("deposit")==null?new UFDouble(0):new UFDouble(getHeadValueObject("deposit").toString());			
			getBillCardPanel().setHeadItem("bcskmny", deposit.add(skmny));
		}else if(e.getKey().equals("tkmny")){
			UFDouble tkmny = getHeadValueObject("tkmny")==null?new UFDouble(0):new UFDouble(getHeadValueObject("tkmny").toString());
			getBillCardPanel().setHeadItem("bcskmny", tkmny);
		}else if(e.getKey().equals("deposit")){
			UFDouble deposit = getHeadValueObject("deposit")==null?new UFDouble(0):new UFDouble(getHeadValueObject("deposit").toString());
			UFDouble skmny = getHeadValueObject("skmny")==null?new UFDouble(0):new UFDouble(getHeadValueObject("skmny").toString());
			String receivetype = getHeadValueObject("receivetype")==null?"":getHeadValueObject("receivetype").toString();
			if(deposit.doubleValue()<0){
				   showErrorMessage("保证金不容许录入负值");
				   getBillCardPanel().setHeadItem("deposit", null);
				   return;
		    }
//			if(deposit.doubleValue()==0&&receivetype.equals(IJyglgtConst.RECEIVETYPE_DJ)){
//				   showErrorMessage("保证金不容许录入零");
//				   getBillCardPanel().setHeadItem("deposit", null);
//				   return;
//		    }
			getBillCardPanel().setHeadItem("bcskmny", deposit.add(skmny));
		}else if(e.getKey().equals("moneyy")){
			UFDouble moneyy=new UFDouble(0);
			int row=getBillCardPanel().getRowCount();			
			for(int i=0;i<row;i++){
				UFDouble ufd=getBillCardPanel().getBodyValueAt(i, "moneyy")==null?new UFDouble(0):new UFDouble(getBillCardPanel().getBodyValueAt(i, "moneyy").toString());
				moneyy=moneyy.add(ufd);
				String receivetype = getHeadValueObject("receivetype")==null?"":getHeadValueObject("receivetype").toString();
//				if(receivetype.equals(IJyglgtConst.RECEIVETYPE_SK)){
//					getBillCardPanel().setHeadItem("skmny", moneyy);
//					getBillCardPanel().setHeadItem("bcskmny", moneyy);
//				}if(receivetype.equals(IJyglgtConst.RECEIVETYPE_TK)){
//					if(moneyy.doubleValue()>0){
//						showErrorMessage("收款类型为退款时不容许录入正值");
//					}
//					getBillCardPanel().setHeadItem("skmny", moneyy);
//					getBillCardPanel().setHeadItem("tkmny", new UFDouble(0));
//					getBillCardPanel().setHeadItem("bcskmny", moneyy);
//				}if(receivetype.equals(IJyglgtConst.RECEIVETYPE_DJ)){
//					getBillCardPanel().setHeadItem("deposit", moneyy);
//					getBillCardPanel().setHeadItem("skmny", new UFDouble(0));
//					getBillCardPanel().setHeadItem("bcskmny", moneyy);
//				}
			}
		}
		else if(e.getKey().equals("isreceipt")){
			String receivetype = getHeadValueObject("receivetype")==null?"":getHeadValueObject("receivetype").toString();
			UFBoolean isreceipt = getHeadValueObject("isreceipt")==null?new UFBoolean(false):new UFBoolean(getHeadValueObject("isreceipt").toString());
			//期初、赊欠、其他不传资金部
			if(isreceipt.booleanValue()&&(receivetype.equals(IJyglgtConst.RECEIVETYPE_QC)||receivetype.equals(IJyglgtConst.RECEIVETYPE_SX)||receivetype.equals(IJyglgtConst.RECEIVETYPE_QT))){
			   showErrorMessage("当前收款类型不可传资金部！");
			   getBillCardPanel().getHeadItem("isreceipt").setValue(new UFBoolean(false));
			   return;
	        }
		}
		else if(e.getKey().equals("pk_receiveway")){
			String receivetype = getHeadValueObject("receivetype")==null?"":getHeadValueObject("receivetype").toString();
			String pk_receiveway = getHeadValueObject("pk_receiveway")==null?"":getHeadValueObject("pk_receiveway").toString();
			//赊欠：收款方式，必须是现汇
			if(receivetype.equals(IJyglgtConst.RECEIVETYPE_SX)&&!"现汇".equals(pk_receiveway)){
			   showWarningMessage("赊欠：收款方式，必须是现汇！");
			   getBillCardPanel().getHeadItem("pk_receiveway").setValue("现汇");
			   return;
	        }
		}
		//根据收款类型，编辑课操作的项目，更换收款类型情况所有的金额
		if(e.getKey().equals("receivetype")){
			int receivetype_int = (Integer) getBillCardPanel().getHeadItem("receivetype").getValueObject();
			if(receivetype_int!=2){
				getBillCardPanel().getHeadItem("pk_vbillcode_tk").setEnabled(false);//.setEdit(false);
				getBillCardPanel().setHeadItem("pk_vbillcode_tk", null);
			}
			String receivetype = getHeadValueObject("receivetype")==null?"":getHeadValueObject("receivetype").toString();
			//期初、赊欠、其他不传资金部
			if(receivetype.equals(IJyglgtConst.RECEIVETYPE_QC)||receivetype.equals(IJyglgtConst.RECEIVETYPE_SX)||receivetype.equals(IJyglgtConst.RECEIVETYPE_QT)){
			   getBillCardPanel().getHeadItem("isreceipt").setValue(new UFBoolean(false));
	        }else{
	        	getBillCardPanel().getHeadItem("isreceipt").setValue(new UFBoolean(true));
	        }
			//赊欠：收款方式，必须是现汇
			if(receivetype.equals(IJyglgtConst.RECEIVETYPE_SX)){
				getBillCardPanel().getHeadItem("pk_receiveway").setValue("现汇");
			}else{
				getBillCardPanel().getHeadItem("pk_receiveway").setValue(null);
			}
			//不可以选择收款类型为期初收款、赊销、弥补的收款类型  add by shipeng at 2011-8-3
//			if(receivetype.equals(IJyglgtConst.RECEIVETYPE_QC)||receivetype.equals(IJyglgtConst.RECEIVETYPE_SX)||receivetype.equals(IJyglgtConst.RECEIVETYPE_MB)){
//				   showErrorMessage("当前收款类型不可用，请选择其他收款类型！");
//				   getBillCardPanel().getHeadItem("receivetype").setValue(IJyglgtConst.RECEIVETYPE_SK);
//				   return;
//		    }
//			if(receivetype.equals(IJyglgtConst.RECEIVETYPE_TK)){
//				//当收款类型为退款时 本次交款金额=收款金额  收款金额=退款金额 退款金额设置为0
//				getBillCardPanel().setHeadItem("deposit", null);
//				getBillCardPanel().getHeadItem("deposit").setEdit(false);
//				getBillCardPanel().getHeadItem("pk_vbillcode_tk").setEnabled(true);
//				UFDouble skmny=getBillCardPanel().getHeadItem("skmny").getValueObject()==null?new UFDouble(0):new UFDouble(getBillCardPanel().getHeadItem("skmny").getValueObject().toString());
//				if(skmny.doubleValue()>0){
//					   showErrorMessage("收款类型为退款时不容许录正值");
//					   getBillCardPanel().setHeadItem("skmny", new UFDouble(0));
//					   return;
//			    }
//				setHeadValue("bcskmny",skmny);
//				setHeadValue("tkmny",0);
//				getBillCardPanel().getHeadItem("skmny").setEnabled(true);
//			}//end by shipeng
//			else if(receivetype.equals(IJyglgtConst.RECEIVETYPE_DJ)){
//				getBillCardPanel().getHeadItem("tkmny").setEdit(false);
//				getBillCardPanel().getHeadItem("deposit").setEdit(true);
//				getBillCardPanel().getHeadItem("skmny").setEdit(false);
//				getBillCardPanel().setHeadItem("deposit", null);
//				getBillCardPanel().setHeadItem("skmny", null);
//				getBillCardPanel().setHeadItem("bcskmny", null);
//				getBillCardPanel().setHeadItem("tkmny", null);
//				
//			}else if(receivetype.equals(IJyglgtConst.RECEIVETYPE_SK)){
//				getBillCardPanel().getHeadItem("tkmny").setEdit(false);
////				getBillCardPanel().getHeadItem("deposit").setEdit(true);
//				getBillCardPanel().getHeadItem("deposit").setEdit(false);//add by cm 2021-2-26
//				getBillCardPanel().setHeadItem("deposit","");
//				getBillCardPanel().getHeadItem("skmny").setEdit(true);
//				getBillCardPanel().setHeadItem("skmny", null);
//				getBillCardPanel().setHeadItem("bcskmny", null);
//				getBillCardPanel().setHeadItem("tkmny", null);
//			}
		}
	else if(e.getKey().equals("pk_basinfo")){
			Object pk_basinfo = getBodyValue("pk_basinfo", e.getRow());
			if(!Toolkits.isEmpty(pk_basinfo)){
				try {
					boolean flag = Proxy.getItf().existValue("fbm_baseinfo", "fbmbillno", pk_basinfo.toString());
					if(flag){
						showErrorMessage("该票据编号已经存在!");
						getBillCardPanel().setBodyValueAt(null, e.getRow(), "pk_basinfo");
						return;
					}
				} catch (BusinessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
		else if(e.getKey().equals("vreceivename")){
			Object vreceivename = getBodyValue("vreceivename", e.getRow());
			Object vpayname = getBodyValue("vpayname", e.getRow());
			if(!Toolkits.isEmpty(vpayname)){
					if(vreceivename.equals(vpayname)){
						showErrorMessage("收款单位和付款单位相同!");
						getBillCardPanel().setBodyValueAt(null, e.getRow(), "vreceivename");
						return;
					}			
			}	
	}
		else if(e.getKey().equals("vpayname")){
			Object vreceivename = getBodyValue("vreceivename", e.getRow());
			Object vpayname = getBodyValue("vpayname", e.getRow());
			if(!Toolkits.isEmpty(vreceivename)){
					if(vpayname.equals(vreceivename)){
						showErrorMessage("收款单位和付款单位相同!");
						getBillCardPanel().setBodyValueAt(null, e.getRow(), "vpayname");
						return;
					}			
			}
	}
		else if(e.getKey().equals("invoicedate")){
			Object invoicedate = getBodyValue("invoicedate", e.getRow());
			Object enddate = getBodyValue("enddate", e.getRow());
			if(!Toolkits.isEmpty(enddate)){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String str1 = invoicedate.toString();
			String str2 = enddate.toString();
			try {
				Date dt1 = df.parse(str1);
				Date dt2 = df.parse(str2);
				if (dt1.getTime() > dt2.getTime()) {
					showErrorMessage("出票日期大于到票日期!");
					getBillCardPanel().setBodyValueAt(null, e.getRow(), "invoicedate");
					return;
				}			
			} catch (ParseException e1) {
				e1.printStackTrace();
			}			
			}	
			if(!Toolkits.isEmpty(invoicedate)){
//				UFDate invoicedatea = (UFDate) getBodyValue("invoicedate", e.getRow());
//                int a = invoicedatea.getYear();
//                int b = invoicedatea.getMonth();
////              int c = invoicedatea.getDay();               
//                int d = b+6;
//                if(d>12){
//                	a = a+1;
//                	b = b+6-12;
//                }else{
//                	b = b+6;
//                }
//                String aa = ""+a;
//                String bb = ""+b;
//                String cc = ""+25;
//                String dd = "-";
//                String str = aa+dd+bb+dd+cc;
//                getBillCardPanel().setBodyValueAt(str, e.getRow(), "enddate");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String str1 = invoicedate.toString();
				Date dt1;
				try {
					dt1 = df.parse(str1);
					Date dt = addMonths(dt1, 6);
					String str = df.format(dt);
					getBillCardPanel().setBodyValueAt(str, e.getRow(), "enddate");
				} catch (ParseException e1) {
					e1.printStackTrace();
				}				   				
			}		
		}
		else if(e.getKey().equals("enddate")){
			Object invoicedate = getBodyValue("invoicedate", e.getRow());
			Object enddate = getBodyValue("enddate", e.getRow());
			if(!Toolkits.isEmpty(invoicedate)){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String str1 = invoicedate.toString();
			String str2 = enddate.toString();
			try {
				Date dt1 = df.parse(str1);
				Date dt2 = df.parse(str2);
				if (dt1.getTime() > dt2.getTime()) {
					showErrorMessage("出票日期大于到票日期!");
					getBillCardPanel().setBodyValueAt(null, e.getRow(), "enddate");
					return;
				}			
			} catch (ParseException e1) {
				e1.printStackTrace();
			}			
			}	
		}else
//		if(e.getKey().equals("priceway")){
//			String wayname=Toolkits.getString(getHeadValueObject("priceway"));
//			if(wayname.equals("固定加价")){
//				getBillCardPanel().getHeadItem("cdprice").setEdit(true);
//				getBillCardPanel().getHeadItem("montax").setEdit(false);
//				getBillCardPanel().getHeadItem("hddays").setEdit(false);
//				getBillCardPanel().getHeadItem("voicesdate").setEdit(false);
//				getBillCardPanel().getHeadItem("voiceedate").setEdit(false);
//				setHeadValue("montax", null);
//				setHeadValue("hddays", null);
//				setHeadValue("voicesdate", null);
//				setHeadValue("voiceedate", null);
//				//add by cm start
//				getBillCardPanel().setHeadItem("txdays", null);
//				//add by cm end
//			}else if(wayname.equals("贴现加价")){
//				getBillCardPanel().getHeadItem("cdprice").setEdit(false);
//				getBillCardPanel().getHeadItem("montax").setEdit(true);
//				getBillCardPanel().getHeadItem("hddays").setEdit(true);
//				getBillCardPanel().getHeadItem("voicesdate").setEdit(true);
//				getBillCardPanel().getHeadItem("voiceedate").setEdit(true);
//				setHeadValue("cdprice", null);
//			}
//		}
		if(e.getKey().equals("hddays")||e.getKey().equals("voicesdate")||e.getKey().equals("voiceedate")){//自动计算贴现天数
				String voicesdate=Toolkits.getString(getHeadValueObject("voicesdate"));
				String voiceedate=Toolkits.getString(getHeadValueObject("voiceedate"));
				UFDouble hddays=getBillCardPanel().getHeadItem("hddays").getValueObject()==null?new UFDouble(0):new UFDouble(getBillCardPanel().getHeadItem("hddays").getValueObject().toString());
				UFDouble txdays = new UFDouble(0);
                if(!Toolkits.isEmpty(voicesdate)||!Toolkits.isEmpty(voiceedate)){
                	setHeadValue("txdays", txdays);
                	if(!Toolkits.isEmpty(voicesdate)&&!Toolkits.isEmpty(voiceedate)){
                		txdays=hddays.add(UFDate.getDaysBetween(new UFDate(voicesdate), new UFDate(voiceedate)));
                		setHeadValue("txdays", txdays);
                	}
                	
                }else if(Toolkits.isEmpty(voicesdate)&&Toolkits.isEmpty(voiceedate)){
                	setHeadValue("txdays", hddays);
                }
                
		}
		//add by cm start
//		else if(e.getKey().equals("pk_receiveway")){
////			String s = Toolkits.getString(getHeadValueObject("balancename"));
//			String pk_receiveway = (String)getBillCardPanel().getHeadItem("pk_receiveway").getValueObject();
//			BalanceWayVO vo = null;
//			try {
//				//根据主键查名称
//				vo = (BalanceWayVO) HYPubBO_Client.queryByPrimaryKey(BalanceWayVO.class, pk_receiveway);
//			} catch (UifException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			String balancename = vo.getBalancename();
//			if(balancename.indexOf("承兑")==-1){
//				getBillCardPanel().getHeadItem("cdprice").setEnabled(false);
//				getBillCardPanel().getHeadItem("montax").setEnabled(false);
//				getBillCardPanel().getHeadItem("priceway").setEnabled(false);
////				getBillCardPanel().getHeadItem("pk_productline").setEnabled(false);
////				getBillCardPanel().getHeadItem("pk_custproduct").setEnabled(false);
//				getBillCardPanel().getHeadItem("hddays").setEnabled(false);
//				getBillCardPanel().getHeadItem("voicesdate").setEnabled(false);
//				getBillCardPanel().getHeadItem("voiceedate").setEnabled(false);
//				getBillCardPanel().getHeadItem("txdays").setEnabled(false);
//				getBillCardPanel().setHeadItem("cdprice", null);
//				getBillCardPanel().setHeadItem("montax", null);
//				getBillCardPanel().setHeadItem("priceway", null);
////				getBillCardPanel().setHeadItem("pk_productline", null);
////				getBillCardPanel().setHeadItem("pk_custproduct", null);
//				getBillCardPanel().setHeadItem("hddays", null);
//				getBillCardPanel().setHeadItem("voicesdate", null);
//				getBillCardPanel().setHeadItem("voiceedate", null);
//				getBillCardPanel().setHeadItem("txdays", null);
//			}else{
//				getBillCardPanel().getHeadItem("priceway").setEnabled(true);
//			}
//			if(balancename.equals("现汇")){
//				getBillCardPanel().setHeadItem("cdprice", null);
//				getBillCardPanel().setHeadItem("montax", null);
//				getBillCardPanel().setHeadItem("priceway", null);
////				getBillCardPanel().setHeadItem("pk_productline", null);
////				getBillCardPanel().setHeadItem("pk_custproduct", null);
//				getBillCardPanel().setHeadItem("hddays", null);
//				getBillCardPanel().setHeadItem("voicesdate", null);
//				getBillCardPanel().setHeadItem("voiceedate", null);
//				getBillCardPanel().setHeadItem("txdays", null);
//			}
//		}
//		else if(e.getKey().equals("pk_vbillcode_tk")){
////			String pk_cumandoc = (String) getBillCardPanel().getHeadItem("pk_cumandoc").getValueObject();
////			if(pk_cumandoc==null){
////				showErrorMessage("请先填写客户名称");
////				getBillCardPanel().setHeadItem("pk_vbillcode_tk", null);
////				return;
////			}
//			try {
//				onLoadDate();
//			} catch (ClassNotFoundException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
		else if(e.getKey().equals("pk_cumandoc")){
			getBillCardPanel().setHeadItem("csaleid", null);
			getBillCardPanel().setHeadItem("pk_vbillcode_tk", null);
		}
		else if(e.getKey().equals("csaleid")){
				String csaleid = getBillCardPanel().getHeadItem("csaleid").getValueObject()==null?"":
					getBillCardPanel().getHeadItem("csaleid").getValueObject().toString();
//				String sql = " select * from gt_credit where nvl(dr,0)=0 and vbillstatus='"+IGTBillStatus.CHECKPASS+"' and receivetype in("+IJyglgtConst.RECEIVETYPE_QC+","+IJyglgtConst.RECEIVETYPE_SK+") and csaleid = '"+csaleid+"'";
				String sql = " select ccustomerid from so_sale where nvl(dr,0)=0 and fstatus!='1' and pk_corp='"+_getCorp().getPk_corp()+"' and csaleid = '"+csaleid+"'";
				try {
//					ArrayList<CreditVO> list = new ArrayList<CreditVO>();
					String str = Proxy.getItf().queryStrBySql(sql);
					getBillCardPanel().setHeadItem("pk_cumandoc", str==null?"":str.toString());
					getBillCardPanel().execHeadEditFormulas();
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		//add by cm end
	}
	
	/**
	 * CM
	 */
	@Override
    public boolean beforeEdit(BillItemEvent e) {
    	if(e.getItem().getKey().equals("pk_vbillcode_tk")){
    		StringBuffer sql = new StringBuffer("");
			Object invmandocid = getHeadValueObject("pk_cumandoc");
			UIRefPane pane = (UIRefPane)getBillCardPanel().getHeadItem("pk_vbillcode_tk").getComponent();
			if(!Toolkits.isEmpty(invmandocid)){
				sql.append(" and a.pk_cumandoc = '"+invmandocid.toString()+"'");
			}
			pane.getRefModel().addWherePart(sql.toString());
    	}else if(e.getItem().getKey().equals("csaleid")){
    		StringBuffer sqlsale = new StringBuffer("");
			Object invmandocid = getHeadValueObject("pk_cumandoc");
			UIRefPane panesale = (UIRefPane)getBillCardPanel().getHeadItem("csaleid").getComponent();
			if(!Toolkits.isEmpty(invmandocid)){
				sqlsale.append(" and so_sale.ccustomerid = '"+invmandocid.toString()+"' ");
			}
//			sqlsale.append(" and so_sale.vdef12 = '是' ");
			panesale.getRefModel().addWherePart(sqlsale.toString());
    	}
    	return super.beforeEdit(e);
    }
//	@Override
//	public void updateBgtButtons() {
//		super.updateBgtButtons();
//		AggregatedValueObject avo = getBufferData().getCurrentVO();
//		if(avo!=null){
//			CreditVO cvo = (CreditVO)avo.getParentVO();
//			if(cvo.getVdef19().equals("Y")){
//				setButtonState(IGTButton.BTN_REGISTRATION,false);
//			}
//		}
//		
//		
//		
//
//	}
//	public boolean beforeEdit(BillEditEvent e){
//		StringBuffer sql = new StringBuffer("");
//		Object invmandocid = getHeadValueObject("pk_cumandoc");
//		UIRefPane pane = (UIRefPane)getBillCardPanel().getHeadItem("pk_vbillcode_tk").getComponent();
//		if(!Toolkits.isEmpty(invmandocid)){
//			sql.append(" and a.pk_cumandoc = '"+invmandocid.toString()+"'");
//		}
//		pane.getRefModel().addWherePart(sql.toString());
//		if(e.getKey().equals("pk_vbillcode_tk")){
////			String pk_cumandoc=getHeadValueObject("pk_cumandoc")==null?"":getHeadValueObject("pk_cumandoc").toString();
////			if(pk_cumandoc.equals("")){
////				showErrorMessage("请先选择客户！");
////				return;
////			}
////			
//			String pk_cumandoc = (String) getBillCardPanel().getHeadItem("pk_cumandoc").getValueObject();
//			if(pk_cumandoc==null){
//				showErrorMessage("请先填写客户名称");
//			}
//		}
//		return super.beforeEdit(e);
//	}
	public static Date addMonths(Date date, int amount) {   
		      return add(date, Calendar.MONTH, amount);   
		  }
	public static Date add(Date date, int calendarField, int amount) {   
		      if (date == null) {   
		          throw new IllegalArgumentException("The date must not be null");   
		      }   
		      Calendar c = Calendar.getInstance();   
		      c.setTime(date);   
		      c.add(calendarField, amount);   
		      return c.getTime();   
		  }
	
	public void isExistSktype(){
		Object receivetype = getHeadValueObject("receivetype");
		if(Toolkits.isEmpty(receivetype)){
			showErrorMessage("请先输入收款类型!");
			return;
		}
	}
//add by cm 
//	public void onLoadDate() throws ClassNotFoundException{
//		String pk_vbillcode_tk = getBillCardPanel().getHeadItem("pk_vbillcode_tk").getValueObject().toString();
//		String sql = " select * from gt_credit where nvl(dr,0)=0 and vbillstatus='"+IJyglgtBillStatus.CHECKPASS+"' and receivetype in("+IJyglgtConst.RECEIVETYPE_QC+","+IJyglgtConst.RECEIVETYPE_SK+") and pk_credit = '"+pk_vbillcode_tk+"'";
//		try {
//			ArrayList<CreditVO> list = new ArrayList<CreditVO>();
//			list = Proxy.getItf().getVOsfromSql(sql, CreditVO.class.getName());
//			CreditVO cred =null;
//			if(list!=null&&list.size()>0){
//				cred=new CreditVO();
//				cred=(CreditVO)list.get(0);
////				cred.getSkmny()==null?0:cred.getSkmny() - nvl(cred.getFhmny(),0) - nvl(cred.getTkmny(),0);
//				UIRefPane pane= (UIRefPane)getBillCardPanel().getHeadItem("pk_vbillcode_tk").getComponent();
//				getBillCardPanel().setHeadItem("pk_cumandoc", cred.getPk_cumandoc());
//				getBillCardPanel().setHeadItem("pk_receiveway", cred.getPk_receiveway());
//				getBillCardPanel().setHeadItem("pk_balatype", cred.getPk_balatype());
//				//ADD BY SK START 2012-3-28
//				String vdef13 = Toolkits.getString(getHeadValueObject("vdef13"));
//				if(vdef13.equals("Y")){
//					getBillCardPanel().setHeadItem("isreceipt", new UFBoolean("N"));
//				}else{
//					getBillCardPanel().setHeadItem("skmny", new UFDouble("-"+pane.getRefValue("(nvl(a.skmny,0) - nvl(a.fhmny,0) - nvl(a.tkmny,0))").toString()));//new UFDouble("-"+cred.getSkmny()));
//	//				getBillCardPanel().setHeadItem("deposit", cred.getDeposit());
//					getBillCardPanel().setHeadItem("bcskmny", new UFDouble("-"+pane.getRefValue("(nvl(a.skmny,0) - nvl(a.fhmny,0) - nvl(a.tkmny,0))").toString()));//new UFDouble("-"+cred.getBcskmny()));
//					getBillCardPanel().setHeadItem("isreceipt", cred.getIsreceipt());
//				}
//				//ADD BY SK END
//				getBillCardPanel().setHeadItem("csaleid", cred.getCsaleid());
//				getBillCardPanel().setHeadItem("cdprice", cred.getCdprice());
//				getBillCardPanel().setHeadItem("montax", cred.getMontax());
//				getBillCardPanel().setHeadItem("priceway", cred.getPriceway());
//				getBillCardPanel().setHeadItem("pk_productline", cred.getPk_productline());
//				getBillCardPanel().setHeadItem("pk_custproduct", cred.getPk_custproduct());
//				getBillCardPanel().setHeadItem("hddays", cred.getHddays());
//				getBillCardPanel().setHeadItem("voicesdate", cred.getVoicesdate());
//				getBillCardPanel().setHeadItem("voiceedate", cred.getVoiceedate());
//				getBillCardPanel().setHeadItem("txdays", cred.getTxdays());
//				if(cred.getPriceway()==null){
//					getBillCardPanel().getHeadItem("cdprice").setEnabled(false);
//					getBillCardPanel().getHeadItem("montax").setEnabled(false);
//					getBillCardPanel().getHeadItem("priceway").setEnabled(false);
////					getBillCardPanel().getHeadItem("pk_productline").setEnabled(false);
////					getBillCardPanel().getHeadItem("pk_custproduct").setEnabled(false);
//					getBillCardPanel().getHeadItem("hddays").setEnabled(false);
//					getBillCardPanel().getHeadItem("voicesdate").setEnabled(false);
//					getBillCardPanel().getHeadItem("voiceedate").setEnabled(false);
//					getBillCardPanel().getHeadItem("txdays").setEnabled(false);
//					getBillCardPanel().setHeadItem("cdprice", null);
//					getBillCardPanel().setHeadItem("montax", null);
//					getBillCardPanel().setHeadItem("priceway", null);
////					getBillCardPanel().setHeadItem("pk_productline", null);
////					getBillCardPanel().setHeadItem("pk_custproduct", null);
//					getBillCardPanel().setHeadItem("hddays", null);
//					getBillCardPanel().setHeadItem("voicesdate", null);
//					getBillCardPanel().setHeadItem("voiceedate", null);
//					getBillCardPanel().setHeadItem("txdays", null);
//				}else
//				if(cred.getPriceway().equals("固定加价")){
//					getBillCardPanel().getHeadItem("priceway").setEdit(true);
//					getBillCardPanel().getHeadItem("cdprice").setEdit(true);
//					getBillCardPanel().getHeadItem("montax").setEdit(false);
//					getBillCardPanel().getHeadItem("hddays").setEdit(false);
//					getBillCardPanel().getHeadItem("voicesdate").setEdit(false);
//					getBillCardPanel().getHeadItem("voiceedate").setEdit(false);
//					setHeadValue("montax", null);
//					setHeadValue("hddays", null);
//					setHeadValue("voicesdate", null);
//					setHeadValue("voiceedate", null);
//					getBillCardPanel().setHeadItem("txdays", null);
//				}else if(cred.getPriceway().equals("贴现加价")){
//					getBillCardPanel().getHeadItem("priceway").setEdit(true);
//					getBillCardPanel().getHeadItem("cdprice").setEdit(false);
//					getBillCardPanel().getHeadItem("montax").setEdit(true);
//					getBillCardPanel().getHeadItem("hddays").setEdit(true);
//					getBillCardPanel().getHeadItem("voicesdate").setEdit(true);
//					getBillCardPanel().getHeadItem("voiceedate").setEdit(true);
//					setHeadValue("cdprice", null);
//				}
//			}
//		} catch (BusinessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
}
