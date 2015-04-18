package nc.ui.jyglgt.j40068d;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.event.ListSelectionListener;

import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.jyglgt.pub.UpstreamDialog;
import nc.ui.pub.beans.MessageDialog;
import nc.vo.gt.gs.gs11.BalanceInfoBVO;
import nc.vo.gt.gs.gs11.BalanceListBVO;
import nc.vo.gt.gs.gs11.SellPaymentJSVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.query.ConditionVO;
import nc.vo.trade.pub.HYBillVO;


/**
 * 结算到优惠结算对话框：获取上游单据数据，加载到下游单据
 * @author 施鹏
 * @version v1.0
 * */
public class JY06TOJY07Dialog extends UpstreamDialog implements ActionListener,ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HYBillVO[] hybvos=null;
	public HYBillVO[] getHybvos() {
		return hybvos;
	}

	public void setHybvos(HYBillVO[] hybvos) {
		this.hybvos = hybvos;
	}

	public JY06TOJY07Dialog(Container parent, String title) {		
		super(parent, title);	
	}

	public JY06TOJY07Dialog(){
		super();
		initialize();
	}

	public JY06TOJY07Dialog(String cztypename) {
		super(cztypename);
		initialize();
	}

	protected void overWriteInitI(){
		overWriteInit();
	}

	private void overWriteInit() {
		 title="销售结算选择窗口";
		 billtype="JY06";
		 funCode="40068C";
		 busitype=null;
		 tablename_h=new SellPaymentJSVO().getTableName();
		 tablenameclass_h=new SellPaymentJSVO().getClass().getName();
		 hpk_key=new SellPaymentJSVO().getPKFieldName();
		 tablename1_b=new BalanceInfoBVO().getTableName();
		 tablenameclass1_b=new BalanceInfoBVO().getClass().getName();
		 bpk_key1=new BalanceInfoBVO().getPKFieldName();
		 tablename2_b=new BalanceListBVO().getTableName();
		 tablenameclass2_b=new BalanceListBVO().getClass().getName();
		 bpk_key2=new BalanceListBVO().getPKFieldName();
		 tablename_bs = new String[]{tablename1_b,tablename2_b};
		 tablenameclass_bs = new String[]{tablenameclass1_b,tablenameclass2_b};
		 bpk_keys = new String[]{bpk_key1,bpk_key2};
	}
	
	/**
	 * 通过查询条件得到上游表头数据并显示
	 * */
	public void getUpData(){
		ConditionVO[] convos = getQueryConditionClient().getConditionVO();
		StringBuffer strWhere = new StringBuffer("");
		strWhere.append(" where nvl("+tablename_h+".dr,0)=0 and "+tablename_h+".billtype='"+IJyglgtBillType.JY06+"' and "+tablename_h+".vbillstatus='"+IJyglgtBillStatus.CHECKPASS+"'");
        String querysql = getQueryConditionClient().getWhereSQL(convos);
        if(querysql!=null&&querysql.length()>0){strWhere.append(" and " + querysql);}     
        if(!Toolkits.isEmpty(getHeadCondition())){strWhere.append(" and " + getHeadCondition());}   
		getBillListPanel().getHeadBillModel().clearBodyData();
		getBillListPanel().getHeadBillModel().setBodyDataVO(getHeadVOs(strWhere.toString()));
		getBillListPanel().getHeadBillModel().execLoadFormula();		
	}
	
	@SuppressWarnings("unchecked")
	public SuperVO[] getHeadVOs(String whereSql){
		String sql=" select distinct "+tablename_h+".* from "+tablename_h+" "+tablename_h+" "+whereSql;
		SuperVO[] vo=null;
		try {
			ArrayList<SuperVO[]> list =Proxy.getItf().getVOsfromSql(sql, tablenameclass_h);
		    if(list!=null&&list.size()>0){
		    	vo = (SuperVO[])list.toArray(new SuperVO[list.size()]);
		    }
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		headvos=vo;
		return vo;
	}
	
	/**
	 * 获取表头查询条件
	 * */
	public String getHeadCondition(){
		return " 1=1 order by "+tablename_h+".vbillcode desc";
	}
	
	public String getBodySqlWhere(){
			return " where "+hpk_key+"='"+hpk_key_id+"' and nvl(dr,0)=0";
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		if(evt.getSource() == getUIButtonOK()){
			BalanceInfoBVO[] bvo = (BalanceInfoBVO[])getBillListPanel().getBodyBillModel(tablename1_b).getBodySelectedVOs(tablenameclass1_b);
//			if(bvo.length==0){
//				MessageDialog.showWarningDlg(this, "提示", "请选择表体存货信息！");				
//				return;
//			}
			for (int i = 0; i < bvo.length; i++) {
				selectedMap.put(bvo[i].getPk_balanceinfo_b(), bvo[i]);
			}
			super.actionPerformed(evt);
		}else if(evt.getSource() == getUIButtonCancle()){
			closeCancel();
		}
	}

}
