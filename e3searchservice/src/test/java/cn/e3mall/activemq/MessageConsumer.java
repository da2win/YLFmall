package cn.e3mall.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Darwin on 2018/4/18.
 */
public class MessageConsumer {

    @Test
    public void msgConsumer() throws IOException {
        // Initialize spring consumer.
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        // wait...
        System.in.read();


    }
}
