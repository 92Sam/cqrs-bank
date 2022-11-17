package com.company.bankservice.services.impl;

import com.company.bankservice.dto.events.UserCreateEventMessageDTO;
import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.enums.AccountStatus;
import com.company.bankservice.enums.CreditLine;
import com.company.bankservice.enums.Currency;
import com.company.bankservice.events.AccountKafkaProducerEvent;
import com.company.bankservice.repositories.AccountMongoRepository;
import com.company.bankservice.services.AccountCommandService;
import com.company.bankservice.utils.AccountUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            account.setAccountNumber(AccountUtils.generateString());
            account.setCreditLineId(CreditLine.CREDIT_BASIC);
            account.setCreditAmount(CreditLine.CREDIT_BASIC.getCreditValue());
            account.setCreditAvailable(CreditLine.CREDIT_BASIC.getCreditValue());
            account.setCurrency(user.getInitialDepositAccountDTO().getCurrency());
            account.setBalance(user.getInitialDepositAccountDTO().getAmount());
            account.setCreatedAt(LocalDateTime.now());

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
    public List<Account> getAccountsByUserId(String userId) {
        try {

            List<Account> res = accountMongoRepository.findAccountByUserId(userId);
            return res;

        } catch (Exception e) {
            log.error("[reciever][createFromBroker] Cannot Create Account: ", e);
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
    public Account updateAccountBalanceByTransaction(Transaction transaction) {

        Optional<Account> account = accountMongoRepository.findById(transaction.getAccountId());
        log.info("Account {}", account);
        log.info("Account {}", account.isEmpty());
        log.info(account.isEmpty());
        if (account.isEmpty()){
            log.info("Error the account not exist ");
            return null;
        }

        // Evaluate Credits
        Account accountBalance = AccountUtils.calculateBalanceAndCreditAvailable(account.get(), transaction);
        if (accountBalance == null){
            log.info("Not have balance enough");
            return null;
        }

        log.info("Transaction {}", accountBalance.toString());

        return this.updateAccountBalance(accountBalance);
    }

    private Account updateAccountBalance(Account account) {
        try {
            Account res = accountMongoRepository.save(account);
            return res;
        } catch (Exception e){
            log.error("[updateAccountBalance] Cannot update Account: ", e);
        }
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
            log.error("[getAccountByUserIdByCurrency] Cannot get Account: ", e);
        }
        return false;
    }
}
