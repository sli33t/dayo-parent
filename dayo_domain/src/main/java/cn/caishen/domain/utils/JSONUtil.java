package cn.caishen.domain.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JSONUtil {

    /**
     * 将 json 字符串转化为 Class
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonStringToClass(String source, Class<T> clazz){
        if (source.equals("")){
            return null;
        }
        return JSONObject.parseObject(source, clazz);
    }


    /**
     * 将 Object 转化为 Json 串
     * @param source
     * @return
     */
    public static String classToJsonString(Object source){
        if (source==null){
            return "";
        }
        return JSON.toJSONString(source);
    }
}
