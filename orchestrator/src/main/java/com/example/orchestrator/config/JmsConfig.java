package com.example.orchestrator.config;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
@EnableJms
public class JmsConfig {
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();

        jmsListenerContainerFactory.setConnectionFactory(connectionFactory);
        jmsListenerContainerFactory.setConcurrency("5-10");

        return jmsListenerContainerFactory;
    }

    @Bean
    @ConditionalOnProperty(name = "spring.activemq.broker-url", matchIfMissing = false)
    public ActiveMQConnectionFactory connectionFactory(@Value("${spring.activemq.broker-url}") final String activeMqUrl,
                                                       @Value("${spring.activemq.username:artemis}") final String username,
                                                       @Value("${spring.activemq.password:artemis}") final String password){
        return new ActiveMQConnectionFactory(activeMqUrl, username, password);
    }
}
