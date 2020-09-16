package com.dingqi.controller;

import com.dingqi.Response.Response;
import com.dingqi.pojo.Category;
import com.dingqi.pojo.Note;
import com.dingqi.service.CategoryService;
import com.dingqi.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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

    @PostMapping("api/update/note/{id}/info")
    @ResponseBody
    public Response updateNote(@RequestBody Note note, @PathVariable("id") int id){
        Note byId = noteService.getById(id);
        byId=note;
        byId.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        noteService.updateNote(byId);
        return new Response(200,"成功",null);
    }

    @PostMapping("api/update/category/{id}/note/add")
    @ResponseBody
    public Response addNote(@RequestBody Note requestNote,@PathVariable("id") int id) {
        Note note = new Note();
        note .setName(requestNote.getName());
        note.setAbs(requestNote.getAbs());
        note.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        note.setCategory(categoryService.getById(id));
        System.out.println(note);
        noteService.updateNote(note);
        return new Response(200, "成功", null);
    }

    @GetMapping("api/categories/delete/{id}")
    @ResponseBody
    public Response delectCategory(@PathVariable("id") int id) {
        categoryService.deleteById(id);
        return new Response(200, "成功", null);
    }

    @GetMapping("api/note/delete/{id}")
    @ResponseBody
    public Response delectNote(@PathVariable("id") int id) {
        noteService.delectById(id);
        return new Response(200, "成功", null);
    }
}
