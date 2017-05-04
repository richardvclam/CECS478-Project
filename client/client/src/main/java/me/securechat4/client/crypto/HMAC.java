package me.securechat4.client.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author Richard Lam
 * @author Mark Tsujimura
 *
 */
public class HMAC {
	
	/**
	 * HMAC key bit size for generating a random key.
	 */
	public static final int HMAC_KEY_BIT_SIZE = 256;
	/**
	 * The algorithm to use for calculating the hash tag.
	 */
	public static final String ALGORITHM = "HmacSHA256";
	
	/**
	 * Returns a randomly generated 256-bit HMAC key.
	 * @return randomly generated 256-bit HMAC key
	 */
	public static SecretKey generateKey() {
		KeyGenerator keygen = null;
		try {
			keygen = KeyGenerator.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keygen.init(HMAC_KEY_BIT_SIZE);
		
		return keygen.generateKey();
	}
	
	/**
	 * Loads a Base64 encoded HMAC key string into a SecretKey.
	 * @param hmacKey is the Base64 encoded HMAC key string
	 * @return a HMAC SecretKey
	 */
	public static SecretKey loadKey(String hmacKey) {
		return new SecretKeySpec(Base64.getDecoder().decode(hmacKey), ALGORITHM);
	}
	
	/**
	 * Returns a Base64 encoded HMAC integrity tag for {@code data} using the {@code key}.
	 * @param data - the data to generate the hash
	 * @param key - the key used to generate the hash
	 * @return Base64 encoded HMAC integrity tag
	 */
	public static String calculateIntegrity(String data, SecretKey key) {
		Mac mac = null;
		try {
			mac = Mac.getInstance(ALGORITHM);
			mac.init(key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
		return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));
	}

}
