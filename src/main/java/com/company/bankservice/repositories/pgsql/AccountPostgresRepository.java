package com.company.bankservice.repositories.pgsql;

import com.company.bankservice.entities.Account;
import com.company.bankservice.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountPostgresRepository extends JpaRepository<Account, String> {

//    @Query("{'userId' : ?0, 'currency' : ?1}")
//    @Query(value = "SELECT * FROM accounts WHERE user_id=:userId AND currency:currency")
//    Account findAccountByUserIdByCurrency(String userId, Currency currency);
//
////    @Query("{'userId' : {'$eq': ?0 }}")
//    @Query(value="SELECT * FROM accounts where user_id=:userId")
//    List<Account> findAccountByUserId(String userId);
//
////    @Query("{'balance' : {'$lt': 0 }}")
//    @Query(value="SELECT * FROM accounts WHERE balance < 0")
//    List<Account> getAccountsOverdraft();
}
