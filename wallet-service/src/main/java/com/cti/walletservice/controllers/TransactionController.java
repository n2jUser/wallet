package com.cti.walletservice.controllers;

import com.cti.walletservice.dto.TransactionReponse;
import com.cti.walletservice.dto.TransactionRequest;
import com.cti.walletservice.models.Transaction;
import com.cti.walletservice.models.Wallet;
import com.cti.walletservice.repository.TransactionRepository;
import com.cti.walletservice.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    private TransactionRepository transactionRepository;

    //@PostMapping("/create")
    //@ResponseStatus(value = HttpStatus.CREATED)
    public TransactionReponse createdtransaction(){
        return transactionService.createTransaction();

    }

    @GetMapping("/all")
    @ResponseStatus(value = HttpStatus.OK)  // afficher le status de la requette
    public List<Transaction> getTransactions(){
        System.out.println("getting all Transaction");
        return transactionService.getTransactions();
    }

    @GetMapping("/find/id/{id}")
    public Optional<Transaction> getWalletById(@PathVariable Long id){
        System.out.println("getting wallet with id  %f" +id);
        return transactionService.getTransactionsById(id);

    }

    @GetMapping("/find/code/{transactionCode}")
    public Transaction getTransactionByCode(@PathVariable String transactionCode){
        System.out.println("getting transaction by code %s" + transactionCode);
        return transactionService.getTransactionByCode(transactionCode);

    }

    @DeleteMapping("/delete/{id}")
    public List<Transaction> deleteWalletByCode(@PathVariable Long id){
        System.out.println("delete a wallet");
        return transactionService.deleteTransactionById(id);

    }

}
