package me.securechat4.client.model;

import org.json.simple.JSONObject;

import me.securechat4.client.HttpsApi;
import me.securechat4.client.controller.Controller;

public class LoginModel extends Model {
	
	public LoginModel(Controller controller) {
		super(controller);
	}
	
	public void login(String username, String password) {
		JSONObject json = new JSONObject();
		json.put("username", username);
		json.put("password", password);
		
		HttpsApi.post("login", json);
	}

}
