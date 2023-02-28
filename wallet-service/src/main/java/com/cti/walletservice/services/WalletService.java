package com.cti.walletservice.services;

import com.cti.walletservice.dto.WalletReponse;
import com.cti.walletservice.dto.WalletRequest;
import com.cti.walletservice.models.Wallet;
import com.cti.walletservice.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    WalletRepository walletRepository;

    // Logger logger; permet d'afficher dans la console
    public WalletReponse createWallet (WalletRequest wallet){
        WalletRequest wallet1 = WalletRequest.builder() // construction du wallet sans id
                .code(wallet.getCode())
                .isBlocked(wallet.getIsBlocked())
                .balance(wallet.getBalance())
                .userId(wallet.getUserId())
                .build();
        // fin  de la construction d'un wallet sans id

        Wallet walletToSave = mapWalletRequestToWallet(wallet1); // construction d'un wallet ayant un id
        Wallet wallet2 = walletRepository.save(walletToSave); // enregistrement de la base de donnéés
        System.out.println("Id Saved in database{}  %f" + wallet2.getId());

        WalletReponse walletReponse = WalletReponse.builder()
                .id(wallet2.getId())
                .code(wallet.getCode())
                .isBlocked(wallet.getIsBlocked())
                .balance(wallet.getBalance())
                .userId(wallet.getUserId())
                .build();

        return walletReponse;
    }
    public Wallet mapWalletRequestToWallet(WalletRequest walletRequest){
        Wallet wallet = Wallet.builder()
                .code(walletRequest.getCode())
                .isBlocked(walletRequest.getIsBlocked())
                .balance(walletRequest.getBalance())
                .userId(walletRequest.getUserId())
                .build();
        return wallet;
    }

    // returne un ou un false wallet par son id
    public Optional<Wallet> getWalletById(Long id){
        return Optional.of(walletRepository.findById(id).get());
    }
    public List<Wallet> getWallets(){
        List<Wallet> wallets = walletRepository.findAll().stream().toList();
        return wallets;
    }
    public Wallet getwalletByCode(String code){
        Wallet wallet = walletRepository.findByCode(code);
        return wallet;
    }
    public  Wallet updateWallet(WalletRequest wallet){
        Optional<Wallet> wallet1 = Optional.ofNullable(getwalletByCode(wallet.getCode()));
        Wallet wallet2 = null;
        if(wallet1.get().getId() != null){
            wallet2 = Wallet.builder()
                    .id(wallet1.get().getId())
                    .code(wallet.getCode())
                    .isBlocked(wallet.getIsBlocked())
                    .balance(wallet.getBalance())
                    .userId(wallet.getUserId())
                    .build();
            walletRepository.save(wallet2);
        }

        return wallet2;
    }
    public List<Wallet> deleteWalletById(Long id){
        walletRepository.deleteById(id);
        List<Wallet> wallets= getWallets();
        return wallets;

    }



}
