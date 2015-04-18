
package nc.bs.jyglgt.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.framework.naming.Context;
import nc.itf.uap.pf.IPFMessage;
import nc.jdbc.framework.JdbcSession;
import nc.jdbc.framework.PersistenceManager;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.exception.DbException;
import nc.jdbc.framework.generator.SequenceGenerator;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IExAggVO;



/**
 * @author  公共开发者 2012-1-5 13:48:36
 * @类型名  : ServerDAO
 * @功能 : TODO
 * @版本 : v1.0
 */
public class ServerDAO extends AbstractDAO {
	
	/**
	 * 
	 */
	public ServerDAO() {
           super();
	}
	
	//保存VO数组带PK
	public void insertVOsWithPK(Object[] objs)throws BusinessException{		
			getBaseDAO().insertVOArrayWithPK((SuperVO[])objs);	
	}

	//保存VO数组不带PK
	public void insertVOsArr(Object[] objs)throws BusinessException{		
			getBaseDAO().insertVOArray((SuperVO[])objs);	
	}
	
	//删除VO数组
	public void deleteVOsArr(Object[] objs)throws BusinessException{		
		getBaseDAO().deleteVOArray((SuperVO[])objs);		
    }
	
	
	//删除VO
	public void deleteVO(Object obj)throws BusinessException{		
		getBaseDAO().deleteVO((SuperVO)obj);		
    }
	
	//更新VO数组
	public void updateVOsArr(Object[] objs)throws BusinessException{		
		getBaseDAO().updateVOArray((SuperVO[])objs);		
    }
	
	//更新VO
	public void updateVO(Object obj)throws BusinessException{		
		getBaseDAO().updateVO((SuperVO)obj);	
		
    }
	
	//保存VO返回PK
	public String insertVObackPK(Object obj)throws BusinessException{		
			return getBaseDAO().insertVO((SuperVO)obj);	
	}
	
	//更新聚合Vo
	public void updateAggregatedValueVO(AggregatedValueObject obj)throws BusinessException{
		getBaseDAO().updateVO((SuperVO)obj.getParentVO());
		getBaseDAO().updateVOArray((SuperVO[])obj.getChildrenVO());
	}
	
	/**
	 * 针对有表体增删行修改时保存的数据，表体增行数据插入，删行数据更新删除标记
	 * */
	public void updateAggVOToEdit(AggregatedValueObject obj,String hid,String bid,String btable)throws BusinessException{		
		//更新表头
		getBaseDAO().updateVO((SuperVO)obj.getParentVO());
		String sql="select "+bid+" from "+btable+" where isnull(dr,0)=0 and "+hid+"='"+obj.getParentVO().getPrimaryKey()+"'";
		ArrayList<HashMap<String,String>> blist=queryArrayBySql(sql);
		ArrayList<String> blist_pk=new ArrayList<String>();
		for(int i=0;i<blist.size();i++){
			HashMap<String,String> map=(HashMap<String,String>)blist.get(i);
			blist_pk.add(map.get(bid)==null?"":map.get(bid).toString());
		}
		SuperVO[]svo=(SuperVO[])obj.getChildrenVO();
		ArrayList<SuperVO> list=new ArrayList<SuperVO>();
		ArrayList<String> list_pk=new ArrayList<String>();
		for(int i=0;i<svo.length;i++){
			if(svo[i].getPrimaryKey()==null){
				//插入新增行
				svo[i].setAttributeValue(hid, obj.getParentVO().getPrimaryKey());
				getBaseDAO().insertVO(svo[i]);
			}else{
				list_pk.add(svo[i].getPrimaryKey());
				list.add(svo[i]);
			}
		}
		SuperVO[] svo_update=(SuperVO[])list.toArray(new SuperVO[list.size()]);
		//更新表体
		getBaseDAO().updateVOArray(svo_update);
		blist_pk.removeAll(list_pk);
		//更新删行表体
		if(blist_pk.size()>0){
			String update_sql=" update "+btable+" set dr=1 where "+bid+
			" in ("+combinArrayToString(blist_pk.toArray())+")";
			updateBYsql(update_sql);
		}
	}
	
	/**
	 * 拼字符串，格式：'xx','xx','xx'
	 * */
    private   String combinArrayToString(Object[] pkvaluses) {
    	String str = "";
    	if(pkvaluses != null){
    		for(int i=0;i<pkvaluses.length;i++){
    			if(i == pkvaluses.length-1){
    				str += "'"+pkvaluses[i]+"'";
    			}else{
    				str += "'" +pkvaluses[i] + "','";
    			}
    		}
    		return str;
    	}else{
    		return null;
    	}
	}
    
