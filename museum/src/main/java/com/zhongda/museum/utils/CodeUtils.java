package com.zhongda.museum.utils;

import java.util.Formatter;

/**
 * 类CodeUtils的功能描述:
 * Code编解码工具类
 * @auther dengzm
 */
public class CodeUtils {
	
	/**
	 * 将字节数组转换为十六进制字符串
	 * @param hash
	 * @return
	 */
    public static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02X", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
