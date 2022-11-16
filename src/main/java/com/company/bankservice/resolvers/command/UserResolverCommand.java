package com.company.bankservice.resolvers.command;
import com.company.bankservice.dto.resolvers.UserDepositAccountReqDTO;
import com.company.bankservice.dto.resolvers.UserReqDTO;
import com.company.bankservice.dto.resolvers.UserResDTO;
import com.company.bankservice.entities.User;
import com.company.bankservice.resolvers.query.AccountResolverQuery;
import com.company.bankservice.services.impl.UserCommandServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class UserResolverCommand {
    @Autowired
    private UserCommandServiceImpl userCommandServiceImpl;

    private static Logger log = LogManager.getLogger(UserResolverCommand.class);

    @MutationMapping()
    public UserResDTO createUser(@Argument("input") UserReqDTO userInputDTO)
    {
        try{
            return userCommandServiceImpl.create(userInputDTO);
        }catch (Error error){
            log.error(error);
        }
        return null;
    }


    @MutationMapping()
    public UserResDTO createUserDepositAccount(@Argument("input") UserDepositAccountReqDTO userInputDTO)
    {
        try{
            return userCommandServiceImpl.createUserDepositAccount(userInputDTO);
        }catch (Error error){
            log.error(error);
        }
        return null;
    }

}
