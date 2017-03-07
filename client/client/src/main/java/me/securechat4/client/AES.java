package me.securechat4.client;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * 
 * @author Richard Lam
 * @author Mark Tsujimura
 *
 */
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
	 * Algorithm to use including the block cipher mode of operation and padding.
	 */
	private static final String ALGORITHM = "AES/CBC/PKCS5PADDING";
	
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
	 * @param key - the inputed AES key
	 */
	public AES(SecretKey key) {
		this.key = key;
	}
	
	/**
	 * Returns a 256-bit SecretKey for AES.
	 * @return 256-bit SecretKey
	 */
	public SecretKey getKey() {
		return key;
	}
	
	/**
	 * Encrypts a raw string {@code message} using the AES-256-CBC algorithm.
	 * @param message - the message to encrypt
	 * @return a Base64 encoded IV and encrypted message
	 */
	public String encrypt(String message) {
		byte[] encryptedData = null;
		byte[] iv = new byte[IV_BYTE_SIZE];
		
		SecureRandom random = new SecureRandom();
		random.nextBytes(iv);
		
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
			
			byte[] encryptedMessage = cipher.doFinal(message.getBytes());
			encryptedData = Util.concatenate(iv, encryptedMessage);
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return Base64.getEncoder().encodeToString(encryptedData);
	}
    
	/**
	 * Decrypts an {@code encryptedData} using the AES-256-CBC algorithm. Returns a raw decrypted 
	 * string. The function first splits the IV and encrypted message from the the encoded data. 
	 * Using the acquired IV and a 256-bit AES key, it decrypts the message using the specified
	 * algorithm.
	 * @param encodedData - the Base64 encoded IV and encrypted message
	 * @return a raw decrypted string
	 */
	public String decrypt(String encodedData) {
		byte[] decodedData = Base64.getDecoder().decode(encodedData);
		byte[] iv = new byte[IV_BYTE_SIZE];
		byte[] encryptedMessage = new byte[decodedData.length - IV_BYTE_SIZE];
		
		System.arraycopy(decodedData, 0, iv, 0, IV_BYTE_SIZE);
		System.arraycopy(decodedData, IV_BYTE_SIZE, encryptedMessage, 0, encryptedMessage.length);
		
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
			
			return new String(cipher.doFinal(encryptedMessage), "UTF-8");
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;               
	}
        
}
