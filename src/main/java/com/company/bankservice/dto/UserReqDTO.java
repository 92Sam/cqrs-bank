package com.company.bankservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReqDTO {
    private String email;
    private String password;
    private String name;
}
