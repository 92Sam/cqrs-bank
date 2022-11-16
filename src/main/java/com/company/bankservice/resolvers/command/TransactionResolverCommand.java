package com.company.bankservice.resolvers.command;

import com.company.bankservice.services.impl.TransactionCommandServiceImpl;
import com.company.bankservice.dto.resolvers.TransactionReqDTO;
import com.company.bankservice.dto.resolvers.TransactionResDTO;
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
    public TransactionResDTO addTransaction(@Argument("input") TransactionReqDTO transactionInputReq)
    {
        return transactionCommandServiceImpl.create(transactionInputReq);
    }
}