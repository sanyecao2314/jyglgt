package nc.vo.jyglgt.pub.Toolkits;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nc.bs.pub.billcodemanage.BillcodeGenerater;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.billcodemanage.BillCodeObjValueVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;


/**
 * 说明:前后台公用工具类
 * @author 公共开发者
 * 2014-9-14  13:43:40
 */
public class Toolkits {
	
    /**
     * 功能:
     * 检查传入的参数是否为空。
     * 
     * 如果value的类型为String，并且value.length()为0，返回true。
     * 如果value的类型为Object[]，并且value.length为0，返回true。
     * 如果value的类型为Collection，并且value.size()为0，返回true。
     * 如果value的类型为Dictionary，并且value.size()为0，返回true。 
     * 如果value的类型为Map，并且value.size()为0，返回true。 
     * 否则返回false。
     * @param value 被检查值。
     * @return
     * @return boolean 如果被检查值为null，返回true。 
     */
    @SuppressWarnings("unchecked")
	public static boolean isEmpty(Object value) {
        if (value == null){
            return true;
        }
        if ((value instanceof String) && (((String) value).trim().length() <= 0)){
            return true;
        }
        if ((value instanceof Object[]) && (((Object[]) value).length <= 0)) {
            return true;
        }
        //判断数组中的值是否全部为空null.
        if (value instanceof Object[]) {
            Object[] t = (Object[]) value;
            for (int i = 0; i < t.length; i++) {
                if (t[i] != null) {
                    return false;
                }
            }
            return true;
        }
        if ((value instanceof Collection) && ((Collection) value).size() <= 0){
            return true;
        }
        if ((value instanceof Dictionary) && ((Dictionary) value).size() <= 0){
            return true;
        }
        if ((value instanceof Map) && ((Map) value).size() <= 0){
            return true;
        }
        return false;
    }
    /**
     * 功能:转换int数组为List 
     * @param iarray
     * @return
     * @return:List
     */
    @SuppressWarnings("unchecked")
	public static LinkedList toList(int [] iarray){
        LinkedList<Integer> list=new LinkedList<Integer>();
        for (int i = 0; i < iarray.length; i++) {
            list.add(new Integer(iarray[i]));
        }
        return list;
    }
    

	public static UFDouble getUfdoubleNRound(UFDouble value,int n){
		value = new UFDouble(value.doubleValue(),n);
		return value;
	}
    
    /**
     * 功能: 转换List为int数组
     * @param list
     * @return
     * @return:int[]
     */
    @SuppressWarnings("unchecked")
	public static int[] toArray(List list){
        Object[] arr1=list.toArray();
        int[] arr2=new int[list.size()];
        for (int i = 0; i < arr1.length; i++) {
            arr2[i] = ((Integer)arr1[i]).intValue();
        }
        return arr2;
    }
    
    
    /**
     * 将一个String数组变成带括号字符,便于带in条件的SQL语句使用
     * 例:('111','222','333','')
     * @param array
     * @return
     */
    public static String combinArrayToString(String[] array){
        if(isEmpty(array)){
            return "('')";
        }
        StringBuffer str=new StringBuffer("('");
        for (int i = 0; i < array.length; i++) {
            str.append(array[i]);
            str.append("','");
        }
        str.append("')");
        return str.toString();
    }
    
    /**
     * 将一个String数组变成带括号字符,便于带in条件的SQL语句使用
     * 例:'111','222','333',''
     * @param array
     * @return
     */
    public static String combinArrayToString2(String[] array){
      
        StringBuffer str=new StringBuffer("");
        for (int i = 0; i < array.length; i++) {
        	str.append("'");
        	str.append(array[i]);
            str.append("',");
        }
        str.append("''");
        return str.toString();
    }
    
