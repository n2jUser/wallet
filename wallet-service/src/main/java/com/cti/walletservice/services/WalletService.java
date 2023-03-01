package com.cti.walletservice.services;

import com.cti.walletservice.dto.WalletReponse;
import com.cti.walletservice.dto.WalletRequest;
import com.cti.walletservice.exception.WalletBadRequestException;
import com.cti.walletservice.exception.WalletNofoundException;
import com.cti.walletservice.models.Wallet;
import com.cti.walletservice.repository.WalletRepository;
import com.cti.walletservice.utility.ValidateWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    ValidateWallet validateWallet;

    /**
     * pour creer un wallet
     * dans la methode walletResponse du service
     * 1.on creer un walletResponse
     * 2.on teste si le userId recu de la requette n'est pas null
     * dans le if
     *  3. on verifie si le wallet existe deja ou pas
     *  4.on teste s'il existe deja dans la BD, s'il existe on fait un 'throw new' WalletBadRequestException("This user already has a wallet")
     *  else on continue la creation
     *      on fait une intense de wallet walletToSave
     *      on lui attribue un code une balance a 0, on met son isBlocked a true
     *      on enregistre le wallet dans le repository et elle nous retoourne un wallet
     *      on teste s'il ce wallet a un id
     *          a partir de ce retour on creer le walletResponse
     *      si non on fait un WalletBadRequestException
     */
    public ResponseEntity<WalletReponse>  createWallet (WalletRequest walletRequest){

        WalletReponse walletReponse;

        if ( walletRequest.getUserId() != null){

            Wallet wallet1 = walletRepository.findByUserId(walletRequest.getUserId());

            if (wallet1 != null){
                throw new WalletBadRequestException("This wallet already exist in dataBase");
            }else{
                Wallet walletToSave = new Wallet();
                walletToSave = Wallet.builder()
                        .code("CTI"+new Date())
                        .isActive(false)
                        .balance(BigDecimal.valueOf(0))
                        .userId(walletRequest.getUserId())
                        .build();
                Wallet wallet = walletRepository.save(walletToSave);
                if (wallet.getId() != null){
                    walletReponse = WalletReponse.builder()
                    .id(wallet.getId())
                    .code(wallet.getCode())
                    .isActive(wallet.getIsActive())
                    .message("Success")
                    .balance(wallet.getBalance())
                    .userId(wallet.getUserId())
                    .build();

                    return new ResponseEntity<>(walletReponse, HttpStatus.CREATED);

                }else {
                    throw new WalletBadRequestException("Wallet was not registered successfully");
                }
            }
        }else {
            throw new WalletBadRequestException("This wallet don't have userId");
        }

    }
    //Active un wallet

    public ResponseEntity<WalletReponse> activateWallet(Long walletUserId){

        Wallet wallet = walletRepository.findByUserId(walletUserId);

        if (wallet != null){
            if (wallet.getId() != null){
                wallet.setIsActive(true);

                Wallet wallet1 = walletRepository.save(wallet);
                WalletReponse walletReponse = WalletReponse.builder()
                        .id(wallet1.getId())
                        .code(wallet1.getCode())
                        .isActive(wallet1.getIsActive())
                        .message("Success")
                        .balance(wallet1.getBalance())
                        .userId(wallet1.getUserId())
                        .build();

                return new ResponseEntity<>(walletReponse, HttpStatus.OK);
            }else{
                throw new WalletBadRequestException("Wallet with that userId doesn't exist");
            }

        }else{
            throw new WalletBadRequestException("Wallet with that userId doesn't exist");
        }


    }

    //Desactive un wallet

    public ResponseEntity<WalletReponse> disableWallet(Long walletUserId){

        Wallet wallet = walletRepository.findByUserId(walletUserId);

        if (wallet != null){
            if (wallet.getId() != null){
                wallet.setIsActive(false);

                Wallet wallet1 = walletRepository.save(wallet);
                WalletReponse walletReponse = WalletReponse.builder()
                        .id(wallet1.getId())
                        .code(wallet1.getCode())
                        .isActive(wallet1.getIsActive())
                        .message("Success")
                        .balance(wallet1.getBalance())
                        .userId(wallet1.getUserId())
                        .build();

                return new ResponseEntity<>(walletReponse, HttpStatus.OK);
            }else{
                throw new WalletBadRequestException("Wallet with that userId doesn't exist");
            }

        }else{
            throw new WalletBadRequestException("Wallet with that userId doesn't exist");
        }


    }


    public ResponseEntity<WalletReponse> findById(Long id){
        Wallet wallet = walletRepository.findById(id).orElseThrow(()-> new WalletNofoundException(id));
        WalletReponse walletReponse = WalletReponse.builder()
                .id(wallet.getId())
                .code(wallet.getCode())
                .isActive(wallet.getIsActive())
                .message("Success")
                .balance(wallet.getBalance())
                .userId(wallet.getUserId())
                .build();

        return new ResponseEntity<>(walletReponse, HttpStatus.OK);

    }

    public ResponseEntity<WalletReponse> findByUserId(Long id){
        Wallet wallet = walletRepository.findByUserId(id);
        if (wallet != null) {
            WalletReponse walletReponse = WalletReponse.builder()
                    .id(wallet.getId())
                    .code(wallet.getCode())
                    .isActive(wallet.getIsActive())
                    .message("Success")
                    .balance(wallet.getBalance())
                    .userId(wallet.getUserId())
                    .build();

            return new ResponseEntity<>(walletReponse, HttpStatus.OK);
        }else{
            throw new WalletBadRequestException("Wallet doesn't exist");
        }

    }

    public ResponseEntity<List<Wallet>> getAllWallets(){

        List<Wallet> wallets = walletRepository.findAll().stream().toList();
        return new ResponseEntity<>(wallets, HttpStatus.OK);

    }



    // Logger logger; permet d'afficher dans la console
