/*
 * 
 */
package com.box.common.util.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class LimitSpaceUnwriteException.
 *
 * @ClassName: LimitSpaceUnwriteException
 * @Description:
 * @author  作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
 * @version 创建时间：2013-12-25 14:11:28
 * Limit space unwrite exception.
 */
public class LimitSpaceUnwriteException 
extends Exception{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5556913968169905814L;
	
	/**
	 * Instantiates a new limit space unwrite exception.
	 */
	public LimitSpaceUnwriteException() {
        super("存储空间不足，无法写入");
    }
}
