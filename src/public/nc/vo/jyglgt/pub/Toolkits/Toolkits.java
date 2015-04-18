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
 * ˵��:ǰ��̨���ù�����
 * @author ����������
 * 2014-9-14  13:43:40
 */
public class Toolkits {
	
    /**
     * ����:
     * ��鴫��Ĳ����Ƿ�Ϊ�ա�
     * 
     * ���value������ΪString������value.length()Ϊ0������true��
     * ���value������ΪObject[]������value.lengthΪ0������true��
     * ���value������ΪCollection������value.size()Ϊ0������true��
     * ���value������ΪDictionary������value.size()Ϊ0������true�� 
     * ���value������ΪMap������value.size()Ϊ0������true�� 
     * ���򷵻�false��
     * @param value �����ֵ��
     * @return
     * @return boolean ��������ֵΪnull������true�� 
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
        //�ж������е�ֵ�Ƿ�ȫ��Ϊ��null.
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
     * ����:ת��int����ΪList 
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
     * ����: ת��ListΪint����
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
     * ��һ��String�����ɴ������ַ�,���ڴ�in������SQL���ʹ��
     * ��:('111','222','333','')
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
     * ��һ��String�����ɴ������ַ�,���ڴ�in������SQL���ʹ��
     * ��:'111','222','333',''
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
     * ����: �滻�ַ�
     * @param str_par ԭʼ�ַ���
     * @param old_item ���滻���ַ���
     * @param new_item �滻���ַ���
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
            } // ����Ҳ���,�򷵻�
            String str_prefix = str_remain.substring(0, li_pos);
            str_return = str_return + str_prefix + new_item; // ������ַ�������ԭ��ǰ�
            str_remain = str_remain.substring(li_pos + old_item.length(), str_remain.length());
        }
        str_return = str_return + str_remain; // ��ʣ��ļ���
        return str_return;
    }
    
    
	  /**
	   * 
	  * @Title: cutZero 
	  * @Description: ȥ��С�������0
	  * @return String    �������� 
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
     * �ϼ�
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
     * ����: �Ƚ������ַ����Ĵ�С,����true��ʾ�����ǰ���
     * @param String sa,String sbҪ�Ƚϵ������ַ���
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
     * ����: �Ƚ�������ֵ�Ĵ�С,����true��ʾ�����ǰ���
     * @param String sa,String sbҪ�Ƚϵ������ַ���
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
	 * <H3>��������</H3>��ȡ���ݺ�<BR>
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
	
	//��"1"ת����1ʱ���±���TOBIG[1]���Ƕ�Ӧ��
    private static final String[] TOBIG = new String[] { "��", "Ҽ", "��", "��",
        "��", "��", "½", "��", "��", "��" };
    // �����ǵ�λ�ӵ͵��ߵ�����
    private static final String POS[] = new String[] { "", "ʰ", "��", "Ǫ", "��",
        "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��" };
	/**
	 * ������ת���ɴ�д
	 * @param number java.lang.string
	 * @autor shipeng
	 * @return java.lang.String
	 */
	public static String getCapital(String number){  
		String capital;
    	int len_zs=number.indexOf(".");               //����
    	if(len_zs<0){
    		capital = getCapitalOfInt(number)+"��";//.concat("��");
    	}else{
    	int len_xs=number.substring(len_zs).length(); //С��
         capital = getCapitalOfInt(number.substring(0,len_zs)).concat("��").concat(getCapitalOfFloat(number.substring(len_zs+1,len_zs+len_xs)));
    	}
    	return capital;
	}
	
	
 	
