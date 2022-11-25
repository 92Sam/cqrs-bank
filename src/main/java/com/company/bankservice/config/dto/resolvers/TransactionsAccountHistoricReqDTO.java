package com.company.bankservice.config.dto.resolvers;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionsAccountHistoricReqDTO {
    private String accountId;
    private String dateFrom;
    private String dateTo;
}
