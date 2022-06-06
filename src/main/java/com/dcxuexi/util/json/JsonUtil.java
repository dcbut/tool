package com.dcxuexi.util.json;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created with Intellij IDEA
 */
public class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    }

    /**
     * java bean to json字符串
     *
     * @param bean
     * @return
     */
    public static String beanToJsonString(Object bean) {
        if (bean == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * java bean to json字符串 支持跨域
     *
     * @param bean
     * @return
     */
    public static String beanToJsonString(String callback, Object bean) {
        if (bean == null) {
            return null;
        }
        try {
            if (!Strings.isNullOrEmpty(callback)) {
                return callback + "(" + beanToJsonString(bean) + ")";
            } else {
                return mapper.writeValueAsString(bean);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * java bean to json字符串 支持跨域
     *
     * @param bean
     * @return
     */
    @SuppressWarnings("rawtypes")
//	public static String beanToJsonString(String callback,BaseDto bean) {
//		if (bean == null) {
//			return null;
//		}
//		try {
//			if(!Strings.isNullOrEmpty(callback)&&null!=bean.getResCode()){
//				return callback+"("+mapper.writeValueAsString(bean)+")";
//			}else{
//				return mapper.writeValueAsString(bean);
//			}
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

    /**
     * java bean to json字符串
     *
     * @param bean
     * @return
     * @throws JsonProcessingException
     */
    public static String beanToJsonStringThrowException(Object bean) throws JsonProcessingException {
        if (bean == null) {
            return null;
        }
        return mapper.writeValueAsString(bean);
    }

    /**
     * json字符串 to Java Bean
     *
     * @param
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Object jsonStringToBean(String jsonString, String className) {
        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        }
        try {
            Class beanClass = Class.forName(className);
            Object t = mapper.readValue(jsonString, beanClass);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串 to Java Object
     *
     * @param
     * @return
     */
    public static Object jsonStringToObject(String jsonString, Class<?> beanClass) {
        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, beanClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串 to Java Bean
     *
     * @param
     * @return
     */
    public static <T extends Object> T jsonStringToBean(String jsonString, Class<T> beanClass) {
        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, beanClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象 to Map
     *
     * @param object
     * @param mapClass
     * @param keyClass
     * @param valueClass
     * @return
     */
    public static <K extends Object, V extends Object> Map<K, V> objectToMap(Object object, Class<?> mapClass,
                                                                             Class<K> keyClass, Class<V> valueClass) {
        try {
            JavaType t = mapper.getTypeFactory().constructParametricType(mapClass, keyClass, valueClass);
            return mapper.readValue(beanToJsonString(object), t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * string to Map
     *
     * @param string
     * @param mapClass
     * @param keyClass
     * @param valueClass
     * @return
     */
    public static <K extends Object, V extends Object> Map<K, V> jsonStringToMap(String string, Class<?> mapClass,
                                                                                 Class<K> keyClass, Class<V> valueClass) {
        try {
            JavaType t = mapper.getTypeFactory().constructParametricType(mapClass, keyClass, valueClass);
            return mapper.readValue(string, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串 to Java Bean
     *
     * @param
     * @return
     */
    public static <T extends Object> T objectToBean(Object object, Class<T> beanClass) {
        if (object == null) {
            return null;
        }
        return JsonUtil.jsonStringToBean(JsonUtil.beanToJsonString(object), beanClass);
    }

    /**
     * json字符串 to Java Bean
     *
     * @param
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    public static <T extends Object> T jsonStringToBeanThrowException(String jsonString, Class<T> beanClass)
            throws JsonParseException, JsonMappingException, IOException {
        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        }
        return mapper.readValue(jsonString, beanClass);
    }

    /**
     * json字符串 to Java Bean List
     *
     * @param
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @SuppressWarnings("unchecked")
    public final static <T extends Object> List<T> jsonStringToBeanListThrowException(String jsonString,
                                                                                      Class<T> beanClass) throws JsonParseException, JsonMappingException, IOException {
        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        }
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanClass);
        return (List<T>) mapper.readValue(jsonString, javaType);
    }

    /**
     * json字符串 to Java Bean List
     *
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    public final static <T extends Object> List<T> jsonStringToBeanList(String jsonString, Class<T> beanClass) {
        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        }
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanClass);
            return (List<T>) mapper.readValue(jsonString, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }
}