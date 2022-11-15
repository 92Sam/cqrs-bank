package com.company.bankservice.entities;

import com.company.bankservice.enums.TransactionType;
import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

//import javax.persistence.Entity;

@Getter
@Setter
//@Entity(name = "transactions")
@Document(collection = "transactions")
public class Transaction {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "account_id_ID")
    @NonNull
    private String accountId;

    @Enumerated(EnumType.STRING)
    @NonNull
    private TransactionType transactionType;

    private Float amount;

    @Nullable
    private String title;

    @Nullable
    private String description;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public void setTransactionTypeByAmount(Float amount){
        TransactionType transactionType = TransactionType.DEPOSIT;
        if (amount < 0){
            transactionType = TransactionType.DEBIT;
        }
        this.setTransactionType(transactionType);
    }
}
