package me.securechat4.client;

import org.json.simple.JSONObject;
/**
 * 
 * @author Richard Lam
 * @author Mark Tsujimura
 *
 */
public class App {
	
	public static ContentPanel panel;
	
    public static void main(String[] args) {      
        //JSONObject jsonObject = Crypto.encrypt("Hi world!", "public.der");
        //System.out.println(Crypto.decrypt(jsonObject, "private.der"));
    	
    	Window window = new Window();
    	panel = new ContentPanel();
    	window.getContentPane().add(panel);
    	window.setVisible(true);
    }
    
}
