package com.company.bankservice.entities;

import com.company.bankservice.enums.TransactionType;
import com.mongodb.lang.Nullable;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;


//@Entity(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "transactions")
@Document(collection = "transactions")
public class Transaction {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @NonNull
    @DocumentReference(lazy = true)
    @Field("accountId")
    private Account account;

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
