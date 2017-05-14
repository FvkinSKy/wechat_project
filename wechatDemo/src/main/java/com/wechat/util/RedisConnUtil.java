package com.wechat.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by rui on 2017/5/12.
 * redis工具类
 */
public class RedisConnUtil {

    //ip
    private static String ADDRESS = "127.0.0.1";
    //端口
    private static int PORT = 6379;
    //口令
    private static String AUTH = "admin";
    //可用连接数
    private static int MAX_ACTIVE = 100;
    //空闲状态实例数量
    private static int MAX_IDLE = 200;
    //等待连接时间
    private static int WAIT = 10000;
    //连接超时时间
    private static int TIMEOUT = 10000;

    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    //初始化连接池
    static {
        try {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxIdle(MAX_IDLE);
            poolConfig.setMaxWaitMillis(WAIT);
            poolConfig.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(poolConfig, ADDRESS, PORT, TIMEOUT, AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取连接
     *
     * @return
     */
    public static Jedis getConn() {
        try {
            if (jedisPool != null) {
                Jedis jedis = jedisPool.getResource();
                return jedis;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 关闭连接
     * @param jedis
     */
    public static void closeJedis(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
