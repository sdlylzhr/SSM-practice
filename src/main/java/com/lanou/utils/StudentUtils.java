package com.lanou.utils;

import com.lanou.bean.Student;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

/**
 * Created by lizhongren1.
 */
public class StudentUtils {

    /*
     * 将GoodDetails 转换成document 将GoodDetails 的值设置到document里面去...
     */
    public static Document studentToDocument(Student student) {

        Document document = new Document();

        StringField idfield = new StringField("id", String.valueOf(student.getId()), Field.Store.YES);

        TextField namefield = new TextField("stuName", student.getName(), Field.Store.YES);
        TextField hobbyfield = new TextField("stuHobby", student.getHobby(), Field.Store.YES);
        document.add(idfield);
        document.add(namefield);
        document.add(hobbyfield);

        return document;
    }
}
