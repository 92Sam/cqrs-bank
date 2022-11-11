package com.company.bankservice.resolvers.command;

import com.company.bankservice.services.impl.TransactionCommandServiceImpl;
import com.company.bankservice.dto.TransactionReqDTO;
import com.company.bankservice.dto.TransactionResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class TransactionResolverCommand {
    @Autowired
    private TransactionCommandServiceImpl transactionCommandServiceImpl;

    @MutationMapping()
    public ResponseEntity<TransactionResDTO> addTransaction(@Argument("input") TransactionReqDTO transactionInputReq)
    {
        TransactionResDTO transactionResDTO = transactionCommandServiceImpl.create(transactionInputReq);

        return ResponseEntity.ok(transactionResDTO);
    }
}