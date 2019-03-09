package com.wsm.poidemo.poi.controller;

import com.wsm.poidemo.poi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class PersonController {
    @Autowired
    private PersonService personService;
    @GetMapping()
    String person(){
        return "person";
    }

    @ResponseBody
    @PostMapping("/move")
    public String move(@RequestParam("file") MultipartFile file,HttpServletRequest request){
       try{
           String str=personService.resolveExcel(file);
           str="执行成功！"+"<br/>"+str;
           return str;
       }catch(Exception e){
           e.printStackTrace();
           return "执行失败！";
       }
    }
}
