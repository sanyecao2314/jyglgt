package nc.ui.jyglgt.scm.ic.freeitem;

import java.util.ArrayList;
import java.util.Hashtable;

import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.scm.ic.freeitem.DefHelper;
import nc.ui.scm.pub.CacheTool;
import nc.vo.pub.BusinessException;
import nc.vo.scm.ic.bill.FreeVO;
import nc.vo.scm.ic.bill.InvVO;
import nc.vo.scm.ic.freeitem.DefdefHeaderVO;
import nc.vo.scm.ic.freeitem.DefdefVO;

/**
 * 创建者：仲瑞庆
 * 创建日期：(2001-5-9 下午 6:44)
 * 功能：自由项参照
 * 
 * wnj:2005-04-28:add comment below:
 * 
 * 可以在存货基本档案按顺序设置free1-free5的类型，保存的值是bd_defdef.pk_defdef
 * -----------------
 * 如果type='统计' 类型的并且pk_defdoclist不为空，
 * 那么在bd_defdoclist中pk_defdoclist对应的是统计类型的参照类型编码、名称。（如‘部门级别’、‘行政区划’）
 * 统计类型的参照内容在bd_defdoc中保存。
 * -----------------
 * 如果pk_defdoclist为空，则以pk_bdinfo指定的参照为准。
 * bd_bdinfo中保存的是UAP缺省定义或产品模块定义(指定的RefModel类不为空)的参照。
 * -----------------
 * 如果pk_bdinfo为空，那就是日期型参照，或数量、备注（文本）等不需要参照的类型
 * -----------------
 * 修改日期，修改人，修改原因，注释标志：
 */
public class FreeItemRefPane extends nc.ui.pub.beans.UIRefPane{
	private FreeItemDlg m_dlgFreeItemDlg;
	private java.lang.String m_sCode= "";
	private java.lang.String m_sInventoryName= "";
	private java.lang.String m_sSpec= "";
	private java.lang.String m_sType= "";
	//自由项参照PK
	private ArrayList m_alFreeItemID= new ArrayList();
	//自由项参照名称
	private ArrayList m_alFreeItemNameIn= new ArrayList();
	//自由项参照名称
	private ArrayList m_alFreeItemReturnName= new ArrayList();
	//自由项值ID
	private ArrayList m_alFreeItemValue= new ArrayList();
	//自由项值ID
	private ArrayList m_alFreeItemValueIn= new ArrayList();
	//自由项值ID
	private ArrayList m_alFreeItemReturnValue= new ArrayList();
	//自由项值名称
	private ArrayList m_alFreeItemReturnValueName= new ArrayList();

