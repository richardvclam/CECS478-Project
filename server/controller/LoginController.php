<?php

require_once('vendor/autoload.php');
include('MessageController.php');
use \Firebase\JWT\JWT;

define('ALGORITHM','HS256');

class LoginController {

    public static function login($api, $user, $pass) {
        if ($user && $pass) {
            // if there is no error below code run
            $sql = "SELECT * FROM account WHERE accountName='$user'";
            $result = $api->getConnection()->query($sql);
            $row = $result->fetch_assoc();
            $hashAndSalt = password_hash($pass, PASSWORD_BCRYPT);

            if (count($row) > 0 && password_verify($row['accountPassword'], $hashAndSalt)) {
                $tokenId = base64_encode(random_bytes(32));
                $issuedAt = time();
                $notBefore = $issuedAt + 10;  //Adding 10 seconds
                $expire = $notBefore + 7200; // Adding 60 seconds
                $serverName = 'https://securechat4.me/'; /// set your domain name

                /*
                 * Create the token as an array
                 */
                $data = [
                    'iat' => $issuedAt,         // Issued at: time when the token was generated
                    'jti' => $tokenId,          // Json Token Id: an unique identifier for the token
                    'iss' => $serverName,       // Issuer
                    'nbf' => $notBefore,        // Not before
                    'exp' => $expire,           // Expire
                    'data' => [                  // Data related to the logged user you can set your required data
                        'id' => $row['accountID'], // id from the users table
                        'name' => $row['accountName'], //  name
                    ]
                ];
                //open key from file
                try {
                    $myfile = fopen("/var/www/html/key.txt", "r") or die("Unable to open file!");
                    $key = fgets($myfile);
                    //close($myfile);
                    $secretKey = base64_decode($key);
                    /// Here we will transform this array into JWT:
                    $jwt = JWT::encode(
                        $data, //Data to be encoded in the JWT
                        $secretKey, // The signing key
                        ALGORITHM
                    );
                    $unencodedArray = ['jwt' => $jwt];
			        echo "Login sucessful";
			        $json = json_encode($unencodedArray);
               		echo $json;
	         	    return json_encode($unencodedArray);
                } catch ( Exception $e ) {
                        echo "error";
                } 
            } else {
                echo "Invalid email or password. Try again.";
            }
        }
    }
}




