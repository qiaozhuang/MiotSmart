/*
 * 
 */

package com.box.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.box.common.util.exception.LimitSpaceUnwriteException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



// TODO: Auto-generated Javadoc

/**
 * 通用工具类.
 * 
 * @author Administrator
 */
public class CommonUtil {

    /** The Constant TAG. */
    private static final String TAG = "CommonUtil";

    public static int getResIdByName(Context context, String defType, String name) {
        return context.getResources().getIdentifier(name, defType, context.getPackageName());
    }



    /**
     * 获取文件写入路径，无视错误.
     * 
     * @param fileName 文件全名
     * @return 返回路径，返回null则拒绝写入操作
     */
    public static String getWritePathIgnoreError(String fileName) {
        try {
            MultiCardFilePath path = MultiCard.getInstance().getWritePath(fileName);
            return path.getFilePath();
        } catch (LimitSpaceUnwriteException e) {

            e.printStackTrace();
        } catch (IllegalArgumentException e) {

            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the write path.
     * 
     * @param fileName the file name
     * @return the write path
     */
    public static String getWritePath(String fileName) {
        return MultiCard.getInstance().getWritePathIgnoreError(fileName);
    }

    /**
     * Gets the read path.
     * 
     * @param fileName the file name
     * @return the read path
     */
    public static String getReadPath(String fileName) {
        return MultiCard.getInstance().getReadPath(fileName);
    }

    /**
     * 获取文件读取路径.
     * 
     * @param fileName 文件全名
     * @param orgFilePath
     *            已经存在的文件路径(由于通过龙骞功能模块保存的图片没和翼聊程序的保持一致(why?)，所以必须这样处理，否则图片会找不到)
     * @return 如果orgFilePath存在直接返回orgFilePath，否则根据一定规则返回路径，如果路径不存在返回""
     */
    public static String getReadPath(String fileName, String orgFilePath) {
        if (!TextUtils.isEmpty(orgFilePath) && new File(orgFilePath).exists()) {
            return orgFilePath;
        } else {
            return MultiCard.getInstance().getReadPath(fileName);
        }
    }

    /**
     * 等比例缩放图片（带滤波器）.
     * 
     * @param srcFile 来源文件
     * @param dstFile 目标文件
     * @param dstMaxWH 目标文件宽高最大值
     * @param bContrast 提高对比度滤波器，可使图片变亮丽
     * @param bSharp 锐化图片，可使图片清晰（暂时无效果）
     * @param bRotate 是否旋转
     * @return the boolean
     */
    public static Boolean scaleImageWithFilter(File srcFile, File dstFile, int dstMaxWH,
            Boolean bContrast, Boolean bSharp, Boolean bRotate) {
        Boolean bRet = false;
        if (dstMaxWH == 0)
            return bRet;
        // 路径文件不存在
        if (!srcFile.exists()) {
            return bRet;
        }

        try {
            // 判断是否旋转
            float rotate = 90.0F;
            if (bRotate) {
                ExifInterface localExifInterface = new ExifInterface(srcFile.getAbsolutePath());
                int rotateInt = localExifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);
                // LectekLog.i("gzb",
                // "CommonUtil scaleImageWithFilter Orientation=" + rotateInt +
                // ";rotate=" + getImageRotate(rotateInt));
                rotate = getImageRotate(rotateInt);
            }

            // 打开源文件
            Bitmap srcBitmap;
            {
                java.io.InputStream is;
                is = new FileInputStream(srcFile);
                Options opts = getOptionsWithInSampleSize(srcFile.getPath(), dstMaxWH);
                srcBitmap = BitmapFactory.decodeStream(is, null, opts);
                if (srcBitmap == null)
                    return bRet;
            }
            // 原图片宽高
            int width = srcBitmap.getWidth();
            int height = srcBitmap.getHeight();
            // 获得缩放因子
            float scale = 1.f;
            {
                if (width > dstMaxWH || height > dstMaxWH) {
                    float scaleTemp = (float)dstMaxWH / (float)width;
                    float scaleTemp2 = (float)dstMaxWH / (float)height;
                    if (scaleTemp > scaleTemp2)
                        scale = scaleTemp2;
                    else
                        scale = scaleTemp;
                }
            }
            // 图片缩放
            Bitmap dstBitmap;
            if (scale == 1.f)
                dstBitmap = srcBitmap;
            else {
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                if (bRotate) {
                    matrix.postRotate(rotate);
                }

                dstBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, width, height, matrix, true);

                if (!srcBitmap.isRecycled())
                    srcBitmap.recycle();
                srcBitmap = null;
            }

            // 提高对比度
            if (bContrast) {
                Bitmap tempBitmap = Bitmap.createBitmap(dstBitmap.getWidth(),
                        dstBitmap.getHeight(), Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(tempBitmap);
                ColorMatrix cm = new ColorMatrix();
                float contrast = 30.f / 180.f; // 提高30对比度
                setContrast(cm, contrast);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColorFilter(new ColorMatrixColorFilter(cm));
                canvas.drawBitmap(dstBitmap, 0, 0, paint);

                if (!dstBitmap.isRecycled())
                    dstBitmap.recycle();
                dstBitmap = null;
                dstBitmap = tempBitmap;
            }
            // 提高锐化
            if (bSharp) {
            }
            // 保存文件
            if (dstFile.exists())
                dstFile.delete();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dstFile));
            dstBitmap.compress(CompressFormat.JPEG, 90, bos);

            if (!dstBitmap.isRecycled())
                dstBitmap.recycle();
            dstBitmap = null;

            bos.flush();
            bos.close();
            bRet = true;
        } catch (Exception e) {
            return bRet;
        }