    public static  String combinArrayToString3(String[] pkvaluses) {
    	String str = "'";
    	if(pkvaluses != null){
    		for(int i=0;i<pkvaluses.length;i++){
    			if(i == pkvaluses.length-1){
    				str += pkvaluses[i]+"'";
    			}else{
    				str += pkvaluses[i] + "','";
    			}
    		}
    		return str;
    	}else{
    		return null;
    	}
	}

    
    /**
     * 功能: 替换字符
     * @param str_par 原始字符串
     * @param old_item 被替换的字符串
     * @param new_item 替换的字符串
     * @return
     * @return:String
     */
    public static synchronized String replaceAll(String str_par, String old_item, String new_item)
    {
        String str_return = "";
        String str_remain = str_par;
        boolean bo_1 = true;
        while (bo_1)
        {
            int li_pos = str_remain.indexOf(old_item);
            if (li_pos < 0)
            {
                break;
            } // 如果找不到,则返回
            String str_prefix = str_remain.substring(0, li_pos);
            str_return = str_return + str_prefix + new_item; // 将结果字符串加上原来前辍
            str_remain = str_remain.substring(li_pos + old_item.length(), str_remain.length());
        }
        str_return = str_return + str_remain; // 将剩余的加上
        return str_return;
    }
    
    
	  /**
	   * 
	  * @Title: cutZero 
	  * @Description: 去掉小数后面的0
	  * @return String    返回类型 
	  * @throws
	   */
	 public static String cutZero(String v){
		   if(v.indexOf(".") > -1){
		   while(true)
		   {
		      if(v.lastIndexOf("0") == (v.length() - 1)){
		      v = v.substring(0,v.lastIndexOf("0"));
		      }else{
		   break;
		      }
		   }
		   if(v.lastIndexOf(".") == (v.length() - 1)){
		   v = v.substring(0, v.lastIndexOf("."));
		   }
		   }
		   return v;
		   }
	 
    /**
     * 合计
     * @param value
     * @return
     */
    public static double total(double [] value){
        double j=0;
        for (int i = 0; i < value.length; i++) {
            j=j+value[i];
        }
        return j;
    }
    
    

    /**
     * 功能: 比较两个字符串的大小,返回true表示后面比前面大
     * @param String sa,String sb要比较的两个字符串
     * @return boolean
     */
    public static boolean compareStr(String sa,String sb){
        boolean flag=false;
        int i=sa.compareTo(sb);
        if (i<0){
            flag = true;
            return flag;
        }
        return flag;
    }
    
    /**
     * 功能: 比较两个数值的大小,返回true表示后面比前面大
     * @param String sa,String sb要比较的两个字符串
     * @return boolean
     */
    public static boolean compareDou(UFDouble sa,UFDouble sb){
        boolean flag=false;
        int i=sa.compareTo(sb);
        if (i<0){
            flag = true;
            return flag;
        }
        return flag;
    }
    
	/**
	 * <H3>方法作用</H3>获取单据号<BR>
	 * 
	 * @param billTypecode
	 * @param pk_corp
	 * @param customBillCode
	 * @param vo
	 * @return
	 * @throws BusinessException
	 */
	public static String getBillNO(String billTypecode,String pk_corp,String customBillCode,BillCodeObjValueVO vo) throws BusinessException{
		return new BillcodeGenerater ().getBillCode(billTypecode,pk_corp,customBillCode,vo);
	}
	
	//把"1"转化成1时用下标找TOBIG[1]就是对应的
    private static final String[] TOBIG = new String[] { "零", "壹", "贰", "叁",
        "肆", "伍", "陆", "柒", "捌", "玖" };
    // 这里是单位从低到高的排列
    private static final String POS[] = new String[] { "", "拾", "佰", "仟", "万",
        "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿" };
	/**
	 * 将数字转换成大写
	 * @param number java.lang.string
	 * @autor shipeng
	 * @return java.lang.String
	 */
	public static String getCapital(String number){  
		String capital;
    	int len_zs=number.indexOf(".");               //整数
    	if(len_zs<0){
    		capital = getCapitalOfInt(number)+"整";//.concat("整");
    	}else{
    	int len_xs=number.substring(len_zs).length(); //小数
         capital = getCapitalOfInt(number.substring(0,len_zs)).concat("点").concat(getCapitalOfFloat(number.substring(len_zs+1,len_zs+len_xs)));
    	}
    	return capital;
	}
	
	
 	
	/**
	 * 将整数转换成大写
	 * @author shipeng
     * @return java.lang.String
	 * */
    public static String getCapitalOfInt(String str)
    {
       String newStr ="";
       for (int i = 0, j = str.length(); i < j; i++)
       {
        String s = str.substring(j - i - 1, j - i);
        newStr = TOBIG[Integer.parseInt(s)].concat(POS[i])+newStr;
       }
       newStr = newStr.replace("零仟", "零");
       newStr = newStr.replace("零佰", "零");
       newStr = newStr.replace("零拾", "零");
       newStr = newStr.replace("零万", "万");
       for(int i= 0;i<8;i++)
        newStr = newStr.replace("零零", "零");
       newStr = newStr.replace("零仟", "仟");
       newStr = newStr.replace("零佰", "佰");
       newStr = newStr.replace("零拾", "拾");
       newStr = newStr.replace("零万", "万");
       newStr = newStr.replace("零亿", "亿");
       if(newStr.endsWith("零"))
        newStr = newStr.substring(0,newStr.length()-1);
       return newStr;
    }
    
