/*
 * file name: ConnectionImpl.java copyright: Unis Cloud Information Technology
 * Co., Ltd. Copyright 2016, All rights reserved description: <description>
 * mofidy staff: Jie Zhang mofidy time: Oct 25, 2016
 */
package com.uniswdc.apiimpl.core;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.uniswdc.api.core.Connection;

/**
 * An Implement of Connection, manage the connection to s3
 * 
 * @author Jie Zhang
 * @version [V1.0, Oct 25, 2016]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class ConnectionS3Impl implements Connection {
    private static final long serialVersionUID = -6491558550511690649L;
    
    private String uri;
    
    private AmazonS3 s3;
    
    /**
     * constructor
     * 
     * @param uri: the Universal Resource Identity (URI) for this connection
     */
    public ConnectionS3Impl(String uri) {
        this.uri = uri;
    }
    
    /**
     * connect Amazons3, produce a AmazonS3Client object and assign to private
     * member.The username and password are saved in the connection object, each
     * connection using the object to check.
     * 
     * @param accessKey
     * @param secretKey
     */
    @Override
    public void connect(String accessKey, String secretKey) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey,
                secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setMaxErrorRetry(3);
        clientConfig.setConnectionTimeout(10 * 1000);
        clientConfig.setSocketTimeout(10 * 1000);
        clientConfig.setProtocol(Protocol.HTTP);
        s3 = new AmazonS3Client(credentials, clientConfig);
        s3.setEndpoint(uri);
    }
    
    /**
     * Returns the Universal Resource Identity (URI) for this connection. The
     * URI carries the information required to establish a connection to the
     * Content Engine server, such as the transport protocol, host, and port
     * used for server communication.
     * 
     * @return String A String containing the URI.
     */
    @Override
    public String getURI() {
        return uri;
    }
    
    /**
     * get the AmazonS3 object
     * 
     * @return returns s3
     */
    @Override
    public AmazonS3 getS3() {
        return s3;
    }
}
