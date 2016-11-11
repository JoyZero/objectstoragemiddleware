/*
 * file name: ObjectStoreS3ImplTest.java copyright: Unis Cloud Information
 * Technology Co., Ltd. Copyright 2016, All rights reserved description:
 * <description> mofidy staff: Jie Zhang mofidy time: Oct 26, 2016
 */
package com.uniswdc.apiimpl.core;

import junit.framework.TestCase;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jie Zhang
 * @version [V1.0, Oct 26, 2016]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class ObjectStoreS3ImplTest extends TestCase {
    /**
     * Test method for
     * {@link com.uniswdc.apiimpl.core.ObjectStoreS3Impl#isNameLegal(java.lang.String)}.
     */
    public void testIsNameLegal() {
        String name = "";
        assertFalse(ObjectStoreS3Impl.isNameLegal(name));
        name = "12";
        assertFalse(ObjectStoreS3Impl.isNameLegal(name));
        name = "214sLlfa";
        assertFalse(ObjectStoreS3Impl.isNameLegal(name));
        name = "dfag@123";
        assertFalse(ObjectStoreS3Impl.isNameLegal(name));
        name = "asbf.9dfas-.dfa";
        assertFalse(ObjectStoreS3Impl.isNameLegal(name));
        name = "dfas..fdsad.d23";
        assertFalse(ObjectStoreS3Impl.isNameLegal(name));
        name = "192.168.9.161";
        assertFalse(ObjectStoreS3Impl.isNameLegal(name));
        name = "adf-1.dkl0.089";
        assertTrue(ObjectStoreS3Impl.isNameLegal(name));
    }
}
