package nc.vo.jyglgt.pub;
	
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
	
/**
 * <b> �ڴ˴���Ҫ��������Ĺ��� </b>
 * <p>
 *     �ڴ˴���Ӵ����������Ϣ
 * </p>
 * ��������:2012-08-25 16:41:36
 * @author ����������
 * @version NCPrj 1.0
 */
@SuppressWarnings("serial")
public class BillMakeVO extends SuperVO {
	private String vdef5;   //�Զ���5
	private String vdef9;   //�Զ���9
	private String vdef1;   //�Զ���1
	private String pk_modifier;   //�޸���
	private UFDate vapprovedate;   //�������
	private String vdef8;   //�Զ���8
	private String btablename;   //�ӱ����
	private String vmemo;   //��ע
	private String billtype;   //��������
	private String vapproveid;   //�����
	private String hvoname;   //����VO
	private String hpk;   //��������
	private UFDouble secleve;   //�ܼ�
	private String vdef7;   //�Զ���7
	private String vdef10;   //�Զ���10
	private String pk_operator;   //�Ƶ���
	private UFDateTime ts;   //ts
	private String pk_billmake;   //����
	private String htablename;   //�������
	private String vdef3;   //�Զ���3
	private String vdef6;   //�Զ���6
	private String uiclass;   //UI����
	private String modelcode;   //ģ�����
	private Integer vbillstatus;   //����״̬
	private String cancatfiel;   //���������ֶ�
	private UFDate modifydate;   //�޸�����
	private String vdef4;   //�Զ���4
	private String vdef2;   //�Զ���2
	private String bvoname;   //�ֱ�VO
	private String name;   //��������
	private UFDate dmakedate;   //�Ƶ�����
	private String pk_busitype;   //ҵ������
	private String vbillcode;   //���ݺ�
	private Integer dr;   //dr
	private String nodecode;   //�����
	private String bpk;   //�ӱ�����
	private String pk_corp;   //pk_corp
	private String bvoname2;   //�ֱ�VO2
	private String bvoname3;   //�ֱ�VO3
	private String bvoname4;   //�ֱ�VO4
	private String bvoname5;   //�ֱ�VO5
	private String bvoname6;   //�ֱ�VO6
	private String btablename2;   //�ӱ����2
	private String bpk2;   //�ӱ�����2
	private String bname2;   //�ӱ�ҳǩ��2
	private String btablename3;   //�ӱ����3
	private String bpk3;   //�ӱ�����3
	private String bname3;   //�ӱ�ҳǩ��3
	private String btablename4;   //�ӱ����4
	private String bpk4;   //�ӱ�����4
	private String bname4;   //�ӱ�ҳǩ��4
	private String btablename5;   //�ӱ����5
	private String bpk5;   //�ӱ�����5
	private String bname5;   //�ӱ�ҳǩ��5
	private String btablename6;   //�ӱ����6
	private String bpk6;   //�ӱ�����6
	private String bname6;   //�ӱ�ҳǩ��6
	private String vclass;   //����
	private String bname;   //�ӱ�ҳǩ��
	
	public static final String PK_CORP = "pk_corp";
	public static final String BILLTYPE = "billtype";
	public static final String VDEF9 = "vdef9";
	public static final String VBILLCODE = "vbillcode";
	public static final String VDEF1 = "vdef1";
	public static final String VDEF8 = "vdef8";
	public static final String VDEF10 = "vdef10";
	public static final String SECLEVE = "secleve";
	public static final String MODIFYDATE = "modifydate";
	public static final String BVONAME = "bvoname";
	public static final String PK_BUSITYPE = "pk_busitype";
	public static final String VAPPROVEID = "vapproveid";
	public static final String HVONAME = "hvoname";
	public static final String CANCATFIEL = "cancatfiel";
	public static final String PK_BILLMAKE = "pk_billmake";
	public static final String VDEF7 = "vdef7";
	public static final String PK_MODIFIER = "pk_modifier";
	public static final String NODECODE = "nodecode";
	public static final String VDEF2 = "vdef2";
	public static final String VDEF5 = "vdef5";
	public static final String VMEMO = "vmemo";
	public static final String VBILLSTATUS = "vbillstatus";
	public static final String VAPPROVEDATE = "vapprovedate";
	public static final String DMAKEDATE = "dmakedate";
	public static final String VDEF3 = "vdef3";
	public static final String VDEF6 = "vdef6";
	public static final String VDEF4 = "vdef4";
	public static final String PK_OPERATOR = "pk_operator";
			
