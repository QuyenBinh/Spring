package com.example.config;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class jmsconfig {
    private static final String BROKE_URL = "tcp://localhost:61616";
    private static final String BROKE_USERNAME = "admin";
    private static final String BROKE_PASSWORD = "admin";
    
    @Bean
    public ActiveMQConnectionFactory connectionFactory()    {

        ActiveMQConnectionFactory aConnectionFactory = new ActiveMQConnectionFactory();
        aConnectionFactory.setBrokerURL(BROKE_URL);
        aConnectionFactory.setUserName(BROKE_USERNAME);
        aConnectionFactory.setPassword(BROKE_PASSWORD);
        return aConnectionFactory; 

    }
    @Bean
    public JmsTemplate jmsTemplate()    {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        return jmsTemplate;
    }
    @Bean 
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {

        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setConnectionFactory(connectionFactory());
        defaultJmsListenerContainerFactory.setConcurrency("1-1");
        defaultJmsListenerContainerFactory.setPubSubDomain(false);
        return defaultJmsListenerContainerFactory;
        // gửi nhận tin?
    }
}
