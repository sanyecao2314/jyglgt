package nc.vo.jyglgt.j400420;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

public class PosanctionVO extends SuperVO { 

	 private String pk_posanction;//���� 

	 private String cdptid;//���� 

	 private String sanctiontype;//������� 

	 private String concode;//��ͬ�� 
	 
	 private String pk_order ;

	 private String pocode;//�ɹ������� 
	 
	 private String pk_invbasdoc;

	 private String invname;//������� 

	 private UFDouble bucklenum;//���� 

	 private UFDouble price;//���� 

	 private UFDouble mny;//��� 

	 private UFDouble sanctionprice;//�������� 

	 private UFDouble sanctionmny;//������� 

	 private String reason;//����ԭ�� 

	 private String pk_busitype;//ҵ������ 

	 private String vbillcode;//���ݵ��� 

	 private String cbillname;//�������� 

	 private UFDate dbilldate;//�������� 

	 private Integer vbillstatus;//����״̬ 

	 private String billtype;//�������� 

	 private String pk_operator;//�Ƶ��� 

	 private UFDate dmakedate;//�Ƶ����� 

	 private UFDateTime tmaketime;//�Ƶ�ʱ�� 

	 private String modifyid;//�޸��� 

	 private UFDate modifydate;//�޸����� 

	 private UFDateTime tmodifytime;//�޸�ʱ�� 

	 private String vapproveid;//����� 

	 private UFDate dapprovedate;//������� 

	 private UFDateTime vapprovetime;//���ʱ�� 

	 private String vapprovenote;//������ 

	 private String vunapproveid;//������ 

	 private UFDate vunapprovedate;//�������� 

	 private UFDateTime vunapprovetime;//����ʱ�� 

	 private String vunapprovednote;//������� 

	 private String vmemo;//��ע 

	 private UFDateTime ts;//ʱ��� 

	 private Integer dr;//ɾ����־ 

	 private String pk_corp;//��˾ 

	 private UFDouble vdef1;//�Զ�����1 

	 private UFDouble vdef2;//�Զ�����2 

	 private UFDouble vdef3;//�Զ�����3 

	 private UFDouble vdef4;//�Զ�����4 

	 private UFDouble vdef5;//�Զ�����5 

	 private String vdef6;//�Զ�����6 

	 private String vdef7;//�Զ�����7 

	 private String vdef8;//�Զ�����8 

	 private String vdef9;//�Զ�����9 

	 private String vdef10;//�Զ�����10 

	 private String vdef11;//�Զ�����11 

	 private String vdef12;//�Զ�����12 

	 private String vdef13;//�Զ�����13 

	 private String vdef14;//�Զ�����14 

	 private String vdef15;//�Զ�����15 

	 private String vdef16;//�Զ�����16 

	 private String vdef17;//�Զ�����17 

	 private String vdef18;//�Զ�����18 

	 private String vdef19;//�Զ�����19 

	 private String vdef20;//�Զ�����20 

	 
	 
	 
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
	 	  * <p>ȡ�ø�VO�����ֶ�. 
	 	  * <p> 
	 	  * ��������:2012-08-25 16:41:36 
	 	  * @return java.lang.String 
	 	  */ 
	 	public java.lang.String getParentPKFieldName() { 
	 	    return null; 
	 	} 
	 	  /** 
	 	  * <p>ȡ�ñ�����. 
	 	  * <p> 
	 	  * ��������:2012-08-25 16:41:36 
	 	  * @return java.lang.String 
	 	  */ 
	 	public java.lang.String getPKFieldName() { 
	 	  return "pk_posanction"; 
	 	} 
	      
	 	/** 
	 	 * <p>���ر�����. 
	 	 * <p> 
	 	 * ��������:2012-08-25 16:41:36 
	 	 * @return java.lang.String 
	 	 */ 
	 	public java.lang.String getTableName() { 
	 		return "jyglgt_posanction"; 
	 	}     
	      
	     /** 
	 	  * ����Ĭ�Ϸ�ʽ����������. 
	 	  * 
	 	  * ��������:2012-08-25 16:41:36 
	 	  */ 
			public PosanctionVO() { 
	 		super();	 
	 	} 
}
