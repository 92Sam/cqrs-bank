package com.company.bankservice.repositories;

import com.company.bankservice.entities.Transaction;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionMongoRepository extends MongoRepository<Transaction, String> {

    @Query("{createdAt:{$gte:{$date:?0}, $lt:{$date:?1}}}")
    List<Transaction> getTransactionsByDateRange(String dateFrom, String dateTo);

    @Query("{accountId: ?0, createdAt:{$gte:{$date:?1}, $lt:{$date:?2}}}")
    List<Transaction> getTransactionsByAccountIdByDateRange(String accountId, String dateFrom, String dateTo);

}
