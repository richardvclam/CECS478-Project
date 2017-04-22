<?php
require_once("REST.php");
require_once("Route.php");
require_once('Password.php');
require_once('vendor/autoload.php');
use \Firebase\JWT\JWT;

JWT::$leeway = 60;

define('ALGORITHM','HS256');

/**
 * @author Richard Lam
 * @author Mark Tsujimura
 */
class API extends REST {

    /**
     * MySQL database URL
     */
    const DB_SERVER = "localhost";
    /**
     * MySQL database username
     */
    const DB_USER = "root";
    /**
     * MySQL database password
     */
    const DB_PASSWORD = "cia";
    /**
     * MySQL database name
     */
    const DB = "securechat";

    /**
     * @var MySQL database connection
     */
    private $conn;

    /**
     * API constructor.
     */
    public function __construct() {
        parent::__construct();              // Init parent contructor
        $this->dbConnect();                 // Initiate Database connection
    }

    /**
     * Connects to the MySQL database.
     */
    private function dbConnect() {
        $this->conn = new mysqli(self::DB_SERVER, self::DB_USER, self::DB_PASSWORD, self::DB);
        if ($this->conn->connect_error) {
            die ('Database Connect Error (' . $this->conn->connect_errno . ') ' . $this->conn->connect_error);
        }
    }

    /**
     * Returns the MySQL database connection.
     *
     * @return MySQL database connection
     */
    public function getConnection() {
        return $this->conn;
    }

    /*
     * Processes incoming HTTP requests.
     * This method dynamically calls the method based on the request variable.
     * If the request variable's function exists in Route.class, then run the
     * respective request function.
     */
    public function processAPI() {
        // If 'request' variable exists in URL, then continue. Else send to /home/
        if (!empty($_REQUEST['request'])) {
            $func = strtolower(trim(str_replace("/", "", $_REQUEST['request'])));
        } else {
            $func = 'home';
        }
        //echo "Request: " . $_REQUEST['request'] . "\n";
        if ((int)method_exists(Route::class, $func) > 0) {
            Route::$func($this);
        } else {
            // If the method does not exist within the Route.class, then the response would be "Page not found".
            $this->response('Error code 404, Page not found', 404);
        }
    }

    /**
     * Encodes the $data array into a JSON representation.
     *
     * @param $data   the array of data
     * @return string the JSON representation
     */
    public static function json($data) {
        if(is_array($data)) {
            return json_encode($data);
        }
    }

    public function authentication() {
        $headers = apache_request_headers();
        //print_r($headers);
        //echo $headers['Authorization'];

        if (isset($headers['Authorization'])) {
            //list($jwt) = sscanf($headers['Authorization'], '%s');
            $jwt = $headers['Authorization'];
            //echo $jwt;

            try {
                $myfile = fopen("/var/www/html/jwtkey.txt", "r") or die("Unable to open file!");
                $secretKey = base64_decode(fgets($myfile));

                $token = JWT::decode(
                    $jwt,
                    $secretKey,
                    array(ALGORITHM)
                );

                //print_r($token);
                return $token;

            } catch (Exception $e) {
                //throw new Exception($e);
                header('HTTP/1.0 401 Unauthorized');
                die("Error 401. Unauthorized. Token is invalid.");
            }
        } else {
            header('HTTP/1.0 401 Unauthorized');
            die("Error 401. Unauthorized. No token found.");
        }
    }

}
