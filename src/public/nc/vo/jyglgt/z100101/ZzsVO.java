package nc.vo.jyglgt.z100101;

import nc.vo.pub.SuperVO; 
import nc.vo.pub.lang.UFDate; 
import nc.vo.pub.lang.UFDateTime; 
import nc.vo.pub.lang.UFDouble; 
import nc.vo.pub.lang.UFBoolean; 

public class ZzsVO extends SuperVO { 

	 private String pk_zzs;//pk_zzs 

	 private String pk_corp;//pk_corp 

	 private UFDateTime ts;//ts 

	 private String dr;//dr 

	 public void setPk_corp(String newpk_corp){
	     pk_corp = newpk_corp;
	 }
	 public String getPk_corp(){
	     	return pk_corp;
	 }
	 public void setTs(UFDateTime newts){
	     ts = newts;
	 }
	 public UFDateTime getTs(){
	     	return ts;
	 }
	 public void setDr(String newdr){
	     dr = newdr;
	 }
	 public String getDr(){
	     	return dr;
	 }
	 public void setPk_zzs(String newpk_zzs){
	     pk_zzs = newpk_zzs;
	 }
	 public String getPk_zzs(){
	     	return pk_zzs;
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
	 	  return ""; 
	 	} 
	      
	 	/** 
	 	 * <p>���ر�����. 
	 	 * <p> 
	 	 * ��������:2012-08-25 16:41:36 
	 	 * @return java.lang.String 
	 	 */ 
	 	public java.lang.String getTableName() { 
	 		return "zyzl_zzs"; 
	 	}     
	      
	     /** 
	 	  * ����Ĭ�Ϸ�ʽ����������. 
	 	  * 
	 	  * ��������:2012-08-25 16:41:36 
	 	  */ 
			public ZzsVO() { 
	 		super();	 
	 	} 
}