        return bRet;
    }

    /**
     * 获得旋转角度.
     * 
     * @param rotate the rotate
     * @return the image rotate
     */
    private static float getImageRotate(int rotate) {
        float f;
        if (rotate == 6) {
            f = 90.0F;
        } else if (rotate == 3) {
            f = 180.0F;
        } else if (rotate == 8) {
            f = 270.0F;
        } else {
            f = 0.0F;
        }

        return f;
    }

    /**
     * 获取长宽都不超过160dip的图片，基本思想是设置Options.inSampleSize按比例取得缩略图
     * 
     * @param filePath the file path
     * @param maxWidth the max width
     * @return the options with in sample size
     */
    public static Options getOptionsWithInSampleSize(String filePath, int maxWidth) {
        Options bitmapOptions = new Options();
        bitmapOptions.inJustDecodeBounds = true;// 只取得outHeight(图片原始高度)和
        // outWidth(图片的原始宽度)而不加载图片
        BitmapFactory.decodeFile(filePath, bitmapOptions);
        bitmapOptions.inJustDecodeBounds = false;
        int inSampleSize = bitmapOptions.outWidth / (maxWidth / 10);
        // 应该直接除160的，但这里出16是为了增加一位数的精度
        if (inSampleSize % 10 != 0) {
            inSampleSize += 10;// 尽量取大点图片，否则会模糊
        }
        inSampleSize = inSampleSize / 10;
        if (inSampleSize <= 0) {// 判断200是否超过原始图片高度
            inSampleSize = 1;// 如果超过，则不进行缩放
        }
        bitmapOptions.inSampleSize = inSampleSize;
        return bitmapOptions;
    }

    /**
     * 设置对比度矩阵.
     * 
     * @param cm the cm
     * @param contrast the contrast
     */
    private static void setContrast(ColorMatrix cm, float contrast) {
        float scale = contrast + 1.f;
        float translate = (-.5f * scale + .5f) * 255.f;
        cm.set(new float[] {
                scale, 0, 0, 0, translate, 0, scale, 0, 0, translate, 0, 0, scale, 0, translate, 0,
                0, 0, 1, 0
        });
    }



    /**
     * 等比例缩放图片.
     * 
     * @param pathString 文件路径
     * @param dstMaxWH 目标文件宽高最大值
     * @return the bitmap
     */
    public static Bitmap scaleImageByPath(String pathString, int dstMaxWH) {
        Bitmap retBm = null;
        // 路径为空
        if (Util.isEmpty(pathString) || dstMaxWH <= 0) {
            return retBm;
        }
        // 路径文件不存在
        {
            File file = new File(pathString);
            if (!file.exists()) {
                return retBm;
            }
        }

        try {
            // 打开源文件
            Bitmap srcBitmap;
            {
                java.io.InputStream is;
                is = new FileInputStream(pathString);
                Options opts = getOptionsWithInSampleSize(pathString, dstMaxWH);
                srcBitmap = BitmapFactory.decodeStream(is, null, opts);

                if (srcBitmap == null)
                    return retBm;
            }
            // 原图片宽高
            int width = srcBitmap.getWidth();
            int height = srcBitmap.getHeight();
            // 获得缩放因子
            float scale = 1.f;
            {
                if (width > dstMaxWH || height > dstMaxWH) {
                    float scaleTemp = (float)dstMaxWH / (float)width;
                    float scaleTemp2 = (float)dstMaxWH / (float)height;
                    if (scaleTemp > scaleTemp2)
                        scale = scaleTemp2;
                    else
                        scale = scaleTemp;
                }
            }
            // 图片缩放
            Bitmap dstBitmap;
            if (scale == 1.f)
                dstBitmap = srcBitmap;
            else {
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                dstBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, width, height, matrix, true);
                if (!srcBitmap.isRecycled())
                    srcBitmap.recycle();
                srcBitmap = null;
            }

            retBm = dstBitmap;
        } catch (Exception e) {
            return retBm;
        }

        return retBm;
    }

    /*
     * public static Bitmap scaleImage(Bitmap bitmap,Context context) { Bitmap
     * scaleBmp = null; int bmpW = bitmap.getWidth(); int bmpH =
     * bitmap.getHeight(); try { //根据屏幕宽度设置图片大小 if (ScreenUtil.screenHeight == 0
     * || 0 == ScreenUtil.screenWidth) ScreenUtil.GetInfo(context); int tarWidth
     * = ScreenUtil.screenWidth; int tarHeigth = (ScreenUtil.screenWidth *
     * bmpH)/bmpW; float scaleH = (float)tarHeigth/bmpH; int destH = (int)
     * (bmpH*scaleH); scaleBmp = Bitmap.createScaledBitmap(bitmap,
     * tarWidth,destH, true); if (null != bitmap && !bitmap.isRecycled())
     * bitmap.recycle(); } catch (Exception e) { e.printStackTrace(); } return
     * scaleBmp; }
     */

    /**
     * 判断是否有SDCARD.
     * 
     * @return true, if is s dcard exist
     */
    public static boolean isSDcardExist() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 判断存储卡空间(外置、内置存储卡是否有空间可写).
     * 
     * @param context the context
     * @param fileType 文件类型
     * @param type 检查范围,0表示所有,1表示外置
     * @param bNeedTip 是否要做出提示语
     * @return true表示无存储卡或无空间可写,false表示ok
     */
    public static boolean isSDCardSapceForWriteWithTip(Context context, int fileType, int type,
            boolean bNeedTip) {
        if (type == 0 && !CommonUtil.isSDcardExist()) {
            if (bNeedTip) {
                CommonUtil.showToast(context, "对不起，没有可用存储空间");
            }
            return true;
        } else if (type == 1 && !CommonUtil.isExternalSDCardExist()) {
            if (bNeedTip) {
                CommonUtil.showToast(context, "对不起，没有可用存储空间");
            }
            return true;
        }

        if (!CommonUtil.isLimitSDCardSpaceForWrite(fileType, type)) {
            if (bNeedTip) {
                CommonUtil.showToast(context, "对不起,您的SD卡不存在，请插入SD卡");
            }
            return true;
        }

        if (!CommonUtil.isLimitSDCardSpaceForWriteWarning(fileType, type)) {
            if (bNeedTip) {
                CommonUtil.showToast(context, "对不起,您的SD卡不存在，请插入SD卡");
            }
        }

        return false;
    }

    /**
     * Show toast.
     * 
     * @param mContext the m context
     * @param resId the res id
     */
    public static void showToast(Context mContext, int resId) {
        Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * Show toast.
     * 
     * @param mContext the m context
     * @param text the text
     */
    public static void showToast(Context mContext, String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 判断是否有外置存储卡.
     * 
     * @return true, if is external sd card exist
     */
    public static boolean isExternalSDCardExist() {
        return MultiCard.getInstance().isExternalSDCardExist();
    }

    /**
     * 根据文件类型检查外置及内置存储卡是否有空间可写.
     * 
     * @param fileType 文件类型
     * @param type 检查范围,0表示所有,1表示只检查外置
     * @return true, if is limit sd card space for write
     */
    public static boolean isLimitSDCardSpaceForWrite(int fileType, int type) {
        return MultiCard.getInstance().checkSDCardSpace(fileType, type);
    }

    /**
     * 根据文件类型检查外置及内置存储卡是否超过预警空间.
     * 
     * @param fileType 文件类型
     * @param type 检查范围,0表示所有,1表示只检查外置
     * @return true, if is limit sd card space for write warning
     */
    public static boolean isLimitSDCardSpaceForWriteWarning(int fileType, int type) {
        return MultiCard.getInstance().islimitSpaceWarning(fileType, type);
    }

    /**
     * Gets the cst.
     * 
     * @param GMTStr the gMT str
     * @return the cst
     * @throws ParseException the parse exception
     */
    public static Date getCST(String GMTStr) throws ParseException {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        return df.parse(GMTStr);
    }

    /**
     * Gets the cst.
     * 
     * @param GMTStr the gMT str
     * @param dateFormat the date format
     * @return the cst
     * @throws ParseException the parse exception
     */
    public static Date getCST(String GMTStr, DateFormat dateFormat) throws ParseException {
        return dateFormat.parse(GMTStr);
    }

    /**
     * 爱音乐组装文件描述.
     * 
     * @param cid the cid
     * @param music the music
     * @param singer the singer
     * @param size the size
     * @param imageUrl the image url
     * @return the string
     */
    public static String assembleFileDesc(String cid, String music, String singer, String size,
            String imageUrl) {

        StringBuilder sb = new StringBuilder();
        sb.append("cid=" + cid).append(";music=" + music).append(";singer=" + singer)
        .append(";size=" + size).append(";imageUrl=" + imageUrl);
        return sb.toString();
    }

    /** 数据存放根路径, 默认sd卡，在程序创建会初始化一次. */
    public static File DATA_ROOT_PATH = Environment.getExternalStorageDirectory();

    /**
     * 获取当前应用的名称，如box.
     * 
     * @param context the context
     * @return the simple package name
     */
    public static String getSimplePackageName(Context context) {
        return MultiCard.APP_DIRECTORY_NAME;
    }

    /**
     * Clear all cache files.
     * 
     * @param context the context
     */
    public static void clearAllCacheFiles(Context context) {
        String externalPath = MultiCard.getInstance().getExternalSDCardPath();
        String internalPath = MultiCard.getInstance().getInternalSDCardPath();
        String phonePath = MultiCard.getInstance().getPhoneDataPath();
        if (Util.isNotEmpty(externalPath))
            deleteFiles(externalPath + "/" + MultiCard.APP_DIRECTORY_NAME);
        if (Util.isNotEmpty(internalPath))
            deleteFiles(internalPath + "/" + MultiCard.APP_DIRECTORY_NAME);
        if (Util.isNotEmpty(phonePath))
            deleteFiles(phonePath + "/" + MultiCard.APP_DIRECTORY_NAME);

        MultiCard.getInstance().reinit(context);
    }

    /**
     * 删除文件路径下的所有文件.
     * 
     * @param path 文件路径
     */
    public static void deleteFiles(String path) {
        // 定义文件路径
        File file = new File(path);

        // 判断是文件还是目录
        if (file.exists() && file.isDirectory()) {
            // 若目录下没有文件则直接删除
            if (file.listFiles().length == 0) {
                file.delete();
            } else {
                // 若有则把文件放进数组，并判断是否有下级目录
                File delFile[] = file.listFiles();
                int i = file.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        // 递归调用deleteFiles方法并取得子目录路径
                        deleteFiles(delFile[j].getAbsolutePath());
                    }

                    delFile[j].delete();// 删除文件
                }
            }
        }
    }
}
