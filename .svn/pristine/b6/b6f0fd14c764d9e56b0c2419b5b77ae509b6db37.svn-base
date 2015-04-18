package nc.work.gdt;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.itf.jyglgt.pub.IJyglgtItf;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.ui.jyglgt.billcard.AbstractEventHandler;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.BillCardUI;
import nc.vo.jyglgt.pub.BillMakeVO;
import nc.vo.jyglgt.pub.DatadictVO;
import nc.vo.jyglgt.pub.GDTBillcodeRuleVO;
import nc.vo.jyglgt.pub.MyBillTempBVO;
import nc.vo.jyglgt.pub.MyBillTempTVO;
import nc.vo.jyglgt.pub.MyBillTempVO;
import nc.vo.jyglgt.pub.Toolkits.DataType;
import nc.vo.jyglgt.pub.Toolkits.DatadictProcess;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.billtype.BilltypeVO;
import nc.vo.pub.ddc.datadict.FieldDef;
import nc.vo.pub.ddc.datadict.FieldDefList;
import nc.vo.pub.ddc.datadict.TableDef;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.sm.funcreg.FuncModuleJudger;
import nc.vo.sm.funcreg.FuncRegisterVO;
/**
 * 功能：卡产品档案
 * 日期：2010-08-03
 */

public class ClientEventHandler extends AbstractEventHandler{
	
	private final String EXP_PATH = "c:/autoCreateBill/";//生成代码路径
	
	int tabindex = 0;//多子表页签排序
	
	int maxbtableindex = 0;//子表最大个数
	
	private List<MyBillTempTVO> tvolist = new ArrayList<MyBillTempTVO>();//单据模板所有页签信息_t
	
	private List<MyBillTempBVO> bvolist = new ArrayList<MyBillTempBVO>();//单据模板所有项目信息_b
	
	private IUAPQueryBS qbs = null;
	
	private IUAPQueryBS getQbs(){
		if(qbs == null){
			qbs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		}
		return qbs;
	}
	
	
	public ClientEventHandler(BillCardUI billUI, ICardController control) {
		super(billUI, control);
	} 
	
	/**
	 * @param isthrow
	 * @param msg
	 * @throws BusinessException
	 * 有条件的抛出异常
	 */
	private void warpException(boolean isthrow,String msg) throws BusinessException{
		if(isthrow){
			throw new BusinessException(msg);
		}
	}
	
	/**
	 * @return
	 * 是否只生成代码
	 */
	private boolean isOnelyCode(){
		Object flag = getBillCardPanelWrapper().getBillCardPanel().getHeadItem("onelycode").getValueObject();
		boolean isonely = flag == null?false:new UFBoolean(flag.toString()).booleanValue();
		return isonely;
	}

	public void onBoQuery() throws Exception { 
    	super.onBoQuery();
    }
	
	public void onBoSave() throws Exception {
		
	}
	//删除
	public void onBoDelete() throws Exception{
		super.onBoDelete();
		onBoRefresh();
	}
	
	public void onBoElse(int intBtn)throws Exception {
		bvolist.clear();
		tvolist.clear();
		tabindex = 0;
		maxbtableindex = ((ClientUI)getBillUI()).maxbtableindex;
		if(intBtn == 800){
			//通过结点号生成 功能注册 单据类型 单据号规则 单据模板 VO 代码
			getBillCardPanelWrapper().getBillCardPanel().getBillData().dataNotNullValidate();
			BillMakeVO hvo = (BillMakeVO) getBillCardPanelWrapper().getBillCardPanel().getBillData().getHeaderValueVO(getUIController().getBillVoName()[1]);
			//1.功能注册VO
			FuncRegisterVO fvo = getFunctionVO(hvo);
			//2.单据类型VO
			BilltypeVO btvo = createBillType(hvo);
			//3.单据号规则VO:单据类型+年月日+4位流水号 
			GDTBillcodeRuleVO trvo = createBillTypeRule(hvo);
			//4.生成单据模板主VO
			MyBillTempVO mvo = creatTemplet();
			/** 动作脚本 跟 VO对照待以后完善 **/
			//5.创建代码VO
			String type = getHeadValue("vclass").toString();
			if(type.equals("单表头管理型")){
				creatHVO();
			}else if(type.equals("单表体管理型")){
				creatBVO(getHeadValue("bname").toString(),getHeadValue("btablename").toString(),getHeadValue("bvoname").toString());
			}else if(type.equals("管理型")){
				creatHVO();
				creatBVO(getHeadValue("bname").toString(),getHeadValue("btablename").toString(),getHeadValue("bvoname").toString());
			}else if(type.equals("多子表")){
				creatHVO();
				for (int i = 1; i <= maxbtableindex; i++) {
					String k = "";
					if(i != 1){
						k = i + "";
					}
					Object bname = getHeadValue("bname"+k);
					Object btablename = getHeadValue("btablename"+k);
					Object bvoname = getHeadValue("bvoname"+k);
					if(!Toolkits.isEmpty(bname)&&!Toolkits.isEmpty(btablename)&&!Toolkits.isEmpty(bvoname)){
						creatBVO(bname.toString(),btablename.toString(),bvoname.toString());
					}
				}
				//创建多子表的聚合VO
				createAggVO();
			}
			//6.创建UI EH CTRL代码
			creatCode();
			if(!isOnelyCode()){//非只生成代码时,脚本要一并执行了
				//脚本的统一放到后台一个事务里去执行
				insertIntoDataBase(btvo,trvo,mvo,fvo,hvo);
			}
			getBillUI().showWarningMessage("生成成功,代码生成目录： '"+EXP_PATH+"'");
		}
	}
	
