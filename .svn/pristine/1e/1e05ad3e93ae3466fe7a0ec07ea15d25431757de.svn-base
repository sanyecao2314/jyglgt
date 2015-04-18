package nc.ui.jyglgt.scm.pub.query;

import java.util.HashMap;

import nc.ui.querytemplate.QueryConditionEditorContext;
import nc.ui.querytemplate.filter.DefaultFilter;
import nc.ui.querytemplate.filter.IFilter;
import nc.ui.querytemplate.filtereditor.DefaultFilterEditor;
import nc.ui.querytemplate.filtereditor.IFilterEditor;
import nc.ui.querytemplate.filtereditor.IFilterEditorFactory;
import nc.ui.querytemplate.meta.IFilterMeta;


public class TableLinkedFilterEdit implements IFilterEditorFactory{
	private HashMap<String, SingleTableLinkedMeta> singleTableMetas = new HashMap<String, SingleTableLinkedMeta>();
	
	private QueryConditionEditorContext queryEditorContext=null;
	
	public TableLinkedFilterEdit(QueryConditionEditorContext queryEditorContext){
		this.queryEditorContext=queryEditorContext;
	}
   
	public void addTableLinkedMeta(String queryName, SingleTableLinkedMeta meta) {
		singleTableMetas.put(queryName, meta);
	}
	public IFilterEditor createFilterEditor(IFilterMeta meta) {

		DefaultFilterEditor editor = null;
		for (String queryName : singleTableMetas.keySet()) {
			if (singleTableMetas.get(queryName) == null) {
				continue;
			}
			final SingleTableLinkedMeta metaInfo = singleTableMetas.get(queryName);
			if (queryName.equals(meta.getFieldCode())) {
				editor = new DefaultFilterEditor(queryEditorContext, meta) {
					@Override
					protected IFilter createFilter(
							nc.ui.querytemplate.meta.FilterMeta meta) {
						return new SingleTableLinkedFilter(meta,metaInfo);
					}
				};
			}
		}
		return editor;
	}
	
	

}
