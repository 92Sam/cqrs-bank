package com.company.bankservice.services.impl;

import com.company.bankservice.dto.resolvers.TransactionReqDTO;
import com.company.bankservice.dto.resolvers.TransactionResDTO;
import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.enums.TransactionType;
import com.company.bankservice.events.TransactionKafkaProducerEvent;
import com.company.bankservice.repositories.AccountMongoRepository;
import com.company.bankservice.repositories.TransactionMongoRepository;
import com.company.bankservice.services.TransactionCommandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionCommandServiceImpl implements TransactionCommandService {

    @Autowired
    private TransactionMongoRepository transactionMongoRepository;

    @Autowired
    private AccountMongoRepository accountMongoRepository;

    @Autowired
    private AccountCommandServiceImpl accountCommandServiceImpl;

    @Autowired
    private TransactionKafkaProducerEvent transactionEventKafkaProducer;

    private static Logger log = LogManager.getLogger(TransactionCommandServiceImpl.class);

    @Override
    public TransactionResDTO create(TransactionReqDTO transactionReq) {

        Optional<Account> account = accountMongoRepository.findById(transactionReq.getAccountId());
        if (!account.isPresent()){
            log.info("Error the account not exist ");
            return null;
        }

        TransactionResDTO transactionResDTO = new TransactionResDTO();
        Float totalAvailable = account.get().getBalance()+account.get().getCreditAvailable();
        if(totalAvailable<transactionReq.getAmount()) {
            log.info("Account No available Balance->{}", account.get().getId());
            return transactionResDTO;
        }

        Transaction transaction = new Transaction();
        transaction.setAccountId(account.get().getId());
        transaction.setAmount(transactionReq.getAmount());
        transaction.setTransactionTypeByAmount(transactionReq.getAmount());
        transaction.setTitle("Init Deposit Account");
        transaction.setDescription(transactionReq.getDescription().orElse(null));
        transaction.setCreatedAt(new Date());

        transaction = transactionMongoRepository.save(transaction);

        transactionResDTO.setAmount(transaction.getAmount());
        if (transaction.getTitle() != null){
            transactionResDTO.setTitle(transaction.getTitle());
        }
        if (transaction.getDescription() != null){
            transactionResDTO.setDescription(transaction.getDescription());
        }

        transactionEventKafkaProducer.sendMessage(transaction);

        return transactionResDTO;
    }

    @Override
    public TransactionResDTO initializeAccount(Account account) {

        Transaction transactionDeposit = new Transaction();
        transactionDeposit.setAccountId(account.getId());
        transactionDeposit.setAmount(account.getBalance());
        transactionDeposit.setTransactionType(TransactionType.DEPOSIT);
        transactionDeposit.setTitle("Initialize Project");
        transactionDeposit.setCreatedAt(new Date());

//        Transaction transactionCredit = new Transaction();
//        transactionCredit.setAccountId(account.getId());
//        transactionCredit.setAmount(account.getCreditAvailable());
//        transactionCredit.setTransactionType(TransactionType.CREDIT);
//        transactionCredit.setTitle("Credit Available");
//        transactionCredit.setCreatedAt(LocalDateTime.from(LocalTime.now()));

        List<Transaction> transactionList= Arrays.asList(transactionDeposit);

        List<Transaction> transactions = transactionMongoRepository.insert(transactionList);
        transactions.forEach(data ->{
            System.out.println(data);
        });
//        log.info(transaction);
//        transactionEventKafkaProducer.sendMessage(transaction);
        TransactionResDTO transactionResDTO = new TransactionResDTO();
//        transactionResDTO.setId(transaction.getId());
//        transactionResDTO.setAmount(transaction.getAmount());
//        transactionResDTO.setDescription(transaction.getDescription());
//        transactionResDTO.setTitle(transaction.getTitle());

        return transactionResDTO;
    }

}
