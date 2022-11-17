package com.company.bankservice.dto.resolvers;


import com.company.bankservice.enums.TransactionType;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResDTO {
    private String id;
    private String accountId;
    private TransactionType transactionType;
    private Float amount;
    private String title;
    private String description;
    private Date createdAt;
    private Date updatedAt;
}