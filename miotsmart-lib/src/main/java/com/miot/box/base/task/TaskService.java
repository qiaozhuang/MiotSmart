/*
 * 
 */

package com.miot.box.base.task;

import android.util.Log;

import com.miot.box.base.core.task.infc.IDisposable;
import com.miot.box.base.core.task.infc.ITaskListener;
import com.miot.box.base.core.task.infc.ITaskProgress;
import com.miot.box.base.core.task.infc.ITaskResult;
import com.miot.box.base.core.task.infc.ITaskTimeout;
import com.miot.box.base.core.task.infc.ITaskWakeTimer;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;



// TODO: Auto-generated Javadoc
/**
 * The Class TaskService.
 * 
 * @ClassName: TaskService
 * @Description:
 * @author 作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
 * @version 创建时间：2013-12-25 14:11:38 Task service.
 */
public class TaskService implements Comparator<Task>, IDisposable {

	/** The tag. */
	protected final String TAG = getClass().getSimpleName();

	/** The Constant STAG. */
	public final static String STAG = TaskService.class.getSimpleName();

	// //#enddebug
	/** The Constant SERVICE_TASK_INIT. */
	final static byte SERVICE_TASK_INIT = -1;

	/** The Constant SERVICE_PROCESSING. */
	final static byte SERVICE_PROCESSING = SERVICE_TASK_INIT + 1;

	/** The Constant SERVICE_SCHEDULE. */
	final static byte SERVICE_SCHEDULE = SERVICE_PROCESSING + 1;

	/** The Constant SERVICE_NOTIFYOBSERVER. */
	final static byte SERVICE_NOTIFYOBSERVER = SERVICE_SCHEDULE + 1;

	/** The Constant SERVICE_DOWN. */
	final static byte SERVICE_DOWN = -128;

	/** The Constant LISTENER_INITIAL_NUM. */
	final static byte LISTENER_INITIAL_NUM = 10;

	/** The processor. */
	final Processor processor;

	/** The listeners. */
	final HashMap<Integer, ITaskListener> listeners;

	/** The response queue. */
	final ConcurrentLinkedQueue<ITaskResult> responseQueue;

	/** The main queue. */
	protected final ScheduleQueue<Task> mainQueue;

	/** The main lock. */
	final ReentrantLock mainLock = new ReentrantLock();

	/** The run lock. */
	final ReentrantLock runLock = new ReentrantLock();

	/**
	 * Instantiates a new task service.
	 */
	protected TaskService() {
		listeners = new HashMap<Integer, ITaskListener>(LISTENER_INITIAL_NUM);
		recycleListeners = new HashMap<Integer, ITaskListener>(
				LISTENER_INITIAL_NUM);
		mainQueue = new ScheduleQueue<Task>(this, this);
		responseQueue = new ConcurrentLinkedQueue<ITaskResult>();
		processor = new Processor();
		_instance = this;
	}

	public boolean isAllowRun;
	/** The _instance. */
	protected static TaskService _instance;

	/**
	 * Gets the single instance of TaskService.
	 * 
	 * @return single instance of TaskService
	 */
	public static TaskService getInstance() {
		if (_instance == null)
			throw new NullPointerException("No create service!");
		return _instance;
	}

	public final void startService() {
		processor.start();
		Thread.yield();
	}

	/**
	 * Stop service.
	 */
	public final void stopService() {
		processor.processing = false;
		processor.interrupt();
	}

	/**
	 * Sets the schedule alarm time.
	 * 
	 * @param RTC_WakeTime
	 *            the new schedule alarm time
	 */
	protected void setScheduleAlarmTime(long RTC_WakeTime) {
	}

	/**
	 * No schedule alarm time.
	 */
	protected void noScheduleAlarmTime() {

	}

	/**
	 * On processor stop.
	 */
	protected void onProcessorStop() {

	}

	/**
	 * Sets the task alarm time.
	 * 
	 * @param RTC_WakeTime
	 *            the rT c_ wake time
	 * @param owner
	 *            the owner
	 * @return the i task wake timer
	 */
	protected ITaskWakeTimer setTaskAlarmTime(long RTC_WakeTime,
											  ITaskWakeTimer owner) {
		return null;
	}

	@Override
	public final int compare(Task task1, Task task2) {
		int result = 0;
		result = task1.priority > task2.priority ? -1
				: task1.priority < task2.priority ? 1
						: task1.inQueueIndex < task2.inQueueIndex ? -1
								: task1.inQueueIndex > task2.inQueueIndex ? 1
										: 0;
		result = task1.doTime == task2.doTime ? result
				: task1.doTime < task2.doTime ? -1 : 1;
		if (result == 0)
			result = task1.hashCode() - task2.hashCode();
		return result;
	}

