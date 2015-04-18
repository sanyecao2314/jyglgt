package nc.ui.jyglgt.j400670;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.jyglgt.billmanage.AbstractEventHandler;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.pub.tools.BannerDialog;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;
import nc.vo.fa.pub.bill.FAPfUtil;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillType;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.lang.UFTime;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;

/**
 * ����: ���ۼ۸�����EH�� 
 */
public class ClientEventHandler extends AbstractEventHandler{

	String[] headKey=new String[]{"product","sdate","stime","edate","etime","memo"};
	String[] bodyKey=new String[]{"vinvcode","vlevel","free1_0","free1_1","free1_2",
			  "free2_0","free2_1","free2_2","free3_0","free3_1","free3_2","free4_0",
			  "free4_1","free4_2","free5","free6","free7","free8","free9","free10","vzkway","hsprice","memo"};
	public ClientEventHandler(BillManageUI arg0, IControllerBase arg1) {
		super(arg0, arg1);
	}
    //�ж��ĸ���ť������
	protected void onBoElse(int intBtn) throws Exception {
		super.onBoElse(intBtn); 
//		switch (intBtn) {
//		case IGTButton.BTN_ZJCH:
//			onBoStoreAdd(); 
//			return;
//		case IGTButton.BTN_XGJZDATE:
//			onBoUpdateDate(); 
//			return;
//		case IGTButton.BTN_AUTOOPEN:
////			onBoAutoOpen();
//			return;
//		}
	}
//	@Override
//	protected void onBoReturn() throws Exception {
//		super.onBoReturn();
//		super.onBoQuery();
//	}
	@Override
	protected void onBoCard() throws Exception {
		super.onBoCard();
		super.onBoRefresh();
		String product=Toolkits.getString(getHeadValue("product"));
		columnshow(product);
	}
	//���ư�ť����
	protected void onBoCopy() throws Exception {
		super.onBoCopy();
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("sdate").setValue(_getDate());
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("stime").setValue(getBillUI()._getServerTime().getTime());
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("edate").setValue(_getDate());
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("etime").setValue("23:59:59");
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("memo").setValue(null);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vbillcode").setValue(null);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("vbillstatus").setValue(IJyglgtBillStatus.FREE);
		getBillCardPanelWrapper().getBillCardPanel().getTailItem("pk_operator").setValue(_getOperator());
		getBillCardPanelWrapper().getBillCardPanel().getTailItem("dmakedate").setValue(_getDate());
		getBillCardPanelWrapper().getBillCardPanel().getTailItem("vapproveid").setValue(null);
		getBillCardPanelWrapper().getBillCardPanel().getTailItem("vapprovedate").setValue(null);
		getBillCardPanelWrapper().getBillCardPanel().getTailItem("vunapproveid").setValue(null);
		getBillCardPanelWrapper().getBillCardPanel().getTailItem("vunapprovedate").setValue(null);
		for(int i=0;i<getRowCount();i++){
			setBodyValue(null, i, "memo");
			setBodyValue(null, i, "pk_deliveryplan_b");
		}
		String product=Toolkits.getString(getHeadValue("product"));
		columnshow(product);
		
//		PriceChangeDlg dlg=new PriceChangeDlg();
//		dlg.show();
//		UFDouble value=new UFDouble(dlg.getReturnValue());
//		if(dlg.isOk){
//			for(int i=0;i<getRowCount();i++){
//				UFDouble hsprice=getBillCardPanelWrapper().getBillCardPanel().getBodyValueAt(i, "hsprice")==null?new UFDouble(0):new UFDouble(String.valueOf(getBillCardPanelWrapper().getBillCardPanel().getBodyValueAt(i, "hsprice")));
//				setBodyValue(hsprice.add(value), i, "hsprice");
//			}
//	    }
	}

