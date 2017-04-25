package me.securechat4.client.controllers;

import java.awt.event.ActionEvent;

import me.securechat4.client.models.AddContactModel;
import me.securechat4.client.models.LoginModel;
import me.securechat4.client.views.AddContactView;
import me.securechat4.client.views.LoginView;
import me.securechat4.client.views.MainView;

public class AddContactController extends Controller {
	
	public AddContactController() {
		
		this.model = new AddContactModel(this);
		this.view = new AddContactView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
