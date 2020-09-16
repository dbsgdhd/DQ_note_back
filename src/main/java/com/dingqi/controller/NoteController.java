package com.dingqi.controller;

import com.dingqi.pojo.Category;
import com.dingqi.pojo.Note;
import com.dingqi.service.CategoryService;
import com.dingqi.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class NoteController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    NoteService noteService;
//    @CrossOrigin
    @GetMapping("/api/categories")
    @ResponseBody
    public List<Category> getCategoryList(){
        return categoryService.getAll();
    }
    @GetMapping("/api/categories/{id}/notes")
    @ResponseBody
    public List<Note> getNoteByCategroy(@PathVariable("id") int id){
        return noteService.getNoteByCategroy(id);
    }
}
