package me.securechat4.client.controller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import me.securechat4.client.App;
import me.securechat4.client.model.LoginModel;
import me.securechat4.client.model.RegisterModel;
import me.securechat4.client.view.RegisterView;
import me.securechat4.client.view.View;

public class RegisterController extends Controller {
	
	public RegisterController() {
		this.model = new RegisterModel(this);
		this.view = new RegisterView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println(actionCommand);
		switch (actionCommand) {
			case "Register":
				System.out.println("Attempting to login!");
				JTextField username = (JTextField) view.getComponent("usernameField");
				System.out.println(username.getText());
				JPasswordField passwordField = (JPasswordField) view.getComponent("passwordField");
				String password = new String(passwordField.getPassword());
				System.out.println(passwordField.getPassword());
				JPasswordField confirmPasswordField = (JPasswordField) view.getComponent("confirmPasswordField");
				String confirmPassword = new String(confirmPasswordField.getPassword());
				
				boolean valid = true;
				
				if (username.getText().equals("")) {
					((JLabel) view.getComponent("usernameLabel")).setText("Username (This field is required!)");
					((JLabel) view.getComponent("usernameLabel")).setForeground(View.RED);
					valid = false;
				} else {
					((JLabel) view.getComponent("usernameLabel")).setText("Username");
					((JLabel) view.getComponent("usernameLabel")).setForeground(Color.WHITE);
				}
				
				
				if (password.length() == 0) {
					((JLabel) view.getComponent("passwordLabel")).setText("Password (This field is required!)");
					((JLabel) view.getComponent("passwordLabel")).setForeground(View.RED);
					valid = false;
				} else {
					((JLabel) view.getComponent("passwordLabel")).setText("Password");
					((JLabel) view.getComponent("passwordLabel")).setForeground(Color.WHITE);
				}
				
				if (confirmPassword.length() == 0) {
					((JLabel) view.getComponent("confirmPasswordLabel")).setText("Confirm Password (This field is required!)");
					((JLabel) view.getComponent("confirmPasswordLabel")).setForeground(View.RED);
					valid = false;
				} else if (!confirmPassword.equals(password)) {
					((JLabel) view.getComponent("confirmPasswordLabel")).setText("Confirm Password (Passwords must match!)");
					((JLabel) view.getComponent("confirmPasswordLabel")).setForeground(View.RED);
					valid = false;
				} else {
					((JLabel) view.getComponent("confirmPasswordLabel")).setText("Confirm Password");
					((JLabel) view.getComponent("confirmPasswordLabel")).setForeground(Color.WHITE);
				}
				
				if (valid) {
					((JLabel) view.getComponent("usernameLabel")).setText("Username");
					((JLabel) view.getComponent("usernameLabel")).setForeground(Color.WHITE);
					((JLabel) view.getComponent("passwordLabel")).setText("Password");
					((JLabel) view.getComponent("passwordLabel")).setForeground(Color.WHITE);
					((JLabel) view.getComponent("confirmPasswordLabel")).setText("Confirm Password");
					((JLabel) view.getComponent("confirmPasswordLabel")).setForeground(Color.WHITE);				
					((RegisterModel) getModel()).register(username.getText(), password);
				}
				break;
			case "Login":
				CardLayout cardLayout = (CardLayout) App.panel.getLayout();
				cardLayout.show(App.panel, "login");
				break;
			default:
				System.out.println("Attempting to call action");
				break;
		}
		
	}

}
