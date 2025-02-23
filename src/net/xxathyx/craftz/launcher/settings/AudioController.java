package net.xxathyx.craftz.launcher.settings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.logging.Logger;
import net.xxathyx.craftz.launcher.translation.Translater;

public class AudioController implements Initializable {
	
	private static final Logger logger = Main.logger;
	
	private Settings settings;
	
    public int width = 1080;
    public int height = 720;
	
	@FXML private ImageView hide_image;
	@FXML private ImageView close_image;
	
	@FXML private ImageView save_icon;
	
	@FXML private Slider main_volume_slider;
	@FXML private Slider music_slider;
	@FXML private Slider jukebox_slider;
	@FXML private Slider weather_slider;
	@FXML private Slider hostile_mobs_slider;
	@FXML private Slider player_slider;
	@FXML private Slider blocks_slider;
	@FXML private Slider friendly_slider;
	@FXML private Slider ambient_slider;
	@FXML private Slider vocal_chat_slider;
	
	@FXML private Text music_header_text;
	@FXML private Text sounds_header_text;
	
	@FXML private Text main_volume_header_text;
	@FXML private Text music_second_header_text;
	@FXML private Text weather_header_text;
	@FXML private Text hostile_entities_header_text;
	@FXML private Text players_header_text;
	@FXML private Text blocks_header_text;
	@FXML private Text friendly_entities_header_text;
	@FXML private Text ambient_header_text;
	@FXML private Text vocal_chat_header_text;
	
	@FXML private Text music_text;
	@FXML private Text main_volume_text;
	@FXML private Text jukebox_text;
	@FXML private Text weather_text;
	@FXML private Text hostile_entities_text;
	@FXML private Text player_text;
	@FXML private Text blocks_text;
	@FXML private Text friendly_entities_text;
	@FXML private Text ambient_text;
	@FXML private Text vocal_chat_text;
	
