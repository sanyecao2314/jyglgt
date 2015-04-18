package nc.bs.gt.gs.balance;


import java.util.ArrayList;
import java.util.HashMap;
import nc.bs.jyglgt.dao.ServerDAO;
import nc.bs.pub.pf.PfUtilBO;
import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.scm.so.SaleBillType;
import nc.ui.so.so002.ISaleInvoiceAction;
import nc.vo.gt.gs.gs11.BalanceInfoBVO;
import nc.vo.gt.gs.gs11.SellPaymentJSVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.so.so002.SaleVO;
import nc.vo.so.so002.SaleinvoiceBVO;
import nc.vo.so.so002.SaleinvoiceVO;


/**
 * @author 施鹏 销售结算回写销售发票 业务后台处理类
 */
public class BanlanceBackInvoiceDMO extends ServerDAO {

	/**
	 * 生成发票结算
	 * */
	public void addNewSaleInvoice(
			BalanceInfoBVO[] infbvo,SellPaymentJSVO sjsvo,UFDate date,String pk_operator) throws BusinessException {	     
	   try {
		   SaleinvoiceVO billvo = new SaleinvoiceVO();	  
		   SaleVO hvo=new SaleVO();
		   SaleinvoiceBVO[]bvo=new SaleinvoiceBVO[infbvo.length];
		   hvo.setCreceiptcorpid(sjsvo.getPk_cumandoc());
		   hvo.setDbilldate(date);
		   hvo.setCoperatorid(pk_operator);
		   hvo.setDmakedate(date);
		   hvo.setPk_corp(sjsvo.getPk_corp());
		   hvo.setFinvoicetype(new Integer(1));
		   hvo.setCdeptid(sjsvo.getPk_deptdoc());
		   hvo.setFcounteractflag(SaleVO.FCOUNTERACTFLAG_NORMAL);
		   hvo.setCreceipttype(SaleBillType.SaleInvoice);
		   hvo.setFstatus(new Integer(1));
		   for(int i=0;i<infbvo.length;i++){
			   bvo[i]=new SaleinvoiceBVO();
			   bvo[i].setPk_corp(sjsvo.getPk_corp());
			   bvo[i].setCinventoryid(infbvo[i].getPk_invmandoc());
			   bvo[i].setCinvbasdocid(infbvo[i].getPk_invbasdoc());
			   bvo[i].setVfree1(infbvo[i].getVfree1());
			   bvo[i].setVfree2(infbvo[i].getVfree2());
			   bvo[i].setVfree3(infbvo[i].getVfree3());
			   bvo[i].setVfree4(infbvo[i].getVfree4());
			   bvo[i].setCbatchid(infbvo[i].getVdef14());
			   bvo[i].setNnumber(infbvo[i].getSuttle());
			   bvo[i].setNpacknumber(infbvo[i].getNum());
			   bvo[i].setNoriginalcurtaxprice(infbvo[i].getVdef1());
			   bvo[i].setNoriginalcursummny(infbvo[i].getVdef2());
			   bvo[i].setCupsourcebillbodyid(infbvo[i].getVdef12());
			   bvo[i].setCrowno(String.valueOf(10*(i+1)));
			   bvo[i].setCupreceipttype("4C");
			   String sql="select   a.cwarehouseid,a.pk_calbody,c.ccurrencytypeid,c.nexchangeotobrate,d.cbiztype,d.csalecorpid,c.ntaxrate,a.cgeneralhid,a.vbillcode,a.ts hts,b.* from ic_general_h a inner join ic_general_b b on a.cgeneralhid=b.cgeneralhid  inner join so_saleorder_b c on b.csourcebillbid=c.corder_bid inner join so_sale d on c.csaleid=d.csaleid"+
			   " where b.cgeneralbid='"+infbvo[i].getVdef12()+"'";
				ArrayList list=Proxy.getItf().queryArrayBySql(sql);
				if(list!=null&&list.size()>0){
					HashMap map=(HashMap)list.get(0);
					bvo[i].setCupsourcebillcode(Toolkits.getString(map.get("vbillcode")));
					bvo[i].setCupsourcebillid(Toolkits.getString(map.get("cgeneralhid")));
					bvo[i].setCunitid(Toolkits.getString(map.get("pk_measdoc")));
					bvo[i].setCpackunitid(Toolkits.getString(map.get("castunitid")));
					bvo[i].setScalefactor(Toolkits.getUFDouble(map.get("scalefactor")));
					bvo[i].setTs(new UFDateTime(Toolkits.getString(map.get("hts"))));
					bvo[i].setCsourcebillid(Toolkits.getString(map.get("csourcebillhid")));
					bvo[i].setCsourcebillbodyid(Toolkits.getString(map.get("csourcebillbid")));
					//税率
					UFDouble ntaxrate=Toolkits.getUFDouble(map.get("ntaxrate"));
					//含税金额
					UFDouble noriginalcursummny=infbvo[i].getVdef2();					
					//无税金额
					UFDouble wsmny=new UFDouble(noriginalcursummny.div(new UFDouble(1).add(ntaxrate.div(100))).doubleValue(),2);
					//无税单价
					UFDouble noriginalcurprice=new UFDouble(wsmny.div(infbvo[i].getSuttle()).doubleValue(),5);
                    //税额
					UFDouble noriginalcurtaxmny=noriginalcursummny.sub(wsmny);
					bvo[i].setNtaxrate(ntaxrate);
					bvo[i].setNoriginalcurprice(noriginalcurprice);
					bvo[i].setNoriginalcurmny(wsmny);
					bvo[i].setNoriginalcurtaxmny(noriginalcurtaxmny);
					hvo.setCbiztype(Toolkits.getString(map.get("cbiztype")));
					hvo.setCsalecorpid(Toolkits.getString(map.get("csalecorpid")));
					hvo.setCcurrencyid(Toolkits.getString(map.get("ccurrencytypeid")));
					hvo.setNexchangeotobrate(Toolkits.getUFDouble(map.get("nexchangeotobrate")));
					bvo[i].setCcurrencytypeid(Toolkits.getString(map.get("ccurrencytypeid")));
					bvo[i].setNexchangeotobrate(Toolkits.getUFDouble(map.get("nexchangeotobrate")));
					bvo[i].setNprice(noriginalcurprice);
					bvo[i].setNtaxprice(infbvo[i].getVdef1());
					bvo[i].setNtaxmny(noriginalcurtaxmny);
					bvo[i].setNsummny(noriginalcursummny);
					bvo[i].setNmny(wsmny);
					bvo[i].setBlargessflag(new UFBoolean(false));
					bvo[i].setCbodywarehouseid(Toolkits.getString(map.get("cwarehouseid")));//仓库
					bvo[i].setCadvisecalbodyid(Toolkits.getString(map.get("pk_calbody")));//库存组织
				}
		   }
		   billvo.setParentVO(hvo);
		   billvo.setChildrenVO(bvo); 
		   billvo.setStatus(nc.vo.pub.VOStatus.NEW);
		   new PfUtilBO().processAction(ISaleInvoiceAction.PreSave, SaleBillType.SaleInvoice, date.toString(), null, billvo, null);
	   } catch (Exception e) {		
		   e.printStackTrace();
		   throw new BusinessException(e.getMessage());		
	   }
	}

