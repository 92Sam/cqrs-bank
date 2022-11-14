package com.company.bankservice.resolvers.command;
import com.company.bankservice.dto.UserReqDTO;
import com.company.bankservice.entities.User;
import com.company.bankservice.services.impl.TransactionCommandServiceImpl;
import com.company.bankservice.dto.TransactionResDTO;
import com.company.bankservice.services.impl.UserCommandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class UserResolverCommand {
    @Autowired
    private UserCommandServiceImpl userCommandServiceImpl;

    @MutationMapping()
    public ResponseEntity<Object> createUser(@Argument("input") UserReqDTO userInputDTO)
    {
        System.out.println(userInputDTO.getEmail());
        User userResDTO = userCommandServiceImpl.create(userInputDTO);
        return ResponseEntity.ok("transactionResDTO");
    }
}