	/**
	 * @param hvo
	 * 自动生成功能注册
	 * @throws BusinessException 
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	private FuncRegisterVO getFunctionVO(BillMakeVO hvo) throws BusinessException {
		FuncRegisterVO vo = new FuncRegisterVO();
		//功能名称
		vo.setFunName(hvo.getName());
		String funCode = hvo.getNodecode();
		if(funCode.length()<2){
			warpException(true,"结点编号必须大于2位,并且缩减两位后,的虚父结点必须存在!");
		}
		String qfselfsql = " select 1 from sm_funcregister a where a.fun_code='"+funCode+"' and nvl(dr,0)=0 ";
		List slist = (List) getQbs().executeQuery(qfselfsql, new MapListProcessor());
		if(!Toolkits.isEmpty(slist)){
			warpException(true,"当前结点号已存在,请修改!");
		}
		
		String fathercode = funCode.substring(0, funCode.length() - 2);
		
		String qfathersql = " select * from sm_funcregister a where a.fun_code='"+fathercode+"' and nvl(dr,0)=0 ";
		List flist = (List) getQbs().executeQuery(qfathersql, new MapListProcessor());
		
		if(Toolkits.isEmpty(flist)){
			warpException(true,"结点编号缩减两位后,未找到其虚父结点! 父结点为："+fathercode);
		}
		
		//获得模块类型
		char cModuleType = FuncModuleJudger.getModuleType(funCode);
		vo.setFunCode(funCode);
		//属性类型
		vo.setGroupFlag(new Integer(0));//集团完全控制,公司查看
		//功能属性
		//int index = getComboBoxProperty().getSelectedIndex();
		vo.setFunProperty(new Integer(0));//可执行功能结点
		//对应类名或控件名称
		vo.setClassName(hvo.getUiclass());
		//对应帮助文件名
		vo.setHelpName("");
		//resid
		vo.setResid("");
		//禁用标志
		vo.setForbidFlag(new Integer("0"));
		//参数显示标志
		vo.setHasPara(new UFBoolean(false));
		//NeedLog
		vo.setisneedbuttonlog(new UFBoolean(false));
		vo.setIsbuttonpower(new UFBoolean(false));
		//功能描述
		vo.setFunDesc("");
			///
//		FuncRegisterVO voParent = fathernode;
		Object level = ((Map)flist.get(0)).get("fun_level");
		int paLevel = level==null?0:Integer.valueOf(level.toString());
		vo.setFunLevel(new Integer(paLevel + 1)); //节点级次
		Object property = ((Map)flist.get(0)).get("fun_property");
		@SuppressWarnings("unused")
		int iParentProp = property==null?0:Integer.valueOf(property.toString());
		String paID = ((Map)flist.get(0)).get("cfunid").toString();
		vo.setParentId(paID); //父节点ID
		Object subsid = ((Map)flist.get(0)).get("subsystem_id");
		String subSysID = subsid==null?null:subsid.toString();
		if (subSysID != null && subSysID.trim().length() == 20) {
			vo.setSubsystemId(subSysID); //子系统ID
		}
		//系统功能标志，暂时默认为1
		vo.setSystemFlag(new Integer(1));
		//树显示编码
		vo.setDispCode(funCode);
		//设置模块类型
		vo.setModuleType(cModuleType);
		//zsb+：组织类型
		vo.setOrgTypecode("1");//公司
		return vo;
	}
	
	/**
	 * 生成单据类型
	 */
	private BilltypeVO createBillType(BillMakeVO hvo) throws BusinessException {
		String modelcode = hvo.getModelcode();
		String billtype = hvo.getBilltype();
		String hassql = " select '1' from bd_billtype where pk_billtypecode='"+billtype+"' ";
		String alexitstype = (String) getQbs().executeQuery(hassql,new ColumnProcessor());
		if(!Toolkits.isEmpty(alexitstype)){
			warpException(true,"应该单据类型已存在!");
		}
		String sql = " select systypecode from dap_dapsystem where module=lower('"+modelcode+"') and nvl(dr,0)=0; ";
		String systypecode = (String) getQbs().executeQuery(sql,new ColumnProcessor());
		if(Toolkits.isEmpty(systypecode)){
			warpException(true,"模块编码不存在,请先维护系统类型,或更改成已存在的模块名!");
		}
		BilltypeVO typevo = new BilltypeVO();
		typevo.setBilltypename(hvo.getName());
		typevo.setCanextendtransaction(new UFBoolean(false));
		typevo.setCheckclassname("nc.bs.trade.business.HYSuperDMO");
		typevo.setClassname("<Y>"+hvo.getNodecode());
		typevo.setIsaccount(new UFBoolean(false));
		typevo.setIsitem(new UFBoolean(false));
		typevo.setPk_billtypecode(billtype);
		typevo.setIsroot(new UFBoolean(false));
		typevo.setNodecode(hvo.getNodecode());
		typevo.setPk_billtypecode(billtype);
		typevo.setSystemcode(systypecode);
		//插入
//		getIvop().insertVO(typevo);
		return typevo;
	}
	
	/**
	 * 生成单据号规则
	 * @throws BusinessException 
	 */
	private GDTBillcodeRuleVO createBillTypeRule(BillMakeVO hvo) throws BusinessException {
		String year = _getDate().getYear()+"";
		year = year.substring(2);
		String month = _getDate().getMonth()+"";
		String day = _getDate().getDay()+"";
		String pk_billtype = hvo.getBilltype();
		String sql = " select '1' from pub_billcode_rule where pk_billtypecode='"+pk_billtype+"' and nvl(dr,0)=0 ";
		String alexitstype = (String) getQbs().executeQuery(sql,new ColumnProcessor());
		if(!Toolkits.isEmpty(alexitstype)){
			warpException(true,"单据类型对应的单据号规则已存在!");
		}
		GDTBillcodeRuleVO rvo = new GDTBillcodeRuleVO();
		rvo.setBillcodeshortname(hvo.getBilltype());
		rvo.setControlpara("N");
		rvo.setDay(day);
		rvo.setIsAutoFill(new UFBoolean(false));
		rvo.setIsCheck(new UFBoolean(true));
		rvo.setIshaveshortname(new UFBoolean(true));
		rvo.setIsPreserve(new UFBoolean(true));
		rvo.setLastsn("0000");
		rvo.setMonth(month);
		rvo.setPk_billtypecode(hvo.getBilltype());
		rvo.setPk_corp("0001");
		rvo.setSnnum(new Integer(4));//4位流水号
		rvo.setSnresetflag(3);//日归零
		rvo.setYear(year);
		//插入
//		getIvop().insertVO(rvo);
		return rvo;
		
	}
	
