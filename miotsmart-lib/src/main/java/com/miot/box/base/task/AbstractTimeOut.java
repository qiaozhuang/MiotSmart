/*
 * 
 */
package com.miot.box.base.task;


import com.miot.box.base.core.task.infc.ITaskTimeout;

import java.util.concurrent.TimeUnit;
 
// TODO: Auto-generated Javadoc
/**
 * The Class AbstractTimeOut.
 *
 * @param <T> the generic type
 * @ClassName: AbstractTimeOut
 * @Description:
 * @author  作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
 * @version 创建时间：2013-12-25 14:11:32
 * Abstract time out.
 */
public abstract class AbstractTimeOut<T extends Task>
implements
		ITaskTimeout<T> {
	
	/** The enabled. */
	protected boolean enabled = true;
	
	/* (non-Javadoc)
	 * @see org.box.base.core.task.infc.ITaskTimeout#cancel()
	 */
	@Override
	public void cancel() {
		enabled = false;
	}
	
	/* (non-Javadoc)
	 * @see org.box.base.core.task.infc.ITaskTimeout#isTimeout(long, org.box.base.task.Task)
	 */
	@Override
	public boolean isTimeout(long curTime, T task) {
		return TimeUnit.SECONDS.toMillis(task.timeOut) < curTime - task.getStartTime();
	}
	
	/* (non-Javadoc)
	 * @see org.box.base.core.task.infc.ITaskTimeout#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	/* (non-Javadoc)
	 * @see org.box.base.core.task.infc.ITaskTimeout#toWait(long, org.box.base.task.Task)
	 */
	@Override
	public long toWait(long curTime, T task) {
		return TimeUnit.SECONDS.toMillis(task.timeOut) + task.getStartTime() - curTime;
	}
}
