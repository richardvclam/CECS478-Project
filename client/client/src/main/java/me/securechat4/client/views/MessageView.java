package me.securechat4.client.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.prompt.PromptSupport;
import org.jdesktop.swingx.prompt.PromptSupport.FocusBehavior;
import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.Window;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.controllers.MessagesController;
import me.securechat4.client.models.MessageModel;
import me.securechat4.client.models.MessagesModel;
import me.securechat4.client.views.templates.NavigationPane;

public class MessageView extends View {
	
	private NavigationPane navigationPane;
	private JPanel messagesPanel;

	public MessageView(Controller controller) {
		super(controller);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));

		Font labelFont = new Font("Ariel", Font.BOLD, 14);
		
		messagesPanel = new JPanel();
		
		JLabel noContacts = new JLabel("Start a conversation!");
		messagesPanel.add(noContacts);
		
		JPanel messageArea = new JPanel(new BorderLayout());
		messageArea.setBorder(BorderFactory.createLineBorder(new Color(237, 237, 237), 1));

		JTextArea messageTextArea = new JTextArea();
		messageTextArea.setLineWrap(true);
		messageTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		messageArea.add(messageTextArea, BorderLayout.CENTER);
		
		PromptSupport.setPrompt("Type a message...", messageTextArea);
		PromptSupport.setFocusBehavior(FocusBehavior.HIDE_PROMPT, messageTextArea);
		
		JButton messageSendButton = new JButton("Send");
		messageSendButton.setBorder(new EmptyBorder(5, 5, 5, 5));
		messageSendButton.setFocusPainted(false);
		
		messageArea.add(messageTextArea);
		
		navigationPane = new NavigationPane("", null, messagesPanel);
		navigationPane.add(messageArea, BorderLayout.SOUTH);
		
		add(navigationPane);
	}
	
	public NavigationPane getNavPane() {
		return navigationPane;
	}
	
	public void addMessages(JPanel panel) {
		MessagesModel model = ((MessagesModel) App.getControllers().get("messages").getModel());
		LinkedList<JSONObject> messages = model.getMessages(((MessageModel) controller.getModel()).getCurrentID());
		
		for (JSONObject json : messages) {
			JLabel message = new JLabel((String) json.get("jsonEncMsg"));
			panel.add(message);
		}
	}

}
