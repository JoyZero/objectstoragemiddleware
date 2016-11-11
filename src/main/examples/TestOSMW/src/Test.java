import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.security.auth.Subject;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.uniswdc.api.collection.ContentElementList;
import com.uniswdc.api.constants.RefreshMode;
import com.uniswdc.api.core.Connection;
import com.uniswdc.api.core.ContentTransfer;
import com.uniswdc.api.core.Document;
import com.uniswdc.api.core.Domain;
import com.uniswdc.api.core.Factory;
import com.uniswdc.api.core.ObjectStore;
import com.uniswdc.api.util.Id;
import com.uniswdc.api.util.UserContext;
import com.uniswdc.apiimpl.core.ConnectionS3Impl;
import com.uniswdc.apiimpl.core.DocumentS3Imp;

/*
 * file name: Test.java copyright: Unis Cloud Information Technology Co., Ltd.
 * Copyright 2016, All rights reserved description: <description> mofidy staff:
 * Jie Zhang mofidy time: Oct 28, 2016
 */
/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jie Zhang
 * @version [version, Oct 28, 2016]
 * @see [about class/method]
 * @since [product/module version]
 */
public class Test {
    private String uri = "192.168.30.12:7073";
    
    private String username = "unisaa06";
    
    private String password = "unisaa06";
    
    private String uploadPath = "/root/text/example/";
    
    private String downloadPath = "/root/text/result/";
    
    private String osName = "test1";
    
    private String docName = "root/text/example/";
    
    List<String> imgPathList = Arrays.asList("oop.py",
            "fib.py",
            "pool.py",
            "hash.py");
    
    public void upload() throws FileNotFoundException {
        ConnectionS3Impl conn = (ConnectionS3Impl) Factory.Connection
                .getConnection(uri);
        Subject sub = UserContext.createSubject(conn, username, password, null);
        UserContext uc = UserContext.get();
        uc.pushSubject(sub);
        // 获取domain
        Domain domain = Factory.Domain.getInstance(conn, "domain1");
        // 获取object store
        ObjectStore store = Factory.ObjectStore.fetchInstance(domain,
                osName,
                null);
        Document document = Factory.Document.createInstance(store,
                null,
                new Id(docName));
        ContentElementList ceList = Factory.ContentElement.createList();
        InputStream input = null;
        // 将imgPathList的所有文件进行增加到document中
        for (int j = 0; j < imgPathList.size(); j++) {
            ContentTransfer ct = Factory.ContentTransfer.createInstance();
            input = new FileInputStream(uploadPath + imgPathList.get(j));
            // 设置保存的文件流
            ct.setCaptureSource(input);
            // 设置文件名
            String[] temp = imgPathList.get(j).split("/");
            ct.set_RetrievalName(temp[temp.length - 1]);
            ceList.add(ct);
        }
        // 将ceList设置到此document中并保存document
        document.set_ContentElements(ceList);
        document.save(RefreshMode.NO_REFRESH);
    }
    
    public void download() throws IOException {
        Connection conn = Factory.Connection.getConnection(uri);
        // 获取subject
        Subject sub = UserContext.createSubject(conn, username, password, null);
        UserContext uc = UserContext.get();
        uc.pushSubject(sub);
        // 获取domain
        Domain domain = Factory.Domain.getInstance(conn, "domain1");
        // 获取object store
        ObjectStore store = Factory.ObjectStore.fetchInstance(domain,
                osName,
                null);
        // 根据documentid获取document
        Document document = Factory.Document.fetchInstance(store,
                new Id(docName),
                null);
        // 获取所有保存文件的element
        ContentElementList ceList = document.get_ContentElements();
        for (Iterator it = ceList.iterator(); it.hasNext();) {
            ContentTransfer ct = (ContentTransfer) it.next();
            InputStream input = ct.accessContentStream();
            String fileName = downloadPath + ct.get_RetrievalName();
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(file);
            int bytesWritten = 0;
            int byteCount = 0;
            byte[] bytes = new byte[1024];
            while ((byteCount = input.read(bytes)) != -1) {
                output.write(bytes, bytesWritten, byteCount);
                bytesWritten += byteCount;
            }
            input.close();
            output.close();
        }
    }
    
    public void delete() throws Exception {
        Connection conn = Factory.Connection.getConnection(uri);
        Subject sub = UserContext.createSubject(conn, username, password, null);
        UserContext uc = UserContext.get();
        uc.pushSubject(sub);
        // Enter the correct Bucket Name and Document Name
        // 获取domain
        Domain domain = Factory.Domain.getInstance(conn, "domain1");
        ObjectStore store = Factory.ObjectStore.fetchInstance(domain,
                osName,
                null);
        String osNameTure = store.getName();
        Document document = new DocumentS3Imp(docName, osName, conn);
        document.delete();
        document.save(null);
        AmazonS3Client s3 = (AmazonS3Client) conn.getS3();
        List<S3ObjectSummary> prefixList = s3.listObjects(osNameTure, docName)
                .getObjectSummaries();
    }
    
    public void deleteOneFile(String fileName) throws Exception {
        Connection conn = Factory.Connection.getConnection(uri);
        Subject sub = UserContext.createSubject(conn, username, password, null);
        UserContext uc = UserContext.get();
        uc.pushSubject(sub);
        // Enter the correct Bucket Name and Document Name
        // 获取domain
        Domain domain = Factory.Domain.getInstance(conn, "domain1");
        ObjectStore store = Factory.ObjectStore.fetchInstance(domain,
                osName,
                null);
        String osNameTure = store.getName();
        Document document = new DocumentS3Imp(docName, osName, conn);
        document.delete(fileName);
        document.save(null);
    }
    
    public void list() {
        System.out.println(
                "Document name: " + docName + "--------------------------");
        Connection conn = Factory.Connection.getConnection(uri);
        Subject sub = UserContext.createSubject(conn, username, password, null);
        UserContext uc = UserContext.get();
        uc.pushSubject(sub);
        // Enter the correct Bucket Name and Document Name
        // 获取domain
        Domain domain = Factory.Domain.getInstance(conn, "domain1");
        ObjectStore store = Factory.ObjectStore.fetchInstance(domain,
                osName,
                null);
        Document document = new DocumentS3Imp(docName, osName, conn);
        ContentElementList ceList = document.get_ContentElements();
        for (Iterator it = ceList.iterator(); it.hasNext();) {
            ContentTransfer ct = (ContentTransfer) it.next();
            String fileName = docName + ct.get_RetrievalName();
            System.out.println("fileName: " + fileName);
        }
    }
    
    public static void main(String[] args) {
        Test test = new Test();
        try {
            test.list();
            test.upload();
            test.list();
            test.download();
            test.deleteOneFile("fib.py");
            test.list();
            test.deleteOneFile("hash.py");
            test.list();
            test.delete();
            test.list();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
