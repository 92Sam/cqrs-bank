package com.company.bankservice.projections;

import com.company.bankservice.entities.User;
import com.company.bankservice.enums.OperationType;
import com.company.bankservice.repositories.mongo.UserMongoRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserQueryProjection {

    @Autowired
    UserMongoRepository userMongoRepository;

    private Logger log = LogManager.getLogger(UserQueryProjection.class);

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void projection(String operation, JsonObject payload){
        log.info("[ETL UserQueryProjection] Message: {}", operation);
        log.info("[ETL UserQueryProjection] Message: {}", payload);
        if (operation.equals(OperationType.CREATE.getValue())) {
            createUser(gson.fromJson(payload.get("after"), User.class));
        } else if (operation.equals(OperationType.UPDATE.getValue())) {
            updateUser(gson.fromJson(payload.get("after"), User.class));
        } else if (operation.equals(OperationType.DELETE.getValue())) {
            deleteUser(gson.fromJson(payload.get("before"), User.class));
        }
    }

    private void createUser(User user) {
        try {
            userMongoRepository.save(user);
        }catch (Error error){
            log.error(error);
        }
    }

    private void updateUser(User user) {
        try {
            userMongoRepository.save(user);
        }catch (Error error){
            log.error(error);
        }
    }

    private void deleteUser(User user) {
        try {
            userMongoRepository.deleteById(user.getId().toString());
        }catch (Error error){
            log.error(error);
        }
    }
}
