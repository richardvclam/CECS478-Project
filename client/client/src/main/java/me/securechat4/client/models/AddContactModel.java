package me.securechat4.client.models;

import java.util.Base64;

import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.HttpsApi;
import me.securechat4.client.controllers.Controller;

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

	
}
