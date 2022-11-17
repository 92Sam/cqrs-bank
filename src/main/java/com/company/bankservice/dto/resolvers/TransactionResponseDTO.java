package com.company.bankservice.dto.resolvers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponseDTO {
    private TransactionResDTO transactionResDTO;
    private ErrorResponseDTO errorResponseDTO;
}
