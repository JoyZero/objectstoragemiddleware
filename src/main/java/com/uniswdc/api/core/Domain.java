/*
 * file name: Domain.java copyright: Unis Cloud Information Technology Co., Ltd.
 * Copyright 2016, All rights reserved description: <description> mofidy staff:
 * Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.core;

/**
 * Represents a collection of resources and services.Currently, it is empty for
 * being compatible with filenet.
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public interface Domain {
    /**
     * get the Connection object
     * 
     * @return Connection [explain return type]
     * @exception throws [exception type] [explain exception]
     * @see [class,class#method,class#member]
     */
    public Connection getConnection();
}
