package com.company.bankservice.services;

import com.company.bankservice.config.dto.resolvers.UserDepositAccountReqDTO;
import com.company.bankservice.config.dto.resolvers.UserReqDTO;
import com.company.bankservice.config.dto.resolvers.UserResDTO;

public interface UserCommandService {
    UserResDTO create(UserReqDTO userReqDTO);

    UserResDTO createUserDepositAccount(UserDepositAccountReqDTO userDepositAccountReqDTO);
}