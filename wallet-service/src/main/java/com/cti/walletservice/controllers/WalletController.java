package com.cti.walletservice.controllers;

import com.cti.walletservice.dto.WalletReponse;
import com.cti.walletservice.dto.WalletRequest;
import com.cti.walletservice.models.Wallet;
import com.cti.walletservice.services.TransactionService;
import com.cti.walletservice.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<WalletReponse>  createdwallet(@RequestBody WalletRequest walletRequest){
        return walletServices.createWallet (walletRequest);

    }
    @PostMapping("/activate/{userId}")
    public ResponseEntity<WalletReponse>  activateWallet(@PathVariable Long userId){
        return walletServices.activateWallet (userId);

    }

    @PostMapping("/disable/{userId}")
    public ResponseEntity<WalletReponse>  disableteWallet(@PathVariable Long userId){
        return walletServices.disableWallet (userId);

    }

    @GetMapping("/find/Id/{Id}")
    public ResponseEntity<WalletReponse> findById(@PathVariable Long Id){
        return walletServices.findById(Id);

    }

    @GetMapping("/find/UserId/{Id}")
    public ResponseEntity<WalletReponse> findByUserId(@PathVariable Long Id){
        return walletServices.findByUserId(Id);

    }

    @GetMapping("/all")
    public ResponseEntity<List<Wallet>> getWalletts(){
        return walletServices.getAllWallets();
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
