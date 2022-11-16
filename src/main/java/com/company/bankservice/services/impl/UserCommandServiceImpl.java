package com.company.bankservice.services.impl;

import com.company.bankservice.dto.events.UserCreateEventMessageDTO;
import com.company.bankservice.dto.resolvers.UserDepositAccountReqDTO;
import com.company.bankservice.dto.resolvers.UserReqDTO;
import com.company.bankservice.entities.User;
import com.company.bankservice.enums.UserStatus;
import com.company.bankservice.events.UserKafkaProducerEvent;
import com.company.bankservice.repositories.UserMongoRepository;
import com.company.bankservice.services.UserCommandService;
import com.company.utils.EncryptUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    @Autowired
    UserMongoRepository userMongoRepository;

    @Autowired
    UserKafkaProducerEvent userEventKafkaProducer;

    private static Logger log = LogManager.getLogger(UserCommandServiceImpl.class);

    @Override
    public User create(UserReqDTO userReqDTO) {

        try {
            log.info(userReqDTO);
            User user = new User();
            user.setEmail(userReqDTO.getEmail());
            user.setPassword(EncryptUtils.hashBCrypt(userReqDTO.getPassword()));
            user.setEmail(userReqDTO.getEmail());
            user.setUserStatus(UserStatus.ENABLED);
            user.setCreatedAt(new Date());

            User userres = userMongoRepository.save(user);
            log.info("Mongo Insert", userres.toString());


            UserCreateEventMessageDTO userCreateEventMessageDTO = new UserCreateEventMessageDTO();
            userCreateEventMessageDTO.setUserId(userres.getId());
            userCreateEventMessageDTO.generateInitialDepositAccountDTO();

            userEventKafkaProducer.sendMessage(userCreateEventMessageDTO);

            return userres;
        }catch (Error error){
            log.error("Error: ", error);
        }
        return null;
    }

    public User createUserDepositAccount(UserDepositAccountReqDTO userDepositAccountReqDTO) {

        try {
            log.info(userDepositAccountReqDTO);

            User user = new User();
            user.setEmail(userDepositAccountReqDTO.getEmail());
            user.setPassword(userDepositAccountReqDTO.getPassword());
            user.setEmail(userDepositAccountReqDTO.getEmail());
            user.setUserStatus(UserStatus.ENABLED);

            User userres = userMongoRepository.save(user);
            log.info("Mongo Insert", userres.toString());

            UserCreateEventMessageDTO userCreateEventMessageDTO = new UserCreateEventMessageDTO();
            userCreateEventMessageDTO.setUserId(userres.getId());
            userCreateEventMessageDTO.setInitialDepositAccountDTO(userDepositAccountReqDTO.getInitialDeposit());

            userEventKafkaProducer.sendMessage(userCreateEventMessageDTO);

            return userres;
        }catch (Error error){
            log.error("Error: ", error);
        }
        return null;
    }
}
