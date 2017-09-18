/*
 * 
 */
package com.miot.box.base.queue;

import java.util.Iterator;
import java.util.Queue;
// TODO: Auto-generated Javadoc

/**
 * 双向队列
 * 线性集合，支持元素插入和删除
 * 两端。名称双端队列为“双端队列是短暂的”。
 *  *通常是明显的“套牌”。
 *  *实现将元素的数目没有固定的限制 它们可能包含，但此接口支持容量限制
 *  *双端队列以及那些没有固定的大小限制。.
 *
 * @param <E> the element type
 * @author Administrator
 */
public interface IDeque<E> extends Queue<E>{
		
		/**
		 * 指定的元素插入此双端队列的前面.
		 *
		 * @param e the e
		 */
		void addFirst(E e);
		
		/**
		 * 指定的元素插入此双端队列的末尾.
		 *
		 * @param e the e
		 */
	    void addLast(E e);
	    
    	/**
    	 * 在此双端队列的前面插入指定的元素.
    	 *
    	 * @param e the e
    	 * @return true, if successful
    	 */
	    boolean offerFirst(E e);
	    
    	/**
    	 * 在此双端队列的末尾插入指定的元素.
    	 *
    	 * @param e the e
    	 * @return true, if successful
    	 */
	    boolean offerLast(E e);
	    
    	/**
    	 * 获取并移除此双端队列的第一个元素,此方法队列为空时会返回异常信息.
    	 *
    	 * @return the e
    	 */
	    E removeFirst();
	    
    	/**
    	 * 获取并移除此双端队列的末尾元素,此方法队列为空时会返回异常信息.
    	 *
    	 * @return the e
    	 */
	    E removeLast();
	    
    	/**
    	 * 获取并移除此双端队列的第一个元素,此方法队列为空时会返回null.
    	 *
    	 * @return the e
    	 */
	    E pollFirst();
	    
    	/**
    	 * 获取并移除此双端队列的末尾元素,此方法队列为空时会返回null.
    	 *
    	 * @return the e
    	 */
	    E pollLast();
	    
    	/**
    	 * 获得双端队列第一个元素.
    	 *
    	 * @return the first
    	 */
	    E getFirst();
	    
    	/**
    	 * 获得双端队列最后一个元素.
    	 *
    	 * @return the last
    	 */
	    E getLast();
	    
    	/**
    	 * 检索，但是不移除此双端队列的第一个元素.
    	 *
    	 * @return the e
    	 */
	    E peekFirst();
	    
    	/**
    	 * 检索，但是不移除此双端队列的最后一个元素.
    	 *
    	 * @return the e
    	 */
	    E peekLast();
	    
    	/**
    	 * 指定元素从此双端队列移除第一次出现。
    	 * 如果双端队列不包含该元素，这是不变的。.
    	 *
    	 * @param o the o
    	 * @return true, if successful
    	 */
	    boolean removeFirstOccurrence(Object o);
	    
    	/**
    	 * 指定元素从此双端队列移除最后一次出现。.
    	 *
    	 * @param o the o
    	 * @return true, if successful
    	 */
	    boolean removeLastOccurrence(Object o);
	    
    	/**
    	 * 插入指定的元素插入此双端队列表示的队列.
    	 *
    	 * @param e the e
    	 * @return true, if successful
    	 */
	    boolean add(E e);
	    
    	/**
    	 * 插入指定的元素插入此双端队列表示的队列.
    	 *
    	 * @param e the e
    	 * @return true, if successful
    	 */
	    boolean offer(E e);
	    
    	/**
    	 * 获取并移除此双端队列表示的队列的头.
    	 *
    	 * @return the e
    	 */
	    E remove();
	    
    	/**
    	 * 获取并移除此双端队列表示的队列的头.
    	 *
    	 * @return the e
    	 */
	    E poll();
	    
    	/**
    	 * 检索，但是不移除，所表示的队列的头.
    	 *
    	 * @return the e
    	 */
	    E element();
	    
    	/**
    	 * 检索，但是不移除，所表示的队列的头.
    	 *
    	 * @return the e
    	 */
	    E peek();
	    
    	/**
    	 * 将元素推入此双端队列所表示的堆栈（在其他如果是的话，在此双端队列的头），可以这样做
    	 *     	 *不违反容量限制的情况下立即.
    	 *
    	 * @param e the e
    	 */
	    void push(E e);
	    
    	/**
    	 * 此双端队列所表示的堆栈中弹出一个元素。在其他换句话说，移除并返回此双端队列的第一个元素。.
    	 *
    	 * @return the e
    	 */
	    E pop();
	    
    	/**
    	 * 从此双端队列移除第一次出现的指定元素.
    	 *
    	 * @param o the o
    	 * @return true, if successful
    	 */
	    boolean remove(Object o);
	    
    	/**
    	 * 如果此双端队列包含指定的元素。.
    	 *
    	 * @param o the o
    	 * @return true, if successful
    	 */
	    boolean contains(Object o);
	    
    	/**
    	 * 返回此双端队列中的元素数。.
    	 *
    	 * @return the int
    	 */
	    public int size();
	    
    	/**
    	 * 在此双端队列中的元素上以正确的顺序，返回一个迭代器。
    	 *      	 * 的元素的顺序返回第一个（头）至最后一个（尾部）。.
    	 *
    	 * @return the iterator
    	 */
	    Iterator<E> iterator();
	    
    	/**
    	 * 返回一个迭代器反向此双端队列中的元素
    	 *     	 * 顺序。该元素将被返回，以便从
    	 *    		 * 最后一个（尾部）到第一个（头）。.
    	 *
    	 * @return the iterator
    	 */
	    Iterator<E> descendingIterator();    
}
