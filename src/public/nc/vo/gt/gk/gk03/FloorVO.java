package nc.vo.gt.gk.gk03;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

public class FloorVO extends SuperVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer dr;
	private UFDateTime ts;
	private String pk_floor;
	private Integer floornum;
	private UFBoolean isout;
	private String vdef1;
	private String vdef2;
	private String vdef3;
	private String vdef4;
	private String vdef5;
	private UFDouble vdef6;
	private UFDouble vdef7;
	private UFDouble vdef8;
	private UFDouble vdef9;
	private UFDouble vdef10;
	private String vdef11;
	private String pk_store;
	private String pk_cargdoc;
	
	
	
	public Integer getFloornum() {
		return floornum;
	}

	public void setFloornum(Integer floornum) {
		this.floornum = floornum;
	}

	public String getVdef11() {
		return vdef11;
	}

	public void setVdef11(String vdef11) {
		this.vdef11 = vdef11;
	}

	public String getPk_store() {
		return pk_store;
	}

	public void setPk_store(String pk_store) {
		this.pk_store = pk_store;
	}

	public String getPk_cargdoc() {
		return pk_cargdoc;
	}

	public void setPk_cargdoc(String pk_cargdoc) {
		this.pk_cargdoc = pk_cargdoc;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	public UFDateTime getTs() {
		return ts;
	}

	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}

	public String getPk_floor() {
		return pk_floor;
	}

	public void setPk_floor(String pk_floor) {
		this.pk_floor = pk_floor;
	}

	public UFBoolean getIsout() {
		return isout;
	}

	public void setIsout(UFBoolean isout) {
		this.isout = isout;
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

	public String getVdef4() {
		return vdef4;
	}

	public void setVdef4(String vdef4) {
		this.vdef4 = vdef4;
	}

	public String getVdef5() {
		return vdef5;
	}

	public void setVdef5(String vdef5) {
		this.vdef5 = vdef5;
	}

	public UFDouble getVdef6() {
		return vdef6;
	}

	public void setVdef6(UFDouble vdef6) {
		this.vdef6 = vdef6;
	}

	public UFDouble getVdef7() {
		return vdef7;
	}

	public void setVdef7(UFDouble vdef7) {
		this.vdef7 = vdef7;
	}

	public UFDouble getVdef8() {
		return vdef8;
	}

	public void setVdef8(UFDouble vdef8) {
		this.vdef8 = vdef8;
	}

	public UFDouble getVdef9() {
		return vdef9;
	}

	public void setVdef9(UFDouble vdef9) {
		this.vdef9 = vdef9;
	}

	public UFDouble getVdef10() {
		return vdef10;
	}

	public void setVdef10(UFDouble vdef10) {
		this.vdef10 = vdef10;
	}

	@Override
	public String getPKFieldName() {
		// TODO Auto-generated method stub
		return "pk_floor";
	}

	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "bd_floor";
	}

}
