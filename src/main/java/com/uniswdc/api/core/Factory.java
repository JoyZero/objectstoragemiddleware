/*
 * file name: Factory.java copyright: Unis Cloud Information Technology Co.,
 * Ltd. Copyright 2016, All rights reserved description: <description> mofidy
 * staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.core;

import com.uniswdc.api.collection.ContentElementList;
import com.uniswdc.api.property.PropertyFilter;
import com.uniswdc.api.util.Id;
import com.uniswdc.apiimpl.collection.SubListS3Impl;
import com.uniswdc.apiimpl.core.ConnectionS3Impl;
import com.uniswdc.apiimpl.core.ContentTransferS3Impl;
import com.uniswdc.apiimpl.core.DocumentS3Imp;
import com.uniswdc.apiimpl.core.DomainS3Impl;
import com.uniswdc.apiimpl.core.ObjectStoreS3Impl;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class Factory {
    public static class Connection {
        public static com.uniswdc.api.core.Connection getConnection(
                String uri) {
            return new ConnectionS3Impl(uri);
        }
    }
    
    public static class Domain {
        public static com.uniswdc.api.core.Domain getInstance(
                com.uniswdc.api.core.Connection conn, String name) {
            return new DomainS3Impl(conn, name);
        }
    }
    
    public static class ObjectStore {
        public static com.uniswdc.api.core.ObjectStore getInstance(
                com.uniswdc.api.core.Domain domain, String name) {
            return fetchInstance(domain, name, null);
        }
        
        public static com.uniswdc.api.core.ObjectStore fetchInstance(
                com.uniswdc.api.core.Domain domain, String name,
                PropertyFilter filter) {
            return new ObjectStoreS3Impl(name, domain.getConnection());
        }
    }
    
    public static class Document {
        public static com.uniswdc.api.core.Document fetchInstance(
                com.uniswdc.api.core.ObjectStore os, Id id,
                PropertyFilter filter) {
            return new DocumentS3Imp(id.getPath(), os.getName(),
                    os.getConnection());
        }
        
        public static com.uniswdc.api.core.Document createInstance(
                com.uniswdc.api.core.ObjectStore os, String classId, Id id) {
            return fetchInstance(os, id, null);
        }
    }
    
    public static class ContentElement {
        public static ContentElementList createList() {
            return new SubListS3Impl();
        }
    }
    
    public static class ContentTransfer {
        public static com.uniswdc.api.core.ContentTransfer createInstance() {
            return new ContentTransferS3Impl();
        }
    }
}
