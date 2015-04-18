package nc.vo.gt.pub.Toolkits;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nc.bs.framework.common.NCLocator;
import nc.bs.pub.billcodemanage.BillcodeGenerater;
import nc.bs.trade.business.HYPubBO;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.pub.jyglgt.proxy.Proxy;
import nc.vo.bd.invdoc.InvbasdocVO;
import nc.vo.bd.invdoc.ProdlineVO;
import nc.vo.gt.gk.gk03.FloorVO;
import nc.vo.ic.pub.bill.GeneralBillHeaderVO;
import nc.vo.ic.pub.bill.GeneralBillItemVO;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.logging.Debug;
import nc.vo.pub.BusinessException;
import nc.vo.pub.billcodemanage.BillCodeObjValueVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;


/**
 * 说明:前后台公用工具类
 * @author 施鹏
 * 2011-6-3  13:43:40
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
    
	public static double getDoubleValueNRound(double value,int n){
//		int ws = n;
//		String a = "1";
//		for(int i=0;i<ws;i++){
//			a += "0";
//		}
//		double div = Double.valueOf(a);
//		value = Math.round(value*div)/div;
		return Arith.round(value, n);
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
	   * 
	  * @Title: cutZero 
	  * @Description: 去掉小数后面的0
	  * @author zyb zhyb966@gmail.com 
	  * @date 2011-8-28 下午06:04:11 
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
		 * 返回用户名称
		 * @throws BusinessException 
		 * */
		public static String getUserName(String curOper) throws BusinessException{
			String sql = " select user_name from sm_user "
				+ "where cuserid ='" + curOper+ "'";
			 return Proxy.getItf().queryStrBySql(sql);
		}
		
		/**
		 * 包含值返回索引
		 * @param String str 值
		 * @return int 返回索引
		 * @author sp
		 * @serialData 2011-7-5
		 * */
		public static int getIndexOfContin(String str){
			int index=0;
			if(str.endsWith("全包含")){
				index= 0;
			}else if(str.equalsIgnoreCase("左包含")){
				index= 1;
			}else if(str.equalsIgnoreCase("右包含")){
				index= 2;
			}else if(str.equalsIgnoreCase("全不包含")){
				index= 3;
			}else {
				index= 0;
			}
			return index;
		}
		
		/**
		 * 货款结算参照名称编码对应关系
		 * */
		public static HashMap<String, String> getStoreGoodMap(){
			HashMap<String,String> map=new HashMap<String,String>();
			String[] names = {"磅单号","收款单号","存货编码","存货名称"};
		    String[] codes = {"b.poundcode","b.gatheringcode","c.invcode","c.invname"};
		    for(int i=0;i<names.length;i++){
		    	map.put(names[i], codes[i]);
		    }
			return map;
		}
		
		/**
		 * 普阳轨道衡,删除层号后重排,通用方法
		 * add by gdt
		 * @param delvo 要删除的层VO 信息要全
		 * @delvo来源: 
		 * 1.NC标准出入库单表体的vfree10关联到bd_floor的vdef11.
		 * 2.轨道衡入库单表体的vdef11关联到bd_floor的vdef11. 
		 * 层号从界面表体从下往上算 1 2 3
		 * @throws BusinessException 
		 */
		public static void deleteFloor(List<FloorVO> delvo) throws BusinessException{
			if(isEmpty(delvo)){
				Debug.debug("传入参数为空,不能删除,并重排");
				throw new BusinessException("传入参数为空,不能删除,并重排");
			}
			for (int i = 0; i < delvo.size(); i++) {
				FloorVO fvo = delvo.get(i);
				String pk_store = fvo.getPk_store();
				String pk_cargdoc = fvo.getPk_cargdoc();
				int floornum = fvo.getFloornum();
				String delSelfSql = " update bd_floor a set a.dr=1 ,a.isout='Y' where a.pk_floor='"+fvo.getPk_floor()+"' ";
				String reSetSql = " update bd_floor a set a.floornum = a.floornum-1 where a.floornum>"+floornum+" " +
						" and a.pk_cargdoc='"+pk_cargdoc+"' and a.pk_store='"+pk_store+"' ";
				//以下两个更新,分开执行,不好.但文丰接口里没有批量执行更新语句(先这样写了,之后要改成批量执行)
				Proxy.getItf().updateBYsql(delSelfSql);
				Proxy.getItf().updateBYsql(reSetSql);
			}
		}
		
		/**
		 * 产成品入库,删除层号后重排,通用方法
		 * @author 施鹏/shipeng/ship/sp t
		 * @param delvo 要删除的层VO 信息要全
		 * @delvo来源: 
		 * 1.NC标准出入库单表体的vfree10关联到bd_floor的vdef11.
		 * 2.轨道衡入库单表体的vdef11关联到bd_floor的vdef11. 
		 * 层号从界面表体从下往上算 1 2 3
		 * @throws BusinessException 
		 */
		public static void deleteFloor_46(List<FloorVO> delvo) throws BusinessException{
			if(isEmpty(delvo)){
				Debug.debug("传入参数为空,不能删除,并重排");
				throw new BusinessException("传入参数为空,不能删除,并重排");
			}
			for (int i = 0; i < delvo.size(); i++) {
				FloorVO fvo = delvo.get(i);
				int floornum = fvo.getFloornum();
				String delSelfSql = " update bd_floor a set a.dr=1 ,a.isout='Y' where a.pk_floor='"+fvo.getPk_floor()+"' ";
				StringBuffer reSetSql=new StringBuffer();
				reSetSql.append("  update bd_floor a ") 
				.append("     set a.floornum = a.floornum - 1 ") 
				.append("   where a.floornum >= "+floornum+" ") 
				.append("     and a.pk_floor <> '"+fvo.getPk_floor()+"' ") 
				.append("     and a.pk_floor in ") 
				.append("         (select bd_floor.pk_floor ") 
				.append("            from bd_floor ") 
				.append("           where vdef11 in ") 
				.append("                 (select v1.vfree10 ") 
				.append("                    from GT_IC_ONHANDNUM3 v1 ") 
				.append("                   where v1.cspaceid = ") 
				.append("                         (select cspaceid ") 
				.append("                            from ic_onhandnum_b ") 
				.append("                           where vfreeb10 = '"+fvo.getVdef11()+"' ") 
				.append("                             and ic_onhandnum_b.nastnum > 0 ") 
				.append("                             and nvl(dr, 0) = 0) ") 
				.append("                     and nvl(v1.ninspacenum, 0.0) - nvl(v1.noutspacenum, 0.0) > 0 ") 
				.append("                     and nvl(v1.ninspaceassistnum, 0.0) - ") 
				.append("                         nvl(v1.noutspaceassistnum, 0.0) > 0)) "); 
				//以下两个更新,分开执行,不好.但文丰接口里没有批量执行更新语句(先这样写了,之后要改成批量执行)
				Proxy.getItf().updateBYsql(delSelfSql);
				Proxy.getItf().updateBYsql(reSetSql.toString());
			}
		}

		
		/**
		 * 销售出库删除更新层号
		 * add by gdt
		 * @param delvo 要删除的层VO 信息要全
		 * @delvo来源: 
		 * 1.NC标准出入库单表体的vfree10关联到bd_floor的vdef11.
		 * 2.轨道衡入库单表体的vdef11关联到bd_floor的vdef11. 
		 * 层号从界面表体从下往上算 1 2 3
		 * @throws BusinessException 
		 */
		public static void createFloor(List<FloorVO> delvo) throws BusinessException{
			if(isEmpty(delvo)){
				Debug.debug("传入参数为空,不能删除,并重排");
				throw new BusinessException("传入参数为空,不能删除,并重排");
			}
			for (int i = 0; i < delvo.size(); i++) {
				FloorVO fvo = delvo.get(i);
				String pk_store = fvo.getPk_store();
				String pk_cargdoc = fvo.getPk_cargdoc();
				int floornum = fvo.getFloornum();
				String delSelfSql = " update bd_floor a set a.dr=0 ,a.isout='N' where a.pk_floor='"+fvo.getPk_floor()+"' ";
				String reSetSql = " update bd_floor a set a.floornum = a.floornum+1 where a.floornum>="+floornum+" and a.pk_floor<>'"+fvo.getPk_floor()+"' " +
						" and a.pk_cargdoc='"+pk_cargdoc+"' and a.pk_store='"+pk_store+"' ";
				//以下两个更新,分开执行,不好.但文丰接口里没有批量执行更新语句(先这样写了,之后要改成批量执行)
				Proxy.getItf().updateBYsql(delSelfSql);
				Proxy.getItf().updateBYsql(reSetSql);
			}
		}
		
		/**
		 * 普阳轨道衡,新增加的层号重排,通用方法
		 * add by gdt
		 * @param addvo 要新增的层VO,除层号信息外,其余信息都得要有
		 * @delvo来源: 
		 * 1.NC标准出入库单表体的vfree10关联到bd_floor的vdef11.
		 * 2.轨道衡入库单表体的vdef11关联到bd_floor的vdef11. 
		 * 层号从界面表体从下往上算 1 2 3
		 * @throws BusinessException 
		 */
		public static void addFloor(List<FloorVO> addvo) throws BusinessException{
			if(isEmpty(addvo)){
				Debug.debug("传入参数为空,不能新增,并重排");
				throw new BusinessException("传入参数为空,不能新增,并重排");
			}
			//以下开始分类,把在同一货位(子表里面货位可能不一样)的层,分开
			HashMap<String, List<FloorVO>> sameCargdoc = new HashMap<String,List<FloorVO>>();
			for (int i = 0; i < addvo.size(); i++) {
				FloorVO fvo = addvo.get(i);
				String pk_cargdoc = fvo.getPk_cargdoc();
				if(sameCargdoc.containsKey(pk_cargdoc)){
					sameCargdoc.get(pk_cargdoc).add(fvo);
				}else{
					List<FloorVO> newfloorvolist = new ArrayList<FloorVO>();
					newfloorvolist.add(fvo);
					sameCargdoc.put(pk_cargdoc, newfloorvolist);
				}
			}
			
			//开始通过规则来填充层号
			Iterator<String> iterator = sameCargdoc.keySet().iterator();
			while (iterator.hasNext()) {
				String pk_cargdoc = iterator.next();
				List<FloorVO> list = sameCargdoc.get(pk_cargdoc);
				//同一货位的.仓库肯定是一样的,任取一个就行
				String pk_store = list.get(0).getPk_store();
				//先查该仓库的该货位有几张板,来生成层号
				StringBuffer sb = new StringBuffer();
				sb.append(" select count(*) from ic_onhandnum_b where cspaceid='"+pk_cargdoc+"' ") 
				.append("  and cwarehouseidb='"+pk_store+"' ") 
				.append(" and nvl(dr,0)=0 and nvl(nastnum,0)>0");
				String alreadycount = Proxy.getItf().queryStrBySql(sb.toString());
				int acount = 0;
				if(!Toolkits.isEmpty(alreadycount)){
					acount = Integer.valueOf(alreadycount);
				}
				//遍历该货位的所有层,填充层号是从层底部往上的
				int realnum = 1;
				for (int i = list.size()-1; i >=0; i--) {
					FloorVO fvo = list.get(i);
					fvo.setFloornum(acount+realnum);
					realnum++;
				}
			}
			//保存层号
			Proxy.getItf().insertVOsArr(addvo.toArray(new FloorVO[0]));
		}
		/**
		 * 
		* 功能 当前是否为中板，有中板才进行层号的操作
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午11:46:52 
		* 返回 boolean    返回类型
		 */
		public static boolean isZbInv(GeneralBillVO m_billvo) throws BusinessException{
			GeneralBillItemVO[] itemvo = m_billvo.getItemVOs();
			GeneralBillHeaderVO hvo=(GeneralBillHeaderVO)m_billvo.getParentVO();
			String storsql="select csflag from bd_stordoc where nvl(csflag,'N')='Y' and nvl(dr,0)=0 and pk_corp='"+hvo.getPk_corp()+"' and pk_stordoc='"+hvo.getCwarehouseid()+"'";
			ArrayList list=Proxy.getItf().queryArrayBySql(storsql);
			if(list==null||list.size()==0){
				return false;
			}
			boolean flag = false;
			//自由项10是否有值判断是否为中板
			for (int i = 0; i < itemvo.length; i++) {
				String pk_invbasdoc = itemvo[i].getCinvbasid();
				InvbasdocVO invo = (InvbasdocVO) new HYPubBO().queryByPrimaryKey(InvbasdocVO.class, pk_invbasdoc);
				String pk_prodline = invo.getPk_prodline();
				if(Toolkits.isEmpty(pk_prodline))
					break;
				
				ProdlineVO prlvo = (ProdlineVO) new HYPubBO().queryByPrimaryKey(ProdlineVO.class, pk_prodline.toString());
				if(Toolkits.getString(prlvo.getProdlinename()).equals("中板")){
					flag =true;
					break;
				}
			}
			return flag;
		}
		
		/**
		 * 
		* 功能 产成品入库 表体数据是否有负值，当前是否为中板，有中板才进行层号的操作
		* 作者 施鹏/shipeng/ship/sp  
		* 时间 2012-04-13  
		* 返回 boolean    返回类型
		 */
		public static boolean isZbInv_46(GeneralBillVO m_billvo) throws BusinessException{
			GeneralBillItemVO[] itemvo = m_billvo.getItemVOs();
			GeneralBillHeaderVO hvo=(GeneralBillHeaderVO)m_billvo.getParentVO();
			String storsql="select csflag from bd_stordoc where nvl(csflag,'N')='Y' and nvl(dr,0)=0 and pk_corp='"+hvo.getPk_corp()+"' and pk_stordoc='"+hvo.getCwarehouseid()+"'";
			ArrayList list=Proxy.getItf().queryArrayBySql(storsql);
			if(list==null||list.size()==0){
				return false;
			}
			boolean flag = false;
			//自由项10是否有值判断是否为中板
			for (int i = 0; i < itemvo.length; i++) {
				if(itemvo[i].getNinassistnum().doubleValue()<0){//判断是否为负数
					flag=true;
				}
				String pk_invbasdoc = itemvo[i].getCinvbasid();
				InvbasdocVO invo = (InvbasdocVO) new HYPubBO().queryByPrimaryKey(InvbasdocVO.class, pk_invbasdoc);
				String pk_prodline = invo.getPk_prodline();
				if(Toolkits.isEmpty(pk_prodline))
					break;
				
				ProdlineVO prlvo = (ProdlineVO) new HYPubBO().queryByPrimaryKey(ProdlineVO.class, pk_prodline.toString());
				if(Toolkits.getString(prlvo.getProdlinename()).equals("中板")){
					flag =true;
					break;
				}
			}
			return flag;
		}
		
		/**
		 * 
		* 功能 产成品入库单保存冲掉负数的库存
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:32:38 
		* 返回 GeneralBillVO    返回类型
		 */
		public  static void reSetFloorNumWhenSaveIcIN(GeneralBillVO mbillvo) throws BusinessException{
			//只有当 当前界面的数据全都在同一货位时,才能使用此功能		
			GeneralBillItemVO[] itemsvo = mbillvo.getItemVOs();//需要保存的表体VO
			GeneralBillItemVO[] delitemvo = mbillvo.getDeletedItemVOs();//需要删除的表体VO
			List<String> vfree10list = new ArrayList<String>();
			for (int i = 0; i < itemsvo.length; i++) {
				String vfree10 = itemsvo[i].getVfree10();
				String cgeneralhid = itemsvo[i].getCgeneralbid();//表体没主键是新增行，修改层号发生变化先不考虑
				UFDouble ninnum = itemsvo[i].getNinnum()==null?new UFDouble(0):itemsvo[i].getNinnum();
				if(!isEmpty(vfree10)&&isEmpty(cgeneralhid)&&ninnum.doubleValue()<0){
					vfree10list.add(vfree10);
				}
			}
			String vfree10s = Toolkits.combinArrayToString(vfree10list.toArray(new String[0]));
			//将保存后重排层号
			reSetNum(vfree10s);			
			//删除出库行数，恢复对应的货物到最大的一层
			if(delitemvo!=null){
				delLineItemvoForICIn(mbillvo);
			}
		}
		/**
		 * 
		* 功能 来源是否为形态转换
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-18 下午08:01:18 
		* 返回 boolean    返回类型
		 */
		public static boolean isChangeWx(GeneralBillVO m_billvo){
			GeneralBillItemVO[] itemvos = m_billvo.getItemVOs();
			boolean flag = false;
			for (GeneralBillItemVO generalBillItemVO : itemvos) {
				String soursebilltype = generalBillItemVO.getCsourcetype()==null?"":generalBillItemVO.getCsourcetype();
				if(soursebilltype.equals("4N")){
					flag = true;
					break;
				}
			}
			return flag;
		}
		
		/**
		 * 
		* 功能 出库保存层号
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:32:38 
		* 返回 GeneralBillVO    返回类型
		 */
		public  static void reSetFloorNumWhenSave(GeneralBillVO mbillvo) throws BusinessException{
			//只有当 当前界面的数据全都在同一货位时,才能使用此功能		
			GeneralBillItemVO[] itemsvo = mbillvo.getItemVOs();//需要保存的表体VO
			GeneralBillItemVO[] delitemvo = mbillvo.getDeletedItemVOs();//需要删除的表体VO
			List<String> vfree10list = new ArrayList<String>();
			for (int i = 0; i < itemsvo.length; i++) {
				String vfree10 = itemsvo[i].getVfree10();
				String cgeneralhid = itemsvo[i].getCgeneralbid();//表体没主键是新增行，修改层号发生变化先不考虑
				if(!isEmpty(vfree10)&&isEmpty(cgeneralhid)){
					vfree10list.add(vfree10);
				}
			}
			String vfree10s = Toolkits.combinArrayToString(vfree10list.toArray(new String[0]));
			//将保存后重排层号
			reSetNum(vfree10s);			
			//删除出库行数，恢复对应的货物到最大的一层
			if(delitemvo!=null){
				delLineItemvo(mbillvo);
			}
		}
		
		
		/**
		 * 
		* 功能 产成品入库保存层号
		* 作者 施鹏/shipeng/ship/sp
		* 时间 2012-4-13 下午17:32:38 
		* 返回 GeneralBillVO    返回类型
		 */
		public  static void reSetFloorNumWhenSave_46(GeneralBillVO mbillvo) throws BusinessException{
			//只有当 当前界面的数据全都在同一货位时,才能使用此功能		
			GeneralBillItemVO[] itemsvo = mbillvo.getItemVOs();//需要保存的表体VO
			GeneralBillItemVO[] delitemvo = mbillvo.getDeletedItemVOs();//需要删除的表体VO
			List<String> vfree10list = new ArrayList<String>();
			for (int i = 0; i < itemsvo.length; i++) {
				if(itemsvo[i].getNinassistnum().doubleValue()>=0){//判断是否为负数
					continue;
				}
				String vfree10 = itemsvo[i].getVfree10();
				String cgeneralhid = itemsvo[i].getCgeneralbid();//表体没主键是新增行，修改层号发生变化先不考虑
				if(!isEmpty(vfree10)&&isEmpty(cgeneralhid)){
					vfree10list.add(vfree10);
				}
			}
			String vfree10s = Toolkits.combinArrayToString(vfree10list.toArray(new String[0]));
			//将保存后重排层号
			reSetNum(vfree10s);			
			//删除出库行数，恢复对应的货物到最大的一层
			if(delitemvo!=null){
				delLineItemvo_46(mbillvo);
			}
		}
		/**
		 * 
		* 功能 货位调整层号
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:32:38 
		* 返回 GeneralBillVO    返回类型
		 */
		public  static void reSetFloorNumCargdocChange(GeneralBillVO mbillvo) throws BusinessException{
			//只有当 当前界面的数据全都在同一货位时,才能使用此功能		
			GeneralBillItemVO[] itemsvo = mbillvo.getItemVOs();//需要保存的表体VO
			GeneralBillItemVO[] delitemvo = mbillvo.getDeletedItemVOs();//需要删除的表体VO
			String cwarehouseidb = mbillvo.getHeaderVO().getCwarehouseid();	
			List<String> vfree10Fromlist = new ArrayList<String>();
			HashMap<String, ArrayList<String>> mapcargdoc = new HashMap<String, ArrayList<String>>(); //传入层号的货位和层号
			for (int i = 0; i < itemsvo.length; i++) {
				String vfree10 = itemsvo[i].getVfree10();
				String cspaceid2 = itemsvo[i].getCspace2id();//转入货位
				ArrayList<String> list = new ArrayList<String>();
				list.add(0, cwarehouseidb);
				list.add(1, cspaceid2);
				mapcargdoc.put(vfree10, list);
				String cgeneralbid = itemsvo[i].getCgeneralbid();//表体没主键是新增行，修改层号发生变化先不考虑
				if(!Toolkits.isEmpty(vfree10)&&isEmpty(cgeneralbid)){
					vfree10Fromlist.add(vfree10);
				}
			}
			String vfree10s = Toolkits.combinArrayToString(vfree10Fromlist.toArray(new String[0]));
			//将保存后重排层号
			reSetNumCargdocChange(vfree10s, mapcargdoc);	
			//删除出库行数，恢复对应的货物到最大的一层
			if(delitemvo!=null){
				delCargdocLineItemvo(mbillvo);
			}
		}
		
		/**
		 * 
		* 功能 删行和删除销售出库对层号重排
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:47:23 
		* 返回 void    返回类型
		 */
		public static void delItemvo(GeneralBillVO m_delbillvo) throws BusinessException{
			HashMap<String, Integer> hm = new HashMap<String, Integer>();//保存行对应货位层号
			String cwarehouseidb = m_delbillvo.getHeaderVO().getCwarehouseid();		
			GeneralBillItemVO[] itemvo = m_delbillvo.getItemVOs();
			for (int i = 0; i < itemvo.length; i++) {
				String cspaceid = itemvo[i].getCspaceid()==null?"":itemvo[i].getCspaceid();
				if(hm.containsKey(cspaceid)){
					hm.put(cspaceid, hm.get(cspaceid)+1);
				}else{
					int floornum = getMaxFloorNum(cspaceid, cwarehouseidb);
					hm.put(cspaceid, floornum+1);
				}
				int floornum = hm.get(cspaceid);
				String vfree10 = itemvo[i].getVfree10();
				updateFloorNum(vfree10,floornum);
			}
		}
		
		
		/**
		 * 
		* 功能 产成品入库删行和对层号重排
		* 作者 施鹏/shipeng/ship/sp 
		* 时间 2012-4-13 下午17:32:38  
		* 返回 void    返回类型
		 */
		public static void delItemvo_46(GeneralBillVO m_delbillvo) throws BusinessException{
			HashMap<String, Integer> hm = new HashMap<String, Integer>();//保存行对应货位层号
			String cwarehouseidb = m_delbillvo.getHeaderVO().getCwarehouseid();		
			GeneralBillItemVO[] itemvo = m_delbillvo.getItemVOs();
			for (int i = 0; i < itemvo.length; i++) {
				if(itemvo[i].getNinassistnum().doubleValue()>=0){//判断是否为负数
					continue;
				}
				String cspaceid = itemvo[i].getCspaceid()==null?"":itemvo[i].getCspaceid();
				if(hm.containsKey(cspaceid)){
					hm.put(cspaceid, hm.get(cspaceid)+1);
				}else{
					int floornum = getMaxFloorNum(cspaceid, cwarehouseidb);
					hm.put(cspaceid, floornum+1);
				}
				int floornum = hm.get(cspaceid);
				String vfree10 = itemvo[i].getVfree10();
				updateFloorNum(vfree10,floornum);
			}
		}
		
		/**
		 * 
		* 功能 删行和删除销售出库对层号重排
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:47:23 
		* 返回 void    返回类型
		 */
		public static void delItemOtherIcvo(GeneralBillVO m_delbillvo) throws BusinessException{
			HashMap<String, Integer> hm = new HashMap<String, Integer>();//保存行对应货位层号
			String cwarehouseidb = m_delbillvo.getHeaderVO().getCwarehouseid();		
			GeneralBillItemVO[] itemvo = m_delbillvo.getItemVOs();
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < itemvo.length; i++) {
				String vfree10 = itemvo[i].getVfree10();
				if(!isEmpty(vfree10)){
					list.add(vfree10);
				}
			}
			String vfree10s = combinArrayToString(list.toArray(new String[0]));
			reSetNum(vfree10s);
		}
		
		/**
		 * 
		* 功能 删行和删除销售出库对层号重排
		* 作者 施鹏/shipeng/ship/sp 
		* 时间 2012-4-13 下午17:32:38 
		* 返回 void    返回类型
		 */
		public static void delItemOtherIcvo_46(GeneralBillVO m_delbillvo) throws BusinessException{
			HashMap<String, Integer> hm = new HashMap<String, Integer>();//保存行对应货位层号
			String cwarehouseidb = m_delbillvo.getHeaderVO().getCwarehouseid();		
			GeneralBillItemVO[] itemvo = m_delbillvo.getItemVOs();
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < itemvo.length; i++) {
				String vfree10 = itemvo[i].getVfree10();
				if(!isEmpty(vfree10)){
					list.add(vfree10);
				}
			}
			String vfree10s = combinArrayToString(list.toArray(new String[0]));
			reSetNum(vfree10s);
		}
		
		/**
		 * 
		* 功能 产成品入库删除，数量为负数时
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:47:23 
		* 返回 void    返回类型
		 */
		public static void delLineItemvoForICIn(GeneralBillVO m_delbillvo) throws BusinessException{
			HashMap<String, Integer> hm = new HashMap<String, Integer>();//保存行对应货位层号
			String cwarehouseidb = m_delbillvo.getHeaderVO().getCwarehouseid();		
			GeneralBillItemVO[] itemvo = m_delbillvo.getDeletedItemVOs();
			for (int i = 0; i < itemvo.length; i++) {
				String cspaceid = itemvo[i].getCspaceid()==null?"":itemvo[i].getCspaceid();
				if(hm.containsKey(cspaceid)){
					hm.put(cspaceid, hm.get(cspaceid)+1);
				}else{
					int floornum = getMaxFloorNum(cspaceid, cwarehouseidb);
					hm.put(cspaceid, floornum+1);
				}
				int floornum = hm.get(cspaceid);
				String vfree10 = itemvo[i].getVfree10();
				updateFloorNum(vfree10,floornum);
			}
		}
		
		/**
		 * 
		* 功能 删行和删除销售出库对层号重排
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:47:23 
		* 返回 void    返回类型
		 */
		public static void delLineItemvo(GeneralBillVO m_delbillvo) throws BusinessException{
			HashMap<String, Integer> hm = new HashMap<String, Integer>();//保存行对应货位层号
			String cwarehouseidb = m_delbillvo.getHeaderVO().getCwarehouseid();		
			GeneralBillItemVO[] itemvo = m_delbillvo.getDeletedItemVOs();
			for (int i = 0; i < itemvo.length; i++) {
				String cspaceid = itemvo[i].getCspaceid()==null?"":itemvo[i].getCspaceid();
				if(hm.containsKey(cspaceid)){
					hm.put(cspaceid, hm.get(cspaceid)+1);
				}else{
					int floornum = getMaxFloorNum(cspaceid, cwarehouseidb);
					hm.put(cspaceid, floornum+1);
				}
				int floornum = hm.get(cspaceid);
				String vfree10 = itemvo[i].getVfree10();
				updateFloorNum(vfree10,floornum);
			}
		}
		
		/**
		 * 
		* 功能 产成品入库删行和删除销售出库对层号重排
		* 作者 施鹏/shipeng/ship/sp 
		* 时间 2012-4-13 下午17:32:38  
		* 返回 void    返回类型
		 */
		public static void delLineItemvo_46(GeneralBillVO m_delbillvo) throws BusinessException{
			HashMap<String, Integer> hm = new HashMap<String, Integer>();//保存行对应货位层号
			String cwarehouseidb = m_delbillvo.getHeaderVO().getCwarehouseid();		
			GeneralBillItemVO[] itemvo = m_delbillvo.getDeletedItemVOs();
			for (int i = 0; i < itemvo.length; i++) {
				if(itemvo[i].getNinassistnum().doubleValue()>=0){//判断是否为负数
					continue;
				}
				String cspaceid = itemvo[i].getCspaceid()==null?"":itemvo[i].getCspaceid();
				if(hm.containsKey(cspaceid)){
					hm.put(cspaceid, hm.get(cspaceid)+1);
				}else{
					int floornum = getMaxFloorNum(cspaceid, cwarehouseidb);
					hm.put(cspaceid, floornum+1);
				}
				int floornum = hm.get(cspaceid);
				String vfree10 = itemvo[i].getVfree10();
				updateFloorNum(vfree10,floornum);
			}
		}
		
		/**
		 * 
		* 功能 货位转换删除行层号重排
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:47:23 
		* 返回 void    返回类型
		 */
		public static void delCargdocLineItemvo(GeneralBillVO m_delbillvo) throws BusinessException{
			String cwarehouseidb = m_delbillvo.getHeaderVO().getCwarehouseid();		
			GeneralBillItemVO[] itemvo = m_delbillvo.getDeletedItemVOs();
			HashMap<String, ArrayList<String>> mapTocargdoc = new HashMap<String, ArrayList<String>>(); //传入层号的货位和层号
			HashMap<String, ArrayList<String>> mapFromcargdoc = new HashMap<String, ArrayList<String>>(); //传入层号的货位和层号
			List<String> listvfree10  = new ArrayList<String>();
			for (int i = 0; i < itemvo.length; i++) {
				String vfree10 = itemvo[i].getVfree10();
				if(!isEmpty(vfree10)){
					listvfree10.add(vfree10);
				}
				ArrayList<String> Tolist = new ArrayList<String>();
				ArrayList<String> Fromlist = new ArrayList<String>();
				String cspaceid2 = itemvo[i].getCspace2id()==null?"":itemvo[i].getCspace2id();
				String cspaceid = itemvo[i].getCspaceid()==null?"":itemvo[i].getCspaceid();
				Tolist.add(0, cwarehouseidb);
				Tolist.add(1,cspaceid2);
				Fromlist.add(0,cwarehouseidb);
				Fromlist.add(1, cspaceid);
				mapTocargdoc.put(vfree10, Tolist);
				mapFromcargdoc.put(vfree10, Fromlist);					
			}
			String vfree10s = combinArrayToString(listvfree10.toArray(new String[0]));
			updateFloorNumCargdocChangeForSave(vfree10s,mapFromcargdoc,mapTocargdoc);
		}
		
		/**
		 * 
		* 功能 删行和删除货位转换层号重排
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:47:23 
		* 返回 void    返回类型
		 */
		public static void delCargdocItemvo(GeneralBillVO m_delbillvo) throws BusinessException{
			String cwarehouseidb = m_delbillvo.getHeaderVO().getCwarehouseid();		
			GeneralBillItemVO[] itemvo = m_delbillvo.getItemVOs();
			HashMap<String, ArrayList<String>> mapTocargdoc = new HashMap<String, ArrayList<String>>(); //传入层号的货位和层号
			HashMap<String, ArrayList<String>> mapFromcargdoc = new HashMap<String, ArrayList<String>>(); //传入层号的货位和层号
			List<String> vfree10list = new ArrayList<String>();
			for (int i = 0; i < itemvo.length; i++) {
				String vfree10 = itemvo[i].getVfree10();
				if(!isEmpty(vfree10)){
					vfree10list.add(vfree10);
				}
				ArrayList<String> Tolist = new ArrayList<String>();
				ArrayList<String> Fromlist = new ArrayList<String>();
				String cspaceid2 = itemvo[i].getCspace2id()==null?"":itemvo[i].getCspace2id();
				String cspaceid = itemvo[i].getCspaceid()==null?"":itemvo[i].getCspaceid();
				Tolist.add(0, cwarehouseidb);
				Tolist.add(1,cspaceid2);
				Fromlist.add(0,cwarehouseidb);
				Fromlist.add(1, cspaceid);
				mapTocargdoc.put(vfree10, Tolist);
				mapFromcargdoc.put(vfree10, Fromlist);
			}
			String vfree10s = combinArrayToString(vfree10list.toArray(new String[0]));
			updateFloorNumCargdocChange(vfree10s,mapTocargdoc,mapFromcargdoc);
		}
		
		/**
		 * 
		* 功能 货位转换保存重排层号
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:28:50 
		* 返回 void    返回类型
		 */
		public static void reSetNumCargdocChange(String vfree10s,HashMap<String, ArrayList<String>> map) throws BusinessException{
			HashMap<String, Integer> mapcpaseid = new HashMap<String, Integer>();//保存货位的最大层号			
			IUAPQueryBS qbs = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
			String floorSql = "select * from bd_floor where vdef11 in "+vfree10s+" order by floornum desc";
			List<FloorVO> floorlist = (List<FloorVO>) qbs.executeQuery(floorSql, new BeanListProcessor(FloorVO.class));
			if(floorlist!=null&&floorlist.size()>0){
				//重排转出货位的层号  根据自由项10获取货位，再根据货位找到层号档案，对应的所有信息，从而不用每次更新层号档案的仓库和货位
				for (FloorVO floorVO : floorlist) {					
					int floornum = floorVO.getFloornum();
					StringBuffer reSetSql = new StringBuffer("");
					reSetSql.append("  update bd_floor a ") 
					.append("     set a.floornum = a.floornum - 1 ") 
					.append("   where a.floornum >= "+floorVO.getFloornum()+" ") 
					.append("     and a.pk_floor <> '"+floorVO.getPk_floor()+"' ") 
					.append("     and a.pk_floor in ") 
					.append("         (select bd_floor.pk_floor ") 
					.append("            from bd_floor ") 
					.append("           where vdef11 in ") 
					.append("                 (select v1.vfree10 ") 
					.append("                    from GT_IC_ONHANDNUM3 v1 ") 
					.append("                   where v1.cspaceid = ") 
					.append("                         (select cspaceid ") 
					.append("                            from ic_onhandnum_b ") 
					.append("                           where vfreeb10 = '"+floorVO.getVdef11()+"' ") 
					.append("                             and ic_onhandnum_b.nastnum > 0 ") 
					.append("                             and nvl(dr, 0) = 0) ") 
					.append("                     and nvl(v1.ninspacenum, 0.0) - nvl(v1.noutspacenum, 0.0) > 0 ") 
					.append("                     and nvl(v1.ninspaceassistnum, 0.0) - ") 
					.append("                         nvl(v1.noutspaceassistnum, 0.0) > 0)) "); 
					//以下两个更新,分开执行,不好.但文丰接口里没有批量执行更新语句(先这样写了,之后要改成批量执行)
					Proxy.getItf().updateBYsql(reSetSql.toString());
				}
				for (FloorVO floorVO : floorlist) {
					String vfree10 = floorVO.getVdef11();
					ArrayList list = map.get(vfree10);
					String cwarehouseid = (String) list.get(0);
					String cspaceid = (String) list.get(1);
					if(mapcpaseid.containsKey(cspaceid)){
						mapcpaseid.put(cspaceid, mapcpaseid.get(cspaceid)+1);
					}else{
						int maxnum = getMaxFloorNum(cspaceid, cwarehouseid);
						mapcpaseid.put(cspaceid, maxnum+1);
					}
					floorVO.setFloornum(mapcpaseid.get(cspaceid));
					Proxy.getItf().updateVO(floorVO);
				}				
			}
		}
		
		/**
		 * 
		* 功能 销售出库保存重排层号
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:28:50 
		* 返回 void    返回类型
		 */
		public static void reSetNum(String vfree10s) throws BusinessException{
			IUAPQueryBS qbs = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
			String floorSql = "select * from bd_floor where vdef11 in "+vfree10s+" order by floornum desc";
			List<FloorVO> floorlist = (List<FloorVO>) qbs.executeQuery(floorSql, new BeanListProcessor(FloorVO.class));
			if(floorlist!=null&&floorlist.size()>0){
				for (FloorVO floorVO : floorlist) {
					StringBuffer reSetSql = new StringBuffer("");
					reSetSql.append("  update bd_floor a ") 
					.append("     set a.floornum = a.floornum - 1 ") 
					.append("   where a.floornum >= "+floorVO.getFloornum()+" ") 
					.append("     and a.pk_floor <> '"+floorVO.getPk_floor()+"' ") 
					.append("     and a.pk_floor in ") 
					.append("         (select bd_floor.pk_floor ") 
					.append("            from bd_floor ") 
					.append("           where vdef11 in ") 
					.append("                 (select v1.vfree10 ") 
					.append("                    from GT_IC_ONHANDNUM3 v1 ") 
					.append("                   where v1.cspaceid = ") 
					.append("                         (select cspaceid ") 
					.append("                            from ic_onhandnum_b ") 
					.append("                           where vfreeb10 = '"+floorVO.getVdef11()+"' ") 
					.append("                             and ic_onhandnum_b.nastnum > 0 ") 
					.append("                             and nvl(dr, 0) = 0) ") 
					.append("                     and nvl(v1.ninspacenum, 0.0) - nvl(v1.noutspacenum, 0.0) > 0 ") 
					.append("                     and nvl(v1.ninspaceassistnum, 0.0) - ") 
					.append("                         nvl(v1.noutspaceassistnum, 0.0) > 0)) "); 
					//以下两个更新,分开执行,不好.但文丰接口里没有批量执行更新语句(先这样写了,之后要改成批量执行)
					Proxy.getItf().updateBYsql(reSetSql.toString());
				}
			}			
		}
		
		/**
		 * 
		* 功能 其他入库单删除重排层号
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:28:50 
		* 返回 void    返回类型
		 */
		public static void reSetNumOherIc(String vfree10s) throws BusinessException{
			IUAPQueryBS qbs = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
			String floorSql = "select * from bd_floor where vdef11 in "+vfree10s+" order by floornum desc";
			List<FloorVO> floorlist = (List<FloorVO>) qbs.executeQuery(floorSql, new BeanListProcessor(FloorVO.class));
			if(floorlist!=null&&floorlist.size()>0){
				for (FloorVO floorVO : floorlist) {
//					String reSetSql = " update bd_floor a set a.floornum = a.floornum-1 where a.floornum>="+floornum+" and a.pk_floor<>'"+floorVO.getPk_floor()+"' " +
//							" and a.pk_cargdoc='"+pk_cargdoc+"' and a.pk_store='"+pk_store+"' ";
					StringBuffer reSetSql = new StringBuffer("");
					reSetSql.append("  update bd_floor a ") 
					.append("     set a.floornum = a.floornum - 1 ") 
					.append("   where a.floornum >= "+floorVO.getFloornum()+" ") 
					.append("     and a.pk_floor <> '"+floorVO.getPk_floor()+"' ") 
					.append("     and a.pk_floor in ") 
					.append("         (select bd_floor.pk_floor ") 
					.append("            from bd_floor ") 
					.append("           where vdef11 in ") 
					.append("                 (select v1.vfree10 ") 
					.append("                    from GT_IC_ONHANDNUM3 v1 ") 
					.append("                   where v1.cspaceid = ") 
					.append("                         (select cspaceid ") 
					.append("                            from ic_onhandnum_b ") 
					.append("                           where vfreeb10 = '"+floorVO.getVdef11()+"' ") 
					.append("                             and ic_onhandnum_b.nastnum > 0 ") 
					.append("                             and nvl(dr, 0) = 0) ") 
					.append("                     and nvl(v1.ninspacenum, 0.0) - nvl(v1.noutspacenum, 0.0) > 0 ") 
					.append("                     and nvl(v1.ninspaceassistnum, 0.0) - ") 
					.append("                         nvl(v1.noutspaceassistnum, 0.0) > 0)) "); 
					//以下两个更新,分开执行,不好.但文丰接口里没有批量执行更新语句(先这样写了,之后要改成批量执行)
					Proxy.getItf().updateBYsql(reSetSql.toString());
				}
			}			
		}
		
		/**
		 * 
		* 功能 更新删除的行的层号为最大层
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:15:55 
		* 返回 void    返回类型
		 */
		public static void updateFloorNumOtherIc(String cspaceid,int floornum) throws BusinessException{
			String sql = "update bd_floor set floornum=floornum-1 where pk_store='"+cspaceid+"' and floornum>="+floornum+" ";
			Proxy.getItf().updateBYsql(sql);
		}
		
		/**
		 * 
		* 功能 更新删除的行的层号为最大层
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:15:55 
		* 返回 void    返回类型
		 */
		public static void updateFloorNum(String vfree10,int floornum) throws BusinessException{
			String sql = "update bd_floor set floornum="+floornum+" where bd_floor.vdef11='"+vfree10+"'";
			Proxy.getItf().updateBYsql(sql);
		}
		
		/**
		 * 
		* 功能 货位转换重新设置转入货位的层号信息，转出仓库的层号信息
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:15:55 
		* 返回 void    返回类型
		 */
		public static void updateFloorNumCargdocChangeForSave(String vfree10,HashMap<String, ArrayList<String>> mapTocargdoc,
				HashMap<String, ArrayList<String>> mapFromcargdoc) throws BusinessException{
			String floorSql = "select * from bd_floor where vdef11 in "+vfree10+" order by floornum desc";
			IUAPQueryBS qbs = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
			List<FloorVO> floorlist = (List<FloorVO>) qbs.executeQuery(floorSql, new BeanListProcessor(FloorVO.class));
			if(floorlist!=null&&floorlist.size()>0){
				for (FloorVO floorVO : floorlist) {
					String vdef11 = floorVO.getVdef11();
					ArrayList<String> list = mapFromcargdoc.get(vdef11);
					String cspaceid = list.get(1);
					StringBuffer reSetSql = new StringBuffer("");
					reSetSql.append("  update bd_floor a ") 
					.append("     set a.floornum = a.floornum - 1 ") 
					.append("   where a.floornum >= "+floorVO.getFloornum()+" ") 
					.append("     and a.pk_floor <> '"+floorVO.getPk_floor()+"' ") 
					.append("     and a.pk_floor in ") 
					.append("         (select bd_floor.pk_floor ") 
					.append("            from bd_floor ") 
					.append("           where vdef11 in ") 
					.append("                 (select v1.vfree10 ") 
					.append("                    from GT_IC_ONHANDNUM3 v1 ") 
					.append("                   where v1.cspaceid = '"+cspaceid+"'") 
					.append("                     and nvl(v1.ninspacenum, 0.0) - nvl(v1.noutspacenum, 0.0) > 0 ") 
					.append("                     and nvl(v1.ninspaceassistnum, 0.0) - ") 
					.append("                         nvl(v1.noutspaceassistnum, 0.0) > 0)) "); 
					//以下两个更新,分开执行,不好.但文丰接口里没有批量执行更新语句(先这样写了,之后要改成批量执行)
					Proxy.getItf().updateBYsql(reSetSql.toString());
				}
				HashMap<String, Integer> map = new HashMap<String, Integer>();
				for (FloorVO floorVO : floorlist) {
					String vdef11 = floorVO.getVdef11();
					ArrayList<String> fromlist = mapTocargdoc.get(vdef11);
					String cwarehouseidb = fromlist.get(0);//仓库
					String cspaceid = fromlist.get(1);//货位
					if(map.containsKey(cspaceid)){
						map.put(cspaceid, map.get(cspaceid)+1);
					}else{
						int floornum = getMaxFloorNum(cspaceid, cwarehouseidb);//和删除不同，保存是更新现存量之前重排层号的，而删除时更新现存量之后（需要排除当前层号）
						map.put(cspaceid, floornum+1);
					}
					floorVO.setFloornum(map.get(cspaceid));
					Proxy.getItf().updateVO(floorVO);
				}
			}					
		}
		
		/**
		 * 
		* 功能 货位转换重新设置转入货位的层号信息，转出仓库的层号信息
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:15:55 
		* 返回 void    返回类型
		 */
		public static void updateFloorNumCargdocChange(String vfree10,HashMap<String, ArrayList<String>> mapTocargdoc,
				HashMap<String, ArrayList<String>> mapFromcargdoc) throws BusinessException{
			String floorSql = "select * from bd_floor where vdef11 in "+vfree10+" order by floornum desc";
			IUAPQueryBS qbs = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
			List<FloorVO> floorlist = (List<FloorVO>) qbs.executeQuery(floorSql, new BeanListProcessor(FloorVO.class));
			if(floorlist!=null&&floorlist.size()>0){
				for (FloorVO floorVO : floorlist) {
					String vdef11 = floorVO.getVdef11();
					ArrayList<String> list = mapTocargdoc.get(vdef11);
					String cspaceid = list.get(1);
					StringBuffer reSetSql = new StringBuffer("");
					reSetSql.append("  update bd_floor a ") 
					.append("     set a.floornum = a.floornum - 1 ") 
					.append("   where a.floornum >= "+floorVO.getFloornum()+" ") 
					.append("     and a.pk_floor <> '"+floorVO.getPk_floor()+"' ") 
					.append("     and a.pk_floor in ") 
					.append("         (select bd_floor.pk_floor ") 
					.append("            from bd_floor ") 
					.append("           where vdef11 in ") 
					.append("                 (select v1.vfree10 ") 
					.append("                    from GT_IC_ONHANDNUM3 v1 ") 
					.append("                   where v1.cspaceid = '"+cspaceid+"'") 
					.append("                     and nvl(v1.ninspacenum, 0.0) - nvl(v1.noutspacenum, 0.0) > 0 ") 
					.append("                     and nvl(v1.ninspaceassistnum, 0.0) - ") 
					.append("                         nvl(v1.noutspaceassistnum, 0.0) > 0)) "); 
					//以下两个更新,分开执行,不好.但文丰接口里没有批量执行更新语句(先这样写了,之后要改成批量执行)
					Proxy.getItf().updateBYsql(reSetSql.toString());
				}
				HashMap<String, Integer> map = new HashMap<String, Integer>();
				for (FloorVO floorVO : floorlist) {
					String vdef11 = floorVO.getVdef11();
					ArrayList<String> fromlist = mapFromcargdoc.get(vdef11);
					String cwarehouseidb = fromlist.get(0);//仓库
					String cspaceid = fromlist.get(1);//货位
					if(map.containsKey(cspaceid)){
						map.put(cspaceid, map.get(cspaceid)+1);
					}else{
						int floornum = getMaxFloorNumForCargdoc(cspaceid, cwarehouseidb,vfree10);
						map.put(cspaceid, floornum+1);
					}
					floorVO.setFloornum(map.get(cspaceid));
					Proxy.getItf().updateVO(floorVO);
				}
			}					
		}
		/**
		 * 
		* 功能 其他入库单，保存生成层号
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 下午10:34:58 
		* 返回 GeneralBillVO    返回类型
		 */
		public static GeneralBillVO CreatBdfloor(GeneralBillVO m_billvo) throws BusinessException{
			String cwarehouseidb = m_billvo.getHeaderVO().getCwarehouseid();
			String pk_corp= m_billvo.getHeaderVO().getPk_corp();
			GeneralBillItemVO[] itemvo = m_billvo.getItemVOs();
			GeneralBillItemVO[] delitemvos = (GeneralBillItemVO[]) m_billvo.getDeletedItemVOs();
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			for (int i = 0; i < itemvo.length; i++) {
				int row = i+1;
				String cspaceid = itemvo[i].getCspaceid();
				if(isEmpty(cspaceid)){
					Debug.debug("当前没有货位不能生成层号!");
					throw new BusinessException("第"+row+" 行没有货位请检查！");
				}
				UFDouble noutnum = itemvo[i].getNinnum()==null?new UFDouble(0):itemvo[i].getNinnum();
				if(noutnum.doubleValue()<=0){
					throw new BusinessException("第"+row+"行没有实收数量，请检查！");
				}
				String cgeneralbid = itemvo[i].getCgeneralbid();
				if(isEmpty(cgeneralbid)){
					if(map.containsKey(cspaceid)){
						map.put(cspaceid, map.get(cspaceid)+1);
					}else{
						int maxfloornum = getMaxFloorNum(cspaceid, cwarehouseidb);
						map.put(cspaceid,maxfloornum+1);
					}
					String[] pk = Proxy.getItf().getPKFromDB(pk_corp, 2);
					FloorVO flvo = new FloorVO();
					flvo.setVdef11(pk[0]);
					m_billvo.getItemVOs()[i].setAttributeValue("vfree10", pk[0]);
					flvo.setPk_floor(pk[1]);
					flvo.setPk_cargdoc(cspaceid);
					flvo.setPk_store(cwarehouseidb);
					flvo.setDr(new Integer(0));
					flvo.setIsout(new UFBoolean(false));
					flvo.setFloornum(map.get(cspaceid));
					Proxy.getItf().insertVOsWithPK(new FloorVO[]{flvo});
				}
			}
			List<String> vdef11dellist  = new ArrayList<String>();
			if(delitemvos!=null){
				for (int j = 0; j < delitemvos.length; j++) {
					String vfree10 = delitemvos[j].getVfree10();
					vdef11dellist.add(vfree10);
				}
				String[] vdef11del = vdef11dellist.toArray(new String[0]);
				String vdef11dels = Toolkits.combinArrayToString(vdef11del);
				IUAPQueryBS qbs = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
				List<FloorVO> listdelfvo = (List<FloorVO>) qbs.executeQuery("select * from bd_floor where vdef11 in "+vdef11dels+" order by floornum desc ", new BeanListProcessor(FloorVO.class));
				Toolkits.deleteFloor_46(listdelfvo);	//行删除恢复层号	
			}
			return m_billvo;
		}
		
		/**
		 * 
		* 功能 产成品入库，保存生成层号
		* 作者 施鹏/shipeng/ship/sp 
		* 时间 2012-4-13 下午17:32:38 
		* 返回 GeneralBillVO    返回类型
		 */
		public static GeneralBillVO CreatBdfloor_46(GeneralBillVO m_billvo) throws BusinessException{
			String cwarehouseidb = m_billvo.getHeaderVO().getCwarehouseid();
			String pk_corp= m_billvo.getHeaderVO().getPk_corp();
			GeneralBillItemVO[] itemvo = m_billvo.getItemVOs();
			GeneralBillItemVO[] delitemvos = (GeneralBillItemVO[]) m_billvo.getDeletedItemVOs();
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			for (int i = 0; i < itemvo.length; i++) {
				int row = i+1;
				String cspaceid = itemvo[i].getCspaceid();
				if(isEmpty(cspaceid)){
					Debug.debug("当前没有货位不能生成层号!");
					throw new BusinessException("第"+row+" 行没有货位请检查！");
				}
				UFDouble noutnum = itemvo[i].getNinnum()==null?new UFDouble(0):itemvo[i].getNinnum();
				if(noutnum.doubleValue()<=0){
					throw new BusinessException("第"+row+"行没有实收数量，请检查！");
				}
				String cgeneralbid = itemvo[i].getCgeneralbid();
				if(isEmpty(cgeneralbid)){
					if(map.containsKey(cspaceid)){
						map.put(cspaceid, map.get(cspaceid)+1);
					}else{
						int maxfloornum = getMaxFloorNum(cspaceid, cwarehouseidb);
						map.put(cspaceid,maxfloornum+1);
					}
					String[] pk = Proxy.getItf().getPKFromDB(pk_corp, 2);
					FloorVO flvo = new FloorVO();
					flvo.setVdef11(pk[0]);
					m_billvo.getItemVOs()[i].setAttributeValue("vfree10", pk[0]);
					flvo.setPk_floor(pk[1]);
					flvo.setPk_cargdoc(cspaceid);
					flvo.setPk_store(cwarehouseidb);
					flvo.setDr(new Integer(0));
					flvo.setIsout(new UFBoolean(false));
					flvo.setFloornum(map.get(cspaceid));
					Proxy.getItf().insertVOsWithPK(new FloorVO[]{flvo});
				}
			}
			List<String> vdef11dellist  = new ArrayList<String>();
			if(delitemvos!=null){
				for (int j = 0; j < delitemvos.length; j++) {
					String vfree10 = delitemvos[j].getVfree10();
					vdef11dellist.add(vfree10);
				}
				String[] vdef11del = vdef11dellist.toArray(new String[0]);
				String vdef11dels = Toolkits.combinArrayToString(vdef11del);
				IUAPQueryBS qbs = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
				List<FloorVO> listdelfvo = (List<FloorVO>) qbs.executeQuery("select * from bd_floor where vdef11 in "+vdef11dels+" order by floornum desc ", new BeanListProcessor(FloorVO.class));
				Toolkits.deleteFloor_46(listdelfvo);	//行删除恢复层号	
			}
			return m_billvo;
		}
		
		/**
		 * 
		* 功能 查找当前货位的最大层号
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:20:03 
		* 返回 int    返回类型
		 */
		public static int getMaxFloorNum(String cspaceid,String cwarehouseidb) throws BusinessException{
			//先查该仓库的该货位有几张板,来生成层号
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(*) from ic_onhandnum_b where cspaceid='"+cspaceid+"' ") 
			.append("  and cwarehouseidb='"+cwarehouseidb+"' ") 
			.append(" and nvl(dr,0)=0 and nvl(nastnum,0)>0");
			String alreadycount = Proxy.getItf().queryStrBySql(sb.toString());
			return alreadycount==null?0:Integer.parseInt(alreadycount);
		}
		
		/**
		 * 
		* 功能 查找当前货位的最大层号,因为转库删除现存量货位已经更新，所有不包含当前界面的自由项10
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-11 上午10:20:03 
		* 返回 int    返回类型
		 */
		public static int getMaxFloorNumForCargdoc(String cspaceid,String cwarehouseidb,String vfree10) throws BusinessException{
			//先查该仓库的该货位有几张板,来生成层号
			StringBuffer sb = new StringBuffer();
			sb.append(" select count(*) from ic_onhandnum_b where cspaceid='"+cspaceid+"' ") 
			.append("  and cwarehouseidb='"+cwarehouseidb+"' ") 
			.append(" and nvl(dr,0)=0 and nvl(nastnum,0)>0 and vfreeb10 not in (select vfreeb10 from ic_onhandnum_b where vfreeb10 in "+vfree10+")");
			String alreadycount = Proxy.getItf().queryStrBySql(sb.toString());
			return alreadycount==null?0:Integer.parseInt(alreadycount);
		}
		/**
		 * 
		* 功能 销售出库回写派车单
		* 作者 朱永波/zyb/zhyb 
		* 时间 2012-4-13 上午11:49:58 
		* 返回 void    返回类型
		 */
		  public static void backPc(String pk_pcbill,String flag){
			  String sql = "update gt_pcbill set flag_issue='"+flag+"' where nvl(dr,0)=0 and pk_pcbill='"+pk_pcbill+"';";
			  try {
				Proxy.getItf().updateBYsql(sql);
			} catch (BusinessException e) {
				e.printStackTrace();
			}			  
		  }
		
		public static double getThreeWeightRound(double value){
			return Arith.round(value, 3);
		}
}
