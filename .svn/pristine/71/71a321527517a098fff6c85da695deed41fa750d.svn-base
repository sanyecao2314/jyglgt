package nc.ui.jyglgt.scm.pub.def;

//JAVA
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nc.ui.bd.def.DefquoteQueryUtil;
import nc.ui.dbcache.DBCacheFacade;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillListData;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.query.QueryConditionClient;
import nc.vo.bd.def.DefVO;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.query.QueryConditionVO;
import nc.vo.scm.constant.ScmConst;
import nc.vo.scm.datatype.DataTypeTool;
import nc.vo.scm.pub.ScmDataSet;

/**
 * 晁志平 功能：自定义项设置工具 日期：(2003-12-11 11:33:04) ydy20050606
 * 修改：将自定义数据静态缓存存储，供应链只需要初始化一次
 */

public class DefSetTool {
	// 前缀
	private static final String strPre = "[";

	// 后缀
	private static final String strEnd = "]";
	
	private static final String OBJCODE_BATCHCODE=ScmConst.DEF_USED_PREFIX+"vbatchcode";

	/* 查询模板：确定查询模板是表头还是表体自定义项 */
	// 特殊处理
	public static final int _STYLE_IC = 0;

	// 默认处理
	public static final int _STYLE_NORMAL = -999;

	/* 单据模板：表头表体自定义项在单据模板中分布 */
	// 分别在表头、表体
	public static final int _POS_NORMAL = 0;

	// 全在表头
	public static final int _POS_HEAD = 1;

	// 全在表体
	public static final int _POS_BODY = 2;

	// 供应链表头、表体自定义项VO(优化前后台交互次数)
	private static Object[] m_objDefVos = null;

	private static HashMap m_hmDefHead = new HashMap();

	private static HashMap m_hmDefBody = new HashMap();
	
	private static HashMap m_hmDefName=new HashMap();

	// 用户未修改过自定义项名称标记关键字
	private static final String KEY_NOT_MODIFIED_1 = "UDC";

	private static final String KEY_NOT_MODIFIED_2 = "自定义项";

	private static Pattern defKeyPattern;

	/**
	 * PuTool 构造子注解。
	 */
	public DefSetTool() {
		super();
	}

	public static DefVO[] getDefHead(String pk_corp,String cbilltypecode) {

    if(pk_corp == null){
      return null;
    }
		if (!m_hmDefHead.containsKey(pk_corp +cbilltypecode)) {
			Object[] objs = getDefVOBatch(pk_corp,cbilltypecode);
      if(objs == null || objs.length == 0){
        return null;
      }      
      if(objs[1] != null){
        m_hmDefBody.put(pk_corp+cbilltypecode, (DefVO[]) objs[1]);
      }
      if(objs[0] == null){
        return null;
      }  
      m_hmDefHead.put(pk_corp+cbilltypecode, (DefVO[]) objs[0]);
		}

		return (DefVO[]) m_hmDefHead.get(pk_corp+cbilltypecode);

	}

	public static DefVO[] getDefBody(String pk_corp,String cbilltypecode) {

    if(pk_corp == null){
      return null;
    }
		if (!m_hmDefBody.containsKey(pk_corp+cbilltypecode)) {
			Object[] objs = getDefVOBatch(pk_corp,cbilltypecode);
      if(objs == null){
        return null;
      }
      if(objs[0] != null){
        m_hmDefHead.put(pk_corp+cbilltypecode, (DefVO[]) objs[0]);
      }
      if(objs[1] == null){
        return null;
      }
      m_hmDefBody.put(pk_corp+cbilltypecode, (DefVO[]) objs[1]);
		}

		return (DefVO[]) m_hmDefBody.get(pk_corp+cbilltypecode);

	}
	public static DefVO[] getBatchcodeDef(String pk_corp) {

	    if(pk_corp == null){
	      return null;
	    }
			if (!m_hmDefBody.containsKey(pk_corp+OBJCODE_BATCHCODE)) {
				Object[] objs = getDefVOBatch(true,pk_corp,new String[]{OBJCODE_BATCHCODE});//"批次号档案"
	      if(objs == null){
	        return null;
	      }
	    
	      if(objs[0] == null){
	        return null;
	      }
	      m_hmDefBody.put(pk_corp+OBJCODE_BATCHCODE, (DefVO[]) objs[0]);
			}

			return (DefVO[]) m_hmDefBody.get(pk_corp+OBJCODE_BATCHCODE);

		}
	
	/**
	 * 设置卡片模板参照型自定义项的pk值，用于查询数据置入单据模板后
	 * @param bdata
	 * @param sVdefValueKey
	 * @param sVdefPkKey
	 */
	public static void updateCardDefValues(BillData bdata, String sVdefValueKey,
			String sVdefPkKey) {

		String hvdef_pk, bvdef_pk = null;
		BillItem item = null;
		UIRefPane refpane = null;

		int rowCount = 0;

		for (int i = 0; i < 20; i++) {

			// head
			item = bdata.getHeadItem(sVdefValueKey + i);
			if (item != null
					&& (item.getDataType() == BillItem.USERDEF || item
							.getDataType() ==

					BillItem.UFREF)) {
				hvdef_pk =

				DataTypeTool.getString_Trim0LenAsNull(bdata.getHeadItem(
						sVdefPkKey + i).getValueObject());
				refpane = (UIRefPane) item.getComponent();
				refpane.setPK(hvdef_pk);
			}// end if head

			// body
			/*item = bdata.getBodyItem(sVdefValueKey + i);
			if (item != null
					&& (item.getDataType() == BillItem.USERDEF || item
							.getDataType() ==

					BillItem.UFREF)) {
				rowCount = bdata.getBillModel().getRowCount();
				for (int j = 0; j < rowCount; j++) {
					bvdef_pk =

					DataTypeTool.getString_Trim0LenAsNull(bdata.getBillModel()
							.getValueAt(j, sVdefPkKey + i));
					bdata.getBillModel().setValueAt(bvdef_pk, j,
							sVdefValueKey + i + IBillItem.ID_SUFFIX);
				}// end for
			}*/// end if body

		}// end for
	}
	
	
	/**
	 * 功能： afterEdit后，设置自定义项主键 此方法执行完毕后，给定自定义项的相应PK值已设置完成 不区分是头还是体的自定义项，都可使用此方法
	 * 
	 * @author：王印芬
	 * @param BillModel
	 *            billModel
	 * @param int
	 *            iRow 当前编辑的BillModel的行
	 * @param String
	 *            sVdefValueKey 自定义项值的KEY，一般在此KEY上进行输入
	 * @param String
	 *            sVdefPkKey 自定义项PK的KEY，
	 * @return：无
	 * @serialData(2003-12-11 19:02:29)
	 * 
	 */
	public static void afterEditBody(BillModel billModel, int iRow,
			String sVdefValueKey, String sVdefPkKey) {

		// 参数正确性判断
		if (billModel == null || sVdefPkKey == null || sVdefValueKey == null
				|| billModel.getItemByKey(sVdefPkKey) == null
				|| billModel.getItemByKey(sVdefValueKey) == null) {
			return;
		}

		BillItem item = billModel.getItemByKey(sVdefValueKey);
		// 根据类型判断
		if (item.getDataType() == BillItem.USERDEF || item.getDataType() == BillItem.UFREF) {
			UIRefPane refpane = (UIRefPane) item.getComponent();
	    //String sPk_defdoc = DataTypeTool.getString_Trim0LenAsNull(refpane.getRefPK());
	    //根据yb建议，支持批改，用批改的名称列强行匹配参照PK by zhaoyha at 2009.12.2
	    refpane.setBlurValue((String) billModel.getValueAt(iRow, sVdefValueKey));
	    String sPk_defdoc = DataTypeTool.getString_Trim0LenAsNull(refpane.getRefPK());
			if(sPk_defdoc==null && (refpane.getUITextField().getText()!=null && refpane.getUITextField().getText().trim().length()>0)){
				refpane.setValue("");
				billModel.setValueAt(null, iRow, sVdefValueKey);
				return;
			}
			billModel.setValueAt(sPk_defdoc, iRow, sVdefPkKey);
		} else {
			billModel.setValueAt(null, iRow, sVdefPkKey);
		}
		

	}

	/**
	 * 功能： afterEdit后，设置表头自定义项主键，表头是ListPanel时使用此方法 此方法执行完毕后，给定自定义项的相应PK值已设置完成
	 * 不区分是头还是体的自定义项，都可使用此方法
	 * 
	 * @author：王印芬
	 * @param BillModel
	 *            billModel
	 * @param int
	 *            iRow 当前编辑的BillModel的行
	 * @param String
	 *            sVdefValueKey 自定义项值的KEY，一般在此KEY上进行输入
	 * @param String
	 *            sVdefPkKey 自定义项PK的KEY，
	 * @return：无
	 * @serialData(2003-12-11 19:02:29)
	 * 
	 */
	public static void afterEditHead(BillModel billModel, int iRow,
			String sVdefValueKey, String sVdefPkKey) {

		// 只要是BillModel，则使用相同。
		afterEditBody(billModel, iRow, sVdefValueKey, sVdefPkKey);

	}

	/**
	 * 功能： afterEdit后，设置表头自定义项主键。使用CardPanel时使用此方法 此方法执行完毕后，给定自定义项的相应PK值已设置完成
	 * 不区分是头还是体的自定义项，都可使用此方法
	 * 
	 * @author：王印芬
	 * @param BillData
	 *            bdata
	 * @param String
	 *            sVdefValueKey 自定义项值的KEY，一般在此KEY上进行输入
	 * @param String
	 *            sVdefPkKey 自定义项PK的KEY，
	 * @return void
	 * @serialData：(2003-12-11 19:02:29)
	 * 
	 */
	public static void afterEditHead(BillData bdata, String sVdefValueKey,
			String sVdefPkKey) {

		// 参数正确性判断
		if (bdata == null || sVdefPkKey == null || sVdefValueKey == null
				|| bdata.getHeadItem(sVdefPkKey) == null
				|| bdata.getHeadItem(sVdefValueKey) == null) {
			return;
		}

		BillItem item = bdata.getHeadItem(sVdefValueKey);
		// 根据类型判断
		if (item.getDataType() == BillItem.USERDEF || item.getDataType() == BillItem.UFREF) {
			UIRefPane refpane = (UIRefPane) item.getComponent();
			String sPk_defdoc = DataTypeTool.getString_Trim0LenAsNull(refpane
					.getRefPK());
      
			if (sPk_defdoc == null
					&& (refpane.getUITextField().getText() != null && refpane
							.getUITextField().getText().trim().length() > 0)) {
//				bdata.setHeadItem(sVdefValueKey, null);
				return;
			} else {
				bdata.setHeadItem(sVdefPkKey, sPk_defdoc);
			}
		} else {
			bdata.setHeadItem(sVdefPkKey, null);
		}

	}

	/**
	 * 功能：批量执行自定义项VO获取方法 创建者：晁志平 创建日期：(2004-9-7 20:03:40)
	 */
	private static Object[] getDefVOBatch(String pk_corp,String cbilltypecode) {

		// 产品组传过来
		if (m_objDefVos != null && m_objDefVos.length == 2) {
			return m_objDefVos;
		}
	
		// 产品组未处理
		Object[] objs = null;
		try {
			
//			String objheadname=getObjName(pk_corp,ScmConst.getHeadObjCode(cbilltypecode));
//			String objbodyname=getObjName(pk_corp,ScmConst.getBodyObjCode(cbilltypecode));
			
			String objheadcode=ScmConst.getHeadObjCode(cbilltypecode);
			String objbodycode=ScmConst.getBodyObjCode(cbilltypecode);
			
			if(objheadcode==null&&objbodycode==null)
				return null;
			
			List<DefVO[]> l = DefquoteQueryUtil.getInstance().queryDefusedVOByCodes(new String[]{objheadcode,objbodycode}, pk_corp);
			
			//objs = LocalCallService.callService(getTwoSCDs(pk_corp,cbilltypecode));
			if(l!=null)
				return l.toArray();
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("批量执行自定义项VO获取方法异常!详细信息如下：");
			nc.vo.scm.pub.SCMEnv.out(e);
		}
		return objs;
	}
	
