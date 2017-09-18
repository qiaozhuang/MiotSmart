/*
 * 
 */
package com.miot.box.base.queue;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
 
// TODO: Auto-generated Javadoc
/**
 * 闭塞双向界面.
 *
 * @param <E> the element type
 * @author Administrator
 */
public interface IBlockingDeque<E> 
extends 
BlockingQueue<E>, 
IDeque<E> {
	
	/**
	 * 指定的元素插入此双端队列的前面.
	 *
	 * @param e the e
	 */
    void addFirst(E e);
	
	/**
	 * 指定的元素插入此双端队列的尾部.
	 *
	 * @param e the e
	 */
    void addLast(E e);
    
    /**
     * 指定的元素插入此双端队列的前面.
     *
     * @param e the e
     * @return true, if successful
     */
    boolean offerFirst(E e);
    
    /**
     * 指定的元素插入此双端队列的尾部.
     *
     * @param e the e
     * @return true, if successful
     */
    boolean offerLast(E e);
     
    /**
     * Put first.
     *
     * @param e the e
     * @throws InterruptedException the interrupted exception
     */
    void putFirst(E e) throws InterruptedException;
    
    /**
     * Put last.
     *
     * @param e the e
     * @throws InterruptedException the interrupted exception
     */
    void putLast(E e) throws InterruptedException;
    
    /**
     * Offer first.
     *
     * @param e the e
     * @param timeout the timeout
     * @param unit the unit
     * @return true, if successful
     * @throws InterruptedException the interrupted exception
     */
    boolean offerFirst(E e, long timeout, TimeUnit unit)   throws InterruptedException;
    
    /**
     * Offer last.
     *
     * @param e the e
     * @param timeout the timeout
     * @param unit the unit
     * @return true, if successful
     * @throws InterruptedException the interrupted exception
     */
    boolean offerLast(E e, long timeout, TimeUnit unit)   throws InterruptedException;
    
    /**
     * Take first.
     *
     * @return the e
     * @throws InterruptedException the interrupted exception
     */
    E takeFirst() throws InterruptedException;
    
    /**
     * Take last.
     *
     * @return the e
     * @throws InterruptedException the interrupted exception
     */
    E takeLast() throws InterruptedException;
    
    /**
     * Poll first.
     *
     * @param timeout the timeout
     * @param unit the unit
     * @return the e
     * @throws InterruptedException the interrupted exception
     */
    E pollFirst(long timeout, TimeUnit unit)            throws InterruptedException;
    
    /**
     * Poll last.
     *
     * @param timeout the timeout
     * @param unit the unit
     * @return the e
     * @throws InterruptedException the interrupted exception
     */
    E pollLast(long timeout, TimeUnit unit)            throws InterruptedException;
    
    /* (non-Javadoc)
     * @see org.box.base.queue.IDeque#removeFirstOccurrence(java.lang.Object)
     */
    boolean removeFirstOccurrence(Object o);
    
    /* (non-Javadoc)
     * @see org.box.base.queue.IDeque#removeLastOccurrence(java.lang.Object)
     */
    boolean removeLastOccurrence(Object o);
    
    /* (non-Javadoc)
     * @see java.util.concurrent.BlockingQueue#add(java.lang.Object)
     */
    boolean add(E e);
    
    /* (non-Javadoc)
     * @see java.util.concurrent.BlockingQueue#offer(java.lang.Object)
     */
    boolean offer(E e);
    
    /* (non-Javadoc)
     * @see java.util.concurrent.BlockingQueue#put(java.lang.Object)
     */
    void put(E e) throws InterruptedException;
    
    /* (non-Javadoc)
     * @see java.util.concurrent.BlockingQueue#offer(java.lang.Object, long, java.util.concurrent.TimeUnit)
     */
    boolean offer(E e, long timeout, TimeUnit unit)            throws InterruptedException;
    
    /* (non-Javadoc)
     * @see java.util.Queue#remove()
     */
    E remove();
    
    /* (non-Javadoc)
     * @see java.util.Queue#poll()
     */
    E poll();
    
    /* (non-Javadoc)
     * @see java.util.concurrent.BlockingQueue#take()
     */
    E take() throws InterruptedException;
    
    /* (non-Javadoc)
     * @see java.util.concurrent.BlockingQueue#poll(long, java.util.concurrent.TimeUnit)
     */
    E poll(long timeout, TimeUnit unit)            throws InterruptedException;
    
    /* (non-Javadoc)
     * @see java.util.Queue#element()
     */
    E element();
    
    /* (non-Javadoc)
     * @see java.util.Queue#peek()
     */
    E peek();
    
    /* (non-Javadoc)
     * @see java.util.concurrent.BlockingQueue#remove(java.lang.Object)
     */
    boolean remove(Object o);
    
    /* (non-Javadoc)
     * @see java.util.concurrent.BlockingQueue#contains(java.lang.Object)
     */
    public boolean contains(Object o);
    
    /* (non-Javadoc)
     * @see java.util.Collection#size()
     */
    public int size();
    
    /* (non-Javadoc)
     * @see java.util.Collection#iterator()
     */
    Iterator<E> iterator();
    
    /* (non-Javadoc)
     * @see org.box.base.queue.IDeque#push(java.lang.Object)
     */
    void push(E e);
}
