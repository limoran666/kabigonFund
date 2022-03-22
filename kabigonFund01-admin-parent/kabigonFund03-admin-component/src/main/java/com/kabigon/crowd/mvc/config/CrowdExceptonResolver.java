package com.kabigon.crowd.mvc.config;

import com.google.gson.Gson;
import com.kabigon.crowd.exception.LoginAcctAlreadyInUseException;
import com.kabigon.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.kabigon.crowd.exception.LoginFailedException;
import com.kabigon.crowd.util.CrowdUtil;
import com.kabigon.crowd.util.ResultEntity;
import com.kabigon.crowd.constant.CrowdConstant;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice//表示当前类是一个基于注解的异常处理器类
public class CrowdExceptonResolver {


    @ExceptionHandler(value = Exception.class)
    public ModelAndView resolveLoginFailedException(Exception Exception,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String viewName="admin-login";
        return commonResolve(viewName,Exception,request,response);
    }

    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveSaveFailedException(LoginAcctAlreadyInUseException loginAcctAlreadyInUseException,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String viewName="admin-add";
        return commonResolve(viewName,loginAcctAlreadyInUseException,request,response);
    }
    @ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
    public ModelAndView resolveUpdateFailedException(LoginAcctAlreadyInUseException loginAcctAlreadyInUseException,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String viewName="system-error";
        return commonResolve(viewName,loginAcctAlreadyInUseException,request,response);
    }














//ExceptionHandler将一个具体的异常类型和一个方法关联起来
@ExceptionHandler(value = NullPointerException.class)
public ModelAndView resolverNullPointException(NullPointerException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {//当前请求对象 和实际捕获到的异常类型

    String viewName="system.error";
    return commonResolve(viewName,exception,request,response);

}

private ModelAndView commonResolve(String viewName,Exception exception,HttpServletRequest request,HttpServletResponse response) throws IOException {
    //1.判断当前请求类型
    boolean judgeRequestType = CrowdUtil.judgeRequestType(request);
    //2如果是ajax请求
    if (judgeRequestType){
        //3.创建ResultEntity对象
        ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());

        //创建json对象
        Gson gson=new Gson();

        //将ResultEntity对象转换为JSON字符串
        String json = gson.toJson(resultEntity);

        //将json响应给浏览器
        response.getWriter().write(json);

        //上面已经返回对象 所以不提供model and view

        return null;
    }

    //如果不是Ajax请求 则创建ModelAndView对象
    ModelAndView modelAndView = new ModelAndView();

    //将Exception对象存入模型
    modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION,exception);

    //设置对应的视图名称
    modelAndView.setViewName(viewName);

    return modelAndView;

}


}
