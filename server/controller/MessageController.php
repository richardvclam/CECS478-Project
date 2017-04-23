<?php

/**
 * @author Richard Lam
 * @author Mark Tsujimura
 */
class MessageController {

    /**
     * HTTP GET request handler.
     * Retrieves all messages from a user through their JWT.
     *
     * @param $api      the api reference
     */
    public static function getMessage($api) {
        $token = $api->authentication();
        $userID = $token->data->id;

        $sql = "SELECT senderID, s.accountName sender, receiverID, r.accountName receiver, timestamp, data 
                FROM message m 
                INNER JOIN account s ON m.senderID=s.accountID
                INNER JOIN account r ON m.receiverID=r.accountID
                WHERE (senderID='$userID' OR receiverID='$userID')";

        $result = $api->getConnection()->query($sql);

        if ($result) {
            $arr = array();
            while ($row = $result->fetch_assoc()) {
                array_push($arr, array(
                    'senderID' => $row['senderID'],
                    'sender' => $row['sender'],
                    'receiverID' => $row['receiverID'],
                    'receiver' => $row['receiver'],
                    'timestamp' => $row['timestamp'],
                    'data' => $row['data']
                ));
            }
            $json = json_encode($arr);
            echo $json;
        } else {
            echo "An error occured while retrieving the message from the database.";
        }
    }

    /**
     * HTTP POST request handler.
     * Adds an encoded JSON message between two users into the database.
     *
     * @param $api
     * @param $receiver
     * @param $data
     */
    public static function postMessage($api, $receiver, $data) {
        $token = $api->authentication();
        $userID = $token->data->id;

        // Add message to database
	    $sql = "INSERT INTO message (senderID, receiverID, data) VALUES ('$userID', '$receiver', '$data')";
        $result = $api->getConnection()->query($sql);

        // Print result messsage
        if ($result) {
            // Success
            $response = '0';
        } else {
            // Error
            $response = '-1';
        }

        $responseArray = [
            'response' => $response
        ];

        echo json_encode($responseArray);
    }
}
