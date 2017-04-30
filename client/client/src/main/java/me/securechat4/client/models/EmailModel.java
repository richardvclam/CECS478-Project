package me.securechat4.client.models;

import java.util.Base64;

import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.HttpsApi;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.crypto.CryptoUtil;

public class EmailModel extends Model {

	public EmailModel(Controller controller) {
		super(controller);
	}
	
	public int sendEmail(String username) {
		JSONObject json = new JSONObject();
		json.put("recipient", username);
		json.put("message", CryptoUtil.encodeKeyToString(App.getUserKeys().getRSAPublicKey()));
		
		JSONObject responseJSON = HttpsApi.post("email", json, true);
		
		int response = Integer.parseInt((String) responseJSON.get("response"));
		
		return response;
	}

}
