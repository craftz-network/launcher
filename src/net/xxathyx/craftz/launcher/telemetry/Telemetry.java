package net.xxathyx.craftz.launcher.telemetry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.logging.Logger;
import net.xxathyx.craftz.launcher.profile.Profile;

public class Telemetry {
	
	private static String TELEMETRY_SERVER = "telemetry.craftz.net";
	private static int TELEMETRY_SERVER_PORT = 50674;
	
	private static int BUFFER_SIZE = 1024;
	
	private static final Logger logger = Main.logger;
	
	private Profile profile;
	
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
	
    public Telemetry(Profile profile) {
    	this.profile = profile;
    }
    
	public void transmit() {
		
		if(profile.getTelemetry()) {
			try (Socket socket = new Socket(TELEMETRY_SERVER, TELEMETRY_SERVER_PORT)) {
				
				dataInputStream = new DataInputStream(socket.getInputStream());
				dataOutputStream = new DataOutputStream(socket.getOutputStream());
			
				logger.log("Transmitting logger file to the telemetry server");
			
				sendFile(logger.getFile());

				dataInputStream.close();
				dataInputStream.close();
				
			}catch (Exception e) {
				e.printStackTrace();
				logger.log(e);
			}
		}
	}
	
	public void sendFile(File file) throws Exception {
		
		int bytes = 0;
		FileInputStream fileInputStream	= new FileInputStream(file);
		dataOutputStream.writeLong(file.length());
		
		byte[] buffer = new byte[4 * BUFFER_SIZE];
		
		while((bytes = fileInputStream.read(buffer)) != -1) {
			dataOutputStream.write(buffer, 0, bytes);
			dataOutputStream.flush();
		}
		fileInputStream.close();
	}
}
