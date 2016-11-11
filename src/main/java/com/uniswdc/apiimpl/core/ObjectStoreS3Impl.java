/*
 * file name: ObjectStoreS3Impl.java copyright: Unis Cloud Information
 * Technology Co., Ltd. Copyright 2016, All rights reserved description:
 * <description> mofidy staff: Jie Zhang mofidy time: Oct 25, 2016
 */
package com.uniswdc.apiimpl.core;

import com.uniswdc.api.core.Connection;
import com.uniswdc.api.core.ObjectStore;
import com.uniswdc.api.exception.EngineRuntimeException;
import com.uniswdc.api.exception.ExceptionCode;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jie Zhang
 * @version [V1.0, Oct 25, 2016]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class ObjectStoreS3Impl implements ObjectStore {
    private String name;
    
    private Connection conn;
    
    /**
     * <default constructor>
     * 
     * @param name : name of objectstore
     * @param conn : Connection object
     */
    public ObjectStoreS3Impl(String name, Connection conn) {
        if (!isNameLegal(name)) {
            throw new EngineRuntimeException(ExceptionCode.E_NAME_ILLEGAL);
        }
        this.name = name;
        this.conn = conn;
    }
    
    /**
     * judge whether the name is a legal ObjectStore name. Naming rules: 1.The
     * length of the Bucket name must be at least 3 characters, and no more than
     * 63 characters. 2.Bucket name must be a series of one or more tags. The
     * adjacent label is separated by a single period (.). 3.Bucket names can
     * contain lowercase letters, numbers, and even characters. Each label must
     * begin and end with a lower case letter or number. 4.Bucket name may not
     * be used in IP address format (for example, 192.168.5.4). Do not recommend
     * that you use a "." in a Bucket name .
     * 
     * @param name
     * @return [explain parameter]
     * @return boolean [explain return type]
     * @exception throws [exception type] [explain exception]
     * @see [class,class#method,class#member]
     */
    public static boolean isNameLegal(String name) {
        boolean result = false;
        // name长度必须在3-63个字符之间
        boolean len = name.length() >= 3 && name.length() < 63;
        // name中只能包含小写字母，数字，连字符和点号
        String regex1 = "[a-z0-9\\-\\.]+";
        // name可为由.分隔的多个标签，每个标签只能以数字或小写字母开头或结尾
        String regex2 = "([0-9a-z][0-9a-z\\-]+[0-9a-z]\\.)*[0-9a-z][0-9a-z\\-]+[0-9a-z]";
        // 不能采用ip格式
        String regex3 = "(\\d+\\.)+(\\d)+";
        if (len && name.matches(regex1) && name.matches(regex2)
                && !name.matches(regex3)) {
            result = true;
        }
        return result;
    }
    
    /**
     * get the name property
     * 
     * @return returns name
     */
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * @param assgin values to name
     */
    public void setName(String name) {
        if (!isNameLegal(name)) {
            throw new EngineRuntimeException();
        }
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
