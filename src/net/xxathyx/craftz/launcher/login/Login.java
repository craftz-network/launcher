package net.xxathyx.craftz.launcher.login;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.display.Display;
import net.xxathyx.craftz.launcher.home.Home;
import net.xxathyx.craftz.launcher.interfaces.Interfaces;
import net.xxathyx.craftz.launcher.logging.Logger;
import net.xxathyx.craftz.launcher.profile.Profile;
import net.xxathyx.craftz.launcher.settings.Settings;
import net.xxathyx.craftz.launcher.translation.Translater;
import net.xxathyx.craftz.launcher.updater.Updater;

public class Login extends Application {
	
	private static final Logger logger = Main.logger;
	
    public int width = 610;
    public int height = 750;
	
	private double xOffset = 0; 
	private double yOffset = 0;
    
	@Override
	public void start(Stage stage) {
		
		try {
						
			net.xxathyx.craftz.launcher.util.System.getNewsFolder().mkdir();
			
			Updater updater = new Updater();
			updater.update();
			
			if(!new Translater("EN").isInstalled()) new Translater("EN").install("EN");
			if(!new Translater("FR").isInstalled()) new Translater("FR").install("FR");
			
			Settings settings = new Settings();
			if(!settings.getOptionsFile().exists()) net.xxathyx.craftz.launcher.util.System.download(settings.getOptionsFile().toString(), "https://www.dropbox.com/scl/fi/nna6vdpvbv38h9p6thnyl/options.txt?rlkey=hac3mu9qk01iobyfbd3c3nebn&st=9utu61eg&dl=1");
			if(!settings.getOptionsOfFile().exists()) net.xxathyx.craftz.launcher.util.System.download(settings.getOptionsOfFile().toString(), "https://www.dropbox.com/scl/fi/oz85m0kvgjto7e9pqf5e1/optionsof.txt?rlkey=1n6azxelmta7193iewkv6a1ad&st=0dijo6lg&dl=1");
			
			net.xxathyx.craftz.launcher.util.System.getProfileFolder().mkdir();
			
		    File pidFile = net.xxathyx.craftz.launcher.util.System.getPidFile();
			if(!pidFile.exists()) pidFile.createNewFile();
			
		    FileWriter writer = new FileWriter(pidFile);
		    writer.write(String.valueOf(net.xxathyx.craftz.launcher.util.System.getPid()));
		    writer.close();
			
		    logger.log("Login box displayed");
			
			Font.loadFont(getClass().getResourceAsStream("resources/bahnschrift.ttf"), 30);
						
			Profile profile = Profile.getSelectedProfile();
			if(profile != null) {
				
				if(profile.isSelected()) {new Home(profile);return;}
				
				File[] files = net.xxathyx.craftz.launcher.util.System.getProfileFolder().listFiles();
				
				if(files.length>1) {
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/continue.fxml"));
					
					try {
						
						Parent root = loader.load();
						
						Scene scene = new Scene(root, width, height);
						
				        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
				        stage.setX((screenBounds.getWidth()-width)/2);
				        stage.setY((screenBounds.getHeight()-height)/2); 
				        
			        	stage.setAlwaysOnTop(true);stage.setAlwaysOnTop(false);
			        	stage.toFront();
				        
				        stage.setTitle("CraftZ - Continue");
				        stage.getIcons().add(new Image(new Main().getClass().getResourceAsStream("resources/icon.png")));	
				        
				        stage.initStyle(StageStyle.TRANSPARENT);
				        scene.setFill(Color.TRANSPARENT);
										        
			        	ScrollPane scrollPane = new ScrollPane();
			        	scrollPane.setPrefSize(360, 452);
			        	scrollPane.setStyle("-fx-background-color: #2f2f2f");
			        	scrollPane.setLayoutX(124);scrollPane.setLayoutY(204);
			        	scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

			        	Pane pane = new Pane();
			        	pane.setStyle("-fx-background-color: #2f2f2f");
			        	pane.setPrefSize(360, 452+69*files.length);
			        	pane.setLayoutX(124);pane.setLayoutY(204);
				        
				        int y = 0;

				        for(File file : files) {
				        	Profile current = new Profile(file);
				        	Interfaces.add_account(pane, current, y);
				        	y+=69;
				        }
				        
			        	scrollPane.setContent(pane);
			        	((Pane) root).getChildren().add(scrollPane);
				        
				        root.setOnMousePressed(new EventHandler<MouseEvent>() {
				            @Override
				            public void handle(MouseEvent event) {
				                xOffset = event.getSceneX();
				                yOffset = event.getSceneY();
				            }
				        });
				        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
				            @Override
				            public void handle(MouseEvent event) {
				            	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				            	stage.setX(event.getScreenX() - xOffset);
				            	stage.setY(event.getScreenY() - yOffset);
				            }
				        });
				        
				        stage.setScene(scene);
				        
					}catch (IOException e) {
						e.printStackTrace();
						logger.log(e);
					}
					stage.show();
				}else new Home(profile);return;
			}
			new Display("CraftZ - Login", stage, width, height, getClass().getResource("resources/login.fxml"), getClass().getResource("resources/style.css"), (int)stage.getX(), (int)stage.getY(), true, true, true, true);			
		}catch(Exception e) {
		    e.printStackTrace();
		    logger.log(e);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}