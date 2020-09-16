package com.dingqi.service;

import com.dingqi.dao.CategoryDao;
import com.dingqi.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;
    public List<Category> getAll(){
        return categoryDao.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }

}