	public void load() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(settings.getOptionsFile()));
			String line = bufferedReader.readLine();
			
		    while(line != null) {
		    	String[] brut = line.split(":");
		    	if(brut.length>1) {
			    	if(brut[0].contains("soundCategory_master")) {main_volume_slider.setValue((int)(Float.parseFloat(brut[1])*100));main_volume_text.setText((int)(Float.parseFloat(brut[1])*100)+"%");}
			    	if(brut[0].contains("soundCategory_music")) {music_slider.setValue((int)(Float.parseFloat(brut[1])*100));music_text.setText((int)(Float.parseFloat(brut[1])*100)+"%");}
			    	if(brut[0].contains("soundCategory_record")) {jukebox_slider.setValue((int)(Float.parseFloat(brut[1])*100));jukebox_text.setText((int)(Float.parseFloat(brut[1])*100)+"%");}
			    	if(brut[0].contains("soundCategory_weather")) {weather_slider.setValue((int)(Float.parseFloat(brut[1])*100));weather_text.setText((int)(Float.parseFloat(brut[1])*100)+"%");}
			    	if(brut[0].contains("soundCategory_hostile")) {hostile_mobs_slider.setValue((int)(Float.parseFloat(brut[1])*100));hostile_entities_text.setText((int)(Float.parseFloat(brut[1])*100)+"%");}
			    	if(brut[0].contains("soundCategory_player")) {player_slider.setValue((int)(Float.parseFloat(brut[1])*100));player_text.setText((int)(Float.parseFloat(brut[1])*100)+"%");}
			    	if(brut[0].contains("soundCategory_blocks")) {blocks_slider.setValue((int)(Float.parseFloat(brut[1])*100));blocks_text.setText((int)(Float.parseFloat(brut[1])*100)+"%");}
			    	if(brut[0].contains("soundCategory_neutral")) {friendly_slider.setValue((int)(Float.parseFloat(brut[1])*100));friendly_entities_text.setText((int)(Float.parseFloat(brut[1])*100)+"%");}
			    	if(brut[0].contains("soundCategory_ambient")) {ambient_slider.setValue((int)(Float.parseFloat(brut[1])*100));ambient_text.setText((int)(Float.parseFloat(brut[1])*100)+"%");}
			    	if(brut[0].contains("soundCategory_voice")) {vocal_chat_slider.setValue((int)(Float.parseFloat(brut[1])*100));vocal_chat_text.setText((int)(Float.parseFloat(brut[1])*100)+"%");}
		    	}
		        line = bufferedReader.readLine();
		    }
			bufferedReader.close();
		}catch (IOException e) {
			e.printStackTrace();
			logger.log(e);
		}
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> {
			
	    	Translater translater = new Translater(settings.getProfile().getLang());
	    	
	    	music_header_text.setText(translater.music());
	    	sounds_header_text.setText(translater.sounds());
			main_volume_header_text.setText(translater.main_volume());
	    	music_second_header_text.setText(translater.music());
			weather_header_text.setText(translater.weather());
			hostile_entities_header_text.setText(translater.hostile_entities());
			players_header_text.setText(translater.players());
			blocks_header_text.setText(translater.blocks());
			friendly_entities_header_text.setText(translater.friendly_entities());
			ambient_header_text.setText(translater.ambient());
			vocal_chat_header_text.setText(translater.vocal_chat());
			
			load();
		});
	}
	
	public void update_main_volume(MouseEvent event) {settings.add("soundCategory_master", String.valueOf(main_volume_slider.getValue()/100));main_volume_text.setText(String.valueOf((int)main_volume_slider.getValue())+"%");}
	public void update_music(MouseEvent event) {settings.add("soundCategory_music", String.valueOf(music_slider.getValue()/100));music_text.setText(String.valueOf((int)music_slider.getValue())+"%");}
	public void update_jukebox(MouseEvent event) {settings.add("soundCategory_record", String.valueOf(jukebox_slider.getValue()/100));jukebox_text.setText(String.valueOf((int)jukebox_slider.getValue())+"%");}
	public void update_weather(MouseEvent event) {settings.add("soundCategory_weather", String.valueOf(weather_slider.getValue()/100));weather_text.setText(String.valueOf((int)weather_slider.getValue())+"%");}
	public void update_hostile(MouseEvent event) {settings.add("soundCategory_hostile", String.valueOf(hostile_mobs_slider.getValue()/100));hostile_entities_text.setText(String.valueOf((int)hostile_mobs_slider.getValue())+"%");}
	public void update_player(MouseEvent event) {settings.add("soundCategory_player", String.valueOf(player_slider.getValue()/100));player_text.setText(String.valueOf((int)player_slider.getValue())+"%");}
	public void update_blocks(MouseEvent event) {settings.add("soundCategory_blocks", String.valueOf(blocks_slider.getValue()/100));blocks_text.setText(String.valueOf((int)blocks_slider.getValue())+"%");}
	public void update_friendly(MouseEvent event) {settings.add("soundCategory_neutral", String.valueOf(friendly_slider.getValue()/100));friendly_entities_text.setText(String.valueOf((int)friendly_slider.getValue())+"%");}
	public void update_ambient(MouseEvent event) {settings.add("soundCategory_ambient", String.valueOf(ambient_slider.getValue()/100));ambient_text.setText(String.valueOf((int)ambient_slider.getValue())+"%");}
	public void update_vocal_chat(MouseEvent event) {settings.add("soundCategory_voice", String.valueOf(vocal_chat_slider.getValue()/100));vocal_chat_text.setText(String.valueOf((int)vocal_chat_slider.getValue())+"%");}
	
	public void enter_subcategory(Event event) {((Button)event.getSource()).setOpacity(1);}
	public void exit_subcategory(Event event) {((Button)event.getSource()).setOpacity(0.8);}
	
	public void enter_save(Event event) {((Button)event.getSource()).setOpacity(1); save_icon.setOpacity(1);}
	public void exit_save(Event event) {((Button)event.getSource()).setOpacity(0.8); save_icon.setOpacity(0.8);}
	
	public void enter_hide(Event event) {hide_image.setOpacity(1);}
	public void exit_hide(Event event) {hide_image.setOpacity(0.8);}
	
	public void enter_close(Event event) {close_image.setOpacity(1);}
	public void exit_close(Event event) {close_image.setOpacity(0.8);}
	
	public void graphics(ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		settings.graphics(stage);
	}
	
	public void controls(ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		settings.controls(stage);
	}
	
	public void launcher(ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		settings.launcher(stage);
	}
	
	public void save(ActionEvent event) {
		settings.save();
		Platform.runLater(() -> {((Node)event.getSource()).getScene().getWindow().hide();});
	}
	
	public void discard(ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		settings.getHome().setX(stage.getX()); settings.getHome().setY(stage.getY());
		settings.getHome().show();
		Platform.runLater(() -> {((Node)event.getSource()).getScene().getWindow().hide();});
	}
	
	public void hide(ActionEvent event) {
		((Stage)((Node)event.getSource()).getScene().getWindow()).setIconified(true);
	}
	
	public void close(ActionEvent event) {
		System.exit(0);
	}
	
	public void setSettings(Settings settings) {
		this.settings=settings;
	}
}