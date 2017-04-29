package me.securechat4.client.controllers;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import me.securechat4.client.App;
import me.securechat4.client.UserKeys;
import me.securechat4.client.models.AddContactModel;
import me.securechat4.client.models.EmailModel;
import me.securechat4.client.views.AddContactView;
import me.securechat4.client.views.EmailView;

public class AddContactController extends Controller {
	
	public AddContactController() {
		
		this.model = new AddContactModel(this);
		this.view = new AddContactView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		System.out.println("Action: " + (!actionCommand.isEmpty() ? actionCommand : "Undefined"));
		switch (actionCommand) {
			case "Add User":
				String username = ((AddContactView) view).getUsernameField().getText();
				String key = ((AddContactView) view).getKeyField().getText();
				//TODO check if username is in database
								
				KeyFactory kf = null;
				try {
					kf = KeyFactory.getInstance("RSA");
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				PublicKey publicKey = null;
				try {
					publicKey = kf.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(key)));
				} catch (InvalidKeySpecException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//adding user to contact/message list
				UserKeys newContact = new UserKeys(username);
				newContact.setPublicKey(publicKey);
				
				break;
			default:
				System.out.println("Attempting to call undefined action.");
				break;
		}
	}
	
	
	//Base64.getEncoder().encodeToString(App.getUserKeys().getPublicKey().getEncoded())
}
