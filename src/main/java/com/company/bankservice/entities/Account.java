package com.company.bankservice.entities;

import com.company.bankservice.enums.AccountStatus;
import com.company.bankservice.enums.Currency;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

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
    private User user;

    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private Float balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @CreatedDate
    private Date createdAt;

    private Date updatedAt;
}
