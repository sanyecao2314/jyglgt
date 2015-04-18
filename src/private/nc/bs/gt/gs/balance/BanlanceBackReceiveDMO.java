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
 * @author ʩ�� ���۰���������д�տ���� ҵ���̨������
 */
public class BanlanceBackReceiveDMO extends ServerDAO {

	/**
	 * ��д�������\������
	 * @param BalanceInfoBVO jlbvo
	 * @param BalancelistBVO balanceListBVO
	 * @throws BusinessException
	 * @author ʩ��
	 * @param balanceListBVO 
	 * @serialData 2011-6-28
	 * @return ArrayList<HashMap<String,String>>
	 * */
	public void backFhMny(
			BalanceInfoBVO jlbvo, BalanceListBVO balanceListBVO,String hpk,String pk_cumandoc,String pk_corp) throws BusinessException {
		    ArrayList<HashMap<String, String>> list=getMnyList(pk_cumandoc,pk_corp);
		    UFDouble YSecondJZ=Toolkits.getUFDouble(jlbvo.getSuttle());             //ԭ���μ���
		    UFDouble NSecondJZ= Toolkits.getUFDouble(jlbvo.getSuttle());            //�ֶ��μ���
		    UFDouble fnum= Toolkits.getUFDouble(jlbvo.getNum());   
		    UFDouble bprice=Toolkits.getUFDouble(jlbvo.getVdef1());      //��������
		    UFDouble fhmny=new UFDouble(0); //�������:A
		    UFDouble Kfhmny=new UFDouble(0);//�ɷ������:B
		    if(list!=null&&list.size()>0){
		    	//���1:�пɷ������
			    for(int i=0;i<list.size();i++){
			    	UFDouble nprice=bprice;
			    	Kfhmny=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list.get(i)).get("kfhmny"))));
			    	String pk_credit=Toolkits.getString(((HashMap<String, String>)list.get(i)).get("pk_credit")); 
			    	String pvbillcode=Toolkits.getString(((HashMap<String, String>)list.get(i)).get("vbillcode"));
			    	UFDouble cdprice=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list.get(i)).get("cdprice"))));
			    	Integer txdays=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list.get(i)).get("txdays")))).intValue();
			    	UFDouble txtax=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list.get(i)).get("montax")))).multiply(txdays);
			    	String priceway=Toolkits.getString(((HashMap<String, String>)list.get(i)).get("priceway"));//�Ӽ۷�ʽ
			    	String pk_receiveway=Toolkits.getString(((HashMap<String, String>)list.get(i)).get("pk_receiveway"));//�տʽ
			    	UFDouble vdef1=new UFDouble(0);
			    	balanceListBVO.setPk_receiveway(pk_receiveway);
			    	if(!Toolkits.isEmpty(priceway)&&priceway.equals("�̶��Ӽ�")&&pk_receiveway.equals("�ж�")){
			    		nprice=bprice.add(cdprice);
			    		vdef1=cdprice;
			    	}
