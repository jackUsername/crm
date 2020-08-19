package com.xujie.crm.setting.dao;

import com.xujie.crm.setting.domian.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User login(Map map);
    List<User> selectUser();
}
