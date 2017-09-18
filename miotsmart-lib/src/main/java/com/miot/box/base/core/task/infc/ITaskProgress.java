/*
 * 
 */
package com.miot.box.base.core.task.infc;
 
// TODO: Auto-generated Javadoc
/**
 * The Interface ITaskProgress.
 *
 * @ClassName: ITaskProgress
 * @Description:
 * @author  作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
 * @version 创建时间：2013-12-25 14:11:35
 * I task progress.
 */
public interface ITaskProgress {
	
	/**
	 * Creates the progress.
	 *
	 * @param type the type
	 * @param max the max
	 */
	public void createProgress(TaskProgressType type, int max);
	
	/**
	 * Update progress.
	 *
	 * @param type the type
	 * @param increase the increase
	 */
	public void updateProgress(TaskProgressType type, int increase);
	
	/**
	 * Finish progress.
	 *
	 * @param type the type
	 */
	public void finishProgress(TaskProgressType type);
	
	/**
	 * The Enum TaskProgressType.
	 *
	 * @ClassName: ITaskProgress
	 * @Description:
	 * @author  作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
	 * @version 创建时间：2013-12-25 14:11:35
	 * Task progress type.
	 */
	public enum TaskProgressType {
		
		/** The error. */
		error, 
 /** The cancel. */
 cancel, 
 /** The percent. */
 percent, 
 /** The horizontal. */
 horizontal,
/** The vertical. */
vertical, 
 /** The cycle. */
 cycle, 
 /** The complete. */
 complete
	};
}
