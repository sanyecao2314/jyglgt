package nc.vo.jyglgt.pub;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;

public class MyBillTempTVO extends SuperVO {
	
	private String basetab;
	private Integer dr;
	private String metadataclass;
	private String metadatapath;
	private Integer mixindex;
	private String pk_billtemplet;
	private String pk_billtemplet_t;
	private Integer pos;
	private Integer position;
	private String resid;
	private String tabcode;
	private Integer tabindex;
	private String tabname;
	private UFDateTime ts;
	private String vdef1;
	private String vdef2;
	private String vdef3;

	public String getBasetab() {
		return basetab;
	}

	public void setBasetab(String basetab) {
		this.basetab = basetab;
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

	public String getMetadatapath() {
		return metadatapath;
	}

	public void setMetadatapath(String metadatapath) {
		this.metadatapath = metadatapath;
	}

	public Integer getMixindex() {
		return mixindex;
	}

	public void setMixindex(Integer mixindex) {
		this.mixindex = mixindex;
	}

	public String getPk_billtemplet() {
		return pk_billtemplet;
	}

	public void setPk_billtemplet(String pk_billtemplet) {
		this.pk_billtemplet = pk_billtemplet;
	}

	public String getPk_billtemplet_t() {
		return pk_billtemplet_t;
	}

	public void setPk_billtemplet_t(String pk_billtemplet_t) {
		this.pk_billtemplet_t = pk_billtemplet_t;
	}

	public Integer getPos() {
		return pos;
	}

	public void setPos(Integer pos) {
		this.pos = pos;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getResid() {
		return resid;
	}

	public void setResid(String resid) {
		this.resid = resid;
	}

	public String getTabcode() {
		return tabcode;
	}

	public void setTabcode(String tabcode) {
		this.tabcode = tabcode;
	}

	public Integer getTabindex() {
		return tabindex;
	}

	public void setTabindex(Integer tabindex) {
		this.tabindex = tabindex;
	}

	public String getTabname() {
		return tabname;
	}

	public void setTabname(String tabname) {
		this.tabname = tabname;
	}

	public UFDateTime getTs() {
		return ts;
	}

	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}

	public String getVdef1() {
		return vdef1;
	}

	public void setVdef1(String vdef1) {
		this.vdef1 = vdef1;
	}

	public String getVdef2() {
		return vdef2;
	}

	public void setVdef2(String vdef2) {
		this.vdef2 = vdef2;
	}

	public String getVdef3() {
		return vdef3;
	}

	public void setVdef3(String vdef3) {
		this.vdef3 = vdef3;
	}

	@Override
	public String getPKFieldName() {
		return "pk_billtemplet_t";
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return "pub_billtemplet_t";
	}

}
