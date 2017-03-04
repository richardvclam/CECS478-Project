<?php 
spl_autoload_register(function ($class_name) {
	include $class_name . '.php';
});

//encrypt('Hi', '/home/ubuntu/public.pem');

echo "Testing page\n";

//$rsa = new RSA('/home/ubuntu/public.pem');
//echo $rsa->getPublicKey();
//$rsa->loadPublicKey();
Crypto::encrypt('Hi', '/home/ubuntu/public.pem');

