package com.xujie.crm.setting.handler;

import com.xujie.crm.setting.domian.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         System.out.println("拦截器");

        String path=request.getServletPath();
/*          if("/login.jsp".equals(path)||"/setting/save.do".equals(path)){
              return  true;
          }else {
              HttpSession session= request.getSession();
              User user= (User) session.getAttribute("name");
              if( user!=null){
                  return  true;
              }else {
                  response.sendRedirect("/login.jsp");
              }
          }*/

   return  true;
    }
}
