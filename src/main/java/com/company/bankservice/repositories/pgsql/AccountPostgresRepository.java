package com.company.bankservice.repositories.pgsql;

import com.company.bankservice.entities.Account;
import com.company.bankservice.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountPostgresRepository extends JpaRepository<Account, String> {

    List<Account> findAccountByUserId(String userId);

    Optional<Account> findById(UUID userId);

}
