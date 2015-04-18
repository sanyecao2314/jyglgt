
package nc.ui.jyglgt.billcard;

import nc.ui.pub.ButtonObject;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.button.IBillButton;
import nc.ui.trade.card.BillCardUI;
import nc.vo.jyglgt.button.ButtonFactory;
import nc.vo.jyglgt.button.IJyglgtButton;
import nc.vo.jyglgt.pub.StateVO;
import nc.vo.jyglgt.pub.Toolkits.StateMachine;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * ˵��:����������,��Ƭ�ͽ���Ӧ�̳д���
 * @author ���������� 2010-8-30 ����01:57:47
 */
public class AbstractClientUI extends BillCardUI {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractClientUI() {
        super();
        initilize();
    }
	private StateMachine m_stateMachine = null;
    public AbstractClientUI(String arg0, String arg1, String arg2, String arg3, String arg4) {
        super(arg0, arg1, arg2, arg3, arg4);
    }

    protected ICardController createController() {
        return new AbstractCtrl();
    }

    protected AbstractEventHandler createEventHandler() {
        return new AbstractEventHandler(this,this.getUIControl());
    }

    public String getRefBillType() {
        return null;
    }

    protected void initSelfData() {
    }
    
    @Override
	public boolean onClosing() {
		return super.onClosing();
	}
    
    public void setDefaultData() throws Exception {
        try {

            Class c = Class.forName(getUIControl().getBillVoName()[1]);
            SuperVO[] vos = getBusiDelegator().queryByCondition(c, getBodyWherePart());
            //��Ҫ�����
            getBufferData().clear();

            if (vos != null) {
                HYBillVO billVO = new HYBillVO();
                //�������ݵ�����
                billVO.setChildrenVO(vos);
                //�������ݵ�����
                if (getBufferData().isVOBufferEmpty()) {
                    getBufferData().addVOToBuffer(billVO);
                } else {
                    getBufferData().setCurrentVO(billVO);
                }

                //���õ�ǰ��
                getBufferData().setCurrentRow(0);
            } else {
                getBufferData().setCurrentRow(-1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    protected void initilize() {
//        getBillCardPanel().getBodyPanel().getRendererVO().setShowZeroLikeNull(false);
        try {
            setDefaultData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected String getBodyWherePart() {
        return "  pk_corp='"+_getCorp().getPk_corp()+"'";
    }
    
    protected String getStr(String key,int row){
    	return getBillCardPanel().getBodyValueAt(row, key)==null?null:getBillCardPanel().getBodyValueAt(row, key).toString();
    }
    
	 /**
     * ����: ���ܱ�����
     * @param e 
     * @param key  
     * @param model
     * @param flag��0:BODY/1:HEAD
     * @return UFDouble
     * @author ����������
     * 2010-8-30 ����12:26:02
     */
    protected UFDouble sumMny(BillEditEvent e,String key,String model,int flag){
    	UFDouble mny=new UFDouble(0);
    	int rows=getBillCardPanel().getRowCount();
    	for(int i=0;i<rows;i++){
        	UFDouble temp=getUFDouble(key,i,e,model,flag);
    		mny=mny.add(temp);
    	}
    	return mny;
    }
    
	 /**
     * ����: ���ܱ�������
     * @param e 
     * @param key  
     * @param model 
     * @param flag��0:BODY/1:HEAD
     * @return Integer
     * @author ����������
     * 2010-8-30 ����12:26:02
     */
    protected Integer sumDays(BillEditEvent e,String key,String model,int flag){
    	Integer mny=new Integer(0);
    	int rows=getBillCardPanel().getRowCount();
    	for(int i=0;i<rows;i++){
        	Integer temp=getInteger(key,i,e,model,flag);
    		mny=mny+temp;
    	}
    	return mny;
    }
    
	 /**
     * ����: ���ܱ�������
     * @param e 
     * @param key  
     * @param model
     * @param flag��0:BODY/1:HEAD
     * @return Integer
     * @author ����������
     * 2010-8-30 ����12:26:02
     */
    protected UFDouble sumNum(BillEditEvent e,String key,String model,int flag){
    	UFDouble num=new UFDouble(0);
    	int rows=getBillCardPanel().getBillModel(model).getRowCount();
    	for(int i=0;i<rows;i++){
        	UFDouble temp=getUFDouble(key,i,e,model,flag);
        	num=num.add(temp);
    	}
    	return num;
    }
    
    protected UFDouble getUFDouble(String key,int row,BillEditEvent e,String model,int flag){
    	if(flag==BODY){
    		return Toolkits.isEmpty(getBillCardPanel().getBillModel(model).getValueAt(row, key))?new UFDouble(0):new UFDouble(getBillCardPanel().getBillModel(model).getValueAt(row, key).toString());
    	}else if(flag==HEAD){
    		return Toolkits.isEmpty(getBillCardPanel().getHeadItem(key).getValueObject())?new UFDouble(0):new UFDouble(getBillCardPanel().getHeadItem(key).getValueObject().toString());
    	}
    	return new UFDouble(0);
    }
    
    protected Integer getInteger(String key,int row,BillEditEvent e,String model,int flag){   	
    	if(flag==BODY){
       		return Toolkits.isEmpty(getBillCardPanel().getBillModel(model).getValueAt(row, key))?new Integer(0):new Integer(getBillCardPanel().getBillModel(model).getValueAt(row, key).toString());
    	}else if(flag==HEAD){
    		return Toolkits.isEmpty(getBillCardPanel().getHeadItem(key).getValueObject())?new Integer(0):new Integer(getBillCardPanel().getHeadItem(key).getValueObject().toString());
    	}
    	return new Integer(0);
    }
    
    /**
     * ����: ��ʼ���Զ��尴Ŧ
     * @param id,code,name,newStatus
     * @author ���������� 2011-6-3 14:15:32
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
    public void initPrivateButton(){
        super.initPrivateButton();
        initButton(IJyglgtButton.BTN_IMPORT, "����", "����",
				new int[] { nc.ui.trade.base.IBillOperate.OP_ALL},null);
        initButton(IJyglgtButton.BTN_EXPORT, "����", "����",
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
        int iStatus =IBillStatus.FREE;
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
	        // ��ӡ 
	        setButtonState(IJyglgtButton.Print, false);
        }else{
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
        }
        resetButton(null);
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
    
    
}