package com.xujie.crm.workbench.service.lmpl;

import com.xujie.crm.setting.utils.UUIDUtil;
import com.xujie.crm.workbench.dao.ClueActivityRelationDao;
import com.xujie.crm.workbench.dao.ClueDao;
import com.xujie.crm.workbench.domain.Activity;
import com.xujie.crm.workbench.domain.Clue;
import com.xujie.crm.workbench.domain.ClueActivityRelation;
import com.xujie.crm.workbench.service.ClueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueDao clueDao;
    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao;

    @Override
    public String  save(Clue clue) {
       int num= clueDao.save(clue);
       if(num==1){
           return "添加成功";
       }
       return  "添加失败";
    }

    @Override
    public Clue detail(String id) {
        return  clueDao.detail(id);

    }

    @Override
    public String unbund(String id) {
       int num= clueActivityRelationDao.unband(id);
       if(num==1){
           return  "删除成功";
       }return  "删除失败";
    }

    @Override
    public String bund(String cid, String[] aids) {
        for(String aid:aids){
            //每一个aid和cid做关联
            ClueActivityRelation car=new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(aid);
            car.setClueId(cid);

            //添加关联关系
           int count= clueActivityRelationDao.bund(car);
           if(count!=1){
               return "添加失败";
           }
        }
        return  "添加成功";
    }


}
