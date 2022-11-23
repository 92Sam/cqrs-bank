package com.company.bankservice.repositories.pgsql;

import com.company.bankservice.entities.Transaction;
import com.company.bankservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionPostgresRepository extends JpaRepository<Transaction, String> {

//    @Query("SELECT * FROM Transactions WHERE created_at BETWEEN :dateFrom AND :dateTo ORDER BY created_at DESC")
//    List<Transaction> getTransactionsByDateRange(String dateFrom, String dateTo);
//
//    @Query("SELECT * FROM Transactions WHERE account_id = :accountId AND created_at BETWEEN :dateFrom AND :dateTo ORDER BY created_at DESC")
//    List<Transaction> getTransactionsByAccountIdByDateRange(String accountId, String dateFrom, String dateTo);

}
