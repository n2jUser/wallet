package com.cti.walletservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WalletServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletServiceApplication.class, args);
	}

}

// recuperer toutes les trasaction
// recupere toutes les trasaction avec le status completed
// rcuperer les transaction en fonction du code
// recuperer un trasction en fonction de l'id
