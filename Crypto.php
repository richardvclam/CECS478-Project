<?php 
namespace securechat;

//var $message = 'Hi';
//var $rsaPublicKey = '/home/ubuntu/public.pem';

public function encrypt($message, $rsaPublicKey) {
	var $rsa = new RSA($rsaPublicKey);
	var $aes = new AES();
	echo $message;
}

public function decrypt($jsonObj, $rsaPrivateKey) {

}

?>