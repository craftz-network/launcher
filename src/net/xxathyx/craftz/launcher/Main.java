package net.xxathyx.craftz.launcher;

import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import net.xxathyx.craftz.launcher.logging.Logger;

public class Main extends Application {
	
	public static final Logger logger = new Logger(new File(net.xxathyx.craftz.launcher.util.System.getLogsFolder(),
			(net.xxathyx.craftz.launcher.util.System.getLogsFolder().listFiles().length)+".log"));
	
	@Override
	public void start(Stage stage) {}
	
	public static void main(String[] args) {
		launch(args);
	}
}
