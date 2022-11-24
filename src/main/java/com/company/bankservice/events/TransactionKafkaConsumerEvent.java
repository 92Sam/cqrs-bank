package com.company.bankservice.events;

import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.services.impl.TransactionCommandServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//@EnableKafka
//@Service
public class TransactionKafkaConsumerEvent {

    @Autowired
    TransactionCommandServiceImpl transactionCommandServiceImpl;

    Logger log = LogManager.getLogger(TransactionKafkaConsumerEvent.class);

    @KafkaListener(
            topics = "${spring.kafka.topics.transactions}",
            groupId = "${spring.kafka.group}",
            containerFactory = "transactionListenerContainerFactory"
    )
    public void listenTransactions(@Payload Transaction message, Acknowledgment acknowledgment) {
        if ( message == null ) return;
        log.info("[Transaction] Message: {}", message.getId());
        try {
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("[Transaction][Exception] Error on Message.", e);
        }
        log.info(message.toString());
    }


    @KafkaListener(
            topics = "${spring.kafka.topics.accounts}",
            groupId = "${spring.kafka.group}",
            containerFactory = "accountListenerContainerFactory"
    )
    public void listenAccountsCreateTransactions(@Payload Account message, Acknowledgment acknowledgment) {
        if ( message == null ) return;
        log.info("[Account] Message: {}", message.getId());
        try {
            transactionCommandServiceImpl.initializeAccount(message);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("[Account][Exception] Error on Message.", e);
        }
        log.info(message.toString());
    }
}
