package nc.ui.jyglgt.j40068c;


import java.util.ArrayList;
import java.util.HashMap;
import nc.pub.jyglgt.proxy.Proxy;
import nc.vo.gt.gs.gs11.BalanceInfoBVO;
import nc.vo.gt.gs.gs11.SellPaymentJSVO;
import nc.vo.ic.pub.bill.GeneralBillHeaderVO;
import nc.vo.ic.pub.bill.GeneralBillItemVO;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.pub.HYBillVO;

/**
 * 对照工具类
 * @author 施鹏
 * @version v1.0
 * */
public class IssueToBalanceTool {

	
	/**
	 * 上下游VO对照，返回对照结果VO
	 * */
	public static HYBillVO upToDownHybillVO(GeneralBillVO upvo){
		HYBillVO downvo=new HYBillVO();
        downvo.setParentVO(getHeadVO(upvo));
        downvo.setChildrenVO(getBodyVO(upvo));
		return downvo;
	}
	
	/**
	 * 上下游表头VO对照，返回对照结果主表VO
	 * */
	public static SuperVO getHeadVO(GeneralBillVO upvo){
		GeneralBillHeaderVO hvo_up=(GeneralBillHeaderVO)upvo.getParentVO();
		SellPaymentJSVO hvo_down=new SellPaymentJSVO();
		hvo_down.setPk_cumandoc(hvo_up.getCcustomerid());
		hvo_down.setBilltype(IJyglgtBillType.JY06);		
		hvo_down.setVdef9(hvo_up.getVbillcode());
		return hvo_down;
	} 
	
