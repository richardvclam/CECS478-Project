package me.securechat4.client.crypto;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.json.simple.JSONObject;

/**
 * 
 * @author Richard Lam
 * @author Mark Tsujimura
 *
 */
public class Crypto {
	
    private static final int KEY_BYTE_SIZE = 32;
	
	/**
	 * Returns a JSON object containing encoded data from encrypting a {@code message} using
	 * and the resulting AES ciphertext, RSA ciphertext, and HMAC integrity tag.
	 * @param message - the message to encrypt
	 * @param rsaPublicKey - the file path for the public RSA key
	 * @return JSON object with aesCipherText, rsaCipherText, and hmacTag
	 */
    /*
	public static JSONObject encrypt(String message, String rsaPublicKey) {
		// Initialize a RSA object with a public key
		RSA rsa = new RSA(rsaPublicKey, false);
		// Initialize an AES object
		AES aes = new AES();
		
		// Encrypt the message with AES
		String encryptedMessage = AES.encrypt(message);
		// Generate a random HMAC key
		SecretKey hmacKey = HMAC.generateKey();
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

		return jsonObject;
	}
	*/
	
	/**
	 * Returns a JSON object containing encoded data from encrypting a {@code message}
	 * and the resulting AES ciphertext and HMAC integrity tag.
	 * @param message - the message to encrypt
	 * @param aesKey  - the AES key used to encrypt
	 * @param hmacKey - the HMAC key used to hash the message
	 * @return
	 */
	public static JSONObject encrypt(String message, SecretKey aesKey, SecretKey hmacKey) {
		SecretKeySpec aesKeySpec = new SecretKeySpec(aesKey.getEncoded(), "AES");
		String encryptedMessage = AES.encrypt(message, aesKeySpec);
		SecretKeySpec hmacKeySpec = new SecretKeySpec(hmacKey.getEncoded(), "HmacSHA256");
		String hash = HMAC.calculateIntegrity(encryptedMessage, hmacKeySpec);
		
		JSONObject json = new JSONObject();
		json.put("aesCipherText", encryptedMessage);
		json.put("hmacTag", hash);
		
		return json;
	}
	
	/**
	 * Returns a decrypted string message from a JSONObject containining an encoded RSA 
	 * ciphertext, AES ciphertext, and HMAC integrity tag.
	 * @param jsonObj - the JSONObject with aesCipherText, rsaCipherText, and hmacTag
	 * @param rsaPrivateKey - the file path for the private RSA key
	 * @return raw string message
	 */
	/*
	public static String decrypt(JSONObject jsonObj, String rsaPrivateKey) {
		SecretKey aesKey;
		SecretKey hmacKey;
		AES aes;
        String decryptAESText = "";
        byte[] aesKeyinBytes = new byte[KEY_BYTE_SIZE];
        byte[] hmacKeyinBytes = new byte[KEY_BYTE_SIZE];
        
        
        // Decode JSON obj
        String rsaCipherText = (String) jsonObj.get("rsaCipherText");
        String aesCipherText = (String) jsonObj.get("aesCipherText");
        String hmacTag = (String) jsonObj.get("hmacTag");
        
        // Initialize RSA Obj
        RSA rsa = new RSA(rsaPrivateKey, true);
        
        //Decrypt RSA text
        byte[] decryptedRSAText = rsa.decrypt(rsaCipherText);
        
        // Get AES Key from the text
        // First half is Key
        for (int i = 0; i < KEY_BYTE_SIZE; i++) {
            aesKeyinBytes[i] = decryptedRSAText[i]; 
        }
        // AES Secret Key
        aesKey = new SecretKeySpec(aesKeyinBytes, 0, KEY_BYTE_SIZE, "AES");
        aes = new AES(aesKey);
        
        // Get HMAC Key from the text
        // Second half is the key
        for (int i = 0; i < KEY_BYTE_SIZE; i++) {
            hmacKeyinBytes[i] = decryptedRSAText[KEY_BYTE_SIZE + i];
        }
        // HMAC Secret Key
        hmacKey = new SecretKeySpec(hmacKeyinBytes, 0, KEY_BYTE_SIZE, "HMAC");  
        
        // Run HMAC w/ key from JSON
        String hash = HMAC.calculateIntegrity(aesCipherText, hmacKey);
        
        // Compare with the input tag form JSON
        if (hash.equals(hmacTag)) {
            decryptAESText = aes.decrypt(aesCipherText);
        } else {
             System.out.println("Error: HMAC Integrity Tag does not match.");
        }
        
        return decryptAESText;  
	}
	*/
	
	/**
	 * Returns a decrypted string message from a JSONObject.
	 * @param json    - json object containing the data
	 * @param aesKey  - the AES key used to decrypt the message
	 * @param hmacKey - HMAC key used to hash the message
	 * @return decrypted message
	 */
	public static String decrypt(JSONObject json, SecretKey aesKey, SecretKey hmacKey) {
		// Decode JSON obj
		String aesCipherText = (String) json.get("aesCipherText");
        String hmacTag = (String) json.get("hmacTag");
        
        // Run HMAC w/ key from JSON
        SecretKeySpec hmacKeySpec = new SecretKeySpec(hmacKey.getEncoded(), "HmacSHA256");
        String hash = HMAC.calculateIntegrity(aesCipherText, hmacKeySpec);
        
        // Compare with the input tag from JSON
        if (hash.equals(hmacTag)) {
        	SecretKeySpec aesKeySpec = new SecretKeySpec(aesKey.getEncoded(), "AES");
            return AES.decrypt(aesCipherText, aesKeySpec);
        } else {
            return "Error: HMAC Integrity Tag does not match.";
        }
	}

}
