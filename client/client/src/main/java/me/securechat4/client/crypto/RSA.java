package me.securechat4.client.crypto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
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
		/*
		try {
			if (isPrivate) {				
					privateKey = loadPrivateKey(Files.readAllBytes(new File(keyFilePath).toPath()));				
			} else {
				publicKey = loadPublicKey(Files.readAllBytes(new File(keyFilePath).toPath()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
	
	/**
	 * Loads a 2048-bit RSA private key file.
	 * @param publicKey the Base64 encoded String of the key to load
	 * @return 2048-bit RSA private key
	 */
	public static PrivateKey loadPrivateKey(String publicKey) {
		byte[] keyBytes = Base64.getDecoder().decode(publicKey);
		
		try {
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(spec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Loads a 2048-bit RSA public key file.
	 * @param privateKey the Base64 encoded String of the key to load
	 * @return 2048-bit RSA public key
	 */
	public static PublicKey loadPublicKey(String privateKey) {
		byte[] keyBytes = Base64.getDecoder().decode(privateKey);
		
		try {
			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(spec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Generate a 2048-bit public & private key pair for RSA.
	 * @return a keypair
	 */
	public static KeyPair generateKeyPair() {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(2048, new SecureRandom());
			
			return generator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String sign(String plaintext, PrivateKey privateKey) {
		Signature privateSignature;
		try {
			privateSignature = Signature.getInstance("SHA256withRSA");
			privateSignature.initSign(privateKey);
			privateSignature.update(plaintext.getBytes());
			
			return Base64.getEncoder().encodeToString(privateSignature.sign());
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean verify(String plaintext, String signature, PublicKey publicKey) {
		Signature publicSignature;
		try {
			publicSignature = Signature.getInstance("SHA256withRSA");
			publicSignature.initVerify(publicKey);
			publicSignature.update(plaintext.getBytes());
			
			return publicSignature.verify(Base64.getDecoder().decode(signature));
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			e.printStackTrace();
		}
		
		return false;
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
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | 
				IllegalBlockSizeException | BadPaddingException e) {
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
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | 
				IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		
		return decryptedText;
	}
	
}
