package me.securechat4.client.models;

import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.HttpsApi;
import me.securechat4.client.controllers.Controller;

public class LoginModel extends Model {
	
	public LoginModel(Controller controller) {
		super(controller);
	}
	
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

}
