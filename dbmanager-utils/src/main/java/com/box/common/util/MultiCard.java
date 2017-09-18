/*
 * 
 */

package com.box.common.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.box.common.util.exception.LimitSpaceUnwriteException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc

/**
 * The Class MultiCard.
 * 
 * @ClassName: MultiCard
 * @Description:
 * @author 作者 E-mail <a href="mailto:yubo@51box.cn">禹波</a>
 * @version 创建时间：2013-12-25 14:11:32 Multi card.
 */
public class MultiCard {

	/** The m. */
	private final long M = 1024 * 1024;

	/** The k. */
	private final long K = 1024;

	/** 外置存储卡默认预警临界值. */
	private final long DEF_EXTERNAL_SDCARD_WARNNING_LIMIT_SPACE_SIZE = 100 * M;

	/** 内置存储卡默认预警临界值. */
	private final long DEF_INTERNAL_SDCARD_WARNNING_LIMIT_SPACE_SIZE = 100 * M;

	/** 手机默认预警临界值. */
	private final long DEF_PHONE_WARNNING_LIMIT_SPACE_SIZE = 50 * M;

	/** 默认视频大小最大值. */
	private final long DEF_VIDEO_MAX_SIZE = 50 * M;

	/** 默认图片大小最大值. */
	private final long DEF_IMAGE_MAX_SIZE = 500 * K;

	/** 默认文本大小最大值. */
	private final long DEF_TXT_MAX_SIZE = 300 * K;

	/** 默认日志大小最大值. */
	private final long DEF_LOG_MAX_SIZE = 300 * K;

	/** 默认音频大小最大值. */
	private final long DEF_AUDIO_MAX_SIZE = 10 * M;

	/** 默认自定义数据大小最大值. */
	private final long DEF_DATA_MAX_SIZE = 50 * M;

	/** 默认头像大小最大值. */
	private final long DEF_HEAD_MAX_SIZE = 500 * K;

	/** 默认APK大小最大值. */
	private final long DEF_APK_MAX_SIZE = 20 * M;

	/** 媒体类型. */
	public final static int TYPE_DATA = 0;

	/** The Constant TYPE_VIDEO. */
	public final static int TYPE_VIDEO = 1;

	/** The Constant TYPE_IMAGE. */
	public final static int TYPE_IMAGE = 2;

	/** The Constant TYPE_TXT. */
	public final static int TYPE_TXT = 3;

	/** The Constant TYPE_LOG. */
	public final static int TYPE_LOG = 4;

	/** The Constant TYPE_AUDIO. */
	public final static int TYPE_AUDIO = 5;

	/** The Constant TYPE_HEAD. */
	public final static int TYPE_HEAD = 6;

	/** The Constant TYPE_APK. */
	public final static int TYPE_APK = 7;

	public final static int TYPE_JAR = 8;
	
	public final static int TYPE_HTML = 9;
	
	public final static int TYPE_ZIP = 10;

	/** 目标目录. */
	private final int DIRECTORY_EXTERNAL_SDCARD = 0;

	/** The directory internal sdcard. */
	private final int DIRECTORY_INTERNAL_SDCARD = 1;

	/** The directory phone. */
	private final int DIRECTORY_PHONE = 2;

	/** 文件格式. */
	private final String FORMAT_VIDEO = ".vid";

	/** The format image jpg. */
	private final String FORMAT_IMAGE_JPG = ".jpg";

	/** The format image bmp. */
	private final String FORMAT_IMAGE_BMP = ".bmp";

	/** The format image jpeg. */
	private final String FORMAT_IMAGE_JPEG = ".jpeg";

	/** The format image png. */
	private final String FORMAT_IMAGE_PNG = ".png";

	/** The format image gif. */
	private final String FORMAT_IMAGE_GIF = ".gif";

	/** The format txt. */
	private final String FORMAT_TXT = ".txt";

	/** The format log. */
	private final String FORMAT_LOG = ".log";

	/** The FORMA t_ audi o_ m p4. */
	private final String FORMAT_AUDIO_MP4 = ".mp4";

	/** The FORMA t_ audi o_ m4 a. */
	private final String FORMAT_AUDIO_M4A = ".m4a";

	/** The FORMA t_ audi o_3 gpp. */
	private final String FORMAT_AUDIO_3GPP = ".3gpp";

