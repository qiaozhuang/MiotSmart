/*
 * 
 */
package com.box.common.util;

// TODO: Auto-generated Javadoc
/**
 * The Class MultiCardFilePath.
 *
 * @ClassName: MultiCardFilePath
 * @Description:
 * @author  作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
 * @version 创建时间：2013-12-25 14:11:35
 * Multi card file path.
 */
public class MultiCardFilePath {
	
	/** 返回OK. */
	public static final int RET_OK = 0;
	
	/** 返回内存不多预警. */
	public static final int RET_LIMIT_SPACE_WARNNING = 1;
	
	/** 文件完整路径. */
	private String filePath;
	
	/** 返回码. */
	private int code;
	
	
	/**
	 * 获取文件路径.
	 *
	 * @return the file path
	 */
	public String getFilePath() {
		return filePath;
	}
	
	/**
	 * 设置文件路径.
	 *
	 * @param mFilePath the new file path
	 */
	public void setFilePath(String mFilePath) {
		this.filePath = mFilePath;
	}
	
	/**
	 * 获取返回码.
	 *
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * 设置返回码.
	 *
	 * @param code the new code
	 */
	public void setCode(int code) {
		this.code = code;
	}
}
