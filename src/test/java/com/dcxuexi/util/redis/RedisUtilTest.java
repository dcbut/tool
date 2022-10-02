package com.dcxuexi.util.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/***
 * @Title RedisUtilTest
 * @Description TOTD
 * @Auter DongChuang
 * @Date 2022/9/26 23:13
 * @Version 1.0.0
 */

public class RedisUtilTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void  testString(){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        //ops.set("country","china");
//        ops.set("province","shanghai");
//        ops.set("city","shanghaishi");
//        ops.set("area","pudongxinqu");
        ops.set("country","china",Duration.ofMillis(30000));
        ops.set("province","shanghai",3);
        ops.set("city","shanghaishi",10,TimeUnit.MINUTES);

    }

    @Test
    void  testGet(){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String country = ops.get("country");
        String province = ops.get("province");
        String city = ops.get("city");
        String area = ops.get("area");
        System.out.println("country : "+country+", province : "+province+", city : "+city+", area : "+area);


    }

    @Test
    void testDelete(){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String country = ops.getAndSet("area","baoshan");
        System.out.println(country);

    }


    @Test
    void testHash(){
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
//        ops.put("admin","name","张三");
//        ops.put("admin","age","25");
//        ops.put("user","name","李四");

//        Map<String ,String> userMap = new HashMap<>();
//        userMap.put("name","张三");
//        userMap.put("adress","上海");
//        userMap.put("mail","zhagnsan@qq.com");

        //ops.putAll(userMap.get("mail"),userMap);
        //System.out.println(ops.get("zhagnsan@qq.com", "adress"));
        //System.out.println(ops.entries("zhagnsan@qq.com").get("mail"));

        //System.out.println(ops.delete("amdin", "age"));

        Set<Object> keys = ops.keys("zhagnsan@qq.com");
        System.out.println(keys);

        Long size = ops.size("zhagnsan@qq.com");
        System.out.println(size);

        Long name = ops.lengthOfValue("zhagnsan@qq.com", "name2");
        System.out.println(name);




    }


    @Test
    void testList(){
        ListOperations<String, String> ops = redisTemplate.opsForList();
        //System.out.println(ops.leftPush("tel", "15200002222"));
        //System.out.println(ops.leftPushAll("tel", "13811110000", "15201832209", "18265388569"));
//        String tel = ops.leftPop("tel", 3000, TimeUnit.MINUTES);
//        System.out.println(tel);

        //System.out.println(ops.index("tel", 0));

        System.out.println(ops.indexOf("tel", "13704870777"));

        Collection<String> strlist = new ArrayList<>();
        strlist.add("13796055669");
        strlist.add("13704870777");
        strlist.add("18245801246");
        strlist.add("18245801249");
        strlist.add("13836807798");
        //System.out.println(ops.rightPushAll("tel", strlist));
        System.out.println(ops.lastIndexOf("tel", "13704870777"));
        System.out.println(ops.size("tel"));


    }

    @Test
    void testSet(){
        SetOperations<String, String> ops = redisTemplate.opsForSet();
        //System.out.println(ops.add("username", "zhangsan3", "lisi3", "wanger3", "mazi3", "liudehua3"));
        //System.out.println(ops.add("username1", "zhangsan4", "lisi4", "wanger4", "mazi5", "liudehua5"));

        ///System.out.println(ops.size("username"));
        //System.out.println(ops.pop("username"));


        Set<String> difference = ops.difference("username", "username4");
        System.out.println(difference);

        //Long aLong = ops.differenceAndStore("username", "username1", "username2");
        //System.out.println(aLong);

        Set<String> union = ops.union("username", "username1");
        System.out.println(union);

        //Long aLong = ops.unionAndStore("username", "username1", "username3");
        //System.out.println(aLong);

        Set<String> intersect = ops.intersect("username", "username1");
        System.out.println(intersect);

        //System.out.println(ops.intersectAndStore("username", "username1", "username4"));


    }

    @Test
    void testzset(){
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        System.out.println(ops.add("areaZSet", "beijign0", 1));
        System.out.println(ops.add("areaZSet", "beijign02", 2));
        System.out.println(ops.add("areaZSet", "beijign06", 6));
        System.out.println(ops.add("areaZSet", "beijign04", 4));
        System.out.println(ops.add("areaZSet", "beijign07", 7));
        System.out.println(ops.add("areaZSet", "beijign03", 3));

        Set<ZSetOperations.TypedTuple<Long>> var = new HashSet<>();
        //ZSetOperations.TypedTuple<Long> s = new An
        //ops.add()


    }

    /**
     * *  Zset类型操作
     * 1.增加
     * add
     * 注意：
     * （1）与set的区别是需要带分数score
     * （2）如果key不存在，将会被创建，value不存在也会被创建
     * （3）如果value存在，将不能被添加
     */
    @Test
    public void addZset(){
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        //（1）添加单个值
        Boolean success = ops.add("nameZset01", "V01", 1.0);
        System.out.println("添加单个值："+success);
        //（2）添加多个值
        ZSetOperations.TypedTuple<String> v2 = new DefaultTypedTuple<String>("V02", 3.0);
        ZSetOperations.TypedTuple<String> v3 = new DefaultTypedTuple<String>("V03", 2.0);
        HashSet<ZSetOperations.TypedTuple<String>> typedTuples = new HashSet<ZSetOperations.TypedTuple<String>>();
        typedTuples.add(v2);
        typedTuples.add(v3);
        Long count = ops.add("nameZset01",typedTuples);
        System.out.println("成功添加了"+count+"个值");
    }

    /** *
     *  2.移除元素 *
     *  remove */
    @Test
    public void removeZset(){
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        redisTemplate.delete("nameZset01");

        ops.add("zset1","a",1.0);
        ops.add("zset1","b",3.2);
        ops.add("zset1","d",5.5);
        ops.add("zset1","c",2.0);
        ops.add("zset1","e",5.2);
        ops.add("zset1","f",6.0);
        //（1）移除一个元素
        Long count = ops.remove("zset1","a");
        System.out.println("移除的元素个数："+count);
        //（2）移除多个元素
        Long count1 = ops.remove("zset1","b", "c");
        System.out.println("移除的元素个数："+count1);
        //（3）移除指定位置的元素（注意是闭区间）
        ops.removeRange("zset1",0,-1);
        //（4）移除分数区间的元素（注意是闭区间）
        ops.removeRangeByScore("zset1",1.0,2.0);
    }

    /**
     * 2.获取Zset的大小
     * size:获取Zset的大小
     * zCard:获取Zset的大小（其实size的底层就是使用zCard）
     */
    @Test
    public void getZsetSize(){
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        redisTemplate.delete("zset02");
        ops.add("zset02","a",1.0);
        ops.add("zset02","b",3.2);
        ops.add("zset02","d",5.5);
        ops.add("zset02","c",2.0);
        Long size = ops.size("zset02");
        System.out.println("zset1的值个数:"+size);
        Long zCard = ops.zCard("zset02");
        System.out.println("zset1的值个数:"+zCard);
    }

    /**
     * 4.获取score在指定区间值的个数
     * count
     * 注意：区间是包括两边的，比如下面的实际上是区间[20.0,30.0]
     */
    @Test
    public void getZsetFromScoreRange(){
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        redisTemplate.delete("zset03");
        ops.add("zset03","a",1.0);
        ops.add("zset03","b",3.2);
        ops.add("zset03","d",5.5);
        ops.add("zset03","c",2.0);
        Long count = ops.count("zset03",1.0, 4.0);
        System.out.println("zset03中在区间10.0到30.0之间的数据个数有:"+count);
    }

    /**
     *  5.通过value获取score
     *  score
     */
    @Test
    public void getScoreByValue(){
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        Double score = ops.score("zset03","d");
        System.out.println("zset03中value为d的score为:"+score);
    }

    /**
     *  6.对score增加指定的数值，返回增加后的值
     *  incrementScore */
    @Test
    public void increScore(){
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        Double score = ops.incrementScore("zset03","a", 10.0);
        System.out.println("zset03中v1的score增加到了:"+score);
    }


    /**
     *  7.读取Zset集合中的全部元素元素
     *  scan */
    @Test
    public void zsetScan(){
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        Cursor<ZSetOperations.TypedTuple<String>> scan = ops.scan("zset03", ScanOptions.NONE);
        //需要迭代
        while (scan.hasNext()){
            ZSetOperations.TypedTuple<String> next = scan.next();
            System.out.println("value:"+next.getValue()+","+"score:"+next.getScore());
        }
    }

    /**
     *  8.获取排序后的索引：
     *  rank：在排序集中确定具有值的元素的索引,并返回其索引(从低到高),
     *  reverseRank：在排序集中确定具有值的元素的索引,并返回其索引(从高到底),
     */
    @Test
    public void getZsetRank(){
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        //（1）正序获得索引值
        //第一步：排序（按照分数值从小到大排序）
        System.out.println("正序排列："+ops.range("zset03",0, -1));
        //第二步：获取指定值的索引（必须在排序的情况下,另外索引值从0开始）
        System.out.println("指定值的位置："+ops.rank("zset03","b"));
        //（2）反序获得索引值
        System.out.println("反序排列："+ ops.reverseRange("zset03",0,-1));
        System.out.println("指定值的位置："+ops.reverseRank("zset03","b"));
    }


    /**
     *  8.各种排序；
     *  range：按照分数由小到大排序（指定位置区间）
     *  reverseRange：按照分数由大到小排序（指定位置区间）
     *  rangeByScore：按照分数由小到大排序（指定分数区间）
     *  reverseRangeByScore：按照分数由大到小排序（指定分数区间）
     *  rangeByScoreWithScores：按照分数由小到大排序（指定分数区间），并返回排序后的结果（带分数）
     *  reverseRangeByScoreWithScores：按照分数由大到小排序（指定分数区间），并返回排序后的结果（带分数）
     *  rangeWithScores：按照分数从小到大排序（指定位置区间），得到的值带有score
     *  reverseRangeWithScores：按照分数从大到小排序（指定分数区间），得到的值带有score
     *  */
    @Test
    public void getZsetRange(){
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        //（1）默认的按照分数从小到大排序（指定位置区间）
        System.out.println("按照score从小到大排序："+ ops.range("zset03",0,-1));
        //（2）按照分数从大到小排序（指定位置区间）
        System.out.println("按照score从大到小排序："+ ops.reverseRange("zset03",0,-1));
        //（3）按照分数从大到小排序（指定分数区间）
        System.out.println("按照score从大到小排序："+ ops.rangeByScore("zset03",1.0,6.0));
        //（4）按照分数从大到小排序（指定分数区间）
        System.out.println("按照score从大到小排序："+ ops.reverseRangeByScore("zset03",1.0,6.0));
        //（5）按照分数从小到大排序（指定分数区间），得到的值带有score
        Set<ZSetOperations.TypedTuple<String>> zset1 = ops.rangeByScoreWithScores("zset03",1.0, 6.0);
        //需要迭代
        Iterator<ZSetOperations.TypedTuple<String>> iterator1 = zset1.iterator();
        while (iterator1.hasNext()){
            ZSetOperations.TypedTuple<String> next1 = iterator1.next();
            System.out.println("value:"+next1.getValue()+","+"score:"+next1.getScore());
        }
        System.out.println("***************************************************");
        //（6）按照分数从大到小排序（指定分数区间），得到的值带有score
        Set<ZSetOperations.TypedTuple<String>> zset2 = ops.reverseRangeByScoreWithScores("zset03",1.0, 6.0);
        //需要迭代
        Iterator<ZSetOperations.TypedTuple<String>> iterator2 = zset2.iterator();
        while (iterator2.hasNext()){
            ZSetOperations.TypedTuple<String> next2 = iterator2.next();
            System.out.println("value:"+next2.getValue()+","+"score:"+next2.getScore());
        }
        System.out.println("***************************************************");
        //（7）按照分数从小到大排序（指定位置区间），得到的值带有score
        Set<ZSetOperations.TypedTuple<String>> zset3 = ops.rangeWithScores("zset03",0, -1);
        //需要迭代
        Iterator<ZSetOperations.TypedTuple<String>> iterator3 = zset3.iterator();
        while (iterator3.hasNext()){
            ZSetOperations.TypedTuple<String> next3 = iterator3.next();
            System.out.println("value:"+next3.getValue()+","+"score:"+next3.getScore());
        }
        System.out.println("***************************************************");
        //（8）按照分数从大到小排序（指定分数区间），得到的值带有score
        Set<ZSetOperations.TypedTuple<String>> zset4 = ops.reverseRangeWithScores("zset03",0, -1);
        //需要迭代
        Iterator<ZSetOperations.TypedTuple<String>> iterator4 = zset4.iterator();
        while (iterator4.hasNext()){
            ZSetOperations.TypedTuple<String> next4 = iterator4.next();
            System.out.println("value:"+next4.getValue()+","+"score:"+next4.getScore());
        }
        System.out.println("***************************************************");
    }


    /**
     *  10.交集
     *  intersectAndStore */
    @Test
    public void getIntersectAndStore() {
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        redisTemplate.delete("zset11");
        ops.add("zset11","a", 1.0);
        ops.add("zset11","b", 3.2);
        ops.add("zset11","d", 5.5);
        ops.add("zset11","c", 2.0);
        redisTemplate.delete("zset12");
        ops.add("zset12","b", 3.2);
        ops.add("zset12","d", 5.5);
        ops.add("zset12","c", 2.0);
        ops.add("zset12","f", 4.4);
        redisTemplate.delete("zset13");
        ops.add("zset13","e", 3.2);
        ops.add("zset13","f", 5.5);
        ops.add("zset13","c", 2.0);
        ops.add("zset13","a", 4.4);
        //一：两个集合做交集
        ops.intersectAndStore("zset11","zset12", "zset21");
        Cursor<ZSetOperations.TypedTuple<String>> scan = ops.scan("zset21", ScanOptions.NONE);
        //需要迭代
        while (scan.hasNext()) {
            ZSetOperations.TypedTuple<String> next = scan.next();
            System.out.println("value:" + next.getValue() + "," + "score:" + next.getScore());
        }
        System.out.println("***********************************************************");
        redisTemplate.delete("zset15");
        //二：多个集合做交集
        List<String> list = new ArrayList<String>();
        list.add("zset12");
        list.add("zset13");
        ops.intersectAndStore("zset11",list, "zset25");
        Cursor<ZSetOperations.TypedTuple<String>> scan1 = ops.scan("zset25", ScanOptions.NONE);
        //需要迭代
        while (scan1.hasNext()) {
            ZSetOperations.TypedTuple<String> next1 = scan1.next();
            System.out.println("value:" + next1.getValue() + "," + "score:" + next1.getScore());
        }
    }


    /**
     *  并集
     *  unionAndStore
     *  注意：对于相同的value，其score是相加
     */
    @Test
    public void getUnionAndStore(){
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        redisTemplate.delete("zset101");
        ops.add("zset101","a",1.0);
        ops.add("zset101","b",3.2);
        ops.add("zset101","d",5.5);
        ops.add("zset101","c",2.0);
        redisTemplate.delete("zset102");
        ops.add("zset102","b",3.2);
        ops.add("zset102","d",5.5);
        ops.add("zset102","c",2.0);
        ops.add("zset102","f",4.4);
        redisTemplate.delete("zset104");
        ops.add("zset104","e",3.2);
        ops.add("zset104","f",5.5);
        ops.add("zset104","c",2.0);
        ops.add("zset104","a",4.4);
        redisTemplate.delete("zset103");
        //一：两个集合做交集
        ops.unionAndStore("zset101","zset102","zset103");
        Cursor<ZSetOperations.TypedTuple<String>> scan =ops.scan("zset103", ScanOptions.NONE);
        //需要迭代
        while (scan.hasNext()){
            ZSetOperations.TypedTuple<String> next = scan.next();
            System.out.println("value:"+next.getValue()+","+"score:"+next.getScore());
        }
        System.out.println("***********************************************************");
        redisTemplate.delete("zset105");
        //二：多个集合做交集
        List<String> list = new ArrayList<String>();
        list.add("zset102");
        list.add("zset104");
        ops.unionAndStore("zset101",list,"zset105");
        Cursor<ZSetOperations.TypedTuple<String>> scan1 = ops.scan("zset105", ScanOptions.NONE);
        //需要迭代
        while (scan1.hasNext()){
            ZSetOperations.TypedTuple<String> next1 = scan1.next();
            System.out.println("value:"+next1.getValue()+","+"score:"+next1.getScore());
        }
    }


    /**
     *  12.按照字典顺序给value排序（可以参考redis命令ZRANGEBYLEX）
     *  rangeByLex
     *  注意：（1）排序的value，其socre必须一致相同
     *  （2）rangeByLex的作用参考redis命令ZRANGEBYLEX
     *  */
    @Test
    public void zsetRangeByLex(){
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        redisTemplate.delete("zset201");
        ops.add("zset201","a", 1.0);
        ops.add("zset201","f", 1.0);
        ops.add("zset201","s", 1.0);
        ops.add("zset201","d", 1.0);
        ops.add("zset201","b", 1.0);
        ops.add("zset201","c", 1.0);
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        range.gt("a"); //从a开始，但是不包括a
        range.lt("s"); //s结束，但是不包括s
        System.out.println(ops.rangeByLex("zset201",range));  //获得所有元素与ZSET辞典编纂的排序键和一个值
        RedisZSetCommands.Limit limit = new RedisZSetCommands.Limit();
        limit.offset(0); //从坐标0开始
        limit.count(3); //取出3个
        System.out.println(ops.rangeByLex("zset201",range, limit)); //获得所有元素与ZSET辞典编纂的排序键和一个值,限制个数
    }


    @Test
    void testRedis(){
        redisTemplate.boundValueOps("keyValue").set("value");
        redisTemplate.boundHashOps("keyHash").put("key","value");
        redisTemplate.boundListOps("keyList").leftPush("value");
        redisTemplate.boundSetOps("keySet").add("values");
        redisTemplate.boundZSetOps("keyZSet").add("values",1.0);



    }









}
