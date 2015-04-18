package nc.vo.jyglgt.pub.Toolkits;



public class DataType {

	/** 字段名称常量 */
	public final static int TYPE_CHAR = 1;

	public final static int TYPE_VARCHAR2 = 12;

	public final static int TYPE_SMALLINT = 5;

	public final static int TYPE_INTENGER = 4;

	public final static int TYPE_NUMBER = 2;
	
	/**
	 * @param datatype
	 * @param length
	 * @return
	 * VO字段对应的类型
	 */
	public static String getNCVOType(int datatype,int length){
		if(datatype == TYPE_CHAR&&length == 19){
			return "UFDateTime";
		}else if(datatype == TYPE_CHAR&&length == 10){
			return "UFDate";
		}else if(datatype == TYPE_CHAR&&length == 1){
			return "UFBoolean";
		}
		switch (datatype) {
		case TYPE_CHAR:
		case TYPE_VARCHAR2:
			return "String";
		case TYPE_SMALLINT:
		case TYPE_INTENGER:
			return "Integer";
		case TYPE_NUMBER:
			return "UFDouble";
		default:
			return "String";
		}
	}
	
	/**
	 * @param datatype
	 * @param length
	 * 返回单据模板里对应的类型
	 */
	public static Integer getTempletType(int datatype,int length,String itemkey){
		if(itemkey.equals("ts")||(datatype == TYPE_CHAR&&length == 19)){
			return 15;
		}
		if(datatype == TYPE_CHAR&&length == 10){
			return 3;//日期类控件
		}
		if(datatype == TYPE_CHAR&&length == 1){
			return 4;//逻辑类控件
		}
		switch (datatype) {
		case TYPE_CHAR:
		case TYPE_VARCHAR2:
			return 0;//普通字符类
		case TYPE_SMALLINT:
		case TYPE_INTENGER:
			return 1;//整数
		case TYPE_NUMBER:
			return 2;//小数
		default:
			return 0;
		}
	}

	
	
}
