package cn.e3mall.publish;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Darwin on 2018/4/9.
 */
public class TestPublish {

    @Test
    public void publicService() throws InterruptedException, IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        System.out.println("Service has already started!");
        System.in.read();
        System.out.println("Service has already stopped!");
    }
}
