package nc.bs.gt.gs.balance;

import java.util.ArrayList;
import java.util.HashMap;

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
import nc.vo.pub.lang.UFDouble;


/**
 * @author ʩ�� ����ѯ�����»�ȡ������������ϸ���� ҵ���̨������
 */
public class BalanceXJDMO extends ServerDAO {
	
	
	
	/**
	 * ѯ�ۺ����»�ȡ����vo
	 * @param userid
	 * @param pk_cumandoc
	 * @param pk_corp
	 * @throws ClassNotFoundException 
	 * @throws BusinessException 
	 * */
	public BalanceListBVO[] getBalanceListVOS(String pk_cumandoc,String pk_corp,String pk_sellpaymentjs,String pk_productline,String pk_custproduct ) throws BusinessException, ClassNotFoundException{
		StringBuffer sql=new StringBuffer();
		sql.append(" select pk_measure_b,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4, vfree5,vdef11,weight,num,poundcode,")
			.append(" hsprice,vdef6,balanceprice,pk_corp,pk_productline,pk_saleorder_b,pk_upper_b,vdef10,pk_cargdoc,pk_stordoc,issuecode,dates,times,balanceflag,pk_salescontract,vdef12")
			.append(" from gt_balancelist_b ")
			.append(" where nvl(dr,0)=2 and pk_sellpaymentjs='"+pk_sellpaymentjs+"'")
			.append(" group by pk_measure_b,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vfree5,vdef11,vdef9,weight,num,poundcode,")
			.append(" hsprice,vdef6,balanceprice,pk_corp,pk_productline,pk_saleorder_b,pk_upper_b,vdef10,pk_cargdoc,pk_stordoc,issuecode,dates,times,balanceflag,pk_salescontract,vdef12");
		ArrayList list_sbvos=getVOsfromSql(sql.toString(), BalanceListBVO.class.getName());
		BalanceListBVO[]tempvo=(BalanceListBVO[])list_sbvos.toArray(new BalanceListBVO[list_sbvos.size()]);
		ArrayList<BalanceListBVO> list_ebvos=new ArrayList<BalanceListBVO>(); 
		for(int i=0;i<tempvo.length;i++){
			getJSList(list_ebvos,tempvo[i],pk_cumandoc,pk_corp,pk_sellpaymentjs,pk_productline,pk_custproduct);
		}
		//���������Ϣ��
		insertInfo(pk_sellpaymentjs,pk_corp);
      return list_ebvos.toArray(new BalanceListBVO[list_ebvos.size()]);
	}

