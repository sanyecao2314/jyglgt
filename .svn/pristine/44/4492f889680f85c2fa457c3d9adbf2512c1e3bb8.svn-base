package nc.ui.jyglgt.scm.pub.query;

import static nc.vo.jcom.lang.StringUtil.isEmpty;

import java.util.HashMap;
import java.util.Map;

import nc.ui.querytemplate.IQueryTemplateTotalVOProcessor;
import nc.vo.pub.query.QueryConditionVO;
import nc.vo.pub.query.QueryTempletTotalVO;

/**
 * 用来设置查询模板字段默认值的查询模板VO处理器
 * 
 * @since 2008-9-12
 */
class DefaultValueProcessor implements IQueryTemplateTotalVOProcessor {

	private Map<String, Value> valueMap = new HashMap<String, Value>();

	public void processQueryTempletTotalVO(QueryTempletTotalVO totalVO) {
		for (QueryConditionVO qcvo : totalVO.getConditionVOs()) {
			String key = getKey(qcvo.getTableCode(), qcvo.getFieldCode());
			Value value = valueMap.get(key);
			if (null != value) {
				qcvo.setValue(value.pk);
				if (!isEmpty(value.text)) {
					qcvo.setDispValue(value.text);
				}
			}
		}
	}

	/**
	 * 设置查询模板字段的默认值
	 * 
	 * @param tableCode 表编码
	 * @param fieldCode 字段编码
	 * @param pk 要设置的默认值
	 * @param text 如果字段是参照的话可以用来设置显示值
	 */
	public void setDefaultValue(String tableCode, String fieldCode, String pk,
			String text) {
		valueMap.put(getKey(tableCode, fieldCode), new Value(pk, text));
	}

	private String getKey(String tableCode, String fieldCode) {
		if (null == tableCode) {
			tableCode = "";
		}
		return tableCode + "%$%" + fieldCode;
	}

	class Value {
		Value(String pk, String text) {
			this.pk = pk;
			this.text = text;
		}

		String pk;
		String text;
	}
}
