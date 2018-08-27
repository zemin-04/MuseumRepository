package com.zhongda.museum.constant;

public class WeiXinConfigConstant {

	// public static final String APP_ID = "wx1e9eae4dd62c0801";
	public static final String APP_ID = "wx1c0dbb5b637c056d";

	// public static final String APP_SECRET =
	// "ec11f8e1059061d6ac071039034bc38e";
	public static final String APP_SECRET = "960b040a23766fa6606831c3c47fa496";

	/**
	 * 获取微信access_token的url
	 */
	public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

	/**
	 * 创建微信菜单的url
	 */
	public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

	/**
	 * 获取微信code的url,同时设置重定向url(可获取用户完整信息，需授权)
	 */
	public static final String GET_CODE_Y_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";

	/**
	 * 获取微信code的url,同时设置重定向url(只可获取微信用户唯一标识，无需授权)
	 */
	public static final String GET_CODE_N_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=1#wechat_redirect";

	/**
	 * 获取微信oauth_token的url
	 */
	public static final String GET_OAUTH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

	/**
	 * 获取微信用户信息的url
	 */
	public static final String GET_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

	/**
	 * 获取微信jsapi_ticket的url
	 */
	public static final String GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

	/**
	 * 本项目访问的基本url
	 */
	// public static final String BASE_URL =
	// "http://weixin.zjjsmuseum.com/museum";
	public static final String BASE_URL = "http://zjjlmp.vicp.cc/museum";

	/**
	 * 前端项目访问的基本url
	 */
	// public static final String FRONT_BASE_URL =
	// "http://weixin.zjjsmuseum.com";
	public static final String FRONT_BASE_URL = "http://zjjlmp.vicp.cc:29823";

	/**
	 * 微信公众号配置的校验token，验证请求是否是来自微信
	 */
	public static final String TOKEN = "museum";

}
