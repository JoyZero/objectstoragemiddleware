/*
 * file name: DocumentS3Imp.java copyright: Unis Cloud Information Technology
 * Co., Ltd. Copyright 2016, All rights reserved description: <description>
 * mofidy staff: Jie Zhang mofidy time: Oct 25, 2016
 */
package com.uniswdc.apiimpl.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.uniswdc.api.collection.ContentElementList;
import com.uniswdc.api.constants.RefreshMode;
import com.uniswdc.api.core.Connection;
import com.uniswdc.api.core.ContentTransfer;
import com.uniswdc.api.core.Document;
import com.uniswdc.api.exception.EngineRuntimeException;
import com.uniswdc.api.exception.ExceptionCode;
import com.uniswdc.apiimpl.collection.SubListS3Impl;

/**
 * an implement of Document
 * 
 * @author Jie Zhang
 * @version [V1.0, Oct 25, 2016]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class DocumentS3Imp implements Document {
    /**
     * name of Document
     */
    private String name;
    
    /**
     * name of Bucket
     */
    private String objectStoreName;
    
    private Connection conn;
    
    private SubListS3Impl list;
    
    private LinkedList<S3Request> requestQueue;
    
    /**
     * constructor
     * 
     * @param name of Document,name of Bucket,Connection conn
     */
    public DocumentS3Imp(String name, String objectStoreName, Connection conn) {
        if (name.charAt(name.length() - 1) != '/') {
            name += "/";
        }
        this.name = name;
        this.objectStoreName = objectStoreName;
        this.conn = conn;
        this.list = new SubListS3Impl();
        this.requestQueue = new LinkedList<S3Request>();
    }
    
    /**
     * judge whether the document name is legal. Naming rules: 1.The name of the
     * key is a sequence of Unicode characters, whose maximum length of the
     * UTF-8 encoding is 1024 bytes. 2 The name of the key should be used "/"
     * separated by the full name of the path: such as "ucsm/bin/ucsm.exe"
     * 3.Recommend character are letter and number: [0-9a-zA-Z], special
     * characters:!,-,_,.,*,',(,)
     * 
     * @param name: document name
     * @return boolean: weather the document name is legal
     */
    public static boolean isNameLegal(String name) {
        boolean result = true;
        if (name.contains("/")) {
            result = false;
        }
        return result;
    }
    
    /**
     * Sets the value of the ContentElements property.
     * 
     * @param value a ContentElementList object which contain a list of
     * ContentTransfer
     */
    @Override
    public void set_ContentElements(ContentElementList value) {
        this.list = (SubListS3Impl) value;
    }
    
    /**
     * keep the remote document same as local document object. if you call
     * delete() before call this function, this function will delete document in
     * remote. if you set_ContentElements() before this function , this function
     * will upload document to remote.
     * 
     * @param paramRefreshMode [explain parameter]
     * @return void [explain return type]
     */
    @Override
    public void save(RefreshMode paramRefreshMode) {
        if (!list.isEmpty()) {
            upload();
        }
        else {
            while (!requestQueue.isEmpty()) {
                S3Request request = requestQueue.removeFirst();
                if (request.getType() == S3Request.REQUEST_TYPE_DELETE) {
                    deleteRemote();
                }
                else if (S3Request.REQUEST_TYPE_DELETE_ATTACHMENT
                        .equals(request.getType())) {
                    deleteAttachment((String) request
                            .get(S3Request.REQUEST_TYPE_DELETE_ATTACHMENT));
                }
            }
        }
    }
    
    /**
     * upload the document to remote
     * 
     * @return void [explain return type]
     * @throws IOException
     * @exception throws [exception type] [explain exception]
     * @see [class,class#method,class#member]
     */
    private void upload() {
        AmazonS3Client s3 = (AmazonS3Client) conn.getS3();
        if (!s3.doesBucketExist(objectStoreName)) {
            throw new EngineRuntimeException(ExceptionCode.E_BUCKET_NOT_EXIST);
        }
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(0);
        s3.putObject(objectStoreName,
                this.name,
                new ByteArrayInputStream(new byte[0]),
                meta);
        for (int i = 0; i < list.size(); i++) {
            ContentTransfer ct = (ContentTransfer) list.get(i);
            InputStream input = ct.accessContentStream();
            ObjectMetadata om = ct.getMetaData();
            String name = ct.get_RetrievalName();
            String key = this.name + name;
            // try {
            // om.setContentLength((long) input.available());
            // om.setContentLength(8589934592L);
            System.out.println(om.getContentLength());
            // }
            // catch (IOException e1) {
            // // TODO Auto-generated catch block
            // e1.printStackTrace();
            // }
            if (!isNameLegal(name)) {
                throw new EngineRuntimeException(ExceptionCode.E_NAME_ILLEGAL,
                        "file name should not include '/' character ");
            }
            s3.putObject(objectStoreName, key, input, ct.getMetaData());
            try {
                input.close();
            }
            catch (IOException e) {
                throw new EngineRuntimeException(
                        ExceptionCode.E_INPUTSTREAM_IOECEPTION);
            }
        }
    }
    
    /**
     * download the document from remote, and return the ContetnElementList of
     * the document
     * 
     * @param paramRefreshMode [explain parameter]
     * @return ContentElementList [explain return type]
     */
    @Override
    public ContentElementList get_ContentElements() {
        AmazonS3Client s3 = (AmazonS3Client) conn.getS3();
        if (!s3.doesBucketExist(objectStoreName)) {
            throw new EngineRuntimeException(ExceptionCode.E_BUCKET_NOT_EXIST);
        }
        ObjectListing listing = s3.listObjects(objectStoreName, name);
        List<S3ObjectSummary> summaries = listing.getObjectSummaries();
        list.clear();
        for (S3ObjectSummary s : summaries) {
            String key = s.getKey();
            if (key.charAt(key.length() - 1) == '/') {
                continue;
            }
            ContentTransfer ct = new ContentTransferS3Impl();
            S3Object s3Object = s3.getObject(objectStoreName, key);
            ct.setCaptureSource(s3Object.getObjectContent());
            String[] temp = key.split("/");
            ct.set_RetrievalName(temp[temp.length - 1]);
            ct.setMetaData(s3Object.getObjectMetadata());
            list.add(ct);
        }
        return list;
    }
    
    /**
     * @return returns name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the value of the name property.
     * 
     * @param assgin values to name
     */
    public void setName(String name) {
        if (isNameLegal(name)) {
            throw new EngineRuntimeException(ExceptionCode.E_NAME_ILLEGAL);
        }
        this.name = name;
    }
    
    /**
     * @param name
     * @return
     */
    @Override
    public boolean isExist(String fileName) {
        boolean isExist = false;
        try {
            AmazonS3Client s3 = (AmazonS3Client) conn.getS3();
            isExist = (null != s3.getObject(objectStoreName, name + fileName));
        }
        catch (AmazonClientException e) {
            // nothing to do when key does not exist
        }
        return isExist;
    }
    
    /**
     * delete the document in remote
     *
     * @return void
     */
    private void deleteRemote() {
        AmazonS3Client s3 = (AmazonS3Client) conn.getS3();
        if (!s3.doesBucketExist(objectStoreName)) {
            Exception ex = new Exception();
            throw new EngineRuntimeException(ex,
                    ExceptionCode.E_BUCKET_NOT_EXIST, null);
        }
        List<S3ObjectSummary> prefixList = s3.listObjects(objectStoreName, name)
                .getObjectSummaries();
        try {
            for (S3ObjectSummary prefix : prefixList) {
                s3.deleteObject(objectStoreName, prefix.getKey());
            }
        }
        // if one or more of the objects couldn't be deleted
        catch (MultiObjectDeleteException ex) {
            throw new EngineRuntimeException(ex,
                    ExceptionCode.E_MultiObject_Delete, null);
        }
        // If any errors occurred in Amazon S3 while processing the request.
        catch (AmazonServiceException ex) {
            throw new EngineRuntimeException(ex, ExceptionCode.E_AmazonService,
                    null);
        }
    }
    
    /**
     * delete data in this document only in local. this function only clear the
     * data in this document object but will not delte data in remote. To delete
     * document in remote, call save() after call the function
     * 
     * @throws Exception
     */
    @Override
    public void delete() {
        requestQueue.add(new S3Request(S3Request.REQUEST_TYPE_DELETE));
        list.clear();
    }
    
    /**
     * @param name
     * @throws Exception
     */
    @Override
    public void delete(String name) throws Exception {
        S3Request request = new S3Request(
                S3Request.REQUEST_TYPE_DELETE_ATTACHMENT);
        request.put(S3Request.REQUEST_TYPE_DELETE_ATTACHMENT, name);
        requestQueue.add(request);
        list.clear();
    }
    
    /**
     * Delete attachment which key is attachName
     * 
     * @param object name of attachment
     * @return void [explain return type]
     */
    private void deleteAttachment(String attachName) {
        AmazonS3Client s3 = (AmazonS3Client) conn.getS3();
        s3.deleteObject(objectStoreName, name + attachName);
    }
}
