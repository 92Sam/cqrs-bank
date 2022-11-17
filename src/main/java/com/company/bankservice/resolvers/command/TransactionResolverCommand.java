package com.company.bankservice.resolvers.command;

import com.company.bankservice.dto.resolvers.ErrorResponseDTO;
import com.company.bankservice.dto.resolvers.TransactionResponseDTO;
import com.company.bankservice.services.impl.TransactionCommandServiceImpl;
import com.company.bankservice.dto.resolvers.TransactionReqDTO;
import com.company.bankservice.dto.resolvers.TransactionResDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class TransactionResolverCommand {
    @Autowired
    private TransactionCommandServiceImpl transactionCommandServiceImpl;

    private static Logger log = LogManager.getLogger(TransactionResolverCommand.class);

    @MutationMapping()
    public TransactionResDTO addTransaction(@Argument("input") TransactionReqDTO transactionInputReq) {
        try {
            TransactionResDTO transactionResDTO = transactionCommandServiceImpl.create(transactionInputReq);
            log.info("Response {}", transactionResDTO);
            return transactionResDTO;
        }catch (Error | Exception error){
            log.error("Error Response {}", error.getMessage());
            return null;
        }
    }
}