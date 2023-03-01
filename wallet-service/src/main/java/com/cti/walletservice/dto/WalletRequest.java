package com.cti.walletservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletRequest {

    private String code = "";

    private Boolean isActive = false;

    private BigDecimal balance = BigDecimal.valueOf(0);

    private Long userId = null;
}


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