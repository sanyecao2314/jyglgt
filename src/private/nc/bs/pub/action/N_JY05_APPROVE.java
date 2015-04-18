package nc.bs.pub.action;

import nc.vo.pub.pf.PfUtilActionVO;
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
 * 备注：销售价格政策的审批
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2014-9-29)
 * @author 平台脚本生成
 */
public class N_JY05_APPROVE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_JY02_APPROVE 构造子注解。
 */
public N_JY05_APPROVE() {
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
Object retObj  =null;	
setParameter ( "billVO",getVo ());
setParameter ( "paraVO", vo);
setParameter ( "billType",getBillType ());
//####重要说明：生成的业务组件方法尽量不要进行修改####	
//####该组件为单动作工作流处理开始...不能进行修改####
Object m_sysflowObj= procActionFlow(vo);
	if (m_sysflowObj!=null){
		return m_sysflowObj;
	}
retObj  =getVo ();
//##################################################	
return retObj;
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
	return "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####	\nObject retObj  =null;	\nsetParameter ( \"billVO\",getVo ());\nsetParameter ( \"paraVO\", vo);\nsetParameter ( \"billType\",getBillType ());\n//####重要说明：生成的业务组件方法尽量不要进行修改####	\n//####该组件为单动作工作流处理开始...不能进行修改####\nprocActionFlow@@;\nretObj  =getVo ();\n//##################################################	\nreturn retObj;\n";}
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
