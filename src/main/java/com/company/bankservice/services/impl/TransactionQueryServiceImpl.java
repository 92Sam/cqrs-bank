package com.company.bankservice.services.impl;

import com.company.bankservice.dto.TransactionResDTO;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.repositories.TransactionMongoRepository;
import com.company.bankservice.services.TransactionQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionQueryServiceImpl implements TransactionQueryService {

    @Autowired()
    private TransactionMongoRepository transactionMongoRepository;

    @Override
    public List<TransactionResDTO> getTransactionList() {

        List<Transaction> transactions = transactionMongoRepository.findAll();

        return transactions.stream().map(transaction -> {
            TransactionResDTO res = new TransactionResDTO();
            res.setId(transaction.getId());
            res.setAmount(transaction.getAmount());
            res.setTitle(transaction.getTitle());
            res.setDescription(transaction.getDescription());
            return res;
        }).collect(Collectors.toList());
    }

}
