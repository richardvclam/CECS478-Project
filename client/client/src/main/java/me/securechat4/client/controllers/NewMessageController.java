package me.securechat4.client.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JList;

import me.securechat4.client.App;
import me.securechat4.client.models.NewMessageModel;
import me.securechat4.client.views.MessagesView;
import me.securechat4.client.views.NewMessageView;

public class NewMessageController extends Controller implements MouseListener {
	
	public NewMessageController() {
		this.model = new NewMessageModel(this);
		this.view = new NewMessageView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println("Action: " + (!actionCommand.isEmpty() ? actionCommand : "Undefined"));
		switch (actionCommand) {
			case "Add Contact":
				((MessagesController) App.getController("messages")).changeDetailView("addContact");
				break;
			case "Select":
				// Get selected contact username from list
				JList list = ((NewMessageView) getView()).getList();
				if (list.getSelectedValue() != null) {
					String selectedUser = list.getSelectedValue().toString();
					// Check if user conversation already exists
					((MessagesController) App.getController("messages")).addConversation(selectedUser);
					((MessageController) App.getController("message")).updateView(selectedUser);
					
					((NewMessageModel) model).createConversation(selectedUser);
				}

				((MessagesController) App.getController("messages")).changeDetailView("message");
				break;
			default:
				System.out.println("Attempting to call undefined action.");
				break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		String actionCommand = ((JButton) e.getSource()).getActionCommand();
		switch (actionCommand) {
			case "Add Contact":
				((JButton) e.getComponent()).setIcon(NewMessageView.addIconPressed);
				break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		String actionCommand = ((JButton) e.getSource()).getActionCommand();
		switch (actionCommand) {
			case "Add Contact":
				((JButton) e.getComponent()).setIcon(NewMessageView.addIconNormal);
				break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