	//保存聚合Vo
	public String insertAggregatedValueVO(AggregatedValueObject obj)throws BusinessException{
		String primaryPK=getBaseDAO().insertVO((SuperVO)obj.getParentVO());
		SuperVO[] svo=(SuperVO[])obj.getChildrenVO();
		for(int i=0;i<svo.length;i++){
//			svo[i].setPrimaryKey(primaryPK);
			svo[i].setAttributeValue(svo[i].getParentPKFieldName(), primaryPK);
		}
		getBaseDAO().insertVOArray(svo);
		return primaryPK;
	}
	
	//保存多子表聚合Vo(多子表IEXAggvo里要重写getAllChildrenVO()方法)
	public void insertIEXAggregatedValueVO(IExAggVO obj,String[] tablecode)throws BusinessException{
		String primaryPK=getBaseDAO().insertVO((SuperVO)obj.getParentVO());
		for(int k=0;k<tablecode.length;k++){
			SuperVO[] tablevo1=(SuperVO[])obj.getTableVO(tablecode[k]);
			if(tablevo1!=null){
				for(int i=0;i<tablevo1.length;i++){
					tablevo1[i].setAttributeValue(tablevo1[i].getParentPKFieldName(), primaryPK);
				}
				getBaseDAO().insertVOArray(tablevo1);	
			}
		}
	}
	
	//删除多子表聚合Vo(多子表IEXAggvo里要重写getAllChildrenVO()方法)
	public void deleteIEXAggregatedValueVO(IExAggVO obj)throws BusinessException{
		getBaseDAO().deleteVO((SuperVO)obj.getParentVO());
		SuperVO[] svo=(SuperVO[])obj.getAllChildrenVO();
		getBaseDAO().deleteVOArray(svo);	
	}
	

	//执行update语句
	public void updateBYsql(String sql)throws BusinessException{		
			 getBaseDAO().executeUpdate(sql);
	}
	
	//执行query根据不同结果集返回不同的对象
	public Object queryBySql(String tabname,SQLParameter parm,ResultSetProcessor rsp)throws BusinessException{
		return getBaseDAO().executeQuery(tabname, parm, rsp);
	}
	
	 //功能：执行SQL返回一个值
	public String queryStrBySql(String sql) throws BusinessException{
		Object obj=null;
		obj=getBaseDAO().executeQuery(sql, new ColumnProcessor());
		return obj==null?null:obj.toString();
	}
    
