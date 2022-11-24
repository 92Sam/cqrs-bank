package com.company.bankservice.services;

import com.company.bankservice.dto.events.UserCreateEventMessageDTO;
import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.entities.User;
import com.company.bankservice.enums.Currency;

import java.util.List;

public interface AccountCommandService {
    Account createAccountFromUser(UserCreateEventMessageDTO userCreateEventMessageDTO);

//    Account getUserBalance(String userId);

//    List<Account> getAccountsByUserId(String userId);

//    Account getAccountByUserIdByCurrency(String userId, Currency currency);

    Account updateAccountBalanceByTransaction(Transaction transaction);

    Boolean verifyAccountCurrencyBalance(String accountId, Currency currency, Float amount);
}
