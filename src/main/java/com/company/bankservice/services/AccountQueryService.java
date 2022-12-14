package com.company.bankservice.services;

import com.company.bankservice.dto.resolvers.AccountResDTO;

import java.util.List;

public interface AccountQueryService {

    List<AccountResDTO> getAccountsUser(String userId);

    List<AccountResDTO> getAccountsOverdraft();

}
