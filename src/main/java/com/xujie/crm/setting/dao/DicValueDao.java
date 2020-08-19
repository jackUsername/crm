package com.xujie.crm.setting.dao;

import com.xujie.crm.setting.domian.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
