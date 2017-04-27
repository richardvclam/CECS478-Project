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

import me.securechat4.client.crypto.RSA;

public class UserKeys implements Serializable {
	
	/**
	 * 2048-bit RSA Public key.
	 */
	private PublicKey publicKey;
	
	/**
	 * 2048-bit RSA Private key.
	 */
	private PrivateKey privateKey;
	
	/**
	 * Collection of other users' public keys.
	 */
	private HashMap<Integer, PublicKey> usersKeys; 
	private String filePath;
	
	public UserKeys(String username) {
		usersKeys = new HashMap<Integer, PublicKey>();
		filePath = "data/" + username + "_keys.json";
		
		if (new File(filePath).exists()) {
			parseJSONFile();
		}
	}
	
	public PublicKey getPublicKey() {
		return publicKey;
	}
	
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
		
		writeJSONFile();
	}
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
		
		writeJSONFile();
	}
	
	public void addKey(int userID, PublicKey key) {
		usersKeys.put(userID, key);
		
		writeJSONFile();
	}
	
	public PublicKey getKey(int userID) {
		return usersKeys.get(userID);
	}
	
	public void generateKeys() {
		KeyPair keyPair = RSA.generateKeyPair();
		
		publicKey = keyPair.getPublic();
		privateKey = keyPair.getPrivate();
		
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
		
		publicKey = RSA.loadPublicKey(Base64.getDecoder().decode(((String) json.get("publicKey"))));
		privateKey = RSA.loadPrivateKey(Base64.getDecoder().decode(((String) json.get("privateKey"))));
		
		JSONArray keys = (JSONArray) json.get("keys");
		if (keys != null) {
			keys.forEach((object) -> {
				JSONObject user = (JSONObject) object;
				int id = Integer.parseInt((String) user.get("id"));
				String keyString = (String) user.get("publicKey"); 
				
				PublicKey key = RSA.loadPublicKey(Base64.getDecoder().decode(keyString));
				
				usersKeys.put(id, key);
			});
		}
	}
	
	private void writeJSONFile() {
		JSONObject json = new JSONObject();
		if (publicKey != null) {
			json.put("publicKey", Base64.getEncoder().encodeToString(publicKey.getEncoded()));
		}
		
		if (privateKey != null) {
			json.put("privateKey", Base64.getEncoder().encodeToString(privateKey.getEncoded()));
		}
		
		JSONArray array = new JSONArray();
		
		for (Entry<Integer, PublicKey> entry : usersKeys.entrySet()) {
			JSONObject user = new JSONObject();
			user.put("id", entry.getKey().toString());
			user.put("publicKey", Base64.getEncoder().encodeToString(entry.getValue().getEncoded()));
			
			array.add(user);
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

}
