package com.e3mall.jedis;

import cn.e3mall.common.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Darwin on 2018/4/11.
 */
public class JedisClientTest {

    @Test
    public void testJedisClient() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        // 从容器中获取jedisClient对象
        JedisClient jedisClient = (JedisClient) context.getBean(JedisClient.class);
        jedisClient.set("test123", "hahahah");
        String test = jedisClient.get("test123");
        System.out.println(test);
    }
}
