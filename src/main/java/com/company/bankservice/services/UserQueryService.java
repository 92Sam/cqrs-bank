package com.company.bankservice.services;

import com.company.bankservice.dto.resolvers.UserLoginResDTO;

public interface UserQueryService {
    UserLoginResDTO login(String email, String password);
}
