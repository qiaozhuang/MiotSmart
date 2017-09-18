/*
 * 
 */
package com.miot.box.base.task;

import android.util.Log;

import com.miot.box.base.core.task.infc.ITask;
import com.miot.box.base.core.task.infc.ITaskProgress;
import com.miot.box.base.core.task.infc.ITaskResult;
import com.miot.box.base.core.task.infc.ITaskTimeout;
import com.miot.box.base.core.task.infc.ITaskWakeTimer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


// TODO: Auto-generated Javadoc
/**
 *  Task 的生命周期
 *  	1、initTask() ;->TaskService.processor 中执行
 *  	并在此进行任务执行空间的分发,是否进入Excutor来执行.由isBlock属性来决定
 *  	2、run();
 *  	3、afterRun()；
 *  	4、finish()；
 *  	---Exception
 *  	doAfterException()
 *    notifyObserver()
 * @author Administrator
 *
 */
public abstract class Task
extends
AbstractResult
implements
        ITask {

    /** The thread id. */
    public int threadId;

    /** The progress. */
    private ITaskProgress progress;

    /** The run lock. */
    protected final transient ReentrantLock runLock;

    /** The available. */
    protected final transient Condition     available;

    /** 为timeOut所使用的启动时间. */
    private long                            startTime;

    /** The invalid. */
    protected volatile boolean	  isDone, disable, isBloker, isCycle, isPending, isSegment, isProxy, isInit, discardPre, wakeLock, invalid;

    /** The wake timer. */
    public ITaskWakeTimer wakeTimer;

    /** The schedule service. */
    protected transient TaskService         scheduleService;

    /** The hold thread. */
    protected Thread                        		    holdThread;

    /** The to scheduled. */
    private volatile int toScheduled;

    /** The time limit. */
    public volatile int priority,timeOut,timeLimit;

    /** The off time. */
    protected volatile long                 	  doTime, offTime;

    /** The retry. */
    private byte retry;

    /** The attachment. */
    public Object                          			  attachment;

    /** The timeout call. */
    public ITaskTimeout<Task> timeoutCall;

    /** The in queue index. */
    volatile int                            				  inQueueIndex;

    /** 是否需要唤醒. */
    protected volatile boolean needAlarm;

    /**
     * Instantiates a new task.
     *
     * @param threadId the thread id
     */
    public Task(int threadId) {
        this(threadId, null);
    }

    /**
     * Instantiates a new task.
     *
     * @param threadId the thread id
     * @param progress the progress
     */
    public Task(int threadId, ITaskProgress progress) {
        super();
        this.threadId = threadId;
        this.progress = progress;
        runLock = new ReentrantLock();
        available = runLock.newCondition();
    }

    /**
     * Sets the start time.
     *
     * @param start 任务启动开始进入timeout计时行为
     * 注意:时间起点由任务的特定超时行为规约,并不等价于任务开始执行的时刻
     */
    public final void setStartTime(long start) {
        startTime = start;
    }

    /**
     * 当前时间作为任务启动计时行为的时刻.
     *
     * @see #setStartTime(long)
     */
    public final void setStartTime() {
        setStartTime(System.currentTimeMillis());
    }

    /**
     * 设置警告时间.
     *
     * @param RTC_WakeTime the new alarm time
     */
    public final void setAlarmTime(long RTC_WakeTime) {
        if (isInit)
        {
            wakeTimer = scheduleService.setTaskAlarmTime(RTC_WakeTime, wakeTimer);
            wakeTimer.setTask(this);
            needAlarm = true;
        }
    }

    /**
     * 当任务需要处理同步条件的时候将需要此方法进行操作.
     *
     * @return the lock
     */
    public final ReentrantLock getLock() {
        if (runLock == null) throw new NullPointerException();
        return runLock;
    }

    /**
     * 终止任务的执行.
     */
    @Override
    public void interruptTask() {
        Thread thread = getHoldThread();
        if (thread.isAlive() && !thread.isInterrupted())
        {
            thread.interrupt();
        }
    }

    /**
     * 设置任务的优先级.
     *
     * @param priority the new priority
     */
    public final void setPriority(int priority) {
        if (priority != this.priority)
        {
            this.priority = priority;
            scheduleService.mainQueue.replace(this);
        }
    }

    /**
     * 任务复制.
     *
     * @param task the task
     */
    protected void clone(Task task) {
        threadId = task.threadId;
        retry = (byte) (task.retry & 0xF0);
        timeLimit = task.timeLimit;
        progress = task.progress;
        timeOut = task.timeOut;
        timeoutCall = task.timeoutCall;
    }

    /* (non-Javadoc)
     * @see org.box.base.core.task.infc.ITask#wakeUpTask()
     */
    @Override
    public void wakeUpTask() {
    }

    /* (non-Javadoc)
     * @see org.box.base.core.task.infc.ITask#needAlarm()
     */
    @Override
    public boolean needAlarm() {
        return needAlarm;
    }

    /**
     * Gets the hold thread.
     *
     * @return the hold thread
     */
    public final Thread getHoldThread() {
        return holdThread;
    }

    /**
     * 设置当前任务的是否可以离开队列，并判断进入此状态的次数
     * 取值范围在0-0x40FFFFFF
     * delayTime>0 toScheduled |= 0x8000000;.
     *
     * @return >=0允许离开队列
     */
    final int isToSchedule() {
        if (getDelay(TimeUnit.MILLISECONDS) > 0) toScheduled |= 0x8000000;
        else toScheduled &= 0x40FFFFFF;
        return toScheduled;
    }

    /**
     * Into schedule queue.
     */
    final void intoScheduleQueue() {
        toScheduled++;
        toScheduled &= 0x40FFFFFF;
    }

    /**
     * Out schedule queue.
     */
    final void outScheduleQueue() {
        toScheduled = 0;
    }

    /**
     * Gets the delay.
     *
     * @param unit the unit
     * @return the delay
     */
    public final long getDelay(TimeUnit unit) {
        //毫秒级比较
        return unit.convert(getDelayNow(), TimeUnit.MILLISECONDS);
    }

    /**
     * Gets the delay now.
     *
     * @return the delay now
     */
    final long getDelayNow() {
        return doTime - System.currentTimeMillis();
    }

    /**
     * Sets the done.
     */
    public final void setDone() {
        isDone = true;
    }

    /**
     * Checks if is blocker.
     *
     * @return true, if is blocker
     */
    public final boolean isBlocker() {
        return isBloker;
    }

    /**
     * Checks if is disable.
     *
     * @return true, if is disable
     */
    public final boolean isDisable() {
        return disable;
    }

    /**
     * Checks if is pending.
     *
     * @return true, if is pending
     */
    public final boolean isPending() {
        return isPending;
    }

    /**
     * Checks if is discard pre.
     *
     * @return true, if is discard pre
     */
    public final boolean isDiscardPre() {
        return discardPre;
    }

    /**
     * Checks if is done.
     *
     * @return true, if is done
     */
    public final boolean isDone() {
        return isDone;
    }

    /**
     * Checks if is proxy.
     *
     * @return true, if is proxy
     */
    public final boolean isProxy() {
        return isProxy;
    }

    /**
     * Checks if is inits the.
     *
     * @return true, if is inits the
     */
    public final boolean isInit() {
        return isInit;
    }

    /**
     * Sets the retry limit.
     *
     * @param limit the new retry limit
     */
    public final void setRetryLimit(int limit) {
        retry &= 0x0F;
        retry |= (limit & 0x0F) << 4;
    }

    /**
     * Retry.
     */
    public final void retry() {
        setError(null);
        doTime = 0;
        retry += (retry & 0x0F) < 0x0F ? 1 : 0;
        isDone = false;
        isPending = false;
        isInit = false;
    }

    /**
     * Gets the cur retry.
     *
     * @return the cur retry
     */
    public final int getCurRetry() {
        return retry & 0x0F;
    }

    /**
     * Can retry.
     *
     * @return true, if successful
     */
    public final boolean canRetry() {
        return (retry >> 4) > (retry & 0x0F);
    }

    /**
     * Retry.
     *
     * @param duration the duration
     * @param timeUnit the time unit
     */
    public final void retry(int duration, TimeUnit timeUnit) {
        retry();
        setDelay(duration, timeUnit);
    }

    /**
     * Re open.
     *
     * @param attach the attach
     * @return true, if successful
     */
    public final boolean reOpen(Object attach) {
        if (!isDone) return false;
        attach(attach);
        retry();
        return true;
    }

    /**
     * Attach.
     *
     * @param object the object
     */
    public final void attach(Object object) {
        attachment = object;
    }


    /**
     * Sets the delay.
     *
     * @param duration the duration
     * @param timeUnit the time unit
     */
    protected final void setDelay(long duration, TimeUnit timeUnit) {
        if (duration > 0)
        {
            if (scheduleService != null)				scheduleService.mainQueue.updateDelay(this, duration, timeUnit);
            else //尚未进入 调度系统
            {
                doTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(duration, timeUnit);
            }
        }
    }

    /**
     * Gets the start time.
     *
     * @return the start time
     */
    public final long getStartTime() {
        return startTime;
    }

    /**
     * 子类在实现本类时，资源释放不必要全面完成，只需要将自身的资源完成释放即可
     * 其余的资源由资源自身进行释放.
     */
    @Override
    public void dispose() {
        if (timeoutCall != null && timeoutCall.isEnabled()) timeoutCall.onInvalid(this);
        timeoutCall = null;
        attachment = null;
        holdThread = null;
        progress = null;
        holdThread = null;
        scheduleService = null;
        if (wakeTimer != null && wakeTimer.isDisposable()) wakeTimer.dispose();
        wakeTimer = null;
        super.dispose();
    }


    /**
     * Do no dispose.
     */
    private void doNoDispose() {
        // TODO Auto-generated method stub

    }

    /**
     * Do dispse.
     */
    private void doDispse() {
        // TODO Auto-generated method stub
        if (timeoutCall != null && timeoutCall.isEnabled()) timeoutCall.onInvalid(this);
        timeoutCall = null;
        attachment = null;
        holdThread = null;
        progress = null;
        holdThread = null;
        scheduleService = null;
        if (wakeTimer != null) wakeTimer.dispose();
        wakeTimer = null;
    }
    /**
     * 此方法在Processor线程中执行.
     * isBlocker 特性可以在此方法内设置为true
     * 但是此方法不可进行任何可能导致Throwable的操作
     */
    @Override
    public void initTask() {
        isInit = true;
    }

    /**
     * before task.run();
     * 当isBlocker为true时 此方法将只执行一次.无论isCycle如何设置.
     *
     * @throws Exception the exception
     */
    protected void beforeRun() throws Exception {
        holdThread = Thread.currentThread();
        isPending = true;
    }

    /**
     * after task.finish();
     * 可能会由于run方法的exception而无法执行到.
     * 当isBlocker为true时，此方法只会执行一次.无论isCycle如何设置.
     *
     * @throws Exception the exception
     */
    protected void afterRun() throws Exception {
    }

    /**
     * 提交执行结果.
     *
     * @param result the result
     * @param action the action
     */
    public final void commitResult(ITaskResult result, CommitAction action) {
        commitResult(result, action, getListenSerial());
    }


    /**
     * Commit result.
     *
     * @param result the result
     */
    public final void commitResult(ITaskResult result) {
        commitResult(result, CommitAction.NOWAKE_UP);
    }


    /**
     * Commit result.
     *
     * @param result the result
     * @param action the action
     * @param listenerBind the listener bind
     */
    public final void commitResult(ITaskResult result, CommitAction action, int listenerBind) {
        try {
            if (scheduleService == null || result == null || action == null
                    || !scheduleService.isDisposable())
                return;
            if (result.getListenSerial() == 0)
                result.setListenSerial(listenerBind);
            boolean responsed = scheduleService.responseTask(result);
            if (scheduleService != null && responsed && isBloker
                    && CommitAction.WAKE_UP.equals(action)) {
                scheduleService.wakeUp();
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("Task", "scheduleService is Exception");
        }
    }


    /**
     * The Enum CommitAction.
     *
     * @ClassName: Task
     * @Description:
     * @author  作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
     * @version 创建时间：2013-12-25 14:11:38
     * Commit action.
     */
    public enum CommitAction {

        /** The wake up. */
        WAKE_UP,
        /** The nowake up. */
        NOWAKE_UP
    }


    /**
     * Cancel.
     */
    public final void cancel() {
        disable = true;
    }

    /**
     * Invalid.
     */
    public final void invalid() {
        invalid = isPending ? invalid : true;
    }


    /**
     * Finish.
     */
    protected void finish() {
        if (!isProxy && !isCycle && !isSegment)
        {
            isDone = true;
            isPending = false;
        }
        else if (!isDone && (isSegment || (isCycle && !isBloker))) isPending = false;
        invalid = false;
    }

    /* (non-Javadoc)
     * @see org.box.base.core.task.infc.ITask#finishTask()
     */
    @Override
    public void finishTask() {
        dispose();
    }

    /**
     * 进度.
     */
    protected void doAfterException() {
        if (progress != null) progress.finishProgress(ITaskProgress.TaskProgressType.error);
    }



    /**
     * Sets the progress.
     *
     * @param progress the new progress
     */
    public final void setProgress(ITaskProgress progress) {
        this.progress = progress;
    }

    /**
     * Gets the progress.
     *
     * @return the progress
     */
    public final ITaskProgress getProgress() {
        return progress;
    }


    /**
     * 任务超时以秒计算,>0代表超时时间有效,0将无视超时设计
     * 当超时操作生效时将执行callback接口.
     *
     * @param second the second
     * @param callBack the call back
     */
    @SuppressWarnings ("unchecked")
    public final void timeOut(int second, ITaskTimeout<?> callBack) {
        if (second < 0) throw new IllegalArgumentException("second must > 0");
        timeOut = second;
        timeoutCall = (ITaskTimeout<Task>) callBack;
    }

    /**
     * Finish thread task.
     */
    protected void finishThreadTask() {
    }


}
