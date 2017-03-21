<?php
define('SECRET_KEY','Your-Secret-Key')
define('ALGORITHM','HS256')

public static function CreateJWT($username) {
		
	$data = array('username' => $username);	//missing some payload parameter
	$secretKey = base64_decode(SECRET_KEY);	
	$jwt = JWT::encode(
                            $data, //Data to be encoded in the JWT
                            $secretKey, // The signing key
                            ALGORITHM 
                           ); 	
	
	return $jwt;
	
	//$header = array('typ' => 'JWT', 'alg' => ALGORITHM);	
	//$signing_input = base64_url_encode(json_encode($header)) . '.' . base64_url_encode(json_encode($jwt_data));
	//$jwt =  base64_url_encode($header).'.'.base64_url_encode($payload).'.'.base64_url_encode($signature);
}






