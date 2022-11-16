package com.company.bankservice.services.impl;

import com.company.bankservice.dto.events.UserCreateEventMessageDTO;
import com.company.bankservice.dto.resolvers.UserDepositAccountReqDTO;
import com.company.bankservice.dto.resolvers.UserReqDTO;
import com.company.bankservice.dto.resolvers.UserResDTO;
import com.company.bankservice.entities.User;
import com.company.bankservice.enums.UserStatus;
import com.company.bankservice.enums.errors.UserError;
import com.company.bankservice.events.UserKafkaProducerEvent;
import com.company.bankservice.repositories.UserMongoRepository;
import com.company.bankservice.services.UserCommandService;
import com.company.utils.EncryptUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    @Autowired
    UserMongoRepository userMongoRepository;

    @Autowired
    UserKafkaProducerEvent userEventKafkaProducer;

    private static Logger log = LogManager.getLogger(UserCommandServiceImpl.class);

    @Override
    public UserResDTO create(UserReqDTO userReqDTO) {
        try {
            User verifyUser = userMongoRepository.findByEmail(userReqDTO.getEmail());
            if (verifyUser != null) {
                log.info("Error: {}", UserError.USER_ALREADY_EXIST.toString());
                return null;
            }

            User user = new User();
            user.setEmail(userReqDTO.getEmail());
            user.setPassword(EncryptUtils.hashBCrypt(userReqDTO.getPassword()));
            user.setEmail(userReqDTO.getEmail());
            user.setUserStatus(UserStatus.ENABLED);
            user.setCreatedAt(new Date());

            // Produce Store the User
            User userStored = userMongoRepository.save(user);

            UserCreateEventMessageDTO userCreateEventMessageDTO = new UserCreateEventMessageDTO();
            userCreateEventMessageDTO.setUserId(userStored.getId());
            userCreateEventMessageDTO.generateInitialDepositAccountDTO();

            // Produce Event Kafka
            userEventKafkaProducer.sendMessage(userCreateEventMessageDTO);

            //Mapping UserResDTO
            UserResDTO userResDTO = new UserResDTO();
            userResDTO.setId(userStored.getId());
            userResDTO.setEmail(userStored.getEmail());
            userResDTO.setName(userStored.getName());
            userResDTO.setUserStatus(userStored.getUserStatus());
            userResDTO.setCreatedAt(userStored.getCreatedAt());

            return userResDTO;
        }catch (Error error){
            log.error("Error: ", error);
        }
        return null;
    }

    @Override
    public UserResDTO createUserDepositAccount(UserDepositAccountReqDTO userDepositAccountReqDTO) {
        try {
            User verifyUser = userMongoRepository.findByEmail(userDepositAccountReqDTO.getEmail());
            if (verifyUser != null) {
                log.info("Error: {}", UserError.USER_ALREADY_EXIST.toString());
                return null;
            }

            //Mapping User
            User user = new User();
            user.setEmail(userDepositAccountReqDTO.getEmail());
            user.setPassword(EncryptUtils.hashBCrypt(userDepositAccountReqDTO.getPassword()));
            user.setEmail(userDepositAccountReqDTO.getEmail());
            user.setUserStatus(UserStatus.ENABLED);
            user.setCreatedAt(new Date());

            User userStored = userMongoRepository.save(user);

            //Mapping UserCreateEventMessageDTO
            UserCreateEventMessageDTO userCreateEventMessageDTO = new UserCreateEventMessageDTO();
            userCreateEventMessageDTO.setUserId(userStored.getId());
            userCreateEventMessageDTO.setInitialDepositAccountDTO(userDepositAccountReqDTO.getInitialDeposit());

            userEventKafkaProducer.sendMessage(userCreateEventMessageDTO);

            //Mapping UserResDTO
            UserResDTO userResDTO = new UserResDTO();
            userResDTO.setId(userStored.getId());
            userResDTO.setEmail(userStored.getEmail());
            userResDTO.setName(userStored.getName());
            userResDTO.setUserStatus(userStored.getUserStatus());
            userResDTO.setCreatedAt(userStored.getCreatedAt());

            return userResDTO;
        }catch (Error error){
            log.error("Error: ", error);
        }
        return null;
    }
}
