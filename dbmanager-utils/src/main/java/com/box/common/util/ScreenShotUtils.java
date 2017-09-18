package com.box.common.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

/**
 * 此类是用于截屏处理
 * @author Administrator
 *
 */
public class ScreenShotUtils {	
	public static Bitmap takeScreenShot(Activity pActivity)
	{
		Bitmap bitmap=null;
		View view=pActivity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		bitmap=view.getDrawingCache();
		
		Rect frame=new Rect();
		view.getWindowVisibleDisplayFrame(frame);
		int stautsHeight=frame.top;
		
		int width=pActivity.getWindowManager().getDefaultDisplay().getWidth();
		int height=pActivity.getWindowManager().getDefaultDisplay().getHeight();
		bitmap=Bitmap.createBitmap(bitmap, 0, stautsHeight, width, height-stautsHeight);
		return bitmap;
	}
	
	
	private static boolean savePic(Bitmap pBitmap,String strName)
	{
	  FileOutputStream fos=null;
	  try {
		fos=new FileOutputStream(strName);
		if(null!=fos)
		{
			pBitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
			fos.flush();
			fos.close();
			return true;
		}
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}catch (IOException e) {
		e.printStackTrace();
	}
	  return false;
	} 
	public static boolean shotBitmap(Activity pActivity,String path)
	{
		return  ScreenShotUtils.savePic(takeScreenShot(pActivity), path+".png");
	}
	 static Bitmap getSceenShort(Activity context) {
		 try {
			 View cv = context.getWindow().getDecorView();  
		     Bitmap bmp = Bitmap.createBitmap(cv.getWidth(), cv.getHeight(),Config.ARGB_8888);  
		     Canvas canvas = new Canvas(bmp);  
		     cv.draw(canvas);  
		     return bmp;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;
		
	}
	 
	 public static boolean isSceenShort(WebView context,String path){
		 return savePic(captureWebView(context),path);
	 }
	 static Bitmap captureWebView(WebView webView){
			Picture snapShot = webView.capturePicture();
			
			Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(),snapShot.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(bmp);
			snapShot.draw(canvas);
			return bmp;
		}
}
