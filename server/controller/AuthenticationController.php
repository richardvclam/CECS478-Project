<?php
require_once('vendor/autoload.php');
use \Firebase\JWT\JWT;

define('ALGORITHM','HS256');

/**
 * @author Richard Lam
 * @author Mark Tsujimura
 */
class AuthenticationController {

    public static function authentication($api, $jwt) {
        try {
            $myfile = fopen("/var/www/html/jwtkey.txt", "r") or die("Unable to open file!");
            $secretKey = base64_decode(fgets($myfile));

            $token = JWT::decode(
                $jwt,
                $secretKey,
                array(ALGORITHM)
            );

            print_r($token);

        } catch (Exception $e) {
            header('HTTP/1.0 401 Unauthorized');
            echo "Error 401. Unauthorized. Token is invalid.";
        }
    }

}