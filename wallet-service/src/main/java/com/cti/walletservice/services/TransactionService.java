package com.cti.walletservice.services;

import com.cti.walletservice.dto.TransactionReponse;
import com.cti.walletservice.dto.TransactionRequest;
import com.cti.walletservice.dto.WalletReponse;
import com.cti.walletservice.dto.WalletRequest;
import com.cti.walletservice.models.Transaction;
import com.cti.walletservice.models.Wallet;
import com.cti.walletservice.repository.TransactionRepository;
import com.cti.walletservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    WalletRepository walletRepository;

    TransactionReponse  currentTransactionResponse;

    public Wallet walletSendTransaction (TransactionRequest transaction, Long userId){
        TransactionRequest transaction1=TransactionRequest.builder()
                .code(transaction.getCode())
                .reference(transaction.getReference())
                .created(transaction.getCreated())
                .status(transaction.getStatus())
                .amount(transaction.getAmount())
                .build();
        Transaction transactionToSave = mapTransactionRequestToTransaction (transaction);
        Wallet wallet = senderTransaction(transaction1, userId);

        if (wallet != null) {
            walletRepository.save(wallet);
            Transaction transaction2 = transactionRepository.save(transactionToSave);
            TransactionReponse transactionResponse = TransactionReponse.builder()
                    .id(transaction2.getId())
                    .code(transaction2.getCode())
                    .reference(transaction2.getReference())
                    .created(transaction2.getCreated())
                    .status(transaction2.getStatus())
                    .amount(transaction2.getAmount())
                    .build();
            this.currentTransactionResponse = transactionResponse;
        }
        return wallet;
    }

    public Wallet walletReceiveTransaction (TransactionRequest transaction, Long userId){
        TransactionRequest transaction1=TransactionRequest.builder()
                .code(transaction.getCode())
                .reference(transaction.getReference())
                .created(transaction.getCreated())
                .status(transaction.getStatus())
                .amount(transaction.getAmount())
                .build();
        Transaction transactionToSave = mapTransactionRequestToTransaction (transaction);
        Wallet wallet = receiveTransaction(transaction1, userId);

        if (wallet != null) {
            walletRepository.save(wallet);
            Transaction transaction2 = transactionRepository.save(transactionToSave);
            TransactionReponse transactionResponse = TransactionReponse.builder()
                    .id(transaction2.getId())
                    .code(transaction2.getCode())
                    .reference(transaction2.getReference())
                    .created(transaction2.getCreated())
                    .status(transaction2.getStatus())
                    .amount(transaction2.getAmount())
                    .build();
            this.currentTransactionResponse = transactionResponse;
        }
        return wallet;
    }

    public TransactionReponse createTransaction(){
        return this.currentTransactionResponse;
    }

    public Transaction mapTransactionRequestToTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = Transaction.builder()
                .code(transactionRequest.getCode())
                .reference(transactionRequest.getReference())
                .created(transactionRequest.getCreated())
                .status(transactionRequest.getStatus())
                .amount(transactionRequest.getAmount())
                .build();
        return transaction;
    }


    public List<Transaction> getTransactions(){
        List<Transaction> transactions = transactionRepository.findAll().stream().toList();
        return transactions;
    }

    public Optional<Transaction> getTransactionsById(Long id){
        return Optional.of(transactionRepository.findById(id).get());
    }

    public Transaction getTransactionByCode(String code){
        Transaction transaction = transactionRepository.findByCode(code);
        return transaction;
    }

    public List<Transaction> deleteTransactionById(Long id){
        transactionRepository.deleteById(id);
        List<Transaction> transactions= getTransactions();
        return transactions;

    }

    // ici c'est l'id de la classe utilisateur du microservice utilisateur

    public  Wallet senderTransaction(TransactionRequest transactionRequest, Long userId) {
        Optional<Wallet> wallet1 = Optional.ofNullable(getWalletByUserId(userId)); // recuperation du wallet a partir du useId
        Wallet wallet2 = null;
        if (wallet1.get().getId() != null) {
            BigDecimal currentBalance = wallet1.get().getBalance(); // recuperation du montant du wallet
            BigDecimal balance = currentBalance.subtract(transactionRequest.getAmount()); // update du montant
            wallet2 = Wallet.builder()
                    .id(wallet1.get().getId())
                    .code(wallet1.get().getCode())
                    .isBlocked(wallet1.get().getIsBlocked())
                    .balance(balance)
                    .userId(wallet1.get().getUserId())
                    .build();
        }
        return wallet2;

    }

    public  Wallet receiveTransaction(TransactionRequest transactionRequest, Long userId) {
        Optional<Wallet> wallet1 = Optional.ofNullable(getWalletByUserId(userId)); // recuperation du wallet a partir du useId
        Wallet wallet2 = null;
        if (wallet1.get().getId() != null) {
            BigDecimal currentBalance = wallet1.get().getBalance(); // recuperation du montant du wallet
            BigDecimal balance = currentBalance.add(transactionRequest.getAmount()); // update du montant
            wallet2 = Wallet.builder()
                    .id(wallet1.get().getId())
                    .code(wallet1.get().getCode())
                    .isBlocked(wallet1.get().getIsBlocked())
                    .balance(balance)
                    .userId(wallet1.get().getUserId())
                    .build();
        }
        return wallet2;

    }

    public Wallet getWalletByUserId(Long userId){
        Wallet wallet = walletRepository.findByUserId(userId);
        return wallet;

    }
}

