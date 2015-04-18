package nc.vo.jyglgt.j40068b;

import nc.vo.pub.SuperVO; 
import nc.vo.pub.lang.UFDate; 
import nc.vo.pub.lang.UFDateTime; 
import nc.vo.pub.lang.UFDouble; 
import nc.vo.pub.lang.UFBoolean; 

public class PricediscountVO extends SuperVO { 

	 private String pk_pricediscount;//价格政策主键 

	 private String distype;//优惠类型 

	 private String custname;//客户名称 

	 private String pk_customer;//客户主键 

	 private UFDouble disprice;//优惠单价 

	 private String disreason;//优惠原因 

	 private String billtype;//单据类型 

	 private String pk_saleorder;//销售订单号 

	 private String pk_busitype;//业务类型 

	 private String vbillcode;//单据单号 

	 private String pk_dept;//提交部门 

	 private String commitmanid;//提交人 

	 private UFDate dbilldate;//单据日期 

	 private Integer vbillstatus;//单据状态 

	 private String pk_operator;//制单人 

	 private UFDate dmakedate;//制单日期 

	 private UFDateTime tmaketime;//制单时间 

	 private String vapproveid;//审核人 

	 private UFDate dapprovedate;//审核日期 

	 private UFDateTime vapprovetime;//审核时间 

	 private String vapprovednote;//审核意见 

	 private String vunapproveid;//弃审人 

	 private UFDate vunapprovedate;//弃审日期 

	 private UFDateTime vunapprovetime;//弃审时间 

	 private String vunapprovednote;//弃审意见 

	 private String modifyid;//修改人 

	 private UFDate modifydate;//修改日期 

	 private UFDateTime tmodifytime;//修改时间 

	 private String vmemo;//备注 

	 private UFDateTime ts;//时间 

	 private Integer dr;//删除标记 

	 private String pk_corp;//公司主键 

	 private UFDouble vdef1;//自定义项1 

	 private UFDouble vdef2;//自定义项2 

	 private UFDouble vdef3;//自定义项3 

	 private UFDouble vdef4;//自定义项4 

	 private UFDouble vdef5;//自定义项5 

	 private UFDate vdef6;//自定义项6 

	 private UFDate vdef7;//自定义项7 

	 private String vdef8;//自定义项8 

	 private String vdef9;//自定义项9 

	 private String vdef10;//自定义项10 

	 private String vdef11;//自定义项11 

	 private String vdef12;//自定义项12 

	 private String vdef13;//自定义项13 

	 private String vdef14;//自定义项14 

	 private String vdef15;//自定义项15 

	 private String vdef16;//自定义项16 

	 private String vdef17;//自定义项17 

	 private String vdef18;//自定义项18 

	 private String vdef19;//自定义项19 

	 private String vdef20;//自定义项20 

	 private String vdef21;//自定义项21 

	 private String vdef22;//自定义项22 

	 private String vdef23;//自定义项23 

	 private String vdef24;//自定义项24 

	 private String vdef25;//自定义项25 

	 private String vdef26;//自定义项26 

	 private String vdef27;//自定义项27 

	 private String vdef28;//自定义项28 

	 private String vdef29;//自定义项29 

	 private String vdef30;//自定义项30 

