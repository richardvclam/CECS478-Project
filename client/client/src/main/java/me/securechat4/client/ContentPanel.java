package me.securechat4.client;

import java.awt.CardLayout;
import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import me.securechat4.client.controller.Controller;
import me.securechat4.client.controller.LoginController;
import me.securechat4.client.controller.RegisterController;

public class ContentPanel extends JPanel {
	
	public static HashMap<String, Controller> controllers = new HashMap<String, Controller>(); 
	
	public ContentPanel() {
		setBorder(null);
		setLayout(new CardLayout());
		
		init();
		
		addContent();
	}
	
	public static void init() {
		controllers.put("login", new LoginController());
		controllers.put("register", new RegisterController());
	}
	
	public void addContent() {
		for (Map.Entry<String, Controller> e : controllers.entrySet()) {
			add(e.getValue().getView(), e.getKey());
		}
	}

}
