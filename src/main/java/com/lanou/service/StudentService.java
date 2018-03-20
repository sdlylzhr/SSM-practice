package com.lanou.service;

import com.lanou.bean.Student;
import com.lanou.utils.AjaxResult;

import java.util.List;

/**
 * Created by lizhongren1.
 */
public interface StudentService {

    AjaxResult findAllStu();

    AjaxResult insertStu(Student student);
    AjaxResult searchStu(String keywords);


}
