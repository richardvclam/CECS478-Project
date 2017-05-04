package me.securechat4.client.models;

import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.HttpsApi;
import me.securechat4.client.User;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.crypto.AES;
import me.securechat4.client.crypto.Crypto;
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
			User user = App.getUserKeys().getUser(currentID);
			if (user.getAESKey() != null && user.getHMACKey() != null) {
				JSONObject encryptedData = Crypto.encrypt(message, user.getAESKey(), user.getHMACKey());
				
				JSONObject json = new JSONObject();
				json.put("receiver", currentID);
				json.put("data", encryptedData.toJSONString());
				
				JSONObject responseJSON =  HttpsApi.post("message", json, true);
				int response = Integer.parseInt((String) responseJSON.get("response"));
				
				if (response == 0) {
					MessageView view = (MessageView) controller.getView();
					
					view.addMessage(
							view.getMessagePanels().get(currentID), 
							App.getUsername(), 
							message
					);
				}
			}
		}
	}

}
