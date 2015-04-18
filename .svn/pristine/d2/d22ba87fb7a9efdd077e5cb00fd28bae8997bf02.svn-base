package nc.ui.jyglgt.scm.pub.query;

import nc.ui.querytemplate.filter.DefaultFilter;

public class SingleTableLinkedFilter extends DefaultFilter{
	SingleTableLinkedMeta metaInfo =null;
	public SingleTableLinkedFilter(nc.ui.querytemplate.meta.FilterMeta meta,SingleTableLinkedMeta metaInfo){
		super(meta);
		this.metaInfo=metaInfo;
		
	}
	
	public String getSqlString() {
		if (this.getFieldValue() != null
				&& this.getFieldValue().getFieldValues() != null
				&& this.getFieldValue().getFieldValues()
						.size() != 0
				&& this.getFieldValue().getFieldValues()
						.get(0).getShowString() != null
				&& !"".equals(this.getFieldValue()
						.getFieldValues().get(0)
						.getShowString())) {
			String valueSql = null;
			// String whereSql=null;

			if ("like".equalsIgnoreCase(this.getOperator()
					.getOperatorCode().trim())) {
				valueSql = " like '%"
						+ this.getFieldValue()
								.getFieldValues().get(0)
								.getShowString().trim()
						+ "%')";

			} else {
				valueSql = " ='"
						+ this.getFieldValue()
								.getFieldValues().get(0)
								.getShowString().trim()
						+ "')";
			}
			// Object[][]
			// values=CacheTool.getAnyValue2("bd_invbasdoc",
			// "pk_invbasdoc",
			// whereSql);
			// StringBuffer sqlPart=new
			// StringBuffer().append("(''");
			// for(Object[]
			// value:values){
			// if(value[0]!=null){
			// sqlPart.append(",
			// '"+(String)value[0]+"'");
			// }
			//												
			//												
			// }
			// sqlPart.append("");

			return " " + metaInfo.getQueryName()
					+ " in (select a."
					+ metaInfo.getTableTagetField()
					+ " from " + metaInfo.getTableName()
					+ " a where a."
					+ metaInfo.getTableSourceField()
					+ valueSql;
			// return " cinvbasdocid in
			// "+sqlPart.toString();
		} else {
			return " 1=1";
		}
	}
	

}
