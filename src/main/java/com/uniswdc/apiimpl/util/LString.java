/*
 * file name: LString.java copyright: Unis Cloud Information Technology Co.,
 * Ltd. Copyright 2016, All rights reserved description: <description> mofidy
 * staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.apiimpl.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class LString {
    protected String mKey;
    
    protected String mEnglishString;
    
    private static final long serialVersionUID = -4846791583317622768L;
    
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[0];
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeObject(this.mKey);
        s.writeObject(this.mEnglishString);
    }
    
    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        this.mKey = ((String) s.readObject());
        this.mEnglishString = ((String) s.readObject());
    }
}
