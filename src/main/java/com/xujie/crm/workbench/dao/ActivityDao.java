package com.xujie.crm.workbench.dao;

import com.xujie.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity activity);

    List<Activity> getActivityListByCoundtion(Map<String, Object> map);

    int getTOtalByCondition(Map<String, Object> map);

    int delect(String[] ids);

    Activity getById(String id);

    int update(Activity activity);

    Activity detail(String id);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);
}
