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

    public static function authentication($api) {
        switch ($api->getRequestMethod()) {
            case "POST":
                AuthenticationController::authentication($api, $api->_request['jwt']);
                break;
            default:
                echo "Authentication error.";
                break;
        }

    }

    public static function register($api) {
        // If there isn't a POST request, then load the view.
        switch ($api->getRequestMethod()) {
            case "POST":
                RegisterController::register($api, $api->_request['username'], $api->_request['password']);
                unset($_POST['username']);
                unset($_POST['password']);
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

    public static function message($api) {
        switch ($api->getRequestMethod()) {
            case "GET":
                // Check to make sure there are valid and exist
                if (isset($api->_request['fromUser']) && isset($api->_request['toUser'])) {
                    MessageController::getMessage($api, $api->_request['fromUser'], $api->_request['toUser']);
                } else {
                    echo "Error 404. Messages not found.";
                }
                break;
            case "POST":
                MessageController::postMessage($api, $api->_request['fromUser'], $api->_request['toUser'], $api->_request['jsonEncMsg']);
                break;
            default:
                echo "Message";
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