	//ȡ��ʱ�Ա�ͷ�ͱ������ݵı༭����
	protected void onBoCancel() throws Exception {
	    setAllItemEditTrue(headKey,bodyKey);
		super.onBoCancel();
	    showLine();
	}
	/**
	 * ���Ӵ����ť
	 * @throws Exception 
     * @author ʩ��
     * 2011-11-12 
     */
	protected void onBoStoreAdd() throws Exception{
		int row = getRowCount();
		BillItem[] headitsb = getBillCardPanelWrapper().getBillCardPanel().getBodyItems();
		int colnum=getBillCardPanelWrapper().getBillCardPanel().getBodyItems().length;
		for(int i=0;i<row;i++){
			for(int j=0;j<colnum;j++){
				getBillCardPanelWrapper().getBillCardPanel().getBillModel().setBackground(Color.LIGHT_GRAY, i, j);
				getBillCardPanelWrapper().getBillCardPanel().getBillModel().setCellEditable(i, headitsb[j].getKey(), false);
			}
		}
		onBoEdit();
		super.onBoLineAdd();
		int rownum = getRowCount();
		Object obj=getHeadValue("product");
		if(!Toolkits.isEmpty(obj)){
			columnshow(obj.toString(),rownum-1);
		}
		setBodyValue(getOperName()+"��"+getBillUI()._getServerTime()+" ���Ӵ��", rownum-1, "memo");
		BillItem[] headits = getBillCardPanelWrapper().getBillCardPanel().getHeadItems();
		for(int i=0;i<headits.length;i++){
			headits[i].setEdit(false);
		}
	}
	/** �޸Ľ�ֹ����ʱ�䰴ť */
	private void onBoUpdateDate() throws Exception{
		hidLine();
		onBoEdit();
		setAllItemEditFalse();
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("edate").setEdit(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("edate").getComponent().setBackground(Color.PINK);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("etime").setEdit(true);
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("etime").getComponent().setBackground(Color.PINK);
	}
	/**
	 * ����˵�������ݿ�ʼ���ںͿ�ʼʱ�����Զ���˵�ǰ���ݣ�����������˹��ĵ��ݽ����رգ���ǰ����̬����״̬��Ϊ���̬. 
	 * ��ǰ���������ʱ�������ť��ʾ���õ�������ˣ�������Ч��
	 * @author ���챦
	 * @throws Exception 
	 * @time 2010-11-8
	 * */
	private void onBoAutoOpen() throws Exception {
		AggregatedValueObject avo = getBufferData().getCurrentVO();
		if (avo == null)
			return;
		int result = getBillUI().showYesNoMessage("�Զ�������?");
		if (result == UIDialog.ID_YES) {
			getBillUI().showWarningMessage("ϵͳ�ѿ����������Թرյ�ǰ���棬��������Ч��");
			
			Runnable run = new Runnable(){
				public void run(){
	 				BannerDialog banner = new BannerDialog(getParent());			
					getParent().setCursor(new Cursor(Cursor.WAIT_CURSOR));
					banner.start();
					try {
						if(autoCheck()){
			    	        getBufferData().updateView();
		    	        	getBillUI().showOkCancelMessage("�Զ������ɣ�");
						};
					} catch (Exception e) {
						e.printStackTrace();
						banner.end();
						getParent().setCursor(Cursor.getDefaultCursor());
						getBillUI().showErrorMessage("��̨����ҵ���쳣:"+e.getMessage());
					}finally{
						banner.end();
						getParent().setCursor(Cursor.getDefaultCursor());
					}	
					}
				};
				new Thread(run).start();
        }
			
	}
	/**
	 * ����˵������˰�ť���ƣ�������˹��ĵ��ݽ����رգ���ǰ����̬����״̬��Ϊ���̬. 
	 * ��ǰ���������ʱ�������ť��ʾ���õ�������ˣ�������Ч��
	 * @author ���챦
	 * @throws Exception 
	 * @time 2010-11-8
	 * */
	@SuppressWarnings("static-access")
//	public void onBoCheckPass() throws Exception {
//    	AggregatedValueObject avo=getBufferData().getCurrentVO();
//    	if(avo==null)return ;
//        int result=getBillUI().showYesNoMessage("ȷ���˲�����?");
//        if(result==UIDialog.ID_YES){
//	     // ��ǰ����̬����״̬��Ϊ���̬��
//			if (getInteger(this.getBillCardPanelWrapper().getBillCardPanel()
//					.getHeadItem("vbillstatus").getValueObject()) == (IBillStatus.FREE)) {
//				UFDate date=_getDate();
//				UFTime time=new UFTime(ClientEnvironment.getInstance().getServerTime().getTime());
//				// ��ǰ�������ͨ������
//				onSelfBoCheckPass((SuperVO)avo.getParentVO(),date,time);
//				// �ر�������˹��ĵ���
//				closeAllCust();
//				//������һ�ŵ��ݵĽ������ڵ��ڵ�ǰ���ݵĿ�ʼ����
//				updateLastDate((SuperVO)avo.getParentVO(),date,time);				
//			}
//			// ��ǰ���������ʱ�������ť��ʾ���õ�������ˣ�������Ч
//			else if (getInteger(this.getBillCardPanelWrapper()
//					.getBillCardPanel().getHeadItem("vbillstatus")
//					.getValueObject()) == (IBillStatus.CHECKPASS)) {
//				this.getBillUI().showErrorMessage("�õ�������ˣ�������Ч��");
//				return;
//			}
//			getBufferData().updateView(); 
//			onBoRefresh();
//        }
//	}
	// ��ǰ�������ͨ������
	public void onSelfBoCheckPass(SuperVO vo, UFDate date, UFTime time) throws Exception {
		// �����
        vo.setAttributeValue("vapproveid" , _getOperator());
		// �������
		vo.setAttributeValue("vapprovedate", _getDate());
		// ��ʼ����
		vo.setAttributeValue("sdate", date);
		// ��ʼʱ��
		vo.setAttributeValue("stime",time);
		// ����״̬
		vo.setAttributeValue("vbillstatus", IJyglgtBillStatus.CHECKPASS);
		Proxy.getInstance();
		Proxy.getItf().updateVO(vo);
        getBufferData().updateView(); 
	}
	//������һ�ŵ��ݵĽ������ڵ��ڵ�ǰ���ݵĿ�ʼ����
	@SuppressWarnings({ "unchecked"})
	private void updateLastDate(SuperVO vo, UFDate date, UFTime time) throws BusinessException{
       	String vbillcode =getHeadValue("vbillcode")==null?"":getHeadValue("vbillcode").toString();
       	String product =getHeadValue("product")==null?"":getHeadValue("product").toString();
    	String sql="select edate,etime,pk_pricepolicy from gt_pricepolicy where nvl(dr,0)=0 and product='"+product+"'  and pk_corp='"+_getCorp().getPk_corp()+"' ";
    	//���������˵�� ��ǰ�ǡ��޸ġ�����(�޸�ʱ,�����ж�Ҫ�ų�����������¼)
    	if(!vbillcode.equals("")){
    		sql += " and vbillcode <'" + vbillcode + "' ";
    	}
    	//����order by
    	sql += " order by vbillcode desc ";
		Proxy.getInstance();
		ArrayList list =Proxy.getItf().queryArrayBySql(sql);
		String lastPk="";
		if(list==null||list.size()==0){return;}else{
			HashMap<String, String> map=(HashMap<String,String>)list.get(0);
			lastPk=map.get("pk_pricepolicy");
			if(!Toolkits.isEmpty(lastPk)){
				StringBuffer sql2=new StringBuffer();
				sql2.append(" update gt_pricepolicy")
				.append(" set edate='" +date+"',")
				.append(" etime='" + time+"'")
				.append(" where pk_pricepolicy = '"+lastPk+"'" );
				try {
					Proxy.getInstance();
					Proxy.getItf().updateBYsql(sql2.toString());
			        getBufferData().updateView(); 
				} catch (BusinessException e) {
					e.printStackTrace();
					showErrorMessage("����쳣:" + e.getMessage());
				}
			}
		}
	}	
	/**
	 * ����˵�����ر�������˹��ĵ���
	 * @author ʩ��
	 * @time 2011-11-13
	 * */
	public void closeAllCust() {
		Object  pkPrimay = getHeadValue("pk_pricepolicy");
       	String product =getHeadValue("product")==null?"":getHeadValue("product").toString();
		if(!Toolkits.isEmpty(pkPrimay)){
			StringBuffer sql=new StringBuffer();
			sql.append(" update gt_pricepolicy")
			.append(" set vbillstatus='" + IJyglgtBillStatus.CLOSE+"'")
			.append(" where vbillstatus = '" + IBillStatus.CHECKPASS+ "'" )
			.append(" and pk_pricepolicy != '" + pkPrimay + "' and product='"+product+"' and nvl(dr,0)=0 and pk_corp='"+_getCorp().getPk_corp()+"'");
			try {
				Proxy.getInstance();
				Proxy.getItf().updateBYsql(sql.toString());
		        getBufferData().updateView(); 
			} catch (BusinessException e) {
				e.printStackTrace();
				showErrorMessage("����쳣:" + e.getMessage());
			}
		}
	}
	//���水ť�Ŀ���
	protected void onBoSave() throws Exception {
		int rows = getBillCardPanelWrapper().getBillCardPanel().getRowCount();
		if(upPKdown(rows)){return;}//���޺����޵ıȽ�
		if(isallPreRepeat(rows)){return;}//һ����Ʒ�µı�����Ϣ�����ظ�
		if(beginOverEnetime()){
        	showErrorMessage("��ʼʱ�䲻�ܸ��ڽ�ֹʱ�䣡");
        	return;
		}else if(!beginOverLastEnetime()){
        	showErrorMessage("��ʼʱ�������һ�ŵ��ݿ�ʼʱ�䣡");
        	return;
		}
	    setAllItemEditTrue(headKey,bodyKey);
	    for(int i=0;i<getRowCount();i++){
	    	String pk_invmandoc=getBodyValue(i, "pk_invmandoc")==null?"":getBodyValue(i, "pk_invmandoc").toString();
	    	String pk_level=getBodyValue(i, "pk_level")==null?"":getBodyValue(i, "pk_level").toString();
	    	String sql=" select pk_invmandoc from bd_invmandoc where pk_invmandoc='"+pk_invmandoc+"' " +
	    			"    and wholemanaflag='Y' and nvl(dr,0)=0 and pk_corp='"+_getCorp().getPk_corp()+"' ";
	    	ArrayList list =Proxy.getItf().queryArrayBySql(sql);
	    	if(list!=null&&list.size()>0){
	    		if(pk_level.equals("")){
	    			showErrorMessage("��"+(i+1)+"�У������ȼ�Ϊ�����");
	    			return;
	    		}
	    	}
	    }
			super.onBoSave();
	}

	String[] vbodyKey=new String[]{"vareal","vinvcode","vlevel"};
	String[] vbodyKey0=new String[]{"free1_0","free2_0","free3_0","free4_0"};
	String[] vbodyKey1=new String[]{"free1_1","free2_1","free3_1","free4_1"};
	String[] vbodyKey2=new String[]{"free1_2","free2_2","free3_2","free4_2"};
	/**
	 * ���޺����޵ıȽ�
	 * */
	private boolean upPKdown(int rows) {
		for(int i=0;i<rows;i++){
			for(int j=0;j<4;j++){
				UFDouble fp0=Toolkits.getUFDouble(getBodyValue(i, vbodyKey0[j]));
				UFDouble fp1=Toolkits.getUFDouble(getBodyValue(i, vbodyKey1[j]));
				String fp2=Toolkits.getString(getBodyValue(i,vbodyKey2[j]) );
				if(fp0.doubleValue()!=0||fp1.doubleValue()!=0){
					
					if(fp0.doubleValue()>fp1.doubleValue()){
						showErrorMessage("��"+(i+1)+"�У�����ֵ���ɴ�������ֵ��");
						return true;
					}
					
					if(fp2.equals("�����")){
						if(fp0.doubleValue()==fp1.doubleValue()){
							showErrorMessage("��"+(i+1)+"�У������ʱ����ֵ���ɵ�������ֵ��");
							return true;
						}
					}else if(fp2.equals("�Ұ���")){
						if(fp0.doubleValue()==fp1.doubleValue()){
							showErrorMessage("��"+(i+1)+"�У��Ұ���ʱ����ֵ���ɵ�������ֵ��");
							return true;
						}
					}else if(fp2.equals("ȫ������")){
						if(fp0.doubleValue()==fp1.doubleValue()){
							showErrorMessage("��"+(i+1)+"�У�ȫ������ʱ����ֵ���ɵ�������ֵ��");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	/**
	 * һ����Ʒ�µı�����Ϣ�����ظ�
	 * */
	private boolean isallPreRepeat(int rows) {
		Boolean flag=true;
		StringBuffer sb=new StringBuffer("");
		for(int i=0;i<rows-1;i++){
			for(int j=i+1;j<rows;j++){
				StringBuffer sql=new StringBuffer("");
				int xt=0;
				for(int lie=0;lie<vbodyKey.length;lie++){
					String ibody=Toolkits.getString(getBodyValue(i, vbodyKey[lie]));
					String jbody=Toolkits.getString(getBodyValue(j, vbodyKey[lie]));
					if(ibody.equals(jbody)){
						xt++;
					}
				}
				if(xt==vbodyKey.length){
					for(int d=0;d<vbodyKey1.length;d++){
						if(!duibifree(i,j,vbodyKey0[d],vbodyKey1[d],vbodyKey2[d])){
							if(sql.toString().equals("")){
							    sql.append("��"+(i+1)+"�����"+(j+1)+"�еĴ����Ϣ������Ҫ��;\n");
							}
						}else{
							sql=new StringBuffer("");
							break;
						}
					}
					sb.append(sql.toString());
				}
			}
		}
		if(!sb.toString().trim().equals("")){
			showErrorMessage(sb.toString()+" ���ʵ!");
		}else{
			flag=false;
		}
		return flag;
	}
	private boolean duibifree(int i,int j,String free0, String free1, String free2) {
		UFDouble fp0=Toolkits.getUFDouble(getBodyValue(i, free0));
		UFDouble fp1=Toolkits.getUFDouble(getBodyValue(i, free1));
		String fp2=Toolkits.getString(getBodyValue(i, free2));
		UFDouble fd0=Toolkits.getUFDouble(getBodyValue(j, free0));
		UFDouble fd1=Toolkits.getUFDouble(getBodyValue(j, free1));
		String fd2=Toolkits.getString(getBodyValue(j, free2));
		if((fp0.doubleValue()!=0&&fp1.doubleValue()!=0)&&(fd0.doubleValue()!=0&&fd1.doubleValue()!=0)){
			if(fp2.equals("ȫ����")&&fd2.equals("ȫ����")){//��Ϊȫ����
				if(fp0.doubleValue()>fd1.doubleValue()||fd0.doubleValue()>fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("ȫ����")&&fd2.equals("�����")){//��λȫ��������λ�����
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("ȫ����")&&fd2.equals("�Ұ���")){//��λȫ��������λ�Ұ���
				if(fp0.doubleValue()>fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("ȫ����")&&fd2.equals("ȫ������")){//��λȫ��������λȫ������
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("�����")&&fd2.equals("ȫ����")){//��λ���������λȫ����
				if(fp0.doubleValue()>fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("�����")&&fd2.equals("�Ұ���")){//��λ���������λ�Ұ���
				if(fp0.doubleValue()>fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("�����")&&fd2.equals("�����")){//��λ���������λ�����
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("�����")&&fd2.equals("ȫ������")){//��λ���������λȫ������
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("�Ұ���")&&fd2.equals("ȫ����")){//��λ�Ұ�������λȫ����
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("�Ұ���")&&fd2.equals("�����")){//��λ�Ұ�������λ�����
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("�Ұ���")&&fd2.equals("�Ұ���")){//��λ�Ұ�������λ�Ұ���
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("�Ұ���")&&fd2.equals("ȫ������")){//��λ�Ұ�������λȫ������
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("ȫ������")&&fd2.equals("ȫ����")){//��λȫ����������λȫ����
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("ȫ������")&&fd2.equals("�����")){//��λȫ����������λȫ����
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("ȫ������")&&fd2.equals("�Ұ���")){//��λȫ����������λȫ����
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}else if(fp2.equals("ȫ������")&&fd2.equals("ȫ������")){//��λȫ����������λȫ����
				if(fp0.doubleValue()>=fd1.doubleValue()||fd0.doubleValue()>=fp1.doubleValue()){
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}
	//��ʼʱ�䲻�ܸ��ڽ���ʱ��
	private boolean beginOverEnetime() throws ParseException{
		boolean isOver=false;			
		// ��ʼ����
		Object sdate = getHeadValue("sdate");
		// ��ʼʱ��
		Object stime = getHeadValue("stime");
		// ��������
		Object edate = getHeadValue("edate");
		// ����ʱ��
		Object etime = getHeadValue("etime");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 
		String str1 = sdate.toString() + " "+ stime.toString(); 
		String str2 = edate.toString() + " "+ etime.toString(); 
		Date dt1 = df.parse(str1); 
        Date dt2 = df.parse(str2); 
        if (dt1.getTime() > dt2.getTime()) { 
        	isOver=true;
        }
		return isOver;
	}
	//��ʼ���ڲ��ܵ�����һ�ŵ��ݿ�ʼ����
	@SuppressWarnings("unchecked")
	private boolean beginOverLastEnetime() throws BusinessException, ParseException{
		boolean isOver=true;		
		// ��ʼ����
		Object sdate = getHeadValue("sdate");
		if(!Toolkits.isEmpty(sdate)){
	       	String vbillcode =getHeadValue("vbillcode")==null?"":getHeadValue("vbillcode").toString();
	    	String sql="select sdate,stime,pk_pricepolicy from gt_pricepolicy where nvl(dr,0)=0  and pk_corp='"+_getCorp().getPk_corp()+"' ";
	    	//���������˵�� ��ǰ�ǡ��޸ġ�����(�޸�ʱ,�����ж�Ҫ�ų�����������¼)
	    	if(!vbillcode.equals("")){
	    		sql += " and vbillcode<'" + vbillcode + "' ";
	    	}
	    	//����order by
	    	sql += " order by vbillcode desc ";
			Proxy.getInstance();
			ArrayList list =Proxy.getItf().queryArrayBySql(sql);
			if(list==null||list.size()==0){isOver=true;}else{
				HashMap<String, String> map=(HashMap<String,String>)list.get(0);
				String str = map.get("sdate");
		        if (new UFDate(sdate.toString()).before(new UFDate(str))) { 
		        	isOver=false;
		        }
			}
		}
		return isOver;
	}
	String sys_sdate=null;
	String sys_stime=null;
	AggregatedValueObject avo=null;
	/**
	 * �������Ϊ��ȡ��ǰ����Ŀ�ʼ���ںͿ�ʼʱ���ں�̨��ѯƥ���һ��,�ڷ�����ʱ�����ʱִ��
	 * */
	private boolean  autoCheck() {
		avo=getBufferData().getCurrentVO();
		sys_sdate=getBillUI()._getDate().toString();
		sys_stime=getBillUI()._getServerTime().getTime().toString();  
		if(avo==null)return false;
		SuperVO vo=(SuperVO)avo.getParentVO();
		String sdate=vo.getAttributeValue("sdate")==null?"":vo.getAttributeValue("sdate").toString().trim();
		String stime=vo.getAttributeValue("stime")==null?"":vo.getAttributeValue("stime").toString().trim();
	    while(!sys_sdate.equalsIgnoreCase(sdate)||!sys_stime.equalsIgnoreCase(stime)){
			sys_sdate=getBillUI()._getDate().toString();
			sys_stime=getBillUI()._getServerTime().getTime().toString();  
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
	    }
		String pk_pricepolicy=vo.getAttributeValue("pk_pricepolicy")==null?"":vo.getAttributeValue("pk_pricepolicy").toString().trim();
	    if(sys_sdate.equalsIgnoreCase(sdate)&&sys_stime.equalsIgnoreCase(stime)&&!Toolkits.isEmpty(pk_pricepolicy)){
	    	onAudoBoCheckPass(pk_pricepolicy,vo);
	    	return true;
	    }else{
	    	return false;
	    }
	}
	/**
	 * ����˵�����Զ���˵��ڣ��ر�֮ǰ�ĵ���
	 * @param  String pk_price_zc
	 * @param  SuperVO avo
	 * @author ʩ��
	 * @throws Exception 
	 * @time 2010-11-9
	 * */
	@SuppressWarnings("unchecked")
	private void onAudoBoCheckPass(String pk_pricepolicy,SuperVO vo){
		try {
	        vo.setAttributeValue("vapproveid" , _getOperator());
			vo.setAttributeValue("vapprovedate", _getDate());
			vo.setAttributeValue("vbillstatus", IJyglgtBillStatus.CHECKPASS);
			Proxy.getInstance();
			Proxy.getItf().updateVO(vo);
			// �ر�������˹��ĵ���
			StringBuffer sql_update=new StringBuffer();
			sql_update.append(" update gt_pricepolicy")
			.append(" set vbillstatus='" + IJyglgtBillStatus.CLOSE+"'")
			.append(" where vbillstatus = '" + IBillStatus.CHECKPASS+ "'" )
			.append(" and pk_pricepolicy != '" + pk_pricepolicy + "' and nvl(dr,0)=0 and pk_corp='"+_getCorp().getPk_corp()+"'");	
			Proxy.getInstance();
			Proxy.getItf().updateBYsql(sql_update.toString());
			//������һ�ŵ��ݵĽ������ڵ��ڵ�ǰ���ݵĿ�ʼ����
	     	String vbillcode =vo.getAttributeValue("vbillcode")==null?"":vo.getAttributeValue("vbillcode").toString();
	    	String sql="select edate,etime,pk_pricepolicy from gt_pricepolicy where nvl(dr,0)=0  and pk_corp='"+_getCorp().getPk_corp()+"' ";
	    	//���������˵�� ��ǰ�ǡ��޸ġ�����(�޸�ʱ,�����ж�Ҫ�ų�����������¼
	    	sql += " and vbillcode <'" + vbillcode + "' ";
	    	sql += " order by vbillcode desc ";
			Proxy.getInstance();
			ArrayList list =Proxy.getItf().queryArrayBySql(sql);
			String lastPk="";
			if(list==null||list.size()==0){return;}else{
				HashMap<String, String> map=(HashMap<String,String>)list.get(0);
				lastPk=map.get("pk_pricepolicy");
				if(!Toolkits.isEmpty(lastPk)){
					StringBuffer sql2=new StringBuffer();
					sql2.append(" update gt_pricepolicy")
					.append(" set edate='" + vo.getAttributeValue("sdate")+"'")
					.append(" where pk_pricepolicy = '" + lastPk + "'" );
					Proxy.getInstance();
					Proxy.getItf().updateBYsql(sql2.toString());
				}
			}
		}catch (BusinessException e) {
				e.printStackTrace();
		}
			
	}
    Container parent;
    public Container getParent() {
    	if(parent==null){
    		parent=new Container();
    	}
        return parent;
    }
    @Override
    protected void onBoLineAdd() throws Exception {
    	super.onBoLineAdd();
    	Integer vbillstatus=Toolkits.getInteger(getHeadValue("vbillstatus"));
    	if(vbillstatus==1){
    		setBodyValue(getOperName()+"��"+getBillUI()._getServerTime()+" ���Ӵ��", getRowCount()-1, "memo");
        }
		Object obj=getHeadValue("product");
		if(!Toolkits.isEmpty(obj)){
			int row=getRowCount()-1;
			columnshow(obj.toString(),row);
		}
    }
    /**
     * ��ȡ�۸������������Ӧ���������б����룻
     * */
	@SuppressWarnings("unchecked")
	private void columnshow(String product, int row) {
		String sql=" select b.free,b.listcode from gt_columnshow_b b " +
		"    left join gt_columnshow a on a.pk_columnshow=b.pk_columnshow" +
		"    where b.showflag='Y' and a.pk_productline='"+product+"' and nvl(a.dr,0)=0 and nvl(b.dr,0)=0 ";
		ArrayList al;
		try {
			al = Proxy.getItf().queryArrayBySql(sql.toString());
			if(al!=null&&al.size()>0){
				for(int i=0;i<al.size();i++){
					HashMap<String, String> hmp =(HashMap<String, String>)al.get(i);
					String vfree = Toolkits.getString(hmp.get("free"));
					String listcode = Toolkits.getString(hmp.get("listcode"));
					setlist(listcode,vfree,row);//��۸����߲����������б�����
				}
			}
		}catch (BusinessException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * ��۸����߲����������б�����
	 * */
	private void setlist(String listcode, String vfree, int row) {
		if(vfree.equals("free1_0")||vfree.equals("free1_1")){
			setBodyValue(listcode, row, "list1_0");
			setBodyValue(listcode, row, "list1_1");
		}else if(vfree.equals("free2_0")||vfree.equals("free2_1")){
			setBodyValue(listcode, row, "list2_0");
			setBodyValue(listcode, row, "list2_1");
		}else if(vfree.equals("free3_0")||vfree.equals("free3_1")){
			setBodyValue(listcode, row, "list3_0");
			setBodyValue(listcode, row, "list3_1");
	    }else if(vfree.equals("free4_0")||vfree.equals("free4_1")){
			setBodyValue(listcode, row, "list4_0");
			setBodyValue(listcode, row, "list4_1");
	    }
	}
	@Override
	protected void onBoEdit() throws Exception {
		super.onBoEdit();
		String product=Toolkits.getString(getHeadValue("product"));
		columnshow(product);
	}
	/**
	 * ���ܣ����ݲ�Ʒ�������ñ����ֶε���ʾ
	 * */
	@SuppressWarnings("unchecked")
	private void columnshow(String product) {
		String sql=" select b.free,b.name from gt_columnshow_b b " +
				"    left join gt_columnshow a on a.pk_columnshow=b.pk_columnshow" +
				"    where b.showflag='Y' and a.pk_productline='"+product+"' and nvl(a.dr,0)=0 and nvl(b.dr,0)=0 ";
		ArrayList al;
		try {
			String[] frees={"free1_0","free1_1","free1_2","free2_0","free2_1","free2_2","free3_0","free3_1","free3_2","free4_0","free4_1","free4_2","free5","free6","free7","free8","free9","free10"};
			String[] vfrees=new String[18];
			al = Proxy.getItf().queryArrayBySql(sql.toString());
			if(al!=null&&al.size()>0){
				for(int i=0;i<al.size();i++){
					HashMap<String, String> hmp =(HashMap<String, String>)al.get(i);
					String vfree = Toolkits.getString(hmp.get("free"));
					vfrees[i]=vfree;
					String name = Toolkits.getString(hmp.get("name"));
					getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().showTableCol(vfree);
					BillData bd = getBillCardPanelWrapper().getBillCardPanel().getBillData();
					BillItem item = bd.getBodyItem("gt_pricepolicy_b",vfree);
					item.setName(name);
					getBillCardPanelWrapper().getBillCardPanel().setBillData(bd);
				}
				for(int j=0;j<frees.length;j++){
					Boolean vflag=false;
					for(int x=0;x<vfrees.length;x++){
						if(frees[j].equals(vfrees[x])){
							vflag=true;
						}
					}
					if(!vflag){
						getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().hideTableCol(frees[j]);
					}
				}
			}else{
				for(int j=0;j<frees.length;j++){
					getBillCardPanelWrapper().getBillCardPanel().getBodyPanel().hideTableCol(frees[j]);
				}
			}
		} catch (BusinessException e1) {
			e1.printStackTrace();
		}
	}
	
	public void onBoCheckPass() throws Exception {
		if (this.getBillUI().getBufferData() == null)
			return;

		AggregatedValueObject billvo = (HYBillVO) this.getBillUI().getBufferData().getCurrentVO();
		if (Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.COMMIT
				|| Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.CHECKGOING) {
			this.getBillCardPanelWrapper().getBillCardPanel()
					.dataNotNullValidate();
			billvo.getParentVO().setAttributeValue("vapproveid" , _getOperator());
			PfUtilClient.processActionFlow(this.getBillUI(),FAPfUtil.AUDIT, IJyglgtBillType.JY02, this._getDate().toString(), billvo,null);
			this.getBillUI().showHintMessage("�������...");

			super.onBoRefresh();
		} else if(Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.FREE){
			this.getBillUI().showWarningMessage("û���ύ���������ظ���ˣ�");
			return;
		}else {
			this.getBillUI().showWarningMessage("�����ظ���ˣ�");
			return;
		}
	}


	protected void onBoCommit() throws Exception {
		if (this.getBillUI().getBufferData() == null)
			return;
		AggregatedValueObject billvo = (HYBillVO) this.getBillUI().getBufferData()
				.getCurrentVO();
		if(!Toolkits.getString(billvo.getParentVO().getAttributeValue("pk_operator")).equals(_getOperator())){
			this.getBillUI().showErrorMessage("�ύʧ�ܣ���ȷ���Ƿ���Ȩ���ύ");
			return;
		}
		if (Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() != IJyglgtBillStatus.FREE
				&& Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() != IJyglgtBillStatus.NOPASS) {
			this.getBillUI().showWarningMessage("�����ظ��ύ��");
			return;
		}
		billvo.getParentVO().setAttributeValue("vbillstatus", IJyglgtBillStatus.COMMIT);
			PfUtilClient.processAction(FAPfUtil.SAVE, IJyglgtBillType.JY02, this._getDate()
					.toString(), billvo, FAPfUtil.UPDATE_SAVE);

		this.getBillUI().showHintMessage("�ύ���.....");
		super.onBoRefresh();
	}
	
	public void onBoCheckNoPass() throws Exception {
		for(int row=0;row<getRowCount();row++){
			String pk_deliveryplan_b=getBodyValue(row, "pk_deliveryplan_b")==null?"":getBodyValue(row, "pk_deliveryplan_b").toString();
	    	//�ж����ε����Ƿ�ʹ��
	    	if(!pk_deliveryplan_b.equals("")){
	    		showErrorMessage("�ѱ����ε���ʹ�ã�����������");
	    		return ;
	    	}
		}
		if (this.getBillUI().getBufferData() == null)
			return;

		AggregatedValueObject billvo = (HYBillVO) this.getBillUI().getBufferData().getCurrentVO();
		if (Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.CHECKPASS
				|| Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.CHECKGOING
				|| Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.NOPASS) {
//			if(!Toolkits.getString(billvo.getParentVO().getAttributeValue("vapproveid")).equals(_getOperator())){
//			this.getBillUI().showErrorMessage("������˱��ˣ�����������");
//			return;
//		}
		billvo.getParentVO().setAttributeValue("vapproveid" , _getOperator());
		billvo.getParentVO().setAttributeValue("vunapproveid" , _getOperator());
		billvo.getParentVO().setAttributeValue("vunapprovedate" , _getDate());
//		billvo.getParentVO().setAttributeValue("vbillstatus" , IJyglgtBillStatus.FREE);
		billvo.getParentVO().setAttributeValue("dapprovedate" , null);

			PfUtilClient.processActionFlow(this.getBillUI(),FAPfUtil.UNAUDIT, IJyglgtBillType.JY02, this._getDate().toString(), billvo,null);
			this.getBillUI().showHintMessage("�������...");

			super.onBoRefresh();
		} else if(Toolkits.getInteger(billvo.getParentVO().getAttributeValue("vbillstatus")).intValue() == IJyglgtBillStatus.FREE){
			this.getBillUI().showWarningMessage("�Ƶ���״̬������Ҫ����");
			return;
		}else {
			this.getBillUI().showWarningMessage("�����ظ���ˣ�");
			return;
		}
      }
	
	@Override
	protected StringBuffer getWhere() {
		StringBuffer sb = new StringBuffer();
		sb.append(" billtype='"+getUIController().getBillType()+"' and ");
		return sb;
	}
}