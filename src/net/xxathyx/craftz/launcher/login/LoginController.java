package net.xxathyx.craftz.launcher.login;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.net.httpserver.HttpServer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.home.Home;
import net.xxathyx.craftz.launcher.logging.Logger;
import net.xxathyx.craftz.launcher.profile.Profile;
import net.xxathyx.craftz.launcher.translation.Translater;

public class LoginController implements Initializable {
	
	private static final Logger logger = Main.logger;
	
	private Translater translater = new Translater(net.xxathyx.craftz.launcher.util.System.getCountryCode());
	
	private static HttpServer httpServer; 
	
	private boolean authenticating = false;
	
	private ArrayList<Rectangle> squares = new ArrayList<>();
	private ArrayList<String> colors = new ArrayList<String>(Arrays.asList("#2f2f2f", "#282828", "#252525", "#222222", "#1e1d1d", "#0d0d0d", "#141414", "#1b1b1b"));
	
	@FXML private TextField username_field;
	@FXML private PasswordField password_field;
	@FXML private Text craftz_login_text;
	
	@FXML private Text login_successful;
	@FXML private Text login_failed;
	
	@FXML private ImageView hide_image;
	@FXML private ImageView close_image;
	
	@FXML private Button password_forgotten;
	@FXML private Button login_craftz_button;
	
	@FXML private Text login;
	@FXML private Text choose_how;
	
	@FXML private Button microsoft_signin;
	@FXML private Button craftz_signin;
	@FXML private Text no_account;
	@FXML private Button craftz_signup;
	
	@FXML private AnchorPane accounts_selector_pane;
	
	@FXML Rectangle square0;@FXML Rectangle square1;@FXML Rectangle square2;@FXML Rectangle square3;@FXML Rectangle square4;
	@FXML Rectangle square5;@FXML Rectangle square6;@FXML Rectangle square7;@FXML Rectangle square8;@FXML Rectangle square9;
	@FXML Rectangle square10;@FXML Rectangle square11;@FXML Rectangle square12;@FXML Rectangle square13;@FXML Rectangle square14;
	@FXML Rectangle square15;@FXML Rectangle square16;@FXML Rectangle square17;	@FXML Rectangle square18;@FXML Rectangle square19;
	@FXML Rectangle square20;@FXML Rectangle square21;@FXML Rectangle square22;	@FXML Rectangle square23;@FXML Rectangle square24;
	@FXML Rectangle square25;@FXML Rectangle square26;@FXML Rectangle square27;	@FXML Rectangle square28;@FXML Rectangle square29;
	
	long attempt;
	
	public void signup_craftz(ActionEvent event) {
		net.xxathyx.craftz.launcher.util.System.web("https://craftz.net/login-signup/?signup=true&embed=true");
		auth(event);
	}
	
	public void signin(ActionEvent event) {
		net.xxathyx.craftz.launcher.util.System.web("https://craftz.net/login-signup?embed=true");
		auth(event);
	}
	
	public void auth(ActionEvent event) {
		
		if(!authenticating) {
			
			authenticating = true;
						
			try {
				httpServer = HttpServer.create(new InetSocketAddress(50828), 0);
		        httpServer.createContext("/", exchange -> {
		        		                		        	
		        	InputStream inputStream = Login.class.getResourceAsStream("resources/index.html");
		        	
		        	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		        	
	                byte[] buffer = new byte[1024];
	                int bytesRead;
	                
	                while((bytesRead = inputStream.read(buffer)) != -1) byteArrayOutputStream.write(buffer, 0, bytesRead);
	                		        	
                    byte[] response = byteArrayOutputStream.toByteArray();
					exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
					exchange.sendResponseHeaders(200, response.length);
					
					OutputStream os = exchange.getResponseBody();
					os.write(response);
					os.close();
		        	
		            String args = exchange.getRequestURI().toString();
		            String username = args.substring(args.indexOf('=')+1, args.lastIndexOf('&'));
		            String token = args.substring(args.lastIndexOf('=')+1);
		            		            
		            Authenticator authenticator = new Authenticator(token.length()>128, username, token);
		            		            
		            if(authenticator.isValid()) {
		            			            			            	
		                Platform.runLater(() -> {
		    				Profile profile = new Profile(authenticator.uuid());
		    				
		    				if(authenticator.isMicrosoft()) {
		    					logger.log("Microsoft auth successful");
								if(!profile.exists()) profile.create(true, authenticator.access_token(), authenticator.username(), authenticator.avatar_skin());
		    				}else {
		    					logger.log("CraftZ auth successful");
			    				if(!profile.exists()) profile.create(false, authenticator.access_token(), authenticator.username(), "default");
		    				}
		        			toHome((Stage)((Node)event.getSource()).getScene().getWindow(), profile);
		        		});
						authenticating = false;
						httpServer.stop(0); httpServer = null;
		            }
		        });
		        httpServer.setExecutor(null);
		        httpServer.start();
			}catch (IOException e) {
				e.printStackTrace();
				logger.log(e);
			}
		}
	}
	
