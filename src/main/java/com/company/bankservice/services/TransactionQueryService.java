package com.company.bankservice.services;

import com.company.bankservice.dto.TransactionResDTO;
import java.util.List;

public interface TransactionQueryService {
    List<TransactionResDTO> getTransactionList();
}
