/*
 * file name: SubListS3Impl.java copyright: Unis Cloud Information Technology
 * Co., Ltd. Copyright 2016, All rights reserved description: <description>
 * mofidy staff: Jie Zhang mofidy time: Oct 25, 2016
 */
package com.uniswdc.apiimpl.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.uniswdc.api.collection.ContentElementList;
import com.uniswdc.api.core.ContentTransfer;

/**
 * an implement of ContentElementList, hold a list of ContentTransfer. the
 * content of a document can be set by set the contentElementList of a document.
 * 
 * @author Jie Zhang
 * @version [V1.0, Oct 25, 2016]
 * @see [about class/method]
 * @since [objectstoragemiddleware/V1.0]
 */
public class SubListS3Impl implements ContentElementList {
    private List<ContentTransfer> list;
    
    public SubListS3Impl() {
        list = new ArrayList<ContentTransfer>();
    }
    
    /**
     * @return
     */
    @Override
    public int size() {
        return list.size();
    }
    
    /**
     * @return
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    /**
     * @param o
     * @return
     */
    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }
    
    /**
     * @return
     */
    @Override
    public Iterator<ContentTransfer> iterator() {
        return list.iterator();
    }
    
    /**
     * @return
     */
    @Override
    public Object[] toArray() {
        return list.toArray();
    }
    
    /**
     * @param a
     * @return
     */
    @Override
    public Object[] toArray(Object[] a) {
        return list.toArray();
    }
    
    /**
     * @param e
     * @return
     */
    @Override
    public boolean add(Object e) {
        return list.add((ContentTransfer) e);
    }
    
    /**
     * @param o
     * @return
     */
    @Override
    public boolean remove(Object o) {
        return false;
    }
    
    /**
     * @param c
     * @return
     */
    @Override
    public boolean containsAll(Collection c) {
        return list.containsAll(c);
    }
    
    /**
     * @param c
     * @return
     */
    @Override
    public boolean addAll(Collection c) {
        return list.addAll(c);
    }
    
    /**
     * @param index
     * @param c
     * @return
     */
    @Override
    public boolean addAll(int index, Collection c) {
        return list.addAll(index, c);
    }
    
    /**
     * @param c
     * @return
     */
    @Override
    public boolean removeAll(Collection c) {
        return list.removeAll(c);
    }
    
    /**
     * @param c
     * @return
     */
    @Override
    public boolean retainAll(Collection c) {
        return list.retainAll(c);
    }
    
    /**
     * 
     */
    @Override
    public void clear() {
        list.clear();
    }
    
    /**
     * @param index
     * @return
     */
    @Override
    public Object get(int index) {
        return list.get(index);
    }
    
    /**
     * @param index
     * @param element
     * @return
     */
    @Override
    public Object set(int index, Object element) {
        return list.set(index, (ContentTransfer) element);
    }
    
    /**
     * @param index
     * @param element
     */
    @Override
    public void add(int index, Object element) {
        list.add(index, (ContentTransfer) element);
    }
    
    /**
     * @param index
     * @return
     */
    @Override
    public Object remove(int index) {
        return list.remove(index);
    }
    
    /**
     * @param o
     * @return
     */
    @Override
    public int indexOf(Object o) {
        return list.indexOf((ContentTransfer) o);
    }
    
    /**
     * @param o
     * @return
     */
    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }
    
    /**
     * @return
     */
    @Override
    public ListIterator listIterator() {
        return list.listIterator();
    }
    
    /**
     * @param index
     * @return
     */
    @Override
    public ListIterator listIterator(int index) {
        return list.listIterator(index);
    }
    
    /**
     * @param fromIndex
     * @param toIndex
     * @return
     */
    @Override
    public List subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
}
