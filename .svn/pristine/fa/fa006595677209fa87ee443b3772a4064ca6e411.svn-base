package nc.ui.jyglgt.pub;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.jyglgt.scm.pub.query.SCMQueryConditionDlg;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.pub.bill.BillModel;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.query.ConditionVO;
import nc.vo.trade.pub.HYBillVO;

/**
 * 上游单据通用对话框：获取上游单据数据，加载到下游单据：多子表表体只进行显示
 * @author 施鹏
 * @version v1.3
 * */
public class UpstreamDialog extends UIDialog implements ActionListener, ListSelectionListener,BillEditListener {

	private static final long serialVersionUID = 1L;
	UIPanel UIPanelButton = null;
	UIButton UIButtonOK = null;
	UIButton UIButtonCancle = null;
	UIPanel UIPanel = null;
	BillListPanel billListPanel = null;
	SCMQueryConditionDlg  querycondition=null;
	public ClientEnvironment ce = ClientEnvironment.getInstance();
	public String title="";
	public String billtype=null;
	public String busitype=null;
	public String operator=null;
	public String pk_corp=null;
	public String funCode=null;
	public String tablename_h=null;
	public String tablenameclass_h=null;
	public String tablename1_b=null;
	public String tablenameclass1_b=null;
	public String tablename2_b=null;
	public String tablenameclass2_b=null;
	public String tablename3_b=null;
	public String tablenameclass3_b=null;
	public String tablename4_b=null;
	public String tablenameclass4_b=null;
	public String tablename5_b=null;
	public String tablenameclass5_b=null;
	public String[] tablename_bs = null;
	public String[] tablenameclass_bs = null;
	public SuperVO[] headvos=null;
	public HYBillVO hybvo=null;
	public HYBillVO[] hybvos=null;
	//存放所有表体行
	public HashMap<String,HashMap<String,SuperVO[]>> bmap = new HashMap<String,HashMap<String,SuperVO[]>>();
	public String hpk_key=null;
	public String bpk_key1=null;
	public String bpk_key2=null;
	public String bpk_key3=null;
	public String bpk_key4=null;
	public String bpk_key5=null;
	public String[] bpk_keys = null;
	//得到改行前的表头id
	public String hpk_key_id=null;
	//存放选中的表体行
	public HashMap<String,HashMap<String,SuperVO[]>> focusMap = new HashMap<String,HashMap<String,SuperVO[]>>();
	
	public HashMap<String,SuperVO> selectedMap = new HashMap<String,SuperVO>();

	//用于参照多个上游单据时判断单据类型
	String cztype = "";
	
	public UpstreamDialog(Container parent, String title)
	{
		super(parent,title);
	}
	
	@SuppressWarnings("deprecation")
	public UpstreamDialog(){
		super();
		initialize();
	}
	
	@SuppressWarnings("deprecation")
	public UpstreamDialog(String cztypename) {
		super();
		setcztype(cztypename);
		initialize();
	}

	private void setcztype(String cztypename) {
		cztype = cztypename;
	}

	public String getcztype(){
		return cztype;
	}

	protected void initialize() {
		overWriteInitI();
		operator=ce.getUser().getPrimaryKey();
		pk_corp=ce.getCorporation().getPk_corp();
        this.setSize(new Dimension(1000, 510));
        this.setContentPane(getUIPanel());
        this.setTitle(title);     
        getUIButtonOK().addActionListener(this);
        getUIButtonCancle().addActionListener(this);
        getBillListPanel().getHeadTable().getSelectionModel().addListSelectionListener(this);
        getBillListPanel().addBodyEditListener(this);

	}
  
	
	
	protected void overWriteInitI(){
		 title=null;
		 billtype=null;
		 funCode=null;
		 busitype=null;
	}
	
	protected UIPanel getUIPanel() {
		if (UIPanel == null) {
			UIPanel = new UIPanel();
			UIPanel.setLayout(new BorderLayout());
			UIPanel.add(getBillListPanel(), BorderLayout.CENTER);
			UIPanel.add(getUIPanelButton(), BorderLayout.SOUTH);
		}
		return UIPanel;
	}
	
	
	private UIPanel getUIPanelButton() {
		if (UIPanelButton == null) {
			UIPanelButton = new UIPanel();
			UIPanelButton.setLayout(null);
			UIPanelButton.setPreferredSize(new Dimension(10, 40));
			UIPanelButton.add(getUIButtonOK(), null);
			UIPanelButton.add(getUIButtonCancle(), null);
		}
		return UIPanelButton;
	}
	
	protected UIButton getUIButtonOK() {
		if (UIButtonOK == null) {
			UIButtonOK = new UIButton();
			UIButtonOK.setName("UIButtonOK");
			UIButtonOK.setText("确定");
			UIButtonOK.setBounds(new Rectangle(340, 4, 75, 20));
		}
		return UIButtonOK;
	}
	
