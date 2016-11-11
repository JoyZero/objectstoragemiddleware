/*
 * file name: ConnectionS3ImplTest.java copyright: Unis Cloud Information
 * Technology Co., Ltd. Copyright 2016, All rights reserved description:
 * <description> mofidy staff: Jie Zhang mofidy time: Oct 26, 2016
 */
package com.uniswdc.apiimpl.core;

import java.util.List;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;

import junit.framework.TestCase;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jie Zhang
 * @version [V1.0, Oct 26, 2016]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class ConnectionS3ImplTest extends TestCase {
    /**
     * Test method for
     * {@link com.uniswdc.apiimpl.core.ConnectionS3Impl#connect(java.lang.String, java.lang.String)}.
     */
    public void testConnect() {
        ConnectionS3Impl conn = new ConnectionS3Impl("192.168.30.12:7073");
        conn.connect("unisaa06", "unisaa06");
        AmazonS3Client s3 = (AmazonS3Client) conn.getS3();
        List<Bucket> list = s3.listBuckets();
        for (Bucket bucket : list) {
            System.out.println(bucket.getName());
        }
    }
}
