package com.zhongda.museum.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zhongda.museum.model.AccessToken;
import com.zhongda.museum.utils.WeiXinUtil;

@Component
public class TokenTask {
	
	
    /**
     * 从0点开始，每3小时执行一次
     * @return
     */
	@Scheduled(cron = "0 0 0/3 * * ?")
	public void getAccessTokenA(){
		WeiXinUtil.accessToken = getAccessToken();
	}
	
	/**
	 * 从1点半开始，每3小时执行一次
	 * @return
	 */
	@Scheduled(cron = "0 30 1/3 * * ?")
	public void getAccessTokenB(){
		WeiXinUtil.accessToken = getAccessToken();
	}
	
	/**
	 * 获取微信access_token,如果没有获取到，休眠一秒后再次获取
	 * @return
	 */
	private AccessToken getAccessToken() {
		AccessToken token = WeiXinUtil.getAccessToken();
		while(null == token){
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