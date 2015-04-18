package nc.ui.jyglgt.scm.pub.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.query.QueryConditionClient;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.query.QueryConditionVO;
import nc.vo.scm.pub.lang.DateUtils;

/**
 * 作者：田锋涛
 * 功能：查询模板的处理类，通过修改代码修改查询模板的相关字段，避开直接修改查询模板
 * 日期：2009-9-30
 */
public class SCMQueryConditionHandler {
	
	/**
	 * 查询对话框
	 */
	private QueryConditionClient queryDlg = null;
	
	/**
	 * 日期处理标志
	 */
	private boolean isAddExtraDt = false;
	
	/**
	 * 构造方法
	 * @param queryDlg - QueryConditionClient 查询对话框类
	 */
	public SCMQueryConditionHandler(QueryConditionClient queryDlg){
		this.queryDlg = queryDlg;
	}
	
	/**
	 * @return the isAddExtraDt
	 */
	public boolean isAddExtraDt() {
		return isAddExtraDt;
	}

	/**
	 * @param isAddExtraDt the isAddExtraDt to set
	 */
	public void setAddExtraDt(boolean isAddExtraDt) {
		this.isAddExtraDt = isAddExtraDt;
	}


	/**
	 * 
	 * 作者：田锋涛
	 * 功能：实现查询日期处理，添加一个日期查询项，同时更改查询条件操作符(如 等于，大于等于)。
	 *       1. 处理前示例：
	 *          查询日期： 等于 XXXX-XX-XX
	 *       2. 处理后示例：
	 *          查询日期： 小于等于 XXXX-XX-XX
	 *          查询日期： 大于等于 XXXX-XX-XX
	 * 参数：dateFieldCodes - String 数组，要处理的日期字段名，查询模板里必须有相应的字段才做处理
	 *       extraFromDates - UFDate[]，日期数组
	 *       extraToDates -  UFDate[]，日期数组
	 * 返回：无
	 * 例外：无
	 * 日期：2009-9-30
	 * 修改日期， 修改人，修改原因，注释标志
	 */
	public void processExtraDates(String[] dateFieldCodes,UFDate[] extraFromDates,UFDate[] extraToDates) {
		if(queryDlg == null || ArrayUtils.isEmpty(dateFieldCodes)) 
			return;

		//从模板中读出的条件VO数组
		QueryConditionVO[] oldQueryCondVos = queryDlg.getConditionDatas();
		//模板无数据直接返回
		if(ArrayUtils.isEmpty(oldQueryCondVos))
			return;
		
		//存储新的VO数组
		List<QueryConditionVO> newQueryCondVOs = new ArrayList<QueryConditionVO>();
		
		for (int i = 0; i < oldQueryCondVos.length; i++) {
			//直接添加，需要变化后面再处理
			newQueryCondVOs.add(oldQueryCondVos[i]);
			
			//判断日期字段名是否匹配
			int iPostion = indexExtraDate(oldQueryCondVos[i].getFieldCode(),dateFieldCodes);
			if(iPostion <= -1) continue;
			
			//设置操作码
			oldQueryCondVos[i].setOperaCode(">=@<=@=@<@>@<>@");
			oldQueryCondVos[i].setOperaName(QueryConditionVO.translateOperaCodeToName(">=@<=@=@<@>@<>@"));
			
			//对应日期数组有值的话，赋值
			if (iPostion < extraFromDates.length && extraFromDates[iPostion] != null) {
				oldQueryCondVos[i].setValue(extraFromDates[iPostion].toString());
				queryDlg.setDefaultValue(oldQueryCondVos[i].getFieldCode(), null,extraFromDates[iPostion].toString());
			}
			
			//需要处理的日期，复制一份
			QueryConditionVO colneQueryCondVo = (QueryConditionVO) oldQueryCondVos[i].clone();
			//设置操作码
			colneQueryCondVo.setOperaCode("<=@>=@=@<@>@<>@");
			colneQueryCondVo.setOperaName(QueryConditionVO.translateOperaCodeToName("<=@>=@=@<@>@<>@"));
			
		  //对应日期数组有值的话，赋值
			if (iPostion < extraToDates.length && extraToDates[iPostion] != null)
				colneQueryCondVo.setValue(extraToDates[iPostion].toString());
			
			newQueryCondVOs.add(colneQueryCondVo);
		}
		//设置新的条件VO
		queryDlg.setConditionDatas(newQueryCondVOs.toArray(new QueryConditionVO[newQueryCondVOs.size()]));
	  
		//设置调用方法标志
		setAddExtraDt(true);
  }
	
