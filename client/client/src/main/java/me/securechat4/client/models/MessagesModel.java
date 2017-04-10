package me.securechat4.client.models;

import java.util.ArrayList;
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
	public DefaultListModel<String> userIDs;
	private HashMap<String, LinkedList<JSONObject>> allUserMessages;

	public MessagesModel(Controller controller) {
		super(controller);
		
		//userIDs = new ArrayList<Integer>();
		userIDs = new DefaultListModel<>();
		allUserMessages = new HashMap<>();
	}
	
	public void getMessagesFromServer() {
		JSONArray messagesArray = HttpsApi.get("message", null);
		
		sortMessages(messagesArray);
		
		System.out.println("hi");
	}

	@SuppressWarnings("unchecked")
	private void sortMessages(JSONArray messagesArray) {
		messagesArray.forEach((object) -> {
			final JSONObject messageJson = (JSONObject) object;
			final int senderID = Integer.parseInt((String) messageJson.get("senderID"));
			final String senderName = (String) messageJson.get("sender");
			if (senderID != App.getUserID() && !userIDs.contains(senderName)) {
				userIDs.addElement((String) messageJson.get("sender"));
			}
			
			final int receiverID = Integer.parseInt((String) messageJson.get("receiverID"));
			final String receiverName = (String) messageJson.get("receiver"); 
			if (receiverID != App.getUserID() && !userIDs.contains((String) messageJson.get("receiver"))) {
				userIDs.addElement((String) messageJson.get("receiver"));
			}
			
			final String id = senderID == App.getUserID() ? receiverName : senderName;

			if (allUserMessages.containsKey(id)) {
				allUserMessages.get(id).add(messageJson);
			} else {
				final LinkedList<JSONObject> messages = new LinkedList<>();
				messages.add(messageJson);
				
				allUserMessages.put(id, messages);
			}
		});
	}

}
