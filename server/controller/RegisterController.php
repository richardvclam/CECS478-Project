<?php

/**
 * @author Richard Lam
 * @author Mark Tsujimura
 */
class RegisterController {

    /**
     * Registers a new user account.
     * Returns a JSON object with a response value.
     *
     * Response values:
     * 0 - Success
     * 1 - Username already exists
     * 2 - SQL Error
     * 3 - Empty Input
     *
     * @param $api
     * @param $user
     * @param $pass
     */
    public static function register($api, $user, $pass, $email) {
        // Check if user put something in the field
        if ($user && $pass && $email) {

            // Check if username is already taken
            $checkUsernameQuery = "SELECT accountName FROM account WHERE accountName=?;";
            if ($stmt = $api->getConnection()->prepare($checkUsernameQuery)) {
                $stmt->bind_param("s", $user);
                $stmt->execute();
                $stmt->store_result();
                $numRows = $stmt->num_rows;
                $stmt->close();

                // If count is greater than 0, then accountName is not unique
                // Return response 1
                if ($numRows > 0) {
                    $unencodedArray = array(
                        'response' => '1'
                    );

                    echo json_encode($unencodedArray);
                    return;
                }
            }

            // Create a prepared statement
            $registerQuery = "INSERT INTO account (accountName, accountPassword, email) VALUES (?, ?, ?);";
            if ($stmt = $api->getConnection()->prepare($registerQuery)) {
                // Bind params
                $stmt->bind_param("sss", $user, $pass, $email);

                // Execute query
                $stmt->execute();

                // Close statement
                $stmt->close();

                // Successful registration
                $unencodedArray = array(
                    'response' => '0',
                );
            } else {
                // SQL Error
                $unencodedArray = array(
                    'response' => '2',
                );
            }

            echo json_encode($unencodedArray);
        }
    }

}