	/**
	 * ������ת���ɴ�д
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
       newStr = newStr.replace("��Ǫ", "��");
       newStr = newStr.replace("���", "��");
       newStr = newStr.replace("��ʰ", "��");
       newStr = newStr.replace("����", "��");
       for(int i= 0;i<8;i++)
        newStr = newStr.replace("����", "��");
       newStr = newStr.replace("��Ǫ", "Ǫ");
       newStr = newStr.replace("���", "��");
       newStr = newStr.replace("��ʰ", "ʰ");
       newStr = newStr.replace("����", "��");
       newStr = newStr.replace("����", "��");
       if(newStr.endsWith("��"))
        newStr = newStr.substring(0,newStr.length()-1);
       return newStr;
    }
    
    /**
     * ��С��ת���ɴ�д
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
		 * �������������Ʊ����Ӧ��ϵ
		 * */
		public static HashMap<String, String> getStoreGoodMap(){
			HashMap<String,String> map=new HashMap<String,String>();
			String[] names = {"��ʼ�ڼ�","�����ڼ�"};
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
	 * ת����NODE���ĸ�ʽ
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
	 * @param year �������
	 * @param month �������
	 * @param �²���
	 * @return ���ݴ��������,������һ���µ������
	 * ע������ֵint����,���ȶ���2λ,��һλ�꣬�ڶ�λ��,�·�û�в���,������߲���
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
		 * ��ȡ�ʼ챨�������ĳ�������ֵ
		 * @param factvalue ʵ��ֵ
		 * @param contrastvalue �Ա�ֵ
		 * @author ʩ��
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
			

		//ȡ�ַ����е����֣�����С����
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
		 * @param actualscore//��������
		 * @param bucklecap //���۷ⶥ
		 * @return
		 */
		public static UFDouble getBuckleprize(UFDouble actualscore,UFDouble bucklecap){
//			bucklecap//���۷ⶥ
//			actualscore//��������
//			actualscore=new UFDouble(5);
//			bucklecap = new UFDouble(3);
			UFDouble buckleprize=new UFDouble(0);
			if(actualscore==null){
	        	actualscore=new UFDouble(0);
	        }
	        //ȡ��
			if(actualscore.doubleValue()>=0){
				buckleprize=new UFDouble(Math.floor(actualscore.doubleValue()));
			}else if(actualscore.doubleValue()<0){
				buckleprize=new UFDouble(0).sub(new UFDouble(Math.floor(actualscore.abs().doubleValue())));
			}
	        //���۷ⶥ
	        if(buckleprize.doubleValue()<0&&buckleprize.abs().sub(bucklecap.abs()).doubleValue()>0){
	        	buckleprize=new UFDouble(0).sub(bucklecap);
	        }else if(buckleprize.doubleValue()>=0&&buckleprize.abs().sub(bucklecap.abs()).doubleValue()>0){
	        	buckleprize=bucklecap;
	        }
			return buckleprize;
		}
		
		/**
		 * cm
		 * ճ��
		 * 0.2����1%
		 * @param actualscore//��������
		 * @param bucklecap //���۷ⶥ
		 * @return
		 */
		public static UFDouble getBuckleprize_zk(UFDouble actualscore,UFDouble bucklecap){
//			bucklecap//���۷ⶥ
//			actualscore//��������
//			actualscore=new UFDouble(5);
//			bucklecap = new UFDouble(3);
			UFDouble buckleprize=new UFDouble(0);
			if(actualscore==null){
	        	actualscore=new UFDouble(0);
	        }
	        //ȡ��
			if(actualscore.doubleValue()>=0){
				buckleprize=new UFDouble(Math.floor(actualscore.div(0.2).doubleValue()));
			}else if(actualscore.doubleValue()<0){
				buckleprize=new UFDouble(0).sub(new UFDouble(Math.floor(actualscore.abs().div(0.2).doubleValue())));
			}
	        //���۷ⶥ
	        if(buckleprize.doubleValue()<0&&buckleprize.abs().sub(bucklecap.abs()).doubleValue()>0){
	        	buckleprize=new UFDouble(0).sub(bucklecap);
	        }else if(buckleprize.doubleValue()>=0&&buckleprize.abs().sub(bucklecap.abs()).doubleValue()>0){
	        	buckleprize=bucklecap;
	        }
			return buckleprize;
		}
		
		/**
		 * cm
		 * ����
		 * 0.5����1%
		 * @param actualscore//��������
		 * @param bucklecap //���۷ⶥ
		 * @return
		 */
		public static UFDouble getBuckleprize_zb(UFDouble actualscore,UFDouble bucklecap){
//			bucklecap//���۷ⶥ
//			actualscore//��������
//			actualscore=new UFDouble(5);
//			bucklecap = new UFDouble(3);
			UFDouble buckleprize=new UFDouble(0);
			if(actualscore==null){
	        	actualscore=new UFDouble(0);
	        }
	        //ȡ��
			if(actualscore.doubleValue()>=0){
				buckleprize=new UFDouble(Math.floor(actualscore.div(0.5).doubleValue()));
			}else if(actualscore.doubleValue()<0){
				buckleprize=new UFDouble(0).sub(new UFDouble(Math.floor(actualscore.abs().div(0.5).doubleValue())));
			}
	        //���۷ⶥ
	        if(buckleprize.doubleValue()<0&&buckleprize.abs().sub(bucklecap.abs()).doubleValue()>0){
	        	buckleprize=new UFDouble(0).sub(bucklecap);
	        }else if(buckleprize.doubleValue()>=0&&buckleprize.abs().sub(bucklecap.abs()).doubleValue()>0){
	        	buckleprize=bucklecap;
	        }
			return buckleprize;
		}
		/**
		 * �ַ�ת����JSON��ʽ
		 * @author ʩ��
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
	 * JSONת�����ַ���ʽ
	 * @author ����
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
	 * JSONת�����ַ���ʽ
	 * @author ����
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
	  * ʱ��Ӽ���Сʱ
	  * cm
	  * @param day
	  * @param x
	  * @return
	  */
	 public static String addDate(String day, int x)
	    {
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24Сʱ��
	        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//12Сʱ��
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
	        cal.add(Calendar.HOUR_OF_DAY, x);//24Сʱ��
	        //cal.add(Calendar.HOUR, x);12Сʱ��
	        date = cal.getTime();
	        System.out.println("front:" + date);
	        cal = null;
	        return format.format(date);
	    }
}