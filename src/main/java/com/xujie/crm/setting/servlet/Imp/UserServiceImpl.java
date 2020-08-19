package com.xujie.crm.setting.servlet.Imp;

import com.xujie.crm.setting.dao.UserDao;
import com.xujie.crm.setting.domian.User;
import com.xujie.crm.setting.servlet.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
     @Resource
    private UserDao userDao;

       public User login(String loginAct ,String loginPaw,String ip){
           Map<String,String> map=new HashMap<String,String>();
           map.put("loginAct" ,loginAct);
           map.put("loginPaw",loginPaw);
           User user=userDao.login(map);
           return  user;
       }

    @Override
    public List<User> selectUser() {
        return  userDao.selectUser();
    }
}
