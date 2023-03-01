package com.cti.walletservice.exception;

public class WalletNofoundException extends RuntimeException{

    public WalletNofoundException (Long walletId){
        super("Wallet with id "+walletId+" doesn't exist");
    }
}
