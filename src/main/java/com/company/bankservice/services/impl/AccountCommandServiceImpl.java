package com.company.bankservice.services.impl;

import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.User;
import com.company.bankservice.enums.AccountStatus;
import com.company.bankservice.enums.Currency;
import com.company.bankservice.events.UserEventKafkaProducer;
import com.company.bankservice.repositories.AccountMongoRepository;
import com.company.bankservice.services.AccountCommandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandServiceImpl implements AccountCommandService {

    @Autowired
    AccountMongoRepository accountMongoRepository;

    private static Logger log = LogManager.getLogger(AccountCommandServiceImpl.class);

    @Override
    public Account createFromBroker(User user) {
        try {
            Account account = new Account();
            account.setUser(user);
            account.setAccountStatus(AccountStatus.ENABLED);
            account.setCurrency(Currency.USD);
            account.setBalance(0.0F);

            Account res = accountMongoRepository.save(account);
            return res;
        } catch (Exception e) {
            log.debug("[reciever][createFromBroker] Cannot Create Account: ", e);
        }
        return null;
    }
}
