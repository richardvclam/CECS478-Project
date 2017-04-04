package me.securechat4.client;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import me.securechat4.client.controllers.Controller;
import me.securechat4.client.controllers.LoginController;
import me.securechat4.client.controllers.MainController;
import me.securechat4.client.controllers.RegisterController;

public class ContentPanel extends JPanel {
	
	public static HashMap<String, Controller> controllers = new HashMap<String, Controller>(); 
	
	public ContentPanel() {
		setBorder(null);
		setLayout(new CardLayout());
		setMinimumSize(new Dimension(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT));
		
		init();
		
		addContent();
	}
	
	public static void init() {
		controllers.put("login", new LoginController());
		controllers.put("register", new RegisterController());
		controllers.put("main", new MainController());
	}
	
	public void addContent() {
		for (Map.Entry<String, Controller> e : controllers.entrySet()) {
			add(e.getValue().getView(), e.getKey());
		}
	}

}
