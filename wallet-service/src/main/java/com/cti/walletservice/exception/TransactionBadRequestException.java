package com.cti.walletservice.exception;

public class TransactionBadRequestException extends RuntimeException{

    public TransactionBadRequestException(String message){
        super(" error request : "+message);
    }
}

