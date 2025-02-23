package net.xxathyx.craftz.launcher.settings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.logging.Logger;
import net.xxathyx.craftz.launcher.translation.Translater;

public class ControlsController implements Initializable {
	
	private static final Logger logger = Main.logger;
	
	private Settings settings;
	
    public int width = 1080;
    public int height = 720;
	
	@FXML private TextField walk_forwards_field;
	@FXML private TextField walk_backwards_field;
	@FXML private TextField strafe_left_field;
	@FXML private TextField strafe_right_field;
	@FXML private TextField sprint_field;
	@FXML private TextField sneak_field;
	@FXML private TextField jump_field;
	@FXML private TextField attack_field;
	@FXML private TextField use_item_field;
	@FXML private TextField drop_item_field;
	@FXML private TextField walk_slowly_field;
	@FXML private TextField carry_on_field;
	@FXML private TextField open_backpack_field;
	@FXML private TextField reload_field;
	@FXML private TextField flashlight_field;
	@FXML private TextField zoom_field;
	@FXML private TextField open_inventory_field;
	@FXML private TextField hotbar_one_field;
	@FXML private TextField hotbar_two_field;
	@FXML private TextField inspect_field;
	@FXML private TextField select_firing_mode_field;
	@FXML private TextField weapon_cross_hit_field;
	@FXML private TextField weapon_attachements_field;
	@FXML private TextField weapon_zoom_field;
	@FXML private TextField wear_backpack_field;
	@FXML private TextField open_chat_field;
	@FXML private TextField enable_vocal_field;
	@FXML private TextField show_map_field;
	@FXML private TextField map_settings_field;
	@FXML private TextField open_vehicle_gui_field;
	@FXML private TextField start_car_engine_field;
	@FXML private TextField plane_throttle_up_field;
	@FXML private TextField plane_throttle_down_field;
	@FXML private TextField plane_drop_playload_field;
	@FXML private TextField move_helicopter_up_field;
	
	@FXML private Slider mouse_sensitivity_slider;
	@FXML private Text mouse_sensitivity_text;
	
	@FXML private Text mouvement_text;
	@FXML private Text vehicles_text;
	@FXML private Text combat_text;
	@FXML private Text communication_text;

	@FXML private Text walk_forwards_text;
	@FXML private Text walk_backwards_text;
	@FXML private Text strafe_left_text;
	@FXML private Text strafe_right_text;
	@FXML private Text sprint_text;
	@FXML private Text sneak_text;
	@FXML private Text jump_text;
	@FXML private Text attack_text;
	@FXML private Text use_item_text;
	@FXML private Text drop_item_text;
	@FXML private Text walk_slowly_text;
	@FXML private Text carry_on_text;
	@FXML private Text open_backpack_text;
	@FXML private Text reload_text;
	@FXML private Text flashlight;
	@FXML private Text zoom_text;
	@FXML private Text open_inventory_text;
	@FXML private Text hotbar_one_text;
	@FXML private Text hotbar_two_text;
	@FXML private Text inspect_text;
	@FXML private Text sensitivity_header_text;
	@FXML private Text select_firing_mode_text;
	@FXML private Text weapon_cross_hit_text;
	@FXML private Text weapon_attachements_text;
	@FXML private Text weapon_zoom_text;
	@FXML private Text wear_backpack_text;
	@FXML private Text open_chat_text;
	@FXML private Text enable_vocal_text;
	@FXML private Text show_map_text;
	@FXML private Text map_settings_text;
	@FXML private Text open_vehicle_gui_text;
	@FXML private Text start_car_engine_text;
	@FXML private Text plane_throttle_up_text;
	@FXML private Text plane_throttle_down_text;
	@FXML private Text plane_drop_playload_text;
	@FXML private Text move_helicopter_up_text;
	
	@FXML private Text mouse_controls_info;
	
	@FXML private ImageView hide_image;
	@FXML private ImageView close_image;
	
	@FXML private ImageView save_icon;
	
