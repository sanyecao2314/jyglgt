package nc.ui.jyglgt.scm.ic.freeitem;

import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UIRefPaneTextField;
import nc.ui.pub.bill.BillCellEditor;


/**
 * 
 * 自由项 参照 界面上的 celleditor， 因为每行的参照类型都可能不同，用缺省的BillCellEditor时录入小数有精度问题，
 * 所以这里单独提供一个CellEditor.
 * 
 * 复制自生产制造的自由项处理代码。稍加改造。
 * 
 * 2006-06-28，wnj
 *  
 */
public class FreeItemCellEditor extends BillCellEditor {

    public FreeItemCellEditor(final UIRefPane refTextField) {
        super(refTextField);
        refTextField.setReturnCode(false);
        editorComponent = refTextField;
        final nc.ui.pub.beans.UITextField textField = refTextField
                .getUITextField();
        this.clickCountToStart = 2;
        delegate = new EditorDelegate() {

            public void setValue(Object value) {
                //子类里无法判断OLD VALUE 。	
                if (refTextField instanceof nc.ui.pub.bill.BillObjectRefPane)
                    ((nc.ui.pub.bill.BillObjectRefPane) refTextField)
                            .setBillObject((nc.ui.pub.bill.IBillObject) value);
                else {
                    refTextField.setValue((String) value);
                }
                //获得焦点，并选中已有的内容，以便于删除、修改。
                if (!refTextField.getUITextField().hasFocus())
                    refTextField.getUITextField().selectAll();
                
            }

            public Object getCellEditorValue() {

                if (refTextField instanceof nc.ui.pub.bill.BillObjectRefPane)
                    return ((nc.ui.pub.bill.BillObjectRefPane) refTextField)
                            .getBillObject();
                if (refTextField.getRefNodeName() != null
                        && refTextField.getRefNodeName().trim().length() > 0)
                    return (refTextField.getRefName() == null ? ""
                            : refTextField.getRefName());
                else {
                    String text = (textField == null ? "" : textField.getText().trim());
                    //nc.vo.scm.pub.SCMEnv.out(refTextField.getTextType()+","+refTextField.getNumPoint());
                    //处理小数位数为0时只有一个点的情况。或以 ‘.’ 结束的整数问题。
                    if ("TextDbl".equals(refTextField.getTextType())//'TextDbl'是在FreeItemDlg设置的类型。
                            && refTextField.getNumPoint() >= 0){
                        if(text.equals(".") || text.equals("-") )
                            text="";
                        else if(text.endsWith(".")){
                            text=text.substring(0,text.length()-1);
                        }
                            
                    }
                    //未被清空,且小数位数大于零。
                    if (text.length()>0 && refTextField.getNumPoint() > 0){
                        if(text.startsWith(".")) //处理录入以点开始 .8213  的情况。
                            text="0"+text;
                        text = new nc.vo.pub.lang.UFDouble(text, refTextField
                                .getNumPoint()).toString();
                    }
                    return text;
                }
            }

            public boolean stopCellEditing() {
                // this line must before super.stopCellEditing()
                ((UIRefPaneTextField) refTextField.getUITextField())
                        .stopEditing();
                boolean br = super.stopCellEditing();
                return br;
            }
        };
        textField.addActionListener(delegate);
        textField.addFocusListener(delegate);
    }

}