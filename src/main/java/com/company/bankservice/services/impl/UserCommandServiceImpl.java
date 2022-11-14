package com.company.bankservice.services.impl;

import com.company.bankservice.dto.UserReqDTO;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.entities.User;
import com.company.bankservice.enums.UserStatus;
import com.company.bankservice.events.UserEventKafkaProducer;
import com.company.bankservice.repositories.UserMongoRepository;
import com.company.bankservice.services.UserCommandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    @Autowired
    UserMongoRepository userMongoRepository;

    @Autowired
    UserEventKafkaProducer userEventKafkaProducer;

    private static Logger log = LogManager.getLogger(UserCommandServiceImpl.class);

    @Override
    public User create(UserReqDTO userReqDTO) {

        try {
            log.info(userReqDTO);
            User user = new User();
            user.setEmail(userReqDTO.getEmail());
            user.setPassword(userReqDTO.getPassword());
            user.setEmail(userReqDTO.getEmail());
            user.setUserStatus(UserStatus.ENABLED);

            User userres = userMongoRepository.save(user);
            log.info("Mongo Insert", userres.toString());

            userEventKafkaProducer.sendMessage(user);

            return userres;
        }catch (Error error){
            log.error("Error: ", error);
        }
        return null;
    }
}
