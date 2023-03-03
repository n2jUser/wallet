package com.cti.walletservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
class ClassExceptionHandler {

        @ExceptionHandler(value = WalletNofoundException.class)
        public ResponseEntity<Object> notFoundException(WalletNofoundException exception) {
            return new ResponseEntity<>("Wallet not found", HttpStatus.NOT_FOUND);
        }


        @ExceptionHandler(value = WalletBadRequestException.class)
        public ResponseEntity<Object> badRequestException(WalletBadRequestException exception) {
            return new ResponseEntity<>("bad Wallet Request :check if user is not having a wallet or any error about informations", HttpStatus.BAD_REQUEST);
        }

    @ExceptionHandler(value = TransactionNoFoundException.class)
    public ResponseEntity<Object> notFoundException(TransactionNoFoundException exception) {
        return new ResponseEntity<>("Transaction not found", HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = TransactionBadRequestException.class)
    public ResponseEntity<Object> badRequestException(TransactionBadRequestException exception) {
        return new ResponseEntity<>("bad transaction Request : check if all your information are correct.", HttpStatus.BAD_REQUEST);
    }

}