//			    	else if(!Toolkits.isEmpty(priceway)&&priceway.equals("���ּӼ�")){
////			    		nprice=bprice.add(bprice.multiply(txtax).div(30).div(1000));
////			    		vdef1=bprice.multiply(txtax).div(30).div(1000);
//			    		//�жҵ���=�ֻ㺬˰����/��1- ������������/30*����������,�жҼӼ�=�жҵ���-�ֻ㺬˰����
//			    		nprice=(bprice.sub(yhprice)).div(new UFDouble(1).sub(txtax.div(30).div(1000)));
//			    		vdef1=nprice.sub(bprice);
//			    	}
			    	UFDouble kfhnum=Kfhmny.div(nprice);
			    	fhmny=new UFDouble(NSecondJZ.multiply(nprice).doubleValue(),2); 
			    	//���1-1:B	-A>=0 ˵�������տ������
			    	if(Kfhmny.sub(fhmny).doubleValue()>=0){
			    		//��д���������տ
			    		if (!Toolkits.isEmpty(pk_credit)) {
							StringBuffer updateSbf = new StringBuffer("");
							updateSbf.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ fhmny.doubleValue()+ " where pk_credit='" + pk_credit + "'");						
							updateBYsql(updateSbf.toString());
							UFDouble xhmny=new UFDouble(NSecondJZ.multiply(bprice).doubleValue(),2); 
				    		/***��д�������***/
							BalanceListBVO bbvo = (BalanceListBVO) balanceListBVO.clone();
							bbvo.setGatheringcode(pvbillcode);
							bbvo.setBalanceprice(nprice);
							bbvo.setBalancemny(fhmny);
							bbvo.setWeight(YSecondJZ);
							bbvo.setTwonum(NSecondJZ);
							bbvo.setNum(fnum);
							bbvo.setVdef2(bprice);//�ֻ㵥��
							bbvo.setVdef6(xhmny);//�ֻ���
							bbvo.setVdef3(fhmny);//�տ���
							if(pk_receiveway.equals("�ж�")){
					    		balanceListBVO.setBalanceprice(nprice);//�жҵ���
								bbvo.setVdef1(vdef1);//�жҼӼ�
								bbvo.setVdef4(fhmny);//�жҽ��
							}else{
					    		balanceListBVO.setBalanceprice(new UFDouble(0));//�жҵ���
								bbvo.setVdef1(new UFDouble(0));//�жҼӼ�
								bbvo.setVdef4(new UFDouble(0));//�жҽ��
							}
							bbvo.setPreferential(jlbvo.getPreferentialprice());//�Żݵ���
							bbvo.setPreferentialmny(jlbvo.getPreferentialmny());//�Żݽ��
							bbvo.setPk_receiveway(pk_receiveway);
							insertVObackPK(bbvo);
				    		//��ֹ��������
							return ;
			    	   }
			    	}
			    	
			    	//���1-2:B-A<0 ˵�������տ��������
			    	else if(Kfhmny.sub(fhmny).doubleValue()<0){
			    		//�������ַ������������=B
			    		/******�������ַ������*******/
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
							bbvo.setVdef2(bprice);//�ֻ㵥��
							bbvo.setVdef6(Kfhmny);//�ֻ���
							bbvo.setVdef3(Kfhmny);//�տ���
							if(pk_receiveway.equals("�ж�")){
					    		balanceListBVO.setBalanceprice(nprice);//�жҵ���
								bbvo.setVdef1(vdef1);//�жҼӼ�
								bbvo.setVdef4(Kfhmny);//�жҽ��
							}else{
					    		balanceListBVO.setBalanceprice(new UFDouble(0));//�жҵ���
								bbvo.setVdef1(new UFDouble(0));//�жҼӼ�
								bbvo.setVdef4(new UFDouble(0));//�жҽ��
							}
							bbvo.setPreferential(jlbvo.getPreferentialprice());//�Żݵ���
							bbvo.setPreferentialmny(jlbvo.getPreferentialmny());//�Żݽ��
							bbvo.setPk_receiveway(pk_receiveway);
							insertVObackPK(bbvo);
			    		   //δ����ķ�����
							fhmny=fhmny.sub(Kfhmny); //A=A-B
							NSecondJZ=NSecondJZ.sub(kfhnum);
			    		//
			    		/*******������һ���ۼ��������**********/
			    	  }
			        
			    	}			    	
			    }	
		    }
		    /****************��������������ߵ����ʱ�Ż����ձ�������*******************************/
            throw new BusinessException("����");		
	}

	
	
	/**
	 * ��ȡ�ɷ������
	 * @param String pk_receiveway
	 * */
	private ArrayList<HashMap<String,String>> getMnyList(String pk_cumandoc,String pk_corp) throws BusinessException{
		ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
		StringBuffer strSk=new StringBuffer("");
		//�ͻ�ȫ���Ź�������
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
