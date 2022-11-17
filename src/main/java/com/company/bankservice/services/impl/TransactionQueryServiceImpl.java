package com.company.bankservice.services.impl;

import com.company.bankservice.dto.resolvers.TransactionResDTO;
import com.company.bankservice.dto.resolvers.TransactionsAccountHistoricReqDTO;
import com.company.bankservice.dto.resolvers.TransactionsHistoricReqDTO;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.mappers.TransactionMapper;
import com.company.bankservice.repositories.TransactionMongoRepository;
import com.company.bankservice.services.TransactionQueryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

            return TransactionMapper.transactionMapper.transactionListToTransactionResDTOList(transactionList);

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

            return TransactionMapper.transactionMapper.transactionListToTransactionResDTOList(transactionList);

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

            return TransactionMapper.transactionMapper.transactionListToTransactionResDTOList(transactionList);

        }catch (Error error){
            log.error("Error executing Query {}", error);
        }
        return null;
    }

}
