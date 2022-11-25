package com.company.bankservice.mappers;

import com.company.bankservice.config.dto.resolvers.UserLoginResDTO;
import com.company.bankservice.config.dto.resolvers.UserReqDTO;
import com.company.bankservice.config.dto.resolvers.UserResDTO;
import com.company.bankservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    User userReqDTOtoUser(UserReqDTO userReqDTO);

    User userResDTOtoUser(UserResDTO userResDTO);
    UserResDTO userToUserResDTO(User user);

    UserLoginResDTO userToUserLoginResDTO(User user);
}
