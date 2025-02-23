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
import javafx.scene.control.MenuButton;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.logging.Logger;
import net.xxathyx.craftz.launcher.translation.Translater;

public class GraphicsController implements Initializable {
	
	private static final Logger logger = Main.logger;
	
	private Settings settings;
	
    public int width = 1080;
    public int height = 720;
	
	@FXML private ImageView hide_image;
	@FXML private ImageView close_image;
	
	@FXML private ImageView save_icon;
	
	@FXML private MenuButton textures_menu;
	@FXML private MenuButton gui_scale_menu;
	@FXML private MenuButton clouds_menu;
	@FXML private MenuButton trees_leaves_menu;
	@FXML private MenuButton rain_and_snow_menu;
	@FXML private MenuButton filtrage_menu;
	@FXML private MenuButton antialiasing_menu;
	@FXML private MenuButton mipmap_menu;
	
	@FXML private Button on_sky_button;
	@FXML private Button on_sun_and_moon_button;
	@FXML private Button on_fog_button;
	@FXML private Button on_stars_button;
	@FXML private Button on_swamp_colors_button;
	@FXML private Button on_entity_shadow_button;
	@FXML private Button on_dynamic_fov_button;
	@FXML private Button on_better_snow_button;
	@FXML private Button on_water_button;
	@FXML private Button on_fire_button;
	@FXML private Button on_redstone_button;
	@FXML private Button on_flame_button;
	@FXML private Button on_rain_splash_button;
	@FXML private Button on_terrain_button;
	@FXML private Button on_firework_button;
	@FXML private Button on_lava_button;
	@FXML private Button on_explosion_button;
	@FXML private Button on_smoke_button;
	@FXML private Button on_dripping_water_button;
	
	@FXML private Button off_sky_button;
	@FXML private Button off_sun_and_moon_button;
	@FXML private Button off_fog_button;
	@FXML private Button off_stars_button;
	@FXML private Button off_swamp_colors_button;
	@FXML private Button off_entity_shadow_button;
	@FXML private Button off_dynamic_fov_button;
	@FXML private Button off_better_snow_button;
	@FXML private Button off_water_button;
	@FXML private Button off_fire_button;
	@FXML private Button off_redstone_button;
	@FXML private Button off_flame_button;
	@FXML private Button off_rain_splash_button;
	@FXML private Button off_terrain_button;
	@FXML private Button off_firework_button;
	@FXML private Button off_lava_button;
	@FXML private Button off_explosion_button;
	@FXML private Button off_smoke_button;
	@FXML private Button off_dripping_water_button;
	
	@FXML private Slider brightness_slider;
	@FXML private Slider max_fps_slider;
	@FXML private Slider render_distance_slider;
	@FXML private Slider render_entity_slider;
	@FXML private Slider fog_start_slider;
	@FXML private Slider cloud_height_slider;
	@FXML private Slider fov_slider;
	
	@FXML private Text brightness_text;
	@FXML private Text max_fps_text;
	@FXML private Text render_distance_text;
	@FXML private Text render_entity_text;
	@FXML private Text fog_start_text;
	@FXML private Text cloud_height_text;
	@FXML private Text fov_text;
	
	@FXML private Text general_text;
	@FXML private Text details_text;
	@FXML private Text quality_text;
	@FXML private Text animations_text;
	
	@FXML private Text textures_text;
	@FXML private Text brightness_header_text;
	@FXML private Text render_distance_header_text;
	@FXML private Text render_entity_header_text;
	@FXML private Text gui_scale_text;
	@FXML private Text clouds_text;
	@FXML private Text trees_leaves_text;
	@FXML private Text sky_text;
	@FXML private Text sun_and_moon_text;
	@FXML private Text fog_text;
	@FXML private Text fog_start_header_text;
	@FXML private Text cloud_height_header_text;
	@FXML private Text rain_and_snow_text;
	@FXML private Text stars_text;
	@FXML private Text swamp_colors_text;
	@FXML private Text entity_shadow_text;
	@FXML private Text dynamic_fov_text;
	@FXML private Text fov_header_text;
	@FXML private Text better_snow_text;
	@FXML private Text water_text;
	@FXML private Text fire_text;
	@FXML private Text flame_text;
	@FXML private Text rain_splash_text;
	@FXML private Text terrain_text;
	@FXML private Text firework_text;
	@FXML private Text lava_text;
	@FXML private Text explosion_text;
	@FXML private Text smoke_text;
	@FXML private Text dripping_water_text;
	
