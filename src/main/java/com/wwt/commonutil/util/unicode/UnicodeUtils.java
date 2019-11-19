package com.wwt.commonutil.util.unicode;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  unicode 工具类 
 * @author wangwenteng
 *
 */
public class UnicodeUtils {
	
	public static String unicodeToString(String unicodeStr){
		String str = null;
		if (StringUtils.isNotEmpty(unicodeStr)) {
			Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
			Matcher matcher = pattern.matcher(unicodeStr);
			char ch;
			while (matcher.find()) {
				ch = (char) Integer.parseInt(matcher.group(2), 16);
				str = str.replace(matcher.group(1), ch + "");
			}
		}
		return str;
	}
}
