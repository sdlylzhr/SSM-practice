package com.lanou.mapper;

import com.lanou.bean.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StudentMapper {
    int insert(Student record);
    Student selectByStudentId(@Param("sid") Integer id);

    List<Student> findAllStudents();

}