	/** The format data. */
	private final String FORMAT_DATA = ".dat";

	/** The format apk. */
	private final String FORMAT_APK = ".apk";

	/** The format jar. */
	private final String FORMAT_JAR = ".jar";
	
	/** The format jar. */
	private final String FORMAT_HTML = ".html";
	
	private final String FORMAT_ZIP = ".zip";


	/** 目录名. */
	// public final static String BOX_DIRECTORY_NAME = "llgj/";

	/** The audio directory name. */
	private final String AUDIO_DIRECTORY_NAME = "audio/";

	/** The video directory name. */
	private final String VIDEO_DIRECTORY_NAME = "video/";

	/** The txt directory name. */
	private final String TXT_DIRECTORY_NAME = "txt/";

	/** The log directory name. */
	private final String LOG_DIRECTORY_NAME = "log/";

	/** The Constant IMAGE_DIRECTORY_NAME. */
	public final static String IMAGE_DIRECTORY_NAME = "image/";

	/** The data directory name. */
	private final String DATA_DIRECTORY_NAME = "data/";

	/** The head directory name. */
	private final String HEAD_DIRECTORY_NAME = "avatar/";

	/** The Constant APK_DIRECTORY_NAME. */
	public final static String APK_DIRECTORY_NAME = "apk/";

	public final static String JAR_DIRECTORY_NAME = "jar/";
	public final static String ZIP_DIRECTORY_NAME = "zip/";
	public final static String HTML_DIRECTORY_NAME = "html/";

	/** 外置存储卡预警临界值. */
	private final long mExternalSDCardWarnningLimitSpace = DEF_EXTERNAL_SDCARD_WARNNING_LIMIT_SPACE_SIZE;

	/** 内置存储卡预警临界值. */
	private final long mInternalSDCardWarnningLimitSpace = DEF_INTERNAL_SDCARD_WARNNING_LIMIT_SPACE_SIZE;

	/** 手机预警临界值. */
	private final long mPhoneWarnningLimitSpace = DEF_PHONE_WARNNING_LIMIT_SPACE_SIZE;

	/** 视频大小最大值. */
	private final long mVideoMaxSize = DEF_VIDEO_MAX_SIZE;

	/** 图片大小最大值. */
	private final long mImageMaxSize = DEF_IMAGE_MAX_SIZE;

	/** 文本大小最大值. */
	private final long mTxtMaxSize = DEF_TXT_MAX_SIZE;

	/** 日志大小最大值. */
	private final long mLogMaxSize = DEF_LOG_MAX_SIZE;

	/** 音频大小最大值. */
	private final long mAudioMaxSize = DEF_AUDIO_MAX_SIZE;

	/** 自定义数据大小最大值. */
	private final long mDataMaxSize = DEF_DATA_MAX_SIZE;

	/** 头像大小最大值. */
	private final long mHeadMaxSize = DEF_HEAD_MAX_SIZE;

	/** APK大小最大值. */
	private final long mApkMaxSize = DEF_APK_MAX_SIZE;

	/** 外置SD卡路径. */
	private String mExternalSDCardPath = "";

	/** 内置SD卡路径. */
	private List<String> mInternalSDCardPath = new ArrayList<String>();

	/** 手机本身存储空间路径. */
	private String mPhoneDataPath = "";

	/** The Constant APP_DIRECTORY_NAME. */
	protected static String APP_DIRECTORY_NAME;

	/** 存储空间相应目录是否已经创建. */
	private boolean isMakeExternalSDCardDirectory;

	/** The is make internal sd card directory. */
	private boolean isMakeInternalSDCardDirectory;

	/** The is make phone data directroy. */
	private boolean isMakePhoneDataDirectroy;

	/** The m context. */
	private static Context mContext;

	/** The instance. */
	private static MultiCard instance;

	/**
	 * Gets the external sd card path.
	 * 
	 * @return the external sd card path
	 */
	public String getExternalSDCardPath() {
		return mExternalSDCardPath;
	}

	/**
	 * Gets the internal sd card path.
	 * 
	 * @return the internal sd card path
	 */
	public String getInternalSDCardPath() {
		return mInternalSDCardPath.get(0);
	}

