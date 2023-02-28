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

    private String code;
    private Boolean isBlocked;
    private BigDecimal balance;
    private Long userId;
}
