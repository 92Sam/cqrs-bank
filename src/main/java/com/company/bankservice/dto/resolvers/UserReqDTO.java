package com.company.bankservice.dto.resolvers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReqDTO {
    private String email;
    private String password;
    private String name;
}
