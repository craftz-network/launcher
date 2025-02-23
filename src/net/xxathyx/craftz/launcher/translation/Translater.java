package net.xxathyx.craftz.launcher.translation;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;

import net.xxathyx.craftz.launcher.Main;
import net.xxathyx.craftz.launcher.configuration.FileConfiguration;
import net.xxathyx.craftz.launcher.logging.Logger;

public class Translater {
	
	private static final Logger logger = Main.logger;
	
	private File translationFile;
	
	public Translater() {}
	
	public Translater(String countryCode) {
    	translationFile = new File(net.xxathyx.craftz.launcher.util.System.getUpdaterFolder() + "/translations/", countryCode + ".lang");
	}
	
	public String getMessage(String id) {
		try {
			return new String(new FileConfiguration(translationFile).getString(id).getBytes(),"UTF-8");
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.log(e);
		}
		return "null";
	}
	
	public String invalid_username() {
		return getMessage("invalid-username");
	}
	
	public String invalid_password() {
		return getMessage("invalid-password");
	}
	
	public String login() {
		return getMessage("login");
	}
	
	public String login_choose() {
		return getMessage("login-choose");
	}
	
	public String signin_microsoft() {
		return getMessage("signin-microsoft");
	}
	
	public String signin_craftz() {
		return getMessage("signin-craftz");
	}
	
	public String no_account() {
		return getMessage("no-account");
	}
	
	public String signup_craftz() {
		return getMessage("signup-craftz");
	}
	
	public String signin_successful() {
		return getMessage("signin-successful");
	}
	
	public String login_failed() {
		return getMessage("login-failed");
	}
	
	public String username() {
		return getMessage("username");
	}
	
	public String password() {
		return getMessage("password");
	}
	
	public String forgot_password() {
		return getMessage("forgot-password");
	}
	
	public String log_in() {
		return getMessage("log-in");
	}
	
	public String play_header() {
		return getMessage("play-header");
	}
	
	public String store_header() {
		return getMessage("store-header");
	}
	
	public String wiki_header() {
		return getMessage("wiki-header");
	}
	
	public String welcoming() {
		return getMessage("welcoming");
	}
	
	public String seen_long_ago() {
		return getMessage("seen-long-ago");
	}
	
	public String profile() {
		return getMessage("profile");
	}
	
	public String settings() {
		return getMessage("settings");
	}
	
	public String community() {
		return getMessage("community");
	}
	
	public String help() {
		return getMessage("help");
	}
	
	public String news_title() {
		return getMessage("news-title");
	}
	
	public String news_description() {
		return getMessage("news-desciption");
	}
	
	public String play_button() {
		return getMessage("play-button");
	}
	
	public String graphics() {
		return getMessage("graphics");
	}
	
	public String controls() {
		return getMessage("controls");
	}
	
	public String audio() {
		return getMessage("audio");
	}
	
	public String launcher() {
		return getMessage("launcher");
	}
	
	public String save() {
		return getMessage("save");
	}
	
	public String discard() {
		return getMessage("discard");
	}
	
	public String general() {
		return getMessage("general");
	}
	
	public String textures() {
		return getMessage("textures");
	}
	
	public String brightness() {
		return getMessage("brightness");
	}
	
	public String render_distance() {
		return getMessage("render-distance");
	}
	
	public String render_entity() {
		return getMessage("render-entity");
	}
	
	public String gui_scale() {
		return getMessage("gui-scale");
	}
	
	public String details() {
		return getMessage("details");
	}
	
	public String clouds() {
		return getMessage("clouds");
	}
	
	public String trees_leaves() {
		return getMessage("trees-leaves");
	}
	
	public String sky() {
		return getMessage("sky");
	}
	
	public String sun_and_moon() {
		return getMessage("sun-and-moon");
	}
	
	public String fog() {
		return getMessage("fog");
	}
	
	public String fog_start() {
		return getMessage("fog-start");
	}
	
	public String cloud_height() {
		return getMessage("cloud-height");
	}
	
	public String rain_and_snow() {
		return getMessage("rain-and-snow");
	}
	
	public String stars() {
		return getMessage("stars");
	}
	
	public String swamp_colors() {
		return getMessage("swamp-colors");
	}
	
	public String entity_shadow() {
		return getMessage("entity-shadow");
	}
	
	public String quality() {
		return getMessage("quality");
	}
	
	public String dynamic_fov() {
		return getMessage("dynamic-fov");
	}
	
	public String fov() {
		return getMessage("fov");
	}
	
	public String better_snow() {
		return getMessage("better-snow");
	}
	
	public String animations() {
		return getMessage("animations");
	}
	
	public String water() {
		return getMessage("water");
	}
	
	public String fire() {
		return getMessage("fire");
	}
	
	public String flame() {
		return getMessage("flame");
	}
	
	public String rain_splash() {
		return getMessage("rain-splash");
	}
	
	public String terrain() {
		return getMessage("terrain");
	}
	
	public String firework() {
		return getMessage("firework");
	}
	
	public String lava() {
		return getMessage("lava");
	}
	
	public String explosion() {
		return getMessage("explosion");
	}
	
	public String smoke() {
		return getMessage("smoke");
	}
	
	public String dripping_water() {
		return getMessage("dripping-water");
	}
	
	public String mouvement() {
		return getMessage("mouvement");
	}
	
	public String walk_forwards() {
		return getMessage("walk-forwards");
	}
	
	public String walk_backwards() {
		return getMessage("walk-backwards");
	}
	
	public String strafe_left() {
		return getMessage("strafe-left");
	}
	
