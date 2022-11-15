package com.company.bankservice.entities;

import com.company.bankservice.enums.AccountStatus;
import com.company.bankservice.enums.CreditLine;
import com.company.bankservice.enums.Currency;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
//@Entity(name = "accounts")
@Document(collection = "accounts")
public class Account {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_ID")
    private String userId;

    @Enumerated
    private CreditLine creditLineId;

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private Float balance;

    private Float creditAvailable;

    private Float creditAmount;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