    /**
     * 将小数转换成大写
     * @author shipeng
     * @return java.lang.String
     * */
    public static String getCapitalOfFloat(String str)
    {
       String newStr ="";
       for (int i = 0, j = str.length(); i < j; i++)
       {
        String s = str.substring(j - i - 1, j - i);
        newStr = TOBIG[Integer.parseInt(s)]+newStr;
       }
       return newStr;
    }
    
	  public static UFDate getUFDate(Object obj){
		  if(!Toolkits.isEmpty(obj)){
			  try{
				  return new UFDate(obj.toString().trim());
			  }catch(Exception e){
				  return null;
			  }
		  }else{
			  return null;
		  }
	  }
	  public static UFDouble getUFDouble(Object obj){
		  if(!Toolkits.isEmpty(obj)){
			  try{
				  return new UFDouble(obj.toString().trim());
			  }catch(Exception e){
				  return new UFDouble(0);
			  }
		  }else{
			  return new UFDouble(0);
		  }
	  }
	  public  static UFDouble getUFDouble(String obj){
		  if(!Toolkits.isEmpty(obj)){
			  try{
				  return new UFDouble(obj.trim());
			  }catch(Exception e){
				  return new UFDouble(0);
			  }
		  }else{
			  return new UFDouble(0);
		  }
	  }
	  
	  public  static UFDouble getUFDouble(UFDouble ufd){
		  if(!Toolkits.isEmpty(ufd)){
			  try{
				  return ufd;
			  }catch(Exception e){
				  return new UFDouble(0);
			  }
		  }else{
			  return new UFDouble(0);
		  }
	  }
	  
	  public  static Integer getInteger(Object obj){
		  if(!Toolkits.isEmpty(obj)){
			  try{
				  return Integer.parseInt(obj.toString().trim());
			  }catch(Exception e){
				  return new Integer(0);
			  }
		  }else{
			  return new Integer(0);
		  }
	  }
	  public static Integer getInteger(String obj){
		  if(!Toolkits.isEmpty(obj)){
			  try{
				  return Integer.parseInt(obj.trim());
			  }catch(Exception e){
				  return new Integer(0);
			  }
		  }else{
			  return new Integer(0);
		  }
	  }
	  
