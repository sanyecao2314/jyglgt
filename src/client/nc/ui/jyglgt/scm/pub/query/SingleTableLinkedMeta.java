package nc.ui.jyglgt.scm.pub.query;

public class SingleTableLinkedMeta {
	private String queryName=null;
	private String tableName=null;
	private String tableSourceField=null;
	private String tableTagetField=null;
	/**
	 * @param queryName��ǰ��ѯ���еĲ�ѯ�ֶ���
	 * @param tableName��������
	 * @param tableSourceField�����ֶ���
	 * @param tableTagetField����Ŀ���ֶ���
	 */
	public SingleTableLinkedMeta(String queryName,String tableName,String tableSourceField,String tableTagetField){
		this.queryName=queryName;
		this.tableName=tableName;
		this.tableSourceField=tableSourceField;
		this.tableTagetField=tableTagetField;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableSourceField() {
		return tableSourceField;
	}
	public void setTableSourceField(String tableSourceField) {
		this.tableSourceField = tableSourceField;
	}
	public String getTableTagetField() {
		return tableTagetField;
	}
	public void setTableTagetField(String tableTagetField) {
		this.tableTagetField = tableTagetField;
	}
	

}
