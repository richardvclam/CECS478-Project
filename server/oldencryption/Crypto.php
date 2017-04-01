<?php

/**
 * @author Mark Tsujimura
 * @author Richard Lam
 */
class Crypto {

    /**
     * Encrypts a message using a AES-256-CBC encryption and using a RSA-OAEP-256 encryption for the keys.
     * @param $message the plaintext to be encrypted
     * @param $rsaPublicKey the RSA public key .pem file path
     * @return string a JSON object that contains 'rsaCiphertext', 'aesCiphertext', and 'hmacTag'
     */
	public static function encrypt($message, $rsaPublicKey) {
	    // Initialize a RSA object with a public key
		$rsa = new RSA($rsaPublicKey);
		// Load the public key into the RSA object
		$rsa->loadPublicKey();
		// Initialize an AES object
		$aes = new AES();

        // Encrypt the message with AES
		$encryptedData = $aes->encrypt($message);

        // Generate integrity tag/hash from HMAC Key
		$hash = hash_hmac('sha256', $encryptedData, $aes->getHMACKey());

		// Concatenate AES and HMAC key
		$aeshmacKey =$aes->getAESKey() . $aes->getHMACKey();
		// Use RSA-OAEP-256 encryption on AES & HMAC keys
		$rsaCipherText = $rsa->encrypt($aeshmacKey);

		// Create an array for the JSON object
		$arr = array(
		    'rsaCiphertext' => $rsaCipherText,
            'aesCiphertext' => $encryptedData,
            'hmacTag' => $hash
        );
		// Encode the array in UTF-8
		$encodedArr = array_map('utf8_encode', $arr);
		// Create the JSON object
		$jsonObj = json_encode($encodedArr, JSON_UNESCAPED_UNICODE);

		// Return the JSON object
        return $jsonObj;
	}

    /**
     * Decrypts a message using a AES-256-CBC encryption and using a RSA-OAEP-256 encryption for the keys.
     * @param $jsonObj the JSON object that includes 'rsaCiphertext', 'aesCiphertext', and 'hmacTag'
     * @param $rsaPrivateKey the RSA private key .pem file path
     * @return string the decrypted plaintext message
     * @throws Exception throws an exception if the HMAC integrity tag does not match
     */
	public static function decrypt($jsonObj, $rsaPrivateKey) {
	    // Initialize a RSA object with a private key
	    $rsa = new RSA($rsaPrivateKey);
	    // Load the priate key into the RSA object
	    $rsa->loadPrivateKey();

	    // Decode the JSON object
	    $arr = json_decode($jsonObj, false, 512, JSON_UNESCAPED_UNICODE);
	    // Decrypt the AES-HMAC keys using RSA-OAEP-256
	    $aeshmacKey = $rsa->decrypt($arr->rsaCiphertext);
	    // Split the acquired keys into a pair
        // We split at length 44 because base64-encode on a 256-bit key = 44
        $keys = str_split($aeshmacKey, 44);
        // Generate a hash on the decrypted AES ciphertext to acquire a integrity tag
        $hash = hash_hmac('sha256', $arr->aesCiphertext, $keys[1]);
        // Check if the generated integrity tag matches with the integrity tag from JSON
        // else throw an exception
        if ($hash != $arr->hmacTag) {
            throw new Exception(
                print("The HMAC tag integrity is incorrect.")
            );
        }

        // Initialize an AES object
        $aes = new AES();
        // Set AES key to key from JSON
        $aes->setAESKey($keys[0]);

        // Return the encrypted message
        return $aes->decrypt($arr->aesCiphertext);
	}

}
