package com.company.bankservice.events;

import com.company.bankservice.projections.AccountQueryProjection;
import com.company.bankservice.projections.TransactionQueryProjection;
import com.company.bankservice.projections.UserQueryProjection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Getter;
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
public class MongoDBSinkKafkaConsumerEvent {

    @Getter
    private enum TableProjection {
        ACCOUNTS("accounts"),
        TRANSACTIONS("transactions"),
        USERS("users");

        private String value;
        TableProjection(String type) {
            this.value = type;
        }
    }

    @Autowired
    UserQueryProjection userQueryProjection;
    @Autowired
    AccountQueryProjection accountQueryProjection;
    @Autowired
    TransactionQueryProjection transactionQueryProjection;

    @Autowired
    Gson gson = new Gson();
    Logger log = LogManager.getLogger(MongoDBSinkKafkaConsumerEvent.class);

    @KafkaListener(
            topics = {"cqrs-bank-psql.public.users",
                    "cqrs-bank-psql.public.accounts",
                    "cqrs-bank-psql.public.transactions"},
            groupId = "${spring.kafka.group}",
            containerFactory = "sinkListenerContainerFactory"
    )
    public void listen(@Payload(required = false) String message, Acknowledgment acknowledgment) {
        if ( message == null ) return;

        log.info("[ETL listen] Message: {}", message);
        try {
            JsonObject messageData = gson.fromJson(message, JsonObject.class);
            JsonObject payload =  messageData.getAsJsonObject("payload");
            JsonObject source =  payload.getAsJsonObject("source");
            String operation =  payload.get("op").getAsString();
            String table =  source.get("table").getAsString();
            String nameQueue =  source.get("name").getAsString();

//            log.info("[ETL operation] Message: {}", operation);
//            log.info("[ETL table] Message: {}", table);
//            log.info("[ETL nameQueue] Message: {}", nameQueue);
//            log.info("[ETL payload] Message: {}", payload);

            if (table.equals(TableProjection.USERS.getValue())) {
                log.info("[ETL USERS] Message: {}", payload);
                userQueryProjection.projection(operation, payload);
            } else if (table.equals(TableProjection.ACCOUNTS.getValue())) {
                log.info("[ETL ACCOUNTS] Message: {}", payload);
                accountQueryProjection.projection(operation, payload);
            } else if (table.equals(TableProjection.TRANSACTIONS.getValue())) {
                log.info("[ETL TRANSACTIONS] Message: {}", payload);
                transactionQueryProjection.projection(operation, payload);
            }

            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("[User][Exception] Error on Message.", e);
        }
        log.info(message.toString());
    }
}

