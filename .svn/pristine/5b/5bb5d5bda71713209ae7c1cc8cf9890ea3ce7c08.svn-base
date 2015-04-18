package nc.itf.jyglgt.pub;

import java.util.ArrayList;
import java.util.List;

import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.vo.gt.gs.gs11.ExAggSellPaymentJSVO;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.jyglgt.pub.BillMakeVO;
import nc.vo.jyglgt.pub.GDTBillcodeRuleVO;
import nc.vo.jyglgt.pub.MyBillTempBVO;
import nc.vo.jyglgt.pub.MyBillTempTVO;
import nc.vo.jyglgt.pub.MyBillTempVO;
import nc.vo.jyglgt.pub.Toolkits.IDatasourceConst;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.billtype.BilltypeVO;
import nc.vo.sm.funcreg.FuncRegisterVO;
import nc.vo.trade.pub.IExAggVO;

/**
 * @author  公共开发者 2012-1-5
 * @类型名  : ICBITF
 * @功能 : 公共接口
 * @版本 : v1.0
 */
public interface IJyglgtItf {
	/**
	 * 功能:保存VO数组带PK
	 * @param objs SuperVO子类
	 * @throws BusinessException
	 */
	public void insertVOsWithPK(Object[] objs)throws BusinessException;
	/**
	 * 功能:保存VO数组不带PK 
	 * @param objs SuperVO子类
	 * @throws BusinessException
	 */
	public void insertVOsArr(Object[] objs)throws BusinessException;
	/**
	 * 功能:删除VO数组
	 * @param objs SuperVO子类
	 * @throws BusinessException
	 */
	public void deleteVOsArr(Object[] objs)throws BusinessException;
	/**
	 * 功能:删除VO
	 * @param obj SuperVO子类
	 * @throws BusinessException
	 */
	public void deleteVO(Object obj)throws BusinessException;
	/**
	 * 功能:更新VO数组
	 * @param objs SuperVO子类
	 * @throws BusinessException
	 */
	public void updateVOsArr(Object[] objs)throws BusinessException;
	/**
	 * 功能:更新VO
	 * @param obj SuperVO子类
	 * @throws BusinessException
	 */
	public void updateVO(Object obj)throws BusinessException;
	/**
	 * 功能:保存VO返回PK
	 * @param obj SuperVO子类
	 * @return VO的pk
	 * @throws BusinessException
	 */
	public String insertVObackPK(Object obj)throws BusinessException;
	/**
	 *功能:执行update语句
	 * @param sql update语句
	 * @throws BusinessException
	 */
	public void updateBYsql(String sql)throws BusinessException;
	/**
	 * 功能:执行query根据不同结果集返回不同的对象
	 * @param sql update语句
	 * @throws BusinessException
	 * BusinessException
	 */
	public Object queryBySql(String sql,SQLParameter parm,ResultSetProcessor rsp)throws BusinessException;
	/**
	 * 功能:根据sql执行query
	 * @param sql update语句
	 * @throws BusinessException
	 */
	public String queryStrBySql(String sql)throws BusinessException;
  
	/**
	 * 功能：由SQL和VO，查询出记录 
	 * @param sql 查询语句
	 * @param voname VO名称
	 * @return ArrayList
	 * @throws BusinessException
	 * @throws ClassNotFoundException 
	 * @author shipeng 2010-3-11 13:11:43
	 */
	@SuppressWarnings("unchecked")
	public  ArrayList getVOsfromSql(String sql,String voname) throws BusinessException, ClassNotFoundException;	
    /**
     * 功能:根据sql执行返回ArrayList,ArrayList里为HashMap 
     * @param sql query语句
     * @throws BusinessException
     * @author shipeng 2010-3-11 13:11:43
     */
    @SuppressWarnings("unchecked")
	public ArrayList queryArrayBySql(String sql)throws BusinessException;
 
    /**
     * @功能： 为了生成唯一标识 
     * @param pk_corp 公司主键
     * @param pkNum	  要生成的PK数量
     * @return PK数组
     */
    public String[] getPKFromDB(String pk_corp, int pkNum);

	/**
      *更新聚合Vo 
      */
	public void updateAggregatedValueVO(AggregatedValueObject obj)throws BusinessException;
	/**
	 * 保存聚合Vo
	 * @param AggregatedValueObject obj
	 * @author shipeng
	 * @throws BusinessException
	 * */
	public String insertAggregatedValueVO(AggregatedValueObject obj)throws BusinessException;
	/**
	 * 针对有表体增删行修改时保存的数据，表体增行数据插入，删行数据更新删除标记
	 * */
	public void updateAggVOToEdit(AggregatedValueObject obj,String hid,String bid,String btable)throws BusinessException;


	/**
	 * 根据公司主键得到单据主键
	 * @param pk_corp 公司主键
	 * @serialData 2011-8-23
	 * @author guoyt
	 */
	public String getOID(String pk_corp) throws BusinessException;
	
	/**
	 * 根据公司主键得到单据主键
	 * @param pk_corp 公司主键
	 * @serialData 2011-8-23
	 * @author guoyt
	 */
	public String[] getMoreOID(String pk_corp,int pkcount) throws BusinessException;
	
