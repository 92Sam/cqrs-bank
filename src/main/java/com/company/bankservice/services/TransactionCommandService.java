package com.company.bankservice.services;

import com.company.bankservice.dto.resolvers.TransactionReqDTO;
import com.company.bankservice.dto.resolvers.TransactionResDTO;
import com.company.bankservice.entities.Account;

public interface TransactionCommandService {
    TransactionResDTO create(TransactionReqDTO transactionReq);

    TransactionResDTO initializeAccount(Account account);

}
