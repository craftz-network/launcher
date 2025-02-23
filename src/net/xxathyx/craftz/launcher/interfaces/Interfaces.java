package net.xxathyx.craftz.launcher.interfaces;

import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.display.Display;
import net.xxathyx.craftz.launcher.home.Home;
import net.xxathyx.craftz.launcher.logging.Logger;
import net.xxathyx.craftz.launcher.login.Authenticator;
import net.xxathyx.craftz.launcher.login.Login;
import net.xxathyx.craftz.launcher.profile.Profile;

public class Interfaces {
	
	private static final Logger logger = Main.logger;
	
	public static void add_account(Pane pane, Profile profile, int y) {
		
    	boolean valid = new Authenticator(profile.isMicrosoft(), profile.getUsername(), profile.access_token()).isValid();
    	
    	Rectangle background = new Rectangle(326, 66);
    	background.setFill(Color.web("#262626"));
    	background.setStroke(Color.web("#393939"));
    	background.setArcWidth(8);background.setArcHeight(8);
    	background.setLayoutX(10);background.setLayoutY(y+2);
    	
        Stop[] stops = new Stop[] {
                new Stop(0, Color.web(valid ? "#c6ff21" : "#ff2121")),
                new Stop(1, Color.web(valid ? "#92ff1f" : "#b50000"))
        };
    	
    	Rectangle available = new Rectangle(24, 64, new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops));
    	available.setStroke(Color.web("#00000000"));
    	available.setArcWidth(5);background.setArcHeight(5);
    	available.setLayoutX(11);available.setLayoutY(y+3);

    	ImageView head = null;
		try {
			head = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(new URL("https://mc-heads.net/avatar/"+profile.getUsername()+"/64.png")), null));
	    	head.setLayoutX(39);head.setLayoutY(y+3);
		}catch (IOException e) {
			e.printStackTrace();
			logger.log(e);
		}
		
    	Text username = new Text();
    	username.setFill(Color.WHITE);
    	username.setFont(Font.font("Calibri", FontWeight.BOLD, 24));
    	username.setText(profile.getUsername());
    	username.setLayoutX(116);username.setLayoutY(y+31);
    	
    	ImageView logo = new ImageView(new Image(profile.isMicrosoft() ? Login.class.getResourceAsStream("resources/mojang.png") : Main.class.getResourceAsStream("resources/icon.png")));
    	logo.setFitWidth(profile.isMicrosoft() ? 39 : 28);
    	logo.setFitHeight(profile.isMicrosoft() ? 22 : 28);
    	logo.setLayoutX(112);logo.setLayoutY(profile.isMicrosoft() ? y+36 : y+38);
    	
    	Text type = new Text(profile.isMicrosoft() ? "MOJANG" : "CRAFTZ");
    	type.setFont(new Font("Calibri", 24));
    	type.setFill(Color.WHITE);
    	type.setLayoutX(143);type.setLayoutY(y+62);

    	Rectangle separator = new Rectangle(1, 51);
    	separator.setFill(Color.WHITE);
    	separator.setArcWidth(5);separator.setArcHeight(5);
    	separator.setLayoutX(244);separator.setLayoutY(y+10);

    	Text subscription = new Text();
    	subscription.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
    	subscription.setFill(Color.web("#ffe400"));
    	subscription.setText("VIP ACTIVE");
    	subscription.setLayoutX(261);subscription.setLayoutY(y+27);
    	
    	Button button = new Button();
    	button.setPrefSize(326, 66);
    	button.setCursor(Cursor.HAND);
    	button.setStyle("-fx-background-color: #00000000;");
    	button.setLayoutX(10);button.setLayoutY(y+2);
    	button.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override
    	    public void handle(ActionEvent event) {
    	    	if(valid) {
    	    		((Node)event.getSource()).getScene().getWindow().hide();
    	    		profile.setSelected(true);;
    	    		new Home(profile);
    	    	}else new Display("CraftZ - Login", (Stage)((Node)event.getSource()).getScene().getWindow(), 610, 750, Login.class.getResource("resources/login.fxml"), Login.class.getResource("resources/style.css"), 0, 0, false, true, true, true);
    	    }
    	});
    	button.setOnMouseEntered(new EventHandler<Event>() {
    	    @Override
    	    public void handle(Event event) {
	        	background.setFill(Color.web("#2f2f2f"));
    	    }
    	});
    	button.setOnMouseExited(new EventHandler<Event>() {
    	    @Override
    	    public void handle(Event event) {
	        	background.setFill(Color.web("#262626"));
    	    }
    	});
    	
    	pane.getChildren().addAll(background, available, head, username, logo, type, separator, subscription, button);
	}
}