    /**
     * 功能:根据sql执行返回ArrayList,ArrayList里为HashMap 
     * @param sql query语句
     * @throws BusinessException
     */
	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, String>> queryArrayBySql(String sql)throws BusinessException{   	
		ArrayList<HashMap<String, String>> al=new ArrayList<HashMap<String, String>>();
		try {
			al=(ArrayList<HashMap<String, String>> ) getBaseDAO().executeQuery(sql,new MapListProcessor());
		} catch (BusinessException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		return al;       
    }
    
    
	/**
	 * 功能：由SQL和VO，查询出记录
	 * 
	 * @param sql 查询语句
	 * @param voname VO名称
	 * @return ArrayList
	 * @throws BusinessException
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public  ArrayList<SuperVO> getVOsfromSql(String sql,String voname) throws BusinessException, ClassNotFoundException{
		return (ArrayList<SuperVO>)getBaseDAO().executeQuery(sql,new BeanListProcessor(Class.forName(voname))); 
	}
	
    /**
     * @功能： 为了生成唯一标识 
     * @param pk_corp 公司主键
     * @param pkNum	  要生成的PK数量
     * @return PK数组
     */
	public String[] getPKFromDB(String pk_corp, int pkNum) {
		String[] pks = new SequenceGenerator(InvocationInfoProxy.getInstance().getUserDataSource()).generate(pk_corp, pkNum);
		return pks;
	}


	/**
	 * @return UFDate _getDate
	 * @serialData 2011-6-30
	 * @author sp
	 */
	public UFDate _getDate() {
		UFDate nowdate = new UFDate(new Date(Long.valueOf(InvocationInfoProxy.getInstance().getDate())));
		return nowdate;
	}
	
	/**
	 * @return String _getTime
	 * @serialData 2011-6-30
	 * @author sp
	 */
	public String _getTime(){
		Date nowtime = new Date();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(nowtime);
		return time;
	}
	
	/**
	 * @return String getPk_measdoc 辅单位主键
	 * @serialData 2011-6-30
	 * @author sp
	 */
	public String getPk_measdoc(String pk_invbasdoc) throws BusinessException{
		String sql = "select pk_measdoc from bd_convert where pk_invbasdoc='"+pk_invbasdoc+"'";
		return queryStrBySql(sql);
	}
	
	   /**
     * 查询value是否存在于tabName中field中,要求被查询的表中必须有dr字段
     * @param pk_plan
     * @param field 表中字段  
     * @param tabName
     * @return true被引用,false未引用
     * @throws Exception
     */
    public  boolean existValue(String tabName,String field,String value) throws BusinessException{
        try {
            return existValue(tabName,new String[]{field},new String[]{value},"AND");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException();
        }
    }
    
    /**
     * 功能: 查询value[]是否存在于tabName中field[]中,要求被查询的表中必须有dr字段
     * value数组的顺序必须与field对应
     * @param tabName 表名
     * @param field 字段名数组
     * @param value 值数组
     * @param condition 条件:and/or
     * @return boolean
     * @throws Exception
     * @return:boolean
     * @author shipeng 2011-6-3 14:05:43
     */
    public boolean existValue(String tabName,String[] field,String[] value,String condition) throws BusinessException{
        Object obj = null;
        StringBuffer sql=new StringBuffer();
        sql.append("SELECT 1 FROM ");
        sql.append(tabName);
        sql.append(" WHERE ");
        int row=value.length;
        for (int i = 0; i < row; i++) {
            sql.append(" ");
            if(i>0){
               	sql.append(" ")
               	   .append(condition)
               	   .append(" ");
               }
            sql.append(field[i])
               .append(" ='")
               .append(value[i])
               .append("'");
        }
        if(row>0){
        	sql.append(" AND ISNULL(dr,0)=0");
        }else{
        	sql.append(" ISNULL(dr,0)=0 ");
        }
        try {
        	obj=queryStrBySql(sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException();
        }     
        return !Toolkits.isEmpty(obj);
    }

    /**
     * 查询value是否存在于tabName中field中,要求被查询的表中必须有dr字段
     * @param pk_plan
     * @param field 表中字段  
     * @param tabName
     * @return true被引用,false未引用
     * @throws BusinessException
     * @author 施鹏 2011-6-3 14:04:43
     */
    public  boolean existValue(String tabName,String field,String value,String condition) throws BusinessException{
        try {
            return existValues(tabName,new String[]{field},new String[]{value},condition);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException();
        }
    }
    
    /**
     * 功能: 查询value[]是否存在于tabName中field[]中,要求被查询的表中必须有dr字段
     * value数组的顺序必须与field对应
     * @param tabName 表名
     * @param field 字段名数组
     * @param value 值数组
     * @param condition 条件:and/or
     * @return boolean
     * @throws BusinessException
     * @return:boolean
     * @author 施鹏 2011-6-3 14:04:43
     */
    public boolean existValues(String tabName,String[] field,String[] value,String condition) throws BusinessException{
        Object obj = null;
        StringBuffer sql=new StringBuffer();
        sql.append("select 1 from ");
        sql.append(tabName);
        sql.append(" where ");
        for (int i = 0; i < value.length; i++) {
            sql.append(" ");
            sql.append(field[i]);
            sql.append(" ='");
            sql.append(value[i]);
            sql.append("' ");
            if(i!=value.length-1){
            	sql.append(" and ");
            }
        }
        sql.append(condition);
        sql.append(" 1=1 and isnull(dr,0)=0 ");
        try {
        	obj=queryStrBySql(sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException();
        }     
        return !Toolkits.isEmpty(obj);
    }
    
    
	/**
	 * 通过公司主键取得对应的库存组织
	 * @param String pk_corp
	 * @return String[] 
	 * @throws BusinessException
	 * @author sp
	 * @serialData 2011-6-30
	 */
	public String[] getCalBodyByCorp(String pk_corp) throws BusinessException{
		String sql = " select pk_calbody from bd_calbody where pk_corp='"+pk_corp+"'";
		ArrayList<HashMap<String, String>> al = queryArrayBySql(sql);
		String[] pk_calbodys = null;
		if(al!=null&&al.size()>0){
			pk_calbodys = new String[al.size()];
			for(int i=0;i<al.size();i++){
				HashMap<String, String> hm = (HashMap<String, String>)al.get(i);
				pk_calbodys[i]=hm.get("pk_calbody")==null?"":hm.get("pk_calbody").toString();
			}
		}
		return pk_calbodys;
	}
	
	/**
	 * 通过用户找到用户对应的业务员与部门主键
	 * @param String cuserid
	 * @return HashMap<String, String> key：pk_deptdoc, pk_psndoc 
	 * @throws BusinessException
	 * @author sp
	 * @serialData 2011-6-30
	 */
	public HashMap<String, String> getDeptAndUser(String cuserid) throws BusinessException{
		StringBuffer sb = new StringBuffer();
		sb.append(" select distinct su.cuserid,pd.pk_deptdoc,pd.pk_psndoc  ") 
		.append(" from sm_user su left join sm_userandclerk usc on su.cuserid=usc.userid ") 
		.append(" left join bd_psndoc pd on pd.pk_psnbasdoc=usc.pk_psndoc ") 
		.append(" left join bd_psnbasdoc pbd on pbd.pk_psnbasdoc=pbd.pk_psnbasdoc ")
		.append(" where su.cuserid='"+cuserid+"'");
		ArrayList<HashMap<String, String>> al = queryArrayBySql(sb.toString());
		if(al!=null&&al.size()>0){
			return al.get(0);
		}
		return null;
	}
	
	/**
	 * 获取计量单位主键
	 * @return String measname 计量单位名称
	 * @serialData 2011-6-30
	 * @author sp
	 */
	public String getPk_measdocByName(String measname) throws BusinessException{
		String sql = "select pk_measdoc from bd_measdoc where measname='"+measname+"'";
		return queryStrBySql(sql);
	}
	
	/**
	 * 根据存货基本档案主键获取计量单位主键
	 * @return String pk_invbasdoc 存货基本档案主键
	 * @serialData 2011-7-20
	 * @author sp
	 */
	public String getPk_measdocByPk(String pk_invbasdoc) throws BusinessException{
		String sql = "select pk_measdoc from bd_invbasdoc where pk_invbasdoc='"+pk_invbasdoc+"'";
		return queryStrBySql(sql);
	}

	/**
	 * 根据客商主键获取客户基本档案主键
	 * @param String pk_cumandoc
	 * @throws BusinessException 
	 * */
	public String getPK_cubasdoc(String pk_cumandoc) throws BusinessException{
        String sql=" select pk_cubasdoc from bd_cumandoc where pk_cumandoc='"+pk_cumandoc+"'";
		return queryStrBySql(sql)==null?"":queryStrBySql(sql);
	}
	
	public String getInPartSql(String colName, String[] values) {
		if (values == null || values.length == 0) {
			return "";
		}

		StringBuffer sql = new StringBuffer();
		sql.append(" and ");
		sql.append(colName);
		sql.append(" in ('");

		for (int i = 0; i < values.length; i++) {
			sql.append(values[i]);
			sql.append("','");
		}
		sql.setLength(sql.length() - 2);
		sql.append(")");

		return sql.toString();

	}
	
	/**
	 * @param sqls
	 * @throws BusinessException
	 * 批量执行更新语句
	 */
	public void executBatchSQL(String[] sqls) throws BusinessException {
		if (Toolkits.isEmpty(sqls)) {
			return;
		}
		PersistenceManager sessionManager = null;
		try {
			sessionManager = getSessionManager();
			JdbcSession session = sessionManager.getJdbcSession();
			for (int i = 0; i < sqls.length; i++) {
				session.addBatch(sqls[i]);
			}
			// 数据库访问操作
			session.executeBatch();
		} catch (DbException e) {
			throw new BusinessException(e);
		} finally {
			if (sessionManager != null)
				sessionManager.release();// 需要关闭会话
		}
	}
	

	private  static Context getLocator() {
		return NCLocator.getInstance();
	}
	public static IPFMessage getIPFMessage(){
		try{ 
	 		return (IPFMessage) getLocator().lookup(IPFMessage.class.getName());  
	 	}catch(Exception e){ 
	 		e.printStackTrace(); 
	 		return null; 
	 	}
	}
	
	/**
	 * 多子表数据保存
	 * @author 施鹏
	 * @time 2014-03-22
	 * */
	public AggregatedValueObject saveIEXAggVO(AggregatedValueObject avo,AggregatedValueObject checkvo)throws BusinessException{
		IExAggVO backavo=(IExAggVO)avo;
		IExAggVO obj=(IExAggVO)avo;
		String primaryPK=null;
		if(obj.getParentVO().getPrimaryKey()!=null){
			updateVO(obj.getParentVO());
			primaryPK=obj.getParentVO().getPrimaryKey();
		}else{
			primaryPK=insertVObackPK(obj.getParentVO());
			avo.getParentVO().setPrimaryKey(primaryPK);
			backavo.setParentVO(avo.getParentVO());
		}
		String[]tablecodes=obj.getTableCodes();
		IExAggVO iexaggcheckvo = (IExAggVO)checkvo;
		HashMap<String, String> mapdelkey=new HashMap<String,String>();//存放界面值改变未删除的主键
		for(String tablecode:tablecodes){
			SuperVO[] bvos=(SuperVO[])obj.getTableVO(tablecode);
			SuperVO[] checkbvos=(SuperVO[])iexaggcheckvo.getTableVO(tablecode);//校样子表vo
			if(bvos!=null){
				for(SuperVO bvo:bvos){
					if(checkbvos!=null){
						for(SuperVO checkbvo:checkbvos){
							if(checkbvo.getPrimaryKey()!=null&&bvo.getPrimaryKey()!=null&&checkbvo.getPrimaryKey().equals(bvo.getPrimaryKey())){
								mapdelkey.put(checkbvo.getPrimaryKey(), checkbvo.getPrimaryKey());
							}
						}
					}
					if(bvo.getPrimaryKey()==null){
						bvo.setAttributeValue(bvo.getParentPKFieldName(), primaryPK);
						bvo.setAttributeValue("pk_corp", Toolkits.getString(obj.getParentVO().getAttributeValue("pk_corp")));
						bvo.setAttributeValue("dr", new Integer(0));
						String key=insertVObackPK(bvo);
						bvo.setPrimaryKey(key);
					}else{
						updateVO(bvo);
					}
				}
				backavo.setTableVO(tablecode, bvos);
			}
		}
		//删除修改后删行的数据
		for(String tablecode:tablecodes){
			SuperVO[] checkbvos=(SuperVO[])iexaggcheckvo.getTableVO(tablecode);//校样子表vo
			if(checkbvos!=null){
				for(SuperVO bvo:checkbvos){
					if(bvo.getPrimaryKey()!=null&&mapdelkey.get(bvo.getPrimaryKey())==null){
						deleteVO(bvo);
					}
				}
			}
		}
		return (AggregatedValueObject)backavo;
	}
	
	
	/**
	 * 主子表数据保存
	 * @author 施鹏
	 * @time 2014-04-24
	 * */
	public AggregatedValueObject saveHYBillVO(AggregatedValueObject avo,AggregatedValueObject checkvo)throws BusinessException{
		HYBillVO backavo=(HYBillVO)avo;
		HYBillVO obj=(HYBillVO)avo;
		String primaryPK=null;
		if(obj.getParentVO().getPrimaryKey()!=null){
			updateVO(obj.getParentVO());
			primaryPK=obj.getParentVO().getPrimaryKey();
		}else{
			primaryPK=insertVObackPK(obj.getParentVO());
			avo.getParentVO().setPrimaryKey(primaryPK);
			backavo.setParentVO(avo.getParentVO());
		}
		HYBillVO iexaggcheckvo = (HYBillVO)checkvo;
		HashMap<String, String> mapdelkey=new HashMap<String,String>();//存放界面值改变未删除的主键
		SuperVO[] bvos=(SuperVO[])obj.getChildrenVO();
		SuperVO[] checkbvos=(SuperVO[])iexaggcheckvo.getChildrenVO();//校样子表vo
		if(bvos!=null){
			for(SuperVO bvo:bvos){
				if(checkbvos!=null){
					for(SuperVO checkbvo:checkbvos){
						if(checkbvo.getPrimaryKey()!=null&&bvo.getPrimaryKey()!=null&&checkbvo.getPrimaryKey().equals(bvo.getPrimaryKey())){
							mapdelkey.put(checkbvo.getPrimaryKey(), checkbvo.getPrimaryKey());
						}
					}
				}
				if(bvo.getPrimaryKey()==null){
					bvo.setAttributeValue(bvo.getParentPKFieldName(), primaryPK);
					bvo.setAttributeValue("pk_corp", Toolkits.getString(obj.getParentVO().getAttributeValue("pk_corp")));
					bvo.setAttributeValue("dr", new Integer(0));
					String key=insertVObackPK(bvo);
					bvo.setPrimaryKey(key);
				}else{
					updateVO(bvo);
				}
			}
			backavo.setChildrenVO(bvos);
		}
		//删除修改后删行的数据
		if(checkbvos!=null){
			for(SuperVO bvo:checkbvos){
				if(bvo.getPrimaryKey()!=null&&mapdelkey.get(bvo.getPrimaryKey())==null){
					deleteVO(bvo);
				}
			}
		}
		return (AggregatedValueObject)backavo;
	}
	
	/**
	 * 多子表数据删除
	 * @author 施鹏
	 * @time 2014-03-22
	 * */
	public boolean delIEXAggVO(IExAggVO obj)throws BusinessException{
		boolean isDel=false;
		deleteVO(obj.getParentVO());
		String[]tablecodes=obj.getTableCodes();
		for(String tablecode:tablecodes){
			 CircularlyAccessibleValueObject[] bvos=obj.getTableVO(tablecode);
			if(bvos!=null){
               deleteVOsArr((SuperVO[])bvos);
			}
		}
		isDel=true;
		return isDel;
	}
	
	/**
	 * 获取部门档案主键
	 * @return String pk_deptdoc 部门档案主键
	 * @serialData 2014-3-24
	 * @author cm
	 */
	public String getPk_deptdocByID(String deptcode) throws BusinessException{
		String sql = "select pk_deptdoc from bd_deptdoc where deptcode = '"+deptcode+"'";
		return queryStrBySql(sql);
	}
	/**
	 * 根据仓库编码查找仓库主键
	 * @param String storcode
	 * @throws BusinessException 
	 * */
	public String getPK_stordoc(String storcode) throws BusinessException{
        String sql=" select pk_stordoc from bd_stordoc where storcode='"+storcode+"'";
		return queryStrBySql(sql)==null?"":queryStrBySql(sql);
	}
	
	/**
	 * 根据供应商编码查找供应商基本档案主键
	 * @param String custcode
	 * @throws BusinessException 
	 * */
	public String getCustcode(String custcode) throws BusinessException{
        String sql=" select pk_cubasdoc from bd_cubasdoc where custcode ='"+custcode+"'";
		return queryStrBySql(sql)==null?"":queryStrBySql(sql);
	}
	/**
	 * 根据存货编码查找存货基本档案主键
	 * @param String invcode
	 * @throws BusinessException 
	 * */
	public ArrayList<HashMap<String, String>> getPk_invmb(String invcode,String pk_corp) throws BusinessException{
        String sql=" select invm.pk_invbasdoc,invm.wholemanaflag,pk_invmandoc,invb.free1,invb.free2,invb.free3,invb.free4,invb.free5, invb.invname from bd_invmandoc invm left join bd_invbasdoc invb on invm.pk_invbasdoc = invb.pk_invbasdoc where invb.invcode = '"+invcode+"' and invm.pk_corp = '"+pk_corp+"'";
        return queryArrayBySql(sql);
	}
	
	/**
	 * 根据仓库主键查找库存组织
	 */
	public String getPk_calbody(String pk_stordoc) throws BusinessException{
        String sql=" select pk_calbody from bd_stordoc where pk_stordoc='"+pk_stordoc+"' and nvl(dr,0)=0 ";
		return queryStrBySql(sql)==null?"":queryStrBySql(sql);
	}
	
	/**
	 * 查找收发类别主键-
	 */
	public String getPk_rdcl(String rdname) throws BusinessException{
        String sql=" select pk_rdcl from bd_rdcl where rdname='"+rdname+"'";
		return queryStrBySql(sql)==null?"":queryStrBySql(sql);
	}
	
	/**
	 * 查找采购订单主表主键
	 */
	public String getPo_order(String corder_bid ) throws BusinessException{
		String sql=" select h.corderid from po_order h inner join po_order_b b on h.corderid=b.corderid where nvl(h.dr,0)=0 and nvl(b.dr,0)=0 and b.corder_bid  ='"+corder_bid.trim() +"'";
		String corderid = queryStrBySql(sql);
		return corderid==null?"":corderid;
	}
	 
	/**
	 * 查找采购订单子表主键
	 */
	public String getPo_order_b(String invcode,String corderid ) throws BusinessException{
		String sql=" select corder_bid  from po_order_b where nvl(dr,0)=0 and corderid='" + corderid + "' and   cbaseid ='"+invcode +"'";
		return queryStrBySql(sql)==null?"":queryStrBySql(sql);
	}
	
	/**
	 * 根据仓库编码查找仓库
	 */
	public String getCwarehouseid(String storcode,String pk_corp) throws BusinessException{
        String sql=" select pk_stordoc from bd_stordoc where pk_corp ='"+pk_corp+"' and storcode='"+storcode+"' and nvl(dr,0)=0  ";
		return queryStrBySql(sql)==null?"":queryStrBySql(sql);
	}
	
	/**
	 * 根据内部公司编码查找供应商基本档案主键
	 */
	public ArrayList<HashMap<String, String>> getPk_basMan(String custcode,String u8pk_corp) throws BusinessException{
        String sql=" select cum.pk_cumandoc,cum.pk_cubasdoc from bd_cubasdoc cub left join bd_cumandoc cum on cub.pk_cubasdoc = cum.pk_cubasdoc where custcode = '"+custcode+"' and nvl(cum.dr,0)=0 and nvl(cum.dr,0)=0 and custflag ='1' and cum.pk_corp = '"+u8pk_corp+"' ";
		return queryArrayBySql(sql);
	}
	
	/**
	 * 根据编码查找制单人
	 */
	public String getCuserid(String user_code) throws BusinessException{
        String sql=" select cuserid from sm_user where user_code ='"+user_code+"' and nvl(dr,0)=0 ";
		return queryStrBySql(sql)==null?"":queryStrBySql(sql);
	}
	/**
	 * 根据存货基本档案主键找换算率和辅计量单位
	 * @param String invcode
	 * @throws BusinessException 
	 * */
	public ArrayList<HashMap<String, String>> getHslJldw(String pk_invbasdoc) throws BusinessException{
        String sql=" select mainmeasrate,measname,invb.pk_measdoc1 from bd_convert co left join bd_invbasdoc invb on co.pk_invbasdoc = invb.pk_invbasdoc left join bd_measdoc me on me.pk_measdoc = invb.pk_measdoc1 where invb.assistunit='Y' and co.pk_invbasdoc = '"+pk_invbasdoc+"' ";
        return queryArrayBySql(sql);
	}
	/**
	 * 多单据多子表数据插入
	 * @author 施鹏
	 * @time 2014-04-11
	 * */
	public AggregatedValueObject[] insertIEXAggVO(AggregatedValueObject[] avos)throws BusinessException{
		IExAggVO[] tempbackavos=new IExAggVO[avos.length];
		for(int i=0;i<avos.length;i++){
			AggregatedValueObject avo=avos[i];
			tempbackavos[i]=(IExAggVO)avo;
			IExAggVO obj=(IExAggVO)avo;
			String primaryPK=insertVObackPK(obj.getParentVO());
			avo.getParentVO().setPrimaryKey(primaryPK);
			tempbackavos[i].setParentVO(avo.getParentVO());
			String[]tablecodes=obj.getTableCodes();
			for(String tablecode:tablecodes){
				SuperVO[] bvos=(SuperVO[])obj.getTableVO(tablecode);
				if(bvos!=null){
					for(SuperVO bvo:bvos){
						bvo.setAttributeValue(bvo.getParentPKFieldName(), primaryPK);
						bvo.setAttributeValue("pk_corp", Toolkits.getString(obj.getParentVO().getAttributeValue("pk_corp")));
						bvo.setAttributeValue("dr", new Integer(0));
						String key=insertVObackPK(bvo);
						bvo.setPrimaryKey(key);
					}
					tempbackavos[i].setTableVO(tablecode, bvos);
				}
			}
		}
		AggregatedValueObject []backavos=new AggregatedValueObject[avos.length];
		for(int i=0;i<backavos.length;i++){
			backavos[i]=(AggregatedValueObject)tempbackavos[i];
		}
		return backavos;
	}
	
	/**
	 * 根据名称获取档案主键
	 * @param pk_corp
	 * @param name
	 * @param flag 0:客户档案，1：部门档案,2:人员档案,3:计划年月档案,4:生产类型档案
	 * @author 施鹏
	 * @throws BusinessException 
	 * @time 2014-04-12
	 * */
	public  String getDocPkByName(String pk_corp ,String name,int flag) throws BusinessException{
		String pk=null;
		String sql="";
		if(flag==0){
			sql="select a.pk_cumandoc from bd_cumandoc a inner join bd_cubasdoc b on a.pk_cubasdoc=b.pk_cubasdoc "
		          +" where a.pk_corp='"+pk_corp+"' and nvl(a.dr,0)=0 and nvl(b.dr,0)=0 and b.custname='"+name+"' and (a.custflag='0' OR a.custflag='2') and a.sealflag is null AND (a.frozenflag='N' or a.frozenflag is null)";
		}else if(flag==1){
			sql="select pk_deptdoc from bd_deptdoc where pk_corp='"+pk_corp+"' and nvl(dr,0)=0 and deptname='"+name+"'";
		}else if(flag==2){
			sql="select pk_psndoc from bd_psndoc where nvl(dr,0)=0 and pk_corp='"+pk_corp+"' and psnname='"+name+"'";
		}else if(flag==3){
			sql="select a.pk_defdoc from bd_defdoc a inner join bd_defdoclist b on a.pk_defdoclist=b.pk_defdoclist "
				+" where nvl(a.dr,0)=0 and a.pk_corp='0001' and nvl(b.dr,0)=0 and b.doclistname='计划年月' and a.docname='"+name+"'";
		}else if(flag==4){
			sql="select pk_prodtypedoc from yj_prodtypedoc where nvl(dr,0)=0 and pk_corp='0001' and docname='"+name+"'";
		}	
		pk=queryStrBySql(sql);
		return pk;
	}
	
	/**
	 * 根据编码获取档案主键
	 * @param pk_corp
	 * @param name
	 * @param flag 0:包装类别档案,1:存活档案,2:客商档案
	 * @author 施鹏
	 * @throws BusinessException 
	 * @time 2014-04-13
	 * */
	public  String getDocPkByCode(String pk_corp ,String code,int flag) throws BusinessException{
		String pk=null;
		String sql="";
		if(flag==0){
			sql="select pk_packdoc from yj_packdoc where packcode='"+code+"' and nvl(dr,0)=0 and pk_corp='0001'";
		}
		else if(flag==1){
			sql="select a.pk_invmandoc from bd_invmandoc a inner join bd_invbasdoc b on a.pk_invbasdoc=b.pk_invbasdoc where nvl(a.dr,0)=0 and nvl(b.dr,0)=0"
				+" and a.pk_corp='"+pk_corp+"' and b.invcode='"+code+"'";
		}else if(flag==2){
			sql="select a.pk_cumandoc from bd_cumandoc a inner join bd_cubasdoc b on a.pk_cubasdoc=b.pk_cubasdoc "
		          +" where a.pk_corp='"+pk_corp+"' and nvl(a.dr,0)=0 and nvl(b.dr,0)=0 and b.custcode='"+code+"' and (a.custflag='0' OR a.custflag='2') and a.sealflag is null AND (a.frozenflag='N' or a.frozenflag is null)";
		}
		pk=queryStrBySql(sql);
		return pk;
	}
	
	/**
	 * 审批流提交方法
	 * @author 施鹏
	 * @time 2014-04-24
	 * **/
	public boolean commitBill(AggregatedValueObject vo)throws BusinessException{
		updateVO(vo.getParentVO());
		return true;
	}
	
	/**
	 * 审批流审核方法
	 * @author 施鹏
	 * @time 2014-04-24
	 * **/
	public boolean approveBill(AggregatedValueObject vo)throws BusinessException{
		updateVO(vo.getParentVO());
		return true;
	}
	/**
	 * 转换客商管理档案主键
	 * @param pk_corp
	 * @param name
	 * @author
	 * @throws BusinessException 
	 * @time 2014-04-13
	 * */
	public  String getCusPkByCusPk(String pk_corp ,String inpk_cumandoc ) throws BusinessException{
		String outpk_cumandoc=null;
		String sql="";
		sql="select a.pk_cumandoc  from bd_cumandoc a where a.custflag='0' and a.pk_corp='"+pk_corp+"' and  a.pk_cubasdoc = "+
		"(select b.pk_cubasdoc  from bd_cumandoc b where b.custflag='0' and nvl(b.dr,0)=0 and b.pk_cumandoc='"+inpk_cumandoc+"')";
		outpk_cumandoc=queryStrBySql(sql);
		return outpk_cumandoc;
	}
	/**
	 * 转换存货管理档案主键
	 * @param pk_corp
	 * @param name
	 * @author
	 * @throws BusinessException 
	 * @time 2014-04-13
	 * */
	public  String getInvPkByInvPk(String pk_corp ,String inpk_invmandoc ) throws BusinessException{
		String outpk_invmandoc=null;
		String sql="";
		sql="select a.pk_invmandoc  from bd_invmandoc a where a.pk_corp='"+pk_corp+"' and  a.pk_invbasdoc = "+
		"(select b.pk_invbasdoc  from bd_invmandoc b where  nvl(b.dr,0)=0 and b.pk_invmandoc='"+inpk_invmandoc+"')";
		outpk_invmandoc=queryStrBySql(sql);
		return outpk_invmandoc;
	}
}
	
