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

    @PostMapping("api/update/{type}/note/{id}")
    @ResponseBody
    public Response updateNote(@RequestBody Note requestNote, @PathVariable("id") int id,@PathVariable("type") String type){
        Note note = noteService.getById(id);
        if(type.equals("info")){

            note.setName(requestNote.getName());
            note.setAbs(requestNote.getAbs());
        }else if( type.equals("content")){
            note.setContentHtml(requestNote.getContentHtml());
            note.setContentMd(requestNote.getContentMd());
        }
        note.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        noteService.updateNote(note);
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

    @GetMapping("api/category/delete/{id}")
    @ResponseBody
    public Response delectCategory(@PathVariable("id") int id) {
        categoryService.deleteById(id);
        return new Response(200, "成功", null);
    }

    @PostMapping("api/category/add")
    @ResponseBody
    public Response addCategory(@RequestBody Category requestCategory) {
        Category category = new Category();
        category.setName(requestCategory.getName());
        categoryService.updateCategory(category);
        return new Response(200, "成功", null);
    }

    @GetMapping("api/note/delete/{id}")
    @ResponseBody
    public Response delectNote(@PathVariable("id") int id) {
        noteService.delectById(id);
        return new Response(200, "成功", null);
    }

    @PostMapping("api/category/update")
    @ResponseBody
    public Response updateCategory(@RequestBody Category requestCategory){
        Category category = categoryService.getById(requestCategory.getId());
        category.setName(requestCategory.getName());
        categoryService.updateCategory(category);
        return new Response(200, "成功", null);
    }

    @GetMapping("api/note/{id}")
    @ResponseBody
    public Response getNote(@PathVariable("id")int id){
        Note note = noteService.getById(id);
        return new Response(200,"成功",note);
    }


}
