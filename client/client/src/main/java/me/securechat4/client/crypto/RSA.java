package me.securechat4.client.crypto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * 
 * @author Richard Lam
 * @author Mark Tsujimura
 *
 */
public class RSA {
	
	/**
	 * Algorithm to use including the block cipher mode of operation and padding.
	 */
	private static final String ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
	
	/**
	 * File path to the public or private key file.
	 */
	private String keyFilePath;
	
	/**
	 * Public key loaded from a 2048-bit RSA key file.
	 */
	private PublicKey publicKey;
	
	/**
	 * Private key loaded from a 2048-bit RSA key file.
	 */
	private PrivateKey privateKey;
	
	/**
	 * Constructs a new RSA object and loads a public or private 2048-bit RSA key file 
	 * depending on whether {@code isPrivate} is true or false.
	 * @param keyFileName - the file path to the public or private key file
	 * @param isPrivate - whether the key file is public or private
	 */
	public RSA(String keyFileName, boolean isPrivate) {
		this.keyFilePath = keyFileName;
		if (isPrivate) {
			privateKey = loadPrivateKey();
		} else {
			publicKey = loadPublicKey();
		}
	}
	
	/**
	 * Loads a 2048-bit RSA private key file.
	 * @return 2048-bit RSA private key
	 */
	public PrivateKey loadPrivateKey() {
		try {
			byte[] keyBytes = Files.readAllBytes(new File(keyFilePath).toPath());
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(spec);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Loads a 2048-bit RSA public key file.
	 * @return 2048-bit RSA public key
	 */
	public PublicKey loadPublicKey() {
		try {
			byte[] keyBytes = Files.readAllBytes(new File(keyFilePath).toPath());
			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(spec);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Encrypts an array of bytes using the RSA-OAEP algorithm and a public key. Returns 
	 * a Base64 encoded string.
	 * @param data - array of bytes to encrypt
	 * @return Base64 encoded string
	 */
	public String encrypt(byte[] data) {
		byte[] encryptedData = null;
		
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encryptedData = cipher.doFinal(data);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return Base64.getEncoder().encodeToString(encryptedData);
	}
        
	/**
	 * Decrypts a Base64 encoded string using the RSA-OAEP algorithm and a private key. 
	 * Returns an array of bytes.
	 * @param text - Base64 encoded string to decrypt
	 * @return decrypted array of bytes
	 */
	public byte[] decrypt(String text) {
		byte[] decryptedText = Base64.getDecoder().decode(text);
   
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			decryptedText = cipher.doFinal(decryptedText);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		return decryptedText;
	}
	
}
