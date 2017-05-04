package me.securechat4.client.models;

import javax.crypto.SecretKey;

import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.Constants;
import me.securechat4.client.HttpsApi;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.crypto.CryptoUtil;
import me.securechat4.client.crypto.HMAC;

public class LoginModel extends Model {
	
	public LoginModel(Controller controller) {
		super(controller);
	}
	
	/**
	 * Login to the server by performing a remote login authentication method.
	 * @param username is the username to login with
	 * @param password is the password to login with
	 * @return server response
	 */
	public int login(String username, String password) {
		String challenge = "";
		String salt = "";
		
		JSONObject json = new JSONObject();
		json.put("username", username);
		
		// Send first login response with username
		// Returns a response JSON with a challenge if username is correct
		JSONObject responseJSON = HttpsApi.post("login1", json, false);
		int response = Integer.parseInt((String) responseJSON.get("response"));
		
		if (response == 0) {
			challenge = (String) responseJSON.get("challenge");
			challenge.replace("\\", "");
			salt = (String) responseJSON.get("salt");
			
			// Convert hardcoded HMAC key for use
			SecretKey hashKey = CryptoUtil.convertStringToKey(Constants.hashKey, HMAC.ALGORITHM);
			// Scrypt password
			String hashedPass = CryptoUtil.scrypt(password, salt, 16384, 8, 1);
			// Calculate integrity tag for the hashed password
			String doubleHashPass = HMAC.calculateIntegrity(hashedPass, hashKey);
			
			// Create key out of the hashed password
			SecretKey passKey = CryptoUtil.convertStringToKey(doubleHashPass, HMAC.ALGORITHM);
			// Calculate integrity tag for the challenge using the hashed password key
			String hashTag = HMAC.calculateIntegrity(challenge, passKey);
			
			json.put("challenge", challenge);
			json.put("hashTag", hashTag);
			
			// Send second login response
			responseJSON = HttpsApi.post("login2", json, false);
			response = Integer.parseInt((String) responseJSON.get("response"));
			
			if (response == 0) {
				App.setJWT((String) responseJSON.get("jwt"));
				App.setUserID((int) ((long)responseJSON.get("userid")));
				App.setUsername(username);
				App.getWindow().setTitle("SecureChat - " + username);
				App.initKeys();
			}
		}
		
		return response;
	}

}
