package com.cti.walletservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WalletServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletServiceApplication.class, args);
	}

}

// une methode qui retourne un utilisateur a partir de userId
// on recupere son montant
// on recupere le wallet de celui qui envoie
// le wallet de celui qui recoit
