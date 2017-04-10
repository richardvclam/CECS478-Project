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
     * @param $fromUser
     * @param $toUser
     * @param $jsonEncMsg
     */
    public static function postMessage($api, $fromUser, $toUser, $jsonEncMsg) {
	    $sql = "INSERT INTO message (senderID, receiverID, data) VALUES ('$fromUser', '$toUser', '$jsonEncMsg')";
        $result = $api->getConnection()->query($sql);	 //Add message to database

        if ($result) {    //echo out result messsage
            echo "Message has been successfully added into database.";
        } else {
            echo "An error occured while adding the message into the database.";
        }
    }
}
