package com.company.bankservice.entities;

import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

//import javax.persistence.Entity;

@Getter
@Setter
//@Entity(name = "transactions")
@Document(collection = "transactions")
public class Transaction {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id_ID")
    private Account accountId;

    private Long amount;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private String userStatus;

    @CreatedDate
    private Date createdAt;

    @Nullable
    private Date updatedAt;
}
