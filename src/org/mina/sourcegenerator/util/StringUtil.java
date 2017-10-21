package org.mina.sourcegenerator.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {

	public static boolean isNotNull(String str) {
		if (str == null)
			return false;
		if ("".equals(str.trim()))
			return false;
		return true;
	}

	public static boolean isEmpty(String str) {
		if (str == null)
			return true;
		if ("".equals(str.trim()))
			return true;
		return false;
	}

	public static String trim(String str) {
		if (str == null)
			return null;
		return reverse(reverse(str.trim()).trim());
	}

	public static String reverse(String str) {
		if (str == null)
			return null;
		String ret = "";
		for (int i = 0; i < str.length(); i++) {
			ret = str.substring(i, i + 1) + ret;
		}
		return ret;
	}

	public static String toString(String str) {
		if (str == null)
			return "";
		return trim(str);
	}

	public static int length(String str) {
		if (str == null)
			return 0;

		return str.length();
	}

	public static boolean isValidPwd(String str) {
		String exp = "^[a-zA-Z0-9;',.@#$%&()_=+]*";
		Pattern p = Pattern.compile(exp);
		Matcher m = p.matcher(str);
		boolean b = m.matches();
		return b;
	}

	public static boolean isValidUid(String str) {
		return isValidPwd(str);
	}
	/**
	 * 驼峰命名法转换
	 * @param str
	 * @return
	 */
	public static String camel(String str) {
		if (isEmpty(str)) return "";		
		String s[] = str.split("_");
		if (s.length == 1) return str;
		
		String ret = s[0];
		for (int i=1;i<s.length;i++) {
			if (isEmpty(s[i])) continue;
			String s1 = s[i].substring(0, 1);
			String s2 = s[i].substring(1, s[i].length());			
			ret = ret + s1.toUpperCase() + s2;			
		}		
		return ret;
	}	
	
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
