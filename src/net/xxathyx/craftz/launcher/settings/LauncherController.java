package net.xxathyx.craftz.launcher.settings;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import net.xxathyx.craftz.launcher.translation.Translater;

public class LauncherController implements Initializable {
	
	private Settings settings;
	
    public int width = 1080;
    public int height = 720;
	
	@FXML private ImageView hide_image;
	@FXML private ImageView close_image;
	
	@FXML private ImageView save_icon;
	
	@FXML private MenuButton language_menu;
	
	@FXML private Button on_telemetry_button;
	@FXML private Button off_telemetry_button;
	
	@FXML private Text launcher_parameters_text;
	@FXML private Text language_text;
	@FXML private Text telemetry_text;

	@FXML private TextField args_jvm_field;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> {
			
	    	Translater translater = new Translater(settings.getProfile().getLang());
	    	launcher_parameters_text.setText(translater.launcher_parameters());
			language_text.setText(translater.language());
	    	
			on_telemetry_button.setOpacity(settings.getProfile().getTelemetry() ? 1 : 0.5);
			off_telemetry_button.setOpacity(settings.getProfile().getTelemetry() ? 0.5 : 1);

			String language = "ENGLISH";
			if(settings.getProfile().getLang().equals("FR")) language = "FRENCH";
			language_menu.setText(language);
			args_jvm_field.setText(settings.getProfile().getArgsJVM());
		});
	}
	
	@FXML
    public void args_jvm_field_pressed(KeyEvent event) {
		
		TextField field = (TextField)event.getSource();
		
		KeyCode code = event.getCode();
        String numpadInput = "";

        if(code == KeyCode.NUMPAD0) numpadInput = "0";
        if(code == KeyCode.NUMPAD1) numpadInput = "1";
        if(code == KeyCode.NUMPAD2) numpadInput = "2";
        if(code == KeyCode.NUMPAD3) numpadInput = "3";
        if(code == KeyCode.NUMPAD4) numpadInput = "4";
        if(code == KeyCode.NUMPAD5) numpadInput = "5";
        if(code == KeyCode.NUMPAD6) numpadInput = "6";
        if(code == KeyCode.NUMPAD7) numpadInput = "7";
        if(code == KeyCode.NUMPAD8) numpadInput = "8";
        if(code == KeyCode.NUMPAD9) numpadInput = "9";
                
        if(!numpadInput.isEmpty()) {
        	event.consume();
        	field.insertText(field.getCaretPosition(), numpadInput);
        }
		settings.add("args-jvm", field.getText());
	}
	
	public void language_english(ActionEvent event) {settings.getProfile().setLang("EN");language_menu.setText("ENGLISH");}
	public void language_french(ActionEvent event) {settings.getProfile().setLang("FR");language_menu.setText("FRENCH");}
	
    public void on_telemetry(ActionEvent event) {settings.getProfile().setTelemetry(true);on_telemetry_button.setOpacity(1);off_telemetry_button.setOpacity(0.5);}
	public void off_telemetry(ActionEvent event) {settings.getProfile().setTelemetry(false);off_telemetry_button.setOpacity(1);on_telemetry_button.setOpacity(0.5);}
	
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
	
	public void audio(ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		settings.audio(stage);
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
		this.settings = settings;
	}
}