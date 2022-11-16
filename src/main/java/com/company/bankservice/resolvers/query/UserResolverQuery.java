package com.company.bankservice.resolvers.query;
import com.company.bankservice.dto.resolvers.UserLoginResDTO;
import com.company.bankservice.services.impl.TransactionCommandServiceImpl;
import com.company.bankservice.services.impl.UserCommandServiceImpl;
import com.company.bankservice.services.impl.UserQueryServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;


@Controller
public class UserResolverQuery {
    @Autowired
    private UserQueryServiceImpl userQueryServiceImpl;

    private static Logger log = LogManager.getLogger(UserResolverQuery.class);

    @QueryMapping
    public UserLoginResDTO login(@Argument("email") String email, @Argument("password") String password) {
        try {
            return userQueryServiceImpl.login(email, password);
        }catch (Error error){
            log.error(error);
        }
        return null;
    }
}
