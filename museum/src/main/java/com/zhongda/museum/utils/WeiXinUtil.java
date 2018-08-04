package com.zhongda.museum.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhongda.museum.constant.WeiXinConfigConstant;
import com.zhongda.museum.model.AccessToken;
import com.zhongda.museum.model.menu.Menu;

public class WeiXinUtil {
	
	private static Logger logger = LoggerFactory.getLogger(WeiXinUtil.class);
    // 与接口配置信息中的Token要一致
    public static AccessToken accessToken = null;
 
    /**
     * 验证签名
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] array = new String[] { WeiXinConfigConstant.TOKEN, timestamp, nonce };
        // 将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(array);  

        StringBuilder content = new StringBuilder();
        for(String str : array) {
        	content.append(str);
        }
        
        String tmpStr = null;
        try {
        	MessageDigest md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        // 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }
 
    /**
     * 将字节数组转换为十六进制字符串
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }
 
    /**
     * 将字节转换为十六进制字符串
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
    
    /**
	 * 获取微信access_token
	 * @return
	 */
	public static AccessToken getAccessToken() {
		String Url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", WeiXinConfigConstant.APP_ID, WeiXinConfigConstant.APP_SECRET);
        String result = HttpClientUtil.httpGetRequest(Url);
        logger.info(result);
        ObjectMapper mapper = new ObjectMapper();
        AccessToken token = null;
		try {
			token = mapper.readValue(result, AccessToken.class);
		} catch (Exception e) {
			e.printStackTrace();
		} 
        return token;
	}
	
	/**
	 * 自定义菜单
	 * @param menu 菜单对象
	 */
	public static void createMenu(Menu menu) {
		if(null == accessToken){
			accessToken = getAccessToken();
		}
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken.getAccessToken();
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = null;
		try {
			jsonData = mapper.writeValueAsString(menu);
			String result = HttpClientUtil.httpPostRequest(url, jsonData);
			logger.info(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}