	/**
	 * Adds the listener.
	 * 
	 * @param listener
	 *            the listener
	 * @return true, if successful
	 */
	public final boolean addListener(ITaskListener listener) {
		if (listener == null)
			throw new NullPointerException();
		ReentrantLock mainLock = this.mainLock;
		try {
			if (mainLock.tryLock() || mainLock.tryLock(100, TimeUnit.SECONDS)) {
				Set<Integer> keySet = listeners.keySet();
				if (listener.getBindSerial() == 0
						|| keySet.contains(listener.getBindSerial())) {
					return false;
				}
				listeners.put(listener.getBindSerial(), listener);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, e.getMessage(), e);
		} finally {
			mainLock.unlock();
		}
		return false;
	}

	/** The recycle listener. */

	final HashMap<Integer, ITaskListener> recycleListeners;

	/**
	 * Sets the recycle.
	 * 
	 * @param recycle
	 *            the new recycle
	 * @return true, if successful
	 */
	public final boolean addRecycleListener(ITaskListener recycle) {
		if (recycle == null)
			throw new NullPointerException();
		ReentrantLock mainLock = this.mainLock;
		try {
			if (mainLock.tryLock() || mainLock.tryLock(100, TimeUnit.SECONDS)) {
				Set<Integer> keySet = listeners.keySet();
				if (recycle.getBindSerial() == 0
						|| keySet.contains(recycle.getBindSerial())) {
					return false;
				}
				recycleListeners.put(recycle.getBindSerial(), recycle);
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, e.getMessage(), e);
		} finally {
			mainLock.unlock();
		}
		return false;
	}

