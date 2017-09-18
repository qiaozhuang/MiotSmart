package com.miot.android.robot.host.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by admin on 2016/7/1
 */
public class SharedPreferencesUtil {

    /**
     * SharedPreferences存储文件
     */
    public static final String PU_TOCKEN = "miotlink_pu_tocken";

    public static final String SCEEN_TOCKEN="miotlink_sceen_tocken";

    public static final String MODEL_TOCKEN="miotlink_model_tocken";

    public static  final String PLATFORM_DEVCEI_DATA="platform_device_data";
    public static  final String REBOT_INIT_SATRT="init_start";
    public static  final String REBOT_ADDRESS="address";
    public static  final String REBOT_PORT="port";
    public static  final String REBOT_UPDATE="isUPdate";
    public static  final String REBOT_LAST_DEVICE_DATA="LAST_DEVICE_DATA";
    public static  final String VSP_UPDATE_CUID="cuId";
    public static  final String VSP_LAST_SYSTEM_TIME="time";

    public static final String ROBOT_SAVE_PLATFORM="robotPlatformData";

    /**
     * The settings.
     */
    private SharedPreferences settings;

    /**
     * The Constant sPrefsFileName.
     */
    public static final String sPrefsFileName = "Box_PrefsFile";

    /**
     * The editor.
     */
    private SharedPreferences.Editor editor;

    /**
     * The instance.
     */
    private static SharedPreferencesUtil instance;

    /**
     * The lock.
     */
    private final Lock lock = new ReentrantLock();

    /**
     * The current account.
     */
    private static String currentAccount;

    public final String GUIDE_MASK = "guide_mask";

    /**
     * Gets the single instance of SharedPreferencesUtil.
     *
     * @param context the context
     * @return single instance of SharedPreferencesUtil
     */
    public static SharedPreferencesUtil getInstance(Context context) {
        return getInstance(context, "com.box.share.preference.common");
    }

    /**
     * Instantiates a new shared preferences util.
     *
     * @param context the context
     * @param account the account
     */
    private SharedPreferencesUtil(Context context, String account) {
        String fileName = sPrefsFileName;
        currentAccount = account;
        if (!TextUtils.isEmpty(account)) { // 如果有用户名，则各自存储，否则存在公用的
            fileName = account + sPrefsFileName;
            // userAccount = account;
        } else {
            throw new IllegalArgumentException("account can not be empty.");
        }
        lock.lock();
        try {
            settings = context.getApplicationContext().getSharedPreferences(
                    fileName, Context.MODE_PRIVATE);
            editor = settings.edit();
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }
    }

    /**
     * 配置是不区分用户名存储的，即所有用户都共享这份存储.
     *
     * @param context the context
     * @param account the account
     * @return single instance of SharedPreferencesUtil
     */
    public static SharedPreferencesUtil getInstance(Context context,
                                                    String account) {
        if (instance == null) {
            instance = new SharedPreferencesUtil(context, account);
        } else {
            if (TextUtils.isEmpty(account)) {
                throw new IllegalArgumentException("account can not be empty.");
            } else if (!account.equals(currentAccount)) {
                instance = new SharedPreferencesUtil(context, account);
            }
        }
        return instance;
    }

    /**
     * Gets the shared preferences.
     *
     * @return the shared preferences
     */
    public SharedPreferences getSharedPreferences() {
        return settings;
    }

    /**
     * Gets the editor.
     *
     * @return the editor
     */
    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    /**
     * 清空所有数据.
     */
    public void clearAllData() {
        editor.clear();
        editor.commit();
    }

//    public void

    public void setPuTocken(String tocken){
        editor.putString(PU_TOCKEN,tocken);
        editor.commit();
    }
    public  String getPuTocken() {
        return settings.getString(PU_TOCKEN,"");
    }

    public  void setSceenTocken(String sceenTocken) {
        editor.putString(SCEEN_TOCKEN,sceenTocken);
        editor.commit();
    }

    public  String getSceenTocken() {
        return settings.getString(SCEEN_TOCKEN,"");
    }

    public  void setModelTocken(String modelTocken) {
        editor.putString(MODEL_TOCKEN,modelTocken);
        editor.commit();
    }

    public  String getModelTocken() {
        return settings.getString(MODEL_TOCKEN,"");
    }


    public  void setPlatformDevceiData(String platformDevceiData) {
        editor.putString(PLATFORM_DEVCEI_DATA,platformDevceiData);
        editor.commit();
    }



    public  String getPlatformDevceiData() {

        return settings.getString(PLATFORM_DEVCEI_DATA,"");
    }

    public  void setRebotInitSatrt(String rebotInitSatrt) {
        editor.putString(REBOT_INIT_SATRT,rebotInitSatrt);
        editor.commit();
    }

    public String getRebotInitSatrt() {
        return settings.getString(REBOT_INIT_SATRT,"");
    }

    public  void setIpAddress(String rebotInitSatrt) {
        editor.putString(REBOT_ADDRESS,rebotInitSatrt);
        editor.commit();
    }

    public  void setRebotLastDeviceData(String rebotLastDeviceData) {
        editor.putString(REBOT_LAST_DEVICE_DATA,rebotLastDeviceData);
        editor.commit();
    }

    public  String getRebotLastDeviceData() {
        return settings.getString(REBOT_LAST_DEVICE_DATA,"");
    }

    public String getIpAddress() {
        return settings.getString(REBOT_ADDRESS,"");
    }
    public  void setPort(int  rebotInitSatrt) {
        editor.putInt(REBOT_PORT,rebotInitSatrt);
        editor.commit();
    }
    public int getPort() {
        return settings.getInt(REBOT_PORT,0);
    }

    public  void setRebotUpdate(boolean rebotInitSatrt) {
        editor.putBoolean(REBOT_UPDATE,rebotInitSatrt);
        editor.commit();
    }
    public boolean getRebotUpdate() {
        return settings.getBoolean(REBOT_UPDATE,false);
    }

    public  void setCuId(int  rebotInitSatrt) {
        editor.putInt(VSP_UPDATE_CUID,rebotInitSatrt);
        editor.commit();
    }
    public int getCuId() {
        return settings.getInt(VSP_UPDATE_CUID,0);
    }
    public  void setLastTime(long  rebotInitSatrt) {
        editor.putLong(VSP_LAST_SYSTEM_TIME,rebotInitSatrt);
        editor.commit();
    }
    public long getLastTime() {
        return settings.getLong(VSP_LAST_SYSTEM_TIME,0);
    }

    public  void setRobotSavePlatform(String robotSavePlatform) {
        editor.putString(ROBOT_SAVE_PLATFORM,robotSavePlatform);
        editor.commit();
    }
    public  String getRobotSavePlatform() {
        return settings.getString(ROBOT_SAVE_PLATFORM,"");
    }
}
