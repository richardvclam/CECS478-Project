package me.securechat4.client.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.prompt.PromptSupport;
import org.jdesktop.swingx.prompt.PromptSupport.FocusBehavior;
import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.Window;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.models.MessageModel;
import me.securechat4.client.models.MessagesModel;
import me.securechat4.client.views.templates.NavigationPane;

public class MessageView extends View {
	
	private NavigationPane navigationPane;
	private JPanel mainMessagePanel;
	private JTextArea messageTextArea;
	private HashMap<String, JPanel> messagePanels; 

	public MessageView(Controller controller) {
		super(controller);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));

		Font labelFont = new Font("Ariel", Font.BOLD, 14);
		
		messagePanels = new HashMap<String, JPanel>();
		
		mainMessagePanel = new JPanel();
		mainMessagePanel.setLayout(new CardLayout());
		
		//JLabel noContacts = new JLabel("Start a conversation!");
		//messagesPanel.add(noContacts);
		
		JPanel messageArea = new JPanel(new BorderLayout());
		messageArea.setBorder(BorderFactory.createLineBorder(new Color(237, 237, 237), 1));

		messageTextArea = new JTextArea();
		messageTextArea.setLineWrap(true);
		messageTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		messageTextArea.addKeyListener((KeyListener) controller);
		InputMap inputMap = messageTextArea.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap actionMap = messageTextArea.getActionMap();
		
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
		actionMap.put("enter", new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message = messageTextArea.getText();
				
				if (!message.isEmpty()) {
					((MessageModel) controller.getModel()).sendMessage(message);
				}
			}
		});
		
		messageArea.add(messageTextArea, BorderLayout.CENTER);
		
		PromptSupport.setPrompt("Type a message...", messageTextArea);
		PromptSupport.setFocusBehavior(FocusBehavior.HIDE_PROMPT, messageTextArea);
		
		JButton messageSendButton = new JButton("Send");
		messageSendButton.setBorder(new EmptyBorder(5, 5, 5, 5));
		messageSendButton.setFocusPainted(false);
		
		messageArea.add(messageTextArea);
		
		navigationPane = new NavigationPane("", null, mainMessagePanel);
		navigationPane.add(messageArea, BorderLayout.SOUTH);
		
		add(navigationPane);
	}
	
	public NavigationPane getNavPane() {
		return navigationPane;
	}
	
	public JPanel getMainMessagePanel() {
		return mainMessagePanel;
	}
	
	public HashMap<String, JPanel> getMessagePanels() {
		return messagePanels;
	}
	
	public JTextArea getMessageTextArea() {
		return messageTextArea;
	}
	
	public void createMessagePanels() {
		MessagesModel model = ((MessagesModel) App.getControllers().get("messages").getModel());
		HashMap<Integer, LinkedList<JSONObject>> allmessages = model.getAllMessages();
		
		for (Entry<Integer, LinkedList<JSONObject>> message : allmessages.entrySet()) {
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBackground(Color.WHITE);
			
			for (JSONObject json : message.getValue()) {
				String sender = (String) json.get("sender");
				String msg = (String) json.get("data"); 
				System.out.println(msg);
				JLabel messageLabel = new JLabel(sender + ": " + msg);
				panel.add(messageLabel);
			}
			
			mainMessagePanel.add(panel, message.getKey().toString());
			messagePanels.put(message.getKey().toString(), panel);
		}
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);
		
		JLabel noContacts = new JLabel("Start a conversation!");
		noContacts.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(noContacts);
		
		mainMessagePanel.add(panel, "empty");
		
		CardLayout layout = (CardLayout) mainMessagePanel.getLayout();
		layout.show(mainMessagePanel, "empty");
	}
	
	public void addMessage(String username, String message) {
		JPanel panel = messagePanels.get(Integer.toString(((MessageModel) controller.getModel()).getCurrentID()));
		
		JLabel label = new JLabel(username + ": " + message);
		
		panel.add(label);
		panel.revalidate();
		panel.repaint();
	}

}
