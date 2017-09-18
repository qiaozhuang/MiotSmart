/*
 * 
 */
package com.miot.box.base.queue;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
 
// TODO: Auto-generated Javadoc
/**
 * The Class LinkedBlockingDeque.
 *
 * @param <E> the element type
 * @ClassName: LinkedBlockingDeque
 * @Description:
 * @author  作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
 * @version 创建时间：2013-12-25 14:11:31
 * Linked blocking deque.
 */
public class LinkedBlockingDeque<E>
extends AbstractQueue<E>
implements IBlockingDeque<E>,
 java.io.Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1295089364674900881L;

	/* (non-Javadoc)
	 * @see java.util.concurrent.BlockingQueue#drainTo(java.util.Collection)
	 */
	@Override
	public int drainTo(Collection<? super E> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.BlockingQueue#drainTo(java.util.Collection, int)
	 */
	@Override
	public int drainTo(Collection<? super E> arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.BlockingQueue#remainingCapacity()
	 */
	@Override
	public int remainingCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IDeque#removeFirst()
	 */
	@Override
	public E removeFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IDeque#removeLast()
	 */
	@Override
	public E removeLast() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IDeque#pollFirst()
	 */
	@Override
	public E pollFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IDeque#pollLast()
	 */
	@Override
	public E pollLast() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IDeque#getFirst()
	 */
	@Override
	public E getFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IDeque#getLast()
	 */
	@Override
	public E getLast() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IDeque#peekFirst()
	 */
	@Override
	public E peekFirst() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IDeque#peekLast()
	 */
	@Override
	public E peekLast() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IDeque#pop()
	 */
	@Override
	public E pop() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IDeque#descendingIterator()
	 */
	@Override
	public Iterator<E> descendingIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#addFirst(java.lang.Object)
	 */
	@Override
	public void addFirst(E e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#addLast(java.lang.Object)
	 */
	@Override
	public void addLast(E e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#offerFirst(java.lang.Object)
	 */
	@Override
	public boolean offerFirst(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#offerLast(java.lang.Object)
	 */
	@Override
	public boolean offerLast(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#putFirst(java.lang.Object)
	 */
	@Override
	public void putFirst(E e) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#putLast(java.lang.Object)
	 */
	@Override
	public void putLast(E e) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#offerFirst(java.lang.Object, long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public boolean offerFirst(E e, long timeout, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#offerLast(java.lang.Object, long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public boolean offerLast(E e, long timeout, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#takeFirst()
	 */
	@Override
	public E takeFirst() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#takeLast()
	 */
	@Override
	public E takeLast() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#pollFirst(long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public E pollFirst(long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#pollLast(long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public E pollLast(long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#removeFirstOccurrence(java.lang.Object)
	 */
	@Override
	public boolean removeFirstOccurrence(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#removeLastOccurrence(java.lang.Object)
	 */
	@Override
	public boolean removeLastOccurrence(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.Queue#offer(java.lang.Object)
	 */
	@Override
	public boolean offer(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#put(java.lang.Object)
	 */
	@Override
	public void put(E e) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#offer(java.lang.Object, long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public boolean offer(E e, long timeout, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.Queue#poll()
	 */
	@Override
	public E poll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#take()
	 */
	@Override
	public E take() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#poll(long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.Queue#peek()
	 */
	@Override
	public E peek() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.box.base.queue.IBlockingDeque#push(java.lang.Object)
	 */
	@Override
	public void push(E e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#size()
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

}
