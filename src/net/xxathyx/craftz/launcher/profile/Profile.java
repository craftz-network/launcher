package net.xxathyx.craftz.launcher.profile;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.configuration.FileConfiguration;
import net.xxathyx.craftz.launcher.login.Authenticator;
import net.xxathyx.craftz.launcher.util.Host;
import net.xxathyx.craftz.launcher.logging.Logger;

public class Profile {
	
	private static final Logger logger = Main.logger;
	
	private FileConfiguration fileConfiguration;
	
	private UUID uuid;
	private File file;
	
	private int coins;
	private String role;
	private String rank;
	
	public Profile(UUID uuid) {
		this.uuid=uuid;
		File profileFolder = net.xxathyx.craftz.launcher.util.System.getProfileFolder();
		profileFolder.mkdir();
		file = new File(profileFolder, uuid+".profile");
		fileConfiguration = new FileConfiguration(file);
	}
	
	public Profile(File file) {	
		this.uuid=UUID.fromString(file.getName().replaceFirst("[.][^.]+$", ""));
		fileConfiguration = new FileConfiguration(file);
		
		//TODO: fetch coin/ rank /role
	}
	
	public void create(boolean microsoft, String access_token, String username, String avatar_skin) {
		try {
			fileConfiguration.create();
			fileConfiguration.add("uuid", uuid.toString());
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress("craftz.net", 443));
			socket.close();
			fileConfiguration.add("lang", new Host(socket.getLocalAddress().toString()).getCountryCode());
			fileConfiguration.add("args-jvm", "-Xmx4G");
			fileConfiguration.add("microsoft", String.valueOf(microsoft));
			fileConfiguration.add("access-token", access_token);
			fileConfiguration.add("username", username);
			fileConfiguration.add("email", "");
			fileConfiguration.add("avatar-skin", avatar_skin);
			fileConfiguration.add("birthday", "");
			fileConfiguration.add("telemetry", "true");
			fileConfiguration.add("selected", "true");
			LocalDateTime date  = LocalDateTime.now();
			fileConfiguration.add("last-time", date.getMonthValue()+"/"+date.getDayOfMonth()+"/"+date.getYear()+"-"+date.getHour()+":"+date.getMinute()+":"+date.getSecond());
			
			fileConfiguration.map();
						
		}catch (IOException e) {
			e.printStackTrace();
			logger.log(e);
		}
	}
	
	public boolean exists() {
		return file.exists();
	}
	
	public FileConfiguration getFileConfiguration() {
		return fileConfiguration;
	}
	
	public void update(String lang, String argsJVM, String access_token, String email, String avatar_skin, String birthday, boolean selected) {
		setLang(lang);
		setArgsJVM(lang);
		update_access_token(access_token);
		setEmail(email);
		setBirthday(birthday);
		setSelected(selected);
	}
	
	public void setLang(String countryCode) {
		fileConfiguration.update("lang", countryCode);
	}
	
	public void setArgsJVM(String argsJVM) {
		fileConfiguration.update("args-jvm", argsJVM);
	}
	
	public void update_access_token(String access_token) {
		fileConfiguration.update("access-token", access_token);
	}
	
	public void setEmail(String email) {
		fileConfiguration.update("email", email);
	}
	
	public void setAvatarSkin(String avatar_skin) {
		fileConfiguration.update("avatar-skin", avatar_skin);
	}
	
	public void setBirthday(String birthday) {
		fileConfiguration.update("birthday", birthday);
	}
	
	public void setSelected(boolean selected) {
		fileConfiguration.update("selected", String.valueOf(selected));
	}
	
	public void setTelemetry(boolean telemetry) {
		fileConfiguration.update("telemetry", String.valueOf(telemetry));
	}
	
	public void updateCoins(int coins) {
		this.coins=coins;
	}
	
	public UUID getUUID() {
		if(uuid != null) return uuid;
		return  UUID.fromString(fileConfiguration.getString("uuid"));
	}
	
	public boolean isMicrosoft() {
		return fileConfiguration.getBoolean("microsoft");
	}
	
	public String getLang() {
		return fileConfiguration.getString("lang");
	}
	
	public String getArgsJVM() {
		return fileConfiguration.getString("args-jvm");
	}
	
	public String access_token() {
		return fileConfiguration.getString("access-token");
	}
	
	public String getUsername() {
		return fileConfiguration.getString("username");
	}
	
	public String getAvatarSkin() {
		return fileConfiguration.getString("avatar-skin");
	}
	
	public String getEmail() {
		return fileConfiguration.getString("email");
	}
	
	public String getBirthday() {
		return fileConfiguration.getString("birthday");
	}
	
	public boolean isSelected() {
		return fileConfiguration.getBoolean("selected");
	}
	
	public boolean getTelemetry() {
		return fileConfiguration.getBoolean("telemetry");
	}
	
	public String getLastime() {
		return fileConfiguration.getString("last-time");
	}
	
	public String getRole() {
		return role;
	}
	
	public String getRank() {
		return rank;
	}
	
	public int getPlayedTime() {
		return fileConfiguration.getInteger("played-time");
	}
	
	public int getCoins() {
		return coins;
	}
	
	public static Profile getSelectedProfile() {
		
		List<Profile> valid = new ArrayList<>();
		Profile selected = null;
		
		for(File file : net.xxathyx.craftz.launcher.util.System.getProfileFolder().listFiles()) {
			Profile profile = new Profile(file);
			if(profile.isMicrosoft()) {
				
				Authenticator authenticator = new Authenticator(true, profile.getUsername(), profile.access_token());
				
				if(authenticator.isValid()) {
					valid.add(profile);
					if(profile.isSelected()) selected = profile;
				}
			}else {
				
				Authenticator authenticator = new Authenticator(false, profile.getUsername(), profile.access_token());
				
				if(authenticator.isValid()) {
					valid.add(profile);
					profile.updateCoins(authenticator.coins());
					if(profile.isSelected()) selected = profile;
				}
			}
		}
		if(selected == null && !valid.isEmpty()) return valid.get(0);
		return selected;
	}
}