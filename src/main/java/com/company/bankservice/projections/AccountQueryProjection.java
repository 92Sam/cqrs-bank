package com.company.bankservice.projections;

import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.User;
import com.company.bankservice.enums.AccountStatus;
import com.company.bankservice.enums.CreditLine;
import com.company.bankservice.enums.Currency;
import com.company.bankservice.enums.OperationType;
import com.company.bankservice.repositories.mongo.AccountMongoRepository;
import com.company.bankservice.utils.DateTimeUtils;
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
public class AccountQueryProjection {

    @Autowired
    AccountMongoRepository accountMongoRepository;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Logger log = LogManager.getLogger(AccountQueryProjection.class);

    public void projection(String operation, JsonObject payload){
        if (operation.equals(OperationType.CREATE.getValue())) {
            createAccount(mapperProjection(payload.getAsJsonObject("after")));
        } else if (operation.equals(OperationType.UPDATE.getValue())) {
            updateAccount(mapperProjection(payload.getAsJsonObject("after")));
        } else if (operation.equals(OperationType.DELETE.getValue())) {
            deleteAccount(mapperProjection(payload.getAsJsonObject("before")));
        }
    }

    private Account mapperProjection(JsonObject data) {
        User user = new User();
        user.setId(UUID.fromString(data.get("user_id").getAsString()));

        Account account = new Account();
        account.setUser(user);
        account.setId(UUID.fromString(data.get("id").getAsString()));
        account.setAccountNumber(data.get("account_number").getAsString());
        account.setAccountStatus(AccountStatus.valueOf(data.get("account_status").getAsString()));
        account.setBalance(data.get("balance").getAsFloat());
        account.setCreditAmount(data.get("credit_amount").getAsFloat());
        account.setCreditAvailable(data.get("credit_available").getAsFloat());
        account.setCreditLineId(CreditLine.valueOf(data.get("credit_line_id").getAsString()));
        account.setCurrency(Currency.valueOf(data.get("currency").getAsString()));
        account.setUpdatedAt(DateTimeUtils.unixToDate(data.get("updated_at").getAsLong()));
        account.setCreatedAt(DateTimeUtils.unixToDate(data.get("created_at").getAsLong()));
        return account;
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
