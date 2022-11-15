package com.company.bankservice.services;

import com.company.bankservice.dto.resolvers.TransactionResDTO;
import com.company.bankservice.dto.resolvers.TransactionsAccountHistoricReqDTO;
import com.company.bankservice.dto.resolvers.TransactionsHistoricReqDTO;

import java.util.List;

public interface TransactionQueryService {
    List<TransactionResDTO> getTransactionList();
    List<TransactionResDTO> getTransactionsByDateRange(TransactionsHistoricReqDTO transactionsHistoricReqDTO);
    List<TransactionResDTO> getTransactionsByAccountIdByDateRange(TransactionsAccountHistoricReqDTO transactionsAccountHistoricReqDTO);
}
