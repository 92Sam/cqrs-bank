package com.company.bankservice.repositories.pgsql;

import com.company.bankservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPostgresRepository extends JpaRepository<User, String> {
    @Query
    User findByEmail(String email);
}
