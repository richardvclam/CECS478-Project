package me.securechat4.client.controller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

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
		System.out.println("Action: " + (!actionCommand.isEmpty() ? actionCommand : "Undefined"));
		switch (actionCommand) {
			case "Login":
				JTextField usernameField = (JTextField) getView().getComponent("usernameField");
				JPasswordField passwordField = (JPasswordField) getView().getComponent("passwordField");
				
				boolean empty = false;
				
				if (usernameField.getText().isEmpty()) {
					empty = true;
					((LoginView) getView()).displayEmptyUsernameLabel();
				} else {
					((LoginView) getView()).displayNormalUsernameLabel();
				}

				if (passwordField.getPassword().length == 0) {
					empty = true;
					((LoginView) getView()).displayEmptyPasswordLabel();
				} else {
					((LoginView) getView()).displayNormalPasswordLabel();
				}
				
				if (!empty) {
					JSONObject jwt = ((LoginModel) getModel()).login(usernameField.getText(), new String(passwordField.getPassword()));
					
					int response = Integer.parseInt((String) jwt.get("response"));
					System.out.println("Response: " + response);
					
					switch (response) {
						case 0: // Successful authentication
							((LoginView) getView()).displayNormalUsernameLabel();
							((LoginView) getView()).displayNormalPasswordLabel();
							App.setJWT(jwt);
							break;
						case 1: // Username does not exist
							((LoginView) getView()).displayInvalidUsername();
							break;
						case 2: // Password does not match
							((LoginView) getView()).displayInvalidPassword();
							break;
					}
				}
				break;
			case "Register":
				CardLayout cardLayout = (CardLayout) App.panel.getLayout();
				cardLayout.show(App.panel, "register");
				break;
			default:
				System.out.println("Attempting to call undefined action.");
				break;
		}
	}

}
