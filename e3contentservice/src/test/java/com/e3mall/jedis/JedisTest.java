package com.e3mall.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Darwin on 2018/4/11.
 */
public class JedisTest {

    @Test
    public void testJedis() throws Exception {
        // 创建一个连接jedis对象, 参数host, port
        Jedis jedis = new Jedis("192.168.25.130", 6379);
        // 直接使用jedis操作redis
        jedis.set("test123", "my first jedis test!");
        String test123 = jedis.get("test123");
        System.out.println(test123);
        // 关闭连接
        jedis.close();
    }

    @Test
    public void testJedisPool() throws Exception {

        // 创建一个连接池对象
        JedisPool jedisPool = new JedisPool("192.168.25.130", 6379);
        // 从连接池中获取一个jedis对象
        Jedis jedis = jedisPool.getResource();
        // 使用jedis操作redis
        String test123 = jedis.get("test123");
        System.out.println(test123);
        // 关闭连接, 每次使用后关闭连接, 连接池回收资源
        jedis.close();
        // 关闭连接池
        jedisPool.close();
    }


    @Test
    public void testJedisCluster() {
        // 创建一个JedisCluster对象, 有一个参数nodes是一个set对象, set中包含若干个HostAndPort对象
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.130", 7001));
        nodes.add(new HostAndPort("192.168.25.130", 7002));
        nodes.add(new HostAndPort("192.168.25.130", 7003));
        nodes.add(new HostAndPort("192.168.25.130", 7004));
        nodes.add(new HostAndPort("192.168.25.130", 7005));
        nodes.add(new HostAndPort("192.168.25.130", 7006));
        JedisCluster cluster = new JedisCluster(nodes);
        // 直接使用Cluster获取操作redis
        cluster.set("test", "123");
        String test = cluster.get("test");
        System.out.println(test);
        // 关闭jedisCluster
        cluster.close();

    }
}
