package net.xxathyx.craftz.launcher.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.stage.Stage;
import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.display.Display;
import net.xxathyx.craftz.launcher.profile.Profile;
import net.xxathyx.craftz.launcher.logging.Logger;

public class Settings extends Stage {

	private static final Logger logger = Main.logger;
	
	private HashMap<String, String> parameters;
	
	private Stage home;
	private Profile profile;
	
    public int width = 1080;
    public int height = 720;
	
	public void add(String key, String value) {
		if(parameters.containsKey(key)) parameters.replace(key, value);
		else parameters.put(key, value);
	}
	
	public File getOptionsFile() {
		return new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(),"options.txt");
	}
	
	public File getOptionsOfFile() {
		return new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(),"optionsof.txt");
	}
	
	public HashMap<String, Integer> map(boolean of) {
		
		HashMap<String, Integer> map = new HashMap<>();
		int count=0;
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(of ? getOptionsOfFile() : getOptionsFile()));
			String line = bufferedReader.readLine();
			
		    while(line != null) {
		    	String[] brut = line.split(":");
		    	map.put(brut[0], count);
		    	count++;
		        line = bufferedReader.readLine();
		    }
			bufferedReader.close();
		}catch (IOException e) {
			e.printStackTrace();
			logger.log(e);
		}
		return map;
	}
	
	public void graphics(Stage stage) {
		Display display = new Display("CraftZ - Graphics", stage, width, height, getClass().getResource("resources/graphics.fxml"), getClass().getResource("resources/style.css"), (int)stage.getX(), (int)stage.getY(), false, true, false, true);
		GraphicsController graphicsController = (GraphicsController) display.getController();
		graphicsController.setSettings(this);
	}
	
	public void controls(Stage stage) {
		Display display = new Display("CraftZ - Controls", stage, width, height, getClass().getResource("resources/controls.fxml"), getClass().getResource("resources/style.css"), (int)stage.getX(), (int)stage.getY(), false, true, false, true);
		ControlsController controlsController = (ControlsController) display.getController();
		controlsController.setSettings(this);
	}
	
	public void audio(Stage stage) {
		Display display = new Display("CraftZ - Audio", stage, width, height, getClass().getResource("resources/audio.fxml"), getClass().getResource("resources/style.css"), (int)stage.getX(), (int)stage.getY(), false, true, false, true);
		AudioController audioController = (AudioController) display.getController();
		audioController.setSettings(this);
	}
	
	public void launcher(Stage stage) {
		Display display = new Display("CraftZ - Launcher", stage, width, height, getClass().getResource("resources/launcher.fxml"), getClass().getResource("resources/style.css"), (int)stage.getX(), (int)stage.getY(), false, true, false, true);
		LauncherController launcherController = (LauncherController) display.getController();
		launcherController.setSettings(this);
	}
	
	public void save() {
		
		if(!parameters.isEmpty()) {
			
			HashMap<String, Integer> mc_mapping = map(false);
			HashMap<String, Integer> of_mapping = map(true);
			
		    for(Map.Entry<String, String> set : parameters.entrySet()) {
		    	
		    	if(set.getKey().equals("args-jvm")) {
		    		profile.setArgsJVM(set.getValue());
		    	}else {
			    	if(set.getKey().startsWith("of")) {
			    	    try {
			    		    List<String> lines = Files.readAllLines(getOptionsOfFile().toPath(), StandardCharsets.UTF_8);
			    		    lines.set(of_mapping.get(set.getKey()), set.getKey()+":"+set.getValue());
			    			Files.write(getOptionsOfFile().toPath(), lines, StandardCharsets.UTF_8);
			    		}catch (IOException e) {
			    			e.printStackTrace();
							logger.log(e);
			    		}
			    	}else {	    		
			    	    try {
			    		    List<String> lines = Files.readAllLines(getOptionsFile().toPath(), StandardCharsets.UTF_8);
			    		    lines.set(mc_mapping.get(set.getKey()), set.getKey()+":"+set.getValue());
			    			Files.write(getOptionsFile().toPath(), lines, StandardCharsets.UTF_8);
			    		}catch (IOException e) {
			    			e.printStackTrace();
							logger.log(e);
			    		}
			    	}
		    	}
		    }
		}
		home.show();
	}
	
	public Stage getHome() {
		return home;
	}
	
	public Profile getProfile() {
		return profile;
	}
	
	public Settings(Stage home, Profile profile, HashMap<String, String> parameters) {
		Display display = new Display("CraftZ - Graphics", this, width, height, getClass().getResource("resources/graphics.fxml"), getClass().getResource("resources/style.css"), (int)home.getX(), (int)home.getY(), true, true, false, true);
		GraphicsController graphicsController = (GraphicsController) display.getController();
		graphicsController.setSettings(this);
		this.home=home;
		this.profile=profile;
		this.parameters=parameters;
	}

	public Settings() {}
}