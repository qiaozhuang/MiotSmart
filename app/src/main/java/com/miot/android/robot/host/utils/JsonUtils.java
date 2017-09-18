package com.miot.android.robot.host.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.miot.android.robot.host.entity.ROBOTINFI;
import com.miot.android.robot.host.entity.RobotVersion;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
public class JsonUtils {
    public static String json(Map<String, Object> head, Map<String, Object> body) throws Exception {
        String json = "";
        Gson gson = new Gson();
        List<Object> object = new ArrayList<>();
        Map<String, Object> request = new HashMap<>();
        if (head != null) {
            request.put("head", head);
        }
        head.put("reqTime", System.currentTimeMillis());
        if (body != null) {
            request.put("body", body);
        }
        json = gson.toJson(request);
        return json;
    }

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private JsonUtils() {

    }

    /**
     * 将对象转换成json格式
     *
     * @param ts
     * @return
     */
    public static String objectToJson(Object ts) {
        String jsonStr = null;
        if (gson != null) {
            jsonStr = gson.toJson(ts);
        }
        return jsonStr;
    }

    /**
     * 将对象转换成json格式(并自定义日期格式)
     *
     * @param ts
     * @return
     */
    public static String objectToJsonDateSerializer(Object ts, final String dateformat) {
        String jsonStr = null;
        gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(Date.class, new JsonSerializer<Date>() {
                    @Override
                    public JsonElement serialize(Date src, Type typeOfSrc,
                                                 JsonSerializationContext context) {
                        SimpleDateFormat format = new SimpleDateFormat(dateformat);
                        return new JsonPrimitive(format.format(src));
                    }
                }).setDateFormat(dateformat).create();
        if (gson != null) {
            jsonStr = gson.toJson(ts);
        }
        return jsonStr;
    }

    /**
     * 将json格式转换成list对象
     *
     * @param jsonStr
     * @return
     */
    public static List<?> jsonToList(String jsonStr) {
        List<?> objList = null;
        if (gson != null) {
            Type type = new com.google.gson.reflect.TypeToken<List<?>>() {
            }.getType();
            objList = gson.fromJson(jsonStr, type);
        }
        return objList;
    }

    /**
     * 将json格式转换成list对象，并准确指定类型
     *
     * @param jsonStr
     * @param type
     * @return
     */
    public static List<?> jsonToList(String jsonStr, Type type) {
        List<?> objList = null;
        if (gson != null) {
            objList = gson.fromJson(jsonStr, type);
        }
        return objList;
    }

    /**
     * 将json格式转换成map对象
     *
     * @param jsonStr
     * @return
     */
    public static Map<?, ?> jsonToMap(String jsonStr) {
        Map<?, ?> objMap = null;
        if (gson != null) {
            Type type = new com.google.gson.reflect.TypeToken<Map<?, ?>>() {
            }.getType();
            objMap = gson.fromJson(jsonStr, type);
        }
        return objMap;
    }

    /**
     * 将json转换成bean对象
     *
     * @param jsonStr
     * @return
     */
    public static Object jsonToBean(String jsonStr, Class<?> cl) {
        Object obj = null;
        if (gson != null) {
            obj = gson.fromJson(jsonStr, cl);
        }
        return obj;
    }

    /**
     * 将json转换成bean对象
     *
     * @param jsonStr
     * @param cl
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToBeanDateSerializer(String jsonStr, Class<T> cl, final String pattern) {
        Object obj = null;
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2)
                    throws JsonParseException {
                // TODO Auto-generated method stub
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                String dateStr = json.getAsString();
                try {
                    return format.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).setDateFormat(pattern).create();
        if (gson != null) {
            obj = gson.fromJson(jsonStr, cl);
        }
        return (T) obj;
    }

    /**
     * 根据
     *
     * @param jsonStr
     * @param key
     * @return
     */
    public static Object getJsonValue(String jsonStr, String key) {
        Object rulsObj = null;
        Map<?, ?> rulsMap = jsonToMap(jsonStr);
        if (rulsMap != null && rulsMap.size() > 0) {
            rulsObj = rulsMap.get(key);
        }
        return rulsObj;
    }

    public static String getVoiceCode(String json) {
        String code="";
        try {
            if (json.isEmpty()) {
                return code;
            }
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject != null) {
                JSONObject jsonObject1 = new JSONObject(jsonObject.getString("COMMAND"));
                if (jsonObject1 != null) {
                    code = jsonObject1.getString("code");
                    if (code.isEmpty()) {
                        return code;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return code;
    }
    //{"VERSION":"0001","ROBOTINFO":{"robotID":"SZB0C03005BBF","ip":"192.168.1.162"},"SEARCH":"START"}
    public static RobotVersion getRobotInitStart(String json){
        RobotVersion robotVersion=new RobotVersion();
        try{
            JSONObject jsonObject=new JSONObject(json);
            if (jsonObject!=null){
                robotVersion.setSEARCH(jsonObject.getString("SEARCH"));
                robotVersion.setVERSION(jsonObject.getString("VERSION"));
                ROBOTINFI robotinfi=new ROBOTINFI();
                JSONObject jsonObject1=new JSONObject(jsonObject.getString("ROBOTINFO"));
                if (jsonObject1.has("robotID")){
                    robotinfi.setSn(jsonObject1.getString("robotID"));
                }
                if (jsonObject1.has("sn")){
                    robotinfi.setSn(jsonObject1.getString("sn"));
                }
                robotinfi.setIp(jsonObject1.getString("ip"));
                robotVersion.setROBOTINFO(robotinfi);
            }
        }catch (Exception e){}
        return robotVersion;
    }



    public static String assembleVoiceCommands(String message){
        try {
            JSONObject commandSubObj = new JSONObject();
               if (message.isEmpty()){
                   commandSubObj.put("tts_text","执行命令出错,请重新操作");
                }else {
                   commandSubObj.put("tts_text", message);
               }
            JSONObject commandObj = new JSONObject();
            commandObj.put("TTS", commandSubObj);
            return commandObj.toString();
            } catch (JSONException e) {
                 e.printStackTrace();
          }

        return "";
    }

    public static String getSceneCu(String sceneId){
        String result="";
        try {
            JSONObject jsonObject=new JSONObject();
            JSONObject data=new JSONObject();
            data.put("sceneId",sceneId);
            jsonObject.put("code","triggerScene");
            jsonObject.put("data",data);
            result=jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

//    private


}
