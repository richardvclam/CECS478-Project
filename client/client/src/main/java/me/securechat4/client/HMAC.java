package me.securechat4.client;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

public class HMAC {
	
	/**
	 * HMAC key bit size for generating a random key.
	 */
	private static final int HMAC_KEY_BIT_SIZE = 256;
	/**
	 * The algorithm to use for calculating the hash tag.
	 */
	private static final String HMAC_SHA_256 = "HmacSHA256";
	
	/**
	 * Returns a randomly generated 256-bit HMAC key.
	 * @return a randomly generated 256-bit HMAC key
	 */
	public static SecretKey generateHMACKey() {
		KeyGenerator keygen = null;
		try {
			keygen = KeyGenerator.getInstance(HMAC_SHA_256);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keygen.init(HMAC_KEY_BIT_SIZE);
		return keygen.generateKey();
	}
	
	/**
	 * Returns a Base64 encoded HMAC integrity tag for {@code data} using the {@code key}.
	 * @param data is the data to generate the hash
	 * @param key is the key used to generate the hash
	 * @return a Base64 encoded HMAC integrity tag
	 */
	public static String calculateIntegrity(String data, SecretKey key) {
		Mac mac = null;
		try {
			mac = Mac.getInstance(HMAC_SHA_256);
			mac.init(key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));
	}

}
