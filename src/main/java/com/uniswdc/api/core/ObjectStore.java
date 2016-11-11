/*
 * file name: ObjectStore.java copyright: Unis Cloud Information Technology Co.,
 * Ltd. Copyright 2016, All rights reserved description: <description> mofidy
 * staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.core;

/**
 * Represents a location in which folders, documents, and custom objects are
 * stored, accessed. In s3 it represents a bucket which contains objects.
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public interface ObjectStore {
    /**
     * get the Connection object
     * 
     * @return Connection [explain return type]
     * @exception throws [exception type] [explain exception]
     * @see [class,class#method,class#member]
     */
    public abstract Connection getConnection();
    
    /**
     * get the value of name property
     * 
     * @return String [explain return type]
     * @exception throws [exception type] [explain exception]
     * @see [class,class#method,class#member]
     */
    public abstract String getName();
}
