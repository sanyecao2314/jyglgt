package nc.ui.jyglgt.scm.pub.query;

import static nc.vo.jcom.lang.StringUtil.isEmpty;

import java.util.HashMap;
import java.util.Map;

import nc.ui.querytemplate.IQueryTemplateTotalVOProcessor;
import nc.vo.pub.query.QueryConditionVO;
import nc.vo.pub.query.QueryTempletTotalVO;

/**
 * �������ò�ѯģ���ֶ�Ĭ��ֵ�Ĳ�ѯģ��VO������
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
	 * ���ò�ѯģ���ֶε�Ĭ��ֵ
	 * 
	 * @param tableCode �����
	 * @param fieldCode �ֶα���
	 * @param pk Ҫ���õ�Ĭ��ֵ
	 * @param text ����ֶ��ǲ��յĻ���������������ʾֵ
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
