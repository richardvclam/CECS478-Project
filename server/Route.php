<?php

// Autoload all Controller classes
spl_autoload_register(function ($class) {
   include 'controller/' . $class . '.php';
});

/**
 * @author Richard Lam
 * @author Mark Tsujimura
 */
class Route {

    public static function home($api) {
        readfile("view/index.html");
    }

    public static function register($api) {
        // If there isn't a POST request, then load the view.
        switch ($api->getRequestMethod()) {
            case "POST":
                RegisterController::register($api, $api->_request['username'], $api->_request['password'], $api->_request['email']);
                unset($_POST['username']);
                unset($_POST['password']);
                unset($_POST['email']);
                break;
            default:
                readfile("view/register.html");
                break;
            }
    }

    public static function login($api) {
        // If there isn't a POST request, then load the view.
        switch ($api->getRequestMethod()) {
            case "POST":
                LoginController::login($api, $api->_request['username'], $api->_request['password']);
                unset($_POST['username']);
                unset($_POST['password']);
                break;
            default:
                readfile("view/login.html");
                break;
        }
    }

    public static function login1($api) {
        switch ($api->getRequestMethod()) {
            case "POST":
                LoginController::loginChallenge($api, $api->_request['username']);
                unset($_POST['username']);
                break;
        }
    }

    public static function login2($api) {
        switch ($api->getRequestMethod()) {
            case "POST":
                LoginController::loginResponse($api, $api->_request['username'], $api->_request['challenge'], $api->_request['hashTag']);
                unset($_POST['username']);
                break;
        }
    }

    public static function message($api) {
        switch ($api->getRequestMethod()) {
            case "GET":
                if (isset($api->_request['update'])) {
                    MessageController::getMessage($api, true);
                } else {
                    MessageController::getMessage($api, false);
                }
                break;
            case "POST":
                MessageController::postMessage($api, $api->_request['receiver'], $api->_request['data']);
                break;
            default:
                echo "Message";
                break;
        }
    }

    public static function email($api) {
        switch ($api->getRequestMethod()) {
            case "POST":
                EmailController::sendEmail($api, $api->_request['recipient'], $api->_request['message']);
                break;
        }
    }

    public static function key($api) {
        switch ($api->getRequestMethod()) {
            case "GET":
                KeyController::getKey($api, $api->_request['id']);
                break;
            case "POST":
                KeyController::postKey($api, $api->_request['id'], $api->_request['key'], $api->_request['signature']);
                break;
        }
    }

    public static function user($api) {
        switch ($api->getRequestMethod()) {
            case "GET":
                UserController::checkUser($api, $api->_request['username']);
                break;
	}
    }

    public static function test($api) {
        // Cross validation if the request method is GET else it will return "Not Acceptable" status
        if($api->getRequestMethod() != "GET") {
            $api->response('', 406);
        }
        $param = $api->_request['var'];
        // If successful, send header as "OK" return param
        $api->response($param, 200);
    }

}
