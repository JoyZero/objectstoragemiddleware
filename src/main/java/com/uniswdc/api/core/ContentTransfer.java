/*
 * file name: ContentTransfer.java copyright: Unis Cloud Information Technology
 * Co., Ltd. Copyright 2016, All rights reserved description: <description>
 * mofidy staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.core;

import java.io.InputStream;

import com.amazonaws.services.s3.model.ObjectMetadata;

/**
 * Represents content data that is local to an object store and directly managed
 * by the Content Engine server.
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public interface ContentTransfer {
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
    public abstract InputStream accessContentStream();
    
    /**
     * Sets the value of the RetrievalName property.
     * 
     * @param paramString: name of the file]
     * @return void
     */
    public abstract void set_RetrievalName(String paramString);
    
    /**
     * set the inputstream of the file
     * 
     * @param input: inputstream of the file
     * @return void
     */
    public abstract void setCaptureSource(InputStream input);
    
    /**
     * get the name of the file
     * 
     * @return String [the name of the file]
     */
    public abstract String get_RetrievalName();
    
    /**
     * get the metadata of the file
     * 
     * @return [explain parameter]
     * @return ObjectMetadata [explain return type]
     */
    public abstract ObjectMetadata getMetaData();
    
    /**
     * set the metadata of the file
     * 
     * @param metadata: metadata of the file
     * @return void [explain return type]
     * @see [class,class#method,class#member]
     */
    public abstract void setMetaData(ObjectMetadata metadata);
}
