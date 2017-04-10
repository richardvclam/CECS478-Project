package me.securechat4.client.controllers;

import java.awt.event.ActionEvent;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import me.securechat4.client.App;
import me.securechat4.client.models.MessagesModel;
import me.securechat4.client.views.MessagesView;

public class MessagesController extends Controller implements ListSelectionListener{
	
	public MessagesController() {
		this.model = new MessagesModel(this);
		this.view = new MessagesView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList lsm = (JList) e.getSource();

		if (!lsm.getValueIsAdjusting()) {
			String selectedUser = ((MessagesView) getView()).getList().getSelectedValue().toString();
			((MessageController) App.getControllers().get("message")).updateView(selectedUser);
		}
	}

}