	/**
	 * 功能：批量执行自定义项VO获取方法--查询模板 创建者：晁志平 创建日期：(2004-9-10 17:39:57)
	 */
	private static Object[] getDefVOBatch(boolean bycode,String pk_corp, String[] saDefType) {

		if (pk_corp == null || saDefType == null) {
			return null;
		}
		int iLen = saDefType.length;
		Object[] objsRet = new Object[iLen];
//		ServcallVO scd = null;
//		ArrayList listScd = new ArrayList();
//		for (int i = 0; i < iLen; i++) {
//			if (saDefType[i] == null || saDefType[i].trim().length() == 0) {
//				objsRet[i] = null;
//				continue;
//			}
//			scd = new ServcallVO();
//
//			scd.setBeanName(IDef.class.getName());
//			scd.setMethodName("queryDefVO");
//			scd.setParameter(new Object[] { saDefType[i], pk_corp });
//			scd.setParameterTypes(new Class[] { String.class, String.class });
//			listScd.add(scd);
//		}
//		if (listScd.size() == 0) {
//			return objsRet;
//		}
		Object[] objs = null;
		try {
			List<DefVO[]> l = null;
			if(bycode)
				l = DefquoteQueryUtil.getInstance().queryDefusedVOByCodes(saDefType, pk_corp);
			else
				l = DefquoteQueryUtil.getInstance().queryDefusedVO(saDefType, pk_corp);
//			objs = LocalCallService.callService((ServcallVO[]) listScd
//					.toArray(new ServcallVO[listScd.size()]));
			if(l!=null)
				objs = l.toArray(); 
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("批量执行自定义项VO获取方法异常!详细信息如下：");
			nc.vo.scm.pub.SCMEnv.out(e);
		}
		// 组织返回
		int iPos = 0;
		for (int i = 0; i < iLen; i++) {
			if (saDefType[i] != null && saDefType[i].trim().length() > 0) {
				objsRet[i] = objs[iPos];
				iPos++;
			}
		}
		return objsRet;
	}

	/**
	 * 创建者：晁志平 从给定串中解析索引 创建日期：(2001-11-9 16:18:40)
	 */
	private static int getIndex(String strSrc, String strBgn, String strEnd,
			int iAddNum) {
		int iIndex = -1;
		try {
			int iBgnLen = strBgn.length();
			String strTmp = strSrc.substring(iBgnLen);
			if (strEnd != null && strEnd.length() != 0) {
				strTmp = StringUtil.replaceAllString(strTmp,
						strEnd, "");
			}
			iIndex = new Integer(strTmp.trim()).intValue();
			iIndex--;
			iIndex += iAddNum;
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("从给定串中解析索引：" + "源串：" + strSrc + "前缀：" + strBgn
					+ "、后缀：" + strEnd + "。");
			nc.vo.scm.pub.SCMEnv.out(e);
		}
		// nc.vo.scm.pub.SCMEnv.out("从给定串中解析索引：" + "源串：" + strSrc + "索引：" + iIndex);
		return iIndex;
	}

	/**
	 * 功能：批量执行查询自定义项VO描述 返回：表头、表体自定义项VO描述 创建者：晁志平 创建日期：(2004-9-10 14:09:40)
	 */
//	public static ServcallVO[] getTwoSCDs(String pk_corp,String cbilltypecode) {
//		
//		
//		
//		String objheadname=getObjName(pk_corp,ScmConst.getHeadObjCode(cbilltypecode));
//		String objbodyname=getObjName(pk_corp,ScmConst.getBodyObjCode(cbilltypecode));
//		
//		if(objheadname==null&&objbodyname==null)
//			return null;
//    
//    
//		
//		ServcallVO[] scds = new ServcallVO[2];
//
//		scds[0] = new ServcallVO();
//		scds[0].setBeanName(IDef.class.getName());
//		scds[0].setMethodName("queryDefVO");
//		scds[0].setParameter(new Object[] {objheadname, pk_corp });// "供应链/ARAP单据头"
//		scds[0].setParameterTypes(new Class[] { String.class, String.class });
//
//		scds[1] = new ServcallVO();
//		scds[1].setBeanName(IDef.class.getName());
//		scds[1].setMethodName("queryDefVO");
//		scds[1].setParameter(new Object[] {objbodyname , pk_corp });//"供应链/ARAP单据体"
//		scds[1].setParameterTypes(new Class[] { String.class, String.class });
//
//		return scds;
//	}
	/**
	 * 功能：批量执行查询自定义项VO描述 返回：表头、表体自定义项VO描述 创建者：晁志平 创建日期：(2004-9-10 14:09:40)
	 */
	private static String getObjName(String pk_corp,String objcode) {
		
		if(!m_hmDefName.containsKey(pk_corp+objcode)){
			ScmDataSet data=new ScmDataSet();
			Object ohead=DBCacheFacade.runQuery(" select objname from bd_defused where  ( objcode= '"+objcode+"')",data);
			data=(ScmDataSet)ohead;
			String objname=null;
			if(data!=null&&data.getRowCount()>0)
				objname=(String)data.getValueAt(0,0);
				
//			Object obody=DBCacheFacade.runQuery(" select objname from bd_defused where  ( objcode= '"+getBodyObjCode(cbilltypecode)+"')",data);
//			
//			data=(ScmDataSet)obody;
//			String objbodyname=null;
//			if(data!=null&&data.getRowCount()>0)
//				objbodyname=(String)data.getValueAt(0,0);
			m_hmDefName.put(pk_corp+objcode, objname);
		}

		return (String)m_hmDefName.get(pk_corp+objcode);
	}
	/**
	 * 创建：晁志平 功能：是否拼接自定义项对象名称 参数： Object obj 待设置自定义项控件 创建日期：(2003-12-11
	 * 11:18:40)
	 */
	private static boolean isAddObjName(Object obj) {
		if (obj == null)
			return false;
		return obj instanceof nc.ui.pub.report.ReportBaseClass;
	}

	/**
	 * 创建者：晁志平 功能：根据自定义项键判断其是否为体自定义项 参数： String fieldCode 查询模板自定义项Code键 String
	 * bPrefix 查询模板自定义项前缀，非空 String bEndfix 查询模板自定义项后缀 int iStyle 调用者 返回：boolean
	 * 例外： 日期：(2002-12-09 19:57:29) 修改日期，修改人，修改原因，注释标志：
	 */
	private static boolean isBody(String fieldCode, String bPrefix,
			String bEndfix, int iStyle) {

		// 是否来自IC暂无影响
		if (bPrefix == null)
			return false;
		return fieldCode.startsWith(bPrefix);
	}

	/**
	 * 创建者：晁志平 功能：根据自定义项键判断其是否为头自定义项 参数： String fieldCode 查询模板自定义项Code键 String
	 * hPrefix 查询模板自定义项前缀，非空 String hEndfix 查询模板自定义项后缀 int iStyle 调用者 返回：boolean
	 * 例外： 日期：(2002-12-09 19:57:29) 修改日期，修改人，修改原因，注释标志：
	 */
	private static boolean isHead(String fieldCode, String hPrefix,
			String hEndfix, int iStyle) {

		// 是否来自IC
		boolean bIsFromIc = iStyle == _STYLE_IC;

		if (bIsFromIc) {
			return fieldCode.startsWith(hPrefix)
					&& fieldCode.endsWith(hEndfix == null ? "" : hEndfix);
		}
		return fieldCode.startsWith(hPrefix);
	}

	/**
	 * 功能：设置自定义项VO 创建者：晁志平 创建日期：(2004-9-10 13:52:40)
	 */
	public static void setTwoOBJs(Object[] objDefVos) {
		m_objDefVos = objDefVos;
	}