	private boolean JustClicked= false; //检测是否刚刚按下Click键
	//private boolean m_bOpenFlag = false;//决定参照是否出现,当没有传入参数时，参照是不出现的
	private Hashtable m_hHashtable= new Hashtable();
	//private final ArrayList m_alInherentConsult= new ArrayList();//固定参照
	private Hashtable m_hInherentConsult=new Hashtable();
/**
 * FreeItemRefPane 构造子注解。
 */
public FreeItemRefPane() {
	super();
	setReturnCode(true);
	this.setEditable(false);
	this.setIsBatchData(false);
	//setInherentConsult();
}
/**
 * FreeItemRefPane 构造子注解。
 * @param p0 java.awt.LayoutManager
 */
public FreeItemRefPane(java.awt.LayoutManager p0) {
	super(p0);
	this.setIsBatchData(false);
}
/**
 * FreeItemRefPane 构造子注解。
 * @param p0 java.awt.LayoutManager
 * @param p1 boolean
 */
public FreeItemRefPane(java.awt.LayoutManager p0, boolean p1) {
	super(p0, p1);
	this.setIsBatchData(false);
}
/**
 * FreeItemRefPane 构造子注解。
 * @param p0 boolean
 */
public FreeItemRefPane(boolean p0) {
	super(p0);
	this.setIsBatchData(false);
}
/**
 * 创建者：仲瑞庆
 * 功能：wnj:是否设置了自由项
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-5-9 下午 7:36)
 * 修改日期，修改人，修改原因，注释标志：
 * @return java.lang.String[][]
 */
protected ArrayList checkFreeItemNameOID() {
	if ((null == m_alFreeItemID) || (m_alFreeItemID.size() == 0)) {
		return null;
	}
	boolean bAllNull= true;
	for (int i= 0; i < m_alFreeItemID.size(); i++) {
		if ((null != m_alFreeItemID.get(i))
			&& (m_alFreeItemID.get(i).toString().trim().length() != 0)) {
			bAllNull= false;
			break;
		}
	}
	if (bAllNull) {
		return null;
	}
	return m_alFreeItemID;
}
/**
 * 创建者：仲瑞庆
 * 功能：
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-5-17 上午 10:34)
 * 修改日期，修改人，修改原因，注释标志：
 * @return boolean
 */
protected boolean checkOpenFlag() {

	if ((null == m_sCode) || (null == m_sInventoryName)) {
		nc.vo.scm.pub.SCMEnv.out("没有存货编码或名称");
		return false;
	}
	if ((m_sCode.equals(""))
		|| (m_sInventoryName.equals(""))
		|| (null == checkFreeItemNameOID())) {
		nc.vo.scm.pub.SCMEnv.out("没有存货编码或名称或自由项设置");
		return false;
	}
	return true;
}
/**
 * 创建者：仲瑞庆
 * 功能：
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-5-17 上午 10:34)
 * 修改日期，修改人，修改原因，注释标志：
 * @return boolean
 */
public void clearReturnAllValue() {
		m_alFreeItemReturnName= null;
		m_alFreeItemNameIn= null;
		m_alFreeItemReturnValue= null;
		m_alFreeItemReturnValueName= null;
}
/**
 * 创建者：仲瑞庆
 * 功能：把
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-5-9 下午 6:52)
 * 修改日期，修改人，修改原因，注释标志：
 * @return
 */
protected void fromOIDtoName() {
	//由m_alFreeItemNameOID得到m_alFreeItemName
	if (null == m_alFreeItemID)
		return;
	//m_alFreeItemReturnName= null;
	//m_alFreeItemReturnName= new ArrayList();
	//m_alFreeItemReturnValueName= null;
	//m_alFreeItemReturnValueName= new ArrayList();
	m_alFreeItemNameIn= null;
	m_alFreeItemNameIn= new ArrayList();

	UIRefPane urpUIRefPane= null;

	for (int j= 0; j < m_alFreeItemID.size(); j++) {
		if ((null == m_alFreeItemID.get(j)) 
		        || (m_alFreeItemID.get(j).toString().trim().length() == 0)) {
			continue;
		}
		String sOID= m_alFreeItemID.get(j).toString().trim();
		//判断是否是固有参照
		String sInherentConsultName= null;
		if (m_hInherentConsult.containsKey(sOID)) {
			sInherentConsultName= (String) m_hInherentConsult.get(sOID);
		} else {
		    //自定义项定义：
		    //bd_defdef基础数据资源表，pk_defdef自定义项主键，pk_bdinfo基础数据主键
			try
			{
				Object obj= CacheTool.getCellValue("bd_defdef", "pk_defdef", "pk_bdinfo", sOID);
				if (obj != null
					&& ((Object[]) obj)[0] != null
					&& ((Object[]) obj)[0].toString().trim().length() != 0) {
				    //refnodename参照节点名 ，如“项目档案”。
					obj=
						CacheTool.getCellValue("bd_bdinfo", "pk_bdinfo", "refnodename", ((Object[]) obj)[0].toString());
					if (obj != null
						&& ((Object[]) obj)[0] != null
						&& ((Object[]) obj)[0].toString().trim().length() != 0) {
					    //BD参照名
						sInherentConsultName= ((Object[]) obj)[0].toString().trim();
						m_hInherentConsult.put(sOID, sInherentConsultName);
					}
				}
			}
			catch (BusinessException e)
			{
				nc.vo.scm.pub.SCMEnv.out(e.getMessage());
			}
		}



		//如果是BD参照
		if (null != sInherentConsultName) {
			ArrayList alTemp= new ArrayList();
			alTemp.add(new Integer(ConsultType.InherentConsult));
			alTemp.add(sInherentConsultName);
			//加入自定义参照的显示名称，以便与IFreeItemParamSeqDef相匹配 陈倪娜 2009-05-06
			alTemp.add(null);
			alTemp.add(null);
			alTemp.add(null);
			alTemp.add(m_alFreeItemReturnName.get(j));
			m_alFreeItemNameIn.add(alTemp);
			//m_alFreeItemReturnName.add(sInherentConsultName);
			
			
//			m_alFreeItemReturnName.add(j, sInherentConsultName);


			if (null != m_alFreeItemValue.get(j)) {
				urpUIRefPane= null;
				urpUIRefPane= new UIRefPane();
				urpUIRefPane.setRefNodeName(alTemp.get(1).toString().trim());
				urpUIRefPane.setPK(m_alFreeItemValue.get(j).toString());
				m_alFreeItemReturnValueName.add(urpUIRefPane.getRefName());
			} else {
				m_alFreeItemReturnValueName.add(null);


			}

			continue;
		}
		//如果不是BD参照，那就是自定义参照
		//对自定义项，检查是否具有参照
		//先在Hashtable中查找
		if (m_hHashtable.containsKey(sOID)) {
			ArrayList alTemp= new ArrayList();
			alTemp= (ArrayList) m_hHashtable.get(sOID);
			m_alFreeItemNameIn.add(alTemp);
			continue;
		}

		//Hashtable中没有，在库中查找
		try {
			DefdefVO dvoDefdefVO= DefHelper.findByPrimaryKey(sOID);
			if ((null == dvoDefdefVO) || (null == dvoDefdefVO.getParentVO())) {
				//什么都没有
			    nc.vo.scm.pub.SCMEnv.out("Error:User def ref is not found."+sOID);
				continue;
			}

			if (dvoDefdefVO.getChildrenVO()==null || dvoDefdefVO.getChildrenVO().length == 0) {
			    nc.vo.scm.pub.SCMEnv.out("ERROR:User def ref DATA are not found."+sOID);
				//参照没有定义bd_defdoclist可供选择的数据。如定义了专业参照，但在bd_defdoclist中没有找到相关的数据。
				ArrayList alTemp= new ArrayList();
				alTemp.add(new Integer(ConsultType.NoConsult));
				alTemp.add(((DefdefHeaderVO) (dvoDefdefVO.getParentVO())).getDefname().toString().trim());
				//将整个数据类型传入
				alTemp.add((DefdefHeaderVO) (dvoDefdefVO.getParentVO()));
				m_alFreeItemNameIn.add(alTemp);

				//向Hashtable中加入
				m_hHashtable.put(sOID, alTemp);
				continue;
			}
		    nc.vo.scm.pub.SCMEnv.out("Hint:User def ref data are found."+sOID);

			//找到了，是 自定义项参照,顺序如下
//			IFreeItemParamSeqDef {
//		    public int RET_TYPE=0;		//类型 domain is ConsultType
//		    public int DEF_REF_NAME=1;	//名defname
//		    public int DEF_REF_PK=2;	//PK_defdef
//		    public int DEF_LEN=3;		//Lengthnum
//		    public int DEF_REF_PK_LIST=4;//pk_defdoclist
//			}
			ArrayList alTemp= new ArrayList();
			alTemp.add(new Integer(ConsultType.UserDefConsult));
			alTemp.add(((DefdefHeaderVO) (dvoDefdefVO.getParentVO())).getDefname().toString().trim());
			alTemp.add(((DefdefHeaderVO) (dvoDefdefVO.getParentVO())).getPrimaryKey().toString().trim());
			//传入数据长度
			alTemp.add(((DefdefHeaderVO) (dvoDefdefVO.getParentVO())).getLengthnum());
			//2005-01-17加了下面这一行， 王乃军，使用自定义项参照新MODEL，解决自定义项参照无公司条件问题。getPk_defdoclist
			alTemp.add(((DefdefHeaderVO) (dvoDefdefVO.getParentVO())).getPk_defdoclist());
			m_alFreeItemNameIn.add(alTemp);
			//向Hashtable中加入
			m_hHashtable.put(sOID, alTemp);

		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("数据通讯失败！");
			//handleException(e);
		}

	}
}
/**
 * 创建者：仲瑞庆
 * 功能：
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-5-9 下午 7:36)
 * 修改日期，修改人，修改原因，注释标志：
 * @return java.lang.String[][]
 */
protected String getDlgReturnFreeItem() {
	m_alFreeItemValue= null;
	m_alFreeItemValue= new ArrayList();
	m_alFreeItemReturnName= null;
	m_alFreeItemReturnName= new ArrayList();
	m_alFreeItemReturnValueName= null;
	m_alFreeItemReturnValueName= new ArrayList();

	String[][] dlgReturnFreeItem= getFreeItemDlg().getReturnFreeItem();
	int j= 0;
	for (int i= 0; i < m_alFreeItemID.size(); i++) {
		if ((null == m_alFreeItemID.get(i))
			|| (m_alFreeItemID.get(i).toString().trim().length() == 0)) {
			m_alFreeItemReturnName.add(null);
			m_alFreeItemValue.add(null);
			m_alFreeItemReturnValueName.add(null);
		} else {
			if (j >= dlgReturnFreeItem.length) {
				m_alFreeItemReturnName.add(null);
				m_alFreeItemValue.add(null);
				m_alFreeItemReturnValueName.add(null);
			} else {
				m_alFreeItemReturnName.add(dlgReturnFreeItem[j][0]);
				m_alFreeItemValue.add(dlgReturnFreeItem[j][2]);
				m_alFreeItemReturnValueName.add(dlgReturnFreeItem[j][2]);
				j++;
			}
		}
	}

	m_alFreeItemReturnValue= m_alFreeItemValue;

	//fromOIDtoName();
	if (null == m_alFreeItemReturnValueName)
		return "";

	String returnString= "";
	int k= 0;
	for (int i= 0; i < m_alFreeItemReturnValueName.size(); i++) {
		if ((dlgReturnFreeItem[k][0] == null)
			|| (dlgReturnFreeItem[k][1] == null)||(dlgReturnFreeItem[k][2] == null)
			|| (null == m_alFreeItemReturnValueName.get(i))) {
			continue;
		}
		returnString=
			returnString
				+ "["
				+ dlgReturnFreeItem[k][0]
				+ ":"
				+ m_alFreeItemReturnValueName.get(i).toString().trim()
				+ "]";
		k++;
		if (k > dlgReturnFreeItem.length - 1)
			break;
	}

	return returnString;
}
/**
 * 创建者：仲瑞庆
 * 功能：
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-5-9 下午 6:52)
 * 修改日期，修改人，修改原因，注释标志：
 * @return nc.ui.ic.pub.freeitem.FreeItemDlg
 */
protected FreeItemDlg getFreeItemDlg() {
	if (m_dlgFreeItemDlg == null) {
		m_dlgFreeItemDlg= new FreeItemDlg(this.getParent());
		m_dlgFreeItemDlg.setLocationRelativeTo(this);
		m_dlgFreeItemDlg.setInventoryName(m_sCode, m_sInventoryName, m_sSpec, m_sType);

		//由m_alFreeItemNameOID得到m_alFreeItemName和m_alFreeItemNameIn
		//fromOIDtoName();

		//压缩传入值
		//packFreeItemName();
//		FreeVO freeItemVOs=getFreeVO();

//		m_dlgFreeItemDlg.setFreeItemName(itemReturnNames);
		m_dlgFreeItemDlg.setFreeItemName(m_alFreeItemNameIn);
		

		//压缩传入值
		packFreeItemValue();
		m_dlgFreeItemDlg.setFreeItemValue(m_alFreeItemValueIn);

		m_dlgFreeItemDlg.addKeyListener(new IvjKeyListener());
		m_dlgFreeItemDlg.setVisible(true);
	}
	return m_dlgFreeItemDlg;
}
/**
 * 创建者：仲瑞庆
 * 功能：
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-5-9 下午 7:36)
 * 修改日期，修改人，修改原因，注释标志：
 * @return java.lang.String[][]
 */
public ArrayList getFreeItemName() {
	if ((null == m_alFreeItemReturnName) || (m_alFreeItemReturnName.size() == 0)) {
		return null;
	}
	boolean bAllNull= true;
	for (int i= 0; i < m_alFreeItemReturnName.size(); i++) {
		if (null != m_alFreeItemReturnName.get(i)) {
			bAllNull= false;
			break;
		}
	}
	if (bAllNull) {
		return null;
	}
	return m_alFreeItemReturnName;
}
/**
 * 创建者：仲瑞庆
 * 功能：
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-5-9 下午 7:36)
 * 修改日期，修改人，修改原因，注释标志：
 * @return java.lang.String[][]
 */
public ArrayList getFreeItemValue() {
	if ((null == m_alFreeItemReturnValueName)
		|| (m_alFreeItemReturnValueName.size() == 0)) {
		return null;
	}
	boolean bAllNull= true;
	for (int i= 0; i < m_alFreeItemReturnValueName.size(); i++) {
		if (null != m_alFreeItemReturnValueName.get(i)) {
			bAllNull= false;
			break;
		}
	}
	if (bAllNull) {
		return null;
	}
	return m_alFreeItemReturnValueName;




}

public ArrayList getFreeItemValueAll() {
	return m_alFreeItemReturnValue;
}
/**
 * 创建者：仲瑞庆
 * 功能：
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-5-9 下午 7:36)
 * 修改日期，修改人，修改原因，注释标志：
 * @return java.lang.String[][]
 */
public ArrayList getFreeItemValueID() {
	if ((null == m_alFreeItemReturnValue)
		|| (m_alFreeItemReturnValue.size() == 0)) {
		return null;
	}
	boolean bAllNull= true;
	for (int i= 0; i < m_alFreeItemReturnValue.size(); i++) {
		if (null != m_alFreeItemReturnValue.get(i)) {
			bAllNull= false;
			break;
		}
	}
	if (bAllNull) {
		return null;
	}
	return m_alFreeItemReturnValue;
}
/**
 * 创建者：仲瑞庆
 * 功能：
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-6-19 下午 3:27)
 * 修改日期，修改人，修改原因，注释标志：
 * @return nc.vo.ic.pub.bill.FreeVO
 */
public FreeVO getFreeVO() {
	FreeVO fvoFVO= new FreeVO();
	if ((m_alFreeItemID != null)
		&& (m_alFreeItemID.size() == 10)
		&& (m_alFreeItemReturnName != null)
		&& (m_alFreeItemReturnName.size() >= 10)
		&& (m_alFreeItemReturnValue != null)
		&& (m_alFreeItemReturnValue.size() >= 10)) {
		for (int i= 1; i <= 10; i++) {
			fvoFVO.setAttributeValue(
				"vfreeid" + Integer.toString(i).trim(),
				m_alFreeItemID.get(i - 1));
			fvoFVO.setAttributeValue(
				"vfreename" + Integer.toString(i).trim(),
				m_alFreeItemReturnName.get(i - 1));
			fvoFVO.setAttributeValue(
				"vfree" + Integer.toString(i).trim(),
				m_alFreeItemReturnValue.get(i - 1));
			//fvoFVO.setAttributeValue(
			//"vfreevalue" + Integer.toString(i).trim(),
			//m_alFreeItemReturnValueName.get(i - 1));
		}
	} else {
		for (int i= 1; i <= 10; i++) {
			fvoFVO.setAttributeValue("vfreeid" + Integer.toString(i).trim(), null);
			fvoFVO.setAttributeValue("vfreename" + Integer.toString(i).trim(), null);
			fvoFVO.setAttributeValue("vfree" + Integer.toString(i).trim(), null);
			//fvoFVO.setAttributeValue("vfreevalue" + Integer.toString(i).trim(), null);
		}
	}
	fvoFVO.setAttributeValue(
		"vfree0",
		this.getText() == null ? null : this.getText().trim());
	return fvoFVO;
}
/**
 * 创建者：仲瑞庆
 * 功能：
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-5-23 上午 11:34)
 * 修改日期，修改人，修改原因，注释标志：
 * @return boolean
 */
protected boolean isJustClicked() {
	return JustClicked;
}
/**
 * 创建者：仲瑞庆
 * 功能：
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-5-9 下午 6:59)
 * 修改日期，修改人，修改原因，注释标志：
 */
public void onButtonClicked() {
	int lengthOfInput= 0, m_iMinusOfString= 0;
	String returnString= "";
	String tempString= "";
	m_dlgFreeItemDlg= null;
	if (checkOpenFlag()) {
		if (getFreeItemDlg().getResult() == UIDialog.ID_OK) {

			//置入界面
			returnString= getDlgReturnFreeItem();
			nc.vo.scm.pub.SCMEnv.out(returnString);
			if (returnString.length() > (this.getMaxLength() - m_iMinusOfString)) {
				lengthOfInput= this.getMaxLength() - m_iMinusOfString;
			} else {
				lengthOfInput= returnString.length();
			};
			tempString= returnString.substring(0, lengthOfInput);

			boolean bIsEditable= isEditable();
			if (!bIsEditable) {
				setEditable(true);
			}
			setText(tempString);
			setEditable(bIsEditable);

			setJustClicked(true);
		} else {
			setJustClicked(false);
		}
	}
	getUITextField().setRequestFocusEnabled(true);
	getUITextField().grabFocus();
	return;

}
/**
 * 创建者：仲瑞庆
 * 功能：
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-5-9 下午 6:52)
 * 修改日期，修改人，修改原因，注释标志：
 * @return
 */
protected void packFreeItemName() {
	//压缩FreeItemValue
	m_alFreeItemNameIn= null;
	if (null == m_alFreeItemReturnName)
		return;

	m_alFreeItemNameIn= new ArrayList();
	for (int j= 0; j < m_alFreeItemReturnName.size(); j++) {
		if (null != m_alFreeItemReturnName.get(j)) {
			m_alFreeItemNameIn.add(m_alFreeItemReturnName.get(j));
		}
	}
}
/**
 * 创建者：仲瑞庆
 * 功能：删除m_alFreeItemValueIn中无用的element
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-5-9 下午 6:52)
 * 修改日期，修改人，修改原因，注释标志：
 * @return
 */
protected void packFreeItemValue() {
	//压缩FreeItemValue
	m_alFreeItemValueIn= null;
	if (null == m_alFreeItemValue)
		return;

	m_alFreeItemValueIn= new ArrayList();
	for (int j= 0; j < m_alFreeItemValue.size(); j++) {
		if (null != m_alFreeItemValue.get(j)) {
			m_alFreeItemValueIn.add(m_alFreeItemValue.get(j));
		}
	}
}
/**
 * 创建者：仲瑞庆
 * 
 * 功能：设置必要的参数
 * 
 * 参数：存货编码，存货名称，存货规格，存货型号，10个自由项名称，10个自由项值
 * 返回：
 * 例外：
 * 日期：(2001-5-10 上午 11:07)
 * 修改日期，修改人，修改原因，注释标志：
 * @param newM_sFreeItemName java.lang.String[]
 */
protected void setFreeItemParam(ArrayList alParam) {
	//传入为空或传入长度不对
	if ((alParam == null) || (alParam.size() != 24)) {
		m_sCode= "";
		m_sInventoryName= "";
		m_sSpec= "";
		m_sType= "";
		m_alFreeItemValue= null;
		//清除输出结果
		clearReturnAllValue();
		//清显示结果
		setText("");
	} else {
		//置入变量
		m_sCode= null;
		m_sInventoryName= null;
		m_sSpec= null;
		m_sType= null;
		if (null != alParam.get(0)) {
			m_sCode= alParam.get(0).toString().trim();
		}
		if (null != alParam.get(1)) {
			m_sInventoryName= alParam.get(1).toString().trim();
		}
		if (null != alParam.get(2)) {
			m_sSpec= alParam.get(2).toString().trim();
		}
		if (null != alParam.get(3)) {
			m_sType= alParam.get(3).toString().trim();
		}
		m_alFreeItemID= null;
		m_alFreeItemValue= null;
		m_alFreeItemID= new ArrayList();
		m_alFreeItemValue= new ArrayList();
		for (int i= 4; i < 14; i++) {
			if (null == alParam.get(i)) {
				m_alFreeItemID.add(null);
			} else {
				m_alFreeItemID.add(alParam.get(i).toString().trim());
			}
			if (null == alParam.get(i + 10)) {
				m_alFreeItemValue.add(null);
			} else {
				m_alFreeItemValue.add(alParam.get(i + 10).toString().trim());
			}
		}
		m_alFreeItemID= checkFreeItemNameOID();
		m_alFreeItemReturnName= null;
		m_alFreeItemNameIn= null;
		m_alFreeItemReturnValue= null;
		m_alFreeItemReturnValueName= null;
		fromOIDtoName();
	}
}
/**
 * 创建者：仲瑞庆
 * 功能：
 * 参数：存货编码，存货名称，存货规格，存货型号，10个自由项名称，10个自由项值
 * 返回：
 * 例外：
 * 日期：(2001-5-10 上午 11:07)
 * 修改日期，修改人，修改原因，注释标志：
 * @param newM_sFreeItemName java.lang.String[]
 */
public void setFreeItemParam(InvVO ivoVO) {
	m_sCode = "";
	m_sInventoryName = "";
	m_sSpec = "";
	m_sType = "";
	//传入为空或传入长度不对
	if ((ivoVO == null)
		|| (null == ivoVO.getAttributeValue("cinventoryid"))
		|| (ivoVO.getAttributeValue("cinventoryid").toString().trim().length() == 0)
		|| (null == ivoVO.getAttributeValue("isFreeItemMgt"))
		|| (ivoVO.getAttributeValue("isFreeItemMgt").toString().trim() == Integer.toString(0).trim())) {
		m_alFreeItemValue = null;
		//清除输出结果
		clearReturnAllValue();
		//清显示结果
		setText("");
	} else {
		//置入变量
		if (null != ivoVO.getAttributeValue("cinventorycode")) {
			m_sCode = ivoVO.getCinventorycode().trim();
		}
		if (null != ivoVO.getAttributeValue("invname")) {
			m_sInventoryName = ivoVO.getInvname().trim();
		}
		if (null != ivoVO.getAttributeValue("invspec")) {
			m_sSpec = ivoVO.getInvspec().toString().trim();
		}
		if (null != ivoVO.getAttributeValue("invtype")) {
			m_sType = ivoVO.getInvtype().toString().trim();
		}
		m_alFreeItemID = null;
		m_alFreeItemValue = null;
		m_alFreeItemNameIn = null;
		m_alFreeItemReturnName = null;
		m_alFreeItemReturnName = new ArrayList();
		m_alFreeItemNameIn = new ArrayList();
		m_alFreeItemID = new ArrayList();
		m_alFreeItemValue = new ArrayList();
		int iIDCount = 0, iNameCount = 0;
		for (int i = 1; i <= 10; i++) {
			if (null == ivoVO.getAttributeValue("vfreeid" + Integer.toString(i).trim())) {
				m_alFreeItemID.add(null);
			} else {
				m_alFreeItemID.add(
					ivoVO.getAttributeValue("vfreeid" + Integer.toString(i).trim()).toString().trim());
				iIDCount++;
			}
			if (null == ivoVO.getAttributeValue("vfreename" + Integer.toString(i).trim())) {
				m_alFreeItemReturnName.add(null);
			} else {
				m_alFreeItemReturnName.add(
					ivoVO.getAttributeValue("vfreename" + Integer.toString(i).trim()).toString().trim());
				iNameCount++;
			}
			if (null == ivoVO.getAttributeValue("vfree" + Integer.toString(i).trim())) {
				m_alFreeItemValue.add(null);
			} else {
				m_alFreeItemValue.add(
					ivoVO.getAttributeValue("vfree" + Integer.toString(i).trim()).toString().trim());
			}
		}

    //自由项值名称
    m_alFreeItemValueIn = m_alFreeItemValue;
    m_alFreeItemReturnValue = m_alFreeItemValue;
    m_alFreeItemReturnValueName = m_alFreeItemValue;
    
		if (null == ivoVO.getAttributeValue("vfree0") || ivoVO.getAttributeValue("vfree0").toString().trim().length()==0) {
			this.setText(null);
		} else {
			this.setText(ivoVO.getAttributeValue("vfree0").toString().trim());
		}
    
		//发生错误匹配
		if (iNameCount != iIDCount) {
			m_sCode = "";
			m_sInventoryName = "";
			m_sSpec = "";
			m_sType = "";
			//清除输出结果
			clearReturnAllValue();
			//清显示结果
			setText("");

			nc.ui.pub.beans.MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("scmcommon","UPPSCMCommon-000059")/*@res "错误"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("scmpub","UPPscmpub-000259")/*@res "自由项错误匹配，请检查存货档案中对自由项的定义，是否定义的自定义项已被删除。"*/);
		}

		fromOIDtoName();
	}
}
/**
 * 创建者：仲瑞庆
 * 功能：设置固有的（BD提供的）参照。
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-5-17 上午 10:34)
 * 修改日期，修改人，修改原因，注释标志：
 * @return
 */
