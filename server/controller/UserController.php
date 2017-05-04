<?php

/**
 * Created by PhpStorm.
 * User: Richard
 * Date: 4/26/2017
 * Time: 9:49 PM
 */
class UserController {

    /**
     * Checks if username exists.
     * @param $api
     * @param $username
     */
    public static function checkUser($api, $username) {
        // Require authentication to access server
        $api->authentication();

        if ($username) {
            $userQuery = "SELECT accountID, accountName FROM account WHERE accountName=?";
            $id = -1;
            $accountName = "";

            if ($stmt = $api->getConnection()->prepare($userQuery)) {
                $stmt->bind_param("s", $username);
                $stmt->execute();
                $stmt->store_result();
                $stmt->bind_result($id, $accountName);
                $stmt->fetch();
                $numRows = $stmt->num_rows;
                $stmt->close();

                if ($numRows == 1) {
                    $responseArray = [
                        'response' => '0',
                        'id' => $id,
                        'username' => $accountName
                    ];
                } else {
                    $responseArray = [
                        'response' => '1'
                    ];
                }
            } else {
                $responseArray = [
                    'response' => '2'
                ];
            }
        } else {
            echo "No username defined.";
        }

        echo json_encode($responseArray);
    }

}