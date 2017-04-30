package me.securechat4.client;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import me.securechat4.client.crypto.AES;
import me.securechat4.client.crypto.ECDHE;
import me.securechat4.client.crypto.HMAC;
import me.securechat4.client.crypto.RSA;

public class User {
	
	private byte[] sharedSecretKey;
	private PublicKey rsaPublicKey;
	private PublicKey dhPublicKey;
	private PrivateKey dhPrivateKey;
	private SecretKey aesKey;
	private SecretKey hmacKey;
	
	public User() {
	}
	
	public byte[] getSharedSecret() {
		return sharedSecretKey;
	}
	
	public void setSharedSecret(byte[] sharedSecretKey) {
		this.sharedSecretKey = sharedSecretKey;
	}
	
	public PublicKey getRSAPublicKey() {
		return rsaPublicKey;
	}
	
	public void setRSAPublicKey(PublicKey rsaPublicKey) {
		this.rsaPublicKey = rsaPublicKey;
	}
	
	public void loadRSAPublicKey(String rsaPublicKey) {
		if (rsaPublicKey != null) {
			this.rsaPublicKey = RSA.loadPublicKey(rsaPublicKey);
		}
	}
	
	public PublicKey getDHPublicKey() {
		return dhPublicKey;
	}
	
	public void setDHPublicKey(PublicKey dhPublicKey) {
		this.dhPublicKey = dhPublicKey;
	}
	
	public void loadDHPublicKey(String dhPublicKey) {
		if (dhPublicKey != null) {
			this.dhPublicKey = ECDHE.loadPublicKey(dhPublicKey);
		}
	}
	
	public PrivateKey getDHPrivateKey() {
		return dhPrivateKey;
	}
	
	public void setDHPrivateKey(PrivateKey dhPrivateKey) {
		this.dhPrivateKey = dhPrivateKey;
	}
	
	public void loadDHPrivateKey(String dhPrivateKey) {
		if (dhPrivateKey != null) {
			this.dhPrivateKey = ECDHE.loadPrivateKey(dhPrivateKey);
		}
	}
	
	public SecretKey getAESKey() {
		return aesKey;
	}
	
	public void setAESKey(SecretKey aesKey) {
		this.aesKey = aesKey;
	}
	
	public void loadAESKey(String aesKey) {
		if (aesKey != null) {
			this.aesKey = AES.loadKey(aesKey);
		}
	}
	
	public SecretKey getHMACKey() {
		return hmacKey;
	}
	
	public void setHMACKey(SecretKey hmacKey) {
		this.hmacKey = hmacKey;
	}
	
	public void loadHMACKey(String hmacKey) {
		if (hmacKey != null) {
			this.hmacKey = HMAC.loadKey(hmacKey);
		}
	}
}
