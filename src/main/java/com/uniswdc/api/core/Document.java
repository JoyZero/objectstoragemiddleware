/*
 * file name: Document.java copyright: Unis Cloud Information Technology Co.,
 * Ltd. Copyright 2016, All rights reserved description: <description> mofidy
 * staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.core;

import com.uniswdc.api.collection.ContentElementList;
import com.uniswdc.api.constants.RefreshMode;

/**
 * Represents a single version of a document stored in an object store. In
 * addition to being versionable, a Document object can be subclassed and can
 * carry content, which is stored in one or more content elements. Each content
 * element represents content data, which can either be local to an object store
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public interface Document {
    /**
     * Sets the value of the ContentElements property.
     * 
     * @param value a ContentElementList object which contain a list of
     * ContentTransfer
     */
    public void set_ContentElements(ContentElementList value);
    
    /**
     * delete the document in AmazonS3
     * 
     * @throws Exception [explain parameter]
     * @return void [explain return type]
     * @exception throws [exception type] [explain exception]
     * @see [class,class#method,class#member]
     */
    public void delete() throws Exception;
    
    /**
     * Delete attachment within this document which filename is speficifed by
     * name
     * 
     * @throws Exception [explain parameter]
     * @return void [explain return type]
     * @exception throws [exception type] [explain exception]
     * @see [class,class#method,class#member]
     */
    public void delete(String name) throws Exception;
    
    /**
     * upload the document to Amazons3
     * 
     * @param paramRefreshMode [explain parameter]
     * @return void [explain return type]
     */
    public void save(RefreshMode paramRefreshMode);
    
    /**
     * download the document from Amazons3
     * 
     * @param paramRefreshMode [explain parameter]
     * @return void [explain return type]
     */
    public ContentElementList get_ContentElements();
    
    /**
     * Tell if the name of attachment exist or not
     * 
     * @param name
     * @return [explain parameter]
     * @return boolean [explain return type]
     * @exception throws [exception type] [explain exception]
     * @see [class,class#method,class#member]
     */
    public boolean isExist(String name);
}
