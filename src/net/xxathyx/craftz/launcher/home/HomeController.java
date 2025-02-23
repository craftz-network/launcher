package net.xxathyx.craftz.launcher.home;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.configuration.FileConfiguration;
import net.xxathyx.craftz.launcher.download.Downloader;
import net.xxathyx.craftz.launcher.logging.Logger;
import net.xxathyx.craftz.launcher.profile.Profile;
import net.xxathyx.craftz.launcher.settings.Settings;
import net.xxathyx.craftz.launcher.telemetry.Telemetry;
import net.xxathyx.craftz.launcher.translation.Translater;

public class HomeController implements Initializable {
	
	private static final Logger logger = Main.logger;
	
	private Profile profile;
	
	private boolean microsoft;
	private int coins;
	
    public int width = 1080;
    public int height = 720;
	
	private int slider = 0;
    
	private ArrayList<String> colors = new ArrayList<String>(Arrays.asList("#3a3a3a", "#3b3b3b", "#3c3c3c", "#3d3d3d", "#3e3e3e", "#3f3f3f"));
	private ArrayList<Rectangle> squares = new ArrayList<>();
	
	@FXML private Text header_text;
	@FXML private Text profile_info;
	
	@FXML private ImageView menu_header;
	@FXML private ImageView profile_icon;
	
	@FXML private ImageView hide_image;
	@FXML private ImageView close_image;
	
	@FXML private ImageView left_slider;
	@FXML private ImageView right_slider;
	
	@FXML private ImageView news_preview;
	
	@FXML private ImageView bubble_one;
	@FXML private ImageView bubble_two;
	@FXML private ImageView bubble_three;
	
	@FXML private Button play_header_button;
	@FXML private Button wiki_header_button;
	
	@FXML private Text last_seen;
	
	@FXML private Button profile_button;
	@FXML private Button settings_button;
	@FXML private Button community_button;
	@FXML private Button help_button;
	
	@FXML private Button play_button;
	
	@FXML private Text news_title;
	@FXML private Text news_line_one;
	
	@FXML private ProgressBar download_bar;
	@FXML private Text download_info_text;
	@FXML private Text download_length;
	
