package com.xujie.crm.setting.controller;


import com.xujie.crm.setting.domian.User;
import com.xujie.crm.setting.exception.LoginExceotion;
import com.xujie.crm.setting.servlet.UserService;
import com.xujie.crm.setting.utils.DateTimeUtil;
import com.xujie.crm.setting.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/setting")
public class UserController {
    @Resource
  private UserService userService;

    @RequestMapping("/save.do")
    @ResponseBody
    public User  inserUser(String loginAct, String loginPaw, HttpServletRequest request,HttpSession session) throws LoginExceotion {
        System.out.println("登录验证");
       String password= MD5Util.getMD5(loginPaw);
       String ip=request.getRemoteAddr();
       System.out.println(password);
        System.out.println(loginAct);
      User user= userService.login(loginAct,password,ip);
      System.out.println(user);
      if(user==null){
          throw  new LoginExceotion("账号密码错误");
      }
     /*  String expireTime=user.getExpireTime();
      String  currentTime= DateTimeUtil.getSysTime();
      if(expireTime.compareTo(currentTime)<0){
          throw  new LoginExceotion("账号失效");
      }
      String  lockState=user.getLockState();
      if("0".equals(lockState)){
          throw  new LoginExceotion("账号锁定");
      }
      String allowIps=user.getAllowIps();
      if(!allowIps.contains(ip)){
          throw  new LoginExceotion("ip地址受限");
      }*/
       session.setAttribute("user",user);
      return  user;

    }

    @RequestMapping(value = "/getUser.do")
    @ResponseBody
    public List<User> selectUser(){
        System.out.println("ajax响应了");
       List<User> list = userService.selectUser();
       return  list;
    }
}
