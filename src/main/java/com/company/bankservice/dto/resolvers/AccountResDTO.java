package com.company.bankservice.dto.resolvers;

import com.company.bankservice.enums.AccountStatus;
import com.company.bankservice.enums.CreditLine;
import com.company.bankservice.enums.Currency;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountResDTO {
    private String id;
    private String userId;
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
