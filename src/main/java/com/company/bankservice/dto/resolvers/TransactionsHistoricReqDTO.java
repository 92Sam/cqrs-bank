package com.company.bankservice.dto.resolvers;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionsHistoricReqDTO {
    private String dateFrom;
    private String dateTo;
}
