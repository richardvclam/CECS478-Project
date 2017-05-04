package me.securechat4.client.crypto;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class ECDHE {
	
	public static final String ELLIPTIC_CURVE = "EC";
	
	/**
	 * Generates a 256-bit Elliptic Curve Diffie-Hellman key pair.
	 * @return a 256-bit ECDH key pair
	 */
	public static KeyPair generateKeyPair() {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(ELLIPTIC_CURVE);
			generator.initialize(256);
			
			return generator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Loads a ECDH public key.
	 * @param keyBytes the bytes of the key to load
	 * @return ECDH public key
	 */
	public static PublicKey loadPublicKey(String publicKey) {
		byte[] keyBytes = Base64.getDecoder().decode(publicKey);
		
		try {
			KeyFactory kf = KeyFactory.getInstance(ELLIPTIC_CURVE);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			
			return kf.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Loads a ECDH private key.
	 * @param keyBytes the bytes of the key to load
	 * @return
	 */
	public static PrivateKey loadPrivateKey(String privateKey) {
		byte[] keyBytes = Base64.getDecoder().decode(privateKey);
		
		try {
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(ELLIPTIC_CURVE);
			return keyFactory.generatePrivate(spec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Performs a key agreement and generates a shared secret key from a public key and a private key.
	 * @param privateKey is the user's private key
	 * @param publicKey is another user's public key
	 * @return a shared secret key
	 */
	public static byte[] generateSharedSecretKey(PrivateKey privateKey, PublicKey publicKey) {
		try {
			KeyAgreement agreement = KeyAgreement.getInstance("ECDH");
			agreement.init(privateKey);
			agreement.doPhase(publicKey, true);

			return agreement.generateSecret();
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Derives a key from the shared key using the included salt.
	 * @param sharedKey is the shared key to derive from
	 * @param salt is the salt to append to the key
	 * @return a newly derived SecretKey
	 */
	public static SecretKey deriveKey(byte[] sharedKey, String salt) {
		KeySpec spec = new PBEKeySpec(new String(sharedKey).toCharArray(), new String(salt).getBytes(), 100, 256);
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			return factory.generateSecret(spec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

}
