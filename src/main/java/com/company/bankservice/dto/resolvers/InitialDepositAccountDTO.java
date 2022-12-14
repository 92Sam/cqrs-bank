package com.company.bankservice.dto.resolvers;

import com.company.bankservice.enums.Currency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitialDepositAccountDTO {
    private Currency currency;
    private Float amount;
}
