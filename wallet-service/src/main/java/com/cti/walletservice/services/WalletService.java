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
                if(wallet.getIsActive()){
                    throw new WalletBadRequestException(" This wallet was already activated");

                }else{
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
                }
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
                if(wallet.getIsActive()){
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
                    throw new WalletBadRequestException(" This wallet was already activated");
                }
            }else{
                throw new WalletBadRequestException("Wallet with that userId doesn't exist");
            }
        }else{
            throw new WalletBadRequestException("Wallet with that userId doesn't exist");
        }
    }


    public ResponseEntity<WalletReponse> findWalletById(Long id){
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
        if (wallets != null){
            return new ResponseEntity<>(wallets, HttpStatus.OK);
        }else{
            throw new WalletBadRequestException("The request about Wallet list have some problems");
        }
    }


    public ResponseEntity<Optional<Wallet>> getWalletById(Long id){
        Wallet wallet = walletRepository.findById(id).orElseThrow(()-> new WalletNofoundException(id));
        return new ResponseEntity<>(Optional.of(wallet), HttpStatus.OK);
    }


    public ResponseEntity<WalletReponse> findWalletByCode(String code){
        Wallet wallet = walletRepository.findByCode(code);
        if(wallet != null){
            WalletReponse walletReponse = WalletReponse.builder()
                    .id(wallet.getId())
                    .code(wallet.getCode())
                    .isActive(wallet.getIsActive())
                    .message("Success")
                    .balance(wallet.getBalance())
                    .userId(wallet.getUserId())
                    .build();
            return new ResponseEntity<>(walletReponse, HttpStatus.OK);
        }else {

        }
        throw new WalletBadRequestException("Wallet doesn't exist");
    }


    public Wallet getWalletByCode(String code){
        Wallet wallet = walletRepository.findByCode(code);
        if (wallet != null){
            return wallet;
        }else{
            throw new WalletBadRequestException("This wallet code doesn't have wallet");
        }
    }


    // Faire une precision sur les champs qu'on peut modifier lors d'un update
    public  ResponseEntity<WalletReponse> updateWallet(WalletRequest walletRequest){
        Wallet wallet1 = getWalletByCode(walletRequest.getCode());
        if (wallet1 != null){
            if (wallet1.getId() != null){
                Wallet wallet2 = null;
                wallet2 = Wallet.builder()
                            .id(wallet1.getId())
                            .code(walletRequest.getCode())
                            .isActive(wallet1.getIsActive())
                            .balance(wallet1.getBalance())
                            .userId(wallet1.getUserId())
                            .build();
                Wallet wallet3 = walletRepository.save(wallet2);
                if (wallet3 != null){
                    WalletReponse walletReponse = WalletReponse.builder()
                                .id(wallet3.getId())
                                .code(wallet3.getCode())
                                .isActive(wallet3.getIsActive())
                                .message("Successfully updated")
                                .balance(wallet3.getBalance())
                                .userId(wallet3.getUserId())
                                .build();
                    return new ResponseEntity<>(walletReponse, HttpStatus.OK);

                }else {
                    throw new WalletBadRequestException("Wallet doesn't Update");
                    }
            }else{
                throw  new WalletBadRequestException("This wallet doesn't have the id");
            }

        }else {
            throw  new WalletBadRequestException("This wallet doesn't have the id");
        }
    }


    public ResponseEntity<WalletReponse> deleteWalletByCode(String code){

        System.out.println("delete****************************************************************************************************************************** = "+ code);
        Wallet wallet = getWalletByCode(code);
        if (wallet != null){
            Boolean delete = walletRepository.deleteByCode(code);
            if (!delete) {
                WalletReponse walletReponse = WalletReponse.builder()
                        .id(wallet.getId())
                        .code(wallet.getCode())
                        .isActive(wallet.getIsActive())
                        .message("Successfully deleted")
                        .balance(wallet.getBalance())
                        .userId(wallet.getUserId())
                        .build();
                return new ResponseEntity<>(walletReponse, HttpStatus.OK);
            }else{
                throw new WalletBadRequestException("Wallet doesn't delete");
            }
        }else{
            throw  new WalletBadRequestException("This wallet doesn't exist");
        }
    }














    public Wallet getWalletByUserId(Long userId){
        Wallet wallet = walletRepository.findByUserId(userId);
        return wallet;

    }

    public Wallet recharge(Wallet wallet, BigDecimal amount){

        Boolean isValid = validateWallet.ValidateWallet(wallet);
        if(isValid){
            BigDecimal balance = amount.add(wallet.getBalance());
            wallet.setBalance(balance);
        }else {
            throw new WalletBadRequestException("Wallet are blocking");
        }
        return  wallet;
    }

    public Wallet retrait (Wallet wallet, BigDecimal amount){
        Boolean isValid = validateWallet.ValidateWallet(wallet);
        if(isValid){
            BigDecimal balance = wallet.getBalance();
            if (balance.compareTo(amount) > 0){
                BigDecimal balance1 = balance.subtract(amount);
                wallet.setBalance(balance1);
            }else{
                throw new WalletBadRequestException("your balance is insufficient");
            }
        }else {
            throw new WalletBadRequestException("Wallet are blocking");
        }

        return  wallet;

    }

// ecrire la methode
//         walletRepository.findById(id).orElseThrow(()-> new WalletNofoundException(id))
    // pour retourner une exception si on ne trouve pas le id


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