package nc.bs.pub.action;

import nc.pub.jyglgt.proxy.Proxy;
import nc.vo.pub.pf.PfUtilActionVO;
import nc.bs.ml.NCLangResOnserver;
import nc.bs.pub.compiler.*;
import nc.vo.pub.compiler.PfParameterVO;
import java.math.*;
import java.util.*;
import nc.vo.pub.lang.*;
import nc.bs.pub.pf.PfUtilTools;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：库存采购入库单的删除
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2008-7-23)
 * @author 平台脚本生成
 */
public class N_45_DELETE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_45_DELETE 构造子注解。
 */
public N_45_DELETE() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
	//*************从平台取得由该动作传入的入口参数。本方法取得需要保存的VO。***********
	Object inCurObject  =getVos ();
	Object retObj  =null;
	StringBuffer sErr  =new StringBuffer ();
	//1,首先检查传入参数类型是否合法，是否为空。
	if (! (inCurObject instanceof nc.vo.ic.pub.bill.GeneralBillVO[])) throw new BusinessException ( "Remote Call",new nc.vo.pub.BusinessException ( NCLangResOnserver.getInstance().getStrByID("4008action", "4008action-000032")/*错误：您希望保存的库存采购入库类型不匹配*/));
	if (inCurObject  == null) throw new BusinessException ( "Remote Call",new nc.vo.pub.BusinessException ( NCLangResOnserver.getInstance().getStrByID("4008action", "4008action-000033")/*错误：您希望保存的库存采购入库没有数据*/));
	//2,数据合法，把数据转换为库存采购入库。
	nc.vo.ic.pub.bill.GeneralBillVO inCurVO  =null;
	nc.vo.ic.pub.bill.GeneralBillVO[] inCurVOs  = (nc.vo.ic.pub.bill.GeneralBillVO[])inCurObject;
	inCurObject  =null;
	for (int i=0;i<inCurVOs.length;i  ++) {
		inCurVO  =inCurVOs[i];
		if (inCurVO!=null&&inCurVO.getHeaderVO ()!=null)
		inCurVO.getHeaderVO ().setStatus (nc.vo.pub.VOStatus.DELETED);
		//获取平台传入的参数
		setParameter ( "INCURVO",inCurVO);
		Object alLockedPK  =null;
		try {
			//####重要说明：生成的业务组件方法尽量不要进行修改####
			//方法说明:检查是否关帐。<可配置>
			//目前是根据单据表体业务日期检查。如果根据登录日期检查，请将checkAccountStatus改为checkAccountStatus1
			runClass( "nc.bs.ic.ic2a3.AccountctrlDMO", "checkAccountStatus", "&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
			//#############################################################
			//方法说明:库存出入库单据加业务锁
			alLockedPK  =runClass( "nc.bs.ic.pub.bill.ICLockBO", "lockBill", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
			//##################################################
			//该方法<不可配置>
			//方法说明:检查库存单据时间戳
			runClass( "nc.bs.ic.pub.check.CheckDMO", "checkTimeStamp", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
			//####重要说明：生成的业务组件方法尽量不要进行修改####
			ArrayList listbefore  = null;
			if (inCurVO.isHaveSourceBill ()) {
				//方法说明:修改可用量前获取更新信息
				listbefore  = (ArrayList)runClass( "nc.bs.ic.pub.bill.ICATP", "modifyATPBefore", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
			}
			//该方法<不可配置>
			//方法说明:检查当前单据是否生成下游库存单据
			runClass( "nc.bs.ic.pub.check.CheckBusiDMO", "checkRelativeBill", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
			//##################################################
			//处理单据单据辅数量和库存单位之间的转换，如果没有这种业务，请实施人员注释掉。
			runClass( "nc.bs.ic.pub.bill.DesassemblyBO", "setMeasRateVO", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
			//更新自动化数据  add by shipeng at 2014-10-30
//			runClass( "nc.bs.jyglgt.impl.IJyglgtImpl", "updateMiddleDB", "&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
			Proxy.getItf().updateMiddleDB(inCurVO);
			//end by shipeng at 2014-10-30
			//单据保存前动作统一处理
			runClass( "nc.bs.ic.pub.BillActionBase", "beforeSave", "&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
			//方法说明:单据删除
			runClass( "nc.bs.ic.ic201.GeneralHBO", "deleteBill", "&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
			//处理拆箱，如果没有这种业务，请实施人员注释掉。
			runClass( "nc.bs.ic.pub.bill.DesassemblyBO", "exeDesassembly", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
			//方法说明：回写累计出库数量
			setParameter ( "CURVO",null);
			setParameter ( "PREVO",inCurVO);
			runClass( "nc.bs.ic.pub.RewriteDMO", "reWriteCorNum", "&CURVO:nc.vo.ic.pub.bill.GeneralBillVO,&PREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
			//该方法<不可配置>
			//方法说明:检查存货主辅数量方向一致,批次负结存,存货负结存
			runClass( "nc.bs.ic.pub.check.CheckDMO", "checkDBL_New", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
			//该方法<不可配置>
			//方法说明:检查存货出库是否跟踪入库负结存
			runClass( "nc.bs.ic.pub.check.CheckDMO", "checkInOutTrace", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
			//方法说明:检查删除退库单据行是否已经存在补货订单
			runClass( "nc.bs.ic.pub.check.CheckDMO", "checkBillisReferedOrder", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
			//该方法<可配置>
			//方法说明:检查最高库存、最低库存、安全库存、再订购点
			Object s1  =runClass( "nc.bs.ic.pub.check.CheckDMO", "checkParam_new", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
			if (s1!=null)
			sErr.append ( (String)s1);
			//检查协同的采购入库单删除后是否需要清空对应销售出库单的协同标志
			runClass( "nc.bs.ic.ic211.GeneralHBO", "setCoopFlag", "&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
			//####重要说明：生成的业务组件方法尽量不要进行修改####
			//方法说明:由自由项重置质量等级id <如果启用质检则不可配置>。
			setParameter ( "INCURVOCHK",inCurVO);
			//Object retBillWithChk=runClass("nc.bs.ic.ic201.QualityCheckDMO","setChkStaByFree","&INCURVOCHK:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
			//setParameter ("INCURVOCHK1",retBillWithChk);
			//方法说明:采购入库单回写采购入库数量
			setParameter ( "RWINPREVO",null);
			retObj  =runClass( "nc.bs.ic.pub.RewriteDMO", "reWritePurNew", "&RWINPREVO:nc.vo.ic.pub.bill.GeneralBillVO,&INCURVOCHK:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
			//##################################################
			//该方法<不可配置>
			//方法说明:单据删除时退回单据号
			nc.vo.scm.pub.IBillCode ibc  = (nc.vo.scm.pub.IBillCode)inCurVO;
			setParameter ( "IBC",ibc);
			runClass( "nc.bs.ic.pub.check.CheckDMO", "returnBillCodeWhenDelete", "&IBC:nc.vo.scm.pub.IBillCode",vo,m_keyHas,m_methodReturnHas);
			if (inCurVO.isHaveSourceBill ()) {
				//方法说明:可用量及时检查,可配置
				setParameter ( "INPREVOATP",null);
				runClass( "nc.bs.ic.pub.bill.ICATP", "checkAtpInstantly", "&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVOATP:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
			}
			//单据保存后动作统一处理
			runClass( "nc.bs.ic.pub.BillActionBase", "afterSave", "&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
			//####重要说明：生成的业务组件方法尽量不要进行修改####
			if (inCurVO.isHaveSourceBill ()) {
				//设置可用量参数
				if (listbefore!=null)
				setParameter ( "LISTBEFORE",listbefore);
				//方法说明:修改可用量
				runClass( "nc.bs.ic.pub.bill.ICATP", "modifyATPAfter", "&INCURVO:nc.vo.pub.AggregatedValueObject,&LISTBEFORE:java.util.ArrayList",vo,m_keyHas,m_methodReturnHas);
			}
		}
		catch (Exception e) {
			//############################
			//插入业务日志，该方法可以配置
			setParameter ( "EXC",e.getMessage ());
			setParameter ( "FUN", "删除"/*-=notranslate=-*/);
			runClass( "nc.bs.ic.pub.check.CheckBO", "insertBusinessExceptionlog", "&INCURVO:nc.vo.pub.AggregatedValueObject,&EXC:String,&FUN:String",vo,m_keyHas,m_methodReturnHas);
			//###########################
			if (e instanceof BusinessException)
			throw (BusinessException) e;
			else
			throw new BusinessException ( "Remote Call", e);
		}
		finally {
			//####重要说明：生成的业务组件方法尽量不要进行修改####
			//方法说明:库存出入库单据解业务锁
			if (alLockedPK!=null) {
				setParameter ( "ALLPK", (ArrayList)alLockedPK);
				runClass( "nc.bs.ic.pub.bill.ICLockBO", "unlockBill", "&INCURVO:nc.vo.pub.AggregatedValueObject,&ALLPK:ArrayList",vo,m_keyHas,m_methodReturnHas);
			}
			//##################################################
		}
	}
	//插入业务日志，该方法可以配置
	setParameter ( "INCURVOS",inCurVOs);
	setParameter ( "ERR",sErr.toString ());
	setParameter ( "FUN", "删除"/*-=notranslate=-*/);
	runClass( "nc.bs.ic.pub.check.CheckDMO", "insertBusinesslog", "&INCURVOS:nc.vo.ic.pub.bill.GeneralBillVO[],&ERR:String,&FUN:String",vo,m_keyHas,m_methodReturnHas);
	//############################
	inCurVO  =null;
	ArrayList alRet  =new ArrayList ();
	if (sErr.toString ().trim ().length ()  ==0)
	alRet.add (null);
	else
	alRet.add (sErr.toString ());
	//alRet.add (retObj);
	return retObj;
	//************************************************************************
} catch (Exception ex) {
	if (ex instanceof BusinessException)
		throw (BusinessException) ex;
	else 
    throw new PFBusinessException(ex.getMessage(), ex);
}
}
/*
* 备注：平台编写原始脚本
*/
public String getCodeRemark(){
	return "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\n	//*************从平台取得由该动作传入的入口参数。本方法取得需要保存的VO。***********\n	Object inCurObject  =getVos ();\n	Object retObj  =null;\n	StringBuffer sErr  =new StringBuffer ();\n	//1,首先检查传入参数类型是否合法，是否为空。\n	if (! (inCurObject instanceof nc.vo.ic.pub.bill.GeneralBillVO[])) throw new BusinessException ( \"Remote Call\",new nc.vo.pub.BusinessException ( \"错误：您希望保存的库存采购入库类型不匹配\"));\n	if (inCurObject  == null) throw new BusinessException ( \"Remote Call\",new nc.vo.pub.BusinessException ( \"错误：您希望保存的库存采购入库没有数据\"));\n	//2,数据合法，把数据转换为库存采购入库。\n	nc.vo.ic.pub.bill.GeneralBillVO inCurVO  =null;\n	nc.vo.ic.pub.bill.GeneralBillVO[] inCurVOs  = (nc.vo.ic.pub.bill.GeneralBillVO[])inCurObject;\n	inCurObject  =null;\n	for (int i=0;i<inCurVOs.length;i  ++) {\n		inCurVO  =inCurVOs[i];\n		if (inCurVO!=null&&inCurVO.getHeaderVO ()!=null)\n		inCurVO.getHeaderVO ().setStatus (nc.vo.pub.VOStatus.DELETED);\n		//获取平台传入的参数\n		setParameter ( \"INCURVO\",inCurVO);\n		Object alLockedPK  =null;\n		try {\n			//####重要说明：生成的业务组件方法尽量不要进行修改####\n			//方法说明:检查是否关帐。<可配置>\n			//目前是根据单据表体业务日期检查。如果根据登录日期检查，请将checkAccountStatus改为checkAccountStatus1\n			runClassCom@ \"nc.bs.ic.ic2a3.AccountctrlDMO\", \"checkAccountStatus\", \"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n			//#############################################################\n			//方法说明:库存出入库单据加业务锁\n			alLockedPK  =runClassCom@ \"nc.bs.ic.pub.bill.ICLockBO\", \"lockBill\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n			//##################################################\n			//该方法<不可配置>\n			//方法说明:检查库存单据时间戳\n			runClassCom@ \"nc.bs.ic.pub.check.CheckDMO\", \"checkTimeStamp\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n			//####重要说明：生成的业务组件方法尽量不要进行修改####\n			ArrayList listbefore  = null;\n			if (inCurVO.isHaveSourceBill ()) {\n				//方法说明:修改可用量前获取更新信息\n				listbefore  = (ArrayList)runClassCom@ \"nc.bs.ic.pub.bill.ICATP\", \"modifyATPBefore\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n			}\n			//该方法<不可配置>\n			//方法说明:检查当前单据是否生成下游库存单据\n			runClassCom@ \"nc.bs.ic.pub.check.CheckBusiDMO\", \"checkRelativeBill\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n			//##################################################\n			//处理单据单据辅数量和库存单位之间的转换，如果没有这种业务，请实施人员注释掉。\n			runClassCom@ \"nc.bs.ic.pub.bill.DesassemblyBO\", \"setMeasRateVO\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n			//单据保存前动作统一处理\n			runClasscom@ \"nc.bs.ic.pub.BillActionBase\", \"beforeSave\", \"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n			//方法说明:单据删除\n			runClassCom@ \"nc.bs.ic.ic201.GeneralHBO\", \"deleteBill\", \"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n			//处理拆箱，如果没有这种业务，请实施人员注释掉。\n			runClassCom@ \"nc.bs.ic.pub.bill.DesassemblyBO\", \"exeDesassembly\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n			//方法说明：回写累计出库数量\n			setParameter ( \"CURVO\",null);\n			setParameter ( \"PREVO\",inCurVO);\n			runClassCom@ \"nc.bs.ic.pub.RewriteDMO\", \"reWriteCorNum\", \"&CURVO:nc.vo.ic.pub.bill.GeneralBillVO,&PREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n			//该方法<不可配置>\n			//方法说明:检查存货主辅数量方向一致,批次负结存,存货负结存\n			runClassCom@ \"nc.bs.ic.pub.check.CheckDMO\", \"checkDBL_New\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n			//该方法<不可配置>\n			//方法说明:检查存货出库是否跟踪入库负结存\n			runClassCom@ \"nc.bs.ic.pub.check.CheckDMO\", \"checkInOutTrace\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n			//方法说明:检查删除退库单据行是否已经存在补货订单\n			runClassCom@ \"nc.bs.ic.pub.check.CheckDMO\", \"checkBillisReferedOrder\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n			//该方法<可配置>\n			//方法说明:检查最高库存、最低库存、安全库存、再订购点\n			Object s1  =runClassCom@ \"nc.bs.ic.pub.check.CheckDMO\", \"checkParam_new\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n			if (s1!=null)\n			sErr.append ( (String)s1);" +
			"\n			//检查协同的采购入库单删除后是否需要清空对应销售出库单的协同标志\n			runClassCom@ \"nc.bs.ic.ic211.GeneralHBO\", \"setCoopFlag\", \"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n			//####重要说明：生成的业务组件方法尽量不要进行修改####\n			//方法说明:由自由项重置质量等级id <如果启用质检则不可配置>。\n			setParameter ( \"INCURVOCHK\",inCurVO);\n			//Object retBillWithChk=runClassCom@\"nc.bs.ic.ic201.QualityCheckDMO\",\"setChkStaByFree\",\"&INCURVOCHK:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n			//setParameter (\"INCURVOCHK1\",retBillWithChk);\n			//方法说明:采购入库单回写采购入库数量\n			setParameter ( \"RWINPREVO\",null);\n			retObj  =runClassCom@ \"nc.bs.ic.pub.RewriteDMO\", \"reWritePurNew\", \"&RWINPREVO:nc.vo.ic.pub.bill.GeneralBillVO,&INCURVOCHK:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n			//##################################################\n			//该方法<不可配置>\n			//方法说明:单据删除时退回单据号\n			nc.vo.scm.pub.IBillCode ibc  = (nc.vo.scm.pub.IBillCode)inCurVO;\n			setParameter ( \"IBC\",ibc);\n			runClassCom@ \"nc.bs.ic.pub.check.CheckDMO\", \"returnBillCodeWhenDelete\", \"&IBC:nc.vo.scm.pub.IBillCode\"@;\n			if (inCurVO.isHaveSourceBill ()) {\n				//方法说明:可用量及时检查,可配置\n				setParameter ( \"INPREVOATP\",null);\n				runClassCom@ \"nc.bs.ic.pub.bill.ICATP\", \"checkAtpInstantly\", \"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVOATP:nc.vo.pub.AggregatedValueObject\"@;\n			}\n			//单据保存后动作统一处理\n			runClasscom@ \"nc.bs.ic.pub.BillActionBase\", \"afterSave\", \"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n			//####重要说明：生成的业务组件方法尽量不要进行修改####\n			if (inCurVO.isHaveSourceBill ()) {\n				//设置可用量参数\n				if (listbefore!=null)\n				setParameter ( \"LISTBEFORE\",listbefore);\n				//方法说明:修改可用量\n				runClassCom@ \"nc.bs.ic.pub.bill.ICATP\", \"modifyATPAfter\", \"&INCURVO:nc.vo.pub.AggregatedValueObject,&LISTBEFORE:java.util.ArrayList\"@;\n			}\n		}\n		catch (Exception e) {\n			//############################\n			//插入业务日志，该方法可以配置\n			setParameter ( \"EXC\",e.getMessage ());\n			setParameter ( \"FUN\", \"删除\");\n			runClassCom@ \"nc.bs.ic.pub.check.CheckBO\", \"insertBusinessExceptionlog\", \"&INCURVO:nc.vo.pub.AggregatedValueObject,&EXC:String,&FUN:String\"@;\n			//###########################\n			if (e instanceof BusinessException)\n			throw (BusinessException) e;\n			else\n			throw new BusinessException ( \"Remote Call\", e);\n		}\n		finally {\n			//####重要说明：生成的业务组件方法尽量不要进行修改####\n			//方法说明:库存出入库单据解业务锁\n			if (alLockedPK!=null) {\n				setParameter ( \"ALLPK\", (ArrayList)alLockedPK);\n				runClassCom@ \"nc.bs.ic.pub.bill.ICLockBO\", \"unlockBill\", \"&INCURVO:nc.vo.pub.AggregatedValueObject,&ALLPK:ArrayList\"@;\n			}\n			//##################################################\n		}\n	}\n	//插入业务日志，该方法可以配置\n	setParameter ( \"INCURVOS\",inCurVOs);\n	setParameter ( \"ERR\",sErr.toString ());\n	setParameter ( \"FUN\", \"删除\");\n	runClassCom@ \"nc.bs.ic.pub.check.CheckDMO\", \"insertBusinesslog\", \"&INCURVOS:nc.vo.ic.pub.bill.GeneralBillVO[],&ERR:String,&FUN:String\"@;\n	//############################\n	inCurVO  =null;\n	ArrayList alRet  =new ArrayList ();\n	if (sErr.toString ().trim ().length ()  ==0)\n	alRet.add (null);\n	else\n	alRet.add (sErr.toString ());\n	//alRet.add (retObj);\n	return retObj;\n	//************************************************************************\n"/*-=notranslate=-*/;}
/*
* 备注：设置脚本变量的HAS
*/
private void setParameter(String key,Object val)	{
	if (m_keyHas==null){
		m_keyHas=new Hashtable();
	}
	if (val!=null)	{
		m_keyHas.put(key,val);
	}
}
}
