package net.xxathyx.craftz.launcher.home;

import javafx.stage.Stage;

import net.xxathyx.craftz.launcher.display.Display;
import net.xxathyx.craftz.launcher.profile.Profile;

public class Home extends Stage {
	
    public int width = 1080;
    public int height = 720;
		
	public Home(Profile profile) {		
		Display display = new Display("CraftZ - Welcome", this, width, height, getClass().getResource("resources/home.fxml"), getClass().getResource("resources/style.css"), 0, 0, true, true, true, true);
		HomeController homeController = (HomeController) display.getController();
		homeController.setProfile(profile);
	}
}