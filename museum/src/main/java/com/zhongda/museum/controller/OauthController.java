package com.zhongda.museum.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhongda.museum.constant.WeiXinConfigConstant;
import com.zhongda.museum.model.OauthToken;
import com.zhongda.museum.model.User;
import com.zhongda.museum.service.UserService;
import com.zhongda.museum.utils.JwtTokenUtils;
import com.zhongda.museum.utils.ShiroUtils;
import com.zhongda.museum.utils.TokenUtils;
import com.zhongda.museum.utils.WeiXinUtils;

@Controller
@RequestMapping("/oauth")
@Api(tags = { "微信权限操作接口" })
public class OauthController {

	private static Logger logger = LoggerFactory
			.getLogger(OauthController.class);

	@Resource
	private UserService userService;
	
	/**
	 * 组装授权url
	 * @param resultUrl 需重定向的url
	 * @return
	 */
    @RequestMapping(value ="/oauthUrl")
    @ApiOperation(value = "构造授权url,并进行微信授权", httpMethod = "GET", response = String.class, notes = "构造授权url,并进行微信授权")
	@ApiImplicitParams({ @ApiImplicitParam(name = "resultUrl", value = "需重定向的url", required = true, dataType = "String", paramType = "query") })
    public String oauthUrl(String resultUrl) {
        if (resultUrl != null) {
        	User user = ShiroUtils.getCurrentUser();
        	if(null == user){
        		//构造重定向url
                resultUrl =WeiXinConfigConstant.BASE_URL+"/oauth/authorize?authorizeUrl="+resultUrl;
                //组装授权url
                resultUrl = String.format(WeiXinConfigConstant.GET_CODE_Y_URL, WeiXinConfigConstant.APP_ID, resultUrl);
        	}
            logger.info("resultUrl:" + resultUrl);
            return "redirect:" + resultUrl;
        }else{
        	return "redirect:" + WeiXinConfigConstant.FRONT_BASE_URL + "/error?msg=没有url，无法授权";
        }
    }
	
	@RequestMapping("/authorize")
	public String authorize(String code, String authorizeUrl) {
		//如果用户同意授权
		if (!"authdeny".equals(code)) {
			OauthToken oauthToken = WeiXinUtils.getOauthToken(code);
			User user = userService.selectByOpenid(oauthToken.getOpenid());
			if (null == user) {
				user = WeiXinUtils.getUserInfo(oauthToken);
				userService.insertUser(user);
			}
			Map<String, Object> claims = new HashMap<String, Object>();
			claims.put("openid", user.getOpenid());
			claims.put("userName", user.getNickname());
			String jwtToken = JwtTokenUtils.createJsonWebToken(claims);
			logger.info(authorizeUrl);
			return "redirect:" + authorizeUrl + "?" + TokenUtils.DEFAULT_TOKEN_NAME + "=" + jwtToken;
		}else{
			return "redirect:" + WeiXinConfigConstant.FRONT_BASE_URL + "/error?msg=用户拒绝授权";
		}
	}
	
	@RequestMapping("/test")
	public String Test() {
		return "index";
	}
}
