package nc.ui.jyglgt.j400670;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.BillScrollPane;
import nc.vo.iuforeport.businessquery.SelectFldVO;

/**
 * 
 * @author��shipeng
 */
public class PriceChangeDlg extends nc.ui.pub.beans.UIDialog {

	private nc.ui.pub.beans.UIButton ivjBtnCancel = null;

	private nc.ui.pub.beans.UIButton ivjBtnOK = null;

	private nc.ui.pub.beans.UIPanel ivjPnlMain = null;

	private nc.ui.pub.beans.UIPanel ivjPnlTop = null;

	private javax.swing.JPanel ivjUIDialogContentPane = null;

	IvjEventHandler ivjEventHandler = new IvjEventHandler();

	private BillScrollPane m_ivjTablePane = null;

	private nc.ui.pub.beans.UIPanel ivjPnlRight = null;

	private Hashtable<String, String> ht_table = null;

	private SelectFldVO[] fieldVOs = null;

	private String returnValue = null;
	
	public String getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public boolean isOk=false;

	private String[] names = {"�����"};

	class IvjEventHandler implements java.awt.event.ActionListener, BillEditListener, BillEditListener2 {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == PriceChangeDlg.this.getBtnOK())
				connEtoC1();
			if (e.getSource() == PriceChangeDlg.this.getBtnCancel())
				connEtoC2();
		};

		public boolean beforeEdit(BillEditEvent e) {
			return true;
		}

		public void afterEdit(BillEditEvent e) {
			String value="0.00";
			for(int i=0;i<names.length;i++){
				 value=getUITablePane().getTableModel().getValueAt(i, "value")==null?"0.00":getUITablePane().getTableModel().getValueAt(i, "value").toString();			    
			}
			setReturnValue(value);
		}
		
		public void bodyRowChange(BillEditEvent e) {
		}
	};


	
	
	/**
	 * DataSourceDlg ������ע�⡣
	 */
	public PriceChangeDlg() {
		super();
		initialize();
	}

	/**
	 * DataSourceDlg ������ע�⡣
	 * 
	 * @param parent
	 *            java.awt.Container
	 */
	public PriceChangeDlg(java.awt.Container parent) {
		super(parent);
		initialize();
	}

	/**
	 * DataSourceDlg ������ע�⡣
	 * 
	 * @param parent
	 *            java.awt.Container
	 * @param title
	 *            java.lang.String
	 */
	public PriceChangeDlg(java.awt.Container parent, String title) {
		super(parent, title);
		initialize();
	}


	/**
	 * DataSourceDlg ������ע�⡣
	 * 
	 * @param owner
	 *            java.awt.Frame
	 */
	public PriceChangeDlg(java.awt.Frame owner) {
		super(owner);

	}

	/**
	 * DataSourceDlg ������ע�⡣
	 * 
	 * @param owner
	 *            java.awt.Frame
	 * @param title
	 *            java.lang.String
	 */
	public PriceChangeDlg(java.awt.Frame owner, String title) {
		super(owner, title);
	}

	/**
	 * Comment
	 */
	public void btnCancel_ActionEvents() {
		returnValue="0.00";
		isOk=false;
		closeCancel();
		return;
	}

	/**
	 * Comment
	 */
	public void btnOK_ActionEvents() {
		isOk=true;
		closeOK();
		return;
	}

	private String getTableCodeByName(String tablename) {
		String tablecode = null;
		Enumeration enum1 = ht_table.keys();
		while (enum1.hasMoreElements()) {
			tablecode = enum1.nextElement().toString();
			if (ht_table.get(tablecode).equals(tablename)) {
				break;
			} else {
				tablecode = null;
			}
		}
		return tablecode;
	}


	/**
	 * connEtoC1: (BtnOK.action. --> DataSourceDlg.btnOK_ActionEvents()V)
	 */
	/* ���棺�˷������������ɡ� */
	private void connEtoC1() {
		try {
			// user code begin {1}
			// user code end
			this.btnOK_ActionEvents();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoC2: (BtnCancel.action. -->
	 * DataSourceDlg.btnCancel_ActionEvents()V)
	 */
	/* ���棺�˷������������ɡ� */
	private void connEtoC2() {
		try {
			// user code begin {1}
			// user code end
			this.btnCancel_ActionEvents();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}


	/**
	 * ���� BtnCancel ����ֵ��
	 * 
	 * @return nc.ui.pub.beans.UIButton
	 */
	/* ���棺�˷������������ɡ� */
	private nc.ui.pub.beans.UIButton getBtnCancel() {
		if (ivjBtnCancel == null) {
			try {
				ivjBtnCancel = new UIButton() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public void processKeyEvent(java.awt.event.KeyEvent ev) {
						if (ev.getID() == java.awt.event.KeyEvent.KEY_PRESSED) {
							if (ev.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER || ev.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER) {
								PriceChangeDlg.this.btnCancel_ActionEvents();
							}
						}
					}
				};
				ivjBtnCancel.setName("BtnCancel");
				ivjBtnCancel.setText("ȡ��");
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjBtnCancel;
	}

	/**
	 * ���� BtnOK ����ֵ��
	 * 
	 * @return nc.ui.pub.beans.UIButton
	 */
	/* ���棺�˷������������ɡ� */
	public nc.ui.pub.beans.UIButton getBtnOK() {
		if (ivjBtnOK == null) {
			try {
				ivjBtnOK = new UIButton() {
					public void processKeyEvent(java.awt.event.KeyEvent ev) {
						if (ev.getID() == java.awt.event.KeyEvent.KEY_PRESSED) {
							if (ev.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER || ev.getKeyChar() == java.awt.event.KeyEvent.VK_ENTER) {
								PriceChangeDlg.this.btnOK_ActionEvents();
							}
						}
					}
				};
				ivjBtnOK.setName("BtnOK");
				ivjBtnOK.setText("ȷ��");
			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return ivjBtnOK;
	}

	/**
	 * ���� PnlMain ����ֵ��
	 * 
	 * @return nc.ui.pub.beans.UIPanel
	 */
	/* ���棺�˷������������ɡ� */
	private nc.ui.pub.beans.UIPanel getPnlMain() {
		if (ivjPnlMain == null) {
			try {
				ivjPnlMain = new nc.ui.pub.beans.UIPanel();
				ivjPnlMain.setName("PnlMain");
				ivjPnlMain.setLayout(new java.awt.BorderLayout());
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjPnlMain;
	}

	/**
	 * @return Returns the m_ivjTablePane.
	 */
	private BillScrollPane getUITablePane() {
		if (m_ivjTablePane == null) {
			m_ivjTablePane = new BillScrollPane();
			m_ivjTablePane.setName("Table");
			m_ivjTablePane.setRowNOShow(false);
		}
		return m_ivjTablePane;
	}

	/**
	 * ���� PnlBottom ����ֵ��
	 * 
	 * @return nc.ui.pub.beans.UIPanel
	 */
	/* ���棺�˷������������ɡ� */
	private nc.ui.pub.beans.UIPanel getPnlDown() {
		if (ivjPnlRight == null) {
			try {
				ivjPnlRight = new nc.ui.pub.beans.UIPanel();
				ivjPnlRight.setName("PnlRight");
				ivjPnlRight.setPreferredSize(new java.awt.Dimension(120, 50));
				getPnlDown().add(getBtnOK(), getBtnOK().getName());
				getPnlDown().add(getBtnCancel(), getBtnCancel().getName());
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjPnlRight;
	}

	/**
	 * ���� PnlTop ����ֵ��
	 * 
	 * @return nc.ui.pub.beans.UIPanel
	 */
	/* ���棺�˷������������ɡ� */
	private nc.ui.pub.beans.UIPanel getPnlTop() {
		if (ivjPnlTop == null) {
			try {
				ivjPnlTop = new nc.ui.pub.beans.UIPanel();
				ivjPnlTop.setName("PnlTop");
				ivjPnlTop.setPreferredSize(new java.awt.Dimension(0, 0));
				// user code begin {1}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjPnlTop;
	}

	/**
	 * ���� UIDialogContentPane ����ֵ��
	 * 
	 * @return javax.swing.JPanel
	 */
	/* ���棺�˷������������ɡ� */
	private javax.swing.JPanel getUIDialogContentPane() {
		if (ivjUIDialogContentPane == null) {
			try {
				ivjUIDialogContentPane = new javax.swing.JPanel();
				ivjUIDialogContentPane.setName("UIDialogContentPane");
				ivjUIDialogContentPane.setLayout(new java.awt.BorderLayout());
				getUIDialogContentPane().add(getPnlTop(), "North");
				getUIDialogContentPane().add(getPnlMain(), "Center");
				getUIDialogContentPane().add(getPnlDown(), "South");
			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return ivjUIDialogContentPane;
	}

	/**
	 * ÿ�������׳��쳣ʱ������
	 * 
	 * @param exception
	 *            java.lang.Throwable
	 */
	private void handleException(java.lang.Throwable exception) {

		/* ��ȥ���и��е�ע�ͣ��Խ�δ��׽�����쳣��ӡ�� stdout�� */
		System.out.println("--------- δ��׽�����쳣 ---------");
		exception.printStackTrace(System.out);
	}

	/**
	 * ��ʼ������
	 * 
	 * @exception java.lang.Exception
	 *                �쳣˵����
	 */
	/* ���棺�˷������������ɡ� */
	private void initConnections() throws java.lang.Exception {
		// user code begin {1}
		// user code end
		getBtnOK().addActionListener(ivjEventHandler);
		getBtnCancel().addActionListener(ivjEventHandler);
		getUITablePane().addEditListener(ivjEventHandler);
		getUITablePane().addEditListener2(ivjEventHandler);
	}

	/**
	 * ��ʼ���ࡣ
	 */
	/* ���棺�˷������������ɡ� */
	private void initialize() {
		try {
			// user code begin {1}
			// user code end
			setName("DataSourceDlg");
			setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
			setSize(400, 150);
			setLocation(300, 150);
			setContentPane(getUIDialogContentPane());
			initConnections();
			initTable();
			for(int i=0;i<names.length;i++){
				getUITablePane().addLine();
				getUITablePane().getTableModel().setValueAt(names[i], i, "name");
				getUITablePane().getTableModel().setValueAt("����", i, "cond");
				getUITablePane().getTableModel().setValueAt("0.00", i, "value");
			}
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
		// user code begin {2}
		try {
			getPnlMain().add(getUITablePane(), "Center");
			setTitle("�ͻ����");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initTable() {
		String[] names = {"����","����","ֵ"};
		String[] codes = {"name","cond","value"};
		BillModel oModel = new BillModel();
		BillItem[] biBodyItems = new BillItem[names.length];
		for (int i = 0; i < names.length; i++) {
			biBodyItems[i] = new BillItem();
			biBodyItems[i].setName(names[i]);
			biBodyItems[i].setKey(codes[i]);
			biBodyItems[i].setWidth(60);
			biBodyItems[i].setEdit(true);
			biBodyItems[i].setShow(true);
			biBodyItems[i].setDataType(BillItem.STRING);
		}
		biBodyItems[1].setDataType(BillItem.COMBO);
		biBodyItems[1].setWithIndex(true);
		UIComboBox cmb1 = (UIComboBox) biBodyItems[1].getComponent();
		cmb1.insertItemAt("����", 0);
		biBodyItems[0].setEdit(false);
		biBodyItems[0].setWidth(100);
		biBodyItems[1].setWidth(60);
		biBodyItems[2].setWidth(200);
		biBodyItems[2].setLength(200);
		oModel.setBodyItems(biBodyItems);
		getUITablePane().setTableModel(oModel);
		getUITablePane().updateUI();
	}	


	public void keyPressed(java.awt.event.KeyEvent evt) {
		super.keyPressed(evt);
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			if (getBtnOK().hasFocus()) {
				btnOK_ActionEvents();
			} else if (getBtnCancel().hasFocus()) {
				btnCancel_ActionEvents();
			}
		} else if ((evt.getModifiers() & InputEvent.ALT_MASK) != 0) {
			if (evt.getKeyCode() == KeyEvent.VK_O) {
				btnOK_ActionEvents();
			} else if (evt.getKeyCode() == KeyEvent.VK_Z) {
				btnCancel_ActionEvents();
			}
		} else if (evt.getKeyCode() == KeyEvent.VK_F10) {
			btnOK_ActionEvents();
		} else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
		} else if (evt.getKeyCode() == KeyEvent.VK_F2) {
		} else {
			return;
		}
	}

	/**
	 * ����ڵ� - ��������ΪӦ�ó�������ʱ���������������
	 * 
	 * @param args
	 *            java.lang.String[]
	 */
	public static void main(java.lang.String[] args) {
		try {
			PriceChangeDlg aDataSourceDlg = new PriceChangeDlg();
			aDataSourceDlg.setModal(true);
			aDataSourceDlg.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					System.exit(0);
				};
			});
			aDataSourceDlg.show();
			java.awt.Insets insets = aDataSourceDlg.getInsets();
			aDataSourceDlg.setSize(aDataSourceDlg.getWidth() + insets.left + insets.right, aDataSourceDlg.getHeight() + insets.top + insets.bottom);
			aDataSourceDlg.setVisible(true);
		} catch (Throwable exception) {
			System.err.println("nc.ui.pub.beans.UIDialog �� main() �з����쳣");
			exception.printStackTrace(System.out);
		}
	}

	public SelectFldVO[] getFieldVOs() {
		return fieldVOs;
	}

	public void setFieldVOs(SelectFldVO[] fieldVOs) {
		this.fieldVOs = fieldVOs;
	}
	
}