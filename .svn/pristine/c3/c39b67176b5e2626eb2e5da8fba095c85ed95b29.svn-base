package nc.ui.jyglgt.j40068d;


import java.util.ArrayList;

import nc.pub.jyglgt.proxy.Proxy;
import nc.vo.gt.gs.gs11.BalanceInfoBVO;
import nc.vo.gt.gs.gs11.SellPaymentJSVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.HYBillVO;

/**
 * 对照工具类
 * @author 施鹏
 * @version v1.0
 * */
public class JY06TOJY07Tool {

	
	/**
	 * 上下游VO对照，返回对照结果VO
	 * */
	public static HYBillVO upToDownHybillVO(HYBillVO upvo){
		HYBillVO downvo=new HYBillVO();
        downvo.setParentVO(getHeadVO(upvo));
        try {
			downvo.setChildrenVO(getBodyVO(upvo));
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return downvo;
	}
	
	/**
	 * 上下游表头VO对照，返回对照结果主表VO
	 * */
	public static SuperVO getHeadVO(HYBillVO upvo){
		SellPaymentJSVO hvo_up=(SellPaymentJSVO)upvo.getParentVO();
		SellPaymentJSVO hvo_down=new SellPaymentJSVO();
		hvo_down.setPk_cumandoc(hvo_up.getPk_cumandoc());
		hvo_down.setBilltype(IJyglgtBillType.JY07);
		hvo_down.setPk_corp(hvo_up.getPk_corp());
		hvo_down.setDr(new Integer(0));
		return hvo_down;
	} 
	
	/**
	 * 上下游表体VO对照，返回对照结果子表VO
	 * @throws ClassNotFoundException 
	 * @throws BusinessException 
	 * */
	public static SuperVO[] getBodyVO(HYBillVO upvo) throws BusinessException, ClassNotFoundException{
		BalanceInfoBVO[] bvo_up=(BalanceInfoBVO[])upvo.getChildrenVO();
		if(bvo_up!=null){
			BalanceInfoBVO[] bvo_down=new BalanceInfoBVO[bvo_up.length];
			for(int i=0;i<bvo_up.length;i++){
				bvo_down[i]=new BalanceInfoBVO();
				bvo_down[i].setPk_invmandoc(bvo_up[i].getPk_invmandoc());
				bvo_down[i].setPk_invbasdoc(bvo_up[i].getPk_invbasdoc());
				bvo_down[i].setVfree1(bvo_up[i].getVfree1());
				bvo_down[i].setVfree2(bvo_up[i].getVfree2());
				bvo_down[i].setVfree3(bvo_up[i].getVfree3());
				bvo_down[i].setVfree4(bvo_up[i].getVfree4());
				bvo_down[i].setVdef14(bvo_up[i].getVdef14());
				bvo_down[i].setVdef12(bvo_up[i].getVdef12());
				bvo_down[i].setNum(bvo_up[i].getNum());
				bvo_down[i].setSuttle(bvo_up[i].getSuttle());
				bvo_down[i].setPrice(bvo_up[i].getPrice());
				bvo_down[i].setVdef1(bvo_up[i].getVdef1());
				bvo_down[i].setMoneys(bvo_up[i].getMoneys());
				bvo_down[i].setVdef2(bvo_up[i].getVdef2());
//				bvo_down[i].setPreferentialprice(bvo_up[i].getPreferentialprice());
//				bvo_down[i].setPreferentialmny(bvo_up[i].getPreferentialmny());
				bvo_down[i].setVdef11(bvo_up[i].getVdef11());
				bvo_down[i].setVdef15(bvo_up[i].getPk_balanceinfo_b());
			}
			return bvo_down;
		}else{
			SellPaymentJSVO hvo_up=(SellPaymentJSVO)upvo.getParentVO();
			String sql="select  * from gt_balanceinfo_b where pk_sellpaymentjs='"+hvo_up.getPk_sellpaymentjs()+"' and nvl(dr,0)=0";
			ArrayList list=Proxy.getItf().getVOsfromSql(sql, BalanceInfoBVO.class.getName());
			if(list!=null&&list.size()>0){
				BalanceInfoBVO[] bvo_down=new BalanceInfoBVO[list.size()];
				for(int i=0;i<list.size();i++)
				{
					BalanceInfoBVO bvo_upinfo=(BalanceInfoBVO)list.get(i);
					bvo_down[i]=new BalanceInfoBVO();
					bvo_down[i].setPk_invmandoc(bvo_upinfo.getPk_invmandoc());
					bvo_down[i].setPk_invbasdoc(bvo_upinfo.getPk_invbasdoc());
					bvo_down[i].setVfree1(bvo_upinfo.getVfree1());
					bvo_down[i].setVfree2(bvo_upinfo.getVfree2());
					bvo_down[i].setVfree3(bvo_upinfo.getVfree3());
					bvo_down[i].setVfree4(bvo_upinfo.getVfree4());
					bvo_down[i].setVdef14(bvo_upinfo.getVdef14());
					bvo_down[i].setVdef12(bvo_upinfo.getVdef12());
					bvo_down[i].setNum(bvo_upinfo.getNum());
					bvo_down[i].setSuttle(bvo_upinfo.getSuttle());
					bvo_down[i].setPrice(bvo_upinfo.getPrice());
					bvo_down[i].setVdef1(bvo_upinfo.getVdef1());
					bvo_down[i].setMoneys(bvo_upinfo.getMoneys());
					bvo_down[i].setVdef2(bvo_upinfo.getVdef2());
//					bvo_down[i].setPreferentialprice(bvo_up[i].getPreferentialprice());
//					bvo_down[i].setPreferentialmny(bvo_up[i].getPreferentialmny());
					bvo_down[i].setVdef11(bvo_upinfo.getVdef11());
					bvo_down[i].setVdef15(bvo_upinfo.getPk_balanceinfo_b());
				}
				return bvo_down;
			}else{
				return null;
			}
		}
	} 
	
}
