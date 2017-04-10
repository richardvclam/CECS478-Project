package me.securechat4.client;

import java.awt.CardLayout;
import java.util.HashMap;

import org.json.simple.JSONObject;

import me.securechat4.client.controllers.Controller;
/**
 * 
 * @author Richard Lam
 * @author Mark Tsujimura
 *
 */
public class App {
	
	private static MainPanel panel;
	private static String jwt;
	private static int userID;
	
	public static HashMap<String, Controller> getControllers() {
		if (panel == null) 
			System.out.println("panel is null");
		if (panel.getControllers() == null) 
			System.err.println("controller is null");
		return panel.getControllers();
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
	
	public static String getJWT() {
		return App.jwt;
	}
	
	public static void setJWT(String jwt) {
		App.jwt = jwt;
	}
	
    public static void main(String[] args) {      
        //JSONObject jsonObject = Crypto.encrypt("Hi world!", "public.der");
        //System.out.println(Crypto.decrypt(jsonObject, "private.der"));
    	
    	panel = new MainPanel();
    	panel.init();
    	panel.addContent();
    	
    	Window window = new Window();
    	window.getContentPane().add(panel);
    	
    	// Always show login first!
    	CardLayout cardLayout = (CardLayout) App.panel.getLayout();
		cardLayout.show(App.panel, "login");
    	//window.pack();
    	window.setVisible(true);
    }
    
}
