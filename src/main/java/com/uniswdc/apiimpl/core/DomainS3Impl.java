/*
 * file name: DomainImpl.java copyright: Unis Cloud Information Technology Co.,
 * Ltd. Copyright 2016, All rights reserved description: <description> mofidy
 * staff: Jie Zhang mofidy time: Oct 25, 2016
 */
package com.uniswdc.apiimpl.core;

import com.uniswdc.api.core.Connection;
import com.uniswdc.api.core.Domain;

/**
 * Represents a collection of resources and services.Currently, it is empty for
 * being compatible with filenet.
 * 
 * @author Jie Zhang
 * @version [V1.0, Oct 25, 2016]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class DomainS3Impl implements Domain {
    private String name;
    
    private Connection conn;
    
    public DomainS3Impl(Connection conn, String name) {
        this.conn = conn;
        this.name = name;
    }
    
    /**
     * get the name property
     * 
     * @return String name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param assgin values to name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * get the Connection object
     * 
     * @return returns conn
     */
    @Override
    public Connection getConnection() {
        return conn;
    }
    
    /**
     * @param assgin values to conn
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
