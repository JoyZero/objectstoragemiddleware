/*
 * file name: Connection.java copyright: Unis Cloud Information Technology Co.,
 * Ltd. Copyright 2016, All rights reserved description: <description> mofidy
 * staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.core;

import java.io.Serializable;

import com.amazonaws.services.s3.AmazonS3;

/**
 * Represents a logical connection to a storage domain.You can create a
 * Connection object by calling one of the static getConnection methods on the
 * Factory.Connection class. Once you have obtained a Connection object, you can
 * call its methods to retrieve information about the connection. All engine
 * objects maintain a reference to the connection instance, which you can
 * retrieve by calling the engine object's getConnection method. The Connection
 * object is also used as an input to factory methods that create other objects.
 * Examples are Factory.Domain.getInstance(Connection conn, String name)
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public abstract interface Connection extends Serializable {
    /**
     * Returns the Universal Resource Identity (URI) for this connection. The
     * URI carries the information required to establish a connection to the
     * Content Engine server, such as the transport protocol, host, and port
     * used for server communication.
     * 
     * @return String A String containing the URI.
     */
    public abstract String getURI();
    
    /**
     * get an AmazonS3 object, this object is used to do all the operation,like
     * upload/download/delete...
     * 
     * @return AmazonS3 [com.amazonaws.services.s3.AmazonS3]
     */
    public abstract AmazonS3 getS3();
    
    /**
     * connect AmazonS3
     * 
     * @param accessKey: username
     * @param secretKey: password
     */
    public abstract void connect(String accessKey, String secretKey);
}