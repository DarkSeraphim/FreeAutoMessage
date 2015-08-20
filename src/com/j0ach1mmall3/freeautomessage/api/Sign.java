package com.j0ach1mmall3.freeautomessage.api;

import com.j0ach1mmall3.freeautomessage.Main;
import com.j0ach1mmall3.freeautomessage.api.internal.storage.yaml.Config;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

/**
 * Created by j0ach1mmall3 on 20:58 19/08/2015 using IntelliJ IDEA.
 */
public class Sign {
    private Main plugin;
    private Config customConfig;
    private FileConfiguration config;
    private String broadcasterIdentifier;
    private Location location;

    public Sign(Main plugin, String broadcasterIdentifier, Location location) {
        this.plugin = plugin;
        this.customConfig = new Config("signs.yml", plugin);
        customConfig.saveDefaultConfig();
        this.config = customConfig.getConfig();
        this.broadcasterIdentifier = broadcasterIdentifier;
        this.location = location;
    }

    public String getBroadcasterIdentifier() {
        return broadcasterIdentifier;
    }

    public void setBroadcasterIdentifier(String broadcasterIdentifier) {
        this.broadcasterIdentifier = broadcasterIdentifier;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void add() {
        String path = "SignsBroadcasters." + broadcasterIdentifier + ".";
        List<String> signs = config.getStringList(path + "Signs");
        signs.add(serializeLocation(location));
        config.set("SignsBroadcasters." + broadcasterIdentifier + ".Signs", signs);
        customConfig.saveConfig(config);
    }

    public void remove() {
        String path = "SignsBroadcasters." + broadcasterIdentifier + ".";
        List<String> signs = config.getStringList(path + "Signs");
        signs.remove(serializeLocation(location));
        config.set("SignsBroadcasters." + broadcasterIdentifier + ".Signs", signs);
        customConfig.saveConfig(config);
    }

    public boolean exists() {
        String path = "SignsBroadcasters." + broadcasterIdentifier + ".";
        List<String> signs = config.getStringList(path + "Signs");
        return signs.contains(serializeLocation(location));
    }

    public boolean isSignsBroadcaster() {
        return customConfig.getKeys("SignsBroadcasters").contains(broadcasterIdentifier);
    }

    private String serializeLocation(Location l) {
        return l.getWorld().getName() + "/" + String.valueOf(l.getBlockX()) + "/" + String.valueOf(l.getBlockY()) + "/" + String.valueOf(l.getBlockZ());
    }
}
