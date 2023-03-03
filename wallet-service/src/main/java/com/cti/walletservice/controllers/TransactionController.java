package com.cti.walletservice.controllers;

import com.cti.walletservice.dto.RefillDto;
import com.cti.walletservice.dto.TransactionRequest;
import com.cti.walletservice.dto.TransactionResponse;
import com.cti.walletservice.models.Transaction;
import com.cti.walletservice.services.TransactionService;
import com.cti.walletservice.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    // getting all Transaction
    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getTransactions(){
        return transactionService.getTransactions();
    }

    // getting wallet with id
    @GetMapping("/find/id/{id}")
    public ResponseEntity<Optional<Transaction>> getWalletById(@PathVariable Long id){
        return transactionService.findTransactionById(id);
    }

    // getting transaction by code %s
    @GetMapping("/find/code/{transactionCode}")
    public ResponseEntity<Transaction> findTransactionByCode(@PathVariable String transactionCode){
        return transactionService.findTransactionByCode(transactionCode);
    }
//
//    @DeleteMapping("/delete/{id}")
//    public List<Transaction> deleteWalletByCode(@PathVariable Long id){
//        System.out.println("delete a wallet");
//        return transactionService.deleteTransactionById(id);
//
//    }

    @PostMapping("/refill")
    public ResponseEntity<TransactionResponse> refillTransaction(@RequestBody RefillDto refill){
        return transactionService.refillTransaction(refill);
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> transaction (@RequestBody TransactionRequest transactionRequest){
        return transactionService.transactionOperation(transactionRequest);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<TransactionResponse> withdrawalTransaction (@RequestBody TransactionRequest transactionRequest){
        return transactionService.withdrawalTransaction(transactionRequest);
    }


//
//    @PostMapping("/transfer")
//    public TransactionReponse transferTransaction(@RequestBody RefillDto transfer){
//        return transactionService.transferTransaction(transfer);
//    }

}
