package com.jackc.fun.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by cj on 2019/7/26.
 */
public class JsonUtil {
    public static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    public static <T>T parseJson(String json,Class<T> tClass){
        try {
            T t = objectMapper.readValue(json, tClass);
            return t;
        } catch (IOException e) {

        }
        return null;
    }
    public static String stringify(Object obj){
        try {
            String s = objectMapper.writeValueAsString(obj);
            return s;
        } catch (JsonProcessingException e) {

        }
        return null;
    }

    public static <T>T convertValue(Map map,Class<T> tClass){
        T t = objectMapper.convertValue(map, tClass);
        return t;
    }

    public static <T>List<T> convertValue(List<Map<String,Object>> list,Class<T> tClass){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, tClass);
        List<T> t = objectMapper.convertValue(list, javaType);
        return t;
    }
}
