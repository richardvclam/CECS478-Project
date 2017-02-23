<?php 
namespace securechat;

class RSA {

	var $publicKeyFile;
	var $publicKey;

	public function __construct($publicKeyFile) {
		$this->publicKeyFile = $publicKeyFile;
	}

	public function loadPublicKey() {
		$publicKey = file_get_contents($publicKeyFile);
		echo $publicKey;
	}
	


}


?>