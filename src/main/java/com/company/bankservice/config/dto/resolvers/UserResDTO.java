package com.company.bankservice.config.dto.resolvers;

import com.company.bankservice.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class UserResDTO {
    private UUID id;
    private String email;
    private String name;
    private UserStatus userStatus;
    private Date createdAt;
}
