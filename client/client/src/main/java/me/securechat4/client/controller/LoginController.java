package me.securechat4.client.controller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import me.securechat4.client.App;
import me.securechat4.client.model.LoginModel;
import me.securechat4.client.view.LoginView;
import me.securechat4.client.view.View;

public class LoginController extends Controller {
	
	public LoginController() {
		this.model = new LoginModel(this);
		this.view = new LoginView(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println(actionCommand);
		switch (actionCommand) {
			case "Login":
				System.out.println("Attempting to login!");
				JTextField username = (JTextField) view.getComponent("usernameField");
				System.out.println(username.getText());
				JPasswordField password = (JPasswordField) view.getComponent("passwordField");
				System.out.println(password.getPassword());
				
				boolean valid = true;
				
				if (username.getText().equals("")) {
					((JLabel) view.getComponent("usernameLabel")).setText("Username (This field is required!)");
					((JLabel) view.getComponent("usernameLabel")).setForeground(View.RED);
					valid = false;
				} else {
					((JLabel) view.getComponent("usernameLabel")).setText("Username");
					((JLabel) view.getComponent("usernameLabel")).setForeground(Color.WHITE);
				}
				
				
				if (password.getPassword().length == 0) {
					((JLabel) view.getComponent("passwordLabel")).setText("Password (This field is required!)");
					((JLabel) view.getComponent("passwordLabel")).setForeground(View.RED);
					valid = false;
				} else {
					((JLabel) view.getComponent("passwordLabel")).setText("Password");
					((JLabel) view.getComponent("passwordLabel")).setForeground(Color.WHITE);
				}
				
				
				if (valid) {
					((JLabel) view.getComponent("usernameLabel")).setText("Username");
					((JLabel) view.getComponent("usernameLabel")).setForeground(Color.WHITE);
					((JLabel) view.getComponent("passwordLabel")).setText("Password");
					((JLabel) view.getComponent("passwordLabel")).setForeground(Color.WHITE);
					((LoginModel) getModel()).login(username.getText(), new String(password.getPassword()));
				}
				break;
			case "Register":
				CardLayout cardLayout = (CardLayout) App.panel.getLayout();
				cardLayout.show(App.panel, "register");
				break;
			default:
				System.out.println("Attempting to call action");
				break;
		}
	}

}
