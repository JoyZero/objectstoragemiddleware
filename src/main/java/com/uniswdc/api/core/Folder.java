/*
 * file name: Folder.java copyright: Unis Cloud Information Technology Co., Ltd.
 * Copyright 2016, All rights reserved description: <description> mofidy staff:
 * Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.core;

/**
 * Represents a container that can hold other Containable subclasses, but cannot
 * have content data of its own.
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public interface Folder {
    /**
     * Creates a directly-contained subfolder of this folder.
     * 
     * @param name - A String containing the name to assign to the new child
     * folder.
     * @return Folder A Folder object for the new child folder.
     */
    public Folder createSubFolder(String name);
    
    /**
     * Returns the value of the FolderName property.
     * 
     * @return String [explain return type]
     */
    public String get_FolderName();
    
    /**
     * Returns the value of the PathName property.
     * 
     * @return String [explain return type]
     */
    public String get_PathName();
}
