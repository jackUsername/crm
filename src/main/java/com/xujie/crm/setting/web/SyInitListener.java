package com.xujie.crm.setting.web;

import com.xujie.crm.setting.domian.DicValue;
import com.xujie.crm.setting.servlet.DicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class SyInitListener implements ServletContextListener {
    //该方法用来监听上下文域对象的方法，当服务器启动时，上下文域对象创建对象创建完毕，马上执行该方法
    @Autowired
    private  DicService dicService;
    @Override
    public void contextInitialized(ServletContextEvent event) {
       System.out.println("上下文与对象响应了");
        ServletContext appliction=event.getServletContext();
        //取数据字典
        //7个List
       Map<String, List<DicValue>> map=dicService.getAll();
        Set<String> set =map.keySet();
        for (String key:set){
            appliction.setAttribute(key,map.get(key));
        }
        //appliction.setAttribute("key",数据字典);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
