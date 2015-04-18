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
 * @author：shipeng
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

	private String[] names = {"打款金额"};

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
	 * DataSourceDlg 构造子注解。
	 */
	public PriceChangeDlg() {
		super();
		initialize();
	}

	/**
	 * DataSourceDlg 构造子注解。
	 * 
	 * @param parent
	 *            java.awt.Container
	 */
	public PriceChangeDlg(java.awt.Container parent) {
		super(parent);
		initialize();
	}

	/**
	 * DataSourceDlg 构造子注解。
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
	 * DataSourceDlg 构造子注解。
	 * 
	 * @param owner
	 *            java.awt.Frame
	 */
	public PriceChangeDlg(java.awt.Frame owner) {
		super(owner);

	}

	/**
	 * DataSourceDlg 构造子注解。
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
	/* 警告：此方法将重新生成。 */
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
	/* 警告：此方法将重新生成。 */
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
	 * 返回 BtnCancel 特性值。
	 * 
	 * @return nc.ui.pub.beans.UIButton
	 */
	/* 警告：此方法将重新生成。 */
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
				ivjBtnCancel.setText("取消");
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
	 * 返回 BtnOK 特性值。
	 * 
	 * @return nc.ui.pub.beans.UIButton
	 */
	/* 警告：此方法将重新生成。 */
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
				ivjBtnOK.setText("确定");
			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return ivjBtnOK;
	}

	/**
	 * 返回 PnlMain 特性值。
	 * 
	 * @return nc.ui.pub.beans.UIPanel
	 */
	/* 警告：此方法将重新生成。 */
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
	 * 返回 PnlBottom 特性值。
	 * 
	 * @return nc.ui.pub.beans.UIPanel
	 */
	/* 警告：此方法将重新生成。 */
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
	 * 返回 PnlTop 特性值。
	 * 
	 * @return nc.ui.pub.beans.UIPanel
	 */
	/* 警告：此方法将重新生成。 */
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
	 * 返回 UIDialogContentPane 特性值。
	 * 
	 * @return javax.swing.JPanel
	 */
	/* 警告：此方法将重新生成。 */
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
	 * 每当部件抛出异常时被调用
	 * 
	 * @param exception
	 *            java.lang.Throwable
	 */
	private void handleException(java.lang.Throwable exception) {

		/* 除去下列各行的注释，以将未捕捉到的异常打印至 stdout。 */
		System.out.println("--------- 未捕捉到的异常 ---------");
		exception.printStackTrace(System.out);
	}

	/**
	 * 初始化连接
	 * 
	 * @exception java.lang.Exception
	 *                异常说明。
	 */
	/* 警告：此方法将重新生成。 */
	private void initConnections() throws java.lang.Exception {
		// user code begin {1}
		// user code end
		getBtnOK().addActionListener(ivjEventHandler);
		getBtnCancel().addActionListener(ivjEventHandler);
		getUITablePane().addEditListener(ivjEventHandler);
		getUITablePane().addEditListener2(ivjEventHandler);
	}

	/**
	 * 初始化类。
	 */
	/* 警告：此方法将重新生成。 */
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
				getUITablePane().getTableModel().setValueAt("等于", i, "cond");
				getUITablePane().getTableModel().setValueAt("0.00", i, "value");
			}
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
		// user code begin {2}
		try {
			getPnlMain().add(getUITablePane(), "Center");
			setTitle("客户金额");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initTable() {
		String[] names = {"名称","条件","值"};
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
		cmb1.insertItemAt("等于", 0);
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
	 * 主入口点 - 当部件作为应用程序运行时，启动这个部件。
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
			System.err.println("nc.ui.pub.beans.UIDialog 的 main() 中发生异常");
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