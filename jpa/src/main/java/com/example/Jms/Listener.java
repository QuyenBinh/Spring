package com.example.Jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

// gửi nhận tin
@Component
public class Listener {

    @Autowired
    private Producer producer;

    @JmsListener(destination = "inbound.queue") //Định nghĩa tên queue mà bộ lắng nghe sẽ nhận tin nhắn từ đó.
    public void receiveMessage(Message message) throws JMSException    {
        String messageData = null;
        System.out.println("Nhận tin nhắn: "+ message);
        if(message instanceof TextMessage)  {
            TextMessage textMessage = (TextMessage) message;
            messageData = textMessage.getText();
        }
        producer.sendMessage("outbound.queue", messageData);
    }
}
