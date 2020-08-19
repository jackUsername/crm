package com.xujie.crm.workbench.contrlloer;


import com.xujie.crm.setting.dao.UserDao;
import com.xujie.crm.setting.domian.User;
import com.xujie.crm.setting.utils.DateTimeUtil;
import com.xujie.crm.setting.utils.UUIDUtil;
import com.xujie.crm.workbench.dao.ActivityDao;
import com.xujie.crm.workbench.domain.Activity;
import com.xujie.crm.workbench.domain.Clue;
import com.xujie.crm.workbench.service.ActivityService;
import com.xujie.crm.workbench.service.ClueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(("/workbench"))
public class ClueContrlloer {
    @Autowired
    private ClueService service;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ActivityService activityService;
@RequestMapping("/clue/getUserList.do")
@ResponseBody
    public List<User> getUserList(){
    System.out.println("所有者响应了");
       List<User> list= userDao.selectUser();
       return list;
    }

    @RequestMapping("/clue/save.do")
    @ResponseBody
    public    String   save(Clue clue, HttpServletRequest request){
    clue.setId(UUIDUtil.getUUID());
    clue.setCreateTime(DateTimeUtil.getSysTime());
    clue.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
    System.out.println(clue.toString());
    String fale=service.save(clue);
    return  fale;
    }

  @RequestMapping("/clue/detail.do")
    public ModelAndView detail(String id){
    System.out.println("马云响应了");
       Clue c= service.detail(id);
       ModelAndView mv=new ModelAndView();
       mv.addObject("c",c);
       mv.setViewName("forward:/workbench/clue/detail.jsp");
       return mv;
    }

    @RequestMapping("/clue/getActivityListByClueId.do")
    @ResponseBody
    public  List<Activity> getActivityListByClue(String clueId){
    System.out.println("根据线索id查询市场活动信息页");
    List<Activity> alist=activityService.getActivityListByClue(clueId);
    return  alist;
    }
    @RequestMapping("/clue/unbund.do")
    @ResponseBody
    public String  unbund (String id){
           System.out.println("执行解除关联操作");
          String falg= service.unbund(id);
          return  falg;
    }

    @RequestMapping("/clue/getActivityListByNameAndNotByClueId.do")
    @ResponseBody
    public List<Activity> getActivityListByNameAndNotByClueId(String aname,String clueId){
           System.out.println("查询市场活动列表（按照名称查询）");
        Map<String,String>map =new HashMap<String ,String>();
        map.put("aname",aname);
        map.put("clueId",clueId);
        List<Activity> alist=activityService.getActivityListByNameAndNotByClueId(map);
        return  alist;
    }
    @RequestMapping("/clue/bund.do")
    @ResponseBody
    public  String   bund (String cid,HttpServletRequest request){
                  System.out.println("执行市场活动的操作");
                 String aids[]=request.getParameterValues("aid");
                       String flage= service.bund(cid,aids);
                       return flage;

    }
}