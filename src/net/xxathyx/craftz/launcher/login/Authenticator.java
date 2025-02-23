package net.xxathyx.craftz.launcher.login;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.logging.Logger;
import net.xxathyx.craftz.launcher.util.Http;

public class Authenticator {
	
	private static final Logger logger = Main.logger;
	
	private static String AUTH_URL = "https://api.craftz.net:8443";
	
	private boolean microsoft = false;
	
	private UUID uuid;
	
	private String access_token;
	
	private int coins;
	private String avatar_skin;
	
	private String username;
	
	public Authenticator(boolean microsoft, String username, String token) {
		this.microsoft = microsoft;
		this.username = username;
		this.access_token = token;
	}
	
	public boolean isValid() {
				
		if(microsoft) {
		    try {
		    	HttpURLConnection connection = (HttpURLConnection) (new URL("https://api.minecraftservices.com/minecraft/profile")).openConnection();
		        connection.addRequestProperty("Authorization", "Bearer " + access_token);
		        
		        if(connection.getResponseCode() != 200) return false;
		        
		        JSONObject response = new JSONObject(Http.readResponse(connection));
		        uuid = UUID.fromString(((String)response.get("id")).replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
	            username = (String)response.get("name");
	            
	            JSONArray skins = response.getJSONArray("skins");
	            JSONObject textures = skins.getJSONObject(0);
	            	            
	            avatar_skin = (String)textures.get("url");
	            
	            connection.disconnect();
	            return true;
			}catch (IOException e) {
				e.printStackTrace();
				logger.log(e);
			}
		}else {		    
		    try {
	    		HttpURLConnection connection = (HttpURLConnection) (new URL(AUTH_URL)).openConnection();
	            connection.setRequestMethod("POST");
	            connection.addRequestProperty("type", "login");
	            connection.addRequestProperty("username", username);
	            connection.addRequestProperty("token", access_token);

	            connection.setDoOutput(true);
	            try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {out.writeBytes("");out.flush();}
	            
	            String response = Http.readResponse(connection);
	            JSONObject jsonResponse = new JSONObject(response);
	            
	            if(connection.getResponseCode() == 200) {
	            	connection.disconnect();
		            uuid = UUID.fromString((String)jsonResponse.get("uuid"));
		            coins = (int)jsonResponse.get("coins");
	            	return true;
	            }else return false;
			}catch (IOException e) {
				e.printStackTrace();
				logger.log(e);
			}
		}	
		return false;
	}
	
	public boolean valid_username() {
		if(!username.matches("[A-Za-z0-9_]+") || username.length()<3 || username.length()>16) return false;
		return true;
	}
	
	public boolean isMicrosoft()  {
		return microsoft;
	}
	
	public String username() {
		return username;
	}
	
	public int coins() {
		return coins;
	}
	
	public String avatar_skin() {
		return avatar_skin;
	}

	public UUID uuid() {
		return uuid;
	}

	public String access_token() {
		return access_token;
	}
}