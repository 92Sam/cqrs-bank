package com.company.bankservice.resolvers.command;

import com.company.bankservice.services.impl.TransactionCommandServiceImpl;
import com.company.bankservice.dto.TransactionReqDTO;
import com.company.bankservice.dto.TransactionResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "transactions")
public class TransactionResolverCommand {
    @Autowired
    private TransactionCommandServiceImpl transactionCommandServiceImpl;

    //Http Post method to add the transaction in the transaction list
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<TransactionResDTO> addTransaction(@RequestBody TransactionReqDTO transactionReq)
    {
        TransactionResDTO transactionResDTO = transactionCommandServiceImpl.create(transactionReq);

        return ResponseEntity.ok(transactionResDTO);
    }
}