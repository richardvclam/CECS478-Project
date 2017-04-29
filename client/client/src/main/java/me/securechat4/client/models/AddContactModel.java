package me.securechat4.client.models;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.Constants;
import me.securechat4.client.HttpsApi;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.crypto.CryptoUtil;
import me.securechat4.client.crypto.HMAC;

public class AddContactModel extends Model {

	public AddContactModel(Controller controller) {
		super(controller);
	}
	
	
	public int addContact(String username, String key) {
		JSONObject json = new JSONObject();
		json.put("recipient", username);
		json.put("key", key);
		
		//TO DO
		
		return 0;
	}	
	
	
//	// Checks if the username actually exists
//	public int checkUser(String username) {
//		
//		JSONObject json = new JSONObject();
//		json.put("username", username);
//		
////		JSONObject responseJSON = HttpsApi.post("login1", json, false);
////		int response = Integer.parseInt((String) responseJSON.get("response"));
//		
//		if (response == 0) {
//			challenge = (String) responseJSON.get("challenge");
//			challenge.replace("\\", "");
//			salt = (String) responseJSON.get("salt");
//			
//			SecretKey hashKey = CryptoUtil.convertStringToKey(Constants.hashKey, HMAC.ALGORITHM);
//			String hashedPass = CryptoUtil.scrypt(password, salt, 16384, 8, 1);
//			String doubleHashPass = HMAC.calculateIntegrity(hashedPass, hashKey);
//			
//			SecretKey passKey = CryptoUtil.convertStringToKey(doubleHashPass, HMAC.ALGORITHM);
//			String hashTag = HMAC.calculateIntegrity(challenge, passKey);
//			
//			json.put("challenge", challenge);
//			json.put("hashTag", hashTag);
//			
//			responseJSON = HttpsApi.post("login2", json, false);
//			response = Integer.parseInt((String) responseJSON.get("response"));
//			
//			if (response == 0) {
//				App.setJWT((String) responseJSON.get("jwt"));
//				App.setUserID((int) ((long)responseJSON.get("userid")));
//				App.setUsername(username);
//				App.getWindow().setTitle("SecureChat - " + username);
//				App.initKeys();
//			}
//		}
////		
////		return response;
//	}

	
	

	
}
