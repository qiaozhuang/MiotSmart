/*
 * 
 */
package com.miot.box.base.task;



import java.util.concurrent.TimeUnit;

// TODO: Auto-generated Javadoc
/**
 * The Class TimerTask.
 * 
 * @ClassName: TimerTask
 * @Description:
 * @author 作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
 * @version 创建时间：2013-12-25 14:11:30 Timer task.
 */
public abstract class TimerTask extends Task {

	/** The wait milli second. */
	private long waitMilliSecond;

	/**
	 * Sets the wait time.
	 * 
	 * @param waitMilliSecond
	 *            the new wait time
	 */
	public final void setWaitTime(long waitMilliSecond) {
		this.waitMilliSecond = waitMilliSecond;
	}

	/**
	 * Instantiates a new timer task.
	 * 
	 * @param delaySecond
	 *            the delay second
	 */
	public TimerTask(int delaySecond) {
		this(delaySecond, TimeUnit.SECONDS);
	}

	/**
	 * Instantiates a new timer task.
	 * 
	 * @param duration
	 *            the duration
	 * @param timeUnit
	 *            the time unit
	 */
	public TimerTask(long duration, TimeUnit timeUnit) {
		super(0);
		setDelay(duration, timeUnit);
		setWaitTime(timeUnit.toMillis(duration));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.box.base.task.Task#initTask()
	 */
	@Override
	public final void initTask() {
		isCycle = true;
		super.initTask();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.box.base.core.task.infc.ITask#runTask()
	 */
	@Override
	public final void runTask() throws Exception {
		if (doTimeMethod())
			setDone();
		else
			setDelay(waitMilliSecond, TimeUnit.MILLISECONDS);
	}

	/**
	 * Refresh.
	 * 
	 * @param delaySecond
	 *            the delay second
	 */
	public final void refresh(int delaySecond) {
		invalid();
		setWaitTime(TimeUnit.SECONDS.toMillis(delaySecond));
	}

	/**
	 * Do time method.
	 * 
	 * @return 任务已处理完毕
	 */
	protected abstract boolean doTimeMethod();

	/** The Constant SerialDomain. */
	protected final static int SerialDomain = -0x2000;

}
