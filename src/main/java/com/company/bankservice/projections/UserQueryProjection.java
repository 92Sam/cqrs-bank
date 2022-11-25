package com.company.bankservice.projections;

import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.entities.User;
import com.company.bankservice.enums.OperationType;
import com.company.bankservice.enums.TransactionType;
import com.company.bankservice.repositories.mongo.UserMongoRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class UserQueryProjection {

    @Autowired
    UserMongoRepository userMongoRepository;

    private Logger log = LogManager.getLogger(UserQueryProjection.class);

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void projection(String operation, JsonObject payload){
        if (operation.equals(OperationType.CREATE.getValue())) {
            createUser(mapperProjection(payload.getAsJsonObject("after")));
        } else if (operation.equals(OperationType.UPDATE.getValue())) {
            updateUser(mapperProjection(payload.getAsJsonObject("after")));
        } else if (operation.equals(OperationType.DELETE.getValue())) {
            deleteUser(mapperProjection(payload.getAsJsonObject("before")));
        }
    }

    private User mapperProjection(JsonObject data) {
        User user = new User();
        user.setId(UUID.fromString(data.get("id").getAsString()));
        user.setName(data.get("name").getAsString());
        user.setEmail(data.get("email").getAsString());
        user.setPassword(data.get("password").getAsString());
        user.setCreatedAt(new Date(data.get("created_at").getAsLong()));
        user.setUpdatedAt(new Date(data.get("updated_at").getAsLong()));
        return user;
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
