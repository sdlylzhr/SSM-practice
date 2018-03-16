package com.lanou.mapper;

import com.lanou.bean.Student;
import com.lanou.utils.LuceneUtils;
import com.lanou.utils.StudentUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhongren1.
 */
@Repository
public class LuceneDao {


	/*
	 * 增删改索引都是通过indexWriter对象完成
	 *
	 * */


    /*
     * 建立索引
     * */
    public void addIndex(Student student) throws IOException {

        IndexWriter indexWriter = LuceneUtils.getIndexWriterOfSP();
        Document doc = StudentUtils.studentToDocument(student);
        indexWriter.addDocument(doc);
        		indexWriter.forceMerge(10);//合并cfs文件。比如设定1，就是自动合并成一个索引cfs文件
        indexWriter.close();
    }


    /*
     * 删除索引，根据字段对应的值进行删除
     * */
    public void delIndex(String fieldName,String fieldValue) throws IOException {

        IndexWriter indexWriter = LuceneUtils.getIndexWriterOfSP();
        //term!!!
        Term term = new Term(fieldName,fieldValue);
        //根据字段对应的值删除索引
        indexWriter.deleteDocuments(term);

        indexWriter.close();
    }

    /*
     * 先删除符合条件的记录，再创建一个符合条件的记录
     * */
    public void updateIndex(String fieldName,String fieldValue,Student student) throws IOException {

        IndexWriter indexWriter = LuceneUtils.getIndexWriterOfSP();

        Term term = new Term(fieldName,fieldValue);

        Document document = StudentUtils.studentToDocument(student);

		/*
		 * 1.设置更新的条件
		 * 2.设置更新的内容的对象
		 * */
        indexWriter.updateDocument(term, document);

        indexWriter.close();
    }

    /*
	 * 分页：每页10条
	 * */
    public List<Student> findIndex(String keywords, int start, int rows) throws Exception {

        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(LuceneUtils.INDEXURL_ALL));//索引创建在硬盘上。
        IndexSearcher indexSearcher =  LuceneUtils.getIndexSearcherOfSP();

        /**同义词处理*/
        //需要根据哪几个字段进行检索...
        String fields[] = {"stuName","stuHobby"};

        //查询分析程序（查询解析）
        QueryParser queryParser = new MultiFieldQueryParser(fields, LuceneUtils.getAnalyzer());

        //不同的规则构造不同的子类...
        //title:keywords content:keywords
        Query query = queryParser.parse(keywords);

        //这里检索的是索引目录,会把整个索引目录都读取一遍
        //根据query查询，返回前N条
        TopDocs topDocs = indexSearcher.search(query, start+rows);

        System.out.println("总记录数="+topDocs.totalHits);

        ScoreDoc scoreDoc[] = topDocs.scoreDocs;

        /**添加设置文字高亮begin*/
        //htmly页面高亮显示的格式化，默认是<b></b>即加粗
        Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
        Scorer scorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter, scorer);

        //设置文字摘要（高亮的部分），此时摘要大小为10
        //int fragmentSize = 10;
        Fragmenter fragmenter = new SimpleFragmenter();
        highlighter.setTextFragmenter(fragmenter);

        /**添加设置文字高亮end*/
        List<Student> studentList = new ArrayList<Student>();
        //防止数组溢出
        int endResult = Math.min(scoreDoc.length, start+rows);
        Student student = null;

        for(int i = start;i < endResult ;i++ ){
            student = new Student();
            //docID lucene的索引库里面有很多的document，lucene为每个document定义了一个编号，唯一标识，自增长
            int docID = scoreDoc[i].doc;
            System.out.println("标识docID="+docID);
            Document document = indexSearcher.doc(docID);
            /**获取文字高亮的信息begin*/
            System.out.println("==========================");
            TokenStream tokenStream = LuceneUtils.getAnalyzer().tokenStream("stuName", new StringReader(document.get("stuName")));
            String stuName = highlighter.getBestFragment(tokenStream, document.get("stuName"));
            System.out.println("StuName="+stuName);
            System.out.println("==========================");

            TokenStream tokenStream1 = LuceneUtils.getAnalyzer().tokenStream("stuHobby", new StringReader(document.get("stuHobby")));
            String stuHobby = highlighter.getBestFragment(tokenStream, document.get("stuHobby"));
            System.out.println("StuHobby="+stuHobby);

            System.out.println("==========================");

            /**获取文字高亮的信息end*/

            student.setId(Integer.parseInt(document.get("id")));
            student.setName(document.get("stuName"));
            student.setHobby(document.get("stuHobby"));
            studentList.add(student);
        }
        return studentList;
    }



}
