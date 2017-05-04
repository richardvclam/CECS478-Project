package me.securechat4.client;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.crypto.SecretKey;

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
	private static Keys keys;
	
	/**
	 * Returns a specified controller.
	 * @param controller is the name of the controller to return
	 * @return a Controller
	 */
	public static Controller getController(String controller) {
		return panel.getController(controller);
	}
	
	/**
	 * Returns a set of usernames.
	 * @return a set of usersnames
	 */
	public static HashMap<Integer, String> getUsers() {
		return users;
	}
	
	/**
	 * Returns a user id from the username.
	 * @param username is the username to search for
	 * @return the user id
	 */
	public static int getIDFromUsername(String username) {
		// Searches through the map for the username
		for (Entry<Integer, String> entry : users.entrySet()) {	
			if (entry.getValue().equals(username)) {
				return entry.getKey();
			}
		}
		// Return -1 if cannot be found.
		return -1;
	}
	
	/**
	 * Returns the main panel.
	 * @return the main panel.
	 */
	public static MainPanel getPanel() {
		return panel;
	}
	
	/**
	 * Returns the current client's user ID.
	 * @return the user ID
	 */
	public static int getUserID() {
		return userID;
	}
	
	/**
	 * Sets the current client's user ID.
	 * @param userID the user ID to set
	 */
	public static void setUserID(int userID) {
		App.userID = userID;
	}
	
	/**
	 * Returns the current client's username.
	 * @return the username
	 */
	public static String getUsername() {
		return username;
	}
	
	/**
	 * Sets the current client's username.
	 * @param username the username to set
	 */
	public static void setUsername(String username) {
		App.username = username;
	}
	
	/**
	 * Returns the JSON web token.
	 * @return the JSON web token
	 */
	public static String getJWT() {
		return App.jwt;
	}
	
	/**
	 * Sets the JSON web token.
	 * @param jwt the JSON web token to set
	 */
	public static void setJWT(String jwt) {
		App.jwt = jwt;
	}
	
	/**
	 * Returns the window.
	 * @return the window
	 */
	public static Window getWindow() {
		return window;
	}
	
	/**
	 * Initializes the keys.
	 */
	public static void initKeys() {
		keys = new Keys(username);
	}
	
	/**
	 * Returns the set of keys.
	 * @return the set of keys
	 */
	public static Keys getKeys() {
		return keys;
	}
	
	/**
	 * Starts the refresh thread used to request the server for new messages. It sends
	 * a request to the server every 5 seconds.
	 */
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
    	panel = new MainPanel();
    	panel.init();
    	panel.addContent();
    	
    	window = new Window();
    	window.getContentPane().add(panel);
    	
    	// Always show login first!
    	CardLayout cardLayout = (CardLayout) App.panel.getLayout();
		cardLayout.show(App.panel, "login");
		
    	window.setVisible(true);
    }
    
}
