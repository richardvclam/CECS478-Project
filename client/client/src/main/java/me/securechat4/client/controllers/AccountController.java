package me.securechat4.client.controllers;

import java.awt.event.ActionEvent;

import me.securechat4.client.App;
import me.securechat4.client.models.AccountModel;
import me.securechat4.client.views.AccountView;

public class AccountController extends Controller{
	
	public AccountController() {
		this.model = new AccountModel(this);
		this.view = new AccountView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println("Action: " + (!actionCommand.isEmpty() ? actionCommand : "Undefined"));
		switch (actionCommand) {
			case "Generate Key":
				App.getKeys().generateKeys();
				((AccountView) view).updatePublicKey();
				break;
			case "Email Key":
				((MessagesController) App.getController("messages")).changeDetailView("email");
				break;
		}
	}
	
	public void init() {
		((AccountView) view).init();
	}
	
}
