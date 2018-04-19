package cn.e3mall.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * Created by Darwin on 2018/4/18.
 */
public class ActiveMqTest {

    /**
     * 点到点形式发送消息
     */
    @Test
    public void testQueueProducer() throws JMSException {
        //1. establish connection factory instance. it need to specify the and IP for the service
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
        //2. Using factory to create a Connection instance.
        Connection connection = factory.createConnection();
        //3. Open connection. evoke connection's start method.
        connection.start();
        //4. Create a Session instance.
        // 1st arg: Whether to open transaction. In general, Do not open transaction.
        // if you turn on(true) it. the 2sd arg is meaningless.
        // 2nd arg: Answer mode. Auto-answer or manual-acknowledge. Normally auto-acknowledge.
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5. Use Session instance to create a Destination instance. Two forms of queue and topic. There should be a queue. now.
        Queue queue = session.createQueue("test-queue");
        //6. Use Session instance to create a Producer instance.
        MessageProducer producer = session.createProducer(queue);
        //7. Create a Message instance. There should be a TextMessage instance.
        /*TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("hello activemq!");*/
        TextMessage textMessage = session.createTextMessage("Hello ActiveMq!");
        //8. Send Message.
        producer.send(textMessage);
        //9. Close Resources.
        producer.close();
        session.close();
        connection.close();

    }

    @Test
    public void testQueueConsumer() throws JMSException, IOException {
        //1. establish connection factory instance. it need to specify the and IP for the service
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
        //2. Using factory to create a Connection instance.
        Connection connection = factory.createConnection();
        //3. Open connection. evoke connection's start method.
        connection.start();
        //4. Create a Session instance.
        // 1st arg: Whether to open transaction. In general, Do not open transaction.
        // if you turn on(true) it. the 2sd arg is meaningless.
        // 2nd arg: Answer mode. Auto-answer or manual-acknowledge. Normally auto-acknowledge.
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5. Use Session instance to create a Destination instance. Two forms of queue and topic. There should be a queue. now.
        Queue queue = session.createQueue("spring-queue");
        //6. Use Session instance to create a Consumer instance.
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                // Print result
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //7. Receive Message.
        System.in.read();

        //9. Close Resources.
        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicProducer() throws Exception {
        //1. establish connection factory instance. it need to specify the and IP for the service
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
        //2. Using factory to create a Connection instance.
        Connection connection = factory.createConnection();
        //3. Open connection. evoke connection's start method.
        connection.start();
        //4. Create a Session instance.
        // 1st arg: Whether to open transaction. In general, Do not open transaction.
        // if you turn on(true) it. the 2sd arg is meaningless.
        // 2nd arg: Answer mode. Auto-answer or manual-acknowledge. Normally auto-acknowledge.
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5. Use Session instance to create a Destination instance. Two forms of queue and topic. There should be a queue. now.
        Topic topic = session.createTopic("test-queue");
        //6. Use Session instance to create a Producer instance.
        MessageProducer producer = session.createProducer(topic);
        //7. Create a Message instance. There should be a TextMessage instance.
        /*TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("hello activemq!");*/
        TextMessage textMessage = session.createTextMessage("Topic Message!");
        //8. Send Message.
        producer.send(textMessage);
        //9. Close Resources.
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicConsumer() throws JMSException, IOException {
        //1. establish connection factory instance. it need to specify the and IP for the service
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
        //2. Using factory to create a Connection instance.
        Connection connection = factory.createConnection();
        //3. Open connection. evoke connection's start method.
        connection.start();
        //4. Create a Session instance.
        // 1st arg: Whether to open transaction. In general, Do not open transaction.
        // if you turn on(true) it. the 2sd arg is meaningless.
        // 2nd arg: Answer mode. Auto-answer or manual-acknowledge. Normally auto-acknowledge.
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5. Use Session instance to create a Destination instance. Two forms of queue and topic. There should be a queue. now.
        Topic topic = session.createTopic("test-queue");
        //6. Use Session instance to create a Consumer instance.
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                // Print result
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //7. Receive Message.
        System.out.println("topic消费者3启动....");
        System.in.read();

        //9. Close Resources.
        consumer.close();
        session.close();
        connection.close();
    }

}

