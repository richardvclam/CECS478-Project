<?php

/**
 * Created by PhpStorm.
 * User: richardlam
 * Date: 4/28/17
 * Time: 10:00 AM
 */
class KeyController {

    public static function getKey($api, $ownerID) {
        $token = $api->authentication();
        $userID = $token->data->id;

        if ($ownerID) {
            $sql = "SELECT publicKey, signature FROM ecdheKey WHERE ownerID=? AND receiverID=? ORDER BY timestamp DESC LIMIT 1";

            $key = "";
            $signature = "";

            if ($stmt = $api->getConnection()->prepare($sql)) {
                $stmt->bind_param("ii", $ownerID, $userID);
                $stmt->execute();
                $stmt->store_result();
                $stmt->bind_result($key, $signature);
                $stmt->fetch();

                $numRows = $stmt->num_rows;

                $stmt->close();

                if ($numRows == 1) {
                    $responseArray = [
                        'response' => '0',
                        'key' => $key,
                        'signature' => $signature
                    ];
                } else {
                    $responseArray = [
                        'response' => '1'
                    ];
                }
            } else {
                // SQL Error
                $responseArray = [
                    'response' => '-1'
                ];
            }
        } else {
            echo "Not a valid user.";
        }
        echo json_encode($responseArray);
    }

    public static function postKey($api, $recipientID, $key, $signature) {
        $token = $api->authentication();
        $userID = $token->data->id;

        if ($recipientID && $key && $signature) {
            $sql = "INSERT INTO ecdheKey (ownerID, receiverID, publicKey, signature) VALUES (?, ?, ?, ?)";

            if ($stmt = $api->getConnection()->prepare($sql)) {
                $stmt->bind_param("iiss", $userID, $recipientID, $key, $signature);
                $stmt->execute();
                $stmt->close();

                // Successful insertion
                $responseArray = array(
                    'response' => '0',
                );
            } else {
                // SQL error
                $responseArray = array(
                    'response' => '-1',
                );
            }
        } else {
            $responseArray = array(
                'response' => '-1',
            );
        }

        echo json_encode($responseArray);
    }

}