package nc.ui.jyglgt.billmanage;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import nc.ui.trade.bsdelegate.BDBusinessDelegator;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.pub.SuperVO;

/**
 * 功能说明：通用的多子表业务委拖类
 * @author GDT
 * @time 2012-11-05
 * @notice：Ctrl类里的getBillVoName方法返回VO名,必须包含包名,如：nc.ui.vo.XXVO
 * */
public class CommonBusinessDelegator extends BDBusinessDelegator {
	
	Map<String, String> tmap = new  HashMap<String, String>();
	private String hpkname = null;
	
	public CommonBusinessDelegator(BillManageUI bui){
		
		try {
			String[] vonames = bui.getUIControl().getBillVoName();
			SuperVO hvo = (SuperVO) Class.forName(vonames[1]).newInstance();
			hpkname  = hvo.getPKFieldName();
			for (int i = 2; i < vonames.length; i++) {
				SuperVO vo = (SuperVO) Class.forName(vonames[i]).newInstance();
				tmap.put(vo.getTableName(), vonames[i]);
			}
		} catch (Exception e) {
			if(e instanceof ClassNotFoundException){
				bui.showErrorMessage("Ctrl类里的getBillVoName方法返回VO名,必须包含包名.");
			}
		}
	}
	
	/**
	 * 取得不同表体页签数据
	 */
	public Hashtable loadChildDataAry(String[] tableCodes, String key)
	throws java.lang.Exception {
	Hashtable ht=new Hashtable();
	SuperVO[] vos=null;
	int len=tableCodes.length;
	if (len==0)
		return null;
	String whereSql=hpkname+"='"+key+"'";
	
	for(int i=0;i<len;i++){
		if(tmap.containsKey(tableCodes[i])){
			String voname = tmap.get(tableCodes[i]);
			Class cl = Class.forName(voname);
			vos=queryByCondition(cl, whereSql);
		}
		if (vos!=null&&vos.length>0)
		    ht.put(tableCodes[i],vos);
	}
	return ht;
  }
}