	/**
	 * 生成单据模板
	 * @throws BusinessException 
	 */
	private MyBillTempVO creatTemplet() throws BusinessException {
		String billname = getHeadValue("name").toString();
		String nodecode = getHeadValue("nodecode").toString();
		String billtype = getHeadValue("billtype").toString();
		//封装单据模板VO
		MyBillTempVO hvo = new MyBillTempVO();
		hvo.setBill_templetcaption(billname);
		hvo.setBill_templetname("SYSTEM");
		hvo.setNodecode(nodecode);
		hvo.setPk_billtypecode(billtype);
		hvo.setPk_corp("@@@@");
		hvo.setShareflag("N");
		String exitsql = " select * from pub_billtemplet a where a.pk_billtypecode='"+billtype+"' and nvl(dr,0)=0 ";
		String alexitstype = (String) getQbs().executeQuery(exitsql,new ColumnProcessor());
		if(!Toolkits.isEmpty(alexitstype)){
			warpException(true,"单据类型对应的单据模板已存在!");
		}
		return hvo;
	}
	
	/**
	 * 创建表头VO及模板代码
	 * @throws BusinessException 
	 */
	private void creatHVO() throws BusinessException {
		String tablename = "";
		String classname = "";
		String packagename = "";
		String billname = getHeadValue("name").toString();
		Date date = new Date();
		SimpleDateFormat sdformat = new SimpleDateFormat(
				"yyyy.MM.dd HH:mm:ss a");
		@SuppressWarnings("unused")
		String LgTime = sdformat.format(date);   
		tablename = getHeadValue("htablename").toString().trim();
		String allname = getHeadValue("hvoname").toString();
		classname = allname.substring(allname.lastIndexOf(".")+1);
		packagename = allname.substring(0,allname.lastIndexOf("."));
			
		//////封装TVO
		MyBillTempTVO tvo = new MyBillTempTVO();
		tvo.setTabindex(0);
		tvo.setTabcode(tablename);
		tvo.setPos(0);
		tvo.setTabname(billname);
		tvolist.add(tvo);
		//是否存在表尾
		boolean hastail = false;
		
		String sql = " select id,guid,display,kind,parentguid,prop,ts,dr from pub_datadict where guid = '"+tablename+"'; ";
		DatadictVO dvo = (DatadictVO) getQbs().executeQuery(sql, new DatadictProcess());
		if(dvo == null){
			throw new BusinessException("通过主表表名,"+tablename+"未找到对应的数据字典,请确认数据字段已导入,或主表表名拼写正确!");
		}
		TableDef tdvo = dvo.getProp();
		FieldDefList dlist = tdvo.getFieldDefs();
		StringBuffer sb = new StringBuffer();
		sb.append("package "+packagename+";\n")
		.append("\n")
		.append("import nc.vo.pub.SuperVO; \n") 
		.append("import nc.vo.pub.lang.UFDate; \n") 
		.append("import nc.vo.pub.lang.UFDateTime; \n") 
		.append("import nc.vo.pub.lang.UFDouble; \n")
		.append("import nc.vo.pub.lang.UFBoolean; \n") 
		.append("\n")
		.append("public class "+classname+" extends SuperVO { \n")
		.append("\n");
		
		Map<String, String> allid = new HashMap<String, String>() ;
		String pk = "";
		for (int i = 0; i < dlist.getCount(); i++) {
			FieldDef fdefvo = dlist.getFieldDef(i);
			String displayname = fdefvo.getDisplayName();//VO备注
			int length = fdefvo.getLength();
			int datatype = fdefvo.getDataType();//类型
			String voDateType = DataType.getNCVOType(datatype, length);
			String id = fdefvo.getID();
			if(id.equals("vapprovedate")){
				throw new BusinessException("检测到vapprovedate应该改成dapprovedate");
			}else if(id.equals("dmaketime")||id.equals("maketime")){
				throw new BusinessException("检测到"+id+"应该改成tmaketime");
			}else if(id.equals("dmodifytime")||id.equals("modifytime")){
				throw new BusinessException("检测到"+id+"应该改成tmodifytime");
			}
			allid.put(id, voDateType);//保存所有字段名
			boolean isprimary = fdefvo.getIsPrimary();
			if(isprimary){
				pk = id;
				tablename = fdefvo.getOwnerGUID();
			}
			@SuppressWarnings("unused")
			int pricision = fdefvo.getPrecision();
			sb.append("\t private "+voDateType+" "+id+";//"+displayname+" \n");
			sb.append("\n");
			
			//封装BVO/** showorder跟pk_billtemplet之后再设置 **/
			MyBillTempBVO bvo = new MyBillTempBVO();
			bvo.setCardflag(1);
			int tempType = DataType.getTempletType(datatype, length,id);
			bvo.setDatatype(tempType);
			bvo.setDefaultshowname(displayname);
			bvo.setEditflag(1);
			bvo.setForeground(-1);
			bvo.setInputlength(length);
			bvo.setItemkey(id);
			bvo.setItemtype(0);
			bvo.setLeafflag("N");
			bvo.setListflag(1);
			bvo.setListshowflag("Y");
			bvo.setShowflag(1);
			bvo.setLockflag(0);
			bvo.setNewlineflag("N");
			bvo.setNullflag(0);//全部不必输
			bvo.setPk_corp("@@@@");
			bvo.setReviseflag("N");
			//以下字段不显示
			if("ts".equals(id)||"dr".equals(id)||isprimary||id.startsWith("vdef")
					||id.equals("pk_corp")||id.equals("pk_busitype")||id.equals("tmaketime")
					||id.equals("vapprovetime")||id.equals("vapprovednote")||id.equals("vunapprovednote")
					||id.equals("vunapprovetime")){
				bvo.setShowflag(0);
				bvo.setListshowflag("N");
			}
			//以下字段不可编辑
			if(id.equals("vbillcode")||id.equals("vbillstatus")||id.equals("billtype")
					||id.equals("pk_operator")||id.equals("dmakedate")||id.equals("vapproveid")
					||id.equals("dapprovedate")||id.equals("dmaketime")||id.equals("modifyid")
					||id.equals("tmodifytime")||id.equals("modifydate")
					||id.equals("vunapproveid")||id.equals("vunapprovedate")
					||id.equals("vunapprovetime")){
				bvo.setEditflag(0);
			}
			bvo.setTable_code(tablename);
			bvo.setTable_name(billname);
			bvo.setPos(0);
			bvo.setWidth(1);
			bvo.setTotalflag(0);
			bvo.setUserdefflag("N");
			bvo.setUsereditflag(1);
			bvo.setUserflag(1);
			bvo.setUserreviseflag("N");
			bvo.setUsershowflag(1);
			//特殊的设置
			//1.设置参照
			if(id.equals("pk_operator")||id.equals("vapproveid")||id.equals("modifyid")||id.equals("vunapproveid")||id.equals("cbizid")){
				bvo.setDatatype(5);
				bvo.setReftype("操作员");
				bvo.setLoadformula("getColValue(sm_user,user_name,cuserid, "+id+")");
			}
			if(id.equals("vbillstatus")){
				bvo.setDatatype(6);
			}
			String type = getHeadValue("vclass").toString();
			if((id.equals("pk_operator")||id.equals("dmakedate")||id.equals("vapproveid")
					||id.equals("dapprovedate")||id.equals("dmaketime")||id.equals("modifyid")
					||id.equals("tmodifytime")||id.equals("modifydate")
					||id.equals("vunapproveid")||id.equals("vunapprovedate")
					||id.equals("vunapprovetime")||id.equals("tmaketime")
					||id.equals("vapprovetime")||id.equals("vapprovednote")
					||id.equals("vunapprovednote")
			)&&!type.equals("单表体")){
				hastail = true;
				bvo.setPos(2);//移动到表尾
			}
			bvolist.add(bvo);
			
		}
		
		//有表尾时,并是在生成,主页签时,要多加个表尾页签
		if(hastail){
			MyBillTempTVO tailvo = new MyBillTempTVO();
			tailvo.setTabindex(0);
			tailvo.setTabcode(tablename);
			tailvo.setPos(2);//位于表尾
			tailvo.setTabname(billname);
			tvolist.add(tailvo);
		}		
		
		Iterator<String> ite = allid.keySet().iterator();
		while(ite.hasNext()){
			String id = ite.next();
			String type = allid.get(id);
			String firstu = id.substring(0,1).toUpperCase();
			//set方法
			sb.append("\t public void set"+firstu+id.substring(1)+"("+type+" new"+id+"){\n");
			sb.append("\t     "+id+" = new"+id+";\n");
			sb.append("\t }\n");
			//get方法
			sb.append("\t public "+type+" get"+firstu+id.substring(1)+"(){\n");
			sb.append("\t     \treturn "+id+";\n");
			sb.append("\t }\n");
		}
		sb.append("\t 	 /** \n") 
		.append("\t 	  * <p>取得父VO主键字段. \n") 
		.append("\t 	  * <p> \n") 
		.append("\t 	  * 创建日期:2012-08-25 16:41:36 \n") 
		.append("\t 	  * @return java.lang.String \n") 
		.append("\t 	  */ \n") 
		.append("\t 	public java.lang.String getParentPKFieldName() { \n")
		.append("\t 	    return null; \n")
		.append("\t 	} \n") 
		.append("\t 	  /** \n") 
		.append("\t 	  * <p>取得表主键. \n") 
		.append("\t 	  * <p> \n") 
		.append("\t 	  * 创建日期:2012-08-25 16:41:36 \n") 
		.append("\t 	  * @return java.lang.String \n") 
		.append("\t 	  */ \n") 
		.append("\t 	public java.lang.String getPKFieldName() { \n") 
		.append("\t 	  return \""+pk+"\"; \n") 
		.append("\t 	} \n") 
		.append("\t      \n") 
		.append("\t 	/** \n") 
		.append("\t 	 * <p>返回表名称. \n") 
		.append("\t 	 * <p> \n") 
		.append("\t 	 * 创建日期:2012-08-25 16:41:36 \n") 
		.append("\t 	 * @return java.lang.String \n") 
		.append("\t 	 */ \n") 
		.append("\t 	public java.lang.String getTableName() { \n") 
		.append("\t 		return \""+tablename+"\"; \n") 
		.append("\t 	}     \n") 
		.append("\t      \n") 
		.append("\t     /** \n") 
		.append("\t 	  * 按照默认方式创建构造子. \n") 
		.append("\t 	  * \n") 
		.append("\t 	  * 创建日期:2012-08-25 16:41:36 \n") 
		.append("\t 	  */ \n") 
		.append("\t		public "+classname+"() { \n") 
		.append("\t 		super();	 \n") 
		.append("\t 	} \n");
		sb.append("}\n");
		
		String modulecode = getHeadValue("modelcode").toString();
		String filepath = packagename.replace(".", "/");
		String voexp_path = EXP_PATH+modulecode+"/src/public/"+filepath;
		createPath(voexp_path);
		PrintWriter pw = null;
		try {
			 pw = new PrintWriter(voexp_path+"/"+classname+".java");
			 pw.print(sb.toString());
			 pw.flush();
		} catch (Exception e) {
			throw new BusinessException(e);
		}finally{
			if(pw != null){
				pw.close();
			}
		}
	}

