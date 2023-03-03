package com.cti.walletservice.exception;

public class TransactionNoFoundException extends  RuntimeException {

    public TransactionNoFoundException (Long transactionId){
        super("Transaction with id "+transactionId+" doesn't exist");
    }
}
