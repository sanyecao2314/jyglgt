package nc.ui.jyglgt.pub;


import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import nc.bs.logging.Logger;
import nc.ui.pub.beans.util.NCOptionPane;
import nc.ui.pub.filemanager.FileUtil;

/**
 * This dialog is used for programmers to write file_manager's code.
 * Support upload client files to server;
 * Support download server files to client;
 * getID() method can get current selected dir after close dialog;
 * getSelectedFileName can get current selected fileName after close dialog;
 * this dialog is one base dialog,programmer can extends it to create own dialog and handle special goal.
 */
/**
 * 注意：默认情况点击显示按钮，相当于文件选择确认，触发closeOK()方法，具体处理由程序员完成；
 * 显示方式预定义的有三种，"显示服务器文件"，"下载并显示"，"下载并编辑（要求win2000以上），可以使用setShowStyle()设置，建议不要使用"显示服务器文件"方式，因为中文文件名web服务器不支持，无法显示。
 * 如果点击显示按钮不关闭对话框，请调用setAutoCloseFlag(false)设置。
 * 如果使用"下载并编辑",因为编辑过程程序无法控制，所以最好能提示用户，编辑后要重新上传，避免用户误解；
 * 文件打开调用的是系统的文件打开功能，有的机器如果不能打开，可能与机器的设置有关，或者文件关联有关，具体情况请咨询系统维护部分；
 * 
 */
