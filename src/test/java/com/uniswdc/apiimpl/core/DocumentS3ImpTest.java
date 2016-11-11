/*
 * file name: DocumentS3ImpTest.java copyright: Unis Cloud Information
 * Technology Co., Ltd. Copyright 2016, All rights reserved description:
 * <description> mofidy staff: Jie Zhang mofidy time: Oct 26, 2016
 */
package com.uniswdc.apiimpl.core;

import static org.junit.Assert.assertEquals;

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

import org.junit.Test;

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
import com.uniswdc.api.exception.EngineRuntimeException;
import com.uniswdc.api.exception.ExceptionCode;
import com.uniswdc.api.util.Id;
import com.uniswdc.api.util.UserContext;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jie Zhang
 * @version [V1.0, Oct 26, 2016]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class DocumentS3ImpTest {
    private String uri = "192.168.30.12:7073";
    
    private String username = "unisaa06";
    
    private String password = "unisaa06";
    
    private String uploadPath = "/root/text/example/";
    
    private String downloadPath = "/root/text/result/";
    
    private String osName = "test1";
    
    private String docName = "root/text/example/";
    
    private String testPath = "/tmp/osmw/document";
    
    private List<String> imgPathList = Arrays.asList("f200m");
    
    // @BeforeClass
    // public void init() throws IOException {
    // FileUtils.forceMkdir(new File(testPath));
    // }
    private void upload(String[] paths) throws FileNotFoundException {
        ConnectionS3Impl conn = (ConnectionS3Impl) Factory.Connection
                .getConnection(uri);
        Subject sub = UserContext.createSubject(conn, username, password, null);
        UserContext uc = UserContext.get();
        uc.pushSubject(sub);
        // 获取domain
        Domain domain = Factory.Domain.getInstance(conn, null);
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
        for (int j = 0; j < paths.length; j++) {
            ContentTransfer ct = Factory.ContentTransfer.createInstance();
            input = new FileInputStream(testPath + "/" + paths[j]);
            // 设置保存的文件流
            ct.setCaptureSource(input);
            // 设置文件名
            ct.set_RetrievalName(paths[j]);
            ceList.add(ct);
        }
        // 将ceList设置到此document中并保存document
        document.set_ContentElements(ceList);
        document.save(RefreshMode.NO_REFRESH);
    }
    
    public void upload() throws FileNotFoundException {
        ConnectionS3Impl conn = (ConnectionS3Impl) Factory.Connection
                .getConnection(uri);
        Subject sub = UserContext.createSubject(conn, username, password, null);
        UserContext uc = UserContext.get();
        uc.pushSubject(sub);
        // 获取domain
        Domain domain = Factory.Domain.getInstance(conn, null);
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
    
    private void out() {
        System.out.println("object " + docName + " files are :");
        Connection conn = Factory.Connection.getConnection(uri);
        // 获取subject
        Subject sub = UserContext.createSubject(conn, username, password, null);
        UserContext uc = UserContext.get();
        uc.pushSubject(sub);
        // 获取domain
        Domain domain = Factory.Domain.getInstance(conn, null);
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
            String fileName = docName + ct.get_RetrievalName();
            System.out.println("fileName " + fileName);
        }
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
            while ((byteCount = input.read(bytes)) > 0) {
                output.write(bytes, 0, byteCount);
                bytesWritten += byteCount;
            }
            System.out.println("write " + bytesWritten);
            input.close();
            output.close();
        }
    }
    
    /**
     * Test method for delete()
     * {@link com.uniswdc.apiimpl.core.DocumentS3Imp#get_ContentElements()}.
     * 
     * @throws IOException
     */
    @Test
    public void testDelete() throws Exception {
        Connection conn = Factory.Connection
                .getConnection("192.168.30.12:7073");
        conn.connect("unisaa06", "unisaa06");
        // Enter the correct Bucket Name and Document Name
        String nameTure = "/root/text/example/";
        // 获取domain
        Domain domain = Factory.Domain.getInstance(conn, "domain1");
        ObjectStore store = Factory.ObjectStore.fetchInstance(domain,
                "test1",
                null);
        String osNameTure = store.getName();
        Document document = new DocumentS3Imp(nameTure, osNameTure, conn);
        // document.delete();
        // AmazonS3Client s3 = (AmazonS3Client) conn.getS3();
        // List<S3ObjectSummary> prefixList = s3.listObjects(osNameTure,
        // nameTure)
        // .getObjectSummaries();
        // assertEquals(0, prefixList.size());
        // Enter the false Bucket Name and correct Document Name, and test the
        // Exception
        String osNameFalse = "ab37";
        try {
            document = new DocumentS3Imp(nameTure, osNameFalse, conn);
            document.delete();
            document.save(null);
        }
        catch (EngineRuntimeException excepted) {
            assertEquals(ExceptionCode.E_BUCKET_NOT_EXIST,
                    excepted.getExceptionCode());
        }
        String nameFalse = "/ab37";
        document = new DocumentS3Imp(nameFalse, osNameTure, conn);
        document.delete();
        document.save(null);
    }
    
    private void delete(String filename) throws Exception {
        Connection conn = Factory.Connection.getConnection(uri);
        conn.connect(username, password);
        // Enter the correct Bucket Name and Document Name
        // 获取domain
        Domain domain = Factory.Domain.getInstance(conn, null);
        ObjectStore store = Factory.ObjectStore.fetchInstance(domain,
                osName,
                null);
        Document document = new DocumentS3Imp(docName, osName, conn);
        document.delete(filename);
        document.save(null);
    }
    
    private boolean isExist(String filename) {
        Connection conn = Factory.Connection.getConnection(uri);
        conn.connect(username, password);
        // Enter the correct Bucket Name and Document Name
        // 获取domain
        Domain domain = Factory.Domain.getInstance(conn, "domain1");
        ObjectStore store = Factory.ObjectStore.fetchInstance(domain,
                osName,
                null);
        String osNameTure = store.getName();
        Document document = new DocumentS3Imp(docName, osName, conn);
        return document.isExist(filename);
    }
    
    public void delete() throws Exception {
        Connection conn = Factory.Connection.getConnection(uri);
        conn.connect(username, password);
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
        assertEquals(0, prefixList.size());
    }
    
    // @Test
    // public void testSaveAndDownload() throws Exception {
    // upload();
    // download();
    // delete();
    // }
    // @Test
    // public void testDeleteAttach() throws Exception {
    // String deleteFile = "delete";
    // String deleteFile2 = "delete2测试删除附件";
    // File file1 = new File(testPath + "/" + deleteFile);
    // File file2 = new File(testPath + "/" + deleteFile2);
    // try {
    // out();
    // FileUtils.touch(file1);
    // FileUtils.touch(file2);
    // upload(new String[] { deleteFile, deleteFile2 });
    // out();
    // delete(deleteFile);
    // assertEquals(false, isExist(deleteFile));
    // out();
    // delete(deleteFile2);
    // out();
    // delete();
    // out();
    // }
    // finally {
    // FileUtils.forceDelete(file1);
    // FileUtils.forceDelete(file2);
    // }
    // }
    @Test
    public void testDeleteOneFile() {
        try {
            out();
            System.out.println("upload 4 file------------");
            upload();
            out();
            download();
            // System.out.println("delete 1 file------------");
            // delete("fib.py");
            // out();
            // System.out.println("delete all---------------");
            delete();
            out();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
