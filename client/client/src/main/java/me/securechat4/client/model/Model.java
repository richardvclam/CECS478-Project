package me.securechat4.client.model;

import me.securechat4.client.controller.Controller;

public abstract class Model {
	
	protected Controller controller;
	
	public Model(Controller controller) {
		this.controller = controller;
	}
	
	public Controller getController() {
		return controller;
	}

}
