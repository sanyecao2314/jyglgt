package nc.vo.jyglgt.pub.Toolkits;


import nc.ui.trade.button.IBillButton;
/**
 * ˵��:��������
 * @author ����������
 * 2012-1-5 12:50:51
 */
public interface IJyglgtConst {
	/** ��׼����Ƭ���水ť*/
	public int[] TREECARD_BUTTONS={IBillButton.Add,IBillButton.Edit,IBillButton.Delete,IBillButton.Save,
			IBillButton.Cancel,IBillButton.Refresh};
	/** ��׼���б����水ť*/
	public int[]TREELIST_BUTTONS={IBillButton.Add,IBillButton.Edit,IBillButton.Delete,
            IBillButton.Refresh,   };
    /** ��׼�б����水ť     */
    public int[] LIST_BUTTONS={ IBillButton.Query, IBillButton.Add, IBillButton.Edit,IBillButton.Delete, 
            IBillButton.Line, IBillButton.Save, IBillButton.Cancel,IBillButton.Refresh};
    /** ��׼�����б���ť     */
    public int[] LIST_BUTTONS_M={ IBillButton.Query, IBillButton.Add, IBillButton.Edit, IBillButton.Delete,
            IBillButton.Card,IBillButton.Refresh };
    /**  ��׼������Ƭ��ť--������     */
    public int[] CARD_BUTTONS_M_CSH={ IBillButton.Brow,IBillButton.Add, IBillButton.Edit, IBillButton.Delete,
            IBillButton.Line, IBillButton.Save, IBillButton.Cancel,IBillButton.Return,IBillButton.Refresh,IBillButton.Print 
            };   
    /**  ��׼������Ƭ��ť     */
    public int[] CARD_BUTTONS_M={ IBillButton.Brow,IBillButton.Add, IBillButton.Edit, IBillButton.Delete,
            IBillButton.Line, IBillButton.Save, IBillButton.Cancel,IBillButton.Return,IBillButton.Refresh,IBillButton.Print 
            };  
    /**==============�տ�����============================**/
	
//	/**�տ�����-�տ�**/
//    public static final String RECEIVETYPE_SK = "0";	
//	/**�տ�����-����**/
//    public static final String RECEIVETYPE_DJ = "1";
//	/**�տ�����-�˿�**/
//    public static final String RECEIVETYPE_TK = "2";  
//    /**�տ�����-�ڳ�**/
//    public static final String RECEIVETYPE_QC = "3";
//    /**�տ�����-����**/
//    public static final String RECEIVETYPE_SX = "4";
//    /**�տ�����-�ֲ�**/
//    public static final String RECEIVETYPE_MB = "5";

	  /**�տ�����-Ԥ��**/
	  public static final String RECEIVETYPE_YS = "0";
	  /**�տ�����-�ֿ�**/
	  public static final String RECEIVETYPE_XK = "1";  
	  /**�տ�����-���**/
	  public static final String RECEIVETYPE_CK = "2";
	  /**�տ�����-�ڳ�**/
	  public static final String RECEIVETYPE_QC = "3";
	  /**�տ�����-��Ƿ**/
	  public static final String RECEIVETYPE_SX = "4";
	  /**�տ�����-����**/
	  public static final String RECEIVETYPE_QT = "5";
	  /**�տ�����-�Ż�**/
	  public static final String RECEIVETYPE_YH = "6";

}