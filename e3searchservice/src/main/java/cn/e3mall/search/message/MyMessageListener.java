package cn.e3mall.search.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author Darwin
 * @date 2018/4/18
 */
public class MyMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        // get message content.
        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();
            System.out.println(text);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
