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
 * 创建日期:2011-06-23 15:23:29
 * @author Administrator
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class BalanceInfoBVO extends SuperVO {
	private String pk_corp;
	private UFDateTime ts;
	private String vfree2;
	private UFDouble vdef9;
	private String vdef17;
	private UFDate grossdate;
	private String vdef20;
	private UFDouble vdef1;
	private UFDouble vdef8;
	private UFDouble vdef10;
	private String vfree4;
	private String pk_sellpaymentjs;
	private UFDouble preferentialmny;
	private UFDouble preferentialprice;
	private String vdef15;
	private UFDouble price;
	private String vfree5;
	private String vdef14;
	private String pk_balanceinfo_b;
	private UFDouble vdef7;
	private String jstype;
	private String vdef13;
	private String vfree3;
	private String vfree1;
	private UFDouble vdef2;
	private String vdef16;
	private UFDouble vdef5;
	private String vdef11;
	private UFDouble num;
	private String vdef19;
	private String pk_invbasdoc;
	private String memo;
	private UFDouble vdef3;
	private String vdef12;
	private UFDouble vdef6;
	private Integer dr;
	private UFDouble vdef4;
	private String vdef18;
	private UFDouble moneys;
	private String pk_invmandoc;
	private UFDouble suttle;
	private UFDouble invoicenum;
	private UFDouble weight;
	private String pk_stordoc;
	private String pk_cargdoc;
	private String vfree6;
	private String vfree7;
	private String vfree8;
	private String vfree9;
	private String vfree10;
	public String getPk_stordoc() {
		return pk_stordoc;
	}
	public void setPk_stordoc(String pk_stordoc) {
		this.pk_stordoc = pk_stordoc;
	}
	public String getPk_cargdoc() {
		return pk_cargdoc;
	}
	public void setPk_cargdoc(String pk_cargdoc) {
		this.pk_cargdoc = pk_cargdoc;
	}

	public static final String PK_CORP = "pk_corp";
	public static final String VFREE2 = "vfree2";
	public static final String VDEF9 = "vdef9";
	public static final String VDEF17 = "vdef17";
	public static final String GROSSDATE = "grossdate";
	public static final String VDEF20 = "vdef20";
	public static final String VDEF1 = "vdef1";
	public static final String VDEF8 = "vdef8";
	public static final String VDEF10 = "vdef10";
	public static final String VFREE4 = "vfree4";
	public static final String PK_SELLPAYMENTJS = "pk_sellpaymentjs";
	public static final String PREFERENTIALMNY = "preferentialmny";
	public static final String PREFERENTIALPRICE = "preferentialprice";
	public static final String VDEF15 = "vdef15";
	public static final String PRICE = "price";
	public static final String VFREE5 = "vfree5";
	public static final String VDEF14 = "vdef14";
	public static final String PK_BALANCEINFO_B = "pk_balanceinfo_b";
	public static final String VDEF7 = "vdef7";
	public static final String JSTYPE = "jstype";
	public static final String VDEF13 = "vdef13";
	public static final String VFREE3 = "vfree3";
	public static final String VFREE1 = "vfree1";
	public static final String VDEF2 = "vdef2";
	public static final String VDEF16 = "vdef16";
	public static final String VDEF5 = "vdef5";
	public static final String VDEF11 = "vdef11";
	public static final String NUM = "num";
	public static final String VDEF19 = "vdef19";
	public static final String PK_INVBASDOC = "pk_invbasdoc";
	public static final String MEMO = "memo";
	public static final String VDEF3 = "vdef3";
	public static final String VDEF12 = "vdef12";
	public static final String VDEF6 = "vdef6";
	public static final String VDEF4 = "vdef4";
	public static final String VDEF18 = "vdef18";
	public static final String MONEYS = "moneys";
	public static final String PK_INVMANDOC = "pk_invmandoc";
	public static final String SUTTLE = "suttle";
	public static final String INVOICENUM = "invoicenum";
	public static final String WEIGHT = "weight";
	public static final String PK_CARGDOC = "pk_cargdoc";
	public static final String PK_STORDOC = "pk_stordoc";
	public static final String VFREE6 = "vfree6";
	public static final String VFREE7 = "vfree7";
	public static final String VFREE8 = "vfree8";
	public static final String VFREE9 = "vfree9";
	public static final String VFREE10 = "vfree10";
		
	
	public String getVfree6() {
		return vfree6;
	}
	public void setVfree6(String vfree6) {
		this.vfree6 = vfree6;
	}
	public String getVfree7() {
		return vfree7;
	}
	public void setVfree7(String vfree7) {
		this.vfree7 = vfree7;
	}
	public String getVfree8() {
		return vfree8;
	}
	public void setVfree8(String vfree8) {
		this.vfree8 = vfree8;
	}
	public String getVfree9() {
		return vfree9;
	}
	public void setVfree9(String vfree9) {
		this.vfree9 = vfree9;
	}
	public String getVfree10() {
		return vfree10;
	}
	public void setVfree10(String vfree10) {
		this.vfree10 = vfree10;
	}
	/**
	 * 属性pk_corp的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getPk_corp () {
		return pk_corp;
	}   
	/**
	 * 属性pk_corp的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newPk_corp String
	 */
	public void setPk_corp (String newPk_corp ) {
	 	this.pk_corp = newPk_corp;
	} 	  
	/**
	 * 属性ts的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDateTime
	 */
	public UFDateTime getTs () {
		return ts;
	}   
	/**
	 * 属性ts的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newTs UFDateTime
	 */
	public void setTs (UFDateTime newTs ) {
	 	this.ts = newTs;
	} 	  
	/**
	 * 属性vfree2的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getVfree2 () {
		return vfree2;
	}   
	/**
	 * 属性vfree2的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVfree2 String
	 */
	public void setVfree2 (String newVfree2 ) {
	 	this.vfree2 = newVfree2;
	} 	  
	/**
	 * 属性vdef9的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getVdef9 () {
		return vdef9;
	}   
	/**
	 * 属性vdef9的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef9 UFDouble
	 */
	public void setVdef9 (UFDouble newVdef9 ) {
	 	this.vdef9 = newVdef9;
	} 	  
	/**
	 * 属性vdef17的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getVdef17 () {
		return vdef17;
	}   
	/**
	 * 属性vdef17的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef17 String
	 */
	public void setVdef17 (String newVdef17 ) {
	 	this.vdef17 = newVdef17;
	} 	  
	/**
	 * 属性grossdate的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDate
	 */
	public UFDate getGrossdate () {
		return grossdate;
	}   
	/**
	 * 属性grossdate的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newGrossdate UFDate
	 */
	public void setGrossdate (UFDate newGrossdate ) {
	 	this.grossdate = newGrossdate;
	} 	  
	/**
	 * 属性vdef20的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getVdef20 () {
		return vdef20;
	}   
	/**
	 * 属性vdef20的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef20 String
	 */
	public void setVdef20 (String newVdef20 ) {
	 	this.vdef20 = newVdef20;
	} 	  
	/**
	 * 属性vdef1的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getVdef1 () {
		return vdef1;
	}   
	/**
	 * 属性vdef1的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef1 UFDouble
	 */
	public void setVdef1 (UFDouble newVdef1 ) {
	 	this.vdef1 = newVdef1;
	} 	  
	/**
	 * 属性vdef8的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getVdef8 () {
		return vdef8;
	}   
	/**
	 * 属性vdef8的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef8 UFDouble
	 */
	public void setVdef8 (UFDouble newVdef8 ) {
	 	this.vdef8 = newVdef8;
	} 	  
	/**
	 * 属性vdef10的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getVdef10 () {
		return vdef10;
	}   
	/**
	 * 属性vdef10的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef10 UFDouble
	 */
	public void setVdef10 (UFDouble newVdef10 ) {
	 	this.vdef10 = newVdef10;
	} 	  
	/**
	 * 属性vfree4的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getVfree4 () {
		return vfree4;
	}   
	/**
	 * 属性vfree4的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVfree4 String
	 */
	public void setVfree4 (String newVfree4 ) {
	 	this.vfree4 = newVfree4;
	} 	  
	/**
	 * 属性pk_sellpaymentjs的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getPk_sellpaymentjs () {
		return pk_sellpaymentjs;
	}   
	/**
	 * 属性pk_sellpaymentjs的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newPk_sellpaymentjs String
	 */
	public void setPk_sellpaymentjs (String newPk_sellpaymentjs ) {
	 	this.pk_sellpaymentjs = newPk_sellpaymentjs;
	} 	  
	/**
	 * 属性preferentialmny的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getPreferentialmny () {
		return preferentialmny;
	}   
	/**
	 * 属性preferentialmny的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newPreferentialmny UFDouble
	 */
	public void setPreferentialmny (UFDouble newPreferentialmny ) {
	 	this.preferentialmny = newPreferentialmny;
	} 	  
	/**
	 * 属性preferentialprice的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getPreferentialprice () {
		return preferentialprice;
	}   
	/**
	 * 属性preferentialprice的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newPreferentialprice UFDouble
	 */
	public void setPreferentialprice (UFDouble newPreferentialprice ) {
	 	this.preferentialprice = newPreferentialprice;
	} 	  
	/**
	 * 属性vdef15的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getVdef15 () {
		return vdef15;
	}   
	/**
	 * 属性vdef15的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef15 String
	 */
	public void setVdef15 (String newVdef15 ) {
	 	this.vdef15 = newVdef15;
	} 	  
	/**
	 * 属性price的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getPrice () {
		return price;
	}   
	/**
	 * 属性price的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newPrice UFDouble
	 */
	public void setPrice (UFDouble newPrice ) {
	 	this.price = newPrice;
	} 	  
	/**
	 * 属性vfree5的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getVfree5 () {
		return vfree5;
	}   
	/**
	 * 属性vfree5的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVfree5 String
	 */
	public void setVfree5 (String newVfree5 ) {
	 	this.vfree5 = newVfree5;
	} 	  
	/**
	 * 属性vdef14的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getVdef14 () {
		return vdef14;
	}   
	/**
	 * 属性vdef14的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef14 String
	 */
	public void setVdef14 (String newVdef14 ) {
	 	this.vdef14 = newVdef14;
	} 	  
	/**
	 * 属性pk_balanceinfo_b的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getPk_balanceinfo_b () {
		return pk_balanceinfo_b;
	}   
	/**
	 * 属性pk_balanceinfo_b的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newPk_balanceinfo_b String
	 */
	public void setPk_balanceinfo_b (String newPk_balanceinfo_b ) {
	 	this.pk_balanceinfo_b = newPk_balanceinfo_b;
	} 	  
	/**
	 * 属性vdef7的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getVdef7 () {
		return vdef7;
	}   
	/**
	 * 属性vdef7的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef7 UFDouble
	 */
	public void setVdef7 (UFDouble newVdef7 ) {
	 	this.vdef7 = newVdef7;
	} 	  
	/**
	 * 属性jstype的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getJstype () {
		return jstype;
	}   
	/**
	 * 属性jstype的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newJstype String
	 */
	public void setJstype (String newJstype ) {
	 	this.jstype = newJstype;
	} 	  
	/**
	 * 属性vdef13的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getVdef13 () {
		return vdef13;
	}   
	/**
	 * 属性vdef13的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef13 String
	 */
	public void setVdef13 (String newVdef13 ) {
	 	this.vdef13 = newVdef13;
	} 	  
	/**
	 * 属性vfree3的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getVfree3 () {
		return vfree3;
	}   
	/**
	 * 属性vfree3的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVfree3 String
	 */
	public void setVfree3 (String newVfree3 ) {
	 	this.vfree3 = newVfree3;
	} 	  
	/**
	 * 属性vfree1的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getVfree1 () {
		return vfree1;
	}   
	/**
	 * 属性vfree1的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVfree1 String
	 */
	public void setVfree1 (String newVfree1 ) {
	 	this.vfree1 = newVfree1;
	} 	  
	/**
	 * 属性vdef2的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getVdef2 () {
		return vdef2;
	}   
	/**
	 * 属性vdef2的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef2 UFDouble
	 */
	public void setVdef2 (UFDouble newVdef2 ) {
	 	this.vdef2 = newVdef2;
	} 	  
	/**
	 * 属性vdef16的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getVdef16 () {
		return vdef16;
	}   
	/**
	 * 属性vdef16的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef16 String
	 */
	public void setVdef16 (String newVdef16 ) {
	 	this.vdef16 = newVdef16;
	} 	  
	/**
	 * 属性vdef5的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getVdef5 () {
		return vdef5;
	}   
	/**
	 * 属性vdef5的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef5 UFDouble
	 */
	public void setVdef5 (UFDouble newVdef5 ) {
	 	this.vdef5 = newVdef5;
	} 	  
	/**
	 * 属性vdef11的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getVdef11 () {
		return vdef11;
	}   
	/**
	 * 属性vdef11的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef11 String
	 */
	public void setVdef11 (String newVdef11 ) {
	 	this.vdef11 = newVdef11;
	} 	  
	/**
	 * 属性num的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getNum () {
		return num;
	}   
	/**
	 * 属性num的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newNum UFDouble
	 */
	public void setNum (UFDouble newNum ) {
	 	this.num = newNum;
	} 	  
	/**
	 * 属性vdef19的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getVdef19 () {
		return vdef19;
	}   
	/**
	 * 属性vdef19的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef19 String
	 */
	public void setVdef19 (String newVdef19 ) {
	 	this.vdef19 = newVdef19;
	} 	  
	/**
	 * 属性pk_invbasdoc的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getPk_invbasdoc () {
		return pk_invbasdoc;
	}   
	/**
	 * 属性pk_invbasdoc的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newPk_invbasdoc String
	 */
	public void setPk_invbasdoc (String newPk_invbasdoc ) {
	 	this.pk_invbasdoc = newPk_invbasdoc;
	} 	  
	/**
	 * 属性memo的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getMemo () {
		return memo;
	}   
	/**
	 * 属性memo的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newMemo String
	 */
	public void setMemo (String newMemo ) {
	 	this.memo = newMemo;
	} 	  
	/**
	 * 属性vdef3的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getVdef3 () {
		return vdef3;
	}   
	/**
	 * 属性vdef3的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef3 UFDouble
	 */
	public void setVdef3 (UFDouble newVdef3 ) {
	 	this.vdef3 = newVdef3;
	} 	  
	/**
	 * 属性vdef12的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getVdef12 () {
		return vdef12;
	}   
	/**
	 * 属性vdef12的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef12 String
	 */
	public void setVdef12 (String newVdef12 ) {
	 	this.vdef12 = newVdef12;
	} 	  
	/**
	 * 属性vdef6的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getVdef6 () {
		return vdef6;
	}   
	/**
	 * 属性vdef6的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef6 UFDouble
	 */
	public void setVdef6 (UFDouble newVdef6 ) {
	 	this.vdef6 = newVdef6;
	} 	  
	/**
	 * 属性dr的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public Integer getDr () {
		return dr;
	}   
	/**
	 * 属性dr的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newDr UFDouble
	 */
	public void setDr (Integer newDr ) {
	 	this.dr = newDr;
	} 	  
	/**
	 * 属性vdef4的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getVdef4 () {
		return vdef4;
	}   
	/**
	 * 属性vdef4的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef4 UFDouble
	 */
	public void setVdef4 (UFDouble newVdef4 ) {
	 	this.vdef4 = newVdef4;
	} 	  
	/**
	 * 属性vdef18的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getVdef18 () {
		return vdef18;
	}   
	/**
	 * 属性vdef18的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newVdef18 String
	 */
	public void setVdef18 (String newVdef18 ) {
	 	this.vdef18 = newVdef18;
	} 	  
	/**
	 * 属性moneys的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getMoneys () {
		return moneys;
	}   
	/**
	 * 属性moneys的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newMoneys UFDouble
	 */
	public void setMoneys (UFDouble newMoneys ) {
	 	this.moneys = newMoneys;
	} 	  
	/**
	 * 属性pk_invmandoc的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return String
	 */
	public String getPk_invmandoc () {
		return pk_invmandoc;
	}   
	/**
	 * 属性pk_invmandoc的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newPk_invmandoc String
	 */
	public void setPk_invmandoc (String newPk_invmandoc ) {
	 	this.pk_invmandoc = newPk_invmandoc;
	} 	  
	/**
	 * 属性suttle的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getSuttle () {
		return suttle;
	}   
	/**
	 * 属性suttle的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newSuttle UFDouble
	 */
	public void setSuttle (UFDouble newSuttle ) {
	 	this.suttle = newSuttle;
	} 	  
	/**
	 * 属性invoicenum的Getter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @return UFDouble
	 */
	public UFDouble getInvoicenum () {
		return invoicenum;
	}   
	/**
	 * 属性invoicenum的Setter方法.
	 * 创建日期:2011-06-23 15:23:29
	 * @param newInvoicenum UFDouble
	 */
	public void setInvoicenum (UFDouble newInvoicenum ) {
	 	this.invoicenum = newInvoicenum;
	} 	
	
 
	public UFDouble getWeight() {
		return weight;
	}
	public void setWeight(UFDouble weight) {
		this.weight = weight;
	}
	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:2011-06-23 15:23:29
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
		return "pk_sellpaymentjs";
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:2011-06-23 15:23:29
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_balanceinfo_b";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:2011-06-23 15:23:29
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "gt_balanceinfo_b";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:2011-06-23 15:23:29
	  */
     public BalanceInfoBVO() {
		super();	
	}    
} 
