package com.company.bankservice.config.dto.resolvers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDTO {
    private String message;
    private Integer statusCode;
}
