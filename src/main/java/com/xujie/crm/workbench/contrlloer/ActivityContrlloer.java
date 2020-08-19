package com.xujie.crm.workbench.contrlloer;

import com.xujie.crm.setting.domian.User;
import com.xujie.crm.setting.utils.DateTimeUtil;
import com.xujie.crm.setting.utils.UUIDUtil;
import com.xujie.crm.workbench.domain.Activity;
import com.xujie.crm.workbench.domain.ActivityRemark;
import com.xujie.crm.workbench.service.ActivityService;
import com.xujie.crm.workbench.vo.PaginationVo;
import com.xujie.crm.workbench.vo.UpdateVo;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(("/workbench"))
public class ActivityContrlloer {
 @Autowired
   private  ActivityService service;

    @RequestMapping("/add.do")
    @ResponseBody
    public String  add(Activity activity, HttpServletRequest request){
        System.out.println("添加执行了");
       // System.out.println(activity);
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        activity.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        System.out.println(activity);
        String falg=service.save(activity);
        System.out.println(falg);
        return  falg;
    }



    @RequestMapping("/pageList.do")
    @ResponseBody
    public PaginationVo<Activity> pageList(String name, String owner, String startDate, String endDate, Integer pageNo, Integer pageSize){
      //计算出略过的记录数
        System.out.println("显示请求"+name+owner+startDate+endDate+pageNo+pageSize);
        int skipCount=(pageNo-1)*pageSize;
        Map<String,Object> map =new HashMap<String ,Object>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        PaginationVo<Activity> vo=service.pageList(map);
        System.out.println(vo.getDataList().toString());
        System.out.println("这个是vo"+vo.toString());
        return  vo;
    }

    @RequestMapping(value = "/activity/delete.do" )
    @ResponseBody
    public String  delect(HttpServletRequest request){
        String ids[]=request.getParameterValues("id");
         int flag=service.delect(ids);

         System.out.println("结果是+"+flag);
        //return  "da";
        return "hellow";
    }


    @RequestMapping("/activity/getUserListAndActivity.do")
    @ResponseBody
    public UpdateVo getUserListActivity(String id){
        System.out.println("1111111111111111111111111111");
        UpdateVo vo=service.getUserListAndActivity(id);
        System.out.println(vo.toString());
        System.out.println(vo.getDataList());
        //vo.setActivity();
        return  vo;
    }


    @RequestMapping("/update.do")
    @ResponseBody
    public String  update(Activity activity, HttpServletRequest request){
        System.out.println("修改执行了");
        activity.setEditTime(DateTimeUtil.getSysTime());
        activity.setEditBy(((User)request.getSession().getAttribute("user")).getName());
        System.out.println(activity);
        String falg=service.upate(activity);
        System.out.println(falg);
        return  falg;
    }
     @RequestMapping(value = "/activity/detail.do")
    public  ModelAndView datail(String id){
        System.out.println("进入到跳转页面的操作"+id);
        //model.addAttribute()
        Activity a=service.detail(id);
        ModelAndView mv=new ModelAndView();
        mv.addObject("a",a);
        mv.setViewName("forward:/workbench/activity/detail.jsp");
        return  mv;
    }

    @RequestMapping("/activity/getRemarkListByAid.do")
    @ResponseBody
    public List<ActivityRemark> getRemarkListByAid(String activityId){
         System.out.println("根据市场活动id，取得备注信息列表");
        List<ActivityRemark> arList=service.getRemarkListByAid(activityId);
        return  arList;
    }

    @RequestMapping("/activity/deleteRemark.do")
    public String delectRemark(String id){
        String flas=service.delectRemark(id);
        return  flas;
    }

    @RequestMapping("/activity/saveReark.do")
    @ResponseBody
    public Map<String,Object>  saveRemark(ActivityRemark ar,HttpServletRequest request){
        System.out.println("执行添加操作");
        String id=UUIDUtil.getUUID();
        String createTime=DateTimeUtil.getSysTime();
       String createBy=(((User)request.getSession().getAttribute("user")).getName());
       String editFlag="0";
       /* id
                noteContent
        createTime
                createBy
        editTime
                editBy
        editFlag
                activityId*/
      ar.setCreateBy(createBy);
      ar.setEditFlag(editFlag);
      ar.setId(id);
      ar.setCreateTime(createTime);
     String flag= service.saveRemark(ar);
     Map<String ,Object> map=new HashMap<>();
     map.put("success",ar);
     map.put("ar",ar);
     return  map;
    }

    @RequestMapping("/activity/updateRemark.do")
    @ResponseBody
    public  Map<String,Object> updateRemark(ActivityRemark ar,HttpServletRequest request){
        System.out.println("修改操作");
        String editTime=DateTimeUtil.getSysTime();
        String editBy=(((User)request.getSession().getAttribute("user")).getName());
        String editFlag="1";

        ar.setEditBy(editBy);
        ar.setEditTime(editTime);
        ar.setEditFlag(editFlag);
       String flag= service.updateRemark(ar);

        Map<String ,Object> map=new HashMap<>();
        map.put("success",ar);
        map.put("ar",ar);
        return  map;
    }
}
