package nc.bs.jyglgt.saleoutdao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nc.bs.jyglgt.dao.ServerDAO;
import nc.bs.logging.Logger;
import nc.bs.pub.pf.PfUtilBO;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.jdbc.framework.processor.MapProcessor;
import nc.vo.ic.pub.bill.GeneralBillHeaderVO;
import nc.vo.ic.pub.bill.GeneralBillItemVO;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.ic.pub.smallbill.SMGeneralBillVO;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.scm.ic.bill.FreeVO;
import nc.vo.so.so001.SaleorderBVO;


/**
 * @author ʩ��
 * @���ܣ� Ƥ���Ӽ���(���۳���)��̨������
 * 
 */
public class SaleoutDAO extends ServerDAO {
	public SaleoutDAO() {
		super();
	}

	PersistenceManager sManager = null;

	/**
	 * ����Ƥ���Ӽ���������
	 * */
	public void updateBFPDC(String key,int flag,String datasource) throws BusinessException {
		try {
			JdbcSession session = getsManager(datasource).getJdbcSession();
			String sql = " update BFFHCL set BFFHCL_CKBZ='"+flag+"' where BFFHCL_CKBZ='0' and BFFHCL_DJBH='"+key+"'";
			session.executeUpdate(sql);
		} catch (DbException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

	}
	
	/**
	 * ���м���������
	 * */
	public void insertInteSaleoutByBfgl(String datasource) throws BusinessException {
		insertInteSaleout(datasource);
	}

	/**
	 * �����м������
	 * @throws BusinessException 
	 * **/
    public void insertInteSaleout(String datasource) throws BusinessException{
		try {
			JdbcSession session = getsManager(datasource).getJdbcSession();
			// ��ѯ���
			String sql = "select  * from BFFHCL  where bffhcl_djlx='1' and bffhcl_ckbz !='1' ";
			// ���ݿ���ʲ���
			ArrayList al = (ArrayList) session.executeQuery(sql,new MapListProcessor());
			// ���������
			if (al!=null&&al.size() > 0) {
				for (int i = 0; i < al.size(); i++) {
					HashMap hm = (HashMap) al.get(i);
					String cgeneralhid = null;
					try{
						//�жϴ���ֵ�Ƿ��Ѿ����ڣ������������
						String intehsql = "select cgeneralhid from ic_general_h where cbilltypecode ='4C' and vuserdef20 ='"+Toolkits.getUFDouble(hm.get("bffhcl_djbh"))+"' and nvl(dr,0)=0 ";
						String pk_gen=queryStrBySql(intehsql);
						if(pk_gen!=null&&!pk_gen.equals("")){
							continue;
						}
						//�������ݵ�������۳��ⵥ
    					cgeneralhid=insertGen(hm,session); 
    					//�����Զ���ϵͳ���
    					updateBFPDC(Toolkits.getString(hm.get("bffhcl_djbh")),1,datasource);
					}catch(Exception e){
						e.printStackTrace();
						//���洫����̳��ֵĴ�����ʷ��¼
						insertExLog(e,Toolkits.getString(hm.get("bffhcl_djbh")),Toolkits.getString(hm.get("bffhcl_c6")));					
						continue;
					}
					//���Ѿ�����ɹ����ڴ�����ʷ��¼���е����������
					delExLog(Toolkits.getString(hm.get("bffhcl_djbh")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		} 
		finally {
			if (sManager != null)
				sManager.release();// ��Ҫ�رջỰ
		}
    }
    

    
    /**
     * ���洫����̳��ֵĴ�����ʷ��¼
     * */
    private void insertExLog(Exception e,String bfsfcl_djbh,String pk_corp){
    	nc.vo.jyglgt.bfsfcllog.BfsfclLogVO logvo=new nc.vo.jyglgt.bfsfcllog.BfsfclLogVO();
		logvo.setDr(new Integer(0));
		logvo.setPk_corp(pk_corp);
		logvo.setBfsfcl_djbh(bfsfcl_djbh);
		logvo.setExlog(e.getMessage());
		logvo.setBilltype("4C");
		try {
			insertVObackPK(logvo);
		} catch (BusinessException e1) {
			e1.printStackTrace();
			Logger.debug("���洫����̳��ֵĴ�����ʷ��¼�����쳣:insertExLog,�����������쳣");
		}
    }
    
    /**
     * ���Ѿ�����ɹ����ڴ�����ʷ��¼���е����������
     * */
    private void delExLog(String bfsfcl_djbh){
    	String sql=" select pk_bfsfcllog from jyglgt_bfsfcllog  where bfsfcl_djbh='"+bfsfcl_djbh+"' ";
    	ArrayList<HashMap<String,String>> list= null;
		try {
			list = queryArrayBySql(sql);
	    	if(list!=null&&list.size()>0){
	    		for(HashMap<String,String> map:list){
		    		String delsql=" delete from  jyglgt_bfsfcllog where pk_bfsfcllog='"+map.get("pk_bfsfcllog")+"'";
		    		updateBYsql(delsql);
	    		}
	    	}
		} catch (BusinessException e) {
			e.printStackTrace();
			Logger.debug("���Ѿ�����ɹ����ڴ�����ʷ��¼���е���������������쳣:delExLog,�����������쳣");
		}

    }

	private PersistenceManager getsManager(String datasource) throws DbException {
		if (sManager == null)
			sManager = PersistenceManager.getInstance(datasource);
		return sManager;
	}
	
	private String u8TsToNcTs(String u8Ts){
		String ncTs="";
		if(u8Ts==null||u8Ts.equalsIgnoreCase("")||u8Ts.length()<=19){
			ncTs=u8Ts;
		}else{
			ncTs=u8Ts.substring(0,19);
		}
		return ncTs;
	}

	
	private UFDate getNCDateFormat(Object object) {
		if(object == null || object.toString().length() != 8){
			return null;
		}
		String oldstr = object.toString().substring(0, 4)+"-"+object.toString().substring(4, 6)+"-"+object.toString().substring(6, 8);
		return new UFDate(oldstr);
	}
	
	/**
	 * ���ɿ�����۳��ⵥ
	 * @param session 
	 * @throws BusinessException 
	 * @throws DbException 
	 * **/
    public String insertGen(HashMap hm, JdbcSession session) throws BusinessException, DbException{
    	String djbh = Toolkits.getString(hm.get("bffhcl_djbh"));
    	String pk_corp=Toolkits.getString(hm.get("bffhcl_c6"));/*�Ƴ�����*/
		GeneralBillHeaderVO genHVO = new GeneralBillHeaderVO();
		String cuserid = getCuserid("0000");//Ĭ��ֵ
		if("".equals(cuserid)){
			throw new BusinessException("����˾��Ա�����ֵ��û�����û������в�ѯ���û���"); 
		}
		genHVO.setAttributeValue("coperatorid", cuserid);//�Ƶ���
		genHVO.setAttributeValue("coperatoridnow", cuserid);//�Ƶ���
		UFDate resdate = getNCDateFormat(hm.get("bffhcl_rq"));//ת��NC�ĸ�ʽ 8λ��ϵͳ'20140122' --->'2014-01-22'
		genHVO.setAttributeValue("dbilldate", resdate);//��������
		
//		String cuserid = getCuserid(Toolkits.getString(hm.get("bffhcl_shy")));	//����Ա
//		if("".equals(cuserid)){
//			throw new BusinessException("���ݷ���Ա�����ֵ��û�����û������в�ѯ���û���"); 
//		}
//		genHVO.setAttributeValue("coperatorid", cuserid);//�Ƶ���
		String cwarehouseid = getCwarehouseid(Toolkits.getString(hm.get("bffhcl_ckbh")),pk_corp);
		if("".equals(cwarehouseid)){
			throw new BusinessException("���ݴ���Ĳֿ����û���ҵ��ֿ⣡");
		}
		genHVO.setAttributeValue("cwarehouseid", cwarehouseid);//�ֿ�	
		
		//���۳���,�Ҷ�Ӧ�Ŀ���,���м��û�п�����Ϣ
		String custpk = getDocPkByCode(pk_corp,Toolkits.getString(hm.get("bffhcl_shbh")),2); /*���̱��*/
		if("".equals(custpk)){
			throw new BusinessException("���ݿ��̱��봫���ֵ��û���ڿ��̵������ҵ���");
		}
		genHVO.setAttributeValue("ccustomerid",custpk);////�ͻ�ID
		
		
		if("".equals(getPk_calbody(cwarehouseid))){
			throw new BusinessException("�����֯Ϊ�գ�");
		}
		genHVO.setAttributeValue("pk_calbody",getPk_calbody(cwarehouseid));//�����֯PK
		if ("".equals(getPk_rdcl("���۳���"))) {
			throw new BusinessException("û���ҵ����۳����Ӧ���շ����");
		}
		genHVO.setAttributeValue("cdispatcherid",getPk_rdcl("���۳���"));//�շ����PK
//		String pk_cubasdoc=getCustcode(Toolkits.getString(hm.get("bfsfcl_shbh"))); /*��Ӧ�̱��*/
//		genHVO.setAttributeValue("pk_cubasdoc",pk_cubasdoc);//��Ӧ�̻�������ID
		genHVO.setAttributeValue("vuserdef19",Toolkits.getString(hm.get("bffhcl_clbh")));//����/*�������*/
		genHVO.setAttributeValue("vuserdef20",Toolkits.getString(hm.get("bffhcl_djbh")));//���������
		genHVO.setAttributeValue("vuserdef1",Toolkits.getString(hm.get("bffhcl_pczh")));//�ɳ����� add by gdt
		String ywlb = Toolkits.getString(hm.get("bffhcl_ywlb"));//�Զ�����ҵ����� add by gdt ��Ʒ��������Ʒ��������Ʒ������������Ʒ�໧����
		
//		if (StringUtils.isNotEmpty(String.valueOf(hm.get("bffhcl_fhbh")))) {
//			getPk_trancust(hm.get("bffhcl_fhbh"));
//			genHVO.setAttributeValue("ctrancustid",Toolkits.getString(hm.get("bffhcl_clbh")));//������
//		}
		genHVO.setAttributeValue("vuserdef19",Toolkits.getString(hm.get("bffhcl_clbh")));//����/*�������*/
		 
//		try {
//			//���ɵ��ݱ��
//			String billNo = BillcodeRuleBO_Client.getBillCode("4C", pk_corp,null, null);
//			if(billNo!=null)
//				genHVO.setVbillcode(billNo);
//			else
//				genHVO.setVbillcode("4C000001");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		genHVO.setAttributeValue("fbillflag", nc.vo.pub.VOStatus.NEW);//Toolkits.getInteger(saleHVO.getIverifystate()).toString());//����״̬
		genHVO.setAttributeValue("cbilltypecode", "4C");//
		genHVO.setAttributeValue("pk_corp",pk_corp);//pk_corp
		genHVO.setAttributeValue("dr", 0);
		
		//�ӱ�
		GeneralBillItemVO[] genBVO = null;
		if("��Ʒ����".equals(ywlb)||"��Ʒ����".equals(ywlb)){//��Ʒ��������Ʒ������û�и�������
			genBVO = new GeneralBillItemVO[1];
			for (int i = 0; i < genBVO.length; i++) {
	    		genBVO[i]  = new GeneralBillItemVO();
	    		genBVO[i].setAttributeValue("dr", 0);
	    		genBVO[i].setAttributeValue("pk_corp", pk_corp);
	    		genBVO[i].setNegallowed(UFBoolean.TRUE);//�������
	    		genBVO[i].setDbizdate( genHVO.getDbilldate());   		
	    		ArrayList<HashMap<String, String>> listpkm = getPk_invmb(Toolkits.getString(hm.get("bffhcl_wlbh")), pk_corp);
	    		if(listpkm.size()==0){
	    			throw new BusinessException("�������"+Toolkits.getString(hm.get("bffhcl_wlbh"))+"��NC��û��ά����");
	    		}
	    		genBVO[i].setAttributeValue("crowno", 10*(i+1));//�к�
	    		genBVO[i].setAttributeValue("cinventoryid", listpkm.get(0).get("pk_invmandoc"));//���������
	    		genBVO[i].setAttributeValue("cinvbasid", listpkm.get(0).get("pk_invbasdoc"));//�����������
//	    		genBVO[i].setAttributeValue("dvalidate", Toolkits.getUFDate(salebvo[i].getDvdate()));//ʧЧ����
//	    		genBVO[i].setAttributeValue("scrq", Toolkits.getUFDate(saleHVO.getDveridate().substring(0, 10)));//��������
	    		
	    		if("��Ʒ����".equals(ywlb)||"��Ʒ����".equals(ywlb)){//��Ʒ��������Ʒ������û�и�������
	    			genBVO[i].setAttributeValue("noutnum", Toolkits.getUFDouble(hm.get("bffhcl_jz")));//ʵ������
	    			genBVO[i].setAttributeValue("nshouldoutnum", Toolkits.getUFDouble(hm.get("bfpcdmx_sfsl")));//Ӧ������
	    		}

//	    		String bfpcd_tdls = Toolkits.getString(hm.get("bfpcd_tdls"));//���۶�������id
				String bfpcd_tdfl = Toolkits.getString(hm.get("bfpcd_tdfl"));//���۶����ӱ�id
				String saleordersql = " select b.* from so_sale h inner join so_saleorder_b b on h.csaleid=b.csaleid where nvl(h.dr,0)=0 and nvl(b.dr,0)=0 and b.corder_bid='"+bfpcd_tdfl+"'  ";
				SaleorderBVO salebvo = (SaleorderBVO) queryBySql(saleordersql, null, new BeanProcessor(SaleorderBVO.class));
				if(salebvo == null){
					throw new BusinessException("���Ҳ�����Ӧ�����۶���������");
				}
				genBVO[i].setCsourcebillhid(salebvo.getCsaleid());//��Դ���ݱ�ͷ���к�
				genBVO[i].setAttributeValue("csourcebillbid", bfpcd_tdfl); //��Դ���ݱ������к�
				genBVO[i].setAttributeValue("csourcetype", "30"); //��Դ��������
				genBVO[i].setCfirstbillbid(bfpcd_tdfl);
				genBVO[i].setCfirstbillhid(salebvo.getCsaleid());
				genBVO[i].setCfirsttype("30");
	    		
				//����������
				String freesql = " select vfree1,vfree2,vfree3,vfree4,vfree5 from so_saleexecute where csale_bid='"+bfpcd_tdfl+"' and nvl(dr,0)=0 ";
				Map freemap =  (Map) queryBySql(freesql, null, new MapProcessor());
				if(freemap != null && freemap.size() != 0){
					FreeVO freevo = new FreeVO();
					freevo.setVfree1((String) freemap.get("vfree1"));
					freevo.setVfreeid1(listpkm.get(0).get("free1"));
					
					freevo.setVfree2((String) freemap.get("vfree2"));
					freevo.setVfreeid2(listpkm.get(0).get("free2"));
					
					freevo.setVfree3((String) freemap.get("vfree3"));
					freevo.setVfreeid3(listpkm.get(0).get("free3"));
					
					freevo.setVfree4((String) freemap.get("vfree4"));
					freevo.setVfreeid4(listpkm.get(0).get("free4"));
					
					freevo.setVfree5((String) freemap.get("vfree5"));
					freevo.setVfreeid5(listpkm.get(0).get("free5"));
					genBVO[i].setFreeItemVO(freevo);
					genBVO[i].setIsFreeItemMgt(1);//�������������
					genBVO[i].setIsAstUOMmgt(1);//����������������
				}
				String wholemanaflag = listpkm.get(0).get("wholemanaflag");
				if("Y".equals(wholemanaflag)){//���κ�
					genBVO[i].setVbatchcode(salebvo.getCbatchid());
					genBVO[i].setIsLotMgt(1);
				}
	    		//����û�е��ۺͽ��
	    		genBVO[i].setAttributeValue("nprice", Toolkits.getUFDouble(hm.get("bffhcl_dj")));//����
	    		genBVO[i].setAttributeValue("nmny", Toolkits.getUFDouble(hm.get("bffhcl_je")));//���
	    		genBVO[i].setAttributeValue("nplannedprice", Toolkits.getUFDouble(hm.get("bffhcl_dj")));//����
	    		genBVO[i].setAttributeValue("nplannedmny", Toolkits.getUFDouble(hm.get("bffhcl_je")));//���
	    		
	    		ArrayList<HashMap<String, String>> listhsljldw = new ArrayList<HashMap<String,String>>();
	    		listhsljldw = getHslJldw(listpkm.get(0).get("pk_invbasdoc"));
	    		if(listhsljldw.size()>0){
	    			genBVO[i].setAttributeValue("castunitid", listhsljldw.get(0).get("pk_measdoc1"));//��������λ
	    			genBVO[i].setAttributeValue("hsl", listhsljldw.get(0).get("mainmeasrate"));//������
	    		}    		
			}
		}else if("��Ʒ��������".equals(ywlb)||"��Ʒ�໧����".equals(ywlb)){//��Ʒ������������Ʒ�໧�������и�������
			String bffhcl_djbh = Toolkits.getString(hm.get("bffhcl_djbh"));//�����ǼǱ��� �����ֶ�1
			String bffhcl_c6 = Toolkits.getString(hm.get("bffhcl_c6"));//�����ǼǱ��� �����ֶ�2
			String sql = " select * from BFPCDMX where BFPCDMX_C3='"+bffhcl_djbh+"' and BFPCDMX_C4='"+bffhcl_c6+"'";//
			ArrayList al = (ArrayList) session.executeQuery(sql,new MapListProcessor());
			if(al == null || al.size() == 0){
				throw new BusinessException("��Ʒ�⹺����,�����Զ���'�����ǼǱ���'��û�ҵ��ӱ��¼");
			}
			
			genBVO = new GeneralBillItemVO[al.size()];
			for (int i = 0; i < al.size(); i++) {
				Map smp = (Map) al.get(i);
				String invcode = Toolkits.getString(smp.get("bfpcdmx_wlbh"));
	    		genBVO[i]  = new GeneralBillItemVO();
	    		genBVO[i].setAttributeValue("dr", 0);
	    		genBVO[i].setAttributeValue("pk_corp", pk_corp);
	    		genBVO[i].setNegallowed(UFBoolean.TRUE);//�������
	    		genBVO[i].setDbizdate( genHVO.getDbilldate());   		
	    		ArrayList<HashMap<String, String>> listpkm = getPk_invmb(invcode, pk_corp);
	    		if(listpkm.size()==0){
	    			throw new BusinessException("�������"+invcode+"��NC��û��ά����");
	    		}
	    		genBVO[i].setAttributeValue("crowno", 10*(i+1));//�к�
	    		genBVO[i].setAttributeValue("cinventoryid", listpkm.get(0).get("pk_invmandoc"));//���������
	    		genBVO[i].setAttributeValue("cinvbasid", listpkm.get(0).get("pk_invbasdoc"));//�����������
//	    		genBVO[i].setAttributeValue("dvalidate", Toolkits.getUFDate(salebvo[i].getDvdate()));//ʧЧ����
//	    		genBVO[i].setAttributeValue("scrq", Toolkits.getUFDate(saleHVO.getDveridate().substring(0, 10)));//��������
	    		
	    		if("��Ʒ��������".equals(ywlb)||"��Ʒ�໧����".equals(ywlb)){//��Ʒ������������Ʒ�໧�������и�������
	    			genBVO[i].setAttributeValue("noutnum", Toolkits.getUFDouble(smp.get("bfpcdmx_jz")));//ʵ������
	    			genBVO[i].setNoutassistnum(Toolkits.getUFDouble(smp.get("bfpcdmx_sfjs")));//ʵ��������
	    			genBVO[i].setAttributeValue("nshouldoutnum", Toolkits.getUFDouble(smp.get("bfpcdmx_sfsl")));//Ӧ������
	    			genBVO[i].setNshouldoutassistnum(Toolkits.getUFDouble(smp.get("bfpcdmx_sfjs")));//Ӧ��������
	    		}else{
	    			throw new BusinessException("ֻ��ҵ�����Ϊ��Ʒ��������Ʒ��������Ʒ��������,��Ʒ�໧�����ſ��������۳��ⵥ");
	    		}
	    		
	    		String bfpcdmx_tdfl = Toolkits.getString(smp.get("bfpcdmx_tdfl"));//���۶����ӱ�id
				String saleordersql = " select b.* from so_sale h inner join so_saleorder_b b on h.csaleid=b.csaleid where nvl(h.dr,0)=0 and nvl(b.dr,0)=0 and b.corder_bid='"+bfpcdmx_tdfl+"'  ";
				SaleorderBVO salebvo = (SaleorderBVO) queryBySql(saleordersql, null, new BeanProcessor(SaleorderBVO.class));
				if(salebvo == null){
					throw new BusinessException("���Ҳ�����Ӧ�����۶���������");
				}
				genBVO[i].setCsourcebillhid(salebvo.getCsaleid());//��Դ���ݱ�ͷ���к�
				genBVO[i].setAttributeValue("csourcebillbid", bfpcdmx_tdfl); //��Դ���ݱ������к�
				genBVO[i].setAttributeValue("csourcetype", "30"); //��Դ��������
				genBVO[i].setCfirstbillbid(bfpcdmx_tdfl);
				genBVO[i].setCfirstbillhid(salebvo.getCsaleid());
				genBVO[i].setCfirsttype("30");
	    		
				//����������
				String freesql = " select vfree1,vfree2,vfree3,vfree4,vfree5 from so_saleexecute where csale_bid='"+bfpcdmx_tdfl+"' and nvl(dr,0)=0 ";
				Map freemap =  (Map) queryBySql(freesql, null, new MapProcessor());
				if(freemap != null && freemap.size() != 0){
					FreeVO freevo = new FreeVO();
					freevo.setVfree1((String) freemap.get("vfree1"));
					freevo.setVfreeid1(listpkm.get(0).get("free1"));
					
					freevo.setVfree2((String) freemap.get("vfree2"));
					freevo.setVfreeid2(listpkm.get(0).get("free2"));
					
					freevo.setVfree3((String) freemap.get("vfree3"));
					freevo.setVfreeid3(listpkm.get(0).get("free3"));
					
					freevo.setVfree4((String) freemap.get("vfree4"));
					freevo.setVfreeid4(listpkm.get(0).get("free4"));
					
					freevo.setVfree5((String) freemap.get("vfree5"));
					freevo.setVfreeid5(listpkm.get(0).get("free5"));
					genBVO[i].setFreeItemVO(freevo);
					genBVO[i].setIsFreeItemMgt(1);//�������������
					genBVO[i].setIsAstUOMmgt(1);//����������������
				}
				
				String wholemanaflag = listpkm.get(0).get("wholemanaflag");
				if("Y".equals(wholemanaflag)){//���κ�
					genBVO[i].setVbatchcode(salebvo.getCbatchid());
					genBVO[i].setIsLotMgt(1);
				}
	    		//����û�е��ۺͽ��
	    		genBVO[i].setAttributeValue("nprice", Toolkits.getUFDouble(smp.get("bfpcdmx_dj")));//����
	    		genBVO[i].setAttributeValue("nmny", Toolkits.getUFDouble(smp.get("bfpcdmx_je")));//���
	    		genBVO[i].setAttributeValue("nplannedprice", Toolkits.getUFDouble(smp.get("bfpcdmx_dj")));//����
	    		genBVO[i].setAttributeValue("nplannedmny", Toolkits.getUFDouble(smp.get("bfpcdmx_je")));//���
	    		
	    		
	    		ArrayList<HashMap<String, String>> listhsljldw = new ArrayList<HashMap<String,String>>();
	    		listhsljldw = getHslJldw(listpkm.get(0).get("pk_invbasdoc"));
	    		if(listhsljldw.size()>0){
	    			genBVO[i].setAttributeValue("castunitid", listhsljldw.get(0).get("pk_measdoc1"));//��������λ
	    			genBVO[i].setAttributeValue("hsl", listhsljldw.get(0).get("mainmeasrate"));//������
	    		}    		
			}
		}else{
			throw new BusinessException("ֻ��ҵ�����Ϊ��Ʒ��������Ʒ��������Ʒ��������,��Ʒ�໧�����ſ��������۳��ⵥ");
		}
		
		GeneralBillVO billvo = new GeneralBillVO();
		SMGeneralBillVO billvoreturn = new SMGeneralBillVO();
		billvo.setParentVO(genHVO);
		billvo.setChildrenVO(genBVO);
		billvo.setIsCheckCredit(true);
		billvo.setIsCheckPeriod(true);
		billvo.setIsCheckAtp(true);
		billvo.setGetPlanPriceAtBs(false);
		billvo.setIsRwtPuUserConfirmFlag(false);
		billvo.setStatus(nc.vo.pub.VOStatus.NEW);
		billvo.m_sActionCode = "WRITE";//����״̬
		try {
			//��ȡ����ֵ
			ArrayList al = (ArrayList) new PfUtilBO().processAction("WRITE", "4C", genHVO.getDbilldate().toString(), null, billvo, null);
			billvoreturn = (SMGeneralBillVO) al.get(2);
		} catch (Exception e) {
//			showErrorMessage(""+e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		return billvoreturn.getPrimaryKey();
    }

}
