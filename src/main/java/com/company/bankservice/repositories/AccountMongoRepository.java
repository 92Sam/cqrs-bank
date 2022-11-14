package com.company.bankservice.repositories;

import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMongoRepository extends MongoRepository<Account, String> {
}
