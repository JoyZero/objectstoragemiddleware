/*
 * file name: FilterElement.java copyright: Unis Cloud Information Technology
 * Co., Ltd. Copyright 2016, All rights reserved description: <description>
 * mofidy staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.property;

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
public class FilterElement {
    private Integer maxRecursion;
    
    private Long maxSize;
    
    private Boolean levelDependents;
    
    private String value;
    
    private Integer pageSize;
    
    static final long serialVersionUID = -5951897367908662922L;
    
    public FilterElement(Integer maxRecursion, Long maxSize,
            Boolean levelDependents, String value, Integer pageSize) {
        this.maxRecursion = maxRecursion;
        this.maxSize = maxSize;
        this.levelDependents = levelDependents;
        this.value = value;
        this.pageSize = pageSize;
    }
    
    /**
     * @deprecated
     */
    public FilterElement(Integer maxRecursion, Long maxSize,
            Boolean levelDependents, String value) {
        this.maxRecursion = maxRecursion;
        this.maxSize = maxSize;
        this.levelDependents = levelDependents;
        this.value = value;
    }
    
    public Integer getMaxRecursion() {
        return this.maxRecursion;
    }
    
    public Long getMaxSize() {
        return this.maxSize;
    }
    
    public Boolean getLevelDependents() {
        return this.levelDependents;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public Integer getPageSize() {
        return this.pageSize;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer(128);
        sb.append(" Class=");
        sb.append(getClass().getName());
        sb.append(" MaxRecursion=");
        sb.append(this.maxRecursion);
        sb.append(" MaxSize=");
        sb.append(this.maxSize);
        sb.append(" LevelDependents=");
        sb.append(this.levelDependents);
        sb.append(" Value=");
        sb.append(this.value);
        return sb.toString();
    }
    
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[0];
    
    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        short version = s.readShort();
        this.maxRecursion = ((Integer) s.readObject());
        this.maxSize = ((Long) s.readObject());
        this.levelDependents = ((Boolean) s.readObject());
        this.value = ((String) s.readObject());
        if (version >= 5) {
            this.pageSize = ((Integer) s.readObject());
        }
    }
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeShort(5);
        s.writeObject(this.maxRecursion);
        s.writeObject(this.maxSize);
        s.writeObject(this.levelDependents);
        s.writeObject(this.value);
        s.writeObject(this.pageSize);
    }
}
