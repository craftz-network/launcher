package net.xxathyx.craftz.launcher.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;

public class System {
		
	private static final String operatingSystem = java.lang.System.getProperty("os.name").toLowerCase();
	
    public enum OS {
        WINDOWS, LINUX, MAC, SOLARIS, OTHER;
    };
    
    public static OS getOS() {
    	        
        if(operatingSystem.contains("win")) { return OS.WINDOWS;
        }else if (operatingSystem.contains("nix") || operatingSystem.contains("nux") || operatingSystem.contains("aix")) {return OS.LINUX;
        }else if (operatingSystem.contains("mac")) {return OS.MAC;
        }else if (operatingSystem.contains("sunos")) {return OS.SOLARIS;}
        return OS.OTHER;
    }
	
	private static String fs = File.separator;
	
	public static int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	public static void web(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		}catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static int pasteDays(String date) {
		
		LocalDateTime now = LocalDateTime.now();
		
		String[] components = date.split("-");
		String[] elements = components[0].split("/");
		String month = elements[0]; String day = elements[1]; String year = elements[2];
		
        return (int) ((now.getYear()-Integer.parseInt(year))*365 + Math.abs(now.getMonthValue()-Integer.parseInt(month))*30.4167 + Math.abs(now.getDayOfMonth()-Integer.parseInt(day)));
	}
	
	public static void deleteDirectory(String directoryFilePath) {
		
	    Path directory = Paths.get(directoryFilePath);

	    if (Files.exists(directory)){
	        try {
				Files.walkFileTree(directory, new SimpleFileVisitor<Path>(){
					
				    @Override
				    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
				        Files.delete(path);
				        return FileVisitResult.CONTINUE;
				    }

				    @Override
				    public FileVisitResult postVisitDirectory(Path directory, IOException ioException) throws IOException {
				        Files.delete(directory);
				        return FileVisitResult.CONTINUE;
				    }
				});
			}catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	
	public static int getPid() {
		return Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
	}
	
	public static File getPidFile() {
		return new File(getUpdaterFolder(), "pid.txt");
	}
	
	public static File getGameFolder() {
		return new File((getOS().equals(OS.WINDOWS) ? java.lang.System.getenv("APPDATA") : java.lang.System.getProperty("user.home")) + fs + ".craftz" + fs);
	}
	
	public static File getProfileFolder() {
		return new File(net.xxathyx.craftz.launcher.util.System.getGameFolder()+fs+"profiles"+fs);
	}
	
	public static File getUpdaterFolder() {
		return new File(getGameFolder() + fs + "updater" + fs);
	}
	
	public static File getNewsFolder() {
		return new File(getUpdaterFolder() + fs + "news" + fs);
	}
	
	public static File getLogsFolder() {
		return new File(getGameFolder() + fs + "updater" + fs + "logs" + fs);
	}
	
	public File getBootstrapFile() {
		return new File(getUpdaterFolder() + fs + "bootstrap.jar");
	}
	
	public File getLibrariesFolder() {
		return new File(getGameFolder() + fs + "updater" + fs + "lib" + fs);
	}
	
	public static String getCountryCode() {
		
		String countryCode = "EN";
		
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress("google.com", 443));
			countryCode = new Host(socket.getLocalAddress().toString()).getCountryCode();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return countryCode;
	}
	
	public static Thread download(String to, String from) throws InterruptedException {
	    
	    File file = new File(to);
	    
	    Thread thread = new Thread(() -> {
	    	
	        try {
			    URL url = new URL(from);  
			    URLConnection connection = url.openConnection();  
			    InputStream inputStream = connection.getInputStream();  
			    FileOutputStream outputStream = new FileOutputStream(file);
			    
			    final byte[] bt = new byte[1024];
			    int len;
			    while((len = inputStream.read(bt)) != -1) {
			    	outputStream.write(bt, 0, len);
			    }
			    outputStream.close();
			}catch (IOException e) {
				e.printStackTrace();  
			}
	    });
	    thread.start();
	    return thread;
	}
	
	public static String time() {
		LocalDateTime date  = LocalDateTime.now();
		return "["+date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear()+" "+date.getHour()+":"+date.getMinute()+":"+date.getSecond()+"]";
	}
}