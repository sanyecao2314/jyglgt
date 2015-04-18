package nc.vo.jyglgt.z100103;

import nc.vo.pub.SuperVO; 
import nc.vo.pub.lang.UFDate; 
import nc.vo.pub.lang.UFDateTime; 
import nc.vo.pub.lang.UFDouble; 
import nc.vo.pub.lang.UFBoolean; 

public class SdsVO extends SuperVO { 

	 private String pk_sds;//pk_sds 

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
	 public void setPk_sds(String newpk_sds){
	     pk_sds = newpk_sds;
	 }
	 public String getPk_sds(){
	     	return pk_sds;
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
	 	  return ""; 
	 	} 
	      
	 	/** 
	 	 * <p>返回表名称. 
	 	 * <p> 
	 	 * 创建日期:2012-08-25 16:41:36 
	 	 * @return java.lang.String 
	 	 */ 
	 	public java.lang.String getTableName() { 
	 		return "zyzl_sds"; 
	 	}     
	      
	     /** 
	 	  * 按照默认方式创建构造子. 
	 	  * 
	 	  * 创建日期:2012-08-25 16:41:36 
	 	  */ 
			public SdsVO() { 
	 		super();	 
	 	} 
}
