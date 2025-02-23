module launcher {
	
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;
	requires java.net.http;
	requires jdk.httpserver;
	requires javafx.graphics;
	requires java.desktop;
	requires gson;
	requires java.sql;
	requires javafx.base;
	requires javafx.swing;
	requires java.management;
	requires javafx.media;
	
	opens net.xxathyx.craftz.launcher.login to javafx.graphics, javafx.fxml;
	opens net.xxathyx.craftz.launcher.home to javafx.graphics, javafx.fxml;
	opens net.xxathyx.craftz.launcher.settings to javafx.graphics, javafx.fxml;
	opens net.xxathyx.craftz.launcher.profile to javafx.graphics, javafx.fxml;
}