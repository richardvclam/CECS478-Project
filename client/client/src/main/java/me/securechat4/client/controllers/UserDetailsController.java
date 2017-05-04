package me.securechat4.client.controllers;

import java.awt.event.ActionEvent;

import me.securechat4.client.App;
import me.securechat4.client.User;
import me.securechat4.client.models.EmailModel;
import me.securechat4.client.models.MessageModel;
import me.securechat4.client.models.NewMessageModel;
import me.securechat4.client.models.UserDetailsModel;
import me.securechat4.client.views.UserDetailsView;

public class UserDetailsController extends Controller {
	
	public UserDetailsController() {
		this.model = new UserDetailsModel(this);
		this.view = new UserDetailsView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println("Action: " + (!actionCommand.isEmpty() ? actionCommand : "Undefined"));
		switch (actionCommand) {
			case "Back":
				((MessagesController) App.getController("messages")).changeDetailView("message");
				break;
			case "Edit":
				
				//((MessagesView) view).getList().clearSelection();
				break;
			case "Exchange":
				((NewMessageModel) App.getController("newMessage").getModel()).pullDHFromServer(
						((MessageModel) App.getController("message").getModel()).getCurrentID());
				break;
			case "Email":
				((EmailModel) App.getController("email").getModel()).sendEmail(
						App.getUsers().get(((MessageModel) App.getController("message").getModel()).getCurrentID()));
				break;
		}
	}
	
	public void update(int userid) {
		User user = App.getKeys().getUser(userid);
		
		((UserDetailsView) view).update(user.getUsername(), user.getRSAPublicKey(), user.getAESKey(), user.getHMACKey());
	}

}
