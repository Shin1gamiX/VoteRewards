package com.GeorgeV22.VoteRewards.Utilities.Config;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager extends YamlConfiguration {

	private File file;
	private String defaults;
	private JavaPlugin plugin;

	public ConfigManager(JavaPlugin plugin, String fileName) {
		this(plugin, fileName, null);
	}

	public ConfigManager(JavaPlugin plugin, String fileName, String defaultsName) {
		this.plugin = plugin;
		this.defaults = defaultsName;
		this.file = new File(plugin.getDataFolder(), fileName);
		reload();
	}

	public void reload() {

		if (!file.exists()) {
			try {

				file.getParentFile().mkdirs();
				file.createNewFile();
				load(file);
				if (defaults != null) {
					/*
					 * InputStreamReader reader = new
					 * InputStreamReader(plugin.getResource(defaults)); FileConfiguration
					 * defaultsConfig = YamlConfiguration.loadConfiguration(reader);
					 * 
					 * setDefaults(defaultsConfig); options().copyDefaults(true); reader.close();
					 * 
					 * save();
					 */
					plugin.saveResource(defaults, true);
				}
			} catch (Exception exception) {
				exception.printStackTrace();
				plugin.getLogger().severe("Error while creating file " + file.getName());
			}

		}
		try {
			load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public FileConfiguration getDefault() {
		InputStreamReader reader = new InputStreamReader(plugin.getResource(defaults));
		FileConfiguration defaultsConfig = YamlConfiguration.loadConfiguration(reader);
		return defaultsConfig;
	}

	public void checkPath(String path) {
		if (get(path) == null) {
			if (path.contains("Rewards"))
				return;
			set(path, getDefault().get(path));
			save();
		}
	}

	public void save() {
		try {
			save(file);
		} catch (IOException exception) {
			exception.printStackTrace();
			plugin.getLogger().severe("Error while saving file " + file.getName());
		}

	}

}
