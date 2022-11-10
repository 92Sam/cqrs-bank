package com.company.bankservice.services;

import com.company.bankservice.dto.TransactionReqDTO;
import com.company.bankservice.dto.TransactionResDTO;

import java.util.List;

public interface TransactionCommandService {
    TransactionResDTO create(TransactionReqDTO transactionReq);
}