	@FXML Rectangle square0;@FXML Rectangle square1;@FXML Rectangle square2;@FXML Rectangle square3;@FXML Rectangle square4;
	@FXML Rectangle square5;@FXML Rectangle square6;@FXML Rectangle square7;@FXML Rectangle square8;@FXML Rectangle square9;
	@FXML Rectangle square10;@FXML Rectangle square11;@FXML Rectangle square12;@FXML Rectangle square13;@FXML Rectangle square14;
	@FXML Rectangle square15;@FXML Rectangle square16;@FXML Rectangle square17;	@FXML Rectangle square18;@FXML Rectangle square19;
	@FXML Rectangle square20;@FXML Rectangle square21;@FXML Rectangle square22;	@FXML Rectangle square23;@FXML Rectangle square24;
	@FXML Rectangle square25;@FXML Rectangle square26;@FXML Rectangle square27;	@FXML Rectangle square28;@FXML Rectangle square29;
	@FXML Rectangle square30;@FXML Rectangle square31;@FXML Rectangle square32;	@FXML Rectangle square33;@FXML Rectangle square34;
	@FXML Rectangle square35;@FXML Rectangle square36;@FXML Rectangle square37;	@FXML Rectangle square38;@FXML Rectangle square39;
	@FXML Rectangle square40;@FXML Rectangle square41;@FXML Rectangle square42;	@FXML Rectangle square43;@FXML Rectangle square44;
	@FXML Rectangle square45;@FXML Rectangle square46;@FXML Rectangle square47;	@FXML Rectangle square48;@FXML Rectangle square49;
	@FXML Rectangle square50;@FXML Rectangle square51;@FXML Rectangle square52;	@FXML Rectangle square53;@FXML Rectangle square54;
	@FXML Rectangle square55;@FXML Rectangle square56;@FXML Rectangle square57;	@FXML Rectangle square58;@FXML Rectangle square59;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	    Platform.runLater(() -> {
	    	
		    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		        public void run() {
		        	new Telemetry(profile).transmit();
		        }
		    }, "Shutdown-thread"));
	    	
	    	Translater translater = new Translater(profile.getLang());
	    	FileConfiguration fileConfiguration = new FileConfiguration(new File(net.xxathyx.craftz.launcher.util.System.getUpdaterFolder(), "news.txt"));
	    	
	    	play_header_button.setText(translater.play_header());
	    	wiki_header_button.setText(translater.wiki_header());
	    	
	    	int days = net.xxathyx.craftz.launcher.util.System.pasteDays(profile.getLastime());
	    	
	    	header_text.setText(translater.welcoming());
	    	if(days > 0) last_seen.setText(days > 180 ? translater.seen_long_ago() : translater.happy_login().replaceAll("%days%", String.valueOf(days))); else last_seen.setText(translater.close_seen());
	    	
	    	coins = profile.getCoins();
	    	
	    	profile_button.setText(translater.profile());
	    	settings_button.setText(translater.settings());
	    	community_button.setText(translater.community());
	    	help_button.setText(translater.help());
	    	
	    	news_title.setText(fileConfiguration.getString("title"));
	    	news_title.setTranslateX(1030-news_title.getLayoutX()-news_title.getLayoutBounds().getWidth()-8);
	    	news_line_one.setText(fileConfiguration.getString("line-one"));
	    	news_line_one.setTranslateX(1030-news_line_one.getLayoutX()-news_line_one.getLayoutBounds().getWidth()-8);
	    	
	    	try {
				news_line_one.setText(new String(fileConfiguration.getString("line-one").getBytes(), "UTF-8"));
			}catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				logger.log(e);
			}
	    	
	    	play_button.setText(translater.play_button());
	    	
			header_text.setText(header_text.getText().replaceAll("%username%", profile.getUsername()));
			profile_info.setText(profile_info.getText().replaceAll("%username%", profile.getUsername()));
			profile_info.setText(profile_info.getText().replaceAll("%coins%", String.valueOf(coins)));
			profile_info.setTranslateX(1080-48-(profile_info.getLayoutX()+profile_info.getLayoutBounds().getWidth()));
			
			try {
				menu_header.setImage(SwingFXUtils.toFXImage(ImageIO.read(new URL(profile.isMicrosoft() ? "https://crafatar.com/avatars/"+profile.getUUID()+"?size=44&overlay" : "https://mc-heads.net/avatar/"+profile.getUsername()+"/98.png")), null));
				profile_icon.setImage(SwingFXUtils.toFXImage(ImageIO.read(new URL("https://mc-heads.net/head/"+profile.getUsername()+"/44.png")), null));
			}catch (IOException e) {
				e.printStackTrace();
				logger.log(e);
			}
		});
        Timer timer = new Timer();
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
				right_change_slider(null);
            }
        };
        timer.scheduleAtFixedRate(repeatedTask, 5000L, 5000L);
        
		squares.add(square0);squares.add(square1);squares.add(square2);squares.add(square3);squares.add(square4);
		squares.add(square5);squares.add(square6);squares.add(square7);squares.add(square8);squares.add(square9);
		squares.add(square10);squares.add(square11);squares.add(square12);squares.add(square13);squares.add(square14);
		squares.add(square15);squares.add(square16);squares.add(square17);squares.add(square18);squares.add(square19);
		squares.add(square20);squares.add(square21);squares.add(square22);squares.add(square23);squares.add(square24);
		squares.add(square25);squares.add(square26);squares.add(square27);squares.add(square28);squares.add(square29);
		squares.add(square30);squares.add(square31);squares.add(square32);squares.add(square33);squares.add(square34);
		squares.add(square35);squares.add(square36);squares.add(square37);squares.add(square38);squares.add(square39);
		squares.add(square40);squares.add(square41);squares.add(square42);squares.add(square43);squares.add(square44);
		squares.add(square45);squares.add(square46);squares.add(square47);squares.add(square48);squares.add(square49);
		squares.add(square50);squares.add(square51);squares.add(square52);squares.add(square53);squares.add(square54);
		squares.add(square55);squares.add(square56);squares.add(square57);squares.add(square58);squares.add(square59);
		
		for(Rectangle square : squares) update(square);
	}
	
	public void enter_wiki(Event event) {wiki_header_button.setOpacity(1);}
	public void exit_wiki(Event event) {wiki_header_button.setOpacity(0.8);}
	
	public void enter_subcategory(Event event) {((Button)event.getSource()).setStyle("-fx-background-color: #464646;");}
	public void exit_subcategory(Event event) {((Button)event.getSource()).setStyle("-fx-background-color: #282828;");}
	
	public void enter_play_button(Event event) {((Button)event.getSource()).setStyle("-fx-background-color: #4dc730;");}
	public void exit_play_button(Event event) {((Button)event.getSource()).setStyle("-fx-background-color: #1eb34b;");}
	
	public void enter_left_slider(Event event) {left_slider.setOpacity(0.5);}
	public void exit_left_slider(Event event) {left_slider.setOpacity(0.25);}
	
	public void enter_right_slider(Event event) {right_slider.setOpacity(0.5);}
	public void exit_right_slider(Event event) {right_slider.setOpacity(0.25);}
	
	public void enter_hide(Event event) {hide_image.setOpacity(1);}
	public void exit_hide(Event event) {hide_image.setOpacity(0.8);}
	
	public void enter_close(Event event) {close_image.setOpacity(1);}
	public void exit_close(Event event) {close_image.setOpacity(0.8);}
	
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
	
	public void left_change_slider(ActionEvent event) {
		
		ImageView currentBubble=null,nextBubble=null;
		if(slider==0) currentBubble=bubble_one;
		if(slider==1) currentBubble=bubble_two;
		if(slider==2) currentBubble=bubble_three;
		
		slider--; if(slider<0)slider=2;
		
		if(slider==0) nextBubble=bubble_one;
		if(slider==1) nextBubble=bubble_two;
		if(slider==2) nextBubble=bubble_three;
		
		if(slider==0) {
        	news_preview.setImage(new Image(this.getClass().getResourceAsStream("resources/preview1.jpg")));
            currentBubble.setImage(new Image(this.getClass().getResourceAsStream("resources/empty.png")));
            nextBubble.setImage(new Image(this.getClass().getResourceAsStream("resources/full.png")));
        }
		if(slider==1) {
        	news_preview.setImage(new Image(this.getClass().getResourceAsStream("resources/preview2.jpg")));
            currentBubble.setImage(new Image(this.getClass().getResourceAsStream("resources/empty.png")));
            nextBubble.setImage(new Image(this.getClass().getResourceAsStream("resources/full.png")));
        }
		if(slider==2) {
        	news_preview.setImage(new Image(this.getClass().getResourceAsStream("resources/preview3.jpg")));
            currentBubble.setImage(new Image(this.getClass().getResourceAsStream("resources/empty.png")));
            nextBubble.setImage(new Image(this.getClass().getResourceAsStream("resources/full.png")));
        }
	}
	
	public void right_change_slider(ActionEvent event) {
		
		ImageView currentBubble=null,nextBubble=null;
		if(slider==0) currentBubble=bubble_one;
		if(slider==1) currentBubble=bubble_two;
		if(slider==2) currentBubble=bubble_three;
		
		slider++; if(slider>2)slider=0;
		
		if(slider==0) nextBubble=bubble_one;
		if(slider==1) nextBubble=bubble_two;
		if(slider==2) nextBubble=bubble_three;
		
		if(slider==0) {
        	news_preview.setImage(new Image(this.getClass().getResourceAsStream("resources/preview1.jpg")));
            currentBubble.setImage(new Image(this.getClass().getResourceAsStream("resources/empty.png")));
            nextBubble.setImage(new Image(this.getClass().getResourceAsStream("resources/full.png")));
        }
		if(slider==1) {
        	news_preview.setImage(new Image(this.getClass().getResourceAsStream("resources/preview2.jpg")));
            currentBubble.setImage(new Image(this.getClass().getResourceAsStream("resources/empty.png")));
            nextBubble.setImage(new Image(this.getClass().getResourceAsStream("resources/full.png")));
        }
		if(slider==2) {
        	news_preview.setImage(new Image(this.getClass().getResourceAsStream("resources/preview3.jpg")));
            currentBubble.setImage(new Image(this.getClass().getResourceAsStream("resources/empty.png")));
            nextBubble.setImage(new Image(this.getClass().getResourceAsStream("resources/full.png")));
        }
	}
	
	public void play(ActionEvent event) {
		
		play_button.setVisible(false);
		download_bar.setVisible(true);
		download_info_text.setVisible(true);
		download_length.setVisible(true);
		
		Downloader downloader = new Downloader(net.xxathyx.craftz.launcher.util.System.getGameFolder().toString(), "1.18.2", false, true, false, true, "1.18.2-40.2.21", profile, net.xxathyx.craftz.launcher.util.System.getPid());
		downloader.setProgressBar(download_bar);
		downloader.setTextField(download_info_text);
		downloader.setLengthText(download_length);
		downloader.download();
	}
	
	public void wiki(ActionEvent event) {
		net.xxathyx.craftz.launcher.util.System.web("https://wiki.craftz.net/");
	}
	
	public void profile(ActionEvent event) {
		net.xxathyx.craftz.launcher.util.System.web("https://craftz.net/profile");
	}
	
	public void community(ActionEvent event) {
		net.xxathyx.craftz.launcher.util.System.web("https://craftz.net/");
	}
	
	public void settings(ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		new Settings(stage, profile, new HashMap<>());
		Platform.runLater(() -> {stage.hide();});
	}
	
	public void help(ActionEvent event) {
		net.xxathyx.craftz.launcher.util.System.web("https://discord.com/invite/x8uYugPh7S");
	}
	
	public void buy_coins(ActionEvent event) {
		net.xxathyx.craftz.launcher.util.System.web("https://craftz.net/store");
	}
	
	public void hide(ActionEvent event) {
		((Stage)((Node)event.getSource()).getScene().getWindow()).setIconified(true);
	}
	
	public void close(ActionEvent event) {
		System.exit(0);
	}
	
	public void setProfile(Profile profile) {
		this.profile=profile;
	}

	public boolean isMicrosoft() {
		return microsoft;
	}

	public void setMicrosoft(boolean microsoft) {
		this.microsoft = microsoft;
	}
}