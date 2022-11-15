package com.company.bankservice.resolvers.query;

import com.company.bankservice.dto.resolvers.TransactionResDTO;
import com.company.bankservice.dto.resolvers.TransactionsAccountHistoricReqDTO;
import com.company.bankservice.dto.resolvers.TransactionsHistoricReqDTO;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.services.impl.TransactionQueryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class TransactionResolverQuery {
    @Autowired
    private TransactionQueryServiceImpl transactionServiceImpl;

    private static Logger log = LogManager.getLogger(TransactionResolverQuery.class);

    @QueryMapping
    public  List<TransactionResDTO> getTransactionList()
    {
        List<TransactionResDTO> transactions = transactionServiceImpl.getTransactionList();
        return transactions;
    }

    @QueryMapping
    public List<TransactionResDTO> getTransactionsByDateRange(@Argument("input") TransactionsHistoricReqDTO transactionsHistoricReqDTO) {
        List<TransactionResDTO> transactions = transactionServiceImpl.getTransactionsByDateRange(transactionsHistoricReqDTO);
        return transactions;
    }

    @QueryMapping
    public List<TransactionResDTO> getTransactionsByAccountIdByDateRange(@Argument("input") TransactionsAccountHistoricReqDTO transactionsAccountHistoricReqDTO) {
        List<TransactionResDTO> transactions = transactionServiceImpl.getTransactionsByAccountIdByDateRange(transactionsAccountHistoricReqDTO);
        return transactions;
    }
}