package me.securechat4.client;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import me.securechat4.client.controllers.Controller;
import me.securechat4.client.controllers.LoginController;
import me.securechat4.client.controllers.MessageController;
import me.securechat4.client.controllers.MessagesController;
import me.securechat4.client.controllers.RegisterController;

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

}
