package me.securechat4.client.controllers;

import java.awt.event.ActionEvent;

import me.securechat4.client.models.MessagesModel;
import me.securechat4.client.views.MessagesView;

public class MessagesController extends Controller {
	
	public MessagesController() {
		this.model = new MessagesModel(this);
		this.view = new MessagesView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
