package com.cti.walletservice.controllers;

import com.cti.walletservice.dto.RefillDto;
import com.cti.walletservice.dto.TransactionReponse;
import com.cti.walletservice.dto.TransactionRequest;
import com.cti.walletservice.models.Transaction;
import com.cti.walletservice.models.Wallet;
import com.cti.walletservice.repository.TransactionRepository;
import com.cti.walletservice.repository.WalletRepository;
import com.cti.walletservice.services.TransactionService;
import com.cti.walletservice.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    TransactionService transactionService;

//    @Autowired
//    private TransactionRepository transactionRepository;

//    @Autowired
//    public final WalletService walletService;
//    private final WalletRepository walletRepository;


//    //@PostMapping("/create")
//    //@ResponseStatus(value = HttpStatus.CREATED)
//    public TransactionReponse createdtransaction(){
//        return transactionService.createTransaction();
//
//    }
//
//    @PutMapping("/update/receive/{walletUserId}")
//    public Wallet createdReceiveTransaction(@RequestBody TransactionRequest transaction, @PathVariable Long walletUserId){
//        return  transactionService.walletReceiveTransaction (transaction, walletUserId);
//
//    }
//
//    @PutMapping("/update/send/{walletUserId}")
//    public Wallet createdSendTransaction(@RequestBody TransactionRequest transaction, @PathVariable Long walletUserId){
//        return  transactionService.walletSendTransaction (transaction, walletUserId);
//
//    }
//
//    @GetMapping("/all")
//    @ResponseStatus(value = HttpStatus.OK)  // afficher le status de la requette
//    public List<Transaction> getTransactions(){
//        System.out.println("getting all Transaction");
//        return transactionService.getTransactions();
//    }
//
//    @GetMapping("/find/id/{id}")
//    public Optional<Transaction> getWalletById(@PathVariable Long id){
//        System.out.println("getting wallet with id  %f" +id);
//        return transactionService.getTransactionsById(id);
//
//    }
//
//    @GetMapping("/find/code/{transactionCode}")
//    public Transaction getTransactionByCode(@PathVariable String transactionCode){
//        System.out.println("getting transaction by code %s" + transactionCode);
//        return transactionService.getTransactionByCode(transactionCode);
//
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public List<Transaction> deleteWalletByCode(@PathVariable Long id){
//        System.out.println("delete a wallet");
//        return transactionService.deleteTransactionById(id);
//
//    }

    @PostMapping("/refill")
    public TransactionReponse refillTransaction(@RequestBody RefillDto refill){
        return transactionService.refillTransaction(refill);
    }
//
//    @PostMapping("/transfer")
//    public TransactionReponse transferTransaction(@RequestBody RefillDto transfer){
//        return transactionService.transferTransaction(transfer);
//    }

}