	  public static String getString(String obj){
		  if(!Toolkits.isEmpty(obj)){
			  try{
				  return obj==null?"":obj.trim();
			  }catch(Exception e){
				  return "";
			  }
		  }else{
			  return "";
		  }
	  }
	  public static String getString(Object obj){
		  if(!Toolkits.isEmpty(obj)){
			  try{
				  return obj==null?"":obj.toString().trim();
			  }catch(Exception e){
				  return "";
			  }
		  }else{
			  return "";
		  }
	  }

	  
	  /**
		 * 货款结算参照名称编码对应关系
		 * */
		public static HashMap<String, String> getStoreGoodMap(){
			HashMap<String,String> map=new HashMap<String,String>();
			String[] names = {"开始期间","结束期间"};
		    String[] codes = {"b.poundcode","b.gatheringcode"};
		    for(int i=0;i<names.length;i++){
		    	map.put(names[i], codes[i]);
		    }
			return map;
		}
		
		
	/**
	 * @param vos
	 * @param idfield
	 * @param textfield
	 * @param fatheridname
	 * @return
	 * 转换成NODE结点的格式
	 * GDT
	 */
	public static List<Map<String, String>> getListTreeData(SuperVO[] vos, String idfield,
			String textfield, String fatheridname) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		if(isEmpty(vos)||isEmpty(idfield)||isEmpty(textfield)){
			return null;
		}
		for (int i = 0; i < vos.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			String id = getString(vos[i].getAttributeValue(idfield));
			String[] texts = textfield.split("[+]");
			String text = "";
			for (int j = 0; j < texts.length; j++) {
				text = text + getString(vos[i].getAttributeValue(texts[j]))+"  ";
			}
			String fatherid = getString(vos[i].getAttributeValue(fatheridname));
			map.put("id", id);  
			map.put("text", text);  
			map.put("parentId", fatherid);  
			list.add(map);
		}
		return list;
	}
	
	/**
	 * @param year 传入的年
	 * @param month 传入的月
	 * @param 月步长
	 * @return 根据传入的年月,返回下一个月的年跟月
	 * 注：返回值int数组,长度定长2位,第一位年，第二位月,月份没有补零,需调用者补零
	 */
	public static int[] getNextNMonth(int year, int month, int step) {
		if(step <= 0){
			return new int[] { year, month };
		}
		int nextmonth = month + 1;
		if (nextmonth > 12) {
			nextmonth = nextmonth - 12;
			year = year + 1;
		}
		if (step > 1) {
			int[] result = new int[2];
			result = getNextNMonth(year, nextmonth, step - 1);
			year = result[0];
			nextmonth = result[1];
		}
		return new int[] { year, nextmonth };
	}
			
		
		/**
		 * 获取质检报表分析的超降基数值
		 * @param factvalue 实际值
		 * @param contrastvalue 对比值
		 * @author 施鹏
		 * @time 2014-04-09
		 * */
		public static UFDouble getSuperdownbs(UFDouble factvalue,UFDouble contrastvalue){
            UFDouble superdownbs=new UFDouble(0);
            if(factvalue==null){
            	factvalue=new UFDouble(0);
            }
            if(contrastvalue==null){
            	contrastvalue=new UFDouble(0);
            }
            superdownbs=factvalue.sub(contrastvalue);
			return superdownbs;
		}
			

		//取字符串中的数字，包括小数点
		public static String getBaseNum(String str){
			str=str.trim();  
			StringBuffer sb=new StringBuffer("");  
			if(str != null && !"".equals(str)){  
				for(int i=0;i<str.length();i++){  
					if(str.charAt(i)>=48 && str.charAt(i)<=57||str.charAt(i)==46){  
						sb.append(str.charAt(i));  
					}  
				}  
			}  
			return sb.toString();
		}
		
		/**
		 * cm
		 * @param actualscore//超降基数
		 * @param bucklecap //奖扣封顶
		 * @return
		 */
		public static UFDouble getBuckleprize(UFDouble actualscore,UFDouble bucklecap){
//			bucklecap//奖扣封顶
//			actualscore//超降基数
//			actualscore=new UFDouble(5);
//			bucklecap = new UFDouble(3);
			UFDouble buckleprize=new UFDouble(0);
			if(actualscore==null){
	        	actualscore=new UFDouble(0);
	        }
	        //取整
			if(actualscore.doubleValue()>=0){
				buckleprize=new UFDouble(Math.floor(actualscore.doubleValue()));
			}else if(actualscore.doubleValue()<0){
				buckleprize=new UFDouble(0).sub(new UFDouble(Math.floor(actualscore.abs().doubleValue())));
			}
	        //奖扣封顶
	        if(buckleprize.doubleValue()<0&&buckleprize.abs().sub(bucklecap.abs()).doubleValue()>0){
	        	buckleprize=new UFDouble(0).sub(bucklecap);
	        }else if(buckleprize.doubleValue()>=0&&buckleprize.abs().sub(bucklecap.abs()).doubleValue()>0){
	        	buckleprize=bucklecap;
	        }
			return buckleprize;
		}
		
		/**
		 * cm
		 * 粘口
		 * 0.2奖扣1%
		 * @param actualscore//超降基数
		 * @param bucklecap //奖扣封顶
		 * @return
		 */
		public static UFDouble getBuckleprize_zk(UFDouble actualscore,UFDouble bucklecap){
//			bucklecap//奖扣封顶
//			actualscore//超降基数
//			actualscore=new UFDouble(5);
//			bucklecap = new UFDouble(3);
			UFDouble buckleprize=new UFDouble(0);
			if(actualscore==null){
	        	actualscore=new UFDouble(0);
	        }
	        //取整
			if(actualscore.doubleValue()>=0){
				buckleprize=new UFDouble(Math.floor(actualscore.div(0.2).doubleValue()));
			}else if(actualscore.doubleValue()<0){
				buckleprize=new UFDouble(0).sub(new UFDouble(Math.floor(actualscore.abs().div(0.2).doubleValue())));
			}
	        //奖扣封顶
	        if(buckleprize.doubleValue()<0&&buckleprize.abs().sub(bucklecap.abs()).doubleValue()>0){
	        	buckleprize=new UFDouble(0).sub(bucklecap);
	        }else if(buckleprize.doubleValue()>=0&&buckleprize.abs().sub(bucklecap.abs()).doubleValue()>0){
	        	buckleprize=bucklecap;
	        }
			return buckleprize;
		}
		
		/**
		 * cm
		 * 整包
		 * 0.5奖扣1%
		 * @param actualscore//超降基数
		 * @param bucklecap //奖扣封顶
		 * @return
		 */
		public static UFDouble getBuckleprize_zb(UFDouble actualscore,UFDouble bucklecap){
//			bucklecap//奖扣封顶
//			actualscore//超降基数
//			actualscore=new UFDouble(5);
//			bucklecap = new UFDouble(3);
			UFDouble buckleprize=new UFDouble(0);
			if(actualscore==null){
	        	actualscore=new UFDouble(0);
	        }
	        //取整
			if(actualscore.doubleValue()>=0){
				buckleprize=new UFDouble(Math.floor(actualscore.div(0.5).doubleValue()));
			}else if(actualscore.doubleValue()<0){
				buckleprize=new UFDouble(0).sub(new UFDouble(Math.floor(actualscore.abs().div(0.5).doubleValue())));
			}
	        //奖扣封顶
	        if(buckleprize.doubleValue()<0&&buckleprize.abs().sub(bucklecap.abs()).doubleValue()>0){
	        	buckleprize=new UFDouble(0).sub(bucklecap);
	        }else if(buckleprize.doubleValue()>=0&&buckleprize.abs().sub(bucklecap.abs()).doubleValue()>0){
	        	buckleprize=bucklecap;
	        }
			return buckleprize;
		}
		/**
		 * 字符转换成JSON格式
		 * @author 施鹏
		 * @time 2014-04-28
		 * */
		 public static String StringToJSON(ArrayList<HashMap<String, String>> list){
			 StringBuffer JSONString=new StringBuffer();
	         if(list!=null&&list.size()>=0){
	        	 JSONString.append("[");
	         }
			 int count1=0;
			 for(HashMap<String, String> map:list){
				 Collection col=map.keySet();
				 Iterator ite=col.iterator();
				 int count2=0;
				 while(ite.hasNext()){
					 Object key=ite.next();	
					 if(count2>0){
						 JSONString.append(",");
					 }else{
						 if(count1>0){
							 JSONString.append(",{");
						 }else{
							 JSONString.append("{");
						 }
					 }
					 JSONString.append("\""+key+"\":"+"\""+map.get(key)+"\"");
					 count2++;
				 }
				 count1++;
				 if(count1>0){
					 JSONString.append("}");
				 }
			 }
	         if(list!=null&&list.size()>=0){
	        	 JSONString.append("]");
	         }
			 return JSONString.toString();
		 }
		 
	/**
	 * JSON转换成字符格式
	 * @author 陈明
	 * @time 2014-04-29
	 * */
