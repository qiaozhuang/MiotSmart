/*
 * 
 */
package com.miot.box.base.core.task.infc;



// TODO: Auto-generated Javadoc

import com.miot.box.base.task.Task;

/**
 * A factory for creating ITask objects.
 *
 * @param <S> the generic type
 * @param <I> the generic type
 * @param <K> the key type
 * @param <T> the generic type
 */
public interface ITaskFactory<S, I, K, T extends Task> {
	
	/**
	 * Creates a new ITask object.
	 *
	 * @param arg1 the arg1
	 * @param mode the mode
	 * @param arg3 the arg3
	 * @return the t
	 */
	public T createTask(S arg1, int mode, K arg3);	
	
	/**
	 * Creates a new ITask object.
	 *
	 * @param arg1 the arg1
	 * @param arg3 the arg3
	 * @return the t
	 */
	public T createTask(I arg1, K arg3);	
	
	/**
	 * Creates a new ITask object.
	 *
	 * @param arg1 the arg1
	 * @param mode the mode
	 * @return the t
	 */
	public T createTask(I arg1, int mode);	
	
	/**
	 * Checks if is surport.
	 *
	 * @param task the task
	 * @return true, if is surport
	 */
	public boolean isSurport(T task);	
	
	/**
	 * Gets the type.
	 *
	 * @param task the task
	 * @return the type
	 */
	public byte getType(T task);
}	
