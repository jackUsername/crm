package com.xujie.crm.workbench.service.lmpl;

import com.xujie.crm.setting.dao.UserDao;
import com.xujie.crm.setting.domian.User;
import com.xujie.crm.workbench.dao.ActivityDao;
import com.xujie.crm.workbench.dao.ActivityRemarkDao;
import com.xujie.crm.workbench.domain.Activity;
import com.xujie.crm.workbench.domain.ActivityRemark;
import com.xujie.crm.workbench.service.ActivityService;
import com.xujie.crm.workbench.vo.PaginationVo;
import com.xujie.crm.workbench.vo.UpdateVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
@Resource
private    ActivityDao activityDao;
@Resource
private ActivityRemarkDao activityRemarkDao;

@Resource
private UserDao userDao;
    @Override
    public String save(Activity activity) {

        int i=  activityDao.save(activity);
        if(i==1){
            return "添加成功";
        }
         return  "添加失败";
    }

   /* @Override
    public PaginationVo<Activity> pageList(Map<String, Object> map) {
        //取得total
       int total= activityDao.getTOtalByCondition(map);
        //取得dataList
       List<Activity> dataList= activityDao.getActivityListByCoundtion(map);
        //将tatal和dataLIst封装到vo中
        PaginationVo<Activity> vo=new PaginationVo<Activity>();
        vo.setDataList(dataList);
        vo.setTotal(total);
        //取得vo返回
        return vo;
    }*/
   @Override
   public PaginationVo<Activity> pageList(Map<String, Object> map) {
       //取得total
       int total= activityDao.getTOtalByCondition(map);
       //取得dataList
       List<Activity> dataList= activityDao.getActivityListByCoundtion(map);
       //将tatal和dataLIst封装到vo中
       PaginationVo<Activity> vo=new PaginationVo<Activity>();
       vo.setDataList(dataList);
       vo.setTotal(total);
       //取得vo返回
       return vo;
   }

    @Override
    public int  delect(String[] ids) {
       boolean flag=true;
       //查询出需要删除的备注的数量
       int count1=activityRemarkDao.getCountByAids(ids);

       int count2=activityRemarkDao.deletByAids(ids);


       if(count1==count2){
           flag=false;
       }
       //删除市场活动
       int count3=activityDao.delect(ids);
       if(count3!=ids.length){
           flag=false;
       }
        return count3;
    }

    @Override
    public UpdateVo getUserListAndActivity(String id) {
        //取ulist
        List uList=userDao.selectUser();
        //取a
        Activity a=activityDao.getById(id);
        /*//取ulist和a打包到map中
        Map<String , Object> map=new HashMap<String,Object>();
        map.put("uList",uList);
        map.put("a",a);
        return  map;*/
        UpdateVo<Object> vo=new UpdateVo<>();
        vo.setActivity(a);
        vo.setDataList(uList);
        return  vo;
    }

    @Override
    public String upate(Activity activity) {
        int i=  activityDao.update(activity);
        if(i==1){
            return "修改成功";
        }
        return  "修改失败";
    }

    @Override
    public Activity detail(String id) {
       Activity a= activityDao.detail(id);
       return  a;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {
        return activityRemarkDao.getRemarkListByAid(activityId);
    }

    @Override
    public String delectRemark(String id) {
       //String flag;
        int count=activityRemarkDao.deleteById(id);
        if(count==1){
            return "删除成功";
        }
        return  "删除失败";
    }

    @Override
    public String saveRemark(ActivityRemark ar) {
       int count= activityRemarkDao.saveRemark(ar);
       if(count!=1){
           return  "添加失败";
        }
        return  "添加成功";
    }

    @Override
    public String updateRemark(ActivityRemark ar) {
        int count=activityRemarkDao.updateRemaker(ar);
        if(count!=1){
            return  "修改失败";
        }
        return  "修改成功";
    }

    @Override
    public List<Activity> getActivityListByClue(String clueId) {
       return  activityDao.getActivityListByClueId(clueId);

    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {
        return  activityDao.getActivityListByNameAndNotByClueId(map);
    }

}
