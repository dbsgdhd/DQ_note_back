package com.dingqi.dao;

import com.dingqi.pojo.Category;
import com.dingqi.pojo.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteDao extends JpaRepository<Note,Integer> {

    List<Note> findAllByCategory(Category one);
}
