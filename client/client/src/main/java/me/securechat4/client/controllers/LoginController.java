package me.securechat4.client.controllers;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import me.securechat4.client.App;
import me.securechat4.client.models.LoginModel;
import me.securechat4.client.models.MessagesModel;
import me.securechat4.client.views.LoginView;

public class LoginController extends Controller implements KeyListener {
	
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
				login();
				break;
			case "Register":
				CardLayout cardLayout = (CardLayout) App.getPanel().getLayout();
				cardLayout.show(App.getPanel(), "register");
				break;
			default:
				System.out.println("Attempting to call undefined action.");
				break;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			login();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void login() {
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
			int response = ((LoginModel) getModel()).login(usernameField.getText(), new String(passwordField.getPassword()));

			System.out.println("Response: " + response);
			
			switch (response) {
				case 0: // Successful authentication
					((LoginView) getView()).displayNormalUsernameLabel();
					((LoginView) getView()).displayNormalPasswordLabel();

					MessagesModel model = (MessagesModel) App.getControllers().get("messages").getModel();
					model.getMessagesFromServer();
					model.createListModel();
					
					((MessageController) App.getControllers().get("message")).createMessagePanels();
					App.startRefreshThread();
					
					CardLayout cardLayout = (CardLayout) App.getPanel().getLayout();
					cardLayout.show(App.getPanel(), "messages");
					break;
				case 1: // Username does not exist
					((LoginView) getView()).displayInvalidUsername();
					break;
				case 2: // Password does not match
					((LoginView) getView()).displayInvalidPassword();
					break;
			}
		}
	}

}