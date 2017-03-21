<?php
/**
 * @author Mark Tsujimura
 * @author Richard Lam
 */

class JSONFile {

    private $RSAciphertext;
    private $AESciphertext;
    private $HMACtag;

	public function __construct($RSAciphertext, $AESciphertext, $HMACtag) {
        $this->RSAciphertext = $RSAciphertext;
        $this->AESciphertext = $AESciphertext;
        $this->HMACtag = $HMACtag;

        json_encode(get_object_vars($this));

        //Print to JSON file
        file_put_contents('data.json', $this);
    }

    public function expose() {
        return get_object_vars($this);
    }

}

