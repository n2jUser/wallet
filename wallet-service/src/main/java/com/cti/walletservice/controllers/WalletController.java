package com.cti.walletservice.controllers;

import com.cti.walletservice.dto.TransactionReponse;
import com.cti.walletservice.dto.TransactionRequest;
import com.cti.walletservice.dto.WalletReponse;
import com.cti.walletservice.dto.WalletRequest;
import com.cti.walletservice.models.Wallet;
import com.cti.walletservice.services.TransactionService;
import com.cti.walletservice.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    WalletService walletServices;

    @Autowired
    TransactionService transactionService;



    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED) // Affiche le status de la requette
    public WalletReponse createdwallet(@RequestBody WalletRequest wallet){
        return walletServices.createWallet(wallet);

    }

    @GetMapping("/all")
    @ResponseStatus(value = HttpStatus.OK)  // afficher le status de la requette
    public List<Wallet> getWalletts(){
        System.out.println("getting all wallets");
        return walletServices.getWallets();
    }

    @GetMapping("/find/id/{id}")
    public Optional<Wallet> getWalletById(@PathVariable Long id){
        System.out.println("getting wallet with id  %f" +id);
        return walletServices.getWalletById(id);

    }

    @GetMapping("/find/code/{walletCode}")
    public Wallet getWalletByCode(@PathVariable String walletCode){
        System.out.println("getting wallet by code %s" + walletCode);
        return walletServices.getwalletByCode(walletCode);

    }

    @PutMapping("/update/send/{walletUserId}")
    public Wallet createdSendTransaction(@RequestBody TransactionRequest transaction, @PathVariable Long walletUserId){
        return  transactionService.walletSendTransaction (transaction, walletUserId);

    }

    @PutMapping("/update/receive/{walletUserId}")
    public Wallet createdReceiveTransaction(@RequestBody TransactionRequest transaction, @PathVariable Long walletUserId){
        return  transactionService.walletReceiveTransaction (transaction, walletUserId);

    }

    @PutMapping("/update")
    public  Wallet updateWallet(@RequestBody WalletRequest walletRequest){
        System.out.println("updating a wallet");
        return walletServices.updateWallet(walletRequest);


    }

    @DeleteMapping("/delete/{id}")
    public List<Wallet> deleteWalletByCode(@PathVariable Long id){
        System.out.println("delete a wallet");
        return walletServices.deleteWalletById(id);

    }


}
