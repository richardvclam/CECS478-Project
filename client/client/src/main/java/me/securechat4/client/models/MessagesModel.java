package me.securechat4.client.models;

import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.DefaultListModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.HttpsApi;
import me.securechat4.client.controllers.Controller;

public class MessagesModel extends Model {
	
	//public ArrayList<Integer> userIDs;
	public DefaultListModel<String> usernames;
	private HashMap<Integer, LinkedList<JSONObject>> allMessages;

	public MessagesModel(Controller controller) {
		super(controller);
		
		usernames = new DefaultListModel<>();
		allMessages = new HashMap<>();
	}
	
	public void createListModel() {
		for (String username : App.getUsers().values()) {
			usernames.addElement(username);
		}		
	}
	
	public void getMessagesFromServer() {
		JSONArray messagesArray = HttpsApi.get("message", null);
		
		parseMessages(messagesArray);
		
		System.out.println("hi");
	}
	
	public HashMap<Integer, LinkedList<JSONObject>> getAllMessages() {
		return allMessages;
	}
	
	public LinkedList<JSONObject> getMessages(String username) {
		return allMessages.get(username);
	}

	@SuppressWarnings("unchecked")
	private void parseMessages(JSONArray messagesArray) {
		messagesArray.forEach((object) -> {
			JSONObject messageJson = (JSONObject) object;
			int senderID = Integer.parseInt((String) messageJson.get("senderID"));
			String senderName = (String) messageJson.get("sender");
			if (senderID != App.getUserID() && !App.getUsers().containsKey(senderID)) {
				App.getUsers().put(senderID, senderName);
				//userIDs.addElement((String) messageJson.get("sender"));
			}
			
			int receiverID = Integer.parseInt((String) messageJson.get("receiverID"));
			String receiverName = (String) messageJson.get("receiver"); 
			if (receiverID != App.getUserID() && !App.getUsers().containsKey(receiverID)) {
				App.getUsers().put(receiverID, receiverName);
				//userIDs.addElement((String) messageJson.get("receiver"));
			}
			
			int id = senderID == App.getUserID() ? receiverID : senderID;

			if (allMessages.containsKey(id)) {
				allMessages.get(id).add(messageJson);
			} else {
				LinkedList<JSONObject> messages = new LinkedList<>();
				messages.add(messageJson);
				
				allMessages.put(id, messages);
			}
		});
	}

}
