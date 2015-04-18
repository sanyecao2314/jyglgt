package nc.ui.jyglgt.j40068c;

import java.util.Hashtable;
import nc.ui.trade.bsdelegate.BDBusinessDelegator;
import nc.vo.gt.gs.gs11.BalanceInfoBVO;
import nc.vo.gt.gs.gs11.BalanceListBVO;
import nc.vo.pub.SuperVO;

/**
 * ����˵��:���۽��������

 * */
public class BusinessDelegator extends BDBusinessDelegator {
	/**
	 * ȡ�ò�ͬ����ҳǩ����
	 */
	public Hashtable loadChildDataAry(String[] tableCodes, String key)
	throws java.lang.Exception {
	Hashtable ht=new Hashtable();
	SuperVO[] vos=null;
	int len=tableCodes.length;
	if (len==0)
		return null;
	String whereSql="pk_sellpaymentjs='"+key+"'";
	for(int i=0;i<len;i++){
		if ("gt_balanceinfo_b".equals(tableCodes[i]))
			vos=queryByCondition(BalanceInfoBVO.class, whereSql);
		else if ("gt_balancelist_b".equals(tableCodes[i]))
			vos=queryByCondition(BalanceListBVO.class, whereSql);
		if (vos!=null)
		    ht.put(tableCodes[i],vos);
	}
	return ht;
  }
}