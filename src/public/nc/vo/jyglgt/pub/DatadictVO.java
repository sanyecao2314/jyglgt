package nc.vo.jyglgt.pub;

import nc.vo.pub.SuperVO;
import nc.vo.pub.ddc.datadict.TableDef;
import nc.vo.pub.lang.UFTime;

/**
 * @author Administrator
 *
 */
public class DatadictVO extends SuperVO {
	
	private String id;
	private String guid;
	private String display;
	private String kind;
	private String parentguid;
	private TableDef prop;//用来存储二进制字段 BOLB
	private UFTime ts;
	private Integer dr;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getParentguid() {
		return parentguid;
	}

	public void setParentguid(String parentguid) {
		this.parentguid = parentguid;
	}

	public TableDef getProp() {
		return prop;
	}

	public void setProp(TableDef prop) {
		this.prop = prop;
	}

	public UFTime getTs() {
		return ts;
	}

	public void setTs(UFTime ts) {
		this.ts = ts;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}

	@Override
	public String getPKFieldName() {
		return null;
	}

	@Override
	public String getParentPKFieldName() {
		return null;
	}

	@Override
	public String getTableName() {
		return null;
	}

}
