package me.securechat4.client;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class HMAC {
	
private static final int HMAC_KEY_BIT_SIZE = 256;
	
	public static SecretKey generateHMACKey() {
		KeyGenerator keygen = null;
		try {
			keygen = KeyGenerator.getInstance("HmacSHA256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keygen.init(HMAC_KEY_BIT_SIZE);
		return keygen.generateKey();
	}

}
