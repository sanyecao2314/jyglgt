package nc.bs.gt.gs.balance;

import java.util.ArrayList;
import java.util.HashMap;
import nc.bs.gt.gs.yhbalance.YHBalanceBOByJsPrice;
import nc.bs.gt.gs.yhbalance.YHBalanceBOByJsPriceXH;
import nc.bs.gt.gs.yhbalance.YHBalanceBOByXJPrice;
import nc.bs.jyglgt.dao.ServerDAO;
import nc.vo.gt.gs.gs11.BalanceInfoBVO;
import nc.vo.gt.gs.gs11.BalanceListBVO;
import nc.vo.gt.gs.gs11.ExAggSellPaymentJSVO;
import nc.vo.gt.gs.gs11.SellPaymentJSVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtConst;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFDouble;


/**
 * 销售货款结算后台处理类
 * @author sp
 * @version v1.0
 * */
public class BalanceBO extends ServerDAO {
	
	public static final String tablecode1 = "gt_balanceinfo_b";
	public static final String tablecode2 = "gt_balancelist_b";
	/**
	 * @功能 销售货款结算 保存时回写 结算临时表,回写收款余额表结算额
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-7-4
	 */
	public void writeBackToBalanceTemp(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator)throws BusinessException {
		try {
			SellPaymentJSVO  hvo=(SellPaymentJSVO)iexaggvo.getParentVO();
			if(hvo.getVdef0()!=null&&hvo.getVdef0().equals("提单销售")){
				//回写结算临时表
				BalanceListBVO[] listbvos = (BalanceListBVO[]) iexaggvo.getTableVO("gt_balancelist_b");			
				for(int i=0;i<listbvos.length;i++){
					if(listbvos[i].getStatus()==VOStatus.DELETED){
						continue;
					}
					String pk_balancelisttemp = listbvos[i].getPk_balancelisttemp();
					String pk_balancelist_b = listbvos[i].getPk_balancelist_b();
					String sql= " update gt_balancelist_temp a set pk_balancelist_b='"+pk_balancelist_b+"' where pk_balancelisttemp='"+pk_balancelisttemp+"' ";
					updateBYsql(sql);
				}
			}else{
				//原过磅价结算
				writebacktoBalance(iexaggvo, pk_corp);
				//回写结算临时表
				BalanceListBVO[] listbvos = (BalanceListBVO[]) iexaggvo.getTableVO("gt_balancelist_b");			
				for(int i=0;i<listbvos.length;i++){
					if(listbvos[i].getStatus()==VOStatus.DELETED){
						continue;
					}
					String pk_balancelisttemp = listbvos[i].getPk_balancelisttemp();
					String pk_balancelist_b = listbvos[i].getPk_balancelist_b();
					String sql= " update gt_balancelist_temp a set pk_balancelist_b='"+pk_balancelist_b+"' where pk_balancelisttemp='"+pk_balancelisttemp+"' ";
					updateBYsql(sql);
				}
//				String linename=hvo.getVproductline();//产品线
				if((hvo.getVdef2()!=null&&hvo.getVdef2().doubleValue()==1)){
				  //询价后重新计算
				  //反结算后台业务操作
				  BalanceXJDMO xjdmo=new BalanceXJDMO();
				  xjdmo.deleteBackToBalanceTemp(iexaggvo,pk_corp);
//				  //更新结算明细dr标记为2、更新结算信息dr标记为2
				  String update_list=" update gt_balancelist_b set dr=2 where pk_sellpaymentjs='"+hvo.getPk_sellpaymentjs()+"'";
				  String update_info=" update gt_balanceinfo_b set dr=2 where pk_sellpaymentjs='"+hvo.getPk_sellpaymentjs()+"'";
				  updateBYsql(update_list);
				  updateBYsql(update_info);
				  //重新计算
				  xjdmo.getBalanceListVOS(hvo.getPk_cumandoc(), pk_corp, hvo.getPk_sellpaymentjs(),hvo.getPk_productline(),hvo.getPk_custproduct());
				}else{
					//优惠金额后台业务操作
					YHBalanceBOByJsPrice yhbo=new YHBalanceBOByJsPrice();
					yhbo.writebacktoBalance(iexaggvo, pk_corp);
				}
			}
        	SuperVO vo=(SuperVO) iexaggvo.getParentVO();
			// 审核人
	        vo.setAttributeValue("vapproveid" , pk_operator);
			// 审核日期
			vo.setAttributeValue("vapprovedate", _getDate());
			// 单据状态
			vo.setAttributeValue("vbillstatus", IJyglgtBillStatus.CHECKPASS);
        	// 弃审人
 	        vo.setAttributeValue("vunapproveid" ,"");
 			// 弃审日期
 			vo.setAttributeValue("vunapprovedate",null);
			updateVO(vo);
		} catch (Exception e) { 
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	
	
	/**
	 * @功能 销售货款结算 保存时回写收款余额表结算额
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public void writeBackToBalance(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator)throws BusinessException {
		try {
			SellPaymentJSVO  hvo=(SellPaymentJSVO)iexaggvo.getParentVO();
			BalanceListBVO[] bvo1 = (BalanceListBVO[]) iexaggvo.getTableVO("gt_balancelist_b");
			BalanceInfoBVO[] bvo2 = (BalanceInfoBVO[]) iexaggvo.getTableVO("gt_balanceinfo_b");
			for(int i=0;i<bvo1.length;i++){
				BalanceListBVO jsmny_bvo=bvo1[i];
				UFDouble jsmny=jsmny_bvo.getVdef3()==null?new UFDouble(0):jsmny_bvo.getVdef3();//收款金额
		 		String gatheringcode=jsmny_bvo.getGatheringcode();
//		 		if(Toolkits.isEmpty(gatheringcode)){throw new BusinessException("结算余额不足,可能被其他单据使用！");}
		 		String sql = " update gt_credit set  jsmny=nvl(jsmny,0)+nvl("+jsmny+",0) where vbillcode='"+gatheringcode+"' and pk_corp='"+pk_corp+"' ";
		 		updateBYsql(sql);
			 }
			//回写销售发票
			new BanlanceBackInvoiceDMO().addNewSaleInvoice(bvo2, hvo,_getDate(),pk_operator);
        	SuperVO vo=(SuperVO) iexaggvo.getParentVO();
			// 审核人
	        vo.setAttributeValue("vapproveid" , pk_operator);
			// 审核日期
			vo.setAttributeValue("vapprovedate", _getDate());
			// 单据状态
			vo.setAttributeValue("vbillstatus", IJyglgtBillStatus.CHECKPASS);
        	// 弃审人
 	        vo.setAttributeValue("vunapproveid" ,"");
 			// 弃审日期
 			vo.setAttributeValue("vunapprovedate",null);
			updateVO(vo);
		} catch (Exception e) { 
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	

	/**
	 * @功能 销售货款结算反操作 弃审时回写 收款余额表结算额
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public void deleteBackToBalance(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator) throws BusinessException{
		BalanceListBVO[] bvo1 = (BalanceListBVO[]) iexaggvo.getTableVO("gt_balancelist_b");
		//删除反向操作
		for(int i=0;i<bvo1.length;i++){
			BalanceListBVO jsmny_bvo=bvo1[i];
			UFDouble jsmny=jsmny_bvo.getVdef3()==null?new UFDouble(0):jsmny_bvo.getVdef3();//收款金额
	 		String gatheringcode=jsmny_bvo.getGatheringcode();
//	 		if(Toolkits.isEmpty(gatheringcode)){throw new BusinessException("结算余额不足,可能被其他单据使用！");}
	 		String sql = " update gt_credit set  jsmny=nvl(jsmny,0)-nvl("+jsmny+",0) where vbillcode='"+gatheringcode+"' and pk_corp='"+pk_corp+"' ";
	 		updateBYsql(sql);
		 }
    	SuperVO vo=(SuperVO) iexaggvo.getParentVO();
	   	 // 弃审人
	   	 vo.setAttributeValue("vunapproveid" , pk_operator);
	   	 // 弃审日期
	   	 vo.setAttributeValue("vunapprovedate", _getDate());
	   	 // 单据状态
	   	 vo.setAttributeValue("vbillstatus", IJyglgtBillStatus.FREE);
	   	 // 审核人
	   	 vo.setAttributeValue("vapproveid" , "");
	   	 // 审核日期
	   	 vo.setAttributeValue("vapprovedate", null);
	   	 updateVO(vo);
	}
	
	/**
	 * @功能 销售优惠结算 保存时回写收款余额表结算额
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public void writeBackToBalanceYh(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator)throws BusinessException {
		try {
			SellPaymentJSVO  hvo=(SellPaymentJSVO)iexaggvo.getParentVO();
			BalanceInfoBVO[] bvo2 = (BalanceInfoBVO[]) iexaggvo.getTableVO("gt_balanceinfo_b");
			//生成单据编号
			String vbillcode = pk_corp+_getDate()+"0001";
			try {
				nc.bs.pub.billcodemanage.BillcodeRuleBO bo=new nc.bs.pub.billcodemanage.BillcodeRuleBO();
				vbillcode =bo.getBillCode("JY04", pk_corp,null, null);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BusinessException(e.getMessage());
			}
			//生成收款单
			new YHBalanceBOByJsPriceXH().creatCredit(iexaggvo, pk_corp, hvo.getVdef1(), vbillcode);
			//红冲销售发票
			new BanlanceBackInvoiceDMO().addNewSaleInvoiceYh(bvo2, hvo,_getDate(),pk_operator);
			//新增销售发票
			new BanlanceBackInvoiceDMO().addNewSaleInvoiceYh2(bvo2, hvo,_getDate(),pk_operator);
        	SuperVO vo=(SuperVO) iexaggvo.getParentVO();
			// 审核人
	        vo.setAttributeValue("vapproveid" , pk_operator);
			// 审核日期
			vo.setAttributeValue("vapprovedate", _getDate());
			// 单据状态
			vo.setAttributeValue("vbillstatus", IJyglgtBillStatus.CHECKPASS);
        	// 弃审人
 	        vo.setAttributeValue("vunapproveid" ,"");
 			// 弃审日期
 			vo.setAttributeValue("vunapprovedate",null);
 			vo.setAttributeValue("vdef7",vbillcode);
			updateVO(vo);
		} catch (Exception e) { 
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	

	/**
	 * @功能 销售优惠结算反操作 弃审时回写 收款余额表结算额
	 * @param ExAggSellPaymentJSVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public void deleteBackToBalanceYh(ExAggSellPaymentJSVO iexaggvo,String pk_corp,String pk_operator) throws BusinessException{
		SellPaymentJSVO  hvo=(SellPaymentJSVO)iexaggvo.getParentVO();
//		BalanceListBVO[] bvo1 = (BalanceListBVO[]) iexaggvo.getTableVO("gt_balancelist_b");
		//删除收款通知
 		String gatheringcode=hvo.getVdef7();
 		String sql = " delete from  gt_credit where vbillcode='"+gatheringcode+"' and pk_corp='"+pk_corp+"' ";
 		updateBYsql(sql);
    	SuperVO vo=(SuperVO) iexaggvo.getParentVO();
	   	 // 弃审人
	   	 vo.setAttributeValue("vunapproveid" , pk_operator);
	   	 // 弃审日期
	   	 vo.setAttributeValue("vunapprovedate", _getDate());
	   	 // 单据状态
	   	 vo.setAttributeValue("vbillstatus", IJyglgtBillStatus.FREE);
	   	 // 审核人
	   	 vo.setAttributeValue("vapproveid" , "");
	   	 // 审核日期
	   	 vo.setAttributeValue("vapprovedate", null);
	   	 updateVO(vo);
	}
	
	
	/**
	 * @功能 销售货款结算 保存时回写 结算临时表
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-7-4
	 */
	public boolean writeBackToBalanceTemp_save(ExAggSellPaymentJSVO iexaggvo,String pk_corp)throws BusinessException {
		boolean flag=false;
		try {			
			//保存
			SellPaymentJSVO hvo = (SellPaymentJSVO) iexaggvo.getParentVO();
			String hpk=hvo.getPk_sellpaymentjs();
			if(!Toolkits.isEmpty(hpk)){
				updateVO(hvo);
			}else{
		     	hpk=insertVObackPK(hvo);
			}
			//子表一
			BalanceListBVO[] bvo1 = (BalanceListBVO[]) iexaggvo.getTableVO("gt_balancelist_b");
			for(int i=0;i<bvo1.length;i++){
				BalanceListBVO   itemvo=(BalanceListBVO)bvo1[i];
		     	itemvo.setPk_sellpaymentjs(hpk);
		     	itemvo.setDr(new Integer(0));
				Object bpk=itemvo.getPk_balancelist_b();
				if(!Toolkits.isEmpty(bpk)){
					updateVO(itemvo);
				}else{
			     	insertVObackPK(itemvo);
				}
				//回写结算临时表
				if(itemvo.getStatus()==VOStatus.DELETED){
					continue;
				}								
				String pk_balancelisttemp = itemvo.getPk_balancelisttemp();
				String sql= " update gt_balancelist_temp  set vdef18='Y' where pk_balancelisttemp='"+pk_balancelisttemp+"' ";
				updateBYsql(sql);			
			 }
			//子表二
			BalanceInfoBVO[] bvo2 = (BalanceInfoBVO[]) iexaggvo.getTableVO("gt_balanceinfo_b");
			for(int i=0;i<bvo2.length;i++){
				BalanceInfoBVO   itemvo=(BalanceInfoBVO)bvo2[i];
		     	itemvo.setPk_sellpaymentjs(hpk);
		     	itemvo.setDr(new Integer(0));
				Object bpk=itemvo.getPk_balanceinfo_b();
				if(!Toolkits.isEmpty(bpk)){
					updateVO(itemvo);
				}else{
			     	insertVObackPK(itemvo);
				}
			 }
			String sql2 = " delete from  gt_balancelist_b where vdef20='"+iexaggvo.getParentVO().getAttributeValue("pk_operator")+"' " ;
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
	 * @功能 销售货款结算 保存时回写收款通知并生成结算明细
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public boolean writeBackToCreditAndBalanceDetail(ExAggSellPaymentJSVO iexaggvo,String pk_corp)throws BusinessException {
		boolean flag=false;
		try {			
			//保存
			SellPaymentJSVO hvo = (SellPaymentJSVO) iexaggvo.getParentVO();
			String hpk=hvo.getPk_sellpaymentjs();
			if(!Toolkits.isEmpty(hpk)){
				updateVO(hvo);
			}else{
		     	hpk=insertVObackPK(hvo);
			}
			//子表二
			BalanceInfoBVO[] bvo2 = (BalanceInfoBVO[]) iexaggvo.getTableVO("gt_balanceinfo_b");
			//子表一
//			BalanceListBVO[] bvo1 = (BalanceListBVO[]) iexaggvo.getTableVO("gt_balancelist_b");
			for(int i=0;i<bvo2.length;i++){
				BalanceInfoBVO   itemvo=(BalanceInfoBVO)bvo2[i];
		     	itemvo.setPk_sellpaymentjs(hpk);
		     	itemvo.setDr(new Integer(0));
				Object bpk=itemvo.getPk_balanceinfo_b();
				if(!Toolkits.isEmpty(bpk)){
					updateVO(itemvo);
				}else{
					bpk=insertVObackPK(itemvo);
				}
				BalanceListBVO bvo1=new BalanceListBVO();
				bvo1.setPk_sellpaymentjs(hpk);
				bvo1.setPk_upper_b(Toolkits.getString(bpk));
				bvo1.setPk_invbasdoc(itemvo.getPk_invbasdoc());
				bvo1.setPk_invmandoc(itemvo.getPk_invmandoc());
				bvo1.setTwonum(itemvo.getSuttle());
				bvo1.setNum(itemvo.getNum());
				bvo1.setVdef11(itemvo.getVdef11());
				bvo1.setVfree1(itemvo.getVfree1());
				bvo1.setVfree2(itemvo.getVfree2());
				bvo1.setVfree3(itemvo.getVfree3());
				bvo1.setVfree4(itemvo.getVfree4());
				bvo1.setDr(new Integer(0));
				BanlanceBackReceiveDMO redmo=new BanlanceBackReceiveDMO();
				redmo.backFhMny(itemvo,bvo1, hpk, hvo.getPk_cumandoc(), pk_corp);
			 }
			flag= true;
		} catch (Exception e) { 
			e.printStackTrace();
			flag= false;
			throw new BusinessException(e);
		}
		return flag;
	}
	
	
	/**
	 * @功能 销售结算 删除时回写收款通知并删除结算明细
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2014-10-26
	 */
	public boolean delBackToCreditAndBalanceDetail(ExAggSellPaymentJSVO iexaggvo,String pk_corp)throws BusinessException {
		boolean flag=false;
		try {			
			//子表一
			BalanceListBVO[] bvo1 = (BalanceListBVO[]) iexaggvo.getTableVO("gt_balancelist_b");
			//删除反向操作
			for(int i=0;i<bvo1.length;i++){
				BalanceListBVO jsmny_bvo=bvo1[i];
				UFDouble jsmny=jsmny_bvo.getVdef3()==null?new UFDouble(0):jsmny_bvo.getVdef3();//收款金额
		 		String gatheringcode=jsmny_bvo.getGatheringcode();
//		 		if(Toolkits.isEmpty(gatheringcode)){throw new BusinessException("结算余额不足,可能被其他单据使用！");}
		 		String sql = " update gt_credit set  fhmny=nvl(fhmny,0)- nvl("+jsmny+",0) where vbillcode='"+gatheringcode+"' and pk_corp='"+pk_corp+"' ";
		 		updateBYsql(sql);
			 }
			String sql_listb = " delete gt_balancelist_b   where pk_sellpaymentjs='"+iexaggvo.getParentVO().getPrimaryKey()+"' ";
			String sql_info = " delete gt_balanceinfo_b  where pk_sellpaymentjs='"+iexaggvo.getParentVO().getPrimaryKey()+"' ";
			String sql_h=" delete gt_sellpaymentjs  where pk_sellpaymentjs='"+iexaggvo.getParentVO().getPrimaryKey()+"' ";
			updateBYsql(sql_listb);
			updateBYsql(sql_info);
			updateBYsql(sql_h);
			flag= true;
		} catch (Exception e) { 
			e.printStackTrace();
			flag= false;
			throw new BusinessException(e);
		}
		return flag;
	}
	
	/**
	 * 原过磅价结算
	 * */
	public void writebacktoBalance(ExAggSellPaymentJSVO aggvo,String pk_corp) throws BusinessException {
		try {		
			BalanceListBVO[] listbvos = (BalanceListBVO[]) aggvo.getTableVO(tablecode2);
			for(int j=0;j<listbvos.length;j++){
				UFDouble jsmny=listbvos[j].getVdef4()==null?new UFDouble(0):listbvos[j].getVdef4();
				if(jsmny.doubleValue()<=0)continue;
				if(jsmny.doubleValue()>0){
				    ArrayList<HashMap<String, String>> list=getMnyList2((SellPaymentJSVO)aggvo.getParentVO(),listbvos[j].getGatheringcode());
				    UFDouble kjsmny=new UFDouble(0);//可结算金额B
				    if(list!=null&&list.size()>0){
				    	//情况1:有可结算金额
				    	kjsmny=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list.get(0)).get("kjsmny"))));
				    	String pk_credit=Toolkits.getString(((HashMap<String, String>)list.get(0)).get("pk_credit")); 
				    	//情况1-1:B	-A>=0 说明当条收款单完全够结算
				    	if(kjsmny.sub(jsmny).doubleValue()>=0){
				    		//回写优惠金额到该收款单
				    		if (!Toolkits.isEmpty(pk_credit)) {
								StringBuffer updateSbf = new StringBuffer("");
								updateSbf.append("update gt_credit set jsmny=nvl(jsmny,0)+"+ jsmny.doubleValue()+ "  where pk_credit='" + pk_credit + "'");						
								updateBYsql(updateSbf.toString());
				    	   }
				    	}
				    	else{
				    		throw new BusinessException("收款单号为："+listbvos[j].getGatheringcode()+"的结算金额超出发货金额！");
				    	  }
				        }else{
				        	throw new BusinessException("当前单据无可结算余额！");
				        }
				    }		            
			 	}
		} catch (BusinessException e) {
			e.printStackTrace();
			throw new BusinessException(e);
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
		 		String sql = " update gt_credit set fhmny=nvl(fhmny,0)-nvl("+cemny+",0),jsmny=nvl(jsmny,0)-nvl("+cemny+",0) where vbillcode='"+listbvo.getGatheringcode()+"' and pk_corp='"+pk_corp+"' ";
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
		if(list==null||list.size()==0){
			list=new ArrayList<HashMap<String,String>>();
			// 判断客户可发货剩余额=收款额 – 发货额 – 退款额 
			// 客户名称
			strSk=new StringBuffer("");
			strSk.append(" select vbillcode,pk_credit,(nvl(skmny,0) - nvl(fhmny,0) - nvl(tkmny,0))kfhmny,'N'flag from gt_credit where (nvl(skmny,0) - nvl(fhmny,0) - nvl(tkmny,0))>0 ")
			   .append( " and receivetype in("+IJyglgtConst.RECEIVETYPE_QC+","+IJyglgtConst.RECEIVETYPE_YS+") and pk_cumandoc= '"+ cumandoc+ "' and vbillcode = '"+vbillcode+"' and vbillstatus='"+IJyglgtBillStatus.CHECKPASS+"' and pk_corp='"+pk_corp+"' and nvl(dr,0)=0 order by receivemnydate,vbillcode ");
			list = queryArrayBySql(strSk.toString());
		}
		return list;
	}
	
	
	/**
	 * 获取可结算金额
	 * @param int i
	 * @param MeasureHeaderVO hvo
	 * @param String pk_receiveway
	 * */
	private ArrayList<HashMap<String,String>> getMnyList2(SellPaymentJSVO hvo,String vbillcode) throws BusinessException{
		ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
		// 判断客户可结算额=收款额 – 发货额 – 已结算额 
		// 客户名称
		String cumandoc = Toolkits.getString(hvo.getPk_cumandoc());
		String pk_corp=hvo.getPk_corp()== null ? "" : hvo.getPk_corp().toString().trim();; 
		StringBuffer strSk=new StringBuffer("");
		strSk.append(" select vbillcode,pk_credit,(nvl(fhmny,0)-nvl(jsmny,0))kjsmny from gt_credit where nvl(fhmny,0)>0 ")
		   .append( " and receivetype in("+IJyglgtConst.RECEIVETYPE_QC+","+IJyglgtConst.RECEIVETYPE_YS+") and pk_cumandoc= '"+ cumandoc+ "'  and vbillcode ='"+vbillcode+"' and vbillstatus='"+IJyglgtBillStatus.CHECKPASS+"' and pk_corp='"+pk_corp+"' and nvl(dr,0)=0  ");
		list = queryArrayBySql(strSk.toString());
		return list;
	}
	
	

	/**
	 * @功能 销售货款结算反操作 保存时回写 结算临时表,回写收款余额表结算额
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-7-4
	 */
	public void deleteBackToBalanceTemp(ExAggSellPaymentJSVO iexaggvo,String pk_corp) throws BusinessException {
		try {
			SellPaymentJSVO  hvo=(SellPaymentJSVO)iexaggvo.getParentVO();
			if(hvo.getVdef0()!=null&&hvo.getVdef0().equals("提单销售")){
				BalanceListBVO[] listbvos = (BalanceListBVO[]) iexaggvo.getTableVO("gt_balancelist_b");			
				for(int i=0;i<listbvos.length;i++){
					String pk_balancelisttemp = listbvos[i].getPk_balancelisttemp();
					//删除时的反操作
					String sql = " update gt_balancelist_temp  set pk_balancelist_b=null where pk_balancelisttemp='"+pk_balancelisttemp+"' ";
					updateBYsql(sql);
				}
			}else{
//				String linename=hvo.getVproductline();//产品线
				if((hvo.getVdef2()!=null&&hvo.getVdef2().doubleValue()==1)){
				  //后台业务反向操作
				  YHBalanceBOByXJPrice yhbo=new YHBalanceBOByXJPrice();
				  yhbo.deletebacktoBalance2(iexaggvo, pk_corp);
				  //更新结算明细dr标记为0、更新结算信息dr标记为0
				  String update_list=" update gt_balancelist_b set dr=0 where dr=2 and pk_sellpaymentjs='"+hvo.getPk_sellpaymentjs()+"'";
				  String update_info=" update gt_balanceinfo_b set dr=0 where dr=2 and pk_sellpaymentjs='"+hvo.getPk_sellpaymentjs()+"'";
				  updateBYsql(update_list);
				  updateBYsql(update_info);
				  //后台业务反向操作
				  yhbo.deletebacktoBalance3(iexaggvo, pk_corp);				  
				}else{
					BalanceListBVO[] listbvos = (BalanceListBVO[]) iexaggvo.getTableVO("gt_balancelist_b");			
					for(int i=0;i<listbvos.length;i++){
						String pk_balancelisttemp = listbvos[i].getPk_balancelisttemp();
						//删除时的反操作
						String sql = " update gt_balancelist_temp  set pk_balancelist_b=null where pk_balancelisttemp='"+pk_balancelisttemp+"' ";
						updateBYsql(sql);
					}
					//优惠金额后台业务反向操作
					YHBalanceBOByJsPrice yhbo=new YHBalanceBOByJsPrice();
					yhbo.deletebacktoBalance2(iexaggvo, pk_corp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new BusinessException(e);
		}
	}
	
	/**
	 * @功能 销售货款结算反操作 删除时回写 结算临时表
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author 施鹏
	 * @serialData 2011-7-4
	 */
	public boolean deleteBackToBalanceTemp_del(ExAggSellPaymentJSVO iexaggvo,String pk_corp) throws BusinessException {
		boolean flag=false;
		try {
			BalanceListBVO[] listbvos = (BalanceListBVO[]) iexaggvo.getTableVO("gt_balancelist_b");	
			for(int i=0;i<listbvos.length;i++){
				String pk_balancelisttemp = listbvos[i].getPk_balancelisttemp();
				//删除时的反操作
				String sql = " update gt_balancelist_temp  set vdef18=null where pk_balancelisttemp='"+pk_balancelisttemp+"' ";				
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
