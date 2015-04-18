package nc.ui.jyglgt.j40068c;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.jyglgt.pub.UpstreamDialog;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.bill.BillModel;
//import nc.vo.gt.gs.gs11.GeneralBillItemVO;
import nc.vo.ic.pub.bill.GeneralBillHeaderVO;
import nc.vo.ic.pub.bill.GeneralBillItemVO;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.trade.pub.HYBillVO;


/**
 * 销售出库到销售结算对话框：获取上游单据数据，加载到下游单据
 * @author 施鹏
 * @version v1.0
 * */
public class IssuleToBalanceDialog extends UpstreamDialog implements ActionListener,ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GeneralBillVO[] hybvos_cur=null;
	protected QueryDlgHelp m_qryDlgHelp;

	
	public GeneralBillVO[] getHybvos_cur() {
		return hybvos_cur;
	}

	public void setHybvos_cur(GeneralBillVO[] hybvos_cur) {
		this.hybvos_cur = hybvos_cur;
	}


	public HashMap<String,GeneralBillItemVO> selectedMap = new HashMap<String,GeneralBillItemVO>();
	public HashMap<String,HashMap<String,GeneralBillItemVO[]>> bmap = new HashMap<String,HashMap<String,GeneralBillItemVO[]>>();
	public HashMap<String,HashMap<String,GeneralBillItemVO[]>> focusMap = new HashMap<String,HashMap<String,GeneralBillItemVO[]>>();
	
	public IssuleToBalanceDialog(Container parent, String title) {		
		super(parent, title);	
	}

	public IssuleToBalanceDialog(){
		super();
		initialize();
	}

	public IssuleToBalanceDialog(String cztypename) {
		super(cztypename);
		initialize();
	}

	protected void overWriteInitI(){
		overWriteInit();
	}

	private void overWriteInit() {
		 title="销售出库选择窗口";
		 billtype="4C";
		 funCode="40080802";
		 busitype=null;
		 tablename_h=new GeneralBillHeaderVO().getTableName();
		 tablenameclass_h=new GeneralBillHeaderVO().getClass().getName();
		 hpk_key=new GeneralBillHeaderVO().getPKFieldName();
		 tablename1_b=new GeneralBillItemVO().getTableName();
		 tablenameclass1_b=new GeneralBillItemVO().getClass().getName();
		 bpk_key1=new GeneralBillItemVO().getPKFieldName();
		 tablename_bs = new String[]{tablename1_b};
		 tablenameclass_bs = new String[]{tablenameclass1_b};
		 bpk_keys = new String[]{bpk_key1};
	}
	
	/**
	 * 通过查询条件得到上游表头数据并显示
	 * */
	public void getUpData(){
	      Object[] ret;
		try {
			ret = getQryDlgHelp().queryData(false);
		      if(ret==null || ret[0]==null || !((UFBoolean)ret[0]).booleanValue())
			        return;
			      ArrayList alListData = (ArrayList)ret[1];
			      if(alListData!=null&&alListData.size()>0){
			    	  GeneralBillVO [] billvo=(GeneralBillVO [])alListData.toArray(new GeneralBillVO [alListData.size()]);
			    	  GeneralBillHeaderVO[] hvo=new GeneralBillHeaderVO[billvo.length];
			    	  for(int i=0;i<billvo.length;i++){
			    		  hvo[i]=billvo[i].getHeaderVO();
			    	  }
			  		getBillListPanel().getHeadBillModel().clearBodyData();
			  		getBillListPanel().getHeadBillModel().setBodyDataVO(hvo);
			  		getBillListPanel().getHeadBillModel().execLoadFormula();	
			      }
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	  public QueryDlgHelp getQryDlgHelp() {
		    if(m_qryDlgHelp==null)
		      m_qryDlgHelp = new QueryDlgHelp(this);
		    return m_qryDlgHelp;
		  }
	  
	@SuppressWarnings("unchecked")
	public SuperVO[] getHeadVOs(String whereSql){
		String sql=" select  "+tablename_h+".* from "+tablename_h+" "+tablename_h+" "+whereSql;
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
//		if("yj_mpbill_b".equals(tablename1_b)){
//			return " where "+hpk_key+"='"+hpk_key_id+"' and nvl(dr,0)=0 and nvl(flag,'N')='N'";
//		}else{
			return " where "+hpk_key+"='"+hpk_key_id+"' and nvl(dr,0)=0";
//		}
	}
	
	public void valueChanged(ListSelectionEvent arg0) {
		HashMap<String, GeneralBillItemVO[]> mapvos = new HashMap<String, GeneralBillItemVO[]>();
		hpk_key_id = (String)getBillListPanel().getHeadBillModel().getValueAt(getBillListPanel().getHeadTable().getSelectedRow(),hpk_key);		
		String strWhere=getBodySqlWhere();
		if(bmap.get(hpk_key_id)!=null){
			HashMap<String,GeneralBillItemVO[]> mapi = bmap.get(hpk_key_id);
			for (int i = 0; i < tablename_bs.length; i++) {
				if (mapi.get(tablename_bs[i])!=null) {
					GeneralBillItemVO[] itemVOs= mapi.get(tablename_bs[i]);
					//增补一行
					mapvos.put(tablename_bs[i], itemVOs);
					if(getBillListPanel().getHeadTable().getRowSelectionAllowed()){
						GeneralBillItemVO[] vos=(GeneralBillItemVO[])getBillListPanel().getBodyBillModel().getBodySelectedVOs(tablenameclass_bs[i]);
						HashMap<String, GeneralBillItemVO[]> fmap = new HashMap<String, GeneralBillItemVO[]>();
						if(vos!=null&&vos.length>0){
							fmap.put(tablename_bs[i],vos);
							focusMap.put(hpk_key_id, fmap);
						}else{
							fmap.put(tablename_bs[i], itemVOs);
							focusMap.put(hpk_key_id, fmap);
						}
					}
				}else{
					if(tablename1_b.equals(tablename_bs[i])||tablename2_b.equals(tablename_bs[i])){
						strWhere = " where "+hpk_key+"='"+hpk_key_id+"' and nvl(dr,0)=0 and nvl(flag,'N')='N'";
					}else{
						strWhere=" where "+hpk_key+"='"+hpk_key_id+"' and nvl(dr,0)=0";
					}
					GeneralBillItemVO[] itemVOs = getCurBodyVOs(tablename_bs[i],tablenameclass_bs[i],strWhere.toString(),hpk_key_id);
					
					if(getBillListPanel().getHeadTable().getRowSelectionAllowed()&&itemVOs!=null&&itemVOs.length>0){
						mapvos.put(tablename_bs[i], itemVOs);
						GeneralBillItemVO[] vos=(GeneralBillItemVO[])getBillListPanel().getBodyBillModel().getBodySelectedVOs(tablenameclass_bs[i]);
						HashMap<String, GeneralBillItemVO[]> fmap = new HashMap<String, GeneralBillItemVO[]>();
						if(vos!=null&&vos.length>0){
							fmap.put(tablename_bs[i],vos);
							focusMap.put(hpk_key_id, fmap);
						}else{
							fmap.put(tablename_bs[i], itemVOs);
							focusMap.put(hpk_key_id, fmap);
						}
					}
				}
			}
		}else{
			for (int i = 0; i < tablename_bs.length; i++) {
				GeneralBillItemVO[] itemVOs = getCurBodyVOs(tablename_bs[i],tablenameclass_bs[i],strWhere.toString(),hpk_key_id);
				
				if(getBillListPanel().getHeadTable().getRowSelectionAllowed()&&itemVOs!=null&&itemVOs.length>0){
					mapvos.put(tablename_bs[i], itemVOs);
					GeneralBillItemVO[] vos=(GeneralBillItemVO[])getBillListPanel().getBodyBillModel().getBodySelectedVOs(tablenameclass_bs[i]);
					HashMap<String, GeneralBillItemVO[]> fmap = new HashMap<String, GeneralBillItemVO[]>();
					if(vos!=null&&vos.length>0){
						fmap.put(tablename_bs[i],vos);
						focusMap.put(hpk_key_id, fmap);
					}else{
						fmap.put(tablename_bs[i], itemVOs);
						focusMap.put(hpk_key_id, fmap);
					}
				}
			}
	    }
		for (int i = 0; i < tablename_bs.length; i++) {
			GeneralBillItemVO[] itemVOs = mapvos.get(tablename_bs[i]);
			getBillListPanel().getBodyBillModel().clearBodyData();
			getBillListPanel().getBodyBillModel().setBodyDataVO(itemVOs);
			if(itemVOs!=null){
				GeneralBillItemVO[] focusVOs = (GeneralBillItemVO[])focusMap.get(hpk_key_id).get(tablename_bs[i]);
				if(focusVOs!=null && focusVOs.length>0){
					for(int j=0; j<focusVOs.length; j++){
//						if(getBillListPanel().getBillListData().getBodyBillModel(tablename_bs[i]).getValueAt(j, bpk_keys[i]).equals(focusVOs[j].getPrimaryKey())){
							getBillListPanel().getBodyBillModel().setRowState(j, BillModel.SELECTED);
//						}
					}
				}
			}
			getBillListPanel().getBodyBillModel().execLoadFormula();
		}
	}
	
	protected GeneralBillItemVO[] getCurBodyVOs(String tablename_b,String tablenameclass_b,String whereSql,String key){
		String sql=" select * from "+tablename_b+whereSql;
		GeneralBillItemVO[] vo=null;
		try {
			ArrayList<GeneralBillItemVO[]> list =(ArrayList<GeneralBillItemVO[]>) Proxy.getItf().getVOsfromSql(sql, tablenameclass_b);
		    if(list!=null&&list.size()>0){
				vo = (GeneralBillItemVO[])list.toArray(new GeneralBillItemVO[list.size()]);
				HashMap<String,GeneralBillItemVO[]> mapi = new HashMap<String,GeneralBillItemVO[]>();
				mapi.put(tablename_b,vo);
				bmap.put(key, mapi);
		    }
		    
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return vo;
	}
	

//	public void actionPerformed(ActionEvent evt) {
//		// TODO Auto-generated method stub
//		if(evt.getSource() == getUIButtonOK()){
//			GeneralBillItemVO[] bvo = (GeneralBillItemVO[])getBillListPanel().getBodyBillModel().getBodySelectedVOs(tablenameclass1_b);
//			if(bvo.length==0){
//				if(sz==0){
//					MessageDialog.showWarningDlg(this, "提示", "有且只能选择一条数据！");			
//				}	
//				sz++;
//				if(sz==2){sz=0;}
//				return;
//			}
//			for (int i = 0; i < bvo.length; i++) {
//				selectedMap.put(bvo[i].getCgeneralbid(), bvo[i]);
//			}
//			actionPerformed2(evt);
//		}else if(evt.getSource() == getUIButtonCancle()){
//			closeCancel();
//		}
//	}

	
	public int sz=0;
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == getUIButtonOK()){
			int count = 0;
			ArrayList<GeneralBillVO>list=new ArrayList<GeneralBillVO>();
			GeneralBillHeaderVO[] hvo= (GeneralBillHeaderVO[])getBillListPanel().getHeadBillModel().getBodySelectedVOs(tablenameclass_h);
			for (int i = 0; i < hvo.length; i++) {
				GeneralBillVO hybvo=new GeneralBillVO();
			    hybvo.setParentVO(hvo[i]);
			    for (int j = 0; j < tablename_bs.length; j++) {
			    	if(focusMap.get(hvo[i].getPrimaryKey())!=null)
				    hybvo.setChildrenVO(focusMap.get(hvo[i].getPrimaryKey()).get(tablename_bs[j]));
				}
			    list.add(hybvo);
				count++;
			}		 
			hybvos_cur=(GeneralBillVO[])list.toArray(new GeneralBillVO[count]);
			setHybvos_cur(hybvos_cur);
			if(count != 1)
			{
				if(sz==0){
				MessageDialog.showWarningDlg(this, "提示", "有且只能选择一条数据！");			
				}
				sz++;
				if(sz==2){sz=0;}
				return;
			}
			if(hybvos_cur[0].getHeaderVO().getFbillflag()!=3){
				MessageDialog.showWarningDlg(this, "提示", "请选择签字后的数据！");			
				return;
			}
//			//add by cm 获取选中表体行
//			MpbillbVO[] bvo = (MpbillbVO[])getBillListPanel().getBodyBillModel(tablename1_b).getBodySelectedVOs(tablenameclass1_b);
//			if(bvo.length==0){
//				if(sz==0){
//					MessageDialog.showWarningDlg(this, "提示", "有且只能选择一条数据！");			
//				}	
//				sz++;
//				if(sz==2){sz=0;}
//				return;
//			}
//			for (int i = 0; i < bvo.length; i++) {
//				selectedMap.put(bvo[i].getPk_packdoc(), bvo[i]);
//			}
			
			closeOK();
		}
		else if(evt.getSource() == getUIButtonCancle()){
			closeCancel();
		}
	}
	
	public String getFunctionNode(){
		return funCode;
	}
	
	public String getBillType(){
		return billtype;
	}
	
	public String getCorpID(){
		return ClientEnvironment.getInstance().getCorporation().getPk_corp();
	}
	
	public String getUserID(){
		return ClientEnvironment.getInstance().getUser().getPrimaryKey();
	}

	public String getLogDate(){
		return ClientEnvironment.getInstance().getDate().toString();
	}
	
	public int getInOutFlag(){
		return nc.vo.scm.constant.ic.InOutFlag.OUT;
	}
	
	
}
