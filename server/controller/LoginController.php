<?php

require_once('vendor/autoload.php');
use \Firebase\JWT\JWT;

class LoginController {

    /**
     * Login with user credentials.
     * Returns a JSON object with a JSON web token and a response value.
     *
     * Response values:
     * 0 - Success
     * 1 - Username does not exist
     * 2 - Password does not match
     *
     * @param $api
     * @param $user
     * @param $pass
     */
    public static function login($api, $user, $pass) {
        if ($user && $pass) {
            // if there is no error below code run
            $sql = "SELECT * FROM account WHERE accountName='$user'";
            //$sql = "SELECT * FROM account WHERE accountName=?";
            $result = $api->getConnection()->query($sql);
            $row = $result->fetch_assoc();

            if (count($row) > 0 && password_verify($pass, $row['accountPassword'])) {
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
                    $myfile = fopen("/var/www/html/jwtkey.txt", "r") or die("Unable to open file!");
                    $key = fgets($myfile);
                    //close($myfile);
                    $secretKey = base64_decode($key);
                    /// Here we will transform this array into JWT:
                    $jwt = JWT::encode(
                        $data, //Data to be encoded in the JWT
                        $secretKey, // The signing key
                        ALGORITHM
                    );

                    $unencodedArray = array(
                        'response' => '0',
                        'userid' => $row['accountID'],
                        'jwt' => $jwt
                    );
			        //echo "Login sucessful";

                } catch ( Exception $e ) {
                    echo "error";
                } 
            } else if (count($row) == 0) {
                // Username does not exist.
                $unencodedArray = array(
                    'response' => '1',
                );
            } else if (!password_verify($pass, $row['accountPassword'])) {
                // Invalid password
                $unencodedArray = array(
                    'response' => '2',
                );
            }

            $json = json_encode($unencodedArray);
            echo $json;
        }
    }
}




