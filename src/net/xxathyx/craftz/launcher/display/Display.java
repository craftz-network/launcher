package net.xxathyx.craftz.launcher.display;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.logging.Logger;

public class Display {
	
	private static final Logger logger = Main.logger;
	
	private Object controller;
	
	private double xOffset = 0; 
	private double yOffset = 0;
	
	public Display(String title, Stage previous, int width, int height, URL fxml, URL css, int currentX, int currentY, boolean borderless, boolean movable, boolean centered, boolean front) {
		
		if(previous == null) previous = new Stage();
		
		FXMLLoader loader = new FXMLLoader(fxml);
		
		try {
			
			Parent root = loader.load();
			
			Scene scene = new Scene(root, width, height);
			scene.getStylesheets().add(css.toExternalForm());
			
			if(centered) {
		        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		        previous.setX((screenBounds.getWidth()-width)/2);
		        previous.setY((screenBounds.getHeight()-height)/2); 
			}else {
		        previous.setX(currentX);
		        previous.setY(currentY); 
			}
	        
	        if(front) {
		        previous.setAlwaysOnTop(true);previous.setAlwaysOnTop(false);
		        previous.toFront();
	        }
	        
	        previous.setTitle(title);
	        previous.getIcons().add(new Image(new Main().getClass().getResourceAsStream("resources/icon.png")));	
	        
	        if(borderless) previous.initStyle(StageStyle.TRANSPARENT);
	        scene.setFill(Color.TRANSPARENT);
			
	        if(movable) {
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
	        }
	        previous.setScene(scene);
		}catch (IOException e) {
			e.printStackTrace();
		    logger.log(e);
		}
        previous.show();
        controller = loader.getController();
	}
	
	public Object getController() {
		return controller;
	}
	
	@SuppressWarnings("unchecked")
	public static void closeWindows() {
        String name = "getWindows"; if(Double.parseDouble(System.getProperty("java.class.version")) <= 52) name = "impl_getWindows";
        
		Method getWindows = null;
		for(Method method : Window.class.getMethods()) if(method.getName()==name) getWindows=method;
		try {
			if(Double.parseDouble(System.getProperty("java.class.version")) <= 52) {
				Iterator<Window> impl_getWindows = (Iterator<Window>) getWindows.invoke(null);
				List<Window> windows = new ArrayList<>();
				impl_getWindows.forEachRemaining(windows::add);
				Collections.reverse(windows);
				for(Window window : windows) window.hide();
			}else {
				for(Window window : (ObservableList<Window>) getWindows.invoke(null)) window.hide();
			}
		}catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		    logger.log(e);
		}
	}
}