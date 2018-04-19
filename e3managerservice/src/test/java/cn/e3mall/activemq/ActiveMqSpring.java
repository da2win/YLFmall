package cn.e3mall.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by Darwin on 2018/4/18.
 */
public class ActiveMqSpring {

    @Test
    public void sendMessage() throws Exception {
        // Initialize the spring context.
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        // get JmsTemplate instance from context.
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        // Get Destination instance front context.
        Destination destination = (Destination) context.getBean("queueDestination");
        // Send Message
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                return session.createTextMessage("send active message");
            }
        });
    }

}
