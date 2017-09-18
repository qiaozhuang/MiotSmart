package com.miot.android.robot.host.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miot.android.robot.host.R;
import com.miot.android.robot.host.entity.Device;
import com.miot.android.robot.host.entity.Model;
import com.miot.android.robot.host.entity.Pu;
import com.miot.android.robot.host.entity.Scene;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/12 0012.
 */
public class MmwWebServiceErrorCode {
    public static final String SUCCESS_CODE = "1";
    public static final String SUCCESS_ERROR_MSG = "success";
    public static final String Illegal_request_key_CODE = "101";
    public static final String Illegal_request_key_MSG = "accessKey error";
    public static final String Illegal_request_token_CODE = "102";
    public static final String Illegal_request_token_MSG = "accessToken error";
    public static final String Exception_Code = "103";
    public static final String EFFECT_CODE_0 = "104";
    public static final String EFFECT_0_MSG = "effcet lines 0";
    public static final String DEFAULT_ROOM_NOT_DELETE_CODE = "105";
    public static final String DEFAULT_ROOM_NOT_DELETE_MSG = "default room cannot delete.";
    public static final String PASSWORD_ERROR_CODE = "106";
    public static final String PASSWORD_ERROR_MSG = "old password error";
    public static final String UNKNOW_TYPE_CODE = "107";
    public static final String UNKNOW_TYPE_MSG = "unknow function code.";
    public static final String VCODE_ERROR_MSG = "Vcode error";
    public static final String VCODE_ERROR_CODE = "108";
    public static final String USERNAME_IS_EXIST_MSG = "username is exist";
    public static final String USERNAME_IS_EXIST_CODE = "109";
    public static final String USERNAME_IS_ERROR_MSG = "username is ERROR";
    public static final String USERNAME_IS_ERROR_CODE = "110";
    public static final String USERNAME_IS_NOT_EXIST_MSG = "username is not exist";
    public static final String USERNAME_IS_NOT_EXIST_CODE = "111";

    public static final int VSP_LOADING_EX = 1002;
    public static final int VSP_LOADING_2001 = 2001;
    public static final int VSP_LOADING_2000 = 2000;
    public static final int VSP_LOADING_2002 = 2002;
    public static final int VSP_LOADING_SUCCESS = 1001;
    public static final String NOT_FIND_PU_MAC_CODE_ERROR="115";


