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
 * ��־ƽ ���ܣ��Զ��������ù��� ���ڣ�(2003-12-11 11:33:04) ydy20050606
 * �޸ģ����Զ������ݾ�̬����洢����Ӧ��ֻ��Ҫ��ʼ��һ��
 */

public class DefSetTool {
	// ǰ׺
	private static final String strPre = "[";

	// ��׺
	private static final String strEnd = "]";
	
	private static final String OBJCODE_BATCHCODE=ScmConst.DEF_USED_PREFIX+"vbatchcode";

	/* ��ѯģ�壺ȷ����ѯģ���Ǳ�ͷ���Ǳ����Զ����� */
	// ���⴦��
	public static final int _STYLE_IC = 0;

	// Ĭ�ϴ���
	public static final int _STYLE_NORMAL = -999;

	/* ����ģ�壺��ͷ�����Զ������ڵ���ģ���зֲ� */
	// �ֱ��ڱ�ͷ������
	public static final int _POS_NORMAL = 0;

	// ȫ�ڱ�ͷ
	public static final int _POS_HEAD = 1;

	// ȫ�ڱ���
	public static final int _POS_BODY = 2;

	// ��Ӧ����ͷ�������Զ�����VO(�Ż�ǰ��̨��������)
	private static Object[] m_objDefVos = null;

	private static HashMap m_hmDefHead = new HashMap();

	private static HashMap m_hmDefBody = new HashMap();
	
	private static HashMap m_hmDefName=new HashMap();

	// �û�δ�޸Ĺ��Զ��������Ʊ�ǹؼ���
	private static final String KEY_NOT_MODIFIED_1 = "UDC";

	private static final String KEY_NOT_MODIFIED_2 = "�Զ�����";

	private static Pattern defKeyPattern;

