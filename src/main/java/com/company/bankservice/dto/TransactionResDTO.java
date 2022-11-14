package com.company.bankservice.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TransactionResDTO {
    private UUID id;

    private Long amount;

    private String title;

    private String description;

}