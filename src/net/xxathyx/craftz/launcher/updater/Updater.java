package net.xxathyx.craftz.launcher.updater;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.configuration.FileConfiguration;
import net.xxathyx.craftz.launcher.logging.Logger;

public class Updater {
	
	private static final Logger logger = Main.logger;
	
	private static String fs = File.separator;
	
	private File file;
	private FileConfiguration fileConfiguration;
	
	private String online = "https://www.dropbox.com/scl/fi/akjw7mb9i9gh0hayb3cyg/update.txt?rlkey=f0opkt31l3jz1ka4e01okvf6i&st=fvyaggtj&dl=1";
	
	public Updater() {
		
		File file = new File(net.xxathyx.craftz.launcher.util.System.getUpdaterFolder().getPath()+fs+"update.txt");
		
		try {
			file.createNewFile();
			net.xxathyx.craftz.launcher.util.System.download(file.getPath(), online).join();
		}catch (InterruptedException | IOException e) {
			e.printStackTrace();
			logger.log(e);
		}
				
		this.file=file;
		this.fileConfiguration = new FileConfiguration(file);
	}
	
	public File getFile() {
		return file;
	}
	
	public FileConfiguration getFileConfiguation() {
		return fileConfiguration;
	}
	
	public void update() {
		for(int i=0; i<fileConfiguration.getLines().size(); i=+2) {
			try {
				String old = fileConfiguration.get(i); String online = fileConfiguration.get(i+1);
				if(old.contains("%game-folder%")) {
					old=old.replaceAll("%game-folder%", "");
					old=net.xxathyx.craftz.launcher.util.System.getGameFolder().getPath()+old;
				}
				if(!isUpdated(old, online)) {
					net.xxathyx.craftz.launcher.util.System.download(old, online);
				}
			}catch (InterruptedException e) {
				e.printStackTrace();
				logger.log(e);
			}
		}
	}
	
	public boolean isUpdated(String old, String online) {
		try {
	        URL url = new URL(online);
	        URLConnection urlConnection = url.openConnection();
	        
	        File oldFile = new File(old);
	        if(!oldFile.exists()) return false;
	        return oldFile.length() == urlConnection.getContentLengthLong();
	        
		}catch (IOException e) {
			e.printStackTrace();
			logger.log(e);
		}
		return false;
	}
}