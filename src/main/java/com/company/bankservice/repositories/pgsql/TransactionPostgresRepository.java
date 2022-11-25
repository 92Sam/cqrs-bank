package com.company.bankservice.repositories.pgsql;

import com.company.bankservice.entities.Transaction;
import com.company.bankservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionPostgresRepository extends JpaRepository<Transaction, String> {

}