	/**
	 * Gets the phone data path.
	 * 
	 * @return the phone data path
	 */
	public String getPhoneDataPath() {
		return mPhoneDataPath;
	}

	/**
	 * 获得单实例接口.
	 * 
	 * @return single instance of MultiCard
	 */
	public static MultiCard getInstance() {
		if (instance == null) {
			instance = new MultiCard();
			instance.mExternalSDCardPath = Environment
					.getExternalStorageDirectory().getAbsolutePath();
			if (mContext != null) {
				instance.setInternalSDCard(ReflectionUtil
						.getInternalSDCardPath(mContext));
				instance.mPhoneDataPath = mContext.getFilesDir()
						.getAbsolutePath();
			}
		}

		instance.makeAllDirectory();
		return instance;
	}

	/**
	 * Inits the.
	 * 
	 * @param context
	 *            the context
	 */
	public static void init(Context context, String appDirectoryName) {
		mContext = context;
		APP_DIRECTORY_NAME = appDirectoryName + "/";
		getInstance();
	}

	/**
	 * Reinit.
	 * 
	 * @param context
	 *            the context
	 */
	public void reinit(Context context) {
		isMakeExternalSDCardDirectory = false;
		isMakeInternalSDCardDirectory = false;
		isMakePhoneDataDirectroy = false;
		mContext = context;
		getInstance();
	}

	/**
	 * 获取文件存放文件位置.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the save path
	 */
	public String getSavePath(String fileName) {
		if (TextUtils.isEmpty(fileName)) {
			return "";
		}

		int fileType = getFileType(fileName);
		List<StorageDirectory> directories = getDirectorysByPriority(fileType);
		for (StorageDirectory directory : directories) {
			File file = new File(directory.path);
			if (file.exists()) {
				return directory.path + fileName;
			}
		}
		return "";

	}

	/**
	 * 文件全名转绝对路径（读）.
	 * 
	 * @param fileName
	 *            文件全名（文件名.扩展名）
	 * @return 返回绝对路径
	 */
	public String getReadPath(String fileName) {
		if (TextUtils.isEmpty(fileName)) {
			return "";
		}

		int fileType = getFileType(fileName);
		List<StorageDirectory> directories = getDirectorysByPriority(fileType);
		for (StorageDirectory directory : directories) {
			String filePath = directory.path + fileName;
			File file = new File(filePath);
			if (file.exists()) {
				// Util.cjxLog("read path:", filePath);
				return filePath;
			}
		}
		return "";
	}

	/**
	 * 文件全名转绝对路径（写）.
	 * 
	 * @param fileName
	 *            文件全名（文件名.扩展名）
	 * @return 返回绝对路径信息
	 * @throws LimitSpaceUnwriteException
	 *             内存不足
	 * @throws IllegalArgumentException
	 *             文件名不合法
	 */
	public MultiCardFilePath getWritePath(String fileName)
			throws LimitSpaceUnwriteException, IllegalArgumentException {
		if (TextUtils.isEmpty(fileName)) {
			throw new IllegalArgumentException();
		}

		int fileType = getFileType(fileName);
		StorageDirectory directory = limitSpaceUnwrite(fileType, 0);
		if (directory == null) {
			throw new LimitSpaceUnwriteException();
		} else {
			MultiCardFilePath multiCardFilePath = new MultiCardFilePath();
			multiCardFilePath.setFilePath(directory.path + fileName);
			if (limitSpaceWarnning(directory, fileType)) {
				multiCardFilePath
						.setCode(MultiCardFilePath.RET_LIMIT_SPACE_WARNNING);
			} else {
				multiCardFilePath.setCode(MultiCardFilePath.RET_OK);
			}
			return multiCardFilePath;
		}
	}

	/**
	 * Gets the write path ignore error.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the write path ignore error
	 */
	public String getWritePathIgnoreError(String fileName) {
		MultiCardFilePath multiCardFilePath = new MultiCardFilePath();
		if (TextUtils.isEmpty(fileName)) {
			multiCardFilePath.setFilePath(null);
		} else {

			int fileType = getFileType(fileName);
			StorageDirectory directory = limitSpaceUnwrite(fileType, 0);
			if (directory == null) {
				multiCardFilePath.setFilePath(null);
			} else {

				multiCardFilePath.setFilePath(directory.path + fileName);
				if (limitSpaceWarnning(directory, fileType)) {
					multiCardFilePath
							.setCode(MultiCardFilePath.RET_LIMIT_SPACE_WARNNING);
				} else {
					multiCardFilePath.setCode(MultiCardFilePath.RET_OK);
				}

			}
		}

		if (Util.isEmpty(multiCardFilePath.getFilePath())) {
			CommonUtil.showToast(mContext, "对不起，没有可用存储空间");
		}

		return multiCardFilePath.getFilePath();
	}

