package me.securechat4.client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import me.securechat4.client.model.Model;
import me.securechat4.client.view.View;

public abstract class Controller implements ActionListener {

	protected Model model;
	protected View view;
	
	public abstract void actionPerformed(ActionEvent e);
	
	public Model getModel() {
		return model;
	}
	
	public View getView() {
		return view;
	}

}
