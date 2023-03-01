package com.cti.walletservice.services;

import com.cti.walletservice.dto.*;
import com.cti.walletservice.models.Transaction;
import com.cti.walletservice.models.Wallet;
import com.cti.walletservice.repository.TransactionRepository;
import com.cti.walletservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    TransactionReponse  currentTransactionResponse;
//
//
//
//    public TransactionReponse createTransaction(RefillDto refillDto){
//        Transaction transaction = Transaction.builder()
//                .
//                .build();
//
//        Transaction transaction1 = transactionRepository.save(transaction);
//
//        TransactionReponse transactionReponse = TransactionReponse.builder()
//                .
//                .build();
//    }
//
//    public Wallet walletSendTransaction (TransactionRequest transaction, Long userId){
//        TransactionRequest transaction1=TransactionRequest.builder()
//                .code(transaction.getCode())
//                .reference(transaction.getReference())
//                .created(transaction.getCreated())
//                .status(transaction.getStatus())
//                .amount(transaction.getAmount())
//                .build();
//        Transaction transactionToSave = mapTransactionRequestToTransaction (transaction);
//
//
//        Wallet wallet = senderTransaction(transaction1, userId);
//
//        if (wallet != null) {
//            walletRepository.save(wallet);
//            Transaction transaction2 = transactionRepository.save(transactionToSave);
//
//            TransactionReponse transactionResponse = TransactionReponse.builder()
//                    .id(transaction2.getId())
//                    .code(transaction2.getCode())
//                    .reference(transaction2.getReference())
//                    .created(transaction2.getCreated())
//                    .status(transaction2.getStatus())
//                    .amount(transaction2.getAmount())
//                    .build();
//            this.currentTransactionResponse = transactionResponse;
//        }
//        return wallet;
//    }
//


//    public Wallet walletReceiveTransaction (TransactionRequest transaction, Long userId){
//        TransactionRequest transaction1=TransactionRequest.builder()
//                .code(transaction.getCode())
//                .reference(transaction.getReference())
//                .created(transaction.getCreated())
//                .status(transaction.getStatus())
//                .amount(transaction.getAmount())
//                .build();
//        Transaction transactionToSave = mapTransactionRequestToTransaction (transaction);
//        Wallet wallet = receiveTransaction(transaction1, userId);
//
//        if (wallet != null) {
//            walletRepository.save(wallet);
//            Transaction transaction2 = transactionRepository.save(transactionToSave);
//            TransactionReponse transactionResponse = TransactionReponse.builder()
//                    .id(transaction2.getId())
//                    .code(transaction2.getCode())
//                    .reference(transaction2.getReference())
//                    .created(transaction2.getCreated())
//                    .status(transaction2.getStatus())
//                    .amount(transaction2.getAmount())
//                    .build();
//            this.currentTransactionResponse = transactionResponse;
//        }
//        return wallet;
//    }

    public TransactionReponse refillTransaction(RefillDto refill){

        Wallet wallet = walletServices.getWalletByUserId(refill.getUserId());
        //init amount
        BigDecimal balance1 = wallet.getBalance();

        //Refill operation
        Wallet wallet2 = walletServices.Recharge(wallet, refill.getAmount());
        BigDecimal balance2 = wallet2.getBalance();

        TransactionReponse transactionReponse = null;

        //changed amount
        if (balance2.compareTo(balance1) > 0 ){
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
             transactionReponse = TransactionReponse.builder()
                    .id(transaction1.getId())
                    .senderId(transaction1.getSenderId())
                    .status(transaction1.getStatus())
                    .created(transaction1.getCreated())
                    .message(transaction1.getStatus() ? "SUCCESS": "FAILED")
                    .amount(transaction1.getAmount())
                    .code(transaction1.getCode())
                    .receiveId(transaction1.getReceivedId())
                    .reference(transaction1.getReference())
                    .build();
        }
        return transactionReponse;
    }

