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

            if (count($row) > 0 && Password::check($pass, $row['accountPassword'])) {
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

    public static function loginChallenge($api, $username) {
        // Number of seconds to allow the challenge to last for
        $challengeDuration = 360;
        $expiration = time() + $challengeDuration;

        if ($username) {
            // Check if username exists
            $usernameQuery = "SELECT accountID, accountPassword FROM account WHERE accountName=?";

            $userid = -1;
            $password = "";

            if ($stmt = $api->getConnection()->prepare($usernameQuery)) {
                $stmt->bind_param("s", $username);
                $stmt->execute();
                $stmt->store_result();
                $stmt->bind_result($userid, $password);
                $stmt->fetch();
                $numRows = $stmt->num_rows;
                $stmt->close();

                $salt = Password::getSalt($password);

                if ($numRows == 1) {
                    // Generate challenge key response
                    $challenge = base64_encode(openssl_random_pseudo_bytes(32));

                    $challengeQuery = "INSERT INTO session (accountID, challenge, expirationTime) VALUES (?, ?, ?)";

                    if ($stmtt = $api->getConnection()->prepare($challengeQuery)) {
                        $stmtt->bind_param("isi", $userid, $challenge, $expiration);
                        $stmtt->execute();
                        $stmtt->close();

                        $responseArray = array(
                            'response' => '0',
                            'challenge' => $challenge,
                            'salt' => $salt
                        );
                    } else {
                        echo 'debug 1';
                        // SQL Error
                        $responseArray = array(
                            'response' => '2'
                        );
                    }
                } else {
                    // User does not exist
                    $responseArray = array(
                        'response' => '1'
                    );
                }
            } else {
                echo 'debug 2';
                // SQL Error
                $responseArray = array(
                    'response' => '2'
                );
            }
        } else {
            echo "Error! Did not enter a username.";
        }

        echo json_encode($responseArray);
    }

    public static function loginResponse($api, $username, $challenge, $hashTag) {
        if ($username && $challenge && $hashTag) {
            $usernameQuery = "SELECT accountID, accountPassword FROM account WHERE accountName=?";

            $userid = -1;
            $password = "";

            if ($stmt = $api->getConnection()->prepare($usernameQuery)) {
                $stmt->bind_param("s", $username);
                $stmt->execute();
                $stmt->store_result();
                $stmt->bind_result($userid, $password);
                $stmt->fetch();
                $numRows = $stmt->num_rows;
                $stmt->close();

                if ($numRows == 1) {
                    $challengeKey = "";
                    $expiration = 0;
                    $challengeQuery = "SELECT challenge, expirationTime FROM session WHERE accountID=? ORDER BY expirationTime DESC LIMIT 1";
                    if ($stmt = $api->getConnection()->prepare($challengeQuery)) {
                        $stmt->bind_param("i", $userid);
                        $stmt->execute();
                        $stmt->bind_result($challengeKey, $expiration);
                        $stmt->fetch();
                        $stmt->close();

                        // Check if the challenge key is valid and if the challenge has not expired yet
                        if (strcmp($challengeKey, $challenge) == 0 && $expiration > time()) {
                            // Generate integrity tag for the password from challenge Key
                            $tag = hash_hmac('sha256', $password, base64_decode($challenge), true);
                            //var_dump($tag);
                            //var_dump(base64_decode($hashTag));
                            //echo $tag . '<br>';
                            //echo $hashTag;

                            if (strcmp($tag, base64_decode($hashTag)) == 0) {
                                $tokenId = base64_encode(random_bytes(32));
                                $issuedAt = time();
                                $notBefore = $issuedAt + 10;  //Adding 10 seconds
                                $expire = $notBefore + 7200; // Adding 60 seconds
                                $serverName = 'https://securechat4.me/'; /// set your domain name

                                /*
                                 * Create the token as an array
                                 */
                                $data = [
                                    'iat' => $issuedAt,      // Issued at: time when the token was generated
                                    'jti' => $tokenId,       // Json Token Id: an unique identifier for the token
                                    'iss' => $serverName,    // Issuer
                                    'nbf' => $notBefore,     // Not before
                                    'exp' => $expire,        // Expire
                                    'data' => [              // Data related to the logged user you can set your required data
                                        'id' => $userid,     // ID from the users table
                                        'name' => $username, // Username
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

                                    $responseArray = [
                                        'response' => '0',
                                        'userid' => $userid,
                                        'jwt' => $jwt
                                    ];
                                    //echo "Login sucessful";

                                } catch ( Exception $e ) {
                                    echo "error";
                                }
                            } else {
                                // Hash tag does not match
                                $responseArray = [
                                    'response' => '3'
                                ];
                            }
                        } else {
                            // Challenge key is invalid
                            $responseArray = [
                                'response' => '-1'
                            ];
                        }
                    } else {
                        // SQL Error
                        $responseArray = [
                            'response' => '2'
                        ];
                    }
                } else {
                    // User does not exist
                    $responseArray = [
                        'response' => '1'
                    ];
                }
            } else {
                // SQL Error
                $responseArray = [
                    'response' => '2'
                ];
            }
        } else {
            echo "Not enough info";
        }

        echo json_encode($responseArray);
    }
}




