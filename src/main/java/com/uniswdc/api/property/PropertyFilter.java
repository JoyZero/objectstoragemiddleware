/*
 * file name: PropertyFilter.java copyright: Unis Cloud Information Technology
 * Co., Ltd. Copyright 2016, All rights reserved description: <description>
 * mofidy staff: Jiaqi Yang mofidy time: 2016年10月9日
 */
package com.uniswdc.api.property;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.util.ArrayList;
import java.util.List;

/**
 * <Simple feature description > <Detailed feature description>
 * 
 * @author Jiaqi Yang
 * @version [V1.0, 2016年10月9日]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class PropertyFilter {
    private List includePropertyList = new ArrayList();
    
    private List includeTypeList = new ArrayList();
    
    private List excludePropertyList = new ArrayList();
    
    private Integer maxRecursion;
    
    private Long maxSize;
    
    private Integer pageSize;
    
    private Boolean levelDependents;
    
    static final long serialVersionUID = 733422040975163154L;
    
    public Integer getPageSize() {
        return this.pageSize;
    }
    
    public void setPageSize(int pageSize) {
        setPageSize(new Integer(pageSize));
    }
    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    public void setMaxRecursion(int maxRecursion) {
        setMaxRecursion(new Integer(maxRecursion));
    }
    
    public void setMaxRecursion(Integer maxRecursion) {
        this.maxRecursion = maxRecursion;
    }
    
    public Integer getMaxRecursion() {
        return this.maxRecursion;
    }
    
    public void setMaxSize(Long maxSize) {
        this.maxSize = maxSize;
    }
    
    public Long getMaxSize() {
        return this.maxSize;
    }
    
    public Boolean getLevelDependents() {
        return this.levelDependents;
    }
    
    public void setLevelDependents(boolean levelDependents) {
        setLevelDependents(levelDependents ? Boolean.TRUE : Boolean.FALSE);
    }
    
    public void setLevelDependents(Boolean levelDependents) {
        this.levelDependents = levelDependents;
    }
    
    public void addIncludeProperty(FilterElement fe) {
        this.includePropertyList.add(fe);
    }
    
    public void addIncludeProperty(int maxRecursion, Long maxSize,
            Boolean levelDependents, String value, Integer pageSize) {
        addIncludeProperty(new FilterElement(new Integer(maxRecursion), maxSize,
                levelDependents, value, pageSize));
    }
    
    /**
     * @deprecated
     */
    public void addIncludeProperty(int maxRecursion, Long maxSize,
            Boolean levelDependents, String value) {
        addIncludeProperty(new FilterElement(new Integer(maxRecursion), maxSize,
                levelDependents, value, null));
    }
    
    public FilterElement[] getIncludeProperties() {
        return (FilterElement[]) this.includePropertyList
                .toArray(new FilterElement[this.includePropertyList.size()]);
    }
    
    public void addExcludeProperty(String value) {
        this.excludePropertyList.add(value);
    }
    
    public String[] getExcludeProperties() {
        return (String[]) this.excludePropertyList
                .toArray(new String[this.excludePropertyList.size()]);
    }
    
    public void addIncludeType(FilterElement fe) {
        String[] fevs = fe.getValue().split(" ");
        for (int i = 0; i < fevs.length; i++) {
            String fev = fevs[i];
            if (!fev.equals("")) {
                // FilteredPropertyType.getInstanceFromString(fev);
            }
        }
        this.includeTypeList.add(fe);
    }
    
    // public void addIncludeType(int maxRecursion, Long maxSize,
    // Boolean levelDependents, FilteredPropertyType value,
    // Integer pageSize) {
    // addIncludeType(new FilterElement(new Integer(maxRecursion), maxSize,
    // levelDependents, value, pageSize));
    // }
    // /**
    // * @deprecated
    // */
    // public void addIncludeType(int maxRecursion, Long maxSize,
    // Boolean levelDependents, FilteredPropertyType value) {
    // addIncludeType(new FilterElement(new Integer(maxRecursion), maxSize,
    // levelDependents, value, null));
    // }
    public FilterElement[] getIncludeTypes() {
        return (FilterElement[]) this.includeTypeList
                .toArray(new FilterElement[this.includeTypeList.size()]);
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer(1024);
        sb.append(" Class=");
        sb.append(getClass().getName());
        sb.append(" MaxRecursions=");
        sb.append(this.maxRecursion);
        sb.append(" MaxSize=");
        sb.append(this.maxSize);
        sb.append(" PageSize=");
        sb.append(this.pageSize);
        sb.append(" LevelDependents=");
        sb.append(this.levelDependents);
        sb.append(" IncludePropertyList=");
        sb.append(this.includePropertyList);
        sb.append(" IncludeTypeList=");
        sb.append(this.includeTypeList);
        sb.append(" ExcludePropertyList=");
        sb.append(this.excludePropertyList);
        return sb.toString();
    }
    
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[0];
    
    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        s.readShort();
        this.maxRecursion = ((Integer) s.readObject());
        this.maxSize = ((Long) s.readObject());
        this.pageSize = ((Integer) s.readObject());
        this.levelDependents = ((Boolean) s.readObject());
        this.includePropertyList = new ArrayList();
        int size = s.readInt();
        for (int lp = 0; lp < size; lp++) {
            this.includePropertyList.add(s.readObject());
        }
        this.includeTypeList = new ArrayList();
        size = s.readInt();
        for (int lp = 0; lp < size; lp++) {
            this.includeTypeList.add(s.readObject());
        }
        this.excludePropertyList = new ArrayList();
        size = s.readInt();
        for (int lp = 0; lp < size; lp++) {
            this.excludePropertyList.add(s.readObject());
        }
    }
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeShort(5);
        s.writeObject(this.maxRecursion);
        s.writeObject(this.maxSize);
        s.writeObject(this.pageSize);
        s.writeObject(this.levelDependents);
        int size = this.includePropertyList.size();
        s.writeInt(size);
        for (int lp = 0; lp < size; lp++) {
            s.writeObject(this.includePropertyList.get(lp));
        }
        size = this.includeTypeList.size();
        s.writeInt(size);
        for (int lp = 0; lp < size; lp++) {
            s.writeObject(this.includeTypeList.get(lp));
        }
        size = this.excludePropertyList.size();
        s.writeInt(size);
        for (int lp = 0; lp < size; lp++) {
            s.writeObject(this.excludePropertyList.get(lp));
        }
    }
}
