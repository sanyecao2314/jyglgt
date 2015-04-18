package nc.ui.jyglgt.scm.pub.sourceref;

import nc.ui.pub.beans.UIRadioButton;


/**
 * 创建人：王乃军

 * 功能：打印状态对话框。

 * 日期：(2004-11-15 14:06:17)
 */
public class BillRefModeSelPanel extends nc.ui.pub.beans.UIPanel {
  private UIRadioButton ivjJRadioButtonDoubleTable ;
  private UIRadioButton ivjJRadioButtonOneTable ;
 

/**
 * QryPrintStatusPanel 构造子注解。
 */
public BillRefModeSelPanel() {
  super();
  initialize();
}

/**
 * 返回 ivjJRadioButtonDoubleTable 特性值。
 * @return javax.swing.JRadioButton
 */
/* 警告：此方法将重新生成。 */
private UIRadioButton getRadioButtonDoubleTable() {
  if (ivjJRadioButtonDoubleTable == null) {
    try {
      ivjJRadioButtonDoubleTable = new UIRadioButton();
      ivjJRadioButtonDoubleTable.setName("ivjJRadioButtonDoubleTable");
      ivjJRadioButtonDoubleTable.setSelected(true);
      ivjJRadioButtonDoubleTable.setText(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("SCMCOMMON", "UPPSCMCommon-000502")/* @res"主子表形式 "*/);
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
 * 返回 ivjJRadioButtonOneTable 特性值。
 * @return javax.swing.JRadioButton
 */
/* 警告：此方法将重新生成。 */
private UIRadioButton getRadioButtonOneTable() {
  if (ivjJRadioButtonOneTable == null) {
    try {
      ivjJRadioButtonOneTable = new UIRadioButton();
      ivjJRadioButtonOneTable.setName("ivjJRadioButtonOneTable");
      ivjJRadioButtonOneTable.setText(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("SCMCOMMON", "UPPSCMCommon-000503")/* @res"单表形式"*/);
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
 * 创建人：王乃军
 *
 * 功能：得到选择的结果。sql
 *
 * 参数：表别名。会加在返回SQL的字段前面。可为空。
 *
 * 返回：
 *
 * 异常：
 *
 * 日期：(2004-11-15 14:16:10)
 * 修改日期，修改人，修改原因，注释标志：
 */
public boolean isShowDoubleTableRef() {
  

  if (getRadioButtonDoubleTable().isSelected())
    return true;
  
  return false;
    
}

/**
 * 创建人：王乃军
 *
 * 功能：得到选择的结果。sql
 *
 * 参数：表别名。会加在返回SQL的字段前面。可为空。
 *
 * 返回：
 *
 * 异常：
 *
 * 日期：(2004-11-15 14:16:10)
 * 修改日期，修改人，修改原因，注释标志：
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
 * 每当部件抛出异常时被调用
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

  /* 除去下列各行的注释，以将未捕捉到的异常打印至 stdout。 */
  // nc.vo.scm.pub.SCMEnv.out("--------- 未捕捉到的异常 ---------");
  nc.vo.scm.pub.SCMEnv.out(exception);
}
/**
 * 初始化类。
 */
/* 警告：此方法将重新生成。 */
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