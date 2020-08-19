package com.xujie.crm.setting.servlet.Imp;

import com.xujie.crm.setting.dao.DicTypeDao;
import com.xujie.crm.setting.dao.DicValueDao;
import com.xujie.crm.setting.domian.DicType;
import com.xujie.crm.setting.domian.DicValue;
import com.xujie.crm.setting.servlet.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {
    @Autowired
    private DicTypeDao dicTypeDao;
    @Autowired
    private DicValueDao dicValueDao;

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map=new HashMap<>();
        //将字典类型列表取出
        List<DicType> dtList=dicTypeDao.getTypeList();
        //将字典类型遍历
        for(DicType dt :dtList){
            //取得每一种类型字符串类型编码
            String code=dt.getCode();
            //根据每一个字典类型取得字典列表
            List<DicValue> dvList=dicValueDao.getListByCode(code);
            map.put(code+"list",dvList);

        }
        return map;
    }
}