	 public void setPk_corp(String newpk_corp){
	     pk_corp = newpk_corp;
	 }
	 public String getPk_corp(){
	     	return pk_corp;
	 }
	 public void setVapprovetime(UFDateTime newvapprovetime){
	     vapprovetime = newvapprovetime;
	 }
	 public UFDateTime getVapprovetime(){
	     	return vapprovetime;
	 }
	 public void setDisprice(UFDouble newdisprice){
	     disprice = newdisprice;
	 }
	 public UFDouble getDisprice(){
	     	return disprice;
	 }
	 public void setDisreason(String newdisreason){
	     disreason = newdisreason;
	 }
	 public String getDisreason(){
	     	return disreason;
	 }
	 public void setVdef4(UFDouble newvdef4){
	     vdef4 = newvdef4;
	 }
	 public UFDouble getVdef4(){
	     	return vdef4;
	 }
	 public void setVdef18(String newvdef18){
	     vdef18 = newvdef18;
	 }
	 public String getVdef18(){
	     	return vdef18;
	 }
	 public void setDr(Integer newdr){
	     dr = newdr;
	 }
	 public Integer getDr(){
	     	return dr;
	 }
	 public void setVdef16(String newvdef16){
	     vdef16 = newvdef16;
	 }
	 public String getVdef16(){
	     	return vdef16;
	 }
	 public void setVdef17(String newvdef17){
	     vdef17 = newvdef17;
	 }
	 public String getVdef17(){
	     	return vdef17;
	 }
	 public void setVdef27(String newvdef27){
	     vdef27 = newvdef27;
	 }
	 public String getVdef27(){
	     	return vdef27;
	 }
	 public void setVdef12(String newvdef12){
	     vdef12 = newvdef12;
	 }
	 public String getVdef12(){
	     	return vdef12;
	 }
	 public void setModifydate(UFDate newmodifydate){
	     modifydate = newmodifydate;
	 }
	 public UFDate getModifydate(){
	     	return modifydate;
	 }
	 public void setVdef24(String newvdef24){
	     vdef24 = newvdef24;
	 }
	 public String getVdef24(){
	     	return vdef24;
	 }
	 public void setVapprovednote(String newvapprovednote){
	     vapprovednote = newvapprovednote;
	 }
	 public String getVapprovednote(){
	     	return vapprovednote;
	 }
	 public void setTs(UFDateTime newts){
	     ts = newts;
	 }
	 public UFDateTime getTs(){
	     	return ts;
	 }
	 public void setVdef25(String newvdef25){
	     vdef25 = newvdef25;
	 }
	 public String getVdef25(){
	     	return vdef25;
	 }
	 public void setModifyid(String newmodifyid){
	     modifyid = newmodifyid;
	 }
	 public String getModifyid(){
	     	return modifyid;
	 }
	 public void setVdef19(String newvdef19){
	     vdef19 = newvdef19;
	 }
	 public String getVdef19(){
	     	return vdef19;
	 }
	 public void setTmaketime(UFDateTime newtmaketime){
	     tmaketime = newtmaketime;
	 }
	 public UFDateTime getTmaketime(){
	     	return tmaketime;
	 }
	 public void setDistype(String newdistype){
	     distype = newdistype;
	 }
	 public String getDistype(){
	     	return distype;
	 }
	 public void setPk_saleorder(String newpk_saleorder){
	     pk_saleorder = newpk_saleorder;
	 }
	 public String getPk_saleorder(){
	     	return pk_saleorder;
	 }
	 public void setVdef3(UFDouble newvdef3){
	     vdef3 = newvdef3;
	 }
	 public UFDouble getVdef3(){
	     	return vdef3;
	 }
	 public void setVdef13(String newvdef13){
	     vdef13 = newvdef13;
	 }
	 public String getVdef13(){
	     	return vdef13;
	 }
	 public void setPk_dept(String newpk_dept){
	     pk_dept = newpk_dept;
	 }
	 public String getPk_dept(){
	     	return pk_dept;
	 }
	 public void setVdef28(String newvdef28){
	     vdef28 = newvdef28;
	 }
	 public String getVdef28(){
	     	return vdef28;
	 }
	 public void setVdef23(String newvdef23){
	     vdef23 = newvdef23;
	 }
	 public String getVdef23(){
	     	return vdef23;
	 }
	 public void setVdef29(String newvdef29){
	     vdef29 = newvdef29;
	 }
	 public String getVdef29(){
	     	return vdef29;
	 }
	 public void setVdef10(String newvdef10){
	     vdef10 = newvdef10;
	 }
	 public String getVdef10(){
	     	return vdef10;
	 }
	 public void setVdef5(UFDouble newvdef5){
	     vdef5 = newvdef5;
	 }
	 public UFDouble getVdef5(){
	     	return vdef5;
	 }
	 public void setVbillcode(String newvbillcode){
	     vbillcode = newvbillcode;
	 }
	 public String getVbillcode(){
	     	return vbillcode;
	 }
	 public void setVdef11(String newvdef11){
	     vdef11 = newvdef11;
	 }
	 public String getVdef11(){
	     	return vdef11;
	 }
	 public void setDmakedate(UFDate newdmakedate){
	     dmakedate = newdmakedate;
	 }
	 public UFDate getDmakedate(){
	     	return dmakedate;
	 }
	 public void setVunapprovednote(String newvunapprovednote){
	     vunapprovednote = newvunapprovednote;
	 }
	 public String getVunapprovednote(){
	     	return vunapprovednote;
	 }
	 public void setBilltype(String newbilltype){
	     billtype = newbilltype;
	 }
	 public String getBilltype(){
	     	return billtype;
	 }
	 public void setCommitmanid(String newcommitmanid){
	     commitmanid = newcommitmanid;
	 }
	 public String getCommitmanid(){
	     	return commitmanid;
	 }

