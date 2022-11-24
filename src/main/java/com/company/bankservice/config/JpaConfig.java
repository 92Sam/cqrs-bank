package com.company.bankservice.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = "com.company.bankservice.repositories.pgsql")
@EntityScan(basePackages = "com.company.bankservice.entities")
public class JpaConfig {

}
