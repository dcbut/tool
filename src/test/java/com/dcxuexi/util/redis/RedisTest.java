package com.dcxuexi.util.redis;

import java.util.Set;

public class RedisTest {

    public static void main(String[] args) {


        JedisPoolUtilDB.saveSetValue("testSet", "heh");
        Set<String> sets = JedisPoolUtilDB.getSetValuess("testSet");
        System.out.println(sets);
        JedisPoolUtilDB.setex("token", "6ae21107b9acad52c7a8433156940ad6160fd5d7f091422cd33972cfc8ec92069ea2408f8003", 20000);
        System.out.println(JedisPoolUtilDB.getString("token"));
        //一个守护线程来监听一个tast_id是否完成
        //N个子任务

    }
}
