package nc.vo.jyglgt.j400420;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

public class PosanctionVO extends SuperVO { 

	 private String pk_posanction;//主键 

	 private String cdptid;//部门 

	 private String sanctiontype;//奖罚类别 

	 private String concode;//合同号 
	 
	 private String pk_order ;

	 private String pocode;//采购订单号 
	 
	 private String pk_invbasdoc;

	 private String invname;//存货名称 

	 private UFDouble bucklenum;//扣重 

	 private UFDouble price;//单价 

	 private UFDouble mny;//金额 

	 private UFDouble sanctionprice;//奖罚单价 

	 private UFDouble sanctionmny;//奖罚金额 

	 private String reason;//申请原因 

	 private String pk_busitype;//业务类型 

	 private String vbillcode;//单据单号 

	 private String cbillname;//单据名称 

	 private UFDate dbilldate;//单据日期 

	 private Integer vbillstatus;//单据状态 

	 private String billtype;//单据类型 

	 private String pk_operator;//制单人 

	 private UFDate dmakedate;//制单日期 

	 private UFDateTime tmaketime;//制单时间 

	 private String modifyid;//修改人 

	 private UFDate modifydate;//修改日期 

	 private UFDateTime tmodifytime;//修改时间 

	 private String vapproveid;//审核人 

	 private UFDate dapprovedate;//审核日期 

	 private UFDateTime vapprovetime;//审核时间 

	 private String vapprovenote;//审核意见 

	 private String vunapproveid;//弃审人 

	 private UFDate vunapprovedate;//弃审日期 

	 private UFDateTime vunapprovetime;//弃审时间 

	 private String vunapprovednote;//弃审意见 

	 private String vmemo;//备注 

	 private UFDateTime ts;//时间戳 

	 private Integer dr;//删除标志 

	 private String pk_corp;//公司 

	 private UFDouble vdef1;//自定义项1 

	 private UFDouble vdef2;//自定义项2 

	 private UFDouble vdef3;//自定义项3 

	 private UFDouble vdef4;//自定义项4 

	 private UFDouble vdef5;//自定义项5 

	 private String vdef6;//自定义项6 

	 private String vdef7;//自定义项7 

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

	 
	 
	 
	 public String getPk_order() {
		return pk_order;
	}
	public void setPk_order(String pk_order) {
		this.pk_order = pk_order;
	}
	public String getPk_invbasdoc() {
		return pk_invbasdoc;
	}
	public void setPk_invbasdoc(String pk_invbasdoc) {
		this.pk_invbasdoc = pk_invbasdoc;
	}
	public void setSanctionmny(UFDouble newsanctionmny){
	     sanctionmny = newsanctionmny;
	 }
	 public UFDouble getSanctionmny(){
	     	return sanctionmny;
	 }
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
	 public void setVdef17(String newvdef17){
	     vdef17 = newvdef17;
	 }
	 public String getVdef17(){
	     	return vdef17;
	 }
	 public void setVdef16(String newvdef16){
	     vdef16 = newvdef16;
	 }
	 public String getVdef16(){
	     	return vdef16;
	 }
	 public void setVapprovenote(String newvapprovenote){
	     vapprovenote = newvapprovenote;
	 }
	 public String getVapprovenote(){
	     	return vapprovenote;
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
	 public void setSanctiontype(String newsanctiontype){
	     sanctiontype = newsanctiontype;
	 }
	 public String getSanctiontype(){
	     	return sanctiontype;
	 }
	 public void setTs(UFDateTime newts){
	     ts = newts;
	 }
	 public UFDateTime getTs(){
	     	return ts;
	 }
	 public void setPrice(UFDouble newprice){
	     price = newprice;
	 }
	 public UFDouble getPrice(){
	     	return price;
	 }
	 public void setMny(UFDouble newmny){
	     mny = newmny;
	 }
	 public UFDouble getMny(){
	     	return mny;
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
	 public void setCbillname(String newcbillname){
	     cbillname = newcbillname;
	 }
	 public String getCbillname(){
	     	return cbillname;
	 }
	 public void setReason(String newreason){
	     reason = newreason;
	 }
	 public String getReason(){
	     	return reason;
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
	 public void setSanctionprice(UFDouble newsanctionprice){
	     sanctionprice = newsanctionprice;
	 }
	 public UFDouble getSanctionprice(){
	     	return sanctionprice;
	 }
	 public void setPk_posanction(String newpk_posanction){
	     pk_posanction = newpk_posanction;
	 }
	 public String getPk_posanction(){
	     	return pk_posanction;
	 }
	 public void setVbillcode(String newvbillcode){
	     vbillcode = newvbillcode;
	 }
	 public String getVbillcode(){
	     	return vbillcode;
	 }
	 public void setPocode(String newpocode){
	     pocode = newpocode;
	 }
	 public String getPocode(){
	     	return pocode;
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
	 public void setBilltype(String newbilltype){
	     billtype = newbilltype;
	 }
	 public String getBilltype(){
	     	return billtype;
	 }
	 public void setVunapprovednote(String newvunapprovednote){
	     vunapprovednote = newvunapprovednote;
	 }
	 public String getVunapprovednote(){
	     	return vunapprovednote;
	 }
	 public void setVdef7(String newvdef7){
	     vdef7 = newvdef7;
	 }
	 public String getVdef7(){
	     	return vdef7;
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
	 public void setCdptid(String newcdptid){
	     cdptid = newcdptid;
	 }
	 public String getCdptid(){
	     	return cdptid;
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
	 public void setBucklenum(UFDouble newbucklenum){
	     bucklenum = newbucklenum;
	 }
	 public UFDouble getBucklenum(){
	     	return bucklenum;
	 }
	 public void setPk_operator(String newpk_operator){
	     pk_operator = newpk_operator;
	 }
	 public String getPk_operator(){
	     	return pk_operator;
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
	 public void setInvname(String newinvname){
	     invname = newinvname;
	 }
	 public String getInvname(){
	     	return invname;
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
	 public void setVapproveid(String newvapproveid){
	     vapproveid = newvapproveid;
	 }
	 public String getVapproveid(){
	     	return vapproveid;
	 }
	 public void setConcode(String newconcode){
	     concode = newconcode;
	 }
	 public String getConcode(){
	     	return concode;
	 }
	 public void setVdef6(String newvdef6){
	     vdef6 = newvdef6;
	 }
	 public String getVdef6(){
	     	return vdef6;
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
	 	  return "pk_posanction"; 
	 	} 
	      
	 	/** 
	 	 * <p>返回表名称. 
	 	 * <p> 
	 	 * 创建日期:2012-08-25 16:41:36 
	 	 * @return java.lang.String 
	 	 */ 
	 	public java.lang.String getTableName() { 
	 		return "jyglgt_posanction"; 
	 	}     
	      
	     /** 
	 	  * 按照默认方式创建构造子. 
	 	  * 
	 	  * 创建日期:2012-08-25 16:41:36 
	 	  */ 
			public PosanctionVO() { 
	 		super();	 
	 	} 
}
