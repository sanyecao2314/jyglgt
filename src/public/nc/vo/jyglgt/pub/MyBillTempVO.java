package nc.vo.jyglgt.pub;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;

public class MyBillTempVO extends SuperVO {
	
	private String bill_templetcaption;
	private String bill_templetname;
	private Integer dr;
	private String metadataclass;
	private String model_type;
	private String nodecode;
	private String options;
	private String pk_billtemplet;
	private String pk_billtypecode;
	private String pk_corp;
	private String resid;
	private String shareflag;
	private UFDateTime ts;
	private String validateformula;

	public String getBill_templetcaption() {
		return bill_templetcaption;
	}

	public void setBill_templetcaption(String bill_templetcaption) {
		this.bill_templetcaption = bill_templetcaption;
	}

	public String getBill_templetname() {
		return bill_templetname;
	}

	public void setBill_templetname(String bill_templetname) {
		this.bill_templetname = bill_templetname;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public String getMetadataclass() {
		return metadataclass;
	}

	public void setMetadataclass(String metadataclass) {
		this.metadataclass = metadataclass;
	}

	public String getModel_type() {
		return model_type;
	}

	public void setModel_type(String model_type) {
		this.model_type = model_type;
	}

	public String getNodecode() {
		return nodecode;
	}

	public void setNodecode(String nodecode) {
		this.nodecode = nodecode;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getPk_billtemplet() {
		return pk_billtemplet;
	}

	public void setPk_billtemplet(String pk_billtemplet) {
		this.pk_billtemplet = pk_billtemplet;
	}

	public String getPk_billtypecode() {
		return pk_billtypecode;
	}

	public void setPk_billtypecode(String pk_billtypecode) {
		this.pk_billtypecode = pk_billtypecode;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}

	public String getResid() {
		return resid;
	}

	public void setResid(String resid) {
		this.resid = resid;
	}

	public String getShareflag() {
		return shareflag;
	}

	public void setShareflag(String shareflag) {
		this.shareflag = shareflag;
	}

	public UFDateTime getTs() {
		return ts;
	}

	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}

	public String getValidateformula() {
		return validateformula;
	}

	public void setValidateformula(String validateformula) {
		this.validateformula = validateformula;
	}

	@Override
	public String getPKFieldName() {
		return "pk_billtemplet";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "pub_billtemplet";
	}

}