	public String strage_right() {
		return getMessage("strage-right");
	}
	
	public String sprint() {
		return getMessage("sprint");
	}
	
	public String sneak() {
		return getMessage("sneak");
	}
	
	public String jump() {
		return getMessage("jump");
	}
	
	public String attack() {
		return getMessage("attack");
	}
	
	public String use_item() {
		return getMessage("use-item");
	}
	
	public String drop_item() {
		return getMessage("drop-item");
	}
	
	public String walk_slowly() {
		return getMessage("walk-slowly");
	}
	
	public String combat() {
		return getMessage("combat");
	}
	
	public String reload() {
		return getMessage("reload");
	}
	
	public String flashlight() {
		return getMessage("flashlight");
	}
	
	public String zoom() {
		return getMessage("zoom");
	}
	
	public String open_inventory() {
		return getMessage("open-inventory");
	}
	
	public String hotbar_one() {
		return getMessage("hotbar-one");
	}
	
	public String hotbar_two() {
		return getMessage("hotbar-two");
	}
	
	public String inspect() {
		return getMessage("inspect");
	}
	
	public String sensitivity() {
		return getMessage("sensitivity");
	}
	
	public String communication() {
		return getMessage("communication");
	}
	
	public String open_chat() {
		return getMessage("open-chat");
	}
	
	public String enable_vocal() {
		return getMessage("enable-vocal");
	}
	
	public String show_map() {
		return getMessage("show-map");
	}
	
	public String music() {
		return getMessage("music");
	}
	
	public String main_volume() {
		return getMessage("main-volume");
	}
	
	public String sounds() {
		return getMessage("sounds");
	}
	
	public String weather() {
		return getMessage("weather");
	}
	
	public String hostile_entities() {
		return getMessage("hostile-entities");
	}
	
	public String players() {
		return getMessage("players");
	}
	
	public String blocks() {
		return getMessage("blocks");
	}
	
	public String friendly_entities() {
		return getMessage("friendly-entities");
	}
	
	public String ambient() {
		return getMessage("ambient");
	}
	
	public String vocal_chat() {
		return getMessage("vocal-chat");
	}
	
	public String launcher_parameters() {
		return getMessage("launcher-parameters");
	}
	
	public String language() {
		return getMessage("language");
	}
	
	public String ranks() {
		return getMessage("ranks");
	}
	
	public String exclusives() {
		return getMessage("exclusives");
	}
	
	public String cosmetics() {
		return getMessage("cosmetics");
	}
	
	public String back() {
		return getMessage("back");
	}
	
	public String buy() {
		return getMessage("buy");
	}
	
	public String month() {
		return getMessage("month");
	}
	
	public String permanently() {
		return getMessage("permanently");
	}
	
	public String mouse_controls_info() {
		return getMessage("mouse-controls-info");
	}
	
	public String happy_login() {
		return getMessage("happy-login");
	}
	
	public String close_seen() {
		return getMessage("close-seen");
	}
	
	public String none_of_them() {
		return getMessage("none-of-them");
	}
	
	public String carry_on() {
		return getMessage("carry-on");
	}
	
	public String open_backpack() {
		return getMessage("open-backpack");
	}
	
	public String vehicles() {
		return getMessage("vehicles");
	}
	
	public String open_vehicle_gui() {
		return getMessage("open-vehicle-gui");
	}
	
	public String start_car_engine() {
		return getMessage("start-car-engine");
	}
	
	public String plane_throttle_up() {
		return getMessage("plane-throttle-up");
	}
	
	public String plane_throttle_down() {
		return getMessage("plane-throttle-down");
	}
	
	public String plane_drop_playload() {
		return getMessage("plane-drop-playload");
	}
	
	public String move_helicopter_up() {
		return getMessage("move-helicopter-up");
	}
	
	public String select_firing_mode() {
		return getMessage("select-firing-mode");
	}
	
	public String weapon_cross_hit() {
		return getMessage("weapon-cross-hit");
	}
	
	public String weapon_attachements_menu() {
		return getMessage("weapon-attachements-menu");
	}
	
	public String weapon_zoom() {
		return getMessage("weapon-zoom");
	}
	
	public String wear_backpack() {
		return getMessage("wear-backpack");
	}
	
	public String map_settings() {
		return getMessage("map-settings");
	}
	
    /**
     * Export the translation-file contained within the jar-file, onto the translations
     * folder, according to a country-code.
     *
     * @param langage The langage country-code to be export.
     */
	
    public void install(String countryCode) {
		
    	translationFile = new File(net.xxathyx.craftz.launcher.util.System.getUpdaterFolder() + "/translations/", countryCode + ".lang");
	    
		if(!translationFile.exists()) translationFile.getParentFile().mkdir();
		
		try {
			if(!translationFile.exists()) {
				
				URI uri = Translater.class.getResource("translations/" + countryCode + ".lang").toURI();

				if("jar".equals(uri.getScheme())) {
				    for(FileSystemProvider provider: FileSystemProvider.installedProviders()) {
				        if(provider.getScheme().equalsIgnoreCase("jar")) {
				            try {
				                provider.getFileSystem(uri);
				            }catch (FileSystemNotFoundException e) {
				                provider.newFileSystem(uri, Collections.emptyMap());
				    			logger.log(e);
				            }
				        }
				    }
				}
				Path source = Paths.get(uri);			
				Files.copy(source, translationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		}catch(IOException | URISyntaxException e) {
			e.printStackTrace();
			logger.log(e);
		}
    }
    
    public boolean isInstalled() {
    	return translationFile.exists();
    }
}