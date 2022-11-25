package com.company.bankservice.services.impl;

import com.company.bankservice.dto.events.UserCreateEventMessageDTO;
import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.entities.User;
import com.company.bankservice.enums.AccountStatus;
import com.company.bankservice.enums.CreditLine;
import com.company.bankservice.enums.Currency;
import com.company.bankservice.events.AccountKafkaProducerEvent;
import com.company.bankservice.mappers.UserMapper;
import com.company.bankservice.mappers.UserMapperImpl;
import com.company.bankservice.repositories.mongo.AccountMongoRepository;
import com.company.bankservice.repositories.pgsql.AccountPostgresRepository;
import com.company.bankservice.repositories.pgsql.UserPostgresRepository;
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
    AccountPostgresRepository accountPostgresRepository;

//    @Autowired
//    AccountMongoRepository accountMongoRepository;

    @Autowired
    AccountKafkaProducerEvent accountKafkaProducerEvent;

    private static Logger log = LogManager.getLogger(AccountCommandServiceImpl.class);

    @Override
    public Account createAccountFromUser(UserCreateEventMessageDTO user) {
        try {
            Account account = new Account();

            account.setUser(UserMapper.userMapper.userResDTOtoUser(user.getUserId()));
            account.setAccountStatus(AccountStatus.ENABLED);
            account.setAccountNumber(AccountUtils.generateString());
            account.setCreditLineId(CreditLine.CREDIT_BASIC);
            account.setCreditAmount(CreditLine.CREDIT_BASIC.getCreditValue());
            account.setCreditAvailable(CreditLine.CREDIT_BASIC.getCreditValue());
            account.setCurrency(user.getInitialDepositAccountDTO().getCurrency());
            account.setBalance(user.getInitialDepositAccountDTO().getAmount());

            // TODO: Projector
//            Account res = accountMongoRepository.save(account);

            Account res = accountPostgresRepository.save(account);

//

            //TODO: Removing
//            accountKafkaProducerEvent.sendMessage(res);

            return res;
        } catch (Exception e) {
            log.debug("[reciever][createAccountFromUser] Cannot Create Account: ", e);
        }
        return null;
    }

    // TODO: Moving Logic to Query ACCOUNT
//    @Override
//    public Account getUserBalance(String userId) {
//        return null;
//    }

    // TODO: Moving Logic to Query ACCOUNT
//    @Override
//    public List<Account> getAccountsByUserId(String userId) {
//        try {
//
////            List<Account> res = accountMongoRepository.findAccountByUserId(userId);
//            List<Account> res = accountPostgresRepository.findAccountByUserId(userId);
//
//            return res;
//
//        } catch (Exception e) {
//            log.error("[reciever][getAccountsByUserId] Cannot Create Account: ", e);
//        }
//        return null;
//    }

    // TODO: Moving Logic to Query ACCOUNT
//    @Override
//    public Account getAccountByUserIdByCurrency(String userId, Currency currency) {
//        try {
//
//            Account res = accountMongoRepository.findAccountByUserIdByCurrency(userId, currency);
////            Account res = accountPostgresRepository.findAccountByUserIdByCurrency(userId, currency.toString());
//
//            return res;
//        } catch (Exception e) {
//            log.debug("[getAccountByUserIdByCurrency] Cannot get Account: ", e);
//        }
//        return null;
//    }

    @Override
    public Account updateAccountBalanceByTransaction(Transaction transaction) {

//        Optional<Account> account = accountMongoRepository.findById(transaction.getAccount().getId().toString());
        try {
            log.info("updateAccountBalanceByTransaction() ");
            Optional<Account> account = accountPostgresRepository.findById(transaction.getAccount().getId());
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
        }catch (Error error){
            log.info("Transaction {}", error.toString());
        }
        return null;
    }

    private Account updateAccountBalance(Account account) {
        try {
//            Account res = accountMongoRepository.save(account);
            Account res = accountPostgresRepository.save(account);

            return res;
        } catch (Exception e){
            log.error("[updateAccountBalance] Cannot update Account: ", e);
        }
        return null;
    }

    @Override
    public Boolean verifyAccountCurrencyBalance(String accountId, Currency currency, Float amount) {
        try {
//            Optional<Account> res = accountMongoRepository.findById(accountId);
            Optional<Account> res = accountPostgresRepository.findById(accountId);

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
