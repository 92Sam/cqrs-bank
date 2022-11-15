package com.company.bankservice.services;

import com.company.bankservice.dto.resolvers.UserReqDTO;
import com.company.bankservice.entities.User;

public interface UserCommandService {
    User create(UserReqDTO userReqDTO);
}