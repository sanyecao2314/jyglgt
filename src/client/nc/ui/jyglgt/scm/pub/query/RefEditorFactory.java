package nc.ui.jyglgt.scm.pub.query;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.querytemplate.meta.FilterMeta;
import nc.ui.querytemplate.valueeditor.DefaultFieldValueElementEditor;
import nc.ui.querytemplate.valueeditor.IFieldValueElementEditor;
import nc.ui.querytemplate.valueeditor.IFieldValueElementEditorFactory;
import nc.ui.querytemplate.valueeditor.RefElementEditor;
import nc.vo.scm.pub.SCMEnv;

/**
 * 查询对话框的参照字段编辑器工厂类
 * 
 * @since 2008-9-12
 */
class RefEditorFactory implements IFieldValueElementEditorFactory {
	/** 保存所有字段和参照的映射关系 (表，字段 => 参照) */
	private Map<String, Object> refMap = new HashMap<String, Object>();

	@SuppressWarnings("unchecked")
	public IFieldValueElementEditor createFieldValueElementEditor(
			FilterMeta meta) {
		String key = getKey(meta.getTableCode(), meta.getFieldCode());
		Object ref = refMap.get(key);
		if (null == ref) {
			return null;
		}

		if (ref instanceof IRefPanelCreator) {
			JComponent c = ((IRefPanelCreator<JComponent>)ref).create();
			if (c instanceof UIRefPane) {
				UIRefPane editor = (UIRefPane) c;
				return new RefElementEditor(editor, meta.getReturnType());
			} else if (c instanceof JComboBox) {
				return new DefaultFieldValueElementEditor((JComboBox)c);
			} else {
				SCMEnv.warn("不是参照类型，返回 null <" + "tableCode=" + meta.getTableCode() + " fieldCode=" + meta.getFieldCode() + " refType=" + ref.getClass() + ">");
				return null;
			}
		} else if (ref instanceof String) {
			UIRefPane editor = new UIRefPane();
			editor.setRefNodeName((String) ref);
			return new RefElementEditor(editor, meta.getReturnType());
		} else if (ref instanceof AbstractRefModel) {
			UIRefPane editor = new UIRefPane();
			editor.setRefModel((AbstractRefModel) ref);
			return new RefElementEditor(editor, meta.getReturnType());
		} else {
			SCMEnv.warn("不是参照类型，返回 null <" + "tableCode=" + meta.getTableCode() + " fieldCode=" + meta.getFieldCode() + " refType=" + ref.getClass() + ">");
			return null;
		}
	}

	/**
	 * 设置指定字段的参照类型
	 * 
	 * @param tableCode
	 *            表编码
	 * @param fieldCode
	 *            字段编码
	 * @param refModel
	 *            参照模型
	 */
	void setValueRef(String tableCode, String fieldCode, Object ref) {
		String key = getKey(tableCode, fieldCode);
		refMap.put(key, ref);
	}

	private String getKey(String tableCode, String fieldCode) {
		return tableCode + "%$%" + fieldCode;
	}

}
