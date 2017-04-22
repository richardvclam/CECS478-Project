package me.securechat4.client.models;

import org.json.simple.JSONObject;

import me.securechat4.client.HttpsApi;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.crypto.CryptoUtil;

public class RegisterModel extends Model {

	public RegisterModel(Controller controller) {
		super(controller);
	}
	
	public JSONObject register(String username, String password) {
		String hashedPass = CryptoUtil.scrypt(password);
		System.out.println(hashedPass.length());
		
		JSONObject json = new JSONObject();
		json.put("username", username);
		json.put("password", hashedPass);
		
		return HttpsApi.post("register", json);
	}
}
