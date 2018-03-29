package com.lanou.utils;

//import com.fuzhu.utils.Constant;
import com.hankcs.lucene.HanLPAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class LuceneUtils {
    private static FSDirectory directory_sp = null;
    public static final String INDEXURL_ALL = "Luceneindex";

    private static IndexWriterConfig config = null;

    private static Analyzer analyzer = null;

    static {
        try {
            directory_sp = FSDirectory.open(FileSystems.getDefault()
                    .getPath(INDEXURL_ALL));
            analyzer = new HanLPAnalyzer();
            config = new IndexWriterConfig( analyzer);
            System.out.println("directory_sp    " + directory_sp);
            // 创建内存索引库
//            ramDirectory = new RAMDirectory(directory_sp, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 返回用于操作索引的对象
     * */
    public static IndexWriter getIndexWriterOfSP() throws IOException {
        config = new IndexWriterConfig( analyzer);

        IndexWriter indexWriter = new IndexWriter(directory_sp, config);

        return indexWriter;
    }


    /*
     * 返回用于读取索引的对象
     * */
    public static IndexSearcher getIndexSearcherOfSP() throws IOException {

        System.out.println("directory_sp    " + directory_sp);
        IndexReader indexReader = DirectoryReader.open(directory_sp);

        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        return indexSearcher;
    }

    /*
     * 获取分词器
     * */
    public static Analyzer getAnalyzer() {
        return analyzer;
    }

}
