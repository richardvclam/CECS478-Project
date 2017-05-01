package me.securechat4.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import me.securechat4.client.crypto.CryptoUtil;
import me.securechat4.client.crypto.RSA;

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
	private String filePath;
	
	public Keys(String username) {
		users = new HashMap<Integer, User>();
		filePath = "data/" + username + "_keys.json";
		
		if (new File(filePath).exists()) {
			parseJSONFile();
		}
	}
	
	public PublicKey getRSAPublicKey() {
		return rsaPublicKey;
	}
	
	public void setPublicKey(PublicKey publicKey) {
		this.rsaPublicKey = publicKey;
		
		writeJSONFile();
	}
	
	public PrivateKey getRSAPrivateKey() {
		return rsaPrivateKey;
	}
	
	public void setPrivateKey(PrivateKey privateKey) {
		this.rsaPrivateKey = privateKey;
		
		writeJSONFile();
	}
	
	public void addUser(int userID, User user) {
		users.put(userID, user);
		
		writeJSONFile();
	}
	
	public User getUser(int userID) {
		return users.get(userID);
	}
	
	public void generateKeys() {
		KeyPair keyPair = RSA.generateKeyPair();
		
		rsaPublicKey = keyPair.getPublic();
		rsaPrivateKey = keyPair.getPrivate();
		
		writeJSONFile();
	}
	
	private void parseJSONFile() {
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
		
		if (json.containsKey("rsaPublicKey")) {
			rsaPublicKey = RSA.loadPublicKey((String) json.get("rsaPublicKey"));
		}
		
		if (json.containsKey("rsaPrivateKey")) {
			rsaPrivateKey = RSA.loadPrivateKey((String) json.get("rsaPrivateKey"));
		}
		
		JSONArray keys = (JSONArray) json.get("keys");
		if (keys != null) {
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
			});
		}
	}
	
	private void writeJSONFile() {
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

	public HashMap<Integer, User> getUsers() {
		// TODO Auto-generated method stub
		return users;
	}

}
