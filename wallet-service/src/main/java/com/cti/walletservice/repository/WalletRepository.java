package com.cti.walletservice.repository;

import com.cti.walletservice.models.Wallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByCode(String code);
    Wallet findByUserId(Long userId);
    Boolean deleteByCode(String code);

}
