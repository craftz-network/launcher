package net.xxathyx.craftz.launcher.logging;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class Logger {
	
	private File file;
	
	public Logger(File file) {
		this.file = file;
	}
	
	public void log(Exception exception) {
		
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(baos);
	    exception.printStackTrace(ps);
	    ps.close();
	    
	    try {
		    FileWriter writer = new FileWriter(file, true);
			writer.write(net.xxathyx.craftz.launcher.util.System.time()+": "+baos.toString()+"\n");
			writer.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void log(String message) {
		
		try {
		    FileWriter writer = new FileWriter(file, true);
			writer.write(net.xxathyx.craftz.launcher.util.System.time()+": "+message+"\n");
			writer.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File getFile() {
		return file;
	}
}