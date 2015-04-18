package nc.ui.jyglgt.scm.pub.query;

import java.util.HashMap;
import java.util.Map;

import nc.ui.querytemplate.QueryConditionEditorContext;
import nc.ui.querytemplate.filter.DefaultFilter;
import nc.ui.querytemplate.filter.IFilter;
import nc.ui.querytemplate.filtereditor.DefaultFilterEditor;
import nc.ui.querytemplate.filtereditor.IFilterEditor;
import nc.ui.querytemplate.filtereditor.IFilterEditorFactory;
import nc.ui.querytemplate.meta.FilterMeta;
import nc.ui.querytemplate.meta.IFilterMeta;
import nc.ui.querytemplate.value.DefaultFieldValueElement;

/**
 * 自定义查询模板字段 sql 的 sql 创建工厂。
 * <p>如果某些字段的sql比较复杂，需要自定义sql片段，则可以用此工厂类来注册。
 * 
 * @author 蒲强华
 * @since 2008-11-5
 */
class CustomFieldSqlFactory implements IFilterEditorFactory {
	private QueryConditionEditorContext qceCxt;
	private Map<String, IFilterSqlCreator> sqlMap = new HashMap<String, IFilterSqlCreator>();

	CustomFieldSqlFactory(QueryConditionEditorContext qceCxt) {
		this.qceCxt = qceCxt;
	}

	/**
	 * 注册某个字读的sql创建器
	 * 
	 * @param fieldCode 字段编码
	 * @param creator sql创建器
	 * @see IFilterSqlCreator
	 */
	void registCreator(String fieldCode, IFilterSqlCreator creator) {
		sqlMap.put(fieldCode, creator);
	}

	public IFilterEditor createFilterEditor(IFilterMeta meta) {
		final IFilterSqlCreator creator = sqlMap.get(meta.getFieldCode());
		if (null != creator) {
			return new DefaultFilterEditor(qceCxt, meta){
				private static final long serialVersionUID = 1L;
				protected IFilter createFilter(FilterMeta meta) {
					return new CustomFieldSqlFilter(creator);
				}
			};
		} else {
			return null;
		}
	}

	public static class CustomFieldSqlFilter extends DefaultFilter {
		private static final long serialVersionUID = -832021600481149006L;
		private IFilterSqlCreator creator;
		CustomFieldSqlFilter(IFilterSqlCreator creator) {
			this.creator = creator;
		}
		public String getSqlString() {
			String value = ((DefaultFieldValueElement) (getFieldValue()
					.getFieldValues().get(0))).getSqlString();
			return creator.getSql(value);
		}
	}
}
