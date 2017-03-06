package me.securechat4.client;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;


public class Crypto {
	
	/**
	 * Returns a JSON object containing encoded data from encrypting a {@code message} using
	 * and the resulting AES ciphertext, RSA ciphertext, and HMAC integrity tag.
	 * @param message is the message to encrypt
	 * @param rsaPublicKey is the public RSA key
	 * @return a JSON object with aesCipherText, rsaCipherText, and hmacTag
	 */
    
        private static final int HMAC_KEY_BIT_SIZE = 256;
        private static final int AES_KEY_BIT_SIZE = 256;
    
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
	
	public static String decrypt(JSONObject jsonObj, String rsaPrivateKey) {
		SecretKey key = null;
		AES aes = new AES(key);
                String decryptAESText = "";
                byte[] aesKeyinBytes = new byte[AES_KEY_BIT_SIZE];
                byte[] hmacKeyinBytes = new byte[HMAC_KEY_BIT_SIZE];
                //Decode JSON obj
                String rsaCipherText = (String) jsonObj.get("rsaCipherText");
                String aesCipherText = (String) jsonObj.get("aesCipherText");
                String hmacTag = (String) jsonObj.get("hmacTag");
                
                //Initialize RSA Obj
                RSA rsa = new RSA(rsaPrivateKey, false);
                
                //Decrypt RSA text
                byte[] decryptedRSAText = rsa.decrypt(rsaCipherText);
                
                // Get AES Key from the text
                // first half is Key
                for (int i = 0; i < AES_KEY_BIT_SIZE ; i++) {
                    aesKeyinBytes[i] = decryptedRSAText[i];
                    key = new SecretKeySpec(aesKeyinBytes, 0,aesKeyinBytes.length,"AES");
                    aes = new AES(key);
                }
                
                // Get HMAC Key from the text
                // second half is the key
                for (int i = AES_KEY_BIT_SIZE; i < decryptedRSAText.length ; i++) {
                    hmacKeyinBytes[i] = aesKeyinBytes[i] = decryptedRSAText[AES_KEY_BIT_SIZE + i];
                    key = new SecretKeySpec(hmacKeyinBytes, AES_KEY_BIT_SIZE ,decryptedRSAText.length,"HMAC");
                    
                    
                }
                //Run HMAC w/ key from JSON
                String hash = HMAC.calculateIntegrity(decryptedRSAText.toString(), key );
                
                //compare with the input tag form JSON
                if (hash == hmacTag)
                    decryptAESText = aes.decrypt(aesCipherText);
                else
                     System.out.println("Error");

                
                return decryptAESText;
                
	}

}
