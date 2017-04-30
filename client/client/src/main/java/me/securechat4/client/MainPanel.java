package me.securechat4.client;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import me.securechat4.client.controllers.AccountController;
import me.securechat4.client.controllers.AddContactController;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.controllers.EmailController;
import me.securechat4.client.controllers.LoginController;
import me.securechat4.client.controllers.MessageController;
import me.securechat4.client.controllers.MessagesController;
import me.securechat4.client.controllers.NewMessageController;
import me.securechat4.client.controllers.RegisterController;
import me.securechat4.client.controllers.UserDetailsController;

public class MainPanel extends JPanel {
	
	private HashMap<String, Controller> controllers;
	
	public MainPanel() {
		setBorder(null);
		setLayout(new CardLayout());
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));
		
		controllers = new HashMap<String, Controller>();
	}
	
	public void init() {
		controllers.put("login", new LoginController());
		controllers.put("register", new RegisterController());
		controllers.put("message", new MessageController());
		controllers.put("messages", new MessagesController());
		controllers.put("addContact", new AddContactController());
		controllers.put("account", new AccountController());
		controllers.put("email", new EmailController());
		controllers.put("newMessage", new NewMessageController());
		controllers.put("details", new UserDetailsController());
	}
	
	public void addContent() {
		for (Map.Entry<String, Controller> e : controllers.entrySet()) {
			if (e.getValue().isAddEnabled()) {
				add(e.getValue().getView(), e.getKey());
			}
		}
	}
	
	public HashMap<String, Controller> getControllers() {
		return controllers;
	}
	
	public Controller getController(String controller) {
		return controllers.get(controller);
	}

}