	public void signin_microsoft_enter(Event event) {((Button)event.getSource()).setStyle("-fx-background-color: #1f1f1f;");}
	public void signin_microsoft_exit(Event event) {((Button)event.getSource()).setStyle("-fx-background-color: #161616;");}
	
	public void signin_craftz_enter(Event event) {((Button)event.getSource()).setStyle("-fx-background-color: #6a6a6a;");}
	public void signin_craftz_exit(Event event) {((Button)event.getSource()).setStyle("-fx-background-color: #545454;");}
	
	public void signup_craftz_enter(Event event) {((Button)event.getSource()).setStyle("-fx-background-color: #9b7171;");}
	public void signup_craftz_exit(Event event) {((Button)event.getSource()).setStyle("-fx-background-color: #845f5f;");}
	
	public void craftz_login_enter(Event event) {((Button)event.getSource()).setStyle("-fx-background-color: #536e38;");}
	public void craftz_login_exit(Event event) {((Button)event.getSource()).setStyle("-fx-background-color: #475e30;");}
	
	public void enter_hide(Event event) {hide_image.setOpacity(1);}
	public void exit_hide(Event event) {hide_image.setOpacity(0.8);}
	
	public void enter_close(Event event) {close_image.setOpacity(1);}
	public void exit_close(Event event) {close_image.setOpacity(0.8);}
	
	public void hide(ActionEvent event) {
		((Stage)((Node)event.getSource()).getScene().getWindow()).setIconified(true);
	}
	
	public void close(ActionEvent event) {
        if(httpServer != null) httpServer.stop(0);
		System.exit(0);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> {
			
			if(username_field != null) username_field.setPromptText(translater.username());
			if(password_field != null) password_field.setPromptText(translater.password());
			if(password_forgotten != null) password_forgotten.setText(translater.forgot_password());
			if(login_craftz_button != null) login_craftz_button.setText(translater.log_in());
			if(login != null) login.setText(translater.login());
			if(choose_how != null) choose_how.setText(translater.login_choose());
			if(microsoft_signin != null) microsoft_signin.setText(translater.signin_microsoft());
			if(craftz_signin != null) craftz_signin.setText(translater.signin_craftz());
			if(no_account != null) no_account.setText(translater.no_account());
			if(craftz_signup != null) craftz_signup.setText(translater.signup_craftz());
			
			squares.add(square0);squares.add(square1);squares.add(square2);squares.add(square3);squares.add(square4);
			squares.add(square5);squares.add(square6);squares.add(square7);squares.add(square8);squares.add(square9);
			squares.add(square10);squares.add(square11);squares.add(square12);squares.add(square13);squares.add(square14);
			squares.add(square15);squares.add(square16);squares.add(square17);squares.add(square18);squares.add(square19);
			squares.add(square20);squares.add(square21);squares.add(square22);squares.add(square23);squares.add(square24);
			squares.add(square25);squares.add(square26);squares.add(square27);squares.add(square28);squares.add(square29);
			
			if(net.xxathyx.craftz.launcher.util.System.getRandomNumber(1, 1000)==1)  for(Rectangle square : squares) update(square);
		});
	}
	
	public AnchorPane getAccountSelectorPane() {
		return accounts_selector_pane;
	}
	
	public void update(Rectangle square) {
		
		Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
	    	
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
	                @Override
	                public void run() {
	                	if(!square.isVisible()) square.setVisible(true);
	                	square.setFill(Color.web(colors.get(net.xxathyx.craftz.launcher.util.System.getRandomNumber(0, colors.size()-1))));
	                	update(square);
	                }
	            });
			}		    	
	    }, net.xxathyx.craftz.launcher.util.System.getRandomNumber(1500, 2500));
	}
	
	public void toHome(Stage currentStage, Profile profile) {
		currentStage.hide();
		new Home(profile);
	}
}