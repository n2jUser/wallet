package com.cti.walletservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tbl_transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder             // construire les objets
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "transaction_code", nullable = false)
    private String code;

    @Column( name = "transaction_reference", nullable = false)
    private String reference;

    @Column( name = "transaction_status", nullable = false)
    private String status;

    @Column( name = "transaction_created", nullable = false)
    private Date created;

    @Column( name = "transaction_amount", nullable = false)
    private BigDecimal amount;

}
