package com.company.bankservice.config;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.company.bankservice.entities.Transaction;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    /**
     * Enviroments.
     */
    @Autowired
    private Environment env;

    public ConsumerFactory<String, Transaction> sinkConsumerFactory() {
        JsonDeserializer<Transaction> deserialize = new JsonDeserializer<>(Transaction.class,false); 
        deserialize.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(this.configProps(), new StringDeserializer(), deserialize);
    }

    private Map<String, Object> configProps() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("app.config.kafka.server"));
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, env.getProperty("app.config.kafka.group"));
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        configProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, Integer.valueOf(env.getProperty("app.config.kafka.max.poll")));
        configProps.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, Integer.valueOf(env.getProperty("app.config.kafka.max.interval")));
        return configProps;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Transaction> sinkListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Transaction> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConcurrency(10);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.getContainerProperties().setSyncCommits(true);
        factory.setConsumerFactory(sinkConsumerFactory());
        return factory;
    }
}