@SuppressWarnings("serial")
public class FileManagerDialog extends nc.ui.pub.beans.UIDialog implements
        java.awt.event.ActionListener, java.awt.event.ItemListener {
//    private java.awt.GridLayout ivjUIPanel3GridLayout = null;
//    private javax.swing.BoxLayout ivjUIDialogContentPaneBoxLayout = null;
    private javax.swing.JPanel ivjUIDialogContentPane = null;
    private nc.ui.pub.beans.UIButton ivjUIButtonExit = null;
    private nc.ui.pub.beans.UIButton ivjUIButtonFileDel = null;
    private nc.ui.pub.beans.UIButton ivjUIButtonShowFile = null;
    private nc.ui.pub.beans.UIButton ivjUIButtonUpLoad = null;
    private nc.ui.pub.beans.UIComboBox ivjUIComboBoxID = null;
    private nc.ui.pub.beans.UILabel ivjUILabelID = null;
    private nc.ui.pub.beans.UIPanel ivjUIPanel1 = null;
    private nc.ui.pub.beans.UIPanel ivjUIPanel2 = null;
    private nc.ui.pub.beans.UIPanel ivjUIPanel3 = null;
    private nc.ui.pub.beans.UIScrollPane ivjUIScrollPaneFile = null;
    private nc.ui.pub.beans.UITree ivjUITreeFile = null;
    //real directory arrays
    protected String[] dirs = null;
    //directory arrays's displayed names
    protected String[] dirShowNames = null;
    //file tree model
    protected DefaultTreeModel treemodel = null;
    //file tree node
    protected javax.swing.tree.DefaultMutableTreeNode root = null;
    //FileManagerUtil
    protected FileManagerUtil fmu = null;

    /**
     * 构造器
     */
    /* 警告：此方法将重新生成。 */
    @SuppressWarnings("deprecation")
	public FileManagerDialog() {
        super();
        initialize();
    }
    /**
     * UPLoadFileDialog 构造子注解。
     */
    @SuppressWarnings("deprecation")
	public FileManagerDialog(String[] dirs, String[] dirShowNames) {
        super();
        this.dirs = dirs;
        this.dirShowNames = dirShowNames;
        initialize();
    }
    /**
     * UPLoadFileDialog 构造子注解。
     *
     * @param parent
     *            java.awt.Container
     */
    public FileManagerDialog(java.awt.Container parent, String[] dirs,
            String[] dirShowNames) {
        super(parent);
        this.dirs = dirs;
        this.dirShowNames = dirShowNames;
        initialize();
    }
    /**
     * UPLoadFileDialog 构造子注解。
     *
     * @param parent
     *            java.awt.Container
     * @param title
     *            java.lang.String
     */
    public FileManagerDialog(java.awt.Container parent, String title,
            String[] dirs, String[] dirShowNames) {
        super(parent, title);
        this.dirs = dirs;
        this.dirShowNames = dirShowNames;
        initialize();
    }
    /**
     * UPLoadFileDialog 构造子注解。
     *
     * @param owner
     *            java.awt.Frame
     */
    public FileManagerDialog(java.awt.Frame owner, String[] dirs,
            String[] dirShowNames) {
        super(owner);
        this.dirs = dirs;
        this.dirShowNames = dirShowNames;
        initialize();
    }
    /**
     * UPLoadFileDialog 构造子注解。
     *
     * @param owner
     *            java.awt.Frame
     * @param title
     *            java.lang.String
     */
    public FileManagerDialog(java.awt.Frame owner, String title, String[] dirs,
            String[] dirShowNames) {
        super(owner, title);
        this.dirs = dirs;
        this.dirShowNames = dirShowNames;
        initialize();
    }
    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(java.awt.event.ActionEvent e) {

        Object o = e.getSource();

        setButtonsState(false);
        if (o == getUIButtonExit())
            onExit();
        else if (o == getUIButtonShowFile())
            onFileShow();
        else if (o == getUIButtonUpLoad())
            onFileUpload();
        else if (o == getUIButtonFileDel())
            onFileDel();

        setButtonsState(true);
    }
    /**
     * 此处插入方法描述。 创建日期：(2003-4-17 14:50:54)
     *
     * @return nc.ui.pub.filemanager.FileManagerUtil
     */
    public FileManagerUtil getFmu() {
        if (fmu == null)
            fmu = new FileManagerUtil();
        return fmu;
    }
    /**
     * 此处插入方法说明。 创建日期：(2003-2-28 10:48:33)
     *
     * @return java.lang.String
     */
    public java.lang.String getDir() {
        int index = getUIComboBoxID().getSelectedIndex();
        if (index < 0)
            return null;

        return dirs[index];
    }
    /**
     * 此处插入方法说明。 创建日期：(2003-3-4 14:40:16)
     */
    public String getSelectedFileName() {
        DefaultMutableTreeNode node = getSelectedNode();

        return node == null ? null : (String) node.getUserObject();
    }
    /**
     * 此处插入方法说明。 创建日期：(2003-3-4 16:32:08)
     *
     * @return javax.swing.tree.DefaultMutableTreeNode
     */
    protected DefaultMutableTreeNode getSelectedNode() {
        if (getUITreeFile().getSelectionPath() == null)
            return null;

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) getUITreeFile()
                .getSelectionPath().getLastPathComponent();

        return node;
    }
    /**
     * 返回 UIButtonExit 特性值。
     *
     * @return nc.ui.pub.beans.UIButton
     */
    /* 警告：此方法将重新生成。 */
    protected nc.ui.pub.beans.UIButton getUIButtonExit() {
        if (ivjUIButtonExit == null) {
            try {
                ivjUIButtonExit = new nc.ui.pub.beans.UIButton();
                ivjUIButtonExit.setName("UIButtonExit");
                ivjUIButtonExit.setIButtonType(0/** Java默认(自定义) */
                );
                ivjUIButtonExit
                        .setPreferredSize(new java.awt.Dimension(114, 22));
                ivjUIButtonExit.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("_beans","UPP_uapcom0-000004")/*@res "退出"*/ + "(X)");
                // user code begin {1}
                ivjUIButtonExit.setMnemonic('X');
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUIButtonExit;
    }

    /**
     * 返回 UIButtonFielDel 特性值。
     *
     * @return nc.ui.pub.beans.UIButton
     */
    /* 警告：此方法将重新生成。 */
    protected nc.ui.pub.beans.UIButton getUIButtonFileDel() {
        if (ivjUIButtonFileDel == null) {
            try {
                ivjUIButtonFileDel = new nc.ui.pub.beans.UIButton();
                ivjUIButtonFileDel.setName("UIButtonFileDel");
                ivjUIButtonFileDel.setIButtonType(0/** Java默认(自定义) */
                );
                ivjUIButtonFileDel.setPreferredSize(new java.awt.Dimension(114,
                        22));
                ivjUIButtonFileDel.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000039")/*@res "删除"*/ + "(D)");
                // user code begin {1}
                ivjUIButtonFileDel.setMnemonic('D');
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUIButtonFileDel;
    }

    /**
     * 返回 UIButtonShowFile 特性值。
     *
     * @return nc.ui.pub.beans.UIButton
     */
    /* 警告：此方法将重新生成。 */
    protected nc.ui.pub.beans.UIButton getUIButtonShowFile() {
        if (ivjUIButtonShowFile == null) {
            try {
                ivjUIButtonShowFile = new nc.ui.pub.beans.UIButton();
                ivjUIButtonShowFile.setName("UIButtonShowFile");
                ivjUIButtonShowFile.setIButtonType(0/** Java默认(自定义) */
                );
                ivjUIButtonShowFile.setPreferredSize(new java.awt.Dimension(
                        114, 22));
                ivjUIButtonShowFile.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("_beans","UPP_uapcom0-000005")/*@res "显示"*/ + "(S)");
                // user code begin {1}
                ivjUIButtonShowFile.setMnemonic('S');
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUIButtonShowFile;
    }

    /**
     * 返回 UIButtonUpLoad 特性值。
     *
     * @return nc.ui.pub.beans.UIButton
     */
    /* 警告：此方法将重新生成。 */
    protected nc.ui.pub.beans.UIButton getUIButtonUpLoad() {
        if (ivjUIButtonUpLoad == null) {
            try {
                ivjUIButtonUpLoad = new nc.ui.pub.beans.UIButton();
                ivjUIButtonUpLoad.setName("UIButtonUpLoad");
                ivjUIButtonUpLoad.setIButtonType(0/** Java默认(自定义) */
                );
                ivjUIButtonUpLoad.setPreferredSize(new java.awt.Dimension(114,
                        22));
                ivjUIButtonUpLoad.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("_beans","UPP_uapcom0-000006")/*@res "上传"*/ + "(U)...");
                // user code begin {1}
                ivjUIButtonUpLoad.setMnemonic('U');
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUIButtonUpLoad;
    }

    /**
     * 返回 UIComboBoxID 特性值。
     *
     * @return nc.ui.pub.beans.UIComboBox
     */
    /* 警告：此方法将重新生成。 */
    protected nc.ui.pub.beans.UIComboBox getUIComboBoxID() {
        if (ivjUIComboBoxID == null) {
            try {
                ivjUIComboBoxID = new nc.ui.pub.beans.UIComboBox();
                ivjUIComboBoxID.setName("UIComboBoxID");
                ivjUIComboBoxID
                        .setPreferredSize(new java.awt.Dimension(500, 22));
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUIComboBoxID;
    }
    /**
     * 返回 UIDialogContentPane 特性值。
     *
     * @return javax.swing.JPanel
     */
    /* 警告：此方法将重新生成。 */
    protected javax.swing.JPanel getUIDialogContentPane() {
        if (ivjUIDialogContentPane == null) {
            try {
                ivjUIDialogContentPane = new javax.swing.JPanel();
                ivjUIDialogContentPane.setName("UIDialogContentPane");
                ivjUIDialogContentPane
                        .setLayout(getUIDialogContentPaneBoxLayout());
                getUIDialogContentPane().add(getUIPanel3(),
                        getUIPanel3().getName());
                getUIDialogContentPane().add(getUIPanel1(),
                        getUIPanel1().getName());
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUIDialogContentPane;
    }
    /**
     * 返回 UIDialogContentPaneBoxLayout 特性值。
     *
     * @return javax.swing.BoxLayout
     */
    /* 警告：此方法将重新生成。 */
    private javax.swing.BoxLayout getUIDialogContentPaneBoxLayout() {
        javax.swing.BoxLayout ivjUIDialogContentPaneBoxLayout = null;
        try {
            /* 创建部件 */
            ivjUIDialogContentPaneBoxLayout = new javax.swing.BoxLayout(
                    getUIDialogContentPane(), javax.swing.BoxLayout.Y_AXIS);
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
        ;
        return ivjUIDialogContentPaneBoxLayout;
    }
    /**
     * 返回 UILabelID 特性值。
     *
     * @return nc.ui.pub.beans.UILabel
     */
    /* 警告：此方法将重新生成。 */
    protected nc.ui.pub.beans.UILabel getUILabelID() {
        if (ivjUILabelID == null) {
            try {
                ivjUILabelID = new nc.ui.pub.beans.UILabel();
                ivjUILabelID.setName("UILabelID");
                ivjUILabelID.setPreferredSize(new java.awt.Dimension(500, 22));
                ivjUILabelID.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC000-0000794")/*@res "单据号"*/);
                ivjUILabelID.setMaximumSize(new java.awt.Dimension(500, 22));
                ivjUILabelID.setILabelType(1/** 输入框 */
                );
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUILabelID;
    }
    /**
     * 返回 UIPanel1 特性值。
     *
     * @return nc.ui.pub.beans.UIPanel
     */
    /* 警告：此方法将重新生成。 */
    protected nc.ui.pub.beans.UIPanel getUIPanel1() {
        if (ivjUIPanel1 == null) {
            try {
                ivjUIPanel1 = new nc.ui.pub.beans.UIPanel();
                ivjUIPanel1.setName("UIPanel1");
                ivjUIPanel1.setPreferredSize(new java.awt.Dimension(500, 200));
                ivjUIPanel1.setLayout(new java.awt.BorderLayout());
                getUIPanel1().add(getUIScrollPaneFile(), "Center");
                getUIPanel1().add(getUIPanel2(), "East");
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUIPanel1;
    }
    /**
     * 返回 UIPanel2 特性值。
     *
     * @return nc.ui.pub.beans.UIPanel
     */
    /* 警告：此方法将重新生成。 */
    protected nc.ui.pub.beans.UIPanel getUIPanel2() {
        if (ivjUIPanel2 == null) {
            try {
                ivjUIPanel2 = new nc.ui.pub.beans.UIPanel();
                ivjUIPanel2.setName("UIPanel2");
                ivjUIPanel2.setPreferredSize(new java.awt.Dimension(120, 500));
                ivjUIPanel2.setLayout(new java.awt.FlowLayout());
                getUIPanel2().add(getUIButtonUpLoad(),
                        getUIButtonUpLoad().getName());
                getUIPanel2().add(getUIButtonFileDel(),
                        getUIButtonFileDel().getName());
                getUIPanel2().add(getUIButtonShowFile(),
                        getUIButtonShowFile().getName());
                getUIPanel2().add(getUIButtonExit(),
                        getUIButtonExit().getName());
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUIPanel2;
    }

    /**
     * 返回 UIPanel3 特性值。
     *
     * @return nc.ui.pub.beans.UIPanel
     */
    /* 警告：此方法将重新生成。 */
    protected nc.ui.pub.beans.UIPanel getUIPanel3() {
        if (ivjUIPanel3 == null) {
            try {
                ivjUIPanel3 = new nc.ui.pub.beans.UIPanel();
                ivjUIPanel3.setName("UIPanel3");
                ivjUIPanel3.setLayout(getUIPanel3GridLayout());
                getUIPanel3().add(getUILabelID(), getUILabelID().getName());
                getUIPanel3().add(getUIComboBoxID(),
                        getUIComboBoxID().getName());
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUIPanel3;
    }
    /**
     * 返回 UIPanel3GridLayout 特性值。
     *
     * @return java.awt.GridLayout
     */
    /* 警告：此方法将重新生成。 */
    private java.awt.GridLayout getUIPanel3GridLayout() {
        java.awt.GridLayout ivjUIPanel3GridLayout = null;
        try {
            /* 创建部件 */
            ivjUIPanel3GridLayout = new java.awt.GridLayout();
            ivjUIPanel3GridLayout.setRows(2);
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
        ;
        return ivjUIPanel3GridLayout;
    }
    /**
     * 返回 UIScrollPaneFile 特性值。
     *
     * @return nc.ui.pub.beans.UIScrollPane
     */
    /* 警告：此方法将重新生成。 */
    protected nc.ui.pub.beans.UIScrollPane getUIScrollPaneFile() {
        if (ivjUIScrollPaneFile == null) {
            try {
                ivjUIScrollPaneFile = new nc.ui.pub.beans.UIScrollPane();
                ivjUIScrollPaneFile.setName("UIScrollPaneFile");
                getUIScrollPaneFile().setViewportView(getUITreeFile());
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUIScrollPaneFile;
    }
    /**
     * 返回 UITreeFile 特性值。
     *
     * @return nc.ui.pub.beans.UITree
     */
    /* 警告：此方法将重新生成。 */
    protected nc.ui.pub.beans.UITree getUITreeFile() {
        if (ivjUITreeFile == null) {
            try {
                ivjUITreeFile = new nc.ui.pub.beans.UITree();
                ivjUITreeFile.setName("UITreeFile");
                ivjUITreeFile.setBounds(0, 0, 160, 120);
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUITreeFile;
    }
    /**
     * 每当部件抛出异常时被调用
     *
     * @param exception
     *            java.lang.Throwable
     */
    protected void handleException(java.lang.Throwable exception) {

        /* 除去下列各行的注释，以将未捕捉到的异常打印至 stdout。 */
        Logger.error("--------- 未捕捉到的异常 ---------",exception);
    }
    protected void hotKeyPressed(javax.swing.KeyStroke hotKey,
            java.awt.event.KeyEvent e) {

        setButtonsState(false);

        int modifiers = hotKey.getModifiers();
        if (modifiers != 0) {
            //Combined hot key:
            switch (hotKey.getKeyCode()) {
            case KeyEvent.VK_U:
                if (getUIButtonUpLoad().isEnabled())
                    onFileUpload();
                break;
            case KeyEvent.VK_S:
                if (getUIButtonShowFile().isEnabled())
                    onFileShow();
                break;

            case KeyEvent.VK_D:
                if (getUIButtonFileDel().isEnabled())
                    onFileDel();
                break;
            case KeyEvent.VK_X:
                //case KeyEvent.VK_ESCAPE :
                if (getUIButtonExit().isEnabled())
                    onExit();
                break;

            //case KeyEvent.VK_ESCAPE :
            //keyEscPressed();
            //break;
            //case KeyEvent.VK_PAGE_UP :
            //keyPageUpPressed();
            //break;
            ////...
            }
        } else {
            switch (hotKey.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                if (getUIButtonExit().isEnabled())
                    onExit();
                break;
            }

        }
        setButtonsState(true);

        //else {
        ////Combined hot key:
        //boolean ctrl = false;
        //boolean alt = false;
        //boolean shift = false;
        //if ((modifiers & Event.CTRL_MASK) != 0) {
        //ctrl = true;
        //}
        //if ((modifiers & Event.ALT_MASK) != 0) {
        //alt = true;
        //}
        //if ((modifiers & Event.SHIFT_MASK) != 0) {
        //shift = true;
        //}
        //// 处理ctrl + S:
        //if (ctrl && hotKey.getKeyCode() == KeyEvent.VK_S) {
        //keyCtrlSPressed();
        //}
        //// ...
        //}
    }
    /**
     * 此处插入方法说明。 创建日期：(2003-2-28 9:35:21)
     */
    protected void initConnection() {

        getUIButtonExit().addActionListener(this);
        getUIButtonUpLoad().addActionListener(this);
        getUIButtonFileDel().addActionListener(this);
        getUIButtonShowFile().addActionListener(this);

        getUIComboBoxID().addItemListener(this);
    }
    /**
     * 初始化类。
     */
    /* 警告：此方法将重新生成。 */
    protected void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("UPLoadFileDialog");
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            setResizable(false);
            setSize(462, 337);
            setTitle(nc.ui.ml.NCLangRes.getInstance().getStrByID("_beans","UPP_uapcom0-000007")/*@res "文件管理"*/);
            setContentPane(getUIDialogContentPane());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        getUILabelID().setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC000-0000794")/*@res "单据号"*/);
        initConnection();
        initTree();
        initIDsCombobox();
        // user code end
    }

    /**
     * 此处插入方法说明。 创建日期：(2003-2-28 11:32:54)
     */
    protected void initIDsCombobox() {

        if (dirs == null || dirs.length == 0) {
            getUIComboBoxID().setEnabled(false);
            return;
        }
        for (int i = 0; i < dirs.length; i++) {
            if (dirShowNames[i] != null)
                getUIComboBoxID().addItem(dirShowNames[i]);
            else
                getUIComboBoxID().addItem(dirs[i]);
        }
        getUIComboBoxID().setSelectedIndex(0);
    }
    /**
     * 此处插入方法说明。 创建日期：(2003-3-4 14:40:16)
     */
    protected void initTree() {

        if (root == null)
            root = new DefaultMutableTreeNode("root");

        if (treemodel == null)
            treemodel = new DefaultTreeModel(root);

        getUITreeFile().setModel(treemodel);
        getUITreeFile().setRootVisible(false);
        getUITreeFile().getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
    }
    /**
     * Invoked when an item has been selected or deselected. The code written
     * for this method performs the operations that need to occur when an item
     * is selected (or deselected).
     */
    public void itemStateChanged(java.awt.event.ItemEvent e) {

        if (e.getStateChange() == ItemEvent.SELECTED) {
            updateTree();
            setButtonsState(true);
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
            FileManagerDialog aFileManagerDialog;
            aFileManagerDialog = new FileManagerDialog(new String[] { "aaa",
                    "bbb" }, new String[] { "aaa", "bbb" });
            aFileManagerDialog.setShowStyle(FileManagerDialog.SHOW_FILE_LOCAL);
            aFileManagerDialog.setModal(true);
            aFileManagerDialog
                    .addWindowListener(new java.awt.event.WindowAdapter() {
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        };
                    });
            aFileManagerDialog.show();
            java.awt.Insets insets = aFileManagerDialog.getInsets();
            aFileManagerDialog.setSize(aFileManagerDialog.getWidth()
                    + insets.left + insets.right, aFileManagerDialog
                    .getHeight()
                    + insets.top + insets.bottom);
            aFileManagerDialog.setVisible(true);
        } catch (Throwable exception) {
            System.err.println("nc.ui.pub.beans.UIDialog 的 main() 中发生异常");
            exception.printStackTrace(System.out);
        }
    }
    /**
     * Use can rewrite this method for special。 创建日期：(2003-3-5 10:45:51)
     */
    protected void onExit() {
        this.closeCancel();
    }
    /**
     * 此处插入方法说明。 创建日期：(2003-3-4 14:15:56)
     */
    protected void onFileDel() {

        DefaultMutableTreeNode node = getSelectedNode();

        if (node == null)
            return;

        if (NCOptionPane.showConfirmDialog(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("_beans","UPP_uapcom0-000008")/*@res "确定要删除吗?"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("_beans","UPP_uapcom0-000009")/*@res "提示"*/,
                NCOptionPane.OK_CANCEL_OPTION) != NCOptionPane.OK_OPTION)
            return;

        FileManagerUtil.deleteFileServer(getDir(), getSelectedFileName());
        updateTree();
    }
    /**
     * Use can rewrite this method for special。 创建日期：(2003-3-4 14:15:42)
     */
    protected void onFileShow() {

        final int showStyle = getShowStyle();
        if (showStyle == SHOW_FILE_SERVER || showStyle == SHOW_FILE_LOCAL
                || showStyle == SHOW_FILE_AND_EDIT) {
            final String dir = getDir();
            final String fn = getSelectedFileName();

            if (dir == null || fn == null) {
                if (NCOptionPane.showConfirmDialog(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("_beans","UPP_uapcom0-000010")/*@res "没有选择目录或者文件,要退出吗?"*/) != nc.ui.pub.beans.util.NCOptionPane.OK_OPTION)
                    return;
            } else {
                (new Thread() {
                    public void run() {
                        showFile(dir, fn, showStyle);
                    }
                }).start();
            }
        }

        if (isAutoCloseFlag())
            this.closeOK();
    }
    /**
     * 此处插入方法说明。 创建日期：(2003-2-28 10:14:23)
     */
    protected void onFileUpload() {
        String id = getDir();
        if (id == null) {
            NCOptionPane.showMessageDialog(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("_beans","UPP_uapcom0-000011")/*@res "ID号不能为空!"*/);
            return;
        }

        nc.ui.pub.beans.UIFileChooser fileChooser = new nc.ui.pub.beans.UIFileChooser();
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        int rs = fileChooser.showOpenDialog(this);

        if (rs != javax.swing.JFileChooser.APPROVE_OPTION)
            return;
        File file =  fileChooser.getSelectedFile();
        if(!file.exists())
        	return;
        String localFilePath =file.getAbsolutePath();
        String fileName = fileChooser.getSelectedFile().getName();

        if (localFilePath != null && localFilePath.length() > 0) {

            if (FileManagerUtil.isFileExistedServer(getDir(), fileName)) {
                int result = NCOptionPane.showConfirmDialog(this,
                        nc.ui.ml.NCLangRes.getInstance().getStrByID("_beans","UPP_uapcom0-000012")/*@res "文件存在,要覆盖已有的文件吗?"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("_beans","UPP_uapcom0-000013")/*@res "上传文件"*/,
                        NCOptionPane.OK_CANCEL_OPTION);
                if (result != NCOptionPane.OK_OPTION) {
                    return;
                }
            }

            FileManagerUtil.saveFileToServer(id, localFilePath);
            updateTree();
        }

    }
    /**
     * 此处插入方法说明。 创建日期：(2003-2-28 9:38:30)
     *
     * @param flag
     *            boolean
     */
    protected void setButtonsState(boolean flag) {

        if (!flag) {
            getUIButtonFileDel().setEnabled(flag);
            getUIButtonShowFile().setEnabled(flag);
            getUIButtonExit().setEnabled(flag);
            getUIButtonUpLoad().setEnabled(flag);

            return;
        }

        boolean hasID = getDir() != null && getDir().trim().length() > 0;
//        boolean hasNodes = root.getChildCount() > 0;
        boolean hasSelect = getUITreeFile().getSelectionCount() > 0;

        getUIButtonFileDel().setEnabled(hasSelect);
        getUIButtonShowFile().setEnabled(hasSelect);
        getUIButtonExit().setEnabled(flag);
        getUIButtonUpLoad().setEnabled(hasID);
    }
    /**
     * 此处插入方法说明。 创建日期：(2003-3-4 14:40:16)
     */
    protected void updateTree() {

        root.removeAllChildren();
        treemodel.reload(root);

        String id = getDir();
        if (id == null)
            return;

        String[] files = FileManagerUtil.getDirFileNamesServer(id);

        if (files == null)
            return;

        for (int i = 0; i < files.length; i++) {
            String filename = files[i];
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(filename);
            root.add(node);
        }
        treemodel.reload(root);
        if (getUITreeFile().getRowCount() > 0) {
            getUITreeFile().setSelectionRow(0);
        }
    }

    private boolean autoCloseFlag = true;
    public static final int SHOW_FILE_AND_EDIT = 2;//"下载并编辑（要求win2000以上）"
    public static final int SHOW_FILE_LOCAL = 1;//"下载并显示"
    public static final int SHOW_FILE_SERVER = 0;//"显示服务器文件"
    //显示模式，默认不显示，由程序员控制；
    private int showStyle = -1;

    /**
     * 此处插入方法描述。 创建日期：(2004-5-17 10:49:01)
     *
     * @return int
     */
    public int getShowStyle() {
        return showStyle;
    }

    /**
     * 此处插入方法描述。 创建日期：(2004-5-19 9:11:27)
     *
     * @return boolean
     */
    public boolean isAutoCloseFlag() {
        return autoCloseFlag;
    }

    /**
     * 此处插入方法描述。 创建日期：(2004-5-19 9:11:27)
     *
     * @param newAutoCloseFlag
     *            boolean
     */
    public void setAutoCloseFlag(boolean newAutoCloseFlag) {
        autoCloseFlag = newAutoCloseFlag;
    }

    /**
     * 此处插入方法描述。 创建日期：(2004-5-17 10:49:01)
     *
     * @param newShowStyle
     *            int
     */
    public void setShowStyle(int newShowStyle) {
        showStyle = newShowStyle;
    }

    /**
     * Use can rewrite this method for special。 创建日期：(2003-3-4 14:15:42)
     */
    private static void showFile(String dir, String filename, int showStyle) {
        if (dir == null || filename == null)
            return;

        if (showStyle == SHOW_FILE_SERVER) {
            // if show fileserver (not allow to edit)
            FileManagerUtil.showFileServer(dir, filename);
        } else if (showStyle == SHOW_FILE_LOCAL
                || showStyle == SHOW_FILE_AND_EDIT) {
            //download from server and save to local
            FileManagerUtil.saveFileToLocal(dir, filename);
            try {
                //edit local file(require win2000 above)
                String fp = FileManagerUtil.getFilePathAbsoluteLocal(dir,
                        filename);
                FileUtil.viewFile(fp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //show file local
            //FileManagerUtil.showFileLocal(dir, filename);
        }
        //else if (showStyle == SHOW_FILE_AND_EDIT) {
        ////download from server and save to local
        //FileManagerUtil.saveFileToLocal(dir, filename);
        //try {
        ////edit local file(require win2000 above)
        //String fp = FileManagerUtil.getFilePathAbsoluteLocal(dir, filename);
        //FileUtil.editFile(fp);
        //} catch (Exception ex) {
        //ex.printStackTrace();
        //}
        //}
    }
    
    /**
     * 设置文件删除按钮是否可用
     *  
     * @param useable
     * 创建时间：2005-11-23 11:35:03
     */
    public void setFileDelBtnUseable(boolean useable){
    	getUIButtonFileDel().setVisible(useable);
    }
    /**
     * 设置文件显示按钮是否可用
     *  
     * @param useable
     * 创建时间：2005-11-23 11:35:26
     */
    public void setShowFileBtnUseable(boolean useable){
    	getUIButtonShowFile().setVisible(useable);
    }
    /**
     * 设置上传文件按钮是否可用
     *  
     * @param useable
     * 创建时间：2005-11-23 11:35:43
     */
    public void setUpLoadBtnUseable(boolean useable){
    	getUIButtonUpLoad().setVisible(useable);
    }
}
