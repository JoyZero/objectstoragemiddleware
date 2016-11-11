/*
 * file name: ErrorLoggingLevel.java copyright: Unis Cloud Information
 * Technology Co., Ltd. Copyright 2016, All rights reserved description:
 * <description> mofidy staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.exception;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jiaqi Yang
 * @version V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class ErrorLoggingLevel {
    public static final ErrorLoggingLevel Required = new ErrorLoggingLevel(
            "Required");
    
    public static final ErrorLoggingLevel Optional = new ErrorLoggingLevel(
            "Optional");
    
    private String mLoggingLevel;
    
    private ErrorLoggingLevel(String loggingLevel) {
        this.mLoggingLevel = loggingLevel;
    }
    
    public String toString() {
        return this.mLoggingLevel;
    }
}