	/**
	 * Removes the listener.
	 * 
	 * @param bindSerial
	 *            the bind serial
	 * @return the i task listener
	 */
	public final ITaskListener removeListener(int bindSerial) {
		if (bindSerial == 0)
			return null;
		ReentrantLock mainLock = this.mainLock;
		try {
			if (mainLock.tryLock() || mainLock.tryLock(100, TimeUnit.SECONDS))
				return listeners.remove(bindSerial);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, e.getMessage(), e);
		} finally {
			mainLock.unlock();
		}
		return null;
	}

	/**
	 * Removes the recycle listener.
	 * 
	 * @param bindSerial
	 *            the bind serial
	 * @return the i task listener
	 */
	public final ITaskListener removeRecycleListener(int bindSerial) {
		if (bindSerial == 0)
			return null;
		ReentrantLock mainLock = this.mainLock;
		try {
			if (mainLock.tryLock() || mainLock.tryLock(100, TimeUnit.SECONDS))
				return recycleListeners.remove(bindSerial);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, e.getMessage(), e);
		} finally {
			mainLock.unlock();
		}
		return null;
	}

	/**
	 * Request service.
	 * 
	 * @param timerTask
	 *            the timer task
	 * @return true, if successful
	 */
	public final boolean requestService(TimerTask timerTask) {
		return requestService(timerTask, false, 0);
	}

	/**
	 * Request service.
	 * 
	 * @param task
	 *            the task
	 * @param schedule
	 *            the schedule
	 * @return true, if successful
	 */
	public final boolean requestService(Task task, boolean schedule) {
		return requestService(task, schedule, 0);
	}

	/**
	 * Request service.
	 * 
	 * @param task
	 *            the task
	 * @param bindSerial
	 *            the bind serial
	 * @return true, if successful
	 */
	public final boolean requestService(Task task, int bindSerial) {
		return requestService(task, false, bindSerial);
	}

	/**
	 * Request service.
	 * 
	 * @param task
	 *            正在执行和已经完成的任务不能再次进入任务队列
	 * @param schedule
	 *            是否将任务装入队列头
	 * @param listenerSerial
	 *            绑定的处理器
	 * @return {@code true}成功托管到任务队列 {@code false}
	 *         任务已经执行或完成;或者由于队列中已存在相同任务导致加入队列失败
	 */
	public final boolean requestService(Task task, boolean schedule,
			int listenerSerial) {
		if (task == null)
			throw new NullPointerException();
		if (task.isPending || task.isDone)
			return false;
		task.scheduleService = this;
		task.setListenSerial(listenerSerial);
		ScheduleQueue<Task> mainQueue = this.mainQueue;
		task.priority = schedule ? mainQueue.priorityIncrease.incrementAndGet()
				: 0;
		boolean success = mainQueue.offer(task);
		return success;
	}

	/**
	 * Request service.
	 * 
	 * @param task
	 *            the task
	 * @param isSchedule
	 *            the is schedule
	 * @param timelimit
	 *            the timelimit
	 * @param delayTimeMills
	 *            the delay time mills
	 * @param retryLimit
	 *            the retry limit
	 * @param attachment
	 *            the attachment
	 * @param progress
	 *            the progress
	 * @param timeOutSecound
	 *            the time out secound
	 * @param taskTimeout
	 *            the task timeout
	 * @param bindSerial
	 *            the bind serial
	 * @return true, if successful
	 */
	public final boolean requestService(Task task, boolean isSchedule,
										int timelimit, long delayTimeMills, byte retryLimit,
										Object attachment, ITaskProgress progress, int timeOutSecound,
										ITaskTimeout<?> taskTimeout, int bindSerial) {
		if (task == null)
			throw new NullPointerException();
		task.timeLimit = timelimit;
		task.setRetryLimit(retryLimit);
		task.attachment = attachment;
		task.setDelay(delayTimeMills, TimeUnit.MILLISECONDS);
		task.timeOut(timeOutSecound, taskTimeout);
		return requestService(task, isSchedule, bindSerial);
	}

	/**
	 * Request service retry.
	 * 
	 * @param task
	 *            the task
	 * @param timeOutSecound
	 *            the time out secound
	 * @return true, if successful
	 */
	public final boolean requestServiceRetry(Task task, int timeOutSecound) {
		if (task == null)
			throw new NullPointerException();
		task.timeOut(timeOutSecound, task.timeoutCall);
		return requestService(task, false, task.getListenSerial());
	}

	/**
	 * Request service.
	 * 
	 * @param task
	 *            the task
	 * @param delayTime
	 *            the delay time
	 * @param bindSerial
	 *            the bind serial
	 * @return true, if successful
	 */
	public final boolean requestService(Task task, long delayTime,
			int bindSerial) {
		return requestService(task, false, -1, delayTime, (byte) 0, null, null,
				0, null, bindSerial);
	}

	/**
	 * Cancel service.
	 * 
	 * @param task
	 *            the task
	 */
	public final void cancelService(Task task) {
		ScheduleQueue<Task> mainQueue = this.mainQueue;
		if (mainQueue != null) {
			mainQueue.remove(task);
		}
	}

	/**
	 * Cancel service.
	 * 
	 * @param threadID
	 *            the thread id
	 * @param available
	 *            the available
	 * @param schedule
	 *            the schedule
	 * @return true, if successful
	 */
	public final boolean cancelService(int threadID, int available,
			boolean schedule) {
		if (processor == null || processor.executor == null)
			return true;// û����Ҫ�رյķ��񣬺㷵��true
		Executor.Worker worker = processor.executor.id2work2.get(threadID);
		if (worker != null) {
			// worker.tryRelease(0);
		}
		return false;
	}

	public final boolean responseTask(ITaskResult taskResult) {
		if (taskResult == null)
			return false;
		if (taskResult.isResponsed())
			return false;
		taskResult.setResponse(false);
		return responseQueue.offer(taskResult);
	}

	/**
	 * Commit notify.
	 */
	public final void commitNotify() {
		wakeUp();
	}

	/**
	 * Wake up.
	 */
	protected final void wakeUp() {
		if (processor != null && !processor.isInterrupted())
			processor.interrupt();
	}

	/**
	 * Notify observer.
	 */
	private final void notifyObserver() {
		if (responseQueue.isEmpty())
			return;
		final ReentrantLock mainLock = this.mainLock;
		mainLock.lock();
		ITaskResult receive = null;
		boolean isHandled = false;
		try {
			while (!responseQueue.isEmpty()) {
				receive = responseQueue.poll();
				receive.setResponse(true);
				isHandled = false;
				int listenSerial = receive.getListenSerial();
				if (!listeners.isEmpty()) {
					if (listenSerial != 0) {
						ITaskListener listener = listeners.get(listenSerial);
						if (listener != null && listener.isEnable())
							try {
								isHandled = isHandled(receive, listener);
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								if (receive.isDisposable() && isHandled) {
									receive.dispose();
									removeListener(listenSerial);
								}
							}
					} else {
						for (ITaskListener listener : listeners.values()) {
							if (!listener.isEnable())
								continue;
							try {
								isHandled = isHandled(receive, listener);
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								if (isHandled) {
									removeListener(listenSerial);
									receive.dispose();
								}
							}
							if (isHandled)
								break;
						}
					}
				}
				if (recycleListeners.isEmpty()) {
					try {
						ITaskListener listener = recycleListeners
								.get(listenSerial);
						if (listener != null && listener.isEnable()) {
							listener.exceptionCaught(receive, this);
						}
					} catch (Exception e) {
						// #debug debug
						e.printStackTrace();
					} finally {
						removeRecycleListener(listenSerial);
						receive.dispose();
					}
				}
			}
		} finally {
			mainLock.unlock();
		}
	}

	/**
	 * Checks if is handled.
	 * 
	 * @param receive
	 *            the receive
	 * @param listener
	 *            the listener
	 * @return true, if is handled
	 */
	final boolean isHandled(ITaskResult receive, ITaskListener listener) {
		int result = receive.getSerialNum();
		if (result == Integer.MIN_VALUE || result == Integer.MAX_VALUE)
			return false;
		return receive.hasError() ? listener.exceptionCaught(receive, this)
				: listener.ioHandle(receive, this);
	}

	/**
	 * The Class Processor.
	 * 
	 * @ClassName: TaskService
	 * @Description:
	 * @author 作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
	 * @version 创建时间：2013-12-25 14:11:38 Processor.
	 */
	final class Processor extends Thread implements IDisposable {

		/**
		 * Instantiates a new processor.
		 */
		public Processor() {
			processing = true;
			setName("taskService-processor");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.box.base.core.task.infc.IDisposable#dispose()
		 */
		@Override
		public final void dispose() {
			executor = null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.box.base.core.task.infc.IDisposable#isDisposable()
		 */
		@Override
		public final boolean isDisposable() {
			return true;
		}

		/** The processing. */
		volatile boolean processing;

		/** The wait start. */
		long waitStart;

		/** The executor. */
		Executor executor;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public final void run() {
			final ScheduleQueue<Task> mainQueue = TaskService.this.mainQueue;
			Task curTask = null;
			byte serviceState = SERVICE_SCHEDULE;
			mainLoop: while (processing) {
				processor_switch: {
					switch (serviceState) {
					case SERVICE_TASK_INIT:
						curTask.initTask();
						if (curTask.isBlocker()) {
							if (executor == null)
								executor = new Executor(mainQueue);
							executor.execute(curTask);
							curTask = null;
							serviceState = SERVICE_SCHEDULE;
							break processor_switch;
						}
						serviceState = SERVICE_PROCESSING;
					case SERVICE_PROCESSING:
						try {
							curTask.beforeRun();
							curTask.runTask();
							curTask.finish();
							curTask.afterRun();
						} catch (Exception e) {
							// #debug error
							e.printStackTrace();
							curTask.setError(e);
							curTask.doAfterException();
							curTask.setDone();
							curTask.commitResult(curTask);
						} finally {
							/*
							 * ÿ������ִ�н���֮ǰ�����һ��responseQueue�Ƿ��������
							 * �Ҫ�� ��
							 */
							notifyObserver();
							if (!curTask.hasError())
								curTask.finishTask();// ���curTask��Я�������Ϣ��ִ��finishTask����
							if (!curTask.isDone && !curTask.isPending) {
								curTask.priority = 0;
								mainQueue.offer(curTask);// ����δ������½������ִ��,
							}
							curTask = null;
							serviceState = SERVICE_SCHEDULE;
						}
					case SERVICE_SCHEDULE:
						try {
							curTask = mainQueue.take();
						} catch (InterruptedException ie) {
						} catch (Exception e) {
						}
						if (curTask != null) {
							if (curTask.isDone || curTask.disable) {
								curTask = null;
								continue mainLoop;
							}
							serviceState = SERVICE_TASK_INIT;
							break processor_switch;
						}
					case SERVICE_NOTIFYOBSERVER:
						notifyObserver();
						serviceState = SERVICE_SCHEDULE;
						break processor_switch;
					}
				}
			}
			mainQueue.clear();
			onProcessorStop();
		}
	}

	/** The Constant COUNT_BITS. */
	private static final int COUNT_BITS = Integer.SIZE - 3;

	/** The Constant CAPACITY. */
	private static final int CAPACITY = (1 << COUNT_BITS) - 1;

	/** The Constant RUNNING. */
	private static final int RUNNING = -1 << COUNT_BITS;

	/** The Constant SHUTDOWN. */
	private static final int SHUTDOWN = 0 << COUNT_BITS;

	/** The Constant STOP. */
	private static final int STOP = 1 << COUNT_BITS;

	/** The Constant TIDYING. */
	private static final int TIDYING = 2 << COUNT_BITS;

	/** The Constant TERMINATED. */
	private static final int TERMINATED = 3 << COUNT_BITS;

	/**
	 * Run state of.
	 * 
	 * @param c
	 *            the c
	 * @return the int
	 */
	private static int runStateOf(int c) {
		return c & ~CAPACITY;
	}

	/**
	 * Worker count of.
	 * 
	 * @param c
	 *            the c
	 * @return the int
	 */
	private static int workerCountOf(int c) {
		return c & CAPACITY;
	}

	/**
	 * Ctl of.
	 * 
	 * @param rs
	 *            the rs
	 * @param wc
	 *            the wc
	 * @return the int
	 */
	private static int ctlOf(int rs, int wc) {
		return rs | wc;
	}

	/**
	 * Run state less than.
	 * 
	 * @param c
	 *            the c
	 * @param s
	 *            the s
	 * @return true, if successful
	 */
	private static boolean runStateLessThan(int c, int s) {
		return c < s;
	}

	/**
	 * Run state at least.
	 * 
	 * @param c
	 *            the c
	 * @param s
	 *            the s
	 * @return true, if successful
	 */
	private static boolean runStateAtLeast(int c, int s) {
		return c >= s;
	}

	/**
	 * Checks if is running.
	 * 
	 * @param c
	 *            the c
	 * @return true, if is running
	 */
	private static boolean isRunning(int c) {
		return c < SHUTDOWN;
	}

	/**
	 * The Class Executor.
	 * 
	 * @ClassName: TaskService
	 * @Description:
	 * @author 作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
	 * @version 创建时间：2013-12-25 14:11:38 Executor.
	 */
	final class Executor {

		/** The ctl. */
		final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

		/** The main lock. */
		final ReentrantLock mainLock = new ReentrantLock();

		/** The termination. */
		final Condition termination = mainLock.newCondition();

		/** The work queue. */
		final BlockingQueue<Task> workQueue;

		/** The main queue. */
		final ScheduleQueue<Task> mainQueue;

		/** The workers. */
		final HashSet<Worker> workers = new HashSet<Worker>();

		/** The id2work2. */
		final HashMap<Integer, Worker> id2work2 = new HashMap<Integer, Worker>();

		/** The completed task count. */
		long completedTaskCount;

		/** The allow core thread time out. */
		volatile boolean allowCoreThreadTimeOut;

		/** The core pool size. */
		volatile int corePoolSize;

		/** The thread factory. */
		volatile ThreadFactory threadFactory;

		/** The maximum pool size. */
		volatile int maximumPoolSize;

		/** The keep alive time. */
		volatile long keepAliveTime;

		/** The largest pool size. */
		int largestPoolSize;

		/** The Constant ONLY_ONE. */
		static final boolean ONLY_ONE = true;

		/**
		 * Instantiates a new executor.
		 * 
		 * @param mainQueue
		 *            the main queue
		 */
		public Executor(ScheduleQueue<Task> mainQueue) {
			threadFactory = new WorkerFactory();
			corePoolSize = 0;
			allowCoreThreadTimeOut = true;
			maximumPoolSize = 1024;
			keepAliveTime = TimeUnit.SECONDS.toNanos(30);
			workQueue = new SynchronousQueue<Task>();
			this.mainQueue = mainQueue;
		}

		/**
		 * A factory for creating Worker objects.
		 */
		final class WorkerFactory implements ThreadFactory {

			/** The id. */
			final AtomicInteger id;

			/**
			 * Instantiates a new worker factory.
			 */
			public WorkerFactory() {
				id = new AtomicInteger(0);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
			 */
			@Override
			public Thread newThread(Runnable r) {
				if (id.get() == maximumPoolSize)
					id.set(0);
				return new Thread(r, "TaskService" + "-pool-"
						+ id.getAndIncrement());
			}
		}

		/**
		 * Compare and increment worker count.
		 * 
		 * @param expect
		 *            the expect
		 * @return true, if successful
		 */
		private boolean compareAndIncrementWorkerCount(int expect) {
			return ctl.compareAndSet(expect, expect + 1);
		}

		/**
		 * Compare and decrement worker count.
		 * 
		 * @param expect
		 *            the expect
		 * @return true, if successful
		 */
		private boolean compareAndDecrementWorkerCount(int expect) {
			return ctl.compareAndSet(expect, expect - 1);
		}

		/**
		 * Decrement worker count.
		 */
		private void decrementWorkerCount() {
			do {
				// #debug
				System.err.println("decrementWorkerCount--:" + ctl.get());
			} while (!compareAndDecrementWorkerCount(ctl.get()));
		}

		/**
		 * Execute.
		 * 
		 * @param task
		 *            the task
		 */
		public void execute(Task task) {
			if (task == null)
				throw new NullPointerException();
			if (task.threadId != 0) {
				final ReentrantLock lock = this.mainLock;
				lock.lock();
				try {
					Worker worker = id2work2.get(task.threadId);
					if (worker != null && worker.taskBlkQueue.offer(task))
						return;
				} finally {
					lock.unlock();
				}
			}

			int c = ctl.get();
			if (workerCountOf(c) < corePoolSize) {
				if (addWorker(task, true))
					return;
				c = ctl.get();
			}
			if (isRunning(c) && workQueue.offer(task)) {
				int recheck = ctl.get();
				if (!isRunning(recheck) && remove(task))
					reject(task);
				else if (workerCountOf(recheck) == 0)
					addWorker(null, false);
			} else if (!addWorker(task, false))
				reject(task);
		}

		/**
		 * Reject.
		 * 
		 * @param task
		 *            the task
		 */
		private void reject(Task task) {
			boolean success = mainQueue.offer(task);
			if (!success)
				System.err.println("reject task failed! " + task + " | "
						+ mainQueue);
		}

		/**
		 * Gets the task.
		 * 
		 * @param w
		 *            the w
		 * @return the task
		 */
		private Task getTask(Worker w) {
			boolean timedOut = false; // Did the last poll() time out?

			retry: for (;;) {
				int c = ctl.get();
				int rs = runStateOf(c);

				// Check if queue empty only if necessary.
				if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
					decrementWorkerCount();
					return null;
				}

				boolean timed; // Are workers subject to culling?

				for (;;) {
					int wc = workerCountOf(c);
					timed = allowCoreThreadTimeOut || wc > corePoolSize;

					if (wc <= maximumPoolSize && !(timedOut && timed))
						break;
					if (compareAndDecrementWorkerCount(c))
						return null;
					c = ctl.get(); // Re-read ctl
					if (runStateOf(c) != rs)
						continue retry;
					// else CAS failed due to workerCount change; retry inner
					// loop
				}

				try {
					Task t = null;
					if (w.taskThreadId != 0)
						t = w.taskBlkQueue.poll(keepAliveTime,
								TimeUnit.NANOSECONDS);
					if (t == null)
						t = timed ? workQueue.poll(keepAliveTime,
								TimeUnit.NANOSECONDS) : workQueue.take();
					if (t != null)
						return t;
					timedOut = true;
				} catch (InterruptedException retry) {
					timedOut = false;
				}
			}
		}

		/**
		 * Process worker exit.
		 * 
		 * @param w
		 *            the w
		 * @param completedAbruptly
		 *            the completed abruptly
		 */
		private void processWorkerExit(Worker w, boolean completedAbruptly) {
			if (completedAbruptly) // If abrupt, then workerCount wasn't
				// adjusted
				decrementWorkerCount();

			final ReentrantLock mainLock = this.mainLock;
			mainLock.lock();
			try {
				completedTaskCount += w.completedTasks;
				workers.remove(w);
			} finally {
				mainLock.unlock();
			}

			tryTerminate();

			int c = ctl.get();
			if (runStateLessThan(c, STOP)) {
				if (!completedAbruptly) {
					int min = allowCoreThreadTimeOut ? 0 : corePoolSize;
					if (min == 0 && !workQueue.isEmpty())
						min = 1;
					if (workerCountOf(c) >= min)
						return; // replacement not needed
				}
				addWorker(null, false);
			}
		}

		/**
		 * Try terminate.
		 */
		final void tryTerminate() {
			for (;;) {
				int c = ctl.get();
				if (isRunning(c) || runStateAtLeast(c, TIDYING)
						|| (runStateOf(c) == SHUTDOWN && !workQueue.isEmpty()))
					return;
				if (workerCountOf(c) != 0) { // Eligible to terminate
					interruptIdleWorkers(ONLY_ONE);
					return;
				}

				final ReentrantLock mainLock = this.mainLock;
				mainLock.lock();
				try {
					if (ctl.compareAndSet(c, ctlOf(TIDYING, 0))) {
						try {
							// terminated();
						} finally {
							ctl.set(ctlOf(TERMINATED, 0));
							termination.signalAll();
						}
						return;
					}
				} finally {
					mainLock.unlock();
				}
				// else retry on failed CAS
			}
		}

		/**
		 * Await termination.
		 * 
		 * @param timeout
		 *            the timeout
		 * @param unit
		 *            the unit
		 * @return true, if successful
		 * @throws InterruptedException
		 *             the interrupted exception
		 */
		public boolean awaitTermination(long timeout, TimeUnit unit)
				throws InterruptedException {
			long nanos = unit.toNanos(timeout);
			final ReentrantLock mainLock = this.mainLock;
			mainLock.lock();
			try {
				for (;;) {
					if (runStateAtLeast(ctl.get(), TERMINATED))
						return true;
					if (nanos <= 0)
						return false;
					nanos = termination.awaitNanos(nanos);
				}
			} finally {
				mainLock.unlock();
			}
		}

		/**
		 * Removes the.
		 * 
		 * @param task
		 *            the task
		 * @return true, if successful
		 */
		public boolean remove(Task task) {
			boolean removed = workQueue.remove(task);
			tryTerminate(); // In case SHUTDOWN and now empty
			return removed;
		}

		/**
		 * Adds the worker.
		 * 
		 * @param firstTask
		 *            the first task
		 * @param core
		 *            the core
		 * @return true, if successful
		 */
		private boolean addWorker(Task firstTask, boolean core) {
			retry: for (;;) {
				int c = ctl.get();
				int rs = runStateOf(c);

				// Check if queue empty only if necessary.
				if (rs >= SHUTDOWN
						&& !(rs == SHUTDOWN && firstTask == null && !workQueue
								.isEmpty()))
					return false;

				for (;;) {
					int wc = workerCountOf(c);
					if (wc >= CAPACITY
							|| wc >= (core ? corePoolSize : maximumPoolSize))
						return false;
					if (compareAndIncrementWorkerCount(c))
						break retry;
					c = ctl.get(); // Re-read ctl
					if (runStateOf(c) != rs)
						continue retry;
					// else CAS failed due to workerCount change; retry inner
					// loop
				}
			}

			Worker w = new Worker(firstTask);
			Thread t = w.thread;

			final ReentrantLock mainLock = this.mainLock;
			mainLock.lock();
			try {
				// Recheck while holding lock.
				// Back out on ThreadFactory failure or if
				// shut down before lock acquired.
				int c = ctl.get();
				int rs = runStateOf(c);

				if (t == null
						|| (rs >= SHUTDOWN && !(rs == SHUTDOWN && firstTask == null))) {
					decrementWorkerCount();
					tryTerminate();
					return false;
				}

				workers.add(w);
				int s = workers.size();
				if (s > largestPoolSize)
					largestPoolSize = s;
			} finally {
				mainLock.unlock();
			}

			t.start();
			// It is possible (but unlikely) for a thread to have been
			// added to workers, but not yet started, during transition to
			// STOP, which could result in a rare missed interrupt,
			// because Thread.interrupt is not guaranteed to have any effect
			// on a non-yet-started Thread (see Thread#interrupt).
			if (runStateOf(ctl.get()) == STOP && !t.isInterrupted())
				t.interrupt();

			return true;
		}

		/**
		 * Interrupt idle workers.
		 * 
		 * @param onlyOne
		 *            the only one
		 */
		private void interruptIdleWorkers(boolean onlyOne) {
			final ReentrantLock mainLock = this.mainLock;
			mainLock.lock();
			try {
				for (Worker w : workers) {
					Thread t = w.thread;
					if (!t.isInterrupted() && w.tryLock()) {
						try {
							t.interrupt();
						} catch (SecurityException ignore) {
						} finally {
							w.unlock();
						}
					}
					if (onlyOne)
						break;
				}
			} finally {
				mainLock.unlock();
			}
		}

		/**
		 * Advance run state.
		 * 
		 * @param targetState
		 *            the target state
		 */
		void advanceRunState(int targetState) {
			while (true) {
				int c = ctl.get();
				if (runStateAtLeast(c, targetState)
						|| ctl.compareAndSet(c,
								ctlOf(targetState, workerCountOf(c))))
					break;
			}
		}

		/**
		 * Shutdown.
		 */
		public void shutdown() {
			final ReentrantLock mainLock = this.mainLock;
			mainLock.lock();
			try {
				advanceRunState(SHUTDOWN);
				interruptIdleWorkers();
			} finally {
				mainLock.unlock();
			}
			tryTerminate();
		}

		/**
		 * Interrupt idle workers.
		 */
		private void interruptIdleWorkers() {
			interruptIdleWorkers(false);
		}

		/**
		 * The Class Worker.
		 * 
		 * @ClassName: TaskService
		 * @Description:
		 * @author 作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
		 * @version 创建时间：2013-12-25 14:11:38 Worker.
		 */
		final class Worker extends AbstractQueuedSynchronizer implements
				Runnable {

			/** The Constant serialVersionUID. */
			private static final long serialVersionUID = 3833487996901286223L;

			/** The task blk queue. */
			final BlockingQueue<Task> taskBlkQueue;

			/** The first task. */
			Task firstTask;

			/** The last task. */
			Task lastTask;

			/** The task thread id. */
			volatile int taskThreadId;

			/** The completed tasks. */
			volatile long completedTasks;

			/** The wake lock. */
			IWakeLock wakeLock;

			/** The thread. */
			Thread thread;

			/**
			 * Instantiates a new worker.
			 * 
			 * @param task
			 *            the task
			 */
			public Worker(Task task) {
				firstTask = task;
				taskBlkQueue = new LinkedBlockingQueue<Task>();
				wakeLock = getWakeLock();
				thread = threadFactory.newThread(this);
				if (wakeLock != null && thread != null)
					wakeLock.initialize("worker#" + thread.getId());
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.concurrent.locks.AbstractQueuedSynchronizer#
			 * isHeldExclusively()
			 */
			@Override
			protected boolean isHeldExclusively() {
				return getState() == 1;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.util.concurrent.locks.AbstractQueuedSynchronizer#tryAcquire
			 * (int)
			 */
			@Override
			protected boolean tryAcquire(int unused) {
				if (compareAndSetState(0, 1)) {
					setExclusiveOwnerThread(Thread.currentThread());
					return true;
				}
				return false;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.util.concurrent.locks.AbstractQueuedSynchronizer#tryRelease
			 * (int)
			 */
			@Override
			protected boolean tryRelease(int unused) {
				setExclusiveOwnerThread(null);
				setState(0);
				return true;
			}

			/**
			 * Lock.
			 */
			public void lock() {
				acquire(1);
			}

			/**
			 * Try lock.
			 * 
			 * @return true, if successful
			 */
			public boolean tryLock() {
				return tryAcquire(1);
			}

			/**
			 * Unlock.
			 */
			public void unlock() {
				release(1);
			}

			/**
			 * Checks if is locked.
			 * 
			 * @return true, if is locked
			 */
			public boolean isLocked() {
				return isHeldExclusively();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				runWorker(this);
			}

		}

		/**
		 * Run worker.
		 * 
		 * @param w
		 *            the w
		 */
		final void runWorker(Worker w) {
			Task task = w.firstTask;
			Task lastTask = task;
			w.firstTask = null;
			boolean completedAbruptly = true;
			try {
				workLoop: for (;;) {
					while (task != null || (task = getTask(w)) != null) {
						lastTask = task;
						w.lock();
						clearInterruptsForTaskRun();
						try {
							if (task.disable || task.isDone)
								continue;
							if (w.taskThreadId == 0 && task.threadId != 0) {
								final ReentrantLock lock = mainLock;
								lock.lock();
								try {
									w.taskThreadId = task.threadId;
									id2work2.put(w.taskThreadId, w);
								} finally {
									lock.unlock();
								}
							}
							/*** 并非所有的任务都强制要求cpu 唤醒状态保护. */
							if (task.wakeLock && w.wakeLock != null)
								w.wakeLock.acquire();
							try {
								task.beforeRun();
								run: for (;;) {
									task.runTask();
									task.finish();
									if (!task.isDone && task.isCycle)
										continue run;// 任务还将继续执行
									else if (!task.isDone && task.isSegment) {
										// 判断当前工作线程的队列状态,如果非空将进行任务切换,为空则不进行任务切换
										if (!w.taskBlkQueue.isEmpty()) {
											w.taskBlkQueue.offer(task);
											break run;
										} else if (w.taskThreadId != 0
												&& w.taskThreadId == task.threadId)
											continue run;
										// 回到主队列重新进行分发
										else
											mainQueue.offer(task);
									} else
										break run;
								}
								task.afterRun();
							} catch (Exception x) {
								task.setError(x);
								task.doAfterException();
								task.setDone();
								task.commitResult(task, Task.CommitAction.WAKE_UP);
							} finally {
								if (task.wakeLock && w.wakeLock != null)
									w.wakeLock.release();
								if (!task.hasError())
									task.finishTask();
							}
						} finally {
							task = null;
							w.completedTasks++;
							w.unlock();
						}
					}
					final ReentrantLock lock = this.mainLock;
					lock.lock();
					try {
						if (w.taskBlkQueue.isEmpty()) {
							id2work2.remove(w.taskThreadId);
							w.taskThreadId = 0;
							break workLoop;
						} else {
							retry: for (;;) {
								int c = ctl.get();
								int rs = runStateOf(c);

								// Check if queue empty only if necessary.
								if (rs >= SHUTDOWN)
									break workLoop;

								for (;;) {
									if (compareAndIncrementWorkerCount(c))
										break retry;
									c = ctl.get(); // Re-read ctl
									if (runStateOf(c) != rs)
										continue retry;
									// else CAS failed due to workerCount
									// change; retry inner loop
								}
							}
						}
					} finally {
						lock.unlock();
					}
				}
				if (lastTask != null) {
					lastTask.finishThreadTask();
					lastTask = null;
				}
				completedAbruptly = false;
			} finally {
				processWorkerExit(w, completedAbruptly);
			}
		}

		/**
		 * Clear interrupts for task run.
		 */
		private void clearInterruptsForTaskRun() {
			if (runStateLessThan(ctl.get(), STOP) && Thread.interrupted()
					&& runStateAtLeast(ctl.get(), STOP))
				Thread.currentThread().interrupt();
		}
	}

	/**
	 * The Interface IWakeLock.
	 * 
	 * @ClassName: TaskService
	 * @Description:
	 * @author 作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
	 * @version 创建时间：2013-12-25 14:11:38 I wake lock.
	 */
	public static interface IWakeLock {

		/**
		 * Acquire.
		 */
		public void acquire();

		/**
		 * Release.
		 */
		public void release();

		/**
		 * Initialize.
		 * 
		 * @param lockName
		 *            the lock name
		 */
		public void initialize(String lockName);
	}

	/**
	 * Gets the wake lock.
	 * 
	 * @return the wake lock
	 */
	protected IWakeLock getWakeLock() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.box.base.core.task.infc.IDisposable#dispose()
	 */
	@Override
	public void dispose() {
		_instance = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.box.base.core.task.infc.IDisposable#isDisposable()
	 */
	@Override
	public boolean isDisposable() {
		return true;
	}

}
