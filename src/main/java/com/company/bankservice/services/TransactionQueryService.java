package com.company.bankservice.services;

import com.company.bankservice.config.dto.resolvers.TransactionResDTO;
import com.company.bankservice.config.dto.resolvers.TransactionsAccountHistoricReqDTO;
import com.company.bankservice.config.dto.resolvers.TransactionsHistoricReqDTO;

import java.util.List;

public interface TransactionQueryService {
    List<TransactionResDTO> getTransactionList();
    List<TransactionResDTO> getTransactionsByDateRange(TransactionsHistoricReqDTO transactionsHistoricReqDTO);
    List<TransactionResDTO> getTransactionsByAccountIdByDateRange(TransactionsAccountHistoricReqDTO transactionsAccountHistoricReqDTO);
}
