package nc.ui.jyglgt.billmanage;

import javax.swing.JComponent;

import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardBeforeEditListener;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.billcodemanage.BillcodeRuleBO_Client;
import nc.ui.pub.linkoperate.ILinkQuery;
import nc.ui.pub.linkoperate.ILinkQueryData;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bill.BillTemplateWrapper;
import nc.ui.trade.bsdelegate.BusinessDelegator;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.multichild.MultiChildBillManageUI;
import nc.vo.jyglgt.button.ButtonFactory;
import nc.vo.jyglgt.button.IJyglgtButton;
import nc.vo.jyglgt.pub.StateVO;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;
import nc.vo.jyglgt.pub.Toolkits.StateMachine;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.bill.BillTabVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * @author GDT
 * @since ���ܣ�ͬ ������UI������
 * @category ���ڰ�ť��״̬���ƱȽ��鷳,��д��������ࡣ
 * רΪ���ӱ� UI��ʼ������ 
 * @version �汾��v1.0
 *
 */
public abstract class AbstractMultiChildClientUI extends MultiChildBillManageUI implements BillCardBeforeEditListener,ILinkQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected abstract AbstractManageController createController();

	@Override
	public void setBodySpecialData(CircularlyAccessibleValueObject[] arg0)
			throws Exception {
		

	}

	@Override
	protected void setHeadSpecialData(CircularlyAccessibleValueObject arg0,
			int arg1) throws Exception {
		

	}

	@Override
	protected void setTotalHeadSpecialData(
			CircularlyAccessibleValueObject[] arg0) throws Exception {
		

	}

	@Override
	protected void initSelfData() {
		getBillCardPanel().setBillBeforeEditListenerHeadTail(this);
        getBillCardWrapper().initHeadComboBox("vbillstatus", IJyglgtBillStatus.strStateRemark, true);
        getBillListWrapper().initHeadComboBox("vbillstatus", IJyglgtBillStatus.strStateRemark, true);
        // ��ʾ���ݿ��е�0.
//        getBillCardPanel().getBodyPanel().getRendererVO().setShowZeroLikeNull(false);
        setShowRender(true);
		updateBgtButtons();
//		getBillListPanel().getHeadTable().addMouseListener(new MouseAdapter() {
//			public void mousePressed(MouseEvent e) {
//				super.mousePressed(e);
//				int[] rows = getBillListPanel().getHeadTable()
//						.getSelectedRows();
//				BillItem[] all = getBillListPanel().getHeadBillModel()
//						.getBodyItems();
//				int rowcount = getBillListPanel().getHeadBillModel()
//						.getRowCount();
//				for (int i = 0; i < rowcount; i++) {
//					for (int k = 0; k < all.length; k++) {
//						getBillListPanel().getHeadBillModel()
//								.getRowAttribute(i).clearCellBackColor();
//					}
//				}
//				for (int k = 0; k < all.length; k++) {
//					for (int i = 0; i < rows.length; i++) {
//						getBillListPanel().getHeadBillModel().setBackground(
//								Color.GREEN, rows[i], k);
//					}
//				}
//			}
//		});
	}
	
	/**
     * ��ʾ���ݿ��е�0�븺����ǧ��λ
     */
    protected void setShowRender(boolean flag){
    	BillTabVO[] btabvos = getBillCardPanel().getBillData().getAllTabVos();
    	for (int j = 0; j < btabvos.length; j++) {
    		String tcode = btabvos[j].getTabcode();
    		BillModel bm = getBillCardPanel().getBillModel(tcode);
    		if(!Toolkits.isEmpty(bm)){
    			BillItem[] items = getBillCardPanel().getBillModel(tcode).getBodyItems();
    			if ((items != null) && (items.length > 0))
    				for (int i = 0; i < items.length; i++) {
    					BillItem item = items[i];
    					if ((item.getDataType() == BillItem.INTEGER) || (item.getDataType() == BillItem.DECIMAL))
    						if (item.isShow() && item.getNumberFormat()!=null) {
    							item.getNumberFormat().setShowRed(flag);
    							item.getNumberFormat().setShowThMark(flag);
    							item.getNumberFormat().setShowZeroLikeNull(flag==true?false:true);
    						}
    				}
    		}
		}
    }

	@Override
	public void setDefaultData() throws Exception {
		String pk_corp = _getCorp().getPrimaryKey();
        BillItem oper = getBillCardPanel().getTailItem("pk_operator");
        if (oper != null){
            oper.setValue(_getOperator());
        }
        BillItem date = getBillCardPanel().getTailItem("dmakedate");
        if (date != null){
            date.setValue(_getDate());
        }
        BillItem busitype = getBillCardPanel().getHeadItem("pk_busitype");
        if (busitype != null){
            getBillCardPanel().setHeadItem("pk_busitype", this.getBusinessType());
        }
        BillItem vbillcode = getBillCardPanel().getHeadItem("vbillcode");
        if (vbillcode != null){
	        String billNo = BillcodeRuleBO_Client.getBillCode(this.getUIControl().getBillType(), _getCorp().getPk_corp(),
			        null, null);
            if(billNo!=null){
				getBillCardPanel().getHeadItem("vbillcode").setValue(billNo);
			}
        }
        getBillCardPanel().getHeadItem("pk_corp").setValue(pk_corp);
        BillItem billtype = getBillCardPanel().getHeadItem("billtype");
        if(billtype!=null){
        	getBillCardPanel().setHeadItem("billtype", this.getUIControl().getBillType());
        }
        BillItem billstatus=getBillCardPanel().getHeadItem("vbillstatus");
        if(billstatus!=null){
        	getBillCardPanel().getHeadItem("vbillstatus").setValue(
                Integer.toString(IBillStatus.FREE));	
        }
		updateBgtButtons();
	}

	public boolean beforeEdit(BillItemEvent e) {
		//�����ǰ�༭���ǲ��ս���ˢ��(GDT �޸�)
		JComponent jcomp = e.getItem().getComponent();
		if(jcomp instanceof UIRefPane ){
			UIRefPane refpane = (UIRefPane) jcomp;
			AbstractRefModel refm = refpane.getRefModel();
			if(refm!=null)  
				refm.reloadData1();
		}
		return true;
	}
	
	public boolean beforeEdit(BillEditEvent e) {
		//�����ǰ�༭���ǲ��ս���ˢ��(GDT �޸�)
		JComponent jcomp = getBillCardPanel().getBillModel(e.getTableCode()).getItemByKey(e.getKey()).getComponent();
		if(jcomp instanceof UIRefPane ){
			UIRefPane refpane = (UIRefPane) jcomp;
			AbstractRefModel refm = refpane.getRefModel();
			if(refm!=null)  
				refm.reloadData1();
		}
		return true;
	}
	
    public void initPrivateButton(){
        super.initPrivateButton();
        initButton(IJyglgtButton.BTN_IMPORT, "����", "����",
				new int[] { nc.ui.trade.base.IBillOperate.OP_ALL},null);
        initButton(IJyglgtButton.BTN_EXPORT, "����", "����",
				new int[] { nc.ui.trade.base.IBillOperate.OP_ALL},null);
        initButton(IJyglgtButton.BTN_PASS, "���", "���",
				new int[] { nc.ui.trade.base.IBillOperate.OP_NOADD_NOTEDIT},null);
        initButton(IJyglgtButton.BTN_UNPASS, "����", "����",
				new int[] { nc.ui.trade.base.IBillOperate.OP_NOADD_NOTEDIT},null);
        initButton(IJyglgtButton.BTN_DOMAIL, "���ʼ�", "���ʼ�",
				new int[] { nc.ui.trade.base.IBillOperate.OP_NOADD_NOTEDIT},null);
        initButton(IJyglgtButton.BTN_RESOURCE, "����", "����",
				new int[] { nc.ui.trade.base.IBillOperate.OP_ALL},null);
        initButton(IJyglgtButton.BTN_CALCULATE, "����", "����",
				new int[] { nc.ui.trade.base.IBillOperate.OP_ADD,nc.ui.trade.base.IBillOperate.OP_EDIT},null);
        initButton(IJyglgtButton.BTN_READCARD, "����", "����",
				new int[] { nc.ui.trade.base.IBillOperate.OP_ADD,nc.ui.trade.base.IBillOperate.OP_EDIT},null);
        initButton(IJyglgtButton.BTN_VADD, "����", "����",
				new int[] { nc.ui.trade.base.IBillOperate.OP_ALL},null);
    }
	
	public void updateBgtButtons() {
        AggregatedValueObject voBill = getBufferData().getCurrentVO();
        SuperVO voHead = null;
        Object billstatus=null;
        if(voBill!=null){
	        voHead = (SuperVO) voBill.getParentVO();
	       billstatus=voHead.getAttributeValue("vbillstatus");
        }
        int iStatus = 8;
        if(!Toolkits.isEmpty(billstatus)){
            iStatus = ((Integer) voHead.getAttributeValue("vbillstatus")).intValue();
        }
        // �������״̬����
        StateVO voState = new StateVO(iStatus, -1);
        if(new AbstractEventHandler(this,this.getUIControl()).isAdding()){
	        // �޸�
	        setButtonState(IBillButton.Edit, false);
	        // ɾ��
	        setButtonState(IBillButton.Delete, false); 
        }else{
        	 // ���
	        setButtonState(IJyglgtButton.BTN_PASS, getStateMachine().isVaidAction(voState, IJyglgtButton.BTN_PASS));
	        // ����
	        setButtonState(IJyglgtButton.BTN_UNPASS, getStateMachine().isVaidAction(voState, IJyglgtButton.BTN_UNPASS));
	        // ���ʼ�
	        setButtonState(IJyglgtButton.BTN_DOMAIL, getStateMachine().isVaidAction(voState, IJyglgtButton.BTN_DOMAIL));
	        // �޸�
	        setButtonState(IBillButton.Edit, getStateMachine().isVaidAction(voState, IBillButton.Edit));
	        // ɾ��
	        setButtonState(IBillButton.Delete, getStateMachine().isVaidAction(voState, IBillButton.Delete)); 
	        // ����
	        setButtonState(IBillButton.Save, getStateMachine().isVaidAction(voState, IBillButton.Save));
            // ��ӡ 
	        setButtonState(IJyglgtButton.Print, getStateMachine().isVaidAction(voState, IJyglgtButton.Print));
	        //���ư�ť
	        setButtonState(IJyglgtButton.Copy, getStateMachine().isVaidAction(voState, IJyglgtButton.Copy));
	        //����
	        setButtonState(IJyglgtButton.BTN_IMPORT, getStateMachine().isVaidAction(voState, IJyglgtButton.BTN_IMPORT));
	        //����
	        setButtonState(IJyglgtButton.BTN_EXPORT, getStateMachine().isVaidAction(voState, IJyglgtButton.BTN_EXPORT));	        
        }
        resetButton(null);
    }
	
	private StateMachine m_stateMachine = null;
	
	public StateMachine getStateMachine() {
        if (m_stateMachine == null)
            m_stateMachine = new StateMachine();
        return m_stateMachine;
    }
	
	protected void setButtonState(int iButton, boolean bEnable) {
        nc.ui.pub.ButtonObject bo = getButtonManager().getButton(iButton);
        if (bo == null)
            return;
        bo.setEnabled(bEnable);
    }
	
	public void resetButton(ButtonObject bo) {
        // ���ü������⶯���İ�ť״̬��
        if (bo != null) {
            int tag = Integer.parseInt(bo.getTag());
            switch (tag) {
            case IBillButton.Add:
                // ���Ա��棬�������Ϻ��ύ
                setButtonState(IBillButton.Save, true);
                setButtonState(IBillButton.Commit, false);
                setButtonState(IBillButton.Del, false);
                break;
            case IJyglgtButton.BTN_VADD:
                // ���Ա��棬�������Ϻ��ύ
                setButtonState(IBillButton.Save, true);
                setButtonState(IBillButton.Commit, false);
                setButtonState(IBillButton.Del, false);
                break;
            case IBillButton.Edit:
                setButtonState(IBillButton.Save, true);
                setButtonState(IBillButton.Commit, false);
                setButtonState(IBillButton.Del, false);
                break;
            case IBillButton.Return:
                if (!getBillCardPanel().isShowing()) {
                    return;
                }
            default:
                break;
            }
        }
        setButtons(getButtons());
        updateButtons();
    }

	@Override
	public void afterUpdate() {
		super.afterUpdate();
		updateBgtButtons();
	}
	
	/**
     * ����: ��ʼ���Զ��尴Ŧ
     * @param id,code,name,newStatus
     */
    public  void initButton(int id,String code,String name,int[]newStatus,int[]childButton){
        ButtonVO btnvo = ButtonFactory.createButtonVO(id, code, name);
        if(childButton!=null){
        	btnvo.setChildAry(childButton);
        }
        btnvo.setOperateStatus(newStatus);
        addPrivateButton(btnvo);
        super.initPrivateButton();
    }
    
    protected abstract BusinessDelegator createBusinessDelegator();
    
    public void afterEdit(BillEditEvent e) {
        getBillCardPanel().execHeadTailEditFormulas(getBillCardPanel().getHeadItem(e.getKey()));
        getBillCardPanel().execHeadTailLoadFormulas(getBillCardPanel().getHeadItem(e.getKey()));
    }
    
    /**
	 * ִ�б������������� �Ĺ�ʽ
	 * @author GDT
	 */ 
	public void execAllBodyEditFormulas(String modelName,int row) {
		BillItem[] allbodyitems = getModelByName(modelName).getBodyItems();
		for(int j=0;j<allbodyitems.length;j++){
			String[] formulas = allbodyitems[j].getEditFormulas();
			if(formulas == null){
				continue;
			}
			getBillCardPanel().getBillModel(modelName).execFormula(row,formulas);
		}
	}
	
	/**
	 * ȡ��������
	 * @param modelName
	 * @param row
	 * @param key
	 * @return
	 */
	public Object getbodyValueByModel(String modelName, int row, String key){
		return getModelByName(modelName).getValueAt(row, key);
	}
	
	/**
	 * ���ñ�������
	 * @param modelName
	 * @param row
	 * @param key
	 * @return 
	 */
	public void setbodyValueByModel(String modelName, Object value, int row, String key){
		getModelByName(modelName).setValueAt(value, row, key);
	}
	
	/**
	 * @param modelName
	 * @return
	 */
	public BillModel getModelByName(String modelName){
		return getBillCardPanel().getBillModel(modelName);
	}
	
	/**
	 * ��ȡ��ͷֵ
	 * @param java.lang.String key
	 * @author shipeng
	 * @serialData 2010-10-27
	 * @return Object
	 * */
	protected Object getHeadValueObject(String key){
		return getBillCardPanel().getHeadItem(key).getValueObject();
	}
	
	/**
	 * ���ͷ��ֵ
	 * @param java.lang.String key
	 * @param Object           value
	 * @author shipeng
	 * @serialData 2010-10-27
	 * @return Object
	 * */
	protected void setHeadValue(String key,Object value){
		 getBillCardPanel().setHeadItem(key, value);
	}
	
	
	protected UFDate getUFDate(Object obj){
		  if(!Toolkits.isEmpty(obj)){
			  try{
				  return new UFDate(obj.toString().trim());
			  }catch(Exception e){
				  return null;
			  }
		  }else{
			  return null;
		  }
	  }
	   
	  protected UFDouble getUFDouble(Object obj){
		  if(!Toolkits.isEmpty(obj)){
			  try{
				  return new UFDouble(obj.toString().trim());
			  }catch(Exception e){
				  return new UFDouble(0);
			  }
		  }else{
			  return new UFDouble(0);
		  }
	  }
	  
	  protected Integer getInteger(Object obj){
		  if(!Toolkits.isEmpty(obj)){
			  try{
				  return Integer.parseInt(obj.toString().trim());
			  }catch(Exception e){
				  return new Integer(0);
			  }
		  }else{
			  return new Integer(0);
		  }
	  }
	  
	  /**
		 * ���ӱ� ��ձ���
		 * @author ʩ��
	 * @param tablecode1 
		 * @serialData 2010-11-2
		 * @return boolean 
		 * */
		protected void delBodyValueAndLine(String tablecode){
			int row=getModelByName(tablecode).getRowCount();
			int[] rows = new int[row];
			for(int j=0;j<row;j++){
				rows[j]=j;
			}		
			getModelByName(tablecode).delLine(rows);
		}
		
		/**
		 * ��ձ�ͷ
		 * @author ʩ��
		 * @serialData 2010-11-2
		 * @return boolean 
		 * */
		protected void delHeadValue(){
			 BillItem[]item=getBillCardPanel().getHeadItems();
			 for(int i=0;i<item.length;i++){
				 setHeadValue(item[i].getKey(), null);
			 }
			   try {
					setDefaultData();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

		}
	    
	    /**
	     * ����: ���ܱ��嵽��ͷ(��������嵥��ʹ��)
	     * @param bodyedit Ҫ���ܵı�����
	     * @param headedit ���ܵı�ͷ��
	     * @return void
	     */
	    protected void totalBodyToHead(String bodytablecode,String bodyedit,String headedit){
	        
	        int rowsNum=getBillCardPanel().getBillModel(bodytablecode).getRowCount();
	        UFDouble value = new UFDouble();
	        for(int i=0;i<rowsNum;i++){
	            if(getBillCardPanel().getBillModel(bodytablecode).getValueAt(i, bodyedit)!=null){
	            	value=value.add(new UFDouble(getBillCardPanel().getBillModel(bodytablecode).getValueAt(i, bodyedit).toString()));    
	            }
	       }
	       BillItem bi = getBillCardPanel().getHeadItem(headedit);
	       if(bi!=null){
	           getBillCardPanel().getHeadItem(headedit).setValue(value);
	       }
	    }
	    
	    /**
		  * SFClientUtil.openLinkedQueryDialog������
		 */
		public void doQueryAction(ILinkQueryData querydata) {
				String pk = querydata.getBillID();
				setCurrentPanel(BillTemplateWrapper.CARDPANEL);
				if(Toolkits.isEmpty(pk)){
					return;
				}
				//��������
				try {
					getBufferData().addVOToBuffer(loadHeadData(pk));
					setListHeadData(getBufferData().getAllHeadVOsFromBuffer());
					getBufferData().setCurrentRow(getBufferData().getCurrentRow());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		} 
		
		@Override
		public Object getUserObject() {
			if(this.getUIControl().getBusinessActionType() == nc.ui.trade.businessaction.IBusinessActionType.BD){
				//�߻�������,��Ҫ����һ��ʵ��nc.vo.trade.pub.IRetCurrentDataAfterSave�ӿ�(ֻ��һ�������ӿ�),����ֻ�᷵���޸�ҳǩ������,����ҳǩ���ݻᱻ��� add by gdt
				return new nc.vo.jyglgt.pub.MultiBDUserObject();
			}
			return super.getUserObject();
		}
}