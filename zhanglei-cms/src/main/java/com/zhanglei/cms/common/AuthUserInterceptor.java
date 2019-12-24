package com.zhanglei.cms.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.zhanglei.cms.service.UserService;
import com.zhanglei.util.CookieUtil;
import com.zhanglei.util.StringUtil;

public class AuthUserInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object userInfo = request.getSession().getAttribute(CmsConstant.UserSessionKey);
		if(userInfo!=null) {
			return true;
		}
		//记住登陆
		String username = CookieUtil.getCookieByName(request, "username");
		if(StringUtil.isNotBlank(username)){
			UserService userService = SpringBeanUtils.getBean(UserService.class);
			userInfo  = userService.getByUsername(username);
			request.getSession().setAttribute(CmsConstant.UserSessionKey, userInfo);
			return true;
		}
	    response.sendRedirect("/user/login");
		return false;
	}

}
