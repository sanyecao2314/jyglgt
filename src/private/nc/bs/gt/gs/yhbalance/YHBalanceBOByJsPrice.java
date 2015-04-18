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
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;


/**
 * ���ݽ����Ŀ��ȡ�������Żݽ����̨ҵ������
 * @author ʩ��
 * @version v1.0
 * */
public class YHBalanceBOByJsPrice extends ServerDAO{
	
	public static final String tablecode1 = "gt_balanceinfo_b";
	public static final String tablecode2 = "gt_balancelist_b";
	
	/**
	 * @param �����Żݽ����̨ҵ������
	 * @param ExAggSellPaymentJSVO aggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author ʩ��
	 * @throws ClassNotFoundException 
	 * @serialData 2011-7-6
	 */
	public void writebacktoBalance(ExAggSellPaymentJSVO aggvo,String pk_corp) throws BusinessException, ClassNotFoundException {	
//			BalanceListBVO[] listbvos = (BalanceListBVO[]) aggvo.getTableVO(tablecode2);
//   			String []pk_balancelist_bs=new String[listbvos.length];//������ϸ����
			String sql_gathercode="select distinct gatheringcode from gt_balancelist_b where nvl(dr,0)=0 and pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"'";
   			ArrayList<HashMap<String, String>> list_gathercode=queryArrayBySql(sql_gathercode);
			String []gathercodes=null; //�տ��
   			if(list_gathercode!=null&&list_gathercode.size()>0){
   				gathercodes=new String[list_gathercode.size()];
   				for(int i=0;i<list_gathercode.size();i++){
   					HashMap<String, String> map_gathercode=(HashMap<String, String>)list_gathercode.get(i);
   					gathercodes[i]=map_gathercode.get("gatheringcode");
   				}
   			}
//			for(int i=0;i<listbvos.length;i++){
//				pk_balancelist_bs[i]=listbvos[i].getPk_balancelist_b();
//				gathercodes[i]=listbvos[i].getGatheringcode();
//			}
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
					String pk_sellpaymentjs=aggvo.getParentVO().getPrimaryKey();
					String pk_receiveway=bvo.getPk_receiveway()==null?"":bvo.getPk_receiveway();
					if(preferentialmny.doubleValue()>0){
					    ArrayList<HashMap<String, String>> listMny=getMnyList2((SellPaymentJSVO)aggvo.getParentVO(),gathercodes,pk_receiveway);
					    UFDouble fhmny=preferentialmny; //�Żݽ��:A
					    UFDouble kyhmny=new UFDouble(0);//�����Żݽ��:B
					    if(listMny!=null&&listMny.size()>0){
					    	//���1:�п��Żݽ��
						    for(int j=0;j<listMny.size();j++){
						    	kyhmny=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)listMny.get(j)).get("kyhmny"))));
						    	String pk_credit=Toolkits.getString(((HashMap<String, String>)listMny.get(j)).get("pk_credit")); 
						    	//���1-1:B	-A>=0 ˵�������տ���Ż�
						    	if(kyhmny.sub(fhmny).doubleValue()>=0){
						    		//��д�Żݽ����տ
						    		if (!Toolkits.isEmpty(pk_credit)) {
										StringBuffer updateSbf = new StringBuffer("");
										updateSbf.append("update gt_credit set fhmny=nvl(fhmny,0)-"+ fhmny.doubleValue()+ " ,jsmny=nvl(jsmny,0)-nvl("+fhmny.doubleValue()+",0) where pk_credit='" + pk_credit + "'");						
										updateBYsql(updateSbf.toString());
										String gathercode = ((HashMap<String, String>)listMny.get(j)).get("vbillcode");
										//����һ���µ��Ż�����
										BalanceListBVO newlistbvo = (BalanceListBVO) bvo.clone();
										newlistbvo.setPk_sellpaymentjs(pk_sellpaymentjs);
										newlistbvo.setPk_balancelist_b(null);
										newlistbvo.setBalanceprice(new UFDouble(0.00));
										newlistbvo.setBalancemny(new UFDouble(0.00));
										newlistbvo.setVdef2(new UFDouble(0.00));
										newlistbvo.setVdef3(new UFDouble(0.00));
										newlistbvo.setDr(0);
										newlistbvo.setFyhflag(new UFBoolean(true));
										newlistbvo.setGatheringcode(gathercode);
										newlistbvo.setPreferentialmny(fhmny);
										newlistbvo.setPk_corp(pk_corp);
										insertVObackPK(newlistbvo);
										continue A;
						    	   }
						    	}
						    	//���1-2:B-A<0 ˵�������տ�����Ż�
						    	else if(kyhmny.sub(fhmny).doubleValue()<0){
						    		//���������Żݽ��Żݽ��=B
						    		/******���������Żݽ��*******/
						    		if (!Toolkits.isEmpty(pk_credit)) {
										StringBuffer updateSbf = new StringBuffer("");
										updateSbf.append("update gt_credit set fhmny=nvl(fhmny,0)-"+ kyhmny.doubleValue()+ " ,jsmny=nvl(jsmny,0)-nvl("+kyhmny.doubleValue()+",0) where pk_credit='" + pk_credit + "'");						
										updateBYsql(updateSbf.toString());
										String gathercode = ((HashMap<String, String>)listMny.get(j)).get("vbillcode");
										//����һ���µ��Ż�����
										BalanceListBVO newlistbvo = (BalanceListBVO) bvo.clone();
										newlistbvo.setPk_sellpaymentjs(pk_sellpaymentjs);
										newlistbvo.setPk_balancelist_b(null);
										newlistbvo.setBalanceprice(new UFDouble(0.00));
										newlistbvo.setBalancemny(new UFDouble(0.00));
										newlistbvo.setVdef2(new UFDouble(0.00));
										newlistbvo.setVdef3(new UFDouble(0.00));
										newlistbvo.setDr(0);
										newlistbvo.setFyhflag(new UFBoolean(true));
										newlistbvo.setGatheringcode(gathercode);
										newlistbvo.setPreferentialmny(kyhmny);
										newlistbvo.setPk_corp(pk_corp);
										insertVObackPK(newlistbvo);
						    		   //δ������Żݶ�
										fhmny=fhmny.sub(kyhmny); //A=A-B
										
						    		//
						    		/*******������һ���ۼ��Żݽ��**********/
						    	  }
						        }
						    }	
					    }else{
				            //���3:�����տ��Żݽ��ۼ���ϣ�A>0 
						    //˵����1�����Żݽ��δ���ֻ꣬���������Żݽ��������޿ɷ����
						    //      2�տ��޿��Żݽ��Żݽ��һ��Ҳû����
						    /****************��������������ߵ����4ʱ�Ż����ձ�������*******************************/
				            throw new BusinessException("�Żݽ�������������ʧ��");	
					    }
	            
				 	}else{
				 		//���Ż�
					    UFDouble Kfhmny=new UFDouble(0);//�ɷ������		
					    String pk_credit0="";
					    //���жϵ�ǰ�����տ�Ƿ��пɹ����Ż�
					    ArrayList<HashMap<String,String>> list0=getMnyListFyh((SellPaymentJSVO)aggvo.getParentVO(),pk_receiveway);
					    if(list0!=null&&list0.size()>0){
					    	 Kfhmny=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list0.get(0)).get("kfhmny"))));
						     pk_credit0=((HashMap<String, String>)list0.get(0)).get("pk_credit"); 
					    }else{
					    	throw new BusinessException("�ͻ�����,���������Żݣ�");
					    }
				    	//Ҫ���������
					    UFDouble cemny = new UFDouble(0).sub(preferentialmny);			    	
				    	if((Kfhmny.sub(cemny)).doubleValue()>=0){
				    		StringBuffer updateSbf = new StringBuffer("");
							updateSbf.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ cemny.doubleValue()+ " ,jsmny=nvl(jsmny,0)+"+ cemny.doubleValue()+ " where pk_credit='" + pk_credit0 + "'");						
							updateBYsql(updateSbf.toString());
							String gathercode = ((HashMap<String, String>)list0.get(0)).get("vbillcode");
							//����һ���µ��Ż�����
							BalanceListBVO newlistbvo = (BalanceListBVO) bvo.clone();
							newlistbvo.setPk_sellpaymentjs(pk_sellpaymentjs);
							newlistbvo.setPk_balancelist_b(null);
							newlistbvo.setBalanceprice(new UFDouble(0.00));
							newlistbvo.setBalancemny(new UFDouble(0.00));
							newlistbvo.setVdef2(new UFDouble(0.00));
							newlistbvo.setVdef3(new UFDouble(0.00));
							newlistbvo.setDr(0);
							newlistbvo.setFyhflag(new UFBoolean(true));
							newlistbvo.setGatheringcode(gathercode);
							newlistbvo.setPreferentialmny(new UFDouble(0).sub(cemny));
							newlistbvo.setPk_corp(pk_corp);
							insertVObackPK(newlistbvo);
							continue A;
				    	}else{
				    		StringBuffer updateSbf = new StringBuffer("");
							updateSbf.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ Kfhmny.doubleValue()+ " ,jsmny=nvl(jsmny,0)+"+ Kfhmny.doubleValue()+ " where pk_credit='" + pk_credit0 + "'");						
							updateBYsql(updateSbf.toString());
							String gathercode = ((HashMap<String, String>)list0.get(0)).get("vbillcode");
							//����һ���µ��Ż�����
							BalanceListBVO newlistbvo = (BalanceListBVO) bvo.clone();
							newlistbvo.setPk_sellpaymentjs(pk_sellpaymentjs);
							newlistbvo.setPk_balancelist_b(null);
							newlistbvo.setBalanceprice(new UFDouble(0.00));
							newlistbvo.setBalancemny(new UFDouble(0.00));
							newlistbvo.setVdef2(new UFDouble(0.00));
							newlistbvo.setVdef3(new UFDouble(0.00));
							newlistbvo.setDr(0);
							newlistbvo.setFyhflag(new UFBoolean(true));
							newlistbvo.setGatheringcode(gathercode);
							newlistbvo.setPreferentialmny(new UFDouble(-Kfhmny.doubleValue()));
							newlistbvo.setPk_corp(pk_corp);
							insertVObackPK(newlistbvo);
							cemny = cemny.sub(Kfhmny);
							ArrayList<HashMap<String,String>> list_fyhmny=getMnyListFyh((SellPaymentJSVO)aggvo.getParentVO(),pk_receiveway);
						    if(list_fyhmny!=null&&list_fyhmny.size()>0){
						    	//���1:�пɷ������
							    for(int k=0;k<list_fyhmny.size();k++){
							    	Kfhmny=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list_fyhmny.get(k)).get("kfhmny"))));
							    	String pk_credit=((HashMap<String, String>)list_fyhmny.get(k)).get("pk_credit"); 
							    	gathercode = ((HashMap<String, String>)list_fyhmny.get(k)).get("vbillcode");
							    	//���1-1:B-A>=0 ˵�������տ������
							    	if(Kfhmny.doubleValue()-cemny.doubleValue()>=0){
										StringBuffer sb = new StringBuffer("");
										sb.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ cemny.doubleValue()+ " ,jsmny=nvl(jsmny,0)+"+ cemny.doubleValue()+ " where pk_credit='" + pk_credit + "'");						
										updateBYsql(sb.toString());
										//����һ���µ��Ż����ݲ���̯
										newlistbvo = (BalanceListBVO) bvo.clone();
										newlistbvo.setPk_sellpaymentjs(pk_sellpaymentjs);
										newlistbvo.setPk_balancelist_b(null);
										newlistbvo.setBalanceprice(new UFDouble(0.00));
										newlistbvo.setBalancemny(new UFDouble(0.00));
										newlistbvo.setVdef2(new UFDouble(0.00));
										newlistbvo.setVdef3(new UFDouble(0.00));
										newlistbvo.setDr(0);
										newlistbvo.setFyhflag(new UFBoolean(true));
										newlistbvo.setGatheringcode(gathercode);
										newlistbvo.setPreferentialmny(new UFDouble(-cemny.doubleValue()));
										newlistbvo.setPk_corp(pk_corp);
										insertVObackPK(newlistbvo);
										//��ʱ�ѻ�дȫ��,�ó�0.�Ա�����������Ͳ����ж���.
										cemny=new UFDouble(0);
										continue A;
							    	}						    	
							    	//���1-2:B-A<0 ˵�������տ��������
							    	else if(Kfhmny.doubleValue()-cemny.doubleValue()<0){
							    		//�������ַ������������=B
							    		/******�������ַ������*******/
							    		if (!Toolkits.isEmpty(pk_credit)) {
											StringBuffer sb = new StringBuffer("");
											sb.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ Kfhmny.doubleValue()+ " ,jsmny=nvl(jsmny,0)+"+ Kfhmny.doubleValue()+ " where pk_credit='" + pk_credit + "'");						
											updateBYsql(sb.toString());
											//����һ���µ��Ż����ݲ���̯
											newlistbvo = (BalanceListBVO) bvo.clone();
											newlistbvo.setPk_sellpaymentjs(pk_sellpaymentjs);
											newlistbvo.setPk_balancelist_b(null);
											newlistbvo.setBalanceprice(new UFDouble(0.00));
											newlistbvo.setBalancemny(new UFDouble(0.00));
											newlistbvo.setVdef2(new UFDouble(0.00));
											newlistbvo.setVdef3(new UFDouble(0.00));
											newlistbvo.setDr(0);
											newlistbvo.setFyhflag(new UFBoolean(true));
											newlistbvo.setGatheringcode(gathercode);
											newlistbvo.setPreferentialmny(new UFDouble(-Kfhmny.doubleValue()));
											newlistbvo.setPk_corp(pk_corp);
											insertVObackPK(newlistbvo);
							    		    //δ����ķ�����
											cemny=cemny.sub(Kfhmny); //A=A-B
							    		/*******������һ���ۼ��������**********/
							    	  }
							        }
							    }	
						    }else{
						    	throw new BusinessException("�ͻ�����,���������Żݣ�");
						    }
				    	}	
				 	}

				 	}
			//���½�����ϸ�Żݽ��Ϊ0
			String updateSql="update gt_balancelist_b set vdef5=preferentialmny,preferentialmny=0 where  pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' and  nvl(fyhflag,'N')='N' ";
			updateBYsql(updateSql);
		}
	}
	
	/**
	 * @param �����Żݽ��㷴������̨ҵ������
	 * @param ExAggSellPaymentJSVO aggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author ʩ��
	 * @throws ClassNotFoundException 
	 * @serialData 2011-7-6
	 */
	public void deletebacktoBalance2(ExAggSellPaymentJSVO aggvo,String pk_corp) throws BusinessException, ClassNotFoundException {
//			BalanceListBVO[] listbvos = (BalanceListBVO[]) aggvo.getTableVO(tablecode2);
//   			String []pk_balancelist_bs=new String[listbvos.length];//������ϸ����
//   			String []gathercodes=new String[listbvos.length]; //�տ��
//			for(int i=0;i<listbvos.length;i++){
//				pk_balancelist_bs[i]=listbvos[i].getPk_balancelist_b();
//				gathercodes[i]=listbvos[i].getGatheringcode();
//			}
			StringBuffer listSql_yhmny=new StringBuffer();
			StringBuffer listSql_bvo=new StringBuffer();
			listSql_yhmny.append("select * ")
			   .append(" from gt_balancelist_b where nvl(dr,0)=0 and nvl(fyhflag,'N')='Y' and  pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' ");
			listSql_bvo.append(" select * ")
			   .append(" from gt_balancelist_b where nvl(dr,0)=0 and nvl(vdef3,0)>0 and pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' ");//pk_balancelist_b in ("+combinArrayToString(pk_balancelist_bs)+")");
			ArrayList<SuperVO> list_yhmny=getVOsfromSql(listSql_yhmny.toString(), BalanceListBVO.class.getName());
			ArrayList<SuperVO> list_bvo=getVOsfromSql(listSql_bvo.toString(), BalanceListBVO.class.getName());
			//�Żݷ������
			if(list_yhmny!=null&&list_yhmny.size()>0){
				for(int i=0;i<list_yhmny.size();i++){
					BalanceListBVO yhmny_bvo=(BalanceListBVO)list_yhmny.get(i);
					UFDouble preferentialmny=yhmny_bvo.getPreferentialmny()==null?new UFDouble(0):yhmny_bvo.getPreferentialmny();//�Żݽ��
				 	if(preferentialmny.doubleValue()<0){
				 		String queryYhmny=" select pk_credit from gt_credit where vbillcode='"+yhmny_bvo.getGatheringcode()+"' and nvl(jsmny,0)+"+preferentialmny+">=0";
				 		String obj=queryStrBySql(queryYhmny);
				 		if(Toolkits.isEmpty(obj)){throw new BusinessException("��������,���ܱ���������ʹ�ã�");}
				 		//���Ż�
				 		String sql = " update gt_credit set fhmny=nvl(fhmny,0)+nvl("+preferentialmny+",0),jsmny=nvl(jsmny,0)+nvl("+preferentialmny+",0) where vbillcode='"+yhmny_bvo.getGatheringcode()+"' and pk_corp='"+pk_corp+"' ";
				 		updateBYsql(sql);
				 	}else{
				 		String queryYhmny=" select pk_credit from gt_credit where vbillcode='"+yhmny_bvo.getGatheringcode()+"' and (nvl(skmny,0)-nvl(fhmny,0)-nvl(tkmny,0)-"+preferentialmny+")>=0";
				 		String obj=queryStrBySql(queryYhmny);
				 		if(Toolkits.isEmpty(obj)){throw new BusinessException("�����ɷ�������,���ܱ���������ʹ�ã�");}				 		
				 		String sql = " update gt_credit set fhmny=nvl(fhmny,0)+nvl("+preferentialmny+",0),jsmny=nvl(jsmny,0)+nvl("+preferentialmny+",0) where vbillcode='"+yhmny_bvo.getGatheringcode()+"' and pk_corp='"+pk_corp+"' ";
				 		updateBYsql(sql);	
				 	}     
				}
			}
			//ɾ���Ż�������ϸ����
			String delSql=" delete from gt_balancelist_b where nvl(dr,0)=0 and nvl(fyhflag,'N')='Y'  and  pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' ";
			updateBYsql(delSql);
			//���㷴�����
			if(list_bvo!=null&&list_bvo.size()>0){
				for(int i=0;i<list_bvo.size();i++){
					BalanceListBVO jsmny_bvo=(BalanceListBVO)list_bvo.get(i);
					UFDouble jsmny=jsmny_bvo.getVdef4()==null?new UFDouble(0):jsmny_bvo.getVdef4();//�Żݽ��
			 		String queryYhmny=" select pk_credit from gt_credit where vbillcode='"+jsmny_bvo.getGatheringcode()+"' and nvl(jsmny,0) - "+jsmny+">=0";
			 		String obj=queryStrBySql(queryYhmny);
			 		if(Toolkits.isEmpty(obj)){throw new BusinessException("��������,���ܱ���������ʹ�ã�");}
			 		String sql = " update gt_credit set  jsmny=nvl(jsmny,0) - nvl("+jsmny+",0) where vbillcode='"+jsmny_bvo.getGatheringcode()+"' and pk_corp='"+pk_corp+"' ";
			 		updateBYsql(sql);
				 }
			}
			//���½�����ϸ�Żݽ��Ϊ0
			String updateSql="update gt_balancelist_b set preferentialmny=vdef5,vdef5=0 where  pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' and  nvl(fyhflag,'N')='N' ";
			updateBYsql(updateSql);
	}

	
	/**
	 * @param �����Żݽ��㷴������̨ҵ������
	 * @param ExAggSellPaymentJSVO aggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author ʩ��
	 * @throws ClassNotFoundException 
	 * @serialData 2011-7-6
	 */
	public void deletebacktoBalance3(ExAggSellPaymentJSVO aggvo,String pk_corp) throws BusinessException, ClassNotFoundException {
			StringBuffer listSql_yhmny=new StringBuffer();
//			StringBuffer listSql_bvo=new StringBuffer();
			listSql_yhmny.append("select * ")
			   .append(" from gt_balancelist_b where nvl(dr,0)=0 and nvl(fyhflag,'N')='Y' and  pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' ");
//			listSql_bvo.append(" select * ")
//			   .append(" from gt_balancelist_b where nvl(dr,0)=0 and nvl(vdef4,0)>0 and pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' ");//pk_balancelist_b in ("+combinArrayToString(pk_balancelist_bs)+")");
			ArrayList<SuperVO> list_yhmny=getVOsfromSql(listSql_yhmny.toString(), BalanceListBVO.class.getName());
//			ArrayList<SuperVO> list_bvo=getVOsfromSql(listSql_bvo.toString(), BalanceListBVO.class.getName());
			//�Żݷ������
			if(list_yhmny!=null&&list_yhmny.size()>0){
				for(int i=0;i<list_yhmny.size();i++){
					BalanceListBVO yhmny_bvo=(BalanceListBVO)list_yhmny.get(i);
					UFDouble preferentialmny=yhmny_bvo.getPreferentialmny()==null?new UFDouble(0):yhmny_bvo.getPreferentialmny();//�Żݽ��
				 	if(preferentialmny.doubleValue()<0){
				 		String queryYhmny=" select pk_credit from gt_credit where vbillcode='"+yhmny_bvo.getGatheringcode()+"' and nvl(jsmny,0)+"+preferentialmny+">=0";
				 		String obj=queryStrBySql(queryYhmny);
				 		if(Toolkits.isEmpty(obj)){throw new BusinessException("��������,���ܱ���������ʹ�ã�");}
				 		//���Ż�
				 		String sql = " update gt_credit set fhmny=nvl(fhmny,0)+nvl("+preferentialmny+",0),jsmny=nvl(jsmny,0)+nvl("+preferentialmny+",0) where vbillcode='"+yhmny_bvo.getGatheringcode()+"' and pk_corp='"+pk_corp+"' ";
				 		updateBYsql(sql);
				 	}else{
				 		String queryYhmny=" select pk_credit from gt_credit where vbillcode='"+yhmny_bvo.getGatheringcode()+"' and (nvl(skmny,0)-nvl(fhmny,0)-nvl(tkmny,0)-"+preferentialmny+")>=0";
				 		String obj=queryStrBySql(queryYhmny);
				 		if(Toolkits.isEmpty(obj)){throw new BusinessException("�����ɷ�������,���ܱ���������ʹ�ã�");}				 		
				 		String sql = " update gt_credit set fhmny=nvl(fhmny,0)+nvl("+preferentialmny+",0),jsmny=nvl(jsmny,0)+nvl("+preferentialmny+",0) where vbillcode='"+yhmny_bvo.getGatheringcode()+"' and pk_corp='"+pk_corp+"' ";
				 		updateBYsql(sql);	
				 	}     
				}
			}
			//ɾ���Ż�������ϸ����
			String delSql=" delete from gt_balancelist_b where nvl(dr,0)=0 and nvl(fyhflag,'N')='Y'  and  pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' ";
			updateBYsql(delSql);
//			//���㷴�����
//			if(list_bvo!=null&&list_bvo.size()>0){
//				for(int i=0;i<list_bvo.size();i++){
//					BalanceListBVO jsmny_bvo=(BalanceListBVO)list_bvo.get(i);
//					UFDouble jsmny=jsmny_bvo.getVdef4()==null?new UFDouble(0):jsmny_bvo.getVdef4();//�Żݽ��
//			 		String queryYhmny=" select pk_credit from gt_credit where vbillcode='"+jsmny_bvo.getGatheringcode()+"' and nvl(jsmny,0) - "+jsmny+">=0";
//			 		String obj=queryStrBySql(queryYhmny);
//			 		if(Toolkits.isEmpty(obj)){throw new BusinessException("��������,���ܱ���������ʹ�ã�");}
//			 		String sql = " update gt_credit set  jsmny=nvl(jsmny,0) - nvl("+jsmny+",0) where vbillcode='"+jsmny_bvo.getGatheringcode()+"' and pk_corp='"+pk_corp+"' ";
//			 		updateBYsql(sql);
//				 }
//			}
			//���½�����ϸ�Żݽ��Ϊ0
			String updateSql="update gt_balancelist_b set preferentialmny=vdef5,vdef5=0 where  pk_sellpaymentjs='"+((SellPaymentJSVO)aggvo.getParentVO()).getPk_sellpaymentjs()+"' and  nvl(fyhflag,'N')='N' ";
			updateBYsql(updateSql);
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
	 * @param �����Żݽ��㷴������̨ҵ������
	 * @param ExAggSellPaymentJSVO aggvo
	 * @param String pk_corp
	 * @throws BusinessException
	 * @author ʩ��
	 * @serialData 2011-7-6
	 */
	public void deletebacktoBalance(ExAggSellPaymentJSVO aggvo,String pk_corp,BalanceListBVO listbvo) throws BusinessException {
		try {
			UFDouble preferentialmny=new UFDouble(0);//�Żݽ��
			preferentialmny=listbvo.getPreferentialmny()==null?new UFDouble(0):listbvo.getPreferentialmny();
			if(preferentialmny.doubleValue()==0)return;
		 	if(preferentialmny.doubleValue()<0){
		 		UFDouble cemny = preferentialmny;
		 		//���Ż�
		 		String sql = " update gt_credit set fhmny=nvl(fhmny,0)+nvl("+cemny+",0),jsmny=nvl(jsmny,0)+nvl("+cemny+",0) where vbillcode='"+listbvo.getGatheringcode()+"' and pk_corp='"+pk_corp+"' ";
		 		updateBYsql(sql);
		 	}else{
		 		//���Ż�
			    UFDouble Kfhmny=new UFDouble(0);//�ɷ������		
			    //���жϵ�ǰ�����տ�Ƿ��пɹ����Ż�
			    String pk_credit0="";
			    ArrayList<HashMap<String,String>> list0=getMnyList((SellPaymentJSVO)aggvo.getParentVO(),listbvo.getGatheringcode());
			    if(list0!=null&&list0.size()>0){
			    	 Kfhmny=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list0.get(0)).get("kfhmny"))));
				     pk_credit0=((HashMap<String, String>)list0.get(0)).get("pk_credit"); 
			    }else{
			    	throw new BusinessException("�ͻ�����,��������");
			    }
		    	//Ҫ���������
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
				    	//���1:�пɷ������
					    for(int i=0;i<list.size();i++){
					    	Kfhmny=Toolkits.getUFDouble((String.valueOf(((HashMap<String, String>)list.get(i)).get("kfhmny"))));
					    	String pk_credit=((HashMap<String, String>)list.get(i)).get("pk_credit"); 
					    	//���1-1:B-A>=0 ˵�������տ������
					    	if(Kfhmny.doubleValue()-cemny.doubleValue()>=0){
								StringBuffer sb = new StringBuffer("");
								sb.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ cemny.doubleValue()+ " ,jsmny=nvl(jsmny,0)+"+ cemny.doubleValue()+ " where pk_credit='" + pk_credit + "'");						
								cemny=new UFDouble(0);
								break;
					    	}						    	
					    	//���1-2:B-A<0 ˵�������տ��������
					    	else if(Kfhmny.doubleValue()-cemny.doubleValue()<0){
					    		//�������ַ������������=B
					    		/******�������ַ������*******/
					    		if (!Toolkits.isEmpty(pk_credit)) {
									StringBuffer sb = new StringBuffer("");
									sb.append("update gt_credit set fhmny=nvl(fhmny,0)+"+ Kfhmny.doubleValue()+ " ,jsmny=nvl(jsmny,0)+"+ Kfhmny.doubleValue()+ " where pk_credit='" + pk_credit + "'");						
									updateBYsql(sb.toString());
					    		//δ����ķ�����
									cemny=cemny.sub(Kfhmny); //A=A-B
					    		//
					    		/*******������һ���ۼ��������**********/
					    	  }
					        }
					    }	
				    }else{
				    	throw new BusinessException("�ͻ�����,���������Żݣ�");
				    }
		    	}
	            //���3:�����տ�����ķ������ۼ���ϣ�A>0 
			    //˵����1���з������δ���ֻ꣬�������ַ������������޿ɷ����
			    /****************��������������ߵ����4ʱ�Ż����ձ�������*******************************/
		    	if(cemny.doubleValue()>0)
		    		throw new BusinessException("�ͻ�����,ɾ�����ܻ���ֳ��տ������");		
		 	}
		} catch (BusinessException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	

	
	/**
	 * ��ȡ�ɷ������
	 * @param int i
	 * @param MeasureHeaderVO hvo
	 * @param String pk_receiveway
	 * */
	private ArrayList<HashMap<String,String>> getMnyList(SellPaymentJSVO hvo,String vbillcode) throws BusinessException{
		ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
		// �жϿͻ��ɷ���ʣ���=�տ�� �C ������ �C �˿�� 
		// �ͻ�����
		String cumandoc = Toolkits.getString(hvo.getPk_cumandoc());
		String pk_corp=hvo.getPk_corp()== null ? "" : hvo.getPk_corp().toString().trim();; 
		StringBuffer strSk=new StringBuffer("");
		strSk.append(" select vbillcode,pk_credit,(nvl(skmny,0) - nvl(fhmny,0) - nvl(tkmny,0))kfhmny,'Y'flag from gt_credit where (nvl(skmny,0) - nvl(fhmny,0) - nvl(tkmny,0))>0 ")
		   .append( " and receivetype in("+IJyglgtConst.RECEIVETYPE_QC+","+IJyglgtConst.RECEIVETYPE_YS+") and pk_cumandoc= '"+ cumandoc+ "' and vbillcode = '"+vbillcode+"' and vbillstatus='"+IJyglgtBillStatus.CHECKPASS+"' and pk_corp='"+pk_corp+"' and nvl(dr,0)=0 order by receivemnydate,vbillcode ");
		list = queryArrayBySql(strSk.toString());
		return list;
	}
	
	/**
	 * ��ȡ�ɷ������
	 * @param int i
	 * @param MeasureHeaderVO hvo
	 * @param String pk_receiveway
	 * */
	private ArrayList<HashMap<String,String>> getMnyListFyh(SellPaymentJSVO hvo,String pk_receiveway) throws BusinessException{
		ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
		// �жϿͻ��ɷ���ʣ���=�տ�� �C ������ �C �˿�� 
		// �ͻ�����
		String cumandoc = Toolkits.getString(hvo.getPk_cumandoc());
		String pk_corp=hvo.getPk_corp()== null ? "" : hvo.getPk_corp().toString().trim();; 
		StringBuffer strSk=new StringBuffer("");
		strSk.append(" select vbillcode,pk_credit,(nvl(skmny,0) - nvl(fhmny,0) - nvl(tkmny,0))kfhmny,'Y'flag from gt_credit where (nvl(skmny,0) - nvl(fhmny,0) - nvl(tkmny,0))>0 ")
		   .append( " and receivetype in("+IJyglgtConst.RECEIVETYPE_QC+","+IJyglgtConst.RECEIVETYPE_YS+") and pk_cumandoc= '"+ cumandoc+ "' and pk_receiveway = '"+pk_receiveway+"' and vbillstatus='"+IJyglgtBillStatus.CHECKPASS+"' and pk_corp='"+pk_corp+"' and nvl(dr,0)=0 order by receivemnydate,vbillcode ");
		list = queryArrayBySql(strSk.toString());
		return list;
	}
	
	/**
	 * ��ȡ�����Żݽ��
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
			throw new BusinessException("��ѯ�տʽ����");
		}
		return pk_receiveway;
	}
	
//	private ArrayList<HashMap<String,String>> getMnyList3(SellPaymentJSVO hvo,String vbillcode) throws BusinessException{
//		ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
//		// �жϿͻ����˿���㷴����� 
//		// �ͻ�����
//		String cumandoc = Toolkits.getString(hvo.getPk_cumandoc());
//		String pk_corp=hvo.getPk_corp()== null ? "" : hvo.getPk_corp().toString().trim();; 
//		StringBuffer strSk=new StringBuffer("");
//		strSk.append(" select vbillcode,pk_credit,(nvl(jsmny,0)kjsmny from gt_credit where nvl(jsmny,0)>0 and nvl(fhmny,0)>0 ")
//		   .append( " and receivetype in("+IGTConst.RECEIVETYPE_QC+","+IGTConst.RECEIVETYPE_SK+") and pk_cumandoc= '"+ cumandoc+ "' and vbillcode = '"+vbillcode+"' and vbillstatus='"+IGTBillStatus.CHECKPASS+"' and pk_corp='"+pk_corp+"' and nvl(dr,0)=0 order by receivemnydate,vbillcode desc ");
//		list = queryArrayBySql(strSk.toString());
//		return list;
//	}

		
}
