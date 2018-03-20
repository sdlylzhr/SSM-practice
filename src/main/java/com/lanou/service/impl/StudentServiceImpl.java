package com.lanou.service.impl;

import com.lanou.bean.Student;
import com.lanou.mapper.LuceneDao;
import com.lanou.mapper.StudentMapper;
import com.lanou.service.StudentService;
import com.lanou.utils.AjaxResult;
import com.lanou.utils.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by lizhongren1.
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private LuceneDao luceneDao;

    @Override
    public AjaxResult findAllStu() {

        List<Student> studentList = studentMapper.findAllStudents();

        return AjaxResult.getOK(studentList);
    }

    @Override
    public AjaxResult insertStu(Student student) {

        Integer result = studentMapper.insert(student);

        System.out.println(student);

        if (result > 0){

            try {
                luceneDao.addIndex(student);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return AjaxResult.getOK(student);
    }

    @Override
    public AjaxResult searchStu(String keywords) {

        try {
            List<Student> studentList = luceneDao.findIndex(keywords, 0, 10);

            return AjaxResult.getOK(studentList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return AjaxResult.getOK("找不到符合要求的结果",null, ResultCode.MyException);
    }


}
