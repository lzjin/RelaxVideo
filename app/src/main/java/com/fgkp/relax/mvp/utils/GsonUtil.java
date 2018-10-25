package com.fgkp.relax.mvp.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator
 *  解析工具类
 */

public class GsonUtil {
    private static String tag="test";
    /**
     * 对象数据
     * {
     *    success : true
     *    message : 操作成功
     * }
     */
    public static <T> T getUser(String jsonData, Class<T> type) {
        MLog.e(tag, "----------user之前=="+jsonData.toString());
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    /**
     * 不带数据头 使用Gson进行解析 List<User>
     *     [
     *      {"id":"919479606879191040","status":1,"tType":0},
     *      {"id":"919471842597535744","status":0,"tType":1}
     *     ]
     *
     */
    public static <T> ArrayList<T> getList(String json, Class<T> clazz){
        Type type = new TypeToken<ArrayList<JsonObject>>() {}.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<T>();
        for (JsonObject jsonObject : jsonObjects){
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }


    // 带数据头  List<User>
    public static <T> List<T> getListHead(String json, Class<T> cls) {
        MLog.e(tag, "----------user之前=="+json.toString());
        List<T> list = new ArrayList<T>();
        Gson gson = new Gson();

        try {
            list = gson.fromJson(json, new TypeToken<List<T>>(){
            }.getType());
        } catch (Exception e) {
        }
        return list;
    }
}
