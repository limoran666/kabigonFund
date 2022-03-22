package com.kabigon.crowd.mvc.interceptor;

import com.kabigon.crowd.constant.CrowdConstant;
import com.kabigon.crowd.exception.AccessForbiddenException;
import com.kabigon.entity.Admin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.通过request对象获取Session对象
        HttpSession session = request.getSession();

        // 2.尝试从session域中获取Admin对象
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
        // 3.判断admin对象是否为空
        if (admin==null){
            throw  new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);

        }
        //如果此时session里有admin对象 就放行
        return true;
    }
}
