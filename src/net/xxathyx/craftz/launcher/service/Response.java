package net.xxathyx.craftz.launcher.service;

import org.json.JSONObject;

public class Response {
	
	private String response;
	
	public Response(String response) {
		this.response=response;
	}
	
	public String toString() {
		return response;
	}
	
	public JSONObject toJson() {
		return new JSONObject(response);
	}
	
	public String getString(String id) {
		return toJson().getString(id);
	}
	
	public int getInt(String id) {
		return toJson().getInt(id);
	}
	
	public boolean getBool(String id) {
		return toJson().getBoolean(id);
	}
}