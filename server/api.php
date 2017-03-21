<?php

require_once("REST.php");
require_once("Route.php");

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

    private function test() {
        // Cross validation if the request method is GET else it will return "Not Acceptable" status
        if($this->get_request_method() != "GET") {
            $this->response('', 406);
        }
        $myDatabase = $this->conn;
        $param = $this->_request['var'];
        // If successful, send header as "OK" return param
        $this->response($param, 200);
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

}
