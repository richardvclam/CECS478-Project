package me.securechat4.client.models;

import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.HttpsApi;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.views.MessageView;

public class MessageModel extends Model {
	
	private int currentID;

	public MessageModel(Controller controller) {
		super(controller);
		currentID = -1;
	}
	
	public int getCurrentID() {
		return currentID;
	}
	
	public void setCurrentID(int userID) {
		currentID = userID;
	}
	
	public void sendMessage(String message) {
		if (currentID != -1) {
			JSONObject json = new JSONObject();
			json.put("receiver", currentID);
			json.put("data", message);
			
			JSONObject responseJSON =  HttpsApi.post("message", json, true);
			int response = Integer.parseInt((String) responseJSON.get("response"));
			
			if (response == 0) {
				((MessageView) controller.getView()).addMessage(App.getUsername(), message);
			}
		}
	}

}
