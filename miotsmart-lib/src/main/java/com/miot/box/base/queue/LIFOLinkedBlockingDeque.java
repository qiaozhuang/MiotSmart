/*
 * 
 */
package com.miot.box.base.queue;
 
// TODO: Auto-generated Javadoc
/**
 * The Class LIFOLinkedBlockingDeque.
 *
 * @param <T> the generic type
 * @ClassName: LIFOLinkedBlockingDeque
 * @Description:
 * @author  作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
 * @version 创建时间：2013-12-25 14:11:39
 * LIFO linked blocking deque.
 */
public class LIFOLinkedBlockingDeque<T> extends LinkedBlockingDeque<T> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2477692600447553986L;
	 
	/* (non-Javadoc)
	 * @see org.box.base.queue.LinkedBlockingDeque#offer(java.lang.Object)
	 */
	@Override
	public boolean offer(T e) {
		return super.offerFirst(e);
	}
	
	/* (non-Javadoc)
	 * @see java.util.AbstractQueue#remove()
	 */
	@Override
	public T remove() {
		return super.removeFirst();
	}
}
