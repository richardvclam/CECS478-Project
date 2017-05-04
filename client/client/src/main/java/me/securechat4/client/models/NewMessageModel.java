package me.securechat4.client.models;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.LinkedList;

import javax.crypto.SecretKey;
import javax.swing.DefaultListModel;

import org.json.simple.JSONObject;

import me.securechat4.client.App;
import me.securechat4.client.HttpsApi;
import me.securechat4.client.User;
import me.securechat4.client.controllers.Controller;
import me.securechat4.client.crypto.CryptoUtil;
import me.securechat4.client.crypto.ECDHE;
import me.securechat4.client.crypto.RSA;
import me.securechat4.client.views.MessagesView;

public class NewMessageModel extends Model {

	public DefaultListModel<User> contact;
	public static DefaultListModel<String> contactList;
	
	
	public NewMessageModel(Controller controller) {
		super(controller);
		
		contactList = new DefaultListModel<>();
	}
	
	public int createConversation(String username) {
		// Generate DH and send to server
		KeyPair keyPair = ECDHE.generateKeyPair();
		PrivateKey ourPrivateKey = keyPair.getPrivate();
		PublicKey ourPublicKey = keyPair.getPublic();
		String ourEncodedKey = CryptoUtil.encodeKeyToString(ourPublicKey);
		
		int id = App.getIDFromUsername(username);
		JSONObject jsonOut = new JSONObject();
		jsonOut.put("id", id);
		jsonOut.put("key", ourEncodedKey);
		jsonOut.put("signature", RSA.sign(ourEncodedKey, App.getUserKeys().getRSAPrivateKey()));
		
		// Add keys to respected user
		User user = App.getUserKeys().getUser(id);
		user.setDHPrivateKey(ourPrivateKey);
		user.setDHPublicKey(ourPublicKey);
		
		App.getUserKeys().writeJSONFile();
		
		HttpsApi.post("key", jsonOut, true);
		
		// Pull other user's DH from server
		return pullDHFromServer(id);
	}
	
	public int pullDHFromServer(int userid) {
		JSONObject jsonResponse = (JSONObject) HttpsApi.get("key?id=" + userid);
		User user = App.getUserKeys().getUser(userid);
		
		int response = Integer.parseInt((String) jsonResponse.get("response"));
		
		if (response == 0) {
			String encodedKey = (String) jsonResponse.get("key");
			String signature = (String) jsonResponse.get("signature");
			
			// Verify signature
			if (RSA.verify(encodedKey, signature, user.getRSAPublicKey())) {
				// Create shared secret
				PublicKey dhPublicKey = ECDHE.loadPublicKey(encodedKey);
				byte[] sharedSecret = ECDHE.generateSharedSecretKey(user.getDHPrivateKey(), dhPublicKey);
				
				// Derive keys from shared secret
				SecretKey aesKey = ECDHE.deriveKey(sharedSecret, "encryption");
				SecretKey hmacKey = ECDHE.deriveKey(sharedSecret, "integrity");
				
				// Add derived keys to JSON
				user.setAESKey(aesKey);
				user.setHMACKey(hmacKey);
				
				App.getUserKeys().writeJSONFile();
			} else {
				System.out.println("Error trying to verify key signature.");
			}
		} else if (response == 1) {
			System.out.println("No keys available.");
		}
		
		return response;
	}

	public HashMap<Integer, User> getAllContact() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
