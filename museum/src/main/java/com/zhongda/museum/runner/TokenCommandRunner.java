package com.zhongda.museum.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.zhongda.museum.constant.WeiXinConfigConstant;
import com.zhongda.museum.model.AccessToken;
import com.zhongda.museum.model.menu.Button;
import com.zhongda.museum.model.menu.CommonButton;
import com.zhongda.museum.model.menu.ComplexButton;
import com.zhongda.museum.model.menu.Menu;
import com.zhongda.museum.utils.WeiXinUtils;

@Component
public class TokenCommandRunner implements CommandLineRunner {

	@Override
	public void run(String... args) {
		// 获取accessToken
		WeiXinUtils.accessToken = getAccessToken();
		// 自定义菜单
		CommonButton btn = new CommonButton();
		btn.setName("语音导览");
		btn.setType("view");
		String oauth_url = String.format(WeiXinConfigConstant.GET_CODE_Y_URL, WeiXinConfigConstant.APP_ID, WeiXinConfigConstant.HOME_URL);
		System.out.println("oauth_url:" + oauth_url);
		btn.setUrl(oauth_url);

		ComplexButton mainBtn = new ComplexButton();
		mainBtn.setName("博物馆");
		mainBtn.setSub_button(new Button[] { btn });
		
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn });

		WeiXinUtils.createMenu(menu);
	}

	/**
	 * 获取微信access_token,如果没有获取到，休眠一秒后再次获取
	 * 
	 * @return
	 */
	private AccessToken getAccessToken() {
		AccessToken token = WeiXinUtils.getAccessToken();
		while (null == token) {
			try {
				Thread.sleep(1000);
				token = WeiXinUtils.getAccessToken();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return token;
	}
}
