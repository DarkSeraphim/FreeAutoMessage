package com.j0ach1mmall3.freeautomessage.api.internal.storage.yaml;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by j0ach1mmall3 on 8:55 29/06/2015 using IntelliJ IDEA.
 */
public class Config {
    private JavaPlugin plugin;
    private String name;

    public Config(String name, JavaPlugin plugin){
        this.plugin = plugin;
        this.name = name;
    }

    public FileConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), name));
    }

    public void saveConfig(FileConfiguration config) {
        try {
            config.save(new File(plugin.getDataFolder(), name));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        if(plugin.getResource(name) != null){
            FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), name));
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(plugin.getResource(name));
            config.setDefaults(defConfig);
        }
    }

    public void saveDefaultConfig(){
        File configfile = new File(plugin.getDataFolder(), name);
        if (!configfile.exists()) {
            plugin.saveResource(name, false);
        }
    }

    public List<String> getKeys(String section){
        List<String> keysList = new ArrayList<>();
        Set<String> keys = getConfig().getConfigurationSection(section).getKeys(false);
        for(String key : keys){
            keysList.add(key);
        }
        return keysList;
    }
}
