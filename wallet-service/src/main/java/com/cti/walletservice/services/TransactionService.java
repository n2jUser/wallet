package com.cti.walletservice.services;

import com.cti.walletservice.dto.*;
import com.cti.walletservice.exception.TransactionBadRequestException;
import com.cti.walletservice.exception.TransactionNoFoundException;
import com.cti.walletservice.exception.WalletBadRequestException;
import com.cti.walletservice.exception.WalletNofoundException;
import com.cti.walletservice.models.Transaction;
import com.cti.walletservice.models.Wallet;
import com.cti.walletservice.repository.TransactionRepository;
import com.cti.walletservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    WalletService walletServices;

    @Autowired
    WalletRepository walletRepository;


    public ResponseEntity<TransactionResponse> transactionOperation (TransactionRequest transactionRequest){
        Wallet walletSender = walletRepository.findByUserId(transactionRequest.getSenderId());
        Wallet walletReceiver = walletRepository.findByUserId(transactionRequest.getReceiveId());
        if (walletSender != null && walletReceiver != null){
            //init amount
            BigDecimal balanceSender1 = walletSender.getBalance();
            BigDecimal balanceReceiver1 = walletReceiver.getBalance();

            // withdrawal operation
            Wallet walletSender1 = walletServices.retrait(walletSender, transactionRequest.getAmount());
            BigDecimal balanceSender2 = walletSender1.getBalance();

            // deposit operation
            Wallet walletReceiver1 = walletServices.recharge(walletReceiver, transactionRequest.getAmount());
            BigDecimal balanceReceiver2 = walletReceiver1.getBalance();

            if (balanceSender2.compareTo(balanceSender1) < 0 && balanceReceiver2.compareTo(balanceReceiver1) > 0){
                walletRepository.save(walletSender1);
                walletRepository.save(walletReceiver1);

                String code = String.valueOf(Math.random());
                Transaction transaction = Transaction.builder()
                        .status(true)
                        .created(new Date())
                        .receivedId(transactionRequest.getReceiveId())
                        .senderId(transactionRequest.getSenderId())
                        .amount(transactionRequest.getAmount())
                        .reference("Transaction_"+code+"_"+new Date())
                        .code(code)
                        .build();
                Transaction transaction1 = transactionRepository.save(transaction);
                TransactionResponse transactionResponse = TransactionResponse.builder()
                        .id(transaction1.getId())
                        .senderId(transaction1.getSenderId())
                        .created(transaction1.getCreated())
                        .message(transaction1.getStatus() ? "SUCCESS": "FAILED")
                        .amount(transaction1.getAmount())
                        .code(transaction1.getCode())
                        .receiveId(transaction1.getReceivedId())
                        .reference(transaction1.getReference())
                        .build();
                return new ResponseEntity<>(transactionResponse, HttpStatus.OK);

            }else{
                throw new WalletBadRequestException("Transaction failed");
            }
        }else{
            throw new WalletBadRequestException("Wallet doesn't not exist");
        }



    }
    public ResponseEntity<TransactionResponse> refillTransaction(RefillDto refill){

        Wallet wallet = walletServices.getWalletByUserId(refill.getUserId());

        if (wallet != null) {

            //init amount
            BigDecimal balance1 = wallet.getBalance();

            //Refill operation
            Wallet wallet2 = walletServices.recharge(wallet, refill.getAmount());
            BigDecimal balance2 = wallet2.getBalance();

            //changed amount
            if (balance2.compareTo(balance1) > 0 ){
                walletRepository.save(wallet);
                String code = String.valueOf(Math.random());
                Transaction transaction = Transaction.builder()
                        .status(true)
                        .created(new Date())
                        .receivedId(refill.getUserId())
                        .senderId(null)
                        .amount(refill.getAmount())
                        .reference("Transaction_"+code+"_"+new Date())
                        .code(code)
                        .build();
                Transaction transaction1 = transactionRepository.save(transaction);
                TransactionResponse transactionResponse = TransactionResponse.builder()
                        .id(transaction1.getId())
                        .senderId(transaction1.getSenderId())
                        .created(transaction1.getCreated())
                        .message(transaction1.getStatus() ? "SUCCESS": "FAILED")
                        .amount(transaction1.getAmount())
                        .code(transaction1.getCode())
                        .receiveId(transaction1.getReceivedId())
                        .reference(transaction1.getReference())
                        .build();
                return  new ResponseEntity<>(transactionResponse, HttpStatus.OK);
            }else {
                throw new WalletBadRequestException("Transaction failed");
            }
        }else {
            throw new WalletBadRequestException("Wallet doesn't exist");
        }
    }


    public ResponseEntity<TransactionResponse> withdrawalTransaction(TransactionRequest transactionRequest){

        Wallet wallet = walletServices.getWalletByUserId(transactionRequest.getSenderId());

        if (wallet != null) {

            //init amount
            BigDecimal balance1 = wallet.getBalance();

            //Withdrawal operation
            Wallet wallet2 = walletServices.retrait(wallet, transactionRequest.getAmount());
            BigDecimal balance2 = wallet2.getBalance();

            //changed amount
            if (balance2.compareTo(balance1) < 0 ){
                walletRepository.save(wallet);
                String code = String.valueOf(Math.random());
                Transaction transaction = Transaction.builder()
                        .status(true)
                        .created(new Date())
                        .receivedId(null)
                        .senderId(transactionRequest.getSenderId())
                        .amount(transactionRequest.getAmount())
                        .reference("Transaction_"+code+"_"+new Date())
                        .code(code)
                        .build();
                Transaction transaction1 = transactionRepository.save(transaction);
                TransactionResponse transactionResponse = TransactionResponse.builder()
                        .id(transaction1.getId())
                        .senderId(transaction1.getSenderId())
                        .created(transaction1.getCreated())
                        .message(transaction1.getStatus() ? "SUCCESS": "FAILED")
                        .amount(transaction1.getAmount())
                        .code(transaction1.getCode())
                        .receiveId(transaction1.getReceivedId())
                        .reference(transaction1.getReference())
                        .build();
                return  new ResponseEntity<>(transactionResponse, HttpStatus.OK);
            }else {
                throw new TransactionBadRequestException("Transaction failed");
            }
        }else {
            throw new TransactionBadRequestException("Wallet doesn't exist");
        }
    }


    public ResponseEntity<List<Transaction>> getTransactions(){
        List<Transaction> transactions = transactionRepository.findAll().stream().toList();
        if (transactions != null){
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }else {
            throw new WalletBadRequestException(" transaction table are empty");
        }
    }

    public Optional<Transaction> getTransactionsById(Long id){
        return Optional.of(transactionRepository.findById(id).get());
    }

    public ResponseEntity<Optional<Transaction>> findTransactionById (Long id){
        Optional<Transaction> transaction = getTransactionsById(id);
        if (transaction != null) {
            if (transaction.get().getId() != null) {
                return new ResponseEntity<>(transaction, HttpStatus.OK);
            }else {
                throw new TransactionBadRequestException("This transaction doesn't have id");
            }
        }else {

            throw new TransactionBadRequestException("Transaction with id "+id+" doesn't exist");
        }
    }

    public Transaction getTransactionByCode(String code){
        Transaction transaction = transactionRepository.findByCode(code);
        return transaction;
    }

    public ResponseEntity<Transaction> findTransactionByCode(String code){
        Transaction transaction = transactionRepository.findByCode(code);
        if (transaction != null) {
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        }else {
            throw new TransactionBadRequestException("Transaction with code "+code+" doesn't exist");
        }
    }
//
//    public List<Transaction> deleteTransactionById(Long id){
//        transactionRepository.deleteById(id);
//        List<Transaction> transactions= getTransactions();
//        return transactions;
//
//    }



}

