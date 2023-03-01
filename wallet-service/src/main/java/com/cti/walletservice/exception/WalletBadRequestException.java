package com.cti.walletservice.exception;

import com.cti.walletservice.dto.WalletRequest;

public class WalletBadRequestException extends RuntimeException{

    public WalletBadRequestException(String message){
        super(" error request : "+message);
    }
}
