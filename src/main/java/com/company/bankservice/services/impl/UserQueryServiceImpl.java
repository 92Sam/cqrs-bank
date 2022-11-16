package com.company.bankservice.services.impl;

import com.company.bankservice.dto.events.UserCreateEventMessageDTO;
import com.company.bankservice.dto.resolvers.UserDepositAccountReqDTO;
import com.company.bankservice.dto.resolvers.UserLoginReqDTO;
import com.company.bankservice.dto.resolvers.UserLoginResDTO;
import com.company.bankservice.dto.resolvers.UserReqDTO;
import com.company.bankservice.entities.User;
import com.company.bankservice.enums.UserStatus;
import com.company.bankservice.enums.errors.UserError;
import com.company.bankservice.events.UserKafkaProducerEvent;
import com.company.bankservice.repositories.UserMongoRepository;
import com.company.bankservice.services.UserQueryService;
import com.company.utils.EncryptUtils;
import com.company.utils.JwtUtils;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    @Autowired
    UserMongoRepository userMongoRepository;

    private static Logger log = LogManager.getLogger(UserQueryServiceImpl.class);

    @Override
    public UserLoginResDTO login(String email, String password) {
        try {
            User user = userMongoRepository.findByEmail(email);
            if (user == null) {
                log.info("Error: {}", UserError.USER_ALREADY_EXIST.toString());
                return null;
            }
            if (!EncryptUtils.verifyHashBCrypt(password, user.getPassword())){
                log.info("Error: {}", UserError.CREDENTIAL_NOT_MISMATCH.toString());
                return null;
            }
            if (user.getUserStatus() != UserStatus.ENABLED){
                log.info("Error: {}", UserError.USER_DISABLED_ACCESS.toString());
                return null;
            }

            UserLoginResDTO userLoginResDTO = new UserLoginResDTO();
            userLoginResDTO.setId(user.getId());
            userLoginResDTO.setToken(JwtUtils.generateToken());

            return userLoginResDTO;
        }catch (Error error){
            log.error("Error: ", error);
        }
        return null;
    }

}
