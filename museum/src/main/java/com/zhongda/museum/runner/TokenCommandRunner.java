package com.zhongda.museum.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.zhongda.museum.constant.WeiXinConfigConstant;
import com.zhongda.museum.model.menu.Button;
import com.zhongda.museum.model.menu.CommonButton;
import com.zhongda.museum.model.menu.ComplexButton;
import com.zhongda.museum.model.menu.Menu;
import com.zhongda.museum.utils.WeiXinUtils;

@Component
public class TokenCommandRunner implements CommandLineRunner {

	@Override
	public void run(String... args) {
		
		// 自定义菜单
		CommonButton btn = new CommonButton();
		btn.setName("语音导览");
		btn.setType("view");
		btn.setUrl(WeiXinConfigConstant.BASE_URL + "/oauth/oauthUrl?resultUrl=" + WeiXinConfigConstant.FRONT_BASE_URL);

		ComplexButton mainBtn = new ComplexButton();
		mainBtn.setName("博物馆");
		mainBtn.setSub_button(new Button[] { btn });
		
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn });

		WeiXinUtils.createMenu(menu);
	}
}
