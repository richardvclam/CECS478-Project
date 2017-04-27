package me.securechat4.client.models;

import java.util.Base64;

import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.HttpsApi;
import me.securechat4.client.controllers.Controller;

public class EmailModel extends Model {

	public EmailModel(Controller controller) {
		super(controller);
	}
	
	public int sendEmail(String username) {
		//HttpsApi.get("user?username=" + username, null);
		JSONObject json = new JSONObject();
		json.put("recipient", username);
		json.put("message", Base64.getEncoder().encodeToString(App.getUserKeys().getPublicKey().getEncoded()));
		
		JSONObject responseJSON = HttpsApi.post("email", json, true);
		
		int response = Integer.parseInt((String) responseJSON.get("response"));
		
		return response;
	}

}
