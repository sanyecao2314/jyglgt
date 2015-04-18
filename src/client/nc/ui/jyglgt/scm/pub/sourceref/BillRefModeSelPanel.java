package nc.ui.jyglgt.scm.pub.sourceref;

import nc.ui.pub.beans.UIRadioButton;


/**
 * �����ˣ����˾�

 * ���ܣ���ӡ״̬�Ի���

 * ���ڣ�(2004-11-15 14:06:17)
 */
public class BillRefModeSelPanel extends nc.ui.pub.beans.UIPanel {
  private UIRadioButton ivjJRadioButtonDoubleTable ;
  private UIRadioButton ivjJRadioButtonOneTable ;
 

/**
 * QryPrintStatusPanel ������ע�⡣
 */
public BillRefModeSelPanel() {
  super();
  initialize();
}

/**
 * ���� ivjJRadioButtonDoubleTable ����ֵ��
 * @return javax.swing.JRadioButton
 */
/* ���棺�˷������������ɡ� */
private UIRadioButton getRadioButtonDoubleTable() {
  if (ivjJRadioButtonDoubleTable == null) {
    try {
      ivjJRadioButtonDoubleTable = new UIRadioButton();
      ivjJRadioButtonDoubleTable.setName("ivjJRadioButtonDoubleTable");
      ivjJRadioButtonDoubleTable.setSelected(true);
      ivjJRadioButtonDoubleTable.setText(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("SCMCOMMON", "UPPSCMCommon-000502")/* @res"���ӱ���ʽ "*/);
      ivjJRadioButtonDoubleTable.setBounds(55, 30, 151, 42);
      // user code begin {1}
      ivjJRadioButtonDoubleTable.setSelected(true);//default!
      // user code end
    } catch (java.lang.Throwable ivjExc) {
      // user code begin {2}
      // user code end
      handleException(ivjExc);
    }
  }
  return ivjJRadioButtonDoubleTable;
}
/**
 * ���� ivjJRadioButtonOneTable ����ֵ��
 * @return javax.swing.JRadioButton
 */
/* ���棺�˷������������ɡ� */
private UIRadioButton getRadioButtonOneTable() {
  if (ivjJRadioButtonOneTable == null) {
    try {
      ivjJRadioButtonOneTable = new UIRadioButton();
      ivjJRadioButtonOneTable.setName("ivjJRadioButtonOneTable");
      ivjJRadioButtonOneTable.setText(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("SCMCOMMON", "UPPSCMCommon-000503")/* @res"������ʽ"*/);
      ivjJRadioButtonOneTable.setBounds(55, 100, 151, 42);
      // user code begin {1}
      ivjJRadioButtonOneTable.setSelected(false);
      // user code end
    } catch (java.lang.Throwable ivjExc) {
      // user code begin {2}
      // user code end
      handleException(ivjExc);
    }
  }
  return ivjJRadioButtonOneTable;
}

/**
 * �����ˣ����˾�
 *
 * ���ܣ��õ�ѡ��Ľ����sql
 *
 * �����������������ڷ���SQL���ֶ�ǰ�档��Ϊ�ա�
 *
 * ���أ�
 *
 * �쳣��
 *
 * ���ڣ�(2004-11-15 14:16:10)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 */
public boolean isShowDoubleTableRef() {
  

  if (getRadioButtonDoubleTable().isSelected())
    return true;
  
  return false;
    
}

/**
 * �����ˣ����˾�
 *
 * ���ܣ��õ�ѡ��Ľ����sql
 *
 * �����������������ڷ���SQL���ֶ�ǰ�档��Ϊ�ա�
 *
 * ���أ�
 *
 * �쳣��
 *
 * ���ڣ�(2004-11-15 14:16:10)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 */
public void setSelect(boolean isShowDoubleTableRef) {
  
  if(isShowDoubleTableRef){
    getRadioButtonDoubleTable().setSelected(true);
    getRadioButtonOneTable().setSelected(false);
  }else{
    getRadioButtonDoubleTable().setSelected(false);
    getRadioButtonOneTable().setSelected(true);
  }
    
}

/**
 * ÿ�������׳��쳣ʱ������
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

  /* ��ȥ���и��е�ע�ͣ��Խ�δ��׽�����쳣��ӡ�� stdout�� */
  // nc.vo.scm.pub.SCMEnv.out("--------- δ��׽�����쳣 ---------");
  nc.vo.scm.pub.SCMEnv.out(exception);
}
/**
 * ��ʼ���ࡣ
 */
/* ���棺�˷������������ɡ� */
private void initialize() {
  try {
    // user code begin {1}
    // user code end
    setName("QryPrintStatusPanel");
    setLayout(null);
    setSize(269, 178);
    add(getRadioButtonDoubleTable(), getRadioButtonDoubleTable().getName());
    add(getRadioButtonOneTable(), getRadioButtonOneTable().getName());
  } catch (java.lang.Throwable ivjExc) {
    handleException(ivjExc);
  }
  // user code begin {2}
  javax.swing.ButtonGroup bg=new javax.swing.ButtonGroup();
  bg.add(getRadioButtonDoubleTable());
  bg.add(getRadioButtonOneTable());
  // user code end
}

}