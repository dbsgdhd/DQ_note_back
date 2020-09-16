package com.dingqi.dao;


import com.dingqi.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Integer> {

    User findByUsername(String username);
}
