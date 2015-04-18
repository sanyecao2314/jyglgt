package nc.work.gdt;

import nc.ui.pub.bill.BillItem;

public class MyUtil {
	private static final String BTABLEPRE = "btablename";
	
	/**
	 * @param items
	 * 找出最大的那个子表
	 * @return 不存在返回-1
	 */
	public static int getMaxIndex(BillItem[] items){
		if(items == null || items.length == 0){
			return -1;
		}
		int index = 1;
		for (int i = 0; i < items.length; i++) {
			String key = items[i].getKey();
			if(key.equals(BTABLEPRE)){
				continue;
			}
			if(key.contains(BTABLEPRE)){
				int intd = Integer.valueOf(key.replace(BTABLEPRE, ""));
				if(intd > index){
					index = intd;
				}
			}
		}
		return index;
	}
}