	/**
	 * 创建者：晁志平 功能：置换 BillCardPanel 的自定义项 参数： BillCardPanel panel 被更新的
	 * BillCardPanel String pk_corp 公司主键 String hPrefix 单据模板中单据头的自定义项前缀 String
	 * bPrefix 单据模板中单据体的自定义项前缀 返回：无 例外： 日期：(2003-12-09 21:07:29)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public static void updateBillCardPanelUserDef(BillCardPanel panel,
			String pk_corp,String cbilltypecode, String hPrefix, String bPrefix) {
		if (panel == null || panel.getBillData() == null)
			return;
		updateBillCardPanelUserDef(panel, pk_corp,cbilltypecode, hPrefix, null, 0, bPrefix,
				null, 0,1);
	}
	/**
	 * 创建者：晁志平 功能：置换 BillCardPanel 的自定义项 参数： BillCardPanel panel 被更新的
	 * BillCardPanel String pk_corp 公司主键 String hPrefix 单据模板中单据头的自定义项前缀 String
	 * bPrefix 单据模板中单据体的自定义项前缀 返回：无 例外： 日期：(2003-12-09 21:07:29)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public static void updateBillCardPanelUserDef(BillCardPanel panel,
			String pk_corp,String cbilltypecode, String hPrefix, String bPrefix,int iCount) {
		if (panel == null || panel.getBillData() == null)
			return;
		updateBillCardPanelUserDef(panel, pk_corp,cbilltypecode, hPrefix, null, 0, bPrefix,
				null, 0,iCount);
	}
	public static void updateBillCardPanelUserDef(BillCardPanel panel,
			String pk_corp,String cbilltypecode, String hPrefix, String hEndfix, int hAddNum,
			String bPrefix, String bEndfix, int bAddNum) {
		if (panel == null || panel.getBillData() == null)
			return;
		updateBillCardPanelUserDef(panel, pk_corp,cbilltypecode, hPrefix, null, 0, bPrefix,
				null, 0,1);
	}
	/**
	 * 创建者：晁志平 功能：置换BillData的自定义项 参数： BillData billData 被更新的BillCardPanel String
	 * pk_corp 公司主键 String hPrefix 单据模板中单据头的自定义项前缀 String hEndfix
	 * 单据模板中单据头的自定义项后缀 int hAddNum 表头修正数[只加] String bPrefix 单据模板中单据体的自定义项前缀
	 * String bEndfix 单据模板中单据体的自定义项后缀 int bAddNum 表体修正数[只加] 返回：无 例外：
	 * 日期：(2003-11-11 09:47:29) 修改日期，修改人，修改原因，注释标志：
	 */
	public static void updateBillCardPanelUserDef(BillCardPanel panel,
			String pk_corp,String cbilltypecode, String hPrefix, String hEndfix, int hAddNum,
			String bPrefix, String bEndfix, int bAddNum,int iCount) {

		// new Exception("==>>卡片 自定义项初始化，堆栈信息:").printStackTrace();

		try {
			if (panel == null) {
				nc.vo.scm.pub.SCMEnv.out("--> BillCardPanel is null.");
				return;
			}
			BillData billData = panel.getBillData();
			boolean bIsAddObjName = isAddObjName(panel);
			if (bIsAddObjName) {
				nc.vo.scm.pub.SCMEnv.out("待设自定义项控件是报表模板，要拼接自定义项对象名称");
			}
			if (billData == null) {
				nc.vo.scm.pub.SCMEnv.out("--> BillData is null.");
				return;
			}
			DefVO[] defs = null;
			// Object[] objs = getDefVOBatch(pk_corp);
			// if (objs == null || objs.length != 2) {
			// nc.vo.scm.pub.SCMEnv.out("批量远程调用出现异常，单据卡片自定义项设置异常返回!");
			// return;
			// }
			// 表头,查得对应于公司的该单据的自定义项设置
			// defs = nc.ui.bd.service.BDDef.queryDefVO("供应链/ARAP单据头", pk_corp);
			defs = getDefHead(pk_corp,cbilltypecode);// (DefVO[]) objs[0];
			String strHKey = hPrefix + (hEndfix == null ? "" : hEndfix);
			String strBKey = bPrefix + (bEndfix == null ? "" : bEndfix);
			if ((defs != null)) {
				updateItemByDef(billData, defs, hPrefix, hEndfix, hAddNum,
						true, bIsAddObjName,iCount);
				if (!strHKey.equals(strBKey))
					updateItemByDef(billData, defs, hPrefix, hEndfix, hAddNum,
							false, bIsAddObjName,iCount);
			}
			// 表体,查得对应于公司的该单据的自定义项设置
			// defs = nc.ui.bd.service.BDDef.queryDefVO("供应链/ARAP单据体", pk_corp);
			defs = getDefBody(pk_corp,cbilltypecode);// (DefVO[]) objs[1];
			if ((defs != null)) {
				updateItemByDef(billData, defs, bPrefix, bEndfix, bAddNum,
						false, bIsAddObjName,iCount);
			}
			//这个操作必要：否则表头Label等不能重画，自定义项名称不能正确显示。
			panel.setBillData(billData); 

		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("加载单据模板(卡片)自定义项时出现异常：");
			nc.vo.scm.pub.SCMEnv.out(e);
		}
	}
	/**
	 * 
	 * 方法功能描述：单据卡片模板加载自定义项
	 * <p>
	 * 解决表头表体有三套自定义项的问题，目前5.3询报价单、价格审批单专用，其中表体有一套自定义项的名称与表头名称相同。
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * @param panel
	 * @param pk_corp
	 * @param cbilltypecode
	 * @param hPrefix
	 * @param hEndfix
	 * @param hAddNum
	 * @param bPrefix
	 * @param bEndfix
	 * @param bAddNum
	 * <p>
	 * @author donggq
	 * @time 2008-5-6 下午07:54:10
	 */
	public static void updateBillCardPanelUserDefFor28And29(BillCardPanel panel,
      String pk_corp,String cbilltypecode, String hPrefix, String hEndfix, int hAddNum,
      String bPrefix, String bEndfix, int bAddNum) {

    // new Exception("==>>卡片 自定义项初始化，堆栈信息:").printStackTrace();

    try {
      if (panel == null) {
        nc.vo.scm.pub.SCMEnv.out("--> BillCardPanel is null.");
        return;
      }
      BillData billData = panel.getBillData();
      boolean bIsAddObjName = isAddObjName(panel);
      if (bIsAddObjName) {
        nc.vo.scm.pub.SCMEnv.out("待设自定义项控件是报表模板，要拼接自定义项对象名称");
      }
      if (billData == null) {
        nc.vo.scm.pub.SCMEnv.out("--> BillData is null.");
        return;
      }
      DefVO[] defs = null;
      // Object[] objs = getDefVOBatch(pk_corp);
      // if (objs == null || objs.length != 2) {
      // nc.vo.scm.pub.SCMEnv.out("批量远程调用出现异常，单据卡片自定义项设置异常返回!");
      // return;
      // }
      // 表头,查得对应于公司的该单据的自定义项设置
      // defs = nc.ui.bd.service.BDDef.queryDefVO("供应链/ARAP单据头", pk_corp);
      defs = getDefHead(pk_corp,cbilltypecode);// (DefVO[]) objs[0];
      String strHKey = hPrefix + (hEndfix == null ? "" : hEndfix);
      String strBKey = bPrefix + (bEndfix == null ? "" : bEndfix);
      if ((defs != null)) {
        updateItemByDef(billData, defs, hPrefix, hEndfix, hAddNum,
            true, bIsAddObjName,1);
      }
      // 表体,查得对应于公司的该单据的自定义项设置
      // defs = nc.ui.bd.service.BDDef.queryDefVO("供应链/ARAP单据体", pk_corp);
      defs = getDefBody(pk_corp,cbilltypecode);// (DefVO[]) objs[1];
      if ((defs != null)) {
        updateItemByDef(billData, defs, bPrefix, bEndfix, bAddNum,
            false, bIsAddObjName,1);
      }

      panel.setBillData(billData);

    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.out("加载单据模板(卡片)自定义项时出现异常：");
      nc.vo.scm.pub.SCMEnv.out(e);
    }
  }

	/**
	 * 创建者：方益 功能：置换列表界面的自定义项 参数：panel BillListPanel,pk_corp String,hPrefix
	 * String,bPrefix String 返回：BillListPanel 例外： 日期：(2002-09-18 09:47:29)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public static void updateBillListPanelUserDef(BillListPanel panel, // 被更新的BillListPanel
			String pk_corp,String cbilltypecode, // 公司主键
			String hPrefix, // 单据模板中单据头的自定义项前缀
			String bPrefix // 单据模板中单据体的自定义项前缀
	) {
		if (panel == null)
			return;
		updateBillListPanelUserDef(panel, pk_corp, cbilltypecode,hPrefix, null, 0, bPrefix,
				null, 0);
	}

	/**
	 * 创建者：晁志平 功能：置换单据列表界面的自定义项 参数： BillListPanel panel 待设置的单据模板(列表) String
	 * pk_corp 公司主键 String hPrefix 单据模板(列表)表头自定义项前缀 String hEndfix
	 * 单据模板(列表)表头自定义项后缀 int hAddNum 单据模板(列表)表头自定义项修正数[只加] String bPrefix
	 * 单据模板(列表)表体自定义项前缀 String bEndfix 单据模板(列表)表体自定义项后缀 int bAddNum
	 * 单据模板(列表)表体自定义项修正数[只加] 返回：void 例外： 日期：(2002-09-18 09:47:29)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public static void updateBillListPanelUserDef(BillListPanel panel,
			String pk_corp,String cbilltypecode, String hPrefix, String hEndfix, int hAddNum,
			String bPrefix, String bEndfix, int bAddNum) {
		try {
			// 进行自定义项定义用
			DefVO[] defs = null;
			BillListData bd = panel.getBillListData();
			if (bd == null) {
				nc.vo.scm.pub.SCMEnv.out("--> billdata null.");
				return;
			}
			// Object[] objs = getDefVOBatch(pk_corp);
			// if (objs == null || objs.length != 2) {
			// nc.vo.scm.pub.SCMEnv.out("批量远程调用出现异常，单据列表自定义项设置异常返回!");
			// return;
			// }
			// 表头,查得对应于公司的该单据的自定义项设置
			// defs = nc.ui.bd.service.BDDef.queryDefVO("供应链/ARAP单据头", pk_corp);
			defs = getDefHead(pk_corp,cbilltypecode); // (DefVO[]) objs[0];

			String strHKey = hPrefix + (hEndfix == null ? "" : hEndfix);
			String strBKey = bPrefix + (bEndfix == null ? "" : bEndfix);
			if ((defs != null)) {
				updateItemByDef(bd, defs, hPrefix, hEndfix, hAddNum, true);
				if (!strHKey.equals(strBKey))
					updateItemByDef(bd, defs, hPrefix, hEndfix, hAddNum, false);
			}

			// 表体,查得对应于公司的该单据的自定义项设置
			// defs = nc.ui.bd.service.BDDef.queryDefVO("供应链/ARAP单据体", pk_corp);
			defs = getDefBody(pk_corp,cbilltypecode);// (DefVO[]) objs[1];
			if ((defs != null)) {
				updateItemByDef(bd, defs, bPrefix, bEndfix, bAddNum, false);
			}
			//这个操作必要：否则表头Label等不能重画，自定义项名称不能正确显示。
			panel.setListData(bd);
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("加载单据模板(列表)自定义项时出现异常：");
			nc.vo.scm.pub.SCMEnv.out(e);
		}

	}
	/**
	 * 
	 * 方法功能描述：单据卡片模板加载自定义项
   * <p>
   * 解决表头表体有三套自定义项的问题，目前5.3询报价单、价格审批单专用，其中表体有一套自定义项的名称与表头名称相同.
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * @param panel
	 * @param pk_corp
	 * @param cbilltypecode
	 * @param hPrefix
	 * @param hEndfix
	 * @param hAddNum
	 * @param bPrefix
	 * @param bEndfix
	 * @param bAddNum
	 * <p>
	 * @author donggq
	 * @time 2008-5-6 下午08:02:26
	 */
	public static void updateBillListPanelUserDefFor28And29(BillListPanel panel,
      String pk_corp,String cbilltypecode, String hPrefix, String hEndfix, int hAddNum,
      String bPrefix, String bEndfix, int bAddNum) {
    try {
      // 进行自定义项定义用
      DefVO[] defs = null;
      BillListData bd = panel.getBillListData();
      if (bd == null) {
        nc.vo.scm.pub.SCMEnv.out("--> billdata null.");
        return;
      }
      // Object[] objs = getDefVOBatch(pk_corp);
      // if (objs == null || objs.length != 2) {
      // nc.vo.scm.pub.SCMEnv.out("批量远程调用出现异常，单据列表自定义项设置异常返回!");
      // return;
      // }
      // 表头,查得对应于公司的该单据的自定义项设置
      // defs = nc.ui.bd.service.BDDef.queryDefVO("供应链/ARAP单据头", pk_corp);
      defs = getDefHead(pk_corp,cbilltypecode); // (DefVO[]) objs[0];

      String strHKey = hPrefix + (hEndfix == null ? "" : hEndfix);
      String strBKey = bPrefix + (bEndfix == null ? "" : bEndfix);
      if ((defs != null)) {
        updateItemByDef(bd, defs, hPrefix, hEndfix, hAddNum, true);
      }

      // 表体,查得对应于公司的该单据的自定义项设置
      // defs = nc.ui.bd.service.BDDef.queryDefVO("供应链/ARAP单据体", pk_corp);
      defs = getDefBody(pk_corp,cbilltypecode);// (DefVO[]) objs[1];
      if ((defs != null)) {
        updateItemByDef(bd, defs, bPrefix, bEndfix, bAddNum, false);
      }
      panel.setListData(bd);

    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.out("加载单据模板(列表)自定义项时出现异常：");
      nc.vo.scm.pub.SCMEnv.out(e);
    }

  }

