package com.zhongda.museum.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.zhongda.museum.constant.WeiXinConfigConstant;
import com.zhongda.museum.model.AccessToken;
import com.zhongda.museum.model.menu.Button;
import com.zhongda.museum.model.menu.CommonButton;
import com.zhongda.museum.model.menu.ComplexButton;
import com.zhongda.museum.model.menu.Menu;
import com.zhongda.museum.utils.WeiXinUtil;

@Component
public class TokenCommandRunner implements CommandLineRunner {

	@Override
	public void run(String... args) {
		// 获取accessToken
		WeiXinUtil.accessToken = getAccessToken();
		// 自定义菜单
		CommonButton btn = new CommonButton();
		btn.setName("语音导览");
		btn.setType("view");
		String oauth_url = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect", WeiXinConfigConstant.APP_ID, WeiXinConfigConstant.HOME_URL);
		System.out.println(oauth_url);
		btn.setUrl(oauth_url);

		ComplexButton mainBtn = new ComplexButton();
		mainBtn.setName("博物馆");
		mainBtn.setSub_button(new Button[] { btn });
		
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn });

		WeiXinUtil.createMenu(menu);
	}

	/**
	 * 获取微信access_token,如果没有获取到，休眠一秒后再次获取
	 * 
	 * @return
	 */
	private AccessToken getAccessToken() {
		AccessToken token = WeiXinUtil.getAccessToken();
		while (null == token) {
			try {
				Thread.sleep(1000);
				token = WeiXinUtil.getAccessToken();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return token;
	}
}
