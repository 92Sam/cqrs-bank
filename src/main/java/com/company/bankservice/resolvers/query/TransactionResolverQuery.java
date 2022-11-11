package com.company.bankservice.resolvers.query;

import com.company.bankservice.dto.TransactionReqDTO;
import com.company.bankservice.dto.TransactionResDTO;
import com.company.bankservice.services.impl.TransactionQueryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;

@Controller
public class TransactionResolverQuery {
    @Autowired
    private TransactionQueryServiceImpl transactionServiceImpl;

    @QueryMapping
    public  ResponseEntity<Page<List<TransactionResDTO>>> getTransactionList()
    {
        System.out.println("Execution Here");
        List<TransactionResDTO> transactions = transactionServiceImpl.getTransactionList();
        Page<List<TransactionResDTO>> pagination = new PageImpl<List<TransactionResDTO>>(
                Collections.singletonList(transactions),
                PageRequest.of(1, 2),
                10);
        return ResponseEntity.ok(pagination);
    }

}