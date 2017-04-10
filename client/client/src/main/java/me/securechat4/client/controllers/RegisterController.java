package me.securechat4.client.controllers;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.models.RegisterModel;
import me.securechat4.client.views.RegisterView;

public class RegisterController extends Controller {
	
	public RegisterController() {
		this.model = new RegisterModel(this);
		this.view = new RegisterView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println("Action: " + actionCommand);
		switch (actionCommand) {
			case "Register":
				System.out.println("Attempting to register!");
				JTextField username = (JTextField) view.getComponent("usernameField");
				System.out.println(username.getText());
				JPasswordField passwordField = (JPasswordField) view.getComponent("passwordField");
				String password = new String(passwordField.getPassword());
				System.out.println(passwordField.getPassword());
				JPasswordField confirmPasswordField = (JPasswordField) view.getComponent("confirmPasswordField");
				String confirmPassword = new String(confirmPasswordField.getPassword());
				
				boolean valid = true;
				
				if (username.getText().isEmpty()) {
					valid = false;
					((RegisterView) getView()).displayEmptyUsernameLabel();
				} else {
					((RegisterView) getView()).displayNormalUsernameLabel();
				}
				
				if (password.length() == 0) {
					valid = false;
					((RegisterView) getView()).displayEmptyPasswordLabel();
				} else {
					((RegisterView) getView()).displayNormalPasswordLabel();
				}
				
				if (confirmPassword.length() == 0) {
					valid = false;
					((RegisterView) getView()).displayEmptyConfirmPasswordLabel();
				} else if (!confirmPassword.equals(password)) {
					valid = false;
					((RegisterView) getView()).displayInvalidConfirmPasswordLabel();
				} else {
					((RegisterView) getView()).displayNormalConfirmPasswordLabel();
				}
				
				if (valid) {				
					JSONObject jsonResponse = ((RegisterModel) getModel()).register(username.getText(), password);
					
					int response = Integer.parseInt((String) jsonResponse.get("response"));
					System.out.println("Response: " + response);
					
					switch (response) {
						case 0: // Successful registration
							((RegisterView) getView()).displayNormalUsernameLabel();
							((RegisterView) getView()).displayNormalPasswordLabel();
							CardLayout cardLayout = (CardLayout) App.getPanel().getLayout();
							cardLayout.show(App.getPanel(), "login");
							break;
						case 1: // Username already exists
							((RegisterView) getView()).displayInvalidUsername();
							break;
						case 2: // SQL Error
							break;
					}
				}
				break;
			case "Login":
				CardLayout cardLayout = (CardLayout) App.getPanel().getLayout();
				cardLayout.show(App.getPanel(), "login");
				break;
			default:
				System.out.println("Attempting to call undefined action.");
				break;
		}
		
	}

}