	 public void setPk_customer(String newpk_customer){
	     pk_customer = newpk_customer;
	 }
	 public String getPk_customer(){
	     	return pk_customer;
	 }
	 public void setCustname(String newcustname){
	     custname = newcustname;
	 }
	 public String getCustname(){
	     	return custname;
	 }
	 public void setVdef20(String newvdef20){
	     vdef20 = newvdef20;
	 }
	 public String getVdef20(){
	     	return vdef20;
	 }
	 public void setVbillstatus(Integer newvbillstatus){
	     vbillstatus = newvbillstatus;
	 }
	 public Integer getVbillstatus(){
	     	return vbillstatus;
	 }
	 public void setVdef2(UFDouble newvdef2){
	     vdef2 = newvdef2;
	 }
	 public UFDouble getVdef2(){
	     	return vdef2;
	 }
	 public void setPk_pricediscount(String newpk_pricediscount){
	     pk_pricediscount = newpk_pricediscount;
	 }
	 public String getPk_pricediscount(){
	     	return pk_pricediscount;
	 }
	 public void setTmodifytime(UFDateTime newtmodifytime){
	     tmodifytime = newtmodifytime;
	 }
	 public UFDateTime getTmodifytime(){
	     	return tmodifytime;
	 }
	 public void setVmemo(String newvmemo){
	     vmemo = newvmemo;
	 }
	 public String getVmemo(){
	     	return vmemo;
	 }
	 public void setPk_busitype(String newpk_busitype){
	     pk_busitype = newpk_busitype;
	 }
	 public String getPk_busitype(){
	     	return pk_busitype;
	 }
	 public void setVdef1(UFDouble newvdef1){
	     vdef1 = newvdef1;
	 }
	 public UFDouble getVdef1(){
	     	return vdef1;
	 }
	 public void setPk_operator(String newpk_operator){
	     pk_operator = newpk_operator;
	 }
	 public String getPk_operator(){
	     	return pk_operator;
	 }
	 public void setVdef26(String newvdef26){
	     vdef26 = newvdef26;
	 }
	 public String getVdef26(){
	     	return vdef26;
	 }
	 public void setDbilldate(UFDate newdbilldate){
	     dbilldate = newdbilldate;
	 }
	 public UFDate getDbilldate(){
	     	return dbilldate;
	 }
	 public void setVunapprovetime(UFDateTime newvunapprovetime){
	     vunapprovetime = newvunapprovetime;
	 }
	 public UFDateTime getVunapprovetime(){
	     	return vunapprovetime;
	 }
	 public void setVdef14(String newvdef14){
	     vdef14 = newvdef14;
	 }
	 public String getVdef14(){
	     	return vdef14;
	 }
	 public void setVdef21(String newvdef21){
	     vdef21 = newvdef21;
	 }
	 public String getVdef21(){
	     	return vdef21;
	 }
	 public void setVdef9(String newvdef9){
	     vdef9 = newvdef9;
	 }
	 public String getVdef9(){
	     	return vdef9;
	 }
	 public void setDapprovedate(UFDate newdapprovedate){
	     dapprovedate = newdapprovedate;
	 }
	 public UFDate getDapprovedate(){
	     	return dapprovedate;
	 }
	 public void setVdef8(String newvdef8){
	     vdef8 = newvdef8;
	 }
	 public String getVdef8(){
	     	return vdef8;
	 }
	 public void setVunapproveid(String newvunapproveid){
	     vunapproveid = newvunapproveid;
	 }
	 public String getVunapproveid(){
	     	return vunapproveid;
	 }
	 public void setVdef30(String newvdef30){
	     vdef30 = newvdef30;
	 }
	 public String getVdef30(){
	     	return vdef30;
	 }
	 public void setVapproveid(String newvapproveid){
	     vapproveid = newvapproveid;
	 }
	 public String getVapproveid(){
	     	return vapproveid;
	 }

	 public UFDate getVdef6() {
		return vdef6;
	}
	public void setVdef6(UFDate vdef6) {
		this.vdef6 = vdef6;
	}
	public UFDate getVdef7() {
		return vdef7;
	}
	public void setVdef7(UFDate vdef7) {
		this.vdef7 = vdef7;
	}
	public void setVdef15(String newvdef15){
	     vdef15 = newvdef15;
	 }
	 public String getVdef15(){
	     	return vdef15;
	 }
	 public void setVunapprovedate(UFDate newvunapprovedate){
	     vunapprovedate = newvunapprovedate;
	 }
	 public UFDate getVunapprovedate(){
	     	return vunapprovedate;
	 }
	 public void setVdef22(String newvdef22){
	     vdef22 = newvdef22;
	 }
	 public String getVdef22(){
	     	return vdef22;
	 }
	 	 /** 
	 	  * <p>取得父VO主键字段. 
	 	  * <p> 
	 	  * 创建日期:2012-08-25 16:41:36 
	 	  * @return java.lang.String 
	 	  */ 
	 	public java.lang.String getParentPKFieldName() { 
	 	    return null; 
	 	} 
	 	  /** 
	 	  * <p>取得表主键. 
	 	  * <p> 
	 	  * 创建日期:2012-08-25 16:41:36 
	 	  * @return java.lang.String 
	 	  */ 
	 	public java.lang.String getPKFieldName() { 
	 	  return "pk_pricediscount"; 
	 	} 
	      
	 	/** 
	 	 * <p>返回表名称. 
	 	 * <p> 
	 	 * 创建日期:2012-08-25 16:41:36 
	 	 * @return java.lang.String 
	 	 */ 
	 	public java.lang.String getTableName() { 
	 		return "jyglgt_pricediscount"; 
	 	}     
	      
	     /** 
	 	  * 按照默认方式创建构造子. 
	 	  * 
	 	  * 创建日期:2012-08-25 16:41:36 
	 	  */ 
			public PricediscountVO() { 
	 		super();	 
	 	} 
}
