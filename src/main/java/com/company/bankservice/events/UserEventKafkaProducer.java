package com.company.bankservice.events;

import com.company.bankservice.entities.Transaction;
import com.company.bankservice.entities.User;
import org.apache.kafka.common.KafkaException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class UserEventKafkaProducer {

    @Autowired
    @Qualifier("userTemplate")
    private KafkaTemplate<String, User> kafkaTemplate;

    private static Logger log = LogManager.getLogger(UserEventKafkaProducer.class);

    @Value(value = "${app.config.kafka.topics.users}")
    private String topic;

    public boolean sendMessage(User transactionMsg) {
        try {
            ListenableFuture<SendResult<String, User>> future = kafkaTemplate.send(topic, transactionMsg);

            future.addCallback(new ListenableFutureCallback<SendResult<String, User>>() {
                @Override
                public void onSuccess(SendResult<String, User> result) {
                    log.info("[send] Mensaje inyectado OK.");
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.error("[send][Throwable] Error al inyectar el mensaje.");
                    throw new KafkaException(ex);
                }
            });

            log.info("[send] offset mensaje: {}", future.get().getRecordMetadata().offset());
        } catch (Exception e) {
            log.debug("[send][InterruptedException] Mensaje no inyectado: ", e);
            return false;
        }

        return true;
    }
}