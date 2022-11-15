package com.company.bankservice.dto.resolvers;

import com.company.bankservice.enums.Currency;
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