	/**
	 * 设置内置SD卡目录.
	 * 
	 * @param directories
	 *            the new internal sd card
	 */
	public void setInternalSDCard(List<String> directories) {
		mInternalSDCardPath = directories;
	}

	/**
	 * 删除所有头像.
	 * 
	 * @param noDel
	 *            不删除的文件
	 */
	public void deleteAllHeaderImage(String noDel) {
		int fileType = TYPE_HEAD;
		List<StorageDirectory> directories = getDirectorysByPriority(fileType);
		for (StorageDirectory directory : directories) {
			File file = new File(directory.path);
			if (file.exists() && file.isDirectory()) {
				String[] strings = file.list();
				for (String string : strings) {
					if (string.equals(noDel)) {
						continue;
					}
					File avatarFile = new File(directory.path + string);
					if (avatarFile.exists()) {
						avatarFile.delete();
					}
				}
			}
		}
	}

	/**
	 * 外置和内置SD卡是否存在.
	 * 
	 * @return true, if is sD card exist
	 */
	public boolean isSDCardExist() {
		return isExternalSDCardExist() || isInternalSDCardExist();
	}

	/**
	 * 获取外置存储卡剩余空间.
	 * 
	 * @return the external sd card space
	 */
	public long getExternalSDCardSpace() {
		return getResidualSpace(mExternalSDCardPath);
	}

	/**
	 * 获取内置存储卡剩余空间.
	 * 
	 * @return the internal sd card space
	 */
	public long getInternalSDCardSpace() {
		if (mInternalSDCardPath != null && mInternalSDCardPath.size() > 0) {
			long size = 0;
			for (String path : mInternalSDCardPath) {
				long space = getResidualSpace(path);
				if (space > size) {
					size = space;
				}
			}

			return size;
		} else {
			return 0;
		}
	}