//	 public static ArrayList<HashMap<String, Object>> JSONToList(String json){
//		 String[] jsons = json.substring(2, json.length()-2).replace("\"", "").split("\\},\\{");				 
//		 ArrayList<HashMap<String, Object>> listmap = new ArrayList<HashMap<String,Object>>();
//		 for (int i = 0; i < jsons.length; i++) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			String[] json1 = jsons[i].split(",");
//			for (int j = 0; j < json1.length; j++) {
//				String[] jsonmaps = json1[j].split(":");
//				map.put(jsonmaps[0], jsonmaps[1]);
//			}
//			listmap.add(map);
//		}
//		return listmap;
//	}
	 /**
	 * JSON转换成字符格式
	 * @author 陈明
	 * @time 2014-06-3
	 * */
	 public static ArrayList<HashMap<String, Object>> JSONToList(String json){
		 	 
		 ArrayList<HashMap<String, Object>> listmap = new ArrayList<HashMap<String,Object>>();
		 if(json.length()==2) return listmap;
		 String[] jsons = json.substring(2, json.length()-2).split("\\},\\{");
		 
		 for (int i = 0; i < jsons.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			String[] json1 = jsons[i].split("\",\"");
			for (int j = 0; j < json1.length; j++) {
				String[] jsonmaps = json1[j].replace("\"", "").split(":");
				if(jsonmaps.length==1){
					map.put(jsonmaps[0], "");
					System.out.println("null");
				}else{
					StringBuffer js = new StringBuffer("");
					for (int k = 1; k < jsonmaps.length; k++) {
						if(k==jsonmaps.length-1){
							js.append(jsonmaps[k]);
						}else{
							js.append(jsonmaps[k]+":");
						}
					}
					map.put(jsonmaps[0], js.toString());	
					System.out.println(jsonmaps[0]+":"+js.toString());
				}
			}
			listmap.add(map);
		}
		return listmap;
	}
	 
	 /**
	  * 时间加几个小时
	  * cm
	  * @param day
	  * @param x
	  * @return
	  */
	 public static String addDate(String day, int x)
	    {
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
	        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//12小时制
	        Date date = null;
	        try
	        {
	            date = format.parse(day);
	        }
	        catch (Exception ex)   
	        {
	            ex.printStackTrace();
	        }
	        if (date == null) return "";
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.add(Calendar.HOUR_OF_DAY, x);//24小时制
	        //cal.add(Calendar.HOUR, x);12小时制
	        date = cal.getTime();
	        System.out.println("front:" + date);
	        cal = null;
	        return format.format(date);
	    }
}
