package com.kabigon.crowd.filter;

import com.kabigon.crowd.constant.AccessPassResources;
import com.kabigon.crowd.constant.CrowdConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.protocol.RequestContent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CrowdAccessFilter extends ZuulFilter {


    @Override
    public String filterType() {
        //这里返回“pre”意思是在目标微服务前执行过滤
        return "pre";

    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //获取requestContext
        RequestContext requestContext = RequestContext.getCurrentContext();

        //通过RequestContext对象获取当前请求对象 框架底层借用的是threadlocal
        HttpServletRequest request = requestContext.getRequest();

        //获取servletPath值
        String servletPath = request.getServletPath();

        //根据servletPath判断当前请求是否对应可以直接放行的特定功能
        boolean containsResult = AccessPassResources.PASS_RES_SET.contains(servletPath);

        if (containsResult){

            //如果当前请求是可以放行的特定功能请求则返回false
        return false;
        }

        //判断当前请求是否为静态资源// 工具方法返回 true：说明当前请求是静态资源请求，取反为 false 表示放行不做登录检查
        //// 工具方法返回 false：说明当前请求不是可以放行的特定请求也不是静态资源，取反为 true 表示需要做登录检
    return   !AccessPassResources.judgeCurrentServletWheather(servletPath);

    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpSession session = request.getSession();

        //从session获取已登录的用户
        Object loginMember = session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        if (loginMember==null){
            //获取response
            HttpServletResponse response = currentContext.getResponse();

            //将消息存入Session域
            session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_ACCESS_FORBIDEN);

            //重定向 必须  不然zull就会一直打转
            try {
                response.sendRedirect("/auth/member/to/login/page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
