package com.company.bankservice.services.impl;

import com.company.bankservice.dto.events.UserCreateEventMessageDTO;
import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.User;
import com.company.bankservice.enums.AccountStatus;
import com.company.bankservice.enums.CreditLine;
import com.company.bankservice.enums.Currency;
import com.company.bankservice.events.AccountKafkaProducerEvent;
import com.company.bankservice.repositories.AccountMongoRepository;
import com.company.bankservice.services.AccountCommandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountCommandServiceImpl implements AccountCommandService {

    @Autowired
    AccountMongoRepository accountMongoRepository;

    @Autowired
    AccountKafkaProducerEvent accountKafkaProducerEvent;

    private static Logger log = LogManager.getLogger(AccountCommandServiceImpl.class);

    @Override
    public Account createAccountFromBroker(UserCreateEventMessageDTO user) {
        try {
            Account account = new Account();
            account.setUserId(user.getUserId());
            account.setAccountStatus(AccountStatus.ENABLED);
            account.setCreditLineId(CreditLine.CREDIT_BASIC);
            account.setCreditAmount(CreditLine.CREDIT_BASIC.getCreditValue());
            account.setCreditAvailable(CreditLine.CREDIT_BASIC.getCreditValue());
            account.setCurrency(user.getInitialDepositAccountDTO().getCurrency());
            account.setBalance(user.getInitialDepositAccountDTO().getAmount());

            Account res = accountMongoRepository.save(account);

            accountKafkaProducerEvent.sendMessage(res);

            return res;
        } catch (Exception e) {
            log.debug("[reciever][createFromBroker] Cannot Create Account: ", e);
        }
        return null;
    }

    @Override
    public Account getUserBalance(String userId) {
        return null;
    }

    @Override
    public List<Account> getAccountByUserId(String userId) {
        try {

            List<Account> res = accountMongoRepository.findAccountByUserId(userId);
            return res;

        } catch (Exception e) {
            log.debug("[reciever][createFromBroker] Cannot Create Account: ", e);
        }
        return null;
    }

    @Override
    public Account getAccountByUserIdByCurrency(String userId, Currency currency) {
        try {

            Account res = accountMongoRepository.findAccountByUserIdByCurrency(userId, currency);
            return res;

        } catch (Exception e) {
            log.debug("[getAccountByUserIdByCurrency] Cannot get Account: ", e);
        }
        return null;
    }

    @Override
    public Account updateAccountBalance(String userId) {
        return null;
    }

    @Override
    public Boolean verifyAccountCurrencyBalance(String accountId, Currency currency, Float amount) {
        try {

            Optional<Account> res = accountMongoRepository.findById(accountId);
            Float totalAvailable =  res.get().getBalance()+res.get().getCreditAvailable();
            if(totalAvailable>=amount){
                return true;
            }

        } catch (Exception e) {
            log.debug("[getAccountByUserIdByCurrency] Cannot get Account: ", e);
        }
        return false;
    }
}
