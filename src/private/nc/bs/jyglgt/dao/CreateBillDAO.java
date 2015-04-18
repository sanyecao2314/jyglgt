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
		//1.插入功能注册
		SFServiceFacility.getFuncRegisterService().insert(fvo);
		//2.插入单据类型(老方法,有带主键的插入)
		new HYPubBO().insert(btvo);
		//3.插入单据号规则
		getIvop().insertVO(trvo);
		//4.先插入单据模板主表
		String hpk = getIvop().insertVO(thvo);
		
		//设置子子表的主表主键
		for (int i = 0; i < tvolist.size(); i++) {
			tvolist.get(i).setPk_billtemplet(hpk);
		}
		
		Map<String, List<MyBillTempBVO>> samecodemap = new HashMap<String, List<MyBillTempBVO>>();
		//区分子表,相同表编码的区分开,供后面排序用
		for (int i = 0; i < bvolist.size(); i++) {
			//设子表主键
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
		//排序字段
		while(ite.hasNext()){
			String tcode = ite.next();
			List<MyBillTempBVO> list = samecodemap.get(tcode);
			int index = 0;
			for (int i = 0; i < list.size(); i++) {
				MyBillTempBVO tbvo = list.get(i);
				//把备注放在最后显示,并设置成大文本
				if(tbvo.getItemkey().equals("vmemo")&&tbvo.getPos()==0){
					tbvo.setShoworder(list.size()-1);
					tbvo.setDatatype(9);
					continue;
				}
				tbvo.setShoworder(index);
				index++;
			}
		}
		//5.插入单据模板子表
		getIvop().insertVOArray(bvolist.toArray(new SuperVO[0]));
		
		//6.插入单据模板子子表
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
