package com.company.bankservice.projections;

import com.company.bankservice.entities.Transaction;
import com.company.bankservice.enums.OperationType;
import com.company.bankservice.repositories.mongo.TransactionMongoRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionQueryProjection {

    @Autowired
    TransactionMongoRepository transactionMongoRepository;

    private Logger log = LogManager.getLogger(TransactionQueryProjection.class);
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void projection(String operation, JsonObject payload){
        log.info("[ETL TransactionQueryProjection] Message: {}", operation);
        log.info("[ETL TransactionQueryProjection] Message: {}", payload);
        if (operation.equals(OperationType.CREATE.getValue())) {
            createTransaction(gson.fromJson(payload.get("after"), Transaction.class));
        } else if (operation.equals(OperationType.UPDATE.getValue())) {
            updateTransaction(gson.fromJson(payload.get("after"), Transaction.class));
        } else if (operation.equals(OperationType.DELETE.getValue())) {
            deleteTransaction(gson.fromJson(payload.get("before"), Transaction.class));
        }
    }

    private void createTransaction(Transaction transaction) {
        try {
            transactionMongoRepository.save(transaction);
        }catch (Error error){
            log.error(error);
        }
    }

    private void updateTransaction(Transaction transaction) {
        try {
            transactionMongoRepository.save(transaction);
        }catch (Error error){
            log.error(error);
        }
    }

    private void deleteTransaction(Transaction transaction) {
        try {
            transactionMongoRepository.deleteById(transaction.getId().toString());
        }catch (Error error){
            log.error(error);
        }
    }
}
