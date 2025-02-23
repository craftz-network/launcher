package net.xxathyx.craftz.launcher.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.logging.Logger;

public class FileConfiguration {
	
	private static final Logger logger = Main.logger;
	
	private File file;
	
	private Map<String, Integer> map = new HashMap<>();
	private List<String> lines = new ArrayList<>();
	
	public FileConfiguration(File file) {
		this.file=file;
		if(file.exists()) map();
	}
	
	public boolean create() {
		try {
			return file.createNewFile();
		}catch (IOException e) {
			e.printStackTrace();
		    logger.log(e);
		}
		return false;
	}
	
	public void map() {
		
		int count=0;
		lines = new ArrayList<>();
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line = bufferedReader.readLine();
			
		    while(line != null) {
		    	
		    	String[] brut = line.split(": ");
		    	
		    	map.put(brut[0], count);
		    	if(brut.length>1) lines.add(brut[1]);
		    	count++;
		        line = bufferedReader.readLine();
		    }
			bufferedReader.close();
		}catch (IOException e) {
			e.printStackTrace();
		    logger.log(e);
		}
	}
	
	public void update(String id, String data) {
	    try {
		    List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		    lines.set(map.get(id), id+": "+data);
			Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
		}catch (IOException e) {
			e.printStackTrace();
		    logger.log(e);
		}
	}
	
	public String get(int line) {
	    return lines.get(line);
	}
	
	public void add(String id, String data) {
	    try {
		    FileWriter writer = new FileWriter(file, true);
		    writer.write(id+": " + data+"\n");
			writer.close();
		}catch (IOException e) {
			e.printStackTrace();
		    logger.log(e);
		}
	}
	
	public String getObject(String id) {
		
		String object = "";
				
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line = bufferedReader.readLine();
			
		    while(line != null) {
		    	if(line.startsWith(id)) {
		    		object=line.replaceAll(id+": ", "");
		    		break;
		    	}
		        line = bufferedReader.readLine();
		    }
			bufferedReader.close();
		}catch (IOException e) {
			e.printStackTrace();
		    logger.log(e);
		}
		return object;
	}
	
	public String getString(String id) {
		return getObject(id).replaceAll("\"", "");
	}
	
	public int getInteger(String id) {
		return Integer.parseInt(getObject(id));
	}
	
	public double getDouble(String id) {
		return Double.parseDouble(getObject(id));
	}
	
	public float getFloat(String id) {
		return Float.parseFloat(getObject(id));
	}
	
	public boolean getBoolean(String id) {
		return Boolean.parseBoolean(getObject(id));
	}
	
	public File getFile() {
		return file;
	}
	
	public Map<String, Integer> getMap() {
		return map;
	}
	
	public List<String> getLines() {
		return lines;
	}
}