package com.company.bankservice.repositories;

import com.company.bankservice.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMongoRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
