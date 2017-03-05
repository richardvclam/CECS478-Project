package me.securechat4.client;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AES {
	
	/**
	 * Initialization Vector size in bytes. AES block size.
	 */
	private static final int IV_BYTE_SIZE = 16;
	/**
	 * AES key size. This can be changed to 128, 192, or 256 bits.
	 */
	private static final int KEY_BIT_SIZE = 256;
	/**
	 * This is the AES Key.
	 */
	private SecretKey key;
	
	/**
	 * Constructs a new AES object and generates a random AES key using {@code KEY_BIT_SIZE}.
	 */
	public AES() {
		KeyGenerator keygen = null;
		try {
			keygen = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keygen.init(KEY_BIT_SIZE);
		key = keygen.generateKey();
	}
	
	/**
	 * Constructs a new AES object with an inputed AES key.
	 * @param key is the inputed AES key
	 */
	public AES(SecretKey key) {
		this.key = key;
	}
	
	/**
	 * Returns a 256-bit SecretKey for AES.
	 * @return a 256-bit SecretKey
	 */
	public SecretKey getKey() {
		return key;
	}
	
	/**
	 * Encrypts a {@code message} using the AES-256-CBC encryption method. 
	 * @param message is the message to encrypt
	 * @return a Base64 encoded IV and encrypted message
	 */
	public String encrypt(String message) {
		String encodedString = "";
		try {
			SecureRandom random = new SecureRandom();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			byte[] iv = new byte[IV_BYTE_SIZE];
			random.nextBytes(iv);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			byte[] encryptedMessage = cipher.doFinal(message.getBytes());
			
			byte[] concatenatedIvEncryptedMessage =  Util.concatenate(iv, encryptedMessage);
			
			encodedString = Base64.getEncoder().encodeToString(concatenatedIvEncryptedMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encodedString;
	}

}
