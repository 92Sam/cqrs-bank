package com.company.bankservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class TransactionReqDTO {

    private Long amount;

    private String title;

    private String description;

}