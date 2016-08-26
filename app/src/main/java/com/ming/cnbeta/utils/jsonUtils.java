package com.ming.cnbeta.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * json数据处理类
 * Created by ming on 15/8/25.
 */
public class jsonUtils {


    public static <T> T toBean(byte[] json, Class<T> type) {
        T obj = null;
        try {
            String info = new String(json, "UTF-8");

            Gson gson = new Gson();
            obj = gson.fromJson(info, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> T toBean(byte[] json, Type type) {
        T obj = null;
        try {
            String info = new String(json, "UTF-8");
            Gson gson = new Gson();
            obj = gson.fromJson(info, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> T toBean(String json, Type type) {
        T obj = null;
        Gson gson = new Gson();
        obj = gson.fromJson(json, type);
        return obj;
    }

    public static String toJson(Object object) {
        Gson gson = new Gson();
        String result = gson.toJson(object);
        return result;
    }

    /**
     * 通用Json转为List
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String json,Type type){
        Gson gson = new Gson();
        List<T> list = gson.fromJson(json, type);
        return list;
    }
}
