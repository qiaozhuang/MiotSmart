/*
 * 
 */

package com.miot.task;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.util.SparseArray;

import com.miot.box.base.core.task.infc.ITask;
import com.miot.box.base.core.task.infc.ITaskWakeTimer;
import com.miot.box.base.task.TaskService;

import java.util.concurrent.TimeUnit;



// TODO: Auto-generated Javadoc
/**
 * The Class IoAService.
 * 
 * @ClassName: IoAService
 * @Description:
 * @author
 * @version 创建时间：2013-12-25 14:11:36 Io a service.
 */
public class IoAService extends TaskService {

    /**
     * Instantiates a new io a service.
     */
    public IoAService() {
        super();
    }

    /** The power manager. */
    PowerManager powerManager;

    /** The main wake lock. */
    PowerManager.WakeLock mainWakeLock;

    /** The alarm manager. */
    AlarmManager alarmManager;

    /** The Constant AlarmGap. */
    final static long AlarmGap = TimeUnit.SECONDS.toMillis(600);

    /** The context. */
    Context context;

    /** The screen receiver. */
    final BroadcastReceiver screenReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                if (mainQueue.toWakeUpAbsoluteTime.get() < 0)
                    setScheduleAlarmTime(mainQueue.toWakeUpAbsoluteTime.get());
                else
                    wakeLock();
                isScreenOn = false;
            } else if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
                wakeUnlock();
                isScreenOn = true;
            } else if (TASK_SCHEDULE_ACTION.equals(intent.getAction())) {
                /*
                 * 无需担心Unlock 当队列中的头节点 设置完时间时将释放wakelock ,当队列中无任务时也将自动释放wakelock
                 */
                wakeLock();
                wakeUp();
            } else if (TASK_ALARM_ACTION.equals(intent.getAction())) {

                TaskAlarmTimer taskAlarmTimer = taskAlarmMap.get(intent.getIntExtra(
                        TASK_ALARM_ACTION, 0));
                if (taskAlarmTimer != null)
                    taskAlarmTimer.wakeUpTask();
                wakeLock(200);
            }
        }
    };

    /**
     * Sets the schedule alarm time.
     * 
     * @param RTC_WakeTime 负数将执行当前时间+AlarmGap <tt>long</tt> millsecond
     * @author Zhangzhuo
     */
    @Override
    protected final void setScheduleAlarmTime(long RTC_WakeTime) {
        PendingIntent alarmSender = getAlarmCancel();
        if (alarmSender != null)
            alarmManager.cancel(alarmSender);
        if (RTC_WakeTime < 0)
            RTC_WakeTime = System.currentTimeMillis() + AlarmGap;
        alarmSender = getAlarmSet();
        alarmManager.set(AlarmManager.RTC_WAKEUP, RTC_WakeTime, alarmSender);
        wakeUnlock();
    }

    /*
     * (non-Javadoc)
     * @see org.box.base.task.TaskService#noScheduleAlarmTime()
     */
    @Override
    protected final void noScheduleAlarmTime() {
        PendingIntent alarmSender = getAlarmCancel();
        if (alarmSender != null)
            alarmManager.cancel(alarmSender);
        wakeUnlock();
    }

    /*
     * (non-Javadoc)
     * @see org.box.base.task.TaskService#setTaskAlarmTime(long,
     * org.box.base.core.task.infc.ITaskWakeTimer)
     */
    @Override
    public final ITaskWakeTimer setTaskAlarmTime(long RTC_WakeTime, ITaskWakeTimer owner) {
        if (owner != null)
            owner.cancel();
        else
            owner = new TaskAlarmTimer();
        owner.setAlarmTime(RTC_WakeTime);
        return owner;
    }

    /** The task alarm map. */
    final SparseArray<TaskAlarmTimer> taskAlarmMap = new SparseArray<TaskAlarmTimer>(4);

    /**
     * The Class TaskAlarmTimer.
     * 
     * @ClassName: IoAService
     * @Description:
     * @author 作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
     * @version 创建时间：2013-12-25 14:11:36 Task alarm timer.
     */
    final class TaskAlarmTimer implements ITaskWakeTimer {

        /**
         * Instantiates a new task alarm timer.
         */
        public TaskAlarmTimer() {
            taskAlarmMap.put(hashCode(), this);
        }

        /** The my task. */
        ITask myTask;

        /*
         * (non-Javadoc)
         * @see org.box.base.core.task.infc.ITaskWakeTimer#setAlarmTime(long)
         */
        @Override
        public final ITaskWakeTimer setAlarmTime(long absoluteTime) {
            if (absoluteTime < 0)
                absoluteTime = System.currentTimeMillis() + AlarmGap;
            PendingIntent tAlarmSender = tGetAlarmSet();
            alarmManager.set(AlarmManager.RTC_WAKEUP, absoluteTime, tAlarmSender);
            return this;
        }

        /*
         * (non-Javadoc)
         * @see org.box.base.core.task.infc.ITaskWakeTimer#cancel()
         */
        @Override
        public final ITaskWakeTimer cancel() {
            PendingIntent tAlarmSender = tGetAlarmCancel();
            if (tAlarmSender != null)
                alarmManager.cancel(tAlarmSender);
            return this;
        }

        /*
         * (non-Javadoc)
         * @see org.box.base.core.task.infc.ITaskWakeTimer#wakeUpTask()
         */
        @Override
        public final void wakeUpTask() {
            if (myTask.needAlarm())
                myTask.wakeUpTask();
        }

        /*
         * (non-Javadoc)
         * @see
         * org.box.base.core.task.infc.ITaskWakeTimer#setTask(org.box.base.core
         * .task.infc.ITask)
         */
        @Override
        public final void setTask(ITask myTask) {
            this.myTask = myTask;
        }

        /*
         * (non-Javadoc)
         * @see org.box.base.core.task.infc.IDisposable#isDisposable()
         */
        @Override
        public final boolean isDisposable() {
            return true;
        }

        /*
         * (non-Javadoc)
         * @see org.box.base.core.task.infc.IDisposable#dispose()
         */
        @Override
        public final void dispose() {
            cancel();
            taskAlarmMap.remove(hashCode());
        }

        /**
         * T get alarm set.
         * 
         * @return the pending intent
         */
        private final PendingIntent tGetAlarmSet() {
            Intent intent = new Intent(TASK_ALARM_ACTION);
            intent.putExtra(TASK_ALARM_ACTION, hashCode());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, hashCode(), intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            return pendingIntent;
        }

        /**
         * T get alarm cancel.
         * 
         * @return the pending intent
         */
        private final PendingIntent tGetAlarmCancel() {
            Intent intent = new Intent(TASK_ALARM_ACTION);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, hashCode(), intent,
                    PendingIntent.FLAG_NO_CREATE);
            return pendingIntent;
        }

    }

    /** The Constant TASK_SCHEDULE_ACTION. */
    protected final static String TASK_SCHEDULE_ACTION = "AlarmTaskSchedule";

    /** The Constant TASK_ALARM_ACTION. */
    protected final static String TASK_ALARM_ACTION = "AlarmTaskSelf";

    /**
     * TaskService 启动函数 在此之前需要将listener都加入到监听队列中.
     * 
     * @param context the context
     */
    public final void startAService(Context context) {
        if (started)
            return;
        powerManager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        mainWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "Tina TaskService Schedule");
        mainWakeLock.setReferenceCounted(false);
        alarmManager = (AlarmManager)context.getSystemService(Service.ALARM_SERVICE);
        IntentFilter filter = new IntentFilter(TASK_ALARM_ACTION);
        filter.addAction(TASK_SCHEDULE_ACTION);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        context.registerReceiver(screenReceiver, filter);
        this.context = context;
        startService();
        started = true;
    }

    /** The started. */
    private volatile boolean started;

    /**
     * Stop a service.
     */
    public final void stopAService() {
        if (!started)
            return;
        context.unregisterReceiver(screenReceiver);
        wakeUnlock();
        mainWakeLock = null;
        PendingIntent alarmSender = getAlarmCancel();
        if (alarmSender != null)
            alarmManager.cancel(alarmSender);
        stopService();
        powerManager = null;
        alarmManager = null;
        this.context = null;
        started = false;
    }

    /**
     * Gets the alarm set.
     * 
     * @return the alarm set
     */
    private final PendingIntent getAlarmSet() {
        Intent intent = new Intent(TASK_SCHEDULE_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, hashCode(), intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;
    }

    /**
     * Gets the alarm cancel.
     * 
     * @return the alarm cancel
     */
    private final PendingIntent getAlarmCancel() {
        Intent intent = new Intent(TASK_SCHEDULE_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, hashCode(), intent,
                PendingIntent.FLAG_NO_CREATE);
        return pendingIntent;
    }

    /**
     * Wake lock.
     */
    public final void wakeLock() {
        wakeLock(0);
    }

    /**
     * Wake lock.
     * 
     * @param duration the duration
     */
    public final void wakeLock(long duration) {
        if (mainWakeLock != null) {
            if (duration > 0)
                mainWakeLock.acquire(duration);
            else {
                mainWakeLock.acquire();
            }
        }
    }

    /**
     * Wake unlock.
     */
    public final void wakeUnlock() {
        if (mainWakeLock == null || !mainWakeLock.isHeld())
            return;

        mainWakeLock.release();
    }

    /** The is screen on. */
    public boolean isScreenOn;

    /*
     * (non-Javadoc)
     * @see org.box.base.task.TaskService#getWakeLock()
     */
    @Override
    protected IWakeLock getWakeLock() {
        IWakeLock taskWakeLock = new IWakeLock() {
            private PowerManager.WakeLock wakeLock;

            @Override
            public void release() {
                wakeLock.release();
            }

            @Override
            public void initialize(String lockName) {
                wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, lockName);
                wakeLock.setReferenceCounted(false);
            }

            @Override
            public void acquire() {
                wakeLock.acquire();
            }
        };
        return taskWakeLock;
    }
}