	public String getBvoname2() {
		return bvoname2;
	}
	public void setBvoname2(String bvoname2) {
		this.bvoname2 = bvoname2;
	}
	public String getBvoname3() {
		return bvoname3;
	}
	public void setBvoname3(String bvoname3) {
		this.bvoname3 = bvoname3;
	}
	public String getBvoname4() {
		return bvoname4;
	}
	public void setBvoname4(String bvoname4) {
		this.bvoname4 = bvoname4;
	}
	public String getBvoname5() {
		return bvoname5;
	}
	public void setBvoname5(String bvoname5) {
		this.bvoname5 = bvoname5;
	}
	public String getBvoname6() {
		return bvoname6;
	}
	public void setBvoname6(String bvoname6) {
		this.bvoname6 = bvoname6;
	}
	public String getBtablename2() {
		return btablename2;
	}
	public void setBtablename2(String btablename2) {
		this.btablename2 = btablename2;
	}
	public String getBpk2() {
		return bpk2;
	}
	public void setBpk2(String bpk2) {
		this.bpk2 = bpk2;
	}
	public String getBname2() {
		return bname2;
	}
	public void setBname2(String bname2) {
		this.bname2 = bname2;
	}
	public String getBtablename3() {
		return btablename3;
	}
	public void setBtablename3(String btablename3) {
		this.btablename3 = btablename3;
	}
	public String getBpk3() {
		return bpk3;
	}
	public void setBpk3(String bpk3) {
		this.bpk3 = bpk3;
	}
	public String getBname3() {
		return bname3;
	}
	public void setBname3(String bname3) {
		this.bname3 = bname3;
	}
	public String getBtablename4() {
		return btablename4;
	}
	public void setBtablename4(String btablename4) {
		this.btablename4 = btablename4;
	}
	public String getBpk4() {
		return bpk4;
	}
	public void setBpk4(String bpk4) {
		this.bpk4 = bpk4;
	}
	public String getBname4() {
		return bname4;
	}
	public void setBname4(String bname4) {
		this.bname4 = bname4;
	}
	public String getBtablename5() {
		return btablename5;
	}
	public void setBtablename5(String btablename5) {
		this.btablename5 = btablename5;
	}
	public String getBpk5() {
		return bpk5;
	}
	public void setBpk5(String bpk5) {
		this.bpk5 = bpk5;
	}
	public String getBname5() {
		return bname5;
	}
	public void setBname5(String bname5) {
		this.bname5 = bname5;
	}
	public String getBtablename6() {
		return btablename6;
	}
	public void setBtablename6(String btablename6) {
		this.btablename6 = btablename6;
	}
	public String getBpk6() {
		return bpk6;
	}
	public void setBpk6(String bpk6) {
		this.bpk6 = bpk6;
	}
	public String getBname6() {
		return bname6;
	}
	public void setBname6(String bname6) {
		this.bname6 = bname6;
	}
	public String getVclass() {
		return vclass;
	}
	public void setVclass(String vclass) {
		this.vclass = vclass;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public static String getPK_CORP() {
		return PK_CORP;
	}
	public static String getBILLTYPE() {
		return BILLTYPE;
	}
	public static String getVDEF9() {
		return VDEF9;
	}
	public static String getVBILLCODE() {
		return VBILLCODE;
	}
	public static String getVDEF1() {
		return VDEF1;
	}
	public static String getVDEF8() {
		return VDEF8;
	}
	public static String getVDEF10() {
		return VDEF10;
	}
	public static String getSECLEVE() {
		return SECLEVE;
	}
	public static String getMODIFYDATE() {
		return MODIFYDATE;
	}
	public static String getBVONAME() {
		return BVONAME;
	}
	public static String getPK_BUSITYPE() {
		return PK_BUSITYPE;
	}
	public static String getVAPPROVEID() {
		return VAPPROVEID;
	}
	public static String getHVONAME() {
		return HVONAME;
	}
	public static String getCANCATFIEL() {
		return CANCATFIEL;
	}
	public static String getPK_BILLMAKE() {
		return PK_BILLMAKE;
	}
	public static String getVDEF7() {
		return VDEF7;
	}
	public static String getPK_MODIFIER() {
		return PK_MODIFIER;
	}
	public static String getNODECODE() {
		return NODECODE;
	}
	public static String getVDEF2() {
		return VDEF2;
	}
	public static String getVDEF5() {
		return VDEF5;
	}
	public static String getVMEMO() {
		return VMEMO;
	}
	public static String getVBILLSTATUS() {
		return VBILLSTATUS;
	}
	public static String getVAPPROVEDATE() {
		return VAPPROVEDATE;
	}
	public static String getDMAKEDATE() {
		return DMAKEDATE;
	}
	public static String getVDEF3() {
		return VDEF3;
	}
	public static String getVDEF6() {
		return VDEF6;
	}
	public static String getVDEF4() {
		return VDEF4;
	}
	public static String getPK_OPERATOR() {
		return PK_OPERATOR;
	}
	public String getModelcode() {
		return modelcode;
	}
	public void setModelcode(String modelcode) {
		this.modelcode = modelcode;
	}
	public String getUiclass() {
		return uiclass;
	}
	public void setUiclass(String uiclass) {
		this.uiclass = uiclass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHtablename() {
		return htablename;
	}
	public void setHtablename(String htablename) {
		this.htablename = htablename;
	}
	public String getBtablename() {
		return btablename;
	}
	public void setBtablename(String btablename) {
		this.btablename = btablename;
	}
	public String getHpk() {
		return hpk;
	}
	public void setHpk(String hpk) {
		this.hpk = hpk;
	}
	public String getBpk() {
		return bpk;
	}
	public void setBpk(String bpk) {
		this.bpk = bpk;
	}
	/**
	 * ����pk_corp��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getPk_corp () {
		return pk_corp;
	}   
	/**
	 * ����pk_corp��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newPk_corp String
	 */
	public void setPk_corp (String newPk_corp ) {
	 	this.pk_corp = newPk_corp;
	} 	  
	/**
	 * ����ts��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return UFDateTime
	 */
	public UFDateTime getTs () {
		return ts;
	}   
	/**
	 * ����ts��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newTs UFDateTime
	 */
	public void setTs (UFDateTime newTs ) {
	 	this.ts = newTs;
	} 	  
	/**
	 * ����billtype��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getBilltype () {
		return billtype;
	}   
	/**
	 * ����billtype��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newBilltype String
	 */
	public void setBilltype (String newBilltype ) {
	 	this.billtype = newBilltype;
	} 	  
	/**
	 * ����vdef9��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getVdef9 () {
		return vdef9;
	}   
	/**
	 * ����vdef9��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newVdef9 String
	 */
	public void setVdef9 (String newVdef9 ) {
	 	this.vdef9 = newVdef9;
	} 	  
	/**
	 * ����vbillcode��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getVbillcode () {
		return vbillcode;
	}   
	/**
	 * ����vbillcode��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newVbillcode String
	 */
	public void setVbillcode (String newVbillcode ) {
	 	this.vbillcode = newVbillcode;
	} 	  
	/**
	 * ����vdef1��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getVdef1 () {
		return vdef1;
	}   
	/**
	 * ����vdef1��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newVdef1 String
	 */
	public void setVdef1 (String newVdef1 ) {
	 	this.vdef1 = newVdef1;
	} 	  
	/**
	 * ����vdef8��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getVdef8 () {
		return vdef8;
	}   
	/**
	 * ����vdef8��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newVdef8 String
	 */
	public void setVdef8 (String newVdef8 ) {
	 	this.vdef8 = newVdef8;
	} 	  
	/**
	 * ����vdef10��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getVdef10 () {
		return vdef10;
	}   
	/**
	 * ����vdef10��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newVdef10 String
	 */
	public void setVdef10 (String newVdef10 ) {
	 	this.vdef10 = newVdef10;
	} 	  
	/**
	 * ����secleve��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return UFDouble
	 */
	public UFDouble getSecleve () {
		return secleve;
	}   
	/**
	 * ����secleve��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newSecleve UFDouble
	 */
	public void setSecleve (UFDouble newSecleve ) {
	 	this.secleve = newSecleve;
	} 	  
	/**
	 * ����modifydate��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return UFDate
	 */
	public UFDate getModifydate () {
		return modifydate;
	}   
	/**
	 * ����modifydate��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newModifydate UFDate
	 */
	public void setModifydate (UFDate newModifydate ) {
	 	this.modifydate = newModifydate;
	} 	  
	/**
	 * ����bvoname��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getBvoname () {
		return bvoname;
	}   
	/**
	 * ����bvoname��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newBvoname String
	 */
	public void setBvoname (String newBvoname ) {
	 	this.bvoname = newBvoname;
	} 	  
	/**
	 * ����pk_busitype��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getPk_busitype () {
		return pk_busitype;
	}   
	/**
	 * ����pk_busitype��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newPk_busitype String
	 */
	public void setPk_busitype (String newPk_busitype ) {
	 	this.pk_busitype = newPk_busitype;
	} 	  
	/**
	 * ����vapproveid��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getVapproveid () {
		return vapproveid;
	}   
	/**
	 * ����vapproveid��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newVapproveid String
	 */
	public void setVapproveid (String newVapproveid ) {
	 	this.vapproveid = newVapproveid;
	} 	  
	/**
	 * ����hvoname��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getHvoname () {
		return hvoname;
	}   
	/**
	 * ����hvoname��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newHvoname String
	 */
	public void setHvoname (String newHvoname ) {
	 	this.hvoname = newHvoname;
	} 	  
	/**
	 * ����cancatfiel��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getCancatfiel () {
		return cancatfiel;
	}   
	/**
	 * ����cancatfiel��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newCancatfiel String
	 */
	public void setCancatfiel (String newCancatfiel ) {
	 	this.cancatfiel = newCancatfiel;
	} 	  
	/**
	 * ����pk_billmake��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getPk_billmake () {
		return pk_billmake;
	}   
	/**
	 * ����pk_billmake��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newPk_billmake String
	 */
	public void setPk_billmake (String newPk_billmake ) {
	 	this.pk_billmake = newPk_billmake;
	} 	  
	/**
	 * ����vdef7��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getVdef7 () {
		return vdef7;
	}   
	/**
	 * ����vdef7��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newVdef7 String
	 */
	public void setVdef7 (String newVdef7 ) {
	 	this.vdef7 = newVdef7;
	} 	  
	/**
	 * ����pk_modifier��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getPk_modifier () {
		return pk_modifier;
	}   
	/**
	 * ����pk_modifier��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newPk_modifier String
	 */
	public void setPk_modifier (String newPk_modifier ) {
	 	this.pk_modifier = newPk_modifier;
	} 	  
	/**
	 * ����nodecode��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getNodecode () {
		return nodecode;
	}   
	/**
	 * ����nodecode��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newNodecode String
	 */
	public void setNodecode (String newNodecode ) {
	 	this.nodecode = newNodecode;
	} 	  
	/**
	 * ����vdef2��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getVdef2 () {
		return vdef2;
	}   
	/**
	 * ����vdef2��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newVdef2 String
	 */
	public void setVdef2 (String newVdef2 ) {
	 	this.vdef2 = newVdef2;
	} 	  
	/**
	 * ����vdef5��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getVdef5 () {
		return vdef5;
	}   
	/**
	 * ����vdef5��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newVdef5 String
	 */
	public void setVdef5 (String newVdef5 ) {
	 	this.vdef5 = newVdef5;
	} 	  
	/**
	 * ����vmemo��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getVmemo () {
		return vmemo;
	}   
	/**
	 * ����vmemo��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newVmemo String
	 */
	public void setVmemo (String newVmemo ) {
	 	this.vmemo = newVmemo;
	} 	  

	
	/**
	 * ����vapprovedate��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return UFDate
	 */
	public UFDate getVapprovedate () {
		return vapprovedate;
	}   
	public Integer getVbillstatus() {
		return vbillstatus;
	}
	public void setVbillstatus(Integer vbillstatus) {
		this.vbillstatus = vbillstatus;
	}
	/**
	 * ����vapprovedate��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newVapprovedate UFDate
	 */
	public void setVapprovedate (UFDate newVapprovedate ) {
	 	this.vapprovedate = newVapprovedate;
	} 	  
	/**
	 * ����dmakedate��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return UFDate
	 */
	public UFDate getDmakedate () {
		return dmakedate;
	}   
	/**
	 * ����dmakedate��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newDmakedate UFDate
	 */
	public void setDmakedate (UFDate newDmakedate ) {
	 	this.dmakedate = newDmakedate;
	} 	  
	/**
	 * ����vdef3��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getVdef3 () {
		return vdef3;
	}   
	/**
	 * ����vdef3��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newVdef3 String
	 */
	public void setVdef3 (String newVdef3 ) {
	 	this.vdef3 = newVdef3;
	} 	  
	/**
	 * ����vdef6��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getVdef6 () {
		return vdef6;
	}   
	/**
	 * ����vdef6��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newVdef6 String
	 */
	public void setVdef6 (String newVdef6 ) {
	 	this.vdef6 = newVdef6;
	} 	  
	
	public Integer getDr() {
		return dr;
	}
	public void setDr(Integer dr) {
		this.dr = dr;
	}
	/**
	 * ����vdef4��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getVdef4 () {
		return vdef4;
	}   
	/**
	 * ����vdef4��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newVdef4 String
	 */
	public void setVdef4 (String newVdef4 ) {
	 	this.vdef4 = newVdef4;
	} 	  
	/**
	 * ����pk_operator��Getter����.
	 * ��������:2012-08-25 16:41:36
	 * @return String
	 */
	public String getPk_operator () {
		return pk_operator;
	}   
	/**
	 * ����pk_operator��Setter����.
	 * ��������:2012-08-25 16:41:36
	 * @param newPk_operator String
	 */
	public void setPk_operator (String newPk_operator ) {
	 	this.pk_operator = newPk_operator;
	} 	  
 
	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:2012-08-25 16:41:36
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:2012-08-25 16:41:36
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_billmake";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:2012-08-25 16:41:36
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "csh_billmake";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:2012-08-25 16:41:36
	  */
     public BillMakeVO() {
		super();	
	}    
     
//     alter table CSH_BILLMAKE add vclass VARCHAR2(200);
//     alter table CSH_BILLMAKE add bvoname2 VARCHAR2(200);
//     alter table CSH_BILLMAKE add bvoname3 VARCHAR2(200);
//     alter table CSH_BILLMAKE add bvoname4 VARCHAR2(200);
//     alter table CSH_BILLMAKE add bvoname5 VARCHAR2(200);
//     alter table CSH_BILLMAKE add bvoname6 VARCHAR2(200);
//     alter table CSH_BILLMAKE add btablename2 VARCHAR2(200);
//     alter table CSH_BILLMAKE add btablename3 VARCHAR2(200);
//     alter table CSH_BILLMAKE add btablename4 VARCHAR2(200);
//     alter table CSH_BILLMAKE add btablename5 VARCHAR2(200);
//     alter table CSH_BILLMAKE add btablename6 VARCHAR2(200);
//     alter table CSH_BILLMAKE add bpk2 VARCHAR2(200);
//     alter table CSH_BILLMAKE add bpk3 VARCHAR2(200);
//     alter table CSH_BILLMAKE add bpk4 VARCHAR2(200);
//     alter table CSH_BILLMAKE add bpk5 VARCHAR2(200);
//     alter table CSH_BILLMAKE add bpk6 VARCHAR2(200);
//     alter table CSH_BILLMAKE add bname2 VARCHAR2(200);
//     alter table CSH_BILLMAKE add bname3 VARCHAR2(200);
//     alter table CSH_BILLMAKE add bname4 VARCHAR2(200);
//     alter table CSH_BILLMAKE add bname5 VARCHAR2(200);
//     alter table CSH_BILLMAKE add bname6 VARCHAR2(200);
     
} 
