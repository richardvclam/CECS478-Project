package me.securechat4.client.controllers;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import me.securechat4.client.App;
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
			case "Add Contact":
				//TO DO Add contact
				break;
//			case "Register":
//				CardLayout cardLayout = (CardLayout) App.getPanel().getLayout();
//				cardLayout.show(App.getPanel(), "register");
//				break;
			default:
				System.out.println("Attempting to call undefined action.");
				break;
		}
		
	}

}
