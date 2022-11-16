package com.company.bankservice.services;

import com.company.bankservice.dto.resolvers.AccountResDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import java.util.List;

public interface AccountQueryService {

    List<AccountResDTO> getAccountsUser(String userId);

    List<AccountResDTO> getAccountsOverdraft();

}