	/**
	 * 上下游表体VO对照，返回对照结果子表VO
	 * */
	public static SuperVO[] getBodyVO(GeneralBillVO upvo){
		GeneralBillItemVO[] bvo_up=(GeneralBillItemVO[])upvo.getChildrenVO();
		BalanceInfoBVO[] bvo_down=new BalanceInfoBVO[bvo_up.length];
		for(int i=0;i<bvo_up.length;i++){
			bvo_down[i]=new BalanceInfoBVO();
			bvo_down[i].setPk_invmandoc(bvo_up[i].getCinventoryid());
			bvo_down[i].setPk_invbasdoc(bvo_up[i].getCinvbasid());
			bvo_down[i].setVfree1(bvo_up[i].getVfree1());
			bvo_down[i].setVfree2(bvo_up[i].getVfree2());
			bvo_down[i].setVfree3(bvo_up[i].getVfree3());
			bvo_down[i].setVfree4(bvo_up[i].getVfree4());
			bvo_down[i].setVdef14(bvo_up[i].getVbatchcode());
			bvo_down[i].setVdef12(bvo_up[i].getCgeneralbid());
			bvo_down[i].setNum(bvo_up[i].getNoutassistnum());
			bvo_down[i].setSuttle(bvo_up[i].getNoutnum());
			String sql="select 	noriginalcurtaxprice,	noriginalcursummny,	b.disprice,noriginalcurtaxprice-nvl(b.disprice,0) jsprice,c.vdef8,c.pk_defdoc8 from so_saleorder_b a left join 	jyglgt_pricediscount_b b"+
			" on a.corder_bid=b.vdef6 and nvl(b.dr,0)=0 "+
			" left join so_saleexecute c on a.corder_bid=c.csale_bid   and nvl(c.dr,0)=0"+
			" where corder_bid='"+bvo_up[i].getCsourcebillbid()+"' and nvl(a.dr,0)=0 "; 
		    try {
				ArrayList list=Proxy.getItf().queryArrayBySql(sql);
				if(list!=null&&list.size()>0){
					HashMap map=(HashMap)list.get(0);
					UFDouble price=Toolkits.getUFDouble(map.get("noriginalcurtaxprice"));
					UFDouble jsprice=Toolkits.getUFDouble(map.get("jsprice"));
					UFDouble jsmny=jsprice.multiply(bvo_up[i].getNoutnum());
					UFDouble mny=Toolkits.getUFDouble(map.get("noriginalcursummny"));
					UFDouble yhprice=Toolkits.getUFDouble(map.get("disprice"));
					UFDouble yhmny=yhprice.multiply(bvo_up[i].getNoutnum());
					bvo_down[i].setPrice(price);
					bvo_down[i].setVdef1(jsprice);
					bvo_down[i].setMoneys(mny);
					bvo_down[i].setVdef2(jsmny);
					bvo_down[i].setPreferentialprice(yhprice);
					bvo_down[i].setPreferentialmny(yhmny);
					bvo_down[i].setVdef11(Toolkits.getString(map.get("pk_defdoc8")));
				}
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}
		
		return bvo_down;
	} 
	
	
	/**
	 * 上下游表体VO对照，返回对照结果子表VO
	 * */
	public static BalanceInfoBVO[] getBodyVOBySql(String pk_cumandoc,String pcbillcode){
		BalanceInfoBVO[] bvo_down=null;
		StringBuffer sql=new StringBuffer();
		sql.append(" select * ") 
		.append("   from ic_general_b ") 
		.append("  where cgeneralhid in (select cgeneralhid ") 
		.append("                          from ic_general_h ") 
		.append("                         where cbilltypecode = '4C' ") 
		.append("                           and nvl(dr, 0) = 0 ") 
		.append("                           and ccustomerid = '"+pk_cumandoc+"' ") 
		.append("                           and fbillflag = '3' ") 
		.append("                           and cdilivertypeid = '"+pcbillcode+"') ") ;
        try {
			ArrayList list=Proxy.getItf().getVOsfromSql(sql.toString(), GeneralBillItemVO.class.getName());
			if(list!=null&&list.size()>0){
				bvo_down=new BalanceInfoBVO[list.size()];	
				for(int i=0;i<list.size();i++){
					GeneralBillItemVO bvo_up=(GeneralBillItemVO)list.get(i);
					bvo_down[i]=new BalanceInfoBVO();
					bvo_down[i].setPk_invmandoc(bvo_up.getCinventoryid());
					bvo_down[i].setPk_invbasdoc(bvo_up.getCinvbasid());
					bvo_down[i].setVfree1(bvo_up.getVfree1());
					bvo_down[i].setVfree2(bvo_up.getVfree2());
					bvo_down[i].setVfree3(bvo_up.getVfree3());
					bvo_down[i].setVfree4(bvo_up.getVfree4());
					bvo_down[i].setVdef14(bvo_up.getVbatchcode());
					bvo_down[i].setVdef12(bvo_up.getCgeneralbid());
					bvo_down[i].setNum(bvo_up.getNoutassistnum());
					bvo_down[i].setSuttle(bvo_up.getNoutnum());
					String sql2="select 	noriginalcurtaxprice,	noriginalcursummny,	b.disprice,noriginalcurtaxprice-nvl(b.disprice,0) jsprice,c.vdef8,c.pk_defdoc8 from so_saleorder_b a left join 	jyglgt_pricediscount_b b"+
					" on a.corder_bid=b.vdef6 and nvl(b.dr,0)=0 "+
					" left join so_saleexecute c on a.corder_bid=c.csale_bid   and nvl(c.dr,0)=0"+
					" where corder_bid='"+bvo_up.getCsourcebillbid()+"' and nvl(a.dr,0)=0 "; 
					ArrayList list2=Proxy.getItf().queryArrayBySql(sql2);
					if(list2!=null&&list2.size()>0){
						HashMap map=(HashMap)list2.get(0);
						UFDouble price=Toolkits.getUFDouble(map.get("noriginalcurtaxprice"));
						UFDouble jsprice=Toolkits.getUFDouble(map.get("jsprice"));
						UFDouble jsmny=jsprice.multiply(bvo_up.getNoutnum());
						UFDouble mny=Toolkits.getUFDouble(map.get("noriginalcursummny"));
						UFDouble yhprice=Toolkits.getUFDouble(map.get("disprice"));
						UFDouble yhmny=yhprice.multiply(bvo_up.getNoutnum());
						bvo_down[i].setPrice(price);
						bvo_down[i].setVdef1(jsprice);
						bvo_down[i].setMoneys(mny);
						bvo_down[i].setVdef2(jsmny);
						bvo_down[i].setPreferentialprice(yhprice);
						bvo_down[i].setPreferentialmny(yhmny);
						bvo_down[i].setVdef11(Toolkits.getString(map.get("pk_defdoc8")));
					}
				}
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return bvo_down;
	} 
	
}
