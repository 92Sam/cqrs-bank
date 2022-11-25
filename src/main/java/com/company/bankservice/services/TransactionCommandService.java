package com.company.bankservice.services;

import com.company.bankservice.config.dto.resolvers.TransactionReqDTO;
import com.company.bankservice.config.dto.resolvers.TransactionResDTO;
import com.company.bankservice.entities.Account;

import java.util.List;

public interface TransactionCommandService {
    TransactionResDTO create(TransactionReqDTO transactionReq) throws Exception;

    List<TransactionResDTO> initializeAccount(Account account);

}
