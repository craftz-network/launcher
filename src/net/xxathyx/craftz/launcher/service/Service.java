package net.xxathyx.craftz.launcher.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.logging.Logger;
import net.xxathyx.craftz.launcher.util.Http;

public class Service {
	
	private static final Logger logger = Main.logger;
	
	private String address;
	private boolean secure;
	
	private HttpURLConnection connection;
	private Response response;
	
	public Service(String address, boolean secure) {
		this.address=address;
		this.secure=secure;
	}
	
	public HttpURLConnection send(HashMap<String, String> parameters) {
		
		HttpURLConnection connection = null;
		
		try {
			connection = secure ? (HttpsURLConnection) (new URL(address).openConnection()) : (HttpURLConnection) (new URL(address).openConnection());
			connection.setRequestMethod("POST");
			for(Entry<String, String> entry : parameters.entrySet()) connection.addRequestProperty(entry.getKey(), entry.getValue());
            connection.setDoOutput(true);
            try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {out.writeBytes("");out.flush();}
          
		}catch (IOException e) {
			e.printStackTrace();
			logger.log(e);
		}
		
		this.connection=connection;
		return connection;
	}
	
	public Service fetch()  {
		try {
			response = new Response(Http.readResponse(connection));
		}catch (IOException e) {
			e.printStackTrace();
			logger.log(e);
		}
		return this;
	}
	
	public Response response() {
		if(response == null) fetch();
		return response;
	}
	
	public void end() {
		connection.disconnect();
	}
	
	public String getAddress() {
		return address;
	}
	
	public HttpURLConnection getConnection() {
		return connection;
	}

	public boolean isSecure() {
		return secure;
	}
}