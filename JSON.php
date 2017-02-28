<?php 
namespace securechat;

class JASONfile {
var $RSAciphertext
var $AESciphertext
var $HMACtag


	public function __construct($RSAciphertext, $AESciphertext, $HMACtag) {
		$this->RSAciphertext = $RSAciphertext;
		$this->AESciphertext = $AESciphertext;
		$this->HMACtag= $HMACtag;
		$json_data = json_encode($this->expose());
		
		
		//Print to JSON file
		file_put_contents('data.json', $json_data);
	
	}
	
	public function expose() {
		return get_object_vars($this);
	}


}
?>