	   /**
     * 功能:根据sql执行query查询value是否存在于tabName中field中,要求被查询的表中必须有dr字段
     * @param tabName
     * @param field 表中字段  
     * @param value
     * @return true被引用,false未引用
     * @throws BusinessException
     * @author shipeng 2011-6-3 14:07:43
     */
    public  boolean existValue(String tabName,String field,String value) throws BusinessException;
	
	/**
	 * @param obj
	 * @throws BusinessException
	 * @功能 保存多子表聚合Vo(多子表IEXAggvo里要重写getAllChildrenVO()方法)
	 *     (多子表IEXAggvo里要重写getAllChildrenVO()方法)
	 */
	public void insertIEXAggregatedValueVO(IExAggVO obj, String[] tablecodes)
			throws BusinessException;

	/**
	 * 多单据多子表数据插入
	 * @author 施鹏
	 * @time 2014-09-14
	 * */
	public AggregatedValueObject[] insertIEXAggVO(AggregatedValueObject[] avos)throws BusinessException;


	/**
	 * 主子表数据保存
	 * @author 施鹏
	 * @time 2014-04-24
	 * */
	public AggregatedValueObject saveHYBillVO(AggregatedValueObject avo,AggregatedValueObject checkvo)throws BusinessException;
	
	/**
	 * 判断系统自动化收货通知是否存在
	 * @author 施鹏
	 * @time 2014-09-18
	 * @throws BusinessException 
	 * */
	public boolean isExitByBfJLArriveNote(String datasource,String vbillcode,String bpk) throws BusinessException;
	
	/**
	 * 获取自动化收获榜单数据
	 * @author 施鹏
	 * @time 2014-09-18
	 * */
	public void insertIntePoStorByBfgl(String datasource)throws BusinessException;
	
	/**
	 * 获取自动化采购结算单数据
	 * @author 施鹏
	 * @time 2014-09-24
	 * */
	public void insertIntePoVoiceByBfgl(String datasource)throws BusinessException;
	
	/**
	 * 获取自动化收获榜单数据-销售出库
	 * @author 施鹏
	 * @time 2014-09-18
	 * */
	public void insertInteSaleoutByBfgl(String datasource)throws BusinessException;
	
	/**
	 * 获取自动化收获榜单数据-调拨出库
	 * @author 施鹏
	 * @time 2014-09-18
	 * */
	public void insertInteTransoutByBfgl(String datasource)throws BusinessException;
	
	/**
	 * 获取自动化运费结算数据-存货核算调整单
	 * @author 施鹏
	 * @time 2014-09-18
	 * */
	public void insertInteFreightByBfgl(String datasource)throws BusinessException;
	
	
	public void autoCreateBill(BilltypeVO btvo, GDTBillcodeRuleVO trvo, MyBillTempVO thvo,List<MyBillTempBVO> bvolist, List<MyBillTempTVO> tvolist, FuncRegisterVO fvo, BillMakeVO hvo) throws BusinessException;
	 
	/**
	 * 测试中间库是否能够连接
	 * @author fans 
	 * @return
	 */
	public boolean testConnMiddleDB();
	
	/**
	 * 更新中间表数据
	 * @author fans
	 * @param sql
	 * @return
	 */
	public void updateMiddleDB(GeneralBillVO vo) throws BusinessException;
	/**
	 * @功能 销售货款结算 保存时回写 结算临时表,回写收款余额表结算额
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-7-4
	 */
	
	public void updateMiddleDBByBFJL(String sql)throws BusinessException;
	
	public void writeBackToBalanceTemp(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator)throws BusinessException;
	/**
	 * @功能 销售货款结算反操作 保存时回写 结算临时表,回写收款余额表结算额
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-7-4
	 */
	public void deleteBackToBalanceTemp(ExAggSellPaymentJSVO iexaggvo,String pk_corp) throws BusinessException;
	/**
	 * @功能 销售货款结算 保存时回写 结算临时表
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-9-20
	 */
	public boolean writeBackToBalanceTemp_save(ExAggSellPaymentJSVO iexaggvo,String pk_corp)throws BusinessException;
	/**
	 * @功能 销售货款结算反操作 删除时回写 结算临时表
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-9-20
	 */
	public boolean deleteBackToBalanceTemp_del(ExAggSellPaymentJSVO iexaggvo,String pk_corp) throws BusinessException ;
	
	/**
	 * @功能 销售结算 保存时回写收款通知并生成结算明细
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public boolean writeBackToCreditAndBalanceDetail(ExAggSellPaymentJSVO iexaggvo,String pk_corp)throws BusinessException;
	/**
	 * @功能 销售结算 删除时时回写收款通知并删除结算明细
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public boolean delBackToCreditAndBalanceDetail(ExAggSellPaymentJSVO iexaggvo,String pk_corp)throws BusinessException;
	
	/**
	 * @功能 销售货款结算 审核时回写收款余额表结算额
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public void writeBackToBalance(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator)throws BusinessException;

	/**
	 * @功能 销售货款结算反操作 弃审时回写 收款余额表结算额
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public void deleteBackToBalance(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator) throws BusinessException;
	
	/**
	 * @功能 销售优惠结算 审核时回写收款余额表结算额
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public void writeBackToBalanceYh(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator)throws BusinessException;

	/**
	 * @功能 销售优惠结算反操作 弃审时回写 收款余额表结算额
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public void deleteBackToBalanceYh(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator) throws BusinessException;
}  

    
