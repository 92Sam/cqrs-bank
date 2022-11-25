package com.company.bankservice.projections;

import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.enums.OperationType;
import com.company.bankservice.repositories.mongo.AccountMongoRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountQueryProjection {

    @Autowired
    AccountMongoRepository accountMongoRepository;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Logger log = LogManager.getLogger(AccountQueryProjection.class);

    public void projection(String operation, JsonObject payload){
        log.info("[ETL AccountQueryProjection] Message: {}", operation);
        log.info("[ETL AccountQueryProjection] Message: {}", payload);
        if (operation.equals(OperationType.CREATE.getValue())) {
            createAccount(gson.fromJson(payload.get("after"), Account.class));
        } else if (operation.equals(OperationType.UPDATE.getValue())) {
            updateAccount(gson.fromJson(payload.get("after"), Account.class));
        } else if (operation.equals(OperationType.DELETE.getValue())) {
            deleteAccount(gson.fromJson(payload.get("before"), Account.class));
        }
    }

    private void createAccount(Account account) {
        try {
            accountMongoRepository.save(account);
        }catch (Error error){
            log.error(error);
        }
    }

    private void updateAccount(Account account) {
        try {
            accountMongoRepository.save(account);
        }catch (Error error){
            log.error(error);
        }
    }

    private void deleteAccount(Account account) {
        try {
            accountMongoRepository.deleteById(account.getId().toString());
        }catch (Error error){
            log.error(error);
        }
    }
}