	protected UIButton getUIButtonCancle() {
		if (UIButtonCancle == null) {
			UIButtonCancle = new UIButton();
			UIButtonCancle.setName("UIButtonCancle");
			UIButtonCancle.setText("取消");
			UIButtonCancle.setBounds(new Rectangle(492, 4, 75, 20));
		}
		return UIButtonCancle;
	}
	
	/**
	 * 加载单据模板
	 * */
	public BillListPanel getBillListPanel() {
		if (billListPanel == null) {
			billListPanel = new BillListPanel();
			billListPanel.loadTemplet(billtype, busitype, operator, pk_corp);
			billListPanel.setEnabled(false);
			billListPanel.setMultiSelect(true);
			
		}
		return billListPanel;
	}
	
	/**
	 * 加载查询模板
	 * */
	public SCMQueryConditionDlg  getQueryConditionClient() {
		if(querycondition == null){
			querycondition = new SCMQueryConditionDlg (this);
		    querycondition.hideNormal();
		    querycondition.setTempletID(pk_corp, funCode, operator, busitype);
		    querycondition.setIsWarningWithNoInput(false);		    
		}
		
		return querycondition;
	}
	
	/**
	 * 通过查询条件得到上游表头数据并显示
	 * */
	public void getUpData(){
		ConditionVO[] convos = getQueryConditionClient().getConditionVO();
		StringBuffer strWhere = new StringBuffer();
		strWhere.append(" where (1=1) and nvl(dr,0)=0 and pk_corp='"+pk_corp+"'");
        String querysql = getQueryConditionClient().getWhereSQL(convos);
        if(querysql!=null&&querysql.length()>0){strWhere.append(" and " + querysql);}     
        if(!Toolkits.isEmpty(getHeadCondition())){strWhere.append(" and " + getHeadCondition());}   
		getBillListPanel().getHeadBillModel().clearBodyData();
		for (int i = 0; i < tablename_bs.length; i++) {
			getBillListPanel().getBodyBillModel(tablename_bs[i]).clearBodyData();
		}
		getBillListPanel().getHeadBillModel().setBodyDataVO(getHeadVOs(strWhere.toString()));
		getBillListPanel().getHeadBillModel().execLoadFormula();		
	}
	
	/**
	 * 获取表头查询条件
	 * */
	public String getHeadCondition(){
		return "";
	}
	