//
//    public TransactionReponse transferTransaction(RefillDto transfer){
//        Wallet wallet = walletServices.getWalletByUserId(refill.getUserId());
//        //init amount
//        BigDecimal balance1 = wallet.getBalance();
//
//        //Refill operation
//        Wallet wallet2 = walletServices.Recharge(wallet, refill.getAmount());
//        BigDecimal balance2 = wallet2.getBalance();
//        TransactionReponse transactionReponse = TransactionReponse.builder().build();
//        //changed amount
//        if (balance2.compareTo(balance1) > 0 ){
//
//            String code = String.valueOf(Math.random());
//            Transaction transaction = Transaction.builder()
//                    .status(true)
//                    .created(new Date())
//                    .receivedId(refill.getUserId())
//                    .senderId(null)
//                    .amount(refill.getAmount())
//                    .reference("Transaction_"+code+"_"+new Date())
//                    .code(code)
//                    .build();
//            Transaction transaction1 = transactionRepository.save(transaction);
//
//            transactionReponse = TransactionReponse.builder()
//                    .id(transaction1.getId())
//                    .senderId(transaction1.getSenderId())
//                    .status(transaction1.getStatus())
//                    .created(transaction1.getCreated())
//                    .message(transaction1.getStatus() ? "SUCCESS": "FAILED")
//                    .amount(transaction1.getAmount())
//                    .code(transaction1.getCode())
//                    .receiveId(transaction1.getReceivedId())
//                    .reference(transaction1.getReference())
//                    .build();
//
//        }else {
//
//        }
//
//        return transactionReponse;
//    }

//    public Transaction mapTransactionRequestToTransaction(TransactionRequest transactionRequest) {
//        Transaction transaction = Transaction.builder()
//                .code(transactionRequest.getCode())
//                .reference(transactionRequest.getReference())
//                .created(transactionRequest.getCreated())
//                .status(transactionRequest.getStatus())
//                .amount(transactionRequest.getAmount())
//                .build();
//        return transaction;
//    }
//

//    public List<Transaction> getTransactions(){
//        List<Transaction> transactions = transactionRepository.findAll().stream().toList();
//        return transactions;
//    }

//    public Optional<Transaction> getTransactionsById(Long id){
//        return Optional.of(transactionRepository.findById(id).get());
//    }
//
//    public Transaction getTransactionByCode(String code){
//        Transaction transaction = transactionRepository.findByCode(code);
//        return transaction;
//    }
//
//    public List<Transaction> deleteTransactionById(Long id){
//        transactionRepository.deleteById(id);
//        List<Transaction> transactions= getTransactions();
//        return transactions;
//
//    }
//
//    // ici c'est l'id de la classe utilisateur du microservice utilisateur
//
//    public  Wallet senderTransaction(TransactionRequest transactionRequest, Long userId) {
//        Optional<Wallet> wallet1 = Optional.ofNullable(walletServices.getWalletByUserId(userId)); // recuperation du wallet a partir du useId
//
//        Wallet wallet2 = null;
//        if (wallet1.get().getId() != null) {
//            BigDecimal currentBalance = wallet1.get().getBalance(); // recuperation du montant du wallet
//            BigDecimal balance = currentBalance.subtract(transactionRequest.getAmount()); // update du montant
//            wallet2 = Wallet.builder()
//                    .id(wallet1.get().getId())
//                    .code(wallet1.get().getCode())
//                    .isBlocked(wallet1.get().getIsBlocked())
//                    .balance(balance)
//                    .userId(wallet1.get().getUserId())
//                    .build();
//        }
//        return wallet2;
//
//    }
//
//    public  Wallet receiveTransaction(TransactionRequest transactionRequest, Long userId) {
//        Optional<Wallet> wallet1 = Optional.ofNullable(walletServices.getWalletByUserId(userId)); // recuperation du wallet a partir du useId
//        Wallet wallet2 = null;
//        if (wallet1.get().getId() != null) {
//            BigDecimal currentBalance = wallet1.get().getBalance(); // recuperation du montant du wallet
//            BigDecimal balance = currentBalance.add(transactionRequest.getAmount()); // update du montant
//            wallet2 = Wallet.builder()
//                    .id(wallet1.get().getId())
//                    .code(wallet1.get().getCode())
//                    .isBlocked(wallet1.get().getIsBlocked())
//                    .balance(balance)
//                    .userId(wallet1.get().getUserId())
//                    .build();
//        }
//        return wallet2;
//
//    }


}

