package com.company.bankservice.resolvers.query;
import com.company.bankservice.services.impl.TransactionCommandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class UserResolverQuery {
    @Autowired
    private TransactionCommandServiceImpl transactionCommandServiceImpl;

    @QueryMapping
    public ResponseEntity<Object> login(@Argument("email") String email, @Argument("password") String password)
    {
        System.out.println(email);
        System.out.println(password);
//        TransactionResDTO transactionResDTO = transactionCommandServiceImpl.create(transactionInputReq);
        return ResponseEntity.ok("transactionResDTO");
    }
}
