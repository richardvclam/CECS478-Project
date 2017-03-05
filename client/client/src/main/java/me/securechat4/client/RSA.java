package me.securechat4.client;

import java.awt.RenderingHints.Key;
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

public class RSA {
	
	private static final String ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
	
	private String keyFileName;
	private PublicKey publicKey;
	private PrivateKey privateKey;
	
	public RSA(String keyFileName, boolean isPrivate) {
		this.keyFileName = keyFileName;
		if (isPrivate) {
			privateKey = loadPrivateKey();
		} else {
			publicKey = loadPublicKey();
		}
	}
	
	public PrivateKey loadPrivateKey() {
		try {
			byte[] keyBytes = Files.readAllBytes(new File(keyFileName).toPath());
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
	
	public PublicKey loadPublicKey() {
		try {
			byte[] keyBytes = Files.readAllBytes(new File(keyFileName).toPath());
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
        
        
        public String decrypt(byte[] data) {
		byte[] decryptedData = null;
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			decryptedData = cipher.doFinal(data);
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
		
		return Base64.getEncoder().encodeToString(decryptedData);
	}
	
}
