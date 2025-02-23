package net.xxathyx.craftz.launcher.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.nio.file.CopyOption;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.configuration.FileConfiguration;
import net.xxathyx.craftz.launcher.logging.Logger;
import net.xxathyx.craftz.launcher.profile.Profile;

public class Downloader {
	
	private static final Logger logger = Main.logger;
	
	private String gamefolder;
	
	private String version;
	private String forge_version;
	
	private boolean debug;
	private boolean verify;
	private boolean overwrite;
	private boolean forge;
	
	private File versionFolder;
	private File versionJar;
	private File versionJson;
	
	private File assetsFolder;
	
	private HashMap<String, String> downloads = new HashMap<>();
	
	private HashMap<String, String> assets;
	private HashMap<String, String> libraries;
	private HashMap<String, Long> sizes;
	
	private HashMap<String, String> indexes = new HashMap<>();
	
	public ArrayList<String> gamelibs = new ArrayList<>();
	private ArrayList<String> natives = new ArrayList<>();;
	
	private Profile profile;
	private int pid;
	
	private static String VERSION_MANIFEST_V2 = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";
	private static String MINECRAFT_RESOURCES = "https://resources.download.minecraft.net/%begining%/%hash%";
	
	private static String ADDONS_URL = "https://craftz.net/download/game/";
	private static String FORGE_INSTALLER = "https://maven.minecraftforge.net/net/minecraftforge/forge/%forge-version%/forge-%forge-version%-installer.jar";
	
	private static String WINDOWS_JRE = "https://builds.openlogic.com/downloadJDK/openlogic-openjdk-jre/17.0.12+7/openlogic-openjdk-jre-17.0.12+7-windows-x64.zip";
	private static String LINUX_JRE = "https://builds.openlogic.com/downloadJDK/openlogic-openjdk-jre/17.0.12+7/openlogic-openjdk-jre-17.0.12+7-linux-x64.tar.gz";
	private static String MAC_JRE = "https://builds.openlogic.com/downloadJDK/openlogic-openjdk-jre/17.0.12+7/openlogic-openjdk-jre-17.0.12+7-mac-x64.zip";
	
	private static String MAIN_CLASS = "";
	
	private static String fs = File.separator;
	
	private static net.xxathyx.craftz.launcher.util.System.OS os = net.xxathyx.craftz.launcher.util.System.getOS();
	
	private String[] folders = {"assets","assets"+fs+"indexes","assets"+fs+"log_configs","assets"+fs+"objects","libraries","mods","resourcepacks","saves","versions"};
	
	private final DecimalFormat decimalFormat = new DecimalFormat("#.##");
	
	private ProgressBar progressBar;
	private Text length;
	private Text text;
	
	private String word = "Downloading";
	
	private boolean downloading = false;
	private boolean verifying = false;
	
	private long downloaded = 0;
	private long left = 0;
	private int step = 1;
	private int total = 9;
		
