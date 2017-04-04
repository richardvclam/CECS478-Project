package me.securechat4.client.models;

import org.json.simple.JSONObject;

import me.securechat4.client.HttpsApi;
import me.securechat4.client.controllers.Controller;

public class LoginModel extends Model {
	
	public LoginModel(Controller controller) {
		super(controller);
	}
	
	public JSONObject login(String username, String password) {
		JSONObject loginJSON = new JSONObject();
		loginJSON.put("username", username);
		loginJSON.put("password", password);
		
		return HttpsApi.post("login", loginJSON);
	}

}
