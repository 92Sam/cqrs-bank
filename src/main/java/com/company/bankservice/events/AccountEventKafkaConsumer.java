package com.company.bankservice.events;

import com.company.bankservice.entities.Transaction;
import com.company.bankservice.entities.User;
import com.company.bankservice.services.impl.AccountCommandServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@EnableKafka
@Service
public class AccountEventKafkaConsumer {

    @Autowired
    AccountCommandServiceImpl accountCommandServiceImpl;

    Logger log = LogManager.getLogger(AccountEventKafkaConsumer.class);

    @KafkaListener(
            topics = "${app.config.kafka.topics.users}",
            groupId = "${app.config.kafka.group}",
            containerFactory = "userListenerContainerFactory"
    )
    public void listen(@Payload User message, Acknowledgment acknowledgment) {
        if ( message == null ) return;
        log.info("[User] Message: {}", message.getId());
        try {
            accountCommandServiceImpl.createFromBroker(message);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("[User][Exception] Error on Message.", e);
        }
        log.info(message.toString());
    }
}