	/**
	 * 创建者：晁志平 功能：置换单据列表界面的自定义项 参数： BillListPanel panel 待设置的单据模板(列表) String
	 * pk_corp 公司主键 String hPrefix 单据模板(列表)表头自定义项前缀 String hEndfix
	 * 单据模板(列表)表头自定义项后缀 int hAddNum 单据模板(列表)表头自定义项修正数[只加] String bPrefix
	 * 单据模板(列表)表体自定义项前缀 String bEndfix 单据模板(列表)表体自定义项后缀 int bAddNum
	 * 单据模板(列表)表体自定义项修正数[只加] int iPos 单据模板(列表)表头表体自定义项分布情况 返回：void 例外：
	 * 日期：(2002-09-18 09:47:29) 修改日期，修改人，修改原因，注释标志：
	 */
	public static void updateBillListPanelUserDef(BillListPanel panel,
			String pk_corp,String cbilltypecode, String hPrefix, String hEndfix, int hAddNum,
			String bPrefix, String bEndfix, int bAddNum, int iPos) {
		if (iPos == _POS_NORMAL) {
			updateBillListPanelUserDef(panel, pk_corp,cbilltypecode, hPrefix, hEndfix,
					hAddNum, bPrefix, bEndfix, bAddNum);
			return;
		}
		boolean bHead = false;
		if (iPos == _POS_HEAD) {
			bHead = true;
		}
		try {
			// 进行自定义项定义用
			DefVO[] defs = null;
			BillListData bd = panel.getBillListData();
			if (bd == null) {
				nc.vo.scm.pub.SCMEnv.out("--> billdata null.");
				return;
			}
			// Object[] objs = getDefVOBatch(pk_corp);
			// if (objs == null || objs.length != 2) {
			// nc.vo.scm.pub.SCMEnv.out("批量远程调用出现异常，单据列表自定义项设置异常返回!");
			// return;
			// }
			// 表头,查得对应于公司的该单据的自定义项设置
			// defs = nc.ui.bd.service.BDDef.queryDefVO("供应链/ARAP单据头", pk_corp);
			defs = getDefHead(pk_corp,cbilltypecode);// (DefVO[]) objs[0];
			String strHKey = hPrefix + (hEndfix == null ? "" : hEndfix);
			String strBKey = bPrefix + (bEndfix == null ? "" : bEndfix);
			if ((defs != null)) {
				updateItemByDef(bd, defs, hPrefix, hEndfix, hAddNum, bHead);
			}
			// 表体,查得对应于公司的该单据的自定义项设置
			// defs = nc.ui.bd.service.BDDef.queryDefVO("供应链/ARAP单据体", pk_corp);
			defs = getDefBody(pk_corp,cbilltypecode);// (DefVO[]) objs[1];
			if ((defs != null)) {
				updateItemByDef(bd, defs, bPrefix, bEndfix, bAddNum, bHead);
			}
			panel.setListData(bd);

		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("加载单据模板(列表)自定义项时出现异常：");
			nc.vo.scm.pub.SCMEnv.out(e);
		}

	}

	/**
	 * 创建：晁志平 功能：更新自定义项 参数： BillData bd 单据模板卡片 DefVO[] defVOs 自定义项VO[] String
	 * fieldPrefix 自定义项前缀 String fieldEndfix 自定义项后缀 int iAddNum 自定义项修正数[只加]
	 * boolean isHead 是否表头 boolean isAddNumObjName 是否拼接自定义项对象名称
	 * 
	 * 创建日期：(2001-11-9 16:18:40)
	 */
	public static void updateItemByDef(BillData bd,
			nc.vo.bd.def.DefVO[] defVOs, String fieldPrefix,
			String fieldEndfix, int iAddNum, boolean isHead,
			boolean isAddNumObjName,int iConut) {
		if (defVOs == null)
			return;
		Integer iOldIndex = null;
		for (int i = 0; i < defVOs.length; i++) {
			nc.vo.bd.def.DefVO defVO = defVOs[i];
			if (defVOs[i] == null) {
				continue;
			}
			String itemkey = null;
			if(fieldPrefix == null){
				itemkey = defVO.getFieldName();
			}else{
				String strTemp = defVO.getFieldName();
				/* 解析key */ 
				//2009/10/27 田锋涛  -  对取下标做了修改；物资需求的自定义项为vhdef1，.. 的形式，strTemp.substring(4)取不到正确的下标
				String sDefKeysuffix = getDefKeySuffix(strTemp);
				strTemp = (sDefKeysuffix == null) ? strTemp.substring(4) : sDefKeysuffix;
				
				if (iAddNum != 0) {
					iOldIndex = new Integer(strTemp);
					nc.vo.scm.pub.SCMEnv.out("原来下标：" + iOldIndex);
					iOldIndex = new Integer(iOldIndex.intValue() + iAddNum);
					strTemp = iOldIndex.toString();
					nc.vo.scm.pub.SCMEnv.out("调整后下标：" + strTemp);
				}
				itemkey = fieldPrefix + strTemp;
			}
			if (fieldEndfix != null) {
				itemkey += fieldEndfix.trim();
			}

			BillItem item = null;

			// 位置
			if (isHead) {
				item = bd.getHeadItem(itemkey);
				if (item == null)
					item = bd.getTailItem(itemkey);
				if (item == null)
					item = bd.getBodyItem(itemkey);
				updateItem(item,defVO,fieldPrefix,isAddNumObjName,true);
			} else{
				if(iConut==1){
					item = bd.getBodyItem(itemkey);
					updateItem(item,defVO,fieldPrefix,isAddNumObjName,false);
				}else{
					for(int j = 0 ; j < iConut ; j ++){
						item = bd.getBodyItem("table"+String.valueOf(j),itemkey);
						updateItem(item,defVO,fieldPrefix,isAddNumObjName,false);
					}
				}
			}

			
		}
	}
	
	/**
	 * 
	 * 作者：田锋涛
	 * 功能：获取自定义项的下标，如hdef1，hdef2，，，取下标为1,2,..
	 * 参数：strValue - 自定义项key
	 * 返回：下标值
	 * 例外：
	 * 日期：2009-10-27
	 * 修改日期， 修改人，修改原因，注释标志
	 */
	public static String getDefKeySuffix(String strValue){
		
		Matcher matcher = getDefKeyPattern().matcher(strValue);
		
		if(!matcher.matches()) return null;
		
		return matcher.group(2);

	}

	/**
	 * 作者：田锋涛
	 * 功能：获取自定义项key的匹配模式
	 * 参数：
	 * 返回：Pattern - 匹配模式
	 * 例外：
	 * 日期：2009-10-27
	 * 修改日期， 修改人，修改原因，注释标志
	 */
	private static Pattern getDefKeyPattern() {
		if(null == defKeyPattern)
			defKeyPattern = Pattern.compile("([a-zA-Z]*[\\d]*[a-zA-Z]+)([\\d]+)");
		return defKeyPattern;
	}
	
	private static void updateItem(BillItem item,nc.vo.bd.def.DefVO defVO,String fieldPrefix,boolean isAddNumObjName,boolean isHead){
		if (item != null) {
			
			//模板中定义的类型
			int iDataType = item.getDataType();
			
			if (defVO != null) {

				String itemKey = item.getKey();

				// 用户没有调整过，才设置为集团自定义项名称
				String fieldName = item.getName();
				if (fieldName == null || fieldName.trim().equals("")
						|| fieldName.indexOf(KEY_NOT_MODIFIED_1) >= 0
						|| fieldName.indexOf(KEY_NOT_MODIFIED_2) >= 0) {
					if (itemKey.indexOf(fieldPrefix) >= 0) {
//						if (isAddNumObjName) {
//							String resID = "D" + defVO.getDefcode();
//							String multiLangStr = nc.ui.ml.NCLangRes
//									.getInstance().getStrByID("bd_defused",
//											resID);
//
//							item.setName(strPre
//									+ transRes(defVO.getObjName()) + strEnd
//									+ defVO.getDefname());
//						} else {
							item.setName(defVO.getDefname());
//						}
					}
				}
				// 输入长度
				int inputlength = defVO.getLengthnum().intValue();
				item.setLength(inputlength);
				// 数据类型
				String iTypeDef = defVO.getType();
				int datatype = BillItem.STRING;
				if (iTypeDef.equals("备注"))
					datatype = BillItem.STRING;
				else if (iTypeDef.equals("日期"))
					datatype = BillItem.DATE;
				else if (iTypeDef.equals("数字")) {
					datatype = BillItem.INTEGER;
					if ((defVO.getDigitnum() != null)
							&& (defVO.getDigitnum().intValue() > 0)) {
						datatype = BillItem.DECIMAL;
						item.setDecimalDigits(defVO.getDigitnum().intValue());
					}
				}
				
				/**
				 * 元数据模型的自定义项要将MetaDataProperty属性置空
				 * v5.5 暂时的解决办法。v6.0UAP统一解决
				 */
				if (item.getMetaDataProperty()!=null){
					item.setMetaDataProperty(null);
				}
				
				//有档案的自定义项才认为是自定义项参照类型，否则按用户定义的类型设置
				String strDefDocReftype = defVO.getDefdef().getPk_bdinfo();
				if (iTypeDef.equals("统计") && strDefDocReftype != null){
					datatype = BillItem.USERDEF;
					item.setRefType(strDefDocReftype);
				}
				item.setDataType(datatype);
				item.reCreateComponent();
				item.setIsDef(true);
				
				if (item.getDataType() == BillItem.UFREF || item.getDataType() == BillItem.USERDEF) {
					if(!isHead){
						item.setDataType(BillItem.UFREF);
					}
					UIRefPane ref = (UIRefPane) item.getComponent();
					//统计型自定义项需要自动校验其数据的有效性，since V57
					ref.setAutoCheck(true);
					//设置返回名称
					ref.setReturnCode(false);
				}
			} else {
				// item.setShow(false);
			}
		}
	}
	/**
	 * 创建：晁志平 功能：更新列表模版自定义项 参数： BillListData bd 单据列表模版BillListData DefVO[]
	 * defVOs 自定义项VO[] String fieldPrefix 自定义项前缀 String fieldEndfix 自定义项后缀 int
	 * iAddNum 自定义项修正数[只加] boolean isHead 是否表头
	 * 
	 * 创建日期：(2003-12-9 20:38:40)
	 */
	public static void updateItemByDef(BillListData bd,
			nc.vo.bd.def.DefVO[] defVOs, String fieldPrefix,
			String fieldEndfix, int iAddNum, boolean isHead) {
		if (defVOs == null)
			return;

		Integer iOldIndex = null;
		for (int i = 0; i < defVOs.length; i++) {
			nc.vo.bd.def.DefVO defVO = defVOs[i];
			if (defVOs[i] == null) {
				continue;
			}
			
			String itemkey=defVO.getFieldName();
       if(fieldEndfix!=null)
          itemkey=itemkey+fieldEndfix;
       

			BillItem item = null;

			// 位置
			if (isHead)
				item = bd.getHeadItem(itemkey);
			else
				item = bd.getBodyItem(itemkey);

			if (item != null) {
				if (defVO != null) {

					String itemKey = item.getKey();
					// 用户没有调整过，才设置为集团自定义项名称
					String fieldName = item.getName();
					if (fieldName == null || fieldName.trim().equals("")
							|| fieldName.indexOf(KEY_NOT_MODIFIED_1) >= 0
							|| fieldName.indexOf(KEY_NOT_MODIFIED_2) >= 0
							|| fieldName.startsWith(fieldPrefix)) {
						if (itemKey.indexOf(fieldPrefix) >= 0) {
							item.setName(defVO.getDefname());
						}
					}
					// 输入长度
					int inputlength = defVO.getLengthnum().intValue();
					item.setLength(inputlength);
					// 数据类型
					String type = defVO.getType();
					int datatype = BillItem.STRING;
					if (type.equals("备注"))
						datatype = BillItem.STRING;
					else if (type.equals("日期"))
						datatype = BillItem.DATE;
					else if (type.equals("数字")) {
						datatype = BillItem.INTEGER;
						if ((defVO.getDigitnum() != null)
								&& (defVO.getDigitnum().intValue() > 0)) {
							datatype = BillItem.DECIMAL;
							item.setDecimalDigits(defVO.getDigitnum()
									.intValue());
						}
					}
					
					/**
					 * 元数据模型的自定义项要将MetaDataProperty属性置空
					 * v5.5 暂时的解决办法。v6.0UAP统一解决
					 */
					if (item.getMetaDataProperty()!=null){
						item.setMetaDataProperty(null);
					}
						
					if (type.equals("统计"))
						datatype = BillItem.USERDEF;
					item.setDataType(datatype);
					// 参照类型
					String reftype = defVO.getDefdef().getPk_bdinfo();
					// String reftype = defVO.getPk_defdef();
					if (type.equals("统计")) {
						item.setRefType(reftype);
					}

					item.reCreateComponent();
					
				} else {
					// item.setShow(false);
				}
			}
		}
	}

