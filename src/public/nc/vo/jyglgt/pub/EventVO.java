package nc.vo.jyglgt.pub;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;

public class EventVO extends SuperVO { 

	 private String pk_event;//主键 

	 private String modifier_id;//修改人 

	 private UFDateTime modify_ts;//修改日期 

	 private Integer dr;//dr 

	 private String pk_corp;//pk_corp 

	 private UFDateTime ts;//ts 

	 private String pk_busitype;//业务类型 

	 private String billtype;//单据类型 

	 private Integer vbillstatus;//单据状态 

	 private String vbillcode;//单据号 
	 
	 private String sbillcode;//来源单据号 
	 
	 private String sfuncode;//来源功能节点号

	 private String pk_source;//来源单据id 

	 private Integer version;//版本 

	 private String svbilltype;//来源单据单据类型 

	 private String sbillname;//来源单据单据名称 

	 private String reson;//理由 

	 private String vmemo;//备注 

	 private Integer secleve;//密级 

	 private String pk_operator;//制单人 

	 private UFDate dmakedate;//制单日期 

	 private UFDateTime dmaketime;//制单时间 

	 private UFBoolean islock;//是否锁定 

	 private String lockman;//锁定人 

	 private String vdef1;//自定义1 

	 private String vdef2;//自定义2 

	 private String vdef3;//自定义3 

	 private String vdef4;//自定义4 

	 private String vdef5;//自定义5 

	 private String vdef6;//自定义6 

	 private String vdef7;//自定义7 

	 private String vdef8;//自定义8 

	 private String vdef9;//自定义9 

	 private String vapprovenote;//自定义10 

	 private String vdef10;//自定义10 

	 private Integer sortno;//排序 

	 private String vapproveid;//审核人 

	 private UFDate lockdate;//锁定日期 

	 private UFDate dapprovedate;//审核日期 
	 
	 private String pk_history;//历史数据主键

	 public String getPk_history() {
		return pk_history;
	 }
	 public void setPk_history(String pk_history) {
		this.pk_history = pk_history;
	 }

