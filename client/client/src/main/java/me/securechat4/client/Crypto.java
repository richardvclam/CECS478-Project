package me.securechat4.client;

import javax.crypto.SecretKey;

import org.json.simple.JSONObject;

public class Crypto {
	
	/**
	 * Returns a JSON object containing encoded data from encrypting a {@code message} using
	 * and the resulting AES ciphertext, RSA ciphertext, and HMAC integrity tag.
	 * @param message is the message to encrypt
	 * @param rsaPublicKey is the public RSA key
	 * @return a JSON object with aesCipherText, rsaCipherText, and hmacTag
	 */
	public static JSONObject encrypt(String message, String rsaPublicKey) {
		// Initialize a RSA object with a public key
		RSA rsa = new RSA(rsaPublicKey, false);
		// Initialize an AES object
		AES aes = new AES();
		
		// Encrypt the message with AES
		String encryptedMessage = aes.encrypt(message);
		// Generate a random HMAC key
		SecretKey hmacKey = HMAC.generateHMACKey();
		// Calculate the integrity hash with HMAC key
		String hash = HMAC.calculateIntegrity(encryptedMessage, hmacKey);
		
		// Concatenate the AES and HMAC keys
		byte[] aeshmacKey = Util.concatenate(aes.getKey().getEncoded(), hmacKey.getEncoded());
		// Encrypt the concatenated AES and HMAC keys with RSA
		String rsaCipherText = rsa.encrypt(aeshmacKey);
		
		// Create a JSON object with all of the data in it
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("rsaCipherText", rsaCipherText);
		jsonObject.put("aesCipherText", encryptedMessage);
		jsonObject.put("hmacTag", hash);
		
		System.out.println(jsonObject.toJSONString());
		return jsonObject;
	}
	
	public static void decrypt(String jsonObj, String rsaPrivateKey) {
		SecretKey key = null;
		AES aes = new AES(key);
	}

}
