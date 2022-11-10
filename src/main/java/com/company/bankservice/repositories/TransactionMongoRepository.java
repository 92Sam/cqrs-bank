package com.company.bankservice.repositories;

import com.company.bankservice.entities.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionMongoRepository extends MongoRepository<Transaction, String> {

}