	/**
	 * 创建表体VO及模板代码
	 * @throws BusinessException 
	 */
	private void creatBVO(String bname,String tablename,String bvoname) throws BusinessException {
//		String tablename = "";
		String classname = "";
		String packagename = "";
		String billname = getHeadValue("name").toString();
		Date date = new Date();
		SimpleDateFormat sdformat = new SimpleDateFormat(
				"yyyy.MM.dd HH:mm:ss a");
		@SuppressWarnings("unused")
		String LgTime = sdformat.format(date);   
//		tablename = getHeadValue("btablename").toString();
//		String bvoname = getHeadValue("bvoname").toString();
		classname = bvoname.substring(bvoname.lastIndexOf(".")+1);
		packagename = bvoname.substring(0,bvoname.lastIndexOf("."));
		//////封装TVO
		MyBillTempTVO tvo = new MyBillTempTVO();
		tvo.setTabindex(tabindex);//子表可能存在多页签
		tabindex++;
		tvo.setTabcode(tablename);
		tvo.setPos(1);
		tvo.setTabname(bname);
		tvolist.add(tvo);
		
		String sql = " select id,guid,display,kind,parentguid,prop,ts,dr from pub_datadict where guid = '"+tablename+"'; ";
		DatadictVO dvo = (DatadictVO) getQbs().executeQuery(sql, new DatadictProcess());
		if(dvo == null){
			throw new BusinessException("通过子表表名,"+tablename+"未找到对应的数据字典,请确认数据字段已导入,或子表表名拼写正确!");
		}
		TableDef tdvo = dvo.getProp();
		FieldDefList dlist = tdvo.getFieldDefs();
		StringBuffer sb = new StringBuffer();
		sb.append("package "+packagename+";\n")
		.append("\n")
		.append("import nc.vo.pub.SuperVO; \n") 
		.append("import nc.vo.pub.lang.UFDate; \n") 
		.append("import nc.vo.pub.lang.UFDateTime; \n") 
		.append("import nc.vo.pub.lang.UFDouble; \n") 
		.append("import nc.vo.pub.lang.UFBoolean; \n") 
		.append("\n")
		.append("public class "+classname+" extends SuperVO { \n")
		.append("\n");
		
		Map<String, String> allid = new HashMap<String, String>() ;
		String pk = "";
		for (int i = 0; i < dlist.getCount(); i++) {
			FieldDef fdefvo = dlist.getFieldDef(i);
			String displayname = fdefvo.getDisplayName();//VO备注
			int length = fdefvo.getLength();
			int datatype = fdefvo.getDataType();//类型
			String voDateType = DataType.getNCVOType(datatype, length);
			String id = fdefvo.getID();
			if(id.equals("vapprovedate")){
				id = "dapprovedate";
			}
			allid.put(id, voDateType);//保存所有字段名
			boolean isprimary = fdefvo.getIsPrimary();
			if(isprimary){
				pk = id;
				tablename = fdefvo.getOwnerGUID();
			}
			@SuppressWarnings("unused")
			int pricision = fdefvo.getPrecision();
			sb.append("\t private "+voDateType+" "+id+";//"+displayname+" \n");
			sb.append("\n");
			
			//封装BVO/** showorder跟pk_billtemplet之后再设置 **/
			MyBillTempBVO bvo = new MyBillTempBVO();
			bvo.setCardflag(1);
			int tempType = DataType.getTempletType(datatype, length,id);
			bvo.setDatatype(tempType);
			bvo.setDefaultshowname(displayname);
			bvo.setEditflag(1);
			bvo.setForeground(-1);
			bvo.setInputlength(length);
			bvo.setItemkey(id);
			bvo.setItemtype(0);
			bvo.setLeafflag("N");
			bvo.setListflag(1);
			bvo.setListshowflag("Y");
			bvo.setNullflag(0);//不必输
			bvo.setShowflag(1);
			bvo.setLockflag(0);
			bvo.setNewlineflag("N");
			bvo.setPk_corp("@@@@");
			bvo.setReviseflag("N");
			//以下字段不显示
			if("ts".equals(id)||"dr".equals(id)||isprimary||id.startsWith("vdef")
					||id.equals("pk_corp")||id.equals("pk_busitype")||id.equals("tmaketime")
					||id.equals("vapprovetime")||id.equals("vapprovednote")||id.equals("vunapprovednote")
					||displayname.contains("主表主键")
					||displayname.contains("子表主键")){
				bvo.setShowflag(0);
				bvo.setListshowflag("N");
			}
			//以下字段不可编辑
			if(id.equals("vbillcode")||id.equals("vbillstatus")||id.equals("billtype")
					||id.equals("pk_operator")||id.equals("dmakedate")||id.equals("vapproveid")
					||id.equals("dapprovedate")||id.equals("dmaketime")||id.equals("lockman")
					||id.equals("lockdate")||id.equals("islock")){
				bvo.setEditflag(0);
			}
			bvo.setTable_code(tablename);
			bvo.setTable_name(billname+"子表");
			bvo.setPos(1);
			bvo.setWidth(100);
			bvo.setTotalflag(0);
			bvo.setUserdefflag("N");
			bvo.setUsereditflag(1);
			bvo.setUserflag(1);
			bvo.setUserreviseflag("N");
			bvo.setUsershowflag(1);
			//特殊的设置 
			//1.设置操作员参照
			if(id.equals("pk_operator")||id.equals("vapproveid")||id.equals("modifyid")||id.equals("vunapproveid")||id.equals("cbizid")){
				bvo.setDatatype(5);
				bvo.setReftype("操作员");
				bvo.setLoadformula("getColValue(sm_user,user_name,cuserid, "+id+")");
			}
			if(id.equals("vbillstatus")){
				bvo.setDatatype(6);
			}
			String type = getHeadValue("vclass").toString();
			if((id.equals("pk_operator")||id.equals("dmakedate")||id.equals("vapproveid")
					||id.equals("dapprovedate")||id.equals("dmaketime")||id.equals("modifyid")
					||id.equals("tmodifytime")||id.equals("modifydate")
					||id.equals("vunapproveid")||id.equals("vunapprovedate")
					||id.equals("vunapprovetime")||id.equals("tmaketime")
					||id.equals("vapprovetime")||id.equals("vapprovednote")
					||id.equals("vunapprovednote")
			)&&!type.equals("单表体")){
				bvo.setPos(2);//移动到表尾
			}
			bvolist.add(bvo);
			
		}
		
		Iterator<String> ite = allid.keySet().iterator();
		while(ite.hasNext()){
			String id = ite.next();
			String type = allid.get(id);
			String firstu = id.substring(0,1).toUpperCase();
			//set方法
			sb.append("\t public void set"+firstu+id.substring(1)+"("+type+" new"+id+"){\n");
			sb.append("\t     "+id+" = new"+id+";\n");
			sb.append("\t }\n");
			//get方法
			sb.append("\t public "+type+" get"+firstu+id.substring(1)+"(){\n");
			sb.append("\t     \treturn "+id+";\n");
			sb.append("\t }\n");
		}
		sb
		.append("\t 	 /** \n") 
		.append("\t 	  * <p>取得父VO主键字段. \n") 
		.append("\t 	  * <p> \n") 
		.append("\t 	  * 创建日期:2012-08-25 16:41:36 \n") 
		.append("\t 	  * @return java.lang.String \n") 
		.append("\t 	  */ \n") 
		.append("\t 	public java.lang.String getParentPKFieldName() { \n");
		String refpk = getHeadValue("hpk").toString();
		sb.append("\t 	    return \""+refpk+"\"; \n"); 
		sb.append("\t 	} \n") 
		.append("\t 	  /** \n") 
		.append("\t 	  * <p>取得表主键. \n") 
		.append("\t 	  * <p> \n") 
		.append("\t 	  * 创建日期:2012-08-25 16:41:36 \n") 
		.append("\t 	  * @return java.lang.String \n") 
		.append("\t 	  */ \n") 
		.append("\t 	public java.lang.String getPKFieldName() { \n") 
		.append("\t 	  return \""+pk+"\"; \n") 
		.append("\t 	} \n") 
		.append("\t      \n") 
		.append("\t 	/** \n") 
		.append("\t 	 * <p>返回表名称. \n") 
		.append("\t 	 * <p> \n") 
		.append("\t 	 * 创建日期:2012-08-25 16:41:36 \n") 
		.append("\t 	 * @return java.lang.String \n") 
		.append("\t 	 */ \n") 
		.append("\t 	public java.lang.String getTableName() { \n") 
		.append("\t 		return \""+tablename+"\"; \n") 
		.append("\t 	}     \n") 
		.append("\t      \n") 
		.append("\t     /** \n") 
		.append("\t 	  * 按照默认方式创建构造子. \n") 
		.append("\t 	  * \n") 
		.append("\t 	  * 创建日期:2012-08-25 16:41:36 \n") 
		.append("\t 	  */ \n") 
		.append("\t		public "+classname+"() { \n") 
		.append("\t 		super();	 \n") 
		.append("\t 	} \n");
		sb.append("}\n");
		
		String modulecode = getHeadValue("modelcode").toString();
		String filepath = packagename.replace(".", "/");
		String voexp_path = EXP_PATH+modulecode+"/src/public/"+filepath;
		createPath(voexp_path);
		PrintWriter pw = null;
		try {
			 pw = new PrintWriter(voexp_path+"/"+classname+".java");
			 pw.print(sb.toString());
			 pw.flush();
		} catch (Exception e) {
			throw new BusinessException(e);
		}finally{
			if(pw != null){
				pw.close();
			}
		}
	}

