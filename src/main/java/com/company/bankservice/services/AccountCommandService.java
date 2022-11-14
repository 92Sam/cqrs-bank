package com.company.bankservice.services;

import com.company.bankservice.dto.UserReqDTO;
import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.User;

public interface AccountCommandService {
    Account createFromBroker(User user);
}
