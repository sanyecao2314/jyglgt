package nc.bs.gt.gs.balance;

import java.util.ArrayList;
import java.util.HashMap;
import nc.bs.jyglgt.dao.ServerDAO;
import nc.vo.gt.gs.gs11.BalanceInfoBVO;
import nc.vo.gt.gs.gs11.BalanceListBVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;


/**
 * @author 施鹏 销售磅单过磅回写收款控制 业务后台处理类
 */
public class BanlanceBackReceiveDMO extends ServerDAO {

	/**
	 * 回写发货金额\结算金额
	 * @param BalanceInfoBVO jlbvo
	 * @param BalancelistBVO balanceListBVO
	 * @throws BusinessException
	 * @author 施鹏
	 * @param balanceListBVO 
	 * @serialData 2011-6-28
	 * @return ArrayList<HashMap<String,String>>
	 * */
	public void backFhMny(
			BalanceInfoBVO jlbvo, BalanceListBVO balanceListBVO,String hpk,String pk_cumandoc,String pk_corp) throws BusinessException {
		    ArrayList<HashMap<String, String>> list=getMnyList(pk_cumandoc,pk_corp);
		    UFDouble YSecondJZ=Toolkits.getUFDouble(jlbvo.getSuttle());             //原二次计重
		    UFDouble NSecondJZ= Toolkits.getUFDouble(jlbvo.getSuttle());            //现二次计重
		    UFDouble fnum= Toolkits.getUFDouble(jlbvo.getNum());   
		    UFDouble bprice=Toolkits.getUFDouble(jlbvo.getVdef1());      //发货单价
		    UFDouble fhmny=new UFDouble(0); //发货金额:A
		    UFDouble Kfhmny=new UFDouble(0);//可发货金额:B
		    if(list!=null&&list.size()>0){
		    	//情况1:有可发货金额
			    for(int i=0;i<list.size();i++){
			    	UFDouble nprice=bprice;
			    	Kfhmny=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list.get(i)).get("kfhmny"))));
			    	String pk_credit=Toolkits.getString(((HashMap<String, String>)list.get(i)).get("pk_credit")); 
			    	String pvbillcode=Toolkits.getString(((HashMap<String, String>)list.get(i)).get("vbillcode"));
			    	UFDouble cdprice=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list.get(i)).get("cdprice"))));
			    	Integer txdays=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list.get(i)).get("txdays")))).intValue();
			    	UFDouble txtax=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list.get(i)).get("montax")))).multiply(txdays);
			    	String priceway=Toolkits.getString(((HashMap<String, String>)list.get(i)).get("priceway"));//加价方式
			    	String pk_receiveway=Toolkits.getString(((HashMap<String, String>)list.get(i)).get("pk_receiveway"));//收款方式
			    	UFDouble vdef1=new UFDouble(0);
			    	balanceListBVO.setPk_receiveway(pk_receiveway);
			    	if(!Toolkits.isEmpty(priceway)&&priceway.equals("固定加价")&&pk_receiveway.equals("承兑")){
			    		nprice=bprice.add(cdprice);
			    		vdef1=cdprice;
			    	}
