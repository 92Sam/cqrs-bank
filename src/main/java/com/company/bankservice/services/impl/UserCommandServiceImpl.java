package com.company.bankservice.services.impl;

import com.company.bankservice.dto.events.UserCreateEventMessageDTO;
import com.company.bankservice.dto.resolvers.UserDepositAccountReqDTO;
import com.company.bankservice.dto.resolvers.UserReqDTO;
import com.company.bankservice.dto.resolvers.UserResDTO;
import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.User;
import com.company.bankservice.enums.UserStatus;
import com.company.bankservice.enums.errors.UserError;
import com.company.bankservice.events.UserKafkaProducerEvent;
import com.company.bankservice.mappers.UserMapper;
import com.company.bankservice.repositories.pgsql.UserPostgresRepository;
import com.company.bankservice.services.UserCommandService;
import com.company.bankservice.utils.EncryptUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    @Autowired
    UserPostgresRepository userPostgresRepository;

    @Autowired
    UserKafkaProducerEvent userEventKafkaProducer;

    @Autowired
    AccountCommandServiceImpl accountCommandServiceImpl;

    @Autowired
    TransactionCommandServiceImpl transactionCommandServiceImpl;

    private static Logger log = LogManager.getLogger(UserCommandServiceImpl.class);

    private Boolean verifyIfUserExist(String email) {
         try{
             User verifyUser = userPostgresRepository.findByEmail(email);
             if (verifyUser != null) {
                 return true;
             }
         }catch (Error error){
             log.info("Error: {}", UserError.USER_ALREADY_EXIST.toString());
         }
         return false;
    }

    @Override
    public UserResDTO create(UserReqDTO userReqDTO) {
        try {
//            User verifyUser = userMongoRepository.findByEmail(userReqDTO.getEmail());
            if (verifyIfUserExist(userReqDTO.getEmail())){
                log.info("Error: {}", UserError.USER_ALREADY_EXIST.toString());
                return null;
            }

            User user = new User();
            user.setEmail(userReqDTO.getEmail());
            user.setPassword(EncryptUtils.hashBCrypt(userReqDTO.getPassword()));
            user.setName(userReqDTO.getName());
            user.setUserStatus(UserStatus.ENABLED);
            user.setCreatedAt(new Date());

            // Produce Store the User
            User userStored = userPostgresRepository.save(user);

            UserCreateEventMessageDTO userCreateEventMessageDTO = new UserCreateEventMessageDTO();
            userCreateEventMessageDTO.setUserId(UserMapper.userMapper.userToUserResDTO(userStored));
            userCreateEventMessageDTO.generateInitialDepositAccountDTO();

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
            if (verifyIfUserExist(userDepositAccountReqDTO.getEmail())){
                log.info("Error: {}", UserError.USER_ALREADY_EXIST.toString());
                return null;
            }

            //Mapping User
            User user = new User();
            user.setEmail(userDepositAccountReqDTO.getEmail());
            user.setPassword(EncryptUtils.hashBCrypt(userDepositAccountReqDTO.getPassword()));
            user.setEmail(userDepositAccountReqDTO.getEmail());
            user.setName(userDepositAccountReqDTO.getName());
            user.setUserStatus(UserStatus.ENABLED);
            user.setCreatedAt(new Date());

            User userStored = userPostgresRepository.save(user);

            //Mapping UserCreateEventMessageDTO
            UserCreateEventMessageDTO userCreateEventMessageDTO = new UserCreateEventMessageDTO();
            userCreateEventMessageDTO.setUserId(UserMapper.userMapper.userToUserResDTO(userStored));
            userCreateEventMessageDTO.setInitialDepositAccountDTO(userDepositAccountReqDTO.getInitialDeposit());

            Account account = accountCommandServiceImpl.createAccountFromUser(userCreateEventMessageDTO);
            transactionCommandServiceImpl.initializeAccount(account);

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
