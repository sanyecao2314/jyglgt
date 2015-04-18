package nc.ui.jyglgt.scm.pub.query;

import static nc.vo.jcom.lang.StringUtil.isEmpty;
import nc.ui.jyglgt.scm.pub.def.DefSetTool;
import nc.ui.querytemplate.IQueryTemplateTotalVOProcessor;
import nc.ui.querytemplate.meta.FilterMeta;
import nc.ui.querytemplate.valueeditor.IFieldValueElementEditor;
import nc.ui.querytemplate.valueeditor.IFieldValueElementEditorFactory;
import nc.ui.querytemplate.valueeditor.RefElementEditor;
import nc.vo.bd.def.DefVO;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.query.IQueryConstants;
import nc.vo.pub.query.QueryConditionVO;
import nc.vo.pub.query.QueryTempletTotalVO;
import nc.vo.scm.pub.SCMEnv;

/**
 * 查询模板中的自定义项处理类，参考<code>nc.ui.ic.pub.query.DefHelp</code> 并且去掉IC相关代码而来
 * 
 * @see nc.ui.ic.pub.query.DefHelp
 * @since 2008-9-16
 */
class UserDefConditionProcessor implements IQueryTemplateTotalVOProcessor,
		IFieldValueElementEditorFactory {
	private String pk_corp;
	private String cbilltypecode;
	private String hPrefix;
	private String bPrefix;
	private DefVO[] bDefs;
	private DefVO[] hDefs;
	private String hEndfix;
	private int hAddNum;
	private String bEndfix;
	private int bAddNum;

	// 用户未修改过自定义项名称标记关键字
	public static final String KEY_NOT_MODIFIED_1 = "UDC";
	public static final String KEY_NOT_MODIFIED_2 = "自定义项";

	/**
	 * 构造方法
	 * 
	 * @param pk_corp
	 *            公司主键
	 * @param cbilltypecode
	 *            单据类型
	 * @param hPrefix
	 *            单据模板中单据头的自定义项前缀
	 * @param bPrefix
	 *            单据模板中单据体的自定义项前缀
	 */
	UserDefConditionProcessor(String pk_corp, String cbilltypecode,
			String hPrefix, String bPrefix) {
		this.pk_corp = pk_corp;
		this.cbilltypecode = cbilltypecode;
		this.hPrefix = hPrefix;
		this.bPrefix = bPrefix;
	}

	UserDefConditionProcessor(String pk_corp, String cbilltypecode,
			String hPrefix, String hEndfix, int hAddNum, String bPrefix,
			String bEndfix, int bAddNum) {
		this.pk_corp = pk_corp;
		this.cbilltypecode = cbilltypecode;
		this.hPrefix = hPrefix;
		this.hEndfix = hEndfix;
		this.hAddNum = hAddNum;
		this.bPrefix = bPrefix;
		this.bEndfix = bEndfix;
		this.bAddNum = bAddNum;
	}

	private boolean isHead(String fieldCode) {
		return fieldCode.startsWith(hPrefix);
	}

	private boolean isBody(String fieldCode) {
		return fieldCode.startsWith(bPrefix);
	}

	private int getIndex(String strSrc, String strBgn, String strEnd,
			int iAddNum) {
		int iIndex = -1;
		try {
			int iBgnLen = strBgn.length();
			String strTmp = strSrc.substring(iBgnLen);
			if (strEnd != null && strEnd.length() != 0) {
				strTmp = StringUtil.replaceAllString(strTmp, strEnd, "");
			}
			iIndex = new Integer(strTmp.trim()).intValue();
			iIndex--;
			iIndex += iAddNum;
		} catch (Exception e) {
			SCMEnv.error("从给定串中解析索引发生异常：" + "源串：" + strSrc + "前缀：" + strBgn
					+ "、后缀：" + strEnd + "。", e);
		}
		return iIndex;
	}

	private DefVO[] getBDefs() {
		if (bDefs == null)
			bDefs = DefSetTool.getDefBody(pk_corp, cbilltypecode);
		return bDefs;
	}

	private DefVO[] getHDefs() {
		if (hDefs == null)
			hDefs = DefSetTool.getDefHead(pk_corp, cbilltypecode);
		return hDefs;
	}

	public void processQueryTempletTotalVO(QueryTempletTotalVO totalVO) {
		if (isEmpty(hPrefix))
			return;

		for (QueryConditionVO cs : totalVO.getConditionVOs()) {
			String fieldCode = cs.getFieldCode();
			String fieldName = cs.getFieldName();
			if (fieldCode == null)
				continue;
			DefVO[] defVOs = null;
			int index = -1;
			if (isHead(fieldCode) && getHDefs() != null) {
				defVOs = getHDefs();
				index = getIndex(fieldCode, hPrefix, hEndfix, hAddNum);
			} else if (isBody(fieldCode) && getBDefs() != null) {
				defVOs = getBDefs();
				index = getIndex(fieldCode, bPrefix, bEndfix, bAddNum);
			} else {
				SCMEnv.out("不是自定义项，fieldCode：" + fieldCode + " fieldName：" + fieldName);
				continue;
			}

			if (index >= 0 && defVOs != null && index < defVOs.length
					&& defVOs[index] != null) {
				if (isEmpty(fieldName)
						|| fieldName.indexOf(KEY_NOT_MODIFIED_1) >= 0
						|| fieldName.indexOf(KEY_NOT_MODIFIED_2) >= 0) {
					cs.setFieldName(defVOs[index].getDefname());
					cs.setDispType(IQueryConstants.DISPNAME);
					cs.setReturnType(IQueryConstants.RETURNNAME);
					cs.setDataType(IQueryConstants.UFREF);
				}
			}else if(index >= 0&& defVOs != null && index < defVOs.length
					&&defVOs[index] == null){
				cs.setIfUsed(new UFBoolean(false));
				
			}
		}
		return;
	}

	public IFieldValueElementEditor createFieldValueElementEditor(
			FilterMeta meta) {
		boolean bIsHead = isHead(meta.getFieldCode());
		boolean bIsBody = isBody(meta.getFieldCode());
		DefVO vo = null;
		if (bIsHead && getHDefs() != null) {
			int iIndex = -1;
			iIndex = getIndex(meta.getFieldCode(), hPrefix, hEndfix, hAddNum);
			if (iIndex >= 0 && iIndex < getHDefs().length
					&& getHDefs()[iIndex] != null) {
				vo = getHDefs()[iIndex];
			}
		}
		// 查询条件来自表体中的的自定义项
		else if (bIsBody && getBDefs() != null) {
			int iIndex = -1;
			iIndex = getIndex(meta.getFieldCode(), bPrefix, bEndfix, bAddNum);
			if (iIndex >= 0 && iIndex < getBDefs().length
					&& getBDefs()[iIndex] != null) {
				vo = getBDefs()[iIndex];
			}
		}
		if (vo != null)
			return getFieldValueElementEditor(vo);
		return null;
	}

	private IFieldValueElementEditor getFieldValueElementEditor(DefVO vo) {
		// 定义参照的长度
		final int iMaxLenOfDef = 20;
		String type = vo.getType();
		nc.ui.pub.beans.UIRefPane refPane = new nc.ui.pub.beans.UIRefPane();
		if (type.equals("统计")) {
			/* 自定义项列表主键为空，认为是基础数据档案 */
			if (vo.getDefdef() != null
					&& vo.getDefdef().getPk_defdoclist() == null) {
				refPane = nc.ui.bd.ref.RefCall.getUIRefPane(vo.getDefdef()
						.getPk_bdinfo(), pk_corp);
			} else {
				/* 自定义项列表主键不为空，认为是自定义档案 */
				String sWhereString = " and ";
				sWhereString = sWhereString + "pk_defdef = (";
				sWhereString = sWhereString + "select a.pk_defdef ";
				sWhereString = sWhereString
						+ "from bd_defquote a,bd_defused b ";
				sWhereString = sWhereString
						+ "where a.pk_defused = b.pk_defused ";
				sWhereString = sWhereString + "and b.objname = '"
						+ vo.getObjName() + "' ";
				sWhereString = sWhereString + "and a.fieldname = '"
						+ vo.getFieldName() + "'";
				sWhereString = sWhereString + ") ";
				refPane.setRefNodeName("自定义项档案");
				refPane.getRefModel().addWherePart(sWhereString);
			}
			// 查询框对封存的应可显示
			if (refPane != null && refPane.getRefModel() != null) {
				refPane.getRefModel().setSealedDataShow(true);
			}
		} else if (type.equals("日期")) {
			refPane.setRefNodeName("日历");
		} else if (type.equals("备注")) {
			refPane.setMaxLength(iMaxLenOfDef);
			refPane.setButtonVisible(false);
		} else if (type.equals("数字")) {
			refPane.setTextType("TextDbl");
			refPane.setButtonVisible(false);
			refPane.setMaxLength(iMaxLenOfDef);
			int iDigt = 0;
			if (vo.getDigitnum() != null)
				iDigt = vo.getDigitnum().intValue();
			refPane.setNumPoint(iDigt);

		}
		return new RefElementEditor(refPane, IQueryConstants.RETURNNAME);

	}
}
