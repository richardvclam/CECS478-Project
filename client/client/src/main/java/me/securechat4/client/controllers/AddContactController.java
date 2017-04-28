package me.securechat4.client.controllers;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import me.securechat4.client.App;
import me.securechat4.client.models.AddContactModel;
import me.securechat4.client.models.EmailModel;
import me.securechat4.client.views.AddContactView;
import me.securechat4.client.views.EmailView;

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
			case "Add Contact":
				String username = ((AddContactView) view).getUsernameField().getText();
				String key = ((AddContactView) view).getKeyField().getText();
				
				
				break;
			default:
				System.out.println("Attempting to call undefined action.");
				break;
		}
		
	}

}
