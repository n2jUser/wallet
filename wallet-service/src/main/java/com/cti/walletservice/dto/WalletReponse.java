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
public class WalletReponse {

    private Long id = null;

    private String code = "";

    private Boolean isActive = false;

    private BigDecimal balance = BigDecimal.valueOf(0);

    private Long userId = null;

    private String message = "";
}
