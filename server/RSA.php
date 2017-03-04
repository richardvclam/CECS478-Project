<?php 

class RSA {

	var $publicKeyFile;
	var $publicKey;

	public function __construct($publicKeyFile) {
		$this->publicKeyFile = $publicKeyFile;
	}

	public function getPublicKey() {
		return $this->publicKeyFile;
	}

	public function loadPublicKey() {
		$this->publicKey = openssl_pkey_get_public(file_get_contents($this->publicKeyFile));
		$publicKeyDetails = @openssl_pkey_get_details($this->publicKey);
		if ($publicKeyDetails === null) {
			throw new \LogicException (
				print_r('Could not get details of public key.')
			);
		} else {
			echo 'Key has been loaded.';
		}
	}
	


}


?>