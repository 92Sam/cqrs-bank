package com.company.bankservice.services;

import com.company.bankservice.dto.resolvers.TransactionReqDTO;
import com.company.bankservice.dto.resolvers.TransactionResDTO;
import com.company.bankservice.entities.Account;

import java.util.List;

public interface TransactionCommandService {
    TransactionResDTO create(TransactionReqDTO transactionReq);

    List<TransactionResDTO> initializeAccount(Account account);

}