	/**
	 * 获取上游表头、表体数据并显示
	 * */
	public int sz=0;
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == getUIButtonOK()){
			int count = 0;
			ArrayList<HYBillVO>list=new ArrayList<HYBillVO>();
			SuperVO[] hvo= (SuperVO[])getBillListPanel().getHeadBillModel().getBodySelectedVOs(tablenameclass_h);
			for (int i = 0; i < hvo.length; i++) {
			    HYBillVO hybvo=new HYBillVO();
			    hybvo.setParentVO(hvo[i]);
//			    for (int j = 0; j < tablename_bs.length; j++) {
//			    	if(focusMap.get(hvo[i].getPrimaryKey())!=null)
//				    hybvo.setChildrenVO(focusMap.get(hvo[i].getPrimaryKey()).get(tablename_bs[j]));
//				}
			    list.add(hybvo);
				count++;
			}		 
			hybvos=(HYBillVO[])list.toArray(new HYBillVO[count]);
			setHybvos(hybvos);
			if(count != 1)
			{
				if(sz==0){
				MessageDialog.showWarningDlg(this, "提示", "有且只能选择一条数据！");			
				}
				sz++;
				if(sz==2){sz=0;}
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
	
	/**
	 * 获取表头VO数据
	 * @param String whereSql 查询条件
	 * */
	@SuppressWarnings("unchecked")
	protected SuperVO[] getHeadVOs(String whereSql){
		String sql=" select * from "+tablename_h+whereSql;
		SuperVO[] vo=null;
		try {
			ArrayList<SuperVO[]> list = Proxy.getItf().getVOsfromSql(sql, tablenameclass_h);
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
	 * 获取表体VO数据
	 * @param String whereSql 查询条件
	 * @param String key 
	 * */
	@SuppressWarnings("unchecked")
	protected SuperVO[] getBodyVOs(String tablename_b,String tablenameclass_b,String whereSql,String key){
		String sql=" select * from "+tablename_b+whereSql;
		SuperVO[] vo=null;
		try {
			ArrayList<SuperVO[]> list =(ArrayList<SuperVO[]>) Proxy.getItf().getVOsfromSql(sql, tablenameclass_b);
		    if(list!=null&&list.size()>0){
				vo = (SuperVO[])list.toArray(new SuperVO[list.size()]);
				HashMap<String,SuperVO[]> mapi = new HashMap<String,SuperVO[]>();
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
	
	public void valueChanged(ListSelectionEvent arg0) {
		HashMap<String, SuperVO[]> mapvos = new HashMap<String, SuperVO[]>();
		hpk_key_id = (String)getBillListPanel().getHeadBillModel().getValueAt(getBillListPanel().getHeadTable().getSelectedRow(),hpk_key);		
		String strWhere=getBodySqlWhere();
		if(bmap.get(hpk_key_id)!=null){
			HashMap<String,SuperVO[]> mapi = bmap.get(hpk_key_id);
			for (int i = 0; i < tablename_bs.length; i++) {
				if (mapi.get(tablename_bs[i])!=null) {
					SuperVO[] itemVOs= mapi.get(tablename_bs[i]);
					//增补一行
					mapvos.put(tablename_bs[i], itemVOs);
					if(getBillListPanel().getHeadTable().getRowSelectionAllowed()){
						SuperVO[] vos=(SuperVO[])getBillListPanel().getBodyBillModel(tablename_bs[i]).getBodySelectedVOs(tablenameclass_bs[i]);
						HashMap<String, SuperVO[]> fmap = new HashMap<String, SuperVO[]>();
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
					SuperVO[] itemVOs = getBodyVOs(tablename_bs[i],tablenameclass_bs[i],strWhere.toString(),hpk_key_id);
					
					if(getBillListPanel().getHeadTable().getRowSelectionAllowed()&&itemVOs!=null&&itemVOs.length>0){
						mapvos.put(tablename_bs[i], itemVOs);
						SuperVO[] vos=(SuperVO[])getBillListPanel().getBodyBillModel(tablename_bs[i]).getBodySelectedVOs(tablenameclass_bs[i]);
						HashMap<String, SuperVO[]> fmap = new HashMap<String, SuperVO[]>();
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
				SuperVO[] itemVOs = getBodyVOs(tablename_bs[i],tablenameclass_bs[i],strWhere.toString(),hpk_key_id);
				
				if(getBillListPanel().getHeadTable().getRowSelectionAllowed()&&itemVOs!=null&&itemVOs.length>0){
					mapvos.put(tablename_bs[i], itemVOs);
					SuperVO[] vos=(SuperVO[])getBillListPanel().getBodyBillModel(tablename_bs[i]).getBodySelectedVOs(tablenameclass_bs[i]);
					HashMap<String, SuperVO[]> fmap = new HashMap<String, SuperVO[]>();
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
			SuperVO[] itemVOs = mapvos.get(tablename_bs[i]);
			if(itemVOs!=null){
				getBillListPanel().getBodyBillModel(tablename_bs[i]).clearBodyData();
				getBillListPanel().getBodyBillModel(tablename_bs[i]).setBodyDataVO(itemVOs);
				SuperVO[] focusVOs = (SuperVO[])focusMap.get(hpk_key_id).get(tablename_bs[i]);
				if(focusVOs!=null && focusVOs.length>0){
					for(int j=0; j<focusVOs.length; j++){
//						if(getBillListPanel().getBillListData().getBodyBillModel(tablename_bs[i]).getValueAt(j, bpk_keys[i]).equals(focusVOs[j].getPrimaryKey())){
							getBillListPanel().getBodyBillModel(tablename_bs[i]).setRowState(j, BillModel.SELECTED);
//						}
					}
				}
			}
			getBillListPanel().getBodyBillModel(tablename_bs[i]).execLoadFormula();
		}
	}
	
	public String getBodySqlWhere(){
		return " where "+hpk_key+"='"+hpk_key_id+"' and nvl(dr,0)=0";
	}
	public HYBillVO getHybvo() {
		return hybvo;
	}

	public void setHybvo(HYBillVO hybvo) {
		this.hybvo = hybvo;
	}

	public HYBillVO[] getHybvos() {
		return hybvos;
	}

	public void setHybvos(HYBillVO[] hybvos) {
		this.hybvos = hybvos;
	}
	
	public void afterEdit(BillEditEvent arg0) {
		
	}

	public void bodyRowChange(BillEditEvent arg0) {
		hpk_key_id = (String)getBillListPanel().getHeadBillModel().getValueAt(getBillListPanel().getHeadTable().getSelectedRow(),hpk_key);		
	    if(hpk_key_id!=null){
	    	for (int i = 0; i < tablename_bs.length; i++) {
	    		HashMap<String, SuperVO[]> fmap = new HashMap<String, SuperVO[]>();
	    		SuperVO[] vo = (SuperVO[])getBillListPanel().getBodyBillModel(tablename_bs[i]).getBodySelectedVOs(tablenameclass_bs[i]);
	    		fmap.put(tablename_bs[i], vo);
			    focusMap.put(hpk_key_id, fmap);	
			}
	    }
	}


}
