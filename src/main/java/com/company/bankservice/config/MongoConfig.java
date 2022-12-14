package com.company.bankservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = {"com.company.bankservice.repositories.mongo"},
        considerNestedRepositories = true
)
public class MongoConfig {}
