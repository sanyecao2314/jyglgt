package nc.vo.jyglgt.pub.Toolkits;

import java.sql.ResultSet;
import java.sql.SQLException;

import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.vo.com.utils.DBObjectReader;
import nc.vo.jyglgt.pub.DatadictVO;
import nc.vo.pub.ddc.datadict.TableDef;

/**
 * @author ����������
 * �Լ�д���������,ר����Զ������ֶ�
 *
 */
public class DatadictProcess implements ResultSetProcessor {
	
	/** �ֶ����Ƴ��� */
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
	 * �ӽ������ȡ�ڵ����Ļ������ԣ�����ҵ����󷵻�
	 * 
	 * @param resultSet �����
	 * @param node ����ڵ�
	 * @return ҵ�����
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
