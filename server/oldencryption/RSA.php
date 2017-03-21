<?php
/**
 * @author Mark Tsujimura
 * @author Richard Lam
 */

class RSA {

	private $keyFile;
	private $key;

	public function __construct($keyFile) {
		$this->keyFile = $keyFile;
	}

	public function getKey() {
		return $this->keyFile;
	}

	public function loadPublicKey() {
		$this->key = openssl_pkey_get_public(file_get_contents($this->keyFile));
		$publicKeyDetails = @openssl_pkey_get_details($this->key);

		if ($publicKeyDetails === null) {
			throw new \LogicException (
				print_r('Could not get details of public key.')
			);
		}
	}

	public function loadPrivateKey() {
	    $this->key = openssl_pkey_get_private(file_get_contents($this->keyFile), 'cecs478');
    }

	public function encrypt($plaintext) {
        openssl_public_encrypt($plaintext, $ciphertext, $this->key, OPENSSL_PKCS1_OAEP_PADDING);

        return base64_encode($ciphertext);
    }

    public function decrypt($ciphertext) {
	    openssl_private_decrypt(base64_decode($ciphertext), $plaintext, $this->key, OPENSSL_PKCS1_OAEP_PADDING);

	    return $plaintext;
    }


}

