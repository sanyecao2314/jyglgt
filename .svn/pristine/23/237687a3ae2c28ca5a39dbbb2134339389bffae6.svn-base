package nc.ui.jyglgt.scm.pub.query;

import java.awt.Container;

import nc.ui.pub.querymodel.QEQueryDlg;
import nc.vo.pub.lang.UFDate;

/**
 * 作者：田锋涛
 * 功能：查询引擎对话框，添加对查询日期的处理
 * 日期：2009-9-24
 */
public class SCMQEQueryDlg extends QEQueryDlg {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	//tianft 2009/09/30 查询模板处理类
	private SCMQueryConditionHandler queryCondHandler = null;
	
	/**
	 * @param c - Container
	 */
	public SCMQEQueryDlg(Container c) {
		super(c);
	}
	
	/**
	 * 
	 * 作者：田锋涛
	 * 功能：获取查询模板处理类
	 * 参数：
	 * 返回：
	 * 例外：
	 * 日期：2009-9-30
	 * 修改日期， 修改人，修改原因，注释标志
	 */
	protected SCMQueryConditionHandler getQueryCondHandler(){
		if(queryCondHandler == null)
			queryCondHandler = new SCMQueryConditionHandler(this);
		return queryCondHandler ;
	}
	
	/**
	 * 
	 * 作者：田锋涛
	 * 功能：添加查询日期字段
	 * 参数：dateFieldCodes - 要添加的日期字段数组
	 *       fromDates - from date 数组
	 *       toDates  - to date 数组
	 * 返回：
	 * 例外：
	 * 日期：2009-9-22
	 * 修改日期， 修改人，修改原因，注释标志
	 */
	public void addExtraDates(String[] dateFieldCodes,UFDate[] fromDates,UFDate[] toDates){
		getQueryCondHandler().addExtraDates(dateFieldCodes, fromDates, toDates);
	}
	
	/**
	 * 
	 * 作者：田锋涛
	 * 功能：添加查询日期字段
	 * 参数：dateFieldCode - 要添加的日期字段
	 *       fromDate - from date
	 *       toDate - to date
	 * 返回：
	 * 例外：
	 * 日期：2009-9-22
	 * 修改日期， 修改人，修改原因，注释标志
	 */
	public void addExtraDate(String dateFieldCode,UFDate fromDate,UFDate toDate){
		getQueryCondHandler().addExtraDate(dateFieldCode, fromDate, toDate);
	}
	
	/**
	 * 
	 * 作者：田锋涛
	 * 功能：
	 * 参数：dateFieldCode
	 * 返回：
	 * 例外：
	 * 日期：2009-9-23
	 * 修改日期， 修改人，修改原因，注释标志
	 */
	public void addCurToCurDate(String dateFieldCode){
		getQueryCondHandler().addCurToCurDate(dateFieldCode);
	}
	
	/**
	 * 
	 * 作者：田锋涛
	 * 功能：
	 * 参数：dateFieldCode
	 * 返回：
	 * 例外：
	 * 日期：2009-9-23
	 * 修改日期， 修改人，修改原因，注释标志
	 */
	public void addCurToCurDates(String[] dateFieldCode){
		getQueryCondHandler().addCurToCurDates(dateFieldCode);
	}
	
	/**
	 * 
	 * 作者：田锋涛
	 * 功能：
	 * 参数：dateFieldCode
	 * 返回：
	 * 例外：
	 * 日期：2009-9-23
	 * 修改日期， 修改人，修改原因，注释标志
	 */
	public void addCurMthFirstToCurDate(String dateFieldCode) {
		getQueryCondHandler().addCurMthFirstToCurDate(dateFieldCode);
	}
	
	/**
	 * 
	 * 作者：田锋涛
	 * 功能：
	 * 参数：dateFieldCode
	 * 返回：
	 * 例外：
	 * 日期：2009-9-23
	 * 修改日期， 修改人，修改原因，注释标志
	 */
	public void addCurMthFirstToCurDates(String[] dateFieldCode) {
		getQueryCondHandler().addCurMthFirstToCurDates(dateFieldCode);
	}
	
  /**
   * 父类方法重写
   * 
   * @see nc.ui.pub.query.QueryConditionClient#doInitBeforShowModal()
   */
  @Override
  protected int doInitBeforShowModal() {
  	int result = super.doInitBeforShowModal();
  	if(getQueryCondHandler().isAddExtraDt()){
  		initData();
  		getQueryCondHandler().setAddExtraDt(false);
  	}
  	return result;
  }

}
