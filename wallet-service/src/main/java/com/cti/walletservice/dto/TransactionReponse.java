package com.cti.walletservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionReponse {

    private Long id;
    private String code;
    private String reference;
    private String status;
    private Date created;
    private BigDecimal amount;
}
