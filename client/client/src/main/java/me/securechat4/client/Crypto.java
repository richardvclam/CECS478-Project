package me.securechat4.client;

import javax.crypto.SecretKey;

public class Crypto {
	
	public static void encrypt(String message, String rsaPublicKey) {
		AES aes = new AES();
		
		String encryptedMessage = aes.encrypt(message);
		

		System.out.println(encryptedMessage);
	}
	
	public static void decrypt(String jsonObj, String rsaPrivateKey) {
		
		SecretKey key = null;
		AES aes = new AES(key);
	}

}
