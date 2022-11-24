package com.company.bankservice.entities;

import com.company.bankservice.enums.AccountStatus;
import com.company.bankservice.enums.CreditLine;
import com.company.bankservice.enums.Currency;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "accounts")
@Document(collection = "accounts")
public class Account {
//    @Id
//    private String id;
    @javax.persistence.Id
    @Column(name = "id", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @DocumentReference(lazy = true)
    @Field("userId")
    private User user;

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
