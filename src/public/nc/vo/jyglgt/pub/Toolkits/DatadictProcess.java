package nc.vo.jyglgt.pub.Toolkits;

import java.sql.ResultSet;
import java.sql.SQLException;

import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.vo.com.utils.DBObjectReader;
import nc.vo.jyglgt.pub.DatadictVO;
import nc.vo.pub.ddc.datadict.TableDef;

/**
 * @author 公共开发者
 * 自己写个结果处理集,专门针对二进制字段
 *
 */
public class DatadictProcess implements ResultSetProcessor {
	
	/** 字段名称常量 */
	private final static String PROPERTY_FIELD = "PROP";

	private final static String ID_FIELD = "ID";

	private final static String GUID_FIELD = "GUID";

	private final static String DISPLAYNAME_FIELD = "DISPLAY";

	private final static String KIND_FIELD = "KIND";

	private final static String PARENTGUID_FIELD = "PARENTGUID";

	public DatadictVO handleResultSet(ResultSet rs) throws SQLException {
		DatadictVO obj = null;
		if (rs.next()) {
			obj = readMainObject(rs);
		}
		return obj;
	}
	
	/**
	 * 从结果集获取节点对象的基本属性，并把业务对象返回
	 * 
	 * @param resultSet 结果集
	 * @param node 对象节点
	 * @return 业务对象
	 * @throws SQLException
	 */
	protected DatadictVO readMainObject(ResultSet resultSet)
			throws SQLException {
		DatadictVO dvo = new DatadictVO();
		dvo.setId(resultSet.getString(ID_FIELD));
		dvo.setDisplay(resultSet.getString(DISPLAYNAME_FIELD));
		dvo.setKind(resultSet.getString(KIND_FIELD));
		dvo.setParentguid(resultSet.getString(PARENTGUID_FIELD));
		TableDef tdef = (TableDef) DBObjectReader.readObject(resultSet, PROPERTY_FIELD);
		dvo.setProp(tdef);
		return dvo;
	}

}
