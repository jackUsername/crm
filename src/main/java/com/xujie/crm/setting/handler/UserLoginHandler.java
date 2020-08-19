package com.xujie.crm.setting.handler;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;

@ControllerAdvice
public class UserLoginHandler {
      @ExceptionHandler(LoginException.class)
    public String  loginError(Exception ex, HttpSession session){
   /* ModelAndView mv=new ModelAndView();
    mv.addObject("ex",ex);
    mv.setViewName("forward:/login.jsp");
    return  mv;*/
   System.out.println("异常");
    session.setAttribute("ex",ex);
    return  "/login.jsp";
    }

}
