package com.company.bankservice.dto.resolvers;

import com.company.bankservice.enums.TransactionType;
import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Validated
public class TransactionReqDTO {
    private UUID accountId;
    private TransactionType transactionType;
    private Float amount;
    private String title;
    @Nullable
    private String description;
}