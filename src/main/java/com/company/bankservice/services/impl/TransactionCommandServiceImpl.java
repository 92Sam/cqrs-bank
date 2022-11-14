package com.company.bankservice.services.impl;

import com.company.bankservice.dto.TransactionReqDTO;
import com.company.bankservice.dto.TransactionResDTO;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.events.TransactionEventKafkaProducer;
import com.company.bankservice.repositories.TransactionMongoRepository;
import com.company.bankservice.repositories.TransactionPostgresRepository;
import com.company.bankservice.services.TransactionCommandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionCommandServiceImpl implements TransactionCommandService {

    @Autowired
    private TransactionMongoRepository transactionMongoRepository;

    @Autowired
    private TransactionEventKafkaProducer transactionEventKafkaProducer;

    private static Logger log = LogManager.getLogger(TransactionCommandServiceImpl.class);

    @Override
    public TransactionResDTO create(TransactionReqDTO transactionReq) {

        log.info(transactionReq);
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionReq.getAmount());
        transaction.setTitle(transactionReq.getTitle());
        transaction.setDescription(transactionReq.getDescription());

        transaction = transactionMongoRepository.save(transaction);

        log.info(transaction);
        transactionEventKafkaProducer.sendMessage(transaction);

        TransactionResDTO transactionResDTO = new TransactionResDTO();
        transactionResDTO.setId(transaction.getId());
        transactionResDTO.setAmount(transaction.getAmount());
        transactionResDTO.setDescription(transaction.getDescription());
        transactionResDTO.setTitle(transaction.getTitle());

        return transactionResDTO;
    }

}