    public static String getWebServiceErrorCode(String json) {
        String resultCode = "0";
        try {
            JSONObject jsonObject = new JSONObject(json);
            String body = jsonObject.getString("body");
            JSONObject bodyJson = new JSONObject(body);
            resultCode = bodyJson.getString("resultCode");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultCode;
    }

    public static boolean webserviceRequest(String json) {
        if (getWebServiceErrorCode(json).equals("1")) {
            return true;
        }
        return false;
    }

    public static String webserviceError(Context context, String json) {
        try {
            return getErrorMessage(context, getWebServiceErrorCode(json));
        } catch (Exception e) {
        }
        return "no error";
    }

    static Gson gson=new Gson();

	/**
     * 获取设备列表
     * @param json
     * @return
     */
    public static ArrayList<Pu> getPus(String json){
        ArrayList<Pu> pus=null;
        try {
            if (webserviceRequest(json)){
                JSONObject jsonObject = new JSONObject(json);
                String body = jsonObject.getString("body");
                JSONObject bodyJson = new JSONObject(body);
                JSONObject data=new JSONObject(bodyJson.getString("data"));
                pus = (ArrayList<Pu>) JsonUtils.jsonToList(
                        data.getString("puList"), new TypeToken<ArrayList<Pu>>() {
                        }.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pus;
    }

	/**
     * 获取设备Tocken
     * @param json
     * @return
     */
    public  static String puTocken(String json){

        String result="";
        try {
            if (webserviceRequest(json)){
                JSONObject jsonObject = new JSONObject(json);
                String body = jsonObject.getString("body");
                JSONObject bodyJson = new JSONObject(body);
                JSONObject data=new JSONObject(bodyJson.getString("data"));
                if (data!=null){
                    result=data.getString("puListToken");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }

    public static ArrayList<Scene> getSceen(String json){
        ArrayList<Scene> sceens=null;
        try {
            if (webserviceRequest(json)){
                JSONObject jsonObject = new JSONObject(json);
                String body = jsonObject.getString("body");
                JSONObject bodyJson = new JSONObject(body);
                JSONObject data=new JSONObject(bodyJson.getString("data"));
                sceens = (ArrayList<Scene>) JsonUtils.jsonToList(
                        data.getString("sceneList"), new TypeToken<ArrayList<Scene>>() {
                        }.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sceens;
    }

    public static Device getDevice(String json){
        Device device=null;
        try {
            if (webserviceRequest(json)){
                JSONObject jsonObject = new JSONObject(json);
                String body = jsonObject.getString("body");
                JSONObject bodyJson = new JSONObject(body);
                JSONObject data=new JSONObject(bodyJson.getString("data"));
                if (data!=null) {
                    device=new Device();
                    ArrayList<Pu> pus = (ArrayList<Pu>) JsonUtils.jsonToList(
                            data.getString("puList"), new TypeToken<ArrayList<Pu>>() {
                            }.getType());
                    ArrayList<Scene> sceens = (ArrayList<Scene>) JsonUtils.jsonToList(
                            data.getString("sceneList"), new TypeToken<ArrayList<Scene>>() {
                            }.getType());
                    device.setPus(pus);
                    device.setPuTocken(data.getString("puListToken"));
                    device.setSceens(sceens);
                    device.setSceenTocken(data.getString("sceneListToken"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return device;
    }


    public static Model getModelList(String json){
        Model model=null;
        try {
            if (webserviceRequest(json)){
                model=new Model();
                JSONObject jsonObject = new JSONObject(json);
                String body = jsonObject.getString("body");
                JSONObject bodyJson = new JSONObject(body);
                JSONObject data=new JSONObject(bodyJson.getString("data"));
                if (data!=null) {
                    model.setModelId(data.getString("modelId"));
                    model.setOperationList(data.getString("operationList"));
                    model.setToRefresh(data.getString("toRefresh"));
                    model.setOperationListToken(data.getString("token"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }


    public static String getErrorMessage(Context context, String code) {
        String error = "";
        switch (code) {
            case Illegal_request_key_CODE:
                error = context.getResources().getString(R.string.t_Illegal_request_key_MSG_101);
                break;
            case Illegal_request_token_CODE:
                error = context.getResources().getString(R.string.t_Illegal_request_token_MSG_102);
                break;
            case Exception_Code:
                error = context.getResources().getString(R.string.t_Exception_Code_103);
                break;
            case EFFECT_CODE_0:
                error = context.getResources().getString(R.string.t_EFFECT_0_MSG_104);
                break;
            case DEFAULT_ROOM_NOT_DELETE_CODE:
                error = context.getResources().getString(R.string.t_DEFAULT_ROOM_NOT_DELETE_MSG_105);
                break;
            case PASSWORD_ERROR_CODE:
                error = context.getResources().getString(R.string.t_PASSWORD_ERROR_MSG_106);
                break;
            case UNKNOW_TYPE_CODE:
                error = context.getResources().getString(R.string.t_UNKNOW_TYPE_MSG_107);
                break;
            case VCODE_ERROR_CODE:
                error = context.getResources().getString(R.string.t_VCODE_ERROR_MSG_108);
                break;
            case USERNAME_IS_EXIST_CODE:
                error = context.getResources().getString(R.string.t_USERNAME_IS_EXIST_MSG_109);
                break;
            case USERNAME_IS_ERROR_CODE:
                error = context.getResources().getString(R.string.t_USERNAME_IS_NOT_EXIST_MSG_110);
                break;
            case USERNAME_IS_NOT_EXIST_CODE:
                error = context.getResources().getString(R.string.t_USERNAME_IS_NOT_EXIST_CODE_111);
                break;
            case "0":
                error = "Error Data ";
                break;
            case "112":
                error = "qrcode msg not exist.";
                break;
            case NOT_FIND_PU_MAC_CODE_ERROR:
                error="未将设备MAC地址添加到平台.";
                break;
            default:
                error = "Error";
                break;

        }
        return error;
    }


    public static String getVspLoading(int type) {
        String error = "";
        switch (type) {
            case VSP_LOADING_EX:
                error = "网络异常";
                break;
            case VSP_LOADING_2001:
                error = "用户名或密码不正确,请重新输入";
                break;
            case VSP_LOADING_2002:
                error = "连接服务器失败,请检查网络";
                break;
            case VSP_LOADING_2000:
                error="服务器错误,RS连接失败.";
                break;
            default:
                error = "未知错误";
                break;
        }
        return error;
    }
}
