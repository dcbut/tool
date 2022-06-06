package com.dcxuexi.util.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;


/**
 * <p>Title: JedisPool操作类</p>
 * <p>Description: 完成一些对redis的工作</p>
 *
 * @version 1.0.0
 */
public class JedisPoolUtilDB {

    private static final String REDIS_HOST = RedisPropertiesUtil.getProperty("redis.host");
    private static final int REDIS_PORT = Integer.valueOf(RedisPropertiesUtil.getProperty("redis.port"));
    private static final String REDIS_PASSWD = RedisPropertiesUtil.getProperty("redis.password");

    private static final int MAX_TOTAL = Integer.valueOf(RedisPropertiesUtil.getProperty("redis.pool.maxTotal"));
    private static final int MIN_IDLE = Integer.valueOf(RedisPropertiesUtil.getProperty("redis.pool.maxIdle"));
    private static final int MAX_IDLE = Integer.valueOf(RedisPropertiesUtil.getProperty("redis.pool.minIdle"));
    private static final boolean TEST_ON_BORROW = Boolean.valueOf(RedisPropertiesUtil.getProperty("redis.pool.testOnBorrow"));
    private static final int USER_REDIS_DB = 0;

    private static JedisPool pool = null;


    private JedisPoolUtilDB() {
    }

    private static JedisPool getPool() {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMinIdle(MIN_IDLE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxTotal(MAX_TOTAL);
            config.setTestOnReturn(true);
            config.setTestWhileIdle(true);
            config.setTestOnBorrow(TEST_ON_BORROW);
            pool = new JedisPool(config, REDIS_HOST, REDIS_PORT, 5000, REDIS_PASSWD, USER_REDIS_DB);
        }
        return pool;
    }

    private static synchronized void returnBrokenResource(JedisPool pool, Jedis jedis) {
        if (pool != null && jedis != null) {
            pool.returnBrokenResource(jedis);
        }
    }

    private static synchronized void returnResource(JedisPool pool, Jedis jedis) {
        if (pool != null && jedis != null) {
            pool.returnResource(jedis);
        }
    }

    /**
     * HASH 数据结构操作
     */
    public static synchronized Boolean hasHashKey(String key, String field) {
        Boolean flag = false;
        Jedis jedis = null;

        try {
            jedis = getPool().getResource();

            flag = jedis.hexists(key, field);
        } catch (Exception e) {

            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return flag;
    }

    public static synchronized void putHash(String key, String field, String value) {
        Jedis jedis = null;

        try {
            jedis = getPool().getResource();
            jedis.hset(key, field, value);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
    }

    public static synchronized void delHash(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            jedis.hdel(key, field);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
    }

    public static synchronized String getHashValue(String key, String field) {
        Object obj = null;
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            obj = jedis.hget(key, field);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return (obj == null) ? "" : String.valueOf(obj);
    }

    public static synchronized void putHashMap(String key, Map<String, String> fieldMap) {
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            jedis.hmset(key, fieldMap);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
    }

    public static synchronized void delKey(String key) {
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            jedis.del(key);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
    }

    /*
     *
     */
    public static synchronized void rpushList(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            jedis.rpush(key, value);
            //	    jedis.lpush(key,value);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
    }

    /**
     * @param key
     * @return String
     * @Title: lpopList
     * @Description: Jedis jedis = new Jedis("localhost");
     * Transaction transaction = jedis.multi();
     * transaction.lpush("key", "11");
     * transaction.lpush("key", "22");
     * transaction.lpush("key", "33");
     * List<Object> list = transaction.exec();
     */
    public static synchronized String lpopList(String key) {

        String result = "";
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
//			Transaction  transaction = jedis.multi();//给redis加事物
            result = jedis.lpop(key);
//			transaction.exec();  
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return result;
    }

    public static synchronized Map<String, String> getHashValues(String key) {
        Map<String, String> map = new HashMap<String, String>();
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            map = jedis.hgetAll(key);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return map;
    }

    public static synchronized Long increment(String key, long value) {
        Jedis jedis = null;
        Long nextCursor = 0l;
        try {
            jedis = getPool().getResource();
            nextCursor = jedis.incrBy(key, value);
            //	jedis.incrBy(key, integer)
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return nextCursor;
    }

    public static synchronized String getString(String key) {
        Jedis jedis = null;
        String nextCursor = "";
        try {
            jedis = getPool().getResource();
            nextCursor = jedis.get(key);
            //	jedis.incrBy(key, integer)
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return nextCursor;
    }

    public static synchronized void flushDB() {
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            jedis.flushDB();
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
    }


    public static synchronized Iterator getUserKeys(String key) {
        Jedis jedis = null;
        Iterator result = null;
        try {
            jedis = getPool().getResource();
            result = jedis.keys(key + "*").iterator();
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return result;
    }


    public static synchronized Set<String> getHashKeys(String key) {
        Set<String> map = new HashSet<String>();
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            map = jedis.hkeys(key);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return map;
    }

    public static boolean setex(String key, String value, int seconds) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            jedis.set(key, value);
            jedis.expire(key, seconds);
            result = true;
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return result;
    }


    public static synchronized long expireKey(String key, int seconds) {
        long result = 0l;
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            result = jedis.expire(key, seconds);

        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return result;
    }


    public static synchronized void delListValue(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            jedis.lrem(key, 0, value);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

    }

    public static synchronized List<String> getList(String key) {
        List<String> list = new ArrayList<String>();
        Jedis jedis = null;
        try {
//			System.out.println(new Date());
            jedis = getPool().getResource();

            list = jedis.lrange(key, 0, -1);

            //System.out.println(new Date());
        } catch (Exception e) {
            System.out.println(pool + "|" + jedis);
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return list;
    }

    public static synchronized void delSetValue(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            jedis.srem(key, value);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

    }

    public static synchronized void saveSetValue(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            jedis.sadd(key, value);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
    }

    public static synchronized Set<String> getSetValuess(String key) {
        Jedis jedis = null;
        Set<String> set = null;
        try {
            jedis = getPool().getResource();

            set = jedis.smembers(key);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return set;
    }

    public static synchronized Long getLLen(String key) {
        Jedis jedis = null;
        Long set = null;
        try {
            jedis = getPool().getResource();

            set = jedis.llen(key);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return set;
    }


    public static synchronized boolean stringExists(String key) {
        Jedis jedis = null;
        Boolean set = false;
        try {
            jedis = getPool().getResource();
            set = jedis.exists(key);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
        return set;
    }

    public static synchronized void stringSet(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }
    }


    public static synchronized Long hincrBy(String hashKey, String field) {

        Long obj = 0l;
        Jedis jedis = null;
        try {
            jedis = getPool().getResource();
            obj = jedis.hincrBy(hashKey, field, 1);
        } catch (Exception e) {
            returnBrokenResource(pool, jedis);
            e.printStackTrace();
        } finally {
            returnResource(pool, jedis);
        }

        return obj;

    }


    public static void main(String[] args) throws InterruptedException {


        long i = hincrBy("hash", "heh");
        String string = getHashValue("hash1", "he");
        stringSet("test", "test");
        expireKey("hash", 5);
        System.out.println(i);
        System.out.println(string + "---");

    }
}
