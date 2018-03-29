package com.lanou.controller;

import com.lanou.bean.Student;
import com.lanou.mapper.StudentMapper;
import com.lanou.service.StudentService;
import com.lanou.utils.AjaxResult;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by lizhongren1.
 */
@Controller
public class MainController {

    @Resource
    private StudentService studentService;

    @RequestMapping(value = {"/home","/"})
    public String homePage(){
        return "home";
    }


    @RequestMapping(value = "/searchpage")
    public String searchPage(){
        return "search";
    }

    @ResponseBody
    @RequestMapping(value = "/getall")
    public AjaxResult allStu(){

        return studentService.findAllStu();
    }

    @ResponseBody
    @RequestMapping(value = "/search")
    public AjaxResult searchStu(@RequestParam("keyword") String key){

        return studentService.searchStu(key);
    }

    @ResponseBody
    @RequestMapping(value = "/addnew")
    public AjaxResult addNew(Student student){

        return studentService.insertStu(student);
    }





}
