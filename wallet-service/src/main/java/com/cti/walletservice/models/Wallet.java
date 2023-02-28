package com.cti.walletservice.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tbl_wallet")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder             // construire les objets
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column ( name = "wallet_code")
    private String code;

    @Column ( name = "wallet_isBlocked", nullable = false)
    private Boolean isBlocked;

    @NonNull             // pour que le champs ne soit pas null
    @Column ( name = "wallet_balance")
    private BigDecimal balance;

    @Column ( name = "wallet_userId", nullable = false)
    private Long userId;

}
