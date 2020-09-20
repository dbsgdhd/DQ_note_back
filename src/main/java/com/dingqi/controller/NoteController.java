package com.dingqi.controller;

import com.dingqi.Response.Response;
import com.dingqi.pojo.Category;
import com.dingqi.pojo.Note;
import com.dingqi.pojo.User;
import com.dingqi.service.CategoryService;
import com.dingqi.service.NoteService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.security.provider.Sun;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.multi.MultiInternalFrameUI;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class NoteController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    NoteService noteService;
//    @CrossOrigin
    @GetMapping("/api/notes")
    @ResponseBody
    public List<Note> getNotesList(){
        return noteService.getAll();
    }

    @GetMapping("/api/categories")
    @ResponseBody
    public List<Category> getCategoryList(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return categoryService.getAllByUser(user);
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
        Subject subject = SecurityUtils.getSubject();
        note.setAuthor((User)subject.getPrincipal());
//        System.out.println(note);
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
        Subject subject = SecurityUtils.getSubject();
        Category category = new Category();
        category.setName(requestCategory.getName());
        category.setAuthor((User)subject.getPrincipal());
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

    final static String PIC_Path = "/static/pic";

    @PostMapping("/api/pic")
    @ResponseBody
    public Response uploadPic(MultipartHttpServletRequest pic, HttpServletRequest request){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(new Date());
        String s = "src/main/resources/" + PIC_Path;
        File file = new File(s + format);
        if (!file.isDirectory()){
            file.mkdir();
        }
        String originalFilename = pic.getFile("image").getOriginalFilename();
        String saveName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
        String absolutePath = file.getAbsolutePath();
        try {
            File fileToSave = new File(absolutePath+File.separator+saveName);
            pic.getFile("image").transferTo(fileToSave);
            String returnPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + "/article/images/" + format + "/" + saveName;
            return new Response(200,"成功",returnPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response(500,"失败",null);

    }

}