	/**
	 * PuTool ������ע�⡣
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
				Object[] objs = getDefVOBatch(true,pk_corp,new String[]{OBJCODE_BATCHCODE});//"���κŵ���"
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
	 * ���ÿ�Ƭģ��������Զ������pkֵ�����ڲ�ѯ�������뵥��ģ���
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
	 * ���ܣ� afterEdit�������Զ��������� �˷���ִ����Ϻ󣬸����Զ��������ӦPKֵ��������� ��������ͷ��������Զ��������ʹ�ô˷���
	 * 
	 * @author����ӡ��
	 * @param BillModel
	 *            billModel
	 * @param int
	 *            iRow ��ǰ�༭��BillModel����
	 * @param String
	 *            sVdefValueKey �Զ�����ֵ��KEY��һ���ڴ�KEY�Ͻ�������
	 * @param String
	 *            sVdefPkKey �Զ�����PK��KEY��
	 * @return����
	 * @serialData(2003-12-11 19:02:29)
	 * 
	 */
	public static void afterEditBody(BillModel billModel, int iRow,
			String sVdefValueKey, String sVdefPkKey) {

		// ������ȷ���ж�
		if (billModel == null || sVdefPkKey == null || sVdefValueKey == null
				|| billModel.getItemByKey(sVdefPkKey) == null
				|| billModel.getItemByKey(sVdefValueKey) == null) {
			return;
		}

		BillItem item = billModel.getItemByKey(sVdefValueKey);
		// ���������ж�
		if (item.getDataType() == BillItem.USERDEF || item.getDataType() == BillItem.UFREF) {
			UIRefPane refpane = (UIRefPane) item.getComponent();
	    //String sPk_defdoc = DataTypeTool.getString_Trim0LenAsNull(refpane.getRefPK());
	    //����yb���飬֧�����ģ������ĵ�������ǿ��ƥ�����PK by zhaoyha at 2009.12.2
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
	 * ���ܣ� afterEdit�����ñ�ͷ�Զ�������������ͷ��ListPanelʱʹ�ô˷��� �˷���ִ����Ϻ󣬸����Զ��������ӦPKֵ���������
	 * ��������ͷ��������Զ��������ʹ�ô˷���
	 * 
	 * @author����ӡ��
	 * @param BillModel
	 *            billModel
	 * @param int
	 *            iRow ��ǰ�༭��BillModel����
	 * @param String
	 *            sVdefValueKey �Զ�����ֵ��KEY��һ���ڴ�KEY�Ͻ�������
	 * @param String
	 *            sVdefPkKey �Զ�����PK��KEY��
	 * @return����
	 * @serialData(2003-12-11 19:02:29)
	 * 
	 */
	public static void afterEditHead(BillModel billModel, int iRow,
			String sVdefValueKey, String sVdefPkKey) {

		// ֻҪ��BillModel����ʹ����ͬ��
		afterEditBody(billModel, iRow, sVdefValueKey, sVdefPkKey);

	}

	/**
	 * ���ܣ� afterEdit�����ñ�ͷ�Զ�����������ʹ��CardPanelʱʹ�ô˷��� �˷���ִ����Ϻ󣬸����Զ��������ӦPKֵ���������
	 * ��������ͷ��������Զ��������ʹ�ô˷���
	 * 
	 * @author����ӡ��
	 * @param BillData
	 *            bdata
	 * @param String
	 *            sVdefValueKey �Զ�����ֵ��KEY��һ���ڴ�KEY�Ͻ�������
	 * @param String
	 *            sVdefPkKey �Զ�����PK��KEY��
	 * @return void
	 * @serialData��(2003-12-11 19:02:29)
	 * 
	 */
	public static void afterEditHead(BillData bdata, String sVdefValueKey,
			String sVdefPkKey) {

		// ������ȷ���ж�
		if (bdata == null || sVdefPkKey == null || sVdefValueKey == null
				|| bdata.getHeadItem(sVdefPkKey) == null
				|| bdata.getHeadItem(sVdefValueKey) == null) {
			return;
		}

		BillItem item = bdata.getHeadItem(sVdefValueKey);
		// ���������ж�
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
	 * ���ܣ�����ִ���Զ�����VO��ȡ���� �����ߣ���־ƽ �������ڣ�(2004-9-7 20:03:40)
	 */
	private static Object[] getDefVOBatch(String pk_corp,String cbilltypecode) {

		// ��Ʒ�鴫����
		if (m_objDefVos != null && m_objDefVos.length == 2) {
			return m_objDefVos;
		}
	
		// ��Ʒ��δ����
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
			nc.vo.scm.pub.SCMEnv.out("����ִ���Զ�����VO��ȡ�����쳣!��ϸ��Ϣ���£�");
			nc.vo.scm.pub.SCMEnv.out(e);
		}
		return objs;
	}
	
	/**
	 * ���ܣ�����ִ���Զ�����VO��ȡ����--��ѯģ�� �����ߣ���־ƽ �������ڣ�(2004-9-10 17:39:57)
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
			nc.vo.scm.pub.SCMEnv.out("����ִ���Զ�����VO��ȡ�����쳣!��ϸ��Ϣ���£�");
			nc.vo.scm.pub.SCMEnv.out(e);
		}
		// ��֯����
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
	 * �����ߣ���־ƽ �Ӹ������н������� �������ڣ�(2001-11-9 16:18:40)
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
			nc.vo.scm.pub.SCMEnv.out("�Ӹ������н���������" + "Դ����" + strSrc + "ǰ׺��" + strBgn
					+ "����׺��" + strEnd + "��");
			nc.vo.scm.pub.SCMEnv.out(e);
		}
		// nc.vo.scm.pub.SCMEnv.out("�Ӹ������н���������" + "Դ����" + strSrc + "������" + iIndex);
		return iIndex;
	}

	/**
	 * ���ܣ�����ִ�в�ѯ�Զ�����VO���� ���أ���ͷ�������Զ�����VO���� �����ߣ���־ƽ �������ڣ�(2004-9-10 14:09:40)
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
//		scds[0].setParameter(new Object[] {objheadname, pk_corp });// "��Ӧ��/ARAP����ͷ"
//		scds[0].setParameterTypes(new Class[] { String.class, String.class });
//
//		scds[1] = new ServcallVO();
//		scds[1].setBeanName(IDef.class.getName());
//		scds[1].setMethodName("queryDefVO");
//		scds[1].setParameter(new Object[] {objbodyname , pk_corp });//"��Ӧ��/ARAP������"
//		scds[1].setParameterTypes(new Class[] { String.class, String.class });
//
//		return scds;
//	}
	/**
	 * ���ܣ�����ִ�в�ѯ�Զ�����VO���� ���أ���ͷ�������Զ�����VO���� �����ߣ���־ƽ �������ڣ�(2004-9-10 14:09:40)
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
	 * ��������־ƽ ���ܣ��Ƿ�ƴ���Զ������������ ������ Object obj �������Զ�����ؼ� �������ڣ�(2003-12-11
	 * 11:18:40)
	 */
	private static boolean isAddObjName(Object obj) {
		if (obj == null)
			return false;
		return obj instanceof nc.ui.pub.report.ReportBaseClass;
	}

	/**
	 * �����ߣ���־ƽ ���ܣ������Զ�������ж����Ƿ�Ϊ���Զ����� ������ String fieldCode ��ѯģ���Զ�����Code�� String
	 * bPrefix ��ѯģ���Զ�����ǰ׺���ǿ� String bEndfix ��ѯģ���Զ������׺ int iStyle ������ ���أ�boolean
	 * ���⣺ ���ڣ�(2002-12-09 19:57:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	private static boolean isBody(String fieldCode, String bPrefix,
			String bEndfix, int iStyle) {

		// �Ƿ�����IC����Ӱ��
		if (bPrefix == null)
			return false;
		return fieldCode.startsWith(bPrefix);
	}

	/**
	 * �����ߣ���־ƽ ���ܣ������Զ�������ж����Ƿ�Ϊͷ�Զ����� ������ String fieldCode ��ѯģ���Զ�����Code�� String
	 * hPrefix ��ѯģ���Զ�����ǰ׺���ǿ� String hEndfix ��ѯģ���Զ������׺ int iStyle ������ ���أ�boolean
	 * ���⣺ ���ڣ�(2002-12-09 19:57:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	private static boolean isHead(String fieldCode, String hPrefix,
			String hEndfix, int iStyle) {

		// �Ƿ�����IC
		boolean bIsFromIc = iStyle == _STYLE_IC;

		if (bIsFromIc) {
			return fieldCode.startsWith(hPrefix)
					&& fieldCode.endsWith(hEndfix == null ? "" : hEndfix);
		}
		return fieldCode.startsWith(hPrefix);
	}

	/**
	 * ���ܣ������Զ�����VO �����ߣ���־ƽ �������ڣ�(2004-9-10 13:52:40)
	 */
	public static void setTwoOBJs(Object[] objDefVos) {
		m_objDefVos = objDefVos;
	}

	/**
	 * �����ߣ���־ƽ ���ܣ��û� BillCardPanel ���Զ����� ������ BillCardPanel panel �����µ�
	 * BillCardPanel String pk_corp ��˾���� String hPrefix ����ģ���е���ͷ���Զ�����ǰ׺ String
	 * bPrefix ����ģ���е�������Զ�����ǰ׺ ���أ��� ���⣺ ���ڣ�(2003-12-09 21:07:29)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public static void updateBillCardPanelUserDef(BillCardPanel panel,
			String pk_corp,String cbilltypecode, String hPrefix, String bPrefix) {
		if (panel == null || panel.getBillData() == null)
			return;
		updateBillCardPanelUserDef(panel, pk_corp,cbilltypecode, hPrefix, null, 0, bPrefix,
				null, 0,1);
	}
	/**
	 * �����ߣ���־ƽ ���ܣ��û� BillCardPanel ���Զ����� ������ BillCardPanel panel �����µ�
	 * BillCardPanel String pk_corp ��˾���� String hPrefix ����ģ���е���ͷ���Զ�����ǰ׺ String
	 * bPrefix ����ģ���е�������Զ�����ǰ׺ ���أ��� ���⣺ ���ڣ�(2003-12-09 21:07:29)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * �����ߣ���־ƽ ���ܣ��û�BillData���Զ����� ������ BillData billData �����µ�BillCardPanel String
	 * pk_corp ��˾���� String hPrefix ����ģ���е���ͷ���Զ�����ǰ׺ String hEndfix
	 * ����ģ���е���ͷ���Զ������׺ int hAddNum ��ͷ������[ֻ��] String bPrefix ����ģ���е�������Զ�����ǰ׺
	 * String bEndfix ����ģ���е�������Զ������׺ int bAddNum ����������[ֻ��] ���أ��� ���⣺
	 * ���ڣ�(2003-11-11 09:47:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public static void updateBillCardPanelUserDef(BillCardPanel panel,
			String pk_corp,String cbilltypecode, String hPrefix, String hEndfix, int hAddNum,
			String bPrefix, String bEndfix, int bAddNum,int iCount) {

		// new Exception("==>>��Ƭ �Զ������ʼ������ջ��Ϣ:").printStackTrace();

		try {
			if (panel == null) {
				nc.vo.scm.pub.SCMEnv.out("--> BillCardPanel is null.");
				return;
			}
			BillData billData = panel.getBillData();
			boolean bIsAddObjName = isAddObjName(panel);
			if (bIsAddObjName) {
				nc.vo.scm.pub.SCMEnv.out("�����Զ�����ؼ��Ǳ���ģ�壬Ҫƴ���Զ������������");
			}
			if (billData == null) {
				nc.vo.scm.pub.SCMEnv.out("--> BillData is null.");
				return;
			}
			DefVO[] defs = null;
			// Object[] objs = getDefVOBatch(pk_corp);
			// if (objs == null || objs.length != 2) {
			// nc.vo.scm.pub.SCMEnv.out("����Զ�̵��ó����쳣�����ݿ�Ƭ�Զ����������쳣����!");
			// return;
			// }
			// ��ͷ,��ö�Ӧ�ڹ�˾�ĸõ��ݵ��Զ���������
			// defs = nc.ui.bd.service.BDDef.queryDefVO("��Ӧ��/ARAP����ͷ", pk_corp);
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
			// ����,��ö�Ӧ�ڹ�˾�ĸõ��ݵ��Զ���������
			// defs = nc.ui.bd.service.BDDef.queryDefVO("��Ӧ��/ARAP������", pk_corp);
			defs = getDefBody(pk_corp,cbilltypecode);// (DefVO[]) objs[1];
			if ((defs != null)) {
				updateItemByDef(billData, defs, bPrefix, bEndfix, bAddNum,
						false, bIsAddObjName,iCount);
			}
			//���������Ҫ�������ͷLabel�Ȳ����ػ����Զ��������Ʋ�����ȷ��ʾ��
			panel.setBillData(billData); 

		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("���ص���ģ��(��Ƭ)�Զ�����ʱ�����쳣��");
			nc.vo.scm.pub.SCMEnv.out(e);
		}
	}
	/**
	 * 
	 * �����������������ݿ�Ƭģ������Զ�����
	 * <p>
	 * �����ͷ�����������Զ���������⣬Ŀǰ5.3ѯ���۵����۸�������ר�ã����б�����һ���Զ�������������ͷ������ͬ��
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ʹ��ʾ��
	 * <p>
	 * <b>����˵��</b>
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
	 * @time 2008-5-6 ����07:54:10
	 */
	public static void updateBillCardPanelUserDefFor28And29(BillCardPanel panel,
      String pk_corp,String cbilltypecode, String hPrefix, String hEndfix, int hAddNum,
      String bPrefix, String bEndfix, int bAddNum) {

    // new Exception("==>>��Ƭ �Զ������ʼ������ջ��Ϣ:").printStackTrace();

    try {
      if (panel == null) {
        nc.vo.scm.pub.SCMEnv.out("--> BillCardPanel is null.");
        return;
      }
      BillData billData = panel.getBillData();
      boolean bIsAddObjName = isAddObjName(panel);
      if (bIsAddObjName) {
        nc.vo.scm.pub.SCMEnv.out("�����Զ�����ؼ��Ǳ���ģ�壬Ҫƴ���Զ������������");
      }
      if (billData == null) {
        nc.vo.scm.pub.SCMEnv.out("--> BillData is null.");
        return;
      }
      DefVO[] defs = null;
      // Object[] objs = getDefVOBatch(pk_corp);
      // if (objs == null || objs.length != 2) {
      // nc.vo.scm.pub.SCMEnv.out("����Զ�̵��ó����쳣�����ݿ�Ƭ�Զ����������쳣����!");
      // return;
      // }
      // ��ͷ,��ö�Ӧ�ڹ�˾�ĸõ��ݵ��Զ���������
      // defs = nc.ui.bd.service.BDDef.queryDefVO("��Ӧ��/ARAP����ͷ", pk_corp);
      defs = getDefHead(pk_corp,cbilltypecode);// (DefVO[]) objs[0];
      String strHKey = hPrefix + (hEndfix == null ? "" : hEndfix);
      String strBKey = bPrefix + (bEndfix == null ? "" : bEndfix);
      if ((defs != null)) {
        updateItemByDef(billData, defs, hPrefix, hEndfix, hAddNum,
            true, bIsAddObjName,1);
      }
      // ����,��ö�Ӧ�ڹ�˾�ĸõ��ݵ��Զ���������
      // defs = nc.ui.bd.service.BDDef.queryDefVO("��Ӧ��/ARAP������", pk_corp);
      defs = getDefBody(pk_corp,cbilltypecode);// (DefVO[]) objs[1];
      if ((defs != null)) {
        updateItemByDef(billData, defs, bPrefix, bEndfix, bAddNum,
            false, bIsAddObjName,1);
      }

      panel.setBillData(billData);

    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.out("���ص���ģ��(��Ƭ)�Զ�����ʱ�����쳣��");
      nc.vo.scm.pub.SCMEnv.out(e);
    }
  }

	/**
	 * �����ߣ����� ���ܣ��û��б������Զ����� ������panel BillListPanel,pk_corp String,hPrefix
	 * String,bPrefix String ���أ�BillListPanel ���⣺ ���ڣ�(2002-09-18 09:47:29)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public static void updateBillListPanelUserDef(BillListPanel panel, // �����µ�BillListPanel
			String pk_corp,String cbilltypecode, // ��˾����
			String hPrefix, // ����ģ���е���ͷ���Զ�����ǰ׺
			String bPrefix // ����ģ���е�������Զ�����ǰ׺
	) {
		if (panel == null)
			return;
		updateBillListPanelUserDef(panel, pk_corp, cbilltypecode,hPrefix, null, 0, bPrefix,
				null, 0);
	}

	/**
	 * �����ߣ���־ƽ ���ܣ��û������б������Զ����� ������ BillListPanel panel �����õĵ���ģ��(�б�) String
	 * pk_corp ��˾���� String hPrefix ����ģ��(�б�)��ͷ�Զ�����ǰ׺ String hEndfix
	 * ����ģ��(�б�)��ͷ�Զ������׺ int hAddNum ����ģ��(�б�)��ͷ�Զ�����������[ֻ��] String bPrefix
	 * ����ģ��(�б�)�����Զ�����ǰ׺ String bEndfix ����ģ��(�б�)�����Զ������׺ int bAddNum
	 * ����ģ��(�б�)�����Զ�����������[ֻ��] ���أ�void ���⣺ ���ڣ�(2002-09-18 09:47:29)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public static void updateBillListPanelUserDef(BillListPanel panel,
			String pk_corp,String cbilltypecode, String hPrefix, String hEndfix, int hAddNum,
			String bPrefix, String bEndfix, int bAddNum) {
		try {
			// �����Զ��������
			DefVO[] defs = null;
			BillListData bd = panel.getBillListData();
			if (bd == null) {
				nc.vo.scm.pub.SCMEnv.out("--> billdata null.");
				return;
			}
			// Object[] objs = getDefVOBatch(pk_corp);
			// if (objs == null || objs.length != 2) {
			// nc.vo.scm.pub.SCMEnv.out("����Զ�̵��ó����쳣�������б��Զ����������쳣����!");
			// return;
			// }
			// ��ͷ,��ö�Ӧ�ڹ�˾�ĸõ��ݵ��Զ���������
			// defs = nc.ui.bd.service.BDDef.queryDefVO("��Ӧ��/ARAP����ͷ", pk_corp);
			defs = getDefHead(pk_corp,cbilltypecode); // (DefVO[]) objs[0];

			String strHKey = hPrefix + (hEndfix == null ? "" : hEndfix);
			String strBKey = bPrefix + (bEndfix == null ? "" : bEndfix);
			if ((defs != null)) {
				updateItemByDef(bd, defs, hPrefix, hEndfix, hAddNum, true);
				if (!strHKey.equals(strBKey))
					updateItemByDef(bd, defs, hPrefix, hEndfix, hAddNum, false);
			}

			// ����,��ö�Ӧ�ڹ�˾�ĸõ��ݵ��Զ���������
			// defs = nc.ui.bd.service.BDDef.queryDefVO("��Ӧ��/ARAP������", pk_corp);
			defs = getDefBody(pk_corp,cbilltypecode);// (DefVO[]) objs[1];
			if ((defs != null)) {
				updateItemByDef(bd, defs, bPrefix, bEndfix, bAddNum, false);
			}
			//���������Ҫ�������ͷLabel�Ȳ����ػ����Զ��������Ʋ�����ȷ��ʾ��
			panel.setListData(bd);
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("���ص���ģ��(�б�)�Զ�����ʱ�����쳣��");
			nc.vo.scm.pub.SCMEnv.out(e);
		}

	}
	/**
	 * 
	 * �����������������ݿ�Ƭģ������Զ�����
   * <p>
   * �����ͷ�����������Զ���������⣬Ŀǰ5.3ѯ���۵����۸�������ר�ã����б�����һ���Զ�������������ͷ������ͬ.
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ʹ��ʾ��
	 * <p>
	 * <b>����˵��</b>
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
	 * @time 2008-5-6 ����08:02:26
	 */
	public static void updateBillListPanelUserDefFor28And29(BillListPanel panel,
      String pk_corp,String cbilltypecode, String hPrefix, String hEndfix, int hAddNum,
      String bPrefix, String bEndfix, int bAddNum) {
    try {
      // �����Զ��������
      DefVO[] defs = null;
      BillListData bd = panel.getBillListData();
      if (bd == null) {
        nc.vo.scm.pub.SCMEnv.out("--> billdata null.");
        return;
      }
      // Object[] objs = getDefVOBatch(pk_corp);
      // if (objs == null || objs.length != 2) {
      // nc.vo.scm.pub.SCMEnv.out("����Զ�̵��ó����쳣�������б��Զ����������쳣����!");
      // return;
      // }
      // ��ͷ,��ö�Ӧ�ڹ�˾�ĸõ��ݵ��Զ���������
      // defs = nc.ui.bd.service.BDDef.queryDefVO("��Ӧ��/ARAP����ͷ", pk_corp);
      defs = getDefHead(pk_corp,cbilltypecode); // (DefVO[]) objs[0];

      String strHKey = hPrefix + (hEndfix == null ? "" : hEndfix);
      String strBKey = bPrefix + (bEndfix == null ? "" : bEndfix);
      if ((defs != null)) {
        updateItemByDef(bd, defs, hPrefix, hEndfix, hAddNum, true);
      }

      // ����,��ö�Ӧ�ڹ�˾�ĸõ��ݵ��Զ���������
      // defs = nc.ui.bd.service.BDDef.queryDefVO("��Ӧ��/ARAP������", pk_corp);
      defs = getDefBody(pk_corp,cbilltypecode);// (DefVO[]) objs[1];
      if ((defs != null)) {
        updateItemByDef(bd, defs, bPrefix, bEndfix, bAddNum, false);
      }
      panel.setListData(bd);

    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.out("���ص���ģ��(�б�)�Զ�����ʱ�����쳣��");
      nc.vo.scm.pub.SCMEnv.out(e);
    }

  }

	/**
	 * �����ߣ���־ƽ ���ܣ��û������б������Զ����� ������ BillListPanel panel �����õĵ���ģ��(�б�) String
	 * pk_corp ��˾���� String hPrefix ����ģ��(�б�)��ͷ�Զ�����ǰ׺ String hEndfix
	 * ����ģ��(�б�)��ͷ�Զ������׺ int hAddNum ����ģ��(�б�)��ͷ�Զ�����������[ֻ��] String bPrefix
	 * ����ģ��(�б�)�����Զ�����ǰ׺ String bEndfix ����ģ��(�б�)�����Զ������׺ int bAddNum
	 * ����ģ��(�б�)�����Զ�����������[ֻ��] int iPos ����ģ��(�б�)��ͷ�����Զ�����ֲ���� ���أ�void ���⣺
	 * ���ڣ�(2002-09-18 09:47:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
			// �����Զ��������
			DefVO[] defs = null;
			BillListData bd = panel.getBillListData();
			if (bd == null) {
				nc.vo.scm.pub.SCMEnv.out("--> billdata null.");
				return;
			}
			// Object[] objs = getDefVOBatch(pk_corp);
			// if (objs == null || objs.length != 2) {
			// nc.vo.scm.pub.SCMEnv.out("����Զ�̵��ó����쳣�������б��Զ����������쳣����!");
			// return;
			// }
			// ��ͷ,��ö�Ӧ�ڹ�˾�ĸõ��ݵ��Զ���������
			// defs = nc.ui.bd.service.BDDef.queryDefVO("��Ӧ��/ARAP����ͷ", pk_corp);
			defs = getDefHead(pk_corp,cbilltypecode);// (DefVO[]) objs[0];
			String strHKey = hPrefix + (hEndfix == null ? "" : hEndfix);
			String strBKey = bPrefix + (bEndfix == null ? "" : bEndfix);
			if ((defs != null)) {
				updateItemByDef(bd, defs, hPrefix, hEndfix, hAddNum, bHead);
			}
			// ����,��ö�Ӧ�ڹ�˾�ĸõ��ݵ��Զ���������
			// defs = nc.ui.bd.service.BDDef.queryDefVO("��Ӧ��/ARAP������", pk_corp);
			defs = getDefBody(pk_corp,cbilltypecode);// (DefVO[]) objs[1];
			if ((defs != null)) {
				updateItemByDef(bd, defs, bPrefix, bEndfix, bAddNum, bHead);
			}
			panel.setListData(bd);

		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("���ص���ģ��(�б�)�Զ�����ʱ�����쳣��");
			nc.vo.scm.pub.SCMEnv.out(e);
		}

	}

	/**
	 * ��������־ƽ ���ܣ������Զ����� ������ BillData bd ����ģ�忨Ƭ DefVO[] defVOs �Զ�����VO[] String
	 * fieldPrefix �Զ�����ǰ׺ String fieldEndfix �Զ������׺ int iAddNum �Զ�����������[ֻ��]
	 * boolean isHead �Ƿ��ͷ boolean isAddNumObjName �Ƿ�ƴ���Զ������������
	 * 
	 * �������ڣ�(2001-11-9 16:18:40)
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
				/* ����key */ 
				//2009/10/27 �����  -  ��ȡ�±������޸ģ�����������Զ�����Ϊvhdef1��.. ����ʽ��strTemp.substring(4)ȡ������ȷ���±�
				String sDefKeysuffix = getDefKeySuffix(strTemp);
				strTemp = (sDefKeysuffix == null) ? strTemp.substring(4) : sDefKeysuffix;
				
				if (iAddNum != 0) {
					iOldIndex = new Integer(strTemp);
					nc.vo.scm.pub.SCMEnv.out("ԭ���±꣺" + iOldIndex);
					iOldIndex = new Integer(iOldIndex.intValue() + iAddNum);
					strTemp = iOldIndex.toString();
					nc.vo.scm.pub.SCMEnv.out("�������±꣺" + strTemp);
				}
				itemkey = fieldPrefix + strTemp;
			}
			if (fieldEndfix != null) {
				itemkey += fieldEndfix.trim();
			}

			BillItem item = null;

			// λ��
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
	 * ���ߣ������
	 * ���ܣ���ȡ�Զ�������±꣬��hdef1��hdef2������ȡ�±�Ϊ1,2,..
	 * ������strValue - �Զ�����key
	 * ���أ��±�ֵ
	 * ���⣺
	 * ���ڣ�2009-10-27
	 * �޸����ڣ� �޸��ˣ��޸�ԭ��ע�ͱ�־
	 */
	public static String getDefKeySuffix(String strValue){
		
		Matcher matcher = getDefKeyPattern().matcher(strValue);
		
		if(!matcher.matches()) return null;
		
		return matcher.group(2);

	}

	/**
	 * ���ߣ������
	 * ���ܣ���ȡ�Զ�����key��ƥ��ģʽ
	 * ������
	 * ���أ�Pattern - ƥ��ģʽ
	 * ���⣺
	 * ���ڣ�2009-10-27
	 * �޸����ڣ� �޸��ˣ��޸�ԭ��ע�ͱ�־
	 */
	private static Pattern getDefKeyPattern() {
		if(null == defKeyPattern)
			defKeyPattern = Pattern.compile("([a-zA-Z]*[\\d]*[a-zA-Z]+)([\\d]+)");
		return defKeyPattern;
	}
	
	private static void updateItem(BillItem item,nc.vo.bd.def.DefVO defVO,String fieldPrefix,boolean isAddNumObjName,boolean isHead){
		if (item != null) {
			
			//ģ���ж��������
			int iDataType = item.getDataType();
			
			if (defVO != null) {

				String itemKey = item.getKey();

				// �û�û�е�������������Ϊ�����Զ���������
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
				// ���볤��
				int inputlength = defVO.getLengthnum().intValue();
				item.setLength(inputlength);
				// ��������
				String iTypeDef = defVO.getType();
				int datatype = BillItem.STRING;
				if (iTypeDef.equals("��ע"))
					datatype = BillItem.STRING;
				else if (iTypeDef.equals("����"))
					datatype = BillItem.DATE;
				else if (iTypeDef.equals("����")) {
					datatype = BillItem.INTEGER;
					if ((defVO.getDigitnum() != null)
							&& (defVO.getDigitnum().intValue() > 0)) {
						datatype = BillItem.DECIMAL;
						item.setDecimalDigits(defVO.getDigitnum().intValue());
					}
				}
				
				/**
				 * Ԫ����ģ�͵��Զ�����Ҫ��MetaDataProperty�����ÿ�
				 * v5.5 ��ʱ�Ľ���취��v6.0UAPͳһ���
				 */
				if (item.getMetaDataProperty()!=null){
					item.setMetaDataProperty(null);
				}
				
				//�е������Զ��������Ϊ���Զ�����������ͣ������û��������������
				String strDefDocReftype = defVO.getDefdef().getPk_bdinfo();
				if (iTypeDef.equals("ͳ��") && strDefDocReftype != null){
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
					//ͳ�����Զ�������Ҫ�Զ�У�������ݵ���Ч�ԣ�since V57
					ref.setAutoCheck(true);
					//���÷�������
					ref.setReturnCode(false);
				}
			} else {
				// item.setShow(false);
			}
		}
	}
	/**
	 * ��������־ƽ ���ܣ������б�ģ���Զ����� ������ BillListData bd �����б�ģ��BillListData DefVO[]
	 * defVOs �Զ�����VO[] String fieldPrefix �Զ�����ǰ׺ String fieldEndfix �Զ������׺ int
	 * iAddNum �Զ�����������[ֻ��] boolean isHead �Ƿ��ͷ
	 * 
	 * �������ڣ�(2003-12-9 20:38:40)
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

			// λ��
			if (isHead)
				item = bd.getHeadItem(itemkey);
			else
				item = bd.getBodyItem(itemkey);

			if (item != null) {
				if (defVO != null) {

					String itemKey = item.getKey();
					// �û�û�е�������������Ϊ�����Զ���������
					String fieldName = item.getName();
					if (fieldName == null || fieldName.trim().equals("")
							|| fieldName.indexOf(KEY_NOT_MODIFIED_1) >= 0
							|| fieldName.indexOf(KEY_NOT_MODIFIED_2) >= 0
							|| fieldName.startsWith(fieldPrefix)) {
						if (itemKey.indexOf(fieldPrefix) >= 0) {
							item.setName(defVO.getDefname());
						}
					}
					// ���볤��
					int inputlength = defVO.getLengthnum().intValue();
					item.setLength(inputlength);
					// ��������
					String type = defVO.getType();
					int datatype = BillItem.STRING;
					if (type.equals("��ע"))
						datatype = BillItem.STRING;
					else if (type.equals("����"))
						datatype = BillItem.DATE;
					else if (type.equals("����")) {
						datatype = BillItem.INTEGER;
						if ((defVO.getDigitnum() != null)
								&& (defVO.getDigitnum().intValue() > 0)) {
							datatype = BillItem.DECIMAL;
							item.setDecimalDigits(defVO.getDigitnum()
									.intValue());
						}
					}
					
					/**
					 * Ԫ����ģ�͵��Զ�����Ҫ��MetaDataProperty�����ÿ�
					 * v5.5 ��ʱ�Ľ���취��v6.0UAPͳһ���
					 */
					if (item.getMetaDataProperty()!=null){
						item.setMetaDataProperty(null);
					}
						
					if (type.equals("ͳ��"))
						datatype = BillItem.USERDEF;
					item.setDataType(datatype);
					// ��������
					String reftype = defVO.getDefdef().getPk_bdinfo();
					// String reftype = defVO.getPk_defdef();
					if (type.equals("ͳ��")) {
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
	 * �����ߣ����� ���ܣ��û�����������͹�Ӧ�������Ĳ�ѯģ���Զ����� ������panel BillCardPanel,pk_corp
	 * String,prefix ���أ�BillCardPanel ���⣺ ���ڣ�(2002-09-18 09:47:29)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	private static void updateQueryCondition(QueryConditionClient client,
	// �����µ�QueryConditionClient
			String pk_corp, // ��˾����
			String docName, String prefix // ģ�����Զ�����ǰ׺
	) {

		// ������յĳ���
		final int iMaxLenOfDef = 20;

		if (client == null || prefix == null || prefix.length() <= 0) // ||
																		// bPrefix
																		// ==
																		// null
																		// ||
																		// bPrefix.length()
																		// <=0)
			return;

		// ��ѯ��Ӧ��˾�Ĵ���������Զ�����
    List<DefVO[]> l = DefquoteQueryUtil.getInstance().queryDefusedVO(new String[]{docName}, pk_corp);
    DefVO[] defs =  null;
    if(l!=null && l.size()>0)
      defs = l.get(0);
		//
		updateQueryConditionByDefVOs(client, pk_corp, prefix, defs);
	}

	/**
	 * �����ߣ����� ���ܣ��û�����������͹�Ӧ�������Ĳ�ѯģ���Զ����� ������panel BillCardPanel,pk_corp
	 * String,prefix ���أ�BillCardPanel ���⣺ ���ڣ�(2002-09-18 09:47:29)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־�� ��־ƽ ����ǰ���ܽ��������Ż������� 2004-09-10
	 */
	private static void updateQueryConditionByDefVOs(
			QueryConditionClient client,
			// �����µ�QueryConditionClient
			String pk_corp, // ��˾����
			String prefix,// ģ�����Զ�����ǰ׺
			DefVO[] defs) {
		if (defs == null) {
			nc.vo.scm.pub.SCMEnv.out("����������Զ�����VOΪ�գ�ֱ�ӷ���!");
			return;
		}
		// ������յĳ���
		final int iMaxLenOfDef = 20;

		if (client == null || prefix == null || prefix.length() <= 0) // ||
																		// bPrefix
																		// ==
																		// null
																		// ||
																		// bPrefix.length()
																		// <=0)
			return;

		// ��ѯ��Ӧ��˾�Ĵ���������Զ�����
		// DefVO[] defs = nc.ui.bd.service.BDDef.queryDefVO(docName, pk_corp);
		Vector v = new Vector();
		nc.vo.pub.query.QueryConditionVO[] cs = client.getConditionDatas();
		for (int i = 0; cs != null && i < cs.length; i++) {
			String fieldCode = cs[i].getFieldCode();
			String fieldName = cs[i].getFieldName();
			DefVO vo = null;
			if (fieldCode == null)
				continue;

			// ��ȡ��ѯģ�����Զ������VO
			if (fieldCode.startsWith(prefix) && defs != null) {
				int iIndex = -1;
				try {
					int len = prefix.length();
					iIndex = new Integer(fieldCode.substring(len)).intValue();
					iIndex--;

				} catch (Exception e) {
					nc.vo.scm.pub.SCMEnv.out("��ֵ���������쳣");
				}
				if (iIndex >= 0 && iIndex < defs.length && defs[iIndex] != null) {
					// �û�û�е�������������Ϊ�����Զ���������
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

			// ���ò���
			if (vo != null) {
				String type = vo.getType();
				nc.ui.pub.beans.UIRefPane refPane = new nc.ui.pub.beans.UIRefPane();
				if (type.equals("ͳ��")) {
					/* �Զ������б�����Ϊ�գ���Ϊ�ǻ������ݵ��� */
					if (vo.getDefdef() != null
							&& vo.getDefdef().getPk_defdoclist() == null) {
						refPane = nc.ui.bd.ref.RefCall.getUIRefPane(vo
								.getDefdef().getPk_bdinfo(), pk_corp);
					} else {
						/* �Զ������б�������Ϊ�գ���Ϊ���Զ��嵵�� */
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
						refPane.setRefNodeName("�Զ������");
						refPane.getRefModel().addWherePart(sWhereString);
						refPane.setReturnCode(false);
					}
					// ��ѯ��Է���Ӧ����ʾ
					if (refPane != null && refPane.getRefModel() != null) {
						refPane.getRefModel().setSealedDataShow(true);
					}
				} else if (type.equals("����")) {
					refPane.setRefNodeName("����");
				} else if (type.equals("��ע")) {
					refPane.setMaxLength(iMaxLenOfDef);
					refPane.setButtonVisible(false);
				} else if (type.equals("����")) {
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
	 * �����ߣ����� ���ܣ��û���ѯģ����Զ����� ������ QueryConditionClient client
	 * �����µ�QueryConditionClient String pk_orp ��˾���� String hPrefix
	 * ����ģ���е���ͷ���Զ�����ǰ׺ String bPrefix ����ģ���е�������Զ�����ǰ׺ ���أ�void ���⣺ ���ڣ�(2002-09-18
	 * 09:47:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * 2003-12-09����־ƽ��ͳһ����
	 */
	public static void updateQueryConditionClientUserDef(
			QueryConditionClient client, String pk_corp,String cbilltypecode,String hPrefix,
			String bPrefix) {
		updateQueryConditionClientUserDef(client, pk_corp,cbilltypecode,hPrefix, null, 0,
				bPrefix, null, 0);
	}

	/**
	 * �����ߣ���־ƽ ���ܣ��û���ѯģ����Զ����� ������ QueryConditionClient client �����õĲ�ѯģ�� String
	 * pk_corp ��˾���� String hPrefix ��ѯģ���ͷ�Զ�����ǰ׺���ǿ� String hEndfix ��ѯģ���ͷ�Զ������׺
	 * int hAddDigit ��ѯģ���ͷ�Զ�����������[ֻ��] String bPrefix ��ѯģ������Զ�����ǰ׺���ǿ� String
	 * bEndfix ��ѯģ������Զ������׺ int bAddDigit ��ѯģ������Զ�����������[ֻ��] ���أ�void ���⣺
	 * ���ڣ�(2002-12-09 19:57:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public static void updateQueryConditionClientUserDef(
			QueryConditionClient client, String pk_corp, String cbilltypecode, String hPrefix,
			String hEndfix, int hAddNum, String bPrefix, String bEndfix,
			int bAddNum) {
		updateQueryConditionClientUserDef(client, pk_corp, cbilltypecode,hPrefix, hEndfix,
				hAddNum, bPrefix, bEndfix, bAddNum, _STYLE_NORMAL);
	}

	/**
	 * �����ߣ���־ƽ ���ܣ��û���ѯģ����Զ����� ������ QueryConditionClient client �����õĲ�ѯģ�� String
	 * pk_corp ��˾���� String hPrefix ��ѯģ���ͷ�Զ�����ǰ׺���ǿ� String hEndfix ��ѯģ���ͷ�Զ������׺
	 * int hAddDigit ��ѯģ���ͷ�Զ�����������[ֻ��] String bPrefix ��ѯģ������Զ�����ǰ׺���ǿ� String
	 * bEndfix ��ѯģ������Զ������׺ int bAddDigit ��ѯģ������Զ�����������[ֻ��] int iStyle ������
	 * ���أ�void ���⣺ ���ڣ�(2002-12-09 19:57:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public static void updateQueryConditionClientUserDef(
			QueryConditionClient client, String pk_corp,String cbilltypecode, String hPrefix,
			String hEndfix, int hAddNum, String bPrefix, String bEndfix,
			int bAddNum, int iStyle) {
		try {
			// ������յĳ���
			final int iMaxLenOfDef = 20;

			if (client == null || hPrefix == null || hPrefix.length() <= 0)
				return;
			// ��ѯ��Ӧ��˾�ĵ���ͷ�͵������Զ�����
			Object[] objs = null;
			DefVO[] hDefs = null;
			DefVO[] bDefs = null;
			if (bPrefix != null) {
				// objs = getDefVOBatch(pk_corp);
				// if (objs == null || objs.length != 2) {
				// nc.vo.scm.pub.SCMEnv.out("����Զ�̵��ó����쳣����ѯģ���Զ����������쳣����!");
				// return;
				// }
				hDefs = getDefHead(pk_corp,cbilltypecode);// (DefVO[]) objs[0];
				bDefs = getDefBody(pk_corp,cbilltypecode);// (DefVO[]) objs[1];
			} else {
        
        List<DefVO[]> l = DefquoteQueryUtil.getInstance().queryDefusedVO(new String[]{"��Ӧ��/ARAP����ͷ"}, pk_corp);
        if(l!=null && l.size()>0)
          hDefs = l.get(0);
          //nc.ui.bd.service.BDDef.queryDefVO("��Ӧ��/ARAP����ͷ",pk_corp);
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
			nc.vo.scm.pub.SCMEnv.out("���ز�ѯģ���Զ�����ʱ�����쳣��");
			nc.vo.scm.pub.SCMEnv.out(e);
		}
	}

	/**
	 * �����ߣ���־ƽ ���ܣ��û���ѯģ����Զ����� ������ QueryConditionClient client �����õĲ�ѯģ�� String
	 * pk_corp ��˾���� String hPrefix ��ѯģ���ͷ�Զ�����ǰ׺���ǿ� String hEndfix ��ѯģ���ͷ�Զ������׺
	 * int hAddDigit ��ѯģ���ͷ�Զ�����������[ֻ��] String bPrefix ��ѯģ������Զ�����ǰ׺���ǿ� String
	 * bEndfix ��ѯģ������Զ������׺ int bAddDigit ��ѯģ������Զ�����������[ֻ��] int iStyle ������
	 * DefVO[] hDefs ���ݱ�ͷ�Զ�����VO[] DefVO[] hDefs ���ݱ����Զ�����VO[] ���أ�void ���⣺
	 * ���ڣ�(2004-09-10 17:32:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	private static void updateQueryConditionClientUserDef(
			QueryConditionClient client, String pk_corp, String hPrefix,
			String hEndfix, int hAddNum, String bPrefix, String bEndfix,
			int bAddNum, int iStyle, DefVO[] hDefs, DefVO[] bDefs,String cbilltypecode) {
		try {
			// ������յĳ���
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
				// ��ѯ�������Ա�ͷ�еĵ��Զ�����
				if (bIsHead && hDefs != null) {
					int iIndex = -1;
					iIndex = getIndex(fieldCode, hPrefix, hEndfix, hAddNum);
					if (iIndex >= 0 && iIndex < hDefs.length
							&& hDefs[iIndex] != null) {
						// �û�û�е�������������Ϊ�����Զ���������
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
				// ��ѯ�������Ա����еĵ��Զ�����
				else if (bIsBody && bDefs != null) {
					int iIndex = -1;
					iIndex = getIndex(fieldCode, bPrefix, bEndfix, bAddNum);
					if (iIndex >= 0 && iIndex < bDefs.length
							&& bDefs[iIndex] != null) {
						// �û�û�е�������������Ϊ�����Զ���������
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
				// ���ò���
				if (vo != null) {
					String type = vo.getType();
					nc.ui.pub.beans.UIRefPane refPane = new nc.ui.pub.beans.UIRefPane();
					if (type.equals("ͳ��")) {
						/* �Զ������б�����Ϊ�գ���Ϊ�ǻ������ݵ��� */
						if (vo.getDefdef() != null
								&& vo.getDefdef().getPk_defdoclist() == null) {
							refPane = nc.ui.bd.ref.RefCall.getUIRefPane(vo
									.getDefdef().getPk_bdinfo(), pk_corp);
						} else {
							/* �Զ������б�������Ϊ�գ���Ϊ���Զ��嵵�� */
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
//				            begin ncm linsf 201011052105111857��������_�Զ�����ֻ��ʾ����˾�ͼ���
							sWhereString += " and pk_corp in('"+pk_corp+"','0001')";
								//end ncm linsf 201011052105111857��������_�Զ�����ֻ��ʾ����˾�ͼ���
							refPane.setRefNodeName("�Զ������");
							refPane.getRefModel().addWherePart(sWhereString);
						}
						// ��ѯ��Է���Ӧ����ʾ
						if (refPane != null && refPane.getRefModel() != null) {
							refPane.getRefModel().setSealedDataShow(true);
						}
					} else if (type.equals("����")) {
						refPane.setRefNodeName("����");
					} else if (type.equals("��ע")) {
						refPane.setMaxLength(iMaxLenOfDef);
						refPane.setButtonVisible(false);
					} else if (type.equals("����")) {
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
				voError.setConsultCode("��˾Ŀ¼");/*-=notranslate=-*/
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
			nc.vo.scm.pub.SCMEnv.out("���ز�ѯģ���Զ�����ʱ�����쳣��");
			nc.vo.scm.pub.SCMEnv.out(e);
		}
	}

  private static void updateQueryConditionDelivClientUserDef(
      QueryConditionClient client, String pk_corp, String hPrefix,
      String hEndfix, int hAddNum, String bPrefix, String bEndfix,
      int bAddNum, int iStyle, DefVO[] hDefs, DefVO[] bDefs,String cbilltypecode) {
    try {
      // ������յĳ���
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
        // ��ѯ�������Ա�ͷ�еĵ��Զ�����
        if (bIsHead && hDefs != null) {
          int iIndex = -1;
          iIndex = getIndex(fieldCode, hPrefix, hEndfix, hAddNum);
          if (iIndex >= 0 && iIndex < hDefs.length
              && hDefs[iIndex] != null) {
            // �û�û�е�������������Ϊ�����Զ���������
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
        // ��ѯ�������Ա����еĵ��Զ�����
        else if (bIsBody && bDefs != null) {
          int iIndex = -1;
          iIndex = getIndex(fieldCode, bPrefix, bEndfix, bAddNum);
          if (iIndex >= 0 && iIndex < bDefs.length
              && bDefs[iIndex] != null) {
            // �û�û�е�������������Ϊ�����Զ���������
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
        // ���ò���
        if (vo != null) {
          String type = vo.getType();
          nc.ui.pub.beans.UIRefPane refPane = new nc.ui.pub.beans.UIRefPane();
          if (type.equals("ͳ��")) {
            /* �Զ������б�����Ϊ�գ���Ϊ�ǻ������ݵ��� */
            if (vo.getDefdef() != null
                && vo.getDefdef().getPk_defdoclist() == null) {
              refPane = nc.ui.bd.ref.RefCall.getUIRefPane(vo
                  .getDefdef().getPk_bdinfo(), pk_corp);
            } else {
              /* �Զ������б�������Ϊ�գ���Ϊ���Զ��嵵�� */
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
//            begin ncm linsf 201011052105111857��������_�Զ�����ֻ��ʾ����˾�ͼ���
			  sWhereString += " and pk_corp in('"+pk_corp+"','0001')";
				//end ncm linsf 201011052105111857��������_�Զ�����ֻ��ʾ����˾�ͼ���
              refPane.setRefNodeName("�Զ������");
              refPane.getRefModel().addWherePart(sWhereString);
            }
            // ��ѯ��Է���Ӧ����ʾ
            if (refPane != null && refPane.getRefModel() != null) {
              refPane.getRefModel().setSealedDataShow(true);
            }
          } else if (type.equals("����")) {
            refPane.setRefNodeName("����");
          } else if (type.equals("��ע")) {
            refPane.setMaxLength(iMaxLenOfDef);
            refPane.setButtonVisible(false);
          } else if (type.equals("����")) {
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
        voError.setConsultCode("��˾Ŀ¼");/*-=notranslate=-*/
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
      nc.vo.scm.pub.SCMEnv.out("���ز�ѯģ���Զ�����ʱ�����쳣��");
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
	 * �����ߣ���־ƽ ���ܣ��û���ѯģ����Զ����� ������ QueryConditionClient client �����õĲ�ѯģ�� String
	 * pk_corp ��˾���� String hPrefix ��ѯģ���ͷ�Զ�����ǰ׺���ǿ� String hEndfix ��ѯģ���ͷ�Զ������׺
	 * int hAddDigit ��ѯģ���ͷ�Զ�����������[ֻ��] String bPrefix ��ѯģ������Զ�����ǰ׺���ǿ� String
	 * bEndfix ��ѯģ������Զ������׺ int bAddDigit ��ѯģ������Զ�����������[ֻ��] String
	 * prefixCumandoc ��ѯģ���п��̹������Զ�����ǰ׺(�޴�����Ҫ��NULL) String prefixCubasdoc
	 * ��ѯģ���п��̵����Զ�����ǰ׺(�޴�����Ҫ��NULL) String prefixInvmandoc
	 * ��ѯģ���д���������Զ�����ǰ׺(�޴�����Ҫ��NULL) String prefixInvbasdoc
	 * ��ѯģ���д�������Զ�����ǰ׺(�޴�����Ҫ��NULL) ���أ�void ���⣺ ���ڣ�(2002-12-09 19:57:29)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־�� 2004-09-10 ��־ƽ �Ż�ǰ��̨��������
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
	 * �����ߣ���־ƽ ���ܣ��û���ѯģ����Զ����� ������ QueryConditionClient client �����õĲ�ѯģ�� String
	 * pk_corp ��˾���� String hPrefix ��ѯģ���ͷ�Զ�����ǰ׺���ǿ� String hEndfix ��ѯģ���ͷ�Զ������׺
	 * int hAddDigit ��ѯģ���ͷ�Զ�����������[ֻ��] String bPrefix ��ѯģ������Զ�����ǰ׺���ǿ� String
	 * bEndfix ��ѯģ������Զ������׺ int bAddDigit ��ѯģ������Զ�����������[ֻ��] int iStyle ������
	 * String prefixCumandoc ��ѯģ���п��̹������Զ�����ǰ׺(�޴�����Ҫ��NULL) String
	 * prefixCubasdoc ��ѯģ���п��̵����Զ�����ǰ׺(�޴�����Ҫ��NULL) String prefixInvmandoc
	 * ��ѯģ���д���������Զ�����ǰ׺(�޴�����Ҫ��NULL) String prefixInvbasdoc
	 * ��ѯģ���д�������Զ�����ǰ׺(�޴�����Ҫ��NULL) ���أ�void ���⣺ ���ڣ�(2002-12-09 19:57:29)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־�� ��־ƽ ֧��ǰ��̨һ�ν������ô�������̡����ݱ�ͷ[�ǿ�]�����ݱ��������Զ�����
	 */
	public static void updateQueryConditionClientUserDefAll(
			QueryConditionClient client, String pk_corp, String cbilltypecode,String hPrefix,
			String hEndfix, int hAddNum, String bPrefix, String bEndfix,
			int bAddNum, int iStyle, String prefixCumandoc,
			String prefixCubasdoc, String prefixInvmandoc,
			String prefixInvbasdoc) {
		try {
			// ������յĳ���
			final int iMaxLenOfDef = 20;

			if (client == null || hPrefix == null || hPrefix.length() <= 0)
				return;
			// ��ѯ��Ӧ��˾�ĵ���ͷ�͵������Զ�����
			String[] saDocName = new String[] {// "��Ӧ��/ARAP����ͷ", "��Ӧ��/ARAP������",
					"���̹�����", "���̵���", "���������", "�������" };
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
				nc.vo.scm.pub.SCMEnv.out("����Զ�̵��ó����쳣����ѯģ���Զ����������쳣����!");
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
			// ���̡�����Զ���������
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
			nc.vo.scm.pub.SCMEnv.out("���ز�ѯģ���Զ�����ʱ�����쳣��");
			nc.vo.scm.pub.SCMEnv.out(e);
		}
	}

	/**
	 * �����ߣ���־ƽ ���ܣ� �û� ���̵��� �Ĳ�ѯģ���Զ����� ������ BillCardPanel panel �����µĲ�ѯģ�� String
	 * pk_corp ��˾���� String prefix ģ�����Զ�����ǰ׺ ���أ�BillCardPanel ���⣺ ���ڣ�(2003-12-11
	 * 19:02:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public static void updateQueryConditionForCubasdoc(
			QueryConditionClient client, String pk_corp, String prefix) {
		updateQueryCondition(client, pk_corp, "���̵���", prefix);

	}

	/**
	 * �����ߣ���־ƽ ���ܣ� �û� ���̹����� �Ĳ�ѯģ���Զ����� ������ BillCardPanel panel �����µĲ�ѯģ�� String
	 * pk_corp ��˾���� String prefix ģ�����Զ�����ǰ׺ ���أ�BillCardPanel ���⣺ ���ڣ�(2003-12-11
	 * 19:02:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public static void updateQueryConditionForCumandoc(
			QueryConditionClient client, String pk_corp, String prefix) {
		updateQueryCondition(client, pk_corp, "���̹�����", prefix);
	}

	/**
	 * �����ߣ���־ƽ ���ܣ� �û� ������� �Ĳ�ѯģ���Զ����� ������ BillCardPanel panel �����µĲ�ѯģ�� String
	 * pk_corp ��˾���� String prefix ģ�����Զ�����ǰ׺ ���أ�BillCardPanel ���⣺ ���ڣ�(2003-12-11
	 * 19:02:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public static void updateQueryConditionForInvbasdoc(
			QueryConditionClient client, String pk_corp, String prefix) {
		updateQueryCondition(client, pk_corp, "�������", prefix);
	}

	/**
	 * �����ߣ���־ƽ ���ܣ� �û� ��������� �Ĳ�ѯģ���Զ����� ������ BillCardPanel panel �����µĲ�ѯģ�� String
	 * pk_corp ��˾���� String prefix ģ�����Զ�����ǰ׺ ���أ�BillCardPanel ���⣺ ���ڣ�(2003-12-11
	 * 19:02:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public static void updateQueryConditionForInvmandoc(
			QueryConditionClient client, String pk_corp, String prefix) {
		updateQueryCondition(client, pk_corp, "���������", prefix);
	}

	/**
	 * ��������־ƽ ���ܣ����롰��Ӧ��/ARAP����ͷ��������Ӧ��/ARAP�����塱 ������String strSrc
	 * {����Ӧ��/ARAP����ͷ��������Ӧ��/ARAP�����塱}�е�һ��ֵ ���أ�String strDst �����Ľ�� ���⣺
	 * ���ڣ�(2005-05-21 13:23:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	private static String transRes(String strSrc) {
		return transRes(strSrc,null,false);
	}
	private static String transRes(String strSrc,String cbilltypecode,boolean isHead) {
		if(cbilltypecode == null){
			String strDst = strSrc;
			if (strSrc != null
					&& (strSrc.equals("��Ӧ��/ARAP����ͷ") || strSrc
							.equals("��Ӧ��/ARAP������"))) {
				String strResId = "D14";
				if (strSrc.indexOf("��") >= 0) {
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
	 * ���������Ӣ �� ���ܣ��û��Զ����� ������ String[] fieldname String pk_corp ��˾���� DefVO[]
	 * Defs ���ݱ�ͷ�Զ�����VO[]
	 * 
	 * ���أ�void ���⣺ ���ڣ�(2004-09-10 17:32:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	/**���´����55��ʹ��@*/
	/**�����ν�˵�������Ǳ����ͷͳ�����Զ�������ش������⣬��Ҫ������ص������ֶΣ�
	 * ���磺pk_defdoc1�������������������Ҫ�޸�DefSetTool����Զ�������صĸ�����
	 * �⣬��Ҫ�����������⣺1����װ����ÿ������ģ���ͷ���Զ����������ֶ�����Ϊ�û�
	 * �����ã�2������Ĭ��ģ���Լ��û��Զ���ģ���У�ͳ�����Զ������Ӧ�������ֶ���ʾ��
	 * �����ֶ����أ�3������˵���������ͷ����ͳ�����Զ�������ʡ�ʵʩ��Ҫ�����Ӧ
	 * �������ֶ���ʾ���� 
	 * */
	public static void updateBillCardPanelUserDefForHead(BillCardPanel panel,
			String pk_corp,String cbilltypecode, String hPrefix, String hEndfix, int hAddNum,
			String bPrefix, String bEndfix, int bAddNum,int iCount) {

		// new Exception("==>>��Ƭ �Զ������ʼ������ջ��Ϣ:").printStackTrace();

		try {
			if (panel == null) {
				nc.vo.scm.pub.SCMEnv.out("--> BillCardPanel is null.");
				return;
			}
			BillData billData = panel.getBillData();
			boolean bIsAddObjName = isAddObjName(panel);
			if (bIsAddObjName) {
				nc.vo.scm.pub.SCMEnv.out("�����Զ�����ؼ��Ǳ���ģ�壬Ҫƴ���Զ������������");
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
			// ����,��ö�Ӧ�ڹ�˾�ĸõ��ݵ��Զ���������
			// defs = nc.ui.bd.service.BDDef.queryDefVO("��Ӧ��/ARAP������", pk_corp);
			defs = getDefBody(pk_corp,cbilltypecode);// (DefVO[]) objs[1];
			if ((defs != null)) {
				updateItemByDefForHead(billData, defs, bPrefix, bEndfix, bAddNum,
						false, bIsAddObjName,iCount,null);
			}


		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("���ص���ģ��(��Ƭ)�Զ�����ʱ�����쳣��");
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
			
			// λ��
			if (isHead) {
				if(defVO.getType().equals("ͳ��") && defVO.getDefdef().getPk_bdinfo() != null){
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
			
			//ģ���ж��������
			int iDataType = item.getDataType();
			
			if (defVO != null) {

				String itemKey = item.getKey();

				// �û�û�е�������������Ϊ�����Զ���������
				String fieldName = item.getName();
				if (fieldName == null || fieldName.trim().equals("")
						|| fieldName.indexOf(KEY_NOT_MODIFIED_1) >= 0
						|| fieldName.indexOf(KEY_NOT_MODIFIED_2) >= 0) {
					if (itemKey.indexOf(fieldPrefix) >= 0) {
							item.setName(defVO.getDefname());
						}
//					}
				}
				// ���볤��
				int inputlength = defVO.getLengthnum().intValue();
				item.setLength(inputlength);
				// ��������
				String iTypeDef = defVO.getType();
				int datatype = BillItem.STRING;
				if (iTypeDef.equals("��ע"))
					datatype = BillItem.STRING;
				else if (iTypeDef.equals("����"))
					datatype = BillItem.DATE;
				else if (iTypeDef.equals("����")) {
					datatype = BillItem.INTEGER;
					if ((defVO.getDigitnum() != null)
							&& (defVO.getDigitnum().intValue() > 0)) {
						datatype = BillItem.DECIMAL;
						item.setDecimalDigits(defVO.getDigitnum().intValue());
					}
				}
				
				/**
				 * Ԫ����ģ�͵��Զ�����Ҫ��MetaDataProperty�����ÿ�
				 * v5.5 ��ʱ�Ľ���취��v6.0UAPͳһ���
				 */
				if (item.getMetaDataProperty()!=null){
					item.setMetaDataProperty(null);
				}
				
				//�е������Զ��������Ϊ���Զ�����������ͣ������û��������������
				String strDefDocReftype = defVO.getDefdef().getPk_bdinfo();
				if (iTypeDef.equals("ͳ��") && strDefDocReftype != null){
					datatype = BillItem.USERDEF;
					item.setRefType(strDefDocReftype);
				}
				item.setDataType(datatype);
				item.reCreateComponent();
				item.setIsDef(true);
				
				if (item.getDataType() == BillItem.UFREF || item.getDataType() == BillItem.USERDEF) {
					item.setDataType(BillItem.UFREF);
					UIRefPane ref = (UIRefPane) item.getComponent();
					//ͳ�����Զ�������Ҫ�Զ�У�������ݵ���Ч�ԣ�����Ʒ���������Ҫ�Զ�У�飬������д������
					ref.setAutoCheck(true);
					//���÷�������
					ref.setReturnCode(false);
				}
			} else {
				// item.setShow(false);
			}
		}
	}
	
//	/**
//	 * ������57
//	 * ���������������Ʒ���չ����޷�������ʾname������
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
   * ��������������ALT+SHIT+�����ק�������Ķ���.�ŵ��Զ�����afterEdit�¼�֮ǰ
   * ���Ϊ���գ��õ�һ�е�pkΪ�����и�ֵ
   * <b>����˵��</b>
   * @param e
   * @author sunwei
   * @param sVdefValueKey 
   * @param sVdefPkKey 
   * @param card 
   * @time 2009-11-4 ����04:31:03
   */
  public static void batchDefEdit(BillCardPanel card, BillEditEvent e, String sVdefPkKey, String sVdefValueKey) {
    int[] rows = card.getBillTable().getSelectedRows();
    //����ALT+SHIT+�����ק�������Ķ����������ǵ�һ�У����ô���Ĭ�϶����õ�һ��
    //���޸������е�ֵ��
    if (rows.length == 0 || e.getRow() == rows[0]) {
      return;
    }
    String key = e.getKey();
    BillItem item = card.getBodyItem(key);
    //���ǵ������գ���˲������pk�ȱ༭�������е�ֵ
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
