package com.company.bankservice.config;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.company.bankservice.entities.User;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.company.bankservice.entities.Transaction;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

    /**
     * Acceso a variables de ambiente.
     */
    @Autowired
    private Environment env;

    @Bean
    public ProducerFactory<String, Transaction> sinkProducerFactory() {
        return new DefaultKafkaProducerFactory<>(configProps());
    }

    @Bean
    public KafkaTemplate<String, Transaction> sinkTemplate() {
        return new KafkaTemplate<>(sinkProducerFactory());
    }

    @Bean
    public ProducerFactory<String, User> userProducerFactory() {
        return new DefaultKafkaProducerFactory<>(configProps());
    }

    @Bean
    public KafkaTemplate<String, User> userTemplate() {
        return new KafkaTemplate<String, User>(userProducerFactory());
    }

    /**
     * Genera los valores de configuracion para inyectar mensajes en el topico kafka.
     *
     * @return Mapa con valores de configuracion.
     */
    private Map<String, Object> configProps() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("app.config.kafka.server"));
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 3000);
        configProps.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 3000);
        configProps.put(ProducerConfig.RETRIES_CONFIG, Integer.valueOf(env.getProperty("app.config.kafka.retries")));
//        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, Integer.valueOf(env.getProperty("app.config.kafka.retry.backoff")));
        return configProps;
    }
}