//			    	else if(!Toolkits.isEmpty(priceway)&&priceway.equals("贴现加价")){
////			    		nprice=bprice.add(bprice.multiply(txtax).div(30).div(1000));
////			    		vdef1=bprice.multiply(txtax).div(30).div(1000);
//			    		//承兑单价=现汇含税单价/（1- 当天月贴现率/30*贴现天数）,承兑加价=承兑单价-现汇含税单价
//			    		nprice=(bprice.sub(yhprice)).div(new UFDouble(1).sub(txtax.div(30).div(1000)));
//			    		vdef1=nprice.sub(bprice);
//			    	}
			    	UFDouble kfhnum=Kfhmny.div(nprice);
			    	fhmny=new UFDouble(NSecondJZ.multiply(nprice).doubleValue(),2); 
			    	//情况1-1:B	-A>=0 说明当条收款单余额够发货
			    	if(Kfhmny.sub(fhmny).doubleValue()>=0){
			    		//回写发货金额到该收款单
			    		if (!Toolkits.isEmpty(pk_credit)) {
							StringBuffer updateSbf = new StringBuffer("");
							updateSbf.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ fhmny.doubleValue()+ " where pk_credit='" + pk_credit + "'");						
							updateBYsql(updateSbf.toString());
							UFDouble xhmny=new UFDouble(NSecondJZ.multiply(bprice).doubleValue(),2); 
				    		/***回写发货金额***/
							BalanceListBVO bbvo = (BalanceListBVO) balanceListBVO.clone();
							bbvo.setGatheringcode(pvbillcode);
							bbvo.setBalanceprice(nprice);
							bbvo.setBalancemny(fhmny);
							bbvo.setWeight(YSecondJZ);
							bbvo.setTwonum(NSecondJZ);
							bbvo.setNum(fnum);
							bbvo.setVdef2(bprice);//现汇单价
							bbvo.setVdef6(xhmny);//现汇金额
							bbvo.setVdef3(fhmny);//收款金额
							if(pk_receiveway.equals("承兑")){
					    		balanceListBVO.setBalanceprice(nprice);//承兑单价
								bbvo.setVdef1(vdef1);//承兑加价
								bbvo.setVdef4(fhmny);//承兑金额
							}else{
					    		balanceListBVO.setBalanceprice(new UFDouble(0));//承兑单价
								bbvo.setVdef1(new UFDouble(0));//承兑加价
								bbvo.setVdef4(new UFDouble(0));//承兑金额
							}
							bbvo.setPreferential(jlbvo.getPreferentialprice());//优惠单价
							bbvo.setPreferentialmny(jlbvo.getPreferentialmny());//优惠金额
							bbvo.setPk_receiveway(pk_receiveway);
							insertVObackPK(bbvo);
				    		//终止操作返回
							return ;
			    	   }
			    	}
			    	
			    	//情况1-2:B-A<0 说明当条收款单余额不够发货
			    	else if(Kfhmny.sub(fhmny).doubleValue()<0){
			    		//发掉部分发货金额：发货金额=B
			    		/******发掉部分发货金额*******/
			    		if (!Toolkits.isEmpty(pk_credit)) {
							StringBuffer updateSbf = new StringBuffer("");
							updateSbf.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ Kfhmny.doubleValue()+ " where pk_credit='" + pk_credit + "'");						
							updateBYsql(updateSbf.toString());
							UFDouble xhmny=new UFDouble(NSecondJZ.multiply(bprice).doubleValue(),2); 
							BalanceListBVO bbvo = (BalanceListBVO) balanceListBVO.clone();
							bbvo.setGatheringcode(pvbillcode);
							bbvo.setBalanceprice(nprice);
							bbvo.setBalancemny(Kfhmny);
							bbvo.setWeight(YSecondJZ);
							bbvo.setTwonum(kfhnum);
							bbvo.setNum(fnum);
							bbvo.setVdef2(bprice);//现汇单价
							bbvo.setVdef6(Kfhmny);//现汇金额
							bbvo.setVdef3(Kfhmny);//收款金额
							if(pk_receiveway.equals("承兑")){
					    		balanceListBVO.setBalanceprice(nprice);//承兑单价
								bbvo.setVdef1(vdef1);//承兑加价
								bbvo.setVdef4(Kfhmny);//承兑金额
							}else{
					    		balanceListBVO.setBalanceprice(new UFDouble(0));//承兑单价
								bbvo.setVdef1(new UFDouble(0));//承兑加价
								bbvo.setVdef4(new UFDouble(0));//承兑金额
							}
							bbvo.setPreferential(jlbvo.getPreferentialprice());//优惠单价
							bbvo.setPreferentialmny(jlbvo.getPreferentialmny());//优惠金额
							bbvo.setPk_receiveway(pk_receiveway);
							insertVObackPK(bbvo);
			    		   //未结算的发货额
							fhmny=fhmny.sub(Kfhmny); //A=A-B
							NSecondJZ=NSecondJZ.sub(kfhnum);
			    		//
			    		/*******继续下一条扣减发货金额**********/
			    	  }
			        
			    	}			    	
			    }	
		    }
		    /****************所有情况最终在走到情况时才会最终报警余额不足*******************************/
            throw new BusinessException("余额不足");		
	}

	
	
	/**
	 * 获取可发货金额
	 * @param String pk_receiveway
	 * */
	private ArrayList<HashMap<String,String>> getMnyList(String pk_cumandoc,String pk_corp) throws BusinessException{
		ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
		StringBuffer strSk=new StringBuffer("");
		//客户全集团共享信用
		strSk.append(" select vbillcode,pk_credit,(nvl(skmny,0) - nvl(fhmny,0))kfhmny,cdprice,montax,voiceedate,voicesdate,txdays,priceway,pk_receiveway from gt_credit where (nvl(skmny,0) - nvl(fhmny,0))>0 ")
		   .append( " and pk_cubasdoc= (select pk_cubasdoc from bd_cumandoc where pk_cumandoc='"+pk_cumandoc+"')  and vbillstatus='"+IJyglgtBillStatus.CHECKPASS+"' and nvl(dr,0)=0 and  billtype='"+IJyglgtBillType.JY04+"'");
//		if(!Toolkits.isEmpty(pk_productline)){
//			strSk.append(" and pk_productline='"+pk_productline+"' ");
//		} 
//		if(!Toolkits.isEmpty(pk_custproduct)){
//			strSk.append(" and pk_custproduct='"+pk_custproduct+"'  ");
//		}else{
//			strSk.append(" and pk_custproduct is null ");
//		}  
//		if(!Toolkits.isEmpty(pk_receiveway)){
//			strSk.append(" and pk_receiveway = '"+pk_receiveway+"' ");
//		}
		strSk.append(" order by receivemnydate,vbillcode ");
		list = queryArrayBySql(strSk.toString());
		return list;
	}

}