	public Downloader(String gamefolder, String version, boolean debug, boolean verify, boolean overwrite, boolean forge, String forge_version, Profile profile, int pid) {
		this.gamefolder=gamefolder;
		this.version=version;
		this.debug=debug;
		this.verify=verify;
		this.overwrite=overwrite;
		this.forge=forge;
		this.forge_version=forge_version;
		this.profile=profile;
		this.pid=pid;
	}
	
	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar=progressBar;
	}
	
	public void setTextField(Text text) {
		this.text=text;
	}
	
	public void setLengthText(Text length) {
		this.length=length;
	}
	
	public void info(String text) {
		
		Platform.runLater(() -> {
			if(!verifying) {
				logger.log(text);
				if(this.text != null) this.text.setText(text);
				length.setText((downloading ? decimalFormat.format((downloaded*Math.pow(10, -6))).replace(",", ".") + "/" + decimalFormat.format((left*Math.pow(10, -6))).replace(",", ".") + " Mo" : "") + " ("+step+"/"+total+")");
				length.setTranslateX(1080-21-(length.getLayoutX()+length.getLayoutBounds().getWidth()));
				if(progressBar != null) if(left>0 && downloading) this.progressBar.setProgress((float)downloaded/(float)left);
			}
		});
		
	}
	
	public void init() {
		
		info("Creating default directories");
		
		for(String folder : folders) {
			info("Creating " + folder+fs);
			new File(gamefolder+fs+folder+fs).mkdirs();
		}
		
		File versionFolder = new File(gamefolder+fs+"versions"+fs+version+fs);
		File versionJson = new File(versionFolder, version+".json");
		File assetsFolder = new File(gamefolder+fs+"assets"+fs);
		
		versionFolder.mkdirs();
		assetsFolder.mkdirs();
		
		this.versionFolder=versionFolder;
		this.versionJson=versionJson;
		this.assetsFolder=assetsFolder;
		
		info("Looking for specific version manifest");
		
		try {
			download(versionJson.toString(), version_manifest(version));
		} catch (JSONException e) {
			e.printStackTrace();
		    logger.log(e);
		}
		
		downloads = new HashMap<>();
		
		assets = new HashMap<>();
		libraries = new HashMap<>();
		sizes = new HashMap<>();
	}
	
	public void install_java() {
		
		info("Downloading a newer version of Java");
		File runtimeFolder = new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "runtime"+fs);
		runtimeFolder.mkdir();
		
		File archive = new File(net.xxathyx.craftz.launcher.util.System.getUpdaterFolder(), "jre."+(os.equals(net.xxathyx.craftz.launcher.util.System.OS.MAC) ? "tar.gz" : "zip"));
		
        try {
		    URL url = new URL(os.equals(net.xxathyx.craftz.launcher.util.System.OS.WINDOWS) ? WINDOWS_JRE : (os.equals(net.xxathyx.craftz.launcher.util.System.OS.LINUX) ? LINUX_JRE : MAC_JRE));  
		    URLConnection connection = url.openConnection();  
		    
		    if(archive.length()!=connection.getContentLengthLong() && verify || overwrite) {
			    InputStream inputStream = connection.getInputStream();  
			    FileOutputStream outputStream = new FileOutputStream(archive);
			    
			    final byte[] bt = new byte[1024];
			    int len;
			    while((len = inputStream.read(bt)) != -1) {
					info("Downloading a newer version of Java");
					downloaded+=1024;
			    	outputStream.write(bt, 0, len);
			    }
			    outputStream.close();
		    } 
		}catch (IOException e) {
			e.printStackTrace();
		    logger.log(e);
		}
        
		info("Extracting binaries");
        downloaded+=archive.length();

        try(ZipInputStream zis = new ZipInputStream(new FileInputStream(archive))) {
            ZipEntry entry;
            byte[] buffer = new byte[1024];
            Set<String> createdDirs = new HashSet<>();

            while((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                File newFile = new File(runtimeFolder, "java" + fs + name.substring(name.indexOf("/") + 1));

                if(entry.isDirectory()) {
                    if(!createdDirs.contains(newFile.getAbsolutePath())) {
                        newFile.mkdirs();
                        createdDirs.add(newFile.getAbsolutePath());
                    }
                }else if (overwrite || (verify && newFile.length() != entry.getSize())) {
                    new File(newFile.getParent()).mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
	        logger.log(e);
        }
	}
	
	public Thread download() {
				
		Thread thread = new Thread(() -> {
			try {			
								
				init();
				client();
				
				File tmp = new File(net.xxathyx.craftz.launcher.util.System.getUpdaterFolder(), "tmp.txt");
				File versionId = new File(net.xxathyx.craftz.launcher.util.System.getUpdaterFolder(), "version.txt");
				
				download(tmp.getPath(), ADDONS_URL+"version.txt");
				FileConfiguration config = new FileConfiguration(versionId);
				
				if(!versionId.exists() || !(config.getObject("version-id").equals(new FileConfiguration(tmp).getObject("version-id")))) {
					
					versionId.delete();
					versionId.createNewFile();
					
					new FileConfiguration(versionId).add("version-id", new FileConfiguration(tmp).getObject("version-id"));
										
					tmp.delete();
					
					File tempGameFolder = new File(net.xxathyx.craftz.launcher.util.System.getUpdaterFolder(), "/game/");
					tempGameFolder.mkdirs();
					
					File root = new File(tempGameFolder, "/root.html");
					
					download(tempGameFolder.getPath()+"/root.html", ADDONS_URL);
					fetchEntries(root, downloads);
				}
				
				downloads.putAll(assets());
				downloads.putAll(libraries());
				
				if(forge) left+=new URL(FORGE_INSTALLER.replaceAll("%forge-version%", forge_version)).openConnection().getContentLengthLong();
				left+=new URL(os.equals(net.xxathyx.craftz.launcher.util.System.OS.WINDOWS) ? WINDOWS_JRE :
					(os.equals(net.xxathyx.craftz.launcher.util.System.OS.LINUX) ? LINUX_JRE : MAC_JRE)).openConnection().getContentLengthLong();
				downloading = true;
				
				step++; info("Downloading and updating assets and libraries");
				
				for(Entry<String, String> entry : downloads.entrySet()) {
										
					File file = new File(entry.getKey());
					
					if(file.length()!=sizes.get(entry.getKey()) && verify || overwrite) {
						
						info(word + " " + (file.getParentFile().getParentFile().getParentFile().getName().equals("assets") ? indexes.get(file.getName()): entry.getKey().replace(gamefolder+fs, "")));
						
						new File(entry.getKey()).getParentFile().mkdirs();
						download(entry.getKey(), entry.getValue());
						downloaded+=file.length();
					}else downloaded+=file.length();
				}
				
				if(forge)
					try {
						step++; info("Installing Forge client");
						forge();
					}catch (SecurityException e) {
						e.printStackTrace();
					    logger.log(e);
					}
				
				step++; info("Extracting natives");
				extract_natives();
				
				step++;
				step++; install_java();
				clean();
				
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(Main.class.getResource("resources/ping.wav"));
			    Clip clip = AudioSystem.getClip();
			    clip.open(audioIn);
			    clip.start();
			    
			    if(verify) {
					step++; info("Verifying game files");
					verifying = true;
					word = "Updating";
					verify();
					verifying = false;
			    }
				
				step++; info("Launching the game, please wait");
				Platform.runLater(() -> {length.setVisible(false); progressBar.setProgress(1.0);});
				launch();
				
			}catch (JSONException | IOException | UnsupportedAudioFileException | LineUnavailableException e) {
				e.printStackTrace();
			    logger.log(e);
			}
		});
		thread.start();
		
		return thread;
	}
	
	public void verify() {
		
		length.setVisible(false);
		
		try {
						
			init();
			client();
			
			downloads.putAll(assets());
			downloads.putAll(libraries());
			
			info("Verifying game files");
			
			for(Entry<String, String> entry : downloads.entrySet()) {
									
				File file = new File(entry.getKey());
				
				if(file.length()!=sizes.get(entry.getKey()) && verify || overwrite) {
					
					info(word + " " + (file.getParentFile().getParentFile().getParentFile().getName().equals("assets") ? indexes.get(file.getName()): entry.getKey().replace(gamefolder+fs, "")));
					
					new File(entry.getKey()).getParentFile().mkdirs();
					download(entry.getKey(), entry.getValue());
				}
			}
			
			if(forge)
				try {
					info("Verifying game files");
					forge();
				}catch (SecurityException e) {
					e.printStackTrace();
				    logger.log(e);
				}
			
			info("Verifying game files");
			extract_natives();
			
			install_java();
			
			clean();
			
		}catch (JSONException e) {
			e.printStackTrace();
		    logger.log(e);
		}
	}
	
	public void extract_natives() {
		
		File nativesFolder = new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "natives"+fs);
		if(!nativesFolder.exists()) nativesFolder.mkdir();
		
		for(String string : natives) {
			
			try {
				File jar = new File(string);
				URL url = jar.toURL();
				
				ZipFile zipFile = new ZipFile(jar);
				ZipInputStream zip = new ZipInputStream(url.openStream());
				
				while(true) {
					
				    ZipEntry entry = zip.getNextEntry();
				    if (entry == null) break;
				    			    
				    File destination = new File(nativesFolder, entry.getName());
				    
				    if(!entry.isDirectory() && (entry.getSize()!=destination.length() && verify || overwrite) && !destination.toString().contains("META-INF")) {
				    	InputStream inputStream = zipFile.getInputStream(entry);
				    	Files.copy(inputStream, new File(nativesFolder, entry.getName()).toPath(), new CopyOption[] {StandardCopyOption.REPLACE_EXISTING});
				    }
				}
				zipFile.close();
				
			}catch (IOException e) {
				e.printStackTrace();
			    logger.log(e);
			}
		}
	}
	
	public void fetchEntries(File source, HashMap<String, String> downloads) {
				
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(source));
			String line = bufferedReader.readLine();
			
		    while(line != null) {
		    	if(line.startsWith("<img src=")) {
		    		
		    		String cut = line.substring(line.indexOf("href="));
		    		String url = cut.substring(6, cut.lastIndexOf("\""));
		    		
		    		String downloadURL = "https://craftz.net/download"+source.getParent().replace(net.xxathyx.craftz.launcher.util.System.getUpdaterFolder().getPath(), "").replaceAll("\\\\", "/")+"/"+url;
					String path = downloadURL.replace(ADDONS_URL, "");
							    		
		    		if(url.charAt(url.length()-1) == '/') {
		    			
		    			File rootFolder = new File(source.getParentFile()+"/"+url);
		    			rootFolder.mkdirs();
		    			
						new File(new String((gamefolder+"/"+path+"/").getBytes(), "UTF-8").replaceAll("%20", " ")).getParentFile().mkdirs();
		    			
		    			download(rootFolder.getPath()+"/"+rootFolder.getName()+".html", downloadURL);
		    			
		    			fetchEntries(new File(rootFolder.getPath()+"/"+rootFolder.getName()+".html"), downloads);
		    		}else {
						File to = new File(new String((gamefolder+"/"+path).getBytes(), "UTF-8").replaceAll("%20", " "));
						if(!to.getParentFile().exists()) to.getParentFile().mkdirs();
						info("Checking " + new String(path.getBytes(), "UTF-8").replaceAll("%20", " "));
						long size = new URL(downloadURL).openConnection().getContentLengthLong();
						left+=size;
						sizes.put(to.getPath(), size);
			    		downloads.put(to.getPath(), downloadURL);	    			
		    		}
		    	}
		        line = bufferedReader.readLine();
		    }
			bufferedReader.close();
		}catch (IOException e) {
			e.printStackTrace();
		    logger.log(e);
		}
	}
	
	public boolean forge() {
		
		try {
		    String[] components = forge_version.split("-");
		    String versionName = components[0]+"-forge-"+components[1];
			
		    File versionFolder = new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "versions"+fs+versionName+fs);
		    
			if(!new File(versionFolder, versionName+".jar").exists()) {
				
				info("Forge not installed, downloading Forge installer");
				
				File forgeInstaller = new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "forge-installer.jar");
				forgeInstaller.deleteOnExit();
				
				download(forgeInstaller.toString(), FORGE_INSTALLER.replaceAll("%forge-version%", forge_version));
		        downloaded+=forgeInstaller.length();
				
			    File librariesDir = new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "libraries");
			    librariesDir.mkdir();
			    
				info("Extracting Forge manifest");
			    
			    File forgeJson = new File(versionFolder, versionName+".json");
			    versionFolder.mkdir();
			    
			    File installProfile = new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "install.json");
			    installProfile.deleteOnExit();
			    
			    URI uri = new URI("jar", forgeInstaller.toURI().toString(),  null);
			    
			    Map<String, String> env = new HashMap<>();
			    env.put("create", "true");

			    FileSystem fileSystem = FileSystems.newFileSystem(uri, env);
			    	    
				Files.copy(fileSystem.getPath("version.json"), forgeJson.toPath(), new CopyOption[] {StandardCopyOption.REPLACE_EXISTING});
				Files.copy(fileSystem.getPath("install_profile.json"), installProfile.toPath(), new CopyOption[] {StandardCopyOption.REPLACE_EXISTING});
				
				JSONObject json = new JSONObject(new String(Files.readAllBytes(forgeJson.toPath())));
				JSONArray array = json.getJSONArray("libraries");
							
				array.putAll(new JSONObject(new String(Files.readAllBytes(installProfile.toPath()))).getJSONArray("libraries"));
				
				info("Downloading Forge libraries");
				
				int count[] = {0};
				array.forEach(object -> {
					
					JSONObject library = (JSONObject) object;
					
					JSONObject artifact = library.getJSONObject("downloads").getJSONObject("artifact");
					String path = gamefolder+fs+"libraries"+fs+artifact.getString("path");
					
					libraries.put(path, artifact.getString("url"));
					sizes.put(path, artifact.getLong("size"));
													
					if(new File(path).length()!=artifact.getLong("size") && verify || overwrite) {
						info("Downloading " + path.replace(gamefolder+fs, ""));
						new File(path).getParentFile().mkdirs();
						download(path, artifact.getString("url"));
				        downloaded+=artifact.getLong("size");
						count[0]++;
					}
				});
			    
				info("Building processors, please wait");
							
				File clientTarget = new File(this.versionFolder, version+".jar");
				
		        try (URLClassLoader ucl = URLClassLoader.newInstance(new URL[] {
		        		Downloader.class.getProtectionDomain().getCodeSource().getLocation(), forgeInstaller.toURI().toURL()
		        }, getParentClassLoader())) {
		        	
		            Class<?> postProcessors = ucl.loadClass("net.minecraftforge.installer.actions.PostProcessors");
		            Class<?> progressCallback = ucl.loadClass("net.minecraftforge.installer.actions.ProgressCallback");
		                        
		            Class<?> util = ucl.loadClass("net.minecraftforge.installer.json.Util");
		            Constructor<?> constructor = postProcessors.getConstructors()[0];
		                        
		            Object instance = constructor.newInstance(new Object[] {
		            		util.getMethod("loadInstallProfile").invoke(null),
		            		true,
		            		progressCallback.getMethod("withOutputs", OutputStream[].class).invoke(null, (Object) new OutputStream[] {System.out})
		            });
		            Object result = postProcessors.getMethod("process", File.class, File.class, File.class, File.class).invoke(instance, librariesDir, clientTarget, net.xxathyx.craftz.launcher.util.System.getGameFolder(), forgeInstaller);
		            boolean success = result != null;
		            
					info(success ? "Forge has been successfully installed" : "Forge installation failed");
					
		            forgeInstaller.delete();
		            installProfile.delete();  
		            
		            if(success) Files.copy(clientTarget.toPath(), new File(versionFolder, versionName+".jar").toPath());
		            
		            return success;
		        }catch (JSONException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
					e.printStackTrace();
				    logger.log(e);
				}
			}
			return true;
		}catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		    logger.log(e);
		}
		return false;
	}
	
	public void clean() {
		File forgeInstaller = new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "forge-installer.jar");
	    File installProfile = new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "install.json");
        File manifest = new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "version_manifest_v2.json");
		if(forgeInstaller.exists()) forgeInstaller.delete();
		if(installProfile.exists()) installProfile.delete();
		if(manifest.exists()) manifest.delete();
	}
	
	public HashMap <String, ArrayList <String>> args(String section, String modifiers) {

	    HashMap <String, ArrayList <String>> map = new HashMap < > ();

	    try {
	        JSONObject json = new JSONObject(new String(Files.readAllBytes(versionJson.toPath()))).getJSONObject("arguments");
	        JSONArray array = json.getJSONArray(section);

	        if(forge) {

	            String[] components = forge_version.split("-");
	            String versionName = components[0] + "-forge-" + components[1];

	            File versionFolder = new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "versions" + fs + versionName + fs);
	            File forgeJson = new File(versionFolder, versionName + ".json");

	            array.putAll(new JSONObject(new String(Files.readAllBytes(forgeJson.toPath()))).getJSONObject("arguments").getJSONArray(section));
	        }
	        
	        for(int i = 0; i < array.length(); i++) {

	            if (array.get(i) instanceof String) {

	                String field = (String) array.get(i);
	                if (field.startsWith("--")) {
	                    if (map.containsKey(field)) map.get(field).add((String) array.get(i + 1));
	                    else map.put(field, new ArrayList <String> (Arrays.asList(new String[] {
	                        (String) array.get(i + 1)
	                    })));
	                    i++;
	                }else if (field.startsWith("-")) {
	                    if (map.containsKey(field)) map.get(field).add(array.get(i + 1) instanceof String ? ((String) array.get(i + 1)).startsWith("$") ? (String) array.get(i + 1) : "" : "");
	                    else map.put(field, array.get(i + 1) instanceof String ?
	                        ((String) array.get(i + 1)).startsWith("$") ?
	                        new ArrayList <String> (Arrays.asList(new String[] {
	                            (String) array.get(i + 1)
	                        })) : new ArrayList <String> (Arrays.asList(new String[] {})) :
	                        new ArrayList <String> (Arrays.asList(new String[] {})));
	                }
	            }else {

	                JSONArray rules = ((JSONObject) array.get(i)).getJSONArray("rules");

	                for(int j = 0; j<rules.length(); j++) {

	                    JSONObject rule = (JSONObject) rules.get(j);

	                    ArrayList <String> values = new ArrayList <String>();
	                    String value = "";

	                    if(((JSONObject) array.get(i)).get("value") instanceof JSONArray) {
	                        JSONArray params = ((JSONObject) array.get(i)).getJSONArray("value");
	                        for (Object object: params) value = value + (value.isEmpty() ? "" : " ") + ((String) object);
	                    }else value = (((JSONObject) array.get(i)).getString("value"));

	                    String[] fields = JSONObject.getNames(rule.getJSONObject(modifiers));
	                    String compute = "";

	                    for(String field: fields) compute = compute + (rule.getJSONObject(modifiers).get(field) instanceof String ? rule.getJSONObject(modifiers).getString(field) : field);

	                    values.add(value);
	                    map.put(compute, values);
	                }
	            }
	        }
	    }catch (IOException e) {
	        e.printStackTrace();
		    logger.log(e);
	    }
	    return map;
	}
	
	public Process process(HashMap<String, ArrayList<String>> jvm, HashMap<String, ArrayList<String>> game) {
		
		File runtimeFolder = new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "runtime"+fs);
		
		String mac = os.equals(net.xxathyx.craftz.launcher.util.System.OS.MAC) ? (runtimeFolder.listFiles().length>0 ? runtimeFolder.listFiles()[0].getName()+fs+"Contents"+fs+"Home"+fs : "") : "";
		String path = "bin"+fs+"javaw"+(os.equals(net.xxathyx.craftz.launcher.util.System.OS.WINDOWS) ? ".exe" : "");
		
		String java = new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "runtime"+fs+"java"+fs+(os.equals(net.xxathyx.craftz.launcher.util.System.OS.MAC) ? mac+path : path)).toString();
		String[] base = new String[]{java, profile.getArgsJVM()};
		
		try {
			if(forge) {
			    String[] components = forge_version.split("-");
			    String versionName = components[0]+"-forge-"+components[1];
			    File forgeJson = new File(new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "versions"+fs+versionName+fs), versionName+".json");
			    MAIN_CLASS = new JSONObject(new String(Files.readAllBytes(forgeJson.toPath()))).getString("mainClass");
			}
			
			ArrayList<String> terminal = new ArrayList<>();
			terminal.addAll(Arrays.asList(base));
					
			ArrayList<String> prioritized_jvm_args = prioritized(build(jvm), "jvm");
			ArrayList<String> prioritized_game_args = prioritized(build(game), "game");
			
			int index = prioritized_jvm_args.indexOf("-Dos.name=Windows 10 -Dos.version=10.0");
			if(index!=-1) prioritized_jvm_args.set(index, "-Dos.name=\"Windows 10\" -Dos.version=10.0");
			
			for(int i=0; i<prioritized_jvm_args.size(); i++) if(prioritized_jvm_args.get(i)!=null) terminal.add(prioritized_jvm_args.get(i));
			terminal.add(MAIN_CLASS);
			for(int i=0; i<prioritized_game_args.size(); i++) if(prioritized_game_args.get(i)!=null) terminal.add(prioritized_game_args.get(i));
			
			String[] command = {""};
			terminal.forEach(object -> {
				command[0] = command[0]+(String)object+" ";
			});
			
			logger.log("Running command: " + command[0].replace(profile.access_token(), "secret").replace(System.getProperty("user.name"), "user"));
			
			File runner = new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "debug." + (os.equals(net.xxathyx.craftz.launcher.util.System.OS.WINDOWS) ? "bat" : "sh"));
			if(runner.exists()) runner.delete();
			runner.createNewFile();
			
		    FileWriter writer = new FileWriter(runner, true);
		    writer.write(command[0]);
			writer.close();
					
			String call = os.equals(net.xxathyx.craftz.launcher.util.System.OS.WINDOWS) ? "cmd /c start " : "";
			Process process = Runtime.getRuntime().exec(debug ? call+runner.toString() : command[0], null, net.xxathyx.craftz.launcher.util.System.getGameFolder());
			
			return process;
		}catch(IOException e) {
			e.printStackTrace();
		    logger.log(e);
		}
		return null;
	}
	
	public String fill(String string, String versionName, String jars) {
		return string
				.replace("${auth_player_name}", profile.getUsername())
				.replace("${version_name}", versionName)
				.replace("${game_directory}", gamefolder)
				.replace("${assets_root}", gamefolder+fs+"assets")
				.replace("${assets_index_name}", new File(gamefolder+fs+"assets"+fs+"indexes").listFiles()[0].getName().replaceFirst("[.][^.]+$", ""))
				.replace("${auth_uuid}", profile.isMicrosoft() ? profile.getUUID().toString() : UUID.randomUUID().toString().replace("-", ""))
				.replace("${auth_access_token}", profile.access_token().isEmpty() ? "NA" : profile.access_token())
				.replace("${clientid}", "NA")
				.replace("${auth_xuid}", "NA")
				.replace("${user_type}", "msa")
				.replace("${version_type}", "CraftZ") 
				.replace("${resolution_width}", "854")
				.replace("${resolution_height}", "854x480")
				.replace("${natives_directory}", gamefolder+fs+"natives")
				.replace("${launcher_name}", "CraftZ")
				.replace("${launcher_version}", "1.0.0")
				.replace("${classpath}", jars)
				.replace("${library_directory}", gamefolder+fs+"libraries")
				.replace("${classpath_separator}", File.pathSeparator); 
	}
	
	public ArrayList<String> prioritized(ArrayList<String> args, String type) {
				
		ArrayList<String> prioritized = new ArrayList<String>(Arrays.asList(new String[20]));
		
		for(int i=0; i<args.size(); i++) prioritized.set(getPriority(args.get(i), type), args.get(i));
		return prioritized;
	}
	
	public int getPriority(String key, String type) {
		
		if(type.equals("jvm")) {
			if(key.equals("-XstartOnFirstThread")) return 0;
			if(key.equals("-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump")) return 1;
			if(key.equals("-Dos.name=Windows 10 -Dos.version=10.0")) return 2;
			if(key.equals("-Xss1M")) return 3;
			if(key.startsWith("-Djava.library.path")) return 4;
			if(key.startsWith("-Dminecraft.launcher.brand")) return 5;
			if(key.startsWith("-Dminecraft.launcher.version")) return 6;
			if(key.startsWith("-cp")) return 7;
			if(key.startsWith("-Djava.net.preferIPv6Addresses")) return 8; //forge
			if(key.startsWith("-DignoreList")) return 9; //forge
			if(key.startsWith("-DmergeModules")) return 10; //forge
			if(key.startsWith("-DlibraryDirectory")) return 11; //forge
			if(key.startsWith("-p")) return 12; //forge
			if(key.startsWith("--add-modules")) return 13;
			if(key.startsWith("--add-opens") && key.split(" ")[1].equals("java.base/java.util.jar=cpw.mods.securejarhandler")) return 14; //forge
			if(key.startsWith("--add-opens") && key.split(" ")[1].equals("java.base/java.lang.invoke=cpw.mods.securejarhandler")) return 15; //forge 
			if(key.startsWith("--add-exports") && key.split(" ")[1].equals("java.base/sun.security.util=cpw.mods.securejarhandler")) return 16; //forge
			if(key.startsWith("--add-exports") && key.split(" ")[1].equals("jdk.naming.dns/com.sun.jndi.dns=java.naming")) return 17; //forge
		}
		
		if(type.equals("game")) {
			if(key.startsWith("--username")) return 0;
			if(key.startsWith("--version")) return 1;
			if(key.startsWith("--gameDir")) return 2;
			if(key.startsWith("--assetsDir")) return 3;
			if(key.startsWith("--assetIndex")) return 4;
			if(key.startsWith("--uuid")) return 5;
			if(key.startsWith("--accessToken")) return 6;
			if(key.startsWith("--clientId")) return 7;
			if(key.startsWith("--xuid")) return 8;
			if(key.startsWith("--userType")) return 9;
			if(key.startsWith("--versionType")) return 10;
			if(key.startsWith("--versionType")) return 11;
			//if(key.startsWith("is_demo_user")) return 12;
			//if(key.startsWith("has_custom_resolution")) return 13;
			if(key.startsWith("--launchTarget")) return 14;
			if(key.startsWith("--fml.forgeVersion")) return 15;
			if(key.startsWith("--fml.mcVersion")) return 16;
			if(key.startsWith("--fml.forgeGroup")) return 17;
			if(key.startsWith("--fml.mcpVersion")) return 18;
		}
		return -1;
	}
	
	public ArrayList<String> build(HashMap<String, ArrayList<String>> map) {
		
		ArrayList<String> array = new ArrayList<>();
		
		String jars = "\"";
	    String[] components = forge_version.split("-");
	    String versionName = components[0]+"-forge-"+components[1];
	    
		for(String key : gamelibs) {
			if(!(key.contains("lwjgl") && key.contains("3.2.1")) && !key.contains("natives")) {
				jars = jars+key+File.pathSeparator;
			}
		}
		jars=jars+versionJar.toString()+"\"";
		
		for(Entry<String, ArrayList<String>> entry : map.entrySet()) {
						
			if(entry.getValue().isEmpty()) {
				String replaced = entry.getKey();
				if(!replaced.startsWith("--") && replaced.contains("${version_name}")) replaced = replaced.replace("${version_name}", version);		
				array.add(fill(replaced, versionName, jars));
			}else {
				for(String string : entry.getValue()) {
					if(entry.getKey().startsWith("-")) {
						String quote = entry.getKey().equals("-p") ? "\"" : "";
						array.add(entry.getKey()+" "+quote+fill(string, versionName, jars)+quote);
					}else if(applicable(entry.getKey())) array.add(string);
				}
			}
		}
		return array;
	}
	
	public boolean applicable(String rule) {
		if(rule.equals("has_custom_resolution")) return false;
		if(rule.equals("is_demo_user")) return false;
		if(rule.equals("windows") && os == net.xxathyx.craftz.launcher.util.System.OS.WINDOWS) return true;
		if(rule.equals("osx") && os == net.xxathyx.craftz.launcher.util.System.OS.MAC) return true;
		if(rule.equals("x86") && System.getProperty("os.name").contains("arch")) return true; //AKA arch linux
		if(rule.contains("windows^10") && System.getProperty("os.name").contains("Windows 10")) return true;
		return false;
	}
	
	public void launch() {
		
		HashMap<String, ArrayList<String>> game = args("game", "features");
		HashMap<String, ArrayList<String>> jvm = args("jvm", "os");
		
		Process process = process(jvm, game);
		
		ProcessBuilder processBuilder;
		
        if(os.equals(net.xxathyx.craftz.launcher.util.System.OS.WINDOWS)) {
            processBuilder = new ProcessBuilder("taskkill", "/PID", String.valueOf(pid), "/F");
        }else processBuilder = new ProcessBuilder("kill", "-9", String.valueOf(pid));
        
        try {
    	    processBuilder.start();
    		
            StringBuilder processOutput = new StringBuilder();

            try(BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));) {
            	
                String readLine;

                while((readLine = processOutputReader.readLine()) != null) {
                    processOutput.append(readLine + System.lineSeparator());
                }

                process.waitFor();
            } catch (InterruptedException e) {
				e.printStackTrace();
			    logger.log(e);
			}
            System.out.println(processOutput.toString().trim());
        }catch(IOException e) {
			e.printStackTrace();
		    logger.log(e);
        }
	}
	
	public File client() {
		
		step++; info("Downloading Minecraft Client");
		
		try {
			JSONObject json = new JSONObject(new String(Files.readAllBytes(versionJson.toPath())));
			JSONObject client = json.getJSONObject("downloads").getJSONObject("client");
			
			MAIN_CLASS = json.getString("mainClass");
			
			File versionJar = new File(versionFolder, json.getString("id")+".jar");
			if(!versionJar.exists() || versionJar.length() != client.getLong("size") && verify || overwrite) {
				left+=client.getLong("size");
				net.xxathyx.craftz.launcher.util.System.download(versionJar.toString(), client.getString("url"));
				downloaded+=client.getLong("size");
			}
			
			this.versionJar=versionJar;		
			return versionJar;
		}catch(IOException | JSONException | InterruptedException e) {
			e.printStackTrace();
		    logger.log(e);
		}
		return null;
	}
	
	public HashMap<String, String> assets() {
		
		info("Fetching Minecraft assets");
		
		try {
			if(assets.isEmpty()) {
				
				JSONObject json = new JSONObject(new String(Files.readAllBytes(versionJson.toPath())));
				
				JSONObject indexer = json.getJSONObject("assetIndex");
				File file = new File(gamefolder+fs+"assets"+fs+"indexes"+fs+indexer.getString("id")+".json");
			    
				if(!file.exists() || file.length() != indexer.getLong("size") && verify || overwrite) {
					download(file.toString(), indexer.getString("url"));
				}
							
				JSONObject objects = new JSONObject(new String(Files.readAllBytes(file.toPath()))).getJSONObject("objects");
				
				for(String key : objects.keySet()) {
					
					JSONObject object = objects.getJSONObject(key);
					
					String hash = object.getString("hash");
					String begining = hash.substring(0, 2);
					
					indexes.put(hash, key);
					
					String path = gamefolder+fs+"assets"+fs+"objects"+fs+begining+fs+hash;
					new File(path).getParentFile().mkdirs();
					
					assets.put(path, MINECRAFT_RESOURCES.replaceAll("%begining%", begining).replaceAll("%hash%", hash));
					sizes.put(path, object.getLong("size"));
					left+=object.getLong("size");
				}
				
				JSONObject client = json.getJSONObject("logging").getJSONObject("client").getJSONObject("file");
				String path = gamefolder+fs+"assets"+fs+"log_configs"+fs+client.getString("id");
				assets.put(path, client.getString("url"));
				sizes.put(path, client.getLong("size"));
			}
		}catch(IOException e) {
			e.printStackTrace();
		    logger.log(e);
		}
		return assets;
	}
	
	public HashMap<String, String> libraries() {
		
		info("Fetching Minecraft libraries");
		
		try {
			if(libraries.isEmpty()) {
				
				JSONObject json = new JSONObject(new String(Files.readAllBytes(versionJson.toPath())));
				JSONArray array = json.getJSONArray("libraries");
				
			    JSONArray temp = new JSONArray(array);
			    
				if(forge) {
				    String[] components = forge_version.split("-");
				    String versionName = components[0]+"-forge-"+components[1];
				    File forgeJson = new File(new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "versions"+fs+versionName+fs), versionName+".json");
				    
				    array.clear();
				    
					if(forgeJson.exists()) {
						array.putAll(new JSONObject(new String(Files.readAllBytes(forgeJson.toPath()))).getJSONArray("libraries"));
						array.putAll(temp);
					}
				}
				
				array.forEach(object -> {
					
					JSONObject library = (JSONObject) object;
					
					JSONObject artifact = library.getJSONObject("downloads").getJSONObject("artifact");
					String path = gamefolder+fs+"libraries"+fs+artifact.getString("path");
					
					if(!gamelibs.contains(path)) gamelibs.add(path);
					
					libraries.put(path, artifact.getString("url"));
					sizes.put(path, artifact.getLong("size"));
					left+=artifact.getLong("size");
					
					if(library.getJSONObject("downloads").has("classifiers")) {
						
						JSONObject classifiers = library.getJSONObject("downloads").getJSONObject("classifiers");
						
						String natives =
							os == net.xxathyx.craftz.launcher.util.System.OS.WINDOWS && classifiers.has("natives-windows") ? "natives-windows" :
							os == net.xxathyx.craftz.launcher.util.System.OS.LINUX && classifiers.has("natives-linux") ? "natives-linux" :
							os == net.xxathyx.craftz.launcher.util.System.OS.MAC && classifiers.has("natives-macos") ? "natives-macos" : "no-natives";
						
						if(!natives.equals("no-natives")) {
							
							JSONObject classifier = classifiers.getJSONObject(natives);
							
							path = gamefolder+fs+"libraries"+fs+classifier.getString("path");
							
							libraries.put(path, classifier.getString("url"));
							sizes.put(path, classifier.getLong("size"));
							this.natives.add(path);
							left+=classifier.getLong("size");
						}
					}
				});
			}
		}catch(IOException e) {
			e.printStackTrace();
		    logger.log(e);
		}
		return libraries;
	}
	
	public String version_manifest(String version) {
		
		String[] data = {""};
		
		try {
	        File file = new File(net.xxathyx.craftz.launcher.util.System.getGameFolder(), "version_manifest_v2.json");
	        file.deleteOnExit();
	                
	        download(file.toString(), VERSION_MANIFEST_V2);
	        
			JSONObject manifest = new JSONObject(new String(Files.readAllBytes(file.toPath())));
			JSONArray versions = manifest.getJSONArray("versions");
					
			versions.forEach(object -> {
				JSONObject jsonObject = (JSONObject) object;
				if(jsonObject.getString("id").equals(version)) data[0] = jsonObject.getString("url"); return;
			});
		}catch(IOException e) {
			e.printStackTrace();
		    logger.log(e);
		}
		return data[0];
	}
	
    private static ClassLoader getParentClassLoader() {
    	
        if(!System.getProperty("java.version").startsWith("1.")) {
            try {
                return (ClassLoader) ClassLoader.class.getDeclaredMethod("getPlatformClassLoader").invoke(null);
            }catch (Exception e) {
                System.out.println("No platform classloader: " + System.getProperty("java.version"));
    		    logger.log(e);
            }
        }
        return null;
    }
	
	public void download(String to, String from) {
	    
	    File file = new File(to);
	    
        try {
		    URL url = new URL(from);  
		    URLConnection connection = url.openConnection();  
		    InputStream inputStream = connection.getInputStream();  
		    FileOutputStream outputStream = new FileOutputStream(file);
		    
		    final byte[] bt = new byte[1024];
		    int len;
		    while((len = inputStream.read(bt)) != -1) outputStream.write(bt, 0, len);
		    outputStream.close();
		    
		}catch (IOException e) {
			e.printStackTrace();
		    logger.log(e);
		}
	}
	
	public HashMap<String, Long> getSizes() {
		return sizes;
	}
	
	public String getGameFolder() {
		return gamefolder;
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getForgeVersion() {
		return forge_version;
	}
	
	public File getVersionFolder() {
		return versionFolder;
	}
	
	public File getVersionJson() {
		return versionJson;
	}
	
	public File getAssetsFolder() {
		return assetsFolder;
	}
}