<?php

/**
 * @author Richard Lam
 * @author Mark Tsujimura
 */
class RegisterController {

    public static function register($api, $user, $pass) {
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
