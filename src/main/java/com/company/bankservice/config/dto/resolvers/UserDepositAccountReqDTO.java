package com.company.bankservice.config.dto.resolvers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDepositAccountReqDTO {
    private String email;
    private String password;
    private String name;
    private InitialDepositAccountDTO initialDeposit;
}