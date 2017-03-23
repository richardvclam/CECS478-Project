<?php

/**
 * @author Richard Lam
 * @author Mark Tsujimura
 */
class REST {

    public $_content_type = "application/json";
    public $_request = array();

    private $_code = 200;

    public function __construct() {
        $this->inputs();
    }

    public function getReferer() {
        return $_SERVER['HTTP_REFERER'];
    }

    public function response($data, $status) {
        $this->_code = ($status) ? $status : 200;
        $this->setHeaders();
        echo $data;
    }

    protected function getStatusMessage() {
        $status = array(
            100 => 'Continue',
            101 => 'Switching Protocols',
            200 => 'OK',
            201 => 'Created',
            202 => 'Accepted',
            203 => 'Non-Authoritative Information',
            204 => 'No Content',
            205 => 'Reset Content',
            206 => 'Partial Content',
            300 => 'Multiple Choices',
            301 => 'Moved Permanently',
            302 => 'Found',
            303 => 'See Other',
            304 => 'Not Modified',
            305 => 'Use Proxy',
            306 => '(Unused)',
            307 => 'Temporary Redirect',
            400 => 'Bad Request',
            401 => 'Unauthorized',
            402 => 'Payment Required',
            403 => 'Forbidden',
            404 => 'Not Found',
            405 => 'Method Not Allowed',
            406 => 'Not Acceptable',
            407 => 'Proxy Authentication Required',
            408 => 'Request Timeout',
            409 => 'Conflict',
            410 => 'Gone',
            411 => 'Length Required',
            412 => 'Precondition Failed',
            413 => 'Request Entity Too Large',
            414 => 'Request-URI Too Long',
            415 => 'Unsupported Media Type',
            416 => 'Requested Range Not Satisfiable',
            417 => 'Expectation Failed',
            500 => 'Internal Server Error',
            501 => 'Not Implemented',
            502 => 'Bad Gateway',
            503 => 'Service Unavailable',
            504 => 'Gateway Timeout',
            505 => 'HTTP Version Not Supported'
        );
        return ($status[$this->_code]) ? $status[$this->_code] : $status[500];
    }


    public function getRequestMethod() {
        return $_SERVER['REQUEST_METHOD'];
    }

    /**
     * Returns the content type of the page.
     *
     * @return string the content type
     */
    public function getContentType() {
        return isset ($_SERVER['CONTENT_TYPE']) ? trim($_SERVER['CONTENT_TYPE']) : '';
    }

    /**
     * Moves and clean HTTP request data into an internal array.
     *
     * @throws Exception thrown when an invalid JSON object is received
     */
    private function inputs() {
        switch($this->getRequestMethod()) {
            case "POST":
                // Checks if Content Type is a json object
                if (strcasecmp($this->getContentType(), 'application/json') == 0) {
                    // Receive the raw post data
                    $jsonObj = trim(file_get_contents("php://input"));
                    //echo $jsonObj;

                    // Attempt to decode the incoming raw post data from JSON object
                    $decodedJson = json_decode($jsonObj, true);

                    // Checks if is a valid JSON object
                    if (!is_array($decodedJson)) {
                        throw new Exception('Received an invalid JSON object.');
                    }

                    // Cleans input and puts data into this API's request array
                    $this->_request = $this->cleanInputs($decodedJson);
                } else {
                    $this->_request = $this->cleanInputs($_POST);
                }
                break;
            case "GET":
            case "DELETE":
                $this->_request = $this->cleanInputs($_GET);
                //print_r(array_values($_GET));
                break;
            case "PUT":
                parse_str(file_get_contents("php://input"), $this->_request);
                $this->_request = $this->cleanInputs($this->_request);
                break;
            default:
                $this->response('', 406);
                break;
        }
    }

    /**
     * Cleans input from HTTP requests to prevent exploits such as SQL injection.
     *
     * @param $data         the data to clean
     * @return array|string the cleaned data
     */
    private function cleanInputs($data) {
        $clean_input = array();

        // Checks if the data is an array
        if (is_array($data)) {
            // Recursively cleans input from array
            foreach ($data as $k => $v) {
                $clean_input[$k] = $this->cleanInputs($v);
            }
        } else {
            // Checks if current configuration settings of magic quotes is ON
            if (get_magic_quotes_gpc()) {
                // Escapes all slashes and trims white space
                $data = trim(stripslashes($data));
            }
            // Strips HTML and PHP tags from a string
            $data = strip_tags($data);
            // Strips whitespace from the beginning and end of a string
            $clean_input = trim($data);
        }
        return $clean_input;
    }

    /**
     * Sets the headers for a page.
     */
    private function setHeaders() {
        header("HTTP/1.1 ".$this->_code." ".$this->getStatusMessage());
        //header("Content-Type:".$this->_content_type);
    }
}
?>