	/**
	 * 根据文件类型，检查是否有可写空间 fileType 文件类型 type 检查的范围,0表示所有,1表示只检查外置.
	 * 
	 * @param fileType
	 *            the file type
	 * @param type
	 *            the type
	 * @return true, if successful
	 */
	public boolean checkSDCardSpace(int fileType, int type) {
		StorageDirectory directory = limitSpaceUnwrite(fileType, type);
		if (directory != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Instantiates a new multi card.
	 */
	public MultiCard() {

	}

	/**
	 * 是否因空间不足报警.
	 * 
	 * @param directory
	 *            存储目录
	 * @param fileType
	 *            请求写操作的文件类型
	 * @return 返回true表示内存预警，否则反之
	 */
	private boolean limitSpaceWarnning(StorageDirectory directory, int fileType) {
		if (getDirectoryWarnningLimitSpace(directory.type) > directory.residualSpace) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否因空间不足拒写.
	 * 
	 * @param fileType
	 *            请求写操作的文件类型
	 * @param type
	 *            the type
	 * @return 返回满足写条件的最优目录；如果返回null表示没有目录满足条件
	 */
	private StorageDirectory limitSpaceUnwrite(int fileType, int type) {
		List<StorageDirectory> directories = getDirectorysByPriority(fileType);
		for (StorageDirectory directory : directories) {
			// 如果只检查外置存储卡则过滤下
			if (type == 1 && directory.type != DIRECTORY_EXTERNAL_SDCARD) {
				continue;
			}
			long residualSpace = getResidualSpace(directory.rootDirectory);
			if (getFileMaxSize(fileType) <= residualSpace) {
				directory.residualSpace = residualSpace;
				return directory;
			}
		}
		return null;
	}

	/**
	 * 根据文件类型及检查范围，判断是否需要空间预警.
	 * 
	 * @param fileType
	 *            文件类型
	 * @param type
	 *            检查范围,0表示全部,1表示外置
	 * @return 返回绝对路径信息
	 */
	public boolean islimitSpaceWarning(int fileType, int type) {
		StorageDirectory directory = limitSpaceUnwrite(fileType, type);
		if (directory == null) {
			return false;
		} else {
			return !limitSpaceWarnning(directory, fileType);
		}
	}

	/**
	 * 获取目录剩余空间.
	 * 
	 * @param directoryPath
	 *            the directory path
	 * @return the residual space
	 */
	private long getResidualSpace(String directoryPath) {
		try {
			StatFs sf = new StatFs(directoryPath);
			long blockSize = sf.getBlockSize();
			long availCount = sf.getAvailableBlocks();
			long availCountByte = availCount * blockSize;
			// if(AppConfig.isDebugMode()) {
			// Log.w("cjx",directoryPath + "剩余空间:" + availCountByte / (1024 *
			// 1024) + "M");
			// }
			return availCountByte;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取文件最大大小.
	 * 
	 * @param type
	 *            the type
	 * @return the file max size
	 */
	private long getFileMaxSize(int type) {
		switch (type) {
		case TYPE_AUDIO:
			return mAudioMaxSize;

		case TYPE_VIDEO:
			return mVideoMaxSize;

		case TYPE_TXT:
			return mTxtMaxSize;

		case TYPE_LOG:
			return mLogMaxSize;

		case TYPE_IMAGE:
			return mImageMaxSize;

		case TYPE_DATA:
			return mDataMaxSize;

		case TYPE_HEAD:
			return mHeadMaxSize;

		case TYPE_APK:
			return mApkMaxSize;

		case TYPE_JAR:
			return mApkMaxSize;
		default:
			return mDataMaxSize;
		}
	}

	/**
	 * 按优先级取出所有目录.
	 * 
	 * @param fileType
	 *            the file type
	 * @return the directorys by priority
	 */
	private List<StorageDirectory> getDirectorysByPriority(int fileType) {
		String endDirectoryName = String.format("/%s%s", APP_DIRECTORY_NAME,
				getDirectoryName(fileType));
		List<StorageDirectory> directories = new ArrayList<StorageDirectory>();
		if (fileType == TYPE_HEAD) {
			directories.add(new StorageDirectory(mPhoneDataPath
					+ endDirectoryName, DIRECTORY_PHONE, mPhoneDataPath));
			if (isInternalSDCardExist()) {
				for (String directory : mInternalSDCardPath) {
					directories.add(new StorageDirectory(directory
							+ endDirectoryName, DIRECTORY_INTERNAL_SDCARD,
							directory));
				}
			}
			if (isExternalSDCardExist()) {
				directories.add(new StorageDirectory(mExternalSDCardPath
						+ endDirectoryName, DIRECTORY_EXTERNAL_SDCARD,
						mExternalSDCardPath));
			}
		} else {
			if (isExternalSDCardExist()) {
				directories.add(new StorageDirectory(mExternalSDCardPath
						+ endDirectoryName, DIRECTORY_EXTERNAL_SDCARD,
						mExternalSDCardPath));
			}
			if (isInternalSDCardExist()) {
				for (String directory : mInternalSDCardPath) {
					directories.add(new StorageDirectory(directory
							+ endDirectoryName, DIRECTORY_INTERNAL_SDCARD,
							directory));
				}
			}

			// 图片,音频，视频需要外部程序使用,外部程序无法访问沙盒，不支持
			if (fileType == TYPE_DATA || fileType == TYPE_TXT
					|| fileType == TYPE_LOG || fileType == TYPE_HEAD
					|| fileType == TYPE_APK || fileType == TYPE_JAR) {
				directories.add(new StorageDirectory(mPhoneDataPath
						+ endDirectoryName, DIRECTORY_PHONE, mPhoneDataPath));
			}
		}
		return directories;
	}

	/**
	 * 获取目录报警临界空间.
	 * 
	 * @param directory
	 *            the directory
	 * @return the directory warnning limit space
	 */
	private long getDirectoryWarnningLimitSpace(int directory) {
		switch (directory) {
		case DIRECTORY_EXTERNAL_SDCARD:
			return mExternalSDCardWarnningLimitSpace;

		case DIRECTORY_INTERNAL_SDCARD:
			return mInternalSDCardWarnningLimitSpace;

		case DIRECTORY_PHONE:
			return mPhoneWarnningLimitSpace;

		default:
			return mPhoneWarnningLimitSpace;
		}
	}

	/**
	 * 获取文件媒体类型.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the file type
	 */
	private int getFileType(String fileName) {
		fileName = fileName.toLowerCase();
		if (fileName.endsWith(FORMAT_AUDIO_MP4)
				|| fileName.endsWith(FORMAT_AUDIO_M4A)
				|| fileName.endsWith(FORMAT_AUDIO_3GPP)) {
			return TYPE_AUDIO;
		} else if (fileName.endsWith(FORMAT_IMAGE_JPG)
				|| fileName.endsWith(FORMAT_IMAGE_JPEG)
				|| fileName.endsWith(FORMAT_IMAGE_BMP)
				|| fileName.endsWith(FORMAT_IMAGE_PNG)
				|| fileName.endsWith(FORMAT_IMAGE_GIF)) {
			return TYPE_IMAGE;
		} else if (fileName.endsWith(FORMAT_LOG)) {
			return TYPE_LOG;
		} else if (fileName.endsWith(FORMAT_TXT)) {
			return TYPE_TXT;
		} else if (fileName.endsWith(FORMAT_VIDEO)) {
			return TYPE_VIDEO;
		} else if (fileName.endsWith(FORMAT_DATA)) {
			return TYPE_DATA;
		} else if (fileName.endsWith(FORMAT_APK)) {
			return TYPE_APK;
		}
		else if (fileName.endsWith(FORMAT_JAR)) {
			return TYPE_JAR;
		} 
		else if (fileName.endsWith(FORMAT_HTML)) {
			return TYPE_HTML;
		}
		else if (fileName.endsWith(FORMAT_ZIP)) {
			return TYPE_ZIP;
		}
		else {
			return TYPE_DATA;
			// return TYPE_HEAD;
		}
	}

	/**
	 * 获取文件对应的目录名.
	 * 
	 * @param fileType
	 *            the file type
	 * @return the directory name
	 */
	private String getDirectoryName(int fileType) {
		switch (fileType) {
		case TYPE_AUDIO:
			return AUDIO_DIRECTORY_NAME;

		case TYPE_VIDEO:
			return VIDEO_DIRECTORY_NAME;

		case TYPE_TXT:
			return TXT_DIRECTORY_NAME;

		case TYPE_LOG:
			return LOG_DIRECTORY_NAME;

		case TYPE_IMAGE:
			return IMAGE_DIRECTORY_NAME;

		case TYPE_DATA:
			return DATA_DIRECTORY_NAME;

		case TYPE_HEAD:
			return HEAD_DIRECTORY_NAME;

		case TYPE_APK:
			return APK_DIRECTORY_NAME;

		case TYPE_HTML:
			return HTML_DIRECTORY_NAME;
		case TYPE_JAR:
			return JAR_DIRECTORY_NAME;
		case TYPE_ZIP:
			return ZIP_DIRECTORY_NAME;
		default:
			return DATA_DIRECTORY_NAME;
		}
	}

	/**
	 * 创建存储空间下的所有目录.
	 */
	private void makeAllDirectory() {
		makeDirectory(DIRECTORY_EXTERNAL_SDCARD);
		makeDirectory(DIRECTORY_INTERNAL_SDCARD);
		makeDirectory(DIRECTORY_PHONE);
	}

	/**
	 * 创建必要的目录.
	 * 
	 * @param directoryType
	 *            the directory type
	 */
	private void makeDirectory(int directoryType) {
		List<String> rootDirectorys = new ArrayList<String>();
		switch (directoryType) {
		case DIRECTORY_EXTERNAL_SDCARD:
			rootDirectorys.add(mExternalSDCardPath);
			if (isMakeExternalSDCardDirectory || !isExternalSDCardExist()) {
				return;
			}
			isMakeExternalSDCardDirectory = true;
			break;

		case DIRECTORY_INTERNAL_SDCARD:
			for (String path : mInternalSDCardPath) {
				rootDirectorys.add(path);
				break;
			}
			if (isMakeInternalSDCardDirectory || !isInternalSDCardExist()) {
				return;
			}
			isMakeInternalSDCardDirectory = true;
			break;

		case DIRECTORY_PHONE:
			rootDirectorys.add(mPhoneDataPath);
			if (isMakePhoneDataDirectroy) {
				return;
			}
			isMakePhoneDataDirectroy = true;
			break;

		default:
			return;
		}

		for (String rootDirectory : rootDirectorys) {
			String appDirectory = rootDirectory + "/" + APP_DIRECTORY_NAME;
			makeDirectoryCheck(directoryType, mkdir(appDirectory));
			makeDirectoryCheck(directoryType, mkdir(appDirectory
					+ AUDIO_DIRECTORY_NAME));
			makeDirectoryCheck(directoryType, mkdir(appDirectory
					+ VIDEO_DIRECTORY_NAME));
			makeDirectoryCheck(directoryType, mkdir(appDirectory
					+ TXT_DIRECTORY_NAME));
			makeDirectoryCheck(directoryType, mkdir(appDirectory
					+ LOG_DIRECTORY_NAME));
			makeDirectoryCheck(directoryType, mkdir(appDirectory
					+ IMAGE_DIRECTORY_NAME));
			makeDirectoryCheck(directoryType, mkdir(appDirectory
					+ DATA_DIRECTORY_NAME));
			makeDirectoryCheck(directoryType, mkdir(appDirectory
					+ HEAD_DIRECTORY_NAME));
			makeDirectoryCheck(directoryType, mkdir(appDirectory
					+ APK_DIRECTORY_NAME));
			makeDirectoryCheck(directoryType, mkdir(appDirectory
					+ JAR_DIRECTORY_NAME));
			makeDirectoryCheck(directoryType, mkdir(appDirectory
					+ ZIP_DIRECTORY_NAME));
			makeDirectoryCheck(directoryType, mkdir(appDirectory
					+ HTML_DIRECTORY_NAME));
		}
	}

	/**
	 * 创建目录检查.
	 * 
	 * @param directoryType
	 *            the directory type
	 * @param mk
	 *            the mk
	 */
	private void makeDirectoryCheck(int directoryType, boolean mk) {
		if (!mk) {
			switch (directoryType) {
			case DIRECTORY_EXTERNAL_SDCARD:
				isMakeExternalSDCardDirectory = false;
				break;

			case DIRECTORY_INTERNAL_SDCARD:
				isMakeInternalSDCardDirectory = false;
				break;

			case DIRECTORY_PHONE:
				isMakePhoneDataDirectroy = false;
				break;
			}
		}
	}

	/**
	 * 创建目录.
	 * 
	 * @param path
	 *            the path
	 * @return true, if successful
	 */
	private boolean mkdir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			boolean mk = file.mkdir();

			return mk;
		}
		return true;
	}

	/**
	 * 外置存储卡是否存在.
	 * 
	 * @return true, if is external sd card exist
	 */
	public boolean isExternalSDCardExist() {
		boolean bExist = false;
		bExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if (bExist && getExternalSDCardSpace() > 0) { // 存在,并且空间大于0
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 内置存储卡是否存在.
	 * 
	 * @return true, if is internal sd card exist
	 */
	private boolean isInternalSDCardExist() {
		boolean bExist = false;
		bExist = mInternalSDCardPath != null && mInternalSDCardPath.size() > 0;
		if (bExist && getInternalSDCardSpace() > 0) { // 存在,并且空间大于0
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 存储目录.
	 * 
	 * @author cjx
	 */
	private class StorageDirectory {

		/** The path. */
		public String path;

		/** The type. */
		public int type;

		/** The residual space. */
		public long residualSpace;

		/** The root directory. */
		public String rootDirectory;

		/**
		 * Instantiates a new storage directory.
		 * 
		 * @param path
		 *            the path
		 * @param type
		 *            the type
		 */
		public StorageDirectory(String path, int type) {
			this.path = path;
			this.type = type;
		}

		/**
		 * Instantiates a new storage directory.
		 * 
		 * @param path
		 *            the path
		 * @param type
		 *            the type
		 * @param rootDirectory
		 *            the root directory
		 */
		public StorageDirectory(String path, int type, String rootDirectory) {
			this.path = path;
			this.type = type;
			this.rootDirectory = rootDirectory;
		}
	}
}
