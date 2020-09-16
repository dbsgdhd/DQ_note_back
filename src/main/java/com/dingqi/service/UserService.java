package com.dingqi.service;

import com.dingqi.dao.UserDao;
import com.dingqi.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getUserVyUsername(String username){
        return userDao.findByUsername(username);
    }

    public  boolean isExist(String username){
        User user = getUserVyUsername(username);
        return user!=null;
    }

    public void addUser(User user){
        userDao.save(user);
    }
    public List<User> findAll(){
        List<User> all = userDao.findAll();
        return all;
    }

    public User getUserByUsername(String userName) {
        return userDao.findByUsername(userName);
    }
}
