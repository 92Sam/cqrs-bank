package com.company.bankservice.resolvers.query;

import com.company.bankservice.dto.TransactionReqDTO;
import com.company.bankservice.dto.TransactionResDTO;
import com.company.bankservice.services.impl.TransactionQueryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "transactions")
public class TransactionResolverQuery {
    @Autowired
    private TransactionQueryServiceImpl transactionServiceImpl;

    //Http Get method to get the transaction list in JSON format
    @GetMapping(produces="application/json")
    public  ResponseEntity<Page<List<TransactionResDTO>>> getTransactionList()
    {

        List<TransactionResDTO> transactions = transactionServiceImpl.getTransactionList();
        Page<List<TransactionResDTO>> pagination = new PageImpl<List<TransactionResDTO>>(
                Collections.singletonList(transactions),
                PageRequest.of(1, 2),
                10);
        return ResponseEntity.ok(pagination);
    }

}