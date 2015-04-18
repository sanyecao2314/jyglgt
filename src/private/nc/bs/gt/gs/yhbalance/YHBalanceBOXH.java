package nc.bs.gt.gs.yhbalance;


import nc.bs.jyglgt.dao.ServerDAO;
import nc.bs.logging.Logger;
import nc.vo.gt.gs.gs11.BalanceListBVO;
import nc.vo.gt.gs.gs11.ExAggSellPaymentJSVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;


/**
 * 销售优惠结算后台业务处理类
 * @author 施鹏
 * @version v1.0
 * */
public class YHBalanceBOXH extends ServerDAO{
	
	public static final String tablecode1 = "gt_balanceinfo_b";
	public static final String tablecode2 = "gt_balancelist_b";
	
	/**
	 * @param 销售优惠结算后台业务处理类
	 * @param ExAggSellPaymentJSVO aggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-7-6
	 */
	public void writebacktoBalance(ExAggSellPaymentJSVO aggvo,String pk_corp,String vbillcode) throws BusinessException {
		YHBalanceBOByJsPriceXH boprice=new YHBalanceBOByJsPriceXH();
		try {
			boprice.writebacktoBalance(aggvo, pk_corp,vbillcode);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			   throw new BusinessException(e.getMessage());	
		}
	}
	
	/**
	 * @param 销售优惠结算反操作后台业务处理类
	 * @param ExAggSellPaymentJSVO aggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-7-6
	 */
	public void deletebacktoBalance(ExAggSellPaymentJSVO aggvo,String pk_corp) throws BusinessException {
		
		try {
			YHBalanceBOByJsPriceXH yhbo=new YHBalanceBOByJsPriceXH();
			yhbo.deletebacktoBalance(aggvo, pk_corp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

	}
	
	
	public String getBalanceWay(String vbillcode,String pk_corp) throws BusinessException{
		String sql = "select pk_receiveway from gt_credit where vbillcode='"+vbillcode+"' and pk_corp='"+pk_corp+"'";
		String pk_receiveway = queryStrBySql(sql);
		if(pk_receiveway==null||pk_receiveway.equals("")){
			Logger.debug(sql);
			throw new BusinessException("查询收款方式错误");
		}
		return pk_receiveway;
	}
		
	/**
	 * @功能 销售优惠结算 保存时回写 结算临时表
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-7-4
	 */
	public boolean writeBackToBalanceTemp_yhsave(ExAggSellPaymentJSVO iexaggvo,String pk_corp)throws BusinessException {
		boolean flag=false;
		try {
			//回写结算表
			BalanceListBVO[] listbvos = (BalanceListBVO[]) iexaggvo.getTableVO("gt_balancelist_b");			
			for(int i=0;i<listbvos.length;i++){
				if(listbvos[i].getStatus()==VOStatus.DELETED){
					continue;
				}							
				String pk_upper_b = listbvos[i].getPk_upper_b();
				String sql= " update gt_balancelist_b set vdef18='Y' where pk_balancelist_b='"+pk_upper_b+"' ";
				updateBYsql(sql);			
			}
			String sql2 = " delete from  gt_balancelist_b where vdef19='"+iexaggvo.getParentVO().getAttributeValue("pk_operator")+"' " ;
			updateBYsql(sql2);
			flag= true;
		} catch (Exception e) { 
			e.printStackTrace();
			flag= false;
			throw new BusinessException(e);
		}
		return flag;
	}
	
	/**
	 * @功能 销售优惠结算反操作 删除时回写 结算临时表
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-7-4
	 */
	public boolean deleteBackToBalanceTemp_yhdel(ExAggSellPaymentJSVO iexaggvo,String pk_corp) throws BusinessException {
		boolean flag=false;
		try {
			BalanceListBVO[] listbvos = (BalanceListBVO[]) iexaggvo.getTableVO("gt_balancelist_b");			
			for(int i=0;i<listbvos.length;i++){
				String pk_upper_b = listbvos[i].getPk_upper_b();
				//删除时的反操作
				String sql = " update gt_balancelist_b  set vdef18=null where pk_balancelist_b='"+pk_upper_b+"' ";
				updateBYsql(sql);
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
			throw new BusinessException(e);
		}
		return flag;
	}
}
