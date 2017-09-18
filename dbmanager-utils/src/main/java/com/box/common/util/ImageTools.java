/*
 * 
 */

package com.box.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

/**
 * 图片工具.
 * 
 * @author Administrator
 */
public class ImageTools {

	/** The Constant LEFT. */
	public static final int LEFT = 0;

	/** The Constant RIGHT. */
	public static final int RIGHT = 1;

	/** The Constant TOP. */
	public static final int TOP = 3;

	/** The Constant BOTTOM. */
	public static final int BOTTOM = 4;

	/**
	 * 图片右上显示字符.图片需要预留白色区域
	 * 
	 * @param src
	 *            源文件
	 * @param number
	 *            数字
	 * @return the bitmap
	 */
	public static Bitmap picText(Bitmap src, String number) {
		int w = src.getWidth();
		int h = src.getHeight();
		int radius = (int) Math.ceil(w * 0.23);
		// 设置显示区域大小
		int cx = w - radius;
		int cy = radius;
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_4444);
		Canvas canvas = new Canvas(newb);
		canvas.drawBitmap(src, 0, 0, null);
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		canvas.drawCircle(cx, cy, radius, paint);
		// paint.setARGB(255, 204, 0, 0);
		paint.setColor(Color.parseColor("#ff3000"));
		canvas.drawCircle(cx, cy, radius - 2, paint);
		int tSize = (int) Math.ceil(ScreenUtil.screenWidthPercentage * 18);
		paint.setTextSize((tSize > 0) ? tSize : 16);
		paint.setColor(Color.WHITE);
		paint.setTypeface(Typeface.MONOSPACE);
		Rect rect = new Rect();
		paint.getTextBounds(number, 0, number.length(), rect);
		canvas.drawText(number, w - 2 * radius + 3 + (5 - 2 * number.length()),
				cy + (rect.height() / 2), paint);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return newb;
	}

	/**
	 * Gets the bitmap.
	 * 
	 * @param is
	 *            the is
	 * @return Bitmap 返回类型
	 * @Title: getBitmap
	 * @Description: 根据输入流，获取制定缩放比例的图片
	 * @date 2012-10-9 上午10:59:22
	 */
	public static Bitmap getBitmap(InputStream is) {
		return BitmapFactory.decodeStream(is);
	}

	/**
	 * Gets the bitmap.
	 * 
	 * @param is
	 *            the is
	 * @param scale
	 *            the scale
	 * @return Bitmap 返回类型
	 * @Title: getBitmap
	 * @Description: 根据输入流，获取制定缩放比例的图片
	 * @date 2012-10-9 上午10:58:14
	 */
	public static Bitmap getBitmap(InputStream is, int scale) {
		Options options = new Options();
		options.inSampleSize = scale;
		Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
		return bitmap;
	}

	/**
	 * Gets the bitmap.
	 * 
	 * @param path
	 *            the path
	 * @return Bitmap 返回类型
	 * @Title: getBitmap
	 * @Description:根据路径获取图片
	 * @date 2012-10-9 上午10:57:11
	 */
	public static Bitmap getBitmap(String path) {
		return BitmapFactory.decodeFile(path);
	}

	/**
	 * Gets the bitmap.
	 * 
	 * @param path
	 *            the path
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @return Bitmap 返回类型
	 * @Title: getBitmap
	 * @Description:根据路径获取图片,获取制定宽高缩放的图片
	 * @date 2012-10-9 上午11:00:20
	 */
	public static Bitmap getBitmap(String path, int width, int height) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		int scaleX = options.outWidth / width;
		int scaleY = options.outHeight / height;
		int scale = scaleX > scaleY ? scaleX : scaleY;
		options.inJustDecodeBounds = false;
		options.inSampleSize = scale;
		return BitmapFactory.decodeFile(path, options);
	}

	/**
	 * Gets the bitmap.
	 * 
	 * @param bytes
	 *            the bytes
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @return Bitmap 返回类型
	 * @Title: getBitmap
	 * @Description:根据图片数据字节数组获取图片,获取制定宽高缩放的图片
	 * @date 2012-10-9 上午11:01:12
	 */
	public static Bitmap getBitmap(byte[] bytes, int width, int height) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
		int scaleX = options.outWidth / width;
		int scaleY = options.outHeight / height;
		int scale = scaleX > scaleY ? scaleX : scaleY;
		options.inJustDecodeBounds = false;
		options.inSampleSize = scale;
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
	}

	/**
	 * Gets the bitmap.
	 * 
	 * @param context
	 *            the context
	 * @param resId
	 *            the res id
	 * @param scale
	 *            the scale
	 * @return Bitmap 返回类型
	 * @Title: getBitmap
	 * @Description:根据图片数据字节数组获取图片,获取制定宽高缩放的图片
	 * @date 2012-10-9 上午11:01:12
	 */
	public static Bitmap getBitmap(Context context, int resId, int scale) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), resId, options);
		// int scaleX = options.outWidth / width;
		// int scaleY = options.outHeight / height;
		// int scale = scaleX > scaleY ? scaleX : scaleY;
		options.inJustDecodeBounds = false;
		options.inSampleSize = scale;
		return BitmapFactory.decodeResource(context.getResources(), resId,
				options);
	}

	/**
	 * Save bitmap.
	 * 
	 * @param path
	 *            the path
	 * @param bitmap
	 *            the bitmap
	 * @return void 返回类型
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @Title: saveBitmap
	 * @Description: 保存图片
	 * @date 2012-10-9 上午11:01:19
	 */
	public static void saveBitmap(String path, Bitmap bitmap)
			throws IOException {
		if (path != null && bitmap != null) {
			File file = new File(path);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			OutputStream out = new FileOutputStream(file);
			String fileType = file.getName().substring(
					file.getName().lastIndexOf(".") + 1);
			if ("png".equals(fileType)) {
				bitmap.compress(CompressFormat.PNG, 100, out);
			} else {
				bitmap.compress(CompressFormat.JPEG, 100, out);
			}
		}
	}

	/**
	 * 切圆角.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param pixels
	 *            the pixels
	 * @return the bitmap
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 得到图片.
	 * 
	 * @param data
	 *            the data
	 * @return the bitmap
	 */
	public static Bitmap getBitmap(byte[] data) {
		Options o = new Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(new ByteArrayInputStream(data), null, o);
		final int IMAGE_MAX_SIZE = 100;
		int scale = 1;
		if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
			scale = (int) Math.pow(
					2,
					(int) Math.round(Math.log(IMAGE_MAX_SIZE
							/ (double) Math.max(o.outHeight, o.outWidth))
							/ Math.log(0.5)));
		}

		Options o2 = new Options();
		o2.inSampleSize = scale;

		final Bitmap bitmap = BitmapFactory.decodeStream(
				new ByteArrayInputStream(data), null, o2);
		return bitmap;
	}

	/**
	 * To grayscale.
	 * 
	 * @param bmpOriginal
	 *            the bmp original
	 * @return the bitmap
	 */
	/**
	 * 图片去色,返回灰度图片
	 * 
	 * @param bmpOriginal
	 *            传入的图片
	 * @return 去色后的图片
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();
		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	/**
	 * 读取路径中的图片，然后将其转化为缩放后的bitmap.
	 * 
	 * @param res
	 *            the res
	 * @param resId
	 *            the res id
	 * @param reqWidth
	 *            the req width
	 * @param reqHeight
	 *            the req height
	 * @return the bitmap
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {
		final Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * Decode original bitmap from resource.
	 * 
	 * @param res
	 *            the res
	 * @param resId
	 *            the res id
	 * @param reqWidth
	 *            the req width
	 * @param reqHeight
	 *            the req height
	 * @return the bitmap
	 */
	public static Bitmap decodeOriginalBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {
		Bitmap bitmapOrg = BitmapFactory.decodeResource(res, resId);
		Bitmap bitmapSampled = createBitmapBySize(bitmapOrg, reqWidth,
				reqHeight);
		if (bitmapOrg != null && !bitmapOrg.isRecycled()) {
			bitmapOrg.recycle();
		}
		return bitmapSampled;
	}

	/**
	 * 读取路径中的图片，然后将其转化为缩放后的bitmap.
	 * 
	 * @param path
	 *            the path
	 * @param reqWidth
	 *            the req width
	 * @param reqHeight
	 *            the req height
	 * @return the bitmap
	 */
	public static Bitmap decodeSampledBitmapFromPath(String path, int reqWidth,
			int reqHeight) {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高
		Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回bm为空
		options.inJustDecodeBounds = false;
		// 计算缩放比
		int be = (int) (options.outHeight / (float) 200);
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight); // 图片长宽各缩小二分之一
		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
		bitmap = BitmapFactory.decodeFile(path, options);
		return bitmap;
	}

	/**
	 * Calculate in sample size.
	 * 
	 * @param options
	 *            the options
	 * @param reqWidth
	 *            the req width
	 * @param reqHeight
	 *            the req height
	 * @return the int
	 */
	public static int calculateInSampleSize(Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

	/**
	 * 保存图片为PNG.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param name
	 *            the name
	 */
	public static void savePNG_After(Bitmap bitmap, String name) {
		File file = new File(name);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(CompressFormat.PNG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存图片为JPEG.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param path
	 *            the path
	 */
	public static void saveJPGE_After(Bitmap bitmap, String path) {
		File file = new File(path);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 水印.
	 * 
	 * @param src
	 *            the src
	 * @param watermark
	 *            the watermark
	 * @return the bitmap
	 */
	public static Bitmap createBitmapForWatermark(Bitmap src, Bitmap watermark) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		// draw watermark into
		cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newb;
	}

	/**
	 * 图片合成.
	 * 
	 * @param direction
	 *            the direction
	 * @param bitmaps
	 *            the bitmaps
	 * @return the bitmap
	 */
	public static Bitmap potoMix(int direction, Bitmap... bitmaps) {
		if (bitmaps.length <= 0) {
			return null;
		}
		if (bitmaps.length == 1) {
			return bitmaps[0];
		}
		Bitmap newBitmap = bitmaps[0];
		// newBitmap = createBitmapForFotoMix(bitmaps[0],bitmaps[1],direction);
		for (int i = 1; i < bitmaps.length; i++) {
			newBitmap = createBitmapForFotoMix(newBitmap, bitmaps[i], direction);
		}
		return newBitmap;
	}

	/**
	 * Creates the bitmap for foto mix.
	 * 
	 * @param first
	 *            the first
	 * @param second
	 *            the second
	 * @param direction
	 *            the direction
	 * @return the bitmap
	 */
	private static Bitmap createBitmapForFotoMix(Bitmap first, Bitmap second,
			int direction) {
		if (first == null) {
			return null;
		}
		if (second == null) {
			return first;
		}
		int fw = first.getWidth();
		int fh = first.getHeight();
		int sw = second.getWidth();
		int sh = second.getHeight();
		Bitmap newBitmap = null;
		if (direction == LEFT) {
			newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, sw, 0, null);
			canvas.drawBitmap(second, 0, 0, null);
		} else if (direction == RIGHT) {
			newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, 0, null);
			canvas.drawBitmap(second, fw, 0, null);
		} else if (direction == TOP) {
			newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, sh, null);
			canvas.drawBitmap(second, 0, 0, null);
		} else if (direction == BOTTOM) {
			newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, 0, null);
			canvas.drawBitmap(second, 0, fh, null);
		}
		return newBitmap;
	}

	/**
	 * 将Bitmap转换成指定大小.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @return the bitmap
	 * @throws OutOfMemoryError
	 *             the out of memory error
	 */
	public static Bitmap createBitmapBySize(Bitmap bitmap, int width, int height)
			throws OutOfMemoryError {
		if (width <= 0) {
			width = bitmap.getWidth();
		}
		if (height <= 0) {
			height = bitmap.getHeight();
		}
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height);
		return bitmap;
	}

	/**
	 * Drawable 转 Bitmap.
	 * 
	 * @param drawable
	 *            the drawable
	 * @return the bitmap
	 */
	public static Bitmap drawableToBitmapByBD(Drawable drawable) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		return bitmapDrawable.getBitmap();
	}

	/**
	 * Bitmap 转 Drawable.
	 * 
	 * @param context
	 *            the context
	 * @param bitmap
	 *            the bitmap
	 * @return the drawable
	 */
	public static Drawable bitmapToDrawableByBD(Context context, Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
		return drawable;
	}

	/**
	 * byte[] 转 bitmap.
	 * 
	 * @param b
	 *            the b
	 * @return the bitmap
	 */
	public static Bitmap bytesToBimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * bitmap 转 byte[].
	 * 
	 * @param bm
	 *            the bm
	 * @return the byte[]
	 */
	public static byte[] bitmapToBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 以最省内存的方式读取本地资源的图片.
	 * 
	 * @param context
	 *            the context
	 * @param resId
	 *            the res id
	 * @return the bitmap
	 */
	public static Bitmap readBitMap(Context context, int resId) {
		Options opt = new Options();
		opt.inPreferredConfig = Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 获取自定义图票的高宽
	 * 
	 * @param context
	 * @param resId
	 * @param mWidth
	 * @param mHeight
	 * @return
	 */
	public static Bitmap readCustomBitmap(Context context, int resId,
			int mWidth, int mHeight) {
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				resId);
		Bitmap useBitmap = Bitmap.createScaledBitmap(bitmap, mWidth, mHeight,
				true);
		if (mWidth != bitmap.getWidth() || mHeight != bitmap.getHeight()) {
			bitmap.recycle();
		}
		return useBitmap;
	}

	/**
	 * Bytes2 bimap.
	 * 
	 * @param b
	 *            the b
	 * @return the bitmap
	 */
	public static Bitmap Bytes2Bimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	// 获得带倒影的图片

	/**
	 * Creates the reflection image with origin.
	 * 
	 * @param bitmap
	 *            the bitmap
	 * @return the bitmap
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
				h / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
				Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	/**
	 * Gets the bitmap drawable.
	 * 
	 * @param context
	 *            the context
	 * @param id
	 *            the id
	 * @return the bitmap drawable
	 */
	public static BitmapDrawable getBitmapDrawable(Context context, int id) {
		Bitmap bm = ImageTools.readBitMap(context, id);
		return new BitmapDrawable(context.getResources(), bm);
	}

	/**
	 * Recycle background.
	 * 
	 * @param view
	 *            the view
	 */
	public static void recycleBackground(View view) {
		if (view == null) {
			return;
		}
		BitmapDrawable viewBd = (BitmapDrawable) view.getBackground();
		if (viewBd != null) {
			view.setBackgroundResource(0);
			viewBd.setCallback(null);
			// if (viewBd.getBitmap() != null &&
			// !viewBd.getBitmap().isRecycled())
			// viewBd.getBitmap().recycle();
		}
		System.gc();
	}

	/**
	 * Recycle image drawable.
	 * 
	 * @param view
	 *            the view
	 */
	public static void recycleImageDrawable(ImageView view) {
		if (view == null) {
			return;
		}
		BitmapDrawable viewBd = (BitmapDrawable) view.getDrawable();
		if (viewBd != null) {
			Bitmap bmp = viewBd.getBitmap();
			view.setImageDrawable(null);
			if (bmp != null && !bmp.isRecycled()) {
				bmp.recycle();
			}
		}
		System.gc();
	}

	public static Bitmap decodeResource(Resources resources, int id) {
		TypedValue value = new TypedValue();
		resources.openRawResource(id, value);
		Options opts = new Options();
		opts.inTargetDensity = value.density;
		return BitmapFactory.decodeResource(resources, id, opts);
	}
}
