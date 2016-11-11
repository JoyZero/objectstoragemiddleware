/*
 * file name: Id.java copyright: Unis Cloud Information Technology Co., Ltd.
 * Copyright 2016, All rights reserved description: <description> mofidy staff:
 * Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.util;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class Id {
    private String path;
    
    /**
     * @return returns strId
     */
    public String getPath() {
        return path;
    }
    
    /**
     * @param assgin values to strId
     */
    public void setPath(String path) {
        this.path = path;
    }
    
    public Id(String path) {
        this.path = path;
    }
}
