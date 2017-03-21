<?php

// Autoload all Controller classes
spl_autoload_register(function ($class) {
   include 'controller/' . $class . '.php';
});

/**
 * @author Richard Lam
 */
class Route {

    public static function home($api) {
        readfile("view/index.html");
    }

    public static function register($api) {
        // If there isn't a POST request, then load the view.
        if ($api->get_request_method() != "POST") {
            readfile("view/register.html");
        } else {
            RegisterController::register($api, $_POST['username'], $_POST['password']);
            unset($_POST['username']);
            unset($_POST['password']);
        }
    }

    public static function login($api) {
        // If there isn't a POST request, then load the view.
        if ($api->get_request_method() != "POST") {
            readfile("view/login.html");
        } else {
            LoginController::login($api, $_POST['username'], $_POST['password']);
            unset($_POST['username']);
            unset($_POST['password']);
        }
    }

}