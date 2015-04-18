package nc.bs.gt.gs.yhbalance;

import java.util.ArrayList;
import java.util.HashMap;

import nc.bs.jyglgt.dao.ServerDAO;
import nc.bs.logging.Logger;
import nc.vo.gt.gs.gs11.BalanceListBVO;
import nc.vo.gt.gs.gs11.ExAggSellPaymentJSVO;
import nc.vo.gt.gs.gs11.SellPaymentJSVO;
import nc.vo.jyglgt.j400680.CreditVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;


/**
 * 根据结算价目表取价销售优惠结算后台业务处理类
 * @author 施鹏
 * @version v1.0
 * */
public class YHBalanceBOByJsPriceXH extends ServerDAO{
	
	public static final String tablecode1 = "gt_balanceinfo_b";
	public static final String tablecode2 = "gt_balancelist_b";
	
	/**
	 * @param 销售优惠结算后台业务处理类
	 * @param ExAggSellPaymentJSVO aggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @throws ClassNotFoundException 
	 * @serialData 2012-2-10
	 */
	public void writebacktoBalance(ExAggSellPaymentJSVO aggvo,String pk_corp,String vbillcode) throws BusinessException, ClassNotFoundException {	
			StringBuffer listSql=new StringBuffer();
			listSql.append(" select sum(nvl(preferentialmny,0)) preferentialmny")
				   .append(" from gt_balancelist_b where nvl(dr,0)=0 and nvl(preferentialmny,0)!=0  and  pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"'");
			ArrayList<SuperVO> list=getVOsfromSql(listSql.toString(), BalanceListBVO.class.getName());
			if(list!=null&&list.size()>0){
				BalanceListBVO bvo=(BalanceListBVO)list.get(0);
				UFDouble preferentialmny=bvo.getPreferentialmny()==null?new UFDouble(0):bvo.getPreferentialmny();
				if(preferentialmny.doubleValue()!=0){
					creatCredit(aggvo,pk_corp,preferentialmny,vbillcode);
				}
		}
	}
	
	
	public void creatCredit(ExAggSellPaymentJSVO avo,String pk_corp,UFDouble preferentialmny,String vbillcode) throws BusinessException{
		SellPaymentJSVO sjvo=(SellPaymentJSVO)avo.getParentVO();
		CreditVO newvo = new CreditVO();
		newvo.setPk_productline(sjvo.getPk_productline());//产品线
		newvo.setPk_custproduct(sjvo.getPk_custproduct());//产品用户
		newvo.setDmakedate(_getDate());
		newvo.setVbillstatus(IJyglgtBillStatus.CHECKPASS);
		newvo.setVbillcode(vbillcode);
		newvo.setReceivetype(Integer.parseInt(IJyglgtConst.RECEIVETYPE_YS));	
		newvo.setPk_cubasdoc(sjvo.getPk_cubasdoc());
		newvo.setPk_cumandoc(sjvo.getPk_cumandoc());
		newvo.setSkmny(preferentialmny);
		newvo.setBcskmny(preferentialmny);
		newvo.setBilltype(IJyglgtBillType.JY04);
		newvo.setPk_deptdoc(sjvo.getPk_deptdoc());
		if(!Toolkits.isEmpty(sjvo.getVdef7())){
			newvo.setPk_receiveway(sjvo.getVdef7());
		}else{
			newvo.setPk_receiveway(getPkBalanceway(pk_corp));
		}		
		newvo.setReceivemnydate(_getDate());
		newvo.setVdef13("Y");//sjvo.getPk_sellpaymentjs());
		newvo.setVdef14(sjvo.getPk_sellpaymentjs());
		newvo.setPk_corp(pk_corp);
		newvo.setDr(new Integer(0));
		newvo.setIsreceipt(new UFBoolean(false));
		newvo.setPk_operator(sjvo.getPk_operator());
		newvo.setMemo("销售优惠结算返利");
		insertVObackPK(newvo);
		
	}
	
    /**
 	 * 获得收款方式ID
 	 */
 	private String getPkBalanceway(String pk_corp) throws BusinessException{
 		StringBuffer sbf=new StringBuffer("");
 		sbf.append("select  pk_balanceway from gt_balanceway  where balancename='现汇' and pk_corp='"+pk_corp+"' and nvl(dr,0)=0 and nvl(balancestatus,'N')='N'");
 			return queryStrBySql(sbf.toString());
 	 }
 	

	
	/**
	 * @param 销售优惠结算反操作后台业务处理类
	 * @param ExAggSellPaymentJSVO aggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @throws ClassNotFoundException 
	 * @serialData 2012-2-10
	 */
	public void deletebacktoBalance(ExAggSellPaymentJSVO aggvo,String pk_corp) throws BusinessException, ClassNotFoundException {
		StringBuffer listSql=new StringBuffer();
		listSql.append(" select sum(nvl(preferentialmny,0)) preferentialmny")
			   .append(" from gt_balancelist_b where nvl(dr,0)=0 and nvl(preferentialmny,0)!=0  and  pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"'");
		ArrayList<SuperVO> list=getVOsfromSql(listSql.toString(), BalanceListBVO.class.getName());
		if(list!=null&&list.size()>0){
			BalanceListBVO bvo=(BalanceListBVO)list.get(0);
			UFDouble preferentialmny=bvo.getPreferentialmny()==null?new UFDouble(0):bvo.getPreferentialmny();
			if(preferentialmny.doubleValue()!=0){
				String sql=" select pk_credit from gt_credit where  vdef14='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' and vbillstatus=1 and nvl(dr,0)=0";
				String pk_credit =queryStrBySql(sql);
				if(!Toolkits.isEmpty(pk_credit)){
					throw new BusinessException("弃审失败：返利收款单已审核！");
				}
				String updateSql="delete gt_credit  where  vdef14='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' ";
				updateBYsql(updateSql);
			}
	    }

	}

	
    public static  String combinArrayToString(String[] pkvaluses) {
    	String str = "'";
    	if(pkvaluses != null){
    		for(int i=0;i<pkvaluses.length;i++){
    			if(i == pkvaluses.length-1){
    				str += pkvaluses[i]+"'";
    			}else{
    				str += pkvaluses[i] + "','";
    			}
    		}
    		return str;
    	}else{
    		return null;
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
	public void deletebacktoBalance(ExAggSellPaymentJSVO aggvo,String pk_corp,BalanceListBVO listbvo) throws BusinessException {
		try {
			UFDouble preferentialmny=new UFDouble(0);//优惠金额
			preferentialmny=listbvo.getPreferentialmny()==null?new UFDouble(0):listbvo.getPreferentialmny();
			if(preferentialmny.doubleValue()==0)return;
		 	if(preferentialmny.doubleValue()<0){
		 		UFDouble cemny = preferentialmny;
		 		//负优惠
		 		String sql = " update gt_credit set fhmny=nvl(fhmny,0)+nvl("+cemny+",0),jsmny=nvl(jsmny,0)+nvl("+cemny+",0) where vbillcode='"+listbvo.getGatheringcode()+"' and pk_corp='"+pk_corp+"' ";
		 		updateBYsql(sql);
		 	}else{
		 		//正优惠
			    UFDouble Kfhmny=new UFDouble(0);//可发货金额		
			    //先判断当前这条收款单是否有可够负优惠
			    String pk_credit0="";
			    ArrayList<HashMap<String,String>> list0=getMnyList((SellPaymentJSVO)aggvo.getParentVO(),listbvo.getGatheringcode());
			    if(list0!=null&&list0.size()>0){
			    	 Kfhmny=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list0.get(0)).get("kfhmny"))));
				     pk_credit0=((HashMap<String, String>)list0.get(0)).get("pk_credit"); 
			    }else{
			    	throw new BusinessException("客户余额不足,不能弃审！");
			    }
		    	//要增补到差额
			    UFDouble cemny = preferentialmny;
		    	
		    	if((Kfhmny.sub(cemny)).doubleValue()>=0){
		    		StringBuffer updateSbf = new StringBuffer("");
					updateSbf.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ cemny.doubleValue()+ " ,jsmny=nvl(jsmny,0)+"+ cemny.doubleValue()+ " where pk_credit='" + pk_credit0 + "'");						
					updateBYsql(updateSbf.toString());
					return;
		    	}else{
		    		StringBuffer updateSbf = new StringBuffer("");
					updateSbf.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ Kfhmny.doubleValue()+ " ,jsmny=nvl(jsmny,0)+"+ Kfhmny.doubleValue()+ " where pk_credit='" + pk_credit0 + "'");						
					updateBYsql(updateSbf.toString());
					cemny = cemny.sub(Kfhmny);						
				    ArrayList<HashMap<String,String>> list=getMnyList((SellPaymentJSVO)aggvo.getParentVO(),listbvo.getGatheringcode());
				    if(list!=null&&list.size()>0){
				    	//情况1:有可发货金额
					    for(int i=0;i<list.size();i++){
					    	Kfhmny=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list.get(i)).get("kfhmny"))));
					    	String pk_credit=((HashMap<String, String>)list.get(i)).get("pk_credit"); 
					    	//情况1-1:B-A>=0 说明当条收款单余额够发货
					    	if(Kfhmny.doubleValue()-cemny.doubleValue()>=0){
								StringBuffer sb = new StringBuffer("");
								sb.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ cemny.doubleValue()+ " ,jsmny=nvl(jsmny,0)+"+ cemny.doubleValue()+ " where pk_credit='" + pk_credit + "'");						
								cemny=new UFDouble(0);
								break;
					    	}						    	
					    	//情况1-2:B-A<0 说明当条收款单余额不够发货
					    	else if(Kfhmny.doubleValue()-cemny.doubleValue()<0){
					    		//发掉部分发货金额：发货金额=B
					    		/******发掉部分发货金额*******/
					    		if (!Toolkits.isEmpty(pk_credit)) {
									StringBuffer sb = new StringBuffer("");
									sb.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ Kfhmny.doubleValue()+ " ,jsmny=nvl(jsmny,0)+"+ Kfhmny.doubleValue()+ " where pk_credit='" + pk_credit + "'");						
									updateBYsql(sb.toString());
					    		//未结算的发货额
									cemny=cemny.sub(Kfhmny); //A=A-B
					    		//
					    		/*******继续下一条扣减发货金额**********/
					    	  }
					        }
					    }	
				    }else{
				    	throw new BusinessException("客户余额不足,不能做负优惠！");
				    }
		    	}
	            //情况3:以上收款、赊销的发货金额扣减完毕，A>0 
			    //说明：1还有发货金额未发完，只发掉部分发货金额，但是已无可发金额
			    /****************所有情况最终在走到情况4时才会最终报警余额不足*******************************/
		    	if(cemny.doubleValue()>0)
		    		throw new BusinessException("客户余额不足,删除可能会出现超收款情况！");		
		 	}
		} catch (BusinessException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	

	
	/**
	 * 获取可发货金额
	 * @param int i
	 * @param MeasureHeaderVO hvo
	 * @param String pk_receiveway
	 * */
	private ArrayList<HashMap<String,String>> getMnyList(SellPaymentJSVO hvo,String vbillcode) throws BusinessException{
		ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
		// 判断客户可发货剩余额=收款额 – 发货额 – 退款额 
		// 客户名称
		String cumandoc = Toolkits.getString(hvo.getPk_cumandoc());
		String pk_corp=hvo.getPk_corp()== null ? "" : hvo.getPk_corp().toString().trim();; 
		StringBuffer strSk=new StringBuffer("");
		strSk.append(" select vbillcode,pk_credit,(nvl(skmny,0) - nvl(fhmny,0) - nvl(tkmny,0))kfhmny,'Y'flag from gt_credit where (nvl(skmny,0) - nvl(fhmny,0) - nvl(tkmny,0))>0 ")
		   .append( " and receivetype in("+IJyglgtConst.RECEIVETYPE_QC+","+IJyglgtConst.RECEIVETYPE_YS+") and pk_cumandoc= '"+ cumandoc+ "' and vbillcode = '"+vbillcode+"' and vbillstatus='"+IJyglgtBillStatus.CHECKPASS+"' and pk_corp='"+pk_corp+"' and nvl(dr,0)=0 order by receivemnydate,vbillcode ");
		list = queryArrayBySql(strSk.toString());
		return list;
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
	
		
}
