package com.zhongda.museum.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaserXmlUtil {
	
	private static Logger logger = LoggerFactory.getLogger(PaserXmlUtil.class);
	
	public static Map<String, String> parseXml(InputStream inputStream) throws Exception { 
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>(); 

		String body = inputStream2String(inputStream); 
		logger.info(body);
		
		// 读取输入流 
		SAXReader reader = new SAXReader(); 
		Document document = reader.read(inputStream); 
		
		// 得到xml根元素 
		Element root = document.getRootElement(); 
		// 得到根元素的所有子节点 
		@SuppressWarnings("unchecked")
		List<Element> elementList = root.elements(); 
		// 遍历所有子节点
		for (Element e : elementList) 
			map.put(e.getName(), e.getText()); 
		// 释放资源 
		inputStream.close(); 
		inputStream = null; 
		return map; 
	} 
	
	/**
	 * 读取字节流中的数据
	 */
	private static String inputStream2String(InputStream is) throws IOException { 
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		int i = -1; 
		while ((i = is.read()) != -1) {
			baos.write(i); 
		} 
		return baos.toString(); 
	}
}
