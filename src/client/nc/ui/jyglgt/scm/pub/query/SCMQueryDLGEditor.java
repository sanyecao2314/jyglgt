package nc.ui.jyglgt.scm.pub.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.md.data.criterion.QueryTreeNode;
import nc.ui.querytemplate.QueryConditionEditor;
import nc.ui.querytemplate.filter.DefaultFilter;
import nc.ui.querytemplate.filter.IFilter;
import nc.ui.querytemplate.meta.FilterMeta;
import nc.ui.querytemplate.normalpanel.INormalQueryPanel;
import nc.ui.querytemplate.operator.IOperatorConstants;
import nc.ui.querytemplate.operator.OperatorFactory;
import nc.ui.querytemplate.querytree.AndOperator;
import nc.ui.querytemplate.querytree.OrOperator;
import nc.ui.querytemplate.querytree.QueryTree;
import nc.vo.querytemplate.TemplateInfo;
import nc.vo.scm.pub.query.DataPowerCtl;
import nc.vo.scm.pub.query.DataPowerRuleVO;

public class SCMQueryDLGEditor extends QueryConditionEditor {
   
	private  DataPowerCtl powerCtl;
	private static final long serialVersionUID = 1L;

	public SCMQueryDLGEditor(INormalQueryPanel normalPanel) {
		super(normalPanel);
	}
	
	public SCMQueryDLGEditor(TemplateInfo tempinfo) {
		super(tempinfo);
	}

	public SCMQueryDLGEditor(INormalQueryPanel normalPanel, TemplateInfo tempinfo) {
		super(normalPanel,tempinfo);
	}

	public DataPowerCtl getPowerCtl() {
		return powerCtl;
	}

	public void setPowerCtl(DataPowerCtl powerCtl) {
		this.powerCtl = powerCtl;
	}
	private QueryTreeNode[] getQueryTreeNodes(){
		List<FilterMeta> filterList=getAllFilterMeta();
		Map<String, FilterMeta> filterMetaMap = new HashMap<String, FilterMeta>();
		for (FilterMeta meta : filterList) {
			filterMetaMap.put(meta.getFieldCode(), meta);
		}
		ArrayList<DataPowerRuleVO> ruleVOList = powerCtl.getListRules();
		for (DataPowerRuleVO ruleVO : ruleVOList) {
			if (!filterMetaMap.containsKey(ruleVO.getBusiPowerField())) {
				
			}
		}
	 return null;
	}
	private FilterMeta getFilterMeta(DataPowerRuleVO rule){
		FilterMeta meta=new FilterMeta();
		meta.setFieldCode(rule.getBusiPowerField());
		return meta;
		
	}
	private IFilter getIsnullFilterByPowerRule(DataPowerRuleVO rule){
		DefaultFilter filter=new DefaultFilter();
		FilterMeta meta=getFilterMeta(rule);
		filter.setFilterMeta(meta);
		filter.setLogicOP(AndOperator.getInstance());
		filter.setOperator(OperatorFactory.getInstance().getOperator(IOperatorConstants.ISNULL));
		return filter;
		
	}
	private IFilter getPowerFilterByPowerRule(DataPowerRuleVO rule){
		DefaultFilter filter=new DefaultFilter();
		FilterMeta meta=getFilterMeta(rule);
		filter.setFilterMeta(meta);
		filter.setLogicOP(OrOperator.getInstance());
		filter.setOperator(OperatorFactory.getInstance().getOperator(IOperatorConstants.IN));
		return filter;
		
	}
	public QueryTree getDataPowerQueryTree() {	
		QueryTree queryTree=super.getDataPowerQueryTree();
		return queryTree;
	}

}
