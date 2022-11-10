package com.company.bankservice.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//import javax.persistence.Entity;

@Getter
@Setter
//@Entity(name = "transactions")
@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;

    private Long amount;

    private String title;

    private String description;

}
