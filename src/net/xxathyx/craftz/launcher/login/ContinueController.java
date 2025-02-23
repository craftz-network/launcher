package net.xxathyx.craftz.launcher.login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import net.xxathyx.craftz.launcher.display.Display;
import net.xxathyx.craftz.launcher.translation.Translater;

public class ContinueController implements Initializable {
	
    public int width = 610;
    public int height = 750;
	
	@FXML private Button back_button;
    
	@FXML private ImageView hide_image;
	@FXML private ImageView close_image;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Translater translater = new Translater(net.xxathyx.craftz.launcher.util.System.getCountryCode());
		back_button.setText(translater.none_of_them());
	}
	
	public void back(ActionEvent event) {
		new Display("CraftZ - Login", (Stage)((Node)event.getSource()).getScene().getWindow(), width, height, getClass().getResource("resources/login.fxml"), getClass().getResource("resources/style.css"), 0, 0, false, true, true, true);
	}
	
	public void enter_back(Event event) {back_button.setOpacity(1);}
	public void exit_back(Event event) {back_button.setOpacity(0.8);}
	
	public void enter_hide(Event event) {hide_image.setOpacity(1);}
	public void exit_hide(Event event) {hide_image.setOpacity(0.8);}
	
	public void enter_close(Event event) {close_image.setOpacity(1);}
	public void exit_close(Event event) {close_image.setOpacity(0.8);}
	
	public void hide(ActionEvent event) {
		((Stage)((Node)event.getSource()).getScene().getWindow()).setIconified(true);
	}
	
	public void close(ActionEvent event) {
		System.exit(0);
	}
}