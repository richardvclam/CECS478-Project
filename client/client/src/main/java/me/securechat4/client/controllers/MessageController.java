package me.securechat4.client.controllers;

import java.awt.event.ActionEvent;

import me.securechat4.client.models.MessageModel;
import me.securechat4.client.views.MessageView;

public class MessageController extends Controller {
	
	public MessageController() {
		this.model = new MessageModel(this);
		this.view = new MessageView(this);
		this.addEnabled = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateView(String username) {
		((MessageView) view).getNavPane().setHeaderLabel(username);
		((MessageModel) model).setCurrentID(username);
	}

}
