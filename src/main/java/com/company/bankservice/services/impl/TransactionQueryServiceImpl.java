package com.company.bankservice.services.impl;

import com.company.bankservice.dto.resolvers.TransactionResDTO;
import com.company.bankservice.dto.resolvers.TransactionsAccountHistoricReqDTO;
import com.company.bankservice.dto.resolvers.TransactionsHistoricReqDTO;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.repositories.TransactionMongoRepository;
import com.company.bankservice.services.TransactionQueryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.internal.LogManagerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionQueryServiceImpl implements TransactionQueryService {

    @Autowired()
    private TransactionMongoRepository transactionMongoRepository;

    private static Logger log = LogManager.getLogger(TransactionQueryServiceImpl.class);

    @Override
    public List<TransactionResDTO> getTransactionList() {
        try {
            List<Transaction> transactionList = transactionMongoRepository.findAll();

            if (transactionList.stream().count() <= 0) {
                return null;
            }

            return transactionList.stream().map(transaction -> {
                TransactionResDTO transactionResDTO = new TransactionResDTO();
                transactionResDTO.setId(transaction.getId());
                transactionResDTO.setTransactionType(transaction.getTransactionType());
                transactionResDTO.setAccountId(transaction.getAccountId());
                transactionResDTO.setCreateAt(transaction.getCreatedAt());
                transactionResDTO.setAmount(transaction.getAmount());
                if (transaction.getDescription() != null) {
                    transactionResDTO.setTitle(transaction.getTitle());
                }
                if (transaction.getDescription() != null) {
                    transactionResDTO.setDescription(transaction.getDescription());
                }
                return transactionResDTO;
            }).collect(Collectors.toList());

        }catch (Error error){
            log.error("Error executing Query {}", error);
        }

        return null;
    }

    @Override
    public List<TransactionResDTO> getTransactionsByDateRange(TransactionsHistoricReqDTO transactionsHistoricReqDTO) {
        try {
            List<Transaction> transactionList = transactionMongoRepository.getTransactionsByDateRange(
                    transactionsHistoricReqDTO.getDateFrom(),
                    transactionsHistoricReqDTO.getDateTo()
            );

            if (transactionList.stream().count() <= 0) {
                return null;
            }

            return transactionList.stream().map(transaction -> {
                TransactionResDTO transactionResDTO = new TransactionResDTO();
                transactionResDTO.setId(transaction.getId());
                transactionResDTO.setTransactionType(transaction.getTransactionType());
                transactionResDTO.setAccountId(transaction.getAccountId());
                transactionResDTO.setCreateAt(transaction.getCreatedAt());
                transactionResDTO.setAmount(transaction.getAmount());
                if (transaction.getDescription() != null) {
                    transactionResDTO.setTitle(transaction.getTitle());
                }
                if (transaction.getDescription() != null) {
                    transactionResDTO.setDescription(transaction.getDescription());
                }
                return transactionResDTO;
            }).collect(Collectors.toList());

            }catch (Error error){
                log.error("Error executing Query {}", error);
            }
        return null;
    }

    @Override
    public List<TransactionResDTO> getTransactionsByAccountIdByDateRange(TransactionsAccountHistoricReqDTO transactionsAccountHistoricReqDTO) {
        try {
            List<Transaction> transactionList = transactionMongoRepository.getTransactionsByAccountIdByDateRange(
                    transactionsAccountHistoricReqDTO.getAccountId(),
                    transactionsAccountHistoricReqDTO.getDateFrom(),
                    transactionsAccountHistoricReqDTO.getDateTo());

            if (transactionList.stream().count() <= 0) {
                return null;
            }

            return transactionList.stream().map(transaction -> {
                TransactionResDTO transactionResDTO = new TransactionResDTO();
                transactionResDTO.setId(transaction.getId());
                transactionResDTO.setTransactionType(transaction.getTransactionType());
                transactionResDTO.setAccountId(transaction.getAccountId());
                transactionResDTO.setCreateAt(transaction.getCreatedAt());
                transactionResDTO.setAmount(transaction.getAmount());
                if (transaction.getDescription() != null) {
                    transactionResDTO.setTitle(transaction.getTitle());
                }
                if (transaction.getDescription() != null) {
                    transactionResDTO.setDescription(transaction.getDescription());
                }
                return transactionResDTO;
            }).collect(Collectors.toList());

        }catch (Error error){
            log.error("Error executing Query {}", error);
        }
        return null;
    }

}
