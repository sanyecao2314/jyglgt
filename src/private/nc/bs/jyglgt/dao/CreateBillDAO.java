package nc.bs.jyglgt.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.bs.trade.business.HYPubBO;
import nc.bs.uap.sf.facility.SFServiceFacility;
import nc.itf.uap.IVOPersistence;
import nc.vo.jyglgt.pub.BillMakeVO;
import nc.vo.jyglgt.pub.GDTBillcodeRuleVO;
import nc.vo.jyglgt.pub.MyBillTempBVO;
import nc.vo.jyglgt.pub.MyBillTempTVO;
import nc.vo.jyglgt.pub.MyBillTempVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.billtype.BilltypeVO;
import nc.vo.sm.funcreg.FuncRegisterVO;

public class CreateBillDAO extends AbstractDAO {

	public void autoCreateBill(BilltypeVO btvo, GDTBillcodeRuleVO trvo, MyBillTempVO thvo,List<MyBillTempBVO> bvolist, List<MyBillTempTVO> tvolist,  FuncRegisterVO fvo, BillMakeVO hvo) throws BusinessException {
		//1.���빦��ע��
		SFServiceFacility.getFuncRegisterService().insert(fvo);
		//2.���뵥������(�Ϸ���,�д������Ĳ���)
		new HYPubBO().insert(btvo);
		//3.���뵥�ݺŹ���
		getIvop().insertVO(trvo);
		//4.�Ȳ��뵥��ģ������
		String hpk = getIvop().insertVO(thvo);
		
		//�������ӱ����������
		for (int i = 0; i < tvolist.size(); i++) {
			tvolist.get(i).setPk_billtemplet(hpk);
		}
		
		Map<String, List<MyBillTempBVO>> samecodemap = new HashMap<String, List<MyBillTempBVO>>();
		//�����ӱ�,��ͬ���������ֿ�,������������
		for (int i = 0; i < bvolist.size(); i++) {
			//���ӱ�����
			bvolist.get(i).setPk_billtemplet(hpk);
			String tcode = bvolist.get(i).getTable_code();
			if(!samecodemap.containsKey(tcode)){
				List<MyBillTempBVO> list = new ArrayList<MyBillTempBVO>();
				list.add(bvolist.get(i));
				samecodemap.put(tcode, list);
			}else{
				List<MyBillTempBVO> list = samecodemap.get(tcode);
				list.add(bvolist.get(i));
			}
		}
		Iterator<String> ite = samecodemap.keySet().iterator();
		//�����ֶ�
		while(ite.hasNext()){
			String tcode = ite.next();
			List<MyBillTempBVO> list = samecodemap.get(tcode);
			int index = 0;
			for (int i = 0; i < list.size(); i++) {
				MyBillTempBVO tbvo = list.get(i);
				//�ѱ�ע���������ʾ,�����óɴ��ı�
				if(tbvo.getItemkey().equals("vmemo")&&tbvo.getPos()==0){
					tbvo.setShoworder(list.size()-1);
					tbvo.setDatatype(9);
					continue;
				}
				tbvo.setShoworder(index);
				index++;
			}
		}
		//5.���뵥��ģ���ӱ�
		getIvop().insertVOArray(bvolist.toArray(new SuperVO[0]));
		
		//6.���뵥��ģ�����ӱ�
		getIvop().insertVOArray(tvolist.toArray(new SuperVO[0]));
	}
	
	private IVOPersistence ivop = null;
	
	private IVOPersistence getIvop(){
		if(ivop == null){
			ivop = NCLocator.getInstance().lookup(IVOPersistence.class);
		}
		return ivop;
	}
	
}
