/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.vo.gt.gs.gs11;
	
import nc.vo.pub.*;
import nc.vo.pub.lang.*;
	
/**
 * <b> 在此处简要描述此类的功能 </b>
 * <p>
 *     在此处添加此类的描述信息
 * </p>
 * 创建日期:2011-06-23 15:23:28
 * @author 施坤
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class SellPaymentJSVO extends SuperVO {
	private String pk_corp;
	private UFDateTime ts;
	private String preferentialzcno;
	private String billtype;
	private String vdef9;
	private String vapprovednote;
	private String vbillcode;
	private UFDouble vdef1;
	private String vdef8;
	private String pk_sellpaymentjs;
	private String vunapproveid;
	private String pk_busitype;
	private String vunapprovednote;
	private UFDate gwsdate;
	private String vapproveid;
	private String pk_deptdoc;
	private UFDouble cess;
	private String preferentialtype;
	private UFDate balancedate;
	private String vdef0;
	private String vdef7;
	private String pk_salescontract;
	private String pk_cubasdoc;
	private String pk_cumandoc;
	private UFDouble vdef2;
	private UFDouble vdef5;
	private UFDouble specialyhjg;
	private Integer vbillstatus;
	private String memo;
	private UFDate gwedate;
	private UFDate vapprovedate;
	private UFDouble vdef3;
	private UFDate dmakedate;
	private String vdef6;
	private Integer dr;
	private UFDouble vdef4;
	private String pk_operator;
	private UFBoolean accounty;
	private UFDate vunapprovedate;
	private String balancyway;
	private String pk_productline;
	private String pk_custproduct;
	private String vproductline;
	private String custproductname;
	private String pk_balanceway;
	private String balancewayname;
	private UFTime stime;
	private UFTime etime;
	private String pk_stordoc;

	public static final String BALANCEWAYNAME = "balancewayname";
	public static final String PK_BALANCEWAY = "pk_balanceway";
	public static final String BALANCYWAY = "balancyway";
	public static final String VPRODUCTLINE = "vproductline";
	public static final String CUSTPRODUCTNAME = "custproductname";
	public static final String PK_PRODUCTLINE = "pk_productline";
	public static final String PK_CUSTPRODUCT = "pk_custproduct";
	public static final String PK_CORP = "pk_corp";
	public static final String PREFERENTIALZCNO = "preferentialzcno";
	public static final String BILLTYPE = "billtype";
	public static final String VDEF9 = "vdef9";
	public static final String VAPPROVEDNOTE = "vapprovednote";
	public static final String VBILLCODE = "vbillcode";
	public static final String VDEF1 = "vdef1";
	public static final String VDEF8 = "vdef8";
	public static final String PK_SELLPAYMENTJS = "pk_sellpaymentjs";
	public static final String VUNAPPROVEID = "vunapproveid";
	public static final String PK_BUSITYPE = "pk_busitype";
	public static final String VUNAPPROVEDNOTE = "vunapprovednote";
	public static final String GWSDATE = "gwsdate";
	public static final String VAPPROVEID = "vapproveid";
	public static final String PK_DEPTDOC = "pk_deptdoc";
	public static final String CESS = "cess";
	public static final String PREFERENTIALTYPE = "preferentialtype";
	public static final String BALANCEDATE = "balancedate";
	public static final String VDEF0 = "vdef0";
	public static final String VDEF7 = "vdef7";
	public static final String PK_SALESCONTRACT = "pk_salescontract";
	public static final String PK_CUBASDOC = "pk_cubasdoc";
	public static final String VDEF2 = "vdef2";
	public static final String VDEF5 = "vdef5";
	public static final String SPECIALYHJG = "specialyhjg";
	public static final String VBILLSTATUS = "vbillstatus";
	public static final String MEMO = "memo";
	public static final String GWEDATE = "gwedate";
	public static final String VAPPROVEDATE = "vapprovedate";
	public static final String VDEF3 = "vdef3";
	public static final String DMAKEDATE = "dmakedate";
	public static final String VDEF6 = "vdef6";
	public static final String VDEF4 = "vdef4";
	public static final String PK_OPERATOR = "pk_operator";
	public static final String ACCOUNTY = "accounty";
	public static final String VUNAPPROVEDATE = "vunapprovedate";
	public static final String STIME = "stime";
	public static final String ETIME = "etime";
	public static final String PK_STORDOC="pk_stordoc";
	

	public String getPk_stordoc() {
		return pk_stordoc;
	}
	public void setPk_stordoc(String pk_stordoc) {
		this.pk_stordoc = pk_stordoc;
	}
	public UFTime getStime() {
		return stime;
	}
	public void setStime(UFTime stime) {
		this.stime = stime;
	}
	public UFTime getEtime() {
		return etime;
	}
	public void setEtime(UFTime etime) {
		this.etime = etime;
	}
	public String getPk_balanceway() {
		return pk_balanceway;
	}
	public void setPk_balanceway(String pk_balanceway) {
		this.pk_balanceway = pk_balanceway;
	}
	public String getBalancewayname() {
		return balancewayname;
	}
	public void setBalancewayname(String balancewayname) {
		this.balancewayname = balancewayname;
	}
	public String getVproductline() {
		return vproductline;
	}
	public void setVproductline(String vproductline) {
		this.vproductline = vproductline;
	}
	public String getCustproductname() {
		return custproductname;
	}
	public void setCustproductname(String custproductname) {
		this.custproductname = custproductname;
	}
	public String getBalancyway() {
		return balancyway;
	}
	public void setBalancyway(String balancyway) {
		this.balancyway = balancyway;
	}
	public String getPk_productline() {
		return pk_productline;
	}
	public void setPk_productline(String pk_productline) {
		this.pk_productline = pk_productline;
	}
	public String getPk_custproduct() {
		return pk_custproduct;
	}
	public void setPk_custproduct(String pk_custproduct) {
		this.pk_custproduct = pk_custproduct;
	}
	/**
	 * 属性pk_corp的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getPk_corp () {
		return pk_corp;
	}   
	/**
	 * 属性pk_corp的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newPk_corp String
	 */
	public void setPk_corp (String newPk_corp ) {
	 	this.pk_corp = newPk_corp;
	} 	  
	/**
	 * 属性ts的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return UFDateTime
	 */
	public UFDateTime getTs () {
		return ts;
	}   
	/**
	 * 属性ts的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newTs UFDateTime
	 */
	public void setTs (UFDateTime newTs ) {
	 	this.ts = newTs;
	} 	  
	/**
	 * 属性preferentialzcno的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getPreferentialzcno () {
		return preferentialzcno;
	}   
	/**
	 * 属性preferentialzcno的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newPreferentialzcno String
	 */
	public void setPreferentialzcno (String newPreferentialzcno ) {
	 	this.preferentialzcno = newPreferentialzcno;
	} 	  
	/**
	 * 属性billtype的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getBilltype () {
		return billtype;
	}   
	/**
	 * 属性billtype的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newBilltype String
	 */
	public void setBilltype (String newBilltype ) {
	 	this.billtype = newBilltype;
	} 	  
	/**
	 * 属性vdef9的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getVdef9 () {
		return vdef9;
	}   
	/**
	 * 属性vdef9的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVdef9 String
	 */
	public void setVdef9 (String newVdef9 ) {
	 	this.vdef9 = newVdef9;
	} 	  
	/**
	 * 属性vapprovednote的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getVapprovednote () {
		return vapprovednote;
	}   
	/**
	 * 属性vapprovednote的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVapprovednote String
	 */
	public void setVapprovednote (String newVapprovednote ) {
	 	this.vapprovednote = newVapprovednote;
	} 	  
	/**
	 * 属性vbillcode的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getVbillcode () {
		return vbillcode;
	}   
	/**
	 * 属性vbillcode的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVbillcode String
	 */
	public void setVbillcode (String newVbillcode ) {
	 	this.vbillcode = newVbillcode;
	} 	  
	/**
	 * 属性vdef1的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return UFDouble
	 */
	public UFDouble getVdef1 () {
		return vdef1;
	}   
	/**
	 * 属性vdef1的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVdef1 UFDouble
	 */
	public void setVdef1 (UFDouble newVdef1 ) {
	 	this.vdef1 = newVdef1;
	} 	  
	/**
	 * 属性vdef8的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getVdef8 () {
		return vdef8;
	}   
	/**
	 * 属性vdef8的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVdef8 String
	 */
	public void setVdef8 (String newVdef8 ) {
	 	this.vdef8 = newVdef8;
	} 	  
	/**
	 * 属性pk_sellpaymentjs的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getPk_sellpaymentjs () {
		return pk_sellpaymentjs;
	}   
	/**
	 * 属性pk_sellpaymentjs的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newPk_sellpaymentjs String
	 */
	public void setPk_sellpaymentjs (String newPk_sellpaymentjs ) {
	 	this.pk_sellpaymentjs = newPk_sellpaymentjs;
	} 	  
	/**
	 * 属性vunapproveid的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getVunapproveid () {
		return vunapproveid;
	}   
	/**
	 * 属性vunapproveid的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVunapproveid String
	 */
	public void setVunapproveid (String newVunapproveid ) {
	 	this.vunapproveid = newVunapproveid;
	} 	  
	/**
	 * 属性pk_busitype的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getPk_busitype () {
		return pk_busitype;
	}   
	/**
	 * 属性pk_busitype的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newPk_busitype String
	 */
	public void setPk_busitype (String newPk_busitype ) {
	 	this.pk_busitype = newPk_busitype;
	} 	  
	/**
	 * 属性vunapprovednote的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getVunapprovednote () {
		return vunapprovednote;
	}   
	/**
	 * 属性vunapprovednote的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVunapprovednote String
	 */
	public void setVunapprovednote (String newVunapprovednote ) {
	 	this.vunapprovednote = newVunapprovednote;
	} 	  
	/**
	 * 属性gwsdate的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return UFDate
	 */
	public UFDate getGwsdate () {
		return gwsdate;
	}   
	/**
	 * 属性gwsdate的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newGwsdate UFDate
	 */
	public void setGwsdate (UFDate newGwsdate ) {
	 	this.gwsdate = newGwsdate;
	} 	  
	/**
	 * 属性vapproveid的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getVapproveid () {
		return vapproveid;
	}   
	/**
	 * 属性vapproveid的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVapproveid String
	 */
	public void setVapproveid (String newVapproveid ) {
	 	this.vapproveid = newVapproveid;
	} 	  
	/**
	 * 属性pk_deptdoc的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getPk_deptdoc () {
		return pk_deptdoc;
	}   
	/**
	 * 属性pk_deptdoc的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newPk_deptdoc String
	 */
	public void setPk_deptdoc (String newPk_deptdoc ) {
	 	this.pk_deptdoc = newPk_deptdoc;
	} 	  
	/**
	 * 属性cess的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return UFDouble
	 */
	public UFDouble getCess () {
		return cess;
	}   
	/**
	 * 属性cess的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newCess UFDouble
	 */
	public void setCess (UFDouble newCess ) {
	 	this.cess = newCess;
	} 	  
	/**
	 * 属性preferentialtype的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getPreferentialtype () {
		return preferentialtype;
	}   
	/**
	 * 属性preferentialtype的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newPreferentialtype String
	 */
	public void setPreferentialtype (String newPreferentialtype ) {
	 	this.preferentialtype = newPreferentialtype;
	} 	  
	/**
	 * 属性balancedate的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return UFDate
	 */
	public UFDate getBalancedate () {
		return balancedate;
	}   
	/**
	 * 属性balancedate的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newBalancedate UFDate
	 */
	public void setBalancedate (UFDate newBalancedate ) {
	 	this.balancedate = newBalancedate;
	} 	  
	/**
	 * 属性vdef0的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getVdef0 () {
		return vdef0;
	}   
	/**
	 * 属性vdef0的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVdef0 String
	 */
	public void setVdef0 (String newVdef0 ) {
	 	this.vdef0 = newVdef0;
	} 	  
	/**
	 * 属性vdef7的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getVdef7 () {
		return vdef7;
	}   
	/**
	 * 属性vdef7的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVdef7 String
	 */
	public void setVdef7 (String newVdef7 ) {
	 	this.vdef7 = newVdef7;
	} 	  
	/**
	 * 属性pk_salescontract的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getPk_salescontract () {
		return pk_salescontract;
	}   
	/**
	 * 属性pk_salescontract的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newPk_salescontract String
	 */
	public void setPk_salescontract (String newPk_salescontract ) {
	 	this.pk_salescontract = newPk_salescontract;
	} 	  
	/**
	 * 属性pk_cubasdoc的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getPk_cubasdoc () {
		return pk_cubasdoc;
	}   
	/**
	 * 属性pk_cubasdoc的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newPk_cubasdoc String
	 */
	public void setPk_cubasdoc (String newPk_cubasdoc ) {
	 	this.pk_cubasdoc = newPk_cubasdoc;
	} 
	
	public String getPk_cumandoc() {
		return pk_cumandoc;
	}
	public void setPk_cumandoc(String pk_cumandoc) {
		this.pk_cumandoc = pk_cumandoc;
	}
	/**
	 * 属性vdef2的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return UFDouble
	 */
	public UFDouble getVdef2 () {
		return vdef2;
	}   
	/**
	 * 属性vdef2的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVdef2 UFDouble
	 */
	public void setVdef2 (UFDouble newVdef2 ) {
	 	this.vdef2 = newVdef2;
	} 	  
	/**
	 * 属性vdef5的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return UFDouble
	 */
	public UFDouble getVdef5 () {
		return vdef5;
	}   
	/**
	 * 属性vdef5的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVdef5 UFDouble
	 */
	public void setVdef5 (UFDouble newVdef5 ) {
	 	this.vdef5 = newVdef5;
	} 	  
	/**
	 * 属性specialyhjg的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return UFDouble
	 */
	public UFDouble getSpecialyhjg () {
		return specialyhjg;
	}   
	/**
	 * 属性specialyhjg的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newSpecialyhjg UFDouble
	 */
	public void setSpecialyhjg (UFDouble newSpecialyhjg ) {
	 	this.specialyhjg = newSpecialyhjg;
	} 	  
	/**
	 * 属性vbillstatus的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return UFDouble
	 */
	public Integer getVbillstatus () {
		return vbillstatus;
	}   
	/**
	 * 属性vbillstatus的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVbillstatus UFDouble
	 */
	public void setVbillstatus (Integer newVbillstatus ) {
	 	this.vbillstatus = newVbillstatus;
	} 	  
	/**
	 * 属性memo的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getMemo () {
		return memo;
	}   
	/**
	 * 属性memo的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newMemo String
	 */
	public void setMemo (String newMemo ) {
	 	this.memo = newMemo;
	} 	  
	/**
	 * 属性gwedate的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return UFDate
	 */
	public UFDate getGwedate () {
		return gwedate;
	}   
	/**
	 * 属性gwedate的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newGwedate UFDate
	 */
	public void setGwedate (UFDate newGwedate ) {
	 	this.gwedate = newGwedate;
	} 	  
  
	public UFDate getVapprovedate() {
		return vapprovedate;
	}
	public void setVapprovedate(UFDate vapprovedate) {
		this.vapprovedate = vapprovedate;
	}
	/**
	 * 属性vdef3的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return UFDouble
	 */
	public UFDouble getVdef3 () {
		return vdef3;
	}   
	/**
	 * 属性vdef3的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVdef3 UFDouble
	 */
	public void setVdef3 (UFDouble newVdef3 ) {
	 	this.vdef3 = newVdef3;
	} 	  
	  
	public UFDate getDmakedate() {
		return dmakedate;
	}
	public void setDmakedate(UFDate dmakedate) {
		this.dmakedate = dmakedate;
	}
	/**
	 * 属性vdef6的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getVdef6 () {
		return vdef6;
	}   
	/**
	 * 属性vdef6的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVdef6 String
	 */
	public void setVdef6 (String newVdef6 ) {
	 	this.vdef6 = newVdef6;
	} 	  
	/**
	 * 属性dr的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return UFDouble
	 */
	public Integer getDr () {
		return dr;
	}   
	/**
	 * 属性dr的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newDr UFDouble
	 */
	public void setDr (Integer newDr ) {
	 	this.dr = newDr;
	} 	  
	/**
	 * 属性vdef4的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return UFDouble
	 */
	public UFDouble getVdef4 () {
		return vdef4;
	}   
	/**
	 * 属性vdef4的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVdef4 UFDouble
	 */
	public void setVdef4 (UFDouble newVdef4 ) {
	 	this.vdef4 = newVdef4;
	} 	  
	/**
	 * 属性pk_operator的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public String getPk_operator () {
		return pk_operator;
	}   
	/**
	 * 属性pk_operator的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newPk_operator String
	 */
	public void setPk_operator (String newPk_operator ) {
	 	this.pk_operator = newPk_operator;
	} 	  
	/**
	 * 属性accounty的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return UFBoolean
	 */
	public UFBoolean getAccounty () {
		return accounty;
	}   
	/**
	 * 属性accounty的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newAccounty UFBoolean
	 */
	public void setAccounty (UFBoolean newAccounty ) {
	 	this.accounty = newAccounty;
	} 	  
	/**
	 * 属性vunapprovedate的Getter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @return String
	 */
	public UFDate getVunapprovedate () {
		return vunapprovedate;
	}   
	/**
	 * 属性vunapprovedate的Setter方法.
	 * 创建日期:2011-06-23 15:23:28
	 * @param newVunapprovedate String
	 */
	public void setVunapprovedate (UFDate newVunapprovedate ) {
	 	this.vunapprovedate = newVunapprovedate;
	} 	  
 
	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2011-06-23 15:23:28
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2011-06-23 15:23:28
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_sellpaymentjs";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2011-06-23 15:23:28
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "gt_sellpaymentjs";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2011-06-23 15:23:28
	  */
     public SellPaymentJSVO() {
		super();	
	}    
} 
