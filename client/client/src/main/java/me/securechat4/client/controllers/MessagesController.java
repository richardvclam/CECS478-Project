package me.securechat4.client.controllers;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import me.securechat4.client.App;
import me.securechat4.client.models.MessagesModel;
import me.securechat4.client.views.AddContactView;
import me.securechat4.client.views.MessageView;
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
			case "Register":
				CardLayout cardLayout = (CardLayout) App.getPanel().getLayout();
				cardLayout.show(App.getPanel(), "register");
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
			String selectedUser = ((MessagesView) getView()).getList().getSelectedValue().toString();
			((MessageController) App.getControllers().get("message")).updateView(selectedUser);
			changeDetailView("message");
		}
	}
	
	public void init() {
		MessagesView mv = ((MessagesView) view);
		mv.initDetailViews();

		changeDetailView("empty");
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
		((JButton) e.getComponent()).setIcon(MessagesView.newMsgIconPressed);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		((JButton) e.getComponent()).setIcon(MessagesView.newMsgIconNormal);
	}

}
