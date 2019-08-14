package cn.com.yusys.yufs;

public class StrUtils {
	
	public static String removeQuotation(String msg, String quota) {
		if (msg != null && quota != null && msg.startsWith(quota) && msg.endsWith(quota)) {
			int len = quota.length();
			return msg.substring(len, msg.length() - len);
		} 
		return msg;
	}

}
