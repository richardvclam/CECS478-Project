<?php 
//var $message = 'Hi';
//var $rsaPublicKey = '/home/ubuntu/public.pem';

class Crypto {

	public static function encrypt($message, $rsaPublicKey) {
		$rsa = new RSA($rsaPublicKey);
		$rsa->loadPublicKey();
		//var $aes = new AES();
		//echo $message;
	}

	public static function decrypt($jsonObj, $rsaPrivateKey) {

	}

}

?>