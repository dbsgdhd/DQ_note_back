package com.dingqi.service;

import com.dingqi.dao.CategoryDao;
import com.dingqi.dao.NoteDao;
import com.dingqi.pojo.Category;
import com.dingqi.pojo.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private CategoryDao categoryDao;
    public List<Note>  getNoteByCategroy(int id){
        Category one = categoryDao.getOne(id);
        return noteDao.findAllByCategory(one);
    }

    public Note getById(int id){
        return  noteDao.findById(id).get();
    }

    public void updateNote(Note note){
        noteDao.save(note);
    }


    public void delectById(int id) {
        Note note = noteDao.findById(id).get();
        noteDao.delete(note);
    }

    public List<Note> getAll() {
        List<Note> all = noteDao.findAll();
        return all;
    }
}
