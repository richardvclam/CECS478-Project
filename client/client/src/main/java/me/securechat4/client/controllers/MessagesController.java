package me.securechat4.client.controllers;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import me.securechat4.client.App;
import me.securechat4.client.models.MessagesModel;
import me.securechat4.client.views.MessagesView;

public class MessagesController extends Controller implements ListSelectionListener, MouseListener {
	
	public MessagesController() {
		this.model = new MessagesModel(this);
		this.view = new MessagesView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println("Action: " + (!actionCommand.isEmpty() ? actionCommand : "Undefined"));
		switch (actionCommand) {
			case "Add Contact":
				changeDetailView("addContact");
				break;
			case "Account":
				changeDetailView("account");
				((MessagesView) view).getList().clearSelection();
				break;
			case "New Message":
				changeDetailView("addContact");
				((MessagesView) view).getList().clearSelection();
				break;
			default:
				System.out.println("Attempting to call undefined action.");
				break;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList lsm = (JList) e.getSource();

		if (!lsm.getValueIsAdjusting()) {
			JList list = ((MessagesView) getView()).getList();
			if (list.getSelectedValue() != null) {
				String selectedUser = list.getSelectedValue().toString();
				((MessageController) App.getControllers().get("message")).updateView(selectedUser);
				changeDetailView("message");
			}
		}
	}
	
	public void init() {
		MessagesView mv = ((MessagesView) view);
		mv.initDetailViews();

		changeDetailView("empty");
		
		getMessagesFromServer(false);
	}
	
	public void getMessagesFromServer(boolean update) {
		((MessagesModel) model).getMessagesFromServer(update);
	}
	
	public void changeDetailView(String panel) {
		((MessagesView) view).changeDetailView(panel);
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
			case "Account":
				((JButton) e.getComponent()).setIcon(MessagesView.accountIconPressed);
				break;
			case "New Message":
				((JButton) e.getComponent()).setIcon(MessagesView.newMsgIconPressed);
				break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		String actionCommand = ((JButton) e.getSource()).getActionCommand();
		switch (actionCommand) {
			case "Account":
				((JButton) e.getComponent()).setIcon(MessagesView.accountIconNormal);
				break;
			case "New Message":
				((JButton) e.getComponent()).setIcon(MessagesView.newMsgIconNormal);
				break;
		}
	}

}
