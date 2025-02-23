package net.xxathyx.craftz.launcher.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import net.xxathyx.craftz.launcher.service.Response;
import net.xxathyx.craftz.launcher.service.Service;

public class API {
	
	private static String ACCOUNT_URL;
	private static String PROFILE_URL;
	private static String RANKS_URL;
	//private static String SKINS_URL;
	
	public static void logged(UUID uuid) {request(ACCOUNT_URL, false, "logged", Arrays.asList(Map.entry("uuid", uuid.toString())));}
	
	public static boolean exists(UUID uuid) {return request(ACCOUNT_URL, false, "exists", Arrays.asList(Map.entry("uuid", uuid.toString()))).getBool("exists");}
	public static boolean exists(String username) {return request(ACCOUNT_URL, false, "exists", Arrays.asList(Map.entry("uuid", username))).getBool("exists");}
	
	public static String username(UUID uuid) {return request(PROFILE_URL, false, "username", Arrays.asList(Map.entry("uuid", uuid.toString()))).getString("username");}
	public static String role(UUID uuid) {return request(PROFILE_URL, false, "role", Arrays.asList(Map.entry("uuid", uuid.toString()))).getString("role");}
	public static int coins(UUID uuid) {return request(PROFILE_URL, false, "coins", Arrays.asList(Map.entry("uuid", uuid.toString()))).getInt("coins");}
	public static boolean microsoft(UUID uuid) {return request(PROFILE_URL, false, "microsoft", Arrays.asList(Map.entry("uuid", uuid.toString()))).getBool("microsoft");}
	public static String lang(UUID uuid) {return request(PROFILE_URL, false, "lang", Arrays.asList(Map.entry("uuid", uuid.toString()))).getString("lang");}
	public static String email(UUID uuid) {return request(PROFILE_URL, false, "email", Arrays.asList(Map.entry("uuid", uuid.toString()))).getString("lang");}
	public static String skin(UUID uuid) {return request(PROFILE_URL, false, "skin", Arrays.asList(Map.entry("uuid", uuid.toString()))).getString("skin");}
	public static String birthday(UUID uuid) {return request(PROFILE_URL, false, "birthday", Arrays.asList(Map.entry("uuid", uuid.toString()))).getString("birthday");}
	public static String last(UUID uuid) {return request(PROFILE_URL, false, "last", Arrays.asList(Map.entry("uuid", uuid.toString()))).getString("last");}
	
	public static String rank(UUID uuid) {return request(RANKS_URL, false, "rank", Arrays.asList(Map.entry("uuid", uuid.toString()))).getString("rank");}
	
	public static Response request(String address, boolean secure, String type, List<Entry<String, String>> entries) {
		
		Service service = new Service(address, secure);
		
		HashMap<String, String> parameters = new HashMap<>();
		parameters.put("type", type);
		for(Entry<String, String> entry : entries) parameters.put(entry.getKey(), entry.getValue());
		service.send(parameters);
		
		return service.fetch().response();
	}
	
	public static void init() {
		ACCOUNT_URL = "";
		PROFILE_URL = "";
		RANKS_URL = "";
		//SKINS_URL = "";
	}
}