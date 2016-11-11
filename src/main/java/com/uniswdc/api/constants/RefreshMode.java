/*
 * file name: RefreshMode.java copyright: Unis Cloud Information Technology Co.,
 * Ltd. Copyright 2016, All rights reserved description: <description> mofidy
 * staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.constants;

/**
 * Specifies whether to retrieve refreshed data from the Content Engine server
 * for updating an object's property cache when the object is explicitly saved
 * or is committed to the persistent store as part of a batch. Use this constant
 * when calling methods that have a refresh parameter, such as save and
 * createUpdatingBatchInstance. Depending on the parameters of the method being
 * called, you can either refresh all of the object's properties or, by
 * specifying a property filter, refresh a subset of the object's properties.
 * Note that you cannot refresh an object's property cache until the object has
 * been created on the Content Engine server. Attempting to do so causes an
 * API_SAVE_BEFORE_REFRESH exception to be thrown.
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class RefreshMode {
    /**
     * Specifies that upon saving or committing the object, its property cache
     * is not refreshed with updated data
     */
    public static RefreshMode REFRESH = new RefreshMode();
    
    /**
     * Specifies that upon saving or committing the object, its property cache
     * is refreshed with updated data
     */
    public static RefreshMode NO_REFRESH = new RefreshMode();
}
