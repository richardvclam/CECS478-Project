package me.securechat4.client.models;

import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.DefaultListModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import me.securechat4.client.App;
import me.securechat4.client.HttpsApi;
import me.securechat4.client.User;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.crypto.Crypto;

public class MessagesModel extends Model {

	public DefaultListModel<String> conversations;
	private HashMap<Integer, LinkedList<JSONObject>> allMessages;

	public MessagesModel(Controller controller) {
		super(controller);
		
		conversations = new DefaultListModel<>();
	}
	
	public void addConversation(String username) {
		// Add unique username to list model
		if (!conversations.contains(username)) {
			conversations.addElement(username);
		}
	}
	
	public HashMap<Integer, LinkedList<JSONObject>> getAllMessages() {
		return allMessages;
	}
	
	public LinkedList<JSONObject> getMessages(String username) {
		return allMessages.get(username);
	}
	
	public void getMessagesFromServer(boolean update) {
		String uri = update ? "message?update=true" : "message";
		
		JSONArray messagesArray = (JSONArray) HttpsApi.get(uri);
		
		parseMessages(messagesArray);
	}

	@SuppressWarnings("unchecked")
	private void parseMessages(JSONArray messagesArray) {
		allMessages = new HashMap<>();
		
		messagesArray.forEach((object) -> {
			JSONObject messageJson = (JSONObject) object;

			int senderID = Integer.parseInt((String) messageJson.get("senderID"));
			int receiverID = Integer.parseInt((String) messageJson.get("receiverID"));
			
			int id;
			String username;
			
			// Set ID to the the user that is not us
			if (senderID != App.getUserID()) {
				id = senderID;
				username = (String) messageJson.get("sender");
			} else {
				id = receiverID;
				username = (String) messageJson.get("receiver"); 
			}
			
			/*
			// Index unique user id to username
			if (!App.getUsers().containsKey(id)) {
				App.getUsers().put(id, username);
			}
			*/
			
			// Decrypts incoming messages 
			// Replaces encoded encrypted json with decrypted message
			User user = App.getUserKeys().getUser(id);
			JSONParser parser = new JSONParser();
			try {
				JSONObject encryptedMessageJSON = (JSONObject) parser.parse((String) messageJson.get("data"));
				String decryptedMessage = Crypto.decrypt(encryptedMessageJSON, user.getAESKey(), user.getHMACKey());
				messageJson.remove("data");
				messageJson.put("data", decryptedMessage);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Add messages to appropriate conversations
			if (allMessages.containsKey(id)) {
				allMessages.get(id).add(messageJson);
			} else {
				LinkedList<JSONObject> messages = new LinkedList<>();
				messages.add(messageJson);
				allMessages.put(id, messages);
			}
			
			addConversation(username);
		});
	}

}