	public void loadmc() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(settings.getOptionsFile()));
			String line = bufferedReader.readLine();
			
		    while(line != null) {
		    	String[] brut = line.split(":");
		    	if(brut.length>1) {
			    	String[] parts = brut[1].split(Pattern.quote("."));
			    	if(parts.length>0) {
			    		if(brut[0].equals("key_key.forward")) {walk_forwards_field.setPromptText(parts[parts.length-1].toUpperCase());}
			    		if(brut[0].contains("key_key.back")) walk_backwards_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.left")) strafe_left_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.right")) strafe_right_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.sprint")) sprint_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.sneak")) sneak_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.jump")) jump_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.attack")) attack_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.use")) use_item_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.drop")) drop_item_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		//if(brut[0].contains("key_key.walk-slowly")) walk_slowly_field.setPromptText(parts[parts.length-1].toUpperCase());
						if(brut[0].contains("key_key.carry.desc")) carry_on_field.setPromptText(parts[parts.length-1].toUpperCase());
						if(brut[0].contains("key_keybind.sophisticatedbackpacks.open_backpack")) open_backpack_field.setPromptText(parts[parts.length-1].toUpperCase());
						if(brut[0].contains("key_key.car_gui")) open_vehicle_gui_field.setPromptText(parts[parts.length-1].toUpperCase());
						if(brut[0].contains("key_key.car_start")) start_car_engine_field.setPromptText(parts[parts.length-1].toUpperCase());
						if(brut[0].contains("key_key.plane_throttle_up.desc")) plane_throttle_up_field.setPromptText(parts[parts.length-1].toUpperCase());
						if(brut[0].contains("key_key.plane_throttle_down.desc")) plane_throttle_down_field.setPromptText(parts[parts.length-1].toUpperCase());
						if(brut[0].contains("key_key.plane_drop_payload.desc")) plane_drop_playload_field.setPromptText(parts[parts.length-1].toUpperCase());
						if(brut[0].contains("key_key.move_heli_up.desc")) move_helicopter_up_field.setPromptText(parts[parts.length-1].toUpperCase());
						if(brut[0].contains("key_key.tacz.reload.desc")) reload_field.setPromptText(parts[parts.length-1].toUpperCase());
						//if(brut[0].contains("key_key.flashlight")) flashlight_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_of.key.zoom")) zoom_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.inventory")) open_inventory_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.hotbar.1")) hotbar_one_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.hotbar.2")) hotbar_two_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.tacz.inspect.desc")) inspect_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.tacz.fire_select.desc")) select_firing_mode_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.tacz.melee.desc")) weapon_cross_hit_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.tacz.refit.desc")) weapon_attachements_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.tacz.zoom.desc")) weapon_zoom_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("wear_backpack_field")) wear_backpack_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_key.chat")) open_chat_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		//if(brut[0].contains("key_key.vocal")) enable_vocal_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_gui.xaero_open_map")) show_map_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		if(brut[0].contains("key_gui.xaero_open_setting")) map_settings_field.setPromptText(parts[parts.length-1].toUpperCase());
			    		}
			    	if(brut[0].equals("mouseSensitivity")) {mouse_sensitivity_slider.setValue((int)(Float.parseFloat(brut[1])*100));mouse_sensitivity_text.setText((int)(Float.parseFloat(brut[1])*100)+"%");}
		    	}
		        line = bufferedReader.readLine();
		    }
			bufferedReader.close();
		}catch (IOException e) {
			e.printStackTrace();
			logger.log(e);
		}
	}
	
	public void loadof() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(settings.getOptionsOfFile()));
			String line = bufferedReader.readLine();
			
		    while(line != null) {
		    	String[] brut = line.split(":");
		    	if(brut.length>1) {
			    	String[] parts = brut[1].split(".");
			    	if(parts.length>0) {
			    		if(brut[0].contains("key_of.key.zoom")) zoom_field.setText(parts[parts.length-1]);
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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> {
			
	    	Translater translater = new Translater(settings.getProfile().getLang());
	    	
	    	mouvement_text.setText(translater.mouvement());
	    	vehicles_text.setText(translater.vehicles());
	    	combat_text.setText(translater.combat());
	    	communication_text.setText(translater.communication());
	    	walk_forwards_text.setText(translater.walk_forwards());
	    	walk_backwards_text.setText(translater.walk_backwards());
	    	strafe_left_text.setText(translater.strafe_left());
	    	strafe_right_text.setText(translater.strage_right());
	    	sprint_text.setText(translater.sprint());
	    	sneak_text.setText(translater.sneak());
	    	jump_text.setText(translater.jump());
	    	attack_text.setText(translater.attack());
	    	use_item_text.setText(translater.use_item());
	    	drop_item_text.setText(translater.drop_item());
	    	walk_slowly_text.setText(translater.walk_slowly());
	    	carry_on_text.setText(translater.carry_on());
	    	open_backpack_text.setText(translater.open_backpack());
	    	reload_text.setText(translater.reload());
	    	flashlight.setText(translater.flashlight());
	    	zoom_text.setText(translater.zoom());
	    	open_inventory_text.setText(translater.open_inventory());
	    	hotbar_one_text.setText(translater.hotbar_one());
	    	hotbar_two_text.setText(translater.hotbar_two());
	    	inspect_text.setText(translater.inspect());
	    	sensitivity_header_text.setText(translater.sensitivity());
	    	select_firing_mode_text.setText(translater.select_firing_mode());
	    	weapon_cross_hit_text.setText(translater.weapon_cross_hit());
	    	weapon_attachements_text.setText(translater.weapon_attachements_menu());
	    	weapon_zoom_text.setText(translater.weapon_zoom());
	    	wear_backpack_text.setText(translater.wear_backpack());
	    	open_chat_text.setText(translater.open_chat());
	    	enable_vocal_text.setText(translater.enable_vocal());
	    	show_map_text.setText(translater.show_map());
	    	map_settings_text.setText(translater.map_settings());
	    	open_vehicle_gui_text.setText(translater.open_vehicle_gui());
	    	start_car_engine_text.setText(translater.start_car_engine());
	    	plane_throttle_up_text.setText(translater.plane_throttle_up());
	    	plane_throttle_down_text.setText(translater.plane_throttle_down());
	    	plane_drop_playload_text.setText(translater.plane_drop_playload());
	    	move_helicopter_up_text.setText(translater.move_helicopter_up());
	    	
	    	mouse_controls_info.setText(translater.mouse_controls_info());
	    	
			loadmc();
			loadof();
		});
	}
	
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
	
	public void audio(ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		settings.audio(stage);
	}
	
	public void launcher(ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		settings.launcher(stage);
	}
	
	@FXML
    public void key_field_pressed(KeyEvent event) {
		
		TextField field = (TextField)event.getSource();
		field.selectEnd();
		field.deselect();
		Platform.runLater(() -> {
			
			String key = event.getCode().getName();
			String specific = "";
			
			if(event.getCode().isKeypadKey()) specific = "keypad";
			if(event.getCode().getName().equals("Num Lock")) specific = "num.lock";
			if(event.getCode().getName().contains("Numpad")) key.replaceAll("Numpad ", "");
			if(event.getCode().getName().contains("Alt Graph")) key.replaceAll("Alt Graph", "Alt");specific = "right";
			if(event.getCode() == KeyCode.ALT) specific = "left";
			if(event.getCode() == KeyCode.ESCAPE) key.replaceAll("Esc", "Escape");
			
			if(event.getCode() == KeyCode.SHIFT) specific = "left";
			if(event.getCode() == KeyCode.CONTROL) specific = "left";
						
			field.setText(event.getCode().getName());
			String id = ((Control)event.getSource()).getId();
			if(id.equals("walk_forwards_field")) settings.add("key_key.forward", "key.keyboard."+specific+"."+key.toLowerCase());
			if(id.equals("walk_backwards_field")) settings.add("key_key.back", "key.keyboard."+key.toLowerCase());
			if(id.equals("strafe_left_field")) settings.add("key_key.left", "key.keyboard."+key.toLowerCase());
			if(id.equals("strafe_right_field")) settings.add("key_key.right", "key.keyboard."+key.toLowerCase());
			if(id.equals("sprint_field")) settings.add("key_key.sprint", "key.keyboard."+key.toLowerCase());
			if(id.equals("sneak_field")) settings.add("key_key.sneak", "key.keyboard."+key.toLowerCase());
			if(id.equals("jump_field")) settings.add("key_key.jump", "key.keyboard."+key.toLowerCase());
			if(id.equals("attack_field")) settings.add("key_key.attack", "key.keyboard."+key.toLowerCase());
			if(id.equals("use_item_field")) settings.add("key_key.use", "key.keyboard."+key.toLowerCase());
			if(id.equals("drop_item_field")) settings.add("key_key.drop", "key.keyboard."+key.toLowerCase());
			//if(id.equals("walk_slowly_field")) settings.add("", "key.keyboard."+key.toLowerCase());
			if(id.equals("carry_field")) settings.add("key_key.carry.desc", "key.keyboard."+key.toLowerCase());
			if(id.equals("open_backpack_field")) settings.add("key_keybind.sophisticatedbackpacks.open_backpack", "key.keyboard."+key.toLowerCase());
			if(id.equals("open_vehicle_gui_field")) {settings.add("key_key.car_gui", "key.keyboard."+key.toLowerCase());settings.add("key_key.plane_inventory_open.des", "key.keyboard."+key.toLowerCase());}
			if(id.equals("start_car_engine_field")) settings.add("key_key.car_start", "key.keyboard."+key.toLowerCase());
			if(id.equals("plane_throttle_up_field")) settings.add("key_key.plane_throttle_up.desc", "key.keyboard."+key.toLowerCase());
			if(id.equals("plane_throttle_down_field")) settings.add("key_key.plane_throttle_down.desc", "key.keyboard."+key.toLowerCase());
			if(id.equals("plane_drop_playload_field")) settings.add("key_key.plane_drop_payload.desc", "key.keyboard."+key.toLowerCase());
			if(id.equals("move_helicopter_up_field")) settings.add("key_key.move_heli_up.desc", "key.keyboard."+key.toLowerCase());
			if(id.equals("reload_field")) settings.add("key_key.tacz.reload.desc", "key.keyboard."+key.toLowerCase());
			//if(id.equals("flashlight_field")) settings.add("key_key.right", "key.keyboard."+key.toLowerCase());
			if(id.equals("zoom_field")) settings.add("key_of.key.zoom", "key.keyboard."+key.toLowerCase());
			if(id.equals("open_inventory_field")) settings.add("key_key.inventory", "key.keyboard."+key.toLowerCase());
			if(id.equals("hotbar_one_field")) settings.add("key_key.hotbar.1", "key.keyboard."+key.toLowerCase());
			if(id.equals("hotbar_two_field")) settings.add("key_key.hotbar.2", "key.keyboard."+key.toLowerCase());
			if(id.equals("inspect_field")) settings.add("key_key.tacz.inspect.desc", "key.keyboard."+key.toLowerCase());
			if(id.equals("select_firing_mode_field")) settings.add("key_key.tacz.fire_select.desc", "key.keyboard."+key.toLowerCase());
			if(id.equals("weapon_cross_hit_field")) settings.add("key_key.tacz.melee.desc", "key.keyboard."+key.toLowerCase());
			if(id.equals("weapon_attachements_field")) settings.add("key_key.tacz.refit.desc", "key.keyboard."+key.toLowerCase());
			if(id.equals("weapon_zoom_field")) settings.add("key_key.tacz.zoom.desc", "key.keyboard."+key.toLowerCase());
			if(id.equals("wear_backpack_field")) settings.add("key_key.curios.open.desc", "key.keyboard."+key.toLowerCase());
			if(id.equals("open_chat_field")) settings.add("key_key.chat", "key.keyboard."+key.toLowerCase());
			//if(id.equals("enable_vocal_field")) settings.add("key_key.right", "key.keyboard."+key.toLowerCase());
			if(id.equals("show_map_field")) settings.add("key_gui.xaero_open_map", "key.keyboard."+key.toLowerCase());
			if(id.equals("map_settings_field")) settings.add("key_gui.xaero_open_setting", "key.keyboard."+key.toLowerCase());
		});
	}
	
	public void update_mouse_sensitivity(MouseEvent event) {settings.add("mouseSensitivity", String.valueOf(mouse_sensitivity_slider.getValue()/100));mouse_sensitivity_text.setText(String.valueOf((int)mouse_sensitivity_slider.getValue())+"%");}
	
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