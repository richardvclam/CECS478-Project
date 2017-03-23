<?php

/**
 * @author Richard Lam
 * @author Mark Tsujimura
 */
class RegisterController {

    public static function register($api, $user, $pass) {
        //check if user put nothing in the field
        if ($pass == NULL || $user == NULL){
            echo "Please don't leave username or password empty<br>";
            echo "<a href='./register'>Register Again</a>";
            return 0;
        }
        //check if username is already taken
        $check =  $api->getConnection()->query('SELECT COUNT(*) FROM `account` WHERE `accountName` = $user ');
        if ($check == 1){
            echo "Username is already taken<br>";
            echo "<a href='./register'>Register Again</a>";
            return 0;
        }

        // Adds user into database
        $sql = "INSERT INTO account (accountName, accountPassword) VALUES ('" . $user . "', '" . $pass ."')";
        $result = $api->getConnection()->query($sql);

        if($result) {
            echo "You have registered successfully! <br/><br/>";
            echo "<a href='./'>Back to Home</a>";
        } else {
            echo "An error occurred during registration. Please try again. <br/><br/>";
            echo "<a href='./register'>Register Again</a>";
        }

        return $result;
    }
}
