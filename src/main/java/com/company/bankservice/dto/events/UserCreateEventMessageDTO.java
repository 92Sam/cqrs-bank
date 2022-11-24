package com.company.bankservice.dto.events;

import com.company.bankservice.dto.resolvers.InitialDepositAccountDTO;
import com.company.bankservice.dto.resolvers.UserResDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateEventMessageDTO {
    private UserResDTO userId;
    private InitialDepositAccountDTO initialDepositAccountDTO;

    public void generateInitialDepositAccountDTO(){
        setInitialDepositAccountDTO(new InitialDepositAccountDTO());
    }
}