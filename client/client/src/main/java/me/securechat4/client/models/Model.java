package me.securechat4.client.models;

import me.securechat4.client.controllers.Controller;

public abstract class Model {
	
	protected Controller controller;
	
	public Model(Controller controller) {
		this.controller = controller;
	}
	
	public Controller getController() {
		return controller;
	}

}
