package com.company.bankservice.services.impl;

import com.company.bankservice.dto.resolvers.AccountResDTO;
import com.company.bankservice.dto.resolvers.TransactionResDTO;
import com.company.bankservice.entities.Account;
import com.company.bankservice.repositories.AccountMongoRepository;
import com.company.bankservice.services.AccountQueryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountQueryServiceImpl implements AccountQueryService {

    @Autowired
    AccountMongoRepository accountMongoRepository;

    private static Logger log = LogManager.getLogger(AccountQueryServiceImpl.class);

    @Override
    public List<AccountResDTO> getAccountsUser(String userId) {
        try {
            List<Account> accountList = accountMongoRepository.findAccountByUserId(userId);

            if (accountList.stream().count() <= 0) {
                return null;
            }

            return accountList.stream().map(account -> {
                AccountResDTO accountResDTO = new AccountResDTO();
                accountResDTO.setId(account.getId());
                accountResDTO.setUserId(account.getUserId());
                accountResDTO.setAccountNumber(account.getAccountNumber());
                accountResDTO.setAccountStatus(account.getAccountStatus());
                accountResDTO.setCurrency(account.getCurrency());
                accountResDTO.setBalance(account.getBalance());
                accountResDTO.setCreditAvailable(account.getCreditAvailable());
                accountResDTO.setCreditAmount(account.getCreditAmount());
                accountResDTO.setCreditLineId(account.getCreditLineId());
                accountResDTO.setCreatedAt(account.getCreatedAt());

                return accountResDTO;
            }).collect(Collectors.toList());

        }catch (Error error){
            log.error("Error on getAccountsOverdraft - {}", error);
        }
        return null;
    }

    @Override
    public List<AccountResDTO> getAccountsOverdraft() {
        try {

            log.info("Data getAccountsOverdraft");
            List<Account> accountList = accountMongoRepository.getAccountsOverdraft();
            log.info("Data getAccountsOverdraft {}", accountList);
            if (accountList.stream().count() <= 0) {
                return null;
            }

            return accountList.stream().map(account -> {
                AccountResDTO accountResDTO = new AccountResDTO();
                accountResDTO.setId(account.getId());
                accountResDTO.setUserId(account.getUserId());
                accountResDTO.setAccountNumber(account.getAccountNumber());
                accountResDTO.setAccountStatus(account.getAccountStatus());
                accountResDTO.setCurrency(account.getCurrency());
                accountResDTO.setBalance(account.getBalance());
                accountResDTO.setCreditAvailable(account.getCreditAvailable());
                accountResDTO.setCreditAmount(account.getCreditAmount());
                accountResDTO.setCreditLineId(account.getCreditLineId());
                accountResDTO.setCreatedAt(account.getCreatedAt());

                return accountResDTO;
            }).collect(Collectors.toList());

        }catch (Error error){
            log.error("Error on getAccountsOverdraft - {}", error);
        }
        return null;
    }
}
