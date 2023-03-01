package com.cti.walletservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefillDto {

    private Long userId = null;

    private BigDecimal amount = null;
}
