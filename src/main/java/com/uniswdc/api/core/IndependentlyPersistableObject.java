/*
 * file name: IndependentlyPersistableObject.java copyright: Unis Cloud
 * Information Technology Co., Ltd. Copyright 2016, All rights reserved
 * description: <description> mofidy staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.core;

import com.uniswdc.api.constants.RefreshMode;

/**
 * Represents a persistable IndependentObject that you can directly create,
 * update, and delete.
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public interface IndependentlyPersistableObject {
    /**
     * Saves changes made to this object.
     * 
     * @param refreshMode Specifies whether or not to refresh all of the
     * object's properties
     * @return void [explain return type]
     */
    public void save(RefreshMode refreshMode);
    
    /**
     * Adds a Delete pending action to this object's PendingActions collection.
     * You must subsequently commit the change to the repository.
     * 
     * @return void [explain return type]
     */
    public void delete();
}
