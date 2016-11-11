/*
 * file name: S3Request.java copyright: Unis Cloud Information Technology Co.,
 * Ltd. Copyright 2016, All rights reserved description: <description> mofidy
 * staff: Jie Zhang mofidy time: Oct 27, 2016
 */
package com.uniswdc.apiimpl.core;

import java.util.HashMap;
import java.util.Map;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jie Zhang
 * @version [V1.0, Oct 27, 2016]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class S3Request {
    public static String REQUEST_TYPE_UPLOAD = "upload";
    
    public static String REQUEST_TYPE_DELETE = "delete";
    
    public static String REQUEST_TYPE_DELETE_ATTACHMENT = "delete_attachment";
    
    private String type;
    
    private Map<String, Object> context = new HashMap<String, Object>();
    
    public S3Request(String type) {
        this.type = type;
    }
    
    /**
     * @return returns type
     */
    public String getType() {
        return type;
    }
    
    /**
     * @param assgin values to type
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * Get params by paraName
     * 
     * @param paraName
     * @return Object vale mapped by paraName
     */
    public Object get(String paraName) {
        return context.get(paraName);
    }
    
    /**
     * Put parameter key is paraName,value is obj
     * 
     * @param paraName
     * @param obj [explain parameter]
     * @return void [explain return type]
     * @exception throws [exception type] [explain exception]
     * @see [class,class#method,class#member]
     */
    public void put(String paraName, Object obj) {
        context.put(paraName, obj);
    }
}
