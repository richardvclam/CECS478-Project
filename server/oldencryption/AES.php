<?php
/**
 * @author Mark Tsujimura
 * @author Richard Lam
 */

class AES {

    private $aesKey;
    private $hmacKey;

    public function __construct() {
        // Generate a 256-bit AES key encoded by base64
        $this->aesKey = base64_encode(openssl_random_pseudo_bytes(32));
        // Generate a 256-bit HMAC key encoded by base64
        $this->hmacKey = base64_encode(openssl_random_pseudo_bytes(32));
    }

    public function getAESKey() {
        return $this->aesKey;
    }

    public function setAESKey($aesKey) {
        $this->aesKey = $aesKey;
    }

    public function getHMACKey() {
        return $this->hmacKey;
    }

    /**
     * Encrypts a message using AES-256-CBC.
     * @param $plaintext is the message to encrypt
     * @return string a base64 encoded ciphertext
     */
	public function encrypt($plaintext) {
        // Decode the key first by removing the base64 encoding
	    $key = base64_decode($this->aesKey);
        // Generate an initialization vector (IV)
        $iv = openssl_random_pseudo_bytes(openssl_cipher_iv_length('AES-256-CBC'));
        // Encrypt the plain text using AES-256-CBC encryption
        $ciphertext = openssl_encrypt($plaintext, 'aes-256-cbc', $key, 0, $iv);

        // Return the ciphertext with the initialization vector prepended
        return base64_encode($iv . "::" . $ciphertext);
	}

    /**
     * Decrypts an encrypted message using AES-256-CBC.
     * @param $encryptedData
     * @return string
     */
	public function decrypt($encryptedData) {
        // Decode the key first by removing the base64 encoding
        $key = base64_decode($this->aesKey);
        // Split the prepended IV from the ciphertext
        $data = explode('::', base64_decode($encryptedData), 2);

        // Return the plaintext
        return openssl_decrypt($data[1], 'aes-256-cbc', $key, 0, $data[0]);
    }

}
