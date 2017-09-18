/*
 * 
 */
package com.miot.box.base.task;


import com.miot.box.base.core.task.infc.ITaskResult;

import java.util.HashMap;
import java.util.Map;
 
// TODO: Auto-generated Javadoc
/**
 * The Class AbstractResult.
 *
 * @ClassName: AbstractResult
 * @Description:
 * @author  作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
 * @version 创建时间：2013-12-25 14:11:30
 * Abstract result.
 */
public abstract class AbstractResult 
implements
		ITaskResult {
	
	/** The is in response queue. */
	private volatile boolean   isInResponseQueue;
	
	/** The bind serial. */
	private int                bindSerial;
	
	/** The exception. */
	private volatile Exception exception; 
	
	/* (non-Javadoc)
	 * @see org.box.base.core.task.infc.IDisposable#dispose()
	 */
	@Override
	public void dispose() {
		exception = null;	 
		if (isDisposable() && attributes != null) attributes.clear();
	}
	
	/* (non-Javadoc)
	 * @see org.box.base.core.task.infc.ITaskResult#setResponse(boolean)
	 */
	@Override
	public void setResponse(boolean reset) {
		isInResponseQueue = !reset;
	}
	
	/* (non-Javadoc)
	 * @see org.box.base.core.task.infc.ITaskResult#isResponsed()
	 */
	@Override
	public boolean isResponsed() {
		return isInResponseQueue;
	}
	
	/* (non-Javadoc)
	 * @see org.box.base.core.task.infc.ITaskResult#setListenSerial(int)
	 */
	@Override
	public void setListenSerial(int bindSerial) {
		this.bindSerial = bindSerial;
	}
	
	/* (non-Javadoc)
	 * @see org.box.base.core.task.infc.ITaskResult#getListenSerial()
	 */
	@Override
	public int getListenSerial() {
		return bindSerial;
	}
	
	
	/* (non-Javadoc)
	 * @see org.box.base.core.task.infc.ITaskResult#hasError()
	 */
	@Override
	public final boolean hasError() {
		return exception != null;
	}
	
	/* (non-Javadoc)
	 * @see org.box.base.core.task.infc.ITaskResult#setError(java.lang.Exception)
	 */
	@Override
	public final void setError(Exception ex) {
		exception = ex;
	}
	
	/* (non-Javadoc)
	 * @see org.box.base.core.task.infc.ITaskResult#getError()
	 */
	@Override
	public final Exception getError() {
		return exception;
	}
	
	/** The attributes. */
	private Map<String, Object> attributes;
	
	/* (non-Javadoc)
	 * @see org.box.base.core.task.infc.ITaskResult#setAttributes(java.util.Map)
	 */
	@Override
	public void setAttributes(Map<String, Object> map) {
		attributes = map;
	}
	
	/* (non-Javadoc)
	 * @see org.box.base.core.task.infc.ITaskResult#getAttribute(java.lang.String)
	 */
	@Override
	public Object getAttribute(String key) {
		if (attributes == null || attributes.isEmpty()) return null;
		return attributes.get(key);
	}
	
	/* (non-Javadoc)
	 * @see org.box.base.core.task.infc.ITaskResult#setAttribute(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setAttribute(String key, Object value) {
		if (attributes == null) attributes = new HashMap<String, Object>(2, 0.5f);
		attributes.put(key, value);
	}
}
