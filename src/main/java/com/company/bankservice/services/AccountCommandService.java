package com.company.bankservice.services;

import com.company.bankservice.config.dto.events.UserCreateEventMessageDTO;
import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.enums.Currency;

public interface AccountCommandService {
    Account createAccountFromUser(UserCreateEventMessageDTO userCreateEventMessageDTO);

    Account updateAccountBalanceByTransaction(Transaction transaction);

    Boolean verifyAccountCurrencyBalance(String accountId, Currency currency, Float amount);
}
