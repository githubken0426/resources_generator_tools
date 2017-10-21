package org.mina.sourcegenerator.util;

public class Util {
	public static void main(String[] args) {
		System.out.println(StringUtil.getUUID());
		String[] str = {
				"abcd____efg",
				"",
				" ",
				"a",
				"abcdefg",
				"abcd_efg",
				"abcd_efg_hijk_lmn",
				"____abcd_efg",
				"abcd_abc_________"
		};
		
		for (String s : str) {
			System.out.println( s + "=" +	StringUtil.camel(s));
		}
	}
}
