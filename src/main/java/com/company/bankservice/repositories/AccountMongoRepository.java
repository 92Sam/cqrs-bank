package com.company.bankservice.repositories;

import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.User;
import com.company.bankservice.enums.Currency;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Enumerated;
import java.util.List;

@Repository
public interface AccountMongoRepository extends MongoRepository<Account, String> {

    @Query("{'userId' : ?0, 'currency' : ?1}")
    Account findAccountByUserIdByCurrency(String userId, Currency currency);

    @Query("{'userId' : {'$eq': ?0 }}")
    List<Account> findAccountByUserId(String userId);

    @Query("{'balance' : {'$lt': 0 }}")
    List<Account> getAccountsOverdraft();
}
