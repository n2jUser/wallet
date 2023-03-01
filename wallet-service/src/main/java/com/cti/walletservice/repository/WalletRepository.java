package com.cti.walletservice.repository;

import com.cti.walletservice.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByCode(String code);
    Optional<Wallet> findById(Long id);
    Wallet findByUserId(Long userId);
    Boolean deleteByCode(String code);

}
