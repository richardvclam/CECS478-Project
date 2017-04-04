package me.securechat4.client.views;

import java.awt.Color;
import java.awt.Component;
import java.util.Map;

import javax.swing.JPanel;

import me.securechat4.client.controllers.Controller;

public abstract class View extends JPanel {
	
	public static final Color BLUE = new Color(1, 174, 242);
	public static final Color RED = new Color(252, 81, 48);
	// Light Blue - #A8DADC
	// Dark Blue - #1D3557
	// Honeydew - #F1FAEE

	protected Controller controller;
	protected Map<String, Component> components;
	
	public View(Controller controller) {
		this.controller = controller;
	}
	
	public Controller getController() {
		return controller;
	}
	
	public Map<String, Component> getViewComponents() {
		return components;
	}
	
	public Component getComponent(String componentName) {
		return components.get(componentName);
	}
	
	public void addComponents() {
		for (Component c : components.values()) {
			add(c);
		}
	}

}
