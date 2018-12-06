package com.dihri.uploader.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
    private static ObjectMapper objectMapper=new ObjectMapper();

    public static <T> T convertJsonToObject(String jsonString, Class<T> clazz) {
        T object=null;
        try {
            object = objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }
    public static  <T> String convertObjectToJson(T object) {
        String str = "";
        try {
            str = objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
