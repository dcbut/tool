package com.dcxuexi.util.redis;


public class RedisPropertiesUtil extends PropertiesUtil {

    /**
     *
     */
    public static String getProperty(String key) {
        return getProperty("redis.properties", key);
    }
}
