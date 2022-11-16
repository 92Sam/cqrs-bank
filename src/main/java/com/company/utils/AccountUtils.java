package com.company.utils;

import com.company.bankservice.entities.Account;
import com.company.bankservice.entities.Transaction;
import com.company.bankservice.services.impl.AccountCommandServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

@Getter
@Setter
public class AccountUtils {

    private static Logger log = LogManager.getLogger(AccountUtils.class);

    public static String generateString(){
        return UUID.randomUUID().toString().toUpperCase();
    }

    public static Account calculateBalanceAndCreditAvailable(Account account, Transaction transaction){
        Float actualBalance = account.getBalance();
        Float actualCreditAvailable = account.getCreditAvailable();

        // Evaluate Debits
        if (transaction.getAmount() < 0){
            if(actualBalance<=0F && actualCreditAvailable == 0) {
                log.info("You dont have credit enough");
                return null;
            }else if(actualBalance<=0F && actualCreditAvailable > 0){
                actualCreditAvailable+=transaction.getAmount();
                if(actualCreditAvailable<0){
                    log.info("You dont have credit enough");
                    return null;
                }
                account.setCreditAvailable(actualCreditAvailable);
                account.setBalance(convertNegativeValue(account.getCreditAmount()-actualCreditAvailable));

            }else if(actualBalance > 0){
                actualBalance+=transaction.getAmount();
                if(actualBalance<0){
                    actualBalance+=actualCreditAvailable;
                    if(actualBalance<0){
                        return null;
                    }
                    account.setCreditAvailable(actualBalance);
                    account.setBalance(convertNegativeValue(account.getCreditAmount()-actualBalance));
                    return account;
                }else{
                    account.setBalance(actualBalance);
                }
            }
        }else{
            //Evaluate Credits aditions
            if(actualBalance<0F){
                actualBalance+=transaction.getAmount();
                if (actualBalance >= 0){
                    account.setCreditAvailable(account.getCreditAmount());
                }else{
                    actualCreditAvailable=account.getCreditAmount()-actualBalance;
                    account.setCreditAvailable(actualCreditAvailable);
                }
                account.setBalance(actualBalance);
            }else{
                actualBalance+=transaction.getAmount();
                account.setBalance(actualBalance);
            }
        }

        return account;
    }

    public static Float convertNegativeValue(Float number) {
        return (number*(-1));
    }
}
