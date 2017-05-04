package me.securechat4.client;

import java.awt.CardLayout;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import me.securechat4.client.controllers.Controller;
import me.securechat4.client.controllers.MessageController;
import me.securechat4.client.controllers.MessagesController;
import me.securechat4.client.crypto.CryptoUtil;

/**
 * 
 * @author Richard Lam
 * @author Mark Tsujimura
 *
 */
public class App {
	
	private static Window window;
	private static MainPanel panel;
	private static String jwt;
	private static int userID;
	private static String username;
	private static Thread refreshMessagesThread;
	private static HashMap<Integer, String> users = new HashMap<Integer, String>(); 
	private static Keys userKeys;
	private static SecretKey hmacKey;
	
	public static HashMap<String, Controller> getControllers() {
		if (panel == null) 
			System.out.println("panel is null");
		if (panel.getControllers() == null) 
			System.err.println("controller is null");
		return panel.getControllers();
	}
	
	public static Controller getController(String controller) {
		return panel.getController(controller);
	}
	
	public static HashMap<Integer, String> getUsers() {
		return users;
	}
	
	public static  void setUserlist(HashMap<Integer, String> list) {
		users = list;
	}
	
	public static int getIDFromUsername(String username) {
		System.out.println("User List: " + users);
		for (Entry<Integer, String> entry : users.entrySet()) {	
			if (entry.getValue().equals(username)) {
				return entry.getKey();
			}
		}
		
		return -1;
	}
	
	public static MainPanel getPanel() {
		return panel;
	}
	
	public static int getUserID() {
		return userID;
	}
	
	public static void setUserID(int userID) {
		App.userID = userID;
	}
	
	public static String getUsername() {
		return username;
	}
	
	public static void setUsername(String username) {
		App.username = username;
	}
	
	public static String getJWT() {
		return App.jwt;
	}
	
	public static void setJWT(String jwt) {
		App.jwt = jwt;
	}
	
	public static Window getWindow() {
		return window;
	}
	
	public static void initKeys() {
		userKeys = new Keys(username);
	}
	
	public static Keys getUserKeys() {
		return userKeys;
	}
	
	public static SecretKey getHMACKey() {
		return hmacKey;
	}
	
	public static void startRefreshThread() {
		refreshMessagesThread = new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					((MessagesController) App.getController("messages")).getMessagesFromServer(true);
					((MessageController) App.getController("message")).createMessagePanels();
				}
			}
		};
		
		refreshMessagesThread.start();
	}
	
    public static void main(String[] args) {      
        //JSONObject jsonObject = Crypto.encrypt("Hi world!", "public.der");
        //System.out.println(Crypto.decrypt(jsonObject, "private.der"));
    	hmacKey = CryptoUtil.convertStringToKey(Constants.hashKey, "HMAC");
    	
    	panel = new MainPanel();
    	panel.init();
    	panel.addContent();
    	
    	window = new Window();
    	window.getContentPane().add(panel);
    	
    	// Always show login first!
    	CardLayout cardLayout = (CardLayout) App.panel.getLayout();
		cardLayout.show(App.panel, "login");
    	//window.pack();
    	window.setVisible(true);
    }
    
}
