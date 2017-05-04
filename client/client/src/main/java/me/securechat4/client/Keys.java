package me.securechat4.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import me.securechat4.client.crypto.CryptoUtil;
import me.securechat4.client.crypto.RSA;
import me.securechat4.client.models.NewMessageModel;

public class Keys implements Serializable {
	
	/**
	 * Owner's 2048-bit RSA Public key.
	 */
	private PublicKey rsaPublicKey;
	
	/**
	 * Owner's 2048-bit RSA Private key.
	 */
	private PrivateKey rsaPrivateKey;
	
	/**
	 * Collection of other users.
	 */
	private HashMap<Integer, User> users;
	
	/**
	 * File path to write the JSON file to.
	 */
	private String filePath;
	
	/**
	 * Initializes the key file. If it exists, parse the JSON file.
	 * @param username is the username of the file to read from
	 */
	public Keys(String username) {
		users = new HashMap<Integer, User>();
		filePath = "data/" + username + "_keys.json";
		
		if (new File(filePath).exists()) {
			parseJSONFile();
		}
	}
	
	/**
	 * Returns a RSA public key.
	 * @return RSA public key
	 */
	public PublicKey getRSAPublicKey() {
		return rsaPublicKey;
	}
	
	/**
	 * Sets the RSA public key.
	 * @param publicKey the public key to set
	 */
	public void setPublicKey(PublicKey publicKey) {
		this.rsaPublicKey = publicKey;
		
		writeJSONFile();
	}
	
	/**
	 * Returns a RSA private key.
	 * @return RSA private key
	 */
	public PrivateKey getRSAPrivateKey() {
		return rsaPrivateKey;
	}
	
	/**
	 * Sets the RSA private key.
	 * @param privateKey the private key to set
	 */
	public void setPrivateKey(PrivateKey privateKey) {
		this.rsaPrivateKey = privateKey;
		
		writeJSONFile();
	}
	
	/**
	 * Adds a new user to the keyset.
	 * @param userID is the userID to map the user to
	 * @param user is the user with the stored keys
	 */
	public void addUser(int userID, User user) {
		users.put(userID, user);
		
		writeJSONFile();
	}
	
	/**
	 * Returns the user with the user ID.
	 * @param userID the user ID to return for
	 * @return a user
	 */
	public User getUser(int userID) {
		return users.get(userID);
	}
	
	/**
	 * Generate RSA private and public keys for the user. Writes the
	 * resulting keys to file.
	 */
	public void generateKeys() {
		KeyPair keyPair = RSA.generateKeyPair();
		
		rsaPublicKey = keyPair.getPublic();
		rsaPrivateKey = keyPair.getPrivate();
		
		writeJSONFile();
	}
	
	/**
	 * Parses a JSON file into memory.
	 */
	private void parseJSONFile() {
		// Initializes the buffer
		String jsonStr = "";
		try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
			jsonStr = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Parses the JSON into an object
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {
			json = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// Loads RSA public key
		if (json.containsKey("rsaPublicKey")) {
			rsaPublicKey = RSA.loadPublicKey((String) json.get("rsaPublicKey"));
		}
		
		// Loads RSA private key
		if (json.containsKey("rsaPrivateKey")) {
			rsaPrivateKey = RSA.loadPrivateKey((String) json.get("rsaPrivateKey"));
		}
		
		// Loads all contact keys
		JSONArray keys = (JSONArray) json.get("keys");
		if (keys != null) {
			// Iterates through each JSON object
			keys.forEach((object) -> {
				JSONObject userJson = (JSONObject) object;
				User user = new User();
				
				int id = Integer.parseInt((String) userJson.get("id"));
				user.setUsername((String) userJson.get("username"));
				user.loadRSAPublicKey((String) userJson.get("rsaPublicKey"));
				user.loadDHPublicKey((String) userJson.get("dhPublicKey"));
				user.loadDHPrivateKey((String) userJson.get("dhPrivateKey"));
				user.loadAESKey((String) userJson.get("aesKey"));
				user.loadHMACKey((String) userJson.get("hmacKey"));
				
				users.put(id, user);

				App.getUsers().put(id, user.getUsername());
			});
		}
	}
	
	/**
	 * Create a JSON file which stores the current user's key info and contact lists.
	 */
	public void writeJSONFile() {
		JSONObject json = new JSONObject();
		
		if (rsaPublicKey != null) {
			json.put("rsaPublicKey", CryptoUtil.encodeKeyToString(rsaPublicKey));
		}
		
		if (rsaPrivateKey != null) {
			json.put("rsaPrivateKey", CryptoUtil.encodeKeyToString(rsaPrivateKey));
		}
		
		JSONArray array = new JSONArray();
		
		for (Entry<Integer, User> entry : users.entrySet()) {
			User user = entry.getValue();
			JSONObject userJson = new JSONObject();
			
			userJson.put("id", entry.getKey().toString());
			userJson.put("username", user.getUsername());
			
			if (user.getRSAPublicKey() != null) {
				userJson.put("rsaPublicKey", CryptoUtil.encodeKeyToString(user.getRSAPublicKey()));
			}
			
			/*
			 * Check if AES and HMAC keys are already defined, if so add keys to JSON.
			 * Otherwise add DH keys because this means the user hasn't swapped and
			 * computed a shared secret key yet.
			 */
			if (user.getAESKey() != null && user.getHMACKey() != null) {
				userJson.put("aesKey", CryptoUtil.encodeKeyToString(user.getAESKey()));
				userJson.put("hmacKey", CryptoUtil.encodeKeyToString(user.getHMACKey()));
			} else if (user.getDHPrivateKey() != null && user.getDHPublicKey() != null){
				userJson.put("dhPrivateKey", CryptoUtil.encodeKeyToString(user.getDHPrivateKey()));
				userJson.put("dhPublicKey", CryptoUtil.encodeKeyToString(user.getDHPublicKey()));
			}

			array.add(userJson);
		}
		
		json.put("keys", array);
		
		File dataDirectory = new File("data");
		if (!dataDirectory.exists()) {
			dataDirectory.mkdir();
		}
		
		try (BufferedWriter out = Files.newBufferedWriter(Paths.get(filePath))) {
			json.writeJSONString(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parse out the username from the JSON file to make contact List
	 */
	public void parseOutUsername() {
		String jsonStr = "";
		try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
			jsonStr = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {
			json = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		JSONArray keys = (JSONArray) json.get("keys");
		System.out.println("Keys:" + keys);
		if (keys != null) {
			keys.forEach((object) -> {
				JSONObject userJson = (JSONObject) object;
				if (!NewMessageModel.contactList.contains((String) userJson.get("username")))
				NewMessageModel.contactList.addElement((String) userJson.get("username"));
			});
		}

	}	

}
