package com.company.bankservice.events;

import com.company.bankservice.services.impl.AccountCommandServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

    private enum ActionTypes {
        UPDATE("u"),
        CREATE("c"),
        DELETE("d");

        private String toString;
        ActionTypes(String type) {
            this.toString = type;
        }
    }

    @Autowired
    AccountCommandServiceImpl accountCommandServiceImpl;
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

            log.info("[ETL operation] Message: {}", operation);
            log.info("[ETL table] Message: {}", table);
            log.info("[ETL nameQueue] Message: {}", nameQueue);
            log.info("[ETL payload] Message: {}", payload);

            if (operation.equals(ActionTypes.UPDATE.toString)) {
//                messageBrokerInputPort.updateReg(table, (Map<String, Object>) payload.get("after"));
            } else if (operation.equals(ActionTypes.CREATE.toString)) {
                log.info("[ETL payload] Message: {}", payload);
//                messageBrokerInputPort.insertReg(table, (Map<String, Object>) payload.get("after"));
            } else if (operation.equals(ActionTypes.CREATE.toString)) {
//                messageBrokerInputPort.deleteReg(table, (Map<String, Object>) payload.get("before"));
            }

//            accountCommandServiceImpl.createAccountFromBroker(message);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("[User][Exception] Error on Message.", e);
        }
        log.info(message.toString());
    }
}

