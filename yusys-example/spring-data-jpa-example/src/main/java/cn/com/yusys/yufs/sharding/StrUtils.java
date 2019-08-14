package cn.com.yusys.yufs.sharding;

public class StrUtils {
	
	public static String leftPaddingZero(int value, int digit) {
		int count = 1;
		while (digit > 10) {
			digit /= 10;
			count++;
		}
		return String.format("%0" + count + "d", value);
	}

}
