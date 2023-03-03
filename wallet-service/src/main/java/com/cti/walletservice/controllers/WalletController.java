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
        return walletServices.findWalletById(Id);

    }

    @GetMapping("/find/UserId/{Id}")
    public ResponseEntity<WalletReponse> findByUserId(@PathVariable Long Id){
        return walletServices.findByUserId(Id);

    }

    @GetMapping("/all")
    public ResponseEntity<List<Wallet>> getWalletts(){
        return walletServices.getAllWallets();
    }

    @GetMapping("/find/code/{walletCode}")
    public ResponseEntity<WalletReponse> getWalletByCode(@PathVariable String walletCode){
        return walletServices.findWalletByCode(walletCode);
    }


    @PutMapping("/update")
    public  ResponseEntity<WalletReponse> updateWallet(@RequestBody WalletRequest walletRequest){
        return walletServices.updateWallet(walletRequest);
    }


    @DeleteMapping("/delete/{code}")
    public ResponseEntity<WalletReponse> deleteWalletByCode(@PathVariable String code){
        return walletServices.deleteWalletByCode(code);

    }


}
