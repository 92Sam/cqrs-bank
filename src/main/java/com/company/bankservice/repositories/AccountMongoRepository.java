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

    @Query("{'userId' : ?1, 'currency' : ?2}")
    Account findAccountByUserIdByCurrency(String userId, Currency currency);

    @Query("{'userId' : ?1}")
    List<Account> findAccountByUserId(String userId);
}
