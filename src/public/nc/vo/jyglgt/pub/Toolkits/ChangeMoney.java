package nc.vo.jyglgt.pub.Toolkits;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ChangeMoney {

	private static final String UPPERNUM = "零壹贰叁肆伍陆柒捌玖";
	private static final String UNIT = "个拾佰仟";
	private static final String GRADEUNIT = "万亿兆";
	private static final int GRADE = 4;
	private static final String DECIMAL = "角分";
	private static final NumberFormat nf = new DecimalFormat("0.00");// 对输入的金额数值进行格式化

	public static String toBigMoney(double amount) {
		String amt = nf.format(amount);// 格式化金额数值（保留两位小数）
		String intPart = "";// 整数位
		String dotPart = "";// 小数位
		int dotPos;// 小数点位置
		if ((dotPos = amt.indexOf(".")) != -1) {// 存在小数点
			intPart = amt.substring(0, dotPos);// 提取整数部分
			dotPart = amt.substring(dotPos + 1);// 提取小数部分
		} else {// 不存在小数点
			intPart = amt;
		}
		if (intPart.length() > 16) {// 数值过大抛出异常
			throw new java.lang.Error("The Money is too big.");
		}
		String intStr = intToBig(intPart);// 转化整数部分为大写
		String dotStr = dotToBig(dotPart);// 转化小数部分为大写
		if (intStr.length() == 0 && dotStr.length() == 0) {
			return "零元";
		} else if (intStr.length() != 0 && dotStr.length() == 0) {
			return intStr + "元整";
		} else if (intStr.length() != 0 && dotStr.length() != 0) {
			return intStr + "元" + dotStr;
		} else {
			return dotStr;
		}
	}

	// 将整数部分转化为大写
	private static String intToBig(String intPart) {
		int grade = intPart.length() / GRADE;// 获取级长
		String strTmp = "";// 获取某级字符串
		String result = "";// 获取转化为大写的结果
		if (intPart.length() % GRADE != 0) {
			grade = grade + 1;
		}
		// 对每级数字处理，先处理最高级，然后再处理低级的
		for (int i = grade; i > 0; i--) {
			strTmp = getNowGradeStr(intPart, i);// 获取某级金额数值字符串
			result += changeToSub(strTmp);// 转化为大写
			result = delZero(result);// 去除连续的零
			if (i > 1) {
				if (changeToSub(strTmp).equals("零零零零")) {
					result = result + "零";
				} else {
					result += GRADEUNIT.substring(i - 2, i - 1);
				}
			}
		}
		return result;
	}

	// 去除连续的零
	private static String delZero(String result) {
		String strBefore = "";
		String strNow = "";
		String strResult = result.substring(0, 1);
		strBefore = strResult;
		for (int i = 1; i < result.length(); i++) {
			strNow = result.substring(i, i + 1);
			if (strBefore.equals("零") && strNow.equals("零")) {
				;
			} else {
				strResult += strNow;
			}
			strBefore = strNow;
		}
		if (strResult.substring(strResult.length() - 1, strResult.length())
				.equals("零")) {
			strResult = strResult.substring(0, strResult.length() - 1);
		}
		// System.out.println(strResult+"g");
		return strResult;
	}

	// 获取转化后的大写结果
	private static String changeToSub(String strTmp) {
		String result = "";
		int strLength = strTmp.length();
		for (int i = 0; i < strLength; i++) {
			int num = Integer.parseInt(String.valueOf(strTmp.charAt(i)));
			if (num == 0) {
				result += "零";
			} else {
				result += UPPERNUM.substring(num, num + 1);
				if (i != strLength - 1) {
					result += UNIT.substring(strLength - 1 - i, strLength - i);
				}
			}
		}
		// System.out.println(result+"g");
		return result;
	}

	// 获取某级金额数值字符串
	private static String getNowGradeStr(String intPart, int grade) {
		String rsStr = "";
		if (intPart.length() <= grade * GRADE) {
			rsStr = intPart
					.substring(0, intPart.length() - (grade - 1) * GRADE);
		} else {
			rsStr = intPart.substring(intPart.length() - grade * GRADE, intPart
					.length()
					- (grade - 1) * GRADE);
		}
		// System.out.println(rsStr + "  " + grade);
		return rsStr;
	}

	// 将小数部分转化为大写
	private static String dotToBig(String dotPart) {
		String dotStr = "";
		for (int i = 0; i < dotPart.length(); i++) {
			int num = Integer.parseInt(dotPart.substring(i, i + 1));
			if (num != 0) {
				dotStr += UPPERNUM.substring(num, num + 1)
						+ DECIMAL.substring(dotPart.length() - 2 + i, dotPart
								.length()
								+ i - 1);
			}
		}
		return dotStr;
	}

	public static void main(String[] args) {
		System.out.println(ChangeMoney.toBigMoney(0.2D));
		System.out.println(ChangeMoney.toBigMoney(1256666640340.05D));
		System.out.println(ChangeMoney.toBigMoney(10.02D));
		System.out.println(ChangeMoney.toBigMoney(100000000.2D));
	}
}