	public void loadmc() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(settings.getOptionsFile()));
			String line = bufferedReader.readLine();
			
		    while(line != null) {
		    	String[] brut = line.split(":");
		    	if(brut.length>1) {
			    	if(brut[0].contains("gamma")) {brightness_slider.setValue((int)(Float.parseFloat(brut[1])*100));brightness_text.setText((int)(Float.parseFloat(brut[1])*100)+"%");}
			    	if(brut[0].contains("maxFps")) {max_fps_slider.setValue(Integer.parseInt(brut[1]));max_fps_text.setText(brut[1]+" FPS");if(Integer.parseInt(brut[1])==260)max_fps_text.setText("NO LIMIT");}
			    	if(brut[0].contains("renderDistance")) {render_distance_slider.setValue(Integer.parseInt(brut[1]));render_distance_text.setText(brut[1]+" CHUNKS");}
			    	if(brut[0].contains("simulationDistance")) {render_entity_slider.setValue(Integer.parseInt(brut[1]));render_entity_text.setText(brut[1]+" CHUNKS");}
			    	if(brut[0].contains("ofFogStart")) {fog_start_slider.setValue(Double.parseDouble(brut[1]));fog_start_text.setText(brut[1]);}
			    	if(brut[0].equals("fov")) {fov_slider.setValue((int)(Float.parseFloat(brut[1])*110));fov_text.setText((int)(Float.parseFloat(brut[1])*110)+"°");}
			    	if(brut[0].contains("graphicsMode")) {if(brut[1].equals("0")) textures_menu.setText("LOW");if(brut[1].equals("1")) textures_menu.setText("MEDIUM");if(brut[1].equals("2")) textures_menu.setText("HIGH");}
			    	if(brut[0].contains("guiScale")) {if(brut[1].equals("1")) gui_scale_menu.setText("STANDARD");if(brut[1].equals("2")) gui_scale_menu.setText("SMALL");}
			    	if(brut[0].contains("entityShadows")) {on_entity_shadow_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_entity_shadow_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
			    	if(brut[0].contains("fovEffectScale")) {on_dynamic_fov_button.setOpacity(brut[1].equals("1.0") ? 1 : 0.5);off_dynamic_fov_button.setOpacity(brut[1].equals("0.0") ? 1 : 0.5);}
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
			    	if(brut[0].contains("ofFogStart")) {fog_start_slider.setValue(Double.parseDouble(brut[1]));fog_start_text.setText(brut[1]);}
			    	if(brut[0].contains("ofCloudsHeight")) {cloud_height_slider.setValue((int)(Float.parseFloat(brut[1])*255));cloud_height_text.setText(String.valueOf((int)(Float.parseFloat(brut[1])*255)));}
			    	if(brut[0].contains("ofClouds")) {if(brut[1].equals("0")) clouds_menu.setText("DEFAULT");if(brut[1].equals("1")) clouds_menu.setText("LOW");if(brut[1].equals("2")) clouds_menu.setText("DETAILED");if(brut[1].equals("3")) clouds_menu.setText("OFF");}
			    	if(brut[0].contains("ofTrees")) {if(brut[1].equals("0")) trees_leaves_menu.setText("DEFAULT");if(brut[1].equals("1")) trees_leaves_menu.setText("LOW");if(brut[1].equals("2")) trees_leaves_menu.setText("HIGH");if(brut[1].equals("4")) trees_leaves_menu.setText("MEDIUM");}
			    	if(brut[0].contains("ofRain")) {if(brut[1].equals("0")) rain_and_snow_menu.setText("DEFAULT"); else if(brut[1].equals("1")) rain_and_snow_menu.setText("LOW"); else if(brut[1].equals("2")) rain_and_snow_menu.setText("HIGH"); else if(brut[1].equals("3")) rain_and_snow_menu.setText("OFF");}
			    	if(brut[0].contains("ofAfLevel")) {if(brut[1].equals("1")) filtrage_menu.setText("OFF");if(brut[1].equals("2")) filtrage_menu.setText("2X");if(brut[1].equals("4")) filtrage_menu.setText("4X");if(brut[1].equals("8")) filtrage_menu.setText("8X");if(brut[1].equals("16")) filtrage_menu.setText("16X");}
			    	if(brut[0].contains("ofAaLevel")) {if(brut[1].equals("0")) antialiasing_menu.setText("OFF");if(brut[1].equals("2")) antialiasing_menu.setText("2X");if(brut[1].equals("4")) antialiasing_menu.setText("4X");if(brut[1].equals("8")) antialiasing_menu.setText("8X");if(brut[1].equals("16")) antialiasing_menu.setText("16X");}
			    	if(brut[0].contains("ofMipmapType")) {if(brut[1].equals("0")) mipmap_menu.setText("NEAREST");if(brut[1].equals("1")) mipmap_menu.setText("LINEAR");if(brut[1].equals("2")) mipmap_menu.setText("BILINEAR");if(brut[1].equals("3")) mipmap_menu.setText("TRILINEAR");}
			    	if(brut[0].contains("ofSky")) {on_sky_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_sky_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
			    	if(brut[0].contains("ofSunMoon")) {on_sun_and_moon_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_sun_and_moon_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
			    	if(brut[0].contains("ofStars")) {on_stars_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_stars_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
			    	if(brut[0].contains("ofSwampColors")) {on_swamp_colors_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_swamp_colors_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
			    	if(brut[0].contains("ofBetterSnow")) {on_better_snow_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_better_snow_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
			    	if(brut[0].contains("ofWaterParticles")) {on_water_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_water_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
			    	if(brut[0].contains("ofAnimatedFire")) {on_fire_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_fire_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
			    	if(brut[0].contains("ofAnimatedRedstone")) {on_redstone_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_redstone_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
			    	if(brut[0].contains("ofAnimatedFlame")) {on_flame_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_flame_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
			    	if(brut[0].contains("ofRainSplash")) {on_rain_splash_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_rain_splash_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
			    	if(brut[0].contains("ofAnimatedTerrain")) {on_terrain_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_terrain_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
			    	if(brut[0].contains("ofFireworkParticles")) {on_firework_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_firework_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
			    	if(brut[0].contains("ofAnimatedLava")) {on_lava_button.setOpacity(brut[1].equals("1") ? 1 : 0.5);off_lava_button.setOpacity(brut[1].equals("0") ? 1 : 0.5);}
			    	if(brut[0].contains("ofAnimatedExplosion")) {on_explosion_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_explosion_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
			    	if(brut[0].contains("ofAnimatedSmoke")) {on_smoke_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_smoke_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
			    	if(brut[0].contains("ofDrippingWaterLava")) {on_dripping_water_button.setOpacity(brut[1].equals("true") ? 1 : 0.5);off_dripping_water_button.setOpacity(brut[1].equals("false") ? 1 : 0.5);}
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
			
	    	general_text.setText(translater.general());
	    	details_text.setText(translater.details());
	    	quality_text.setText(translater.quality());
	    	animations_text.setText(translater.animations());
	    	textures_text.setText(translater.textures());
	    	brightness_header_text.setText(translater.brightness());
	    	render_distance_header_text.setText(translater.render_distance());
	    	render_entity_header_text.setText(translater.render_entity());
	    	gui_scale_text.setText(translater.gui_scale());
	    	clouds_text.setText(translater.clouds());
	    	trees_leaves_text.setText(translater.trees_leaves());
	    	sky_text.setText(translater.sky());
	    	sun_and_moon_text.setText(translater.sun_and_moon());
	    	fog_text.setText(translater.fog());
	    	fog_start_header_text.setText(translater.fog_start());
	    	cloud_height_header_text.setText(translater.cloud_height());
	    	rain_and_snow_text.setText(translater.rain_and_snow());
	    	stars_text.setText(translater.stars());
	    	swamp_colors_text.setText(translater.swamp_colors());
	    	entity_shadow_text.setText(translater.entity_shadow());
	    	dynamic_fov_text.setText(translater.dynamic_fov());
	    	fov_header_text.setText(translater.fov());
	    	better_snow_text.setText(translater.better_snow());
	    	water_text.setText(translater.water());
	    	fire_text.setText(translater.fire());
	    	flame_text.setText(translater.flame());
	    	rain_splash_text.setText(translater.rain_splash());
	    	terrain_text.setText(translater.terrain());
	    	firework_text.setText(translater.firework());
	    	lava_text.setText(translater.lava());
	    	firework_text.setText(translater.firework());
	    	explosion_text.setText(translater.explosion());
	    	smoke_text.setText(translater.smoke());
	    	dripping_water_text.setText(translater.dripping_water());
	    	
			loadmc();
			loadof();
		});
	}
	
	public void update_brightness(MouseEvent event) {settings.add("gamma", String.valueOf(brightness_slider.getValue()/100));brightness_text.setText(String.valueOf((int)brightness_slider.getValue())+"%");}
	public void update_max_fps(MouseEvent event) {settings.add("maxFps", String.valueOf((int)(max_fps_slider.getValue())));max_fps_text.setText(String.valueOf((int)max_fps_slider.getValue())+" FPS"); if((int)max_fps_slider.getValue()==260)max_fps_text.setText("NO LIMIT");}
	public void update_render_distance(MouseEvent event) {settings.add("renderDistance", String.valueOf((int)(render_distance_slider.getValue())));render_distance_text.setText(String.valueOf((int)render_distance_slider.getValue())+" CHUNKS");}
	public void update_render_entity(MouseEvent event) {settings.add("simulationDistance", String.valueOf((int)(render_entity_slider.getValue())));render_entity_text.setText(String.valueOf((int)render_entity_slider.getValue())+" CHUNKS");}
	public void update_fog_start(MouseEvent event) {settings.add("ofFogStart", String.valueOf((float)(fog_start_slider.getValue())));fog_start_text.setText(String.format("%1.1f", fog_start_slider.getValue()));}
	public void update_cloud_height(MouseEvent event) {settings.add("ofCloudsHeight", String.valueOf(cloud_height_slider.getValue()/255));cloud_height_text.setText(String.valueOf((int)cloud_height_slider.getValue()));}
	public void update_fov(MouseEvent event) {settings.add("fov", String.valueOf(fov_slider.getValue()/110));fov_text.setText(String.valueOf((int)fov_slider.getValue())+"°");}
	
	public void textures_low(ActionEvent event) {settings.add("graphicsMode", "0");textures_menu.setText("LOW");}
	public void textures_medium(ActionEvent event) {settings.add("graphicsMode", "1");textures_menu.setText("MEDIUM");}
	public void textures_high(ActionEvent event) {settings.add("graphicsMode", "2");textures_menu.setText("HIGH");}
	
	public void gui_scale_standard(ActionEvent event) {settings.add("guiScale", "1");gui_scale_menu.setText("STANDARD");}
	public void gui_scale_small(ActionEvent event) {settings.add("guiScale", "2");gui_scale_menu.setText("SMALL");}
	
	public void clouds_off(ActionEvent event) {settings.add("ofClouds", "3");clouds_menu.setText("OFF");}
	public void clouds_low(ActionEvent event) {settings.add("ofClouds", "1");clouds_menu.setText("LOW");}
	public void clouds_default(ActionEvent event) {settings.add("ofClouds", "0");clouds_menu.setText("DEFAULT");}
	public void clouds_detailed(ActionEvent event) {settings.add("ofClouds", "2");clouds_menu.setText("DETAILED");}

	public void trees_leaves_low(ActionEvent event) {settings.add("ofTrees", "1");trees_leaves_menu.setText("LOW");}
	public void trees_leaves_default(ActionEvent event) {settings.add("ofTrees", "0");trees_leaves_menu.setText("DEFAULT");}
	public void trees_leaves_medium(ActionEvent event) {settings.add("ofTrees", "4");trees_leaves_menu.setText("MEDIUM");}
	public void trees_leaves_high(ActionEvent event) {settings.add("ofTrees", "2");trees_leaves_menu.setText("HIGH");}

	public void rain_and_snow_off(ActionEvent event) {settings.add("ofRain", "3");rain_and_snow_menu.setText("OFF");}
	public void rain_and_snow_low(ActionEvent event) {settings.add("ofRain", "1");rain_and_snow_menu.setText("LOW");}
	public void rain_and_snow_default(ActionEvent event) {settings.add("ofRain", "0");rain_and_snow_menu.setText("DEFAULT");}
	public void rain_and_snow_high(ActionEvent event) {settings.add("ofRain", "2");rain_and_snow_menu.setText("HIGH");}

	public void filtrage_off(ActionEvent event) {settings.add("ofAfLevel", "1");filtrage_menu.setText("OFF");}
	public void filtrage_2x(ActionEvent event) {settings.add("ofAfLevel", "2");filtrage_menu.setText("2x");}
	public void filtrage_4x(ActionEvent event) {settings.add("ofAfLevel", "4");filtrage_menu.setText("4x");}
	public void filtrage_8x(ActionEvent event) {settings.add("ofAfLevel", "8");filtrage_menu.setText("8x");}
	public void filtrage_16x(ActionEvent event) {settings.add("ofAfLevel", "16");filtrage_menu.setText("16x");}
	
	public void antialiasing_off(ActionEvent event) {settings.add("ofAaLevel", "0");antialiasing_menu.setText("OFF");}
	public void antialiasing_2x(ActionEvent event) {settings.add("ofAaLevel", "2");antialiasing_menu.setText("2x");}
	public void antialiasing_4x(ActionEvent event) {settings.add("ofAaLevel", "4");antialiasing_menu.setText("4x");}
	public void antialiasing_8x(ActionEvent event) {settings.add("ofAaLevel", "8");antialiasing_menu.setText("8x");}
	public void antialiasing_16x(ActionEvent event) {settings.add("ofAaLevel", "16");antialiasing_menu.setText("16x");}
	
	public void mipmap_nearest(ActionEvent event) {settings.add("ofMipmapType", "0");mipmap_menu.setText("NEAREST");}
	public void mipmap_linear(ActionEvent event) {settings.add("ofMipmapType", "1");mipmap_menu.setText("LINEAR");}
	public void mipmap_bilinear(ActionEvent event) {settings.add("ofMipmapType", "2");mipmap_menu.setText("BILINEAR");}
	public void mipmap_trilinear(ActionEvent event) {settings.add("ofMipmapType", "3");mipmap_menu.setText("TRILINEAR");}
	
	public void on_sky(ActionEvent event) {settings.add("ofSky", "true");on_sky_button.setOpacity(1);off_sky_button.setOpacity(0.5);}
	public void on_sun_and_moon(ActionEvent event) {settings.add("ofSunMoon", "true");on_sun_and_moon_button.setOpacity(1);off_sun_and_moon_button.setOpacity(0.5);}
	public void on_fog_button(ActionEvent event) {on_fog_button.setOpacity(1);off_fog_button.setOpacity(0.5);}
	public void on_stars(ActionEvent event) {settings.add("ofStars", "true");on_stars_button.setOpacity(1);off_stars_button.setOpacity(0.5);}
	public void on_swamp_colors(ActionEvent event) {settings.add("ofSwampColors", "true");on_swamp_colors_button.setOpacity(1);off_swamp_colors_button.setOpacity(0.5);}
	public void on_entity_shadow(ActionEvent event) {settings.add("entityShadows", "true");on_entity_shadow_button.setOpacity(1);off_entity_shadow_button.setOpacity(0.5);}
	public void on_dynamic_fov(ActionEvent event) {settings.add("fovEffectScale", "1.0");on_dynamic_fov_button.setOpacity(1);off_dynamic_fov_button.setOpacity(0.5);}
	public void on_better_snow(ActionEvent event) {settings.add("ofBetterSnow", "true");on_better_snow_button.setOpacity(1);off_better_snow_button.setOpacity(0.5);}
	public void on_water(ActionEvent event) {settings.add("ofWaterParticles", "true");on_water_button.setOpacity(1);off_water_button.setOpacity(0.5);}
	public void on_fire(ActionEvent event) {settings.add("ofAnimatedFire", "true");on_fire_button.setOpacity(1);off_fire_button.setOpacity(0.5);}
	public void on_redstone(ActionEvent event) {settings.add("ofAnimatedRedstone", "true");on_redstone_button.setOpacity(1);off_redstone_button.setOpacity(0.5);}
	public void on_flame(ActionEvent event) {settings.add("ofAnimatedFlame", "true");on_flame_button.setOpacity(1);off_flame_button.setOpacity(0.5);}
	public void on_rain_splash(ActionEvent event) {settings.add("ofRainSplash", "true");on_rain_splash_button.setOpacity(1);off_rain_splash_button.setOpacity(0.5);}
	public void on_terrain(ActionEvent event) {settings.add("ofAnimatedTerrain", "true");on_terrain_button.setOpacity(1);off_terrain_button.setOpacity(0.5);}
	public void on_firework(ActionEvent event) {settings.add("ofFireworkParticles", "true");on_firework_button.setOpacity(1);off_firework_button.setOpacity(0.5);}
	public void on_lava(ActionEvent event) {settings.add("ofAnimatedLava", "1");on_lava_button.setOpacity(1);off_lava_button.setOpacity(0.5);}
	public void on_explosion(ActionEvent event) {settings.add("ofAnimatedExplosion", "true");on_explosion_button.setOpacity(1);off_explosion_button.setOpacity(0.5);}
	public void on_smoke(ActionEvent event) {settings.add("ofAnimatedSmoke", "true");on_smoke_button.setOpacity(1);off_smoke_button.setOpacity(0.5);}
	public void on_dripping_water(ActionEvent event) {settings.add("ofDrippingWaterLava", "true");on_dripping_water_button.setOpacity(1);off_dripping_water_button.setOpacity(0.5);}
	
	public void off_sky(ActionEvent event) {settings.add("ofSky", "false");on_sky_button.setOpacity(0.5);off_sky_button.setOpacity(1);}
	public void off_sun_and_moon(ActionEvent event) {settings.add("ofSunMoon", "false");on_sun_and_moon_button.setOpacity(0.5);off_sun_and_moon_button.setOpacity(1);}
	public void off_fog(ActionEvent event) {on_fog_button.setOpacity(0.5);off_fog_button.setOpacity(1);}
	public void off_stars(ActionEvent event) {settings.add("ofStars", "false");on_stars_button.setOpacity(0.5);off_stars_button.setOpacity(1);}
	public void off_swamp(ActionEvent event) {settings.add("ofSwampColors", "false");on_swamp_colors_button.setOpacity(0.5);off_swamp_colors_button.setOpacity(1);}
	public void off_entity_shadow(ActionEvent event) {settings.add("entityShadows", "false");on_entity_shadow_button.setOpacity(0.5);off_entity_shadow_button.setOpacity(1);}
	public void off_dynamic_fov(ActionEvent event) {settings.add("fovEffectScale", "0.0");on_dynamic_fov_button.setOpacity(0.5);off_dynamic_fov_button.setOpacity(1);}
	public void off_better_snow(ActionEvent event) {settings.add("ofBetterSnow", "false");on_better_snow_button.setOpacity(0.5);off_better_snow_button.setOpacity(1);}
	public void off_water(ActionEvent event) {settings.add("ofWaterParticles", "false");on_water_button.setOpacity(0.5);off_water_button.setOpacity(1);}
	public void off_fire(ActionEvent event) {settings.add("ofAnimatedFire", "false");on_fire_button.setOpacity(0.5);off_fire_button.setOpacity(1);}
	public void off_redstone(ActionEvent event) {settings.add("ofAnimatedRedstone", "false");on_redstone_button.setOpacity(0.5);off_redstone_button.setOpacity(1);}
	public void off_flame(ActionEvent event) {settings.add("ofAnimatedFlame", "false");on_flame_button.setOpacity(0.5);off_flame_button.setOpacity(1);}
	public void off_rain_splash(ActionEvent event) {settings.add("ofRainSplash", "false");on_rain_splash_button.setOpacity(0.5);off_rain_splash_button.setOpacity(1);}
	public void off_terrain(ActionEvent event) {settings.add("ofAnimatedTerrain", "false");on_terrain_button.setOpacity(0.5);off_terrain_button.setOpacity(1);}
	public void off_firework(ActionEvent event) {settings.add("ofFireworkParticles", "false");on_firework_button.setOpacity(0.5);off_firework_button.setOpacity(1);}
	public void off_lava(ActionEvent event) {settings.add("ofAnimatedLava", "0");on_lava_button.setOpacity(0.5);off_lava_button.setOpacity(1);}
	public void off_explosion(ActionEvent event) {settings.add("ofAnimatedExplosion", "false");on_explosion_button.setOpacity(0.5);off_explosion_button.setOpacity(1);}
	public void off_smoke(ActionEvent event) {settings.add("ofAnimatedSmoke", "false");on_smoke_button.setOpacity(0.5);off_smoke_button.setOpacity(1);}
	public void off_dripping_water(ActionEvent event) {settings.add("ofDrippingWaterLava", "false");on_dripping_water_button.setOpacity(0.5);off_dripping_water_button.setOpacity(1);}
	
	public void enter_subcategory(Event event) {((Button)event.getSource()).setOpacity(1);}
	public void exit_subcategory(Event event) {((Button)event.getSource()).setOpacity(0.8);}
	
	public void enter_save(Event event) {((Button)event.getSource()).setOpacity(1); save_icon.setOpacity(1);}
	public void exit_save(Event event) {((Button)event.getSource()).setOpacity(0.8); save_icon.setOpacity(0.8);}
	
	public void enter_hide(Event event) {hide_image.setOpacity(1);}
	public void exit_hide(Event event) {hide_image.setOpacity(0.8);}
	
	public void enter_close(Event event) {close_image.setOpacity(1);}
	public void exit_close(Event event) {close_image.setOpacity(0.8);}
	
	public void controls(ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		settings.controls(stage);
	}
	
	public void audio(ActionEvent event) {
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		settings.audio(stage);
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