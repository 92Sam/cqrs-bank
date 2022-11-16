package com.company.bankservice.services;

import com.company.bankservice.dto.resolvers.UserLoginReqDTO;
import com.company.bankservice.dto.resolvers.UserLoginResDTO;
import com.company.bankservice.entities.User;

public interface UserQueryService {
    UserLoginResDTO login(String email, String password);
}
