package com.cti.walletservice.utility;

import com.cti.walletservice.models.Wallet;
import com.cti.walletservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateWallet {
    @Autowired
    WalletRepository walletRepository;

    public Boolean ValidateWallet( Wallet wallet){
       Boolean isValid = false;

       if (wallet.getId() != null){
           Wallet wallet1 = walletRepository.findById(wallet.getId()).get();

           if (wallet1 != null && wallet1.getUserId() != null && wallet1.getIsActive() != false){
               isValid = true;
           }else{
               isValid = false;
           }
       }else {
           isValid = false;
       }

       return isValid;

    }
}
