package com.company.bankservice.services.impl;

import com.company.bankservice.dto.resolvers.TransactionReqDTO;
import com.company.bankservice.dto.resolvers.TransactionResDTO;
import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.enums.TransactionType;
import com.company.bankservice.events.TransactionKafkaProducerEvent;
import com.company.bankservice.mappers.TransactionMapper;
import com.company.bankservice.repositories.mongo.TransactionMongoRepository;
import com.company.bankservice.repositories.pgsql.TransactionPostgresRepository;
import com.company.bankservice.services.TransactionCommandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionCommandServiceImpl implements TransactionCommandService {

    @Autowired
    private TransactionPostgresRepository transactionPostgresRepository;

    @Autowired
    private TransactionMongoRepository transactionMongoRepository;

    @Autowired
    private AccountCommandServiceImpl accountCommandServiceImpl;

    @Autowired
    private TransactionKafkaProducerEvent transactionEventKafkaProducer;

    private static Logger log = LogManager.getLogger(TransactionCommandServiceImpl.class);

    @Override
    public TransactionResDTO create(TransactionReqDTO transactionReq) throws Exception {
        try {
            Transaction transaction = new Transaction();
            transaction.setAccountId(transactionReq.getAccountId());
            transaction.setAmount(transactionReq.getAmount());
            transaction.setTransactionTypeByAmount(transactionReq.getAmount());
            transaction.setTitle("Init Deposit Account");
    //        transaction.setDescription(transactionReq.getDescription().orElse(null));
            transaction.setCreatedAt(new Date());

            Account account = accountCommandServiceImpl.updateAccountBalanceByTransaction(transaction);
            if(account == null){
                throw new Exception("Error on execute Transaction");
            }

            transactionMongoRepository.save(transaction);
            transactionPostgresRepository.save(transaction);

            transactionEventKafkaProducer.sendMessage(transaction);
            return TransactionMapper.transactionMapper.transactionToTransactionResDTO(transaction);
        }catch ( Error error ){
            log.error("Error on create {}", error);
        }
        return null;
    }

    @Override
    public List<TransactionResDTO> initializeAccount(Account account) {
        try{
            Transaction transactionDeposit = new Transaction();
            transactionDeposit.setAccountId(account.getId());
            transactionDeposit.setAmount(account.getBalance());
            transactionDeposit.setTransactionType(TransactionType.DEPOSIT);
            transactionDeposit.setTitle("Initialize Project");
            transactionDeposit.setCreatedAt(new Date());

            List<Transaction> transactionList= Arrays.asList(transactionDeposit);

            List<Transaction> transactions = transactionMongoRepository.insert(transactionList);
            List<Transaction> transactionsPsql = transactionPostgresRepository.saveAll(transactionList);

            return transactions.stream().map(transaction ->{
                TransactionResDTO transactionResDTO = new TransactionResDTO();
                transactionResDTO.setId(transaction.getId());
                transactionResDTO.setAmount(transaction.getAmount());
                transactionResDTO.setDescription(transaction.getDescription());
                transactionResDTO.setTitle(transaction.getTitle());
                return transactionResDTO;
            }).collect(Collectors.toList());
        }catch (Error error){
            log.error("Error on initializeAccount", error);
        }
        return null;
    }

}
