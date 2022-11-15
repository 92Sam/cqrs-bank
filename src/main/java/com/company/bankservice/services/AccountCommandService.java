package com.company.bankservice.services;

import com.company.bankservice.dto.events.UserCreateEventMessageDTO;
import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.User;
import com.company.bankservice.enums.Currency;

import java.util.List;

public interface AccountCommandService {
    Account createAccountFromBroker(UserCreateEventMessageDTO userCreateEventMessageDTO);

    Account getUserBalance(String userId);

    List<Account> getAccountByUserId(String userId);

    Account getAccountByUserIdByCurrency(String userId, Currency currency);

    Account updateAccountBalance(String userId);

    Boolean verifyAccountCurrencyBalance(String accountId, Currency currency, Float amount);
}
