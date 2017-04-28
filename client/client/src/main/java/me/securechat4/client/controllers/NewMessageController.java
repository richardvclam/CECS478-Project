package me.securechat4.client.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import me.securechat4.client.App;
import me.securechat4.client.models.NewMessageModel;
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
				((MessagesController) App.getController("messages")).changeDetailView("empty");
				break;
			default:
				System.out.println("Attempting to call undefined action.");
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

}
