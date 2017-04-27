package me.securechat4.client.controllers;

import java.awt.event.ActionEvent;

import me.securechat4.client.models.EmailModel;
import me.securechat4.client.views.EmailView;

public class EmailController extends Controller {

	public EmailController() {
		this.model = new EmailModel(this);
		this.view = new EmailView(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println("Action: " + (!actionCommand.isEmpty() ? actionCommand : "Undefined"));
		switch (actionCommand) {
			case "Send":
				String username = ((EmailView) view).getUsernameField().getText();
				int response = ((EmailModel) model).sendEmail(username);
				
				if (response == 0) {
					
					//((MessagesController) App.getController("messages")).changeDetailView("account");
				}
				break;
		}
	}

}
