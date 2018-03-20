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
    public AjaxResult searchStu(@RequestParam("keyword") String key,
                                   @RequestParam("name") String aname){

        return studentService.searchStu(key);
    }

    @ResponseBody
    @RequestMapping(value = "/addnew")
    public AjaxResult addNew(Student student){

        return studentService.insertStu(student);
    }


    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    public void uploadPic(@RequestParam("pid") Integer pid, HttpServletRequest request, PrintWriter out, String lastRealPath) throws IOException {
        // 将当前上下文初始化给CommonsMultipartResolver

        System.out.println(pid);

        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 检查form中是否有enctype＝"multipart／form－data"
        if (resolver.isMultipart(request)) {
            // 强制转化request
            MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
            // 从表单获取input名称
            Iterator<String> iterable = req.getFileNames();

            // 存在文件
            if (iterable.hasNext()) {
                String inputName = iterable.next();
                // 获得文件
                MultipartFile mf = req.getFile(inputName);
                byte[] mfs = mf.getBytes();
                // 定义文件名
                String fileName  = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
                Random random = new Random();
                for (int i = 0; i < 3; i++) {
                    fileName = fileName + random.nextInt(10);
                }
                // 获得后缀名
                String oriFileName = mf.getOriginalFilename();
                String suffix = oriFileName.substring(oriFileName.lastIndexOf("."));

                // 上传图片到本地
                String localPath = request.getSession().getServletContext().getRealPath("/img/") + fileName + suffix;
                mf.transferTo(new File(localPath));

                // 获取图片的宽高
                BufferedImage bufferedImage = ImageIO.read(new FileInputStream(new File(localPath)));
                int width = bufferedImage.getWidth();
                int height = bufferedImage.getHeight();
                // 获取文件大小
                long size = mf.getSize();
            }
        }
    }




    @ResponseBody
    @RequestMapping("/upload")
    public AjaxResult upload(@RequestParam("pid") Integer pid, @RequestParam MultipartFile[] myfiles,HttpServletRequest request) throws IOException
    {
        //如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解
        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解
        //并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件

        System.out.println(pid);

        for(MultipartFile myfile : myfiles){
            if(myfile.isEmpty()){
                System.out.println("文件未上传");
            }else{
                System.out.println("文件长度: " + myfile.getSize());
                System.out.println("文件类型: " + myfile.getContentType());
                System.out.println("文件名称: " + myfile.getName());
                System.out.println("文件原名: " + myfile.getOriginalFilename());
                System.out.println("========================================");
                //如果用的是Tomcat服务器，则文件会上传到
//                {服务发布位置}\\WEB-INF\\upload\\文件夹中
                String realPath = request.getSession().getServletContext().getRealPath("/img/");
                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));
            }
        }

        return AjaxResult.getOK("http://localhost:8080/img/" + "avatar.png");
    }





}
