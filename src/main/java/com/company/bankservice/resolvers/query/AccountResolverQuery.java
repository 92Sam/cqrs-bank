package com.company.bankservice.resolvers.query;

import com.company.bankservice.dto.resolvers.AccountResDTO;
import com.company.bankservice.services.impl.AccountQueryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AccountResolverQuery {
    @Autowired
    private AccountQueryServiceImpl accountQueryServiceImpl;

    private static Logger log = LogManager.getLogger(AccountResolverQuery.class);

    @QueryMapping
    public List<AccountResDTO> getAccountsUser(@Argument("userId") String userId) {
        try {
            return accountQueryServiceImpl.getAccountsUser(userId);
        }catch (Error error){
            log.error("Error on getAccountsUser - {}", error);
        }
        return null;
    }

    @QueryMapping
    public List<AccountResDTO> getAccountsOverdraft() {
        try {
            return accountQueryServiceImpl.getAccountsOverdraft();
        }catch (Error error){
            log.error("Error on getAccountsOverdraft - {}", error);
        }
        return null;
    }
}

