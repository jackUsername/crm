package com.xujie.crm.setting.servlet;

import com.xujie.crm.setting.domian.User;

import java.util.List;

public interface UserService {
     User login(String loginAct , String loginPaw, String ip);
     List<User> selectUser();
}
