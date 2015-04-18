package nc.vo.jyglgt.pub.Toolkits;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
  
/** 
 * JAVA获得一个数组的指定长度的排列组合。<br> 
 */  
public class SequenceAll {  
//  public static void main(String[] args) {  
//    SequenceAll t = new SequenceAll();  
//    String[] arr = { "a", "b", "c","d" };  
//    // 循环获得每个长度的排列组合  
////    for (int num = 1; num <= arr.length; num++) {  
//    List<String[]> result = new ArrayList<String[]>();
////    t.getSequence(arr, 0, 2,result);  
//    for (String[] strings : result) {
//    	String msg = "";
//		for (int i = 0; i < strings.length; i++) {
//			msg += strings[i] +",";
//		}
//		System.out.println(msg);
//	}
////    }  
//  }  
  
  // 存储结果的堆栈  
  private Stack<String> stack = new Stack<String>();  
  
  /** 
   * 获得指定数组从指定开始的指定数量的数据组合<br> 
   *  
   * @param favoarr 指定的数组 
   * @param begin 开始位置 
   * @param num 获得的数量 
 * @param result 
   */  
  public void getSequence(String[] favoarr, int begin, int num, List<String[]> result) {  
    if (num == 0) {  
//      System.out.println(stack); // 找到一个结果  
      String[] obj = stack.toArray(new String[0]);
      addResult(result,obj);
    } else {  
      // 循环每个可用的元素  
      for (int i = begin; i < favoarr.length; i++) {  
        // 当前位置数据放入结果堆栈  
        stack.push(favoarr[i]);  
        // 将当前数据与起始位置数据交换  
        swap(favoarr, begin, i);  
        // 从下一个位置查找其余的组合  
        getSequence(favoarr, begin + 1, num - 1,result);  
        // 交换回来  
        swap(favoarr, begin, i);  
        // 去除当前数据  
        stack.pop();  
      }  
    }  
  }  
  
  private void addResult(List<String[]> resultlist, String[] obj) {
	  if(resultlist.size() == 0){
		  resultlist.add(obj);
	  }
	  List<String> objlist = Arrays.asList(obj);
	  for (String[] resultarr : resultlist) {
		  List<String> relist = Arrays.asList(resultarr);
		  if(relist.containsAll(objlist)){
			  return;
		  }
	  }
	  resultlist.add(obj);
  }

/** 
   * 交换2个数组的元素 
   *  
   * @param arr 数组 
   * @param from 位置1 
   * @param to 位置2 
   */  
  public static void swap(String[] arr, int from, int to) {  
    if (from == to) {  
      return;  
    }  
    String tmp = arr[from];  
    arr[from] = arr[to];  
    arr[to] = tmp;  
  }  
  
//  void combine(String str) {
//		char[] in = str.toCharArray();
//		StringBuffer out = new StringBuffer();
//		allCombine(in, out, 0);
//	}
//
//	void allCombine(char[] in, StringBuffer out, int start) {
//		for (int i = start; i < in.length; i++) {
//			out.append(in[i]);
//			System.out.println(out);
//			if (i < in.length - 1) {
//				allCombine(in, out, i + 1);
//			}
//			out.setLength(out.length() - 1);
//		}
//	}
	
	
}  