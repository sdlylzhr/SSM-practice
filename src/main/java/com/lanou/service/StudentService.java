package com.lanou.service;

import com.lanou.bean.Student;

import java.util.List;

/**
 * Created by lizhongren1.
 */
public interface StudentService {

    List<Student> findAllStu();

    Student insertStu(Student student);
    List<Student> searchStu(String keywords);


}
