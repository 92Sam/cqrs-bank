package com.company.bankservice.events;

import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.Transaction;
import org.apache.kafka.common.KafkaException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class AccountKafkaProducerEvent {

    @Autowired
    @Qualifier("accountTemplate")
    private KafkaTemplate<String, Account> kafkaTemplate;

    private static Logger log = LogManager.getLogger(AccountKafkaProducerEvent.class);

    @Value(value = "${app.config.kafka.topics.accounts}")
    private String topic;

    public boolean sendMessage(Account accountMsg) {
        try {
            ListenableFuture<SendResult<String, Account>> future = kafkaTemplate.send(topic, accountMsg);

            future.addCallback(new ListenableFutureCallback<SendResult<String, Account>>() {
                @Override
                public void onSuccess(SendResult<String, Account> result) {
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
