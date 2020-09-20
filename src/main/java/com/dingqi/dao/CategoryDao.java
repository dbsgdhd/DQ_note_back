package com.dingqi.dao;

import com.dingqi.pojo.Category;
import com.dingqi.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category,Integer> {

    List<Category> findByAuthor(User user);

}
