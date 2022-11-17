package com.company.bankservice.mappers;

import com.company.bankservice.dto.events.AccountCreateEventMessageDTO;
import com.company.bankservice.dto.events.UserCreateEventMessageDTO;
import com.company.bankservice.dto.resolvers.AccountReqDTO;
import com.company.bankservice.dto.resolvers.AccountResDTO;
import com.company.bankservice.dto.resolvers.TransactionResDTO;
import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccountMapper {
    AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);

    Account accountReqDTOtoAccount(AccountReqDTO accountReqDTO);
    AccountResDTO accountToAccountResDTO(Account account);
    List<AccountResDTO> accountListToAccountResDTOList(List<Account>  accounts);
    Account userCreateEventMessageDTOtoAccount(UserCreateEventMessageDTO userCreateEventMessageDTO);
    AccountCreateEventMessageDTO accountToAccountCreateEventMessageDTO(Account account);
}