	/**
	 * ��ȡ������ϸ
	 * @param MeasureHeaderVO hvo
	 * @param MeasureItemVO jlbvo
	 * @param BalancelistBVO balanceListBVO
	 * @throws BusinessException
	 * @author ʩ��
	 * @param balanceListBVO 
	 * @serialData 2012-2-20
	 * @return 
	 * */
	public void getJSList(ArrayList<BalanceListBVO> list_ebvos, BalanceListBVO balanceListBVO,String pk_cumandoc,String pk_corp,String pk_sellpaymentjs,String pk_productline,String pk_custproduct) throws BusinessException {
		balanceListBVO.setPk_sellpaymentjs(pk_sellpaymentjs);//��������
//		String pk_receiveway=balanceListBVO.getPk_receiveway();
//		String pk_productline=balanceListBVO.getPk_productline();
//		String pk_custproduct=balanceListBVO.getPk_custproduct();
		String pk_saleorder=getSaleorderpk(balanceListBVO.getPk_saleorder_b());
		String vdef12=getSaleordervdef12(pk_saleorder);
		ArrayList<HashMap<String, String>> list;
		if(!Toolkits.isEmpty(vdef12)&&vdef12.equals("��")){
		   list=getMnyList(0,pk_cumandoc,pk_corp,pk_productline,pk_custproduct,pk_saleorder);	
		}else{
		   list=getMnyList(0,pk_cumandoc,pk_corp,pk_productline,pk_custproduct,null);
		}
		
	    UFDouble YSecondJZ=Toolkits.getUFDouble(balanceListBVO.getWeight());             //ԭ���μ���
	    UFDouble NSecondJZ= Toolkits.getUFDouble(balanceListBVO.getWeight());            //�ֶ��μ���
	    UFDouble fnum= Toolkits.getUFDouble(balanceListBVO.getNum());   
	    UFDouble bprice=Toolkits.getUFDouble(balanceListBVO.getVdef6());      //���㵥��
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
		    	String priceway=Toolkits.getString(((HashMap<String, String>)list.get(i)).get("priceway"));
		    	String pk_receiveway1=Toolkits.getString(((HashMap<String, String>)list.get(i)).get("pk_receiveway"));
		    	//lzj-2012-09-05
		    	UFDouble yhprice=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list.get(i)).get("vdef9"))))==null?new UFDouble(0):Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list.get(i)).get("vdef9"))));
		    	nprice=nprice.sub(yhprice);
		    	//lzj-2012-09-05
		    	UFDouble vdef1=new UFDouble(0);
		    	balanceListBVO.setPk_receiveway(pk_receiveway1);
		    	if(!Toolkits.isEmpty(priceway)&&priceway.equals("�̶��Ӽ�")){
		    		nprice=bprice.sub(yhprice).add(cdprice);
		    		vdef1=cdprice;
		    	}else if(!Toolkits.isEmpty(priceway)&&priceway.equals("���ּӼ�")){
		    		nprice=(bprice.sub(yhprice)).add(bprice.multiply(txtax).div(30).div(1000));
		    		vdef1=bprice.multiply(txtax).div(30).div(1000);
		    	}
		    	UFDouble kfhnum=Kfhmny.div(nprice);
		    	fhmny=new UFDouble(NSecondJZ.multiply(nprice).doubleValue(),2); 
		    	//���1-1:B	-A>=0 ˵�������տ������
		    	if(Kfhmny.sub(fhmny).doubleValue()>=0){
		    		//��д���������տ
		    		if (!Toolkits.isEmpty(pk_credit)) {
						StringBuffer updateSbf = new StringBuffer("");
						updateSbf.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ fhmny.doubleValue()+ ",jsmny=nvl(jsmny,0)+"+ fhmny.doubleValue()+ " where pk_credit='" + pk_credit + "'");						
						updateBYsql(updateSbf.toString());
			    		/***��д�������***/
						BalanceListBVO bbvo = (BalanceListBVO) balanceListBVO.clone();
						bbvo.setGatheringcode(pvbillcode);
						bbvo.setBalanceprice(nprice);
						bbvo.setBalancemny(fhmny);
						bbvo.setWeight(YSecondJZ);
						bbvo.setTwonum(NSecondJZ);
						bbvo.setNum(fnum);
						bbvo.setVdef1(vdef1);
						bbvo.setPriceway(priceway);
						UFDouble ddprice=bbvo.getVdef10()==null?bbvo.getHsprice():bbvo.getVdef10();
						UFDouble vdef2 = bbvo.getBalanceprice()==null?new UFDouble(0):bbvo.getBalanceprice();//���㵥��			
						UFDouble weight=bbvo.getWeight()==null?new UFDouble(0):bbvo.getWeight();
						UFDouble jsmny=bbvo.getBalancemny();//������
						UFDouble ddmny=ddprice.multiply(weight);//�������
						bbvo.setVdef9(ddmny);
						bbvo.setVdef8(yhprice);
						bbvo.setBalancemny(ddmny);//�������
						bbvo.setPreferential(new UFDouble(0));
						bbvo.setBalanceprice(ddprice);
						bbvo.setVdef2(vdef2);//���㵥��			
						bbvo.setVdef3(jsmny);
						bbvo.setVdef4(jsmny);//����������������ʱ��
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
						updateSbf.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ Kfhmny.doubleValue()+ ",jsmny=nvl(jsmny,0)+"+ Kfhmny.doubleValue()+ " where pk_credit='" + pk_credit + "'");						
						updateBYsql(updateSbf.toString());
						BalanceListBVO bbvo = (BalanceListBVO) balanceListBVO.clone();
						bbvo.setGatheringcode(pvbillcode);
						bbvo.setBalanceprice(nprice);
						bbvo.setBalancemny(Kfhmny);
						bbvo.setWeight(YSecondJZ);
						bbvo.setTwonum(kfhnum);
						bbvo.setNum(fnum);
						bbvo.setVdef1(vdef1);
						bbvo.setPriceway(priceway);
						UFDouble ddprice=bbvo.getVdef10()==null?bbvo.getHsprice():bbvo.getVdef10();
						UFDouble vdef2 = bbvo.getBalanceprice()==null?new UFDouble(0):bbvo.getBalanceprice();//���㵥��			
						UFDouble weight=bbvo.getWeight()==null?new UFDouble(0):bbvo.getWeight();
						UFDouble jsmny=bbvo.getBalancemny();//������
						UFDouble ddmny=ddprice.multiply(weight);//�������
						bbvo.setVdef9(ddmny);
						bbvo.setVdef8(yhprice);
						bbvo.setBalancemny(ddmny);//�������
						bbvo.setPreferential(new UFDouble(0));
						bbvo.setBalanceprice(ddprice);
						bbvo.setVdef2(vdef2);//���㵥��			
						bbvo.setVdef3(jsmny);
						bbvo.setVdef4(jsmny);//����������������ʱ��
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
        //���4:�����տ�����ķ������ۼ���ϣ�A>0 
	    //˵����1���з������δ���ֻ꣬�������ַ������������޿ɷ����
	    //      2�տ��޿ɷ���������Ҳ�޿ɷ������������һ��Ҳû����
	    /****************��������������ߵ����4ʱ�Ż����ձ�������*******************************/
        throw new BusinessException("����");		
	}

	/**
	 * ��ȡ��������
	 * */
	public String getSaleorderpk(String pk_saleorder_b) throws BusinessException{
		String sql=" select csaleid from so_saleexecute where csale_bid='"+pk_saleorder_b+"'";
		return queryStrBySql(sql);
	}
	
	/* *  �Ƿ�󶨶���
	 * */
	public String getSaleordervdef12(String pk_saleorder) throws BusinessException{
		String sql=" select vdef12 from so_sale where csaleid='"+pk_saleorder+"'";
		return queryStrBySql(sql);
	}
	
	
	/**
	 * ��ȡ�ɷ������
	 * @param int i
	 * @param MeasureHeaderVO hvo
	 * @param String pk_receiveway
	 * */
	private ArrayList<HashMap<String,String>> getMnyList(int i,String pk_cumandoc,String pk_corp,String pk_productline,String pk_custproduct,String pk_saleorder) throws BusinessException{
		ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
		// �жϿͻ��ɷ���ʣ���=�տ�� �C ������ �C �˿��  
		if(i==0){//������1��2�������տѰ�ҿɷ������
			StringBuffer strSk=new StringBuffer("");
			strSk.append(" select vbillcode,pk_credit,(nvl(skmny,0) - nvl(fhmny,0) - nvl(tkmny,0))kfhmny,fhmny,jsmny,cdprice,montax,voiceedate,voicesdate,txdays,priceway,pk_receiveway,vdef9 from gt_credit where (nvl(skmny,0) - nvl(fhmny,0) - nvl(tkmny,0))>0 ")
			   .append( " and receivetype in("+IJyglgtConst.RECEIVETYPE_QC+","+IJyglgtConst.RECEIVETYPE_YS+") and pk_cumandoc= '"+ pk_cumandoc+ "'  and vbillstatus='"+IJyglgtBillStatus.CHECKPASS+"' and pk_corp='"+pk_corp+"'  and nvl(dr,0)=0 ");
			if(!Toolkits.isEmpty(pk_productline)){
				strSk.append(" and pk_productline='"+pk_productline+"' ");
			} 
			if(!Toolkits.isEmpty(pk_custproduct)){
				strSk.append(" and pk_custproduct='"+pk_custproduct+"'  ");
			}else{
				strSk.append(" and pk_custproduct is null  ");
			} 
//			if(!Toolkits.isEmpty(pk_receiveway)){
//				strSk.append(" and pk_receiveway = '"+pk_receiveway+"' ");
//			}
			if(!Toolkits.isEmpty(pk_saleorder)){
				strSk.append(" and csaleid = '"+pk_saleorder+"' ");
			}else{
				strSk.append(" and csaleid is null ");
			}
			strSk.append(" order by receivemnydate,vbillcode ");
			list = queryArrayBySql(strSk.toString());
		}
		return list;
	}

	/**
	 * ���������Ϣ��
	 * */
	public void insertInfo(String pk_sellpaymentjs,String pk_corp) throws BusinessException {

		
//		//����ʱ�������ѯ����������
//		StringBuffer sb = new StringBuffer();
//		sb.append(" select sum(fnum)fnum,sum(twonum) num ,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,dj")
//		.append(" from (select distinct pk_measure_b,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vdef11 dj,")
//		 .append("  nvl(num,0)fnum,nvl(twonum,0)twonum ")
//		 .append(" from gt_balancelist_b ")
//		 .append("  where nvl(dr,0)=0 and pk_sellpaymentjs='"+pk_sellpaymentjs+"') ")
//		 .append(" group by pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,dj");
//		ArrayList<HashMap<String,String>> al = queryArrayBySql(sb.toString());
//		HashMap<String,UFDouble>hm_jl=new HashMap<String,UFDouble>();
//		HashMap<String,UFDouble>hm_jl2=new HashMap<String,UFDouble>();
//		if(al!=null&&al.size()>0){
//			for(int i=0;i<al.size();i++){
//				HashMap<String,String> hm = (HashMap<String,String>) al.get(i);
//				String pk_invmandoc=hm.get("pk_invmandoc")==null?"":hm.get("pk_invmandoc").trim();
//				String pk_invbasdoc=hm.get("pk_invbasdoc")==null?"":hm.get("pk_invbasdoc").trim();
//				String vfree1=hm.get("vfree1")==null?"":hm.get("vfree1").trim();
//				String vfree2=hm.get("vfree2")==null?"":hm.get("vfree2").trim();
//				String vfree3=hm.get("vfree3")==null?"":hm.get("vfree3").trim();
//				String vfree4=hm.get("vfree4")==null?"":hm.get("vfree4").trim();
//				String dj=hm.get("dj")==null?"":hm.get("dj").trim();
//				String key = pk_invmandoc+pk_invbasdoc+vfree1+vfree2+vfree3+vfree4+dj;
//				UFDouble fnum=hm.get("fnum")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("fnum")));
//				UFDouble num=hm.get("num")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("num")));
//				if(!hm_jl.containsKey(key)){
//					hm_jl.put(key, fnum);
//				}
//				if(!hm_jl2.containsKey(key)){
//					hm_jl2.put(key, num);
//				}
//			}
//		}
		
		//����ʱ�������ѯ����������
		StringBuffer sb = new StringBuffer();
		sb.append(" select pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vdef11 dj,")
		 .append("  sum(nvl(twonum,0)) num")
		 .append(" from gt_balancelist_b ")
		 .append("  where nvl(dr,0)=0 and pk_sellpaymentjs='"+pk_sellpaymentjs+"' ")
		 .append(" group by pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vdef11");
		ArrayList<HashMap<String,String>> al = queryArrayBySql(sb.toString());
		HashMap<String,UFDouble>hm_jl2=new HashMap<String,UFDouble>();
		if(al!=null&&al.size()>0){
			for(int i=0;i<al.size();i++){
				HashMap<String,String> hm = (HashMap<String,String>) al.get(i);
				String pk_invmandoc=hm.get("pk_invmandoc")==null?"":hm.get("pk_invmandoc").trim();
				String pk_invbasdoc=hm.get("pk_invbasdoc")==null?"":hm.get("pk_invbasdoc").trim();
				String vfree1=hm.get("vfree1")==null?"":hm.get("vfree1").trim();
				String vfree2=hm.get("vfree2")==null?"":hm.get("vfree2").trim();
				String vfree3=hm.get("vfree3")==null?"":hm.get("vfree3").trim();
				String vfree4=hm.get("vfree4")==null?"":hm.get("vfree4").trim();
				String dj=hm.get("dj")==null?"":hm.get("dj").trim();
				String key = pk_invmandoc+pk_invbasdoc+vfree1+vfree2+vfree3+vfree4+dj;
				UFDouble num=hm.get("num")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("num")));
				if(!hm_jl2.containsKey(key)){
					hm_jl2.put(key, num);
				}
			}
		}
		
		StringBuffer sb_fnum = new StringBuffer();
		sb_fnum.append(" select sum(fnum)fnum,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,dj")
		.append(" from (select distinct pk_measure_b,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vdef11 dj,")
		 .append("  nvl(num,0)fnum ")
		 .append(" from gt_balancelist_b ")
		 .append("  where nvl(dr,0)=0 and pk_sellpaymentjs='"+pk_sellpaymentjs+"') ")
		 .append(" group by pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,dj");
		ArrayList<HashMap<String,String>> al2 = queryArrayBySql(sb_fnum.toString());
		HashMap<String,UFDouble>hm_jl=new HashMap<String,UFDouble>();
		if(al2!=null&&al2.size()>0){
			for(int i=0;i<al2.size();i++){
				HashMap<String,String> hm = (HashMap<String,String>) al2.get(i);
				String pk_invmandoc=hm.get("pk_invmandoc")==null?"":hm.get("pk_invmandoc").trim();
				String pk_invbasdoc=hm.get("pk_invbasdoc")==null?"":hm.get("pk_invbasdoc").trim();
				String vfree1=hm.get("vfree1")==null?"":hm.get("vfree1").trim();
				String vfree2=hm.get("vfree2")==null?"":hm.get("vfree2").trim();
				String vfree3=hm.get("vfree3")==null?"":hm.get("vfree3").trim();
				String vfree4=hm.get("vfree4")==null?"":hm.get("vfree4").trim();
				String dj=hm.get("dj")==null?"":hm.get("dj").trim();
				String key = pk_invmandoc+pk_invbasdoc+vfree1+vfree2+vfree3+vfree4+dj;
				UFDouble fnum=hm.get("fnum")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("fnum")));
				if(!hm_jl.containsKey(key)){
					hm_jl.put(key, fnum);
				}
			}
		}
		
		
		//����ʱ�������ѯ����������
		sb = new StringBuffer();
		sb.append("select pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vfree5,dj,pk_receiveway,")
		 .append(" sum(num)num, sum(ynum) ynum,sum(fnum)fnum,sum(ddmny) ddmny,sum(jsmny) jsmny,sum(yhmny) yhmny,sum(yjsmny)yjsmny  ")
		 .append(" from ")
		 .append(" (select pk_measure_b,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,null vfree5,vdef11 dj,pk_receiveway,")
		 .append(" nvl(twonum,0) num,nvl(weight,0) ynum, nvl(num,0)fnum,nvl(vdef9,0) ddmny,sum(nvl(vdef3,0))-sum(nvl(preferentialmny,0)) jsmny,sum(nvl(preferentialmny,0)) yhmny,sum(nvl(vdef4,0))yjsmny")
		 .append(" from gt_balancelist_b ")
		 .append("  where nvl(dr,0)=0 and pk_sellpaymentjs='"+pk_sellpaymentjs+"'") 
		 .append("  group by pk_measure_b,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vdef11,pk_receiveway,vdef9,twonum,weight,num")
		 .append(" )a") 
		 .append("  group by  pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vfree5, dj,pk_receiveway")
		 .append("  order by  pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vfree5, dj,pk_receiveway");
		al = (ArrayList<HashMap<String,String>>)queryArrayBySql(sb.toString());
		StringBuffer sb2 = new StringBuffer();//��ѯ�ܶ��μ���
		sb2.append("select sum(num) totalnum ")
		 .append(" from ")
		 .append(" (select pk_measure_b,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,null vfree5,vdef11 dj,pk_receiveway,")
		 .append(" nvl(twonum,0) num,nvl(weight,0) ynum, nvl(num,0)fnum,nvl(vdef9,0) ddmny,sum(nvl(vdef3,0))-sum(nvl(preferentialmny,0)) jsmny,sum(nvl(preferentialmny,0)) yhmny,sum(nvl(vdef4,0))yjsmny")
		 .append(" from gt_balancelist_b ")
		 .append("  where nvl(dr,0)=0 and pk_sellpaymentjs='"+pk_sellpaymentjs+"'") 
		 .append("  group by pk_measure_b,pk_invmandoc,pk_invbasdoc,vfree1,vfree2,vfree3,vfree4,vdef11,pk_receiveway,vdef9,twonum,weight,num")
		 .append(" )a");
		String strtotalnum=queryStrBySql(sb2.toString());
		UFDouble totalnum_sql=strtotalnum==null?new UFDouble(0):new UFDouble(strtotalnum);
		HashMap<String,String>hm_jr=new HashMap<String,String>();
		HashMap<String,UFDouble>hm_ufd=new HashMap<String,UFDouble>();//����������λ����
		HashMap<String,UFDouble>hm_ufd2=new HashMap<String,UFDouble>();//����ԭ���ȼ���
		UFDouble totalnum_insert=new UFDouble(0);
		if(al!=null&&al.size()>0){
			for(int i=0;i<al.size();i++){
				HashMap<String,String> hm = (HashMap<String,String>) al.get(i);
				String pk_invmandoc=hm.get("pk_invmandoc")==null?"":hm.get("pk_invmandoc").trim();
				String pk_invbasdoc=hm.get("pk_invbasdoc")==null?"":hm.get("pk_invbasdoc").trim();
				String vfree1=hm.get("vfree1")==null?"":hm.get("vfree1").trim();
				String vfree2=hm.get("vfree2")==null?"":hm.get("vfree2").trim();
				String vfree3=hm.get("vfree3")==null?"":hm.get("vfree3").trim();
				String vfree4=hm.get("vfree4")==null?"":hm.get("vfree4").trim();
				String dj=hm.get("dj")==null?"":hm.get("dj").trim();
				String pk_receiveway=hm.get("pk_receiveway")==null?"":hm.get("pk_receiveway").trim();
				UFDouble num=hm.get("num")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("num")));
				UFDouble ynum=hm.get("ynum")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("ynum")));
				UFDouble num_3=new UFDouble(num.doubleValue(),3);//��λ���ȼ���
				UFDouble fnum=new UFDouble(0);		
				UFDouble num_insert=new UFDouble(0);
				String key = pk_invmandoc+pk_invbasdoc+vfree1+vfree2+vfree3+vfree4+dj;
				if(!hm_jr.containsKey(key)){
					hm_ufd.put(key, num_3);
					hm_ufd2.put(key, num);
					num_insert=num_3;
					hm_jr.put(key, key);
					fnum=hm_jl.get(key);
				}else{
					UFDouble totalnum = hm_ufd.get(key);
					UFDouble totalnum2 = hm_ufd2.get(key);
					totalnum2=totalnum2.add(num);
					UFDouble totaltwonum=hm_jl2.get(key);
					if(totalnum2.sub(totaltwonum).doubleValue()==0){
						num_insert=totaltwonum.sub(totalnum);
					}else{
						totalnum=totalnum.add(num_3);
						num_insert=num_3;
					}
				}	
				if(i==al.size()-1){
					num_insert=totalnum_sql.sub(totalnum_insert);
				}else{
					totalnum_insert=totalnum_insert.add(num_insert);
				}
				UFDouble ddmny=hm.get("ddmny")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("ddmny")),2);
				UFDouble jsmny=hm.get("jsmny")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("jsmny")),2);
				UFDouble yhmny=hm.get("yhmny")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("yhmny")),2);
				UFDouble yjsmny=hm.get("yjsmny")==null?new UFDouble(0):new UFDouble(String.valueOf(hm.get("yjsmny")),2);
				UFDouble ddprice=num_insert.doubleValue()==0?new UFDouble(0):ddmny.div(ynum);
				UFDouble jsprice=num_insert.doubleValue()==0?new UFDouble(0):jsmny.div(num_insert);
				UFDouble yhprice=num_insert.doubleValue()==0?new UFDouble(0):yhmny.div(num_insert);
				BalanceInfoBVO infovo=new BalanceInfoBVO();
				infovo.setPk_sellpaymentjs(pk_sellpaymentjs);
				infovo.setPk_invmandoc(hm.get("pk_invmandoc"));
				infovo.setPk_invbasdoc(hm.get("pk_invbasdoc"));
                infovo.setVfree1(hm.get("vfree1"));
                infovo.setVfree2(hm.get("vfree2"));
                infovo.setVfree3(hm.get("vfree3"));
                infovo.setVfree4(hm.get("vfree4"));
                infovo.setVfree5(hm.get("vfree5"));
                infovo.setVdef11(hm.get("dj"));
                infovo.setVdef13(pk_receiveway);
                infovo.setSuttle(num_insert);
                infovo.setNum(fnum);
                infovo.setPrice(ddprice);
                infovo.setVdef1(jsprice);
                infovo.setPreferentialprice(yhprice);
				infovo.setMoneys(ddmny);
				infovo.setVdef2(jsmny); 
				infovo.setPreferentialmny(yhmny); 
				infovo.setVdef4(yjsmny);
				infovo.setDr(new Integer(0));
				infovo.setPk_corp(pk_corp);
				insertVObackPK(infovo);
			}
		}
	}
	
	/**
	 * @���� ���ۻ�����㷴���� ����ʱ��д ������ʱ��,��д�տ���������
	 * @param ExAggSellpaymentjsVO iexaggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author ʩ��
	 * @throws ClassNotFoundException 
	 * @serialData 2012-2-23
	 */
	public void deleteBackToBalanceTemp(ExAggSellPaymentJSVO aggvo,String pk_corp) throws BusinessException, ClassNotFoundException {
		StringBuffer listSql_bvo=new StringBuffer();
		listSql_bvo.append(" select * ")
		   .append(" from gt_balancelist_b where nvl(dr,0)=0  and pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' ");
		ArrayList<SuperVO> list_bvo=getVOsfromSql(listSql_bvo.toString(), BalanceListBVO.class.getName());
		//���㷴�����
		if(list_bvo!=null&&list_bvo.size()>0){
			for(int i=0;i<list_bvo.size();i++){
				BalanceListBVO jsmny_bvo=(BalanceListBVO)list_bvo.get(i);
				UFDouble jsmny=jsmny_bvo.getVdef4()==null?new UFDouble(0):jsmny_bvo.getVdef4();//������
		 		String queryYhmny=" select pk_credit from gt_credit where vbillcode='"+jsmny_bvo.getGatheringcode()+"' and pk_corp='"+pk_corp+"' and nvl(jsmny,0) - "+jsmny+">=0";
		 		String pk_credit=queryStrBySql(queryYhmny);
		 		if(Toolkits.isEmpty(pk_credit)){throw new BusinessException("��������,���ܱ���������ʹ�ã�");}
		 		String sql = " update gt_credit set  jsmny=nvl(jsmny,0) - nvl("+jsmny+",0),fhmny=nvl(fhmny,0) - nvl("+jsmny+",0) where pk_credit='"+pk_credit+"'";
		 		updateBYsql(sql);
			 }
		}
	}
}