protected void setInherentConsult() {
	/*
	人员档案
	人员档案HR
	部门档案
	公司目录(集团)S
	公司目录
	公司目录(集团)
	客商基本档案
	客商档案
	客户档案
	供应商档案
	客商辅助核算
	客户辅助核算
	供应商辅助核算
	客商档案包含冻结
	客户档案包含冻结
	供应商档案包含冻结
	存货基本档案
	存货档案
	物料档案
	会计科目
	人员类别
	存货分类
	凭证类别
	收付款协议
	结算方式
	开户银行
	开户银行1
	开户银行2
	开户银行3
	仓库档案
	发运方式
	收发类别
	地区分类
	常用摘要
	公用自定义项
	收支项目
	付方收支项目
	收方收支项目
	计量档案
	税目税率
	项目类型
	项目档案
	项目管理档案
	项目管理档案表格
	货位档案
	外币档案
	客商发货地址
	自定义项档案
	成套件
	会计期间
	业务类型
	库存组织
	权限操作员
	操作员
	采购组织
	帐龄区间
	单据类型
	帐户
	收入项目
	支出项目
	客商档案(并集)
	权限公司目录
	权限公司目录(集团)
	销售组织
	现金流量项目
	自定义项档案列表
	项目分类
	票据类型
	票据类型1
	票据类型2
	票据类型3
	票据类型4
	票据类型5
	账户档案
	基础档案资源列表
	*/
	/*
	String[] sTemp;
	sTemp=new String[]{"0","人员档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"1","人员档案HR"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"2","部门档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"3","公司目录(集团)S"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"4","公司目录"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"5","公司目录(集团)"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"6","客商基本档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"7","客商档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"8","客户档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"9","供应商档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"10","客商辅助核算"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"11","客户辅助核算"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"12","供应商辅助核算"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"13","客商档案包含冻结"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"14","客户档案包含冻结"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"15","供应商档案包含冻结"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"16","存货基本档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"17","存货档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"18","物料档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"19","会计科目"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"20","人员类别"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"21","存货分类"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"22","凭证类别"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"23","收付款协议"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"24","结算方式"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"25","开户银行"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"26","开户银行1"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"27","开户银行2"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"28","开户银行3"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"29","仓库档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"30","发运方式"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"31","收发类别"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"32","地区分类"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"33","常用摘要"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"34","公用自定义项"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"35","收支项目"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"36","付方收支项目"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"37","收方收支项目"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"38","计量档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"39","税目税率"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"40","项目类型"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"41","项目档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"42","项目管理档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"43","项目管理档案表格"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"44","货位档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"45","外币档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"46","客商发货地址"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"47","自定义项档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"48","成套件"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"49","会计期间"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"50","业务类型"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"51","库存组织"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"52","权限操作员"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"53","操作员"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"54","采购组织"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"55","帐龄区间"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"56","单据类型"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"57","帐户"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"58","收入项目"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"59","支出项目"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"60","客商档案(并集)"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"61","权限公司目录"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"62","权限公司目录(集团)"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"63","销售组织"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"64","现金流量项目"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"65","自定义项档案列表"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"66","项目分类"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"67","票据类型"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"68","票据类型1"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"69","票据类型2"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"70","票据类型3"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"71","票据类型4"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"72","票据类型5"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"73","账户档案"};
	m_alInherentConsult.add(sTemp);
	sTemp=new String[]{"74","基础档案资源列表"};
	m_alInherentConsult.add(sTemp);
	*/
}
/**
 * 创建者：仲瑞庆
 * 功能：
 * 参数：
 * 返回：
 * 例外：
 * 日期：(2001-5-23 上午 11:34)
 * 修改日期，修改人，修改原因，注释标志：
 * @param newJustClicked boolean
 */
protected void setJustClicked(boolean newJustClicked) {
	JustClicked = newJustClicked;
}

	class IvjKeyListener extends java.awt.event.KeyAdapter {
		public void keyPressed(java.awt.event.KeyEvent e)  {
			if (e.getKeyCode()==java.awt.event.KeyEvent.VK_ESCAPE){
				FreeItemRefPane.this.getFreeItemDlg().closeCancel();
			}
		}
	}
}