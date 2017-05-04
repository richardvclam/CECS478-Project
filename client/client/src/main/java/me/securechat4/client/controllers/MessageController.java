package me.securechat4.client.controllers;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.models.MessageModel;
import me.securechat4.client.models.MessagesModel;
import me.securechat4.client.views.MessageView;
import me.securechat4.client.views.MessagesView;

public class MessageController extends Controller implements KeyListener, MouseListener {
	
	public MessageController() {
		this.model = new MessageModel(this);
		this.view = new MessageView(this);
		this.addEnabled = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println("Action: " + (!actionCommand.isEmpty() ? actionCommand : "Undefined"));
		switch (actionCommand) {
			case "Details":
				((UserDetailsController) App.getController("details")).update(
						((MessageModel) model).getCurrentID());
				((MessagesController) App.getController("messages")).changeDetailView("details");
				break;
		}
	}
	
	public void createMessagePanel(int id) {
		((MessageView) view).createMessagePanel(id);
	}
	
	public void createMessagePanels() {
		MessagesModel model = ((MessagesModel) App.getController("messages").getModel());
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
			case "Details":
				((JButton) e.getComponent()).setIcon(MessageView.detailsIconPressed);
				break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		String actionCommand = ((JButton) e.getSource()).getActionCommand();
		switch (actionCommand) {
			case "Details":
				((JButton) e.getComponent()).setIcon(MessageView.detailsIconNormal);
				break;
		}
	}

}