	/**
	 * 创建者：方益 功能：置换存货管理档案和供应管理档案的查询模板自定义项 参数：panel BillCardPanel,pk_corp
	 * String,prefix 返回：BillCardPanel 例外： 日期：(2002-09-18 09:47:29)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	private static void updateQueryCondition(QueryConditionClient client,
	// 被更新的QueryConditionClient
			String pk_corp, // 公司主键
			String docName, String prefix // 模板中自定义项前缀
	) {

		// 定义参照的长度
		final int iMaxLenOfDef = 20;

		if (client == null || prefix == null || prefix.length() <= 0) // ||
																		// bPrefix
																		// ==
																		// null
																		// ||
																		// bPrefix.length()
																		// <=0)
			return;

		// 查询对应公司的存货档案的自定义项
    List<DefVO[]> l = DefquoteQueryUtil.getInstance().queryDefusedVO(new String[]{docName}, pk_corp);
    DefVO[] defs =  null;
    if(l!=null && l.size()>0)
      defs = l.get(0);
		//
		updateQueryConditionByDefVOs(client, pk_corp, prefix, defs);
	}

	/**
	 * 创建者：方益 功能：置换存货管理档案和供应管理档案的查询模板自定义项 参数：panel BillCardPanel,pk_corp
	 * String,prefix 返回：BillCardPanel 例外： 日期：(2002-09-18 09:47:29)
	 * 修改日期，修改人，修改原因，注释标志： 晁志平 增加前后能交互次数优化处理功能 2004-09-10
	 */
	private static void updateQueryConditionByDefVOs(
			QueryConditionClient client,
			// 被更新的QueryConditionClient
			String pk_corp, // 公司主键
			String prefix,// 模板中自定义项前缀
			DefVO[] defs) {
		if (defs == null) {
			nc.vo.scm.pub.SCMEnv.out("传入参数：自定义项VO为空，直接返回!");
			return;
		}
		// 定义参照的长度
		final int iMaxLenOfDef = 20;

		if (client == null || prefix == null || prefix.length() <= 0) // ||
																		// bPrefix
																		// ==
																		// null
																		// ||
																		// bPrefix.length()
																		// <=0)
			return;

		// 查询对应公司的存货档案的自定义项
		// DefVO[] defs = nc.ui.bd.service.BDDef.queryDefVO(docName, pk_corp);
		Vector v = new Vector();
		nc.vo.pub.query.QueryConditionVO[] cs = client.getConditionDatas();
		for (int i = 0; cs != null && i < cs.length; i++) {
			String fieldCode = cs[i].getFieldCode();
			String fieldName = cs[i].getFieldName();
			DefVO vo = null;
			if (fieldCode == null)
				continue;

			// 获取查询模板中自定义项的VO
			if (fieldCode.startsWith(prefix) && defs != null) {
				int iIndex = -1;
				try {
					int len = prefix.length();
					iIndex = new Integer(fieldCode.substring(len)).intValue();
					iIndex--;

				} catch (Exception e) {
					nc.vo.scm.pub.SCMEnv.out("键值解释索引异常");
				}
				if (iIndex >= 0 && iIndex < defs.length && defs[iIndex] != null) {
					// 用户没有调整过，才设置为集团自定义项名称
					if (fieldName == null || fieldName.trim().equals("")
							|| fieldName.indexOf(KEY_NOT_MODIFIED_1) >= 0
							|| fieldName.indexOf(KEY_NOT_MODIFIED_2) >= 0) {
						client.setFieldName(fieldCode, strPre
								+ transRes(defs[iIndex].getObjName()) + strEnd
								+ defs[iIndex].getDefname());
					}
					vo = defs[iIndex];
				}
				if (vo != null) {
					cs[i].setDispType(new Integer(1));
					cs[i].setReturnType(new Integer(1));
					v.add(cs[i]);
				}
			} else
				v.add(cs[i]);

			// 设置参照
			if (vo != null) {
				String type = vo.getType();
				nc.ui.pub.beans.UIRefPane refPane = new nc.ui.pub.beans.UIRefPane();
				if (type.equals("统计")) {
					/* 自定义项列表主键为空，认为是基础数据档案 */
					if (vo.getDefdef() != null
							&& vo.getDefdef().getPk_defdoclist() == null) {
						refPane = nc.ui.bd.ref.RefCall.getUIRefPane(vo
								.getDefdef().getPk_bdinfo(), pk_corp);
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
						refPane.setReturnCode(false);
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
					if (vo.getDigitnum() == null) {
						refPane.setNumPoint(0);
					} else {
						refPane.setNumPoint(vo.getDigitnum().intValue());
					}
				}
				client.setValueRef(fieldCode, refPane);
			}
		}
		nc.vo.pub.query.QueryConditionVO[] vos = null;
		if (v.size() > 0) {
			vos = new nc.vo.pub.query.QueryConditionVO[v.size()];
			v.copyInto(vos);
		}
		client.setConditionDatas(vos);
	}

	/**
	 * 创建者：方益 功能：置换查询模板的自定义项 参数： QueryConditionClient client
	 * 被更新的QueryConditionClient String pk_orp 公司主键 String hPrefix
	 * 单据模板中单据头的自定义项前缀 String bPrefix 单据模板中单据体的自定义项前缀 返回：void 例外： 日期：(2002-09-18
	 * 09:47:29) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 2003-12-09，晁志平，统一处理
	 */
	public static void updateQueryConditionClientUserDef(
			QueryConditionClient client, String pk_corp,String cbilltypecode,String hPrefix,
			String bPrefix) {
		updateQueryConditionClientUserDef(client, pk_corp,cbilltypecode,hPrefix, null, 0,
				bPrefix, null, 0);
	}

	/**
	 * 创建者：晁志平 功能：置换查询模板的自定义项 参数： QueryConditionClient client 待设置的查询模板 String
	 * pk_corp 公司主键 String hPrefix 查询模板表头自定义项前缀，非空 String hEndfix 查询模板表头自定义项后缀
	 * int hAddDigit 查询模板表头自定义项修正数[只加] String bPrefix 查询模板表体自定义项前缀，非空 String
	 * bEndfix 查询模板表体自定义项后缀 int bAddDigit 查询模板表体自定义项修正数[只加] 返回：void 例外：
	 * 日期：(2002-12-09 19:57:29) 修改日期，修改人，修改原因，注释标志：
	 */
	public static void updateQueryConditionClientUserDef(
			QueryConditionClient client, String pk_corp, String cbilltypecode, String hPrefix,
			String hEndfix, int hAddNum, String bPrefix, String bEndfix,
			int bAddNum) {
		updateQueryConditionClientUserDef(client, pk_corp, cbilltypecode,hPrefix, hEndfix,
				hAddNum, bPrefix, bEndfix, bAddNum, _STYLE_NORMAL);
	}

	/**
	 * 创建者：晁志平 功能：置换查询模板的自定义项 参数： QueryConditionClient client 待设置的查询模板 String
	 * pk_corp 公司主键 String hPrefix 查询模板表头自定义项前缀，非空 String hEndfix 查询模板表头自定义项后缀
	 * int hAddDigit 查询模板表头自定义项修正数[只加] String bPrefix 查询模板表体自定义项前缀，非空 String
	 * bEndfix 查询模板表体自定义项后缀 int bAddDigit 查询模板表体自定义项修正数[只加] int iStyle 调用者
	 * 返回：void 例外： 日期：(2002-12-09 19:57:29) 修改日期，修改人，修改原因，注释标志：
	 */
	public static void updateQueryConditionClientUserDef(
			QueryConditionClient client, String pk_corp,String cbilltypecode, String hPrefix,
			String hEndfix, int hAddNum, String bPrefix, String bEndfix,
			int bAddNum, int iStyle) {
		try {
			// 定义参照的长度
			final int iMaxLenOfDef = 20;

			if (client == null || hPrefix == null || hPrefix.length() <= 0)
				return;
			// 查询对应公司的单据头和单据体自定义项
			Object[] objs = null;
			DefVO[] hDefs = null;
			DefVO[] bDefs = null;
			if (bPrefix != null) {
				// objs = getDefVOBatch(pk_corp);
				// if (objs == null || objs.length != 2) {
				// nc.vo.scm.pub.SCMEnv.out("批量远程调用出现异常，查询模板自定义项设置异常返回!");
				// return;
				// }
				hDefs = getDefHead(pk_corp,cbilltypecode);// (DefVO[]) objs[0];
				bDefs = getDefBody(pk_corp,cbilltypecode);// (DefVO[]) objs[1];
			} else {
        
        List<DefVO[]> l = DefquoteQueryUtil.getInstance().queryDefusedVO(new String[]{"供应链/ARAP单据头"}, pk_corp);
        if(l!=null && l.size()>0)
          hDefs = l.get(0);
          //nc.ui.bd.service.BDDef.queryDefVO("供应链/ARAP单据头",pk_corp);
			}
      if(bEndfix!=null&&bEndfix.indexOf("_b_")!=-1){
        updateQueryConditionDelivClientUserDef(client, pk_corp,hPrefix,
            hEndfix, hAddNum, bPrefix, bEndfix, bAddNum, iStyle, hDefs,
            bDefs,cbilltypecode);
      }
      else{
			updateQueryConditionClientUserDef(client, pk_corp,hPrefix,
					hEndfix, hAddNum, bPrefix, bEndfix, bAddNum, iStyle, hDefs,
					bDefs,cbilltypecode);
      }
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("加载查询模板自定义项时出现异常：");
			nc.vo.scm.pub.SCMEnv.out(e);
		}
	}

	/**
	 * 创建者：晁志平 功能：置换查询模板的自定义项 参数： QueryConditionClient client 待设置的查询模板 String
	 * pk_corp 公司主键 String hPrefix 查询模板表头自定义项前缀，非空 String hEndfix 查询模板表头自定义项后缀
	 * int hAddDigit 查询模板表头自定义项修正数[只加] String bPrefix 查询模板表体自定义项前缀，非空 String
	 * bEndfix 查询模板表体自定义项后缀 int bAddDigit 查询模板表体自定义项修正数[只加] int iStyle 调用者
	 * DefVO[] hDefs 单据表头自定义项VO[] DefVO[] hDefs 单据表体自定义项VO[] 返回：void 例外：
	 * 日期：(2004-09-10 17:32:29) 修改日期，修改人，修改原因，注释标志：
	 */
	private static void updateQueryConditionClientUserDef(
			QueryConditionClient client, String pk_corp, String hPrefix,
			String hEndfix, int hAddNum, String bPrefix, String bEndfix,
			int bAddNum, int iStyle, DefVO[] hDefs, DefVO[] bDefs,String cbilltypecode) {
		try {
			// 定义参照的长度
			final int iMaxLenOfDef = 20;

			if (client == null || hPrefix == null || hPrefix.length() <= 0)
				return;
			nc.vo.pub.query.QueryConditionVO[] cs = client.getConditionDatas();
			Vector v = new Vector();
			for (int i = 0; cs != null && i < cs.length; i++) {
				String fieldCode = cs[i].getFieldCode();
				String fieldName = cs[i].getFieldName();
				DefVO vo = null;
				if (fieldCode == null)
					continue;
//        if(hEndfix!=null)
//          hEndfix=hEndfix+i;
//        if(fieldCode.indexOf(bEndfix)!=-1){
//          int j=0;
//          bEndfix=bEndfix+j;
//        }
				boolean bIsHead = isHead(fieldCode, hPrefix, hEndfix, iStyle);
				boolean bIsBody = isBody(fieldCode, bPrefix, bEndfix, iStyle);
				// 查询条件来自表头中的的自定义项
				if (bIsHead && hDefs != null) {
					int iIndex = -1;
					iIndex = getIndex(fieldCode, hPrefix, hEndfix, hAddNum);
					if (iIndex >= 0 && iIndex < hDefs.length
							&& hDefs[iIndex] != null) {
						// 用户没有调整过，才设置为集团自定义项名称
						if (fieldName == null || fieldName.trim().equals("")
								|| fieldName.indexOf(KEY_NOT_MODIFIED_1) >= 0
								|| fieldName.indexOf(KEY_NOT_MODIFIED_2) >= 0) {
							client.setFieldName(fieldCode, strPre
									+ transRes(hDefs[iIndex].getObjName(),cbilltypecode,true)
									+ strEnd + hDefs[iIndex].getDefname());
						}
						vo = hDefs[iIndex];
					}
					if (vo != null) {
						cs[i].setDispType(new Integer(1));
						cs[i].setReturnType(new Integer(1));
						v.add(cs[i]);
					}
				}
				// 查询条件来自表体中的的自定义项
				else if (bIsBody && bDefs != null) {
					int iIndex = -1;
					iIndex = getIndex(fieldCode, bPrefix, bEndfix, bAddNum);
					if (iIndex >= 0 && iIndex < bDefs.length
							&& bDefs[iIndex] != null) {
						// 用户没有调整过，才设置为集团自定义项名称
						if (fieldName == null || fieldName.trim().equals("")
								|| fieldName.indexOf(KEY_NOT_MODIFIED_1) >= 0
								|| fieldName.indexOf(KEY_NOT_MODIFIED_2) >= 0) {
							client.setFieldName(fieldCode, strPre
									+ transRes(bDefs[iIndex].getObjName(),cbilltypecode,false)
									+ strEnd + bDefs[iIndex].getDefname());
						}
						vo = bDefs[iIndex];
					}
					if (vo != null) {
						cs[i].setDispType(new Integer(1));
						cs[i].setReturnType(new Integer(1));
						v.add(cs[i]);
					}
				} else
					v.add(cs[i]);
				// 设置参照
				if (vo != null) {
					String type = vo.getType();
					nc.ui.pub.beans.UIRefPane refPane = new nc.ui.pub.beans.UIRefPane();
					if (type.equals("统计")) {
						/* 自定义项列表主键为空，认为是基础数据档案 */
						if (vo.getDefdef() != null
								&& vo.getDefdef().getPk_defdoclist() == null) {
							refPane = nc.ui.bd.ref.RefCall.getUIRefPane(vo
									.getDefdef().getPk_bdinfo(), pk_corp);
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
//				            begin ncm linsf 201011052105111857贵州桥梁_自定义项只显示本公司和集团
							sWhereString += " and pk_corp in('"+pk_corp+"','0001')";
								//end ncm linsf 201011052105111857贵州桥梁_自定义项只显示本公司和集团
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
					client.setValueRef(fieldCode, refPane);
				}
			}
			nc.vo.pub.query.QueryConditionVO[] vos = null;
			if (v.size() > 0) {
				vos = new nc.vo.pub.query.QueryConditionVO[v.size()];
				v.copyInto(vos);
			}
			if(vos == null){
				QueryConditionVO voError = new QueryConditionVO();
				voError.setDataType(new Integer(0));
				voError.setDirty(true);
				voError.setConsultCode("公司目录");/*-=notranslate=-*/
				voError.setDispSequence(new Integer(0));
				voError.setDispType(new Integer(1));
				voError.setDispValue("Error Value");
				voError.setDr(new Integer(1));
				voError.setFieldCode("nothiscode");
				voError.setFieldName("ErrorName");
				voError.setId("dddddd");
				voError.setId("Error00000012");
				voError.setIfAutoCheck(UFBoolean.FALSE);
				voError.setIfDataPower(UFBoolean.FALSE);
				voError.setIfDefault(UFBoolean.FALSE);
				voError.setIfDesc(UFBoolean.FALSE);
				voError.setIfGroup(UFBoolean.FALSE);
				voError.setIfImmobility(UFBoolean.FALSE);
				voError.setIfMust(UFBoolean.FALSE);
				voError.setIfOrder(UFBoolean.FALSE);
				voError.setIfSum(UFBoolean.FALSE);
				voError.setIfUsed(UFBoolean.FALSE);
				voError.setIsUserDef(UFBoolean.TRUE);
				voError.setNodecode("2014XXXX");
				voError.setOperaCode("=");
				voError.setOperaName("Equals");
				voError.setOrderSequence(new Integer(0));
				voError.setPkCorp("1001");
				voError.setPkTemplet("2014XXXXYYYYXXXXYYYY");
				voError.setPrimaryKey("2014XXXXYYYYXXXX0001");
				voError.setResid("ddd");
				voError.setReturnType(new Integer(0));
				voError.setStatus(0);
				voError.setTableCode("ddcode");
				voError.setTableName("ddname");
				voError.setTs(new UFDateTime(System.currentTimeMillis()));
				voError.setValue("error Value");
				vos = new QueryConditionVO[]{voError};
			}
			client.setConditionDatas(vos);
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("加载查询模板自定义项时出现异常：");
			nc.vo.scm.pub.SCMEnv.out(e);
		}
	}

  private static void updateQueryConditionDelivClientUserDef(
      QueryConditionClient client, String pk_corp, String hPrefix,
      String hEndfix, int hAddNum, String bPrefix, String bEndfix,
      int bAddNum, int iStyle, DefVO[] hDefs, DefVO[] bDefs,String cbilltypecode) {
    try {
      // 定义参照的长度
      final int iMaxLenOfDef = 20;

      if (client == null || hPrefix == null || hPrefix.length() <= 0)
        return;
      nc.vo.pub.query.QueryConditionVO[] cs = client.getConditionDatas();
      Vector v = new Vector();
      for (int i = 0; cs != null && i < cs.length; i++) {
        String fieldCode = cs[i].getFieldCode();
        String fieldName = cs[i].getFieldName();
        DefVO vo = null;
        if (fieldCode == null)
          continue;
//        if(hEndfix!=null)
//          hEndfix=hEndfix+i;
//        if(fieldCode.indexOf(bEndfix)!=-1){
//          int j=0;
//          bEndfix=bEndfix+j;
//        }
        boolean bIsHead = isDelivHead(fieldName);
        boolean bIsBody = isBody(fieldCode, bPrefix, bEndfix, iStyle);
        // 查询条件来自表头中的的自定义项
        if (bIsHead && hDefs != null) {
          int iIndex = -1;
          iIndex = getIndex(fieldCode, hPrefix, hEndfix, hAddNum);
          if (iIndex >= 0 && iIndex < hDefs.length
              && hDefs[iIndex] != null) {
            // 用户没有调整过，才设置为集团自定义项名称
            if (fieldName == null || fieldName.trim().equals("")
                || fieldName.indexOf(KEY_NOT_MODIFIED_1) >= 0
                || fieldName.indexOf(KEY_NOT_MODIFIED_2) >= 0) {
              client.setFieldName(fieldCode, strPre
                  + transRes(hDefs[iIndex].getObjName(),cbilltypecode,true)
                  + strEnd + hDefs[iIndex].getDefname());
            }
            vo = hDefs[iIndex];
          }
          if (vo != null) {
            cs[i].setDispType(new Integer(1));
            cs[i].setReturnType(new Integer(1));
            v.add(cs[i]);
          }
        }
        // 查询条件来自表体中的的自定义项
        else if (bIsBody && bDefs != null) {
          int iIndex = -1;
          iIndex = getIndex(fieldCode, bPrefix, bEndfix, bAddNum);
          if (iIndex >= 0 && iIndex < bDefs.length
              && bDefs[iIndex] != null) {
            // 用户没有调整过，才设置为集团自定义项名称
            if (fieldName == null || fieldName.trim().equals("")
                || fieldName.indexOf(KEY_NOT_MODIFIED_1) >= 0
                || fieldName.indexOf(KEY_NOT_MODIFIED_2) >= 0) {
              client.setFieldName(fieldCode, strPre
                  + transRes(bDefs[iIndex].getObjName(),cbilltypecode,false)
                  + strEnd + bDefs[iIndex].getDefname());
            }
            vo = bDefs[iIndex];
          }
          if (vo != null) {
            cs[i].setDispType(new Integer(1));
            cs[i].setReturnType(new Integer(1));
            v.add(cs[i]);
          }
        } else
          v.add(cs[i]);
        // 设置参照
        if (vo != null) {
          String type = vo.getType();
          nc.ui.pub.beans.UIRefPane refPane = new nc.ui.pub.beans.UIRefPane();
          if (type.equals("统计")) {
            /* 自定义项列表主键为空，认为是基础数据档案 */
            if (vo.getDefdef() != null
                && vo.getDefdef().getPk_defdoclist() == null) {
              refPane = nc.ui.bd.ref.RefCall.getUIRefPane(vo
                  .getDefdef().getPk_bdinfo(), pk_corp);
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
//            begin ncm linsf 201011052105111857贵州桥梁_自定义项只显示本公司和集团
			  sWhereString += " and pk_corp in('"+pk_corp+"','0001')";
				//end ncm linsf 201011052105111857贵州桥梁_自定义项只显示本公司和集团
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
          client.setValueRef(fieldCode, refPane);
        }
      }
      nc.vo.pub.query.QueryConditionVO[] vos = null;
      if (v.size() > 0) {
        vos = new nc.vo.pub.query.QueryConditionVO[v.size()];
        v.copyInto(vos);
      }
      if(vos == null){
        QueryConditionVO voError = new QueryConditionVO();
        voError.setDataType(new Integer(0));
        voError.setDirty(true);
        voError.setConsultCode("公司目录");/*-=notranslate=-*/
        voError.setDispSequence(new Integer(0));
        voError.setDispType(new Integer(1));
        voError.setDispValue("Error Value");
        voError.setDr(new Integer(1));
        voError.setFieldCode("nothiscode");
        voError.setFieldName("ErrorName");
        voError.setId("dddddd");
        voError.setId("Error00000012");
        voError.setIfAutoCheck(UFBoolean.FALSE);
        voError.setIfDataPower(UFBoolean.FALSE);
        voError.setIfDefault(UFBoolean.FALSE);
        voError.setIfDesc(UFBoolean.FALSE);
        voError.setIfGroup(UFBoolean.FALSE);
        voError.setIfImmobility(UFBoolean.FALSE);
        voError.setIfMust(UFBoolean.FALSE);
        voError.setIfOrder(UFBoolean.FALSE);
        voError.setIfSum(UFBoolean.FALSE);
        voError.setIfUsed(UFBoolean.FALSE);
        voError.setIsUserDef(UFBoolean.TRUE);
        voError.setNodecode("2014XXXX");
        voError.setOperaCode("=");
        voError.setOperaName("Equals");
        voError.setOrderSequence(new Integer(0));
        voError.setPkCorp("1001");
        voError.setPkTemplet("2014XXXXYYYYXXXXYYYY");
        voError.setPrimaryKey("2014XXXXYYYYXXXX0001");
        voError.setResid("ddd");
        voError.setReturnType(new Integer(0));
        voError.setStatus(0);
        voError.setTableCode("ddcode");
        voError.setTableName("ddname");
        voError.setTs(new UFDateTime(System.currentTimeMillis()));
        voError.setValue("error Value");
        vos = new QueryConditionVO[]{voError};
      }
      client.setConditionDatas(vos);
    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.out("加载查询模板自定义项时出现异常：");
      nc.vo.scm.pub.SCMEnv.out(e);
    }
  }
  
  public static boolean isDelivHead(String fieldName){
    if(fieldName.startsWith("H-UDC")){
      return true;
    }
    return false;
  }
	/**
	 * 创建者：晁志平 功能：置换查询模板的自定义项 参数： QueryConditionClient client 待设置的查询模板 String
	 * pk_corp 公司主键 String hPrefix 查询模板表头自定义项前缀，非空 String hEndfix 查询模板表头自定义项后缀
	 * int hAddDigit 查询模板表头自定义项修正数[只加] String bPrefix 查询模板表体自定义项前缀，非空 String
	 * bEndfix 查询模板表体自定义项后缀 int bAddDigit 查询模板表体自定义项修正数[只加] String
	 * prefixCumandoc 查询模板中客商管理档案自定义项前缀(无此需求要求传NULL) String prefixCubasdoc
	 * 查询模板中客商档案自定义项前缀(无此需求要求传NULL) String prefixInvmandoc
	 * 查询模板中存货管理档案自定义项前缀(无此需求要求传NULL) String prefixInvbasdoc
	 * 查询模板中存货档案自定义项前缀(无此需求要求传NULL) 返回：void 例外： 日期：(2002-12-09 19:57:29)
	 * 修改日期，修改人，修改原因，注释标志： 2004-09-10 晁志平 优化前后台交互次数
	 */
	public static void updateQueryConditionClientUserDef(
			QueryConditionClient client, String pk_corp,String cbilltypecode,String hPrefix,
			String hEndfix, int hAddNum, String bPrefix, String bEndfix,
			int bAddNum, String prefixCumandoc, String prefixCubasdoc,
			String prefixInvmandoc, String prefixInvbasdoc) {
		updateQueryConditionClientUserDefAll(client, pk_corp, cbilltypecode,hPrefix, hEndfix,
				hAddNum, bPrefix, bEndfix, bAddNum, _STYLE_NORMAL,
				prefixCumandoc, prefixCubasdoc, prefixInvmandoc,
				prefixInvbasdoc);
	}

	/**
	 * 创建者：晁志平 功能：置换查询模板的自定义项 参数： QueryConditionClient client 待设置的查询模板 String
	 * pk_corp 公司主键 String hPrefix 查询模板表头自定义项前缀，非空 String hEndfix 查询模板表头自定义项后缀
	 * int hAddDigit 查询模板表头自定义项修正数[只加] String bPrefix 查询模板表体自定义项前缀，非空 String
	 * bEndfix 查询模板表体自定义项后缀 int bAddDigit 查询模板表体自定义项修正数[只加] int iStyle 调用者
	 * String prefixCumandoc 查询模板中客商管理档案自定义项前缀(无此需求要求传NULL) String
	 * prefixCubasdoc 查询模板中客商档案自定义项前缀(无此需求要求传NULL) String prefixInvmandoc
	 * 查询模板中存货管理档案自定义项前缀(无此需求要求传NULL) String prefixInvbasdoc
	 * 查询模板中存货档案自定义项前缀(无此需求要求传NULL) 返回：void 例外： 日期：(2002-12-09 19:57:29)
	 * 修改日期，修改人，修改原因，注释标志： 晁志平 支持前后台一次交互设置存货、客商、单据表头[非空]、单据表体四类自定义项
	 */
	public static void updateQueryConditionClientUserDefAll(
			QueryConditionClient client, String pk_corp, String cbilltypecode,String hPrefix,
			String hEndfix, int hAddNum, String bPrefix, String bEndfix,
			int bAddNum, int iStyle, String prefixCumandoc,
			String prefixCubasdoc, String prefixInvmandoc,
			String prefixInvbasdoc) {
		try {
			// 定义参照的长度
			final int iMaxLenOfDef = 20;

			if (client == null || hPrefix == null || hPrefix.length() <= 0)
				return;
			// 查询对应公司的单据头和单据体自定义项
			String[] saDocName = new String[] {// "供应链/ARAP单据头", "供应链/ARAP单据体",
					"客商管理档案", "客商档案", "存货管理档案", "存货档案" };
			if (prefixCumandoc == null || prefixCumandoc.trim().length() == 0) {
				saDocName[0] = null;
			}
			if (prefixCubasdoc == null || prefixCubasdoc.trim().length() == 0) {
				saDocName[1] = null;
			}
			if (prefixInvmandoc == null || prefixInvmandoc.trim().length() == 0) {
				saDocName[2] = null;
			}
			if (prefixInvbasdoc == null || prefixInvbasdoc.trim().length() == 0) {
				saDocName[3] = null;
			}
			Object[] objs = getDefVOBatch(false,pk_corp, saDocName);
			if (objs == null || objs.length != 4) {
				nc.vo.scm.pub.SCMEnv.out("批量远程调用出现异常，查询模板自定义项设置异常返回!");
				return;
			}
			DefVO[] hDefs = getDefHead(pk_corp, cbilltypecode);//(DefVO[]) objs[0];
			DefVO[] bDefs = null;
			if (bPrefix != null) {
				bDefs = getDefBody(pk_corp, cbilltypecode);//(DefVO[]) objs[1];
			}
			updateQueryConditionClientUserDef(client, pk_corp, hPrefix,
					hEndfix, hAddNum, bPrefix, bEndfix, bAddNum, iStyle, hDefs,
					bDefs,cbilltypecode);
			// 客商、存货自定义项设置
			if (prefixCumandoc != null && prefixCumandoc.trim().length() > 0) {
				updateQueryConditionByDefVOs(client, pk_corp, prefixCumandoc,
						(DefVO[]) objs[2]);
			}
			if (prefixCubasdoc != null && prefixCubasdoc.trim().length() > 0) {
				updateQueryConditionByDefVOs(client, pk_corp, prefixCubasdoc,
						(DefVO[]) objs[3]);
			}
			if (prefixInvmandoc != null && prefixInvmandoc.trim().length() > 0) {
				updateQueryConditionByDefVOs(client, pk_corp, prefixInvmandoc,
						(DefVO[]) objs[4]);
			}
			if (prefixInvbasdoc != null && prefixInvbasdoc.trim().length() > 0) {
				updateQueryConditionByDefVOs(client, pk_corp, prefixInvbasdoc,
						(DefVO[]) objs[5]);
			}
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("加载查询模板自定义项时出现异常：");
			nc.vo.scm.pub.SCMEnv.out(e);
		}
	}

	/**
	 * 创建者：晁志平 功能： 置换 客商档案 的查询模板自定义项 参数： BillCardPanel panel 待更新的查询模板 String
	 * pk_corp 公司主键 String prefix 模板中自定义项前缀 返回：BillCardPanel 例外： 日期：(2003-12-11
	 * 19:02:29) 修改日期，修改人，修改原因，注释标志：
	 */
	public static void updateQueryConditionForCubasdoc(
			QueryConditionClient client, String pk_corp, String prefix) {
		updateQueryCondition(client, pk_corp, "客商档案", prefix);

	}

	/**
	 * 创建者：晁志平 功能： 置换 客商管理档案 的查询模板自定义项 参数： BillCardPanel panel 待更新的查询模板 String
	 * pk_corp 公司主键 String prefix 模板中自定义项前缀 返回：BillCardPanel 例外： 日期：(2003-12-11
	 * 19:02:29) 修改日期，修改人，修改原因，注释标志：
	 */
	public static void updateQueryConditionForCumandoc(
			QueryConditionClient client, String pk_corp, String prefix) {
		updateQueryCondition(client, pk_corp, "客商管理档案", prefix);
	}

	/**
	 * 创建者：晁志平 功能： 置换 存货档案 的查询模板自定义项 参数： BillCardPanel panel 待更新的查询模板 String
	 * pk_corp 公司主键 String prefix 模板中自定义项前缀 返回：BillCardPanel 例外： 日期：(2003-12-11
	 * 19:02:29) 修改日期，修改人，修改原因，注释标志：
	 */
	public static void updateQueryConditionForInvbasdoc(
			QueryConditionClient client, String pk_corp, String prefix) {
		updateQueryCondition(client, pk_corp, "存货档案", prefix);
	}

	/**
	 * 创建者：晁志平 功能： 置换 存货管理档案 的查询模板自定义项 参数： BillCardPanel panel 待更新的查询模板 String
	 * pk_corp 公司主键 String prefix 模板中自定义项前缀 返回：BillCardPanel 例外： 日期：(2003-12-11
	 * 19:02:29) 修改日期，修改人，修改原因，注释标志：
	 */
	public static void updateQueryConditionForInvmandoc(
			QueryConditionClient client, String pk_corp, String prefix) {
		updateQueryCondition(client, pk_corp, "存货管理档案", prefix);
	}

	/**
	 * 创建：晁志平 功能：翻译“供应链/ARAP单据头”、“供应链/ARAP单据体” 参数：String strSrc
	 * {“供应链/ARAP单据头”、“供应链/ARAP单据体”}中的一个值 返回：String strDst 翻译后的结果 例外：
	 * 日期：(2005-05-21 13:23:29) 修改日期，修改人，修改原因，注释标志：
	 */
	private static String transRes(String strSrc) {
		return transRes(strSrc,null,false);
	}
	private static String transRes(String strSrc,String cbilltypecode,boolean isHead) {
		if(cbilltypecode == null){
			String strDst = strSrc;
			if (strSrc != null
					&& (strSrc.equals("供应链/ARAP单据头") || strSrc
							.equals("供应链/ARAP单据体"))) {
				String strResId = "D14";
				if (strSrc.indexOf("体") >= 0) {
					strResId = "D15";
				}
				strDst = nc.ui.ml.NCLangRes.getInstance().getStrByID("bd_defused",
						strResId);
			}
			return strDst;
		}else{
			String strDst = strSrc;
			if(isHead){
				strDst = nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCommon",
						"D"+ScmConst.getHeadObjCode(cbilltypecode));
			}else{
				strDst = nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCommon",
						"D"+ScmConst.getBodyObjCode(cbilltypecode));
			}
			return strDst;
		}
	}

	/**
	 * 创建者余大英 ： 功能：置换自定义项 参数： String[] fieldname String pk_corp 公司主键 DefVO[]
	 * Defs 单据表头自定义项VO[]
	 * 
	 * 返回：void 例外： 日期：(2004-09-10 17:32:29) 修改日期，修改人，修改原因，注释标志：
	 */
	public static String[] updateFieldUserDef(String[] sFieldNames,
			String pk_corp,String cbilltypecode, DefVO[] Defs, boolean isHead) {

		if (sFieldNames == null || sFieldNames.length <= 0)
			return sFieldNames;

		if (Defs == null && pk_corp != null) {

			if (isHead)
				Defs = getDefHead(pk_corp,cbilltypecode);
			else
				Defs = getDefBody(pk_corp,cbilltypecode);

		}

		if (Defs == null || Defs.length < sFieldNames.length)
			return sFieldNames;

		String[] sNewFieldNames = new String[sFieldNames.length];

		for (int i = 0; i < sFieldNames.length; i++) {
			sNewFieldNames[i] = sFieldNames[i];
			if (Defs[i] != null) {
				sNewFieldNames[i] = Defs[i].getDefname();

			}
		}

		return sNewFieldNames;
	}
	
	public static void updateBillCardPanelUserDefForHead(BillCardPanel panel,
			String pk_corp,String cbilltypecode, String hPrefix, String bPrefix) {
		if (panel == null || panel.getBillData() == null)
			return;
		updateBillCardPanelUserDefForHead(panel, pk_corp,cbilltypecode, hPrefix, null, 0, bPrefix,
				null, 0,1);
	}
	/**以下代码待55后使用@*/
	/**按照宋杰说明：我们本身表头统计型自定义项加载存在问题，需要将其加载到主键字段，
	 * 例如：pk_defdoc1。如果这样做，除了需要修改DefSetTool类的自定义项加载的各方法
	 * 外，还要处理如下问题：1、安装盘中每个单据模版表头的自定义项主键字段设置为用户
	 * 可配置；2、升级默认模板以及用户自定义模板中，统计型自定义项对应的主键字段显示，
	 * 名称字段隐藏；3、发版说明：如果表头引用统计型自定义项，顾问、实施需要将其对应
	 * 的主键字段显示出来 
	 * */
	public static void updateBillCardPanelUserDefForHead(BillCardPanel panel,
			String pk_corp,String cbilltypecode, String hPrefix, String hEndfix, int hAddNum,
			String bPrefix, String bEndfix, int bAddNum,int iCount) {

		// new Exception("==>>卡片 自定义项初始化，堆栈信息:").printStackTrace();

		try {
			if (panel == null) {
				nc.vo.scm.pub.SCMEnv.out("--> BillCardPanel is null.");
				return;
			}
			BillData billData = panel.getBillData();
			boolean bIsAddObjName = isAddObjName(panel);
			if (bIsAddObjName) {
				nc.vo.scm.pub.SCMEnv.out("待设自定义项控件是报表模板，要拼接自定义项对象名称");
			}
			if (billData == null) {
				nc.vo.scm.pub.SCMEnv.out("--> BillData is null.");
				return;
			}
			DefVO[] defs = null;
			defs = getDefHead(pk_corp,cbilltypecode);// (DefVO[]) objs[0];
			String strHKey = hPrefix + (hEndfix == null ? "" : hEndfix);
			String strBKey = bPrefix + (bEndfix == null ? "" : bEndfix);
			if ((defs != null)) {
				updateItemByDefForHead(billData, defs, hPrefix, hEndfix, hAddNum,
						true, bIsAddObjName,iCount,"pk_defdoc");
				if (!strHKey.equals(strBKey))
					updateItemByDefForHead(billData, defs, hPrefix, hEndfix, hAddNum,
							false, bIsAddObjName,iCount,null);
			}
			// 表体,查得对应于公司的该单据的自定义项设置
			// defs = nc.ui.bd.service.BDDef.queryDefVO("供应链/ARAP单据体", pk_corp);
			defs = getDefBody(pk_corp,cbilltypecode);// (DefVO[]) objs[1];
			if ((defs != null)) {
				updateItemByDefForHead(billData, defs, bPrefix, bEndfix, bAddNum,
						false, bIsAddObjName,iCount,null);
			}


		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("加载单据模板(卡片)自定义项时出现异常：");
			nc.vo.scm.pub.SCMEnv.out(e);
		}
	}
	
	public static void updateItemByDefForHead(BillData bd,
			nc.vo.bd.def.DefVO[] defVOs, String fieldPrefix,
			String fieldEndfix, int iAddNum, boolean isHead,
			boolean isAddNumObjName,int iConut,String fieldID) {
		String fieldPrefixName = fieldPrefix;
		if (defVOs == null)
			return;
		for (int i = 0; i < defVOs.length; i++) {
			nc.vo.bd.def.DefVO defVO = defVOs[i];
			if (defVOs[i] == null) {
				continue;
			}
			String itemkey = defVO.getFieldName();
			if(fieldEndfix!=null)
				itemkey=itemkey+fieldEndfix;
			
			
			BillItem item = null;
			
			// 位置
			if (isHead) {
				if(defVO.getType().equals("统计") && defVO.getDefdef().getPk_bdinfo() != null){
					int index =i+1; 
					item = bd.getHeadItem(fieldID + index);
					fieldPrefixName = fieldID + index;
				}else{
					item = bd.getHeadItem(itemkey);
				}
				if (item == null)
					item = bd.getTailItem(itemkey);
				if (item == null)
					item = bd.getBodyItem(itemkey);
				updateItemForHead(item,defVO,fieldPrefixName,isAddNumObjName,true);
				fieldPrefixName = fieldPrefix;
			} else{
				if(iConut==1){
					item = bd.getBodyItem(itemkey);
					updateItem(item,defVO,fieldPrefix,isAddNumObjName,false);
				}else{
					for(int j = 0 ; j < iConut ; j ++){
						item = bd.getBodyItem("table"+String.valueOf(i),itemkey);
						updateItem(item,defVO,fieldPrefix,isAddNumObjName,false);
					}
				}
			}
			
			
		}
	}
	
	private static void updateItemForHead(BillItem item,nc.vo.bd.def.DefVO defVO,String fieldPrefix,boolean isAddNumObjName,boolean isHead){
		if (item != null) {
			
			//模板中定义的类型
			int iDataType = item.getDataType();
			
			if (defVO != null) {

				String itemKey = item.getKey();

				// 用户没有调整过，才设置为集团自定义项名称
				String fieldName = item.getName();
				if (fieldName == null || fieldName.trim().equals("")
						|| fieldName.indexOf(KEY_NOT_MODIFIED_1) >= 0
						|| fieldName.indexOf(KEY_NOT_MODIFIED_2) >= 0) {
					if (itemKey.indexOf(fieldPrefix) >= 0) {
							item.setName(defVO.getDefname());
						}
//					}
				}
				// 输入长度
				int inputlength = defVO.getLengthnum().intValue();
				item.setLength(inputlength);
				// 数据类型
				String iTypeDef = defVO.getType();
				int datatype = BillItem.STRING;
				if (iTypeDef.equals("备注"))
					datatype = BillItem.STRING;
				else if (iTypeDef.equals("日期"))
					datatype = BillItem.DATE;
				else if (iTypeDef.equals("数字")) {
					datatype = BillItem.INTEGER;
					if ((defVO.getDigitnum() != null)
							&& (defVO.getDigitnum().intValue() > 0)) {
						datatype = BillItem.DECIMAL;
						item.setDecimalDigits(defVO.getDigitnum().intValue());
					}
				}
				
				/**
				 * 元数据模型的自定义项要将MetaDataProperty属性置空
				 * v5.5 暂时的解决办法。v6.0UAP统一解决
				 */
				if (item.getMetaDataProperty()!=null){
					item.setMetaDataProperty(null);
				}
				
				//有档案的自定义项才认为是自定义项参照类型，否则按用户定义的类型设置
				String strDefDocReftype = defVO.getDefdef().getPk_bdinfo();
				if (iTypeDef.equals("统计") && strDefDocReftype != null){
					datatype = BillItem.USERDEF;
					item.setRefType(strDefDocReftype);
				}
				item.setDataType(datatype);
				item.reCreateComponent();
				item.setIsDef(true);
				
				if (item.getDataType() == BillItem.UFREF || item.getDataType() == BillItem.USERDEF) {
					item.setDataType(BillItem.UFREF);
					UIRefPane ref = (UIRefPane) item.getComponent();
					//统计型自定义项需要自动校验其数据的有效性，各产品组如果不需要自动校验，请自行写代码解决
					ref.setAutoCheck(true);
					//设置返回名称
					ref.setReturnCode(false);
				}
			} else {
				// item.setShow(false);
			}
		}
	}
	
//	/**
//	 * 孔晓东57
//	 * 解决单据由其他产品参照过来无法正常显示name的问题
//	 * @param card
//	 * @param sVdefPkKey
//	 * @param sVdefValueKey
//	 */
//	public static void resetDefCardPaneByDefPK(BillCardPanel card,String sVdefPkKey, String sVdefValueKey ){
//		if(card.getRowCount()<=0)return;
//		for(int row=0;row<card.getRowCount();row++){
//			for(int index=1;index<=20;index++){
//				String defNameColumn=sVdefValueKey+index;
//				String defpkColumn=sVdefPkKey+index;
//				BillItem nameItem=card.getBodyItem(defNameColumn);
//				if(nameItem!=null&&nameItem.isShow()){
//					String pkValue=(String)card.getBodyValueAt(row, defpkColumn);
//					if(pkValue!=null){
//						card.setBodyValueAt(pkValue, row, defNameColumn+IBillItem.ID_SUFFIX);
//					}
//				}
//				
//			}
//		}
//		
//	}
//	
//	public static void resetDefModelByDefPK(BillModel model,String sVdefPkKey, String sVdefValueKey ){
//		if(model.getRowCount()<=0)return;
//		for(int row=0;row<model.getRowCount();row++){
//			for(int index=1;index<=20;index++){
//				String defNameColumn=sVdefValueKey+index;
//				String defpkColumn=sVdefPkKey+index;
//				BillItem nameItem=model.getItemByKey(defNameColumn);
//				if(nameItem!=null&&nameItem.isShow()){
//					String pkValue=(String)model.getValueAt(row, defpkColumn);
//					if(pkValue!=null){
//						model.setValueAt(pkValue, row, defNameColumn+IBillItem.ID_SUFFIX);
//					}
//				}
//				
//			}
//		}
//		
//	}
//	
//	public static void resetDefListPanelByDefPK(BillListPanel listpanel,String sVdefPkKey, String sVdefValueKey ){
//		resetDefModelByDefPK(listpanel.getHeadBillModel(),sVdefPkKey,sVdefValueKey);
//		resetDefModelByDefPK(listpanel.getBodyBillModel(),sVdefPkKey,sVdefValueKey);
//		
//	}
	
  
  /**
   * 方法功能描述：ALT+SHIT+鼠标拖拽触发批改动作.放到自定义项afterEdit事件之前
   * 如果为参照，用第一行的pk为后续行赋值
   * <b>参数说明</b>
   * @param e
   * @author sunwei
   * @param sVdefValueKey 
   * @param sVdefPkKey 
   * @param card 
   * @time 2009-11-4 下午04:31:03
   */
  public static void batchDefEdit(BillCardPanel card, BillEditEvent e, String sVdefPkKey, String sVdefValueKey) {
    int[] rows = card.getBillTable().getSelectedRows();
    //不是ALT+SHIT+鼠标拖拽触发批改动作，或者是第一行，则不用处理（默认都是拿第一行
    //来修改其他行的值）
    if (rows.length == 0 || e.getRow() == rows[0]) {
      return;
    }
    String key = e.getKey();
    BillItem item = card.getBodyItem(key);
    //不是档案参照，因此不会存在pk等编辑器必须有的值
    if (item.getDataType() != BillItem.UFREF
        && item.getDataType() != BillItem.USERDEF) {
      return;
    }
    UIRefPane refpane = (UIRefPane) item.getComponent();
    if( refpane == null ){
      return;
    }
    String pkField = sVdefPkKey + key.substring(sVdefValueKey.length());
    
    Object oTemp = card.getBillModel().getValueAt(rows[0], pkField);
    String pk = oTemp == null ? null : oTemp.toString().trim();
    
    refpane.setPK(pk);
  }
}