//    public WalletReponse createWallet (WalletRequest wallet){
//        WalletRequest wallet1 = WalletRequest.builder() // construction du wallet sans id
//                .code(wallet.getCode())
//                .isBlocked(wallet.getIsBlocked())
//                .balance(wallet.getBalance())
//                .userId(wallet.getUserId())
//                .build();
//        // fin  de la construction d'un wallet sans id
//
//        Wallet walletToSave = mapWalletRequestToWallet(wallet1); // construction d'un wallet ayant un id
//        Wallet wallet2 = walletRepository.save(walletToSave); // enregistrement de la base de donnéés
//        System.out.println("Id Saved in database{}  %f" + wallet2.getId());
//
//        WalletReponse walletReponse = WalletReponse.builder()
//                .id(wallet2.getId())
//                .code(wallet.getCode())
//                .isBlocked(wallet.getIsBlocked())
//                .balance(wallet.getBalance())
//                .userId(wallet.getUserId())
//                .build();
//
//        return walletReponse;
//    }
//    public Wallet mapWalletRequestToWallet(WalletRequest walletRequest){
//        Wallet wallet = Wallet.builder()
//                .code(walletRequest.getCode())
//                .isBlocked(walletRequest.getIsBlocked())
//                .balance(walletRequest.getBalance())
//                .userId(walletRequest.getUserId())
//                .build();
//        return wallet;
//    }

    // returne un ou un false wallet par son id
    public Optional<Wallet> getWalletById(Long id){
        return Optional.of(walletRepository.findById(id).get());
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
                    .isActive(wallet.getIsActive())
                    .balance(wallet.getBalance())
                    .userId(wallet.getUserId())
                    .build();
            walletRepository.save(wallet2);
        }

        return wallet2;
    }
    public List<Wallet> deleteWalletById(Long id){
        walletRepository.deleteById(id);
        List<Wallet> wallets= (List<Wallet>) getAllWallets();
        return wallets;
    }

    public Wallet getWalletByUserId(Long userId){
        Wallet wallet = walletRepository.findByUserId(userId);
        return wallet;

    }

    public Wallet Recharge(Wallet wallet, BigDecimal amount){

        Boolean isValid = validateWallet.ValidateWallet(wallet);
        if(isValid){
            BigDecimal balance = amount.add(wallet.getBalance());
            wallet.setBalance(balance);
            walletRepository.save(wallet);
        }
        return  wallet;
    }

    public Wallet Retrait(Wallet wallet, BigDecimal amount){
        Boolean isValid = validateWallet.ValidateWallet(wallet);
        if(isValid){
            BigDecimal balance = amount.subtract(wallet.getBalance());
            wallet.setBalance(balance);
            walletRepository.save(wallet);
        }

        return  wallet;

    }

// ecrire la methode
//         walletRepository.findById(id).orElseThrow(()-> new WalletNofoundException(id))
    // pour retourner une exception si on ne trouve pas le id


}
