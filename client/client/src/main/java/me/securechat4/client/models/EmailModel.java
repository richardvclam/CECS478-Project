package me.securechat4.client.models;

import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.HttpsApi;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.crypto.CryptoUtil;

public class EmailModel extends Model {

	public EmailModel(Controller controller) {
		super(controller);
	}
	
	/**
	 * Sends an email with an attached RSA public key to the specified username.
	 * @param username is the username to send the RSA public key to
	 * @return server response
	 */
	public int sendEmail(String username) {
		JSONObject json = new JSONObject();
		json.put("recipient", username);
		json.put("message", CryptoUtil.encodeKeyToString(App.getKeys().getRSAPublicKey()));
		
		JSONObject responseJSON = HttpsApi.post("email", json, true);
		
		int response = Integer.parseInt((String) responseJSON.get("response"));
		
		return response;
	}

}
