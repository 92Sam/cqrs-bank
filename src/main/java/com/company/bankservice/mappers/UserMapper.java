package com.company.bankservice.mappers;

import com.company.bankservice.dto.resolvers.*;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    User userReqDTOtoUser(UserReqDTO userReqDTO);
    UserResDTO userToUserResDTO(User user);


    UserLoginResDTO userToUserLoginResDTO(User user);
}