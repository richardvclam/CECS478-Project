package me.securechat4.client.models;

import javax.crypto.SecretKey;

import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.HttpsApi;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.crypto.CryptoUtil;
import me.securechat4.client.crypto.HMAC;

public class LoginModel extends Model {
	
	public LoginModel(Controller controller) {
		super(controller);
	}
	
	/*
	public int login(String username, String password) {
		JSONObject loginJSON = new JSONObject();
		loginJSON.put("username", username);
		loginJSON.put("password", password);
		
		JSONObject loginResponseJSON =  HttpsApi.post("login", loginJSON);
		
		int response = Integer.parseInt((String) loginResponseJSON.get("response"));
		if (response == 0) {
			App.setJWT((String) loginResponseJSON.get("jwt"));
			App.setUserID(Integer.parseInt((String) loginResponseJSON.get("userid")));
		}
		//System.out.println("UserID: " + App.getUserID());
		
		return response;
	}
	*/
	
	public int login(String username, String password) {
		String challenge = "";
		String salt = "";
		
		JSONObject json = new JSONObject();
		json.put("username", username);
		
		JSONObject responseJSON = HttpsApi.post("login1", json);
		int response = Integer.parseInt((String) responseJSON.get("response"));
		
		if (response == 0) {
			challenge = (String) responseJSON.get("challenge");
			challenge.replace("\\", "");
			salt = (String) responseJSON.get("salt");
			
			SecretKey key = CryptoUtil.convertStringToKey(challenge, HMAC.ALGORITHM);
			String hashedPass = CryptoUtil.scrypt(password, salt, 16384, 8, 1);
			String hashTag = HMAC.calculateIntegrity(hashedPass, key);
			
			json.put("challenge", challenge);
			json.put("hashTag", hashTag);
			
			responseJSON = HttpsApi.post("login2", json);
			response = Integer.parseInt((String) responseJSON.get("response"));
			
			if (response == 0) {
				App.setJWT((String) responseJSON.get("jwt"));
				App.setUserID((int) ((long)responseJSON.get("userid")));
			}
		}
		
		return response;
	}

}
