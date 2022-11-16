package com.company.bankservice.dto.resolvers;

import com.company.bankservice.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResDTO {
    private String id;
    private String email;
    private String name;
    private UserStatus userStatus;
    private Date createdAt;
}