	/**
	 * 
	 * 作者：田锋涛
	 * 功能：判断当前字段是否在所传日期字段数组中，并返回在数组中的索引
	 * 参数：dateFieldCode - 当前字段
	 *       dateFieldCodes - 所传日期字段数组
	 * 返回：int 当前字段在所传数组中的索引
	 * 例外：无
	 * 日期：2009-9-30
	 * 修改日期， 修改人，修改原因，注释标志
	 */
	private int indexExtraDate(String dateFieldCode,String[] dateFieldCodes){
		for(int i = 0; i < dateFieldCodes.length; i++){
			if(dateFieldCode.equals(dateFieldCodes[i]))
				return i;
		}
		return -1;
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
	 * 日期：2009-9-30
	 * 修改日期， 修改人，修改原因，注释标志
	 */
	public void addExtraDates(String[] dateFieldCodes,UFDate[] fromDates,UFDate[] toDates){
		processExtraDates(dateFieldCodes,fromDates,toDates);
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
	 * 日期：2009-9-30
	 * 修改日期， 修改人，修改原因，注释标志
	 */
	public void addExtraDate(String dateFieldCode,UFDate fromDate,UFDate toDate){
		addExtraDates(new String[] { dateFieldCode }, new UFDate[] { fromDate }, new UFDate[] { toDate });
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
		addExtraDate(dateFieldCode,getCurDate(),getCurDate());
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
		if(ArrayUtils.isEmpty(dateFieldCode)) 
			return;
		UFDate[] dates = getCurrentDates(dateFieldCode.length);
		addExtraDates(dateFieldCode,dates,dates);
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
		addExtraDate(dateFieldCode, DateUtils.getFirstMonthDay(getCurDate()),
				getCurDate());
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
		if (ArrayUtils.isEmpty(dateFieldCode))
			return;
		addExtraDates(dateFieldCode, getCurMthFirstDates(dateFieldCode.length),
				getCurrentDates(dateFieldCode.length));
	}

	/**
	 * 
	 * 作者：田锋涛
	 * 功能：
	 * 参数：number
	 * 返回：
	 * 例外：
	 * 日期：2009-9-23
	 * 修改日期， 修改人，修改原因，注释标志
	 */
	protected UFDate[] getCurrentDates(int number){
		UFDate[] curDates = new UFDate[number];
		for(int i = 0; i < number; i++)
			curDates[i] = getCurDate();
		return curDates;
	}
	
	/**
	 * 
	 * 作者：田锋涛
	 * 功能：
	 * 参数：number
	 * 返回：
	 * 例外：
	 * 日期：2009-9-23
	 * 修改日期， 修改人，修改原因，注释标志
	 */
	protected UFDate[] getCurMthFirstDates(int number){
		UFDate[] curMthFirstDates = new UFDate[number];
		for(int i = 0; i < number; i++)
			curMthFirstDates[i] = DateUtils.getFirstMonthDay(getCurDate());
		return curMthFirstDates;
	}
	
	/**
	 * 
	 * 作者：田锋涛
	 * 功能：
	 * 参数：
	 * 返回：
	 * 例外：
	 * 日期：2009-9-30
	 * 修改日期， 修改人，修改原因，注释标志
	 * @return
	 */
	protected UFDate getCurDate(){
		return ClientEnvironment.getInstance().getDate();
	}

}
