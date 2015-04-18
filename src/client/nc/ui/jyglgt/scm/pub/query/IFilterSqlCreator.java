package nc.ui.jyglgt.scm.pub.query;

/**
 * 新版查询模板中注册某个查询条件 sql 语句的创建器。
 * <p>新版查询模板中，如果某个查询条件的sql语句比较复杂，无法用默认的规则创建，
 * 则可以注册使用此 sql 创建接口，使用方法如下：
 * <pre>
 * qryDlg.regsterCustomSqlCreator("bd_purorg.mobile", new IFilterSqlCreator(){
 *     public String getSql(String value) {
 *         return "realColumnname = bd_purorg.mobile and bd_purorg.mobile = " + value;
 *     }
 * });
 * </pre>
 * 上述代码实现该功能：fieldcode为bd_purorg.mobile的查询条件最终得到的sql语句为
 * <pre>realColumnname = bd_purorg.mobile and bd_purorg.mobile = 'value'。</pre>
 * 
 * @author 蒲强华
 * @since 2008-11-5
 */
public interface IFilterSqlCreator {
	/**
	 * 根据字段的值返回 sql 片段。
	 * <p><b>注意：只是一个字段的 sql 片段，不能在片段之前包含 "and" 或 "or" 等的链接字符</b>
	 * 
	 * @param value 查询条件的值
	 * @return 生成的当前查询字段的 sql 片段
	 */
	String getSql(String value);
}
