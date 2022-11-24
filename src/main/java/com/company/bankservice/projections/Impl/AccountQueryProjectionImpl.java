package com.company.bankservice.projections.Impl;

import com.company.bankservice.projections.AccountQueryProjection;
import com.company.bankservice.repositories.mongo.AccountMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountQueryProjectionImpl implements AccountQueryProjection {

    @Autowired
    AccountMongoRepository accountMongoRepository;



}