	/**
	 * @throws BusinessException
	 * 创建多子表的AggVO
	 */
	private void createAggVO() throws BusinessException {
		
		String packagename = "";
		String billname = getHeadValue("name").toString();
		//第一个子表VO
		Object bname = getHeadValue("bname");
		Object btablename = getHeadValue("btablename");
		Object bvoname = getHeadValue("bvoname");
		String btablenames = "";
		String bnames = "";
		//其余子表
		for (int i = 1; i <= maxbtableindex; i++) {
			String k = "";
			if(i != 1){
				k = i + "";
			}
			Object bnamei = getHeadValue("bname"+k);
			Object btablenamei = getHeadValue("btablename"+k);
			if(!Toolkits.isEmpty(bnamei)&&!Toolkits.isEmpty(btablenamei)){
				btablenames += "\""+btablenamei.toString()+"\",";
				bnames += "\""+bnamei.toString()+"\",";
				
			}
		}
		btablenames = btablenames.substring(0, btablenames.length()-1);
		bnames = bnames.substring(0, bnames.length()-1);
		
		if(!Toolkits.isEmpty(bname)&&!Toolkits.isEmpty(btablename)&&!Toolkits.isEmpty(bvoname)){
			packagename = bvoname.toString().substring(0,bvoname.toString().lastIndexOf("."));
			
			StringBuffer sb = new StringBuffer();
			sb.append(" package "+packagename+"; \n") 
			.append("  \n") 
			.append(" import java.util.ArrayList; \n") 
			.append(" import java.util.Arrays; \n") 
			.append(" import java.util.HashMap; \n") 
			.append("  \n") 
			.append(" /**"+billname+"聚合VO **/ \n")
			.append(" import nc.vo.pub.CircularlyAccessibleValueObject; \n") 
			.append(" import nc.vo.pub.SuperVO; \n") 
			.append(" import nc.vo.trade.pub.HYBillVO; \n") 
			.append(" import nc.vo.trade.pub.IExAggVO; \n") 
			.append("  \n") 
			.append(" public class MyExAggVO extends HYBillVO implements IExAggVO { \n") 
			.append("  \n") 
			.append(" 	private HashMap hmchidvos=new HashMap(); \n") 
			.append(" 	public MyExAggVO() { \n") 
			.append(" 		super(); \n") 
			.append(" 	} \n") 
			.append(" 	public CircularlyAccessibleValueObject[] getAllChildrenVO() { \n") 
			.append(" 		ArrayList al = new ArrayList(); \n") 
			.append(" 		for (int i = 0; i < getTableCodes().length; i++) { \n") 
			.append(" 			CircularlyAccessibleValueObject[] cvos = getTableVO(getTableCodes()[i]); \n") 
			.append(" 			if (cvos != null && cvos.length > 0) \n") 
			.append(" 				al.addAll(Arrays.asList(cvos)); \n") 
			.append(" 		} \n") 
			.append(" 		return (nc.vo.pub.SuperVO[]) al.toArray(new nc.vo.pub.SuperVO[0]); \n") 
			.append(" 	} \n") 
			.append(" 	public SuperVO[] getChildVOsByParentId(String tableCode, String parentid) { \n") 
			.append(" 		return null; \n") 
			.append(" 	} \n") 
			.append(" 	public String getDefaultTableCode() { \n") 
			.append(" 		return getTableCodes()[0]; \n") 
			.append(" 	} \n") 
			.append(" 	public HashMap getHmEditingVOs() throws Exception { \n") 
			.append(" 		return null; \n") 
			.append(" 	} \n") 
			.append(" 	public String getParentId(SuperVO item) { \n") 
			.append(" 		return null; \n") 
			.append(" 	} \n") 
			.append(" 	public String[] getTableCodes() { \n") 
			.append("         return new String[] { "+btablenames+"}; \n") 
			.append(" 	} \n") 
			.append(" 	public String[] getTableNames() { \n") 
			.append("         return new String[] { "+bnames+"}; \n") 
			.append(" 	} \n") 
			.append(" 	public CircularlyAccessibleValueObject[] getTableVO(String tableCode) { \n") 
			.append(" 		Object o = hmchidvos.get(tableCode); \n") 
			.append(" 		if (o != null) \n") 
			.append(" 			return (nc.vo.pub.CircularlyAccessibleValueObject[]) o; \n") 
			.append(" 		return null; \n") 
			.append(" 	} \n") 
			.append(" 	public void setParentId(SuperVO item, String id) { \n") 
			.append(" 		 \n") 
			.append(" 	} \n") 
			.append(" 	public void setTableVO(String tableCode, \n") 
			.append(" 			CircularlyAccessibleValueObject[] values) { \n") 
			.append(" 		if (values == null || values.length == 0) \n") 
			.append(" 			hmchidvos.remove(tableCode); \n") 
			.append(" 		else \n") 
			.append(" 			hmchidvos.put(tableCode, values); \n") 
			.append(" 	} \n") 
			.append(" } \n");
			
			String modulecode = getHeadValue("modelcode").toString();
			String filepath = packagename.replace(".", "/");
			String voexp_path = EXP_PATH+modulecode+"/src/public/"+filepath;
			createPath(voexp_path);
			PrintWriter pw = null;
			try {
				 pw = new PrintWriter(voexp_path+"/MyExAggVO.java");
				 pw.print(sb.toString());
				 pw.flush();
			} catch (Exception e) {
				throw new BusinessException(e);
			}finally{
				if(pw != null){
					pw.close();
				}
			}
		}
	}

