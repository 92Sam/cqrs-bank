package com.company.bankservice.services.impl;

import com.company.bankservice.dto.events.UserCreateEventMessageDTO;
import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.enums.AccountStatus;
import com.company.bankservice.enums.CreditLine;
import com.company.bankservice.enums.Currency;
import com.company.bankservice.mappers.UserMapper;
import com.company.bankservice.repositories.pgsql.AccountPostgresRepository;
import com.company.bankservice.services.AccountCommandService;
import com.company.bankservice.utils.AccountUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountCommandServiceImpl implements AccountCommandService {

    @Autowired
    AccountPostgresRepository accountPostgresRepository;

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

            Account res = accountPostgresRepository.save(account);

            return res;
        } catch (Exception e) {
            log.debug("[reciever][createAccountFromUser] Cannot Create Account: ", e);
        }
        return null;
    }


    @Override
    public Account updateAccountBalanceByTransaction(Transaction transaction) {

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
