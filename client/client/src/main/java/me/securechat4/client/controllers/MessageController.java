package me.securechat4.client.controllers;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.models.MessageModel;
import me.securechat4.client.models.MessagesModel;
import me.securechat4.client.views.MessageView;

public class MessageController extends Controller implements KeyListener {
	
	public MessageController() {
		this.model = new MessageModel(this);
		this.view = new MessageView(this);
		this.addEnabled = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
	
	public void createMessagePanels() {
		MessagesModel model = ((MessagesModel) App.getControllers().get("messages").getModel());
		HashMap<Integer, LinkedList<JSONObject>> allmessages = model.getAllMessages();
		
		((MessageView) view).createMessagePanels(allmessages);
	}
	
	public void updateView(String username) {
		int userID = -1; 
		for (Entry<Integer, String> set : App.getUsers().entrySet()) {
			if (set.getValue().equals(username)) {
				userID = set.getKey();
			}
		}
		
		((MessageView) view).getNavPane().setHeaderLabel(username);
		((MessageModel) model).setCurrentID(userID);
		
		CardLayout cardLayout = (CardLayout) ((MessageView) view).getRootMessagePanel().getLayout();
		cardLayout.show(((MessageView) view).getRootMessagePanel(), Integer.toString(userID));
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			JTextArea textArea = ((MessageView) view).getMessageTextArea();
			String message = textArea.getText();
			
			if (!message.isEmpty()) {
				((MessageModel) model).sendMessage(message);
				textArea.setText(null);				
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
