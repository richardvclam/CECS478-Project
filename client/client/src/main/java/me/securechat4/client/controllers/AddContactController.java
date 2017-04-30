package me.securechat4.client.controllers;

import java.awt.event.ActionEvent;
import java.security.PublicKey;


import me.securechat4.client.App;
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
				//TODO check if username is in database
								
				PublicKey publicKey = RSA.loadPublicKey(key);
				
				User user = new User();
				user.setRSAPublicKey(publicKey);
				
				// TODO need to get proper username and id from database
				App.getUserKeys().addUser(0, user);
				break;
			default:
				System.out.println("Attempting to call undefined action.");
				break;
		}
	}
}
