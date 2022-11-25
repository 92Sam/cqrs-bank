package com.company.bankservice.config.dto.resolvers;

import com.company.bankservice.enums.AccountStatus;
import com.company.bankservice.enums.CreditLine;
import com.company.bankservice.enums.Currency;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AccountResDTO {
    private String id;
    private UserResDTO userId;
    private CreditLine creditLineId;
    private String accountNumber;
    private Currency currency;
    private Float balance;
    private Float creditAvailable;
    private Float creditAmount;
    private AccountStatus accountStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
