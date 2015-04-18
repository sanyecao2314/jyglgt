package nc.bs.gt.gs.yhbalance;

import java.util.ArrayList;
import java.util.HashMap;

import nc.bs.jyglgt.dao.ServerDAO;
import nc.bs.logging.Logger;
import nc.vo.gt.gs.gs11.BalanceListBVO;
import nc.vo.gt.gs.gs11.ExAggSellPaymentJSVO;
import nc.vo.gt.gs.gs11.SellPaymentJSVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;


/**
 * 根据结算价目表取价销售优惠结算后台业务处理类
 * @author 施鹏
 * @version v1.0
 * */
public class YHBalanceBOByXJPrice extends ServerDAO{
	
	public static final String tablecode1 = "gt_balanceinfo_b";
	public static final String tablecode2 = "gt_balancelist_b";
	
	/**
	 * @param 销售优惠结算后台业务处理类
	 * @param ExAggSellPaymentJSVO aggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @throws ClassNotFoundException 
	 * @serialData 2011-7-6
	 */
	public void writebacktoBalance(ExAggSellPaymentJSVO aggvo,String pk_corp) throws BusinessException, ClassNotFoundException {	
			String sql_gathercode="select distinct gatheringcode from gt_balancelist_b where nvl(dr,0)=0 and pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"'";
   			ArrayList<HashMap<String, String>> list_gathercode=queryArrayBySql(sql_gathercode);
			String []gathercodes=null; //收款单号
   			if(list_gathercode!=null&&list_gathercode.size()>0){
   				gathercodes=new String[list_gathercode.size()];
   				for(int i=0;i<list_gathercode.size();i++){
   					HashMap<String, String> map_gathercode=(HashMap<String, String>)list_gathercode.get(i);
   					gathercodes[i]=map_gathercode.get("gatheringcode");
   				}
   			}
			StringBuffer listSql=new StringBuffer();
			listSql.append(" select pk_receiveway,sum(preferentialmny) preferentialmny")
			       .append(" from (select pk_measure_b,pk_receiveway, nvl(preferentialmny,0) preferentialmny")
				   .append(" from gt_balancelist_b where nvl(dr,0)=0 and nvl(preferentialmny,0)!=0  and  pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"'")//pk_balancelist_b in ("+combinArrayToString(pk_balancelist_bs)+")")
				   .append(" group by pk_measure_b,pk_receiveway, preferentialmny)a group by pk_receiveway ");
			ArrayList<SuperVO> list=getVOsfromSql(listSql.toString(), BalanceListBVO.class.getName());
			if(list!=null&&list.size()>0){
				A:for(int i=0;i<list.size();i++){
					BalanceListBVO bvo=(BalanceListBVO)list.get(i);
					UFDouble preferentialmny=bvo.getPreferentialmny()==null?new UFDouble(0):bvo.getPreferentialmny();
					String pk_receiveway=bvo.getPk_receiveway()==null?"":bvo.getPk_receiveway();
					if(preferentialmny.doubleValue()>0){
					    ArrayList<HashMap<String, String>> listMny=getMnyList2((SellPaymentJSVO)aggvo.getParentVO(),gathercodes,pk_receiveway);
					    UFDouble fhmny=preferentialmny; //优惠金额:A
					    UFDouble kyhmny=new UFDouble(0);//可正优惠金额:B
					    if(listMny!=null&&listMny.size()>0){
					    	//情况1:有可优惠金额
						    for(int j=0;j<listMny.size();j++){
						    	kyhmny=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)listMny.get(j)).get("kyhmny"))));
						    	String pk_credit=Toolkits.getString(((HashMap<String, String>)listMny.get(j)).get("pk_credit")); 
						    	//情况1-1:B	-A>=0 说明当条收款单余额够优惠
						    	if(kyhmny.sub(fhmny).doubleValue()>=0){
						    		//回写优惠金额到该收款单
						    		if (!Toolkits.isEmpty(pk_credit)) {
										StringBuffer updateSbf = new StringBuffer("");
										updateSbf.append("update gt_credit set fhmny=nvl(fhmny,0)-"+ fhmny.doubleValue()+ " ,jsmny=nvl(jsmny,0)-nvl("+fhmny.doubleValue()+",0) where pk_credit='" + pk_credit + "'");						
										updateBYsql(updateSbf.toString());
										continue A;
						    	   }
						    	}
						    	//情况1-2:B-A<0 说明当条收款单余额不够优惠
						    	else if(kyhmny.sub(fhmny).doubleValue()<0){
						    		//发掉部分优惠金额：优惠金额=B
						    		/******发掉部分优惠金额*******/
						    		if (!Toolkits.isEmpty(pk_credit)) {
										StringBuffer updateSbf = new StringBuffer("");
										updateSbf.append("update gt_credit set fhmny=nvl(fhmny,0)-"+ kyhmny.doubleValue()+ " ,jsmny=nvl(jsmny,0)-nvl("+kyhmny.doubleValue()+",0) where pk_credit='" + pk_credit + "'");						
										updateBYsql(updateSbf.toString());
						    		   //未结算的优惠额
										fhmny=fhmny.sub(kyhmny); //A=A-B
										
						    		//
						    		/*******继续下一条扣减优惠金额**********/
						    	  }
						        }
						    }	
					    }else{
				            //情况3:以上收款优惠金额扣减完毕，A>0 
						    //说明：1还有优惠金额未发完，只发掉部分优惠金额，但是已无可发金额
						    //      2收款无可优惠金额，优惠金额一点也没发掉
						    /****************所有情况最终在走到情况4时才会最终报警余额不足*******************************/
				            throw new BusinessException("优惠金额超出发货金额，结算失败");	
					    }
	            
				 	}else{
				 		//负优惠
					    UFDouble Kfhmny=new UFDouble(0);//可发货金额		
					    String pk_credit0="";
					    //先判断当前这条收款单是否有可够负优惠
					    ArrayList<HashMap<String,String>> list0=getMnyListFyh((SellPaymentJSVO)aggvo.getParentVO(),pk_receiveway);
					    if(list0!=null&&list0.size()>0){
					    	 Kfhmny=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list0.get(0)).get("kfhmny"))));
						     pk_credit0=((HashMap<String, String>)list0.get(0)).get("pk_credit"); 
					    }else{
					    	throw new BusinessException("客户余额不足,不能做负优惠！");
					    }
				    	//要增补到差额
					    UFDouble cemny = new UFDouble(0).sub(preferentialmny);			    	
				    	if((Kfhmny.sub(cemny)).doubleValue()>=0){
				    		StringBuffer updateSbf = new StringBuffer("");
							updateSbf.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ cemny.doubleValue()+ " ,jsmny=nvl(jsmny,0)+"+ cemny.doubleValue()+ " where pk_credit='" + pk_credit0 + "'");						
							updateBYsql(updateSbf.toString());
							continue A;
				    	}else{
				    		StringBuffer updateSbf = new StringBuffer("");
							updateSbf.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ Kfhmny.doubleValue()+ " ,jsmny=nvl(jsmny,0)+"+ Kfhmny.doubleValue()+ " where pk_credit='" + pk_credit0 + "'");						
							updateBYsql(updateSbf.toString());
							cemny = cemny.sub(Kfhmny);
							ArrayList<HashMap<String,String>> list_fyhmny=getMnyListFyh((SellPaymentJSVO)aggvo.getParentVO(),pk_receiveway);
						    if(list_fyhmny!=null&&list_fyhmny.size()>0){
						    	//情况1:有可发货金额
							    for(int k=0;k<list_fyhmny.size();k++){
							    	Kfhmny=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list_fyhmny.get(k)).get("kfhmny"))));
							    	String pk_credit=((HashMap<String, String>)list_fyhmny.get(k)).get("pk_credit"); 
							    	//情况1-1:B-A>=0 说明当条收款单余额够发货
							    	if(Kfhmny.doubleValue()-cemny.doubleValue()>=0){
										StringBuffer sb = new StringBuffer("");
										sb.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ cemny.doubleValue()+ " ,jsmny=nvl(jsmny,0)+"+ cemny.doubleValue()+ " where pk_credit='" + pk_credit + "'");						
										updateBYsql(sb.toString());
										//这时已回写全部,置成0.以便下面的赊销就不用判断了.
										cemny=new UFDouble(0);
										continue A;
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
							    		/*******继续下一条扣减发货金额**********/
							    	  }
							        }
							    }	
						    }else{
						    	throw new BusinessException("客户余额不足,不能做负优惠！");
						    }
				    	}	
				 	}

				 	}
		}
	}
	
	/**
	 * @param 销售优惠结算反操作后台业务处理类
	 * @param ExAggSellPaymentJSVO aggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @throws ClassNotFoundException 
	 * @serialData 2011-7-6
	 */
	public void deletebacktoBalance2(ExAggSellPaymentJSVO aggvo,String pk_corp) throws BusinessException, ClassNotFoundException {
			StringBuffer listSql_bvo=new StringBuffer();
			listSql_bvo.append(" select * ")
			   .append(" from gt_balancelist_b where nvl(dr,0)=0  and pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' ");
			ArrayList<SuperVO> list_bvo=getVOsfromSql(listSql_bvo.toString(), BalanceListBVO.class.getName());
			//结算反向操作
			if(list_bvo!=null&&list_bvo.size()>0){
				for(int i=0;i<list_bvo.size();i++){
					BalanceListBVO jsmny_bvo=(BalanceListBVO)list_bvo.get(i);
					UFDouble jsmny=jsmny_bvo.getVdef4()==null?new UFDouble(0):jsmny_bvo.getVdef4();//结算金额
			 		String queryYhmny=" select pk_credit from gt_credit where vbillcode='"+jsmny_bvo.getGatheringcode()+"' and pk_corp='"+pk_corp+"' and nvl(jsmny,0) - "+jsmny+">=0";
			 		String pk_credit=queryStrBySql(queryYhmny);
//			 		if(Toolkits.isEmpty(pk_credit)){throw new BusinessException("结算余额不足,可能被其他单据使用！");}
			 		String sql = " update gt_credit set  jsmny=nvl(jsmny,0) - nvl("+jsmny+",0),fhmny=nvl(fhmny,0) - nvl("+jsmny+",0) where pk_credit='"+pk_credit+"' ";
			 		updateBYsql(sql);
				 }
			}
			//删除结算明细数据
			String delSql=" delete from gt_balancelist_b where nvl(dr,0)=0  and  pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' ";
			//删除结信息数据
			String delSql_info=" delete from gt_balanceinfo_b where nvl(dr,0)=0  and  pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' ";
			updateBYsql(delSql);
			updateBYsql(delSql_info);
	}

	/**
	 * @param 销售结算反操作后台业务处理类
	 * @param ExAggSellPaymentJSVO aggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @throws ClassNotFoundException 
	 * @serialData 2012-2-23
	 */
	public void deletebacktoBalance3(ExAggSellPaymentJSVO aggvo,String pk_corp) throws BusinessException, ClassNotFoundException {
			StringBuffer listSql_bvo=new StringBuffer();
			listSql_bvo.append(" select * ")
			   .append(" from gt_balancelist_b where nvl(dr,0)=0  and pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' ");
			ArrayList<SuperVO> list_bvo=getVOsfromSql(listSql_bvo.toString(), BalanceListBVO.class.getName());
			//结算反向操作
			if(list_bvo!=null&&list_bvo.size()>0){
				for(int i=0;i<list_bvo.size();i++){
					BalanceListBVO jsmny_bvo=(BalanceListBVO)list_bvo.get(i);
					UFDouble jsmny=jsmny_bvo.getVdef4()==null?new UFDouble(0):jsmny_bvo.getVdef4();//结算金额
			 		String gatheringcode=getGatheringcode(jsmny_bvo.getPk_balancelist_b(),jsmny_bvo.getGatheringcode(),jsmny,pk_corp,jsmny_bvo.getPk_cumandoc());
			 		if(Toolkits.isEmpty(gatheringcode)){throw new BusinessException("结算余额不足,可能被其他单据使用！");}
			 		String sql = " update gt_credit set  fhmny=nvl(fhmny,0) + nvl("+jsmny+",0) where vbillcode='"+gatheringcode+"' and pk_corp='"+pk_corp+"' ";
			 		updateBYsql(sql);
				 }
			}
	}


	/**
	 * 获取可发货金额
	 * @param int i
	 * @param MeasureHeaderVO hvo
	 * @param String pk_receiveway
	 * */
	private String getGatheringcode(String pk_balancelist_b,String gatheringcode,UFDouble jsmny,String pk_corp,String pk_cumandoc) throws BusinessException{
		ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
		// 判断客户可发货剩余额=收款额 – 发货额 – 退款额 
		String sql=" select vbillcode from gt_credit where vbillcode='"+gatheringcode+"' and pk_corp='"+pk_corp+"' and nvl(skmny,0) - nvl(fhmny,0) - nvl(tkmny,0)-"+jsmny+">=0";
		String vbillcode=queryStrBySql(sql);
		if(Toolkits.isEmpty(vbillcode)){
			StringBuffer strSk=new StringBuffer("");
			strSk.append(" select vbillcode from gt_credit where (nvl(skmny,0) - nvl(fhmny,0) - nvl(tkmny,0)-"+jsmny+")>=0 ")
			   .append( " and receivetype in("+IJyglgtConst.RECEIVETYPE_QC+","+IJyglgtConst.RECEIVETYPE_YS+") and pk_cumandoc= '"+ pk_cumandoc+ "'  and vbillstatus='"+IJyglgtBillStatus.CHECKPASS+"' and pk_corp='"+pk_corp+"' and csaleid is null and nvl(dr,0)=0 ");
			strSk.append(" order by receivemnydate,vbillcode ");
			vbillcode = queryStrBySql(strSk.toString());
			String update="update gt_balancelist_b set gatheringcode='"+vbillcode+"' where pk_balancelist_b='"+pk_balancelist_b+"'";
			updateBYsql(update);
		}
		return vbillcode;
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
	
	/**
	 * 获取可发货金额
	 * @param int i
	 * @param MeasureHeaderVO hvo
	 * @param String pk_receiveway
	 * */
	private ArrayList<HashMap<String,String>> getMnyListFyh(SellPaymentJSVO hvo,String pk_receiveway) throws BusinessException{
		ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
		// 判断客户可发货剩余额=收款额 – 发货额 – 退款额 
		// 客户名称
		String cumandoc = Toolkits.getString(hvo.getPk_cumandoc());
		String pk_corp=hvo.getPk_corp()== null ? "" : hvo.getPk_corp().toString().trim();; 
		StringBuffer strSk=new StringBuffer("");
		strSk.append(" select vbillcode,pk_credit,(nvl(skmny,0) - nvl(fhmny,0) - nvl(tkmny,0))kfhmny,'Y'flag from gt_credit where (nvl(skmny,0) - nvl(fhmny,0) - nvl(tkmny,0))>0 ")
		   .append( " and receivetype in("+IJyglgtConst.RECEIVETYPE_QC+","+IJyglgtConst.RECEIVETYPE_YS+") and pk_cumandoc= '"+ cumandoc+ "' and pk_receiveway = '"+pk_receiveway+"' and vbillstatus='"+IJyglgtBillStatus.CHECKPASS+"' and pk_corp='"+pk_corp+"' and nvl(dr,0)=0 order by receivemnydate,vbillcode ");
		list = queryArrayBySql(strSk.toString());
		return list;
	}
	
	/**
	 * 获取可正优惠金额
	 * @param int i
	 * @param MeasureHeaderVO hvo
	 * @param String pk_receiveway
	 * */
	private ArrayList<HashMap<String,String>> getMnyList2(SellPaymentJSVO hvo,String[] gathercodes,String pk_receiveway) throws BusinessException{
		ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
		String cumandoc = Toolkits.getString(hvo.getPk_cumandoc());
		String pk_corp=hvo.getPk_corp()== null ? "" : hvo.getPk_corp().toString().trim();; 
		StringBuffer strSk=new StringBuffer("");
		strSk.append(" select vbillcode,pk_credit,(nvl(jsmny,0))kyhmny,'Y' flag from gt_credit where nvl(fhmny,0)>0 and nvl(jsmny,0)>0 ")
		   .append( " and receivetype in("+IJyglgtConst.RECEIVETYPE_QC+","+IJyglgtConst.RECEIVETYPE_YS+") and pk_cumandoc= '"+ cumandoc+ "' and vbillcode in ("+combinArrayToString(gathercodes)+") and pk_receiveway = '"+pk_receiveway+"' and vbillstatus='"+IJyglgtBillStatus.CHECKPASS+"' and pk_corp='"+pk_corp+"' and nvl(dr,0)=0 order by receivemnydate desc,vbillcode desc ");
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
	
//	private ArrayList<HashMap<String,String>> getMnyList3(SellPaymentJSVO hvo,String vbillcode) throws BusinessException{
//		ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
//		// 判断客户可退款，结算反向操作 
//		// 客户名称
//		String cumandoc = Toolkits.getString(hvo.getPk_cumandoc());
//		String pk_corp=hvo.getPk_corp()== null ? "" : hvo.getPk_corp().toString().trim();; 
//		StringBuffer strSk=new StringBuffer("");
//		strSk.append(" select vbillcode,pk_credit,(nvl(jsmny,0)kjsmny from gt_credit where nvl(jsmny,0)>0 and nvl(fhmny,0)>0 ")
//		   .append( " and receivetype in("+IGTConst.RECEIVETYPE_QC+","+IGTConst.RECEIVETYPE_SK+") and pk_cumandoc= '"+ cumandoc+ "' and vbillcode = '"+vbillcode+"' and vbillstatus='"+IGTBillStatus.CHECKPASS+"' and pk_corp='"+pk_corp+"' and nvl(dr,0)=0 order by receivemnydate,vbillcode desc ");
//		list = queryArrayBySql(strSk.toString());
//		return list;
//	}

		
}