	/**
	 * 生成发票结算
	 * */
	public void addNewSaleInvoiceYh(
			BalanceInfoBVO[] infbvo,SellPaymentJSVO sjsvo,UFDate date,String pk_operator) throws BusinessException {	     
	   try {
		   SaleinvoiceVO billvo = new SaleinvoiceVO();	  
		   SaleVO hvo=new SaleVO();
		   SaleinvoiceBVO[]bvo=new SaleinvoiceBVO[infbvo.length];
		   hvo.setCreceiptcorpid(sjsvo.getPk_cumandoc());
		   hvo.setDbilldate(date);
		   hvo.setCoperatorid(pk_operator);
		   hvo.setDmakedate(date);
		   hvo.setPk_corp(sjsvo.getPk_corp());
		   hvo.setFinvoicetype(new Integer(1));
		   hvo.setCdeptid(sjsvo.getPk_deptdoc());
		   hvo.setFcounteractflag(SaleVO.FCOUNTERACTFLAG_NORMAL);
		   hvo.setCreceipttype(SaleBillType.SaleInvoice);
		   hvo.setFstatus(new Integer(1));
		   for(int i=0;i<infbvo.length;i++){
			   bvo[i]=new SaleinvoiceBVO();
			   bvo[i].setPk_corp(sjsvo.getPk_corp());
			   bvo[i].setCinventoryid(infbvo[i].getPk_invmandoc());
			   bvo[i].setCinvbasdocid(infbvo[i].getPk_invbasdoc());
			   bvo[i].setVfree1(infbvo[i].getVfree1());
			   bvo[i].setVfree2(infbvo[i].getVfree2());
			   bvo[i].setVfree3(infbvo[i].getVfree3());
			   bvo[i].setVfree4(infbvo[i].getVfree4());
			   bvo[i].setCbatchid(infbvo[i].getVdef14());
			   bvo[i].setNnumber(new UFDouble(0).sub(infbvo[i].getSuttle()));
			   bvo[i].setNpacknumber(new UFDouble(0).sub(infbvo[i].getNum()));
			   bvo[i].setNoriginalcurtaxprice(infbvo[i].getVdef1());
			   bvo[i].setNoriginalcursummny(new UFDouble(0).sub(infbvo[i].getVdef2()));
			   bvo[i].setCupsourcebillbodyid(infbvo[i].getVdef12());
			   bvo[i].setCrowno(String.valueOf(10*(i+1)));
			   bvo[i].setCupreceipttype("4C");
			   String sql="select   a.cwarehouseid,a.pk_calbody,c.ccurrencytypeid,c.nexchangeotobrate,d.cbiztype,d.csalecorpid,c.ntaxrate,a.cgeneralhid,a.vbillcode,a.ts hts,b.* from ic_general_h a inner join ic_general_b b on a.cgeneralhid=b.cgeneralhid  inner join so_saleorder_b c on b.csourcebillbid=c.corder_bid inner join so_sale d on c.csaleid=d.csaleid"+
			   " where b.cgeneralbid='"+infbvo[i].getVdef12()+"'";
				ArrayList list=Proxy.getItf().queryArrayBySql(sql);
				if(list!=null&&list.size()>0){
					HashMap map=(HashMap)list.get(0);
					bvo[i].setCupsourcebillcode(Toolkits.getString(map.get("vbillcode")));
					bvo[i].setCupsourcebillid(Toolkits.getString(map.get("cgeneralhid")));
					bvo[i].setCunitid(Toolkits.getString(map.get("pk_measdoc")));
					bvo[i].setCpackunitid(Toolkits.getString(map.get("castunitid")));
					bvo[i].setScalefactor(Toolkits.getUFDouble(map.get("scalefactor")));
					bvo[i].setTs(new UFDateTime(Toolkits.getString(map.get("hts"))));
					bvo[i].setCsourcebillid(Toolkits.getString(map.get("csourcebillhid")));
					bvo[i].setCsourcebillbodyid(Toolkits.getString(map.get("csourcebillbid")));
					//税率
					UFDouble ntaxrate=Toolkits.getUFDouble(map.get("ntaxrate"));
					//含税金额
					UFDouble noriginalcursummny=new UFDouble(0).sub(infbvo[i].getVdef2());					
					//无税金额
					UFDouble wsmny=new UFDouble(noriginalcursummny.div(new UFDouble(1).add(ntaxrate.div(100))).doubleValue(),2);
					//无税单价
					UFDouble noriginalcurprice=new UFDouble(wsmny.div(infbvo[i].getSuttle()).doubleValue(),5);
                    //税额
					UFDouble noriginalcurtaxmny=noriginalcursummny.sub(wsmny);
					bvo[i].setNtaxrate(ntaxrate);
					bvo[i].setNoriginalcurprice(noriginalcurprice);
					bvo[i].setNoriginalcurmny(wsmny);
					bvo[i].setNoriginalcurtaxmny(noriginalcurtaxmny);
					hvo.setCbiztype(Toolkits.getString(map.get("cbiztype")));
					hvo.setCsalecorpid(Toolkits.getString(map.get("csalecorpid")));
					hvo.setCcurrencyid(Toolkits.getString(map.get("ccurrencytypeid")));
					hvo.setNexchangeotobrate(Toolkits.getUFDouble(map.get("nexchangeotobrate")));
					bvo[i].setCcurrencytypeid(Toolkits.getString(map.get("ccurrencytypeid")));
					bvo[i].setNexchangeotobrate(Toolkits.getUFDouble(map.get("nexchangeotobrate")));
					bvo[i].setNprice(noriginalcurprice);
					bvo[i].setNtaxprice(infbvo[i].getVdef1());
					bvo[i].setNtaxmny(noriginalcurtaxmny);
					bvo[i].setNsummny(noriginalcursummny);
					bvo[i].setNmny(wsmny);
					bvo[i].setBlargessflag(new UFBoolean(false));
					bvo[i].setCbodywarehouseid(Toolkits.getString(map.get("cwarehouseid")));//仓库
					bvo[i].setCadvisecalbodyid(Toolkits.getString(map.get("pk_calbody")));//库存组织
				}
		   }
		   billvo.setParentVO(hvo);
		   billvo.setChildrenVO(bvo); 
		   billvo.setStatus(nc.vo.pub.VOStatus.NEW);
		   new PfUtilBO().processAction(ISaleInvoiceAction.PreSave, SaleBillType.SaleInvoice, date.toString(), null, billvo, null);
	   } catch (Exception e) {		
		   e.printStackTrace();
		   throw new BusinessException(e.getMessage());		
	   }
	}
	/**
	 * 生成发票结算
	 * */
	public void addNewSaleInvoiceYh2(
			BalanceInfoBVO[] infbvo,SellPaymentJSVO sjsvo,UFDate date,String pk_operator) throws BusinessException {	     
	   try {
		   SaleinvoiceVO billvo = new SaleinvoiceVO();	  
		   SaleVO hvo=new SaleVO();
		   SaleinvoiceBVO[]bvo=new SaleinvoiceBVO[infbvo.length];
		   hvo.setCreceiptcorpid(sjsvo.getPk_cumandoc());
		   hvo.setDbilldate(date);
		   hvo.setCoperatorid(pk_operator);
		   hvo.setDmakedate(date);
		   hvo.setPk_corp(sjsvo.getPk_corp());
		   hvo.setFinvoicetype(new Integer(1));
		   hvo.setCdeptid(sjsvo.getPk_deptdoc());
		   hvo.setFcounteractflag(SaleVO.FCOUNTERACTFLAG_NORMAL);
		   hvo.setCreceipttype(SaleBillType.SaleInvoice);
		   hvo.setFstatus(new Integer(1));
		   for(int i=0;i<infbvo.length;i++){
			   bvo[i]=new SaleinvoiceBVO();
			   bvo[i].setPk_corp(sjsvo.getPk_corp());
			   bvo[i].setCinventoryid(infbvo[i].getPk_invmandoc());
			   bvo[i].setCinvbasdocid(infbvo[i].getPk_invbasdoc());
			   bvo[i].setVfree1(infbvo[i].getVfree1());
			   bvo[i].setVfree2(infbvo[i].getVfree2());
			   bvo[i].setVfree3(infbvo[i].getVfree3());
			   bvo[i].setVfree4(infbvo[i].getVfree4());
			   bvo[i].setCbatchid(infbvo[i].getVdef14());
			   bvo[i].setNnumber(infbvo[i].getSuttle());
			   bvo[i].setNpacknumber(infbvo[i].getNum());
			   bvo[i].setNoriginalcurtaxprice(infbvo[i].getPreferentialmny().div(infbvo[i].getNum()));
			   bvo[i].setNoriginalcursummny(infbvo[i].getPreferentialmny());
			   bvo[i].setCupsourcebillbodyid(infbvo[i].getVdef12());
			   bvo[i].setCrowno(String.valueOf(10*(i+1)));
			   bvo[i].setCupreceipttype("4C");
			   String sql="select   a.cwarehouseid,a.pk_calbody,c.ccurrencytypeid,c.nexchangeotobrate,d.cbiztype,d.csalecorpid,c.ntaxrate,a.cgeneralhid,a.vbillcode,a.ts hts,b.* from ic_general_h a inner join ic_general_b b on a.cgeneralhid=b.cgeneralhid  inner join so_saleorder_b c on b.csourcebillbid=c.corder_bid inner join so_sale d on c.csaleid=d.csaleid"+
			   " where b.cgeneralbid='"+infbvo[i].getVdef12()+"'";
				ArrayList list=Proxy.getItf().queryArrayBySql(sql);
				if(list!=null&&list.size()>0){
					HashMap map=(HashMap)list.get(0);
					bvo[i].setCupsourcebillcode(Toolkits.getString(map.get("vbillcode")));
					bvo[i].setCupsourcebillid(Toolkits.getString(map.get("cgeneralhid")));
					bvo[i].setCunitid(Toolkits.getString(map.get("pk_measdoc")));
					bvo[i].setCpackunitid(Toolkits.getString(map.get("castunitid")));
					bvo[i].setScalefactor(Toolkits.getUFDouble(map.get("scalefactor")));
					bvo[i].setTs(new UFDateTime(Toolkits.getString(map.get("hts"))));
					bvo[i].setCsourcebillid(Toolkits.getString(map.get("csourcebillhid")));
					bvo[i].setCsourcebillbodyid(Toolkits.getString(map.get("csourcebillbid")));
					//税率
					UFDouble ntaxrate=Toolkits.getUFDouble(map.get("ntaxrate"));
					//含税金额
					UFDouble noriginalcursummny=infbvo[i].getPreferentialmny();					
					//无税金额
					UFDouble wsmny=new UFDouble(noriginalcursummny.div(new UFDouble(1).add(ntaxrate.div(100))).doubleValue(),2);
					//无税单价
					UFDouble noriginalcurprice=new UFDouble(wsmny.div(infbvo[i].getSuttle()).doubleValue(),5);
                    //税额
					UFDouble noriginalcurtaxmny=noriginalcursummny.sub(wsmny);
					bvo[i].setNtaxrate(ntaxrate);
					bvo[i].setNoriginalcurprice(noriginalcurprice);
					bvo[i].setNoriginalcurmny(wsmny);
					bvo[i].setNoriginalcurtaxmny(noriginalcurtaxmny);
					hvo.setCbiztype(Toolkits.getString(map.get("cbiztype")));
					hvo.setCsalecorpid(Toolkits.getString(map.get("csalecorpid")));
					hvo.setCcurrencyid(Toolkits.getString(map.get("ccurrencytypeid")));
					hvo.setNexchangeotobrate(Toolkits.getUFDouble(map.get("nexchangeotobrate")));
					bvo[i].setCcurrencytypeid(Toolkits.getString(map.get("ccurrencytypeid")));
					bvo[i].setNexchangeotobrate(Toolkits.getUFDouble(map.get("nexchangeotobrate")));
					bvo[i].setNprice(noriginalcurprice);
					bvo[i].setNtaxprice(infbvo[i].getPreferentialmny().div(infbvo[i].getNum()));
					bvo[i].setNtaxmny(noriginalcurtaxmny);
					bvo[i].setNsummny(noriginalcursummny);
					bvo[i].setNmny(wsmny);
					bvo[i].setBlargessflag(new UFBoolean(false));
					bvo[i].setCbodywarehouseid(Toolkits.getString(map.get("cwarehouseid")));//仓库
					bvo[i].setCadvisecalbodyid(Toolkits.getString(map.get("pk_calbody")));//库存组织
				}
		   }
		   billvo.setParentVO(hvo);
		   billvo.setChildrenVO(bvo); 
		   billvo.setStatus(nc.vo.pub.VOStatus.NEW);
		   new PfUtilBO().processAction(ISaleInvoiceAction.PreSave, SaleBillType.SaleInvoice, date.toString(), null, billvo, null);
	   } catch (Exception e) {		
		   e.printStackTrace();
		   throw new BusinessException(e.getMessage());		
	   }
	}
}
