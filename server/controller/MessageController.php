<?php

/**
 * @author Richard Lam
 * @author Mark Tsujimura
 */
class MessageController {

    /**
     * HTTP GET request handler.
     * Retrieves messages between two users from the database.
     *
     * @param $api      the api reference
     * @param $fromUser the user ID of the message it was sent from
     * @param $toUser   the user ID of the message it was sent to
     */
    public static function getMessage($api, $fromUser, $toUser) {
        $sql = "SELECT * FROM message WHERE (senderID='$fromUser' AND receiverID='$toUser')";
        $result = $api->getConnection()->query($sql);

        if ($result) {
            while ($row = $result->fetch_assoc()) {
                $arr = array(
                    'sender' => $row['senderID'],
                    'receiver' => $row['receiverID'],
                    'timestamp' => $row['timestamp'],
                    'data' => $row['data']
                );

                $json = json_encode($arr);
                echo $json;
            }
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
