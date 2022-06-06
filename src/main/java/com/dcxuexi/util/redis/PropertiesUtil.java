package com.dcxuexi.util.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class PropertiesUtil {

    private static Properties properties;

    /**
     *
     */
    public static synchronized String getProperty(String filename, String key) {
        if (properties == null) {
            try {
                properties = readProperty(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(key);
    }

    /**
     *
     */
    private static <T> Properties readProperty(String file) throws IOException {
        Properties proper = new Properties();
        InputStream in = null;
        try {
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream(file);
            proper.load(in);
        } catch (IOException e) {
            System.out.println("sorry, the file you request does not exist:[" + file + "]");
            throw new IOException();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                System.out.println("sorry, the file you request does not exist:[" + file + "]");
                throw new IOException();
            }
        }
        return proper;
    }
}
