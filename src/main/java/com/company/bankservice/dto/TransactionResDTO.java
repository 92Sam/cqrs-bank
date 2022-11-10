package com.company.bankservice.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResDTO {
    private String id;

    private Long amount;

    private String title;

    private String description;

}