	/**
	 * 创建代码 UI EH CTRL
	 * @throws BusinessException 
	 */
	private void creatCode() throws BusinessException {
		String uiclasss = getHeadValue("uiclass").toString();
		String packagename = uiclasss.substring(0,uiclasss.lastIndexOf("."));
		String billtype = getHeadValue("billtype").toString();
		String hvoname = getHeadValue("hvoname").toString();
		String bvoname = getHeadValue("bvoname").toString();
		String type = getHeadValue("vclass").toString();
		String hpk = getHeadValue("hpk").toString();
		String bpk = getHeadValue("bpk").toString();
		String billname = getHeadValue("name").toString();
		//创建UI
		StringBuffer uisb = new StringBuffer();
		uisb.append("package "+packagename+"; \n") 
		.append("  \n") 
		.append("import nc.ui.jyglgt.billmanage.AbstractClientUI; \n") 
		.append("import nc.ui.trade.bill.AbstractManageController; \n") 
		.append("import nc.ui.trade.manage.ManageEventHandler; \n") 
		.append(" \n") 
		.append("/** \n") 
		.append(" * @author 自动生成 \n") 
		.append(" * 名称: "+billname+"UI类 \n") 
		.append("*/ \n"); 
		if(type.equals("多子表")){
			uisb.append("public class ClientUI extends nc.ui.jyglgt.billmanage.AbstractMultiChildClientUI { \n");
		}else{
			uisb.append("public class ClientUI extends AbstractClientUI { \n");
		}
		uisb.append("  \n") 
		.append("	public ClientUI() { \n") 
		.append("	} \n") 
		.append("  \n") 
		.append(" 	public ManageEventHandler createEventHandler() { \n") 
		.append(" 		return new ClientEventHandler(this, this.createController()); \n") 
		.append(" 	} \n") 
		.append("  \n") 
		.append(" 	protected AbstractManageController createController() { \n") 
		.append(" 		return new ClientCtrl(); \n") 
		.append(" 	} \n"); 
		if(type.equals("多子表")){
			uisb.append(" @Override \n") 
			.append(" 	protected nc.ui.trade.bsdelegate.BusinessDelegator createBusinessDelegator() { \n") 
			.append(" 		return new nc.ui.jyglgt.billmanage.CommonBusinessDelegator(this); \n") 
			.append(" 	} \n");

		}
		uisb.append("  \n")
		.append("} \n");
		//创建EH
		StringBuffer ehsb = new StringBuffer();
		ehsb.append("package "+packagename+"; \n") 
		.append(" \n") 
		.append("import nc.ui.jyglgt.billmanage.AbstractEventHandler; \n") 
		.append("import nc.ui.trade.controller.IControllerBase; \n") 
		.append("import nc.ui.trade.manage.BillManageUI; \n") 
		.append("import nc.ui.trade.bill.ISingleController; \n") 
		.append(" \n") 
		.append("/** \n") 
		.append(" * @author 自动生成 \n") 
		.append(" * 名称: "+billname+"EH类 \n") 
		.append("*/ \n") 
		.append("public class ClientEventHandler extends AbstractEventHandler{ \n") 
		.append("	 \n") 
		.append("	public ClientEventHandler(BillManageUI billUI, IControllerBase control) { \n") 
		.append("		super(billUI, control); \n") 
		.append("	} \n") 
		.append("	 \n") 
		.append("} \n") ;
		//创建CTRL
		StringBuffer ctrlsb = new StringBuffer();
		ctrlsb.append("package "+packagename+"; \n") 
		.append("  \n") 
		.append("import nc.ui.jyglgt.billmanage.AbstractCtrl; \n") 
		.append("import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType; \n") 
		.append("import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst; \n") 
		.append("import nc.vo.trade.pub.HYBillVO; \n") 
		.append("import nc.vo.jyglgt.button.IJyglgtButton; \n") 
		.append("import nc.vo.jyglgt.button.ButtonTool; \n") 
		.append("import nc.ui.trade.bill.ISingleController; ") 
		.append("  \n") 
		.append("  \n") 
		.append("/** \n") 
		.append(" * @author 自动生成 \n") 
		.append(" * 名称: "+billname+"CTRL类 \n") 
		.append("*/ \n"); 
		if(type.equals("管理型")||type.equals("多子表")){
			ctrlsb.append("public class ClientCtrl extends AbstractCtrl { \n");
		}else
			ctrlsb.append("public class ClientCtrl extends AbstractCtrl implements ISingleController { \n"); 
		
		ctrlsb.append("  \n") 
		.append(" 	public String getBillType() { \n") 
		.append(" 		return IJyglgtBillType."+billtype+"; \n") 
		.append(" 	} \n") 
		.append("  \n") 
		.append(" 	public String[] getBillVoName() { \n") 
		.append(" 		return new String[] {  \n"); 
		if(type.equals("单表头管理型")){
			ctrlsb.append(" 				HYBillVO.class.getName(), \n") ;
			ctrlsb.append(" 				"+hvoname+".class.getName(), \n");
			ctrlsb.append(" 				"+hvoname+".class.getName()}; \n"); 
		}else if(type.equals("单表体管理型")){
			ctrlsb.append(" 				HYBillVO.class.getName(), \n") ;
			ctrlsb.append(" 				"+bvoname+".class.getName(), \n");
			ctrlsb.append(" 				"+bvoname+".class.getName()}; \n");
		}else if(type.equals("管理型")){
			ctrlsb.append(" 				HYBillVO.class.getName(), \n") ;
			ctrlsb.append(" 				"+hvoname+".class.getName(), \n");
			ctrlsb.append(" 				"+bvoname+".class.getName()}; \n");
		}else if(type.equals("多子表")){
			Object b1voname = getHeadValue("bvoname");
			String b1pacname = bvoname.toString().substring(0,bvoname.toString().lastIndexOf("."));
			ctrlsb.append(" 				"+b1pacname+".MyExAggVO.class.getName(), \n") ;
			ctrlsb.append(" 				"+hvoname+".class.getName(), \n");
			ctrlsb.append(" 				"+b1voname+".class.getName(), \n") ;
			String allbvoname = "";
			for (int i = 2; i <= maxbtableindex; i++) {
				Object bnamei = getHeadValue("bname"+i);
				Object btablenamei = getHeadValue("btablename"+i);
				Object bvonamei = getHeadValue("bvoname"+i);
				if(!Toolkits.isEmpty(bnamei)&&!Toolkits.isEmpty(btablenamei)&&!Toolkits.isEmpty(bvonamei)){
					allbvoname += " 				"+bvonamei+".class.getName(), \n";
				}
			}
			allbvoname = allbvoname.substring(0, allbvoname.lastIndexOf(","));
			allbvoname = allbvoname + "}; \n";
			ctrlsb.append(allbvoname);
		}
		
		
		ctrlsb.append(" 	} \n") 
		.append("  \n") 
		.append(" 	public int[] getCardButtonAry() { \n"); 
		if(type.equals("单表头管理型")){
			ctrlsb.append(" 		return ButtonTool.deleteButton(IJyglgtButton.Line, IJyglgtConst.CARD_BUTTONS_M); \n");
		}else{
			ctrlsb.append(" 		return IJyglgtConst.CARD_BUTTONS_M; \n");
		}
//		ctrlsb.append(" return ButtonTool.deleteButton(IJyglgtButton.Line, IJyglgtConst.CARD_BUTTONS_M); ")
		ctrlsb.append(" 	} \n") 
		.append("  \n") 
		.append(" 	public int[] getListButtonAry() { \n") 
		.append(" 		return IJyglgtConst.LIST_BUTTONS_M; \n") 
		.append(" 	} \n") 
		.append("  \n") 
		.append(" 	public String getPkField() { \n") 
		.append(" 		return \""+hpk+"\"; \n") 
		.append(" 	} \n") 
		.append("  \n") 
		.append(" 	@Override \n") 
		.append(" 	public String getChildPkField() { \n");
		if(type.equals("管理型")){
			ctrlsb.append(" 		return \""+bpk+"\"; \n");
		}else{
			ctrlsb.append(" 		return null; \n");
		}
		ctrlsb.append(" 	} \n");
		if(type.equals("单表头管理型")||type.equals("单表体管理型")){
			ctrlsb.append(" 	 /** \n") 
			.append(" 	 * 是否单表体,=true单表体，=false单表头。\n ") 
			.append(" 	 * 创建日期：(2004-2-5 13:43:00) \n") 
			.append(" 	 * @return boolean \n") 
			.append(" 	 */ \n") 
			.append(" 	public boolean isSingleDetail() { \n");
			if(type.equals("单表头管理型"))
				ctrlsb.append(" 		return false; \n"); 
			else{
				ctrlsb.append(" 		return true; \n"); 
			}
			ctrlsb.append(" 	} \n"); 
		}

		ctrlsb.append("  \n") 
		.append("} \n");
		
		String modulecode = getHeadValue("modelcode").toString();
		String filepath = packagename.replace(".", "/");
		String uiexp_path = EXP_PATH+modulecode+"/src/client/"+filepath;
		createPath(uiexp_path);
		PrintWriter pw = null;
		try {
			 pw = new PrintWriter(uiexp_path+"/ClientUI.java");
			 pw.print(uisb.toString());
			 pw.flush();
		} catch (Exception e) {
			throw new BusinessException(e);
		}finally{
			if(pw != null){
				pw.close();
			}
		}
		try {
			 pw = new PrintWriter(uiexp_path+"/ClientEventHandler.java");
			 pw.print(ehsb.toString());
			 pw.flush();
		} catch (Exception e) {
			throw new BusinessException(e);
		}finally{
			if(pw != null){
				pw.close();
			}
		}
		try {
			 pw = new PrintWriter(uiexp_path+"/ClientCtrl.java");
			 pw.print(ctrlsb.toString());
			 pw.flush();
		} catch (Exception e) {
			throw new BusinessException(e);
		}finally{
			if(pw != null){
				pw.close();
			}
		}
	}
	 /**
	 * 
	 * @param path
	 *            文件夹路径
	 */
	public void createPath(String path) {
		String[] foldname = path.split("/");
		String fianlpath = "";
		for (int i = 0; i < foldname.length; i++) {
			int index = path.indexOf("/"); 
			String perp = "";
			if(index == -1){
				perp = path;
			}else
				perp = path.substring(0, index);
			
			path = path.replaceFirst(perp+"/", "");
			fianlpath = fianlpath+perp+"/";
			File file = new File(fianlpath);
			// 判断文件夹是否存在,如果不存在则创建文件夹
			if (!file.exists()) {
				file.mkdir();
			}
		}
	}
	


	private Object getHeadValue(String itemkey){
		return getBillCardPanelWrapper().getBillCardPanel().getHeadItem(itemkey).getValueObject();
	}
	
	/**
	 * @param btvo
	 * @param trvo
	 * @param thvo
	 * @param fvo
	 * @param hvo
	 * @throws BusinessException
	 * 所有的语句插入,此方法建议写到后台去
	 */
	private void insertIntoDataBase( BilltypeVO btvo, GDTBillcodeRuleVO trvo, MyBillTempVO thvo,  FuncRegisterVO fvo, BillMakeVO hvo) throws BusinessException {
		NCLocator.getInstance().lookup(IJyglgtItf.class).autoCreateBill(btvo, trvo, thvo, bvolist, tvolist, fvo, hvo);
	}
}
