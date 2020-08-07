package cn.caishen.domain.utils;

public class StringUtil {
	
	public static String fillWithZero(int num,int length){
		String prefix = String.valueOf(num);//int类型转换成字符类型
		 while(prefix.length()<length){//判断长度
			 prefix= "0"+prefix;
		 }
		return prefix;
	}
	
	
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	
	public static String stringAdd(String s, String appendStr, String split) {
		
		if (!isEmpty(appendStr)) {
			if ("".equals(s)) {
				s = appendStr;
			} else {
				if (!isEmpty(split)) {
					s = s + split;
				}
				s = s + appendStr;
			}
		}
		return s;
	}	
	
	public static String qs(String s) {
		String r = "";
		if (s == null || "".equals(s)) {
			r = "''";
		} else {
			r = r.concat("'").concat(s).concat("'");
		}
		return r;
	}	
}
