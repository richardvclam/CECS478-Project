package me.securechat4.client.controllers;

import java.awt.event.ActionEvent;
import java.security.PublicKey;

import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.HttpsApi;
import me.securechat4.client.User;
import me.securechat4.client.crypto.RSA;
import me.securechat4.client.models.AddContactModel;
import me.securechat4.client.views.AddContactView;

public class AddContactController extends Controller {
	
	public AddContactController() {
		
		this.model = new AddContactModel(this);
		this.view = new AddContactView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println("Action: " + (!actionCommand.isEmpty() ? actionCommand : "Undefined"));
		switch (actionCommand) {
			case "Add User":
				String username = ((AddContactView) view).getUsernameField().getText();
				String key = ((AddContactView) view).getKeyField().getText();
				if (!username.isEmpty()) {
					JSONObject jsonResponse = (JSONObject) HttpsApi.get("user?username=" + username);
					int response = Integer.parseInt((String) jsonResponse.get("response"));
					
					if (response == 0) {
						int userid = (int)((long) jsonResponse.get("id"));
						username = (String) jsonResponse.get("username");
						
						if (!key.isEmpty()) {
							PublicKey publicKey = RSA.loadPublicKey(key);
							
							User user = new User();
							user.setUsername(username);
							user.setRSAPublicKey(publicKey);			
							
							App.getKeys().addUser(userid, user);
							App.getUsers().put(userid, username);
							
							((MessagesController) App.getController("messages")).changeDetailView("newMessage");
						}
					} else {
						System.out.println("User does not exist");
					}
				}
				break;
			default:
				System.out.println("Attempting to call undefined action.");
				break;
		}
	}
}
