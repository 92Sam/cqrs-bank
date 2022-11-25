package com.company.bankservice.mappers;


import com.company.bankservice.config.dto.resolvers.TransactionReqDTO;
import com.company.bankservice.config.dto.resolvers.TransactionResDTO;
import com.company.bankservice.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

    Transaction transactionReqDTOtoTransaction(TransactionReqDTO transactionReqDTO);

    TransactionResDTO transactionToTransactionResDTO(Transaction transaction);

    @Mapping(target="accountId", source="accountId")
    List<TransactionResDTO> transactionListToTransactionResDTOList(List<Transaction>  transaction);

}