	 public void setPk_corp(String newpk_corp){
	     pk_corp = newpk_corp;
	 }
	 public String getPk_corp(){
	     	return pk_corp;
	 }
	 public void setVbillcode(String newvbillcode){
	     vbillcode = newvbillcode;
	 }
	 public String getVbillcode(){
	     	return vbillcode;
	 }
	 public void setVdef4(String newvdef4){
	     vdef4 = newvdef4;
	 }
	 public String getVdef4(){
	     	return vdef4;
	 }
	 public void setDmakedate(UFDate newdmakedate){
	     dmakedate = newdmakedate;
	 }
	 public UFDate getDmakedate(){
	     	return dmakedate;
	 }
	 public void setSortno(Integer newsortno){
	     sortno = newsortno;
	 }
	 public Integer getSortno(){
	     	return sortno;
	 }
	 public void setPk_event(String newpk_event){
	     pk_event = newpk_event;
	 }
	 public String getPk_event(){
	     	return pk_event;
	 }
	 public void setVdef7(String newvdef7){
	     vdef7 = newvdef7;
	 }
	 public String getVdef7(){
	     	return vdef7;
	 }
	 public void setBilltype(String newbilltype){
	     billtype = newbilltype;
	 }
	 public String getBilltype(){
	     	return billtype;
	 }
	 public void setDr(Integer newdr){
	     dr = newdr;
	 }
	 public Integer getDr(){
	     	return dr;
	 }
	 public void setSecleve(Integer newsecleve){
	     secleve = newsecleve;
	 }
	 public Integer getSecleve(){
	     	return secleve;
	 }
	 public void setSbillname(String newsbillname){
	     sbillname = newsbillname;
	 }
	 public String getSbillname(){
	     	return sbillname;
	 }
	 public void setVbillstatus(Integer newvbillstatus){
	     vbillstatus = newvbillstatus;
	 }
	 public Integer getVbillstatus(){
	     	return vbillstatus;
	 }
	 public void setPk_source(String newpk_source){
	     pk_source = newpk_source;
	 }
	 public String getPk_source(){
	     	return pk_source;
	 }
	 public void setVdef2(String newvdef2){
	     vdef2 = newvdef2;
	 }
	 public String getVdef2(){
	     	return vdef2;
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
	 public void setTs(UFDateTime newts){
	     ts = newts;
	 }
	 public UFDateTime getTs(){
	     	return ts;
	 }
	 public void setVdef1(String newvdef1){
	     vdef1 = newvdef1;
	 }
	 public String getVdef1(){
	     	return vdef1;
	 }
	 public void setPk_operator(String newpk_operator){
	     pk_operator = newpk_operator;
	 }
	 public String getPk_operator(){
	     	return pk_operator;
	 }
	 public void setIslock(UFBoolean newislock){
	     islock = newislock;
	 }
	 public UFBoolean getIslock(){
	     	return islock;
	 }
	 public void setLockdate(UFDate newlockdate){
	     lockdate = newlockdate;
	 }
	 public UFDate getLockdate(){
	     	return lockdate;
	 }
	 public void setVdef3(String newvdef3){
	     vdef3 = newvdef3;
	 }
	 public String getVdef3(){
	     	return vdef3;
	 }
	 public void setModify_ts(UFDateTime newmodify_ts){
	     modify_ts = newmodify_ts;
	 }
	 public UFDateTime getModify_ts(){
	     	return modify_ts;
	 }
	 public void setVdef9(String newvdef9){
	     vdef9 = newvdef9;
	 }
	 public String getVdef9(){
	     	return vdef9;
	 }
	 public void setDmaketime(UFDateTime newdmaketime){
	     dmaketime = newdmaketime;
	 }
	 public UFDateTime getDmaketime(){
	     	return dmaketime;
	 }
	 public void setVdef8(String newvdef8){
	     vdef8 = newvdef8;
	 }
	 public String getVdef8(){
	     	return vdef8;
	 }
	 public void setVersion(Integer newversion){
	     version = newversion;
	 }
	 public Integer getVersion(){
	     	return version;
	 }
	 public void setReson(String newreson){
	     reson = newreson;
	 }
	 public String getReson(){
	     	return reson;
	 }
	 public void setVapproveid(String newvapproveid){
	     vapproveid = newvapproveid;
	 }
	 public String getVapproveid(){
	     	return vapproveid;
	 }
	 public void setVdef6(String newvdef6){
	     vdef6 = newvdef6;
	 }
	 public String getVdef6(){
	     	return vdef6;
	 }
	 public void setModifier_id(String newmodifier_id){
	     modifier_id = newmodifier_id;
	 }
	 public String getModifier_id(){
	     	return modifier_id;
	 }
	 public void setLockman(String newlockman){
	     lockman = newlockman;
	 }
	 public String getLockman(){
	     	return lockman;
	 }
	 public UFDate getDapprovedate() {
		return dapprovedate;
	}
	public void setDapprovedate(UFDate dapprovedate) {
		this.dapprovedate = dapprovedate;
	}
	public void setVdef10(String newvdef10){
	     vdef10 = newvdef10;
	 }
	 public String getVdef10(){
	     	return vdef10;
	 }
	 public void setVdef5(String newvdef5){
	     vdef5 = newvdef5;
	 }
	 public String getVdef5(){
	     	return vdef5;
	 }
	 public void setSvbilltype(String newsvbilltype){
	     svbilltype = newsvbilltype;
	 }
	 public String getSvbilltype(){
	     	return svbilltype;
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
	 	  return "pk_event"; 
	 	} 
	      
	 	/** 
	 	 * <p>返回表名称. 
	 	 * <p> 
	 	 * 创建日期:2012-08-25 16:41:36 
	 	 * @return java.lang.String 
	 	 */ 
	 	public java.lang.String getTableName() { 
	 		return "csh_event"; 
	 	}     
	      
	     /** 
	 	  * 按照默认方式创建构造子. 
	 	  * 
	 	  * 创建日期:2012-08-25 16:41:36 
	 	  */ 
			public EventVO() { 
	 		super();	 
	 	}
		public void setVapprovenote(String vapprovenote) {
			this.vapprovenote = vapprovenote;
		}
		public String getVapprovenote() {
			return vapprovenote;
		}
		public void setSbillcode(String sbillcode) {
			this.sbillcode = sbillcode;
		}
		public String getSbillcode() {
			return sbillcode;
		}
		public void setSfuncode(String sfuncode) {
			this.sfuncode = sfuncode;
		}
		public String getSfuncode() {
			return sfuncode;
		} 
}
