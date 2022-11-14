package com.company.bankservice.events;

import com.company.bankservice.entities.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@EnableKafka
@Service
public class TransactionEventKafkaConsumer {

    Logger log = LogManager.getLogger(TransactionEventKafkaConsumer.class);

    @KafkaListener(
            topics = "${app.config.kafka.topics.transactions}",
            groupId = "${app.config.kafka.group}",
            containerFactory = "transactionListenerContainerFactory"
    )
    public void listen(@Payload Transaction message, Acknowledgment acknowledgment) {
        if ( message == null ) return;
        log.info("[Transaction] Message: {}", message.getId());
        try {
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("[Transaction][Exception] Error on Message.", e);
        }
        log.info(message.toString());
    }
}
