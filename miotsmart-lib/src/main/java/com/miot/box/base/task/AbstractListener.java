/*
 * 
 */

package com.miot.box.base.task;



// TODO: Auto-generated Javadoc

import com.miot.box.base.core.task.infc.ITaskListener;

/**
 * 抽象监听类.
 * 
 * @author Administrator
 */
public abstract class AbstractListener implements ITaskListener {

    /** The bind serial. */
    private int bindSerial;

    /*
     * (non-Javadoc)
     * @see org.box.base.core.task.infc.ITaskListener#setBindSerial(int)
     */
    @Override
    public void setBindSerial(int bindSerial) {
        if (this.bindSerial == 0)
            this.bindSerial = bindSerial;
    }

    /*
     * (non-Javadoc)
     * @see org.box.base.core.task.infc.ITaskListener#getBindSerial()
     */
    @Override
    public int getBindSerial() {
        return bindSerial;
    }

    /*
     * (non-Javadoc)
     * @see org.box.base.core.task.infc.ITaskListener#isEnable()
     */
    @Override
    public boolean isEnable() {
        return enabled;
    }

    /** The enabled. */
    protected boolean enabled;

    /**
     * Instantiates a new abstract listener.
     */
    public AbstractListener() {
        enabled = true;
        setBindSerial(hashCode());
    }
}
