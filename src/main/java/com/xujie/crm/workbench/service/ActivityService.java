package com.xujie.crm.workbench.service;

import com.xujie.crm.workbench.domain.Activity;
import com.xujie.crm.workbench.domain.ActivityRemark;
import com.xujie.crm.workbench.vo.PaginationVo;
import com.xujie.crm.workbench.vo.UpdateVo;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    String save(Activity activity);
   // List<Activity> list(Map map);
    PaginationVo<Activity> pageList(Map<String, Object> map);

    int delect(String[] ids);

    //Map<String, Object> getUserListAndActivity(String id);
     UpdateVo getUserListAndActivity(String id);

    String upate(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    String delectRemark(String id);

    String saveRemark(ActivityRemark ar);

    String updateRemark(ActivityRemark ar);

    List<Activity> getActivityListByClue(String clueId);

 List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);
}
