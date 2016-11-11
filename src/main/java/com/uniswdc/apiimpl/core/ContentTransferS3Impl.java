/*
 * file name: ContentTransferS3Impl.java copyright: Unis Cloud Information
 * Technology Co., Ltd. Copyright 2016, All rights reserved description:
 * <description> mofidy staff: Jie Zhang mofidy time: Oct 25, 2016
 */
package com.uniswdc.apiimpl.core;

import java.io.InputStream;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.uniswdc.api.core.ContentTransfer;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jie Zhang
 * @version [V1.0, Oct 25, 2016]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class ContentTransferS3Impl implements ContentTransfer {
    private InputStream input;
    
    private String name;
    
    private ObjectMetadata metadata;
    
    /**
     * <default constructor>
     */
    public ContentTransferS3Impl() {
        this.metadata = new ObjectMetadata();
    }
    
    /**
     * Obtains read access, via an input stream, to the content data of this
     * ContentTransfer object. The content data is fetched from the server. The
     * Content Engine will not automatically close the stream after access has
     * finished; unless you want the stream to remain open, your application
     * should close the stream after it has finished reading the content data
     * 
     * @return InputStream An InputStream object for reading content data.
     * @see [class,class#method,class#member]
     */
    @Override
    public InputStream accessContentStream() {
        return input;
    }
    
    /**
     * Sets the value of the RetrievalName property.
     * 
     * @param paramString: name of the file]
     * @return void
     */
    @Override
    public void set_RetrievalName(String name) {
        this.name = name;
    }
    
    /**
     * set the inputstream of the file
     * 
     * @param input: inputstream of the file
     * @return void
     */
    @Override
    public void setCaptureSource(InputStream input) {
        this.input = input;
    }
    
    /**
     * get the name of the file
     * 
     * @return String [the name of the file]
     */
    @Override
    public String get_RetrievalName() {
        return name;
    }
    
    /**
     * get the metadata of the file
     * 
     * @return
     */
    @Override
    public ObjectMetadata getMetaData() {
        return metadata;
    }
    
    /**
     * set the value of metadata property
     * 
     * @param metadata : metadata of the file
     * @return void [explain return type]
     */
    public void setMetaData(ObjectMetadata metadata) {
        this.metadata = metadata;
    }
}
