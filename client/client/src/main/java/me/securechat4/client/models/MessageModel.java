package me.securechat4.client.models;

import me.securechat4.client.controllers.Controller;

public class MessageModel extends Model {
	
	private String currentID;

	public MessageModel(Controller controller) {
		super(controller);
		currentID = "";
	}
	
	public String getCurrentID() {
		return currentID;
	}
	
	public void setCurrentID(String userID) {
		currentID = userID;
	}

}
